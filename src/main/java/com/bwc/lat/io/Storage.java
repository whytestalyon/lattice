/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io;

import com.bwc.lat.io.dom.DiagnosisMap;
import com.bwc.lat.io.dom.Encounter;
import com.bwc.lat.io.dom.Subject;
import com.bwc.lat.io.dom.exam.EncounterExamType;
import com.bwc.lat.io.dom.exam.ExamType;
import com.bwc.lat.io.dom.res.D15ExamResult;
import com.bwc.lat.io.dom.res.ExamResult;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class Storage {

    private static final DiagnosisMap dm = DiagnosisMap.getInstance();

    private static final String subject_insert = "INSERT\n"
            + "INTO\n"
            + "  SUBJECT\n"
            + "  (\n"
            + "    SUBJECT_ID,\n" //1
            + "    AOIP_ID,\n" //2
            + "    FROEDERT_MRN,\n" //3
            + "    CHW_ID,\n" //4
            + "    CLINICAL_TRIAL_ID,\n" //5
            + "    OTHER_ID,\n" //6
            + "    FNAME,\n" //7
            + "    LNAME,\n" //8
            + "    GENDER,\n" //9
            + "    ADDRESS1,\n" //10
            + "    ADDRESS2,\n" //11
            + "    CITY,\n" //12
            + "    STATE,\n" //13
            + "    ZIP,\n" //14
            + "    COUNTRY,\n" //15
            + "    EMAIL,\n" //16
            + "    PHONE_PERSONAL,\n" //17
            + "    PHONE_WORK,\n" //18
            + "    NOTES,\n" //19
            + "    REFERRAL_TYPE_ID,\n" //20
            + "    CREATED_BY,\n" //21
            + "    CREATED_DATE,\n" //22
            + "    MODIFIED_BY,\n" //23
            + "    MODIFIED_DATE,\n" //24
            + "    EYE_COLOR,\n" //25
            + "    DOB,\n" //26
            + "    NYSTAGMUS,\n" //27
            + "    UNSTABLE_FIXATION,\n" //28
            + "    DILATE_SAFE,\n" //29
            + "    MI,\n" //30
            + "    DX_PRI,\n" //31
            + "    DX_SEC,\n" //32
            + "    DX_OTHER,\n" //33
            + "    PERMISSION_RECONTACT\n" //34
            + "  )\n"
            + "  VALUES\n"
            + "  (\n"
            + "  ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?\n"
            + "  )";

    private static String encounter_insert = "INSERT\n"
            + "INTO\n"
            + "  ENCOUNTER\n"
            + "  (\n"
            + "ENCOUNTER_ID,\n" //1
            + "SUBJECT_ID,\n" //2
            + "ENCOUNTER_TYPE_ID,\n" //3
            + "ENCOUNTER_STATUS_ID,\n" //4
            + "BANKQ_ID,\n" //5
            + "REF_PRO_ID,\n" //6
            + "ENCOUNTER_START_DATE,\n" //7
            + "ENCOUNTER_END_DATE,\n" //8
            + "NOTES,\n" //9
            + "STIPEND_GIVEN,\n" //10
            + "CREATED_BY,\n" //11
            + "CREATED_DATE,\n" //12
            + "ITINERARY_NEEDED,\n" //13
            + "CONSENT_NEEDED,\n" //14
            + "ADDENDUM_NEEDED,\n" //15
            + "FEMALE_CHILD_BEARING_AGE,\n" //16
            + "PREGNANT_OR_NURSING\n" //17
            + "  )\n"
            + "  VALUES\n"
            + "  (\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?,\n"
            + "?\n"
            + "  )";

    private static String enc_exam_type_insert = "INSERT\n"
            + "INTO\n"
            + "  ENCOUNTER_EXAM_TYPE\n"
            + "  (\n"
            + "    ENCOUNTER_EXAM_TYPE_ID,\n"//1
            + "    EXAM_TYPE_ID,\n"//2
            + "    ENCOUNTER_ID,\n"//3
            + "    CREATED_BY,\n"//4
            + "    CREATED_DATE,\n"//5
            + "    PERSONNEL_ID\n"//6
            + "  )\n"
            + "  VALUES\n"
            + "  (\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?,\n"
            + "    ?\n"
            + "  )";

    public static int insertSubjects(Connection db, List<Subject> subjects) throws SQLException {
        int insertCnt = 0;
        try (PreparedStatement stat = db.prepareStatement(subject_insert)) {
            for (Subject subject : subjects) {
                stat.setInt(1, subject.getSubject_id());
                stat.setString(2, subject.getAoip_id());
                stat.setString(3, subject.getFroedert_mrn());
                stat.setString(4, subject.getChw_id());
                stat.setString(5, subject.getClinical_trial_id());
                stat.setString(6, subject.getOther_id());
                stat.setString(7, subject.getFname());
                stat.setString(8, subject.getLname());
                stat.setString(9, subject.getGender());
                stat.setString(10, subject.getAddress1());
                stat.setString(11, subject.getAddress2());
                stat.setString(12, subject.getCity());
                stat.setString(13, subject.getState());
                stat.setString(14, subject.getZip());
                stat.setString(15, subject.getCountry());
                stat.setString(16, subject.getEmail());
                stat.setString(17, subject.getPhone_personal());
                stat.setString(18, subject.getPhone_work());
                stat.setString(19, null);
                if (subject.getReferral_type_id() == null) {
                    stat.setNull(20, Types.INTEGER);
                } else {
                    stat.setInt(20, subject.getReferral_type_id().getID());
                }
                stat.setString(21, subject.getCreated_by());
                stat.setDate(22, new Date(subject.getCreated_date().getTime()));
                stat.setString(23, null);
                stat.setDate(24, null);
                stat.setString(25, subject.getEye_color());
                stat.setDate(26, subject.getDob() == null ? null : new Date(subject.getDob().getTime()));
                if (subject.getNystagmusInt() == null) {
                    stat.setNull(27, Types.INTEGER);
                } else {
                    stat.setInt(27, subject.getNystagmusInt());
                }
                if (subject.getUnstable_fixationInt() == null) {
                    stat.setNull(28, Types.INTEGER);
                } else {
                    stat.setInt(28, subject.getUnstable_fixationInt());
                }
                if (subject.getDilate_safeInt() == null) {
                    stat.setNull(29, Types.INTEGER);
                } else {
                    stat.setInt(29, subject.getDilate_safeInt());
                }
                stat.setString(30, subject.getMi() == null || subject.getMi().isEmpty() ? null : subject.getMi().substring(0, 1));
                if (dm.getDiagnosisCode(subject.getDx_pri()) == null) {
                    stat.setNull(31, Types.INTEGER);
                } else {
                    stat.setInt(31, dm.getDiagnosisCode(subject.getDx_pri()));
                }
                if (dm.getDiagnosisCode(subject.getDx_sec()) == null) {
                    stat.setNull(32, Types.INTEGER);
                } else {
                    stat.setInt(32, dm.getDiagnosisCode(subject.getDx_sec()));
                }
                if (dm.getDiagnosisCode(subject.getDx_other()) == null) {
                    stat.setNull(33, Types.INTEGER);
                } else {
                    stat.setInt(33, dm.getDiagnosisCode(subject.getDx_other()));
                }
                if (subject.getPermission_recontactInt() == null) {
                    stat.setNull(34, Types.TINYINT);
                } else {
                    stat.setInt(34, subject.getPermission_recontactInt());
                }
                insertCnt += stat.executeUpdate();
            }
        }
        return insertCnt;
    }

    public static int insertEncounters(Connection db, List<Encounter> encs) throws SQLException {
        int insertCnt = 0;
        try (PreparedStatement stat = db.prepareStatement(encounter_insert)) {
            for (Encounter enc : encs) {
                stat.setInt(1, enc.getEncounter_id());
                stat.setInt(2, enc.getSubject_id());
                stat.setInt(3, enc.getEncounter_type_id());
                stat.setInt(4, enc.getEncounter_status_id());
                stat.setNull(5, Types.INTEGER);
                if (enc.getRef_pro_id() == null) {
                    stat.setNull(6, Types.INTEGER);
                } else {
                    stat.setInt(6, enc.getRef_pro_id());
                }
                stat.setDate(7, enc.getEncounter_start_date() == null ? null : new Date(enc.getEncounter_start_date().getTime()));
                stat.setDate(8, enc.getEncounter_end_date() == null ? null : new Date(enc.getEncounter_end_date().getTime()));
                stat.setString(9, enc.getNotes());
                if (enc.getStipend_given() == null) {
                    stat.setNull(10, Types.TINYINT);
                } else {
                    stat.setInt(10, enc.getStipend_given());
                }
                stat.setString(11, enc.getCreated_by());
                stat.setDate(12, new Date(enc.getCreated_date().getTime()));
                if (enc.getItinerary_needed() == null) {
                    stat.setNull(13, Types.TINYINT);
                } else {
                    stat.setInt(13, enc.getItinerary_needed());
                }
                if (enc.getConsent_needed() == null) {
                    stat.setNull(14, Types.TINYINT);
                } else {
                    stat.setInt(14, enc.getConsent_needed());
                }
                if (enc.getAddendum_needed() == null) {
                    stat.setNull(15, Types.TINYINT);
                } else {
                    stat.setInt(15, enc.getAddendum_needed());
                }
                if (enc.getFemale_child_bearing_age() == null) {
                    stat.setNull(16, Types.TINYINT);
                } else {
                    stat.setInt(16, enc.getFemale_child_bearing_age());
                }
                if (enc.getPregnant_or_nursing() == null) {
                    stat.setNull(17, Types.TINYINT);
                } else {
                    stat.setInt(17, enc.getPregnant_or_nursing());
                }
                insertCnt += stat.executeUpdate();
            }
        }
        return insertCnt;
    }

    public static int insertEncExamTypes(Connection db, List<Encounter> encs) throws SQLException {
        int insertCnt = 0;
        try (PreparedStatement stat = db.prepareStatement(enc_exam_type_insert)) {
            for (Encounter enc : encs) {
                for (EncounterExamType eet : enc.getExams()) {
                    stat.setInt(1, eet.getEncounter_exam_type_id());
                    stat.setInt(2, eet.getExam_type().getId());
                    stat.setInt(3, enc.getEncounter_id());
                    stat.setString(4, eet.getCreated_by());
                    stat.setDate(5, new Date(enc.getCreated_date().getTime()));
                    if (eet.getPersonnel_id() == null) {
                        stat.setNull(6, Types.INTEGER);
                    } else {
                        stat.setInt(6, eet.getPersonnel_id());
                    }
                    insertCnt += stat.executeUpdate();
                }
            }
        }
        return insertCnt;
    }

    public static int insertExamResults(Connection db, List<Encounter> encs) throws SQLException {
        AtomicInteger insertCnt = new AtomicInteger(0);

        //insert D15 exam results
        try (PreparedStatement stat = db.prepareStatement(D15ExamResult.INSERT_QUERY)) {
            encs.stream()
                    .flatMap(enc -> enc.getExams().stream())
                    .filter(ext -> ext.getExam_type() == ExamType.COLOR_D15)
                    .forEach(ext -> {
                        ext.getResults()
                        .stream()
                        .map(res -> (D15ExamResult) res)
                        .forEach(res -> {
                            try {
                                res.setD15InsertStatementFields(stat, ext);
                                insertCnt.addAndGet(stat.executeUpdate());
                            } catch (SQLException ex) {
                                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, "D15 exam result insert failed.", ex);
                                System.exit(2);
                            }
                        });
                    });

        }
        return insertCnt.get();
    }

    public static void clearPrevLoadedData(Connection db) throws SQLException {
        Statement stat = db.createStatement();
        stat.execute("delete from EXAM_RESULT where CREATED_BY = 'BWILK'");
        stat.execute("delete from ENCOUNTER_EXAM_TYPE where CREATED_BY = 'BWILK'");
        stat.execute("delete from ENCOUNTER where CREATED_BY = 'BWILK'");
        stat.execute("delete from SUBJECT where CREATED_BY = 'BWILK'");
        stat.execute("delete from DIAGNOSIS where CREATED_BY = 'BWILK'");
        stat.execute("delete from PROVIDER where CREATED_BY = 'BWILK'");
        stat.execute("delete from PERSONNEL where CREATED_BY = 'BWILK'");
    }
}
