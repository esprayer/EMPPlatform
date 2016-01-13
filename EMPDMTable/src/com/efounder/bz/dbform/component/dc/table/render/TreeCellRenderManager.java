package com.efounder.bz.dbform.component.dc.table.render;

import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.JXTree.*;
import org.jdesktop.swingx.renderer.*;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.treetable.*;
import com.efounder.bz.dbform.event.*;
import com.efounder.eai.ide.*;
import com.efounder.pub.comp.*;
import com.efounder.service.script.*;

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
public class TreeCellRenderManager extends DefaultTreeRenderer implements
		TitleIcon, Scriptable, ComponentManager, DataComponent {
	/**
	 *
	 */
	public TreeCellRenderManager() {
	}

	/**
	 * 
	 * @param dataObject Object
	 * @param js int
	 * @return Icon
	 */
	public javax.swing.Icon getLeafIcon(Object dataObject, int js) {
		return null;
	}

	/**
	 * 
	 * @param dataObject  Object
	 * @return Icon
	 */
	public Icon getOpenIcon(Object dataObject, int js) {
		return null;
	}

	/**
	 * 
	 * @param dataObject  Object
	 * @return Icon
	 */
	public Icon getCloseIcon(Object dataObject, int js) {
		return null;
	}

	/**
	 * 
	 * @param dataObject Object
	 * @return String
	 */
	public String getTitle(Object dataObject, int js) {
		return null;
	}

	/**
	 * 
	 * @param comp  JComponent
	 * @param dataObject  Object
	 */
	public void confing(JComponent comp, Object dataObject, int js) {

	}

	/**
   *
   */
	protected String dataModelID = null;
	/**
   *
   */
	protected String dataCompName = null;

	/**
	 * 
	 * @return String
	 * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
	 */
	public String getID() {
		return dataModelID;
	}

	/**
	 * 
	 * @return String
	 * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent  method
	 */
	public String getName() {
		return dataCompName;
	}

	/**
	 * 
	 * @param id String
	 * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
	 */
	public void setID(String id) {
		dataModelID = id;
	}

	/**
	 * 
	 * @param name String
	 * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent  method
	 */
	public void setName(String name) {
		dataCompName = name;
	}

	/**
	 * 
	 * @return String
	 */
	// public String toString() {
	// return dataCompName!=null?dataCompName:super.toString();
	// }
	/**
   *
   */
	protected java.util.Map propertyMap = null;

	/**
	 * 
	 * @return Map
	 */
	public java.util.Map getPropertyMap() {
		return propertyMap;
	}

	/**
	 * 
	 * @param propertyMap  Map
	 */
	public void setPropertyMap(java.util.Map propertyMap) {
		this.propertyMap = propertyMap;
	}

	/**
	 * 
	 * @param key Object
	 * @param def Object
	 * @return Object
	 */
	public Object getProperty(Object key, Object def) {
		Object ret = null;
		if (propertyMap == null) {
			return ret;
		}
		ret = propertyMap.get(key);
		return ret == null ? def : ret;
	}

	/**
	 * 
	 * @param key Object
	 * @param value Object
	 */
	public void setProperty(Object key, Object value) {
		if (propertyMap == null) {
			propertyMap = new HashMap();
		}
		propertyMap.put(key, value);
	}

	/**
   *
   */
	protected DataContainer dataContainer = null;

	/**
	 * 
	 * @return DataContainer
	 */
	public DataContainer getDataContainer() {
		return dataContainer;
	}

	/**
	 * 
	 * @param dataContainer
	 *            DataContainer
	 */
	public void setDataContainer(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}

	/**
   *
   */
	protected java.util.Map eventMap = new HashMap();

	/**
	 * 
	 * @return Map
	 */
	public Map getEventMap() {
		return eventMap;
	}

	/**
	 * 
	 * @param eventMap  Map
	 */
	public void setEventMap(Map eventMap) {
		this.eventMap = eventMap;
	}

	/**
   *
   */
	protected DataComponent parent = null;

	/**
	 * 
	 * @return DataComponent
	 */
	public DataComponent getParentDataComponent() {
		return parent;
	}

	/**
	 * 
	 * @param dc  DataComponent
	 */
	public void setParentDataComponent(DataComponent dc) {
		parent = dc;
		if (parent instanceof DataContainer) {
			this.dataContainer = (DataContainer) parent;
		}
	}

	/**
   *
   */
	// protected java.util.List childList = null;
	/**
	 * 
	 * @return List
	 */
	public java.util.List getChildList() {
		return null;
	}

	/**
	 * 
	 * @param childList List
	 */
	public void setChildList(java.util.List childList) {
		// this.childList = childList;
	}

	/**
	 * 
	 * @param index  int
	 * @return DataComponent
	 */
	public DataComponent getDataComponent(int index) {
		// if ( childList == null ) return null;
		// if ( index < 0 || index >= childList.size() ) return null;
		// return (DataComponent)childList.get(index);
		return null;
	}

	/**
	 * 
	 * @param ID  String
	 * @return DataComponent
	 */
	public DataComponent getDataComponent(String ID) {
		// if ( childList == null ) return null;
		// for(int i=0;i<childList.size();i++) {
		// if ( ID.equals(((DataComponent)childList.get(i)).getID()) ) {
		// return (DataComponent)childList.get(i);
		// }
		// }
		return null;
	}

	/**
	 * 
	 * @param dataComponent  DataComponent
	 */
	public int insertDataComponent(DataComponent dataComponent) {
		// if ( childList == null ) childList = new java.util.ArrayList();
		// childList.add(dataComponent);
		// dataComponent.setParent(this);
		return -1;
	}

	/**
	 * 
	 * @param dataComponent DataComponent
	 */
	public void removeDataComponent(DataComponent dataComponent) {
		// if ( dataComponent != null ) {
		// this.childList.remove(dataComponent);
		// dataComponent.setParent(null);
		// }
	}

	/**
	 * 
	 * @param childDC DataComponent
	 * @return boolean
	 */
	public boolean canAddChild(DataComponent childDC) {
		return false;
	}

	/**
   *
   */
	protected FormFunctionObject customFunction = null;

	/**
	 * 
	 * @return FormFunctionObject
	 */
	public FormFunctionObject getCustomFunction() {
		return customFunction;
	}

	/**
	 * 
	 * @param ffo FormFunctionObject
	 */
	public void setCustomFunction(FormFunctionObject ffo) {
		this.customFunction = ffo;
	}

	/**
	 * 
	 * @param scriptManager ScriptManager
	 */
	public void initScript(ScriptManager scriptManager) {
	}

	/**
	 * 
	 * @param scriptManager ScriptManager
	 */
	public void finishScript(ScriptManager scriptManager) {
	}

	/**
	 * 
	 * @return ScriptObject
	 */
	public ScriptObject getScriptObject() {
		return null;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getScriptKey() {
		return null;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getScriptInstance() {
		return null;
	}

	/**
	 * 
	 * @return ScriptManager
	 */
	public ScriptManager getScriptManager() {
		return null;
	}

	public void sysInitDataComponent() {
		// TODO Auto-generated method stub

	}

}
