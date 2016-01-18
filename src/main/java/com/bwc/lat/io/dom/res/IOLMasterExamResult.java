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
public class IOLMasterExamResult extends ExamResult{

    private double k1_od, k2_od, k1_os, k2_os, axial_od, axial_os, acd_od, acd_os;

    public IOLMasterExamResult(double k1_od, double k2_od, double k1_os, double k2_os, double axial_od, double axial_os, double acd_od, double acd_os) {
        this.k1_od = k1_od;
        this.k2_od = k2_od;
        this.k1_os = k1_os;
        this.k2_os = k2_os;
        this.axial_od = axial_od;
        this.axial_os = axial_os;
        this.acd_od = acd_od;
        this.acd_os = acd_os;
    }

    public double getK1_od() {
        return k1_od;
    }

    public double getK2_od() {
        return k2_od;
    }

    public double getK1_os() {
        return k1_os;
    }

    public double getK2_os() {
        return k2_os;
    }

    public double getAxial_od() {
        return axial_od;
    }

    public double getAxial_os() {
        return axial_os;
    }

    public double getAcd_od() {
        return acd_od;
    }

    public double getAcd_os() {
        return acd_os;
    }

}
