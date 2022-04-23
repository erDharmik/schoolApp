package com.aspirepublicschool.gyanmanjari.Stationery;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class FullPackageAdapter extends RecyclerView.Adapter {
    Context ctx;
     public static ArrayList<DetailsPackage> fullPacakgeList;
    boolean[] mDataset;
    public static int number=0;

    public FullPackageAdapter(Context ctx, ArrayList<DetailsPackage> fullPacakgeList,boolean[] mDataset) {
        this.ctx = ctx;
        this.fullPacakgeList = fullPacakgeList;
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_full_pacakge_spa, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final DetailsPackage detailsPackage = fullPacakgeList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        final int originalprice=Integer.parseInt(detailsPackage.getProce() )- ((Integer.parseInt(detailsPackage.getProce()) * Integer.parseInt(detailsPackage.getDiscount())) / 100);
        final int Prices=originalprice*Integer.parseInt(detailsPackage.getQty());
        final int originalprice1=Integer.parseInt(detailsPackage.getProce() );
        final int Prices1=originalprice1*Integer.parseInt(detailsPackage.getQty());
        String ImageUrl = "https://www.aspirepublicschool.net/zocarro/image"+"/stationery/book/"+detailsPackage.getCat().toLowerCase()+"/"+detailsPackage.getImg1();
        Log.v("VVVVV", ImageUrl);
        Glide.with(ctx).load(ImageUrl).into(container.imgproductpak);
        container.txtproductpkg.setText(""+detailsPackage.getLabel());
        container.txtpricepkg.setText(""+Prices+" ₹");
        container.txtoripricepkg.setText(""+Prices1+" ₹");
        container.txtqty.setText(detailsPackage.getQty());
        container.txtoripricepkg.setPaintFlags(container.txtoripricepkg.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if(detailsPackage.getStock().equals("0"))
        {
            container.relprostat.setAlpha(0.3f);
            container.chkbuy.setEnabled(false);
        }
        container.chkbuy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mDataset[position]=true;
                    fullPacakgeList.get(holder.getAdapterPosition()).setIschecked(mDataset[position]);
                    FullPackage.total_price=FullPackage.total_price+Prices;
                    FullPackage.txttotal.setText(String.valueOf(FullPackage.total_price)+"₹");
                    number++;
                }
                else
                {
                    mDataset[position]=false;
                    fullPacakgeList.get(holder.getAdapterPosition()).setIschecked(mDataset[position]);
                    FullPackage.total_price=FullPackage.total_price-Prices;
                    FullPackage.txttotal.setText(String.valueOf(FullPackage.total_price)+"₹");
                    number--;
                }
                if(FullPackageAdapter.number==0)
                {
                    FullPackage.btnbuynow.setEnabled(false);
                }
                else
                {
                    FullPackage.btnbuynow.setEnabled(true);
                }

            }
        });




    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return fullPacakgeList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        TextView txtproductpkg,txtpricepkg,txtoripricepkg,txtqty;
        ImageView imgproductpak;
        RelativeLayout relprostat;
        CheckBox chkbuy;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtproductpkg=itemView.findViewById(R.id.txtproductpkg);
            txtpricepkg=itemView.findViewById(R.id.txtpricepkg);
            txtoripricepkg=itemView.findViewById(R.id.txtoripricepkg);
            imgproductpak=itemView.findViewById(R.id.imgproductpak);
            relprostat=itemView.findViewById(R.id.relprostat);
            chkbuy=itemView.findViewById(R.id.chkbuy);
            txtqty=itemView.findViewById(R.id.txtqty);

        }
    }
}
