package com.efounder.bz.dbform.component.dc.combobox;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;

import com.efounder.action.ActionButton;
import com.efounder.action.ActiveObjectAction;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.component.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.comp.*;
import com.efounder.eai.ide.*;
import com.core.xml.JBOFClass;
import com.core.xml.StubObject;

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
public class JDataSetComboBox extends JComponentComboBox implements FormComponent, DMComponent,DataSetListener,DMColComponent,MouseListener{
	/**
   *
   */
	public JDataSetComboBox() {
		// this.addFocusListener(this);
	}

	/**
   *
   */
	protected PopupComponent popupPanel = null;

	/**
   *
   */
	protected PopupComponent popupComponent = null;

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
	protected String dataSetID = null;

	/**
	 * 
	 * @return String
	 */
	public String getDataSetID() {
		return dataSetID;
	}

	/**
   *
   */
	protected EFDataSet dataSet = null;

	/**
	 * 
	 * @return EFDataSet
	 */
	public EFDataSet getDataSet() {
		return dataSet;
	}

	/**
	 * 
	 * @param dataSet
	 *            EFDataSet
	 */
	public void setDataSet(EFDataSet ds) {
		if (dataSet != ds) {
			// 清除掉事件监听器
			if ( dataSet != null ) dataSet.removeDataSetListener(this);
			dataSet = ds;
			// 增加事件监听器
			if ( dataSet != null ) dataSet.addDataSetListener(this);
			// 数据发生改变
//			this.setDataVector(null,null);
//			this.fireTableDataSetChanged();
		}
	}

	/**
	 * 
	 * @param e
	 *            DataSetEvent
	 */
	public void dataSetChanged(DataSetEvent e) {
		if ( e.getEventType() == DataSetEvent.CURSOR ) {
			EFDataSet dataSet = e.getDataSet();
			if ( dataSet == null ) return;
			EFRowSet rowSet = dataSet.getRowSet();
			this.setSelectedItem(rowSet);
			mainRowSet = rowSet;
		}
	}

	/**
   *
   */
	protected JTable popupTable = null;

	/**
	 * 
	 * @return JTable
	 */
	public JTable getPopupTable() {
		return popupTable;
	}

	/**
	 * 
	 * @param popupTable
	 *            JTable
	 */
	public void setPopupTable(JTable popupTable) {
		this.popupTable = popupTable;
		// if ( !DataComponentUtil.isDesigner(this) ) {
		// initPopupComponent();
		// }
	}

	protected String dataSetColID = null;

	/**
	 * 
	 * @return String
	 */
	public String getDataSetColID() {
		return dataSetColID;
	}

	/**
	 * 
	 * @param dataSetColID
	 *            String
	 */
	public void setDataSetColID(String dataSetColID) {
		this.dataSetColID = dataSetColID;
	}

	protected String internalDataSetID = null;

	/**
	 * 
	 * @return String
	 */
	public String getInternalDataSetID() {
		return internalDataSetID;
	}

	/**
	 * 
	 * @param dataSetID
	 *            String
	 */
	public void setInternalDataSetID(String dataSetID) {
		internalDataSetID = dataSetID;
	}

	/**
   *
   */
	protected boolean isMetaData = false;

	/**
	 * 
	 * @return boolean
	 */
	public boolean getMetaData() {
		return isMetaData;
	}

	/**
	 * 
	 * @param v
	 *            boolean
	 */
	public void setMetaData(boolean v) {
		isMetaData = v;
	}

	protected String viewDataSetID = null;

	/**
	 * 
	 * @param viewDataSetID
	 *            String
	 */
	public void setViewDataSetID(String viewDataSetID) {
		this.viewDataSetID = viewDataSetID;
	}

	/**
	 * 
	 * @return String
	 */
	public String getViewDataSetID() {
		return viewDataSetID;
	}

	protected String viewDataSetColID = null;

	/**
	 * 
	 * @param viewDataSetColID
	 *            String
	 */
	public void setViewDataSetColID(String viewDataSetColID) {
		this.viewDataSetColID = viewDataSetColID;
	}

	/**
	 * 
	 * @return String
	 */
	public String getViewDataSetColID() {
		return viewDataSetColID;
	}

	public void helpDICT(Object actionObject, Object nodeArray, Action action, ActionEvent actionevent) {

	}

	/**
   *
   */
	protected boolean hasHelpButton = true;

	/**
	 * 
	 * @param helpButton
	 *            boolean
	 */
	public void setHasHelpButton(boolean hasHelpButton) {
//		this.hasHelpButton = hasHelpButton;
//		if (hasHelpButton) {
//			if (helpButton == null) {
//				Action ac = new ActiveObjectAction(this, this, "helpDICT", "",
//						'0', "",
//						ExplorerIcons.getExplorerIcon("oicons/help.png"));
//				helpButton = ActionButton.getActionButton(this, ac);
//				helpButton.setEnabled(true);
//				addButton(helpButton);
//				validate();
//				repaint();
//				SwingUtilities.invokeLater(new Runnable() {
//					public void run() {
//
//					}
//				});
//
//			}
//		} else {
//			if (helpButton != null) {
//				SwingUtilities.invokeLater(new Runnable() {
//					public void run() {
//						removeButton(helpButton);
//						validate();
//						repaint();
//					}
//				});
//			}
//		}
//		Action ac = new ActiveObjectAction(this, this, "helpDICT", "", '0', "", ExplorerIcons.getExplorerIcon("oicons/help.png"));
//		helpButton = ActionButton.getActionButton(this, ac);
		this.helpButton = new JButton();
		helpButton.setIcon(ExplorerIcons.getExplorerIcon("oicons/help.png"));
		helpButton.addActionListener(this);
		SwingUtilities.invokeLater(new Runnable() {
	          public void run() {
	        	  addButton(helpButton);
	        	  validate();
	        	  repaint();
	          }
	        });
		this.doLayout();
	}

	/**
	 *
	 * @param browser EnterpriseExplorer
	 */
	public void actionPerformed(ActionEvent e) {
		helpDICT(this, null, null, null);
	}
	  
	protected JButton helpButton = null;

	/**
	 * 
	 * @return boolean
	 */
	public boolean getHasHelpButton() {
		return hasHelpButton;
	}

	protected boolean userInternalDataSetID = false;

	public boolean isUserInternalDataSetID() {
		return userInternalDataSetID;
	}

	public void setUserInternalDataSetID(boolean v) {
		userInternalDataSetID = v;
	}

	protected String fkeyID = null;

	//
	public String getFkeyID() {
		return fkeyID;
	}

	//
	public void setFkeyID(String fkey) {
		this.fkeyID = fkey;
	}

	protected String rlglID = null;

	//
	public String getRlglID() {
		return rlglID;
	}

	//
	public void setRlglID(String rlglID) {
		this.rlglID = rlglID;
	}

	protected String valueDataSetColID = null;

	// 取值字段
	public void setValueDataSetColID(String valueDataSetColID) {
		this.valueDataSetColID = valueDataSetColID;
	}

	//
	public String getValueDataSetColID() {
		return valueDataSetColID;
	}

	/**
	 * 
	 * @param dataSetComponentEvent
	 *            DataSetComponentEvent
	 */
	// boolean ordienable=true;
	// public void dataSetComponentListener(DataSetComponentEvent
	// dataSetComponentEvent) {
	// if ( dataSetComponentEvent.getEventType() ==
	// DataSetComponentEvent.DSC_EVENT_START_EDIT ) {
	// this.setEnabled1(ordienable);return;
	// }
	// if ( dataSetComponentEvent.getEventType() ==
	// DataSetComponentEvent.DSC_EVENT_STOP_EDIT ) {
	// this.setEnabled1(false);return;
	// }
	// }
	/**
	 * 
	 * @param b
	 *            boolean
	 */
	public void setEnabled1(boolean b) {
		super.setEnabled(b);
		java.util.List list = this.getButtonArray();
		if (list == null || list.size() == 0)
			return;
		for (int i = 0; i < list.size(); i++) {
			((JComponent) list.get(i)).setEnabled(b);
		}
	}

	public void setEnabled(boolean b) {
		super.setEnabled(b);
		// ordienable=b;
		java.util.List list = this.getButtonArray();
		if (list == null || list.size() == 0)
			return;
		for (int i = 0; i < list.size(); i++) {
			((JComponent) list.get(i)).setEnabled(b);
		}
	}

	/**
   *
   */
	protected ESPRowSet mainRowSet = null;

	public ESPRowSet getMainRowSet() {
		return mainRowSet;
	}

	/**
	 * 
	 * @return String
	 */
	public Object getValueByDataSetColID(Object defValue) {
		if (mainRowSet == null)
			return null;
		String dataSetColID = this.getDataSetColID();
		return mainRowSet.getObject(dataSetColID, defValue);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {
			helpDICT(this, null, null, null);
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
	 * @param horizontalAlignment
	 *            int
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
	 * @param numberFormat
	 *            String
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
	 * @param dateFormat
	 *            String
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
	 * @param formulaOne
	 *            String
	 */
	public void setFormulaOne(String formulaOne) {
		this.formulaOne = formulaOne;
	}

	// 编辑掩码
	String editMask;

	public String getEditMask() {
		// TODO Auto-generated method stub
		return editMask;
	}

	public void setEditMask(String editMask) {
		// TODO Auto-generated method stub
		this.editMask = editMask;
	}

	@Override
	public void setDataSetID(String dataSetID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FormContainer getFormContainer() {
		// TODO Auto-generated method stub
		return null;
	}
}
