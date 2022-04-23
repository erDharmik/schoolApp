package com.aspirepublicschool.gyanmanjari;

public class Assignment
{
    String sub,date,title,desc,subdate,doc;
    public Assignment(String sub, String date, String title, String desc,String subdate, String doc)
    {
        this.sub = sub;
        this.date = date;
        this.title = title;
        this.desc = desc;
        this.subdate = subdate;
        this.doc = doc;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSubdate() {
        return subdate;
    }

    public void setSubdate(String subdate) {
        this.subdate = subdate;
    }
}
