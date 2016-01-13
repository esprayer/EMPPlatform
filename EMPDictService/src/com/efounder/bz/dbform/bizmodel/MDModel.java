package com.efounder.bz.dbform.bizmodel;

import java.util.List;
import java.util.Map;

import com.efounder.builder.base.data.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.bizmodel.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.builder.meta.domodel.*;
import com.efounder.bz.dbform.datamodel.DMComponent;
import com.efounder.bz.dbform.datamodel.DataComponent;
import com.efounder.bz.dbform.datamodel.DataContainer;
import com.efounder.bz.dbform.datamodel.DataSetComponent;
import com.efounder.bz.dbform.datamodel.ServiceComponent;
import com.efounder.bz.dbform.event.FormFunctionObject;
import com.efounder.eai.data.*;
import com.efounder.mdm.*;

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
public class MDModel implements DataSetComponent, DataSetListener,MDMComponent {
  /**
   *
   */
  public MDModel() {
  }
  /**
   *
   */
  protected String RLGL_ID = null;
  /**
   *
   * @return String
   */
  public String getRLGL_ID() {
    return RLGL_ID;
  }
  /**
   *
   * @param RLGL_ID String
   */
  public void setRLGL_ID(String RLGL_ID) {
    this.RLGL_ID = RLGL_ID;
  }
  /**
   *
   */
  protected String FKEY_ID = null;
  /**
   *
   * @return String
   */
  public String getFKEY_ID() {
    return FKEY_ID;
  }
  /**
   *
   * @param FKEY_ID String
   */
  public void setFKEY_ID(String FKEY_ID) {
    this.FKEY_ID = FKEY_ID;
  }
  /**
   *
   * @param dataSetKey String
   * @return EFDataSet
   */
  public EFDataSet getDataSet(String dataSetKey) {
    return null;
  }
  /**
   *
   * @return String[]
   */
  public String[] getDataSetKey() {
    if ( this.bizMDLID == null ) return null;
    BIZMetaData bizMetaData = getBIZMetaData();
    if ( bizMetaData == null ) return null;
    java.util.List tabList = new java.util.ArrayList();
    if ( this.bizDCTID != null ) tabList.add(bizDCTID);
    if ( this.FKEY_ID != null ) {
      tabList.add(FKEY_ID);
      tabList.add(FKEY_ID+"."+bizDCTID);
    }
    if ( this.RLGL_ID != null ) {
      DCTMetaData dctMetaData = bizMetaData.getDCTMetaData(bizDCTID);
      DCTMetaData rlglDCTMetaData = dctMetaData.getRLGLDCTMetaData(RLGL_ID);
      RLGL_DCT_ID = rlglDCTMetaData.getObjID();
      tabList.add(RLGL_DCT_ID);
      tabList.add(RLGL_ID+"."+bizDCTID);
      DOMetaData doMetaData = dctMetaData.getRLGLObjMetaData(RLGL_ID);
      RLGL_OBJ_ID = doMetaData.getObjID();
      tabList.add(RLGL_OBJ_ID);
    }
    String[] ctns = new String[tabList.size()];
    return (String[])tabList.toArray(ctns);
  }
  /**
   *
   */
  protected String RLGL_DCT_ID = null;
  /**
   *
   */
  protected String RLGL_OBJ_ID = null;
  /**
   *
   * @param obj_id String
   * @return DOMetaData[]
   */
  public DOMetaData[] getDOMetaData(String obj_id) {
    if ( this.bizMDLID == null ) return null;
    BIZMetaData bizMetaData = getBIZMetaData();
    if ( bizMetaData == null ) return null;
    return bizMetaData.getDOMetaData(obj_id);
  }
  /**
   *
   * @return BIZMetaData
   */
  public BIZMetaData getBIZMetaData() {
//    if ( database == null ) return null;
    if ( bizMDLID == null ) return null;
//    String eaiServer = database.getEAIServer();
    try {
      JParamObject po = JParamObject.Create();
//      if (eaiServer != null)
//        po.setEAIServer(eaiServer);
      ESPContext espContext = ESPClientContext.getInstance(po);
      BIZMetaData bizMetaData = (BIZMetaData) MetaDataManager.getInstance(
          MetaDataManager._BIZModel_).getMetaData(espContext, "ACFWModel");//bizMDLID);

      if ( this.RLGL_ID != null && bizMetaData != null ) {
        DCTMetaData dctMetaData = bizMetaData.getDCTMetaData(bizDCTID);
        if ( dctMetaData != null ) {
          DCTMetaData rlglDCTMetaData = dctMetaData.getRLGLDCTMetaData(RLGL_ID);
          RLGL_DCT_ID = rlglDCTMetaData.getObjID();
          DOMetaData doMetaData = dctMetaData.getRLGLObjMetaData(RLGL_ID);
          RLGL_OBJ_ID = doMetaData.getObjID();
        }
      }

      return bizMetaData;
    } catch ( Exception ex ) {
      ex.printStackTrace();
    }
    return null;
  }
  /**
   *
   */
  protected java.util.List dmComponentList = null;
  /**
   *
   * @param dmComponent DMComponent
   */
  public void insertDMComponent(DMComponent dmComponent) {
    if ( dmComponentList == null ) dmComponentList = new java.util.ArrayList();
    dmComponentList.add(dmComponent);
  }
  /**
   *
   * @param dmComponent DMComponent
   */
  public void removeDMComponent(DMComponent dmComponent) {
    if ( dmComponentList == null ) return;
    dmComponentList.remove(dmComponent);
  }
  /**
   *
   * @param mdmDataModel EFMDMDataModel
   */
  protected void setActiveDataSet(EFMDMDataModel mdmDataModel) {
    if ( this.FKEY_ID != null ) {
      setActiveDataSet(mdmDataModel.getDataSet(FKEY_ID));
    }
    if ( this.RLGL_DCT_ID != null ) {
      setActiveDataSet(mdmDataModel.getDataSet(RLGL_DCT_ID));
    }
    if ( this.bizDCTID != null ) {
      setActiveDataSet(mdmDataModel.getDataSet(bizDCTID));
    }
  }
  /**
   *
   * @param dataSet EFDataSet
   */
  protected void setActiveDataSet(EFDataSet dataSet) {
    //add by fsz
    if(dataFilter!=null){
      dataSet=dataFilter.filterMainData(dataSet);
    }
    if ( dmComponentList == null || dmComponentList.size() == 0 ) return;
    DMComponent dmComponent = null;
    for(int i=0;i<dmComponentList.size();i++) {
      dmComponent = (DMComponent)dmComponentList.get(i);
      if ( dataSet.getTableName().equals(dmComponent.getDataSetID()) ) {
        dmComponent.setDataSet(dataSet);
        dataSet.activeDataSet();
      }
    }
  }

  /**
   *
   */
  protected String bizDCTID = null;
  /**
   *
   * @return String
   */
  public String getBizDCTID() {
    return getTableFromSuffix(bizDCTID);
  }
  
  /**
   * 增加表后缀 daqing add by wujf at 20120718
   * @param DCT_ID
   * @return
   */
  protected String getTableFromSuffix(String DCT_ID) {

//	  JParamObject PO = paramConverter.createParamObject(this);
//	  this.getParamConverter().convertParamObject(PO, this);
//	  String DCT_SUFFIX = PO.GetValueByParamName("DCT_SUFFIX", "");
//	  if("".equals(DCT_SUFFIX))
//		  DCT_SUFFIX = (String)PO.getEnvValue("DCT_SUFFIX","");
//	  Vector serverList = PackageStub.getContentVector(DCT_ID+"_DZList");
//	  if(serverList == null || DCT_SUFFIX == null) return DCT_ID;
//	  for(int i=0;i<serverList.size();i++){
//		  StubObject so = ((StubObject)serverList.get(i));
//		  if(DCT_SUFFIX.equals(((String)so.getID()).trim())){
//			  return so.getString("dict", "").trim();
//		  }
//	  }
	  return DCT_ID;
  }
  
  /**
   *
   * @param bizDCTID String
   */
  public void setBizDCTID(String bizDCTID) {
    this.bizDCTID = bizDCTID;
  }
  /**
   * ����ҵ��ģ��ID
   */
  protected String bizMDLID = null;
  /**
   *
   * @return String
   */
  public String getBizMDLID() {
    return bizMDLID;
  }
  /**
   *
   * @param bizMDLID String
   */
  public void setBizMDLID(String bizMDLID) {
    this.bizMDLID = bizMDLID;
  }
  /**
   *
   */
  protected ServiceComponent provider = null;
  /**
   *
   */
  protected ServiceComponent resolver = null;
  /**
   *
   * @return String
   */
  public ServiceComponent getFormDataProvider() {
    return provider;
  }
  /**
   *
   * @return String
   */
  public ServiceComponent getFormDataResolver() {
    return resolver;
  }
  /**
   *
   * @param provider String
   */
  public void setFormDataProvider(ServiceComponent provider) {
    this.provider = provider;
  }
  /**
   *
   * @param Resolver String
   */
  public void setFormDataResolver(ServiceComponent resolver) {
    this.resolver = resolver;
  }
  /**
   *
   * @param mdmDataModel EFFormModel
   */
  protected void addDataSetListener(EFMDMDataModel mdmDataModel) {
    EFDataSet dataSet = null;
    if ( this.RLGL_DCT_ID != null ) {
      dataSet = mdmDataModel.getDataSet(RLGL_DCT_ID);
      addDataSetListener(dataSet);
    }
    if ( this.FKEY_ID != null ) {
      dataSet = mdmDataModel.getDataSet(FKEY_ID);
      addDataSetListener(dataSet);
    }
    /**
     *
     */
    if ( this.bizDCTID != null ) {
      dataSet = mdmDataModel.getDataSet(bizDCTID);
      addDataSetListener(dataSet);
    }
  }
  /**
   *
   * @param dataSet EFDataSet
   */
  protected void addDataSetListener(EFDataSet dataSet) {
    if ( dataSet != null )
      dataSet.addDataSetListener(this);
  }
  /**
   *
   * @param mdmDataModel EFFormModel
   */
  protected void removeDataSetListener(EFMDMDataModel mdmDataModel) {
    EFDataSet dataSet = null;
    if ( this.RLGL_DCT_ID != null ) {
      dataSet = mdmDataModel.getDataSet(RLGL_DCT_ID);
      removeDataSetListener(dataSet);
    }
    if ( this.FKEY_ID != null ) {
      dataSet = mdmDataModel.getDataSet(FKEY_ID);
      removeDataSetListener(dataSet);
    }
    /**
     *
     */
    if ( RLGL_DCT_ID == null && FKEY_ID == null && this.bizDCTID != null ) {
      dataSet = mdmDataModel.getDataSet(bizDCTID);
      removeDataSetListener(dataSet);
    }
  }
  /**
   *
   * @param dataSet EFDataSet
   */
  protected void removeDataSetListener(EFDataSet dataSet) {
    dataSet.removeDataSetListener(this);
    EFRowSet rowSet = dataSet.getRowSet();
    if ( rowSet == null ) return;
    removeRowSetListener(rowSet);
  }
  /**
   *
   * @param rowSet EFRowSet
   */
  private void removeRowSetListener(EFRowSet rowSet) {
//    String[] dsTypes = rowSet.getDSTypes();
//    if ( dsTypes == null || dsTypes.length == 0 ) return;
//    for(int i=0;i<dsTypes.length;i++) {
//      EFDataSet childDataSet = rowSet.getDataSet(dsTypes[i]);
//      if ( childDataSet != null ) removeDataSetListener(childDataSet);
//    }
  }
  /**
   *
   * @param e DataSetEvent
   */
  public void dataSetChanged(DataSetEvent e) {
    if ( e.getEventType() == DataSetEvent.CURSOR ) {
      processDataSetCursorChangeEvent(e);
    }
  }
  /**
   *
   * @param dataSetEvent DataSetEvent
   */
  protected void processDataSetCursorChangeEvent(DataSetEvent dataSetEvent) {
    EFDataSet dataSet = dataSetEvent.getDataSet();
    int oldRowIndex = dataSetEvent.getOldCursor();
    EFRowSet rowSet = dataSet.getRowSet(oldRowIndex);
    // ɾ��ɵ�rowSet��DataSet���¼�������
    if ( rowSet != null ) this.removeRowSetListener(rowSet);
    int newRowIndex = dataSetEvent.getNewCursor();
    rowSet = dataSet.getRowSet(newRowIndex);
    if( rowSet != null ) this.addRowSetListener(rowSet);
  }
  /**
   *
   * @param rowSet EFRowSet
   */
  protected void addRowSetListener(EFRowSet rowSet) {
//    String[] dsTypes = rowSet.getDSTypes();
//    if ( dsTypes == null || dsTypes.length == 0 ) return;
//    for(int i=0;i<dsTypes.length;i++) {
//      EFDataSet childDataSet = rowSet.getDataSet(dsTypes[i]);
//      if ( childDataSet != null ) {
//        // �����¼�������
//        childDataSet.addDataSetListener(this);
//        // ����childDataSet
//        setActiveDataSet(childDataSet);
//      }
//    }
  }
  /**
   *
   * @return String
   */
  public String getDCT_ID() {
    return this.getBizDCTID();
  }

  /**
   *
   * @return DCTMetaData
   */
  public DCTMetaData getDCTMetaData() {
    BIZMetaData bizMetaData = getBIZMetaData();
    if ( bizMetaData == null ) return null;
    DCTMetaData dmd = bizMetaData.getDCTMetaData(getDCT_ID());
    if ( dmd == null ) {
      try {
        dmd = (DCTMetaData) MetaDataManager.getInstance(MetaDataManager._DCTObject_).getMetaData(getDCT_ID());
      }
      catch (Exception ex) {
      }
    }
    return dmd;
  }
  protected String serviceKey = null;
  public String getServiceKey() {
    return serviceKey;
  }

  public DataSetFilterAdapter getDataFilter() {

    return dataFilter;
  }

  public void setServiceKey(String serviceKey) {
    this.serviceKey = serviceKey;
  }

  public void setDataFilter(DataSetFilterAdapter dataFilter) {

    this.dataFilter = dataFilter;
  }

  /**
   *
   * @throws Exception
   */
  public Object save() throws Exception {
    return null;
  }
  /**
   *
   * @throws Exception
   */
  public Object load() throws Exception {
    return load(null);
  }
  /**
   * 加载主数据模型
   * @throws Exception
   */
  public Object load(Object param) throws Exception {
//    // 没有定义数据库
//    if ( database == null ) return null;
    // 没有定义模型
    if ( bizMDLID == null ) return null;
    // 没有定义字典
    if (bizDCTID == null) return null;
//    //
//    if ( this.paramConverter == null ) return null;
    // 参数转换为空，则直接返回
    JParamObject po = JParamObject.Create();
    // 装入
    EFMDMDataModel mdmDataModel = MasterDataManager.getDefault().getMDMDataModel(serviceKey,po,bizMDLID,bizDCTID,FKEY_ID,RLGL_ID,null,false);
    // 设置
    setMDMDataModel(mdmDataModel);
    return null;
  }

  /**
   *
   * @param reflush boolean
   * @return Object
   * @throws Exception
   */
  public Object load(boolean reflush) throws Exception{
//      // 没有定义数据库
//    if ( database == null ) return null;
//    // 没有定义模型
//    if ( bizMDLID == null ) return null;
//    if ( this.paramConverter == null ) return null;
//    // 参数转换为空，则直接返回
//    JParamObject po = paramConverter.createParamObject(this);

	  JParamObject po = JParamObject.Create();
	  // 装入
	  EFMDMDataModel mdmDataModel = MasterDataManager.getDefault().getMDMDataModel(serviceKey,po,bizMDLID,bizDCTID,FKEY_ID,RLGL_ID,null,reflush);
	  // 设置
	  setMDMDataModel(mdmDataModel);
	  return null;
    
  }


  /**
   *
   */
  protected EFMDMDataModel mdmDataModel = null;
  private DataSetFilterAdapter dataFilter;
  /**
   *
   * @param fdm EFMDMDataModel
   */
  public void setMDMDataModel(EFMDMDataModel fdm) {
    if ( this.mdmDataModel != fdm ) {
      if ( mdmDataModel != null ) this.removeDataSetListener(mdmDataModel);
      mdmDataModel = fdm;
      if ( mdmDataModel != null ) this.addDataSetListener(mdmDataModel);
      // DataSet
      setActiveDataSet(mdmDataModel);
//      formDataModel.getBillDataSet().actionDataSet();
    }
  }
  /**
   *
   * @return EFMDMDataModel
   */
  public EFMDMDataModel getMDMDataModel() {
    return mdmDataModel;
  }
  /**
   *
   * @return MDModel
   */
  public MDModel clone() {
    MDModel mdModel = new MDModel();
//    mdModel.database = this.database;
//    mdModel.paramConverter = this.paramConverter;
    mdModel.bizMDLID = this.bizMDLID;
    mdModel.serviceKey = this.serviceKey;
    mdModel.dataFilter=this.dataFilter;
    return mdModel;
  }

  public Object first() throws Exception {
    return null;
  }

  public Object prior() throws Exception {
    return null;
  }

  public Object next() throws Exception {
    return null;
  }

  public Object last() throws Exception {
    return null;
  }

  public Object search(ESPRowSet rowSet) throws Exception {
    return null;
  }

  public Object startEdit() throws Exception {
    return null;
  }

  public Object cancelEdit() throws Exception {
    return null;
  }

  public Object stopEdit() throws Exception {
    return null;
  }

  public boolean isEditing() throws Exception {
    return false;
  }

  public boolean canEdit() throws Exception {
    return false;
  }

  public Object create() throws Exception {
    return null;
  }
  

  public String getMDL_ID() {
	return null;
  }

  public DataComponent getParentDataComponent() {
	return null;
  }

  public void setParentDataComponent(DataComponent paramDataComponent) {
	
  }

  public List getChildList() {
	return null;
  }

  public void setChildList(List paramList) {
	
  }

  public DataComponent getDataComponent(String paramString) {
	return null;
  }

  public DataComponent getDataComponent(int paramInt) {
	return null;
  }

  public int insertDataComponent(DataComponent paramDataComponent) {
	return 0;
  }

  public void removeDataComponent(DataComponent paramDataComponent) {
	
  }

  public DataContainer getDataContainer() {
	return null;
  }

  public String getName() {
	return null;
  }

  public void setName(String paramString) {

  }

  public String getID() {
	return null;
  }

  public void setID(String paramString) {
	
  }

  public Map getPropertyMap() {
	return null;
  }

  public void setPropertyMap(Map paramMap) {
	
  }

  public Object getProperty(Object paramObject1, Object paramObject2) {
	return null;
  }

  public void setProperty(Object paramObject1, Object paramObject2) {
	
  }

  public Map getEventMap() {
	return null;
  }

  public void setEventMap(Map paramMap) {
	
  }

  public boolean canAddChild(DataComponent paramDataComponent) {
	return false;
  }

  public void setDataContainer(DataContainer paramDataContainer) {

  }

  public FormFunctionObject getCustomFunction() {
	return null;
  }

  public void setCustomFunction(FormFunctionObject paramFormFunctionObject) {

  }

  public void sysInitDataComponent() {
	
  }
}
