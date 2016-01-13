package com.efounder.bz.dbform.component;

import javax.swing.*;

import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.proxy.DMColComponentProxy;
import com.efounder.service.script.*;
import java.awt.event.*;

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
public class FormCheckBox extends JCheckBox implements FormComponent
    ,DMComponent,DataSetListener,DMColComponent,Scriptable,ActionListener{
  /**
   *
   */
  DMColComponentProxy dmColProxy=new DMColComponentProxy(this);
  public FormCheckBox() {
    this.setText("Check");
    this.addActionListener(this);
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


  public DataSetComponent getDataSetComponent() {
       return dmColProxy.getDataSetComponent();
     }
     /**
      *
      * @param dsc DataSetComponent
      */
     public void setDataSetComponent(DataSetComponent dsc) {
       dmColProxy.setDataSetComponent(dsc);
   }

     /**
      *
      * @param dataSetID String
      */
     public void setDataSetID(String dataSetID) {
       dmColProxy.setDataSetID(dataSetID);
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
         String col=dmColProxy.getWholeColID();
         if(col==null)return ;
         String value=rowSet.getString(col,"");
         this.removeActionListener(this);
         setSelected(false);
         if(value.equals(selValue))
           setSelected(true);
         if(value.equals(deSelValue))
           setSelected(false);
         this.addActionListener(this);
         dmColProxy.setMainRowSet(rowSet);
       }
   }

   boolean ordienable=true;
  private String selValue="1";
  private String deSelValue="0";
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
     return ha;
   }
   int ha=0;
   public void setHorizontalAlignment(int horizontalAlignment) {
     ha=horizontalAlignment;
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

  public ScriptManager getScriptManager() {
	    return null;
  }

  public void setSelValue(String selValue) {
    this.selValue = selValue;
  }

  public void setDeSelValue(String deSelValue) {
    this.deSelValue = deSelValue;
  }

  public String getSelValue() {
    return selValue;
  }

  public String getDeSelValue() {
    return deSelValue;
  }

  public void actionPerformed(ActionEvent e) {
    String  col = dmColProxy.getWholeColID();
    if(col==null)return;
    String v;
    if(isSelected())
      v=selValue;
    else
      v=deSelValue;
    dmColProxy.getMainRowSet().putString(col,v);
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
