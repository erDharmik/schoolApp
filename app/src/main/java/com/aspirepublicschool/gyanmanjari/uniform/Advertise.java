package com.aspirepublicschool.gyanmanjari.uniform;

public class Advertise {
  /* {
    "s_id": "ZCIN4",
    "sin": "USIN3",
    "p_code": "154",
    "sc_id": "SCIDN14",
    "img1": "USIN31.jpg",
    "price": "2000",
    "discount": "5",
    "label": "Male Sports full uniform",
    "cat": "Full Uniform",
    "brand": "Raymond",
    "rating": "2.00"
  }*/
    String sin,s_id,price,discount,label,img,p_code,cat;

    public Advertise(String sin, String s_id, String price, String discount, String label, String img,String p_code,String cat) {
        this.sin = sin;
        this.s_id = s_id;
        this.price = price;
        this.discount = discount;
        this.label = label;
        this.img = img;
        this.p_code = p_code;
        this.cat = cat;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
