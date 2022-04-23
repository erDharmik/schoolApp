package com.aspirepublicschool.gyanmanjari.uniform.ReturnReplace;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Common;


import java.util.ArrayList;

public class ReturnedProductAdapterd extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<ReturnedProductModel> returnedProductModels;

    public ReturnedProductAdapterd(Context ctx, ArrayList<ReturnedProductModel> returnedProductModels) {
        this.ctx = ctx;
        this.returnedProductModels = returnedProductModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_returned_product_row, null);
        ReturnViewHolder container = new ReturnViewHolder(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ReturnedProductModel deliveryModel= returnedProductModels.get(position);
        final ReturnViewHolder container = (ReturnViewHolder) holder;
        container.txtProductname.setText(deliveryModel.getLabel());
        container.txtprice.setText(deliveryModel.getPrice());
        container.txtorderid.setText(deliveryModel.getOrder_id());
        String img_url= Common.GetBaseImageURL()+"/uniform/"+deliveryModel.getImg();
        if(deliveryModel.getCat().equals("Full Uniform")) {
            img_url = Common.GetBaseImageURL() + "/uniform/" + deliveryModel.getImg();
            Log.d("img",img_url);

        }
        else if(deliveryModel.getCat().equals("Shirt"))
        {
            img_url = Common.GetBaseImageURL() + "/shirt/" + deliveryModel.getImg();
            Log.d("img",img_url);

        }
        else if(deliveryModel.getCat().equals("Pant"))
        {
            img_url = Common.GetBaseImageURL() + "/pant/" + deliveryModel.getImg();
            Log.d("img",img_url);

        }
        else if(deliveryModel.getCat().equals("Other Items"))
        {
            img_url = Common.GetBaseImageURL() + "/otheritems/" + deliveryModel.getImg();
            Log.d("img",img_url);

        }
        Glide.with(ctx).load(img_url).into(container.imgProduct);
        container.cardreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx,ReturnedStatus.class);
                intent.putExtra("order_id",deliveryModel.getOrder_id());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return returnedProductModels.size();
    }


    public class ReturnViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductname,txtprice,txtorderid;
        ImageView imgProduct;
        CardView cardreturn;
        public ReturnViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtprice = itemView.findViewById(R.id.txtprice);
            txtProductname = itemView.findViewById(R.id.txtProductname);
            txtorderid = itemView.findViewById(R.id.txtorderid);
            cardreturn = itemView.findViewById(R.id.cardreturn);

        }
    }
}
