package com.aspirepublicschool.gyanmanjari.DoubtSolving;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;
import java.util.List;


public class GMChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mCtx;

    private ArrayList<ChatData> senderNames;
    private List<String> messages;
    private String username;

    private LayoutInflater inflater;

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    String SendingID;

    public GMChatAdapter(final Context mCtx,ArrayList<ChatData> senderNames) {
        this.mCtx = mCtx;
        this.senderNames = senderNames;
        inflater = LayoutInflater.from(mCtx);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mCtx);
        SendingID = preferences.getString("stu_id","none");
        username =SendingID;
       // username = senderId;
    }
    @Override
    public int getItemViewType(int position) {
        if (username.equals(senderNames.get(position).getRole())) {
            return RIGHT;
        }
        return LEFT;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LEFT) {
            return new LeftItemHolder(inflater.inflate(R.layout.private_chat_left_item, parent, false));
        }
        return new RightItemHolder(inflater.inflate(R.layout.private_chat_right_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatData data=senderNames.get(position);
        if (holder instanceof LeftItemHolder) {
            ((LeftItemHolder) holder).textView.setText(Html.fromHtml(data.getMessage()));
        } else {
            ((RightItemHolder) holder).textView.setText(Html.fromHtml(data.getMessage()));
        }
    }

    @Override
    public int getItemCount() {
        return senderNames.size();
    }

    private static class LeftItemHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        LeftItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
    private static class RightItemHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        RightItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

}