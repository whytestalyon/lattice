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
public class DilationExamResult extends ExamResult{

    private boolean pheno, trop;

    public DilationExamResult(boolean pheno, boolean trop) {
        this.pheno = pheno;
        this.trop = trop;
    }

    public boolean isPheno() {
        return pheno;
    }

    public boolean isTrop() {
        return trop;
    }

}
