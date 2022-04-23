package com.aspirepublicschool.gyanmanjari.Homework;

public class Homework {
   String hw_id,doc,sub,remark,status,gr_no;
  /*  "hw_id": "HMWK1",
            "doc": "2020-03-05-10_28_42-ENGLISH.jpg",
            "sub": "SKATING"*/

    public Homework(String hw_id, String doc, String sub, String remark, String status, String gr_no) {
        this.hw_id = hw_id;
        this.doc = doc;
        this.sub = sub;
        this.remark = remark;
        this.status = status;
        this.gr_no = gr_no;
    }

    public String getHw_id() {
        return hw_id;
    }

    public void setHw_id(String hw_id) {
        this.hw_id = hw_id;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGr_no() {
        return gr_no;
    }

    public void setGr_no(String gr_no) {
        this.gr_no = gr_no;
    }
}
