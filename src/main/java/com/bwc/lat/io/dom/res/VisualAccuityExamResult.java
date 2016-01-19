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
public class VisualAccuityExamResult extends ExamResult {

    private String va_measured, va_method, va_refrac_sphere_od, va_refrac_sphere_os, va_refrac_cyl_od, va_refrac_cyl_os, va_refrac_axis_od, va_refrac_axis_os, va_od, va_os, va_binocular;

    public VisualAccuityExamResult(String va_measured, String va_method, String va_refrac_sphere_od, String va_refrac_sphere_os, String va_refrac_cyl_od, String va_refrac_cyl_os, String va_refrac_axis_od, String va_refrac_axis_os, String va_od, String va_os, String va_binocular) {
        this.va_measured = va_measured.equals("-") ? null : va_measured;
        this.va_method = va_method.equals("-") ? null : va_method;
        this.va_refrac_sphere_od = va_refrac_sphere_od.equals("-") ? null : va_refrac_sphere_od;
        this.va_refrac_sphere_os = va_refrac_sphere_os.equals("-") ? null : va_refrac_sphere_os;
        this.va_refrac_cyl_od = va_refrac_cyl_od.equals("-") ? null : va_refrac_cyl_od;
        this.va_refrac_cyl_os = va_refrac_cyl_os.equals("-") ? null : va_refrac_cyl_os;
        this.va_refrac_axis_od = va_refrac_axis_od.equals("-") ? null : va_refrac_axis_od;
        this.va_refrac_axis_os = va_refrac_axis_os.equals("-") ? null : va_refrac_axis_os;
        this.va_od = va_od.equals("-") ? null : va_od;
        this.va_os = va_os.equals("-") ? null : va_os;
        this.va_binocular = va_binocular.equals("-") ? null : va_binocular;
    }

    public String getEye() {
        return "be";
    }

    public String getVa_measured() {
        return va_measured;
    }

    public String getVa_method() {
        return va_method;
    }

    public String getVa_refrac_sphere_od() {
        return va_refrac_sphere_od;
    }

    public String getVa_refrac_sphere_os() {
        return va_refrac_sphere_os;
    }

    public String getVa_refrac_cyl_od() {
        return va_refrac_cyl_od;
    }

    public String getVa_refrac_cyl_os() {
        return va_refrac_cyl_os;
    }

    public String getVa_refrac_axis_od() {
        return va_refrac_axis_od;
    }

    public String getVa_refrac_axis_os() {
        return va_refrac_axis_os;
    }

    public String getVa_od() {
        return va_od;
    }

    public String getVa_os() {
        return va_os;
    }

    public String getVa_binocular() {
        return va_binocular;
    }

    @Override
    public String toString() {
        return "VisualAccuityExamResult{" + "va_measured=" + va_measured + ", va_method=" + va_method + ", va_refrac_sphere_od=" + va_refrac_sphere_od + ", va_refrac_sphere_os=" + va_refrac_sphere_os + ", va_refrac_cyl_od=" + va_refrac_cyl_od + ", va_refrac_cyl_os=" + va_refrac_cyl_os + ", va_refrac_axis_od=" + va_refrac_axis_od + ", va_refrac_axis_os=" + va_refrac_axis_os + ", va_od=" + va_od + ", va_os=" + va_os + ", va_binocular=" + va_binocular + '}';
    }

}
