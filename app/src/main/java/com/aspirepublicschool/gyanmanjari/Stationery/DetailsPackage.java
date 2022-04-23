package com.aspirepublicschool.gyanmanjari.Stationery;

public class DetailsPackage {
    /*{
    "p_id": "PKGID2",
    "s_id": "ZCIN2",
    "rating": "2.0000",
    "s_cont": "8758809709",
    "ss_name": "Sameer Book Stall",
    "ss_id": "SHIDN12",
    "addr": "zone 1, Surat",
    "city": "Bhavnagar",
    "s_img": "87588097091.jpg",
    "s_aval": "0"
  }*/
    String pacakge_name,p_id,sin,qty,p_code,label,stock,img1,proce,discount,s_id,cat;
    boolean ischecked;

    public DetailsPackage(String pacakge_name, String p_id, String sin, String qty, String p_code, String label, String stock, String img1, String proce, String discount, String s_id,String cat,boolean ischecked) {
        this.pacakge_name = pacakge_name;
        this.p_id = p_id;
        this.sin = sin;
        this.qty = qty;
        this.p_code = p_code;
        this.label = label;
        this.stock = stock;
        this.img1 = img1;
        this.proce = proce;
        this.discount = discount;
        this.s_id = s_id;
        this.cat = cat;
        this.ischecked=ischecked;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getPacakge_name() {
        return pacakge_name;
    }

    public void setPacakge_name(String pacakge_name) {
        this.pacakge_name = pacakge_name;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
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

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getProce() {
        return proce;
    }

    public void setProce(String proce) {
        this.proce = proce;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }
}
