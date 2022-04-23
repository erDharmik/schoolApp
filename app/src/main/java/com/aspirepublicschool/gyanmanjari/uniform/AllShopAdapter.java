package com.aspirepublicschool.gyanmanjari.uniform;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.uniform.Shops.AllShopDetails;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<AllShop> shopModelList = new ArrayList<>();
    private static final int LIST_AD_DELTA = 3;

    private static final int CONTENT = 0;
    View view;
    ArrayList<Advertise> advertiseArrayList = new ArrayList<>();


    public AllShopAdapter(Context context, List<AllShop> shopModelList) {
        this.context = context;
        this.shopModelList = shopModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //=LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,false);
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shop_name, parent, false);
            // ProductViewHolder productViewHolder = new ProductViewHolder(view);
            return new ShopViewHolder(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertise_layout, parent, false);
            //AdViewHolder adViewHolder = new AdViewHolder(view);
            return new Ad1ViewHolder(view);
        }
      /*  else if (viewType==2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad2_layout, parent, false);
            //AdViewHolder adViewHolder = new AdViewHolder(view);
            return new Ad2ViewHolder(view);
        }*/

        return null;


/*
        View view = LayoutInflater.from(context).inflate(R.layout.activity_shop_name, null);
        AllShopAdapter.ShopViewHolder container = new AllShopAdapter.ShopViewHolder(view);
        return container;*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

     /*   final AllShop shopModel = shopModelList.get(position);
        holder.text_shop_name.setText(shopModel.getSs_name());
        holder.text_shop_contact.setText(shopModel.getS_cont());
        holder.txtaddress.setText(shopModel.getAddr());
        holder.txtrating.setText(""+shopModel.getRatings());
        String IMG_URL = "https://www.zocarro.com/zocarro/image/shop/"+shopModel.getS_img();
        Glide.with(context).load(IMG_URL).into(holder.shop_img);
        holder.relshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllShopDetails.class);
                intent.putExtra("s_id",shopModel.getIds());
                intent.putExtra("ss_name",shopModel.getSs_name());
                intent.putExtra("s_img",shopModel.getS_img());
                intent.putExtra("s_cont",shopModel.getS_cont());
                intent.putExtra("ratings",""+shopModel.getRatings());
                context.startActivity(intent);
            }
        });*/


        switch (holder.getItemViewType()) {
            case 0:
                if (getItemViewType(position) == CONTENT) {


                    ShopViewHolder shopViewHolder = (ShopViewHolder) holder;
                    final AllShop allShop = shopModelList.get(getRealPosition(position));

                    //   final AllShop shopModel = shopModelList.get(position);
                    allShop.setCustomView(true);

                        shopViewHolder.text_shop_name.setText(allShop.getSs_name());
                        shopViewHolder.text_shop_contact.setText(allShop.getS_cont());
                        shopViewHolder.txtaddress.setText(allShop.getAddr());
                        shopViewHolder.txtrating.setText("" + allShop.getRatings());
                        String IMG_URL = Common.GetBaseImageURL()+"shop/" + allShop.getS_img();
                        Glide.with(context).load(IMG_URL).into(shopViewHolder.shop_img);
                        shopViewHolder.relshop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, AllShopDetails.class);
                                intent.putExtra("s_id", allShop.getIds());
                                intent.putExtra("ss_name", allShop.getSs_name());
                                intent.putExtra("s_img", allShop.getS_img());
                                intent.putExtra("s_cont", allShop.getS_cont());
                                intent.putExtra("ratings", "" + allShop.getRatings());
                                context.startActivity(intent);
                            }
                        });
                    if(allShop.getStatus()==0) {
                        shopViewHolder.relshop.setAlpha(0.3f);
                        shopViewHolder.txtopen.setText("Close");
                        shopViewHolder.txtopen.setTextColor(Color.RED);
                        shopViewHolder.relshop.setClickable(false );

                    }
                    else {
                        shopViewHolder.relshop.setAlpha(1);
                        shopViewHolder.txtopen.setText("Open");

                    }

                }
                break;

            case 1:
                final Ad1ViewHolder ad1ViewHolder = (Ad1ViewHolder) holder;
                advertiseArrayList.clear();
                ad1ViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                //recproduct.addItemDecoration(new DividerItemDecoration(recproduct.getContext(), DividerItemDecoration.VERTICAL));
                ad1ViewHolder.recyclerView.setHasFixedSize(true);
                LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                ad1ViewHolder.recyclerView.setLayoutManager(horizontalLayoutManagaer1);

               /* HeaderDecoration headerDecoration = new HeaderDecoration(view,true,0f, 2.0f,1);
                ad1ViewHolder.recyclerView.addItemDecoration(headerDecoration);*/
                //recsub.addItemDecoration(new DividerItemDecoration(recsub.getContext(), DividerItemDecoration.VERTICAL));
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                final String city = mPrefs.getString("city", "none").toLowerCase();
                final String uid = mPrefs.getString("stu_id", "none");
                final String sc_id = mPrefs.getString("sc_id", "none").toUpperCase();
                final String sc_std = mPrefs.getString("standard", "none");
                final String med = mPrefs.getString("med", "none");
                final String gender = mPrefs.getString("gender", "none");
                String Webserviceurl= Common.GetWebServiceURL()+"displaytoprateddetails.php";
                StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                advertiseArrayList.add(new Advertise(object.getString("sin"),object.getString("s_id"),object.getString("price"),object.getString("discount")
                                        ,object.getString("label"),object.getString("img1"),object.getString("p_code"),object.getString("cat")));
                            }
                            AdvertiseAdapter adapter = new AdvertiseAdapter(context, advertiseArrayList);
                            ad1ViewHolder.recyclerView.setAdapter(adapter);
                            adapter.notifyItemInserted(position);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> data=new HashMap<>();
                        data.put("sc_id", sc_id);
                        data.put("sc_std", sc_std);
                        data.put("sc_med", med);
                        data.put("gender",gender);
                        return data;
                    }
                };
                Volley.newRequestQueue(context).add(request);
                /*Advertise advertise1 = new Advertise("Full Uniform1", R.mipmap.ic_launcher_round);
                advertiseArrayList.add(advertise1);
                Advertise advertise2 = new Advertise("Trouser1", R.mipmap.ic_launcher);
                advertiseArrayList.add(advertise2);
                Advertise advertise3 = new Advertise("Shirt1", R.mipmap.ic_launcher_round);
                advertiseArrayList.add(advertise3);
                Advertise advertise4 = new Advertise("Shoes1", R.mipmap.ic_launcher);
                advertiseArrayList.add(advertise4);
                Advertise advertise5 = new Advertise("Sweater1", R.mipmap.ic_launcher);
                advertiseArrayList.add(advertise5);*/

                break;

        /*    case 2:
                Ad2ViewHolder ad2ViewHolder = (Ad2ViewHolder) holder;
                advertiseList.clear();
                ad2ViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                //recproduct.addItemDecoration(new DividerItemDecoration(recproduct.getContext(), DividerItemDecoration.VERTICAL));
                ad2ViewHolder.recyclerView.setHasFixedSize(true);
                LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                ad2ViewHolder.recyclerView.setLayoutManager(horizontalLayoutManagaer2);
                //recsub.addItemDecoration(new DividerItemDecoration(recsub.getContext(), DividerItemDecoration.VERTICAL));
                advertise advertise21 = new advertise("Full Uniform2", R.mipmap.ic_launcher_round);
                advertiseList.add(advertise21);
                advertise advertise22 = new advertise("Trouser2", R.mipmap.ic_launcher);
                advertiseList.add(advertise22);
                advertise advertise23 = new advertise("Shirt2", R.mipmap.ic_launcher_round);
                advertiseList.add(advertise23);
                advertise advertise24 = new advertise("Shoes2", R.mipmap.ic_launcher);
                advertiseList.add(advertise24);
                advertise advertise25 = new advertise("Sweater2", R.mipmap.ic_launcher);
                advertiseList.add(advertise25);
                advertiseAdapter adapter2 = new advertiseAdapter(context, advertiseList);
                ad2ViewHolder.recyclerView.setAdapter(adapter2);
                adapter2.notifyItemInserted(position);
                break;*/
        }

    }



    private int getRealPosition(int position) {
        if (LIST_AD_DELTA == 0) {
            return position;
        } else {
            return position - position / LIST_AD_DELTA;
        }

    }

    @Override
    public int getItemViewType(int position) {
        // return position % 2 * 1;

/*int a=0;

        String ad = shopProductModelList.get(position).getLabel();
        switch (ad){

            case "ad":
                 a=1 ;
                break;
            case "product1":
                a=0 ;
                break;
            default:
        }

        return a;*/
        if (position > 0 && position % LIST_AD_DELTA == 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {


        int additionalContent = 0;
        if (shopModelList.size() > 0 && LIST_AD_DELTA > 0 && shopModelList.size() > LIST_AD_DELTA) {
            additionalContent = shopModelList.size() / LIST_AD_DELTA;
        }

        return shopModelList.size() + additionalContent;

        //  return shopModelList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView shop_img;
        RelativeLayout relshop;
        TextView text_shop_name, text_shop_contact, txtaddress, txtrating,txtopen;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            text_shop_name = itemView.findViewById(R.id.txtshop);
            text_shop_contact = itemView.findViewById(R.id.txtcontact);
            txtaddress = itemView.findViewById(R.id.txtaddress);
            shop_img = itemView.findViewById(R.id.imgshop);
            relshop = itemView.findViewById(R.id.relshop);
            txtrating = itemView.findViewById(R.id.txtrating);
            txtopen = itemView.findViewById(R.id.txtopen);
        }
    }

    public class Ad1ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public Ad1ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.ad_recyclerView);
           /* HeaderDecoration headerDecoration = new HeaderDecoration(itemView,true,0f, 2.0f,1);
            recyclerView.addItemDecoration(headerDecoration);*/
        }
    }
}
