/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io;

import com.bwc.lat.io.exc.SubjectColumn;
import com.bwc.lat.io.dom.Encounter;
import com.bwc.lat.io.dom.ProviderMap;
import com.bwc.lat.io.dom.Referal;
import com.bwc.lat.io.dom.Subject;
import com.bwc.lat.io.exc.EncounterColumn;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
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

        }

        //find earliest encounter to mark as first encounter for each subject
        encounters.stream()
                .collect(Collectors.groupingBy(Encounter::getSubject_id))
                .values()
                .forEach(ecns -> {
                    if(ecns.size() == 1){
                        ecns.get(0).setEncounter_type_id(1);
                    }else{
                        ecns.stream()
                                .sorted((s1,s2) -> s1.getEncounter_start_date().compareTo(s2.getEncounter_start_date()))
                                .findFirst()
                                .get()
                                .setEncounter_type_id(1);
                    }
                });

        //merge the subjects into a single subject record per AOIP ID, then return final list
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
                            //s1 comes before s2
                            retSub = mergeSubjects(s1, s2);
                        } else {
                            //s2 comes before s1
                            retSub = mergeSubjects(s2, s1);
                        }
                        return retSub;
                    });
                })
                .filter(s -> s.getAoip_id() != null)
                .collect(Collectors.toList());
    }

    private Subject mergeSubjects(Subject baseSub, Subject mergeSub) {
        if (mergeSub.getAoip_id() != null) {
            baseSub.setAoip_id(mergeSub.getAoip_id());
        }
        if (mergeSub.getFname() != null) {
            baseSub.setFname(mergeSub.getFname());
        }
        if (mergeSub.getMi() != null) {
            baseSub.setMi(mergeSub.getMi());
        }
        if (mergeSub.getLname() != null) {
            baseSub.setLname(mergeSub.getLname());
        }
        if (mergeSub.getDob() != null) {
            baseSub.setDob(mergeSub.getDob());
        }
        if (mergeSub.getGender() != null) {
            baseSub.setGender(mergeSub.getGender());
        }
        if (mergeSub.getFroedert_mrn() != null) {
            baseSub.setFroedert_mrn(mergeSub.getFroedert_mrn());
        }
        if (mergeSub.getChw_id() != null) {
            baseSub.setChw_id(mergeSub.getChw_id());
        }
        if (mergeSub.getClinical_trial_id() != null) {
            baseSub.setClinical_trial_id(mergeSub.getClinical_trial_id());
        }
        if (mergeSub.getOther_id() != null) {
            baseSub.setOther_id(mergeSub.getOther_id());
        }
        if (mergeSub.getAddress1() != null) {
            baseSub.setAddress1(mergeSub.getAddress1());
        }
        if (mergeSub.getAddress2() != null) {
            baseSub.setAddress2(mergeSub.getAddress2());
        }
        if (mergeSub.getCity() != null) {
            baseSub.setCity(mergeSub.getCity());
        }
        if (mergeSub.getState() != null) {
            baseSub.setState(mergeSub.getState());
        }
        if (mergeSub.getZip() != null) {
            baseSub.setZip(mergeSub.getZip());
        }
        if (mergeSub.getCountry() != null) {
            baseSub.setCountry(mergeSub.getCountry());
        }
        if (mergeSub.getEmail() != null) {
            baseSub.setEmail(mergeSub.getEmail());
        }
        if (mergeSub.getPhone_personal() != null) {
            baseSub.setPhone_personal(mergeSub.getPhone_personal());
        }
        if (mergeSub.getPhone_work() != null) {
            baseSub.setPhone_work(mergeSub.getPhone_work());
        }
        if (mergeSub.getDx_pri() != null) {
            baseSub.setDx_pri(mergeSub.getDx_pri());
        }
        if (mergeSub.getDx_sec() != null) {
            baseSub.setDx_sec(mergeSub.getDx_sec());
        }
        if (mergeSub.getDx_other() != null) {
            baseSub.setDx_other(mergeSub.getDx_other());
        }
        if (mergeSub.getDx_other() != null) {
            baseSub.setDx_other(mergeSub.getDx_other());
        }
        if (mergeSub.getDilate_safe() != null) {
            baseSub.setDilate_safe(mergeSub.getDilate_safe());
        }
        if (mergeSub.getNystagmus() != null) {
            baseSub.setNystagmus(mergeSub.getNystagmus());
        }
        if (mergeSub.getUnstable_fixation() != null) {
            baseSub.setUnstable_fixation(mergeSub.getUnstable_fixation());
        }
        if (mergeSub.getEye_color() != null) {
            baseSub.setEye_color(mergeSub.getEye_color());
        }
        if (mergeSub.getReferral_type_id() != null && mergeSub.getReferral_type_id() != Referal.UNKNOWN) {
            baseSub.setReferral_type_id(mergeSub.getReferral_type_id());
        }
        return baseSub;
    }

}
