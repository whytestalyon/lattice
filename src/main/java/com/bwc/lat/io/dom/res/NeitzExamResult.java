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
public class NeitzExamResult extends ExamResult {

    private final String result;

    public NeitzExamResult(String result) {
        super();
        this.result = result.equals("-") ? null : result;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "NeitzExamResult{" + "result=" + result + '}';
    }

}
