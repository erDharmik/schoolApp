package com.aspirepublicschool.gyanmanjari;

public class ResultModel {
    String stu_id;
    String cid;
    String t_id;
    String test_type;
    String subject;
    String total_marks;
    String obtain_marks;
    String test_date;
   int progress;

    public ResultModel(String test_type, int progress) {
       this.test_type=test_type;
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public ResultModel(String stu_id, String cid, String t_id, String test_type, String subject, String total_marks, String obtain_marks, String test_date) {
        this.stu_id = stu_id;
        this.cid = cid;
        this.t_id = t_id;
        this.test_type = test_type;
        this.subject = subject;
        this.total_marks = total_marks;
        this.obtain_marks = obtain_marks;
        this.test_date = test_date;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTest_date() {
        return test_date;
    }

    public void setTest_date(String test_date) {
        this.test_date = test_date;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public String getObtain_marks() {
        return obtain_marks;
    }

    public void setObtain_marks(String obtain_marks) {
        this.obtain_marks = obtain_marks;
    }

    public String getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(String total_marks) {
        this.total_marks = total_marks;
    }
}
