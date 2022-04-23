package com.aspirepublicschool.gyanmanjari.Stationery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Stationery.Delivery.DeliveryDetails;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FullPackage extends AppCompatActivity {
    public static TextView txttotal;
    public static Button btnbuynow;
    RecyclerView recpacakges;
    ArrayList<DetailsPackage> fullPackageModalArrayList = new ArrayList<>();
    public static String package_id,seller_id,shop_id,module_name;
    public static int total_price = 0;
    Context ctx = this;
    String sin;
    int originalprice,price,discount;
    boolean[] mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_package);
        seller_id=getIntent().getExtras().getString("s_id");
       package_id=getIntent().getExtras().getString("package_id");
        allocatememory();
        SendRequest();

        btnbuynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   PackageManager pm = ctx.getPackageManager();
                boolean isInstalled = isPackageInstalled("com.example.ds", pm);
                if(isInstalled).
                {
                    Intent LaunchIntent = getPackageManager()
                            .getLaunchIntentForPackage("com.example.ds");
                startActivity(LaunchIntent);
            }
                else
            {
                Intent goToMarket = new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("https://play.google.com/store/apps/details?id=in.swiggy.deliveryapp"));
                startActivity(goToMarket);
            }*/

                Intent intent = new Intent(FullPackage.this, DeliveryDetails.class);
                intent.putExtra("package_id",package_id);
                intent.putExtra("seller_id",seller_id);
                startActivity(intent);
            }
        });

    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void SendRequest() {
        Common.progressDialogShow(FullPackage.this);
        String Webserviceurl = Common.GetWebServiceURL() + "displayproductpackage.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String uid = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none").toUpperCase();
        final String sc_std = mPrefs.getString("standard", "none");
        final String med = mPrefs.getString("med", "none");
        final String board = mPrefs.getString("board", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(FullPackage.this);
                    JSONArray array = new JSONArray(response);
                    Log.v("MMMM", "" + response);
                    fullPackageModalArrayList.clear();
                    total_price=0;
                    int total=Integer.parseInt(array.getJSONObject(0).getString("total"));
                    mDataset=new boolean[array.length()];
                    if(total==0)
                    {
                        Toast.makeText(FullPackage.this, "No Data Available",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(FullPackage.this,StationeryPackage.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        for (int i = 1; i < total+1; i++) {
                            JSONObject object = array.getJSONObject(i);
                                            /*{
                            "package_name": "Stationery",
                                "p_id": "PKGID1",
                                "sin": "BSIN15",
                                "qty": "1",
                                "p_code": "15",
                                "label": "Account Book",
                                "stock": "100",
                                "img1": "BSIN151.jpg",
                                "price": "500",
                                "discount": "5",
                                "s_id": "ZCIN3"
                        }*/
                                            /*int stock=Integer.parseInt(object.getString("stock"));
                                            price=Integer.parseInt(object.getString("price"));
                                            discount=Integer.parseInt(object.getString("discount"));
                                            if(stock>0) {
                                                originalprice=price - ((price * discount) / 100);
                                                total_price = total_price +originalprice ;
                                            }*/
                            fullPackageModalArrayList.add(new DetailsPackage(object.getString("package_name"), object.getString("p_id"), object.getString("sin"), object.getString("qty"),
                                    object.getString("p_code"), object.getString("label"), object.getString("stock"), object.getString("img1"), object.getString("price"),
                                    object.getString("discount"), object.getString("s_id"), object.getString("cat"), false));
                        }
                        Log.v("TOTAL", String.valueOf(total_price));
                        txttotal.setText(String.valueOf(total_price));
                        FullPackageAdapter adapter = new FullPackageAdapter(FullPackage.this, fullPackageModalArrayList, mDataset);
                        recpacakges.setLayoutManager(new LinearLayoutManager(FullPackage.this));
                        recpacakges.setAdapter(adapter);
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
                data.put("sc_id", sc_id);
                data.put("sc_std",sc_std);
                data.put("sc_med",med);
                data.put("board",board);
                data.put("s_id",seller_id);
                data.put("ss_id",shop_id);
                data.put("module_name",module_name);
                Log.d("@@@",data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(FullPackage.this).add(request);
    }

    private void allocatememory() {
        txttotal = findViewById(R.id.txttotal);
        btnbuynow = findViewById(R.id.btnbuynow);
        recpacakges = findViewById(R.id.recpacakges);
    }
}
