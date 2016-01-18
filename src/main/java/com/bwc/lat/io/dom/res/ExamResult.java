/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom.res;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public abstract class ExamResult {

    private static final AtomicInteger idCntr = new AtomicInteger(1000);
    private final int exam_result_id;
    private final String created_by = "BWILK";
    private final Date created_date = new Date();
    private boolean isFileResult = false;

    public ExamResult() {
        exam_result_id = idCntr.getAndIncrement();
    }

    public boolean isIsFileResult() {
        return isFileResult;
    }

    public void setIsFileResult(boolean isFileResult) {
        this.isFileResult = isFileResult;
    }

    public int getExam_result_id() {
        return exam_result_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public Date getCreated_date() {
        return created_date;
    }

}
