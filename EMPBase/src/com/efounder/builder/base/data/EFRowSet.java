package com.efounder.builder.base.data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

import com.efounder.builder.util.MapUtil;


/**
 * 
 * @author ES
 * 
 */
public class EFRowSet extends HashMap implements ESPRowSet {

	/**
	 * 正常状态
	 */
	public static final int _DATA_STATUS_NORMAL_ = 0x0000;
	/**
	 * 更新状态
	 */
	public static final int _DATA_STATUS_UPDATE_ = 0x0001;
	/**
	 * 插入状态
	 */
	public static final int _DATA_STATUS_INSERT_ = 0x0002;
	/**
	 * 子数据集有修改
	 */
	public static final int _DATA_STATUS_CHILD_  = 0x0004;
	  
	/**
	 *
	 */
	private Map dataMap = null;

	/**
	   *
	   */
	protected java.util.Map<String, EFDataSet> dataSetMap = null;

	/**
	   *
	   */
	protected java.util.List<String> dataSetKeyList = null;
	
	/**
	   *
	   */
	  protected java.util.Map backID2NameMap = null;
	  
	  /**
	   *
	   */
	  protected transient java.util.Map frontID2NameMap = null;
	  
	/**
	   *
	   */
	public EFRowSet() {
		dataMap = this;// new HashMap();
	}

	/**
	 * 
	 * @return
	 */
	public static EFRowSet getInstance() {
		EFRowSet rowSet = new EFRowSet();
		return rowSet;
	}

	/**
	 * 
	 * @param dsType
	 *            String
	 * @return EFDataSet
	 */
	public EFDataSet getDataSet(String dsType) {
		if (dataSetMap == null) return null;
		return dataSetMap.get(dsType);
	}

	/**
	 * 
	 * @param dsType
	 *            String
	 * @param dataSet
	 *            EFDataSet
	 */
	public void setDataSet(String dsType, EFDataSet dataSet) {
		if (dataSetMap == null) dataSetMap = new java.util.HashMap<String, EFDataSet>();
		dataSetMap.put(dsType, dataSet);
		dataSet.setParentRowSet(this); // 设置DataSet的ParentRowSet
		dataSet.insertChildRowSet(this);
		this.insertDataSetKeyList(dsType);
	}

	/**
	 * 
	 * @param dsType
	 *            String
	 * @return EFDataSet
	 */
	public EFDataSet removeDataSet(String dsType) {
		if (dataSetMap == null) return null;
		EFDataSet dataSet = dataSetMap.remove(dsType);
		if (dataSet != null) {
			dataSet.setParentRowSet(null);
			dataSet.removeChildRowSet(this);
			this.removeDataSetKeyList(dsType);
		}
		return dataSet;
	}

	public java.util.List<String> getDataSetKeyList() {
		if (dataSetKeyList == null) dataSetKeyList = new java.util.ArrayList<String>();
		return dataSetKeyList;
	}

	private void insertDataSetKeyList(String dataSetKey) {
		if (dataSetKeyList == null) dataSetKeyList = new java.util.ArrayList<String>();
		this.dataSetKeyList.add(dataSetKey);
	}
	
	private void removeDataSetKeyList(String dataSetKey) {
		if (dataSetKeyList == null) return;
		this.dataSetKeyList.remove(dataSetKey);
	}
	
	/**
	 * @return the dataMap
	 */
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap
	 *            the dataMap to set
	 */
	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param defValue
	 *            Boolean
	 * @return Boolean
	 */
	public Boolean getBoolean(String keyName, Boolean defValue) {
		// TODO Auto-generated method stub
		return MapUtil.getBoolean(dataMap, keyName, defValue);
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param defValue
	 *            Date
	 * @return Date
	 */
	public Date getDate(String keyName, Date defValue) {
		// TODO Auto-generated method stub
		return MapUtil.getDate(dataMap, keyName, defValue);
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param defValue
	 *            Date
	 * @return Date
	 */
	public Date getTimestamp(String keyName, Timestamp defValue) {
		// TODO Auto-generated method stub
		return MapUtil.getTimestamp(dataMap, keyName, defValue);
	}
	
	/**
	 * 
	 * @param keyName
	 *            String
	 * @param defValue
	 *            Number
	 * @return Number
	 */
	public Number getNumber(String keyName, Number defValue) {
		// TODO Auto-generated method stub
		return MapUtil.getNumber(dataMap, keyName, defValue);
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param defValue
	 *            String
	 * @return String
	 */
	public String getString(String keyName, String defValue) {
		// TODO Auto-generated method stub
		return MapUtil.getString(dataMap, keyName, defValue);
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param defValue
	 *            String
	 * @return String
	 */
	public String getStringByTrim(String keyName, String defValue) {
		String value = getString(keyName, defValue);
		if (value != null && value.trim().length() == 0)
			return defValue;
		return value;
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param value
	 *            Boolean
	 */
	public void putBoolean(String keyName, Boolean value) {
		// TODO Auto-generated method stub
		putObject(keyName, value);
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param value
	 *            Date
	 */
	public void putDate(String keyName, Date value) {
		// TODO Auto-generated method stub
		putObject(keyName, value);
		// MapUtil.putDate(dataMap, keyName, value);
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param value
	 *            Number
	 */
	public void putNumber(String keyName, Number value) {
		// TODO Auto-generated method stub
		putObject(keyName, value);
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param value
	 *            String
	 */
	public void putString(String keyName, String value) {
		// TODO Auto-generated method stub
		putString(keyName, value, true);
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param defValue
	 *            Object
	 * @return Object
	 */
	public Object getObject(String keyName, Object defValue) {
		// TODO Auto-generated method stub
		return MapUtil.getObject(dataMap, keyName, defValue);
	}

	/**
	 * 
	 * @param keyName
	 *            String
	 * @param value
	 *            Object
	 */
	public void putString(String keyName, String value, boolean btrim) {
		if (btrim)
			putObject(keyName, value);
		else {
			Object oldValue = MapUtil.getObject(dataMap, keyName, null);
			MapUtil.putObject(dataMap, keyName, value);
		}
	}

	public void putObject(String keyName, Object value) {
		// TODO Auto-generated method stub
		// 去掉字符串中的空格
		if (value != null && value instanceof String) {
			value = ((String) value).trim();
		}
		Object oldValue = MapUtil.getObject(dataMap, keyName, null);
		MapUtil.putObject(dataMap, keyName, value);
	}

	@Override
	public void insertDataSetManager(EFDataSet dataSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeDataSetManager(EFDataSet dataSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public ESPRowSet getPrior() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrior(ESPRowSet rowSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public ESPRowSet getNext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNext(ESPRowSet rowSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public ESPRowSet getFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ESPRowSet getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 扩展属性存储，这些扩展属性不于数据列一起处理
	 */
	protected java.util.Map extPropertys = null;

	/**
	 * 
	 * @return Map
	 */
	public java.util.Map getExtPropertys() {
		return extPropertys;
	}

	/**
	 * 
	 * @param eps
	 *            Map
	 */
	public void setExtPropertys(java.util.Map eps) {
		extPropertys = eps;
	}

	/**
	 * 
	 * @param key
	 *            Object
	 * @param value
	 *            Object
	 */
	public void setExtProperty(Object key, Object value) {
		if (extPropertys == null)
			extPropertys = new java.util.HashMap();
		extPropertys.put(key, value);
	}

	/**
	 * 
	 * @param key
	 *            Object
	 * @param def
	 *            Object
	 * @return Object
	 */
	public Object getExtProperty(Object key, Object def) {
		if (extPropertys == null)
			return def;
		Object res = extPropertys.get(key);
		return res == null ? def : res;
	}

	/**
	   *
	   */
	protected java.util.Map<String, ESPRowSet> extRowSetMap = null;

	/**
	 * 
	 * @param key
	 *            String
	 * @param rowSet
	 *            ESPRowSet
	 */
	public void setExtRowSet(String key, ESPRowSet rowSet) {
		if (extRowSetMap == null) {
			extRowSetMap = new java.util.HashMap<String, ESPRowSet>();
		}
		extRowSetMap.put(key, rowSet);
	}

	/**
	 * 
	 * @param key
	 *            String
	 * @param def
	 *            ESPRowSet
	 * @return ESPRowSet
	 */
	public ESPRowSet getExtRowSet(String key, ESPRowSet def) {
		if (extRowSetMap == null)
			return null;
		ESPRowSet rs = extRowSetMap.get(key);
		return (rs == null) ? def : rs;
	}
	
	/**
	   *
	   */
	  protected int dataStatus = _DATA_STATUS_NORMAL_;
	
	/**
	   *
	   * @return int
	   */
	  public int getDataStatus() {
	    return dataStatus;
	  }
	  /**
	   *
	   * @param dataStatus int
	   */
	  public void setDataStatus(int dataStatus) {
	    this.dataStatus = dataStatus;
	  }
	  
	  /**
	   * @return the formDataSet
	   */
	  public EFDataSet getChildDataSet() {
	    return this.getDataSet(DATAConstant._CHILD_DATA_SET_);
	  }
	  
	  /**
	   * @param formDataSet the formDataSet to set
	   */
	  public void setChildDataSet(EFDataSet childDataSet) {
	    this.setDataSet(DATAConstant._CHILD_DATA_SET_,childDataSet);
	  }
	  
	  /**
	   *
	   * @param key Object
	   * @param def Object
	   * @return Object
	   */
	  public Object getValue(Object key, Object def) {
	    return getObject(key.toString(),def);
	  }
	  
	  /**
	   *
	   * @param DCT_ID String
	   * @param COL_ID String
	   * @param rowSet ESPRowSet
	   */
	  public void setID2RowSet(String DCT_ID,String COL_ID,ESPRowSet rowSet) {
	    // 如果frontID2NameMap不为空，说明是在前台，所有放入其中，直接返回
		if (frontID2NameMap != null) {
			frontID2NameMap.put(DCT_ID + "_RS_" + COL_ID, rowSet);
			return;
		}
	    // 执行到这，说明是在后台
	    if ( backID2NameMap == null ) backID2NameMap = new java.util.HashMap();
	    // 放入后台的Map中，可以序列化
	    backID2NameMap.put(DCT_ID+"_RS_"+COL_ID,rowSet);
	  }
	  
	  /**
	   *
	   * @param DCT_ID String
	   * @param COL_ID String
	   * @param rowSet ESPRowSet
	   */
	  public void setID2RowSetFront(String DCT_ID,String COL_ID,ESPRowSet rowSet) {
	    if ( frontID2NameMap == null ) frontID2NameMap = new java.util.HashMap();
	    if ( backID2NameMap != null ) frontID2NameMap.putAll(backID2NameMap);
	    backID2NameMap = null;
	    frontID2NameMap.put(DCT_ID+"_RS_"+COL_ID,rowSet);
	  }
	  
	  /**
	   *
	   * @return Map
	   */
	  public java.util.Map getBackID2NameMap() {
	    return backID2NameMap;
	  }
	  
	  /**
	   *
	   * @param bi2n Map
	   */
	  public void setBackID2NameMap(java.util.Map bi2n) {
	    backID2NameMap = bi2n;
	  }
	  
	  /**
	   *
	   * @param ID String
	   * @param defName String
	   * @return String
	   */
	  public Map CloneID2NameMap() {
		  Map map = new HashMap();
		  if ( frontID2NameMap != null )
			  map.putAll(frontID2NameMap);
		  if ( backID2NameMap != null )
			  map.putAll(backID2NameMap);
		  return map;
	  }
	  
	  /**
	   *
	   * @param ColID String
	   * @param ID String
	   * @param defName String
	   * @return String
	   */
	  public String getID2Name(String DCT_ID,String COL_ID,String defName) {
	    if ( frontID2NameMap == null ) {
				frontID2NameMap = backID2NameMap;
				backID2NameMap = null;
	    }
	    Object res = null;
	    if ( frontID2NameMap != null ) {
	      res = frontID2NameMap.get(DCT_ID+"_"+COL_ID);
	      if ( res != null ) return res.toString();
	    }
	    return defName;
	  }
	  
	  /**
	   *
	   * @param DCT_ID String
	   * @param COL_ID String
	   * @return ESPRowSet
	   */
	  public ESPRowSet getID2RowSet(String DCT_ID,String COL_ID) {
	    if ( frontID2NameMap == null ) {
				frontID2NameMap = backID2NameMap;
				backID2NameMap = null;
	    }
	    Object res = null;
	    if ( frontID2NameMap != null ) {
	      res = frontID2NameMap.get(DCT_ID+"_RS_"+COL_ID);
	    }
	    return (ESPRowSet)res;
	  }
	  
	  /**
	   *
	   * @return Map
	   */
	  public java.util.Map getFrontID2NameMap() {
	    return frontID2NameMap;
	  }
	  
	  /**
	   *
	   * @param fi2n Map
	   */
	  public void setFrontID2NameMap(java.util.Map fi2n) {
	    frontID2NameMap = fi2n;
	  }
	  
	  /**
	   *
	   * @param map Map
	   */
	  public void  setID2NameFront(Map map){
	    getID2Name("SB","","");
	    if( frontID2NameMap == null ) frontID2NameMap = new HashMap();
	    frontID2NameMap.putAll(map);
	  }

	  /**
	   *
	   * @param key Object
	   * @param val Object
	   */
	  public void setValue(Object key, Object val) {
	    this.putObject(key.toString(),val);
	  }
	@Override
	public void setID2Name(String ColID, String ID, String Name) {
		// TODO Auto-generated method stub
		
	}

	/**
	 *
	 */
	protected transient Icon rowIcon = null;

	/**
	 * 
	 * @return Icon
	 */
	public Icon getRowIcon() {
		return rowIcon;
	}

	/**
	 * 
	 * @param rowIcon
	 *            Icon
	 */
	public void setRowIcon(Icon rowIcon) {
		Icon oldIcon = this.rowIcon;
		this.rowIcon = rowIcon;
//		fireDataSetEvent("_ROW_ICON_", oldIcon, rowIcon);
	}
}
