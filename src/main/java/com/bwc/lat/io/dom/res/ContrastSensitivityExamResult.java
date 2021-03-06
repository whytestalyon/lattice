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
public class ContrastSensitivityExamResult extends ExamResult {

    private Double contrast;

    public ContrastSensitivityExamResult(Double contrast) {
        this.contrast = contrast;
    }

    public Double getContrast() {
        return contrast;
    }

    @Override
    public String toString() {
        return "ContrastSensitivityExamResult{" + "contrast=" + contrast + '}';
    }

}
