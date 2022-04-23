package com.aspirepublicschool.gyanmanjari.Profile.Fragment;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.EmptyActivity;
import com.aspirepublicschool.gyanmanjari.Profile.Update.AddressUpdateActivity;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class AddressFragment extends Fragment {


    TextView address, landmark, city, state, pincode;
    ImageButton location;
    Button btncontinue;

    //GMAP
    String latitude, longit, lat = null , lon = null;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    Context context;
    String addressString,landmarkString,CityString, stateString ,pinString;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        address = view.findViewById(R.id.address);
        landmark = view.findViewById(R.id.landmark);
        city = view.findViewById(R.id.city);
        state = view.findViewById(R.id.state);
        pincode = view.findViewById(R.id.pin);
        location = view.findViewById(R.id.location);
        btncontinue = view.findViewById(R.id.updateBtn);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gmapfunction();
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gmapfunction();
            }
        });
        landmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gmapfunction();
            }
        });
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gmapfunction();
            }
        });
        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gmapfunction();
            }
        });
        pincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Gmapfunction();
                }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gmapfunction();
            }
        });
        fetchPersonalData();
        return view;
    }

    private void Gmapfunction() {
        Intent intent = new Intent(getContext(), AddressUpdateActivity.class);
        intent.putExtra("address", addressString);
        intent.putExtra("state", stateString);
        intent.putExtra("pin", pinString);
        intent.putExtra("city", CityString);
        intent.putExtra("landmark", landmarkString);
        startActivity(intent);
    }


    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }
    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
        }
    };

    // If everything is alright then
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
//
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
//            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            String longi = String.valueOf(location.getLongitude());
                            latitude = (location.getLatitude() + "");
                            longit = (location.getLongitude() + "");
                        }
                    }
                });
//            } else {
//                Toast.makeText(getContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }
//

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    protected void fetchPersonalData() {

        String WebServiceUrl = Common.GetWebServiceURL() + "personalDetails.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    addressString = jsonArray.getJSONObject(0).getString("address");
                    landmarkString = jsonArray.getJSONObject(0).getString("landmark");
                    CityString = jsonArray.getJSONObject(0).getString("city");
                    stateString = jsonArray.getJSONObject(0).getString("state");
                    pinString = jsonArray.getJSONObject(0).getString("pincode");
//                    String locationString = jsonArray.getJSONObject(0).getString("location");
                    address.setText(addressString);
                    landmark.setText(landmarkString);
                    city.setText(CityString);
                    state.setText(stateString);
                    pincode.setText(pinString);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("number", "9586417374");
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }

}