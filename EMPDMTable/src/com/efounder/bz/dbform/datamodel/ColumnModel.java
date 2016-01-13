package com.efounder.bz.dbform.datamodel;

import java.util.*;

import javax.swing.table.*;

import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.column.*;
import com.efounder.dctbuilder.data.ColumnMetaData;

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
public class ColumnModel extends DefaultTableColumnModelExt {

//  protected DataSetComponent dataSetComponent = null;
//
//  public void setDataSetComponent(DataSetComponent dsc) {
//	  this.addColumn(arg0)
//    dataSetComponent = dsc;
//  }
  protected String dataSetID = null;
  public void setDataSetID(String ds) {
    dataSetID = ds;
  }
  public String getDataSetID() {
    return dataSetID;
  }
  public void setDataSet(EFDataSet eFDataSet) {

  }
  public EFDataSet getDataSet() {
    return null;
  }
  public ESPRowSet getMainRowSet() {
    return null;
  }


  /**
   *
   */
  public ColumnModel() {
    super();
//    ColumnModelToolkit.createDemoColumn(this);
  }
//  /**
//   *
//   */
//  protected DCTableModel dctTableModel = null;
//  /**
//   *
//   * @return DCTableModel
//   */
//  public DCTableModel getDCTableModel() {
//    return dctTableModel;
//  }
//  /**
//   *
//   * @param dctTableModel DCTableModel
//   */
//  protected void setDCTableModel(DCTableModel dctTableModel) {
//    this.dctTableModel = dctTableModel;
//  }
  /**
   *
   */
  protected EFDataSet keySetInfo = null;
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getKeySetInfo() {
    return keySetInfo;
  }
  /**
   *
   * @param keySetInfo EFDataSet
   */
  public void setKeySetColInfo(EFDataSet keySetInfo) {
    this.keySetInfo = keySetInfo;
  }


  /**
   *
   * @param modelIndex int
   * @return TableColumn
   */
  public Column getColumnByModelIndex(int modelIndex) {
    Enumeration enumeration = getColumns();
    TableColumn aColumn;
    while (enumeration.hasMoreElements()) {
      aColumn = (TableColumn)enumeration.nextElement();
      if ( !(aColumn instanceof Column) ) continue;
      if ( aColumn.getModelIndex() == modelIndex ) return (Column)aColumn;
    }
    return null;
  }
  
  public void addColumn(EFRowSet columnDim) {
	  Column column = Column.getInstance();
	  column.setTitle(columnDim.getString("COL_MC", ""));
	  column.setPreferredWidth(columnDim.getNumber("COL_LEN", 100).intValue());
	  column.setWidth(columnDim.getNumber("COL_LEN", 100).intValue());
	  column.setModelIndex(this.getColumnCount());
	  super.addColumn(column);
  }
  
  public void addColumn(ColumnMetaData columnMetaData) {
	  Column column = Column.getInstance(columnMetaData);
	  column.setTitle(columnMetaData.getString("COL_MC", ""));
//	  column.setPreferredWidth(columnMetaData.getInt("COL_LEN", 100));
//	  column.setWidth(columnMetaData.getInt("COL_LEN", 100));
	  column.setColumnMeataData(columnMetaData);
	  column.setModelIndex(this.getColumnCount());
	  super.addColumn(column);
  }

  /**
   *
   * @param tableColumn TableColumn
   * @return ESPRowSet
   */
  public ESPRowSet getColumnInfo(TableColumn column) {
    // 如果不是Column类型，则直接返回
//    if ( !(column instanceof Column) ) return null;
//    Column tableColumn = (Column)column;
//    DMComponent dmComponent = getDMComponent();
//    if ( dmComponent == null ) return null;
//    DataSetComponent dataSetComponent = getDataSetComponent();
//    if ( dataSetComponent == null ) return null;
//    String contentSetID = dmComponent.getDataSetID();
//    if ( contentSetID == null ) return null;
//    String dataSetColID = tableColumn.getDataSetColID();
//    String dataSetID    = tableColumn.getInternalDataSetID();
//    if ( dataSetColID == null ) return null;
//    String dsid = contentSetID;
//    if ( contentSetID.indexOf(".") != -1 ) {
//      dsid = contentSetID.substring(contentSetID.indexOf(".")+1);
//    }
//    DOMetaData[] doMetaDatas = dataSetComponent.getDOMetaData(dsid);
//    if ( doMetaDatas == null || doMetaDatas.length == 0 ) return null;
//    if ( dataSetID == null || dataSetID.trim().length() == 0 ) dataSetID = dsid;
//    DOMetaData doMetaData = getDOMetaData(dataSetID,doMetaDatas);
//    if ( doMetaData == null ) return null;
//    EFDataSet dataSet = doMetaData.getDOColumns();
//    ESPRowSet rowSet = dataSet.getRowSet(new String[]{dataSetColID});
//    return rowSet;
	  return null;
  }
//  /**
//   *
//   * @param dataSetID String
//   * @param doMetaDatas DOMetaData[]
//   * @return DOMetaData
//   */
//  protected DOMetaData getDOMetaData(String dataSetID,DOMetaData[] doMetaDatas) {
//    for(int i=0;i<doMetaDatas.length;i++) {
//      if ( dataSetID.equals(doMetaDatas[i].getObjID()) ) {
//        return doMetaDatas[i];
//      }
//    }
//    return null;
//  }
//  /**
//   *
//   * @return DMComponent
//   */
//  public DMComponent getDMComponent() {
//    if ( dmComponent != null ) return dmComponent;
//    return this;
//  }
//  /**
//   *
//   */
//  protected DMComponent dmComponent = null;
//  /**
//   *
//   * @param dmComponent DMComponent
//   */
//  public void setDMComponent(DMComponent dmComponent) {
//    this.dmComponent = dmComponent;
//  }
//  /**
//   *
//   * @return DMComponent
//   */
//  public DataSetComponent getDataSetComponent() {
//    if ( this.dmComponent == null ) return dataSetComponent;
//    return dmComponent.getDataSetComponent();
//  }
//  /**
//   *
//   */
//  protected String contentID = null;
//  /**
//   *
//   * @return String
//   */
//  public String getContentID() {
//    return contentID;
//  }
//  /**
//   *
//   * @param contentID String
//   */
//
//
//
//  /**
//   *
//   */
//  protected String dataModelID = null;
//  /**
//   *
//   */
//  protected String dataCompName = null;
//  /**
//   *
//   * @return String
//   * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
//   */
//  public String getID() {
//    return dataModelID;
//  }
//
//  /**
//   *
//   * @return String
//   * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
//   */
//  public String getName() {
//    return dataCompName;
//  }
//
//  /**
//   *
//   * @param id String
//   * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
//   */
//  public void setID(String id) {
//    dataModelID = id;
//  }
//
//  /**
//   *
//   * @param name String
//   * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
//   */
//  public void setName(String name) {
//    dataCompName = name;
//  }
//  /**
//   *
//   * @return String
//   */
//  public String toString() {
//    return dataCompName!=null?dataCompName:super.toString();
//  }
//  /**
//   *
//   */
//  protected java.util.Map propertyMap = null;
//  /**
//   *
//   * @return Map
//   */
//  public java.util.Map getPropertyMap() {
//    return propertyMap;
//  }
//  /**
//   *
//   * @param propertyMap Map
//   */
//  public void setPropertyMap(java.util.Map propertyMap) {
//    this.propertyMap = propertyMap;
//  }
//  /**
//   *
//   * @param key Object
//   * @param def Object
//   * @return Object
//   */
//  public Object getProperty(Object key,Object def) {
//    Object ret = null;
//    if ( propertyMap == null ) return ret;
//    ret = propertyMap.get(key);
//    return ret==null?def:ret;
//  }
//  /**
//   *
//   * @param key Object
//   * @param value Object
//   */
//  public void setProperty(Object key,Object value) {
//    if ( propertyMap == null ) propertyMap = new HashMap();
//    propertyMap.put(key,value);
//  }
//  /**
//   *
//   */
//  protected DataContainer dataContainer = null;
//  /**
//   *
//   * @return DataContainer
//   */
//  public DataContainer getDataContainer() {
//    return dataContainer;
//  }
//  /**
//   *
//   * @param dataContainer DataContainer
//   */
//  public void setDataContainer(DataContainer dataContainer) {
//    this.dataContainer = dataContainer;
//  }
//  /**
//   *
//   */
//  protected java.util.Map eventMap = new HashMap();
//  /**
//   *
//   * @return Map
//   */
//  public Map getEventMap() {
//    return eventMap;
//  }
//  /**
//   *
//   * @param eventMap Map
//   */
//  public void setEventMap(Map eventMap) {
//    this.eventMap = eventMap;
//  }
//  /**
//   *
//   */
//  protected DataComponent parent = null;
//  /**
//   *
//   * @return DataComponent
//   */
//  public DataComponent getParentDataComponent() {
//    return parent;
//  }
//  /**
//   *
//   * @param dc DataComponent
//   */
//  public void setParentDataComponent(DataComponent dc) {
//    parent = dc;
//    if ( parent instanceof DataContainer ) {
//      this.dataContainer = (DataContainer)parent;
//    }
//  }
//  /**
//   *
//   */
////  protected java.util.List childList = null;
//  /**
//   *
//   * @return List
//   */
//  public java.util.List getChildList() {
//    return tableColumns;
//  }
//  /**
//   *
//   * @param childList List
//   */
//  public void setChildList(java.util.List childList) {
////    if ( tableColumns == null ) tableColumns = new Vector<TableColumn>();
////    tableColumns.clear();
////    tableColumns.addAll(childList);
//    if ( childList == null ) return;
//    for(int i=0;i<childList.size();i++) {
//      this.addColumn((TableColumn)childList.get(i));
//    }
//  }
//  /**
//   *
//   * @param ID String
//   * @return DataComponent
//   */
//  public DataComponent getDataComponent(String ID) {
//    if ( tableColumns == null ) return null;
//    for(int i=0;i<tableColumns.size();i++) {
//      if ( ID.equals(((DataComponent)tableColumns.get(i)).getID()) ) {
//        return (DataComponent)tableColumns.get(i);
//      }
//    }
//    return null;
//  }
//  /**
//   *
//   * @param index int
//   * @return DataComponent
//   */
//  public DataComponent getDataComponent(int index) {
//    if ( tableColumns == null ) return null;
//    if ( index < 0 || index >= tableColumns.size() ) return null;
//    return (DataComponent)tableColumns.get(index);
//  }
//  /**
//   *
//   * @param dataComponent DataComponent
//   */
//  public int insertDataComponent(DataComponent dataComponent) {
//    if ( !(dataComponent instanceof Column) ) return -1;
//    this.addColumn((Column)dataComponent);
//    ((Column)dataComponent).setHeaderValue(dataComponent.getID());
//    dataComponent.setParentDataComponent(this);
//    int modelIndex = this.tableColumns.size()-1;
//    if ( ((Column)dataComponent).getModelIndex() == -1 )
//      ((Column)dataComponent).setModelIndex(modelIndex);
//    return modelIndex;
//  }
//  /**
//   *
//   * @param dataComponent DataComponent
//   */
//  public void removeDataComponent(DataComponent dataComponent) {
//    if ( dataComponent != null ) {
//      this.removeColumn((Column)dataComponent);
//      dataComponent.setParentDataComponent(null);
//    }
//  }
//  public boolean canAddChild(DataComponent childDC) {
//    if ( !(childDC instanceof Column) ) return false;
//    return true;
//  }
//  protected FormFunctionObject customFunction = null;
//  /**
//   *
//   * @return FormFunctionObject
//   */
//  public FormFunctionObject getCustomFunction() {
//    return customFunction;
//  }
//  /**
//   *
//   * @param ffo FormFunctionObject
//   */
//  public void setCustomFunction(FormFunctionObject ffo) {
//    this.customFunction = ffo;
//  }
//  /**
//   *
//   */
//  public void removeAll() {
//    int colCount = this.getColumnCount();
//    colMap.clear();
//    calcColList.clear();
//    if ( colCount == 0 ) return;
//    java.util.List colList = new java.util.ArrayList();
//    for(int i=0;i<colCount;i++) {
//      TableColumn tc = this.getColumn(i);
//      colList.add(tc);
//    }
//    for(int i=0;i<colList.size();i++) {
//      this.removeColumn((TableColumn)colList.get(i));
//    }
//  }
//  /**
//   *
//   */
//  protected boolean checkBoxColumn = false;
//  /**
//   *
//   * @param cbc boolean
//   */
//  public void setCheckBoxColumn(boolean cbc) {
//    checkBoxColumn = cbc;
//  }
//  /**
//   *
//   * @return boolean
//   */
//  public boolean isCheckBoxColumn() {
//    return checkBoxColumn;
//  }
//  /**
//   *
//   */
//  protected boolean iconColumn = false;
//  private FormModel formModel;
//  /**
//   *
//   * @param ic boolean
//   */
//  public void setIconColumn(boolean ic) {
//    this.iconColumn = ic;
//  }
//
//  public void setFormModel(FormModel formModel) {
//    this.formModel = formModel;
//    if(contentID!=null)
//      addFormColumn(this.formModel,contentID);
//  }
//  public void setContentID(String contentID) {
//    if (this.contentID != null)
//      removeFormColumn(this.formModel,this.contentID);
//    this.contentID = contentID;
//    if (this.contentID != null&&formModel!=null)
//      addFormColumn(this.formModel, contentID);
//  }
//  //contentID
//  protected void removeFormColumn(FormModel model, String ctnid) {
//    model.removeColumnModel(ctnid);
//  }
//  protected void addFormColumn(FormModel model, String ctnid) {
//    model.addColumnModel(ctnid, this);
//  }
//
//  /**
//   *
//   * @return boolean
//   */
//  public boolean isIconColumn() {
//    return iconColumn;
//  }
//
//  public FormModel getFormModel() {
//    return formModel;
//  }
//  protected Map colMap=new HashMap();
//  public  Map getColumnMap() {
//    int count = getColumnCount();
//    Column tce;
//    String columnid;
//    if(colMap.size()==0){
//      for (int i = 0; i < count; i++) {
//        tce = getColumnByModelIndex(i);
//        if(tce==null)continue;
//        columnid = tce.getDataSetColID();
//        colMap.put(columnid, tce);
//      }
//    }
//    return colMap;
//  }
//  protected List calcColList=new ArrayList();
//  public List getCalcColumnList(){
//    int count = getColumnCount();
//    Column tce;
//    String columnid;
//    if(calcColList.size()==0){
//      for (int i = 0; i < count; i++) {
//        tce = getColumnByModelIndex(i);
//        if(tce!=null){
//          String exp = tce.getFormulaOne();
//          if (exp != null && exp.trim().length() > 0)
//            calcColList.add(tce);
//        }
//      }
//      java.util.Collections.sort(calcColList,new Comparator(){
//        public int compare(Object o1, Object o2) {
//          Column c1=(Column)o1;
//          Column c2=(Column)o2;
//          int s1=c1.getCalcOrder();
//          int s2=c2.getCalcOrder();
//          if(s1>s2)return 1;
//          if(s1<s2)return -1;
//          return 0;
//        }
//        public boolean equals(Object obj) {
//          return false;
//        }
//      });
//    }
//    return calcColList;
//  }
//  public boolean hasBWBColumn(){
//    int count = getColumnCount();
//    for (int i = 0; i < count; i++) {
//      Column tce = getColumnByModelIndex(i);
//      if(tce==null)continue;
//      EFRowSet ers=(EFRowSet) getColumnInfo(tce);
//      //modified by lchong
//      if(ers == null) return false;
//      //modified end
//      String apptype=ers.getString(SYS_OBJCOLS._COL_YELX_,"");
//      String colref=ers.getString(SYS_OBJCOLS._COL_REF_,"");
//      if(apptype.equals(SYS_OBJCOLS.BWB)&&colref.trim().length()>0)return true;
//    }
//    return false;
//  }
//  public boolean hasWBColumn(){
//    int count = getColumnCount();
//    for (int i = 0; i < count; i++) {
//      Column tce = getColumnByModelIndex(i);
//      if(tce==null)continue;
//      EFRowSet ers=(EFRowSet) getColumnInfo(tce);
//      String apptype=ers.getString(SYS_OBJCOLS._COL_YELX_,"");
//        String colref=ers.getString(SYS_OBJCOLS._COL_REF_,"");
//      if(apptype.equals(SYS_OBJCOLS.WB)&&colref.trim().length()>0)return true;
//    }
//    return false;
//  }
//
//  /**
//   *
//   */
//  private java.util.List<Column> sortColumnList = new ArrayList<Column> ();
//
//  /**
//   * 返回所有正在排序的列
//   *
//   * @return List
//   */
//  public java.util.List<Column> getSortColumnList() {
//      return sortColumnList;
//  }
//
//  /**
//   *
//   * @param column Column
//   */
//  public void setSortColumn(Column column) {
//      if (column.getSortOrder() == column.UNSORTED)
//          sortColumnList.remove(column);
//      else if (!sortColumnList.contains(column))
//          sortColumnList.add(column);
//  }
//public void sysInitDataComponent() {
//	// TODO Auto-generated method stub
//
//}

}
