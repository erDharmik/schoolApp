package com.aspirepublicschool.gyanmanjari;

class Academic {
    String sc_id,title,date,file;

    public Academic(String sc_id,String title, String date, String file) {
        this.sc_id = sc_id;
        this.title = title;
        this.date = date;
        this.file = file;
    }

    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
