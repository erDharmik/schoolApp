package com.aspirepublicschool.gyanmanjari;

public class ViewSent {
    String To,Title,t_fname,t_lname,msg,time;

    public ViewSent(String to, String title, String t_fname, String t_lname,String msg,String time) {
        this.To = to;
        this.Title = title;
        this.t_fname = t_fname;
        this.t_lname = t_lname;
        this.msg = msg;
        this.time = time;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getT_fname() {
        return t_fname;
    }

    public void setT_fname(String t_fname) {
        this.t_fname = t_fname;
    }

    public String getT_lname() {
        return t_lname;
    }

    public void setT_lname(String t_lname) {
        this.t_lname = t_lname;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
