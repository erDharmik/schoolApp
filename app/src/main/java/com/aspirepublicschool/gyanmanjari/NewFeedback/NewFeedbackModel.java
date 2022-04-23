package com.aspirepublicschool.gyanmanjari.NewFeedback;

public class NewFeedbackModel {

    String id, teachername, subject, img;

    public NewFeedbackModel() {
    }

    public NewFeedbackModel(String id, String teachername, String subject, String img) {
        this.id = id;
        this.teachername = teachername;
        this.subject = subject;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
