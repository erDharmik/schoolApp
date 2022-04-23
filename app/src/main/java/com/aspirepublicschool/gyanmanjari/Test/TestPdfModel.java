package com.aspirepublicschool.gyanmanjari.Test;

public class TestPdfModel {
  /*  {
        "sub": "Chemistry",
            "title": "gfh",
            "des": "cbfhcf",
            "timeline": "Not Set",
            "doc": "Chemistry2020-03-30 10:38:59.jpg",
            "date": "2020-03-30 10:38:59",
            "t_fname": "Dr vijay",
            "t_lname": "vala"
    }*/
  String sub,title,des,doc,date,t_name;

    public TestPdfModel(String sub, String title, String des, String doc, String date, String t_name) {
        this.sub = sub;
        this.title = title;
        this.des = des;
        this.doc = doc;
        this.date = date;
        this.t_name = t_name;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
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


    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }
}
