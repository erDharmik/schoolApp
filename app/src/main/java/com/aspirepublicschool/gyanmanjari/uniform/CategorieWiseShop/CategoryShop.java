package com.aspirepublicschool.gyanmanjari.uniform.CategorieWiseShop;

public class CategoryShop {
    String ss_id,ids,s_cont,ss_name,addr,s_img;
    Double ratings;
    boolean isCustomView=false;
    int status,aval;

    public CategoryShop(String ss_id, String ids, String s_cont, String ss_name, String addr, String s_img, Double ratings, int status,int aval) {
        this.ss_id = ss_id;
        this.ids = ids;
        this.s_cont = s_cont;
        this.ss_name = ss_name;
        this.addr = addr;
        this.s_img = s_img;
        this.ratings = ratings;
        this.status = status;
        this.aval = aval;
    }

    public int getAval() {
        return aval;
    }

    public void setAval(int aval) {
        this.aval = aval;
    }

    public String getSs_id() {
        return ss_id;
    }

    public void setSs_id(String ss_id) {
        this.ss_id = ss_id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getS_img() {
        return s_img;
    }

    public void setS_img(String s_img) {
        this.s_img = s_img;
    }

    public Double getRatings() {
        return ratings;
    }

    public void setRatings(Double ratings) {
        this.ratings = ratings;
    }

    public boolean isCustomView() {
        return isCustomView;
    }

    public void setCustomView(boolean customView) {
        isCustomView = customView;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
