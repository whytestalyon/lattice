/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io;

import com.bwc.lat.io.dom.Bankq;
import com.bwc.lat.io.exc.SubjectColumn;
import com.bwc.lat.io.dom.Encounter;
import com.bwc.lat.io.dom.ProviderMap;
import com.bwc.lat.io.dom.Referal;
import com.bwc.lat.io.dom.Subject;
import com.bwc.lat.io.dom.exam.ConsentingExam;
import com.bwc.lat.io.dom.exam.EncounterExamType;
import com.bwc.lat.io.dom.exam.ExamType;
import com.bwc.lat.io.dom.exam.OccularHealthExam;
import com.bwc.lat.io.dom.res.AOExamResult;
import com.bwc.lat.io.dom.res.AOHRRExamResult;
import com.bwc.lat.io.dom.res.AmslerGridExamResult;
import com.bwc.lat.io.dom.res.AutoRefractorExamResult;
import com.bwc.lat.io.dom.res.ContrastSensitivityExamResult;
import com.bwc.lat.io.dom.res.D15ExamResult;
import com.bwc.lat.io.dom.res.DilationExamResult;
import com.bwc.lat.io.dom.res.FundusVisucamExamResult;
import com.bwc.lat.io.dom.res.Hue100ExamResult;
import com.bwc.lat.io.dom.res.IOLMasterExamResult;
import com.bwc.lat.io.dom.res.IshiharaExamResult;
import com.bwc.lat.io.dom.res.MDVisitExamResult;
import com.bwc.lat.io.dom.res.NeitzExamResult;
import com.bwc.lat.io.dom.res.OCTBioptigenExamResult;
import com.bwc.lat.io.dom.res.OCTCirrusDilatedExamResult;
import com.bwc.lat.io.dom.res.OCTCirrusUndilatedExamResult;
import com.bwc.lat.io.dom.res.OCTOptovueExamResult;
import com.bwc.lat.io.dom.res.OptosExamResult;
import com.bwc.lat.io.dom.res.PedigreeExamResult;
import com.bwc.lat.io.dom.res.RayleighExamResult;
import com.bwc.lat.io.dom.res.SkinPigment;
import com.bwc.lat.io.dom.res.SpectrailsExamResult;
import com.bwc.lat.io.dom.res.SteroExamResult;
import com.bwc.lat.io.dom.res.VisualAccuityExamResult;
import com.bwc.lat.io.exc.EncounterColumn;
import com.bwc.lat.io.exc.ExamColumn;
import com.bwc.lat.io.exc.Eye;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author melissadiederichs
 */
public class ExcelParser {

    private final int dataRowStart = 4;
    private final Workbook excelWorkbook;
    private final Sheet dataSheet;
    FormulaEvaluator evaluator;
    private ProviderMap pm = ProviderMap.getInstance();

    public ExcelParser(File excelFile) throws IOException, InvalidFormatException {
        excelWorkbook = WorkbookFactory.create(excelFile);
        dataSheet = excelWorkbook.getSheetAt(1);
        evaluator = excelWorkbook.getCreationHelper().createFormulaEvaluator();
    }

    public List<Encounter> getEncounters(List<Subject> subjects) {
        List<Encounter> encounters = new LinkedList<>();
        //create ID lookup map to map encounters to subjects
        Map<String, Integer> aoipIdToSubjectIdMap = subjects.stream()
                .collect(Collectors.toMap(Subject::getAoip_id, Subject::getSubject_id));
        //iterate data in excel worksheet
        for (Row row : dataSheet) {
            if (row.getRowNum() < dataRowStart) {
                continue;
            }
            //get the AOIP ID from the initials column and ID column
            Cell initialsCell = row.getCell(SubjectColumn.INITIALS.getColIndex());
            String initials = initialsCell.getStringCellValue().trim();
            if (initials.isEmpty() || initials.contains("-")) {
                initials = "OI";
            }
            Cell idCell = row.getCell(SubjectColumn.ID.getColIndex());
            DataFormatter formatter = new DataFormatter();
            formatter.addFormat("000#", new DecimalFormat("#0000"));
            String formattedCellValue = formatter.formatCellValue(idCell);
            if (formattedCellValue.isEmpty() || formattedCellValue.equals("0000")) {
                //end of data reached
                break;
            }
            String aoipId = initials + "_" + formattedCellValue;

            //create new encounter
            Encounter enc = null;
            try {
                enc = new Encounter(aoipIdToSubjectIdMap.get(aoipId), 13);
            } catch (NullPointerException np) {
                System.out.println("NP: " + aoipId);
                System.exit(1);
            }
            encounters.add(enc);

            //populate the new encounter
            //set encounter ID
            enc.setEncounter_status_id(4);

            //set start and end date
            Cell visDateCell = row.getCell(SubjectColumn.VISIT_DATE.getColIndex());
            if (visDateCell.getCellType() != Cell.CELL_TYPE_STRING) {
                enc.setEncounter_start_date(visDateCell.getDateCellValue());
                enc.setEncounter_end_date(visDateCell.getDateCellValue());
            }

            //set notes
            String staff = null;
            try {
                staff = row.getCell(EncounterColumn.EXAMS_STAFF.getColIndex()).getStringCellValue();
            } catch (NullPointerException np) {
                System.out.println("NP: " + aoipId + " rownum: " + row.getRowNum());
                System.exit(1);
            }

            String notes = row.getCell(EncounterColumn.NOTES.getColIndex()).getStringCellValue();
            if (staff != null && !staff.isEmpty()) {
                staff = "\"Exam Staff=" + staff + "\"";
            }
            if (notes == null) {
                notes = staff;
            } else {
                notes = staff + " \n " + notes;
            }
            if (notes != null && !notes.isEmpty()) {
                enc.setNotes(notes);
            }

            //set stippend given
            Cell stippendCell = row.getCell(EncounterColumn.STIPEND_GIVEN.getColIndex());
            switch (stippendCell.getStringCellValue().trim()) {
                case "Yes":
                    enc.setStipend_given(1);
                    break;
                case "No":
                case "Declined":
                    enc.setStipend_given(0);
                    break;
                default:
                    break;
            }

            //set addendum given
            Cell addendumCell = row.getCell(EncounterColumn.ADDENDUM.getColIndex());
            switch (addendumCell.getStringCellValue().trim()) {
                case "Yes":
                    enc.setAddendum_needed(1);
                    break;
                case "No":
                case "Declined":
                    enc.setAddendum_needed(0);
                    break;
                default:
                    break;
            }

            //set female of child bearing age flag
            Cell fcbCell = row.getCell(EncounterColumn.FEMALE_CHILD_BEARING_AGE.getColIndex());
            switch (fcbCell.getStringCellValue().trim()) {
                case "Yes":
                    enc.setFemale_child_bearing_age(1);
                    break;
                case "No":
                    enc.setFemale_child_bearing_age(0);
                    break;
                default:
                    break;
            }

            //set pregnant flag
            Cell pregCell = row.getCell(EncounterColumn.PREGNANT_OR_NURSING.getColIndex());
            switch (pregCell.getStringCellValue().trim()) {
                case "Yes":
                    enc.setPregnant_or_nursing(1);
                    break;
                case "No":
                    enc.setPregnant_or_nursing(0);
                    break;
                default:
                    break;
            }

            //set itinerary flag
            Cell itinCell = row.getCell(EncounterColumn.ITINERARY.getColIndex());
            switch (itinCell.getStringCellValue().trim()) {
                case "Yes":
                    enc.setItinerary_needed(1);
                    break;
                case "No":
                    enc.setItinerary_needed(0);
                    break;
                default:
                    break;
            }

            //set provider
            String provider = row.getCell(EncounterColumn.REFERING_MD.getColIndex()).getStringCellValue().trim();
            enc.setRef_pro_id(pm.getProviderCode(provider));

            //set ethnicity
            enc.getBankq().setEthnicity(row.getCell(EncounterColumn.ETHNICITY.getColIndex()).getStringCellValue().trim());

            //set race
            String race = row.getCell(EncounterColumn.RACE.getColIndex()).getStringCellValue();
            if (race != null) {
                Bankq bq = enc.getBankq();
                switch (race) {
                    case "indian":
                        bq.setRace_indian("true");
                        break;
                    case "asian":
                        bq.setRace_asian("true");
                        break;
                    case "hawaiian":
                        bq.setRace_hawaiian("true");
                        break;
                    case "black":
                        bq.setRace_black("true");
                        break;
                    case "white":
                        bq.setRace_white("true");
                        break;
                    case "unreported":
                        bq.setRace_unreported("true");
                        break;
                    default:
                        break;
                }
            }

            addExamsToEncounter(enc, row);
        }

        //find earliest encounter to mark as first encounter for each subject
        encounters.stream()
                .collect(Collectors.groupingBy(Encounter::getSubject_id))
                .values()
                .forEach(ecns -> {
                    if (ecns.size() == 1) {
                        ecns.get(0).setEncounter_type_id(1);
                    } else {
                        ecns.stream()
                        .sorted((s1, s2) -> s1.getEncounter_start_date().compareTo(s2.getEncounter_start_date()))
                        .findFirst()
                        .get()
                        .setEncounter_type_id(1);
                    }
                });

        return encounters;
    }

    public List<Subject> getSubjects() {
        List<Subject> subjects = new LinkedList<>();
        OUT:
        for (Row row : dataSheet) {
            if (row.getRowNum() < dataRowStart) {
                continue;
            }
            Subject subject = new Subject();
            subjects.add(subject);
            for (Cell cell : row) {
                switch (SubjectColumn.getColByIndex(cell.getColumnIndex())) {
                    case INITIALS:
                        String initials = cell.getStringCellValue().trim();
                        if (initials.isEmpty() || initials.contains("-")) {
                            initials = "OI";
                        }
                        if (subject.getAoip_id() == null) {
                            subject.setAoip_id(initials);
                        } else {
                            subject.setAoip_id(initials + "_" + subject.getAoip_id());
                        }
                        break;
                    case ID:
                        DataFormatter formatter = new DataFormatter();
                        formatter.addFormat("000#", new DecimalFormat("#0000"));
                        String formattedCellValue = formatter.formatCellValue(cell);
                        if (formattedCellValue.isEmpty() || formattedCellValue.equals("0000")) {
                            //end of data reached
                            System.out.println("End of data reached at row " + (cell.getRowIndex() + 1));
                            subjects.remove(subjects.size() - 1);
                            break OUT;
                        }
                        if (subject.getAoip_id() == null) {
                            subject.setAoip_id(formattedCellValue);
                        } else {
                            subject.setAoip_id(subject.getAoip_id() + "_" + formattedCellValue);
                        }
                        break;
                    case FIRST_NAME:
                        subject.setFname(cell.getStringCellValue().trim());
                        break;
                    case MIDDLE_INITIAL:
                        subject.setMi(cell.getStringCellValue().trim());
                        break;
                    case LAST_NAME:
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            if (cell.getNumericCellValue() >= 1D) {
                                formatter = new DataFormatter();
                                formatter.addFormat("0000", new DecimalFormat("0000"));
                                formattedCellValue = formatter.formatCellValue(cell);
                                subject.setLname(formattedCellValue);
                            }
                        } else {
                            subject.setLname(cell.getStringCellValue().trim());
                        }
                        break;
                    case DOB:
                        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            break;
                        } else {
                            subject.setDob(cell.getDateCellValue());
                        }
                        break;
                    case VISIT_DATE:
                        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            break;
                        } else {
                            subject.setVisit_date(cell.getDateCellValue());
                        }
                        break;
                    case GENDER:
                        subject.setGender(cell.getStringCellValue().trim());
                        break;
                    case FROEDERT_MRN:
                        if (cell.getNumericCellValue() >= 1D) {
                            formatter = new DataFormatter();
                            formatter.addFormat("00000000", new DecimalFormat("00000000"));
                            formattedCellValue = formatter.formatCellValue(cell);
                            subject.setFroedert_mrn(formattedCellValue);
                        }
                        break;
                    case CHW_ID:
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            if (cell.getNumericCellValue() >= 1D) {
                                formatter = new DataFormatter();
                                formatter.addFormat("00000000", new DecimalFormat("00000000"));
                                formattedCellValue = formatter.formatCellValue(cell);
                                subject.setChw_id(formattedCellValue);
                            }
                        } else {
                            if (!cell.getStringCellValue().trim().isEmpty()) {
                                subject.setChw_id(cell.getStringCellValue().trim());
                            }
                        }
                    case CLINICAL_TRIAL_ID:
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            if (cell.getNumericCellValue() >= 1D) {
                                formatter = new DataFormatter();
                                DecimalFormatSymbols clinIDSymbols = new DecimalFormatSymbols();
                                clinIDSymbols.setGroupingSeparator('-');
                                DecimalFormat clinIdFormat = new DecimalFormat("0000,000", clinIDSymbols);
                                formatter.addFormat("0000-000", clinIdFormat);
                                formattedCellValue = formatter.formatCellValue(cell);
                                subject.setClinical_trial_id(formattedCellValue);
                            }
                        } else {
                            subject.setClinical_trial_id(cell.getStringCellValue().trim());
                        }
                        break;
                    case OTHER_ID:
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            if (cell.getNumericCellValue() >= 1D) {
                                subject.setOther_id(String.valueOf(Math.round(cell.getNumericCellValue())));
                            }
                        } else {
                            subject.setOther_id(cell.getStringCellValue().trim());
                        }
                        break;
                    case DX_PRI:
                        subject.setDx_pri(cell.getStringCellValue().trim());
                        break;
                    case DX_SEC:
                        subject.setDx_sec(cell.getStringCellValue().trim());
                        break;
                    case DX_OTHER:
                        subject.setDx_other(cell.getStringCellValue().trim());
                        break;
                    case DILATE_SAFE:
                        subject.setDilate_safe(cell.getStringCellValue().trim());
                        break;
                    case NYSTAGMUS:
                        subject.setNystagmus(cell.getStringCellValue().trim());
                        break;
                    case UNSTABLE_FIXATION:
                        subject.setUnstable_fixation(cell.getStringCellValue().trim());
                        break;
                    case EYE_COLOR:
                        subject.setEye_color(cell.getStringCellValue().trim());
                        break;
                    case REFERRAL_TYPE:
                        subject.setReferral_type_id(Referal.getReferal(cell.getStringCellValue().trim().toLowerCase()));
                        break;
                    case ADDRESS1:
                        subject.setAddress1(cell.getStringCellValue().trim());
                        break;
                    case ADDRESS2:
                        subject.setAddress2(cell.getStringCellValue().trim());
                        break;
                    case CITY:
                        subject.setCity(cell.getStringCellValue().trim());
                        break;
                    case STATE:
                        subject.setState(cell.getStringCellValue().trim());
                        break;
                    case ZIP:
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            if (cell.getNumericCellValue() >= 1D) {
                                formatter = new DataFormatter();
                                formatter.addFormat("00000", new DecimalFormat("00000"));
                                formattedCellValue = formatter.formatCellValue(cell);
                                subject.setZip(formattedCellValue);
                            }
                        } else {
                            subject.setZip(cell.getStringCellValue().trim());
                        }
                        break;
                    case COUNTRY:
                        subject.setCountry(cell.getStringCellValue().trim());
                        break;
                    case EMAIL:
                        subject.setEmail(cell.getStringCellValue().trim());
                        break;
                    case PHONE_PERSONAL:
                        subject.setPhone_personal(cell.getStringCellValue().trim());
                        break;
                    case PHONE_WORK:
                        subject.setPhone_work(cell.getStringCellValue().trim());
                        break;
                    case PERMISSION_RECONTACT:
                        switch (cell.getStringCellValue().trim()) {
                            case "Yes":
                                subject.setPermission_recontact("true");
                                break;
                            case "No":
                                subject.setPermission_recontact("false");
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
            if (row.getRowNum() == 3266) {
                System.out.println("3267 data: " + subject.getAoip_id());
            }
        }

        //merge the subjects into a single subject record per AOIP ID, then return final list
        return mergeSubjects(subjects);
    }

    private List<Subject> mergeSubjects(List<Subject> rawSubjects) {
        System.out.println("Merging " + rawSubjects.size() + " subject record...");
        Map<String, List<Subject>> idGroupedSubjects = rawSubjects.stream().collect(Collectors.groupingBy(Subject::getAoip_id));
        System.out.println("Grouped into " + idGroupedSubjects.size() + " individual subjects.");
        //when the same field has multiple values for it simply keep value from most recent visit
        return idGroupedSubjects.values()
                .stream()
                .map(subjects -> {
                    Subject identSub = new Subject();
                    identSub.setVisit_date(new Date(0));
                    return subjects.stream()
                    .reduce(identSub, (s1, s2) -> {
                        Subject retSub;
                        if (s1.getVisit_date() == null || (s2.getVisit_date() != null && s1.getVisit_date().compareTo(s2.getVisit_date()) < 0)) {
                            //s1 comes before s2, use s1 as base and merge s2 fields on top of s1 fields
                            retSub = s1.merge(s2);
                        } else {
                            //s2 comes before s1, use s2 as base and merge s1 fields on top of s2 fields
                            retSub = s2.merge(s1);
                        }
                        return retSub;
                    });
                })
                .filter(s -> s.getAoip_id() != null)
                .collect(Collectors.toList());
    }

    private void addExamsToEncounter(Encounter enc, Row row) {
        //check for occular health exam, add if "yes" is in column add it to list of exams to return
        if (row.getCell(ExamColumn.OCCULAR_HEALTH_QUESTIONAIRE.getColIndex()).getStringCellValue().trim().equals("Yes")) {
            enc.addExam(new OccularHealthExam());
        }

        //check for consenting perssonel being list, if so add as an exam
        String consenting = row.getCell(ExamColumn.STAFF_CONSENTING.getColIndex()).getStringCellValue().trim();
        if (!consenting.isEmpty() && !consenting.equals("-")) {
            ConsentingExam exam = new ConsentingExam();
            exam.setPersonnel_id(consenting);
            enc.addExam(exam);
        }

        //check for the Neitz exam
        String dat = row.getCell(ExamColumn.NEITZ.getColIndex()).getStringCellValue().trim();
        if (!dat.isEmpty() && !dat.equals("-")) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_NEITZ);
            exam.addResult(new NeitzExamResult(dat));
            enc.addExam(exam);
        }

        //check for AOHRR exam
        Cell[] cells = new Cell[]{
            row.getCell(ExamColumn.AOHRR_TYPE.getColIndex()),
            row.getCell(ExamColumn.AOHRR_DVALUE.getColIndex())
        };
        String[] data = getStringDataFromCells(cells);
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_AOHRR);
            exam.addResult(new AOHRRExamResult(data[0], data[1]));
            enc.addExam(exam);
        }

        //check for D15 OD saturated trial 1
        data = new String[]{
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_1_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_1_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_1_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_1_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_1_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_1_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_1_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_1_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    true,
                    1,
                    Eye.OD
            ));
            enc.addExam(exam);
        }

        //check for D15 OD saturated trial 2
        data = new String[]{
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_2_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_2_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_2_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_2_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_2_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_2_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_2_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OD_TRIAL_2_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    true,
                    2,
                    Eye.OD
            ));
            enc.addExam(exam);
        }

        //check for D15 OS saturated trial 1
        data = new String[]{
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_1_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_1_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_1_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_1_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_1_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_1_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_1_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_1_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    true,
                    1,
                    Eye.OS
            ));
            enc.addExam(exam);
        }

        //check for D15 OS saturated trial 2
        data = new String[]{
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_2_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_2_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_2_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_2_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_2_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_2_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_2_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_OS_TRIAL_2_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    true,
                    2,
                    Eye.OS
            ));
            enc.addExam(exam);
        }

        //check for D15 Binocular saturated trial 1
        data = new String[]{
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_1_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_1_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_1_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_1_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_1_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_1_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_1_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_1_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    true,
                    1,
                    Eye.BINOCULAR
            ));
            enc.addExam(exam);
        }

        //check for D15 Binocular saturated trial 2
        data = new String[]{
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_2_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_2_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_2_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_2_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_2_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_2_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_2_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_SATURATED_BINOCULAR_TRIAL_2_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    true,
                    2,
                    Eye.BINOCULAR
            ));
            enc.addExam(exam);
        }

        //check for D15 OD desaturated trial 1
        data = new String[]{
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_1_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_1_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_1_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_1_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_1_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_1_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_1_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_1_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    false,
                    1,
                    Eye.OD
            ));
            enc.addExam(exam);
        }

        //check for D15 OD desaturated trial 2
        data = new String[]{
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_2_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_2_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_2_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_2_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_2_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_2_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_2_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OD_TRIAL_2_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    false,
                    2,
                    Eye.OD
            ));
            enc.addExam(exam);
        }

        //check for D15 OS desaturated trial 1
        data = new String[]{
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_1_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_1_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_1_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_1_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_1_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_1_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_1_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_1_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    false,
                    1,
                    Eye.OS
            ));
            enc.addExam(exam);
        }

        //check for D15 OS desaturated trial 2
        data = new String[]{
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_2_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_2_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_2_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_2_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_2_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_2_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_2_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_OS_TRIAL_2_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    false,
                    2,
                    Eye.OS
            ));
            enc.addExam(exam);
        }

        //check for D15 Binocular desaturated trial 1
        data = new String[]{
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_1_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    false,
                    1,
                    Eye.BINOCULAR
            ));
            enc.addExam(exam);
        }

        //check for D15 Binocular desaturated trial 2
        data = new String[]{
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_COLOR_DISCRIMINATON.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.D15_DESATURATED_BINOCULAR_TRIAL_2_MOI_CONFUSION_INDEX.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_D15);
            exam.addResult(new D15ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    false,
                    2,
                    Eye.BINOCULAR
            ));
            enc.addExam(exam);
        }

        //check Rayleigh OD exam
        cells = new Cell[]{
            row.getCell(ExamColumn.RAYLEIGH_OD_RANGE_START.getColIndex()),
            row.getCell(ExamColumn.RAYLEIGH_OD_RANGE_END.getColIndex()),
            row.getCell(ExamColumn.RAYLEIGH_OD_MID_POINT.getColIndex()),
            row.getCell(ExamColumn.RAYLEIGH_OD_YELLOW_1.getColIndex()),
            row.getCell(ExamColumn.RAYLEIGH_OD_YELLOW_2.getColIndex())
        };
        if (Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_RAYLEIGH);
            exam.addResult(new RayleighExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? (int) Math.round(cells[0].getNumericCellValue()) : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? (int) Math.round(cells[1].getNumericCellValue()) : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC || cells[2].getCellType() == Cell.CELL_TYPE_FORMULA ? (int) Math.round(cells[2].getNumericCellValue()) : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? (int) Math.round(cells[3].getNumericCellValue()) : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? (int) Math.round(cells[4].getNumericCellValue()) : null,
                    Eye.OD
            ));

            enc.addExam(exam);
        }

        //check Rayleigh OS exam
        cells = new Cell[]{
            row.getCell(ExamColumn.RAYLEIGH_OS_RANGE_START.getColIndex()),
            row.getCell(ExamColumn.RAYLEIGH_OS_RANGE_END.getColIndex()),
            row.getCell(ExamColumn.RAYLEIGH_OS_MID_POINT.getColIndex()),
            row.getCell(ExamColumn.RAYLEIGH_OS_YELLOW_1.getColIndex()),
            row.getCell(ExamColumn.RAYLEIGH_OS_YELLOW_2.getColIndex())
        };
        if (Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_RAYLEIGH);
            exam.addResult(new RayleighExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? (int) Math.round(cells[0].getNumericCellValue()) : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? (int) Math.round(cells[1].getNumericCellValue()) : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC || cells[2].getCellType() == Cell.CELL_TYPE_FORMULA ? (int) Math.round(cells[2].getNumericCellValue()) : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? (int) Math.round(cells[3].getNumericCellValue()) : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? (int) Math.round(cells[4].getNumericCellValue()) : null,
                    Eye.OS
            ));
            enc.addExam(exam);
        }

        //check Hue 100 OD exam 
        data = new String[]{
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OD_CLASSICAL_NOTES.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OD_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OD_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OD_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OD_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OD_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OD_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OD_MOI_CONFUSION_INDEX.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OD_CLASSICAL_TOTAL_ERROR_SCORE.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_100HUE);
            exam.addResult(new Hue100ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    cells[6].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[6].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    Eye.OD
            ));
            enc.addExam(exam);
        }

        //check Hue 100 OS exam 
        data = new String[]{
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OS_CLASSICAL_NOTES.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OS_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OS_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OS_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OS_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OS_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OS_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OS_MOI_CONFUSION_INDEX.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_OS_CLASSICAL_TOTAL_ERROR_SCORE.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_100HUE);
            exam.addResult(new Hue100ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    cells[6].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[6].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    Eye.OS
            ));
            enc.addExam(exam);
        }

        //check Hue 100 Binocular exam 
        data = new String[]{
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_BINOCULAR_CLASSICAL_NOTES.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_BINOCULAR_MOI_NOTES.getColIndex()).getStringCellValue().trim()
        };
        cells = new Cell[]{
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_BINOCULAR_MOI_ANGLE.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_BINOCULAR_MOI_MAJOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_BINOCULAR_MOI_MINOR_RADIUS.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_BINOCULAR_MOI_TOTAL_ERROR_SCORE.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_BINOCULAR_MOI_SELECTIVITY_INDEX.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_BINOCULAR_MOI_CONFUSION_INDEX.getColIndex()),
            row.getCell(ExamColumn.ONE_HUNDRED_HUE_BINOCULAR_CLASSICAL_TOTAL_ERROR_SCORE.getColIndex())
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))
                || Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_100HUE);
            exam.addResult(new Hue100ExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    cells[6].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[6].getNumericCellValue() : null,
                    data[0],
                    data[1],
                    Eye.BINOCULAR
            ));
            enc.addExam(exam);
        }

        //check Ishihara 24 exam 
        data = new String[]{
            row.getCell(ExamColumn.ISHIHARA_24_CORRECT_ANSWERS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.ISHIHARA_24_TYPE.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_ISHIHARA);
            exam.addResult(new IshiharaExamResult(24, data[0], data[1]));
            enc.addExam(exam);
        }

        //check Ishihara 38 exam 
        data = new String[]{
            row.getCell(ExamColumn.ISHIHARA_38_CORRECT_ANSWERS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.ISHIHARA_38_TYPE.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.COLOR_ISHIHARA);
            exam.addResult(new IshiharaExamResult(38, data[0], data[1]));
            enc.addExam(exam);
        }

        //check CAD Test exam
        //TBD, no definition in results table (yet)
        //check dilation exam
        cells = new Cell[]{
            row.getCell(ExamColumn.DILATION_PHENYLPHRINE_OD.getColIndex()),
            row.getCell(ExamColumn.DILATION_PHENYLPHRINE_OS.getColIndex()),
            row.getCell(ExamColumn.DILATION_TROPICAMIDE_OD.getColIndex()),
            row.getCell(ExamColumn.DILATION_TROPICAMIDE_OS.getColIndex())
        };
        data = getStringDataFromCells(cells);
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.DILATION);
            exam.addResult(new DilationExamResult((!data[0].isEmpty() && !data[0].equals("-")) || (!data[1].isEmpty() && !data[1].equals("-")), (!data[2].isEmpty() && !data[2].equals("-")) || (!data[3].isEmpty() && !data[3].equals("-"))));
            enc.addExam(exam);
        }

        //check PERIMETRY, Crosshair, disease, fixation, HVF all exam
        //TBD mapping from excel sheet to database table not clear
        //check for the best corrected visual accuity tests (BCVA)
        cells = new Cell[]{
            row.getCell(ExamColumn.BCVA_ETDRS_OD_SPHERE.getColIndex()),
            row.getCell(ExamColumn.BCVA_ETDRS_OS_SPHERE.getColIndex()),
            row.getCell(ExamColumn.BCVA_ETDRS_OD_CYLINDER.getColIndex()),
            row.getCell(ExamColumn.BCVA_ETDRS_OS_CYLINDER.getColIndex()),
            row.getCell(ExamColumn.BCVA_ETDRS_OD_AXIS.getColIndex()),
            row.getCell(ExamColumn.BCVA_ETDRS_OS_AXIS.getColIndex()),
            row.getCell(ExamColumn.BCVA_ETDRS_OD_VA.getColIndex()),
            row.getCell(ExamColumn.BCVA_ETDRS_OS_VA.getColIndex()),
            row.getCell(ExamColumn.BCVA_ETDRS_BINOCULAR_VA.getColIndex())
        };
        data = getStringDataFromCells(cells);
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-") && !field.equals("No"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.VISUAL_ACUITY);
            exam.addResult(new VisualAccuityExamResult(
                    "bcva",
                    "etdrs",
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5],
                    data[6],
                    data[7],
                    data[8])
            );
            enc.addExam(exam);
        }

        //check IOL Master exam
        cells = new Cell[]{
            row.getCell(ExamColumn.IOL_MASTER_OD_K1.getColIndex()),
            row.getCell(ExamColumn.IOL_MASTER_OD_K2.getColIndex()),
            row.getCell(ExamColumn.IOL_MASTER_OS_K1.getColIndex()),
            row.getCell(ExamColumn.IOL_MASTER_OS_K2.getColIndex()),
            row.getCell(ExamColumn.IOL_MASTER_OD_AL.getColIndex()),
            row.getCell(ExamColumn.IOL_MASTER_OS_AL.getColIndex()),
            row.getCell(ExamColumn.IOL_MASTER_OD_ACD.getColIndex()),
            row.getCell(ExamColumn.IOL_MASTER_OS_ACD.getColIndex()),};
        if (Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.IOL_MASTER);
            exam.addResult(new IOLMasterExamResult(
                    cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null,
                    cells[1].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[1].getNumericCellValue() : null,
                    cells[2].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[2].getNumericCellValue() : null,
                    cells[3].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[3].getNumericCellValue() : null,
                    cells[4].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[4].getNumericCellValue() : null,
                    cells[5].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[5].getNumericCellValue() : null,
                    cells[6].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[6].getNumericCellValue() : null,
                    cells[7].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[7].getNumericCellValue() : null
            ));
            enc.addExam(exam);
        }

        //check skin pigment exam
        cells = new Cell[]{
            row.getCell(ExamColumn.SKIN_PIGMENT.getColIndex())
        };
        data = getStringDataFromCells(cells);
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.SKIN_PIGMENT);
            exam.addResult(new SkinPigment(data[0]));
            enc.addExam(exam);
        }

        //Amsler exam
        //check skin pigment exam
        data = new String[]{
            row.getCell(ExamColumn.AMSLER_OD_RESULT.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AMSLER_OS_RESULT.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AMSLER_GRID);
            exam.addResult(new AmslerGridExamResult(data[0], data[1]));
            enc.addExam(exam);
        }

        //stereo exam
        //check skin pigment exam
        data = new String[]{
            row.getCell(ExamColumn.STEREO.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.STEREO_VISION);
            exam.addResult(new SteroExamResult(data[0]));
            enc.addExam(exam);
        }

        //Contrast sensitivity
        cells = new Cell[]{
            row.getCell(ExamColumn.CONTRAST_SENSITIVITY.getColIndex())
        };
        if (Arrays.stream(cells).anyMatch(field -> field.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
            EncounterExamType exam = new EncounterExamType(ExamType.CONTRAST_SENSITIVITY);
            exam.addResult(new ContrastSensitivityExamResult(cells[0].getCellType() == Cell.CELL_TYPE_NUMERIC ? cells[0].getNumericCellValue() : null));
            enc.addExam(exam);
        }

        //Autorefractor
        cells = new Cell[]{
            row.getCell(ExamColumn.AUTO_REFRACTOR_OD_SPHERE.getColIndex()),
            row.getCell(ExamColumn.AUTO_REFRACTOR_OD_CYLINDER.getColIndex()),
            row.getCell(ExamColumn.AUTO_REFRACTOR_OD_AXIS.getColIndex()),
            row.getCell(ExamColumn.AUTO_REFRACTOR_OS_SPHERE.getColIndex()),
            row.getCell(ExamColumn.AUTO_REFRACTOR_OS_CYLINDER.getColIndex()),
            row.getCell(ExamColumn.AUTO_REFRACTOR_OS_AXIS.getColIndex())
        };
        data = getStringDataFromCells(cells);
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AUTOREFRACTION);
            exam.addResult(new AutoRefractorExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5]
            ));
            enc.addExam(exam);
        }

        //MD visit
        data = new String[]{
            row.getCell(ExamColumn.MD_VISIT_UNDILATED.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.MD_VISIT_DILATED.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.MD_VISIT);
            exam.addResult(new MDVisitExamResult(
                    data[0],
                    data[1]
            ));
            enc.addExam(exam);
        }

        //pedigree
        data = new String[]{
            row.getCell(ExamColumn.GENETICS_PEDIGREE.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.PEDIGREE);
            exam.addResult(new PedigreeExamResult(
                    data[0]
            ));
            enc.addExam(exam);
        }

        //genetics, maybe? May not be an exam result, need further guidance
        //lunch exam result
        data = new String[]{
            row.getCell(ExamColumn.LUNCH.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.LUNCH);
            enc.addExam(exam);
        }

        //OPTOS
        cells = new Cell[]{
            row.getCell(ExamColumn.OPTOS_COLOR_OPTOS_COLOR_OD.getColIndex()),
            row.getCell(ExamColumn.OPTOS_COLOR_OPTOS_COLOR_OS.getColIndex()),
            row.getCell(ExamColumn.OPTOS_AF_OPTOS_AF_OD.getColIndex()),
            row.getCell(ExamColumn.OPTOS_AF_OPTOS_AF_OS.getColIndex())
        };
        data = getStringDataFromCells(cells);
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.FUNDUS_OPTOS);
            exam.addResult(new OptosExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //Spectralis AF
        data = new String[]{
            row.getCell(ExamColumn.SPECTRALIS_AF_SPECTRALIS_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.SPECTRALIS_AF_SPECTRALIS_OS.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.SPECTRALIS);
            exam.addResult(new SpectrailsExamResult(
                    data[0],
                    data[1]
            ));
            enc.addExam(exam);
        }

        //fundus visucam
        data = new String[]{
            row.getCell(ExamColumn.FUNDUS_VISUCAM_VISUCAM_30_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.FUNDUS_VISUCAM_VISUCAM_30_OS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.FUNDUS_VISUCAM_VISUCAM_45_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.FUNDUS_VISUCAM_VISUCAM_45_OS.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.FUNDUS_VISUCAM);
            exam.addResult(new FundusVisucamExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //OCT Optovue
        data = new String[]{
            row.getCell(ExamColumn.OCT_OPTOVUE_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.OCT_OPTOVUE_OS.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.OCT_OPTOVUE);
            exam.addResult(new OCTOptovueExamResult(
                    data[0],
                    data[1]
            ));
            enc.addExam(exam);
        }

        //OCT Cirrus undilated
        cells = new Cell[]{
            row.getCell(ExamColumn.OCT_CIRRUS_CIRRUS_UNDILATED_OD.getColIndex()),
            row.getCell(ExamColumn.OCT_CIRRUS_CIRRUS_UNDILATED_OS.getColIndex())
        };
        data = getStringDataFromCells(cells);
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.OCT_CIRRUS_UNDILATED);
            exam.addResult(new OCTCirrusUndilatedExamResult(
                    data[0],
                    data[1]
            ));
            enc.addExam(exam);
        }

        //OCT Cirrus dilated
        cells = new Cell[]{
            row.getCell(ExamColumn.OCT_CIRRUS_CIRRUS_DILATED_OD.getColIndex()),
            row.getCell(ExamColumn.OCT_CIRRUS_CIRRUS_DILATED_OS.getColIndex())
        };
        data = getStringDataFromCells(cells);
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.OCT_CIRRUS_DILATED);
            exam.addResult(new OCTCirrusDilatedExamResult(
                    data[0],
                    data[1]
            ));
            enc.addExam(exam);
        }

        //OCT Bioptigen
        cells = new Cell[]{
            row.getCell(ExamColumn.OCT_BIOPTIGEN_BIOPTIGEN_CLINICAL_OD.getColIndex()),
            row.getCell(ExamColumn.OCT_BIOPTIGEN_BIOPTIGEN_CLINICAL_OS.getColIndex()),
            row.getCell(ExamColumn.OCT_BIOPTIGEN_BIOPTIGEN_GLAUCOMA_OD.getColIndex()),
            row.getCell(ExamColumn.OCT_BIOPTIGEN_BIOPTIGEN_GLAUCOMA_OS.getColIndex())
        };
        data = getStringDataFromCells(cells);
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.OCT_BIOPTIGEN);
            exam.addResult(new OCTBioptigenExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //AOSLO 2
        data = new String[]{
            row.getCell(ExamColumn.AOSLO_2_0_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_2_0_OD_FIXATION.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_2_0_OS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_2_0_OS_FIXATION.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AO_SLO_2_0_IMAGING);
            exam.addResult(new AOExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //AOSLO 2.1
        data = new String[]{
            row.getCell(ExamColumn.AOSLO_2_1_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_2_1_OD_FIXATION.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_2_1_OS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_2_1_OS_FIXATION.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AO_SLO_2_1_IMAGING);
            exam.addResult(new AOExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //AOSLO 2.2
        data = new String[]{
            row.getCell(ExamColumn.AOSLO_2_2_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_2_2_OD_FIXATION.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_2_2_OS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_2_2_OS_FIXATION.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AO_SLO_2_2_IMAGING);
            exam.addResult(new AOExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //AOSLO 3
        data = new String[]{
            row.getCell(ExamColumn.AOSLO_3_0_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_3_0_OD_FIXATION.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_3_0_OS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_3_0_OS_FIXATION.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AO_SLO_3_0_IMAGING);
            exam.addResult(new AOExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //AOSLO 4
        data = new String[]{
            row.getCell(ExamColumn.AOSLO_4_0_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_4_0_OD_FIXATION.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_4_0_OS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_4_0_OS_FIXATION.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AO_SLO_4_0_IMAGING);
            exam.addResult(new AOExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //AO FLOOD
        data = new String[]{
            row.getCell(ExamColumn.AO_FLOOD_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AO_FLOOD_OD_FIXATION.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AO_FLOOD_OS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AO_FLOOD_OS_FIXATION.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AO_FLOOD_1_0_IMAGING);
            exam.addResult(new AOExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //AO FLOOD 1.1
        data = new String[]{
            row.getCell(ExamColumn.AO_FLOOD_1_1_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AO_FLOOD_1_1_OD_FIXATION.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AO_FLOOD_1_1_OS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AO_FLOOD_1_1_OS_FIXATION.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AO_FLOOD_1_1_IMAGING);
            exam.addResult(new AOExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //AOSLO MEH
        data = new String[]{
            row.getCell(ExamColumn.AOSLO_MEH_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_MEH_OD_FIXATION.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_MEH_OS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_MEH_OS_FIXATION.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AO_MEH_IMAGING);
            exam.addResult(new AOExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }

        //AOSLO NYEEI
        data = new String[]{
            row.getCell(ExamColumn.AOSLO_NYEEI_OD.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_NYEEI_OD_FIXATION.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_NYEEI_OS.getColIndex()).getStringCellValue().trim(),
            row.getCell(ExamColumn.AOSLO_NYEEI_OS_FIXATION.getColIndex()).getStringCellValue().trim()
        };
        if (Arrays.stream(data).anyMatch(field -> !field.isEmpty() && !field.equals("-"))) {
            EncounterExamType exam = new EncounterExamType(ExamType.AO_NYEEI_IMAGING);
            exam.addResult(new AOExamResult(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            ));
            enc.addExam(exam);
        }
    }

    private String[] getStringDataFromCells(Cell[] cells) {
        String[] data = new String[cells.length];
        for (int i = 0; i < cells.length; i++) {
            Cell cell = cells[i];
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    data[i] = Double.toString(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                case Cell.CELL_TYPE_BLANK:
                    data[i] = cell.getStringCellValue();
                    break;
                default:
                    System.out.println("Uknonwn data type: " + cell.getCellType());
                    System.exit(2);
            }
        }
        return data;
    }
}
