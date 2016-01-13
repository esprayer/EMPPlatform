package jdatareport.dof.classes.report.ext.sort.ui;

import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import com.borland.jbcl.layout.*;
import jdatareport.dof.classes.report.ext.sort.util.*;
import jfoundation.gui.window.classes.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQuerySortDialog
    extends JFrameDialog implements ActionListener {
    /**
     *
     */
    private JQuerySortTableModel mUnselModel = new JQuerySortUnselTableModel();
    private JQuerySortTableModel mSelModel = new JQuerySortSelTableModel();
    /**
     *
     */
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private Border border1;
    private FlowLayout flowLayout1 = new FlowLayout();
    private JButton mCancelBtn = new JButton();
    private JButton mOkBtn = new JButton();
    private BorderLayout borderLayout3 = new BorderLayout();
    private Border border2;
    private Border border3;
    private JPanel jPanel8 = new JPanel();
    private BorderLayout borderLayout6 = new BorderLayout();
    private VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    private JButton mSelAllBtn = new JButton();
    private JButton mUpBtn = new JButton();
    private JButton mDownBtn = new JButton();
    private JPanel jPanel4 = new JPanel();
    private JScrollPane mLeftSp = new JScrollPane();
    private JPanel jPanel7 = new JPanel();
    private JButton mSelBtn = new JButton();
    private BorderLayout borderLayout4 = new BorderLayout();
    private JPanel jPanel6 = new JPanel();
    private JButton mUnselAllBtn = new JButton();
    private BorderLayout borderLayout5 = new BorderLayout();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private JButton mUnselBtn = new JButton();
    private JPanel jPanel5 = new JPanel();
    private JScrollPane mRightSp = new JScrollPane();
    private Border border4;
    private JTable mUnselTable = new JTable();
    private JTable mSelTable = new JTable();
    private Border border5;
    /**
     *
     * @param parent
     * @param title
     * @param isModal
     */
    public JQuerySortDialog(Frame parent, String title, boolean isModal) {
        super(parent, title, isModal);
        try {
            jbInit();
            initGUI();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(103, 101, 98), new Color(148, 145, 140));
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        border3 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        border4 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border5 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        this.getContentPane().setLayout(borderLayout1);
        jPanel1.setLayout(borderLayout2);
        jPanel2.setBorder(border1);
        jPanel2.setLayout(borderLayout3);
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        mCancelBtn.setText("取消");
        mOkBtn.setText("确定");
        jPanel8.setLayout(borderLayout6);
        verticalFlowLayout2.setAlignment(VerticalFlowLayout.MIDDLE);
        mSelAllBtn.setPreferredSize(new Dimension(25, 26));
        mSelAllBtn.setText(">>");
        mUpBtn.setText("上移");
        mDownBtn.setText("下移");
        jPanel4.setBorder(border2);
        jPanel4.setLayout(borderLayout4);
        mLeftSp.setPreferredSize(new Dimension(100, 403));
        jPanel7.setLayout(borderLayout5);
        jPanel7.setBorder(border3);
        mSelBtn.setPreferredSize(new Dimension(25, 26));
        mSelBtn.setText(">");
        jPanel6.setLayout(verticalFlowLayout1);
        mUnselAllBtn.setPreferredSize(new Dimension(25, 26));
        mUnselAllBtn.setText("<<");
        verticalFlowLayout1.setAlignment(VerticalFlowLayout.MIDDLE);
        mUnselBtn.setPreferredSize(new Dimension(25, 26));
        mUnselBtn.setText("<");
        jPanel5.setLayout(verticalFlowLayout2);
        mRightSp.setPreferredSize(new Dimension(200, 403));
        jPanel8.setBorder(border4);
        jPanel6.setPreferredSize(new Dimension(161, 129));
        jPanel5.setBorder(border5);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel8, BorderLayout.CENTER);
        jPanel5.add(mUpBtn, null);
        jPanel5.add(mDownBtn, null);
        jPanel7.add(mRightSp, BorderLayout.CENTER);
        mRightSp.getViewport().add(mSelTable, null);
        jPanel8.add(jPanel6, BorderLayout.CENTER);
        jPanel7.add(jPanel5, BorderLayout.EAST);
        jPanel6.add(mSelBtn, null);
        jPanel6.add(mUnselBtn, null);
        jPanel6.add(mSelAllBtn, null);
        jPanel6.add(mUnselAllBtn, null);
        jPanel8.add(jPanel4, BorderLayout.WEST);
        jPanel4.add(mLeftSp, BorderLayout.CENTER);
        mLeftSp.getViewport().add(mUnselTable, null);
        jPanel8.add(jPanel7, BorderLayout.EAST);
        jPanel1.add(jPanel3, BorderLayout.SOUTH);
        jPanel3.add(mOkBtn, null);
        jPanel3.add(mCancelBtn, null);
    }

    /**
     *
     */
    private void initGUI() {
        this.mUnselTable.setModel(this.mUnselModel);
//        this.mUnselTable.addMouseListener(new MouseAdapter(){
//            public void mousePressed(MouseEvent me){
//                if(me.getSource() == mUnselTable && me.getClickCount() ==2){
//                    onSelect();
//                }
//            }
//        });

        this.mSelTable.setModel(this.mSelModel);
//        this.mSelTable.addMouseListener(new MouseAdapter(){
//            public void mousePressed(MouseEvent me){
//               if(me.getSource() == mSelTable && me.getClickCount() ==2){
//                   onUnselect();
//               }
//           }
//        });

        TableColumn tableCol = mSelTable.getColumnModel().getColumn(1);
        String[] names = new String[] {
            "升序", "降序"};
        JComboBox editorComp = new JComboBox(names);
        tableCol.setCellEditor(new DefaultCellEditor(editorComp));
        this.mSelBtn.addActionListener(this);
        this.mUnselBtn.addActionListener(this);
        this.mSelAllBtn.addActionListener(this);
        this.mUnselAllBtn.addActionListener(this);
        this.mUpBtn.addActionListener(this);
        this.mDownBtn.addActionListener(this);
        this.mOkBtn.addActionListener(this);
        this.mCancelBtn.addActionListener(this);
        this.setSize(470, 300);
        this.setMinimumSize(470, 300);
    }

    /**
     *
     * @param sortCols
     */
    public void setSortColumn(Vector sortCols) {
        //init model
        if (sortCols != null) {
            mUnselModel.setSortColumns(sortCols);
        }
    }

    /**
     *
     */
    public void onSelect() {
        Vector sortCols = this.getSelectedSortColumns(this.mUnselTable);
        if (sortCols != null) {
            for (Enumeration e = sortCols.elements(); e.hasMoreElements(); ) {
                JQuerySortColumn crtSortCol = (JQuerySortColumn) e.nextElement();
                mSelModel.addSortColumn(crtSortCol);
                mUnselModel.removeSortColumn(crtSortCol);
            }
        }
        refreshUI();
    }

    /**
     *
     */

    public void onUnselect() {
        Vector sortCols = this.getSelectedSortColumns(this.mSelTable);
        if (sortCols != null) {
            for (Enumeration e = sortCols.elements(); e.hasMoreElements(); ) {
                JQuerySortColumn crtSortCol = (JQuerySortColumn) e.nextElement();
                mUnselModel.addSortColumn(crtSortCol);
                mSelModel.removeSortColumn(crtSortCol);
            }
        }
        refreshUI();
    }

    /**
     *
     */

    public void onSelectAll() {
        Vector sortCols = this.getAllSortColumn(this.mUnselTable);
        if (sortCols != null) {
            for (Enumeration e = sortCols.elements(); e.hasMoreElements(); ) {
                JQuerySortColumn crtSortCol = (JQuerySortColumn) e.nextElement();
                mSelModel.addSortColumn(crtSortCol);
            }
            mUnselModel.removeAllSortColumn();
        }
        refreshUI();
    }

    /**
     *
     */

    public void onUnselectAll() {
        Vector sortCols = this.getAllSortColumn(this.mSelTable);
        if (sortCols != null) {
            for (Enumeration e = sortCols.elements(); e.hasMoreElements(); ) {
                JQuerySortColumn crtSortCol = (JQuerySortColumn) e.nextElement();
                mUnselModel.addSortColumn(crtSortCol);
            }
            mSelModel.removeAllSortColumn();
        }
        refreshUI();
    }

    /**
     *
     */
    public void refreshUI() {
        this.mUnselTable.updateUI();
        this.mSelTable.updateUI();
    }

    /**
     *
     */
    public void onUp() {
        int selRow = this.mSelTable.getSelectedRow();
        if (selRow != -1 && selRow > 0) {
            int sourceRow = selRow;
            int targetRow = selRow - 1;
            JQuerySortColumn sourceSortCol = this.mSelModel.getSortColumn(sourceRow);
            this.mSelModel.getSortColumns().remove(sourceRow);
            this.mSelModel.getSortColumns().insertElementAt(sourceSortCol, targetRow);
        }
        this.refreshUI();
    }

    /**
     *
     */
    public void onDown() {
        int selRow = this.mSelTable.getSelectedRow();
        if (selRow != -1 && selRow < mSelModel.getRowCount() - 1) {
            int sourceRow = selRow;
            int targetRow = selRow + 1;
            JQuerySortColumn sourceSortCol = this.mSelModel.getSortColumn(sourceRow);
            this.mSelModel.getSortColumns().remove(sourceRow);
            this.mSelModel.getSortColumns().insertElementAt(sourceSortCol, targetRow);
        }
        this.refreshUI();

    }

    /**
     *
     */
    public void onOk() {
        super.OnOk();
    }

    /**
     *
     */
    public void onCancel() {
        super.OnCancel();
    }

    /**
     *
     * @return
     */
    private Vector getSelectedSortColumns(JTable table) {
       Vector cols = new Vector();

        JQuerySortTableModel model = (JQuerySortTableModel) table.getModel();
        int[] selRows = table.getSelectedRows();
        int selRowCount = table.getSelectedRowCount();
        for (int i = 0; i < selRowCount; i++) {
            int crtSelRow = selRows[i];
            JQuerySortColumn crtSortCOl = model.getSortColumn(crtSelRow);
            if (crtSortCOl != null) {
                cols.add(crtSortCOl);
            }
        }
        return cols;
    }

    /**
     *
     * @return
     */
    private Vector getAllSortColumn(JTable table) {
        JQuerySortTableModel model = (JQuerySortTableModel) table.getModel();
        return model.getSortColumns();
    }

    /**
     *
     * @return
     */
    public Vector getSortColumns() {
        return this.mSelModel.getSortColumns();
    }
    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.mSelBtn){
            onSelect();
        }else if(e.getSource()==this.mUnselBtn){
            onUnselect();
        }else if(e.getSource()==this.mSelAllBtn){
            onSelectAll();
        }else if(e.getSource()==this.mUnselAllBtn){
            onUnselectAll();
        }else if(e.getSource()==this.mUpBtn){
            onUp();
        }else if(e.getSource()==this.mDownBtn){
            onDown();
        }else if(e.getSource()==this.mOkBtn){
            onOk();
        }else if(e.getSource()==this.mCancelBtn){
            onCancel();
        }
    }
}
