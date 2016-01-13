package com.efounder.bz.dbform.component.dc;

import javax.swing.*;
import com.efounder.bz.dbform.datamodel.DMColComponent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import com.efounder.bz.dbform.component.FormContainer;
import java.awt.event.FocusEvent;
import java.util.EventObject;
import com.efounder.pub.util.RegexUtil;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.ESPRowSet;
import javax.swing.event.CellEditorListener;
import com.efounder.bz.dbform.datamodel.DataSetComponent;
import java.awt.Color;
import com.efounder.bz.dbform.datamodel.DMComponent;
import com.efounder.bz.dbform.component.FormComponent;
import com.efounder.bz.dbform.datamodel.DataSetComponentEvent;
import com.efounder.builder.base.data.DataSetListener;
import java.awt.event.KeyAdapter;
import com.efounder.builder.base.data.DataSetEvent;
import java.awt.event.FocusListener;

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
public class FormEditorPaneField
    extends JEditorPane implements DataSetListener,FormComponent,DMComponent,DMColComponent,CellEditor {
  /**
   *
   */
  protected JTextComponentColumn textComponentColumn = null;
  /**
   * 默认构造函数
   */
  public FormEditorPaneField() {
    textComponentColumn = JTextComponentColumn.getJTextComponentColumn(this);
    this.addFocusListener(textComponentColumn);
    JTextComponentMouseEvent me = JTextComponentMouseEvent.getJTextComponentMouseEvent(textComponentColumn);
    this.addMouseListener(me);
  }
  /**
   *
   * @return DataSetComponent
   */
  public DataSetComponent getDataSetComponent() {
    return textComponentColumn.getDataSetComponent();
  }
  /**
   *
   * @param dsc DataSetComponent
   */
  public void setDataSetComponent(DataSetComponent dsc) {
    textComponentColumn.setDataSetComponent(dsc);
  }
  /**
   *
   */
  protected String dataSetID = null;
  /**
   *
   * @param dataSetID String
   */
  public void setDataSetID(String dataSetID) {
    textComponentColumn.setDataSetID(dataSetID);
  }
  /**
   *
   * @return String
   */
  public String getDataSetID() {
    return textComponentColumn.getDataSetID();
  }
  /**
   *
   * @return String
   */
  public String getDataSetColID() {
    return textComponentColumn.getDataSetColID();
  }
  /**
   *
   * @param dataSetColID String
   */
  public void setDataSetColID(String dataSetColID) {
    textComponentColumn.setDataSetColID(dataSetColID);
  }
  /**
   *
   * @return String
   */
  public String getInternalDataSetID() {
    return textComponentColumn.getInternalDataSetID();
  }
  /**
   *
   * @param dataSetID String
   */
  public void setInternalDataSetID(String dataSetID) {
    textComponentColumn.setInternalDataSetID(dataSetID);
  }
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getDataSet() {
    return textComponentColumn.getDataSet();
  }
  /**
   *
   * @param dataSet EFDataSet
   */
  public void setDataSet(EFDataSet ds) {
    textComponentColumn.setDataSet(ds);
  }
  /**
   *
   * @return boolean
   */
  public boolean getMetaData() {
    return textComponentColumn.getMetaData();
  }
  /**
   *
   * @param v boolean
   */
  public void setMetaData(boolean v) {
    textComponentColumn.setMetaData(v);
  }
  /**
   *
   * @param viewDataSetID String
   */
  public void   setViewDataSetID(String viewDataSetID) {
    textComponentColumn.setViewDataSetID(viewDataSetID);
  }
  /**
   *
   * @return String
   */
  public String getViewDataSetID() {
    return textComponentColumn.getViewDataSetID();
  }
  /**
   *
   * @param viewDataSetColID String
   */
  public void   setViewDataSetColID(String viewDataSetColID) {
    this.textComponentColumn.setViewDataSetColID(viewDataSetColID);
  }
  /**
   *
   * @return String
   */
  public String getViewDataSetColID() {
    return textComponentColumn.getViewDataSetColID();
  }
  /**
   *
   * @param e DataSetEvent
   */
  public void dataSetChanged(DataSetEvent e) {
    textComponentColumn.dataSetChanged(e);
  }
  /**
   *
   * @param valueDataSetColID String
   */
  public void setValueDataSetColID(String valueDataSetColID) {
    textComponentColumn.setValueDataSetColID(valueDataSetColID);
  }
  /**
   *
   * @return String
   */
  public String getValueDataSetColID() {
    return textComponentColumn.getValueDataSetColID();
  }
  /**
   *
   * @param dataSetComponentEvent DataSetComponentEvent
   */
  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
    textComponentColumn.dataSetComponentListener(dataSetComponentEvent);
  }
  /**
   *
   * @return ESPRowSet
   */
  public ESPRowSet getMainRowSet() {
    return textComponentColumn.getMainRowSet();
  }
  /**
   *
   * @return boolean
   */
  public boolean isUserInternalDataSetID() {
    return textComponentColumn.isUserInternalDataSetID();
  }
  /**
   *
   * @param v boolean
   */
  public void setUserInternalDataSetID(boolean v) {
    textComponentColumn.setUserInternalDataSetID(v);
  }
  /**
   *
   * @return String
   */
  public String getFkeyID() {
    return textComponentColumn.getFkeyID();
  }
  /**
   *
   * @param fkey String
   */
  public void setFkeyID(String fkey) {
    textComponentColumn.setFkeyID(fkey);
  }
  /**
   *
   * @return String
   */
  public String getRlglID() {
    return textComponentColumn.getRlglID();
  }
  /**
   *
   * @param rlglID String
   */
  public void setRlglID(String rlglID) {
    textComponentColumn.setRlglID(rlglID);
  }
  /**
   *
   * @return boolean
   */
  public boolean isEmbedCellEditor() {
    return textComponentColumn.isEmbedCellEditor();
  }
  /**
   *
   * @param v boolean
   */
  public void setEmbedCellEditor(boolean v) {
    textComponentColumn.setEmbedCellEditor(v);
  }





























  /**
   *
   */
  private String editMask;
  /**
   *
   */
  private boolean useRegex;
  /**
   *
   */
  private String regexPattern;
  /**
   *
   * @param        mask String
   */
  public FormEditorPaneField(String mask) throws ParseException {
      this(mask, false, null);
  }
  /**
   *
   * @param         mask String
   * @param regexPattern String
   */
  public FormEditorPaneField(String mask, String regexPattern) throws
      ParseException {
      this(mask, true, regexPattern);
  }
  /**
   *
   * @param         mask String
   * @param     useRegex boolean
   * @param regexPattern String
   */
  public FormEditorPaneField(String mask, boolean useRegex, String regexPattern) throws
      ParseException {
      try {
          this.editMask = mask;
          this.useRegex = useRegex;
          this.regexPattern = regexPattern;
          initRegexListener();
          addFocusListener();
      } catch (Exception ex) {
          ex.printStackTrace();
      }
  }
  /**
   *
   */
  private void initRegexListener() {
      if (!isUseRegex()) return;
      this.addKeyListener(new KeyAdapter() {
          public void keyTyped(KeyEvent e) {
//              setDefaultColor();
              if (e.getKeyChar() == e.VK_ENTER) {
                  checkRegex();
              }
          }
      });
  }
  /**
   *
   */
  public void checkRegex() {
      String text = getText();
      if (RegexUtil.isMatch(getRegexPattern(), text)) return;
      //��ݷǷ���������ʾ��ɫ��ͬʱ��ʾtooltips
      setForeground(Color.RED);
  }

  /**
   *
   * @return boolean
   */
  public boolean isUseRegex() {
      return useRegex;
  }

  /**
   *
   * @param useRegex boolean
   */
  public void setUseRegex(boolean useRegex) {
      this.useRegex = useRegex;
      initRegexListener();
      this.addFocusListener();
  }

  /**
   *
   * @return String
   */
  public String getRegexPattern() {
      return regexPattern;
  }

  /**
   *
   * @param pattern String
   */
  public void setRegexPattern(String pattern) {
      regexPattern = pattern;
  }

  /**
   *
   * @return String
   */
  public String getEditMask() {
      return editMask;
  }

  /**
   *
   * @param pattern String
   */
  public void setEditMask(String mask) {
      this.editMask = mask;
  }
  /**
   *
   */
  private SelfFocusListener selflistener;
  /**
   *
   */
  private void addFocusListener() {
      if (!isUseRegex()) return;
      if (selflistener == null) selflistener = new SelfFocusListener();
      if (!isHaveSelfFocusListener()) addFocusListener(selflistener);
  }

  /**
   *
   * @return boolean
   */
  private boolean isHaveSelfFocusListener(){
      FocusListener[] listeners = getFocusListeners();
      if (listeners != null) {
          for (int i = 0, n = listeners.length; i < n; i++) {
              if (listeners[i] instanceof SelfFocusListener) {
                  return true;
              }
          }
      }
      return false;
  }

  /**
   * ���������
   *
   * @version 1.0
   */
  class SelfFocusListener implements FocusListener {
      public void focusGained(FocusEvent e) {
      }

      public void focusLost(FocusEvent e) {
        checkRegex();
      }
  }





  /**
   *
   * @return Object
   */
  public Object getCellEditorValue() {
    return this.getText();
  }
  /**
   *
   * @param anEvent EventObject
   * @return boolean
   */
  public boolean isCellEditable(EventObject anEvent) {
    return false;
  }
  /**
   *
   * @param anEvent EventObject
   * @return boolean
   */
  public boolean shouldSelectCell(EventObject anEvent) {
    return false;
  }
  /**
   *
   * @return boolean
   */
  public boolean stopCellEditing() {
    return false;
  }
  /**
   *
   */
  public void cancelCellEditing() {
  }
  /**
   *
   * @param l CellEditorListener
   */
  public void addCellEditorListener(CellEditorListener l) {
  }
  /**
   *
   * @param l CellEditorListener
   */
  public void removeCellEditorListener(CellEditorListener l) {
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
   */
  protected int horizontalAlignment = -1;
  /**
   *
   * @return int
   */
  public int getHorizontalAlignment() {
    return horizontalAlignment;
  }

  /**
   *
   * @param horizontalAlignment int
   */
  public void setHorizontalAlignment(int horizontalAlignment) {
    this.horizontalAlignment = horizontalAlignment;
  }

  /**
   *
   */
  protected String numberFormat = null;
  /**
   *
   * @return String
   */
  public String getNumberFormat() {
    return numberFormat;
  }

  /**
   *
   * @param numberFormat String
   */
  public void setNumberFormat(String numberFormat) {
    this.numberFormat = numberFormat;
  }

  /**
   *
   */
  protected String dateFormat = null;
  /**
   *
   * @return String
   */
  public String getDateFormat() {
    return this.dateFormat;
  }

  /**
   *
   * @param dateFormat String
   */
  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  /**
   *
   */
  protected String formulaOne = null;
  /**
   *
   * @return String
   */
  public String getFormulaOne() {
    return this.formulaOne;
  }

  /**
   *
   * @param formulaOne String
   */
  public void setFormulaOne(String formulaOne) {
    this.formulaOne = formulaOne;
  }
}
