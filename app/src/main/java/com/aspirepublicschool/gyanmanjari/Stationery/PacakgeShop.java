package com.aspirepublicschool.gyanmanjari.Stationery;

public class PacakgeShop {

   /* {
        "p_id": "PKGID2",
            "s_id": "ZCIN2",
            "s_cont": "8758809709",
            "ss_name": "Sameer Book Stall",
            "ss_id": "SHIDN12",
            "addr": "zone 1, Surat",
            "city": "Bhavnagar",
            "ss_status": "1",
            "s_img": "87588097091.jpg",
            "s_aval": "0"
    }*/
        String p_id,s_id,s_cont,ss_name,ss_id,addr,city,s_img,s_aval;
        Double rating;

    public PacakgeShop(String p_id, String s_id, String s_cont, String ss_name, String ss_id, String addr, String city, String s_img, String s_aval,Double rating) {
        this.p_id = p_id;
        this.s_id = s_id;
        this.s_cont = s_cont;
        this.ss_name = ss_name;
        this.ss_id = ss_id;
        this.addr = addr;
        this.city = city;
        this.s_img = s_img;
        this.s_aval = s_aval;
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
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

    public String getSs_id() {
        return ss_id;
    }

    public void setSs_id(String ss_id) {
        this.ss_id = ss_id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getS_img() {
        return s_img;
    }

    public void setS_img(String s_img) {
        this.s_img = s_img;
    }

    public String getS_aval() {
        return s_aval;
    }

    public void setS_aval(String s_aval) {
        this.s_aval = s_aval;
    }
}
