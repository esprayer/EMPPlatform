package jservice.jbof.classes.GenerQueryObject.print.util;

import java.awt.*;

import com.f1j.ss.*;
import com.f1j.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JPrintUtils
    implements PrintConstants {

    public JPrintUtils() {
    }

    public static int getTwipsScale(JBook book) {
        if (book.isPrintLandscape()) {
            return 1;
        }
        else {
            return PrintConstants.TWIPS_SCALE;
        }
    }

    /**
     *
     * @return
     */
    public static int getDpi() {
        int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        return dpi;
    }

    /**
     *
     * @param value
     * @return
     */
    public static double dotsToTwips(JBook book, int value) {
        return (double) value * getTwipsScale(book) / (double) JPrintUtils.getDpi();
    }

    /**
     *
     * @param value
     * @return
     */
    public static int twipsToDots(JBook book, double value) {
        return (int) ( ( (double) value * JPrintUtils.getDpi()) / (double) getTwipsScale(book));
    }

    /**
     *
     * @param value
     * @return
     */
    public static double inchToTwips(JBook book, double value) {
        return value * getTwipsScale(book);
    }

    /**
     *
     * @param book
     * @return
     */
    public static JBook copyPrintBook(JBook book) {
        JBook printBook = new JBook();
        try {
            printBook.copyAll(book);
            setPrintValueFormat(printBook);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return printBook;
    }

    /**
     * 对于包含有类似负数红色显示并且显示负号的处理成为正常个格式
     * #,##0.00;[RED]#,##0.00" => "#,##0.00";
     * @param book
     */
    public static void setPrintValueFormat(JBook book) {
        if (book == null) {
            return;
        }
        String viewOption = "[RED]";
        try {
            String formula = book.getPrintArea();
            if (formula == null) {
                return;
            }
            int sheetIndex = formula.indexOf("!");
            String areaFormula = book.getPrintArea().substring(sheetIndex + 1);
            AreaInfo info = formulaToCoor(areaFormula);

            for (int i = info.mStartRow; i <= info.mEndRow; i++) {
                for (int j = info.mStartCol; j <= info.mEndCol; j++) {
                    book.setSelection(i, j, i, j);
                    CellFormat cf = book.getCellFormat();
                    String crtViewFormat = cf.getValueFormat();
                    int viewIndex = crtViewFormat.toUpperCase().indexOf(viewOption);
                    if (viewIndex != -1 && viewIndex > 1) {
                        String printFormat = crtViewFormat.substring(0, viewIndex - 1);
                        cf.setValueFormat(printFormat);
                    }
                    book.setCellFormat(cf);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param formula
     * @return
     */
    public static AreaInfo formulaToCoor(String formula) {
        AreaInfo areaInfo = new AreaInfo();
        int colonIndex = formula.indexOf(":");
        String startFormula = "";
        String endFormula = "";
        if (colonIndex != -1) {
            startFormula = formula.substring(0, colonIndex);
            endFormula = formula.substring(colonIndex + 1);
        }
        else {
            startFormula = formula;
            endFormula = formula;
        }
        int startRow = 0;
        int startCol = 0;
        int endRow = 0;
        int endCol = 0;

        int startIndex = getFirstNumberIndex(startFormula);
        if (startIndex != -1) {
            String colChar = startFormula.substring(0, startIndex);
            String rowChar = startFormula.substring(startIndex);
            startCol = getID(colChar);
            startRow = Integer.parseInt(rowChar);
        }

        int endIndex = getFirstNumberIndex(endFormula);
        if (endIndex != -1) {
            String colChar = endFormula.substring(0, endIndex);
            String rowChar = endFormula.substring(endIndex);
            endCol = getID(colChar);
            endRow = Integer.parseInt(rowChar);
        }
        areaInfo.mStartRow = startRow;
        areaInfo.mStartCol = startCol;
        areaInfo.mEndRow = endRow;
        areaInfo.mEndCol = endCol;

        return areaInfo;
    }

    public static int getID(String sid) {
        int result = 0;
        int carry = 1;
        for (int i = sid.length() - 1; i >= 0; i--) {
            result += ( (sid.charAt(i) - 65) + 1) * carry;
            carry *= 26;
        }
        return result;
    }

    /**
     * 获得字符串第一个数字的索引
     * @param str
     * @return
     */
    public static int getFirstNumberIndex(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

}