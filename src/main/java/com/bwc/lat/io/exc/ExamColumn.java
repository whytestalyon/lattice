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
public enum ExamColumn {
    OCCULAR_HEALTH_QUESTIONAIRE("AJ",35),
    STAFF_CONSENTING("AD",29);
    
    private static final HashMap<Integer, ExamColumn> colMap = new HashMap<>(50);

    static {
        for (ExamColumn sc : ExamColumn.values()) {
            colMap.put(sc.getColIndex(), sc);
        }
    }
    private final String column;
    private final int colIndex;

    private ExamColumn(String column, int colIndex) {
        this.column = column;
        this.colIndex = colIndex;
    }

    public String getColumn() {
        return column;
    }

    public int getColIndex() {
        return colIndex;
    }

    public static ExamColumn getColByIndex(int idx) {
        ExamColumn col = null;
        if (colMap.containsKey(idx)) {
            col = colMap.get(idx);
        }
        return col;
    }
}
