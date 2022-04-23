package com.aspirepublicschool.gyanmanjari;


public class fees {
    String feename,duedate,termdetails;
    int amount;

    public fees(String feename, int amount,String duedate,String termdetails) {
        this.feename = feename;
        this.amount = amount;
        this.duedate = duedate;
        this.termdetails = termdetails;
    }

    public String getTermdetails() {
        return termdetails;
    }

    public void setTermdetails(String termdetails) {
        this.termdetails = termdetails;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getFeename() {
        return feename;
    }

    public void setFeename(String feename) {
        this.feename = feename;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

