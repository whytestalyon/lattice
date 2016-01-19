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
public class MDVisitExamResult extends ExamResult {

    private String undilated, dilated;

    public MDVisitExamResult(String undilated, String dilated) {
        this.undilated = undilated.equals("-") ? null : undilated;
        this.dilated = dilated.equals("-") ? null : dilated;
    }

    public String getUndilated() {
        return undilated;
    }

    public String getDilated() {
        return dilated;
    }

    @Override
    public String toString() {
        return "MDVisitExamResult{" + "undilated=" + undilated + ", dilated=" + dilated + '}';
    }

}
