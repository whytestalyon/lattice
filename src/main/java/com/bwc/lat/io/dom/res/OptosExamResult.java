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
public class OptosExamResult extends ExamResult {

    private String color_od, color_os, af_od, af_os;

    public OptosExamResult(String color_od, String color_os, String af_od, String af_os) {
        this.color_od = color_od;
        this.color_os = color_os;
        this.af_od = af_od;
        this.af_os = af_os;
    }

    public String getColor_od() {
        return color_od;
    }

    public String getColor_os() {
        return color_os;
    }

    public String getAf_od() {
        return af_od;
    }

    public String getAf_os() {
        return af_os;
    }

}
