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
public class AOHRRExamResult extends ExamResult {

    private final String type, dvalue;

    public AOHRRExamResult(String type, String dvalue) {
        this.type = type.equals("-")?null: type;
        this.dvalue = dvalue.equals("-")?null: dvalue;
    }

    public String getType() {
        return type;
    }

    public String getDvalue() {
        return dvalue;
    }

    @Override
    public String toString() {
        return "AOHRRExamResult{" + "type=" + type + ", dvalue=" + dvalue + '}';
    }

}
