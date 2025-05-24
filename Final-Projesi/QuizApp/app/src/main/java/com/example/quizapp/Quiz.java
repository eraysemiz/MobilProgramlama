package com.example.quizapp;

public class Quiz {
    private int quizId;
    private String quizTitle;

    public Quiz(int quizId, String quizTitle) {
        this.quizId    = quizId;
        this.quizTitle = quizTitle;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }
}
