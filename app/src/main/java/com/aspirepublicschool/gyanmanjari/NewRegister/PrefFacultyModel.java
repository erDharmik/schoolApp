package com.aspirepublicschool.gyanmanjari.NewRegister;


public class PrefFacultyModel {
    String id, fname, lname, img, m_no, board, standard, subject, medium, time;

    public PrefFacultyModel(String id, String fname, String lname, String img, String m_no, String board, String standard, String subject, String medium, String time) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.img = img;
        this.m_no = m_no;
        this.board = board;
        this.standard = standard;
        this.subject = subject;
        this.medium = medium;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getM_no() {
        return m_no;
    }

    public void setM_no(String m_no) {
        this.m_no = m_no;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
