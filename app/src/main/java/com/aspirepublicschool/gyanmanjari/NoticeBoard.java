package com.aspirepublicschool.gyanmanjari;

class NoticeBoard {
    String img,date,title,des;

    public NoticeBoard(String img, String date, String title,String des) {
        this.img = img;
        this.date = date;
        this.title = title;
        this.des = des;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
