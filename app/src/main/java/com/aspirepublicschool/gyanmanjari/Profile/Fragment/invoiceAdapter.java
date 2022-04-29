package com.aspirepublicschool.gyanmanjari.Profile.Fragment;


import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

public class invoiceAdapter extends RecyclerView.Adapter<invoiceAdapter.holder> {

    public static invoiceModel userdata[];
    Context context;

    public invoiceAdapter(invoiceModel[] userdata, Context context) {
        this.userdata = userdata;
        this.context = context;


    }

    @NonNull
    @Override
    public invoiceAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.invoice_raw,parent,false);
        return new holder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull invoiceAdapter.holder holder, @SuppressLint("RecyclerView") final int position) {




        holder.id.setText(userdata[position].getStu_id());
        holder.std.setText(userdata[position].getStd());
        holder.amount.setText(userdata[position].getAmount());
        holder.printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context , InvoiceWebActivity.class);
               intent.putExtra("stu_id" , userdata[position].getStu_id());
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userdata.length;
    }

    public class holder extends RecyclerView.ViewHolder {

        TextView id , std , amount ;
        ImageView printer;

        public holder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.studentId);
            std = itemView.findViewById(R.id.stu_standard);
            amount = itemView.findViewById(R.id.invoiceAmount);
            printer = itemView.findViewById(R.id.printer);




        }
    }
}
