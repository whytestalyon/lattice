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
public class AutoRefractorExamResult extends ExamResult {

    private String sphere_od, cyl_od, axis_od, sphere_os, cyl_os, axis_os;

    public AutoRefractorExamResult(String sphere_od, String cyl_od, String axis_od, String sphere_os, String cyl_os, String axis_os) {
        this.sphere_od = sphere_od;
        this.cyl_od = cyl_od;
        this.axis_od = axis_od;
        this.sphere_os = sphere_os;
        this.cyl_os = cyl_os;
        this.axis_os = axis_os;
    }

    public String getSphere_od() {
        return sphere_od;
    }

    public String getCyl_od() {
        return cyl_od;
    }

    public String getAxis_od() {
        return axis_od;
    }

    public String getSphere_os() {
        return sphere_os;
    }

    public String getCyl_os() {
        return cyl_os;
    }

    public String getAxis_os() {
        return axis_os;
    }

}
