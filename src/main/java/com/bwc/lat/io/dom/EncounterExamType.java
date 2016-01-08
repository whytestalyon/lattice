/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom;

import com.bwc.lat.io.dom.res.ExamResult;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class EncounterExamType {

    private static final AtomicInteger idCntr = new AtomicInteger(10000);
    private final int encounter_exam_type_id;
    private final int exam_type_id;
    private final int encounter_id;
    private final String created_by = "BWILK";
    private final Date created_date = new Date();
    private Integer personnel_id;
    private final LinkedList<ExamResult> results = new LinkedList<>();

    public EncounterExamType(int exam_type_id, int encounter_id) {
        this.encounter_exam_type_id = idCntr.getAndIncrement();
        this.exam_type_id = exam_type_id;
        this.encounter_id = encounter_id;
    }

    public Integer getPersonnel_id() {
        return personnel_id;
    }

    public void setPersonnel_id(Integer personnel_id) {
        this.personnel_id = personnel_id;
    }

    public int getEncounter_exam_type_id() {
        return encounter_exam_type_id;
    }

    public int getExam_type_id() {
        return exam_type_id;
    }

    public int getEncounter_id() {
        return encounter_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public LinkedList<ExamResult> getResults() {
        return results;
    }

    public void addResult(ExamResult result) {
        results.add(result);
    }
}
