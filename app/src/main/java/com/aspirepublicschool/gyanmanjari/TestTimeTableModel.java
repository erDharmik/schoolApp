package com.aspirepublicschool.gyanmanjari;

public class TestTimeTableModel {
    private String test_type;
    private String test_subject;
    private String test_total;
    private String test_date;
    private String test_time;
    private String t_fname;
    private String t_lname;

    public TestTimeTableModel(String test_type, String test_subject, String test_total, String test_date, String test_time, String t_fname, String t_lname) {
        this.test_type = test_type;
        this.test_subject = test_subject;
        this.test_total = test_total;
        this.test_date = test_date;
        this.test_time = test_time;
        this.t_fname = t_fname;
        this.t_lname = t_lname;
    }

    public String getTest_type() {
        return test_type;
    }

    public void setTest_type(String test_type) {
        this.test_type = test_type;
    }

    public String getTest_subject() {
        return test_subject;
    }

    public void setTest_subject(String test_subject) {
        this.test_subject = test_subject;
    }

    public String getTest_total() {
        return test_total;
    }

    public void setTest_total(String test_total) {
        this.test_total = test_total;
    }

    public String getTest_date() {
        return test_date;
    }

    public void setTest_date(String test_date) {
        this.test_date = test_date;
    }

    public String getTest_time() {
        return test_time;
    }

    public void setTest_time(String test_time) {
        this.test_time = test_time;
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
}
