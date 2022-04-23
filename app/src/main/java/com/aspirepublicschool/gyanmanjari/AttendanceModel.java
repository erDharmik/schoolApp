package com.aspirepublicschool.gyanmanjari;

public class AttendanceModel {
    private String status;
    private String date;

    public AttendanceModel(String status, String date) {
        this.status = status;
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
