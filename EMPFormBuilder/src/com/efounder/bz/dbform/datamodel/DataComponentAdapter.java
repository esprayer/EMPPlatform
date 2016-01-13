package com.efounder.bz.dbform.datamodel;

import com.efounder.bz.dbform.event.FormFunctionObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class DataComponentAdapter implements DataComponent {
	protected String dataModelID = null;
	protected String dataCompName = null;

	public String getID() {
		return this.dataModelID;
	}

	public String getName() {
		return this.dataCompName;
	}

	public void setID(String id) {
		this.dataModelID = id;
	}

	public void setName(String name) {
		this.dataCompName = name;
	}

	public String toString() {
		return this.dataCompName != null ? this.dataCompName : super.toString();
	}

	protected Map propertyMap = null;

	public Map getPropertyMap() {
		return this.propertyMap;
	}

	public void setPropertyMap(Map propertyMap) {
		this.propertyMap = propertyMap;
	}

	public Object getProperty(Object key, Object def) {
		Object ret = null;
		if (this.propertyMap == null) {
			return ret;
		}
		ret = this.propertyMap.get(key);
		return ret == null ? def : ret;
	}

	public void setProperty(Object key, Object value) {
		if (this.propertyMap == null) {
			this.propertyMap = new HashMap();
		}
		this.propertyMap.put(key, value);
	}

	protected DataContainer dataContainer = null;

	public DataContainer getDataContainer() {
		return getDataContainer(this);
	}

	public void setDataContainer(DataContainer dataContainer) {
	}

	protected DataContainer getDataContainer(DataComponent dc) {
		if (dc == null) {
			return null;
		}
		if ((dc instanceof DataContainer)) {
			return (DataContainer) dc;
		}
		return getDataContainer(dc.getParentDataComponent());
	}

	protected Map eventMap = new HashMap();

	public Map getEventMap() {
		return this.eventMap;
	}

	public void setEventMap(Map eventMap) {
		this.eventMap = eventMap;
	}

	protected DataComponent parent = null;

	public DataComponent getParentDataComponent() {
		return this.parent;
	}

	public void setParentDataComponent(DataComponent dc) {
		this.parent = dc;
		if ((this.parent instanceof DataContainer)) {
			this.dataContainer = ((DataContainer) this.parent);
		}
	}

	protected List childList = null;

	public List getChildList() {
		return this.childList;
	}

	public void setChildList(List childList) {
		this.childList = childList;
	}

	public DataComponent getDataComponent(String ID) {
		if (this.childList == null) {
			return null;
		}
		for (int i = 0; i < this.childList.size(); i++) {
			if (ID.equals(((DataComponent) this.childList.get(i)).getID())) {
				return (DataComponent) this.childList.get(i);
			}
		}
		return null;
	}

	public DataComponent getDataComponent(int index) {
		if (this.childList == null) {
			return null;
		}
		if ((index < 0) || (index >= this.childList.size())) {
			return null;
		}
		return (DataComponent) this.childList.get(index);
	}

	public boolean canAddChild(DataComponent childDC) {
		return true;
	}

	public int insertDataComponent(DataComponent dataComponent) {
		if ((dataComponent == null) && (canAddChild(dataComponent))) {
			return -1;
		}
		if (this.childList == null) {
			this.childList = new Vector();
		}
		this.childList.add(dataComponent);
		if (dataComponent.getParentDataComponent() == null) {
			dataComponent.setParentDataComponent(this);
		}
		return this.childList.size() - 1;
	}

	public void removeDataComponent(DataComponent dataComponent) {
		if (dataComponent != null) {
			this.childList.remove(dataComponent);
			dataComponent.setParentDataComponent(null);
		}
	}

	protected FormFunctionObject customFunction = null;

	public FormFunctionObject getCustomFunction() {
		return this.customFunction;
	}

	public void setCustomFunction(FormFunctionObject ffo) {
		this.customFunction = ffo;
	}

	public void sysInitDataComponent() {
	}
}
