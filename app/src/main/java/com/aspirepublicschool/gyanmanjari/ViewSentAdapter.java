package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class ViewSentAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<ViewSent> SendList;

    public ViewSentAdapter(Context ctx, ArrayList<ViewSent> sendList) {
        this.ctx = ctx;
        SendList = sendList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View design = inflater.inflate(R.layout.activity_view_sent_row, null);
        product p = new product(design);
        return p;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final product p = (product) holder;
        final ViewSent s = SendList.get(position);
        p.txtemail.setText(""+s.getT_fname() + " "+s.getT_lname());
        p.txttitle.setText(""+ Html.fromHtml(s.getTitle()));
        p.relsentmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,ViewMessage.class);
                intent.putExtra("sender",s.getT_fname()+" "+s.getT_lname());
                intent.putExtra("title",s.getTitle());
                intent.putExtra("message",s.getMsg());
                intent.putExtra("time",s.getTime());
                ctx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return SendList.size();
    }

    class product extends RecyclerView.ViewHolder {

        TextView txtemail, txttitle;
        RelativeLayout relsentmsg;


        public product(@NonNull View itemView) {
            super(itemView);
            txtemail = itemView.findViewById(R.id.txtemail);
            txttitle = itemView.findViewById(R.id.txttitle);
            relsentmsg = itemView.findViewById(R.id.relsentmsg);

        }
    }
}
