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
public class SkinPigment extends ExamResult {

    private String lvalue;

    public SkinPigment(String lvalue) {
        this.lvalue = lvalue.equals("-") ? null : lvalue;
    }

    public String getLvalue() {
        return lvalue;
    }

    @Override
    public String toString() {
        return "SkinPigment{" + "lvalue=" + lvalue + '}';
    }

}
