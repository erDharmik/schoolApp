package com.aspirepublicschool.gyanmanjari.NewTest;

public class TestWrittenQuestion {
    String q_id,question,selected,sign,q_img,tst_id,sub;
    boolean mark;

    public TestWrittenQuestion(String q_id, String question, String selected, String sign, String q_img, String tst_id, String sub, boolean mark) {
        this.q_id = q_id;
        this.question = question;
        this.selected = selected;
        this.sign = sign;
        this.q_img = q_img;
        this.tst_id = tst_id;
        this.sub = sub;
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

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getQ_img() {
        return q_img;
    }

    public void setQ_img(String q_img) {
        this.q_img = q_img;
    }

    public String getTst_id() {
        return tst_id;
    }

    public void setTst_id(String tst_id) {
        this.tst_id = tst_id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
