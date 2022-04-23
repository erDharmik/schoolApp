package com.aspirepublicschool.gyanmanjari.uniform.Cart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.aspirepublicschool.gyanmanjari.uniform.clothes.ProductDetails;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Common;

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
    String ImageUrl;

    public CartViewAdapter(Context context, List<ViewCartModel> viewCartModels) {
        this.context = context;
        this.viewCartModels = viewCartModels;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(context).inflate(R.layout.activity_cart_row, null);
        CartViewHolder container = new CartViewHolder(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        viewCartModel = viewCartModels.get(position);
        holder.txtproductname.setText("" + viewCartModel.getLabel());
        int price = Integer.parseInt(viewCartModel.getAmount()) * Integer.parseInt(viewCartModel.getQty());
        Log.d("amount", "" + price);
        holder.txtprice.setText("" + price);
        holder.txtshopname.setText("" + viewCartModel.getSsname());
        holder.display.setText("" + viewCartModel.getQty());
        holder.txtsize.setText(""+viewCartModel.getSize());
        holder.relcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetails.class);
                intent.putExtra("sin", viewCartModel.getSin());
                intent.putExtra("cat",viewCartModel.getCat());
                intent.putExtra("s_id",viewCartModel.getS_id());
                intent.putExtra("p_code",viewCartModel.getP_code());
                context.startActivity(intent);
            }
        });
        if(viewCartModel.getCat().equals("Full Uniform")) {
            ImageUrl = Common.GetBaseImageURL() + "/uniform/" + viewCartModel.getImg();
            Log.d("img",ImageUrl);

        }
        else if(viewCartModel.getCat().equals("Shirt"))
        {
            ImageUrl = Common.GetBaseImageURL() + "/shirt/" + viewCartModel.getImg();
            Log.d("img",ImageUrl);

        }
        else if(viewCartModel.getCat().equals("Pant"))
        {
            ImageUrl = Common.GetBaseImageURL() + "/pant/" + viewCartModel.getImg();
            Log.d("img",ImageUrl);

        }
        else if(viewCartModel.getCat().equals("Other Items"))
        {
            ImageUrl = Common.GetBaseImageURL() + "/otheritems/" + viewCartModel.getImg();
            Log.d("img",ImageUrl);

        }
        Glide.with(context).load(ImageUrl).into(holder.imgproduct);

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = inflater.inflate(R.layout.delete_dialog, null);

                mBuilder.setView(mView);

                final AlertDialog mDialog = mBuilder.create();
                Button btn_yes = mView.findViewById(R.id.btn_yes);
                Button btn_no = mView.findViewById(R.id.btn_no);
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

        holder.display.setOnClickListener(new View.OnClickListener() {
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
                Button btn_update = mView.findViewById(R.id.btn_update);
                Button btn_close = mView.findViewById(R.id.btn_close);
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
        final String uid = preferences.getString("stu_id", "none");
        String CART_URL = Common.GetWebServiceURL() + "DeleteItem.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(context);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("Message").equals("success")) {
                        Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Cart.class));
                        ((Activity) context).finish();
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
                Map<String, String> params = new HashMap<>();
                params.put("sin", viewCartModel.getSin());
                params.put("uid",uid);
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);

    }

    private void UpdateQuantity(final String quantity) {
        Common.progressDialogShow(context);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String uid = preferences.getString("stu_id", "none");
        String CART_URL = Common.GetWebServiceURL() + "UpdateQuantity.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(context);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("Message").equals("success")) {
                        Toast.makeText(context, "Quantity Updated", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Cart.class));
                        ((Activity) context).finish();
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
                Map<String, String> params = new HashMap<>();
                params.put("sin",viewCartModel.getSin());
                params.put("qty", quantity);
                params.put("uid", uid);
                params.put("s_id", viewCartModel.getS_id());
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
        TextView txtproductname, txtshopname, display, txtprice,txtsize;
        ImageView imgdelete, imgproduct;
        RelativeLayout relcart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgdelete = itemView.findViewById(R.id.imgdelete);
            imgproduct = itemView.findViewById(R.id.imgproduct);
            txtproductname = itemView.findViewById(R.id.txtproductname);
            txtshopname = itemView.findViewById(R.id.txtshopname);
            txtsize = itemView.findViewById(R.id.txtsize);
            display = itemView.findViewById(R.id.display);
            txtprice = itemView.findViewById(R.id.txtprice);
            relcart = itemView.findViewById(R.id.relcart);
        }
    }
}
