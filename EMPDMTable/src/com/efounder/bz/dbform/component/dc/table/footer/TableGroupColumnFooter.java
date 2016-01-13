package com.efounder.bz.dbform.component.dc.table.footer;

import javax.swing.*;
import javax.swing.table.*;

import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.footer.*;
import com.efounder.bz.dbform.datamodel.column.*;
import com.swing.table.MultiSpanCellTable;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TableGroupColumnFooter extends MultiSpanCellTable {
    /**
     *
     */
    private static final String uiClassID = "TableGroupColumnFooterUI";

    protected JTable table;

    /**
     *
     */
    public TableGroupColumnFooter(){
        TableModel tm = createDefaultTableModel(null);
        setModel(tm);
        initColumnFooter();
        setUI(new TableGroupColumnFooterUI());
    }
      public void setModel(TableModel dataModel) {
        super.setModel(dataModel);
      }

    /**
     *
     * @return TableModel
     */
    protected TableModel createDefaultTableModel(TableColumnModel tcm){
     GroupTableModel fixedModel=null;
      if(tcm==null){
         fixedModel = new GroupTableModel();
        fixedModel.setRowCount(1);
      }else{
        Object[][] columndata = new Object[1][tcm.getColumnCount()]; //ColumnFooter数组
        columndata[0][0]="合计：";
        Object[] columnname = new Object[tcm.getColumnCount()];
        fixedModel = new GroupTableModel(
            columndata, columnname);
      }

        return fixedModel;
    }
    FooterNumberRender defnumrender=new FooterNumberRender(this,null);
    public TableCellRenderer getCellRenderer(int row, int column) {
      GroupTableModel gtm=this.getTableModel();
        if(gtm!=null&&gtm.getColumnModel()!=null){
          Column cn=gtm.getColumnModel().getColumnByModelIndex(column);
          if(cn!=null&&(cn.isCalcSum()||cn.isCalcAvg()||cn.isCalcMax()||cn.isCalcMin())){
            defnumrender.setPattern(cn.getNumberFormat());
            return defnumrender;
          }
        }
         TableCellRenderer    renderer = getDefaultRenderer(getColumnClass(column));
         return renderer;
    }
    /**
     *
     * @param model TableModel
     */
    public TableGroupColumnFooter(TableModel model) {
        super(model);
        initColumnFooter();
        setUI(new TableGroupColumnFooterUI());
    }

    /**
     *
     * @param model TableColumnModel
     */
    public TableGroupColumnFooter(TableColumnModel model) {
        TableModel tm = createDefaultTableModel(model);
        setModel(tm);
        setColumnModel(model);
        initColumnFooter();
        setUI(new TableGroupColumnFooterUI());
    }
    /**
     *
     */
    private void initColumnFooter(){
      setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setShowGrid(true);
        setRowHeight(21);
        setRowMargin(1);
//        setCellSelectionEnabled(true);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setCellEditor(null);
        setEditingColumn( -1);
        setEditingRow( -1);
    }

    /**
     *
     * @return JTable
     */
    public JTable getTable() {
        return this.table;
    }

    /**
     *
     * @param table JTable
     */
    public void setTable(JTable table) {
        JTable old = this.table;
        this.table = table;
        firePropertyChange("table", old, table);
    }

    /**
     *
     */
//    protected GroupTableModel tableModel;

    /**
     *
     * @param model GroupTableModel
     */
//    public void setTableModel(GroupTableModel model) {
//        this.tableModel = model;
//        this.setModel(tableModel);
//    }

    /**
     *
     * @return GroupTableModel
     */
    public GroupTableModel getTableModel() {
        TableModel model = super.getModel();
        if (model != null && model instanceof GroupTableModel) {
            return (GroupTableModel) model;
        }
        return null;
    }

    /**
     *
     */
    protected ColumnFooterGroupModel groupModel = null;

    /**
     *
     * @param groupModel ColumnHeaderGroupModel
     */
    public void setGroupModel(ColumnFooterGroupModel groupModel) {
        this.groupModel = groupModel;
        GroupTableModel gtm=groupModel.getFooterModel();
        if(gtm==null)return;
        setModel(gtm);
        gtm.setColCount(this.getColumnCount());
    }

    /**
     *
     * @param g ColumnGroup
     */
    public void addColumnGroup(ColumnFooterGroup g) {
        groupModel.insertDataComponent(g);
    }

    /**
     *
     * @param col TableColumn
     * @return Enumeration
     */
    public java.util.List getColumnGroups() {
        if (groupModel == null || groupModel.getChildList() == null)
            return null;
        return groupModel.getChildList();
    }

}
