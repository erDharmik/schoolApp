package com.aspirepublicschool.gyanmanjari.NewRegister.Address;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.GmapActivity;
import com.aspirepublicschool.gyanmanjari.PolicyDashboard.PolicyDashboardMainActivity;
import com.aspirepublicschool.gyanmanjari.Profile.ProfileMainActivity;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {

    Spinner cityEdit;
    Spinner stateEdit, landmarkEdit;
    EditText pinEdit, addressEdit;
    String address, city, pin, state, landmark,time, number, latitude, longitude;
    ImageView locationImg;
    Button submit;
    TextView locationStatus;
    LinearLayout changeLocation, lnrAddress;
    FusedLocationProviderClient mFusedLocationClient;
    String status = "none";
    HashSet<String> hashSet = new HashSet<>();

    ArrayList<String> cityList = new ArrayList<>();
    ArrayList<String> stateList = new ArrayList<>();
    ArrayList<String> landmarkList = new ArrayList<>();
    ArrayList<String> allList = new ArrayList<>();


    // Initializing other items
    // from layout file
    String lat = null , lon = null;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address2);

        getSupportActionBar().hide();

        time = getIntent().getStringExtra("time");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        number = preferences.getString("number", null);
//        address = preferences.getString("address", null);
//        city = preferences.getString("city", null);
//        state = preferences.getString("state", null);
//        pin = preferences.getString("pin", null);

        addressEdit = findViewById(R.id.addressEdit);
        cityEdit = findViewById(R.id.cityEdit);
        stateEdit = findViewById(R.id.stateEdit);
        pinEdit = findViewById(R.id.pinEdit);
        landmarkEdit = findViewById(R.id.landmarkEdit);
        submit = findViewById(R.id.submitInfo);
        locationImg = findViewById(R.id.locationImg);
        locationStatus = findViewById(R.id.locationStatus);
        changeLocation = findViewById(R.id.changeLocation);
        lnrAddress = findViewById(R.id.lnrAddress);

        String address = getIntent().getStringExtra("address");
        addressEdit.setText(address);

        setSpinnerLandmark();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    registerAddress();
            }
        });

        locationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), GmapActivity.class);
                startActivity(intent);

            }
        });

        stateEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cityEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                landmarkFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void registerAddress() {
        String Webserviceurl= Common.GetWebServiceURL()+"insertAddress.php";
        //String Webserviceurl= "https://www.zocarro.net/zocarro_mobile_app/ws/"+"gethwid.php";
        Request request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("$$$", response);
                    landmarkList.clear();
                    JSONArray array=new JSONArray(response);

                    String status = array.getJSONObject(0).getString("message");

                    if (status.equals("true")){
                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), PolicyDashboardMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("number", number);
                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("address", addressEdit.getText().toString().trim());
                data.put("pin", pinEdit.getText().toString().trim());
                data.put("state", stateEdit.getSelectedItem().toString());
                data.put("city", cityEdit.getSelectedItem().toString());
                data.put("landmark", landmarkEdit.getSelectedItem().toString());
                data.put("time", time);
                data.put("number", number);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(AddressActivity.this).add(request);

    }

    private void landmarkFilter() {
        String Webserviceurl= Common.GetWebServiceURL()+"landmarkFilter.php";
        //String Webserviceurl= "https://www.zocarro.net/zocarro_mobile_app/ws/"+"gethwid.php";
        Request request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("$$$", response);
                    landmarkList.clear();
                    JSONArray array=new JSONArray(response);

                    String error=array.getJSONObject(0).getString("error");

                    if(error.equals("no error")) {
                        int total = array.getJSONObject(1).getInt("total");
                        if (total != 0) {
                            for (int i = 2; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                landmarkList.add(object.getString("landmark"));
                            }

                            landmarkEdit.setAdapter(new ArrayAdapter<String>(AddressActivity.this, android.R.layout.simple_spinner_dropdown_item, landmarkList));

                        }
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("state", stateEdit.getSelectedItem().toString());
                data.put("city", cityEdit.getSelectedItem().toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(AddressActivity.this).add(request);

    }

    private void updateInfo() {

        final String addressfinal = addressEdit.getText().toString().trim();
        final String landmarkfinal = landmarkEdit.getSelectedItem().toString();
        final String cityfinal = cityEdit.getSelectedItem().toString().trim();
        final String statefinal = stateEdit.getSelectedItem().toString();
        final String pinfinal = pinEdit.getText().toString().trim();

        if (addressfinal.equals("")){
            Toast.makeText(getApplicationContext(), "Please Provide Address", Toast.LENGTH_SHORT).show();
        }
        else if (landmarkfinal.equals("")){
            Toast.makeText(getApplicationContext(), "Please Provide Landmark", Toast.LENGTH_SHORT).show();
        }else if (cityfinal.equals("")){
            Toast.makeText(getApplicationContext(), "Please Provide City", Toast.LENGTH_SHORT).show();
        }else if (statefinal.equals("")){
            Toast.makeText(getApplicationContext(), "Please Provide State", Toast.LENGTH_SHORT).show();
        }else if (pinfinal.equals("")){
            Toast.makeText(getApplicationContext(), "Please Provide PIN", Toast.LENGTH_SHORT).show();
        }
        else if (addressEdit.getText().toString().trim().equals(address) &&
                cityEdit.getSelectedItem().toString().trim().equals(city) &&
                pinEdit.getText().toString().trim().equals(pin) &&
                stateEdit.getSelectedItem().toString().trim().equals(state) &&
                landmarkEdit.getSelectedItem().toString().equals(landmark)){
            Toast.makeText(getApplicationContext(), "You have no changes to made", Toast.LENGTH_SHORT).show();
        }
        else{

            String WebServiceUrl =  "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro_mobile_app/ws/updateAddress.php";
            StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("true")){
                        Toast.makeText(getApplicationContext(), "Address Details Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ProfileMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                    else if(response.contains("fail")){
                        Toast.makeText(getApplicationContext(), "Failed, Try Again!", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> data=new HashMap<>();
                    data.put("address", addressfinal);
                    data.put("landmark", landmarkfinal);
                    data.put("city", cityfinal);
                    data.put("state", statefinal);
                    data.put("pin", pinfinal);
                    data.put("number", "9586417374");
                    return data;
                }
            };
            Volley.newRequestQueue(getApplicationContext()).add(request);
        }

    }



    private void cityFilter() {
        String Webserviceurl= Common.GetWebServiceURL()+"cityFilter.php";
        //String Webserviceurl= "https://www.zocarro.net/zocarro_mobile_app/ws/"+"gethwid.php";
        Request request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("$$$", response);
                    JSONArray array=new JSONArray(response);
                    cityList.clear();

                    String error=array.getJSONObject(0).getString("error");

                    if(error.equals("no error")) {
                        int total = array.getJSONObject(1).getInt("total");
                        if (total != 0) {
                            for (int i = 2; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                cityList.add(object.getString("city"));
                            }

                            cityEdit.setAdapter(new ArrayAdapter<String>(AddressActivity.this, android.R.layout.simple_spinner_dropdown_item, cityList));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("state", stateEdit.getSelectedItem().toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(AddressActivity.this).add(request);

    }


    private void setSpinnerLandmark() {

        String Webserviceurl= Common.GetWebServiceURL()+"landmark.php";
        //String Webserviceurl= "https://www.zocarro.net/zocarro_mobile_app/ws/"+"gethwid.php";
        Request request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("$$$", response);
                    JSONArray array=new JSONArray(response);

                    String error=array.getJSONObject(0).getString("error");

                    if(error.equals("no error")) {
                        int total = array.getJSONObject(1).getInt("total");
                        if (total != 0) {
                            for (int i = 2; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                cityList.add(object.getString("city"));
                                stateList.add(object.getString("state"));
                                landmarkList.add(object.getString("landmark"));

                            }

                            hashSet.addAll(cityList);
                            cityList.clear();
                            cityList.addAll(hashSet);
                            hashSet.clear();

                            hashSet.addAll(stateList);
                            stateList.clear();
                            stateList.addAll(hashSet);
                            hashSet.clear();

                            hashSet.addAll(landmarkList);
                            landmarkList.clear();
                            landmarkList.addAll(hashSet);
                            hashSet.clear();

                            stateEdit.setAdapter(new ArrayAdapter<String>(AddressActivity.this, android.R.layout.simple_spinner_dropdown_item, stateList));


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(AddressActivity.this).add(request);
    }

}