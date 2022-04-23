package com.aspirepublicschool.gyanmanjari.Register;

public class School {

    String sc_id,sc_name;

    public School(String sc_id, String sc_name) {
        this.sc_id = sc_id;
        this.sc_name = sc_name;
    }

    public String getSc_id() {
        return sc_id;
    }

    public String getSc_name() {
        return sc_name;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }
}
