package com.aspirepublicschool.gyanmanjari.uniform.clothes;

public class ShopProductModel {
    private String sin,label,p_code,price,img,s_id,cat,discount;

    public ShopProductModel(String sin,String label,String p_code,String price,String img ,String s_id,String cat,String discount) {
        this.sin = sin;
        this.label = label;
        this.p_code = p_code;
        this.price = price;
        this.img = img;
        this.s_id = s_id;
        this.cat = cat;
        this.discount = discount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
