package com.aspirepublicschool.gyanmanjari.Stationery.Delivery;

public class DeliveryModel {
    String img, id, p_name, price, des, cat, sin, s_id;

    public DeliveryModel(String img, String id, String p_name, String price, String des, String cat, String sin, String s_id) {
        this.img = img;
        this.id = id;
        this.p_name = p_name;
        this.price = price;
        this.des = des;
        this.cat = cat;
        this.sin = sin;
        this.s_id = s_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }
}
