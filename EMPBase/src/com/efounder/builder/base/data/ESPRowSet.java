package com.efounder.builder.base.data;

import java.util.*;
import java.io.*;

/**
 * 
 * @author Skyline
 * 
 */
public interface ESPRowSet extends Serializable {
	
	public int getDataStatus();
	/**
	 *
	 * @param dataStatus int
	 */
	public void setDataStatus(int dataStatus);
	
	/**
	 * 
	 * @param keyName   String
	 * @param value     Number
	 */
	public void putNumber(String keyName, Number value);

	/**
	 * 
	 * @param keyName
	 * @param defValue
	 * @return
	 */
	public Number getNumber(String keyName, Number defValue);

	/**
	 * 
	 * @param keyName
	 * @param value
	 */
	public void putString(String keyName, String value);

	/**
	 * 
	 * @param keyName  String
	 * @param value    String
	 * @param btrim
	 *            boolean
	 */
	public void putString(String keyName, String value, boolean btrim);

	/**
	 * 
	 * @param keyName
	 * @param defValue
	 * @return
	 */
	public String getString(String keyName, String defValue);

	/**
	 * 
	 * @param keyName    String
	 * @param defValue   String
	 * @return String
	 */
	public String getStringByTrim(String keyName, String defValue);

	/**
	 * 
	 * @param keyName
	 * @param value
	 */
	public void putBoolean(String keyName, Boolean value);

	/**
	 * 
	 * @param keyName
	 * @param defValue
	 * @return
	 */
	public Boolean getBoolean(String keyName, Boolean defValue);

	/**
	 * 
	 * @param keyName
	 * @param value
	 */
	public void putDate(String keyName, Date value);

	/**
	 * 
	 * @param keyName
	 * @param defValue
	 * @return
	 */
	public Date getDate(String keyName, Date defValue);

	/**
	 * 
	 * @param keyName
	 * @param value
	 */
	public void putObject(String keyName, Object value);

	/**
	 * 
	 * @param keyName
	 * @param defValue
	 * @return
	 */
	public Object getObject(String keyName, Object defValue);

	/**
	 * 
	 * @param dsType  String
	 * @return EFDataSet
	 */
	public EFDataSet getDataSet(String dsType);

	/**
	 * 
	 * @param dsType    String
	 * @param dataSet   EFDataSet
	 */
	public void setDataSet(String dsType, EFDataSet dataSet);

	/**
	 * 
	 * @param dsType   String
	 */
	public EFDataSet removeDataSet(String dsType);

	/**
	 * 
	 * @param dataSet   EFDataSet
	 */
	public void insertDataSetManager(EFDataSet dataSet);

	/**
	 * 
	 * @param dataSet  EFDataSet
	 */
	public void removeDataSetManager(EFDataSet dataSet);

	/**
	 * 
	 * @return ESPRowSet
	 */
	public ESPRowSet getPrior();

	/**
	 * 
	 * @param rowSet  ESPRowSet
	 */
	public void setPrior(ESPRowSet rowSet);

	/**
	 * 
	 * @return ESPRowSet
	 */
	public ESPRowSet getNext();

	/**
	 * 
	 * @param rowSet  ESPRowSet
	 */
	public void setNext(ESPRowSet rowSet);

	/**
	 * 
	 * @return ESPRowSet
	 */
	public ESPRowSet getFirst();

	/**
	 * 
	 * @return ESPRowSet
	 */
	public ESPRowSet getLast();

	/**
	 * 
	 * @param key    Object
	 * @param value  Object
	 */
	public void setExtProperty(Object key, Object value);

	/**
	 * 
	 * @param key  Object
	 * @param def  Object
	 * @return Object
	 */
	public Object getExtProperty(Object key, Object def);

	/**
	 * 
	 * @return Map
	 */
	public java.util.Map getExtPropertys();

	/**
	 * 
	 * @param extMap  Map
	 */
	public void setExtPropertys(java.util.Map extMap);

	/**
	 * 
	 * @param ID        String
	 * @param defName   String
	 * @return String
	 */
	public String getID2Name(String ColID, String ID, String defName);

	/**
	 * 
	 * @param DCT_ID   String
	 * @param COL_ID   String
	 * @return ESPRowSet
	 */
	public ESPRowSet getID2RowSet(String DCT_ID, String COL_ID);

	/**
	 * 
	 * @param ID     String
	 * @param Name   String
	 */
	public void setID2Name(String ColID, String ID, String Name);

	/**
	 * 
	 * @param DCT_ID   String
	 * @param COL_ID   String
	 * @param rowSet   ESPRowSet
	 */
	public void setID2RowSet(String DCT_ID, String COL_ID, ESPRowSet rowSet);

	/**
	 * 
	 * @param DCT_ID   String
	 * @param COL_ID   String
	 * @param rowSet   ESPRowSet
	 */
	public void setID2RowSetFront(String DCT_ID, String COL_ID, ESPRowSet rowSet);
}
