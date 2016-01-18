/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom.res;

import com.bwc.lat.io.exc.Eye;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class D15ExamResult extends ExamResult {

    private final double MOI_Angle, MOI_Major_Radius, MOI_Minor_Radius, MOI_Total_Error_Score, MOI_Selectivity_Index, MOI_Confusion_Index;
    private final String MOI_Color_Discriminaton, MOI_Notes;
    private final boolean saturated;
    private final int trial_num;
    private final Eye eye;

    public D15ExamResult(double MOI_Angle, double MOI_Major_Radius, double MOI_Minor_Radius, double MOI_Total_Error_Score, double MOI_Selectivity_Index, double MOI_Confusion_Index, String MOI_Color_Discriminaton, String MOI_Notes, boolean saturated, int trial_num, Eye eye) {
        this.MOI_Angle = MOI_Angle;
        this.MOI_Major_Radius = MOI_Major_Radius;
        this.MOI_Minor_Radius = MOI_Minor_Radius;
        this.MOI_Total_Error_Score = MOI_Total_Error_Score;
        this.MOI_Selectivity_Index = MOI_Selectivity_Index;
        this.MOI_Confusion_Index = MOI_Confusion_Index;
        this.MOI_Color_Discriminaton = MOI_Color_Discriminaton;
        this.MOI_Notes = MOI_Notes;
        this.saturated = saturated;
        this.trial_num = trial_num;
        this.eye = eye;
    }

    public Eye getEye() {
        return eye;
    }

    public boolean isSaturated() {
        return saturated;
    }

    public int getTrial_num() {
        return trial_num;
    }

    public double getMOI_Angle() {
        return MOI_Angle;
    }

    public double getMOI_Major_Radius() {
        return MOI_Major_Radius;
    }

    public double getMOI_Minor_Radius() {
        return MOI_Minor_Radius;
    }

    public double getMOI_Total_Error_Score() {
        return MOI_Total_Error_Score;
    }

    public double getMOI_Selectivity_Index() {
        return MOI_Selectivity_Index;
    }

    public double getMOI_Confusion_Index() {
        return MOI_Confusion_Index;
    }

    public String getMOI_Color_Discriminaton() {
        return MOI_Color_Discriminaton;
    }

    public String getMOI_Notes() {
        return MOI_Notes;
    }

}
