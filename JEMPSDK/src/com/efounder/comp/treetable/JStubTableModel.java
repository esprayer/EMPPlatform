package com.efounder.comp.treetable;

import javax.swing.table.*;
import java.util.*;
import com.core.xml.*;

public class JStubTableModel extends AbstractTableModel {
  protected Vector stubList = null;
  /**
   *
   */
  public JStubTableModel(Vector stubList) {
    this.stubList = stubList;
  }
  /**
   * Returns the number of rows in the model. A
   * <code>JTable</code> uses this method to determine how many rows it
   * should display.  This method should be quick, as it
   * is called frequently during rendering.
   *
   * @return the number of rows in the model
   * @see #getColumnCount
   */
  public int getRowCount(){
    if ( stubList != null ) return stubList.size();
    return 0;
  }

  /**
   * Returns the number of columns in the model. A
   * <code>JTable</code> uses this method to determine how many columns it
   * should create and display by default.
   *
   * @return the number of columns in the model
   * @see #getRowCount
   */
  public int getColumnCount() {
    return cColumns.length;
  }

  /**
   * Returns the name of the column at <code>columnIndex</code>.  This is used
   * to initialize the table's column header name.  Note: this name does
   * not need to be unique; two columns in a table can have the same name.
   *
   * @param	columnIndex	the index of the column
   * @return  the name of the column
   */
  public String getColumnName(int columnIndex) {
    return cNames[columnIndex];
  }

  /**
   * Returns the most specific superclass for all the cell values
   * in the column.  This is used by the <code>JTable</code> to set up a
   * default renderer and editor for the column.
   *
   * @param columnIndex  the index of the column
   * @return the common ancestor class of the object values in the model.
   */
  public Class getColumnClass(int columnIndex) {
    return cTypes[columnIndex];
  }

  /**
   * Returns true if the cell at <code>rowIndex</code> and
   * <code>columnIndex</code>
   * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
   * change the value of that cell.
   *
   * @param	rowIndex	the row whose value to be queried
   * @param	columnIndex	the column whose value to be queried
   * @return	true if the cell is editable
   * @see #setValueAt
   */
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if ( columnIndex == 0 ) return false;
    return true;
  }

  /**
   * Returns the value for the cell at <code>columnIndex</code> and
   * <code>rowIndex</code>.
   *
   * @param	rowIndex	the row whose value is to be queried
   * @param	columnIndex 	the column whose value is to be queried
   * @return	the value Object at the specified cell
   */
  public Object getValueAt(int rowIndex, int columnIndex) {
    if ( stubList == null ) return null;
    StubObject stubObject = (StubObject)stubList.get(rowIndex);
    if ( columnIndex == 0 ) return stubObject;
    String colName = cColumns[columnIndex];
    Object res = stubObject.getObject(colName,null);
    if ( res == null && oDefObjs != null && oDefObjs.length >= columnIndex ) {
      res = oDefObjs[columnIndex];
    }
    return res;
  }

  /**
   * Sets the value in the cell at <code>columnIndex</code> and
   * <code>rowIndex</code> to <code>aValue</code>.
   *
   * @param	aValue		 the new value
   * @param	rowIndex	 the row whose value is to be changed
   * @param	columnIndex 	 the column whose value is to be changed
   * @see #getValueAt
   * @see #isCellEditable
   */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      if ( columnIndex == 0 ) return;
      StubObject stubObject = (StubObject)stubList.get(rowIndex);
      String colName = cColumns[columnIndex];
      stubObject.setObject(colName,aValue);
    }
    protected String[]  cNames   = null;
    protected String[]  cColumns = null;
    protected Class[]   cTypes   = null;
    protected Object[]  oDefObjs  = null;

    public void setDefObjs(Object[] oDefObjs) {
      this.oDefObjs = oDefObjs;
    }
    public Object[] getDefObjs() {
      return oDefObjs;
    }
    public void setNames(String[] ns) {
      cNames = ns;
    }
    public void setTypes(Class[] ts) {
      cTypes = ts;
    }
    public void setColumns(String[] cs) {
      cColumns = cs;
    }
    public String[] getName() {
      return cNames;
    }
    public Class[] getType() {
      return cTypes;
    }
    public String[] getColumn() {
      return cColumns;
    }
}
