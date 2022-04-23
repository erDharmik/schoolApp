package com.aspirepublicschool.gyanmanjari.Stationery.MyOrder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Stationery.ReturnReplace.ReasonReplace;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OrderDetails extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 121;
    ImageView imgproduct;
    TextView txtproductname, txtshopname, txtamount, txtqty, txtorderid, txtorderdate, txtpaymethod, txtaddress, txtbasepay, txtpacking, txtgst, txtdiscount, txtPrice, txtpin, txtdelivery, txtdownladstatement;
    String order_id, shopname, amount, qty, product_name, status, payment, address, basepay, delivery, img, date, sin, s_id, mobile_no,size;
    int price;
  /*  VerticalStepView verticalStepView;
    List<Step> stepcount = new ArrayList<>();*/
    LinearLayout lnrverifypin, lnrproductreview,lnrreturn;
    RatingBar ratproduct;
    Button btnrating,txtreturn;
    float totalcharges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        allocatememory();
        if (ActivityCompat.shouldShowRequestPermissionRationale(OrderDetails.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(OrderDetails.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(OrderDetails.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        order_id = getIntent().getExtras().getString("order_id");
        shopname = getIntent().getExtras().getString("shop_name");
        basepay = getIntent().getExtras().getString("amount");
        qty = getIntent().getExtras().getString("qty");
        product_name = getIntent().getExtras().getString("pro_name");
        img = getIntent().getExtras().getString("img");
        date = getIntent().getExtras().getString("order_date");
        status = getIntent().getExtras().getString("status");
        amount = getIntent().getExtras().getString("amount");
        payment = getIntent().getExtras().getString("paymethod");
        address = getIntent().getExtras().getString("address");
        sin = getIntent().getExtras().getString("sin");
        s_id = getIntent().getExtras().getString("s_id");
        mobile_no = getIntent().getExtras().getString("mobile_no");
        price = Integer.parseInt(basepay) * Integer.parseInt(qty);
        size="Not Set";
        Log.d("price", "" + price);


       /* stepcount.add(new Step("Order Placed", Step.State.CURRENT));
        stepcount.add(new Step("Order Confirmed"));
        stepcount.add(new Step("Order Shipped"));
        stepcount.add(new Step("Order Delivered"));
        //stepcount.add(new Step("Order Return"));

        verticalStepView.setSteps(stepcount);

        verticalStepView // Also applies to VerticalStepView
                // Drawables
                .setCompletedStepIcon(AppCompatResources.getDrawable(this, R.drawable.ic_check))
                .setNotCompletedStepIcon(AppCompatResources.getDrawable(this, R.drawable.ic_uncheck))
                .setCurrentStepIcon(AppCompatResources.getDrawable(this, R.drawable.ic_attention))
                // Text colors
                .setCompletedStepTextColor(Color.DKGRAY) // Default: Color.WHITE
                .setNotCompletedStepTextColor(Color.DKGRAY) // Default: Color.WHITE
                .setCurrentStepTextColor(Color.BLACK) // Default: Color.WHITE
                // Line colors
                .setCompletedLineColor(Color.parseColor("#ea655c")) // Default: Color.WHITE
                .setNotCompletedLineColor(Color.parseColor("#eaac5c")) // Default: Color.WHITE
                // Text size (in sp)
                .setTextSize(15) // Default: 14sp
                // Drawable radius (in dp)
                .setCircleRadius(15) // Default: ~11.2dp
                // Length of lines separating steps (in dp)
                .setLineLength(50).setReverse(false); // Default: ~34dp*/
        txtorderid.setText(order_id);
        txtproductname.setText(product_name);
        txtbasepay.setText(amount + " ₹");
        //txtamount.setText(""+price);
        txtqty.setText(qty);
        txtorderdate.setText(date);
        txtaddress.setText(address);
        if (status.equals("0")) {
          /*  stepcount.get(0).setState(Step.State.COMPLETED);
            stepcount.get(1).setState(Step.State.CURRENT);
            verticalStepView.setSteps(stepcount);*/
            lnrproductreview.setVisibility(View.GONE);
            txtdownladstatement.setVisibility(View.GONE);

        } else if (status.equals("1")) {
            /*stepcount.get(0).setState(Step.State.COMPLETED);
            stepcount.get(1).setState(Step.State.COMPLETED);
            stepcount.get(2).setState(Step.State.CURRENT);
            verticalStepView.setSteps(stepcount);*/
            lnrproductreview.setVisibility(View.GONE);
            txtdownladstatement.setVisibility(View.VISIBLE);
        } else if (status.equals("4")) {
          /*  stepcount.get(0).setState(Step.State.COMPLETED);
            stepcount.get(1).setState(Step.State.COMPLETED);
            stepcount.get(2).setState(Step.State.COMPLETED);
            stepcount.get(3).setState(Step.State.CURRENT);
            verticalStepView.setSteps(stepcount);*/
            lnrproductreview.setVisibility(View.GONE);
            txtdownladstatement.setVisibility(View.VISIBLE);

        } else if (status.equals("5")) {
          /*  stepcount.get(0).setState(Step.State.COMPLETED);
            stepcount.get(1).setState(Step.State.COMPLETED);
            stepcount.get(2).setState(Step.State.COMPLETED);
            stepcount.get(3).setState(Step.State.COMPLETED);
            // stepcount.get(4).setState(Step.State.CURRENT);
            verticalStepView.setSteps(stepcount);*/
            lnrverifypin.setVisibility(View.GONE);
            lnrproductreview.setVisibility(View.VISIBLE);
            txtdownladstatement.setVisibility(View.VISIBLE);
            lnrreturn.setVisibility(View.VISIBLE);


        }
        checkratitngs();
        btnrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewProduct();
            }
        });
        txtdownladstatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createpdf1();

            }
        });
        txtreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderDetails.this, ReasonReplace.class);
                intent.putExtra("order_id",order_id);
                intent.putExtra("sin",sin);
                intent.putExtra("s_id", s_id);
                intent.putExtra("qty", qty);
                intent.putExtra("price", price);
                intent.putExtra("payment_method", payment);
                intent.putExtra("address", address);
                intent.putExtra("mobileno", mobile_no);
                intent.putExtra("size", size);
                startActivity(intent);
            }
        });
       /* if(lnrproductreview.getVisibility()==View.VISIBLE)
        {

        }*/
      /*  else if(status.equals("4"))
        {
            stepcount.get(0).setState(Step.State.COMPLETED);
            stepcount.get(1).setState(Step.State.COMPLETED);
            stepcount.get(2).setState(Step.State.COMPLETED);
            stepcount.get(3).setState(Step.State.COMPLETED);
            stepcount.get(4).setState(Step.State.COMPLETED);
            verticalStepView.setSteps(stepcount);


        }*/
        /*else if(status.equals("5"))
        {
            stepcount.get(0).setState(Step.State.COMPLETED);
            stepcount.get(1).setState(Step.State.COMPLETED);
            stepcount.get(2).setState(Step.State.COMPLETED);
            stepcount.get(3).setState(Step.State.COMPLETED);
            stepcount.get(4).setState(Step.State.COMPLETED);
            stepcount.get(5).setState(Step.State.CURRENT);
            verticalStepView.setSteps(stepcount);

        }*/

        txtshopname.setText(shopname);
        String ImageUrl = "https://www.aspirepublicschool.net/zocarro/image"+"/stationery/book/"+"textbook/"+img;
        Glide.with(OrderDetails.this).load(ImageUrl).into(imgproduct);
        displayOrderDetail();
        SendRequest();
    }

    private void createpdf1() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String f_name = preferences.getString("f_name", "none");
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;
        Log.v("HELLLOO", "AAA");
        int y = 360;

        int convertHighet = 200, convertWidth = 300;

        Resources mResources = getResources();
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
        canvas.drawText("Order ID:- " + order_id, 40, 220, paint);
        canvas.drawText("Shop Name:- " + shopname, 40, 240, paint);


        canvas.drawText("Cutomer Name:- " + f_name + "               " + "Mobile number:- " + mobile_no, 40, 260, paint);
        canvas.drawText("Date:- " + date, 40, 280, paint);

        canvas.drawText("", 40, 300, paint);
        canvas.drawText("Products" + "                                                                                                                             " +
                "Quantity" + "          " +
                "Price/item" + "          " + "Price", 40, 320, paint);
        canvas.drawText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", 40, 340, paint);


        /* for (int i=0;i<MyOrder.orderModelList.size();i++) {*/

        canvas.drawText(product_name, 40, y, paint);

        canvas.drawText(qty, 480, y, paint);

        canvas.drawText(String.valueOf(basepay), 550, y, paint);

        canvas.drawText(String.valueOf(price), 630, y, paint);


        canvas.drawText("-------------------------------------------", 530, y+20, paint);
        canvas.drawText("Shipping Charges:" + totalcharges, 530, y+30, paint);
        //canvas.drawText("------------------------------------------------------------------------------------------", 40, 240, paint);
        y += 20;
        /*   }*/
        y += 20;

        canvas.drawText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", 40, y, paint);


        canvas.drawText("Total:- " + (price + totalcharges), 590, y + 20, paint);
        document.finishPage(page);

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/BillZocarro/";
        File file = new File(directory_path);

        if (!file.exists()) {
            file.mkdirs();
        }

        String targetPdf = directory_path + "Invoice" + order_id + ".pdf";
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

                PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);


                Notification noti = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

                    noti = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Hello " + "Customer ")
                            .setContentText("Your Invoice is downloded !!").setSmallIcon(R.drawable.mainlogo).
                                    setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.mainlogo))
                            .setContentIntent(pIntent)
                            .setAutoCancel(true)
                            .build();

                }
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                assert noti != null;
                noti.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(0, noti);

                Toast.makeText(OrderDetails.this, "Invoice Downloaded", Toast.LENGTH_LONG).show();

            }


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        document.close();
    }


    private void checkratitngs() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        String Webserviceurl = Common.GetWebServiceURL() + "ratingStatus.php";
        final StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int status = object.getInt("status");
                        if (status == 0) {
                            btnrating.setVisibility(View.VISIBLE);
                        } else {
                            float rating = Float.parseFloat(String.valueOf(object.getInt("rating")));
                            ratproduct.setRating(rating);
                            btnrating.setVisibility(View.GONE);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("uid", uid);
                data.put("sin", sin);
                data.put("order_id", order_id);
                return data;
            }
        };
        Volley.newRequestQueue(OrderDetails.this).add(request);
    }

    private void reviewProduct() {
        com.aspirepublicschool.gyanmanjari.Common.progressDialogShow(OrderDetails.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        String Webserviceurl = Common.GetWebServiceURL() + "rating.php";
        final StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    com.aspirepublicschool.gyanmanjari.Common.progressDialogDismiss(OrderDetails.this);
                    Toast.makeText(OrderDetails.this, message, Toast.LENGTH_SHORT).show();
                    ratproduct.setEnabled(false);
                    btnrating.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("uid", uid);
                data.put("s_id", s_id);
                data.put("sin", sin);
                data.put("order_id", order_id);
                data.put("rating", "" + ratproduct.getRating());
                return data;
            }
        };
        Volley.newRequestQueue(OrderDetails.this).add(request);
    }

    private void displayOrderDetail() {
        String Webserviceurl = Common.GetWebServiceURL() + "countOrder.php";
        final StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int count = object.getInt("count");
                    int delivery = Integer.parseInt(object.getString("delivery_charge"));
                    totalcharges = delivery / count;
                    Log.d("aaa", "" + totalcharges);
                    txtdelivery.setText("" + totalcharges + " ₹");
                    txtPrice.setText("" + (price + totalcharges) + " ₹");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("order_id", order_id);
                return data;
            }
        };
        Volley.newRequestQueue(OrderDetails.this).add(request);
    }

    private void SendRequest() {
        String Webserviceurl = Common.GetWebServiceURL() + "displayverificationCode.php";
        final StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject object = array.getJSONObject(0);
                    String vocde = object.getString("v_code");
                    txtpin.setText(vocde);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("order_id", order_id);
                return data;
            }
        };
        Volley.newRequestQueue(OrderDetails.this).add(request);
    }

    private void allocatememory() {
        imgproduct = findViewById(R.id.imgproduct);
        txtproductname = findViewById(R.id.txtproductname);
        txtshopname = findViewById(R.id.txtshopname);
        txtamount = findViewById(R.id.txtamount);
        txtqty = findViewById(R.id.txtqty);
        txtorderid = findViewById(R.id.txtorderid);
        txtorderdate = findViewById(R.id.txtorderdate);
        txtpaymethod = findViewById(R.id.txtpaymethod);
        txtaddress = findViewById(R.id.txtaddress);
        txtbasepay = findViewById(R.id.txtbasepay);
        txtdelivery = findViewById(R.id.txtdelivery);
        // txtpacking=findViewById(R.id.txtpacking);
        txtPrice = findViewById(R.id.txtPrice);
        txtpin = findViewById(R.id.txtpin);
       // verticalStepView = findViewById(R.id.step_view0);
        lnrverifypin = findViewById(R.id.lnrverifypin);
        lnrreturn= findViewById(R.id.lnrreturn);
        ratproduct = findViewById(R.id.ratproduct);
        lnrproductreview = findViewById(R.id.lnrproductreview);
        btnrating = findViewById(R.id.btnrating);
        txtdownladstatement = findViewById(R.id.txtdownladstatement);
        txtreturn = findViewById(R.id.txtreturn);

    }
}
