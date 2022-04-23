package com.aspirepublicschool.gyanmanjari;

public class Teacher {
    String tfname,tlname,tid;

    public Teacher(String tfname, String tlname, String tid) {
        this.tfname = tfname;
        this.tlname = tlname;
        this.tid = tid;
    }

    public String getTfname() {
        return tfname;
    }

    public void setTfname(String tfname)
    {
        this.tfname = tfname;
    }
    public String getTlname() {
        return tlname;
    }

    public void setTlname(String tlname) {
        this.tlname = tlname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
