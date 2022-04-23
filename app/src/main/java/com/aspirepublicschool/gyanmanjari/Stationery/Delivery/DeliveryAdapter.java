package com.aspirepublicschool.gyanmanjari.Stationery.Delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.Stationery.DetailsPackage;

import java.util.ArrayList;

class DeliveryAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<DetailsPackage> deliveryModelArrayList;

    public DeliveryAdapter(Context ctx, ArrayList<DetailsPackage> deliveryModelArrayList) {
        this.ctx = ctx;
        this.deliveryModelArrayList = deliveryModelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.stationery_package, null);
        CartViewHolder container = new CartViewHolder(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DetailsPackage deliveryModel= deliveryModelArrayList.get(position);
        final CartViewHolder container = (CartViewHolder) holder;
        container.txtorderpro.setText(deliveryModel.getLabel());
        int originalprice=Integer.parseInt(deliveryModel.getProce() )- ((Integer.parseInt(deliveryModel.getProce()) * Integer.parseInt(deliveryModel.getDiscount())) / 100);
        final int Prices=originalprice*Integer.parseInt(deliveryModel.getQty());
        final int originalprice1=Integer.parseInt(deliveryModel.getProce() );
        container.txtprice.setText(""+Prices+" ₹");
        container.txtorderquant.setText(deliveryModel.getQty());

    }

    @Override
    public int getItemCount() {
        return deliveryModelArrayList.size();
    }
    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtorderpro, txtprice,txtorderquant;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtorderpro = itemView.findViewById(R.id.txtorderpro);
            txtprice = itemView.findViewById(R.id.txtprice);
            txtorderquant = itemView.findViewById(R.id.txtorderquant);
        }
    }
}
