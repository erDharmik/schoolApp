package com.aspirepublicschool.gyanmanjari.Lunch;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

public class Lunch_Activity extends AppCompatActivity {
/*TextView day1_lunch,day2_lunch,day3_lunch,day4_lunch,day5_lunch,day6_lunch;
    TextView day1_breakfast,day2_breakfast,day3_breakfast,day4_breakfast,day5_breakfast,day6_breakfast;
    TextView day1_dinner,day2_dinner,day3_dinner,day4_dinner,day5_dinner,day6_dinner;*/

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_);

        /*getSupportActionBar().setTitle("Food Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
       /* day1_lunch = findViewById(R.id.day1_lunch);
        day2_lunch = findViewById(R.id.day2_lunch);
        day3_lunch = findViewById(R.id.day3_lunch);
        day4_lunch = findViewById(R.id.day4_lunch);
        day5_lunch = findViewById(R.id.day5_lunch);
        day6_lunch = findViewById(R.id.day6_lunch);

        day1_breakfast= findViewById(R.id.day1_breakFast);
        day2_breakfast = findViewById(R.id.day2_breakFast);
        day3_breakfast = findViewById(R.id.day3_breakFast);
        day4_breakfast = findViewById(R.id.day4_breakFast);
        day5_breakfast = findViewById(R.id.day5_breakFast);
        day6_breakfast = findViewById(R.id.day6_breakFast);

        day1_dinner = findViewById(R.id.day1_dinner);
        day2_dinner = findViewById(R.id.day2_dinner);
        day3_dinner = findViewById(R.id.day3_dinner);
        day4_dinner = findViewById(R.id.day4_dinner);
        day5_dinner = findViewById(R.id.day5_dinner);
        day6_dinner = findViewById(R.id.day6_dinner);*/
       /* if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
            loadBreakFast();
            loadLunch();
            loadDinner();
        }
        else {
            Intent i = new Intent(Lunch_Activity.this, NoInternetActivity.class);
            startActivity(i);
            finish();
        }*/

       // loadLunch();
        viewPager = findViewById(R.id.viewfood);
        tabLayout = findViewById(R.id.tabfood);
        viewPager.setAdapter(new FoodDetailsAdpater(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }

 /*   private void loadBreakFast() {
       // Common.progressDialogShow(this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String school_id = mPrefs.getString("school_id", "none");
        // Log.v("SSSSS",school_id);
        String LUNCH_URL = Common.GetWebServiceURL()+"displayBreakFast.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LUNCH_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(Lunch_Activity.this);
                try {
                    JSONArray array = new JSONArray(response);

                          *//*  if (object.getString("day").equals("Monday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Tuesday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Wednesday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Thursday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Friday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Saturday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }*//*
                    day1_breakfast.setText(array.getJSONObject(0).getString("breakfast"));
                    day2_breakfast.setText(array.getJSONObject(1).getString("breakfast"));
                    day3_breakfast.setText(array.getJSONObject(2).getString("breakfast"));
                    day4_breakfast.setText(array.getJSONObject(3).getString("breakfast"));
                    day5_breakfast.setText(array.getJSONObject(4).getString("breakfast"));
                    day6_breakfast.setText(array.getJSONObject(5).getString("breakfast"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Lunch_Activity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("school_id",school_id);
                return params;
            }
        };
        Volley.newRequestQueue(Lunch_Activity.this).add(stringRequest);
    }

    private void loadDinner() {
      //  Common.progressDialogShow(this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String school_id = mPrefs.getString("school_id", "none");
        // Log.v("SSSSS",school_id);
        String LUNCH_URL = Common.GetWebServiceURL()+"displayDinner.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LUNCH_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(Lunch_Activity.this);
                try {
                    JSONArray array = new JSONArray(response);

                          *//*  if (object.getString("day").equals("Monday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Tuesday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Wednesday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Thursday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Friday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Saturday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }*//*
                    day1_dinner.setText(array.getJSONObject(0).getString("dinner"));
                    day2_dinner.setText(array.getJSONObject(1).getString("dinner"));
                    day3_dinner.setText(array.getJSONObject(2).getString("dinner"));
                    day4_dinner.setText(array.getJSONObject(3).getString("dinner"));
                    day5_dinner.setText(array.getJSONObject(4).getString("dinner"));
                    day6_dinner.setText(array.getJSONObject(5).getString("dinner"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Lunch_Activity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("school_id",school_id);
                return params;
            }
        };
        Volley.newRequestQueue(Lunch_Activity.this).add(stringRequest);
    }

    private void loadLunch() {
        Common.progressDialogShow(this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String school_id = mPrefs.getString("sc_id", "none");
      Log.v("SSSSS",school_id);
        String LUNCH_URL = Common.GetWebServiceURL()+"displayLunch.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LUNCH_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                        Common.progressDialogDismiss(Lunch_Activity.this);
                try {
                    JSONArray array = new JSONArray(response);

                          *//*  if (object.getString("day").equals("Monday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Tuesday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Wednesday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Thursday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Friday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }
                            if (object.getString("day").equals("Saturday")){
                                day1.setText(array.getJSONObject(i+1).getString("lunch"));
                            }*//*
                          day1_lunch.setText(array.getJSONObject(0).getString("lunch"));
                            day2_lunch.setText(array.getJSONObject(1).getString("lunch"));
                            day3_lunch.setText(array.getJSONObject(2).getString("lunch"));
                            day4_lunch.setText(array.getJSONObject(3).getString("lunch"));
                            day5_lunch.setText(array.getJSONObject(4).getString("lunch"));
                            day6_lunch.setText(array.getJSONObject(5).getString("lunch"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Lunch_Activity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("school_id",school_id);
                return params;
            }
        };
        Volley.newRequestQueue(Lunch_Activity.this).add(stringRequest);
    }*/
}
