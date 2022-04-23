package com.aspirepublicschool.gyanmanjari;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity  {

    private static final int REQUEST_PHONE_CALL =111 ;
   // private GoogleMap mMap;
    TextView t_type,d_name,d_cont,v_no,route;
    Context ctx=this;
    LinearLayout lnrmap;
    String stime,etime;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SharedPreferences mPrefs= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String stu_name = mPrefs.getString("stu_name", "Transportation");

        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(stu_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(MapsActivity.this,MainActivity.class));
                finish();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
      /*  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.CALL_PHONE,},REQUEST_PHONE_CALL);
        }
        else
        {

        }
        t_type = findViewById(R.id.t_type);
        d_name = findViewById(R.id.d_name);
        d_cont = findViewById(R.id.d_cont);
        v_no = findViewById(R.id.v_no);
        route = findViewById(R.id.route);
        lnrmap=findViewById(R.id.lnrmap);
        loadData();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(21.718697, 72.122066);
        double lat=21.718697;
        double lng=72.122066;
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        mMap.clear();
        markerOptions.title("Current Position");
        markerOptions.getPosition();
        getAddress(ctx,21.718697, 72.122066);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in GMIT"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14.0f));
    }*/
    public static void getAddress(Context context, double LATITUDE, double LONGITUDE) {

//Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {



                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                Log.d("ADD", "getAddress:  address" + address);
                Log.d("AAA", "getAddress:  city" + city);
                Log.d("AAA", "getAddress:  state" + state);
                Log.d("AAA", "getAddress:  postalCode" + postalCode);
                Log.d("AAA", "getAddress:  knownName" + knownName);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
    private void loadData() {
        Common.progressDialogShow(MapsActivity.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String s_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id","none");
        String URL = Common.GetWebServiceURL()+"transport.php";
        @SuppressLint({"NewApi", "LocalSuppress"}) final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        final String date = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        Log.d("@@@@",date);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(MapsActivity.this);
                    JSONArray array = new JSONArray(response);

                    JSONObject object =array.getJSONObject(0);
                    t_type.setText(object.getString("t_type"));
                    d_name.setText(object.getString("d_name"));
                    d_cont.setText(object.getString("d_cont"));
                    object.getString("v_id");
                    v_no.setText(object.getString("v_no"));
                    route.setText(object.getString("route"));
                    stime=object.getString("stime");
                    etime=object.getString("etime");
                    final String cont=object.getString("d_cont");
                    @SuppressLint({"NewApi", "LocalSuppress"}) LocalTime startTime = LocalTime.parse(stime, format);
                    @SuppressLint({"NewApi", "LocalSuppress"}) LocalTime endTime = LocalTime.parse(etime, format);
                    @SuppressLint({"NewApi", "LocalSuppress"}) LocalTime targetTime = LocalTime.parse(date, format);
                    if (startTime.isAfter(endTime)) {
                        if (targetTime.isBefore(endTime) && targetTime.isAfter(startTime)) {
                            System.out.println("Yes! night shift.");
                           // Toast.makeText(MapsActivity.this,"Visible",Toast.LENGTH_LONG).show();
                            lnrmap.setVisibility(View.VISIBLE);
                        } else {
                            System.out.println("Not! night shift.");
                            //Toast.makeText(MapsActivity.this," Not Visible",Toast.LENGTH_LONG).show();
                            lnrmap.setVisibility(View.GONE);
                        }
                    } else {
                        if (targetTime.isBefore(endTime) && targetTime.isAfter(startTime)) {
                            System.out.println("Yes! without night shift.");
                            lnrmap.setVisibility(View.GONE);

                        } else {
                            System.out.println("Not! without night shift.");
                            lnrmap.setVisibility(View.VISIBLE);

                        }
                    }


                    d_cont.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent SwitchActivity = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + cont));
                            int checkPermission = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.CALL_PHONE);
                            startActivity(SwitchActivity);

                        }
                    });

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
                params.put("sc_id",sc_id);
                params.put("stu_id",s_id);
                return params;
            }
        };
        Volley.newRequestQueue(MapsActivity.this).add(stringRequest);
    }
}
