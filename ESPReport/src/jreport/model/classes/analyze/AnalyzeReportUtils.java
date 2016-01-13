package jreport.model.classes.analyze;

import com.f1j.swing.*;
import com.f1j.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AnalyzeReportUtils{
    /**
     *
     */
    public AnalyzeReportUtils() {
    }

    /**
     *
     * @param book
     * @param row
     */
    public static void setDefaultRowHeight(JBook book, int row) {
        try {
            book.setRowHeight(row, AnalyzeReportConstants.DEFAULT_ROW_HEIGHT);
        }
        catch (F1Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setDefaultRowHeight(JBook book, int startRow, int endRow) {
        try {
            book.setRowHeight(startRow, endRow, AnalyzeReportConstants.DEFAULT_ROW_HEIGHT, false, false);
        }
        catch (F1Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     *
     * @param str
     * @return
     */
    public static boolean isDouble(String str){
        try{
          Double.parseDouble(str);
        }catch(Exception e){
            return false;
        }
        return true;
    }

}