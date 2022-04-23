package com.aspirepublicschool.gyanmanjari.uniform.Delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

class DeliveryAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<DeliveryModel> deliveryModelArrayList;

    public DeliveryAdapter(Context ctx, ArrayList<DeliveryModel> deliveryModelArrayList) {
        this.ctx = ctx;
        this.deliveryModelArrayList = deliveryModelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.billdesign, null);
        CartViewHolder container = new CartViewHolder(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DeliveryModel deliveryModel= deliveryModelArrayList.get(position);
        final CartViewHolder container = (CartViewHolder) holder;
        container.txtorderpro.setText(deliveryModel.getPro_name());
        container.txtorderquant.setText(""+deliveryModel.getQty());
        int oriprice=deliveryModel.getPrice()*deliveryModel.getQty();
        container.txtprice.setText(""+oriprice+" ₹");


    }

    @Override
    public int getItemCount() {
        return deliveryModelArrayList.size();
    }
    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtorderpro, txtorderquant, txtprice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtorderpro = itemView.findViewById(R.id.txtorderpro);
            txtorderquant = itemView.findViewById(R.id.txtorderquant);
            txtprice = itemView.findViewById(R.id.txtprice);
        }
    }
}
