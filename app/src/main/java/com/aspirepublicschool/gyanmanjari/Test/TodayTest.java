package com.aspirepublicschool.gyanmanjari.Test;

public class TodayTest {
    /* {
                    "tst_id": "TST1",
                        "subject": "SKATING",
                        "total": "20",
                        "t_date": "2020-03-26",
                        "des": "zdvxvxvbv",
                        "stime": "12:30",
                        "etime": "13:00"
                }*/
    String tst_id,subject,total,t_date,des,stime,etime,pos,neg,t_type;

    public TodayTest(String tst_id, String subject, String total, String t_date, String des, String stime, String etime,String pos,String neg,String t_type) {
        this.tst_id = tst_id;
        this.subject = subject;
        this.total = total;
        this.t_date = t_date;
        this.des = des;
        this.stime = stime;
        this.etime = etime;
        this.pos=pos;
        this.neg=neg;
        this.t_type=t_type;
    }

    public String getT_type() {
        return t_type;
    }

    public void setT_type(String t_type) {
        this.t_type = t_type;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getNeg() {
        return neg;
    }

    public void setNeg(String neg) {
        this.neg = neg;
    }

    public String getTst_id() {
        return tst_id;
    }

    public void setTst_id(String tst_id) {
        this.tst_id = tst_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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
}
