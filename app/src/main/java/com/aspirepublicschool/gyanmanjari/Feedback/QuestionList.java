package com.aspirepublicschool.gyanmanjari.Feedback;

public class QuestionList {
    private String question,qid,answer;

    public QuestionList(String question,String qid,String answer) {
        this.question = question;
        this.qid = qid;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
