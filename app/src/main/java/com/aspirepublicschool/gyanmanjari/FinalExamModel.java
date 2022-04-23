package com.aspirepublicschool.gyanmanjari;

public class FinalExamModel {
    private String subject,obtain_mark,total_mark;

    public FinalExamModel(String subject, String obtain_mark, String total_mark) {
        this.subject = subject;
        this.obtain_mark = obtain_mark;
        this.total_mark = total_mark;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObtain_mark() {
        return obtain_mark;
    }

    public void setObtain_mark(String obtain_mark) {
        this.obtain_mark = obtain_mark;
    }

    public String getTotal_mark() {
        return total_mark;
    }

    public void setTotal_mark(String total_mark) {
        this.total_mark = total_mark;
    }
}
