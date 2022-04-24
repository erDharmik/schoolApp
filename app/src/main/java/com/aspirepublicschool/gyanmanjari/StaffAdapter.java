package com.aspirepublicschool.gyanmanjari;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

class StaffAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Staff> staffList;


    public StaffAdapter(Context ctx, ArrayList<Staff> staffList) {
        this.ctx = ctx;
        this.staffList = staffList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_teacher_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Staff staff = staffList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtteachername.setText(staff.getT_fname() + " " + staff.getT_lname());
        container.txttcsub.setText(staff.getSub());
        container.imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ctx)
                        .setTitle(staff.getT_fname() + " " + staff.getT_lname())
                        .setMessage("Are you sure you want to call?")
                        .setCancelable(true)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // Continue with Calling operation
                                Intent SwitchActivity = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + staff.getT_cont()));
                                int checkPermission = ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE);
                                ctx.startActivity(SwitchActivity);

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Cancel", null)
                        .setIcon(R.drawable.ic_call)
                        .show();
//                return true;


            }


        });
        String URL_IMAGE = staff.getT_img();
        Log.d("img",staff.getT_img());
        Glide.with(ctx).load(URL_IMAGE).into(container.imgteacher);

    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtteachername,txttcsub;
        ImageView imgcall,imgteacher;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtteachername=itemView.findViewById(R.id.txtteachername);
            txttcsub=itemView.findViewById(R.id.txttcsub);
            imgcall=itemView.findViewById(R.id.imgcall);
            imgteacher=itemView.findViewById(R.id.imgteacher);


        }
    }
}
