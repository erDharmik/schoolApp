package com.aspirepublicschool.gyanmanjari.DoubtSolving;

public class TeacherDoubt {
    String t_id,t_name,t_img,number,subject;

    public TeacherDoubt(String t_id, String t_name, String t_img,String number,String subject) {
        this.t_id = t_id;
        this.t_name = t_name;
        this.t_img = t_img;
        this.number = number;
        this.subject = subject;

    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_img() {
        return t_img;
    }

    public void setT_img(String t_img) {
        this.t_img = t_img;
    }
}
