package com.aspirepublicschool.gyanmanjari;

public class PublicEventsModel {
    String img,title,date,place,weblink;


    public PublicEventsModel(String img, String title, String date, String place, String weblink) {
        this.img = img;
        this.title = title;
        this.date = date;
        this.place = place;
        this.weblink = weblink;

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }


}
