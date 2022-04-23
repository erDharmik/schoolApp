package com.aspirepublicschool.gyanmanjari;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

public class PaymentHistoryAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<PaymentHistory> paymentHistories;
    ArrayList<Receipt> receiptArrayList=new ArrayList<>();
    String receipt_id;

    public PaymentHistoryAdapter(Context ctx, ArrayList<PaymentHistory> paymentHistories) {
        this.ctx = ctx;
        this.paymentHistories = paymentHistories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_payment_history, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final PaymentHistory paymentHistory = paymentHistories.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtamount.setText(paymentHistory.getAmount());
        container.txtdate.setText(paymentHistory.getDate());
        container.txtamounttype.setText(" -"+paymentHistory.getPayment_mode());
        container.txtfeetype.setText("Term fees:"+paymentHistory.getTerm());
        receipt_id=paymentHistory.getReceipt_id();
        container.imgdownloadreceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendRequest(paymentHistory.getReceipt_id());
                //createpdf1();

            }
        });

    }

    private void SendRequest(final String Receipt_id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none");
        String Webserviceurl=Common.GetWebServiceURL()+"getFeeReciept.php";
        final StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    receiptArrayList.clear();
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        receiptArrayList.add(new Receipt(object.getString("term"),object.getString("feename"),object.getString("amount"),object.getString("payment_mode")
                                ,object.getString("date"),object.getString("reciept_id"),object.getString("sc_name")));

                    }
                    createpdf1(receiptArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("r_id",Receipt_id);
                data.put("sc_id",sc_id);
                data.put("stu_id",stu_id);
                return data;
            }
        };
        Volley.newRequestQueue(ctx).add(request);
    }
   private void createpdf1(ArrayList<Receipt> receiptArrayList) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String f_name = preferences.getString("stu_name", "none");
        final String f_cno = preferences.getString("mobile", "none");
        DisplayMetrics displaymetrics = new DisplayMetrics();
        float hight = ctx.getResources().getDisplayMetrics().heightPixels;;
        float width =ctx.getResources().getDisplayMetrics().widthPixels;;
        Log.v("HELLLOO", "Zocarro");
        int y = 360;

        int convertHighet = 200, convertWidth = 300;

        Resources mResources = ctx.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.mainlogo);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(790, 700, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        final Canvas canvas = page.getCanvas();

        final Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, 710, 200, true);
        //String CustName = checksum.name;
        //String CustContact = checksum.contact;
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(bitmap, 40, 50, null);
        //Log.v("Name And Contact : ",checksum.name + " " + checksum.contact);

       for(int i = 0;i<1;i++) {
           Receipt receipt = (Receipt) receiptArrayList.get(i);
           canvas.drawText("Receipt ID:- " + receipt.getReceipt_id(), 40, 220, paint);
           canvas.drawText("School Name:- " + receipt.getSc_name(), 40, 240, paint);


           canvas.drawText("Student Name:- " + f_name + "               " + "Mobile number:- " + f_cno, 40, 260, paint);
           canvas.drawText("Date:- " + receipt.getDate(), 40, 280, paint);
       }
        canvas.drawText("", 40, 300, paint);
       /* canvas.drawText("Term" + "                                                                                                                             " +
                "Fees Name" + "          " +
                "Amount" , 40, 320, paint);*/
       canvas.drawText("Term" ,40, 320, paint);
       canvas.drawText("Feename" ,350, 320, paint);
       canvas.drawText("Amount" ,550, 320, paint);

        canvas.drawText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", 40, 340, paint);
        int totalcharges=0;

         for (int i=0;i<receiptArrayList.size();i++) {
             Receipt receipt= (Receipt) receiptArrayList.get(i);


        canvas.drawText(receipt.getTerm(), 40, y, paint);

        canvas.drawText(receipt.getFeename(), 350, y, paint);

        canvas.drawText(receipt.getAmount(), 550, y, paint);
        totalcharges+=Integer.parseInt(receipt.getAmount());

        //canvas.drawText("------------------------------------------------------------------------------------------", 40, 240, paint);
        y += 20;
         }
        y += 20;

        canvas.drawText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", 40, y, paint);


        canvas.drawText("Total:- " + totalcharges, 510, y + 20, paint);
        document.finishPage(page);

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/SchoolZocarro/";
        File file = new File(directory_path);

        if (!file.exists()) {
            file.mkdirs();
        }

        String targetPdf = directory_path + "Receipt" + receipt_id + ".pdf";
        File filePath = new File(targetPdf);

        try {

            if (!filePath.exists()) {
                document.writeTo(new FileOutputStream(filePath));
            }
            if (filePath.exists()) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(directory_path);
                Log.v("@@@", "@@@ " + uri);
                intent.setDataAndType(uri, "application/pdf");

                PendingIntent pIntent = PendingIntent.getActivity(ctx, (int) System.currentTimeMillis(), intent, 0);


                Notification noti = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

                    noti = new Notification.Builder(ctx)
                            .setContentTitle("Hello " + "Student ")
                            .setContentText("Your Receipt is downloded !!").setSmallIcon(R.drawable.mainlogo).
                                    setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.mainlogo))
                            .setContentIntent(pIntent)
                            .setAutoCancel(true)
                            .build();

                }
                NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
                assert noti != null;
                noti.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(0, noti);

                Toast.makeText(ctx, "Receipt Downloaded", Toast.LENGTH_LONG).show();

            }


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(ctx, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        document.close();
    }


    @Override
    public int getItemCount() {
        return paymentHistories.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtdate,txtamounttype,txtamount,txtfeetype;
        ImageView imgdownloadreceipt;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtamounttype=itemView.findViewById(R.id.txtamounttype);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtamount=itemView.findViewById(R.id.txtamount);
            txtfeetype=itemView.findViewById(R.id.txtfeename);
            imgdownloadreceipt=itemView.findViewById(R.id.imgdownloadreceipt);





        }
    }
}
