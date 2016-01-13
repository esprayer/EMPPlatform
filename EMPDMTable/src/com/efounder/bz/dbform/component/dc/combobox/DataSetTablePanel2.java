package com.efounder.bz.dbform.component.dc.combobox;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.tree.TreeCellRenderer;

import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.component.dc.table.*;
import com.efounder.bz.dbform.component.dc.tree.DMTree;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.comp.*;
import com.efounder.eai.ide.*;
import com.efounder.bz.dbform.component.dc.DCTDataSetContainer;
import com.efounder.bz.dbform.component.RowSetValueUtils;
import com.efounder.bz.dbform.container.EFScrollPane;
import com.efounder.builder.meta.dctmodel.DCTMetaData;

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
public class DataSetTablePanel2 extends JPanel implements PopupComponent,MouseListener {
  /**
   *
   * @param tableModel DCTableModel
   * @return DataSetTablePanel
   */
  public static DataSetTablePanel2 getInstance(JComponent dmTable,DMColComponent dmColComponent,DCTMetaData dctMetaData) {
    DataSetTablePanel2 dstp = new DataSetTablePanel2(dmTable);
    dstp.processEvent(dmTable);
    dstp.dmColComponent = dmColComponent;dstp.dctMetaData = dctMetaData;
    dstp.pnTop.setVisible(false);dstp.pnBottom.setVisible(false);    
    return dstp;
  }
  protected DMColComponent dmColComponent = null;
  protected DCTMetaData dctMetaData = null;
  /**
   *
   */
  public void reBuilder() {
//    this.spContent.doLayout();spContent.validate();spContent.repaint();
//    spContent.getViewport().remove(dmTable);
//    spContent.getViewport().add(dmTable);
  }
  /**
   *
   * @param dmTable DMTable
   */
  protected void processEvent(JComponent dmTable) {
    if ( dmTable != null )
      dmTable.addMouseListener(this);
  }
  /**
   *
   */
  protected DataSetTablePanel2(JComponent dmTable) {
    try {
      jbInit();
      this.dmTable = dmTable;
      if ( dmTable != null ) {
        spContent.getViewport().add(dmTable);
//      dmTable.setEnabled(false);
        dmTable.setVisible(true);
        dmTable.validate();
        dmTable.repaint();
      }
      this.repaint();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  protected JComponent dmTable = null;
  /**
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    pnTop.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    pnBottom.setLayout(flowLayout2);
    flowLayout2.setAlignment(FlowLayout.RIGHT);
    flowLayout2.setHgap(0);
    flowLayout2.setVgap(0);
    this.add(pnTop, java.awt.BorderLayout.NORTH);
    this.add(pnBottom, java.awt.BorderLayout.SOUTH);
    this.add(spContent, java.awt.BorderLayout.CENTER);
    spContent.setBorder(null);
  }
  /**
   *
   */
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnTop = new JPanel();
  JPanel pnBottom = new JPanel();
  EFScrollPane spContent = new EFScrollPane(); /**
   *
   * @return JComponent
   */
  public JComponent getJComponent() {
    return this;
  }
  /**
   *
   * @param item Object
   */
  public void setSelectedItem(Object item) {
//    if ( !(item instanceof ESPRowSet) ) return;
//    DCTableModel tableModel = this.dmTable.getDCTableModel();
//    if ( tableModel == null ) return;
//    EFDataSet dataSet = tableModel.getDataSet();
//    if ( dataSet != null ) dataSet.goToRow((ESPRowSet)item);
  }
  /**
   *
   * @return Object
   */
  public Object getSelectedItem() {
    if ( !(dmTable instanceof DCTDataSetContainer) ) return null;
    DCTDataSetContainer dataSetContainer = (DCTDataSetContainer)dmTable;
    return dataSetContainer.getSelectRowSet();
//    DCTableModel tableModel = this.dmTable.getDCTableModel();
//    if ( tableModel == null ) return null;
//    EFDataSet dataSet = tableModel.getDataSet();
//    if ( dataSet == null ) return null;
//    ESPRowSet rowSet = dataSet.getRowSet();
//    return rowSet;
//    return null;
  }
  protected ESPRowSet getSelectRowSet() {
    if ( !(dmTable instanceof DCTDataSetContainer) ) return null;
    DCTDataSetContainer dataSetContainer = (DCTDataSetContainer)dmTable;
    return dataSetContainer.getSelectRowSet();
  }
  /**
   *
   * @return ListCellRenderer
   */
  public ListCellRenderer getListCellRenderer() {
    RowSetListCellRenderer renderer = new RowSetListCellRenderer(ExplorerIcons.ICON_RUN,(DMComponent)comboBox,(DMColComponent)comboBox);
    return renderer;
  }
  /**
   *
   */
  protected JComboBox comboBox = null;
  /**
   *
   * @param box JComboBox
   */
  public void setJComboBox(JComboBox box) {
    comboBox = box;
  }
  /**
   *
   */
  protected ComboPopup comboPopup = null;
  /**
   *
   */
  FlowLayout flowLayout1 = new FlowLayout();
  /**
   *
   */
  FlowLayout flowLayout2 = new FlowLayout();
  /**
   *
   * @param comboPopup ComboPopup
   */
  public void setComboPopup(ComboPopup comboPopup) {
    this.comboPopup = comboPopup;
    if ( dmTable != null )
      dmTable.requestFocus();
  }
  /**
   *
   * @return boolean
   */
  public boolean isNotUseScrollPane() {
    return true;
  }

  public void mouseClicked(MouseEvent e) {
    if ( e.getClickCount()  == 2 && e.getSource().equals(this.dmTable) ) {
      ESPRowSet rowSet = getSelectRowSet();
      // 处理值
      if ( rowSet != null ) {
        String dataSetID  = dmColComponent.getViewDataSetID();
        String valueColID = dmColComponent.getValueDataSetColID();
        if ( valueColID == null || valueColID.trim().length() == 0 ) valueColID = dctMetaData.getDCT_BMCOLID();
        Object value = RowSetValueUtils.getObject(rowSet,dataSetID,valueColID);
        ((DataSetFieldComboBox)comboBox).setDataSetValue(rowSet,value);
      }
      this.comboPopup.hide();
    }
  }

  public void mousePressed(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }
}
