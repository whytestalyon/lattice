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
public class OCTBioptigenExamResult extends ExamResult{
    private String clinical_od, clinical_os, glac_od, glac_os;

    public OCTBioptigenExamResult(String clinical_od, String clinical_os, String glac_od, String glac_os) {
        this.clinical_od = clinical_od.equals("-") ? null : clinical_od;
        this.clinical_os = clinical_os.equals("-") ? null : clinical_os;
        this.glac_od = glac_od.equals("-") ? null : glac_od;
        this.glac_os = glac_os.equals("-") ? null : glac_os;
    }

    public String getClinical_od() {
        return clinical_od;
    }

    public String getClinical_os() {
        return clinical_os;
    }

    public String getGlac_od() {
        return glac_od;
    }

    public String getGlac_os() {
        return glac_os;
    }

    @Override
    public String toString() {
        return "OCTBioptigenExamResult{" + "clinical_od=" + clinical_od + ", clinical_os=" + clinical_os + ", glac_od=" + glac_od + ", glac_os=" + glac_os + '}';
    }
    
    
}
