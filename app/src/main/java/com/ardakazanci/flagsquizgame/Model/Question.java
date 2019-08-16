package com.ardakazanci.flagsquizgame.Model;

public class Question {

    private int question_id;
    private String question_image;
    private String question_answer_a;
    private String question_answer_b;
    private String question_answer_c;
    private String question_answer_d;
    private String question_correct_answer;


    public Question(int question_id, String question_image, String question_answer_a, String question_answer_b, String question_answer_c, String question_answer_d, String question_correct_answer) {
        this.question_id = question_id;
        this.question_image = question_image;
        this.question_answer_a = question_answer_a;
        this.question_answer_b = question_answer_b;
        this.question_answer_c = question_answer_c;
        this.question_answer_d = question_answer_d;
        this.question_correct_answer = question_correct_answer;
    }


    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_image() {
        return question_image;
    }

    public void setQuestion_image(String question_image) {
        this.question_image = question_image;
    }

    public String getQuestion_answer_a() {
        return question_answer_a;
    }

    public void setQuestion_answer_a(String question_answer_a) {
        this.question_answer_a = question_answer_a;
    }

    public String getQuestion_answer_b() {
        return question_answer_b;
    }

    public void setQuestion_answer_b(String question_answer_b) {
        this.question_answer_b = question_answer_b;
    }

    public String getQuestion_answer_c() {
        return question_answer_c;
    }

    public void setQuestion_answer_c(String question_answer_c) {
        this.question_answer_c = question_answer_c;
    }

    public String getQuestion_answer_d() {
        return question_answer_d;
    }

    public void setQuestion_answer_d(String question_answer_d) {
        this.question_answer_d = question_answer_d;
    }

    public String getQuestion_correct_answer() {
        return question_correct_answer;
    }

    public void setQuestion_correct_answer(String question_correct_answer) {
        this.question_correct_answer = question_correct_answer;
    }
}
