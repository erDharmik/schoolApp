package com.aspirepublicschool.gyanmanjari;

public class Mainpage_design
{
    private String Title;
    private int Thumbnail;

    public Mainpage_design()
    {

    }

    public Mainpage_design(String title, int thumbnail)
    {
        Title = title;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }


    public int getThumbnail() {
        return Thumbnail;
    }


    public void setTitle(String title) {
        Title = title;
    }


    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
