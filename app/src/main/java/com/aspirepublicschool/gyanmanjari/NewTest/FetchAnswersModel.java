package com.aspirepublicschool.gyanmanjari.NewTest;

public class FetchAnswersModel {

    String qid,sign,answer;

    public FetchAnswersModel(String qid, String sign, String answer) {
        this.qid = qid;
        this.sign = sign;
        this.answer = answer;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
