package com.aspirepublicschool.gyanmanjari;

public class Receipt {
    String term,feename,amount,payment_mode,date,receipt_id,sc_name;

    public Receipt(String term, String feename, String amount, String payment_mode, String date,String receipt_id,String sc_name) {
        this.term = term;
        this.feename = feename;
        this.amount = amount;
        this.payment_mode = payment_mode;
        this.date = date;
        this.receipt_id = receipt_id;
        this.sc_name = sc_name;
    }

    public String getSc_name() {
        return sc_name;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }

    public String getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(String receipt_id) {
        this.receipt_id = receipt_id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getFeename() {
        return feename;
    }

    public void setFeename(String feename) {
        this.feename = feename;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
