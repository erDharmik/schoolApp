package com.aspirepublicschool.gyanmanjari.uniform.Delivery;

public class DeliveryModel {
    String sin,pro_name,s_id;
    int qty,price;

    public DeliveryModel(String sin, String pro_name, int qty, int price,String s_id) {
        this.sin = sin;
        this.pro_name = pro_name;
        this.qty = qty;
        this.price = price;
        this.s_id = s_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }



    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
