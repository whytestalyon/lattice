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
public class PedigreeExamResult extends ExamResult{
    private String pedigree;

    public PedigreeExamResult(String pedigree) {
        this.pedigree = pedigree.equals("-")? null: pedigree;
    }

    public String getPedigree() {
        return pedigree;
    }

    @Override
    public String toString() {
        return "PedigreeExamResult{" + "pedigree=" + pedigree + '}';
    }
    
    
}
