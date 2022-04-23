package com.aspirepublicschool.gyanmanjari.uniform.clothes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Common;

import java.util.ArrayList;
import java.util.List;


public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ProductViewHolder> {
    Context context;
    List<ShopProductModel> shopProductModelList = new ArrayList<>();
    String ImageUrl;


    public ShopProductAdapter(Context context, List<ShopProductModel> shopProductModelList) {
        this.context = context;
        this.shopProductModelList = shopProductModelList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_uniform_row, null);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        final ShopProductModel shopProductModel = shopProductModelList.get(position);
        holder.textView_name.setText(shopProductModel.getLabel());
        final int originalprice=Integer.parseInt(shopProductModel.getPrice() )- ((Integer.parseInt(shopProductModel.getPrice()) * Integer.parseInt(shopProductModel.getDiscount())) / 100);
        holder.textView_price.setText(originalprice+"₹");
        Log.d("cat",shopProductModel.getCat());
        if(shopProductModel.getCat().equals("Full Uniform")) {
             ImageUrl = Common.GetBaseImageURL() + "/uniform/" + shopProductModel.getImg();
             Log.d("img",ImageUrl);

        }
        else if(shopProductModel.getCat().equals("Shirt"))
        {
            ImageUrl = Common.GetBaseImageURL() + "/shirt/" + shopProductModel.getImg();
            Log.d("img",ImageUrl);

        }
       else if(shopProductModel.getCat().equals("Pant"))
        {
            ImageUrl = Common.GetBaseImageURL() + "/pant/" + shopProductModel.getImg();
            Log.d("img",ImageUrl);

        }
       else if(shopProductModel.getCat().equals("Other Items"))
        {
            ImageUrl = Common.GetBaseImageURL() + "/otheritems/" + shopProductModel.getImg();
            Log.d("img",ImageUrl);

        }
        Glide.with(context).load(ImageUrl).into(holder.p_img);

        holder.relproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("p_code", shopProductModel.getP_code());
                intent.putExtra("s_id",shopProductModel.getS_id());
                intent.putExtra("sin",shopProductModel.getSin());
                intent.putExtra("cat",shopProductModel.getCat());
                context.startActivity(intent);
            }
        });
        holder.txtcat.setText(shopProductModel.getCat());
    }

    @Override
    public int getItemCount() {
        return shopProductModelList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name,textView_price,txtcat;
        ImageView p_img;
        RelativeLayout relproduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_price = itemView.findViewById(R.id.txtprice);
            txtcat = itemView.findViewById(R.id.txtcat);
            p_img = itemView.findViewById(R.id.imgproduct);
            textView_name = itemView.findViewById(R.id.txtproductname);
            relproduct = itemView.findViewById(R.id.relproduct);
        }
    }
}
