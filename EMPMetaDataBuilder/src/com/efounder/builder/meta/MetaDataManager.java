package com.efounder.builder.meta;

import org.openide.util.Lookup;

import com.efounder.buffer.DataBuffer;
import com.efounder.buffer.DataBufferManager;
import com.efounder.builder.base.util.ESPContext;
import com.efounder.builder.meta.fctmodel.FCTMetaData;
import com.efounder.eai.data.JParamObject;

public abstract class MetaDataManager {
	public static final String _DataObject_ = "DataObject";
	public static final String _DCTObject_  = "DCTObject";
	public static final String _FCTObject_  = "FCTObject";
	public static final String _BIZModel_   = "BIZModel";
	public static final String _IObject_    = "IObject";
	/**
	 * 创建元数据管理用的数据缓冲管理器
	 */
	protected static DataBufferManager metaDataBufferManger = null;
	/**
	 *
	 */
	static {
		metaDataBufferManger = DataBufferManager.createDataBufferManager(MetaDataManager.class);
	    DataBufferManager.putDataBufferManager(MetaDataManager.class,metaDataBufferManger);
	}
	
	/**
	   *
	   */
	  public static void removeAllDatabuffer() {
	    if ( metaDataBufferManger != null )
	      metaDataBufferManger.removeAllDataBuffer();
	  }
	  public static boolean isUseMemCache(){
//	    String value = ParameterManager.getDefault().getSystemParam("METADDATA_MEMCACHE");
//	    return "1".equals(value);
		  return true;
	  }
	  
	  /**
	   * 客户端缓存方式
	   * @return
	   */
	  public static String getClientCacheType(){
//		  String type = ParameterManager.getDefault().getSystemParam("CLIENT_CACHE_TYPE");
//		  return type;
		  return "";
	  }
	  /**
	   *
	   * @param key String
	   */
	  public static void removeDataBuffer(String key) {
	    if ( metaDataBufferManger != null )
	      metaDataBufferManger.removeDataBuffer(key);
	  }
	  /**
	   *
	   * @param key String
	   * @param OBJ_ID String
	   */
	  public static void removeDataObjectMetaData(String key,String OBJ_ID) {
	     DataBuffer metaDataBuffer =null;
	    if(isUseMemCache())
	      metaDataBuffer=metaDataBufferManger.getMemCached(key);
	    else
	     metaDataBuffer=metaDataBufferManger.getDataBuffer(key);
	    if ( metaDataBuffer != null )
	      metaDataBuffer.removeData(OBJ_ID);
	  }
	  /**
	   *
	   */
	//  private DataBuffer metaDataBuffer = null;
	  /**
	   *
	   */
	  protected MetaDataManager() {
	  }

	  protected String metaTypeKey = null;
	  /**
	   *
	   * @param metaTypeKey String
	   * @return MetaDataManager
	   */
	  public static final MetaDataManager getInstance(String metaTypeKey) {
	    MetaDataManager metaDataManager = (MetaDataManager)Lookup.getDefault().lookup(MetaDataManager.class,metaTypeKey);
	    metaDataManager.metaTypeKey = metaTypeKey;
	    metaDataManager.initMetaDataBuffer();
	    if(DataBufferManager.getDataBufferManager(MetaDataManager.class)==null)
	        DataBufferManager.putDataBufferManager(MetaDataManager.class,metaDataBufferManger);
	    return metaDataManager;
	  }
	  /**
	   *
	   * @return MetaDataManager
	   */
	  public static final MetaDataManager getBIZMetaDataManager() {
	    return getInstance(MetaDataManager._BIZModel_);
	  }
	  /**
	   *
	   * @return MetaDataManager
	   */
	  public static final MetaDataManager getDODataManager() {
	    return getInstance(MetaDataManager._DataObject_);
	  }
	  /**
	   *
	   * @return MetaDataManager
	   */
	  public static final MetaDataManager getDCTDataManager() {
	    return getInstance(MetaDataManager._DCTObject_);
	  }

	  /**
	   *
	   * @return MetaDataManager
	   */
	  public static final MetaDataManager getDCTMetaDataManager() {
	    return getInstance(MetaDataManager._DCTObject_);
	  }

	  /**
	   *
	   * @param metaTypeKey String
	   */
	  protected void initMetaDataBuffer() {
	    // 如果metaDataBuffer不为空，则直接用
//	    if ( metaDataBuffer != null ) return;
	    DataBuffer metaDataBuffer=getDataBuffer();
	    if ( metaDataBuffer != null ) return;
	    // 以元数据类型 + 应用唯一ID
//	    String bufferKey = metaTypeKey + ctx.getDBUniqueID();
	    // 以缓冲Key创建metaDataBuffer
	      if (isUseMemCache())
	          metaDataBufferManger.createMemCached(metaTypeKey);
	      else
	          metaDataBufferManger.createDataBuffer(metaTypeKey);
	  }

	  /**
	   *
	   * @return DataBuffer
	   */
	  protected DataBuffer getDataBuffer(){
	    return getDataBuffer(null);
	  }

	  /**
	   *
	   * @param key String
	   * @return DataBuffer
	   */
	  protected DataBuffer getDataBuffer(String key){
	    if(key==null)key=metaTypeKey;
	    DataBuffer db = null;
	    if (isUseMemCache()){
	      db = metaDataBufferManger.getMemCached(key);
	    }
	    else{
	      db = metaDataBufferManger.getDataBuffer(key);
	    }
	    return db;
	  }

	  /**
	   *
	   * @param key String
	   * @param metaData MetaData
	   */
	  public void addMetaData(String key,MetaData metaData) {
	     DataBuffer db = getDataBuffer();
	     db.addData(key,metaData);
	  }

	  /**
	   *
	   * @param objid String
	   * @return MetaData
	   */
	  public abstract MetaData getMetaData(String objid) throws Exception;

	  /**
	   * This method is used to manage(insert/update) the object's metadata.
	   *
	   * @param ctx ESPContext
	   * @param metaData MetaData
	   * @return Object
	   * @throws Exception
	   */
	  public abstract Object updateMetaData(ESPContext ctx, MetaData metaData) throws Exception;

	  /**
	   *
	   * @param ctx MetaContext
	   * @param objid String
	   * @return MetaData
	   */
	  public MetaData getMetaData(ESPContext ctx,String objid)  throws Exception  {
	    if ( objid == null || objid.trim().length() == 0 ) return null;
	    MetaData md = existsRefMetaData(ctx,objid);
	    if ( md != null ) return md;
	    // 初始化元数据缓冲区
	    initMetaDataBuffer();
	    // 创建缓冲区中的数据Key
	    String dataKey = objid+"_"+ctx.getDBUniqueID()+"_"+ctx.getParamObject().GetValueByEnvName("Language");
	    String isClient = ctx.getString("isClient","0");
	    // 首先从缓冲区里取数
	    DataBuffer db = getDataBuffer();
	    MetaData metaData = null;
	    if(db != null) {
	    	metaData = (MetaData)db.getData(dataKey);	
	    }
	    // 如果是客户端调用过来或是为空
	    if ( "1".equals(isClient) || metaData == null ) {
	      // 为空的，从数据库中装入
	      metaData = getMetaDataModel(ctx, objid,metaData);
	      // 如果在缓冲中不存在，则放入，放入缓冲区中
	      if (metaData != null && !db.exists(metaData)) {
	        db.addData(dataKey,metaData);
	        JParamObject paramObject = ctx.getParamObject();
	        // 设置元数据所在的数据库
	        metaData.setDataBaseName(paramObject.GetValueByEnvName("DataBaseName", null));
	        metaData.setDBNO(paramObject.GetValueByEnvName("DBNO", null));
	        // 设置
//	        metaData.metaDataManager = this;
	      } else {
	        System.out.println(objid+"'s metaData == null");
	      }
	    }
//	    // 将获取元数据期间，将所有参考到的对象，都放在Map中
	    processReferenceMetaData(ctx,objid,metaData);
	    addContextREF(ctx,objid,metaData);
	    return metaData;
	  }
	  /**
	   *
	   */
	  public static final String _META_DATA_REF_MAP_ = "_META_DATA_REF_MAP_";
	  /**
	   *
	   * @param ctx ESPContext
	   * @param objid String
	   * @param metaData MetaData
	   */
	  protected final void processReferenceMetaData(ESPContext ctx,String objid,MetaData metaData) {
	    java.util.Map refList = (java.util.Map)ctx.getObject(_META_DATA_REF_MAP_,null);
	    if ( refList == null ) {
	      refList = new java.util.HashMap();
	      ctx.putObject(_META_DATA_REF_MAP_,refList);
	    }
	    refList.put(metaTypeKey+"_"+objid,metaData);
	  }
	  /**
	   *
	   * @param ctx ESPContext
	   * @param objid String
	   * @return boolean
	   */
	  protected final MetaData existsRefMetaData(ESPContext ctx,String objid) {
	    java.util.Map refList = (java.util.Map)ctx.getObject(_META_DATA_REF_MAP_,null);
	    if ( refList == null ) return null;
	    return ( (MetaData)refList.get(metaTypeKey+"_"+objid) );
	  }
	  /**
	   *
	   * @param ctx MetaContext
	   * @param objid String
	   * @return MetaData
	   */
	  protected abstract MetaData getMetaDataModel(ESPContext ctx,String objid,MetaData metaData) throws Exception ;
	  /**
	   *
	   * @param ctx ESPContext
	   * @param id String
	   * @param md MetaData
	   */
	  public void addContextREF(ESPContext ctx,String id, MetaData md){

	  }
	  /**
	   *
	   * @param objid String
	   */

	  public final void removeMetaData(ESPContext ctx,String objid) {
	    // 从缓冲区中清除符合dataKey的数据
	    DataBuffer db = getDataBuffer();
	    if ( db != null ) {
	      String dataKey = objid+"_"+ctx.getDBUniqueID();
	      db.removeData(dataKey);
	    }
	  }

	  /**
	   *
	   * @return MetaDataManager
	   */
	  public static final MetaDataManager getFCTMetaDataManager() {
	    return getInstance(MetaDataManager._FCTObject_);
	  }
}
