package com.aspirepublicschool.gyanmanjari;

public class HomeWorkDetails {
    String sub,date,title,desc,subdate,doc,h_id,t_name;


    public HomeWorkDetails(String sub, String date, String title, String desc, String subdate, String doc, String h_id,String t_name) {
        this.sub = sub;
        this.date = date;
        this.title = title;
        this.desc = desc;
        this.subdate = subdate;
        this.doc = doc;
        this.h_id = h_id;
        this.t_name = t_name;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
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

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getH_id() {
        return h_id;
    }

    public void setH_id(String h_id) {
        this.h_id = h_id;
    }
}
