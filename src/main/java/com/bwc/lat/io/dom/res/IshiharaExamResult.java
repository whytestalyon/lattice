/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom.res;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class IshiharaExamResult extends ExamResult{

    private int edition;
    private String correctAnswers;
    private String type;

    public IshiharaExamResult(int edition, String correctAnswers, String type) {
        this.edition = edition;
        this.correctAnswers = correctAnswers.equals("-") ? null : correctAnswers;
        this.type = type.equals("-") ? null : type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    @Override
    public String toString() {
        return "IshiharaExamResult{" + "edition=" + edition + ", correctAnswers=" + correctAnswers + ", type=" + type + '}';
    }

}
