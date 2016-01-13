package jdatareport.dof.classes.report.paint;

import java.util.*;

import java.awt.*;

import com.eai.form.dal.interfaces.*;
import com.eai.form.formdefine.interfaces.*;
import com.eai.form.formdefine.interfaces.datastyle.*;
import com.f1j.ss.*;
import com.f1j.swing.*;
import jdatareport.dof.classes.report.*;
import jdatareport.dof.classes.report.figure.*;
import jdatareport.dof.classes.report.filter.*;
import jdatareport.dof.classes.report.util.*;
import jformservice.jdof.classes.*;
import jservice.jbof.classes.GenerQueryObject.*;

/**
 *
 * <p>Title: JDRPainter</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */
public class JDRPainter {
    /**
     *
     */
    protected JBook mBook = null;
    protected JDRModel mModel = null;

    private byte mViewFilter = 0;

    public JDRRowFilter mColFilter = new JDRRowFilter();
    public JDRQueryPainter queryPanter = null;
    protected JQueryStubObject QO = null;
    /**
     * 无参构造器
     */
    public void JDRPainter(){

    }
    /**
     *
     * @param qo Object
     */
    public void setQueryObject(Object qo) {
      this.QO = (JQueryStubObject)qo;
    }
    /**
     * 构造方法
     * @param book  JBook
     * @param model 报表数据模型
     */
    public JDRPainter(JBook book, JDRModel model) {
        this.mBook = book;
        this.mModel = model;
    }
    /**
     *
     * @param book JBook
     * @param model JDRModel
     */
    public void initJDRPainter(JBook book, JDRModel model) {
      this.mBook = book;
      this.mModel = model;
    }
    /**
     * 绘制报表
     */
    public void draw() {
        try {
            JDRRptFigure rptFigure = mModel.getRptFigure();
            for (Enumeration en = rptFigure.getCellFigures().elements(); en.hasMoreElements(); ) {
                JDRCellFigure cellFigure = (JDRCellFigure) en.nextElement();
                if (cellFigure.getType().equals(JDRConstants.TYPE_TEXT)) {
                    drawText(cellFigure);
                }
                else if (cellFigure.getType().equals(JDRConstants.TYPE_FORM)) {
                    drawForm(cellFigure);
                }
                else if (cellFigure.getType().equals(JDRConstants.TYPE_QUERY)) {
                    queryPanter = new JDRQueryPainter(mBook, mModel, cellFigure);
                    queryPanter.drawQuery(JDRView.FILTER_ALL,false);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制报表
     *
     */
    public void draw(byte viewFilter) {
        this.mViewFilter = viewFilter;

        try {
            JDRRptFigure rptFigure = mModel.getRptFigure();
            for (Enumeration en = rptFigure.getCellFigures().elements(); en.hasMoreElements(); ) {
                JDRCellFigure cellFigure = (JDRCellFigure) en.nextElement();
                if (cellFigure.getType().equals(JDRConstants.TYPE_TEXT)) {
                    drawText(cellFigure);
                }
                else if (cellFigure.getType().equals(JDRConstants.TYPE_FORM)) {
                    drawForm(cellFigure);
                }
                else if (cellFigure.getType().equals(JDRConstants.TYPE_QUERY)) {
                    queryPanter = new JDRQueryPainter(mBook, mModel, cellFigure);
                    queryPanter.drawQuery(this.mViewFilter,true);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制报表
     * @param rowFilter JDRRowFilter 行过滤器
     */
    public void draw(JDRRowFilter rowFilter) {
        this.mColFilter = rowFilter;
        try {
            JDRRptFigure rptFigure = mModel.getRptFigure();
            for (Enumeration en = rptFigure.getCellFigures().elements(); en.hasMoreElements(); ) {
                JDRCellFigure cellFigure = (JDRCellFigure) en.nextElement();
                if (cellFigure.getType().equals(JDRConstants.TYPE_TEXT)) {
                    drawText(cellFigure);
                }
                else if (cellFigure.getType().equals(JDRConstants.TYPE_FORM)) {
                    drawForm(cellFigure);
                }
                else if (cellFigure.getType().equals(JDRConstants.TYPE_QUERY)) {
                    JDRQueryPainter queryPanter = new JDRQueryPainter(mBook, mModel, cellFigure);
                    queryPanter.drawQuery(mColFilter);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制文本
     * @param cellFigure
     * @throws java.lang.Exception
     */
    private void drawText(JDRCellFigure cellFigure) throws Exception {
        int cellStartRow = cellFigure.getStartRow();
        int cellStartCol = cellFigure.getStartCol();
        int cellEndRow = cellFigure.getEndRow();
        int cellEndCol = cellFigure.getEndCol();

        int refRow = 0;
        int refCol = 0;

        /**
         *
         */
        refRow = JDRPaintUtils.calcRefRow(mModel, cellFigure);
        refCol = JDRPaintUtils.calcRefCol(mModel, cellFigure);

        String text = cellFigure.getCaption();
        text = JDRPaintUtils.replace(text, mModel.getParams());
        mBook.setText(refRow + cellStartRow, refCol + cellStartCol, text);

        //格式
        /**
         * 如果终行列等于-1或者小于起始坐标则等于起始坐标
         */
        if (cellEndRow == -1 || cellEndRow < cellStartRow) {
            cellEndRow = cellStartRow;
        }
        if (cellEndCol == -1 || cellEndCol < cellStartCol) {
            cellEndCol = cellStartCol;

        }
        int crtMaxRow = refRow + cellEndRow;
        int crtMaxCol = refCol + cellEndCol;
        if (crtMaxRow > mModel.getMaxRow()) {
            mModel.setMaxRow(crtMaxRow);
        }
        if (crtMaxCol > mModel.getMaxCol()) {
            mModel.setMaxCol(crtMaxCol);
        }

        mBook.setSelection(refRow + cellStartRow, refCol + cellStartCol, refRow + cellEndRow, refCol + cellEndCol);
        CellFormat cf = mBook.getCellFormat();
        cf.setHorizontalAlignment(JDRPaintUtils.getAlignment(cellFigure.getAlignment()));
        cf.setVerticalAlignment(cf.eVerticalAlignmentCenter);

        Font font = cellFigure.getFont();
        cf.setFontName(font.getName());
        cf.setFontBold(font.isBold());
        cf.setFontItalic(font.isItalic());
        cf.setFontSize(font.getSize() * JDRConstants.DEFAULT_FONT_SCALE);

        cf.setMergeCells(true);
        mBook.setCellFormat(cf);
    }

    /**
     * 绘制数据窗口
     * @param cellFigure
     * @throws java.lang.Exception
     */
    private void drawForm(JDRCellFigure cellFigure) throws Exception {
        JDataWindow form = JDRPaintUtils.getForm(mModel, cellFigure);
        if (form != null) {
            String strFormType = form.getDefine().getFormStyle();
            if (strFormType.equals("GRID")) { //表格
                drawGridForm(form, cellFigure);
            }
            else if (strFormType.equals("FREE")) { //卡片
                drawFreeForm(form, cellFigure);
            }
        }
    }

    /**
     * 绘制表格数据窗口
     * @param form
     * @param cellFigure
     */
    private void drawGridForm(JDataWindow form, JDRCellFigure cellFigure) throws Exception {
        int cellStartRow = cellFigure.getStartRow();
        int cellStartCol = cellFigure.getStartCol();
        int cellEndRow = cellFigure.getEndRow();
        int cellEndCol = cellFigure.getEndCol();
        int refRow = 0;
        int refCol = 0;

        /**
         *
         */
        refRow = JDRPaintUtils.calcRefRow(mModel, cellFigure);
        refCol = JDRPaintUtils.calcRefCol(mModel, cellFigure);

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
//        strText = ReportUtils.replace(strText, this.mParamsStr);
                mBook.setText(row, col, strText);
                //CellFormat
                setFormFormat(row, col, INode);
            }
        }

        int crtMaxRow = refRow + cellStartRow + iRowCount;
        int crtMaxCol = refCol + cellStartRow + FieldIdx.length;
        if (crtMaxRow > mModel.getMaxRow()) {
            mModel.setMaxRow(crtMaxRow);
        }
        if (crtMaxCol > mModel.getMaxCol()) {
            mModel.setMaxCol(crtMaxCol);
        }

        //合计处理
        IRecord sumRecord = form.getDataForm().getDataSet().getComputePool().getRecord(0);
        if (sumRecord != null) {
            for (int i = 0; i < FieldIdx.length; i++) {
                IField IF = sumRecord.Fields().Field(FieldIdx[i]);
                IFormNodeDefine INode = IF.getDefineInfo().getFormNodeDefine();
                String strText = IF.AsString();
                int row = refRow + cellStartRow + iRowCount;
                int col = refCol + cellStartCol + i;
//          strText = ReportUtils.replace(strText, this.mParamsStr);
                mBook.setText(row, col, strText);
                //CellFormat
                setFormFormat(row, col, INode);

                int maxRow = mModel.getMaxRow();
                if (row > maxRow) {
                    mModel.setMaxRow(row);
                }
            }
        }
    }

    /**
     * 绘制卡片数据窗口
     * @param form
     * @param cellFigure
     * @throws java.lang.Exception
     */
    private void drawFreeForm(JDataWindow form, JDRCellFigure cellFigure) throws Exception {
        int cellStartRow = cellFigure.getStartRow();
        int cellStartCol = cellFigure.getStartCol();
        int cellEndRow = cellFigure.getEndRow();
        int cellEndCol = cellFigure.getEndCol();
        int refRow = 0;
        int refCol = 0;

        /**
         *
         */
        refRow = JDRPaintUtils.calcRefRow(mModel, cellFigure);
        refCol = JDRPaintUtils.calcRefCol(mModel, cellFigure);

        IRecord IR = form.getDataSetInterface().getActivePool().getActiveRecord();
        if (IR != null) {
            int iRowCount = form.getDefine().getRowCount();
            int iColCount = form.getDefine().getColumnCount();

            for (int iRow = 0; iRow < iRowCount; iRow++) {
                for (int iCol = 0; iCol < iColCount; iCol++) {
                    //-------------------------------------------------------------------------------
                    //字段值
                    //
                    IFormNodeDefine INode = form.getDefine().getFormNodesDefine().Node(iRow, iCol);
                    if (INode == null) {
                        continue;
                    }
                    String strText = "";
                    //静态文本
                    if (INode.getDataStyle() == IFormDataStyleFactory.DATA_TYPE_USER) { //Label
                        strText = INode.getUserDisplay();
                    }
                    //字段值
                    else if (INode.getDataStyle() == IFormDataStyleFactory.DATA_TYPE_FIELD) { //字段
                        IField IF = IR.Fields().Field(INode.getFieldName());
                        if (IF != null) {
                            strText = IF.getDiaplay();
                        }
                    }
                    //------------------------------------------------------------------------------
                    int row = cellStartRow + iRow;
                    int col = cellStartCol + iCol;
//          strText = ReportUtils.replace(strText, this.mParamsStr);
                    mBook.setText(refRow + row, refCol + col, strText);
                    //CellFormat
                    setFormFormat(refRow + row, refCol + col, INode);
                }
            } //end of for

            int crtMaxRow = refRow + cellStartRow + iRowCount;
            int crtMaxCol = refCol + cellStartCol + iColCount;
            if (crtMaxRow > mModel.getMaxRow()) {
                mModel.setMaxRow(crtMaxRow);
            }
            if (crtMaxCol > mModel.getMaxCol()) {
                mModel.setMaxCol(crtMaxCol);
            }
        }

    }

    /**
     * 设置数据窗口的格式
     * @param row
     * @param col
     * @param node
     * @throws java.lang.Exception
     */
    private void setFormFormat(int row, int col, IFormNodeDefine node) throws Exception {
        mBook.setSelection(row, col, row, col);
        CellFormat cellFormat = mBook.getCellFormat();
        cellFormat.setHorizontalAlignment(JDRPaintUtils.getAlignment(node));
        cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
        cellFormat.setFontName(node.getFontName());
        cellFormat.setFontSize(node.getFontSize() * JDRConstants.DEFAULT_FONT_SCALE);
        mBook.setCellFormat(cellFormat);
        //单元格合并,只有卡片有合并单元格
        int height = node.getComboRowCount();
        int width = node.getComboColumnCount();
        if (width != 0 || height != 0) {
            mBook.setSelection(row, col, row + height, col + width);
            cellFormat = mBook.getCellFormat();
            cellFormat.setMergeCells(true);
            mBook.setCellFormat(cellFormat);
        }
    }

    /**
     *
     * @param filter
     */
    public void setFilter(byte viewFilter) {
        this.mViewFilter = viewFilter;
    }

    /**
     *
     * @return
     */
    public byte getFilter() {
        return this.mViewFilter;
    }

}
