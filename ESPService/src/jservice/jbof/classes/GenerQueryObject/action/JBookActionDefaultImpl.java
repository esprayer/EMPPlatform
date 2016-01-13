package jservice.jbof.classes.GenerQueryObject.action;

import java.awt.*;
import javax.swing.*;

import com.f1j.ss.*;
import com.f1j.swing.*;
import jframework.resource.classes.*;
import jservice.jbof.classes.GenerQueryObject.action.ui.*;
import jservice.jbof.classes.GenerQueryObject.action.util.*;
import jframework.foundation.classes.*;
import java.util.ResourceBundle;
import jservice.jbof.classes.GenerQueryObject.JGenerQueryWindow;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */
/**
 * 暂时没有实现的方法:
 * ================
 * onImport
 * onDeleteHyperLink
 * onObjectGroup
 * onObjectUngroup
 * 空实现的方法:
 * =================
 * onGraphics
 * onPrintPreview
 * onPrint
 */
public class JBookActionDefaultImpl
    implements JBookActionImpl {
  static ResourceBundle res = ResourceBundle.getBundle("jservice.jbof.classes.GenerQueryObject.action.Language");

    /**
     *
     */
    public JBookActionDefaultImpl() {
    }

    //---------------------------------------------------------------------------
    // 常规操作
    //---------------------------------------------------------------------------
    /**
     * 查找
     * @param action
     */
    public void onFind(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.FindDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 替换
     * @param action
     */
    public void onReplace(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.ReplaceDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 删除
     * @param action
     */
    public void onDelete(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.editClear(JBook.eClearValues);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 转到
     * @param action
     */
    public void onGoto(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.GotoDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 导入
     * @param action
     */
    public void onImport(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 导出
     * @param action
     */
    public void onExport(JBookAction action) {
      if (action != null && action.getBook() != null) {
        JBook book = action.getBook();
        try {
          String defaultName = (String) action.getParam();
          //改成可以导出2007 Excle gj 2011.12.6
          //JBookActionUtils.export2Excel(book, defaultName);
          JBookActionUtils.exportToExcel(book, defaultName);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }


    //---------------------------------------------------------------------------
    // 编辑
    //---------------------------------------------------------------------------
    /**
     * 剪切
     * @param action
     */
    public void onCut(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
              //modify 2010-09-16 autho ZhangES
              //和FMIS7的处理方式不太一样，需要先将第一次剪切时遗留在JBook单元格的虚框去掉
              //才能再进行剪切，把数据粘贴到外部存储设备
              book.editCut();
              book.editPaste();
              book.editCut();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制
     * @param action
     */
    public void onCopy(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
              //modify 2010-09-16 autho ZhangES
              //和FMIS7的处理方式不太一样，需要先将第一次复制时遗留在JBook单元格的虚框去掉
              //才能再进行复制，把数据粘贴到外部存储设备
              book.editCut();
              book.editPaste();
              book.editCopy();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 粘贴
     * @param action
     */
    public void onPaste(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.editPaste();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 插入行
     * @param action
     */
    public void onInsertRow(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            int SRow, ERow;
            try {
                SRow = book.getSelStartRow() + 1;
                ERow = book.getSelEndRow() + 1;
                book.editInsert(JBook.eShiftRows);
//        ReportModel.InsertRow(SRow,ERow);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 插入列
     * @param action
     */
    public void onInsertCol(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            int SRow, ERow;
            int SCol, ECol;
            try {
                SCol = book.getSelStartCol() + 1;
                ECol = book.getSelEndCol() + 1;
                book.editInsert(JBook.eShiftColumns);
//        ReportModel.InsertCol(SCol, ECol);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 插入工作表
     * @param action
     */
    public void onInsertWorksheet(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            int SRow, ERow;
            int SCol, ECol;
            try {
                book.editInsertSheets();
//        book.setModified();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 插入分页符
     * @param action
     */
    public void onInsertPageBreak(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            int SRow, ERow;
            int SCol, ECol;
            try {
                book.addPageBreak();
//        book.setModified();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 插入超连接
     * @param action
     */
    public void onInsertHyperLink(JBookAction action) {

        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.HyperlinkDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 正常状态
     * @param action
     */
    public void onInsertGraphicsNormal(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeNormal);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 图表
     * @param action
     */
    public void onInsertGraphicsChart(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeChart);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 图片
     * @param action
     */
    public void onInsertGraphicsPicture(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModePicture);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 曲线
     * @param action
     */
    public void onInsertGraphicsCurve(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeArc);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 直线
     * @param action
     */
    public void onInsertGraphicsLine(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeLine);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 圆形
     * @param action
     */
    public void onInsertGraphicsRound(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeOval);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 多边形
     * @param action
     */
    public void onInsertGraphicsPolygon(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModePolygon);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 矩形
     * @param action
     */
    public void onInsertGraphicsRectangle(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeRectangle);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 插入表单按钮
     * @param action
     */
    public void onInsertFormButton(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeButton);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 插入表单多选按钮
     * @param action
     */
    public void onInsertFormCheckbox(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeCheckBox);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 插入表单下拉列表
     * @param action
     */
    public void onInsertFormCombobox(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeDropDown);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 插入表单列表
     * @param action
     */
    public void onInsertFormList(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeListBox);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 插入表单单选按钮
     * @param action
     */
    public void onInsertFormRationButton(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeRadioButton);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 插入表单文本框
     * @param action
     */
    public void onInsertFormTextArea(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                book.setMode(JBook.eModeText);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 删除行
     * @param action
     */
    public void onDeleteRow(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            int SRow, ERow;
            try {
                SRow = book.getSelStartRow() + 1;
                ERow = book.getSelEndRow() + 1;
                book.editDelete(JBook.eShiftRows);
//        ReportModel.DeleteRow(SRow, ERow);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除列
     * @param action
     */
    public void onDeleteCol(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            int SCol, ECol;
            try {
                SCol = book.getSelStartCol() + 1;
                ECol = book.getSelEndCol() + 1;
                book.editDelete(JBook.eShiftColumns);
//        ReportModel.DeleteCol(SCol, ECol);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除工作表
     * @param action
     */
    public void onDeleteWorksheet(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                if (book.getNumSheets() > 1 &&
                    book.getSheet() != book.getNumSheets() - 1) {
                    book.editDeleteSheets();
//          book.setModified();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除分页符
     * @param action
     */
    public void onDeletePageBreak(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            int SRow, ERow;
            int SCol, ECol;
            try {
                book.removePageBreak();
//        book.setModified();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 删除超连接
     * @param action
     */
    public void onDeleteHyperLink(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            int SRow, ERow;
            int SCol, ECol;
            try {
//         JBookActionUtils.removeHyperLink(book,SRow,Scol,ERow,ECol);
//        book.setModified();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //---------------------------------------------------------------------------
    // 格式
    //---------------------------------------------------------------------------
    /**
     * 单元格格式
     * @param action
     */
    public void onCell(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.FormatCellsDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 黑体
     * @param action
     */
    public void onBold(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setFontBold(!cf.isFontBold());
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onItalic(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setFontItalic(!cf.isFontItalic());
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @param action
     */
    public void onUnderline(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setFontUnderline(!cf.isFontUnderline());
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onLeft(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onCenter(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onRight(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onLeft2Right(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setOrientation(CellFormat.eOrientationNone);
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onTop2Bottom(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setOrientation(CellFormat.eOrientationTopToBottom);
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @param action
     */
    public void onMoney(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setValueFormat(JXMLResource.Money + "#,##0.00");
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onPercent(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setValueFormat("0.00%");
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @param action
     */
    public void onDot(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setValueFormat("#,##0.00");
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onAddZero(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                String NumberFmt = cf.getValueFormat();
                int p = NumberFmt.indexOf(".");
                if (p != -1) {
                    NumberFmt = NumberFmt + "0";
                }
                else {
                    NumberFmt = (NumberFmt.equals("General") ? ("#,##0.0") :
                                 NumberFmt + ".0");
                }
                cf.setValueFormat(NumberFmt);
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @param action
     */
    public void onDeleteZero(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                String NumberFmt = cf.getValueFormat();
                int p = NumberFmt.indexOf(".");
                if (p != -1) {
                    NumberFmt = NumberFmt.substring(0, NumberFmt.length() - 1);
                }
                cf.setValueFormat(NumberFmt);
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @param action
     */
    public void onSubSpace(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.setTextMargin(book, 0);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onAddSpace(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.setTextMargin(book, 1);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @param action 需要显示的坐标,否则屏幕居中
     */
    public void onBorder(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            Point location = null;
            if (action.getParam() != null) {
                location = (Point) action.getParam();
            }
            try {
                JLinePanelDlg dlg = new JLinePanelDlg();
                if (location == null) {
                    dlg.CenterWindow();
                }
                else {
                    dlg.setLocation(location);
                }
                dlg.setCustomObject(book);
                dlg.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     *
     * @param action 需要显示的坐标,否则屏幕居中
     */
    public void onFontColor(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            Point location = null;
            if (action.getParam() != null) {
                location = (Point) action.getParam();
            }

            try {
                JColorPanelDlg dlg = new JColorPanelDlg();
                if (location == null) {
                    dlg.CenterWindow();
                }
                else {
                    dlg.setLocation(location);
                }
                dlg.setCustomObject(book);
                dlg.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action 需要显示的坐标,否则屏幕居中
     */
    public void onBackColor(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            Point location = null;
            if (action.getParam() != null) {
                location = (Point) action.getParam();
            }

            try {
                JColorPanelDlg dlg = new JColorPanelDlg();
                if (location == null) {
                    dlg.CenterWindow();
                }
                else {
                    dlg.setLocation(location);
                }
                dlg.isBack = true;
                dlg.setCustomObject(book);
                dlg.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @param action
     */
    public void onTop(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setVerticalAlignment(CellFormat.eVerticalAlignmentTop);
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onVerticalCenter(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onBottom(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setVerticalAlignment(CellFormat.eVerticalAlignmentBottom);
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param action
     */
    public void onMerge(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                CellFormat cf = book.getCellFormat();
                cf.setMergeCells(!cf.isMergeCells());
                book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 图形控制
     * @param action
     */
    public void onGraphics(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 行高
     * @param action
     */
    public void onRowHeight(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.RowHeightDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 最佳行高
     * @param action
     */
    public void onRowPreferHeight(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                int SRow, SCol, ERow, ECol;
                SRow = book.getSelStartRow();
                SCol = book.getSelStartCol();
                ERow = book.getSelEndRow();
                ECol = book.getSelEndCol();
                book.setRowHeightAuto(SRow, SCol, ERow, ECol, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 隐藏行
     * @param action
     */
    public void onRowHide(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                int SRow, SCol, ERow, ECol;
                SRow = book.getSelStartRow();
                SCol = book.getSelStartCol();
                ERow = book.getSelEndRow();
                ECol = book.getSelEndCol();
                book.setRowHidden(SRow, ERow, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示行
     * @param action
     */
    public void onRowShow(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                int SRow, SCol, ERow, ECol;
                SRow = book.getSelStartRow();
                SCol = book.getSelStartCol();
                ERow = book.getSelEndRow();
                ECol = book.getSelEndCol();
                book.setRowHidden(SRow, ERow, false);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 列宽
     * @param action
     */
    public void onColWidth(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.ColWidthDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 自动列宽
     * @param action
     */
    public void onColPreferWidth(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                int SRow, SCol, ERow, ECol;
                SRow = book.getSelStartRow();
                SCol = book.getSelStartCol();
                ERow = book.getSelEndRow();
                ECol = book.getSelEndCol();
                book.setColWidthAuto(SRow, SCol, ERow, ECol, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 隐藏列
     * @param action
     */
    public void onColHide(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                int SRow, SCol, ERow, ECol;
                SRow = book.getSelStartRow();
                SCol = book.getSelStartCol();
                ERow = book.getSelEndRow();
                ECol = book.getSelEndCol();
                book.setColHidden(SCol, ECol, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示列
     * @param action
     */
    public void onColShow(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                int SRow, SCol, ERow, ECol;
                SRow = book.getSelStartRow();
                SCol = book.getSelStartCol();
                ERow = book.getSelEndRow();
                ECol = book.getSelEndCol();
                book.setColHidden(SCol, ECol, false);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重命名工作区
     * @param action
     */
    public void onWorksheetRename(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                int whichSheet = book.getSheet();
                String initSheetName = book.getSheetName(whichSheet);
                if (initSheetName != null) {
                    String sheetName = JOptionPane.showInputDialog(book, res.getString("String_98"),
                        initSheetName);
                    if (sheetName != null) {
                        book.setSheetName(whichSheet, sheetName);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 隐藏工作区
     * @param action
     */
    public void onWorksheetHide(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.SheetVisibilityDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 显示工作区
     * @param action
     */
    public void onWorksheetShow(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.SheetVisibilityDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 工作区属性
     * @param action
     */
    public void onWorksheetAttribute(JBookAction action) {

//                FormatSheetDlg dlg = new FormatSheetDlg(book);

        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.FormatSheetDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 对象选项
     * @param action
     */
    public void onObjectOption(JBookAction action) {

//                    FormatObjectDlg dlg = new FormatObjectDlg(book);

        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.FormatSheetDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 对象置前
     * @param action
     */
    public void onObjectBring2Front(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                if (book.getSelectedObjectCount() > 0) {
                    book.objectBringToFront();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对象置后
     * @param action
     */
    public void onObjectSend2Back(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                if (book.getSelectedObjectCount() > 0) {
                    book.objectSendToBack();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 对象分开
     * @param action
     */
    public void onObjectUngroup(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
//        CellFormat cf = book.getCellFormat();
//        cf.setMergeCells(false);
//        book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 对象合并
     * @param action
     */
    public void onObjectGroup(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
//        CellFormat cf = book.getCellFormat();
//        cf.setMergeCells(true);
//        book.setCellFormat(cf);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //---------------------------------------------------------------------------
    // 打印
    //---------------------------------------------------------------------------
    /**
     * 打印页面设置
     * @param action
     */
    public void onPrintPageSetup(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                JBookActionUtils.invakeJBookDialog(com.f1j.swing.ss.PageSetupDlg.class, book, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 打印区域
     * @param action
     */
    public void onPrintArea(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
              String printAreaFormula = JBookActionUtils.coor2Formula(book,
                  book.getSelStartRow() , book.getSelStartCol() ,
                    book.getSelEndRow() , book.getSelEndCol() );
                if (printAreaFormula != null && printAreaFormula.length() > 0) {
                    book.setPrintArea(printAreaFormula);
                    printAreaFormula=book.getPrintArea() ;
                    //2008-4-25 fengbo
                    //1 存储修正前的打印区域
                    JGenerQueryWindow queryWindow=(JGenerQueryWindow)action.getParam() ;
                    JActiveDComDM.LocalRegistry.Put(queryWindow.QO.Param + "PrintArea",printAreaFormula);

                    //2 当选择列区域时譬如A:D，修正打印区域为A1:Dn,其中n为实际行数；如果不修正，行数是10亿
                    int index=printAreaFormula.indexOf("!") ;
                    //截掉Sheet!
                    if(index>-1){
                      printAreaFormula=printAreaFormula.substring(index+1) ;
                    }
                    index=printAreaFormula.indexOf(":") ;
                    String startCell=null;
                    String endCell=null;
                    if(index>-1){
                      startCell=printAreaFormula.substring(0,index);
                      endCell=printAreaFormula.substring(index+1);
                      //仅指定了开始列
                      if(isAlphs(startCell)){
                        startCell=startCell+"1";
                      }
                      //仅指定了结束列
                      if(isAlphs(endCell)){
                        endCell=endCell+(book.getLastRow()+1);
                      }
                      printAreaFormula=   startCell+ ":"  + endCell;
                    }

                    //3 重新设置打印区域
                    book.setPrintArea(printAreaFormula);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private  boolean isAlphs(String s){
      boolean tag=true;
      char [] array=s.toCharArray();
      for(int i=0;i<array.length ;i++){
        if(array[i]>='0' && array[i]<='9'){
          tag=false;
          break;
        }
      }
      return tag;
  }

    /**
     * 取消打印区域
     * @param action
     */
    public void onCancelPrintArea(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                String printAreaFormula = JBookActionUtils.coor2Formula(book, 0, 0, 0,
                    0);
                if (printAreaFormula != null && printAreaFormula.length() > 0) {
                    book.setPrintArea(printAreaFormula);
                }
                //2008-4-25 fengbo
                //取消打印区域等价于恢复默认值,参见DrawObject2.setPrintArea()方法
                JGenerQueryWindow queryWindow=(JGenerQueryWindow)action.getParam() ;
                JActiveDComDM.LocalRegistry.Put(queryWindow.QO.Param + "PrintArea","");
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 打印标题
     * @param action
     */
    public void onPrintTitle(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                String printAreaFormula = JBookActionUtils.coor2Formula(book, 0, 0, 0, 0);
                if (printAreaFormula != null && printAreaFormula.length() > 0) {
                    book.setPrintTitles(printAreaFormula);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打印预览
     * @param action
     */
    public void onPrintPreview(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
        }
    }

    /**
     * 打印
     * @param action
     */
    public void onPrint(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
        }
    }

    /**
     *
     * @param action
     */
    public void onFreezePanes(JBookAction action) {
      if (action != null && action.getBook() != null) {
        JBook book = action.getBook();
        try {
          //选择区域个数为1而且不是(0,0)
          int selCount = book.getSelectionCount();

          if (selCount != 1) {
            return;
          }
          int startRow = book.getSelStartRow();
          int startCol = book.getActiveCol();
          if (startRow == 0 && startCol == 0) {
            return;
          }
          //冻结
          book.setFixedRow(0);
          book.setFixedCol(0);
          book.setFixedRows(startRow);
          book.setFixedCols(startCol);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    /**
     *
     * @param action
     */
    public void onDefreezePanes(JBookAction action) {
      if (action != null && action.getBook() != null) {
        JBook book = action.getBook();
        try {
          //撤销冻结
          book.setFixedRows(0);
          book.setFixedCols(0);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
