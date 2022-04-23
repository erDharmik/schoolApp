package com.aspirepublicschool.gyanmanjari.VideoLectures;

public class VideoLectureModel {
    /*sc_id,cid,date,subject,link,stime,etime,des*/
    String sc_id,cid,date,subject,link,stime,etime,des,status,status1,linkyt,id;

    public VideoLectureModel(String sc_id, String cid, String date, String subject, String link, String stime, String etime, String des, String status, String status1,String linkyt,String id) {
        this.sc_id = sc_id;
        this.cid = cid;
        this.date = date;
        this.subject = subject;
        this.link = link;
        this.stime = stime;
        this.etime = etime;
        this.des = des;
        this.status = status;
        this.status1 = status1;
        this.linkyt = linkyt;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkyt() {
        return linkyt;
    }

    public void setLinkyt(String linkyt) {
        this.linkyt = linkyt;
    }

    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }
}
