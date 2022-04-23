package com.aspirepublicschool.gyanmanjari.Stationery;

public class FullPackageModal {
    String img,id,p_name,price,des,cat,sin;

    public FullPackageModal(String img, String id, String p_name, String price,String des,String cat,String sin) {
        this.img = img;
        this.id = id;
        this.p_name = p_name;
        this.price = price;
        this.des = des;
        this.cat = cat;
        this.sin = sin;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
