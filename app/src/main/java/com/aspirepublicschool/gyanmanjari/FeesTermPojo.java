package com.aspirepublicschool.gyanmanjari;

import java.util.ArrayList;

public class FeesTermPojo {
    String list_name;
    ArrayList<fees> feesList;

    public FeesTermPojo(String list_name, ArrayList<fees> feesList) {
        this.list_name = list_name;
        this.feesList = feesList;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public ArrayList<fees> getFeesList() {
        return feesList;
    }

    public void setFeesList(ArrayList<fees> feesList) {
        this.feesList = feesList;
    }
}
