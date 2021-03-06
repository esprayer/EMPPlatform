package com.report.table;

import java.io.*;

import javax.swing.event.*;
import javax.swing.table.*;

import com.efounder.comp.treetable.AbstractTreeTableModel;
import com.efounder.comp.treetable.TreeTableModel;

import java.util.ResourceBundle;

public class GroupableHeaderTreeTableModel extends AbstractTreeTableModel
                             implements TreeTableModel, Serializable {

    static ResourceBundle res = ResourceBundle.getBundle("com.report.table.Language");
  protected Object[][] datas;
    protected Object[] columnIdentifiers;
    protected Object[] columnNames;
    public GroupableHeaderTreeTableModel() {
        this(null, 0);
    }
    public GroupableHeaderTreeTableModel(Object[] columnIdentifiers,int numRows) {
      super(null);
        this.columnIdentifiers = columnIdentifiers;
        datas = new Object[numRows][];
    }
    public Object[][] getDatas(){
        return datas;
    }
    public void setDatas(QueryNode[] QNArray,Object[] columnIdentifiers){
      QueryNode rq =  new QueryNode(res.getString("String_11"),null,null);//datas[0][0];
      rq.setChildQueryNode(QNArray);
      root = rq;
      this.datas = datas;
      this.columnIdentifiers = columnIdentifiers;
      // Generate notification
//        fireTableStructureChanged();
    }
    public Object[] getColumnIdentifiers(){
        return columnIdentifiers;
    }
    public void setColumnIdentifiers(Object[] columnIdentifiers) {
        this.columnIdentifiers = columnIdentifiers;
        // Generate notification
//        fireTableStructureChanged();
    }
    public Object[] getColumnNames(){
        return columnNames;
    }
    public void setColumnNames(Object[] columnNames){
        this.columnNames = columnNames;
        // Generate notification
//        fireTableStructureChanged();
    }
//
// Implementing the TableModel interface
//

    /**
     * Returns the number of rows in this data table.
     * @return the number of rows in the model
     */
    public int getRowCount() {
        return datas.length;
    }

    /**
     * Returns the number of columns in this data table.
     * @return the number of columns in the model
     */
    public int getColumnCount() {
        return columnIdentifiers.length;
    }

    /**
     * Returns the column name.
     *
     * @return a name for this column using the string value of the
     * appropriate member in <code>columnIdentifiers</code>.
     * If <code>columnIdentifiers</code> is <code>null</code>
     * or does not have an entry for this index, returns the default
     * name provided by the superclass
     */
    public String getColumnName(int column) {
        if(columnNames == null){
            return null;//super.getColumnName(column);
        } else{
            return columnNames[column].toString();
        }
    }

    /**
     * Returns true regardless of parameter values.
     *
     * @param   row             the row whose value is to be queried
     * @param   column          the column whose value is to be queried
     * @return                  true
     * @see #setValueAt
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * Returns an attribute value for the cell at <code>row</code>
     * and <code>column</code>.
     *
     * @param   row             the row whose value is to be queried
     * @param   column          the column whose value is to be queried
     * @return                  the value Object at the specified cell
     * @exception  ArrayIndexOutOfBoundsException  if an invalid row or
     *               column was given
     */
    public Object getValueAt(int row, int column) {
        return datas[row][column];
    }
    public Object getValueAt(Object node, int column) {
      QueryNode qn = (QueryNode)node;Object[] Datas=null;
      if ( qn != null ) {
        Datas = qn.getDatas();
        if ( Datas != null )
          return Datas[column];
      }
      if ( column == 0 )
        return qn;
      else
        return null;
    }

    /**
     * Sets the object value for the cell at <code>column</code> and
     * <code>row</code>.  <code>aValue</code> is the new value.  This method
     * will generate a <code>tableChanged</code> notification.
     *
     * @param   aValue          the new value; this can be null
     * @param   row             the row whose value is to be changed
     * @param   column          the column whose value is to be changed
     * @exception  ArrayIndexOutOfBoundsException  if an invalid row or
     *               column was given
     */
    public void setValueAt(Object aValue, int row, int column) {
        datas[row][column] = aValue;
    }
    //
    // The TreeModel interface
    //

    public int getChildCount(Object node) {
      QueryNode qn = (QueryNode)node;
      if ( qn != null ) {
        if ( qn.getChildren() != null )
          return qn.getChildren().length;
      }
      return 0;
    }

    public Object getChild(Object node, int i) {
      QueryNode qn = (QueryNode)node;
      if ( qn != null ) {
        return qn.getChildren()[i];
      }
      return null;
    }

    // The superclass's implementation would work, but this is more efficient.
    public boolean isLeaf(Object node) {
      QueryNode qn = (QueryNode)node;
      if ( qn != null ) {
        if ( qn.getChildren() != null ) return false;
      }
      return true;
    }

    public Class getColumnClass(int column) {
      if ( column == 0 ) return TreeTableModel.class;
        return String.class;
    }
}
