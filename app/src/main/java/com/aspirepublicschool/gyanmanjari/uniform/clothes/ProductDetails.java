package com.aspirepublicschool.gyanmanjari.uniform.clothes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Cart.Cart;
import com.aspirepublicschool.gyanmanjari.uniform.Common;
import com.aspirepublicschool.gyanmanjari.uniform.SelectShopTypes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {
    ViewPager viewproduct;
    ImageView imageView;
    TextView txtsize, txtrupess, txtdesc, txtreadmore, txtlabel;
    Button btn_add_cart,btnanother,btnviewcart;
    EditText edt_qty;
    ArrayList<Sizespro> SizeList = new ArrayList<>();
    ArrayList<String> SizebycatList = new ArrayList<>();
    String size, pr_code;
    Spinner spnsize;
    String sin, s_id, amount, desc, sizes, selectedsize;
    int q;
    String qty = "1";
    String time;
    static String p_code, sell_id, cat;
    //ElegantNumberButton btnqty;
    private String[] sliderImageId = new String[]{};
    LinearLayout lnrbuttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        allocatememory();
        sin = getIntent().getExtras().getString("sin");
        Log.d("sin", sin);
        cat = getIntent().getExtras().getString("cat");
     /*   btnqty.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty = btnqty.getNumber();
                q = Integer.parseInt(btnqty.getNumber());
                btn_add_cart.setVisibility(View.VISIBLE);
            }
        });*/
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddToCart(qty);
              /*  }
                else {
                    startActivity(new Intent(ProductDetails.this, Cart.class));
                }*/
            }
        });
       btnviewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, Cart.class));
                finish();
            }
        });
       btnanother.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(ProductDetails.this, SelectShopTypes.class));
               finish();
           }
       });
        loadUniform();
        SetSpinnerSize();
        txtreadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ProductDetails.this);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProductDetails.this);
                View mView = inflater.inflate(R.layout.actvity_desc_diaglog, null);
                TextView txtdesc;
                txtdesc = mView.findViewById(R.id.txtdesc);
                txtdesc.setText(desc);
                final AlertDialog mDialog = mBuilder.create();
                Button btn_close = mView.findViewById(R.id.btn_close);
                mDialog.setView(mView);

                mDialog.show();
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });

            }
        });
        spnsize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sizes = spnsize.getItemAtPosition(spnsize.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),class_id,Toast.LENGTH_LONG).show();
                selectedsize = SizeList.get(position).getPr_code();
                //Log.v("selectedsize",selectedsize);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void allocatememory() {
        p_code = getIntent().getStringExtra("p_code");
        sell_id = getIntent().getStringExtra("s_id");
        viewproduct = findViewById(R.id.viewproduct);
        btn_add_cart = findViewById(R.id.btnaddtocart);
        spnsize = findViewById(R.id.spnsize);
        txtrupess = findViewById(R.id.txtrupess);
        txtdesc = findViewById(R.id.txtdesc);
        //btnqty = findViewById(R.id.btnqty);
        txtlabel = findViewById(R.id.txtlabel);
        txtreadmore = findViewById(R.id.txtreadmore);
        lnrbuttons = findViewById(R.id.lnrbuttons);
        btnviewcart=findViewById(R.id.btnviewcart);
        btnanother=findViewById(R.id.btnanother);


    }

    private void SetSpinnerSize() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String sc_id = preferences.getString("sc_id", "none").toLowerCase();
        String WebServiceUrl = Common.GetWebServiceURL() + "getSize.php";
        Log.d("subject", WebServiceUrl);
        StringRequest request = new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray res = new JSONArray(response);
                    Log.d("!!!", response);

                    SizebycatList.clear();
                    SizeList.clear();
                    int asize = res.length();
                    for (int i = 0; i < asize; i++) {
                        JSONObject object = res.getJSONObject(i);
                        size = object.getString("size");
                        //pr_code = object.getString("pr_code");
                        Sizespro s = new Sizespro(size, p_code);
                        SizeList.add(s);
                        SizebycatList.add(size);

                    }
                    spnsize.setAdapter(new ArrayAdapter<String>(ProductDetails.this, android.R.layout.simple_spinner_dropdown_item, SizebycatList));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("cat", cat);
                Log.d("###", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(ProductDetails.this).add(request);
    }

    private void AddToCart(final String qty) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        Common.progressDialogShow(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        time = sdf.format(new Date());

        String ADD_CART_URL = Common.GetWebServiceURL() + "UniformCart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_CART_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aaa", response);
                    Common.progressDialogDismiss(ProductDetails.this);
                    JSONArray array = new JSONArray(response);
                    int total = array.getJSONObject(0).getInt("total_cart");
                    if (total == 0) {
                        String Message = array.getJSONObject(1).getString("Message");
                        if (Message.equals("success")) {


                            lnrbuttons.setVisibility(View.VISIBLE);
                            btn_add_cart.setVisibility(View.GONE);
                        }
                    } else {
                        int total_seller = array.getJSONObject(1).getInt("total_seller");
                        if (total_seller == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetails.this);
                            builder.setMessage("Your cart can contain product from one seller only.Delete From the cart product from another seller?").setTitle("Replace cart item ?");

                            //Setting message manually and performing action on button click
                            builder.setMessage("Your cart can contain product from one seller only.Delete From the cart product from another seller?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            SendRequestToDelete();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                                        }
                                    });
                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.show();
                        } else {
                            String Message = array.getJSONObject(2).getString("Message");
                            Toast.makeText(ProductDetails.this, Message, Toast.LENGTH_LONG).show();
                            lnrbuttons.setVisibility(View.VISIBLE);
                            btn_add_cart.setVisibility(View.GONE);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.v("LLL", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sin", sin);
                params.put("qty", qty);
                params.put("uid", uid);
                params.put("s_id", sell_id);
                params.put("amount", amount);
                params.put("time", time);
                params.put("cat", cat);
                params.put("p_code", p_code);
                params.put("size", sizes);
                Log.d("params", params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(ProductDetails.this).add(stringRequest);
    }

    private void SendRequestToDelete() {
        String Webserviceurl = Common.GetWebServiceURL() + "deleteCartSeller.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    int total = Integer.parseInt(array.getJSONObject(0).getString("total"));
                    if (total == 0) {

                    } else {
                        String Message = array.getJSONObject(1).getString("Message");
                        Toast.makeText(ProductDetails.this, Message, Toast.LENGTH_LONG).show();
                        AddToCart(qty);
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
                return data;
            }
        };
        Volley.newRequestQueue(ProductDetails.this).add(request);
    }

    private void loadUniform() {
        Common.progressDialogShow(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("standard", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        String URL = Common.GetWebServiceURL() + "ViewProduct.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ProductDetails.this);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        object.getString("sin");
                        object.getString("p_code");
                        cat = object.getString("cat");
                      /*  brand.setText("Brand:-" + object.getString("brand"));
                        color.setText("Color:-" + object.getString("color"));
                        material.setText("Material:-" + object.getString("material"));*/
                        // txtsize.setText( object.getString("size"));
                        Log.v("VVVVV", object.getString("size"));
                        s_id = object.getString("s_id");
                        object.getString("sc_id");
                        // text_name.setText(object.getString("label"));
                        object.getString("stock");
                        object.getString("img1");
                        object.getString("img2");
                        object.getString("img3");
                        object.getString("img4");
                        object.getString("img5");
                        object.getString("img6");

                        String ImageUrl = Common.GetBaseImageURL() + "/uniform/" + object.getString("img1");
                        Log.v("VVVVV", ImageUrl);
                        sliderImageId = new String[]{object.getString("img1"),
                                object.getString("img2"),
                                object.getString("img3"),
                                object.getString("img4"),
                                object.getString("img5"),
                                object.getString("img6")};
                        desc = object.getString("des");
                        // Glide.with(ProductDetails.this).load(ImageUrl).into(imageView);
                        txtdesc.setText(object.getString("des"));
                        txtrupess.setText(object.getString("price"));
                        amount = object.getString("price");
                        txtlabel.setText(object.getString("label"));
                        //discount.setText("Discount:-" + object.getString("discount") + "%");
                    }
                    UniformImageAdapter adapterView = new UniformImageAdapter(ProductDetails.this, sliderImageId);
                    viewproduct.setAdapter(adapterView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sc_id", sc_id);
                params.put("sc_std", sc_std);
                params.put("sc_med", med);
                params.put("gender", gender);
             /*   params.put("sc_id", "SCIDN1");
                params.put("sc_std", "1");
                params.put("sc_med", "Gujrati");
                params.put("gender", "Male");*/
                params.put("sin", sin);
                params.put("p_code", p_code);
                Log.d("%%%", params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(ProductDetails.this).add(stringRequest);
    }
}
