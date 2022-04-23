package com.aspirepublicschool.gyanmanjari.Result;

public class Result {
  /*  {"total":"20","obtain":"1","subject":"SKATING","t_date":"27-03-2020"}*/
    String total,obtain,subject,t_date,tst_id,status,type,key;


    public Result(String total, String obtain, String subject, String t_date, String tst_id, String status, String type,String key) {
        this.total = total;
        this.obtain = obtain;
        this.subject = subject;
        this.t_date = t_date;
        this.tst_id = tst_id;
        this.status = status;
        this.type = type;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTst_id() {
        return tst_id;
    }

    public void setTst_id(String tst_id) {
        this.tst_id = tst_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getObtain() {
        return obtain;
    }

    public void setObtain(String obtain) {
        this.obtain = obtain;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }
}
