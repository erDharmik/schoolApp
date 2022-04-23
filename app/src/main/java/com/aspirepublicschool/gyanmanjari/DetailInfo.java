package com.aspirepublicschool.gyanmanjari;

class DetailInfo {
    private String day;
    private String lecture;
    private String time;
    private String t_fname;
    private String t_lname;

    public DetailInfo(String day, String lecture,  String t_fname, String t_lname,String time) {
        this.day = day;
        this.lecture = lecture;
        this.time = time;
        this.t_fname = t_fname;
        this.t_lname = t_lname;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
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
}
