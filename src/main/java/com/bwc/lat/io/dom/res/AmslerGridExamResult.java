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
public class AmslerGridExamResult extends ExamResult {
    private String od_res, os_res;

    public AmslerGridExamResult(String od_res, String os_res) {
        this.od_res = od_res;
        this.os_res = os_res;
    }

    public String getOd_res() {
        return od_res;
    }

    public String getOs_res() {
        return os_res;
    }
    
}
