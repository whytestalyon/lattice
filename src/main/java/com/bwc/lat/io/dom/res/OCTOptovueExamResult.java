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
public class OCTOptovueExamResult extends ExamResult {
    private String od, os;

    public OCTOptovueExamResult(String od, String os) {
        this.od = od.equals("-") ? null : od;
        this.os = os.equals("-") ? null : os;
    }

    public String getOd() {
        return od;
    }

    public String getOs() {
        return os;
    }

    @Override
    public String toString() {
        return "OCTOptovueExamResult{" + "od=" + od + ", os=" + os + '}';
    }
    
    

}
