/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom;

import java.util.HashMap;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public enum Referal {

    INTERNAL(1, "internal"),
    EXTERNAL(2, "external"),
    WEB(3, "web"),
    FRIEND(4, "friend"),
    FAMILY(5, "family"),
    STUDENT(6, "student"),
    PHONE(7, ""),
    SELF(8, "self"),
    EMPLOYEE(9, "employee"),
    IRD_CLINIC(10, "ird clinic"),
    VISITOR(11, "visitor"),
    UNKNOWN(12, "none");

    private int ID;
    private String ExcelValue;
    private static final HashMap<String, Referal> refMap = new HashMap<>();

    static {
        for (Referal r : Referal.values()) {
            refMap.put(r.getExcelValue(), r);
        }
    }

    public static Referal getReferal(String referal) {
        if (refMap.containsKey(referal)) {
            return refMap.get(referal);
        } else {
            return UNKNOWN;
        }
    }

    private Referal(int ID, String ExcelValue) {
        this.ID = ID;
        this.ExcelValue = ExcelValue;
    }

    public int getID() {
        return ID;
    }

    public String getExcelValue() {
        return ExcelValue;
    }

}
