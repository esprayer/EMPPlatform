package jdatareport.dof.classes.report.paint;

import com.f1j.swing.*;
import jformservice.jdof.classes.*;
import com.eai.form.dal.interfaces.*;
import com.eai.form.formdefine.interfaces.*;
import com.eai.form.formdefine.interfaces.datastyle.*;
import com.f1j.ss.*;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDRDataWindowPainter {

    private JBook book = null;
    /**
     *
     */
    public JDRDataWindowPainter(JBook book) {
        if (book == null) {
            throw new IllegalArgumentException();
        }
        this.book = book;
    }


    private void draw(JDataWindow dataWindow, CellFigure cellFigure) throws Exception {
        if (dataWindow != null) {
            String strFormType = dataWindow.getDefine().getFormStyle();
            if (strFormType.equals("GRID")) { //±í\u00B8\u00F1
                drawGridForm(dataWindow, cellFigure);
            }
            else if (strFormType.equals("FREE")) { //\u00BF¨\u00C6\u00AC
                drawFreeForm(dataWindow, cellFigure);
            }
        }
    }

    private void drawGridForm(JDataWindow form, CellFigure cellFigure) throws Exception {
        int cellStartRow = cellFigure.getStartRow();
        int cellStartCol = cellFigure.getStartCol();
        int cellEndRow = cellFigure.getEndRow();
        int cellEndCol = cellFigure.getEndCol();
        int refRow = 0;
        int refCol = 0;

        refRow = cellFigure.getRefRow();
        refCol = cellFigure.getRefCol();

        int[] FieldIdx = form.getDataSetInterface().getDiaplayMap();
        int iRowCount = form.getDataSetInterface().getActivePool().getRecordCount();
        for (int iRow = 0; iRow < iRowCount; iRow++) {
            IRecord IR = form.getDataSetInterface().getActivePool().getRecord(iRow);
            for (int iCol = 0; iCol < FieldIdx.length; iCol++) {
                IField IF = IR.Fields().Field(FieldIdx[iCol]);
                IFormNodeDefine INode = IF.getDefineInfo().getFormNodeDefine();
                String strText = IF.AsString();
                int row = refRow + cellStartRow + iRow;
                int col = refCol + cellStartCol + iCol;
                book.setText(row, col, strText);
                //CellFormat
                setFormFormat(row, col, INode);
            }
        }
        IRecord sumRecord = form.getDataForm().getDataSet().getComputePool().getRecord(0);
        if (sumRecord != null) {
            for (int i = 0; i < FieldIdx.length; i++) {
                IField IF = sumRecord.Fields().Field(FieldIdx[i]);
                IFormNodeDefine INode = IF.getDefineInfo().getFormNodeDefine();
                String strText = IF.AsString();
                int row = refRow + cellStartRow + iRowCount;
                int col = refCol + cellStartCol + i;
                book.setText(row, col, strText);
                //CellFormat
                setFormFormat(row, col, INode);
            }
        }
    }

    private void drawFreeForm(JDataWindow form, CellFigure cellFigure) throws Exception {
        int cellStartRow = cellFigure.getStartRow();
        int cellStartCol = cellFigure.getStartCol();
        int cellEndRow = cellFigure.getEndRow();
        int cellEndCol = cellFigure.getEndCol();
        int refRow = 0;
        int refCol = 0;

        refRow = cellFigure.getRefRow();
        refCol = cellFigure.getRefCol();

        IRecord IR = form.getDataSetInterface().getActivePool().getActiveRecord();
        if (IR != null) {
            int iRowCount = form.getDefine().getRowCount();
            int iColCount = form.getDefine().getColumnCount();

            for (int iRow = 0; iRow < iRowCount; iRow++) {
                for (int iCol = 0; iCol < iColCount; iCol++) {
                    IFormNodeDefine INode = form.getDefine().getFormNodesDefine().Node(iRow, iCol);
                    if (INode == null) {
                        continue;
                    }
                    String strText = "";
                    if (INode.getDataStyle() == IFormDataStyleFactory.DATA_TYPE_USER) {
                        strText = INode.getUserDisplay();
                    }
                    else if (INode.getDataStyle() == IFormDataStyleFactory.DATA_TYPE_FIELD) {
                        IField IF = IR.Fields().Field(INode.getFieldName());
                        if (IF != null) {
                            strText = IF.getDiaplay();
                        }
                    }
                    //------------------------------------------------------------------------------
                    int row = cellStartRow + iRow;
                    int col = cellStartCol + iCol;
                    book.setText(refRow + row, refCol + col, strText);
                    //CellFormat
                    setFormFormat(refRow + row, refCol + col, INode);
                }
            } //end of for
        }

    }

    private void setFormFormat(int row, int col, IFormNodeDefine node) throws Exception {
        book.setSelection(row, col, row, col);
        CellFormat cellFormat = book.getCellFormat();
        cellFormat.setHorizontalAlignment(getAlignment(node));
        cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
        cellFormat.setFontName(node.getFontName());
        cellFormat.setFontSize(node.getFontSize() * 16);
        book.setCellFormat(cellFormat);
        int height = node.getComboRowCount();
        int width = node.getComboColumnCount();
        if (width != 0 || height != 0) {
            book.setSelection(row, col, row + height, col + width);
            cellFormat = book.getCellFormat();
            cellFormat.setMergeCells(true);
            book.setCellFormat(cellFormat);
        }
    }

    /**
     * \u00BB\u00F1\u00B5\u00C3\u00B6\u00D4\u00C6\u00EB·\u00BD\u00CA\u00BD
     * @param INode
     * @return
     */
    public short getAlignment(IFormNodeDefine INode) {
        if (INode.getAlignment() == IFormNodeDefine.ALIGN_LEFT) {
            return CellFormat.eHorizontalAlignmentLeft;
        }
        else if (INode.getAlignment() == IFormNodeDefine.ALIGN_MIDDLE) {
            return CellFormat.eHorizontalAlignmentCenter;
        }
        else if (INode.getAlignment() == IFormNodeDefine.ALIGN_RIGHT) {
            return CellFormat.eHorizontalAlignmentRight;
        }
        return CellFormat.eHorizontalAlignment;
    }

    public static void main(String[] args) {
        //添加组件
        JPanel panel=new JPanel(new BorderLayout());
        JBook book=new JBook();
        panel.add(book,BorderLayout.CENTER);

        //绘制
        JDRDataWindowPainter painter=new JDRDataWindowPainter(book);
        CellFigure cellFigure=null;//提供构造好的CellFigure实例
        JDataWindow dataWindow=null;//提供打开的JDataWindow实例
        try {
            painter.draw(dataWindow, cellFigure);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static class CellFigure {
        private int startRow = 0;
        private int startCol = 0;
        private int endRow = -1;
        private int endCol = -1;
        private int refRow=0;
        private int refCol=0;
        public CellFigure(){

        }
        public int getStartRow() {
            return this.startRow;
        }

        public int getStartCol() {
            return this.startCol;
        }

        public int getEndRow() {
            return this.endRow;
        }

        public int getEndCol() {
            return this.endCol;
        }
        public int getRefRow(){
            return refRow;
        }
        public int getRefCol(){
            return refCol;
        }
    }
}