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
import java.util.HashMap;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
        for (Row row : dataSheet) {
            if (row.getRowNum() < dataRowStart) {
                continue;
            }
            System.out.println("rownum: " + row.getRowNum());
            Subject subject = new Subject();
            for (Cell cell : row) {
                CellValue cellValue = evaluator.evaluate(cell);
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
//                        System.out.println(formattedCellValue);
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
                        subject.setLname(cell.getStringCellValue().trim());
                        break;
                    case DOB:
                        subject.setDob(cell.getDateCellValue());
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
                        subject.setClinical_trial_id(cell.getStringCellValue().trim());
                        break;
                    case OTHER_ID:
                        subject.setOther_id(cell.getStringCellValue().trim());
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
                        subject.setZip(cell.getStringCellValue().trim());
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
//                    switch (cellValue.getCellType()) {
//                        case Cell.CELL_TYPE_BOOLEAN:
//                            System.out.print(cellValue.getBooleanValue() + "\t");
//                            break;
//                        case Cell.CELL_TYPE_NUMERIC:
//                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                System.out.print(cell.getDateCellValue() + "\t");
//                            } else {
//                                System.out.print(cellValue.getNumberValue() + "\t");
//                            }
//                            break;
//                        case Cell.CELL_TYPE_STRING:
//                            System.out.print(cellValue.getStringValue() + "\t");
//                            break;
//                        case Cell.CELL_TYPE_BLANK:
//                        case Cell.CELL_TYPE_ERROR:
//                        case Cell.CELL_TYPE_FORMULA:
//                        default:
//                            break;
//                    }
            }
            System.out.println(subject);

            if (row.getRowNum() > 20) {
                break;
            }
        }
        return null;
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
