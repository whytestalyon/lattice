/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io.dom.res;

import com.bwc.lat.io.dom.exam.EncounterExamType;
import com.bwc.lat.io.exc.Eye;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author Brandon M. Wilk {@literal <}wilkb777@gmail.com{@literal >}
 */
public class D15ExamResult extends ExamResult {

    public static final String INSERT_QUERY = "INSERT\n"
            + "INTO\n"
            + "  EXAM_RESULT\n"
            + "  (\n"
            + "    EXAM_RESULT_ID,\n"
            + "    ENCOUNTER_EXAM_TYPE_ID,\n"
            + "    CREATED_BY,\n"
            + "    CREATED_DATE,\n"
            + "    EYE,\n"
            + "    MOI_ANGLE,\n"
            + "    MOI_MAJOR_RADIUS,\n"
            + "    MOI_MINOR_RADIUS,\n"
            + "    MOI_TOTAL_ERROR_SCORE,\n"
            + "    MOI_SELECTIVITY_INDEX,\n"
            + "    MOI_CONFUSION_INDEX,\n"
            + "    MOI_COLOR_DISCRIMINATION,\n"
            + "    MOI_NOTES,\n"
            + "    D15_TRIAL_NUMBER,\n"
            + "    D15_SAT_DESAT_FLAG\n"
            + "  )\n"
            + "  VALUES\n"
            + "  (\n"
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

    private final Double MOI_Angle, MOI_Major_Radius, MOI_Minor_Radius, MOI_Total_Error_Score, MOI_Selectivity_Index, MOI_Confusion_Index;
    private final String MOI_Color_Discriminaton, MOI_Notes;
    private final boolean saturated;
    private final int trial_num;
    private final Eye eye;

    public D15ExamResult(Double MOI_Angle, Double MOI_Major_Radius, Double MOI_Minor_Radius, Double MOI_Total_Error_Score, Double MOI_Selectivity_Index, Double MOI_Confusion_Index, String MOI_Color_Discriminaton, String MOI_Notes, boolean saturated, int trial_num, Eye eye) {
        this.MOI_Angle = MOI_Angle;
        this.MOI_Major_Radius = MOI_Major_Radius;
        this.MOI_Minor_Radius = MOI_Minor_Radius;
        this.MOI_Total_Error_Score = MOI_Total_Error_Score;
        this.MOI_Selectivity_Index = MOI_Selectivity_Index;
        this.MOI_Confusion_Index = MOI_Confusion_Index;
        this.MOI_Color_Discriminaton = MOI_Color_Discriminaton.equals("-") ? null : MOI_Color_Discriminaton;
        this.MOI_Notes = MOI_Notes.equals("-") ? null : MOI_Notes;
        this.saturated = saturated;
        this.trial_num = trial_num;
        this.eye = eye;
    }

    public Eye getEye() {
        return eye;
    }

    public boolean isSaturated() {
        return saturated;
    }

    public int getTrial_num() {
        return trial_num;
    }

    public Double getMOI_Angle() {
        return MOI_Angle;
    }

    public Double getMOI_Major_Radius() {
        return MOI_Major_Radius;
    }

    public Double getMOI_Minor_Radius() {
        return MOI_Minor_Radius;
    }

    public Double getMOI_Total_Error_Score() {
        return MOI_Total_Error_Score;
    }

    public Double getMOI_Selectivity_Index() {
        return MOI_Selectivity_Index;
    }

    public Double getMOI_Confusion_Index() {
        return MOI_Confusion_Index;
    }

    public String getMOI_Color_Discriminaton() {
        return MOI_Color_Discriminaton;
    }

    public String getMOI_Notes() {
        return MOI_Notes;
    }

    @Override
    public String toString() {
        return "D15ExamResult{" + "MOI_Angle=" + MOI_Angle + ", MOI_Major_Radius=" + MOI_Major_Radius + ", MOI_Minor_Radius=" + MOI_Minor_Radius + ", MOI_Total_Error_Score=" + MOI_Total_Error_Score + ", MOI_Selectivity_Index=" + MOI_Selectivity_Index + ", MOI_Confusion_Index=" + MOI_Confusion_Index + ", MOI_Color_Discriminaton=" + MOI_Color_Discriminaton + ", MOI_Notes=" + MOI_Notes + ", saturated=" + saturated + ", trial_num=" + trial_num + ", eye=" + eye + '}';
    }

    public void setD15InsertStatementFields(PreparedStatement stat, EncounterExamType examType) throws SQLException {
        stat.setInt(1, getExam_result_id());
        stat.setInt(2, examType.getEncounter_exam_type_id());
        stat.setString(3, getCreated_by());
        stat.setDate(4, new Date(getCreated_date().getTime()));
        switch (eye) {
            case OS:
                stat.setString(5, "le");
                break;
            case OD:
                stat.setString(5, "re");
                break;
            default:
                stat.setString(5, "be");
                break;
        }
        if (MOI_Angle == null) {
            stat.setNull(6, Types.FLOAT);
        } else {
            stat.setDouble(6, MOI_Angle);
        }
        if (MOI_Major_Radius == null) {
            stat.setNull(7, Types.FLOAT);
        } else {
            stat.setDouble(7, MOI_Major_Radius);
        }
        if (MOI_Minor_Radius == null) {
            stat.setNull(8, Types.FLOAT);
        } else {
            stat.setDouble(8, MOI_Minor_Radius);
        }
        if (MOI_Total_Error_Score == null) {
            stat.setNull(9, Types.FLOAT);
        } else {
            stat.setDouble(9, MOI_Total_Error_Score);
        }
        if (MOI_Selectivity_Index == null) {
            stat.setNull(10, Types.FLOAT);
        } else {
            stat.setDouble(10, MOI_Selectivity_Index);
        }
        if (MOI_Confusion_Index == null) {
            stat.setNull(11, Types.FLOAT);
        } else {
            stat.setDouble(11, MOI_Confusion_Index);
        }
        stat.setString(12, MOI_Color_Discriminaton);
        stat.setString(13, MOI_Notes);
        stat.setInt(14, trial_num);
        stat.setString(15, saturated ? "SAT" : "DESAT");
    }
}
