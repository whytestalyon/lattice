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
public enum SubjectColumn {

    INITIALS("C", 2),
    ID("D", 3),
    FIRST_NAME("E", 4),
    MIDDLE_INITIAL("F", 5),
    LAST_NAME("G", 6),
    VISIT_DATE("H", 7),
    DOB("I", 8),
    GENDER("K", 10),
    FROEDERT_MRN("N", 13),
    CHW_ID("O", 14),
    CLINICAL_TRIAL_ID("P", 15),
    OTHER_ID("R", 17),
    ADDRESS1("U", 20),
    ADDRESS2("V", 21),
    CITY("W", 22),
    STATE("X", 23),
    ZIP("Y", 24),
    COUNTRY("Z", 25),
    EMAIL("AA", 26),
    PHONE_PERSONAL("AB", 27),
    PHONE_WORK("AC", 28),
    DX_PRI("AG", 32),
    DX_SEC("AH", 33),
    DX_OTHER("AI", 34),
    DILATE_SAFE("AK", 36),
    NYSTAGMUS("AL", 37),
    UNSTABLE_FIXATION("AM", 38),
    EYE_COLOR("AN", 39),
    REFERRAL_TYPE("AP", 41),
    PERMISSION_RECONTACT("BL", 63),
    NOT_APPLICABLE("-1", -1);

    private static final HashMap<Integer, SubjectColumn> colMap = new HashMap<>(50);

    static {
        for (SubjectColumn sc : SubjectColumn.values()) {
            colMap.put(sc.getColIndex(), sc);
        }
    }
    private final String column;
    private final int colIndex;

    private SubjectColumn(String column, int colIndex) {
        this.column = column;
        this.colIndex = colIndex;
    }

    public String getColumn() {
        return column;
    }

    public int getColIndex() {
        return colIndex;
    }

    public static SubjectColumn getColByIndex(int idx) {
        SubjectColumn col = NOT_APPLICABLE;
        if (colMap.containsKey(idx)) {
            col = colMap.get(idx);
        }
        return col;
    }

}
