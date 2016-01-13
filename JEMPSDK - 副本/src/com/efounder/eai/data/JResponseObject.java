package com.efounder.eai.data;

import java.util.Map;

import com.efounder.builder.base.data.EFDataSet;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author ES
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
// 描述:
// 设计: ES(2013.09.12)
// 实现: ES
// 修改:
// --------------------------------------------------------------------------------------------------
public class JResponseObject implements java.io.Serializable {
	public static final int RES_ERROR = -1;
	public static final int RES_OK = 0x0000;
	public static final int RES_JUMP_OVER = 0xFF00;
	public Object ResponseObject;
	public Object addinObject = null;
	public int ErrorCode;
	public String ErrorString;
	protected java.util.Map responseMap = null;

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject() {
		ResponseObject = null;
		ErrorCode = 0;
		ErrorString = null;
	}

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject(Object RO) {
		ResponseObject = RO;
		ErrorCode = 0;
		ErrorString = null;
	}

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject(Object RO, int EC) {
		ResponseObject = RO;
		ErrorCode = EC;
		ErrorString = null;
	}

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject(Object RO, int EC, String ES) {
		ResponseObject = RO;
		ErrorCode = EC;
		ErrorString = ES;
	}
	
	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject(Object RO, int EC, Exception ex) {
		ResponseObject = RO;
		ErrorCode = EC;
		exceptoin=ex;
		if ( ex != null ) {
		      ErrorString = ex.getMessage();
		}
	}

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public int GetErrorCode() {
		return ErrorCode;
	}

	/**
	 * 
	 * @return Map
	 */
	public Map getResponseMap() {
		return responseMap;
	}

	/**
	 * 
	 * @param responseMap Map
	 */
	public void setResponseMap(Map responseMap) {
		this.responseMap = responseMap;
	}

	/**
	 * 
	 * @param key Object
	 * @return Object
	 */
	public Object getResponseObject(Object key) {
		if (responseMap != null) return responseMap.get(key);
		return null;
	}

	/**
	 * 
	 * @param key Object
	 * @param value Object
	 */
	public void setResponseObject(Object key, Object value) {
		if (responseMap == null) responseMap = new java.util.HashMap();
		responseMap.put(key, value);
	}

	protected Exception exceptoin = null;

	/**
	 * 
	 * @return Object
	 */
	public Object getAddinObject() {
		return addinObject;
	}

	/**
	 * 
	 * @param addinObject Object
	 */
	public void setAddinObject(Object addinObject) {
		this.addinObject = addinObject;
		EFDataSet dataset = null;
		
	}

	/**
	 * 
	 * @param ResponseObject Object
	 */
	public void setResponseObject(Object ResponseObject) {
		this.ResponseObject = ResponseObject;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getResponseObject() {
		return ResponseObject;
	}

	/**
	 * 
	 * @return String
	 */
	public String getErrorString() {
		return ErrorString;
	}

	/**
	 * 
	 * @param er String
	 */
	public void setErrorString(String er) {
		ErrorString = er;
	}

	/**
	 * 
	 * @return Exception
	 */
	public Exception getExceptoin() {
		return exceptoin;
	}

	/**
	 * 
	 * @return int
	 */
	public int getErrorCode() {
		return ErrorCode;
	}

	/**
	 * 
	 * @param code int
	 */
	public void setErrorCode(int code) {
		this.ErrorCode = code;
	}
}
