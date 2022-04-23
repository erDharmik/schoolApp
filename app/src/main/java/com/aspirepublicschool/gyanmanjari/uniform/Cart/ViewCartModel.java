package com.aspirepublicschool.gyanmanjari.uniform.Cart;

public class ViewCartModel {
    private String sin,qty,s_id,amount,time,label,img,ssname,size,cat,p_code;

    public ViewCartModel(String sin, String qty,String size, String s_id, String amount, String time, String label, String img,String ssname,String cat,String p_code) {
        this.sin = sin;
        this.qty = qty;
        this.size = size;
        this.s_id = s_id;
        this.amount = amount;
        this.time = time;
        this.label = label;
        this.img = img;
        this.ssname = ssname;
        this.cat = cat;
        this.p_code = p_code;

    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSsname() {
        return ssname;
    }

    public void setSsname(String ssname) {
        this.ssname = ssname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
