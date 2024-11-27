package org.example;

public class Question {
    private String studentId;
    private String questionTitle;
    private String questionAsked;
    private String answerBlanks;
    private String studentAnswer;
    private String score;
    private String outOf;
    private String correctAnswers;

    public Question(String studentId, String questionTitle, String questionAsked, String answerBlanks, String studentAnswer, String score, String outOf) {
        this.studentId = studentId;
        this.questionTitle = questionTitle;
        this.questionAsked = questionAsked;
        this.answerBlanks = answerBlanks;
        this.studentAnswer = studentAnswer;
        this.score = score;
        this.outOf = outOf;
        this.correctAnswers ="";
    }

    public Question() {

    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionAsked() {
        return questionAsked;
    }

    public void setQuestionAsked(String questionAsked) {
        this.questionAsked = questionAsked;
    }

    public String getAnswerBlanks() {
        return answerBlanks;
    }

    public void setAnswerBlanks(String answerBlanks) {
        this.answerBlanks = answerBlanks;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getOutOf() {
        return outOf;
    }

    public void setOutOf(String outOf) {
        this.outOf = outOf;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
