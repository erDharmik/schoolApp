package com.aspirepublicschool.gyanmanjari;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.CartViewHolder> {
   private Context context;
    private List<ViewCartModel> viewCartModels = new ArrayList<>();
    ViewCartModel viewCartModel;
    public CartViewAdapter(Context context, List<ViewCartModel> viewCartModels) {
        this.context = context;
        this.viewCartModels = viewCartModels;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(context).inflate(R.layout.cart_layout_raw, null);
        CartViewHolder container = new CartViewHolder(MyView);
        return container;
    }
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        viewCartModel = viewCartModels.get(position);
        holder.text_label.setText(""+viewCartModel.getLabel());
        holder.text_price.setText(""+viewCartModel.getAmount());
        holder.text_qty.setText(""+viewCartModel.getQty());
        String ImageUrl = "http://192.168.43.43:8080/school/images/Clothing/"+viewCartModel.getImg();
    //    Log.v("IIII",ImageUrl);
        Glide.with(context).load(ImageUrl).into(holder.imageView);

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = inflater.inflate(R.layout.delete_dialog, null);

                mBuilder.setView(mView);

                final AlertDialog mDialog = mBuilder.create();
                Button btn_yes=mView.findViewById(R.id.btn_yes);
                Button btn_no=mView.findViewById(R.id.btn_no);
                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    
                        DeleteItem();
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
                
            }
        });

        holder.img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText_update;
                final String[] quantity = new String[1];
                LayoutInflater inflater = LayoutInflater.from(context);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = inflater.inflate(R.layout.update_qty_dialog, null);
                editText_update = mView.findViewById(R.id.edt_quantity);
                editText_update.setText(viewCartModel.getQty());
                 mBuilder.setView(mView);
                quantity[0] = editText_update.getText().toString();
                final AlertDialog mDialog = mBuilder.create();
                Button btn_update=mView.findViewById(R.id.btn_update);
                Button btn_close=mView.findViewById(R.id.btn_close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quantity[0] = editText_update.getText().toString();
                        UpdateQuantity(quantity[0]);
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });

    }

    private void DeleteItem() {

        Common.progressDialogShow(context);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String uid = preferences.getString("stu_id","none");
        String CART_URL = "http://192.168.43.43:8080/school/ws/DeleteItem.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(context);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("Message").equals("success")){
                        Toast.makeText(context, "Quantity Updated", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context,CartActivity.class));
                        ((Activity)context).finish();
                    }

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
                Map<String,String> params = new HashMap<>();
                params.put("sin",viewCartModel.getSin());
                params.put("uid",uid);
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);

    }

    private void UpdateQuantity(final String quantity) {
        Common.progressDialogShow(context);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
       final String uid = preferences.getString("stu_id","none");
        String CART_URL = "http://192.168.43.43:8080/school/ws/UpdateQuantity.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(context);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("Message").equals("success")){
                        Toast.makeText(context, "Quantity Updated", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context,CartActivity.class));
                        ((Activity)context).finish();
                    }

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
                Map<String,String> params = new HashMap<>();
                params.put("sin",viewCartModel.getSin());
                params.put("qty",quantity);
                params.put("uid",uid);
                params.put("s_id",viewCartModel.getS_id());
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);


    }

    @Override
    public int getItemCount() {
        return viewCartModels.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView text_label,text_price,text_qty;
        LinearLayout linearLayout_cart;
        ImageView imageView,img_update,img_delete;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            img_update = itemView.findViewById(R.id.img_update);
            img_delete = itemView.findViewById(R.id.img_delete);
            text_label = itemView.findViewById(R.id.text_label);
            text_price = itemView.findViewById(R.id.text_price);
            text_qty = itemView.findViewById(R.id.text_qty);
            linearLayout_cart = itemView.findViewById(R.id.cart_liner);
        }
    }
}
