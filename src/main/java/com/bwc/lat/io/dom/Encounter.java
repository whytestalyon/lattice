/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Each row in the input spreadsheet represents one encounter. Each encounter
 * has associated data fields plus a list of exams plus results for those exams.
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class Encounter {

    private static final AtomicInteger idCntr = new AtomicInteger(1000);
    private final int encounter_id;
    private final int subject_id;
    private int encounter_type_id;
    private Integer encounter_status_id = null;
    private Integer protocol_id = null;
    private Integer prim_pro_id = null;
    private Integer bankq_id = null;
    private Integer ref_pro_id = null;
    private Integer att_pro_id = null;
    private Date enc_sch_start_date = null;
    private Date enc_sch_stop_date = null;
    private Date encounter_start_date = null;
    private Date encounter_end_date = null;
    private Date clinic_encounter_date = null;
    private Date initial_contact_date = null;
    private String notes = null;
    private Date bill_sentdate = null;
    private Date bill_paiddate = null;
    private String bill_notes = null;
    private Integer stipend_given = null;
    private String stipend_notes = null;
    private final String created_by = "BWILK";
    private final Date created_date = new Date();
    private String modified_by = null;
    private Date modified_date = null;
    private Integer itinerary_needed = null;
    private Integer consent_needed = null;
    private Integer addendum_needed = null;
    private Integer pregnant_or_nursing = null;
    private Integer female_child_bearing_age = null;

    public Encounter(int subject_id, int encounter_type_id) {
        this.encounter_id = idCntr.getAndIncrement();
        this.subject_id = subject_id;
        this.encounter_type_id = encounter_type_id;
    }

    public Integer getEncounter_status_id() {
        return encounter_status_id;
    }

    public void setEncounter_status_id(Integer encounter_status_id) {
        this.encounter_status_id = encounter_status_id;
    }

    public Integer getProtocol_id() {
        return protocol_id;
    }

    public void setProtocol_id(Integer protocol_id) {
        this.protocol_id = protocol_id;
    }

    public Integer getPrim_pro_id() {
        return prim_pro_id;
    }

    public void setPrim_pro_id(Integer prim_pro_id) {
        this.prim_pro_id = prim_pro_id;
    }

    public Integer getBankq_id() {
        return bankq_id;
    }

    public void setBankq_id(Integer bankq_id) {
        this.bankq_id = bankq_id;
    }

    public Integer getRef_pro_id() {
        return ref_pro_id;
    }

    public void setRef_pro_id(Integer ref_pro_id) {
        this.ref_pro_id = ref_pro_id;
    }

    public Integer getAtt_pro_id() {
        return att_pro_id;
    }

    public void setAtt_pro_id(Integer att_pro_id) {
        this.att_pro_id = att_pro_id;
    }

    public Date getEnc_sch_start_date() {
        return enc_sch_start_date;
    }

    public void setEnc_sch_start_date(Date enc_sch_start_date) {
        this.enc_sch_start_date = enc_sch_start_date;
    }

    public Date getEnc_sch_stop_date() {
        return enc_sch_stop_date;
    }

    public void setEnc_sch_stop_date(Date enc_sch_stop_date) {
        this.enc_sch_stop_date = enc_sch_stop_date;
    }

    public Date getEncounter_start_date() {
        return encounter_start_date;
    }

    public void setEncounter_start_date(Date encounter_start_date) {
        this.encounter_start_date = encounter_start_date;
    }

    public Date getEncounter_end_date() {
        return encounter_end_date;
    }

    public void setEncounter_end_date(Date encounter_end_date) {
        this.encounter_end_date = encounter_end_date;
    }

    public Date getClinic_encounter_date() {
        return clinic_encounter_date;
    }

    public void setClinic_encounter_date(Date clinic_encounter_date) {
        this.clinic_encounter_date = clinic_encounter_date;
    }

    public Date getInitial_contact_date() {
        return initial_contact_date;
    }

    public void setInitial_contact_date(Date initial_contact_date) {
        this.initial_contact_date = initial_contact_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getBill_sentdate() {
        return bill_sentdate;
    }

    public void setBill_sentdate(Date bill_sentdate) {
        this.bill_sentdate = bill_sentdate;
    }

    public Date getBill_paiddate() {
        return bill_paiddate;
    }

    public void setBill_paiddate(Date bill_paiddate) {
        this.bill_paiddate = bill_paiddate;
    }

    public String getBill_notes() {
        return bill_notes;
    }

    public void setBill_notes(String bill_notes) {
        this.bill_notes = bill_notes;
    }

    public Integer getStipend_given() {
        return stipend_given;
    }

    public void setStipend_given(Integer stipend_given) {
        this.stipend_given = stipend_given;
    }

    public String getStipend_notes() {
        return stipend_notes;
    }

    public void setStipend_notes(String stipend_notes) {
        this.stipend_notes = stipend_notes;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public Date getModified_date() {
        return modified_date;
    }

    public void setModified_date(Date modified_date) {
        this.modified_date = modified_date;
    }

    public Integer getItinerary_needed() {
        return itinerary_needed;
    }

    public void setItinerary_needed(Integer itinerary_needed) {
        this.itinerary_needed = itinerary_needed;
    }

    public Integer getConsent_needed() {
        return consent_needed;
    }

    public void setConsent_needed(Integer consent_needed) {
        this.consent_needed = consent_needed;
    }

    public Integer getAddendum_needed() {
        return addendum_needed;
    }

    public void setAddendum_needed(Integer addendum_needed) {
        this.addendum_needed = addendum_needed;
    }

    public Integer getPregnant_or_nursing() {
        return pregnant_or_nursing;
    }

    public void setPregnant_or_nursing(Integer pregnant_or_nursing) {
        this.pregnant_or_nursing = pregnant_or_nursing;
    }

    public Integer getFemale_child_bearing_age() {
        return female_child_bearing_age;
    }

    public void setFemale_child_bearing_age(Integer female_child_bearing_age) {
        this.female_child_bearing_age = female_child_bearing_age;
    }

    public int getEncounter_id() {
        return encounter_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public int getEncounter_type_id() {
        return encounter_type_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setEncounter_type_id(int encounter_type_id) {
        this.encounter_type_id = encounter_type_id;
    }

    @Override
    public String toString() {
        return "Encounter{" + "encounter_id=" + encounter_id + ", subject_id=" + subject_id + ", encounter_type_id=" + encounter_type_id + ", encounter_status_id=" + encounter_status_id + ", protocol_id=" + protocol_id + ", prim_pro_id=" + prim_pro_id + ", bankq_id=" + bankq_id + ", ref_pro_id=" + ref_pro_id + ", att_pro_id=" + att_pro_id + ", enc_sch_start_date=" + enc_sch_start_date + ", enc_sch_stop_date=" + enc_sch_stop_date + ", encounter_start_date=" + encounter_start_date + ", encounter_end_date=" + encounter_end_date + ", clinic_encounter_date=" + clinic_encounter_date + ", initial_contact_date=" + initial_contact_date + ", notes=" + notes + ", bill_sentdate=" + bill_sentdate + ", bill_paiddate=" + bill_paiddate + ", bill_notes=" + bill_notes + ", stipend_given=" + stipend_given + ", stipend_notes=" + stipend_notes + ", created_by=" + created_by + ", created_date=" + created_date + ", modified_by=" + modified_by + ", modified_date=" + modified_date + ", itinerary_needed=" + itinerary_needed + ", consent_needed=" + consent_needed + ", addendum_needed=" + addendum_needed + ", pregnant_or_nursing=" + pregnant_or_nursing + ", female_child_bearing_age=" + female_child_bearing_age + '}';
    }

}
