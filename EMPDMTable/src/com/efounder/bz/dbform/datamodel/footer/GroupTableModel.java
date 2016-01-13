package com.efounder.bz.dbform.datamodel.footer;

import java.util.*;

import java.awt.*;

import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.column.*;
import com.efounder.bz.dbform.event.*;
import com.swing.table.AttributiveCellTableModel;

import javax.swing.table.TableColumn;

/**
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
public class GroupTableModel extends AttributiveCellTableModel implements
    DataComponent, DataModelComponent {
    /**
     *
     */
    private int rowCount;
    /**
     *
     */
    private int colCount;

    /**
     *
     */
    protected ColumnModel columnModel = null;
    /**
     *
     * @return ColumnModel
     */
    public ColumnModel getColumnModel() {
        return columnModel;
    }

    /**
     *
     * @param columnModel ColumnModel
     */
    public void setColumnModel(ColumnModel columnModel) {
        this.columnModel = columnModel;
        initColumnModel();
    }
    public Object getValueAt(int row, int column) {
      try{
        return super.getValueAt(row,column);
      }catch(Exception e){
        return "";
      }
    }
    /**
     * 根据表列模型设置表格模型
     */
    protected void initColumnModel() {
        if (columnModel == null) return;
        Vector pColName = new Vector();
        for (int i = 0, n = columnModel.getColumnCount(); i < n; i++) {
            pColName.add(columnModel.getColumn(i).getHeaderValue());
        }
        this.setColumnIdentifiers(pColName);
        this.setColCount(pColName.size());
    }

    /**
     *
     */
    public GroupTableModel() {
        super(new Vector(), 0);
    }

    /**
     *
     * @param numRows int
     * @param numColumns int
     */
    public GroupTableModel(int numRows, int numColumns) {
        super(numRows, numColumns);
    }

    /**
     *
     * @param columnNames Vector
     * @param numRows int
     */
    public GroupTableModel(Vector columnNames, int numRows) {
        super(columnNames, numRows);
    }

    /**
     *
     * @param columnNames Object[]
     * @param numRows int
     */
    public GroupTableModel(Object[] columnNames, int numRows) {
        super(columnNames, numRows);
    }

    /**
     *
     * @param data Vector
     * @param columnNames Vector
     */
    public GroupTableModel(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    /**
     *
     * @param data Object[][]
     * @param columnNames Object[]
     */
    public GroupTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    /**
     *
     * @param rowCount int
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        int count = getRowCount();
        if (count == 0) count = 1;
        cellAtt.setSize(new Dimension(colCount, rowCount));
        for (int i = count - 1; i < rowCount; i++) {
            this.addRow(new Vector());
        }
    }

    /**
     *
     * @param colCount int
     */
    public void setColCount(int colCount) {
        this.colCount = colCount;
        super.setColumnCount(colCount);
        cellAtt.setSize(new Dimension(colCount, rowCount));
    }

    /**
     *
     * @return int
     */
    public int getRowCount() {
        return super.getRowCount();
    }

    /**
     *
     * @return int
     */
    public int getColCount() {
        return super.getColumnCount();
    }




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
      return dataCompName!=null?dataCompName:super.toString();
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
    public Object getProperty(Object key,Object def) {
      Object ret = null;
      if ( propertyMap == null ) return ret;
      ret = propertyMap.get(key);
      return ret==null?def:ret;
    }
    /**
     *
     * @param key Object
     * @param value Object
     */
    public void setProperty(Object key,Object value) {
      if ( propertyMap == null ) propertyMap = new HashMap();
      propertyMap.put(key,value);
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
      if ( parent instanceof DataContainer ) {
        this.dataContainer = (DataContainer)parent;
      }
    }
    /**
     *
     */
    protected java.util.Vector childList = new Vector();
    /**
     *
     * @return List
     */
    public java.util.List getChildList() {
      return childList;
    }
    /**
     *
     * @param childList List
     */
    public void setChildList(java.util.List childList) {
      childList.clear();
      if ( childList == null ) {
        return;
      }
      childList.addAll(childList);
    }
    /**
     *
     * @param ID String
     * @return DataComponent
     */
    public DataComponent getDataComponent(String ID) {
      if ( childList == null ) return null;
      for(int i=0;i<childList.size();i++) {
        if ( ID.equals(((DataComponent)childList.get(i)).getID()) ) {
          return (DataComponent)childList.get(i);
        }
      }
      return null;
    }
    /**
     *
     * @param index int
     * @return DataComponent
     */
    public DataComponent getDataComponent(int index) {
      if ( childList == null ) return null;
      if ( index < 0 || index >= childList.size() ) return null;
      return (DataComponent)childList.get(index);
    }
    public boolean existsColumn(Column column) {
      if ( childList == null || childList.size() == 0 ) return false;
      DataComponent dataComp = null;
      for(int i=0;i<childList.size();i++) {
        dataComp = (DataComponent)childList.get(i);
        if ( column.equals(dataComp) ) return true;
        if ( dataComp instanceof ColumnGroup ) {
          return ((ColumnGroup)dataComp).existsColumn(column);
        }
      }
      return false;
    }
    /**
     *
     * @param dataComponent DataComponent
     */
    public int insertDataComponent(DataComponent dataComponent) {
      if ( dataComponent == null ) return -1;
      return -1;
    }
    public boolean canAddChild(DataComponent childDC) {
      return false;
    }

    /**
     *
     * @param dataComponent DataComponent
     */
    public void removeDataComponent(DataComponent dataComponent) {
      if ( dataComponent != null ) {
        this.childList.remove(dataComponent);
        dataComponent.setParentDataComponent(null);
      }
    }
    protected FormFunctionObject customFunction = null;
    /**
     *
     * @return FormFunctionObject
     */
    public FormFunctionObject getCustomFunction() {
      return customFunction;
    }
    /**
     *
     * @param ffo FormFunctionObject
     */
    public void setCustomFunction(FormFunctionObject ffo) {
      this.customFunction = ffo;
  }

	public void sysInitDataComponent() {
		// TODO Auto-generated method stub
		
	}

}
