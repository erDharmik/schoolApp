package com.aspirepublicschool.gyanmanjari.Test;

public class TestQuestion {
  /*  {
        "qid": "QST1",
            "question": "Test Question ?",
            "a": "A",
            "b": "B",
            "c": "C",
            "d": "D"
    },*/
  String q_id,question,a,b,c,d,selected,q_img,a_img,b_img,c_img,d_img,tst_id,sub;
    boolean mark;

    public TestQuestion(String q_id, String question, String a, String b, String c, String d, String selected, String q_img, String a_img, String b_img, String c_img, String d_img,boolean mark,String tst_id,String sub) {
        this.q_id = q_id;
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.selected = selected;
        this.q_img = q_img;
        this.a_img = a_img;
        this.b_img = b_img;
        this.c_img = c_img;
        this.d_img = d_img;
        this.mark = mark;
        this.tst_id=tst_id;
        this.sub=sub;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTst_id() {
        return tst_id;
    }

    public void setTst_id(String tst_id) {
        this.tst_id = tst_id;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getQ_img() {
        return q_img;
    }

    public void setQ_img(String q_img) {
        this.q_img = q_img;
    }

    public String getA_img() {
        return a_img;
    }

    public void setA_img(String a_img) {
        this.a_img = a_img;
    }

    public String getB_img() {
        return b_img;
    }

    public void setB_img(String b_img) {
        this.b_img = b_img;
    }

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }

    public String getD_img() {
        return d_img;
    }

    public void setD_img(String d_img) {
        this.d_img = d_img;
    }
}
