package com.aspirepublicschool.gyanmanjari.uniform.Promocode;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.uniform.Delivery.DeliveryDetails;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class PromocodeAdapter extends RecyclerView.Adapter<PromocodeAdapter.PromocodeHolder> {
    Context ctx;
    ArrayList<PromocodeModal> promocodeModalArrayList;

    public PromocodeAdapter(Context ctx, ArrayList<PromocodeModal> promocodeModalArrayList) {
        this.ctx = ctx;
        this.promocodeModalArrayList = promocodeModalArrayList;
    }

    @NonNull
    @Override
    public PromocodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.promocode_row, null);
        PromocodeAdapter.PromocodeHolder container = new PromocodeAdapter.PromocodeHolder(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull final PromocodeHolder holder, int position) {
        final PromocodeModal promocodeModal = promocodeModalArrayList.get(position);
         /*{
                    "promocode": "Textbook50",
                        "heading": "50 Rs cashback",
                        "priceper": "1",
                        "pricerup": "100",
                        "termandcond": "erstdfyguhij"
                }*/
        holder.txtpromocode.setText(promocodeModal.getPromocode());
        holder.txtpromodes.setText(promocodeModal.getHeading());
        holder.txttc.setText(promocodeModal.getTermandcond());
        holder.btnapplypromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i;
                i= DeliveryDetails.totalamount1-Integer.parseInt(promocodeModal.getPriceup());
                Log.d("%%",promocodeModal.getPriceup());
              //  if( DeliveryDetails.txtapply.getText().equals("Apply Promocode") || DeliveryDetails.txtapply.getText().equals("sorry offer is not applicable")) {
                    if (DeliveryDetails.totalamount1 >= Integer.parseInt(promocodeModal.getPriceup())) {

                        Toast.makeText(ctx, "you will save " + promocodeModal.getPriceup() + "Rs on this order.", Toast.LENGTH_SHORT).show();
                        DeliveryDetails.txtapply.setText("Final Price after Applying " + promocodeModal.getPromocode() + " offer: " + String.valueOf(i) + "Rs");
                        DeliveryDetails.txtapply.setTextColor(Color.GREEN);
                        DeliveryDetails.totalamount1 = i;
                        DeliveryDetails.text_total.setText("" + (i + DeliveryDetails.deliverycharges));
                        DeliveryDetails.imgcancel.setVisibility(View.VISIBLE);
                        DeliveryDetails.mDialog.dismiss();


                    } else {
                        Toast.makeText(ctx, "Sorry This Offer is not appliabe on this order", Toast.LENGTH_SHORT).show();
                        // MainActivity.finalpricetext.setError("sorry offer is not applicable");
                        DeliveryDetails.txtapply.setText("sorry offer is not applicable");
                        DeliveryDetails.imgcancel.setVisibility(View.VISIBLE);
                        DeliveryDetails.mDialog.dismiss();
                      /*  ctx.startActivity(new Intent(ctx,DeliveryDetails.class));
                        ((Activity)ctx).finish();*/
                    }

            }
        });

    }

    @Override
    public int getItemCount() {
        return promocodeModalArrayList.size();
    }


    public class PromocodeHolder extends RecyclerView.ViewHolder  {
        TextView txttc,txtpromocode,txtpromodes;
        Button btnapplypromo;
        public PromocodeHolder(@NonNull View itemView) {
            super(itemView);
            txttc=itemView.findViewById(R.id.txttc);
            txtpromocode=itemView.findViewById(R.id.txtpromocode);
            txtpromodes=itemView.findViewById(R.id.txtpromodes);
            btnapplypromo=itemView.findViewById(R.id.btnapplypromo);
        }
    }
}
