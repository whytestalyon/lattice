/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.exc;

import java.util.HashMap;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public enum EncounterColumn {

    EXAMS_STAFF("AE", 30),
    NOTES("LX", 335),
    STIPEND_GIVEN("BJ", 61),
    REFERING_MD("AO", 40),
    ADDENDUM("BI", 60),
    FEMALE_CHILD_BEARING_AGE("HF", 213),
    PREGNANT_OR_NURSING("HG", 214),
    ITINERARY("S", 18),
    NOT_APPLICABLE("-1", -1);

    private static final HashMap<Integer, EncounterColumn> colMap = new HashMap<>(50);

    static {
        for (EncounterColumn sc : EncounterColumn.values()) {
            colMap.put(sc.getColIndex(), sc);
        }
    }
    private final String column;
    private final int colIndex;

    private EncounterColumn(String column, int colIndex) {
        this.column = column;
        this.colIndex = colIndex;
    }

    public String getColumn() {
        return column;
    }

    public int getColIndex() {
        return colIndex;
    }

    public static EncounterColumn getColByIndex(int idx) {
        EncounterColumn col = NOT_APPLICABLE;
        if (colMap.containsKey(idx)) {
            col = colMap.get(idx);
        }
        return col;
    }
}
