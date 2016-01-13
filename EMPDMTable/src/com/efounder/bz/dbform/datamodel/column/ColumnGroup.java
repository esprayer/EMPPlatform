package com.efounder.bz.dbform.datamodel.column;

import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.event.FormFunctionObject;
import org.jdesktop.swingx.table.TableColumnExt;

/**
 *
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
public class ColumnGroup
    extends TableColumnExt implements DataComponent,DataModelComponent {
  /**
   *
   */
  public ColumnGroup() {
    this(null, "Title");
  }
  /**
   *
   */
  protected TableCellRenderer renderer;
  /**
   *
   */
  protected String text;
  /**
   *
   */
  protected int margin = 0;
  /**
   *
   * @param text String
   */
  public ColumnGroup(String text) {
    this(null, text);
  }
  /**
   *
   * @param modelIndex int
   */
  public ColumnGroup(int modelIndex) {
    super(modelIndex, 75, null, null);
  }
  /**
   *
   * @param renderer TableCellRenderer
   * @param text String
   */
  public ColumnGroup(TableCellRenderer renderer, String text) {
    if (renderer == null) {
      this.renderer = new DefaultTableCellRenderer() {
        public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected, boolean hasFocus, int row,
            int column) {
          JTableHeader header = new JTableHeader(); //table.getTableHeader();
          if (header != null) {
            setForeground(header.getForeground());
            setBackground(header.getBackground());
            setFont(header.getFont());
          }
          setHorizontalAlignment(JLabel.CENTER);
          setText( (value == null) ? "" : value.toString());
          setBorder(UIManager.getBorder("TableHeader.cellBorder"));
          return this;
        }
      };
    }
    else {
      this.renderer = renderer;
    }
    this.text = text;
  }

  /**
   *
   * @param obj DataComponent
   */
  public void add(DataComponent obj) {
    if (obj == null) return;
    this.insertDataComponent(obj);
  }

  /**
   * @param c    TableColumn
   * @param v    ColumnGroups
   */
  public Vector getColumnGroups(TableColumn c, Vector g) {
    g.addElement(this);
    if (childList.contains(c)) return g;
    Enumeration enume = childList.elements();
    while (enume.hasMoreElements()) {
      Object obj = enume.nextElement();
      if (obj instanceof ColumnGroup) {
        Vector groups =
            (Vector) ( (ColumnGroup) obj).getColumnGroups(c,
            (Vector) g.clone());
        if (groups != null) {
          return groups;
        }
      }
    }
    return null;
  }
  /**
   *
   * @return TableCellRenderer
   */
  public TableCellRenderer getHeaderRenderer() {
    return renderer;
  }
  /**
   *
   * @param renderer TableCellRenderer
   */
  public void setHeaderRenderer(TableCellRenderer renderer) {
    if (renderer != null) {
      this.renderer = renderer;
    }
  }
  /**
   *
   * @return Object
   */
  public Object getHeaderValue() {
    return text;
  }
  public String getHeadText() {
    return text;
  }
  /**
   *
   * @param headText String
   */
  public void setHeadText(String headText) {
    this.text = headText;
    if ( headText != null && headText.length() > 0 ) {
      this.setHeaderValue(headText);
    }
  }


  /**
   *
   * @param table JTable
   * @return Dimension
   */
  public Dimension getSize(JTable table) {
    Component comp = renderer.getTableCellRendererComponent(table, getHeaderValue(), false, false, -1, -1);
    int height = comp.getPreferredSize().height;
    int width = 0;
    Enumeration enume = childList.elements();
    while (enume.hasMoreElements()) {
      Object obj = enume.nextElement();
      if (obj instanceof ColumnGroup) {
        width += ( (ColumnGroup) obj).getSize(table).width;
      }
      else {
        TableColumn aColumn = (TableColumn) obj;
        width += aColumn.getWidth();
        width += margin;
      }
    }
    return new Dimension(width, height);
  }
  /**
   *
   * @param margin int
   */
  public void setColumnMargin(int margin) {
    this.margin = margin;
    Enumeration enume = childList.elements();
    while (enume.hasMoreElements()) {
      Object obj = enume.nextElement();
      if (obj instanceof ColumnGroup) {
        ( (ColumnGroup) obj).setColumnMargin(margin);
      }
    }
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
  public String toString() {
    return dataCompName!=null?dataCompName:super.toString();
  }
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
  protected java.util.Vector childList = new Vector();
  /**
   *
   * @return List
   */
  public java.util.List getChildList() {
    return childList;
  }
  /**
   *
   * @param childList List
   */
  public void setChildList(java.util.List childList) {
    if ( childList == null ) {
      return;
    }
    childList.clear();
    childList.addAll(childList);
  }
  /**
   *
   * @param ID String
   * @return DataComponent
   */
  public DataComponent getDataComponent(String ID) {
    if ( childList == null ) return null;
    for(int i=0;i<childList.size();i++) {
      if ( ID.equals(((DataComponent)childList.get(i)).getID()) ) {
        return (DataComponent)childList.get(i);
      }
    }
    return null;
  }
  /**
   *
   * @param index int
   * @return DataComponent
   */
  public DataComponent getDataComponent(int index) {
    if ( childList == null ) return null;
    if ( index < 0 || index >= childList.size() ) return null;
    return (DataComponent)childList.get(index);
  }
  public boolean existsColumn(Column column) {
    if ( childList == null || childList.size() == 0 ) return false;
    DataComponent dataComp = null;
    for(int i=0;i<childList.size();i++) {
      dataComp = (DataComponent)childList.get(i);
      if ( column.equals(dataComp) ) return true;
      if ( dataComp instanceof ColumnGroup ) {
        return ((ColumnGroup)dataComp).existsColumn(column);
      }
    }
    return false;
  }
  /**
   *
   * @param dataComponent DataComponent
   */
  public int insertDataComponent(DataComponent dataComponent) {
    if ( dataComponent == null ) return -1;
    if ( dataComponent instanceof ColumnGroup ) {
      return addGroup((ColumnGroup)dataComponent);
    }
    if ( dataComponent instanceof Column ) {
      return addColumn((Column)dataComponent);
    }
    return -1;
  }
  public boolean canAddChild(DataComponent childDC) {
    if ( childDC instanceof ColumnGroup ||
         childDC instanceof Column ) return true;
    return false;
  }
  protected int addColumn(Column column) {
    ColumnHeaderGroupModel chgm = getGroupModel();
    // 如果不是在分组模型中，则返回
    if ( chgm == null ) return -1;
    ColumnModel cm = chgm.getColumnModel();
    // 如果分组模型没有设置列模型，则返回
    if ( cm == null ) return -1;
    java.util.List cList = null;//cm.getChildList();
    // 如果当前列不在列模型中，则返回
    if ( cList == null || cList.indexOf(column) == -1 ) return -1;
    if (childList == null) childList = new java.util.Vector();
    childList.add(column); // 在此不用设置父类
    fireColumnMoved();
    return childList.size()-1;
  }
  protected int addGroup(ColumnGroup columnGroup) {
    if (childList == null) childList = new java.util.Vector();
    childList.add(columnGroup);
    columnGroup.setParentDataComponent(this);
    fireColumnMoved();
    return childList.size()-1;
  }
  /**
   *
   */
  protected void fireColumnMoved() {
    ColumnHeaderGroupModel chgm = getGroupModel();
    if ( chgm != null ) chgm.fireColumnMoved();
  }
  /**
   *
   * @return ColumnHeaderGroupModel
   */
  protected ColumnHeaderGroupModel getGroupModel() {
    DataComponent pdc = this.getParentDataComponent();
    if ( pdc instanceof ColumnHeaderGroupModel )
      return (ColumnHeaderGroupModel)pdc;
    else if ( pdc instanceof ColumnGroup ) {
      return ((ColumnGroup)pdc).getGroupModel();
    } else return null;
  }
  /**
   *
   * @param dataComponent DataComponent
   */
  public void removeDataComponent(DataComponent dataComponent) {
    if ( dataComponent != null ) {
      this.childList.remove(dataComponent);
      dataComponent.setParentDataComponent(null);
      fireColumnMoved();
    }
  }
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
}
