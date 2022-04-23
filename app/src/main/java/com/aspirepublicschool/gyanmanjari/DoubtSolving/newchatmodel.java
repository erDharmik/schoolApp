package com.aspirepublicschool.gyanmanjari.DoubtSolving;

public class newchatmodel {

    String stu_id, st_sname, st_fname, f_cno;

    public newchatmodel(String stu_id, String st_sname, String st_fname, String f_cno) {
        this.stu_id = stu_id;
        this.st_sname = st_sname;
        this.st_fname = st_fname;
        this.f_cno = f_cno;
    }

    public String getF_cno() {
        return f_cno;
    }

    public void setF_cno(String f_cno) {
        this.f_cno = f_cno;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getSt_sname() {
        return st_sname;
    }

    public void setSt_sname(String st_sname) {
        this.st_sname = st_sname;
    }

    public String getSt_fname() {
        return st_fname;
    }

    public void setSt_fname(String st_fname) {
        this.st_fname = st_fname;
    }

}
