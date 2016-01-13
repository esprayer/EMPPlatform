package com.efounder.bz.dbform.datamodel;

import com.efounder.eai.data.KeyValue;
import com.efounder.service.script.ScriptManager;
import com.efounder.service.script.ScriptObject;
import com.efounder.service.script.Scriptable;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.Lookup;

public class DataContainer extends DataComponentAdapter implements KeyValue {
	private Map MLangMap = null;
	private String stubID;

	public Map getMLangMap() {
		return this.MLangMap;
	}

	public void setMLangMap(Map mlM) {
		this.MLangMap = mlM;
	}

	protected boolean designer = false;

	public boolean checkDesigner() {
		return this.designer;
	}

	public void setDesigner(boolean d) {
		this.designer = d;
	}

	protected ComponentBuilder prepareBuilder = null;

	public ComponentBuilder getPrepareBuilder() {
		return this.prepareBuilder;
	}

	public void setPrepareBuilder(ComponentBuilder componentBuilder) {
		this.prepareBuilder = componentBuilder;
	}

	protected ComponentBuilder processBuilder = null;

	public ComponentBuilder getProcessBuilder() {
		return this.processBuilder;
	}

	public void setProcessBuilder(ComponentBuilder componentBuilder) {
		this.processBuilder = componentBuilder;
	}

	public String getStubID() {
		return this.stubID;
	}

	public void setStubID(String stubID) {
		this.stubID = stubID;
	}

	protected transient Map valueMap = new HashMap();

	public int getInt(Object Key, int def) {
		int res = def;
		Object re = null;
		try {
			re = this.valueMap.get(Key);
			if (re == null) {
				re = "0";
			}
			res = Integer.parseInt(re.toString());
		} catch (Exception e) {
			res = def;
		}
		return res;
	}

	public void setInt(Object key, int v) {
		this.valueMap.put(key, new Integer(v));
	}

	public static DataContainer getInstance(String id, String name) {
		DataContainer dc = null;
		dc = (DataContainer) Lookup.getDefault().lookups(DataContainer.class,
				id);
		if (dc == null) {
			return null;
		}
		dc.setID(id);
		dc.setName(name);
		return dc;
	}

	protected transient Map valueSet = null;

	public Object getValue(Object key, Object def) {
		if (this.valueSet == null) {
			return def;
		}
		Object res = this.valueSet.get(key);
		return res == null ? def : res;
	}

	public void setValue(Object key, Object value) {
		if (this.valueSet == null) {
			this.valueSet = new HashMap();
		}
		this.valueSet.put(key, value);
	}

	public Map getAttriMap() {
		return this.valueSet;
	}

	public void setAttribMap(Map v) {
		this.valueSet = v;
	}

	protected Map objectMap = null;

	public Map getTranObjMap() {
		return this.objectMap;
	}

	public void setObjectMap(Map om) {
		this.objectMap = om;
	}

	public void setObject(Object key, Object obj) {
		if (this.objectMap == null) {
			this.objectMap = new HashMap();
		}
		this.objectMap.put(key, obj);
	}

	public Object getObject(Object key, Object def) {
		if ((this.objectMap == null) || (this.objectMap.get(key) == null)) {
			return def;
		}
		return this.objectMap.get(key);
	}

	protected Map returnMap = null;

	public Map getReturnMap() {
		return this.returnMap;
	}

	public void setReturnMap(Map rm) {
		this.returnMap = rm;
	}

	public void setReturnObject(Object key, Object obj) {
		if (this.returnMap == null) {
			this.returnMap = new HashMap();
		}
		this.returnMap.put(key, obj);
	}

	public Object getReturnObject(Object key, Object def) {
		if ((this.returnMap == null) || (this.returnMap.get(key) == null)) {
			return def;
		}
		return this.returnMap.get(key);
	}

	protected ScriptManager scriptManager = null;

	public ScriptManager getScriptManager() {
		return this.scriptManager;
	}

	public void setScriptManager(ScriptManager scriptManager) {
		this.scriptManager = scriptManager;
	}

	public void initScript(ScriptManager scriptManager) {
	}

	public void finishScript(ScriptManager scriptManager) {
	}

	public ScriptObject getScriptObject() {
		return null;
	}

	public Object getScriptKey() {
		return null;
	}

	public Object getScriptInstance() {
		return null;
	}
}
