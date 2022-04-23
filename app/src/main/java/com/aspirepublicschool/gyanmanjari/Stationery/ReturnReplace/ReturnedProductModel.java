package com.aspirepublicschool.gyanmanjari.Stationery.ReturnReplace;

public class ReturnedProductModel {
    String sin,img,label,order_id,u_id,s_id,r_status,direction,price,quantity,reason,cat;
     /* {
    "s_id": "ZCIN3",
    "order_id": "ODM175",
    "sin": "USIN22",
    "quantity": "1",
    "size": "M",
    "amount": "300",
    "direction": "Return",
    "reason": "broken product",
    "r_status": "4",
    "img1": "default.png",
    "label": "Male Sports full uniform"
  }*/

    public ReturnedProductModel(String sin, String img, String label, String order_id, String u_id, String s_id, String r_status, String direction,String price,String quantity,String reason,String cat) {
        this.sin = sin;
        this.img = img;
        this.label = label;
        this.order_id = order_id;
        this.u_id = u_id;
        this.s_id = s_id;
        this.r_status = r_status;
        this.direction = direction;
        this.price = price;
        this.quantity = quantity;
        this.reason = reason;
        this.cat = cat;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getR_status() {
        return r_status;
    }

    public void setR_status(String r_status) {
        this.r_status = r_status;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
