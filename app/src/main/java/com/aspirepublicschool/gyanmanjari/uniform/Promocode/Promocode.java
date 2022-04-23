package com.aspirepublicschool.gyanmanjari.uniform.Promocode;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.uniform.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Promocode extends AppCompatActivity {
    RecyclerView promocodelist;
    ArrayList<PromocodeModal> promocodeModalArrayList=new ArrayList<>();
    EditText promocode_here;
    Button btnapplypromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocode);
        allocatememory();
        promocodelist.setLayoutManager(new LinearLayoutManager(this));
        promocodelist.addItemDecoration(new DividerItemDecoration(promocodelist.getContext(), DividerItemDecoration.VERTICAL));
        SendRequest();
    }

    private void SendRequest() {
        String Webserviceurl= Common.GetWebServiceURL()+"promocodedisplay.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*{
                    "promocode": "Textbook50",
                        "heading": "50 Rs cashback",
                        "priceper": "1",
                        "pricerup": "100",
                        "termandcond": "erstdfyguhij"
                }*/
                try {
                    JSONArray array=new JSONArray(response);
                    Log.d("%%%",response );
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        promocodeModalArrayList.add(new PromocodeModal(object.getString("promocode"),
                                object.getString("heading"),object.getString("priceper"),
                                object.getString("pricerup"),object.getString("termsandcond")));
                    }
                    PromocodeAdapter cartViewAdapter = new PromocodeAdapter(Promocode.this,promocodeModalArrayList);
                    promocodelist.setAdapter(cartViewAdapter);

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
               Map<String,String> data=new HashMap<>();
               data.put("s_id","ZCIN1");
               return data;
            }
        };
        Volley.newRequestQueue(Promocode.this).add(request);
    }

    private void allocatememory() {
        promocodelist=findViewById(R.id.promocodelist);
       /* promocode_here=findViewById(R.id.promocode_here);
        btnapplypromo=findViewById(R.id.btnapplypromo);*/

    }
}
