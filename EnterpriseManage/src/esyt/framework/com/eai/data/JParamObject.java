package esyt.framework.com.eai.data;

import java.util.*;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author ES
 * @version 1.0
 */
public class JParamObject implements java.io.Serializable, KeyValue {
	public transient Object httpServletRequest = null;

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
	 * @return HttpServlet
	 */
	public Object getHttpServlet() {
		return httpServlet;
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
		return PO;
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
}
