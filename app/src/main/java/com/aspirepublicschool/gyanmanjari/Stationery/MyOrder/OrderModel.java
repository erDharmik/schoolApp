package com.aspirepublicschool.gyanmanjari.Stationery.MyOrder;

public class OrderModel {
    /*{
        "sin": "USIN22",
            "order_id": "ODM175",
            "quantity": "1",
            "price": "300",
            "mobileno": "8866774456",
            "address": "jweles circle ,bhavnagar,Gujarat",
            "s_id": "ZCIN3",
            "payment_mode": "COD",
            "timestamp": "13-03-2020/14:39:45",
            "order_status": "1",
            "ss_name": "vaidant Book Stall"
    },*/
    String img,order_id,p_name,price,sin,qty,order_status,ss_name,time,payment_mode,address,s_id,mobile_no;

    public OrderModel(String img, String order_id, String p_name, String price, String sin, String qty, String order_status, String ss_name, String time, String payment_mode, String address, String s_id,String mobile_no) {
        this.img = img;
        this.order_id = order_id;
        this.p_name = p_name;
        this.price = price;
        this.sin = sin;
        this.qty = qty;
        this.order_status = order_status;
        this.ss_name = ss_name;
        this.time = time;
        this.payment_mode = payment_mode;
        this.address = address;
        this.s_id = s_id;
        this.mobile_no = mobile_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getSs_name() {
        return ss_name;
    }

    public void setSs_name(String ss_name) {
        this.ss_name = ss_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }
}
