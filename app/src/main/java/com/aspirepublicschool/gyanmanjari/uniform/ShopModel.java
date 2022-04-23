package com.aspirepublicschool.gyanmanjari.uniform;

public class ShopModel {
    private String sc_id;
    private String ss_id,s_aval;
    private String s_id;
    private String s_cont;
    private String ss_name;
    private String s_img,addr;
    Double ratings;

    public ShopModel(String sc_id,String ss_id,String s_id, String s_cont, String ss_name,String addr, String s_img,Double ratings,String s_aval) {
        this.sc_id = sc_id;
        this.ss_id = ss_id;
        this.s_id=s_id;
        this.s_cont = s_cont;
        this.ss_name = ss_name;
        this.s_img = s_img;
        this.addr = addr;
        this.ratings = ratings;
        this.s_aval = s_aval;
    }

    public String getS_aval() {
        return s_aval;
    }

    public void setS_aval(String s_aval) {
        this.s_aval = s_aval;
    }

    public Double getRatings() {
        return ratings;
    }

    public void setRatings(Double ratings) {
        this.ratings = ratings;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public String getSs_id() {
        return ss_id;
    }

    public void setSs_id(String ss_id) {
        this.ss_id = ss_id;
    }

    public String getS_cont() {
        return s_cont;
    }

    public void setS_cont(String s_cont) {
        this.s_cont = s_cont;
    }

    public String getSs_name() {
        return ss_name;
    }

    public void setSs_name(String ss_name) {
        this.ss_name = ss_name;
    }

    public String getS_img() {
        return s_img;
    }

    public void setS_img(String s_img) {
        this.s_img = s_img;
    }
}
