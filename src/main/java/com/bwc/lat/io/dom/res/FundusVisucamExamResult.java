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
public class FundusVisucamExamResult extends ExamResult {

    private String od_30, os_30, od_45, os_45;

    public FundusVisucamExamResult(String od_30, String os_30, String od_45, String os_45) {
        this.od_30 = od_30.equals("-") ? null : od_30;
        this.os_30 = os_30.equals("-") ? null : os_30;
        this.od_45 = od_45.equals("-") ? null : od_45;
        this.os_45 = os_45.equals("-") ? null : os_45;
    }

    public String getOd_30() {
        return od_30;
    }

    public String getOs_30() {
        return os_30;
    }

    public String getOd_45() {
        return od_45;
    }

    public String getOs_45() {
        return os_45;
    }

    @Override
    public String toString() {
        return "FundusVisucamExamResult{" + "od_30=" + od_30 + ", os_30=" + os_30 + ", od_45=" + od_45 + ", os_45=" + os_45 + '}';
    }

}
