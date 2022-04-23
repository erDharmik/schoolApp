package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class FeeAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<fees> FeesList;

    public FeeAdapter(Context ctx, ArrayList<fees> feesList) {
        this.ctx = ctx;
        FeesList = feesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View design = inflater.inflate(R.layout.fees_row, null);
        product p = new product(design);
        return p;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final product p = (product) holder;
        final fees fee = FeesList.get(position);

        p.txtfeename.setText(""+fee.getFeename());
        p.txtamount.setText(""+fee.getAmount()+"/-");
        p.txtduedate.setText(fee.getDuedate());
    }

    @Override
    public int getItemCount() {
        return FeesList.size();
    }
    class product extends RecyclerView.ViewHolder {

        TextView txtfeename,txtamount,txtduedate;


        public product(@NonNull View itemView) {
            super(itemView);
            txtfeename = itemView.findViewById(R.id.txtfeename);
            txtamount = itemView.findViewById(R.id.txtamount);
            txtduedate = itemView.findViewById(R.id.txtduedate);

        }
    }
}