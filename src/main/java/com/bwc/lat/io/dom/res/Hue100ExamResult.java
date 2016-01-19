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
public class Hue100ExamResult extends ExamResult {

    private final Double MOI_Angle, MOI_Major_Radius, MOI_Minor_Radius, MOI_Total_Error_Score, MOI_Selectivity_Index, MOI_Confusion_Index, hue100_classical_error_score;
    private final String hue100_classical_notes, MOI_Notes ;
    private final Eye eye;

    public Hue100ExamResult(Double MOI_Angle, Double MOI_Major_Radius, Double MOI_Minor_Radius, Double MOI_Total_Error_Score, Double MOI_Selectivity_Index, Double MOI_Confusion_Index, Double hue100_classical_error_score, String hue100_classical_notes, String MOI_Notes, Eye eye) {
        this.MOI_Angle = MOI_Angle;
        this.MOI_Major_Radius = MOI_Major_Radius;
        this.MOI_Minor_Radius = MOI_Minor_Radius;
        this.MOI_Total_Error_Score = MOI_Total_Error_Score;
        this.MOI_Selectivity_Index = MOI_Selectivity_Index;
        this.MOI_Confusion_Index = MOI_Confusion_Index;
        this.hue100_classical_error_score = hue100_classical_error_score;
        this.hue100_classical_notes = hue100_classical_notes;
        this.MOI_Notes = MOI_Notes;
        this.eye = eye;
    }

    public Double getHue100_classical_error_score() {
        return hue100_classical_error_score;
    }

    public String getHue100_classical_notes() {
        return hue100_classical_notes;
    }

    public Eye getEye() {
        return eye;
    }

    public Double getMOI_Angle() {
        return MOI_Angle;
    }

    public Double getMOI_Major_Radius() {
        return MOI_Major_Radius;
    }

    public Double getMOI_Minor_Radius() {
        return MOI_Minor_Radius;
    }

    public Double getMOI_Total_Error_Score() {
        return MOI_Total_Error_Score;
    }

    public Double getMOI_Selectivity_Index() {
        return MOI_Selectivity_Index;
    }

    public Double getMOI_Confusion_Index() {
        return MOI_Confusion_Index;
    }

    public String getMOI_Notes() {
        return MOI_Notes;
    }

}
