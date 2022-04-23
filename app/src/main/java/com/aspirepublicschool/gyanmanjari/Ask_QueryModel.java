package com.aspirepublicschool.gyanmanjari;

public class Ask_QueryModel {
    private String sender;
    private String title;
    private String message;
    private String time;
    private String t_fname;
    private String t_lname;

    public Ask_QueryModel(String sender, String title, String message, String time, String t_fname, String t_lname) {
        this.sender = sender;
        this.title = title;
        this.message = message;
        this.time = time;
        this.t_fname = t_fname;
        this.t_lname = t_lname;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
