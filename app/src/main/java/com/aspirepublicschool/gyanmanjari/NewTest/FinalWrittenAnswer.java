package com.aspirepublicschool.gyanmanjari.NewTest;

public class FinalWrittenAnswer {

    String qid,Selected,tst_id,mark,sign;

    public FinalWrittenAnswer(String qid, String selected, String tst_id, String mark, String sign) {
        this.qid = qid;
        Selected = selected;
        this.tst_id = tst_id;
        this.mark = mark;
        this.sign = sign;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getSelected() {
        return Selected;
    }

    public void setSelected(String selected) {
        Selected = selected;
    }

    public String getTst_id() {
        return tst_id;
    }

    public void setTst_id(String tst_id) {
        this.tst_id = tst_id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

