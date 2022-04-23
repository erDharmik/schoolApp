package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    Context ctx;
    ArrayList<TimeTableModel> deptList;
  /*  List<String> expandableTitle;
    LinkedHashMap<String, List<TimeTableModel>> expandableListDetail;*/

    public ExpandableAdapter(Context ctx, ArrayList<TimeTableModel> deptList) {
        this.ctx = ctx;
        this.deptList = deptList;
    }

   /* public ExpandableAdapter(Context ctx, List<String> expandableTitle, LinkedHashMap<String,  List<TimeTableModel>> expandableListDetail) {
        this.ctx = ctx;
        this.expandableTitle = expandableTitle;
        this.expandableListDetail = expandableListDetail;
    }*/

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<DetailInfo> productList = deptList.get(groupPosition).getDetailInfosList();
        return productList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<DetailInfo> productList = deptList.get(groupPosition).getDetailInfosList();
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
        TimeTableModel headerInfo = (TimeTableModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.list_group, null);
        }

        TextView listTitle=convertView.findViewById(R.id.listTitle);
        listTitle.setText(headerInfo.getList_name());
        Log.d("###", " "+headerInfo.getList_name());
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        DetailInfo detailInfo = (DetailInfo) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtsub = convertView.findViewById(R.id.txtsub);
        txtsub.setText(detailInfo.getLecture());
        TextView txttime = convertView.findViewById(R.id.txttime);
        txttime.setText(detailInfo.getTime());
        TextView txtFacultyname = convertView.findViewById(R.id.txtFacultyname);
        if(detailInfo.getLecture().equals("Extra Activity")==true || detailInfo.getLecture().equals("Break")==true || detailInfo.getLecture().equals("Test AND Exams")==true) {
            txtFacultyname.setText("------");
        }
        else
        {
            txtFacultyname.setText(detailInfo.getT_fname() + " " + detailInfo.getT_lname());
        }


        return convertView;


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
