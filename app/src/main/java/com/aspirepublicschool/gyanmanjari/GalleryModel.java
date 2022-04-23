package com.aspirepublicschool.gyanmanjari;

public class GalleryModel {
    private String id;
    private String title,photo,date;

    public GalleryModel(String id, String title, String photo,String date) {
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
