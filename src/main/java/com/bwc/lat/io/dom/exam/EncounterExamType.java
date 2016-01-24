/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom.exam;

import com.bwc.lat.io.dom.PersonnelMap;
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
    private final ExamType exam_type;
    private final String created_by = "BWILK";
    private final Date created_date = new Date();
    private Integer personnel_id = null;
    private final LinkedList<ExamResult> results = new LinkedList<>();

    public EncounterExamType(ExamType exam_type) {
        this.encounter_exam_type_id = idCntr.getAndIncrement();
        this.exam_type = exam_type;
    }

    public Integer getPersonnel_id() {
        return personnel_id;
    }

    public void setPersonnel_id(String personnel) {
        if (personnel != null) {
            String p = personnel.split(",")[0];
            Integer pid = PersonnelMap.getPersonnelId(p);
            if (pid != null) {
                personnel_id = pid;
            }
        }
    }

    public int getEncounter_exam_type_id() {
        return encounter_exam_type_id;
    }

    public ExamType getExam_type() {
        return exam_type;
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

    @Override
    public String toString() {
        return "EncounterExamType{" + "encounter_exam_type_id=" + encounter_exam_type_id + ", exam_type_id=" + exam_type + ", created_by=" + created_by + ", created_date=" + created_date + ", personnel_id=" + personnel_id + ", results=" + results + '}';
    }

}
