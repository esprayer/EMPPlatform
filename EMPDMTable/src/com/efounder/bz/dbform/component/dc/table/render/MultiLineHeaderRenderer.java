package com.efounder.bz.dbform.component.dc.table.render;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.efounder.bz.dbform.bizmodel.MDModel;
import com.efounder.bz.dbform.datamodel.ColumnModel;
import com.efounder.bz.dbform.datamodel.ComponentManager;
import com.efounder.bz.dbform.datamodel.DataComponent;
import com.efounder.bz.dbform.datamodel.DataContainer;
import com.efounder.bz.dbform.event.FormFunctionObject;
import com.efounder.service.script.ScriptManager;
import com.efounder.service.script.ScriptObject;
import com.efounder.service.script.Scriptable;

public class MultiLineHeaderRenderer extends JTextPane implements TableCellRenderer,TitleIcon,Scriptable,ComponentManager,DataComponent {   
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4942182842647351867L;  
	/** map from table to map of rows to map of column heights */  
	private final Map cellSizes = new HashMap();   

	public MultiLineHeaderRenderer() { 
//		super(1,50); 
        setOpaque(true); 
//		setLineWrap(true);   
//		setWrapStyleWord(true);   
		setHighlighter(null);

		SimpleAttributeSet bSet = new SimpleAttributeSet();  
        StyleConstants.setAlignment(bSet, StyleConstants.ALIGN_CENTER); 
		this.getStyledDocument().setParagraphAttributes(0, 104, bSet, false);
	}   

	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {   
		// 设置表头渲染时的一些属性参数   

		JTableHeader header = table.getTableHeader();   
		setForeground(header.getForeground());   
		setBackground(header.getBackground());   
		setFont(header.getFont());   
		setText(obj.toString());   
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));  

		this.setSize(new Dimension(80, this.getLineCount()*22));
		// This line was very important to get it working with JDK1.4   
		TableColumnModel columnModel = table.getColumnModel();   
		if(column == -1) return this;
		int width = columnModel.getColumn(column).getWidth();     
		// 设置JTextArea的大小   
		this.setSize(new Dimension(width, this.getLineCount()*22));   //height_wanted

		return this;   
	}   
	
	public int getLineCount() 
    {
        //tp是一个JTextPane
        Element map = this.getDocument().getDefaultRootElement();
        return map.getElementCount();
    }

	/**
	 *
	 */
	protected MDModel mdModel = null;
	/**
	 *
	 * @return MDModel
	 */
	public MDModel getMDModel() {
		return mdModel;
	}
	/**
	 *
	 * @param mdModel MDModel
	 */
	public void setMDModel(MDModel mdModel) {
		this.mdModel = mdModel;
	}
	  
	/**
	 *
	 */
	private ColumnModel columnModel;
	/**
	 *
	 * @param columnModel ColumnModel
	 */
	public void setColumnModel(ColumnModel columnModel) {
		this.columnModel = columnModel;
	}
	/**
	 *
	 * @return ColumnModel
	 */
	public ColumnModel getColumnModel() {
		return columnModel;
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
	 * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
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
	 * @todo Implement this com.efounder.bz.dbform.datamodel.DataComponent method
	 */
	public void setName(String name) {
		dataCompName = name;
	}
	/**
	 *
	 * @return String
	 */
	//  public String toString() {
	//	    return dataCompName!=null?dataCompName:super.toString();
	//  }
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
	 * @param propertyMap Map
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
	public Object getProperty(Object key,Object def) {
		Object ret = null;
		if ( propertyMap == null ) return ret;
		ret = propertyMap.get(key);
		return ret==null?def:ret;
	}
	/**
	 *
	 * @param key Object
	 * @param value Object
	 */
	public void setProperty(Object key,Object value) {
		if ( propertyMap == null ) propertyMap = new HashMap();
		propertyMap.put(key,value);
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
	 * @param dataContainer DataContainer
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
	 * @param eventMap Map
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
	 * @param dc DataComponent
	 */
	public void setParentDataComponent(DataComponent dc) {
		parent = dc;
		if ( parent instanceof DataContainer ) {
			this.dataContainer = (DataContainer)parent;
		}
	}
	/**
	 *
	 */
	//  protected java.util.List childList = null;
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
		//	    this.childList = childList;
	}
	/**
	 *
	 * @param index int
	 * @return DataComponent
	 */
	public DataComponent getDataComponent(int index) {
		//	    if ( childList == null ) return null;
		//	    if ( index < 0 || index >= childList.size() ) return null;
		//	    return (DataComponent)childList.get(index);
		return null;
	}
	/**
	 *
	 * @param ID String
	 * @return DataComponent
	 */
	public DataComponent getDataComponent(String ID) {
		//	    if ( childList == null ) return null;
		//	    for(int i=0;i<childList.size();i++) {
		//	      if ( ID.equals(((DataComponent)childList.get(i)).getID()) ) {
		//	        return (DataComponent)childList.get(i);
		//	      }
		//	    }
		return null;
	}
	/**
	 *
	 * @param dataComponent DataComponent
	 */
	public int insertDataComponent(DataComponent dataComponent) {
		//	    if ( childList == null ) childList = new java.util.ArrayList();
		//	    childList.add(dataComponent);
		//	    dataComponent.setParent(this);
		return -1;
	}
	/**
	 *
	 * @param dataComponent DataComponent
	 */
	public void removeDataComponent(DataComponent dataComponent) {
		//	    if ( dataComponent != null ) {
		//	      this.childList.remove(dataComponent);
		//	      dataComponent.setParent(null);
		//	    }
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

	public void sysInitDataComponent() {
		// TODO Auto-generated method stub

	}
	
	public Icon getIconByData(byte[] sign){
		try{
			java.io.InputStream inStream = new java.io.ByteArrayInputStream(sign);
			java.awt.Image image = javax.imageio.ImageIO.read(inStream);
			javax.swing.ImageIcon ii = new javax.swing.ImageIcon();
			ii.setImage(image);
			return ii;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Icon getLeafIcon(Object dataObject, int js) {
		return null;
	}

	public Icon getOpenIcon(Object dataObject, int js) {
		return null;
	}

	public Icon getCloseIcon(Object dataObject, int js) {
		return null;
	}

	public String getTitle(Object dataObject, int js) {
		return null;
	}

	public void confing(JComponent comp, Object dataObject, int js) {
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
}
