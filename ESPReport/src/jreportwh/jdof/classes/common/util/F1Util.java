package jreportwh.jdof.classes.common.util;

import com.f1j.swing.JBook;
import com.f1j.ss.CellFormat;
import java.awt.Color;
import com.f1j.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class F1Util
{
    public static void setStdHeadCellFormat(JBook book){
        try{
            CellFormat cellFormat = book.getCellFormat();
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
            cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            cellFormat.setTopBorder(CellFormat.eBorderMedium);
            cellFormat.setBottomBorder(CellFormat.eBorderMedium);
            cellFormat.setLeftBorder(CellFormat.eBorderThin);
            cellFormat.setRightBorder(CellFormat.eBorderThin);
            cellFormat.setPattern(CellFormat.ePatternSolid);
            cellFormat.setPatternFG(0xCEDDF0);
            book.setCellFormat(cellFormat);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void setStdBodyCellFormat(JBook book){
        try{
            CellFormat cellFormat = book.getCellFormat();
            cellFormat = book.getCellFormat();
            cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            cellFormat.setTopBorder(CellFormat.eBorderThin);
            cellFormat.setBottomBorder(CellFormat.eBorderThin);
            cellFormat.setLeftBorder(CellFormat.eBorderThin);
            cellFormat.setRightBorder(CellFormat.eBorderThin);
            book.setCellFormat(cellFormat);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static void center(JBook book){
        try{
            CellFormat cellFormat = book.getCellFormat();
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
            cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            book.setCellFormat(cellFormat);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void setBorder(JBook book,short borderStyle){
        try{
            CellFormat cellFormat = book.getCellFormat();
            cellFormat.setTopBorder(borderStyle);
            cellFormat.setBottomBorder(borderStyle);
            cellFormat.setLeftBorder(borderStyle);
            cellFormat.setRightBorder(borderStyle);
            book.setCellFormat(cellFormat);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void setHorzontalAlignment(JBook book,short orient){
        try{
            CellFormat cellFormat = book.getCellFormat();
            cellFormat.setHorizontalAlignment(orient);
            book.setCellFormat(cellFormat);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void setVertialAlignment(JBook book,short orient){
        try{
            CellFormat cellFormat = book.getCellFormat();
            cellFormat.setVerticalAlignment(orient);
            book.setCellFormat(cellFormat);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void setMergeCells(JBook book,boolean isMerge){
        try {
            CellFormat cellFormat = book.getCellFormat();
            cellFormat.setMergeCells(isMerge);
            book.setCellFormat(cellFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
