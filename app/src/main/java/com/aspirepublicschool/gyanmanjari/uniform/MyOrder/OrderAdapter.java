package com.aspirepublicschool.gyanmanjari.uniform.MyOrder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    Context context;
    List<OrderModel> orderModelList = new ArrayList<>();
    String img_url;

    public OrderAdapter(Context context, List<OrderModel> orderModelList) {
        this.context = context;
        this.orderModelList = orderModelList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(context).inflate(R.layout.activity_order_row, null);
        OrderViewHolder container = new OrderViewHolder(MyView);
        return container;

    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, int position) {
        final OrderModel orderModel = orderModelList.get(position);
         holder.txtorderid.setText(""+orderModel.getOrder_id());
         holder.text_name.setText(""+orderModel.getLabel());
         holder.text_price.setText(""+orderModel.getPrice()+" ₹");
         holder.text_time.setText(""+orderModel.getTime());
        /* holder.btnstatus.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {





                 LayoutInflater inflater = LayoutInflater.from(context);
                 AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                 View mView = inflater.inflate(R.layout.activity_status_dialog, null);
                 TextView txtstatus;
                 txtstatus = mView.findViewById(R.id.txtstatus);
                 final AlertDialog mDialog = mBuilder.create();
                 Button btn_close=mView.findViewById(R.id.btn_close);
                 mDialog.setView(mView);

                 if (orderModel.getOrder_status().equals("0")){
                     txtstatus.setText("Order Is Placed..");
                 }
                 if (orderModel.getOrder_status().equals("1")){
                   txtstatus.setText("Order Is Confirmed..");
                 }
                 if (orderModel.getOrder_status().equals("2")){
                     txtstatus.setText("Order Is Rejected by Seller..");
                 }
                 mDialog.show();
                 btn_close.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         mDialog.dismiss();
                     }
                 });
             }
         });*/
         /*holder.btnreturn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });*/
         holder.relproductorder.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context,OrderDetails.class);
                 intent.putExtra("order_id",orderModel.getOrder_id());
                 intent.putExtra("pro_name",orderModel.getLabel());
                 intent.putExtra("shop_name",orderModel.getSs_name());
                 intent.putExtra("img",orderModel.getImg());
                 intent.putExtra("order_date",orderModel.getTime());
                 intent.putExtra("status",orderModel.getOrder_status());
                 intent.putExtra("paymethod",orderModel.getPayment_mode());
                 intent.putExtra("address",orderModel.getAddress());
                 intent.putExtra("amount",orderModel.getPrice());
                 intent.putExtra("qty",orderModel.getQty());
                 intent.putExtra("sin",orderModel.getP_code());
                 intent.putExtra("s_id",orderModel.getS_id());
                 intent.putExtra("mobile_no",orderModel.getMobile_no());
                 intent.putExtra("cat",orderModel.getCat());
                 intent.putExtra("p_code",orderModel.getP_code());
                 intent.putExtra("size",orderModel.getSize());
                 Log.d("size",orderModel.getSize());
                 context.startActivity(intent);
             }
         });


        String img_url= Common.GetBaseImageURL()+"/uniform/"+orderModel.getImg();
        if(orderModel.getCat().equals("Full Uniform")) {
            img_url = Common.GetBaseImageURL() + "/uniform/" + orderModel.getImg();
            Log.d("img",img_url);

        }
        else if(orderModel.getCat().equals("Shirt"))
        {
            img_url = Common.GetBaseImageURL() + "/shirt/" + orderModel.getImg();
            Log.d("img",img_url);

        }
        else if(orderModel.getCat().equals("Pant"))
        {
            img_url = Common.GetBaseImageURL() + "/pant/" + orderModel.getImg();
            Log.d("img",img_url);

        }
        else if(orderModel.getCat().equals("Other Items"))
        {
            img_url = Common.GetBaseImageURL() + "/otheritems/" + orderModel.getImg();
            Log.d("img",img_url);

        }
        Glide.with(context).load(img_url).into(holder.imgflower);
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtorderid,text_name,text_price,text_time;
        ImageView imgflower;
        Button btnstatus,btnreturn;
        RelativeLayout relproductorder;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgflower = itemView.findViewById(R.id.imgflower);
            text_name = itemView.findViewById(R.id.txtitem);
            txtorderid = itemView.findViewById(R.id.txtorderid);
            text_price = itemView.findViewById(R.id.txtamount);
            text_time = itemView.findViewById(R.id.txtdoorder);
           // btnstatus = itemView.findViewById(R.id.btnstatus);
            //btnreturn = itemView.findViewById(R.id.btnreturn);
            relproductorder = itemView.findViewById(R.id.relproductorder);

        }
    }
}
