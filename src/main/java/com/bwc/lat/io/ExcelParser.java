/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bwc.lat.io;

import com.bwc.lat.io.dom.Referal;
import com.bwc.lat.io.dom.Subject;
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

    public ExcelParser(File excelFile) throws IOException, InvalidFormatException {
        excelWorkbook = WorkbookFactory.create(excelFile);
        dataSheet = excelWorkbook.getSheetAt(1);
        evaluator = excelWorkbook.getCreationHelper().createFormulaEvaluator();
    }

    public List<Subject> getSubjects() {
        List<Subject> subjects = new LinkedList<>();
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
                        if (formattedCellValue.equals("0000")) {
                            //end of data reached
                            System.out.println("End of data reached at row " + (cell.getRowIndex() + 1));
                            break;
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
                    default:
                        break;
                }
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
                    .filter(s -> s.getVisit_date() != null)
                    .reduce(identSub, (s1, s2) -> {
                        Subject retSub;
                        if (s1.getVisit_date().compareTo(s2.getVisit_date()) < 0) {
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

    private enum SubjectColumn {

        INITIALS("C", 2),
        ID("D", 3),
        FIRST_NAME("E", 4),
        MIDDLE_INITIAL("F", 5),
        LAST_NAME("G", 6),
        VISIT_DATE("H", 7),
        DOB("I", 8),
        GENDER("K", 10),
        FROEDERT_MRN("N", 13),
        CHW_ID("O", 14),
        CLINICAL_TRIAL_ID("P", 15),
        OTHER_ID("R", 17),
        ADDRESS1("U", 20),
        ADDRESS2("V", 21),
        CITY("W", 22),
        STATE("X", 23),
        ZIP("Y", 24),
        COUNTRY("Z", 25),
        EMAIL("AA", 26),
        PHONE_PERSONAL("AB", 27),
        PHONE_WORK("AC", 28),
        DX_PRI("AG", 32),
        DX_SEC("AH", 33),
        DX_OTHER("AI", 34),
        DILATE_SAFE("AK", 36),
        NYSTAGMUS("AL", 37),
        UNSTABLE_FIXATION("AM", 38),
        EYE_COLOR("AN", 39),
        REFERRAL_TYPE("AP", 41),
        NOT_APPLICABLE("-1", -1);

        private static final HashMap<Integer, SubjectColumn> colMap = new HashMap<>(50);

        static {
            for (SubjectColumn sc : SubjectColumn.values()) {
                colMap.put(sc.getColIndex(), sc);
            }
        }
        private final String column;
        private final int colIndex;

        private SubjectColumn(String column, int colIndex) {
            this.column = column;
            this.colIndex = colIndex;
        }

        public String getColumn() {
            return column;
        }

        public int getColIndex() {
            return colIndex;
        }

        public static SubjectColumn getColByIndex(int idx) {
            SubjectColumn col = NOT_APPLICABLE;
            if (colMap.containsKey(idx)) {
                col = colMap.get(idx);
            }
            return col;
        }
    }

}
