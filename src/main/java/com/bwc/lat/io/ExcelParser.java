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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
        return subjects;
    }

    private enum SubjectColumn {

        INITIALS("C", 2),
        ID("D", 3),
        FIRST_NAME("E", 4),
        MIDDLE_INITIAL("F", 5),
        LAST_NAME("G", 6),
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
