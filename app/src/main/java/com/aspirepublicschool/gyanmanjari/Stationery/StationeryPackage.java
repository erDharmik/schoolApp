package com.aspirepublicschool.gyanmanjari.Stationery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Stationery.MyOrder.MyOrder;
import com.aspirepublicschool.gyanmanjari.Stationery.ReturnReplace.ReturnedProduct;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StationeryPackage extends AppCompatActivity {
    LinearLayout lnrmovetoapp;
    TextView txtclass;
    String packege_id,s_id;
    Context ctx=this;
    RecyclerView recpacakge;
    ArrayList<PacakgeShop> pacakgeShops=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationery_package);
        allocatememory();
        recpacakge.setLayoutManager(new LinearLayoutManager(this));

        lnrmovetoapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = ctx.getPackageManager();
                boolean isInstalled = isPackageInstalled("com.example.ds", pm);
                if(isInstalled)
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
                }
            }
        });
        SendRequest();
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
        Common.progressDialogShow(StationeryPackage.this);
        String Webserviceurl= Common.GetWebServiceURL()+"displaypackagedeatils.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String uid = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none").toUpperCase();
        final String sc_std = mPrefs.getString("standard", "none");
        final String med = mPrefs.getString("med", "none");
        final String board = mPrefs.getString("board", "none");
        final String gender = mPrefs.getString("gender", "none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(StationeryPackage.this);
                JSONArray array= null;
                try {
                    array = new JSONArray(response);
                    Log.v("LLLL", ""+response);
                    for(int i=0;i<array.length();i++)
                    {
                       /* {
                            "p_id": "PKGID2",
                                "s_id": "ZCIN2",
                                "s_cont": "8758809709",
                                "ss_name": "Sameer Book Stall",
                                "ss_id": "SHIDN12",
                                "addr": "zone 1, Surat",
                                "city": "Bhavnagar",
                                "ss_status": "1",
                                "s_img": "87588097091.jpg",
                                "s_aval": "0"
                        }*/
                        JSONObject object=array.getJSONObject(i);
                        pacakgeShops.add(new PacakgeShop(object.getString("p_id"),object.getString("s_id"),object.getString("s_cont"),
                                object.getString("ss_name"),object.getString("ss_id"),object.getString("addr"),object.getString("city"),
                                object.getString("s_img"),object.getString("s_aval"),object.getDouble("rating")));


                    }
                    PacakgeAdapter adapter=new PacakgeAdapter(StationeryPackage.this,pacakgeShops);
                    recpacakge.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StationeryPackage.this,"Something Went wrong!!",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id);
                data.put("sc_std",sc_std);
                data.put("sc_med",med);
                data.put("board",board);
                Log.i("aaa",data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(StationeryPackage.this).add(request);
    }

    private void allocatememory() {

        lnrmovetoapp=findViewById(R.id.lnrmovetoapp);
        recpacakge=findViewById(R.id.recpacakges);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stationery_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_order:
                intent = new Intent(StationeryPackage.this, MyOrder.class);
                startActivity(intent);
                // do something
                return true;
            case R.id.action_return:
                intent = new Intent(StationeryPackage.this, ReturnedProduct.class);
                startActivity(intent);
                // do something
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}
