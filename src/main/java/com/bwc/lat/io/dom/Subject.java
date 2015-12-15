/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class Subject {

    private static final AtomicInteger idCntr = new AtomicInteger(100);
    private final int subject_id;
    private Referal referral_type_id = Referal.UNKNOWN;
    private String chw_id, froedert_mrn, aoip_id, fname, mi, lname, gender, clinical_trial_id, other_id, address1, address2, city, state, zip, country, email, phone_personal, phone_work, eye_color, created_by, permission_recontact;
    private Date dob, created_date;
    private String dx_pri, dx_sec, dx_other;
    private String dilate_safe, nystagmus, unstable_fixation;

    public Subject() {
        subject_id = idCntr.getAndIncrement();
        aoip_id = null;
        fname = null;
        mi = null;
        lname = null;
        gender = null;
        clinical_trial_id = null;
        other_id = null;
        address1 = null;
        address2 = null;
        city = null;
        state = null;
        zip = null;
        country = null;
        email = null;
        phone_personal = null;
        phone_work = null;
        eye_color = null;
        created_by = "BWILK";
        permission_recontact = null;
        dob = null;
        created_date = new Date();
        froedert_mrn = null;
        chw_id = null;
        dx_pri = null;
        dx_sec = null;
        dx_other = null;
        dilate_safe = null;
        nystagmus = null;
        unstable_fixation = null;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public Referal getReferral_type_id() {
        return referral_type_id;
    }

    public void setReferral_type_id(Referal referral_type_id) {
        this.referral_type_id = referral_type_id == null ? Referal.PHONE : referral_type_id;
    }

    public String getAoip_id() {
        return aoip_id;
    }

    public void setAoip_id(String aoip_id) {
        this.aoip_id = aoip_id.trim().isEmpty() ? null : aoip_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname.trim().isEmpty() ? null : fname;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi.trim().isEmpty() ? null : mi;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname.trim().isEmpty() ? null : lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender.trim().isEmpty() ? null : gender;
    }

    public String getClinical_trial_id() {
        return clinical_trial_id;
    }

    public void setClinical_trial_id(String clinical_trial_id) {
        this.clinical_trial_id = clinical_trial_id.trim().isEmpty() ? null : clinical_trial_id;
    }

    public String getOther_id() {
        return other_id;
    }

    public void setOther_id(String other_id) {
        this.other_id = other_id.trim().isEmpty() ? null : other_id;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1.trim().isEmpty() ? null : address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2.trim().isEmpty() ? null : address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city.trim().isEmpty() ? null : city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state.trim().isEmpty() ? null : state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip.trim().isEmpty() ? null : zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country.trim().isEmpty() ? null : country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim().isEmpty() ? null : email;
    }

    public String getPhone_personal() {
        return phone_personal;
    }

    public void setPhone_personal(String phone_personal) {
        this.phone_personal = phone_personal.trim().isEmpty() ? null : phone_personal;
    }

    public String getPhone_work() {
        return phone_work;
    }

    public void setPhone_work(String phone_work) {
        this.phone_work = phone_work.trim().isEmpty() ? null : phone_work;
    }

    public String getEye_color() {
        return eye_color;
    }

    public void setEye_color(String eye_color) {
        this.eye_color = eye_color.trim().isEmpty() ? null : eye_color;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by.trim().isEmpty() ? null : created_by;
    }

    public String getPermission_recontact() {
        return permission_recontact;
    }

    public void setPermission_recontact(String permission_recontact) {
        this.permission_recontact = permission_recontact.trim().isEmpty() ? null : permission_recontact;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getFroedert_mrn() {
        return froedert_mrn;
    }

    public void setFroedert_mrn(String froedert_mrn) {
        this.froedert_mrn = froedert_mrn.trim().isEmpty() ? null : froedert_mrn;
    }

    public String getChw_id() {
        return chw_id;
    }

    public void setChw_id(String chw_id) {
        this.chw_id = chw_id.trim().isEmpty() ? null : chw_id;
    }

    public String getDx_pri() {
        return dx_pri;
    }

    public void setDx_pri(String dx_pri) {
        this.dx_pri = dx_pri.trim().isEmpty() ? null : dx_pri;
    }

    public String getDx_sec() {
        return dx_sec;
    }

    public void setDx_sec(String dx_sec) {
        this.dx_sec = dx_sec.trim().isEmpty() ? null : dx_sec;
    }

    public String getDx_other() {
        return dx_other;
    }

    public void setDx_other(String dx_other) {
        this.dx_other = dx_other.trim().isEmpty() ? null : dx_other;
    }

    public String getDilate_safe() {
        return dilate_safe;
    }

    public void setDilate_safe(String dilate_safe) {
        this.dilate_safe = dilate_safe.trim().isEmpty() ? null : dilate_safe;
    }

    public String getNystagmus() {
        return nystagmus;
    }

    public void setNystagmus(String nystagmus) {
        this.nystagmus = nystagmus.trim().isEmpty() ? null : nystagmus;
    }

    public String getUnstable_fixation() {
        return unstable_fixation;
    }

    public void setUnstable_fixation(String unstable_fixation) {
        this.unstable_fixation = unstable_fixation.trim().isEmpty() ? null : unstable_fixation;
    }

    @Override
    public String toString() {
        return "Subject{" + "subject_id=" + subject_id + ", referral_type_id=" + referral_type_id + ", chw_id=" + chw_id + ", froedert_mrn=" + froedert_mrn + ", aoip_id=" + aoip_id + ", fname=" + fname + ", mi=" + mi + ", lname=" + lname + ", gender=" + gender + ", clinical_trial_id=" + clinical_trial_id + ", other_id=" + other_id + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state + ", zip=" + zip + ", country=" + country + ", email=" + email + ", phone_personal=" + phone_personal + ", phone_work=" + phone_work + ", eye_color=" + eye_color + ", created_by=" + created_by + ", permission_recontact=" + permission_recontact + ", dob=" + dob + ", created_date=" + created_date + ", dx_pri=" + dx_pri + ", dx_sec=" + dx_sec + ", dx_other=" + dx_other + ", dilate_safe=" + dilate_safe + ", nystagmus=" + nystagmus + ", unstable_fixation=" + unstable_fixation + '}';
    }

}
