package com.efounder.bz.dbform.datamodel;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.domodel.DOMetaData;

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
public interface DataSetComponent extends DataComponent,DataModelComponent{
  // 与组件相关的操作
  public EFDataSet getDataSet(String dataSetKey);
  public String[] getDataSetKey();
  public DOMetaData[] getDOMetaData(String obj_id);
  public void insertDMComponent(DMComponent dmComponent);
  public void removeDMComponent(DMComponent dmComponent);
  // 与导航相关的操作
  public Object load(Object param) throws Exception;
  public Object load() throws Exception;
  public Object save() throws Exception;
  public Object first() throws Exception;
  public Object prior() throws Exception;
  public Object next() throws Exception;
  public Object last() throws Exception;
  public Object search(ESPRowSet rowSet) throws Exception;
  // 与编缉相关的操作
  public Object create() throws Exception;
  public boolean canEdit() throws Exception;
  public Object startEdit() throws Exception;
  public Object cancelEdit() throws Exception;
  public Object stopEdit() throws Exception;
  public boolean isEditing() throws Exception;
  // 可能还什么相关操作？
}
