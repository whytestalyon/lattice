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
public class OCTCirrusUndilatedExamResult extends ExamResult{
    private String od, os;

    public OCTCirrusUndilatedExamResult(String od, String os) {
        this.od = od;
        this.os = os;
    }

    public String getOd() {
        return od;
    }

    public String getOs() {
        return os;
    }
}
