package com.aspirepublicschool.gyanmanjari;

public class Remainder {
    String date,subject,stime,etime,des;

    public Remainder(String date, String subject, String stime, String etime, String des) {
        this.date = date;
        this.subject = subject;
        this.stime = stime;
        this.etime = etime;
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
