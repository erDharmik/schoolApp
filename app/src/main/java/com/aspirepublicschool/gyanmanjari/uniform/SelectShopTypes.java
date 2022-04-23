package com.aspirepublicschool.gyanmanjari.uniform;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.uniform.Cart.Cart;
import com.aspirepublicschool.gyanmanjari.uniform.MyOrder.MyOrder;
import com.aspirepublicschool.gyanmanjari.uniform.ReturnReplace.ReturnedProduct;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectShopTypes extends AppCompatActivity {
    RecyclerView recproduct, recsub;
    List<ShopModel> shopModelList = new ArrayList<>();
    ArrayList<Subcategories> subcategoriesList = new ArrayList<>();
    ArrayList<student_profileModel> student_profileModelList = new ArrayList();
    CircleImageView profile_image;
    String name, stu_id, classid, std, roll_no;
    ViewPager viewUniform;
    LinearLayout sliderDotspanel;
    //NotificationBadge badge;
    private int dotscount;
    private ImageView[] dots;
    public static String s_id, ss_name, s_img, s_cont, ratings;
    ImageView shop_img;
    RelativeLayout relshop;
    TextView text_shop_name, text_shop_contact, txtaddress, txtrating;
    CardView cardothershop, cardcollabshop;
    LinearLayout fragmentadvertise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shop_types);
        allocatememory();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewUniform.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewUniform.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        recproduct.setHasFixedSize(true);
        recproduct.setLayoutManager(new LinearLayoutManager(this));
        recsub.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManagaer1
                = new LinearLayoutManager(SelectShopTypes.this, LinearLayoutManager.HORIZONTAL, false);
        recsub.setLayoutManager(horizontalLayoutManagaer1);
        //recsub.addItemDecoration(new DividerItemDecoration(recsub.getContext(), DividerItemDecoration.VERTICAL));
        Subcategories subcategories1 = new Subcategories("Full Uniform", R.mipmap.uniform);
        subcategoriesList.add(subcategories1);
        Subcategories subcategories2 = new Subcategories("Pant", R.mipmap.pant_icon);
        subcategoriesList.add(subcategories2);
        Subcategories subcategories3 = new Subcategories("Shirt", R.mipmap.shirt_icon);
        subcategoriesList.add(subcategories3);
        Subcategories subcategories4 = new Subcategories("Other Item", R.mipmap.shoe_icon);
        subcategoriesList.add(subcategories4);
        SubcategoriesAdapter adapter = new SubcategoriesAdapter(SelectShopTypes.this, subcategoriesList);
        recsub.setAdapter(adapter);
        loadShop();
        loadStudentData();
        cardothershop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectShopTypes.this, ShopList.class);
                startActivity(intent);
            }
        });
        /*cardcollabshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectShopTypes.this, ShopDetails.class);
                startActivity(intent);
            }
        });*/


    }


    private void loadShop() {
        Common.progressDialogShow(SelectShopTypes.this);
        String Webserviceurl = Common.GetWebServiceURL() + "DisplayShop.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(SelectShopTypes.this);
        final String uid = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    shopModelList.clear();
                    Common.progressDialogDismiss(SelectShopTypes.this);
                    JSONArray array = new JSONArray(response);
                    Log.d("bbb", response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                       /* {
                            "ss_id": "SHIDN1",
                                "sc_id": "SCIDN14",
                                "s_id": "ZCIN1",
                                "rating": 3.5,
                                "s_cont": 9033532883,
                                "ss_name": "Mayur Book Stall",
                                "addr": "Bhavnagar",
                                "s_img": "90335328831.jpg"
                        }*/
                        shopModelList.add(new ShopModel(
                                object.getString("sc_id"),
                                object.getString("ss_id"),
                                object.getString("s_id"),
                                object.getString("s_cont"),
                                object.getString("ss_name"),
                                object.getString("addr"),
                                object.getString("s_img"),
                                object.getDouble("rating"),object.getString("s_aval")));
                        s_id = object.getString("s_id");
                        Log.d("ss", s_id);
                        ss_name = object.getString("ss_name");
                        s_img = object.getString("s_img");
                        s_cont = object.getString("s_cont");
                        ratings = String.valueOf(object.getDouble("rating"));
                    }
                    ShopAdapter adapter = new ShopAdapter(SelectShopTypes.this, shopModelList);
                    recproduct.setAdapter(adapter);


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
                data.put("sc_id",sc_id);
                Log.d("aaa", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(SelectShopTypes.this).add(request);
    }

    private void allocatememory() {
        recproduct = findViewById(R.id.recproduct);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        viewUniform = findViewById(R.id.viewUniform);
        recsub = findViewById(R.id.recsub);
        shop_img = findViewById(R.id.imgshop);
        /*relshop = findViewById(R.id.relshop);
        text_shop_name = findViewById(R.id.txtshop);
        text_shop_contact = findViewById(R.id.txtcontact);
        txtaddress = findViewById(R.id.txtaddress);
         txtrating = findViewById(R.id.txtrating);*/
        cardothershop = findViewById(R.id.cardothershop);

        fragmentadvertise = findViewById(R.id.fragmentadvertise);

    }

    private void loadStudentData() {

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(SelectShopTypes.this);
        final String s_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none");
        String STUDENT_PROFILE_URL = com.aspirepublicschool.gyanmanjari.Common.GetWebServiceURL() + "student_profile.php";
        Log.v("profile", STUDENT_PROFILE_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STUDENT_PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject jsonObject = array.getJSONObject(i);

                        student_profileModelList.add(new student_profileModel(
                                jsonObject.getInt("id"),
                                jsonObject.getString("stu_id"),
                                jsonObject.getString("roll_no"),
                                jsonObject.getString("stu_img"),
                                jsonObject.getString("f_img"),
                                jsonObject.getString("m_img"),
                                jsonObject.getString("gr_no"),
                                jsonObject.getString("st_sname"),
                                jsonObject.getString("st_fname"),
                                jsonObject.getString("f_name"),
                                jsonObject.getString("m_name"),
                                jsonObject.getString("st_cno"),
                                jsonObject.getString("f_cno"),
                                jsonObject.getString("m_cno"),
                                jsonObject.getString("st_email"),
                                jsonObject.getString("cid"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("med"),
                                jsonObject.getString("board"),
                                jsonObject.getString("address"),
                                jsonObject.getString("city"),
                                jsonObject.getString("state"),
                                jsonObject.getString("dob"),
                                jsonObject.getString("nation"),
                                jsonObject.getString("stream"),
                                jsonObject.getString("cast"),
                                jsonObject.getString("std"),
                                jsonObject.getString("division")

                        ));
                        name = jsonObject.getString("st_sname") + " " + jsonObject.getString("st_fname") + " " + jsonObject.getString("f_name");
                        Log.v("nameaaaa@@2", String.valueOf(name));
                        stu_id = jsonObject.getString("std");
                        //class_id.setText(jsonObject.getString("std")+"-"+jsonObject.getString("division"));
                        std = jsonObject.getString("std") + "-" + jsonObject.getString("division");
                        roll_no = jsonObject.getString("roll_no");
                        String DP_URL = Common.GetBaseImageURL() + "student/" + student_profileModelList.get(i).getStu_img();
                        Log.d("DP_URL", DP_URL);
                        // Glide.with(ShopList.this).load(DP_URL).into(profile_image);
                        classid = jsonObject.getString("cid");

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SelectShopTypes.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("class_id", classid);
                        editor.putString("stu_name", name);
                        editor.putString("stu_id", jsonObject.getString("stu_id"));
                        editor.putString("std", std);
                        editor.putString("address", "" + jsonObject.getString("address") + "," +
                                jsonObject.getString("city") + "," +
                                jsonObject.getString("state"));
                        editor.putString("st_dp", DP_URL);
                        editor.apply();

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
                params.put("stu_id", s_id);
                params.put("sc_id", sc_id.toLowerCase());
                return params;
            }
        };
        Volley.newRequestQueue(SelectShopTypes.this).add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_image);
        View view = menu.findItem(R.id.action_cart).getActionView();
       // badge = (NotificationBadge) view.findViewById(R.id.badge);
        updatecartcount();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectShopTypes.this, Cart.class);
                startActivity(intent);
            }
        });


        return true;
    }

    private void updatecartcount() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("std", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
      /*  if (badge == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String WebServiceUrl = Common.GetWebServiceURL() + "countCartItem.php";
                StringRequest request = new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            int count = Integer.parseInt(object.getString("count"));
                            Log.d("count@@", "" + count);
                            if (count == 0) {
                                badge.setVisibility(View.INVISIBLE);
                            } else {
                                badge.setVisibility(View.VISIBLE);
                                badge.setText("" + count);
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

                request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
                Volley.newRequestQueue(SelectShopTypes.this).add(request);

            }
        });
*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_order:
                intent = new Intent(SelectShopTypes.this, MyOrder.class);
                startActivity(intent);
                // do something
                return true;
            case R.id.action_cart:
                intent = new Intent(SelectShopTypes.this, Cart.class);
                startActivity(intent);
                // do something
                return true;
            case R.id.action_return:
                intent = new Intent(SelectShopTypes.this, ReturnedProduct.class);
                startActivity(intent);
                // do something
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
