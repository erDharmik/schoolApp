package com.aspirepublicschool.gyanmanjari.WRT_Test;

public class WRTResultModel {
    String sc_id,t_id,cid,subject,stime,etime,q_doc,sol_doc,tst_date,total,tst_ans_status,obtain,tst_id,ans_doc,remarks;

    public WRTResultModel(String sc_id, String t_id, String cid, String subject, String stime, String etime, String q_doc, String sol_doc, String tst_date, String total, String tst_ans_status, String obtain, String tst_id, String ans_doc, String remarks) {
        this.sc_id = sc_id;
        this.t_id = t_id;
        this.cid = cid;
        this.subject = subject;
        this.stime = stime;
        this.etime = etime;
        this.q_doc = q_doc;
        this.sol_doc = sol_doc;
        this.tst_date = tst_date;
        this.total = total;
        this.tst_ans_status = tst_ans_status;
        this.obtain = obtain;
        this.tst_id = tst_id;
        this.ans_doc = ans_doc;
        this.remarks = remarks;
    }

    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getQ_doc() {
        return q_doc;
    }

    public void setQ_doc(String q_doc) {
        this.q_doc = q_doc;
    }

    public String getSol_doc() {
        return sol_doc;
    }

    public void setSol_doc(String sol_doc) {
        this.sol_doc = sol_doc;
    }

    public String getTst_date() {
        return tst_date;
    }

    public void setTst_date(String tst_date) {
        this.tst_date = tst_date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTst_ans_status() {
        return tst_ans_status;
    }

    public void setTst_ans_status(String tst_ans_status) {
        this.tst_ans_status = tst_ans_status;
    }

    public String getObtain() {
        return obtain;
    }

    public void setObtain(String obtain) {
        this.obtain = obtain;
    }

    public String getTst_id() {
        return tst_id;
    }

    public void setTst_id(String tst_id) {
        this.tst_id = tst_id;
    }

    public String getAns_doc() {
        return ans_doc;
    }

    public void setAns_doc(String ans_doc) {
        this.ans_doc = ans_doc;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
