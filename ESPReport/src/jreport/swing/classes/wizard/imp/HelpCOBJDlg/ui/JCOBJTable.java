package jreport.swing.classes.wizard.imp.HelpCOBJDlg.ui;

import java.util.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import jreport.swing.classes.wizard.*;
import jreport.swing.classes.wizard.imp.HelpCOBJDlg.util.*;
import jreport.swing.classes.wizard.imp.HelpCOBJDlg.help.*;

import jreport.swing.classes.wizard.imp.HelpCOBJDlg.*;
import javax.swing.event.*;
import javax.swing.event.ListSelectionEvent;
import jreport.swing.classes.wizard.imp.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCOBJTable
    extends JTable
    implements JCOBJConstants {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.HelpCOBJDlg.ui.Language");

    /**
     *
     */
    private JHelpCOBJDlg mDlg = null;
    private JCOBJHelpObject mHelpObj = new JCOBJHelpObject();
    private JLimitObjectStub mLos = null;
    private Vector mCompareItems = new Vector();

    /**
     *
     */

    /**
     *
     */
    public JCOBJTable(JHelpCOBJDlg dlg, JLimitObjectStub los) {
        super(new JCOBJTableModel());
        this.mDlg = dlg;
        mLos = los;
        init();
    }

    /**
     *
     */
    private void init() {
        this.mCompareItems = JCOBJUtils.getCompareItems(mLos);
        this.setRowHeight(DEFAULT_ROW_HEIGHT);
        int width = 150;
        TableColumn col = this.getColumnModel().getColumn(1);
        col.setPreferredWidth(width);
        col = this.getColumnModel().getColumn(3);
        col.setPreferredWidth(width);

        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setRowSelectionInterval(0, 0);
    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    public TableCellEditor getCellEditor(int row, int col) {
        /**@todo Override this javax.swing.JTable method*/
        DefaultCellEditor cellEditor = null;
        if (col == 0) {
            JComboBox mEditor0 = null;
            mEditor0 = new JComboBox(this.OP_LEFT);
            cellEditor = new DefaultCellEditor(mEditor0);
        }
        else if (col == 1) {
            //比较项目
            JComboBox mEditor1 = null;
            mEditor1 = new JComboBox(mCompareItems);
            mEditor1.setName("CompareItem");
            mEditor1.addFocusListener(mDlg);
            cellEditor = new DefaultCellEditor(mEditor1);
        }
        else if (col == 2) {
            JComboBox mEditor2 = null;
            mEditor2 = new JComboBox(this.OP_COMPARE);
            cellEditor = new DefaultCellEditor(mEditor2);
        }
        else if (col == 3) {
            JConditionObjectStub itemInfo = (JConditionObjectStub)this.getValueAt(row, 1);
            if (itemInfo != null && mHelpObj.isHelpable(itemInfo)) {
                JCOBJHelpTextField mEditor3 = null;
                mEditor3 = new JCOBJHelpTextField();
                JButton helpBtn = mEditor3.getButton();
                if (helpBtn != null) {
                    helpBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            onHelp();
                        }
                    });
                }
                cellEditor = new DefaultCellEditor(mEditor3);
            }else{
              JTextField mEditor = new JTextField();
              cellEditor = new DefaultCellEditor(mEditor);
            }
        }
        else if (col == 4) {
            JComboBox mEditor4 = null;
            mEditor4 = new JComboBox(this.OP_RIGHT);
            cellEditor = new DefaultCellEditor(mEditor4);
        }
        else if (col == 5) {
            JComboBox conCB = null;
            conCB = new JComboBox(this.OP_CONN);
            conCB.setName("ConnOperator");
            if(conCB != null ){
              conCB.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent e){
                  onOperatorChanged();
                }
              });
            }
            cellEditor = new DefaultCellEditor(conCB);
        }

        if (cellEditor != null) {
            return cellEditor;
        }
        return super.getCellEditor(row, col);
    }

    /**
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      /**
       * 如果是BOOLEAN型的不允许编号比较符列
       * modified by hufeng 05.9.5
       */
      if(columnIndex == 2){
        JConditionObjectStub itemInfo = (JConditionObjectStub)this.getValueAt(rowIndex, 1);
        if (itemInfo != null && itemInfo.text.length() > 0) {
          if (itemInfo.type.endsWith("B")) {
            return false;
          }
          else {
            return true;
          }
        }

      }
      else if (columnIndex == 3) {
        //如果比较项目为空则比较值不可编辑
        JConditionObjectStub itemInfo = (JConditionObjectStub)this.getValueAt(rowIndex, 1);
        if (itemInfo != null && itemInfo.text.length() > 0) {
//          if (itemInfo.id.toUpperCase().endsWith("MX")) {
          if (itemInfo.type.endsWith("B")) {
            return false;
          }
          else {
            return true;
          }
        }
        else {
          return false;
        }
      }
      return true;
    }

    /**
     *
     */
    private void onHelp() {
        int row = this.getSelectedRow();
        JConditionObjectStub itemInfo = (JConditionObjectStub)this.getValueAt(row, 1);
        if (itemInfo != null && itemInfo.text.length() > 0) {
            Object data = mHelpObj.showHelp(itemInfo,this.mLos);
            if (data != null) {
                DefaultCellEditor cellEditor = (DefaultCellEditor)this.getCellEditor();
                JCOBJHelpTextField ctf = (JCOBJHelpTextField) cellEditor.getComponent();
                ctf.setText( (String) data);
                this.setValueAt(data, row, 3);
            }
        }

    }

    /**
     * 当前如果是最后一行的话，自动插入下一行
     */
    private void onOperatorChanged(){
      if(this.getSelectedRow() == (this.getRowCount() - 1)){
       this.addRow();
      }
    }

    /**
     *
     * @param row
     */
    public void addRow() {
        JCOBJTableModel model = (JCOBJTableModel)this.getModel();
        if (model != null) {
            model.addRow();
            int rowCount = this.getRowCount();
        }
    }

    /**
     *
     * @param row
     */
    public void insertRow(int row) {
        JCOBJTableModel model = (JCOBJTableModel)this.getModel();
        if (model != null) {
            model.insertRow(row);
        }

    }

    /**
     *
     * @param row
     */
    public void removeRow(int row) {
        JCOBJTableModel model = (JCOBJTableModel)this.getModel();
        if (model != null) {
            model.removeRow(row);
            if (row > 0) {
                if (row == this.getRowCount()) {
                    this.setRowSelectionInterval(row - 1, row - 1);
                }
                else {
                    this.setRowSelectionInterval(row, row);
                }
            }
        }
    }

    /**
     *
     * @param row
     * @param start
     * @param length
     */
    public void substring(boolean isSubstring,int row, int start, int length) {
        if (row != -1) {
            JCOBJTableModel model = (JCOBJTableModel)this.getModel();
            if (model != null) {
                model.substring(isSubstring,row, start, length);
                this.updateUI();
            }
        }
    }

    /**
     * 获得条件表达式
     */
    public JCOBJExpression getExpression() {
        JCOBJExpression exp = new JCOBJExpression();
        exp.mShowValue = JCOBJUtils.getShowExp(this);
        exp.mStoreValue = JCOBJUtils.getStoreExp(this);
        return exp;
    }

    /**
     *
     * @param row
     * @param column
     * @return
     */
    public TableCellRenderer getCellRenderer(int row, int column) {
        /**@todo Override this javax.swing.JTable method*/
        if (column == 1) {
            return new MyCellRenderer(row, column);
        }
        return super.getCellRenderer(row, column);
    }

    class MyCellRenderer
        extends DefaultTableCellRenderer {
        int mRow = 0;
        int mCol = 0;
        public MyCellRenderer(int row, int col) {
            mRow = row;
            mCol = col;
        }

        protected void setValue(Object value) {
            if(value == null){
                setText("");
            }
            if (mRow != -1) {
                JConditionObjectStub itemInfo = (JConditionObjectStub) value;
                if (itemInfo != null) {
                    JCOBJTableModel model = (JCOBJTableModel) getModel();
                    JCOBJExpressionItem expItem = (JCOBJExpressionItem) model.getExps().get(mRow);
                    if (expItem != null && expItem.isSubstring()) {
                        setText("取子串(" + itemInfo.text + "," + expItem.mStart + "," + expItem.mLength + ")");
                    }
                    else {
                        setText(itemInfo.text);
                    }
                }
            }

        }

    }
}
