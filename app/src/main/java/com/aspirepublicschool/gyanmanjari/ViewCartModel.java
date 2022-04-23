package com.aspirepublicschool.gyanmanjari;

public class ViewCartModel {
    private String sin,qty,s_id,amount,time,label,img;

    public ViewCartModel(String sin, String qty, String s_id, String amount, String time,String label,String img) {
        this.sin = sin;
        this.qty = qty;
        this.s_id = s_id;
        this.amount = amount;
        this.time = time;
        this.label = label;
        this.img = img;
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
