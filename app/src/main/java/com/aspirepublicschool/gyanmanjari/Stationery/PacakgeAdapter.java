package com.aspirepublicschool.gyanmanjari.Stationery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;

public class PacakgeAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<PacakgeShop> pacakgeShops;

    public PacakgeAdapter(Context ctx, ArrayList<PacakgeShop> pacakgeShops) {
        this.ctx = ctx;
        this.pacakgeShops = pacakgeShops;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_package_seller_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final PacakgeShop pacakgeShop = pacakgeShops.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.text_shop_name.setText(pacakgeShop.getSs_name());
        String IMG_URL = "https://www.aspirepublicschool.net/zocarro/image/shop/"+pacakgeShop.getS_img();
        Glide.with(ctx).load(IMG_URL).into(container.shop_img);
        container.relshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* $sc_id = "SCIDN14";
                $sc_std = "Junior KG";
                $sc_med = "Gujarati";
                $gender = "Male";
                $board="GSEB";
                $ss_id="SHIDN9";
                $s_id="ZCIN3";
                $module_name="Stationery";*/
                Intent intent=new Intent(ctx,FullPackage.class);
                intent.putExtra("package_id",pacakgeShop.getP_id());
                intent.putExtra("s_id",pacakgeShop.getS_id());
                ctx.startActivity(intent);

            }
        });
        if(pacakgeShop.getS_aval().equals("0")) {
            container.relshop.setAlpha(0.3f);
            container.txtopen.setText("Close");
            container.txtopen.setTextColor(Color.RED);
            container.relshop.setClickable(false );

        }
        else {
            container.relshop.setAlpha(1);
            container.txtopen.setText("Open");

        }
        container.txtaddress.setText(pacakgeShop.getAddr());
        container.text_shop_contact.setText(pacakgeShop.getS_cont());
        container.txtrating.setText(""+pacakgeShop.getRating());

    }

    @Override
    public int getItemCount() {
        return pacakgeShops.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        ImageView shop_img;
        RelativeLayout relshop;
        TextView text_shop_name, text_shop_contact,txtaddress,txtrating,txtopen;

        public MyWidgetContainer(@NonNull View itemView) {
            super(itemView);
            text_shop_name = itemView.findViewById(R.id.txtshop);
            text_shop_contact = itemView.findViewById(R.id.txtcontact);
            txtaddress = itemView.findViewById(R.id.txtaddress);
            txtrating = itemView.findViewById(R.id.txtrating);
            shop_img = itemView.findViewById(R.id.imgshop);
            relshop = itemView.findViewById(R.id.relshop);
            txtopen = itemView.findViewById(R.id.txtopen);
        }
    }
}
