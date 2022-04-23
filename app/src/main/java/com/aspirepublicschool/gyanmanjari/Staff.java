package com.aspirepublicschool.gyanmanjari;



public class Staff {

    String t_fname,t_lname,sub,t_cont,t_img;

    public Staff(String t_fname, String t_lname, String sub,String t_img, String t_cont) {
        this.t_fname = t_fname;
        this.t_lname = t_lname;
        this.sub = sub;
        this.t_cont = t_cont;
        this.t_img = t_img;

    }

    public String getT_img() {
        return t_img;
    }

    public void setT_img(String t_img) {
        this.t_img = t_img;
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

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getT_cont() {
        return t_cont;
    }

    public void setT_cont(String t_cont) {
        this.t_cont = t_cont;
    }


}
