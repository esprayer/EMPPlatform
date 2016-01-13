package com.efounder.bz.dbform.component;

import javax.swing.*;

import com.efounder.eai.ide.*;
import com.efounder.pfc.swing.JIConListCellRenderer;
import com.efounder.service.script.*;
import com.efounder.builder.base.data.*;
import com.core.xml.*;
import com.efounder.bz.dbform.datamodel.DMColComponent;
import com.efounder.bz.dbform.datamodel.DMComponent;
import com.efounder.bz.dbform.datamodel.DataSetComponent;
import com.efounder.bz.dbform.datamodel.DataSetComponentEvent;
import com.efounder.builder.meta.domodel.*;
import java.awt.event.*;
import com.efounder.bz.dbform.datamodel.proxy.DMColComponentProxy;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FormComboBox extends JComboBox implements
    Scriptable,FormComponent , DMComponent,DataSetListener,DMColComponent,ItemListener {
  protected JIConListCellRenderer cellRenderer = null;
  /**
   *
   */
  DMColComponentProxy dmColProxy=null;
  public FormComboBox() {
    cellRenderer = new JIConListCellRenderer(ExplorerIcons.ICON_RUN);
    this.setRenderer(cellRenderer);
    this.addItemListener(this);
    dmColProxy=new DMColComponentProxy(this);
  }
  /**
   *
   */
  protected String listIcon = null;
  private String initValue;
  /**
   *
   * @return String
   */
  public String getListIcon() {
    return listIcon;
  }

  public String getInitValue() {
    return initValue;
  }

  /**
   *
   * @param listIcon String
   */
  public void setListIcon(String listIcon) {
    this.listIcon = listIcon;
    if(listIcon!=null){
      if (listIcon.indexOf(";") == -1) {
        Icon icon = ExplorerIcons.getExplorerIcon(listIcon);
        this.cellRenderer.setIcon(icon);
      }
      else {
        String[] icons = listIcon.split(";");
        for (int i = 0; i < icons.length && i < getItemCount(); i++) {
          StubObject so = (StubObject) getItemAt(i);
          so.setIcon(com.efounder.eai.ide.ExplorerIcons.getExplorerIcon(icons[i]));
        }
      }
    }
  }
  public void setInitValue(String initValue) {
    this.initValue = initValue;
    this.removeAllItems();
    if(initValue==null||initValue.trim().length()==0)return;
    String[]icons=null;
    if(listIcon!=null)icons=listIcon.split(";");
    String[]strs=initValue.split(";");
    for(int i=0;i<strs.length;i++){
      String item=strs[i];
      StubObject so=new StubObject();
      int pos=item.indexOf(":");
      if(pos==-1){
        so.setID(item);
        so.setCaption(item);
      }
      else{
        so.setID(item.substring(0,pos));
        so.setCaption(item.substring(pos+1));
      }
      if(icons!=null&&icons.length>i){
        so.setIcon(com.efounder.eai.ide.ExplorerIcons.getExplorerIcon(icons[i]));
      }
      addItem(so);
    }
  }
  /**
   *
   * @return FormContainer
   */
  public FormContainer getFormContainer() {
    return null;
  }
  /**
   *
   * @return JComponent
   */
  public JComponent getJComponent() {
    return this;
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
    /**
     *
     * @return DataSetComponent
     */
    public DataSetComponent getDataSetComponent() {
      return dmColProxy.getDataSetComponent();
    }
    /**
     *
     * @param dsc DataSetComponent
     */
    public void setDataSetComponent(DataSetComponent dsc) {
      dmColProxy.setDataSetComponent(dsc);
        initBoxFormMetaData();
  }
  protected void initBoxFormMetaData(){
    if(dmColProxy.getDataSetComponent()==null)return;
    if(dmColProxy.getDataSetColID()==null)return;
    if(dmColProxy.getDataSetID()==null)return;
    DOMetaData[] doms=dmColProxy.getDataSetComponent().getDOMetaData(dmColProxy.getDataSetID());
    if(doms==null||doms.length==0)return;
    EFRowSet  colDefSet=(EFRowSet) doms[0].getColumnDefineRow(dmColProxy.getDataSetColID());
    String view = colDefSet.getString("COL_EDITVIEW", null);
    if (view == null)
      view = colDefSet.getString("COL_VIEW", null);
    removeAllItems();
    String[] item = view.split(";");
    for (int i = 0; i < item.length; i++) {
      if (item[i] == null || item[i].indexOf(":") < 0)
        continue;
      String s1 = item[i].substring(0, item[i].indexOf(":"));
      String s2 = item[i].substring(item[i].indexOf(":") + 1);
      StubObject so=new StubObject();
      so.setID(s1);
      so.setCaption(s2);
      addItem(so);
    }
    setListIcon(listIcon);
  }
    /**
     *
     * @param dataSetID String
     */
    public void setDataSetID(String dataSetID) {
      dmColProxy.setDataSetID(dataSetID);
      initBoxFormMetaData();
    }
    /**
     *
     * @return String
     */
    public String getDataSetID() {
      return dmColProxy.getDataSetID();
    }
    public EFDataSet getDataSet() {
      return dmColProxy.getDataSet();
    }

    /**
     *
     * @param dataSet EFDataSet
     */
    public void setDataSet(EFDataSet ds) {
      dmColProxy.setDataSet(ds);
    }
    public void dataSetChanged(DataSetEvent e) {
      if ( e.getEventType() == DataSetEvent.CURSOR
           || (e.getEventType() == DataSetEvent.UPDATE&&e.getUpdateFieldName().equals(dmColProxy.getWholeColID()))) {
        EFDataSet dataSet = e.getDataSet();
        if ( dataSet == null ) return;
        EFRowSet rowSet = dataSet.getRowSet();
        int index=findItem(rowSet);
        this.removeItemListener(this);
        this.setSelectedIndex(index);
        this.addItemListener(this);
        dmColProxy.setMainRowSet(rowSet);
      }
  }
  protected int  findItem(EFRowSet ers){
    String col=dmColProxy.getWholeColID();
    if(col==null)return -1;
    String value=ers.getString(col,"");
    int count=this.getItemCount();
    for(int i=0;i<count;i++){
      StubObject so=(StubObject)this.getItemAt(i);
      if(value.equals(so.getID()))return i;
    }
    return -1;
  }
  boolean ordienable=true;
 public void dataSetComponentListener(DataSetComponentEvent
                                      dataSetComponentEvent) {
   if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_START_EDIT ) {
     this.setEnabled1(ordienable);return;
   }
   if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_STOP_EDIT ) {
     this.setEnabled1(false);return;
   }
 }
 public void setEnabled1(boolean b) {
     super.setEnabled(b);
   }

   public void setEnabled(boolean b) {
     super.setEnabled(b);
     ordienable=b;

  }

  public ESPRowSet getMainRowSet() {
    return dmColProxy.getMainRowSet();
  }
  public String getDataSetColID() {
    return dmColProxy.getDataSetColID();
  }

  public void setDataSetColID(String dataSetColID) {
     dmColProxy.setDataSetColID(dataSetColID);
     initBoxFormMetaData();
  }

 /**
  *
  * @return String
  */
 public String getInternalDataSetID() {
   return dmColProxy.getInternalDataSetID();
 }
 /**
  *
  * @param dataSetID String
  */
 public void setInternalDataSetID(String dataSetID) {
   dmColProxy.setInternalDataSetID(dataSetID);
 }
 /**
  *
 /**
  *
  * @return boolean
  */
 public boolean getMetaData() {
   return dmColProxy.getMetaData();
 }
 /**
  *
  * @param v boolean
  */
 public void setMetaData(boolean v) {
   dmColProxy.setMetaData(v);
 }
 /**
  *
  * @param viewDataSetID String
  */
 public void setViewDataSetID(String viewDataSetID) {
   dmColProxy.setViewDataSetID(viewDataSetID);
 }
 /**
  *
  * @return String
  */
 public String getViewDataSetID() {
   return dmColProxy.getViewDataSetID();
 }
 /**
  *
  * @param viewDataSetColID String
  */
 public void setViewDataSetColID(String viewDataSetColID) {
   dmColProxy.setViewDataSetColID(viewDataSetColID);
 }
 /**
  *
  * @return String
  */
 public String getViewDataSetColID() {
   return dmColProxy.getViewDataSetColID();
 }

  public String getFkeyID() {
    return dmColProxy.getFkeyID();
  }

  public void setFkeyID(String fkey) {
    dmColProxy.setFkeyID(fkey);
  }

  public String getRlglID() {
    return dmColProxy.getRlglID();
  }

  public void setRlglID(String rlglID) {
    dmColProxy.setRlglID(rlglID);
  }

  public int getHorizontalAlignment() {
    return dmColProxy.getHorizontalAlignment();
  }

  public void setHorizontalAlignment(int horizontalAlignment) {
    dmColProxy.setHorizontalAlignment(horizontalAlignment);
  }

  public String getNumberFormat() {
    return dmColProxy.getNumberFormat();
  }

  public void setNumberFormat(String numberFormat) {
    dmColProxy.setNumberFormat(numberFormat);
  }

  public String getDateFormat() {
    return dmColProxy.getDateFormat();
  }

  public void setDateFormat(String dateFormat) {
    dmColProxy.setDateFormat(dateFormat);
  }

  public String getFormulaOne() {
    return dmColProxy.getFormulaOne();
  }

  public void setFormulaOne(String formulaOne) {
    dmColProxy.setFormulaOne(formulaOne);
  }
  public boolean isUserInternalDataSetID() {
    return dmColProxy.isUserInternalDataSetID();
  }

  public void setUserInternalDataSetID(boolean v) {
    dmColProxy.setUserInternalDataSetID(v);
  }

  // 取值字段
  public void setValueDataSetColID(String valueDataSetColID) {
    dmColProxy.setValueDataSetColID(valueDataSetColID);
  }
  //
  public String getValueDataSetColID() {
    return dmColProxy.getValueDataSetColID();
  }

  public void itemStateChanged(ItemEvent e) {
    if(e.getStateChange()!=ItemEvent.SELECTED)return;
    if(dmColProxy.getMainRowSet()==null)return;
    String  col = dmColProxy.getWholeColID();
    StubObject so=(StubObject) e.getItem();
    dmColProxy.getMainRowSet().putString(col,(String)so.getID());
  }
  
  //编辑掩码
  String editMask;
  public String getEditMask() {
	  // TODO Auto-generated method stub
	  return editMask;
  }
  public void setEditMask(String editMask) {
	  // TODO Auto-generated method stub
	  this.editMask = editMask;
  }
}
