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
public class IOLMasterExamResult extends ExamResult {

    private Double k1_od, k2_od, k1_os, k2_os, axial_od, axial_os, acd_od, acd_os;

    public IOLMasterExamResult(Double k1_od, Double k2_od, Double k1_os, Double k2_os, Double axial_od, Double axial_os, Double acd_od, Double acd_os) {
        this.k1_od = k1_od;
        this.k2_od = k2_od;
        this.k1_os = k1_os;
        this.k2_os = k2_os;
        this.axial_od = axial_od;
        this.axial_os = axial_os;
        this.acd_od = acd_od;
        this.acd_os = acd_os;
    }

    public Double getK1_od() {
        return k1_od;
    }

    public Double getK2_od() {
        return k2_od;
    }

    public Double getK1_os() {
        return k1_os;
    }

    public Double getK2_os() {
        return k2_os;
    }

    public Double getAxial_od() {
        return axial_od;
    }

    public Double getAxial_os() {
        return axial_os;
    }

    public Double getAcd_od() {
        return acd_od;
    }

    public Double getAcd_os() {
        return acd_os;
    }

}
