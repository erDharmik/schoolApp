package com.aspirepublicschool.gyanmanjari.NewRegister.DemoRequestDashboard;

public class teacherModel {

    String id, mobile, email, FirstName, LastName, t_id, subject, status;

    public teacherModel(String id, String mobile, String email, String firstName, String lastName, String t_id, String subject, String status) {
        this.id = id;
        this.mobile = mobile;
        this.email = email;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.t_id = t_id;
        this.subject = subject;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }
}
