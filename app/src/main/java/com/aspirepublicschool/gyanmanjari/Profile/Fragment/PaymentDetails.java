package com.aspirepublicschool.gyanmanjari.Profile.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class PaymentDetails extends Fragment {

String stu_id, sc_id, number;
RecyclerView invoice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_details, container, false);

        stu_id = getArguments().getString("stu_id");
        sc_id = getArguments().getString("sc_id");
        number = getArguments().getString("number");

        invoice = view.findViewById(R.id.invoicercl);
        invoice.setLayoutManager(new LinearLayoutManager(getContext()));


        fetchInvoice();



        return view;
    }

    private void fetchInvoice() {

        String invoiceUrl = Common.GetWebServiceURL() + "fetchInvoice.php";

        StringRequest request = new StringRequest(Request.Method.POST,invoiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                GsonBuilder builder = new GsonBuilder();
                Gson gson =builder.create();
                invoiceModel userdata[] = gson.fromJson(response,invoiceModel[].class);

                invoiceAdapter adapter = new invoiceAdapter(userdata,getContext());
                invoice.setAdapter(adapter);

            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> data = new HashMap<String, String>();
                data.put("stu_id", stu_id);
                return data;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}