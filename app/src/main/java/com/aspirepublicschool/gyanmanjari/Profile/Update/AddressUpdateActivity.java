package com.aspirepublicschool.gyanmanjari.Profile.Update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.aspirepublicschool.gyanmanjari.Profile.ProfileMainActivity;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddressUpdateActivity extends AppCompatActivity {

    EditText addressEdit, cityEdit, stateEdit, pinEdit;
    String address, city, pin, state, landmark;
    ImageView locationImg;
    Button updateBtn;
    Spinner landmarkEdit;
    TextView locationStatus;
    LinearLayout changeLocation;
    String latitude, longit;
    FusedLocationProviderClient mFusedLocationClient;

    // Initializing other items
    // from layout file
    String lat = null , lon = null;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_update);

        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        city = intent.getStringExtra("city");
        state = intent.getStringExtra("state");
        pin = intent.getStringExtra("pin");
        landmark = intent.getStringExtra("landmark");

        addressEdit = findViewById(R.id.addressEdit);
        cityEdit = findViewById(R.id.cityEdit);
        stateEdit = findViewById(R.id.stateEdit);
        pinEdit = findViewById(R.id.pinEdit);
        landmarkEdit = findViewById(R.id.landmarkEdit);
        updateBtn = findViewById(R.id.updateInfoBtn);
        locationImg = findViewById(R.id.locationImg);
        locationStatus = findViewById(R.id.locationStatus);
        changeLocation = findViewById(R.id.changeLocation);

        addressEdit.setText(address);
        cityEdit.setText(city);
        pinEdit.setText(pin);
        stateEdit.setText(state);

        setSpinnerLandmark();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });

        locationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GmapActivity.class);
                startActivity(intent);
            }
        });

    }



    private void updateInfo() {

        final String addressfinal = addressEdit.getText().toString().trim();
        final String landmarkfinal = String.valueOf(landmarkEdit.getSelectedItem());
        final String cityfinal = cityEdit.getText().toString().trim();
        final String statefinal = stateEdit.getText().toString().trim();
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
                 cityEdit.getText().toString().trim().equals(city) &&
                pinEdit.getText().toString().trim().equals(pin) &&
                stateEdit.getText().toString().trim().equals(state) &&
                landmarkEdit.getSelectedItem().equals(landmark)){
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

    private void setSpinnerLandmark() {

        String Webserviceurl= Common.GetWebServiceURL()+"teacher/landmark.php";
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
                            String[] place = new String[0];
                            for (int i = 2; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String temp;
                                temp = (object.getString("landmark"));
                                temp = temp.replaceAll("[\\[\\]\\(\\)]","");
                                place = temp.split(",");

                            }
                            landmarkEdit.setAdapter(new ArrayAdapter<String>(AddressUpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, place));

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
        Volley.newRequestQueue(AddressUpdateActivity.this).add(request);
    }

}