package com.aspirepublicschool.gyanmanjari;

import java.util.ArrayList;

class TimeTableModel {
    String list_name;
    ArrayList<DetailInfo> detailInfosList;

    public TimeTableModel(String list_name, ArrayList<DetailInfo> detailInfosList) {
        this.list_name = list_name;
        this.detailInfosList = detailInfosList;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public ArrayList<DetailInfo> getDetailInfosList() {
        return detailInfosList;
    }

    public void setDetailInfosList(ArrayList<DetailInfo> detailInfosList) {
        this.detailInfosList = detailInfosList;
    }
}
