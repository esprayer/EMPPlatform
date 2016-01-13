package com.efounder.bz.dbform.datamodel;

import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.column.*;
import com.efounder.bz.dbform.event.FormFunctionObject;
import com.efounder.bz.dbform.component.RowSetValueUtils;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.pub.util.ESPKeyValueUtil;

import java.math.*;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DCTableModel
    extends AbstractTableModel implements ListSelectionListener,
    DataSetListener, DataComponent, DMComponent {
  /**
   *
   */
  protected ListSelectionModel listSelectionModel = null;
  /**
   *
   * @param lsm ListSelectionModel
   */
  public void setListSelectionModel(ListSelectionModel lsm) {
    if (listSelectionModel != null) {
      listSelectionModel.removeListSelectionListener(this);
    }
    listSelectionModel = lsm;
    if (listSelectionModel != null) {
      listSelectionModel.addListSelectionListener(this);
    }
  }

  protected boolean canSelection = true;
  /**
   * JTable中的SelectionModel中产生的事件
   * SelectionModel中的选择器，选定了指定的行索引
   * 与
   */
  public void valueChanged(ListSelectionEvent se) {
    if (!canSelection) {
      return;
    }
    if (listSelectionModel == null) {
      return;
    }
    int rowIndex = listSelectionModel.getLeadSelectionIndex(); //se.getLastIndex();
    if (this.dataSet != null) {
      try {
        canCrusorMove = false;
        dataSet.goToRow(rowIndex);
      }
      finally {
        canCrusorMove = true;
      }
    }
  }

  protected boolean canCrusorMove = true;
  /**
   * 当前DCTableModel中的DataSet的游标移动到了相应的位置
   * 在此方法中接收到此事件
   * @param dse DataSetEvent
   */
  protected void cursorMoved(DataSetEvent dse) {
    if (!canCrusorMove) {
      return;
    }
    if (this.listSelectionModel == null) {
      return;
    }
    int rowIndex = dse.getNewCursor();
    try {
      canSelection = false;
      listSelectionModel.setSelectionInterval(rowIndex, rowIndex);
    }
    finally {
      canSelection = true;
    }
  }

  /**
   *
   * @param e DataSetEvent
   */
  public void dataSetChanged(DataSetEvent e) {
    if ( !this.getDataSetID().equals(e.getDataSet().getTableName() ) ) return;
    if (e.getEventType() == DataSetEvent.CURSOR) {
      cursorMoved(e);
      return;
    }
    if (e.getEventType() == DataSetEvent.INSERT) {
      fireTableRowsInserted(e.getFirstRow(), e.getFirstRow());
      return;
    }
    if (e.getEventType() == DataSetEvent.DELETE) {
      this.fireTableRowsDeleted(e.getFirstRow(), e.getFirstRow());
      return;
    }

    if (e.getEventType() == DataSetEvent.UPDATE) {
      this.fireTableCellUpdated(e.getFirstRow(),-1);
//      this.fireTableDataChanged();
    }
  }
  /**
   *
   * @param dataSetComponentEvent DataSetComponentEvent
   */
  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_START_EDIT ) {
      this.setEnableDataEdit(true);
      return;
    }
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_STOP_EDIT ) {
      this.setEnableDataEdit(false);return;
    }
  }
  /**
   *
   */
  public DCTableModel() {
  }

  /**
   *
   */
  protected java.util.Map tableColumnMapByModelIndex = new HashMap();
  /**
   *
   * @param modelIndex int
   * @return TableColumn
   */
  protected TableColumn getTableColumnByModelIndex(int modelIndex) {
    if (columnModel == null) {
      return null;
    }
    String index = String.valueOf(modelIndex);
    Column tableColumn = null;
//    if (DataComponentUtil.isDesigner(this))//add by fsz 不易找
       tableColumn= (Column) tableColumnMapByModelIndex.get(index);
    if (tableColumn == null) {
      tableColumn = columnModel.getColumnByModelIndex(modelIndex);
      if (tableColumn != null) {
        tableColumnMapByModelIndex.put(index, tableColumn);
      }
    }
    return tableColumn;
  }

  protected TableColumn getTableColumnByColumnIndex(int columnIndex) {
    if (columnModel == null || columnIndex < 0 ) {
      return null;
    }
    if (columnIndex >= columnModel.getColumnCount()) {
      return null;
    }
    return columnModel.getColumn(columnIndex);
  }

  /**
   *
   * @param tableColumn Column
   * @return ESPRowSet
   */
  protected ESPRowSet getColumnInfo(TableColumn tableColumn) {
    if (columnModel == null) {
      return null;
    }
    ESPRowSet rowSet = (ESPRowSet) tableColumnMapByModelIndex.get(tableColumn);
    if (rowSet == null) {
      rowSet = columnModel.getColumnInfo(tableColumn);
      if (rowSet != null) {
        tableColumnMapByModelIndex.put(tableColumn, rowSet);
      }
    }
    return rowSet;
  }

  /**
   *
   */
  protected DataSetComponent dataSetComponent = null;
  /**
   *
   * @return DataSetComponent
   */
  public DataSetComponent getDataSetComponent() {
    return dataSetComponent;
  }

  /**
   *
   * @param dsc DataSetComponent
   */
  public void setDataSetComponent(DataSetComponent dsc) {
    if (dataSetComponent != dsc) {
      if (dataSetComponent != null) {
        dataSetComponent.removeDMComponent(this);
      }
      dataSetComponent = dsc;
      if (dataSetComponent != null) {
        dataSetComponent.insertDMComponent(this);
      }
    }
  }

  /**
   *
   */
  protected String dataSetID = null;
  /**
   *
   * @param dataSetID String
   */
  public void setDataSetID(String dataSetID) {
    this.dataSetID = dataSetID;
    if (dataSetComponent != null) {
      setDataSet(dataSetComponent.getDataSet(dataSetID));
    }
  }

  /**
   *
   * @return String
   */
  public String getDataSetID() {
    return dataSetID;
  }

  /**
   *
   */
  protected EFDataSet dataSet = null;
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getDataSet() {
    return dataSet;
  }

  /**
   *
   * @param dataSet EFDataSet
   */
  public void setDataSet(EFDataSet ds) {
    if (dataSet != ds) {
      // 清除掉事件监听器
      if (dataSet != null) {
        dataSet.removeDataSetListener(this);
      }
      dataSet = ds;
      // 增加事件监听器
      if (dataSet != null) {
        dataSet.addDataSetListener(this);
      }
      // 数据发生改变
//      this.setDataVector(null,null);
      this.fireTableDataSetChanged();
    }
  }

  /**
   *
   */
  public void fireTableDataSetChanged() {
    TableModelEvent tme = new TableModelEvent(this);
    fireTableChanged(tme);
  }

  /**
   *
   */
  public void fireTableDataSetInsertChanged() {
    TableModelEvent tme = new TableModelEvent(this, 0, 1, 0,
                                              TableModelEvent.INSERT);
    fireTableChanged(tme);
  }

  /**
   *
   */
  protected ColumnModel columnModel = null;
  /**
   *
   * @return ColumnModel
   */
  public ColumnModel getColModel() {
    return columnModel;
  }

  /**
   *
   * @param colModel ColumnModel
   */
  public void setColModel(ColumnModel colModel) {
    if (this.columnModel != null) {
//      this.columnModel.setDMComponent(null);
    }
    this.columnModel = colModel;
    if (this.columnModel != null) {
//      this.columnModel.setDMComponent(this);
    }
  }

  /**
   *
   */
  protected ColumnHeaderGroupModel columnHeaderGroup = null;
  /**
   *
   * @return GroupModel
   */
  public ColumnHeaderGroupModel getColumnHeaderGroup() {
    return columnHeaderGroup;
  }

  /**
   *
   * @param columnGroup GroupModel
   */
  public void setColumnHeaderGroup(ColumnHeaderGroupModel columnGroup) {
    this.columnHeaderGroup = columnGroup;
  }

//  /**
//   *
//   */
//  protected ColumnFooterGroupModel columnFooterGroup = null;
//  /**
//   *
//   * @return GroupModel
//   */
//  public ColumnFooterGroupModel getColumnFooterGroup() {
//    return columnFooterGroup;
//  }
//
//  /**
//   *
//   * @param columnGroup GroupModel
//   */
//  public void setColumnFooterGroup(ColumnFooterGroupModel columnGroup) {
//    this.columnFooterGroup = columnGroup;
//  }

  /**
   *
   */
  protected String dataModelID = null;
  /**
   *
   */
  protected String dataCompName = null;
  /**
   *
   * @return String
   * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
   */
  public String getID() {
    return dataModelID;
  }

  /**
   *
   * @return String
   * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
   */
  public String getName() {
    return dataCompName;
  }

  /**
   *
   * @param id String
   * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
   */
  public void setID(String id) {
    dataModelID = id;
  }

  /**
   *
   */
  protected java.util.Map eventMap = new HashMap();
  /**
   *
   * @return Map
   */
  public Map getEventMap() {
    return eventMap;
  }

  /**
   *
   * @param eventMap Map
   */
  public void setEventMap(Map eventMap) {
    this.eventMap = eventMap;
  }

  /**
   *
   */
  protected DataComponent parent = null;
  /**
   *
   * @return DataComponent
   */
  public DataComponent getParentDataComponent() {
    return parent;
  }

  /**
   *
   * @param dc DataComponent
   */
  public void setParentDataComponent(DataComponent dc) {
    parent = dc;
    if (parent instanceof DataContainer) {
      this.dataContainer = (DataContainer) parent;
    }
  }

  /**
   *
   * @return List
   */
  public List getChildList() {
    return null;
  }

  /**
   *
   * @param childList List
   */
  public void setChildList(List childList) {
  }

  /**
   *
   * @param ID String
   * @return DataComponent
   */
  public DataComponent getDataComponent(String ID) {
    return null;
  }

  /**
   *
   * @param index int
   * @return DataComponent
   */
  public DataComponent getDataComponent(int index) {
    return null;
  }

  /**
   *
   * @param dataComponent DataComponent
   */
  public int insertDataComponent(DataComponent dataComponent) {
    return -1;
  }

  /**
   *
   * @param dataComponent DataComponent
   */
  public void removeDataComponent(DataComponent dataComponent) {
  }

  /**
   *
   * @param name String
   * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
   */
  public void setName(String name) {
    dataCompName = name;
  }

  /**
   *
   * @return String
   */
  public String toString() {
    return dataCompName != null ? dataCompName : super.toString();
  }

  /**
   *
   */
  protected java.util.Map propertyMap = null;
  /**
   *
   * @return Map
   */
  public java.util.Map getPropertyMap() {
    return propertyMap;
  }

  /**
   *
   * @param propertyMap Map
   */
  public void setPropertyMap(java.util.Map propertyMap) {
    this.propertyMap = propertyMap;
  }

  /**
   *
   * @param key Object
   * @param def Object
   * @return Object
   */
  public Object getProperty(Object key, Object def) {
    Object ret = null;
    if (propertyMap == null) {
      return ret;
    }
    ret = propertyMap.get(key);
    return ret == null ? def : ret;
  }

  /**
   *
   * @param key Object
   * @param value Object
   */
  public void setProperty(Object key, Object value) {
    if (propertyMap == null) {
      propertyMap = new HashMap();
    }
    propertyMap.put(key, value);
  }

  /**
   *
   */
  protected DataContainer dataContainer = null;
  /**
   *
   * @return DataContainer
   */
  public DataContainer getDataContainer() {
    return dataContainer;
  }

  /**
   *
   * @param dataContainer DataContainer
   */
  public void setDataContainer(DataContainer dataContainer) {
    this.dataContainer = dataContainer;
  }

  /**
   *
   * @param childDC DataComponent
   * @return boolean
   */
  public boolean canAddChild(DataComponent childDC) {
    return false;
  }

  /**
   *
   */
  protected FormFunctionObject customFunction = null;
  private boolean enableDataEdit = true;
  /**
   *
   * @return FormFunctionObject
   */
  public FormFunctionObject getCustomFunction() {
    return customFunction;
  }

  public boolean isEnableDataEdit() {
    return enableDataEdit;
  }

  /**
   *
   * @param ffo FormFunctionObject
   */
  public void setCustomFunction(FormFunctionObject ffo) {
    this.customFunction = ffo;
  }

  public void setEnableDataEdit(boolean enableDataEdit) {
    this.enableDataEdit = enableDataEdit;
  }

  /**
   * 获取数据集的行数
   * @return int
   */
  public int getRowCount() {
    // 默认为2行
    if (this.dataSet == null) {
      return 1;
    }
    return dataSet.getRowCount();
  }

  /**
   * 根据列模型，获取有多少列
   * @return int
   */
  public int getColumnCount() {
    // 默认为4列
    if (this.columnModel == null) {
      return 0;
    }
    return columnModel.getColumnCount();
  }

  /**
   *
   * @param row int
   * @param column int
   * @return boolean
   */
  public boolean isCellEditable(int row, int column) {
//    // 先获取表列
    Column tableColumn = (Column)this.getTableColumnByModelIndex(column);
    if ( tableColumn == null && !(tableColumn instanceof DMColComponent) ) return false;
//    if ( tableColumn.isCheckBoxColumn() ) return true;
    
    if ( dataSet == null ) return enableDataEdit;
    EFRowSet mainRowSet = dataSet.getRowSet(row);
    if (mainRowSet == null) {
      return enableDataEdit;
    }
    ColumnMetaData columnMetaData = (ColumnMetaData) tableColumn.getColumnMeataData();
    if(columnMetaData == null) return enableDataEdit;
    String srccont = columnMetaData.getString("F_SRCCONT", "");
    Map map=ESPKeyValueUtil.getKeyValue(srccont);
    String editable = (String)map.get("EDITABLE");
    if(editable != null && !"".equals(editable)){
    	boolean res = RowSetCalc.runCondition(mainRowSet, editable);
    	return res==false?res:enableDataEdit;
    }
    
    return this.enableDataEdit;
  }

  /**
   *
   * @param row int
   * @param column int
   * @return Object
   */
  public Object getValueAt(int row, int column) {
    if ( dataSet == null ) return null;
    // 先获取表列
    Column tableColumn = (Column)this.getTableColumnByModelIndex(column);
    // 如果为空，则直接返回为空
    if (tableColumn == null && !(tableColumn instanceof DMColComponent)) {
      return null;
    }
    EFRowSet mainRowSet = dataSet.getRowSet(row);
    if (mainRowSet == null) {
      return null;
    }
    // 获取cellValue
    Object cellValue = RowSetValueUtils.getObject(mainRowSet,(DMColComponent)tableColumn);
    RowSetValue rowSetValue = RowSetValue.getInstance(mainRowSet,cellValue,(Column)tableColumn);
    EFRowSet colRowSet = getColRowSet(tableColumn.getInternalDataSetID(),tableColumn.getDataSetColID());
    ColumnMetaData columnMetaData = (ColumnMetaData) tableColumn.getColumnMeataData();
//    if(dimset==null)dimset=(EFRowSet) tableColumn.getValSetRowSet();
//    if(dimset!=null)colRowSet=dimset;
    rowSetValue.setColMetaData(colRowSet);
    return rowSetValue;
  }
  public EFRowSet getColRowSet(String FCT_ID,String COL_ID) {
    DOMetaData[] doMetaDatas = this.getDataSetComponent().getDOMetaData(FCT_ID);
    if ( doMetaDatas == null || doMetaDatas.length == 0 ) return null;
    DOMetaData doMetaData = doMetaDatas[0];
    if (doMetaData == null) return null;
    return (EFRowSet)doMetaData.getColumnDefineRow(COL_ID);
//    String FCT_ID = rowSet.getString("FCT_ID",null);
//    String COL_ID = rowSet.getString("DIM_ID",null);
//    DOMetaData doMetaData = (DOMetaData)doMetaDataMap.get(FCT_ID);
//    EFRowSet colRowSet = (EFRowSet)doMetaData.getColumnDefineRow(COL_ID);
//    return colRowSet;
  }
  /**
   *
   * @param aValue Object
   * @param row int
   * @param column int
   */
  public void setValueAt(Object aValue, int row, int column) {
    // 先获取表列
    Column tableColumn = (Column)this.getTableColumnByModelIndex(column);
    // 如果为空，则直接返回为空
    if (tableColumn == null && !(tableColumn instanceof DMColComponent)) {
      return;
    }
    if (dataSet == null) {
      return;
    }
    EFRowSet rowSet = dataSet.getRowSet(row);
    if (rowSet == null) {
      return;
    }
    if(aValue instanceof BigDecimal){
//      String decn=(String)tableColumn.getProperty("DECN","2");
//        double dd;
//        try {
//          dd = JFNumber.round(((BigDecimal)(aValue)).doubleValue(),
//                              Integer.parseInt(decn));
//          aValue=BigDecimal.valueOf(dd);
//        }
//        catch (Exception e) {
//          e.printStackTrace();
//        }
    }
//    if ( tableColumn.isCheckBoxColumn() && ( aValue instanceof Boolean )) {
//      rowSet.setRowSelected((Boolean)aValue);
//      return;
//    }
    RowSetValueUtils.putObject(rowSet,(DMColComponent)tableColumn,aValue);
  }

  /**
   *
   * @param columnIndex int
   * @return Class
   */
  public Class<?> getColumnClass(int columnIndex) {
    // 先获取表列
    TableColumn tableColumn = this.getTableColumnByColumnIndex(columnIndex);
    // 获取列信息
    ESPRowSet rowSet = this.getColumnInfo(tableColumn);
    // 为空，默认为String.class
    if (rowSet == null) {
      return String.class;
    }
    // 取出列信息
    String colType = rowSet.getString("COL_TYPE", "C");
    // 从对照表中取出列类型
    return Column.getColumnClass(colType, String.class);
  }

  /**
   *  Returns the <code>Vector</code> of <code>Vectors</code>
   *  that contains the table's
   *  data values.  The vectors contained in the outer vector are
   *  each a single row of values.  In other words, to get to the cell
   *  at row 1, column 5: <p>
   *
   *  <code>((Vector)getDataVector().elementAt(1)).elementAt(5);</code><p>
   *
   * @return  the vector of vectors containing the tables data values
   *
   * @see #newDataAvailable
   * @see #newRowsAdded
   * @see #setDataVector
   */
  public Vector getDataVector() {
    return null; //dataVector;
  }

  private static Vector nonNullVector(Vector v) {
    return (v != null) ? v : new Vector();
  }

  /**
   *  Replaces the current <code>dataVector</code> instance variable
   *  with the new <code>Vector</code> of rows, <code>dataVector</code>.
   *  Each row is represented in <code>dataVector</code> as a
   *  <code>Vector</code> of <code>Object</code> values.
   *  <code>columnIdentifiers</code> are the names of the new
   *  columns.  The first name in <code>columnIdentifiers</code> is
   *  mapped to column 0 in <code>dataVector</code>. Each row in
   *  <code>dataVector</code> is adjusted to match the number of
   *  columns in <code>columnIdentifiers</code>
   *  either by truncating the <code>Vector</code> if it is too long,
   *  or adding <code>null</code> values if it is too short.
   *  <p>Note that passing in a <code>null</code> value for
   *  <code>dataVector</code> results in unspecified behavior,
   *  an possibly an exception.
   *
   * @param   dataVector         the new data vector
   * @param   columnIdentifiers     the names of the columns
   * @see #getDataVector
   */
  public void setDataVector(Vector dataVector) {
//    this.dataVector = nonNullVector(dataVector);
    justifyRows(0, getRowCount());
    fireTableStructureChanged();
  }

  /**
   *  Equivalent to <code>fireTableChanged</code>.
   *
   * @param event  the change event
   *
   */
  public void newDataAvailable(TableModelEvent event) {
    fireTableChanged(event);
  }

//
// Manipulating rows
//

  private void justifyRows(int from, int to) {
    // Sometimes the DefaultTableModel is subclassed
    // instead of the AbstractTableModel by mistake.
    // Set the number of rows for the case when getRowCount
    // is overridden.
//    dataVector.setSize(getRowCount());

    for (int i = from; i < to; i++) {
//      if (dataVector.elementAt(i) == null) {
//        dataVector.setElementAt(new Vector(), i);
//      }
//      ( (Vector) dataVector.elementAt(i)).setSize(getColumnCount());
    }
  }

  /**
   *  Ensures that the new rows have the correct number of columns.
   *  This is accomplished by  using the <code>setSize</code> method in
   *  <code>Vector</code> which truncates vectors
   *  which are too long, and appends <code>null</code>s if they
   *  are too short.
   *  This method also sends out a <code>tableChanged</code>
   *  notification message to all the listeners.
   *
   * @param e         this <code>TableModelEvent</code> describes
   *                           where the rows were added.
   *				 If <code>null</code> it assumes
   *                           all the rows were newly added
   * @see #getDataVector
   */
  public void newRowsAdded(TableModelEvent e) {
    justifyRows(e.getFirstRow(), e.getLastRow() + 1);
    fireTableChanged(e);
  }

  /**
   *  Equivalent to <code>fireTableChanged</code>.
   *
   *  @param event the change event
   *
   */
  public void rowsRemoved(TableModelEvent event) {
    fireTableChanged(event);
  }

  /**
   * Obsolete as of Java 2 platform v1.3.  Please use <code>setRowCount</code> instead.
   */
  /*
   *  Sets the number of rows in the model.  If the new size is greater
   *  than the current size, new rows are added to the end of the model
   *  If the new size is less than the current size, all
   *  rows at index <code>rowCount</code> and greater are discarded. <p>
   *
   * @param   rowCount   the new number of rows
   * @see #setRowCount
   */
  public void setNumRows(int rowCount) {
    int old = getRowCount();
    if (old == rowCount) {
      return;
    }
//    dataVector.setSize(rowCount);
    if (rowCount <= old) {
      fireTableRowsDeleted(rowCount, old - 1);
    }
    else {
      justifyRows(old, rowCount);
      fireTableRowsInserted(old, rowCount - 1);
    }
  }

  /**
   *  Sets the number of rows in the model.  If the new size is greater
   *  than the current size, new rows are added to the end of the model
   *  If the new size is less than the current size, all
   *  rows at index <code>rowCount</code> and greater are discarded. <p>
   *
   *  @see #setColumnCount
   */
  public void setRowCount(int rowCount) {
    setNumRows(rowCount);
  }

  /**
   *  Adds a row to the end of the model.  The new row will contain
   *  <code>null</code> values unless <code>rowData</code> is specified.
   *  Notification of the row being added will be generated.
   *
   * @param   rowData          optional data of the row being added
   */
  public void addRow(Vector rowData) {
    insertRow(getRowCount(), rowData);
  }

  /**
   *  Adds a row to the end of the model.  The new row will contain
   *  <code>null</code> values unless <code>rowData</code> is specified.
   *  Notification of the row being added will be generated.
   *
   * @param   rowData          optional data of the row being added
   */
  public void addRow(Object[] rowData) {
    addRow(convertToVector(rowData));
  }

  /**
   *  Inserts a row at <code>row</code> in the model.  The new row
   *  will contain <code>null</code> values unless <code>rowData</code>
   *  is specified.  Notification of the row being added will be generated.
   *
   * @param   row             the row index of the row to be inserted
   * @param   rowData         optional data of the row being added
   * @exception  ArrayIndexOutOfBoundsException  if the row was invalid
   */
  public void insertRow(int row, Vector rowData) {
//    dataVector.insertElementAt(rowData, row);
    justifyRows(row, row + 1);
    fireTableRowsInserted(row, row);
  }

  /**
   *  Inserts a row at <code>row</code> in the model.  The new row
   *  will contain <code>null</code> values unless <code>rowData</code>
   *  is specified.  Notification of the row being added will be generated.
   *
   * @param   row      the row index of the row to be inserted
   * @param   rowData          optional data of the row being added
   * @exception  ArrayIndexOutOfBoundsException  if the row was invalid
   */
  public void insertRow(int row, Object[] rowData) {
    insertRow(row, convertToVector(rowData));
  }

  private static int gcd(int i, int j) {
    return (j == 0) ? i : gcd(j, i % j);
  }

  private static void rotate(Vector v, int a, int b, int shift) {
    int size = b - a;
    int r = size - shift;
    int g = gcd(size, r);
    for (int i = 0; i < g; i++) {
      int to = i;
      Object tmp = v.elementAt(a + to);
      for (int from = (to + r) % size; from != i; from = (to + r) % size) {
        v.setElementAt(v.elementAt(a + from), a + to);
        to = from;
      }
      v.setElementAt(tmp, a + to);
    }
  }

  /**
   *  Moves one or more rows from the inclusive range <code>start</code> to
   *  <code>end</code> to the <code>to</code> position in the model.
   *  After the move, the row that was at index <code>start</code>
   *  will be at index <code>to</code>.
   *  This method will send a <code>tableChanged</code> notification
   *  message to all the listeners. <p>
   *
   *  <pre>
   *  Examples of moves:
   *  <p>
   *  1. moveRow(1,3,5);
   *          a|B|C|D|e|f|g|h|i|j|k   - before
   *          a|e|f|g|h|B|C|D|i|j|k   - after
   *  <p>
   *  2. moveRow(6,7,1);
   *          a|b|c|d|e|f|G|H|i|j|k   - before
   *          a|G|H|b|c|d|e|f|i|j|k   - after
   *  <p>
   *  </pre>
   *
   * @param   start       the starting row index to be moved
   * @param   end         the ending row index to be moved
   * @param   to          the destination of the rows to be moved
   * @exception  ArrayIndexOutOfBoundsException  if any of the elements
   * would be moved out of the table's range
   *
   */
  public void moveRow(int start, int end, int to) {
    int shift = to - start;
    int first, last;
    if (shift < 0) {
      first = to;
      last = end;
    }
    else {
      first = start;
      last = to + end - start;
    }
//    rotate(dataVector, first, last + 1, shift);

    fireTableRowsUpdated(first, last);
  }

  /**
   *  Removes the row at <code>row</code> from the model.  Notification
   *  of the row being removed will be sent to all the listeners.
   *
   * @param   row      the row index of the row to be removed
   * @exception  ArrayIndexOutOfBoundsException  if the row was invalid
   */
  public void removeRow(int row) {
//    dataVector.removeElementAt(row);
    fireTableRowsDeleted(row, row);
  }

//
// Manipulating columns
//
  /**
   *  Sets the number of columns in the model.  If the new size is greater
   *  than the current size, new columns are added to the end of the model
   *  with <code>null</code> cell values.
   *  If the new size is less than the current size, all columns at index
   *  <code>columnCount</code> and greater are discarded.
   *
   *  @param columnCount  the new number of columns in the model
   *
   *  @see #setColumnCount
   */
  public void setColumnCount(int columnCount) {
//    columnIdentifiers.setSize(columnCount);
    justifyRows(0, getRowCount());
    fireTableStructureChanged();
  }

//
// Protected Methods
//

  /**
   * Returns a vector that contains the same objects as the array.
   * @param anArray  the array to be converted
   * @return  the new vector; if <code>anArray</code> is <code>null</code>,
   *				returns <code>null</code>
   */
  protected static Vector convertToVector(Object[] anArray) {
    if (anArray == null) {
      return null;
    }
    Vector v = new Vector(anArray.length);
    for (int i = 0; i < anArray.length; i++) {
      v.addElement(anArray[i]);
    }
    return v;
  }

  /**
   * Returns a vector of vectors that contains the same objects as the array.
   * @param anArray  the double array to be converted
   * @return the new vector of vectors; if <code>anArray</code> is
   *				<code>null</code>, returns <code>null</code>
   */
  protected static Vector convertToVector(Object[][] anArray) {
    if (anArray == null) {
      return null;
    }
    Vector v = new Vector(anArray.length);
    for (int i = 0; i < anArray.length; i++) {
      v.addElement(convertToVector(anArray[i]));
    }
    return v;
  }
  public ESPRowSet getMainRowSet() {
    return null;
  }

public void sysInitDataComponent() {
	// TODO Auto-generated method stub

}
}
