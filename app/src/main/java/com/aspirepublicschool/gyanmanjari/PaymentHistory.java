package com.aspirepublicschool.gyanmanjari;

class PaymentHistory {
    String term,amount,payment_mode,date,receipt_id;


    public PaymentHistory(String term, String amount, String payment_mode, String date, String receipt_id) {
        this.term = term;
        this.amount = amount;
        this.payment_mode = payment_mode;
        this.date = date;
        this.receipt_id = receipt_id;


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
