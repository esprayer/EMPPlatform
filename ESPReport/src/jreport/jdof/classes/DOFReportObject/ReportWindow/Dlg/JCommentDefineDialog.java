package jreport.jdof.classes.DOFReportObject.ReportWindow.Dlg;

import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import com.eai.resources.TImages;

import jformservice.jdof.classes.JDataWindow;
import jfoundation.gui.window.classes.JFrameDialog;
import jreport.swing.classes.JReportModel;
import jreport.swing.classes.ReportBook.JBdhInfo;
import jreport.swing.classes.ReportBook.JBdhUtils;
import jreport.swing.classes.ReportBook.JCommentInfo;
import jreport.swing.classes.ReportBook.JUnitInfo;
import jreportfunction.pub.JReportPubFunc;
import java.util.Iterator;
import jreport.swing.classes.ReportBook.JJygsInfo;

/**
 * <p>Title: 批注定义窗口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: pansoft</p>
 * @author not attributable
 * @version 1.0
 */
public class JCommentDefineDialog
    extends JFrameDialog implements ActionListener {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.jdof.classes.DOFReportObject.ReportWindow.Dlg.Language");
    private JReportModel mReportModel = null; //
    private JUnitInfo mUnitInfo = null; //当前编辑的单元格
    private int JYGS_HOFFSET = 0;
    private int JYGS_LOFFSET = 0;

    public JCommentDefineDialog(Frame frame, String title, boolean modal, JReportModel model) {
        super(frame, title, modal);
        mReportModel = model;
        try {
            jbInit();
            initParam();
            setSize(545, 300);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化参数
     * @throws Exception
     */
    private void initParam() throws Exception {
        int phyRow = mReportModel.ReportView.getPhySelStartRow();
        int phyEndRow = mReportModel.ReportView.getPhySelEndRow();
        int phyCol = mReportModel.ReportView.getSelStartCol();
        int Row = mReportModel.ReportView.getRow() + 1;
        int Col = mReportModel.ReportView.getCol() + 1;
        int ECol = mReportModel.ReportView.getSelEndCol();
        int ERow = mReportModel.ReportView.getSelEndRow();

        //取UI
        int RowStatus = JBdhUtils.checkPhyRowStatus(mReportModel, phyRow);
        if (RowStatus == 3) { // 新变动行
            mUnitInfo = JBdhInfo.getBDUnitInfo(mReportModel, Row - 1, Col - 1, phyRow, phyCol);
        } else {
            mUnitInfo = JUnitInfo.GetUnitInfoByHL(Row, Col, 1, mReportModel);
        }
        Vector rows = new Vector();
        if (mUnitInfo != null && mUnitInfo.CommentList != null && mUnitInfo.CommentList.size() > 0) {
            JCommentInfo comment;
            for (Iterator it = mUnitInfo.CommentList.iterator(); it.hasNext(); ) {
                comment = (JCommentInfo) it.next();
                comment.Comment_Area = getRangeTextByUnit(comment.Comment_HOFFSET, comment.Comment_LOFFSET);
                rows.add(comment);
            }
        }

        mTable.setModel(new JTableModel(rows));

        TableColumn col = null;
        col = mTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(60);
        col = mTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(80);
        col = mTable.getColumnModel().getColumn(2);
        col.setPreferredWidth(120);
        col = mTable.getColumnModel().getColumn(3);
        col.setPreferredWidth(240);
    }

    /**
     * 取当前所选范围
     * @return String
     */
    private String getRangeText() {
        String T1, T2, Text = "";
        try {
            int SRow = mReportModel.ReportView.getSelStartRow() + 1;
            int SCol = mReportModel.ReportView.getSelStartCol() + 1;
            int ERow = mReportModel.ReportView.getSelEndRow() + 1;
            int ECol = mReportModel.ReportView.getSelEndCol() + 1;

            T1 = mReportModel.ReportView.formatRCNr(SRow - 1, SCol - 1, false);
            if (SRow == ERow && SCol == ECol) {
                Text = T1;
            } else {
                T2 = mReportModel.ReportView.formatRCNr(ERow - 1, ECol - 1, false);
                Text = T1 + ":" + T2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Text;
    }

    /**
     * 取当前所选范围
     * @return String
     */
    private String getRangeTextByUnit(int hoffset, int loffset) {
        String T1, T2, Text = "";
        try {
            int SRow = mUnitInfo.getHZD_ZB();
            int SCol = mUnitInfo.getLZD_ZB();
            int ERow = mUnitInfo.getHZD_ZB() + hoffset;
            int ECol = mUnitInfo.getLZD_ZB() + loffset;

            T1 = mReportModel.ReportView.formatRCNr(SRow - 1, SCol - 1, false);
            if (SRow == ERow && SCol == ECol) {
                Text = T1;
            } else {
                T2 = mReportModel.ReportView.formatRCNr(ERow - 1, ECol - 1, false);
                Text = T1 + ":" + T2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Text;
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        panelSouth.setLayout(flowLayout1);
        panelCenter.setBorder(BorderFactory.createEtchedBorder());
        addButton.setPreferredSize(new Dimension(80, 23));
        addButton.setMargin(new Insets(2, 2, 2, 2));
        addButton.setIcon(TImages.getIcon("ADD"));
        addButton.setMnemonic('A');
        addButton.setText(res.getString("String_165"));
        delButton.setPreferredSize(new Dimension(80, 23));
        delButton.setMargin(new Insets(2, 2, 2, 2));
        delButton.setIcon(TImages.getIcon("DEL"));
        delButton.setMnemonic('D');
        delButton.setText(res.getString("String_167"));
        saveButton.setPreferredSize(new Dimension(80, 23));
        saveButton.setMargin(new Insets(2, 2, 2, 2));
        saveButton.setIcon(TImages.getIcon("SAVE"));
        saveButton.setMnemonic('S');
        saveButton.setText("保存(S)");
        exitButton.setPreferredSize(new Dimension(80, 23));
        exitButton.setMargin(new Insets(2, 2, 2, 2));
        exitButton.setIcon(TImages.getIcon("EXIT"));
        exitButton.setMnemonic('E');
        exitButton.setText("退出(E)");
        addButton.addActionListener(this);
        delButton.addActionListener(this);
        saveButton.addActionListener(this);
        exitButton.addActionListener(this);
        panelSouth.add(addButton);
        panelSouth.add(delButton);
        panelSouth.add(saveButton);
        panelSouth.add(exitButton);
        mTable.setRowHeight(20);
        mTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        panelCenter.getViewport().add(mTable);
        this.getContentPane().add(panelCenter, java.awt.BorderLayout.CENTER);
        this.getContentPane().add(panelSouth, java.awt.BorderLayout.SOUTH);
        this.getContentPane().add(jPanel3, java.awt.BorderLayout.WEST);
        this.getContentPane().add(jPanel4, java.awt.BorderLayout.EAST);
        this.getContentPane().add(jPanel5, java.awt.BorderLayout.NORTH);
    }

    javax.swing.JScrollPane panelCenter = new JScrollPane();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel panelSouth = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    JButton addButton = new JButton();
    JButton delButton = new JButton();
    JButton saveButton = new JButton();
    JButton exitButton = new JButton();
    JTable mTable = new JTable();

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(ActionEvent e) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if (e.getSource() == addButton) {
            onNew();
        } else if (e.getSource() == delButton) {
            onDelete();
        } else if (e.getSource() == saveButton) {
            onSave();
        } else if (e.getSource() == exitButton) {
            onExit();
        }
        mTable.updateUI();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * 新建一条批注
     */
    private void onNew() {
        JTableModel model = (JTableModel) mTable.getModel();
        model.addRow();

        //取选择范围
        String range = "";
        if (mUnitInfo == null || mUnitInfo.CommentList == null || mUnitInfo.CommentList.size() == 0) {
            range = getRangeText();
        } else {
            // 如果已经有附注信息，则用上一条附注的范围
            JCommentInfo comment = (JCommentInfo) mUnitInfo.CommentList.get(mUnitInfo.CommentList.size() - 1);
            range = getRangeTextByUnit(comment.Comment_HOFFSET, comment.Comment_LOFFSET);
        }
        int lastRowIndex = model.getRowCount() - 1;
        model.setValueAt(range, lastRowIndex, 0);
        model.setValueAt(mUnitInfo.RowInfo.HZD_ORDE, lastRowIndex, 4);
        model.setValueAt(mUnitInfo.ColInfo.LZD_ORDE, lastRowIndex, 5);
    }

    /**
     * 删除一条批注
     */
    private void onDelete() {
        JTableModel model = (JTableModel) mTable.getModel();
        model.removeRow(mTable.getSelectedRow());
    }

    /**
     * 保存：保存的时候根据范围确定HZD_ORDE等的值
     */
    private void onSave() {
        JTableModel  model = (JTableModel) mTable.getModel();
        Vector     rowData = model.rowVector;
        mUnitInfo.createCommentList().removeAllElements();
        for (Iterator   it = rowData.iterator(); it.hasNext(); ) {
            JCommentInfo comment = (JCommentInfo) it.next();
            saveOffSetByArea(comment);
            mUnitInfo.CommentList.add(comment);
        }
        mUnitInfo.setModified();
        mUnitInfo.setCommentModified();
        mReportModel.setModified();
    }


    /**
     * 保存公式范围
     * @param jygs JJygsInfo
     * @param area String
     */
    private void saveOffSetByArea(JCommentInfo comment) {
        char c1, c2;
        String end;
        int pos, endRow, endCol;
        String area = comment.Comment_Area;
        pos = area.indexOf(":");
        if (pos <= 0) {
            comment.Comment_HOFFSET = 0;
            comment.Comment_LOFFSET = 0;
            return;
        }
        end = area.substring(pos + 1);
        //
        try {
            c1 = end.charAt(0);
            c2 = end.charAt(1);
            // 现在只考虑标题是2位的情况，如AB
            if (c2 >= 65) {
                endCol = (c1 - 64) * 26 + (c2 - 64);
                endRow = Integer.parseInt(end.substring(2));
            } else {
                endCol = (c1 - 64);
                endRow = Integer.parseInt(end.substring(1));
            }
            int SRow = mUnitInfo.getHZD_ZB();
            int SCol = mUnitInfo.getLZD_ZB();
            int hset = endRow - SRow;
            int lset = endCol - SCol;
            comment.Comment_HOFFSET  = hset;
            comment.Comment_LOFFSET  = lset;
        } catch (Exception e) {
            e.printStackTrace();
            comment.Comment_HOFFSET  = JYGS_HOFFSET;
            comment.Comment_LOFFSET  = JYGS_LOFFSET;
        }
    }


    /**
     * 退出
     */
    private void onExit() {
        super.OnCancel();
    }

    /**
     * Model
     */
    class JTableModel
        extends AbstractTableModel {

        public Vector rowVector;

        public JTableModel(Vector data) {
            rowVector = data;
        }

        public int getRowCount() {
            if (rowVector != null)
                return rowVector.size();
            return 0;
        }

        public String getColumnName(int columnIndex) {
            if (columnIndex == 0)
                return "范围";
            if (columnIndex == 1)
                return "批注人";
            if (columnIndex == 2)
                return "批注时间";
            if (columnIndex == 3)
                return "批注内容";
            return "";
        }

        public int getColumnCount() {
            return 4;
        }

        public void addRow() {
            if (rowVector != null) {
                rowVector.add(JCommentInfo.CreateCommentInfo(mReportModel));
            }
        }

        public void removeRow(int rowIndex){
            if (rowVector != null && rowIndex < getRowCount()) {
                rowVector.removeElementAt(rowIndex);
            }
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowVector != null) {
                if (rowIndex < rowVector.size()) {
                    JCommentInfo comment = (JCommentInfo) rowVector.get(rowIndex);
                    switch(columnIndex){
                        case 0:
                            return comment.Comment_Area;
                        case 1:
                            return comment.Comment_User;
                        case 2:
                            return comment.Comment_Date;
                        case 3:
                            return comment.Comment_Info;
                        default:
                            return "";
                    }
                }
            }
            return "";
        }

        /**
         * 批注人和批注时间不允许修改！
         *
         * @param    rowIndex int
         * @param columnIndex int
         * @return            boolean
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex != 1 && columnIndex != 2;
        }

        public void setValueAt(Object aValue, int rowIndex) {
            if (rowVector != null) {
                if (rowIndex < rowVector.size())
                    rowVector.setElementAt(aValue, rowIndex);
            }
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (rowVector != null) {
                if (rowIndex < rowVector.size()) {
                    JCommentInfo comment = (JCommentInfo) rowVector.get(rowIndex);
                    switch(columnIndex){
                        case 0:
                            comment.Comment_Area = (String) aValue;
                            break;
                        case 1:
                            comment.Comment_User = (String) aValue;
                            break;
                        case 2:
                            comment.Comment_Date = (String) aValue;
                            break;
                        case 3:
                            comment.Comment_Info = (String) aValue;
                            comment.Comment_Date = JReportPubFunc.getLocalCurrentTime();
                            break;
                        case 4:
                            comment.HZD_ORDE = (String) aValue;
                            break;
                        case 5:
                            comment.LZD_ORDE = (String) aValue;
                            break;
                    }
                }
            }
        }
    }

}
