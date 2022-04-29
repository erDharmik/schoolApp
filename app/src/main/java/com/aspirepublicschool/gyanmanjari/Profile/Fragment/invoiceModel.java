package com.aspirepublicschool.gyanmanjari.Profile.Fragment;

public class invoiceModel {

    String stu_id , std , amount , date ;

    public invoiceModel(String stu_id, String std, String amount, String date) {
        this.stu_id = stu_id;
        this.std = std;
        this.amount = amount;
        this.date = date;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
