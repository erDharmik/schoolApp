package com.aspirepublicschool.gyanmanjari.uniform.MyOrder;

public class OrderModel {
    private String order_id;
    private String qty;
    private String price;
    private String mobile_no;
    private String address;
    private String s_id;
    private String payment_mode;
    private String time;
    private String p_code;
    private String order_status;
    private String label;
    private String img,ss_name;
    String cat,size;

    public OrderModel(String order_id, String qty, String price, String mobile_no, String address, String s_id, String payment_mode, String time, String p_code, String order_status, String label, String img,String ss_name,String cat,String size ) {
        this.order_id = order_id;
        this.qty = qty;
        this.price = price;
        this.mobile_no = mobile_no;
        this.address = address;
        this.s_id = s_id;
        this.payment_mode = payment_mode;
        this.time = time;
        this.p_code = p_code;
        this.order_status = order_status;
        this.label = label;
        this.img = img;
        this.ss_name=ss_name;
        this.cat=cat;
        this.size=size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSs_name() {
        return ss_name;
    }

    public void setSs_name(String ss_name) {
        this.ss_name = ss_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
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

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
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
