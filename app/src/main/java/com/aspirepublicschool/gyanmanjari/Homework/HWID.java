package com.aspirepublicschool.gyanmanjari.Homework;

public class HWID {
    String hw_id,sub;

    public HWID(String hw_id, String sub) {
        this.hw_id = hw_id;
        this.sub = sub;
    }

    public String getHw_id() {
        return hw_id;
    }

    public void setHw_id(String hw_id) {
        this.hw_id = hw_id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
