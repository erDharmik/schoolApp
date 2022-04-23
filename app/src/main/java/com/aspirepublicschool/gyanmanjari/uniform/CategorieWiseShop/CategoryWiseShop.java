package com.aspirepublicschool.gyanmanjari.uniform.CategorieWiseShop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Common;
import com.aspirepublicschool.gyanmanjari.uniform.ViewPagerAdapter;
import com.aspirepublicschool.gyanmanjari.uniform.student_profileModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryWiseShop extends AppCompatActivity {
    RecyclerView recproduct;
    List<CategoryShop> shopModelList = new ArrayList<>();
    ArrayList<student_profileModel> student_profileModelList=new ArrayList();
    String name,stu_id,classid,std,roll_no;
    ViewPager viewUniform;
   // NotificationBadge badge;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    public static String s_id,ss_name,s_img,s_cont,cat;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_shop);
        allocatememory();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewUniform.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        cat=getIntent().getExtras().getString("cat");

        for(int i = 0; i < dotscount; i++){

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

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        loadStudentData();
        recproduct.setHasFixedSize(true);
        recproduct.setLayoutManager(new LinearLayoutManager(this));
        //recproduct.addItemDecoration(new DividerItemDecoration(recproduct.getContext(), DividerItemDecoration.VERTICAL));
        loadShop();
    }
    private void allocatememory() {
        recproduct=findViewById(R.id.recproduct);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        viewUniform=findViewById(R.id.viewUniform);

    }
    private void loadShop() {
        String SHOP_URL = Common.GetWebServiceURL()+"displaycatshop.php";
        Log.d("aaa",SHOP_URL);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(CategoryWiseShop.this);
        final String city = mPrefs.getString("city", "none");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SHOP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("res",response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        shopModelList.add(new CategoryShop( object.getString("ss_id"),
                                object.getString("s_id"),
                                object.getString("s_cont"),
                                object.getString("ss_name"),
                                object.getString("addr"),
                                object.getString("s_img"),
                                object.getDouble("rating"),
                                object.getInt("ss_status"),object.getInt("s_aval")));
                        s_id=  object.getString("s_id");
                        ss_name= object.getString("ss_name");
                        s_img= object.getString("s_img");
                        s_cont=object.getString("s_cont");
                    }
                    CategoryShopAdapter adapter = new CategoryShopAdapter(CategoryWiseShop.this, shopModelList);
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
                Map<String, String> params = new HashMap<>();
                params.put("city",city);
                params.put("cat",cat);
                Log.d("aaa",params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(CategoryWiseShop.this).add(stringRequest);
    }
    private void loadStudentData() {
        Common.progressDialogShow(CategoryWiseShop.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(CategoryWiseShop.this);
        final String s_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id","none");
        String STUDENT_PROFILE_URL = com.aspirepublicschool.gyanmanjari.Common.GetWebServiceURL() +"student_profile.php";
        Log.v("profile",STUDENT_PROFILE_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STUDENT_PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(CategoryWiseShop.this);
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
                        name = jsonObject.getString("st_sname")+" "+jsonObject.getString("st_fname")+" "+jsonObject.getString("f_name");
                        Log.v("nameaaaa@@2", String.valueOf(name));
                        stu_id  =jsonObject.getString("std");

                        //class_id.setText(jsonObject.getString("std")+"-"+jsonObject.getString("division"));
                        std=jsonObject.getString("std")+"-"+jsonObject.getString("division");
                        roll_no=jsonObject.getString("roll_no");
                        String DP_URL = Common.GetBaseImageURL() + "student/" + student_profileModelList.get(i).getStu_img();
                        Log.d("DP_URL",DP_URL);
                        // Glide.with(ShopList.this).load(DP_URL).into(profile_image);
                        classid = jsonObject.getString("cid");

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CategoryWiseShop.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("class_id",classid);
                        editor.putString("stu_name",name);
                        editor.putString("stu_id",jsonObject.getString("stu_id"));
                        editor.putString("std",std);

                        editor.putString("city", jsonObject.getString("city"));
                        editor.putString("address",""+ jsonObject.getString("address")+","+
                                jsonObject.getString("city")+","+
                                jsonObject.getString("state"));
                        editor.putString("st_dp",DP_URL);
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("stu_id",s_id);
                params.put("sc_id",sc_id.toUpperCase());
                return params;
            }
        };
        Volley.newRequestQueue(CategoryWiseShop.this).add(stringRequest);
    }
}
