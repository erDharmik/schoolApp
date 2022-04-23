package com.aspirepublicschool.gyanmanjari.Doubt;

public class SubjectDoubt {
    String t_name,sub,chapter,topic,data,date;

    public SubjectDoubt(String t_name, String sub, String chapter, String topic, String data,String date) {
        this.t_name = t_name;
        this.sub = sub;
        this.chapter = chapter;
        this.topic = topic;
        this.data = data;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
