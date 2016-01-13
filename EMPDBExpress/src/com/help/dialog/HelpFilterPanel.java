package com.help.dialog;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.TableModelListener;
import java.awt.BorderLayout;
import com.efounder.eai.ide.ExplorerIcons;
import java.awt.Dimension;

import java.util.*;
import java.awt.*;

import com.help.data.HelpTableModel;
import com.jidesoft.swing.JideScrollPane;
import java.awt.event.*;
import com.efounder.builder.base.data.*;
import com.efounder.dbc.swing.editor.EnumCellEditor;
import com.efounder.dctbuilder.util.*;
import java.math.*;
import com.core.xml.*;
import com.efounder.builder.meta.domodel.*;
import org.openide.WaitingManager;
import com.efounder.bz.dbform.component.dc.table.header.TableGroupColumnHeader;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class HelpFilterPanel extends JPanel implements TableModel,ActionListener{
    JTable dataTable=null;
    JTable tjTable=new JTable();
    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    HelpTableModel htm=null;
    public HelpFilterPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setContentTable(JTable table){
       if( dataTable==table)return;
       dataTable=table;
       htm=(HelpTableModel)dataTable.getModel();
    }
    public int getRowCount() {
        if(htm==null)return 10;
       int row= htm.getColumnCount();
       if(row<10)row=10;
       return row;
    }
    public int getColumnCount() {
        return 3;
    }
    public String getColumnName(int col) {
        switch(col){
        case 0:return "A";
        case 1:return "B";
        case 2:return "C";
        }
        return "";
    }
    public Class getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int row, int col) {
        if(col>0&&htm!=null&&row<htm.getColumnCount())return true;
        return false;
    }
    public Object getValueAt(int row, int col) {
        if(htm==null||row>=htm.getColumnCount())return null;
        if(col==0){
            return htm.getColumnName(row);
        }
        String cn = "BJF";
        EFRowSet ers = (EFRowSet) htm.getColumnList().get(row);
        if (col == 2)
            cn = "BJVALUE";
        return ers==null?"":ers.getString(cn,"");
    }
    public void setValueAt(Object aValue, int row, int col) {
        if (htm != null && row < htm.getColumnCount()) {
            String cn="BJF";
            EFRowSet ers=(EFRowSet)htm.getColumnList().get(row);
            if(col==2)cn="BJVALUE";
            ers.putString(cn,(String)aValue);
        }
    }

    public void addTableModelListener(TableModelListener l) {
    }

    public void removeTableModelListener(TableModelListener l) {
    }
    JButton btFind=new JButton();
    JButton btFilter=new JButton();
    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jScrollPane1.setPreferredSize(new Dimension(454, 100));
        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
         btFind=new JButton();
        btFind.setIcon(ExplorerIcons.getExplorerIcon("idea/debugger/class_filter.png"));
//        btFind.setBorder(null);
        btFind.setToolTipText("查找");
//        btFilter.setIcon(ExplorerIcons.getExplorerIcon("idea/ant/filter.png"));
//        btFilter.setBorder(null);
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(btFind);
//        panel.add(btFilter);
        btFind.addActionListener(this);
        jScrollPane1.getViewport().add(tjTable);
//        jScrollPane1.setColumnFooterView(panel);
        tjTable.setRowHeight(20);
        tjTable.setModel(this);
        Map map=new Hashtable();
        java.util.List list=new ArrayList();
        String[]BJF=new String[]{"=","<>","[]","][",">","<",">=","<="};
        String[]BJMC=new String[]{"等于","不等于","包含","不包含","大于","小于","大等于","小等于"};
        for(int i=0;i<BJF.length;i++){
            map.put(BJF[i],BJMC[i]);
            list.add(BJF[i]);
        }
        EnumCellEditor ec=new EnumCellEditor(map,list);
        tjTable.getColumnModel().getColumn(1).setCellEditor(ec);
        tjTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        tjTable.getColumnModel().getColumn(1).setPreferredWidth(13);
        TableGroupColumnHeader tgc= new TableGroupColumnHeader(tjTable.getColumnModel());
        tjTable.setTableHeader(tgc);

    }
    JOperatorObject joo=new JOperatorObject();
    public void actionPerformed(ActionEvent e) {
        int col=tjTable.getSelectedColumn();
        int row = tjTable.getSelectedRow();
        if (tjTable.isEditing() && row != -1 && col != -1) {
            CellEditor editor=tjTable.getCellEditor(row, col);
            Object value = editor.getCellEditorValue();
            tjTable.setValueAt(value, row, col);
            editor.stopCellEditing();
        }
        WaitingManager.getDefault().beginWait(this);
        try{
            Filter();
        }finally{
            WaitingManager.getDefault().endWait(this);
        }
    }
    protected void Filter(){
        if(htm==null)return;
     java.util.List colList=htm.getColumnList();
     EFDataSet dataList=htm.getAllDataSet();
     EFDataSet fd=EFDataSet.getInstance(dataList.getTableName());
     fd.setPrimeKey(dataList.getPrimeKey());
     for(int i=0;i<dataList.getRowCount();i++){
         EFRowSet dataers=dataList.getRowSet(i);
         if(isMatch(dataers,colList))
             fd.insertRowSet(dataers);
     }
     htm.setDispDataSet(fd);
     dataTable.setVisible(false);
     dataTable.setVisible(true);

    }
    protected boolean isMatch(EFRowSet row, java.util.List colList) {
        String col,bjf;
        for(int i=0;i<colList.size();i++){
            EFRowSet colers=(EFRowSet)colList.get(i);
            String value = "";
            bjf=colers.getString("BJF","");
            String bjvalue=colers.getString("BJVALUE","");
            if(bjf.trim().length()>0&&bjvalue.trim().length()>0){
                col = colers.getString(SYS_OBJCOLS._COL_ID_, "");
                Object oo = row.getObject(col, "");
                if (oo != null)
                    value = oo.toString();
                Boolean b = (Boolean) JBOFClass.CallObjectMethod(joo,"CALC", bjf, value,
                        bjvalue);
                if(!b.booleanValue())return false;
            }
        }
        return true;
    }

}
