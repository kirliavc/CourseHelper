package com.netlab.vc.coursehelper.util.jsonResults;

/**
 * Created by Vc on 2016/12/9.
 */

public class Answer {
    private String question;

    public String[] getOriginAnswer() {
        return originAnswer;
    }

    public void setOriginAnswer(String[] originAnswer) {
        this.originAnswer = originAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private String[] originAnswer;
}