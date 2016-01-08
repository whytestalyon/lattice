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
    private final int encounter_exam_type_id;
    private String created_by = "BWILK";
    private Date created_date = new Date();

    public ExamResult(int encounter_exam_type_id) {
        exam_result_id = idCntr.getAndIncrement();
        this.encounter_exam_type_id = encounter_exam_type_id;
    }

}
