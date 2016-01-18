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
public class AOExamResult extends ExamResult{
    private String od, od_fixation, os, os_fixation;

    public AOExamResult(String od, String od_fixation, String os, String os_fixation) {
        this.od = od;
        this.od_fixation = od_fixation;
        this.os = os;
        this.os_fixation = os_fixation;
    }

    public String getOd() {
        return od;
    }

    public String getOd_fixation() {
        return od_fixation;
    }

    public String getOs() {
        return os;
    }

    public String getOs_fixation() {
        return os_fixation;
    }

}
