package com.efounder.eai.data;

import java.util.*;

import org.openide.util.Lookup;

import com.core.xml.JXMLBaseObject;
import com.core.xml.StubObject;
import com.efounder.eai.EAI;
import com.efounder.eai.EAIServer;
import org.jdom.Element;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author ES
 * @version 1.0
 */
public class JParamObject implements java.io.Serializable, KeyValue {
	
	public static final String DEF_BIZMODEL="DEF_BIZMODEL";
	
	public transient Object httpServletRequest = null;

	/**
	   *
	   */
	private transient JParamObject parentParamObject = null;

	/**
	 * 
	 * @return JParamObject
	 */
	public JParamObject getParentParamObject() {
		return parentParamObject;
	}

	public Object getHttpServletRequest() {
		return httpServletRequest;
	}

	public void setHttpServletRequest(Object httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public transient Object httpServletResponse = null;

	public Object getHttpServletResponse() {
		return httpServletResponse;
	}

	public void setHttpServletResponse(Object httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}

	/**
	 *
	 */
	public transient Object httpServlet = null;

	/**
	 *
	 */
	private transient java.util.Map losableList = null;
	  
	/**
	 * 
	 * @return HttpServlet
	 */
	public Object getHttpServlet() {
		return httpServlet;
	}

	/**
	 * 
	 * @param key   Object
	 * @param value Object
	 */
	public void setLosableValue(Object key, Object value) {
		if (losableList == null) losableList = new Hashtable();
		if (value == null) losableList.remove(key);
		else losableList.put(key, value);
	}
	
	/**
	 *
	 * @param key Object
	 * @return Object
	 */
	public Object getLosableValue(Object key) {
		return getLosableValue(key,null);
	}
	  
	/**
	 * 
	 * @param key    Object
	 * @param def    Object
	 * @return Object
	 */
	public Object getLosableValue(Object key, Object def) {
		if (losableList == null) return def;
		Object o = losableList.get(key);
		if (o == null) o = def;
		return o;
	}
	  
	/**
	 * 
	 * @param httpServlet HttpServlet
	 */
	public void setHttpServlet(Object httpServlet) {
		this.httpServlet = httpServlet;
	}

	/**
	 *
	 */
	static final long serialVersionUID = 1L;
	/**
	 *
	 */
	protected final transient static Hashtable GPO = new Hashtable();
	/**
	 *
	 */
	protected Hashtable EnvRoot = new Hashtable();
	/**
	 *
	 */
	protected Hashtable ParamRoot = new Hashtable();

	/**
	 * 
	 * @return Hashtable
	 */
	public Hashtable getParamRoot() {
		return ParamRoot;
	}

	/**
	 * 
	 * @param ht Hashtable
	 */
	public void setParamRoot(Hashtable ht) {
		ParamRoot = ht;
	}

	/**
	 * 
	 * @return Hashtable
	 */
	public Hashtable getEnvRoot() {
		return EnvRoot;
	}

	/**
	 * 
	 * @param ht Hashtable
	 */
	public void setEnvRoot(Hashtable ht) {
		EnvRoot = ht;
	}

	/**
	 *
	 */
	public JParamObject() {

	}

	/**
	 * 
	 * @return JParamObject
	 */
	public static JParamObject Create() {
		JParamObject PO = new JParamObject();
		PO.EnvRoot = (Hashtable)GPO.clone();
		return PO;
	}

	/**
	 *
	 * @param paramKey String
	 * @return JParamObject
	 */
	public static JParamObject Create(String paramKey) {
		JParamObject paramObject = null;
	    paramObject = (JParamObject)Lookup.getDefault().lookups(JParamObject.class,paramKey);
	    // 如果是中间层，则默认的
	    if ( "Middle".equals(EAI.Tier) ) {
//	      paramObject.autoConnection = false; // 如果是中间层，则默认的是不关闭连接
	    }
	    return paramObject;
	}
	  
	/**
	 *
	 */
	private transient java.util.List connList = null;

	/**
	 * 
	 * @return List
	 */
	public java.util.List getConnList() {
		return connList;
	}

	/**
	 *
	 */
	private boolean autoConnection = true;

	/**
	 * 
	 * @return boolean
	 */
	public boolean isAutoConnection() {
		Object AUTOCLOSE = getLosableValue("AUTOCLOSE");
		return (autoConnection && (AUTOCLOSE == null || "1".equals(AUTOCLOSE)));
	}

	/**
	 * 
	 * @param b   boolean
	 */
	public void setAutoConnection(boolean b) {
		if (b && losableList != null) losableList.remove("AUTOCLOSE");
		autoConnection = b;
	}

	/**
	 * 
	 * @param conn   Object
	 */
	public void addConn(Object conn) {
		addConn(this, conn);
	}

	/**
	 * 
	 * @param conn  Object
	 */
	public void removeConn(Object conn) {
		removeConn(this, conn);
	}

	/**
	 * 
	 * @param PO     JParamObject
	 * @param conn   Object
	 */
	private static void addConn(JParamObject PO, Object conn) {
		// 如果设置自动管理连接，则可以设置数据库连接
		if (PO.parentParamObject == null && PO.isAutoConnection()) {
			if (PO.connList == null)
				PO.connList = new ArrayList();
			PO.connList.add(conn);
		} else {
			addConn(PO.parentParamObject, conn);
		}
	}
	  /**
	   *
	   * @param PO JParamObject
	   * @param conn Object
	   */
	  private static void removeConn(JParamObject PO,Object conn) {
	    if ( PO.parentParamObject == null ) {
	      if ( PO.connList == null ) PO.connList = new ArrayList();
	      PO.connList.remove(conn);
	    } else {
	      removeConn(PO.parentParamObject,conn);
	    }
	  }
	  
	/**
	 *
	 */
	public void DeleteParam() {
		ParamRoot = null;
	}

	/**
	 *
	 */
	private void createParam() {
		if (ParamRoot == null) ParamRoot = new Hashtable();
	}

	/**
	 *
	 */
	private void createEnv() {
		if (EnvRoot == null) EnvRoot = new Hashtable();
	}

	/**
	 * 
	 * @param ParamName String
	 * @return String
	 */
	public String GetValueByParamName(String ParamName) {
		return GetValueByParamName(ParamName, "");
	}

	/**
	 * 
	 * @param ParamName String
	 * @param Defalut String
	 * @return String
	 */
	public String GetValueByParamName(String ParamName, String Defalut) {
		String Text = null;
		if (ParamRoot != null) Text = (String) ParamRoot.get(ParamName);
		if (Text == null) Text = Defalut;
		return Text;
	}

	/**
	 * 
	 * @param ParamName String
	 * @return int
	 */
	public int GetIntByParamName(String ParamName) {
		return GetIntByParamName(ParamName, -1);
	}

	/**
	 * 
	 * @param ParamName String
	 * @param Default int
	 * @return int
	 */
	public int GetIntByParamName(String ParamName, int Default) {
		int Res = Default;
		try {
			Res = Integer.valueOf(GetValueByParamName(ParamName)).intValue();
		} catch (Exception e) {
		}
		return Res;
	}

	/**
	 * 
	 * @param EnvName
	 *            String
	 * @return String
	 */
	public String GetValueByEnvName(String EnvName) {
		return GetValueByEnvName(EnvName, "");
	}

	/**
	 * 
	 * @param EnvName
	 *            String
	 * @param Default
	 *            String
	 * @return String
	 */
	public String GetValueByEnvName(String EnvName, String Default) {
		String Text = null;
		if (EnvRoot != null) Text = (String) EnvRoot.get(EnvName);
		return Text == null ? Default : Text;
	}

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public int GetIntByEnvName(String EnvName) {
		return GetIntByEnvName(EnvName, -1);
	}

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public int GetIntByEnvName(String EnvName, int Def) {
		int Res = Def;
		try {
			Res = Integer.valueOf(GetValueByEnvName(EnvName)).intValue();
		} catch (Exception e) {

		}
		return Res;
	}

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public void SetValueByParamName(String ParamName, String ParamValue) {
		this.createParam();
		if (ParamValue == null) ParamRoot.remove(ParamName);
		else ParamRoot.put(ParamName, ParamValue);
	}

	/**
	 * 
	 * @param key Object
	 * @param value Object
	 */
	public void setValue(Object key, Object value) {
		this.createParam();
		if (value == null) ParamRoot.remove(key);
		else ParamRoot.put(key, value);
	}

	/**
	 * 
	 * @param key Object
	 * @param defvalue Object
	 * @return Object
	 */
	public Object getValue(Object key, Object defvalue) {
		if (ParamRoot == null) return defvalue;
		return ParamRoot.get(key);
	}

	/**
	 * 
	 * @return Map
	 */
	public java.util.Map getAttriMap() {
		return ParamRoot;
	}

	/**
	 * 
	 * @param key Object
	 * @param value Object
	 */
	public void setEnvValue(Object key, Object value) {
		this.createEnv();
		if (value == null) EnvRoot.remove(key);
		else EnvRoot.put(key, value);
	}

	/**
	 * 
	 * @param key Object
	 * @param defvalue Object
	 * @return Object
	 */
	public Object getEnvValue(Object key, Object defvalue) {
		Object res = EnvRoot.get(key);
		if (EnvRoot != null) {
			res = EnvRoot.get(key);
		}
		return res;
	}

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public void SetIntByParamName(String ParamName, int ParamValue) {
		SetValueByParamName(ParamName, String.valueOf(ParamValue));
	}

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public void SetValueByEnvName(String EnvName, String EnvValue) {
		this.createEnv();
		if (EnvValue == null) EnvRoot.remove(EnvName);
		else EnvRoot.put(EnvName, EnvValue);
	}

	// --------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public void SetIntByEnvName(String ParamName, int ParamValue) {
		SetValueByEnvName(ParamName, String.valueOf(ParamValue));
	}

	/**
	 * 
	 * @return JParamObject
	 */
	public Object clone() {
		JParamObject po = JParamObject.Create();
		if (po.EnvRoot == null) po.EnvRoot = new Hashtable();
		po.EnvRoot.clear();
		po.EnvRoot.putAll(this.getEnvRoot());
		if (po.ParamRoot == null) po.ParamRoot = new Hashtable();
		po.ParamRoot.clear();
		po.ParamRoot.putAll(this.getParamRoot());
		po.httpServlet = this.httpServlet;
		po.httpServletRequest = this.httpServletRequest;
		po.httpServletResponse = this.httpServletResponse;
		return po;
	}
	
	/**
	 *
	 * @param key Object
	 * @param def Object
	 * @return Object
	 */
	static public Object getObject(Object key,Object def) {
		Object res = GPO.get(key);
	    if ( res == null ) res = def;
	    return res;
	}
	/**
	 *
	 * @param key Object
	 * @param value Object
	 */
	static public void setObject(Object key,Object value) {
	    if ( value == null )
	      GPO.remove(key);
	    else
	      GPO.put(key,value);
	}
	
	/**
	 *
	 * @param PO JParamObject
	 */
	static public void assign(JParamObject PO) {
		GPO.putAll(PO.getEnvRoot());
	}
	
	/**
	 *
	 */
	private transient String eaiServer = null;
	  
	/**
	 *
	 * @param eaiServer String
	 */
	public void setEAIServer(String eaiServer) {
		this.eaiServer = eaiServer;
	    /**
	     * 如果eaiServer为空或者根据eaiServer没有找到配置信息，连当前登陆的帐套
	     */
	    if ( eaiServer != null && eaiServer.trim().length() > 0 ) {
	      StubObject serverStub = EAIServer.getEAIServer(eaiServer);
	      if ( serverStub != null ) {
	        // 设置相应的数据库设置
	        String dbset = serverStub.getString(EAIServer.SERVER_DBID, null);
	        if (dbset != null && dbset.trim().length() > 0)
	          this.SetValueByEnvName("DataBaseName", dbset);
	        dbset = serverStub.getString(EAIServer.SERVER_ZTID, null);
	        if (dbset != null && dbset.trim().length() > 0)
	          this.SetValueByEnvName("DBNO", dbset);
	      } else this.setDefaultDB();
	    } else this.setDefaultDB();
	}
	
	/**
	 * 设置当前登录的存储ID和帐套ID
	 */
	private void setDefaultDB(){
		this.eaiServer = null;
		this.SetValueByEnvName(EAIServer.SERVER_DBID, (String)getObject(EAIServer.SERVER_DBID, ""));
		this.SetValueByEnvName(EAIServer.SERVER_ZTID, (String)getObject(EAIServer.SERVER_ZTID, ""));
	}
	
	public String getEAIServer(String def) {
	    if ( eaiServer == null || eaiServer.trim().length() == 0 ) return def;
	    return eaiServer;
	}

	/**
	 *
	 */
	protected java.util.List loginfoList = null;

	/**
	 * 
	 * @param list
	 *            List
	 */
	public void setLoginfoList(java.util.List list) {
		if (list != null && list.size() > 0) loginfoList = list;
		else loginfoList = null;
	}

	/**
	 * 
	 * @return List
	 */
	public java.util.List getLoginfoList() {
		return loginfoList;
	}

	/**
	 *
	 */
	protected transient String serviceURL = null;

	/**
	 * 
	 * @return String
	 */
	public String getServiceURL() {
		return serviceURL;
	}

	/**
	 * 
	 * @param surl String
	 */
	public void setServiceURL(String surl) {
		serviceURL = surl;
	}
	
	/**
	 * getBIZValue
	 *
	 * @param object Object
	 * @param object1 Object
	 * @return Object
	 * @todo Implement this com.efounder.model.biz.BIZContext method
	 */
	public Object getBIZValue(Object object, Object object1) {
		return this.getValue(object,object1);
	}
	
	/**
	 *
	 * @param object Object
	 * @param object1 Object
	 */
	public void setBIZValue(Object object, Object object1) {
		this.setValue(object,object1);
	}
	
	/**
	 *
	 */
	protected String appUniqueID = null;
	
	/**
	 *
	 * @return String
	 */
	public String getAppUniqueID() {
		if ( appUniqueID != null ) return appUniqueID;
	    String dbid = (String)this.getEnvValue("DataBaseName",null);
	    String dbno = (String)this.getEnvValue("DBNO",null);
	    if ( dbid == null || dbno == null ) return null;
	    return dbid+"_"+dbno;
	}
	/**
	 *
	 * @param id String
	 */
	public void setAppUniqueID(String id) {
		appUniqueID = id;
	}
	
	public JParamObject(String ParamString) {
		this();
	    putParamString(ParamString);
	}
	
	public void putParamString(String ParamString) {
		JXMLBaseObject xml = new JXMLBaseObject(ParamString);
	    List envAttList = xml.getElementsByTagName("environments");
	    putVaueToMap(envAttList, this.getEnvRoot());
	    
	    List cusAttList = xml.getElementsByTagName("params");
	    putVaueToMap(cusAttList, this.getAttriMap());
	}
	
	protected static void putVaueToMap(List attList, Map desMap) {
	    if ((attList == null) || (attList.size() == 0)) {
	      return;
	    }
	    for (int i = 0; i < attList.size(); i++) {
	      Element e = (Element)attList.get(i);
	      String key = e.getName();
	      key = e.getName().substring(2);
	      desMap.put(key, e.getAttributeValue("value"));
	    }
	}
}
