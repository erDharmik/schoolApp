package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

class FeesExpandableAdapter extends BaseExpandableListAdapter {
    Context ctx;
    ArrayList<FeesTermPojo> feesTermPojoArrayList;
  /*  List<String> expandableTitle;
    LinkedHashMap<String, List<TimeTableModel>> expandableListDetail;*/

    public FeesExpandableAdapter(Context ctx, ArrayList<FeesTermPojo> feesTermPojoArrayList) {
        this.ctx = ctx;
        this.feesTermPojoArrayList = feesTermPojoArrayList;
    }

   /* public ExpandableAdapter(Context ctx, List<String> expandableTitle, LinkedHashMap<String,  List<TimeTableModel>> expandableListDetail) {
        this.ctx = ctx;
        this.expandableTitle = expandableTitle;
        this.expandableListDetail = expandableListDetail;
    }*/

    @Override
    public int getGroupCount() {
        return feesTermPojoArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<fees> productList = feesTermPojoArrayList.get(groupPosition).getFeesList();
        return productList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return feesTermPojoArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<fees> productList = feesTermPojoArrayList.get(groupPosition).getFeesList();
        return productList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        FeesTermPojo headerInfo = (FeesTermPojo) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.expandable_list_term, null);
        }

        TextView listTitle=convertView.findViewById(R.id.txtterm);
        listTitle.setText(headerInfo.getList_name());
        TextView btnpayfees=convertView.findViewById(R.id.btnpayfees);
        btnpayfees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx,"This Service is not Available",Toast.LENGTH_LONG).show();
            }
        });

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        fees feesInfo = (fees) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fees_row, null);
        }


        TextView txtfeename = convertView.findViewById(R.id.txtfeename);
        txtfeename.setText(feesInfo.getFeename());
        TextView txtamount = convertView.findViewById(R.id.txtamount);
        txtamount.setText(""+feesInfo.getAmount());
        TextView txtduedate = convertView.findViewById(R.id.txtduedate);
        txtduedate.setText(feesInfo.getDuedate());
        return convertView;


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
