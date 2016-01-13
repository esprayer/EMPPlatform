package com.efounder.bz.dbform.datamodel;

import java.util.*;

import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.treetable.*;
import com.efounder.bz.dbform.event.*;

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
public class DCTreeTableModel extends DataSetTreeTableModel implements DataSetListener, DataComponent {
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
