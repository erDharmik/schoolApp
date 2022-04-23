package com.aspirepublicschool.gyanmanjari.uniform;

class ShopBanner {
    String Shopname,address,ss_id,img,s_cont;
    double ratings;

    public ShopBanner(String shopname, String address, String ss_id,String img,String s_cont,Double ratings ) {
        this.Shopname = shopname;
        this.address = address;
        this.ss_id = ss_id;
        this.img = img;
        this.s_cont = s_cont;
        this.ratings = ratings;
    }

    public String getS_cont() {
        return s_cont;
    }

    public void setS_cont(String s_cont) {
        this.s_cont = s_cont;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShopname() {
        return Shopname;
    }

    public void setShopname(String shopname) {
        Shopname = shopname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSs_id() {
        return ss_id;
    }

    public void setSs_id(String ss_id) {
        this.ss_id = ss_id;
    }
}
