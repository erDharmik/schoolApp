package com.aspirepublicschool.gyanmanjari;

public class notificationModel {
    String id, title, des, date , imgLink;

    public notificationModel(String id, String title, String des, String date, String imgLink) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.date = date;
        this.imgLink = imgLink;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
