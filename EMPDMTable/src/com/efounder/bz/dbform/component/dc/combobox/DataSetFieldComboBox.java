package com.efounder.bz.dbform.component.dc.combobox;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.CellEditor;
import javax.swing.ComboBoxEditor;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;

import org.openide.ErrorManager;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.builder.base.data.DataSetEvent;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.meta.dctmodel.DCTMetaDataManager;
import com.efounder.bz.dbform.bizmodel.MDModel;
import com.efounder.bz.dbform.component.RowSetValueUtils;
import com.efounder.bz.dbform.component.dc.table.DictTable;
import com.efounder.bz.dbform.component.dc.tree.DMTree;
import com.efounder.bz.dbform.datamodel.ColumnModel;
import com.efounder.bz.dbform.datamodel.DCTreeModel;
import com.efounder.bz.dbform.datamodel.RowSetValue;
import com.efounder.eai.data.JParamObject;
import com.help.HelpInfoContext;
import com.help.util.ESPHelpUtil;

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
public class DataSetFieldComboBox extends JDataSetComboBox implements CellEditor, KeyListener, PropertyChangeListener, FocusListener {

	public DataSetFieldComboBox() {
		((JTextField) this.getEditor().getEditorComponent()).addFocusListener(this);
		this.addPropertyChangeListener("editor", this);
		this.setHasHelpButton(true);
	}

	public void initComp() {
		this.mdModel = this.createFormModel2MDModel();
		createPopupComponent();
	}
	
	/**
	 *
	 */
	protected RowSetValue rowSetValue = null;
	  
	protected boolean notCheckFKey = false;
	
	/**
	 *
	 */
	protected JComponent popupComp = null;
	  
	public boolean isNotCheckFKey() {
		return notCheckFKey;
	}

	public void setNotCheckFKey(boolean notCheckFKey) {
		this.notCheckFKey = notCheckFKey;
	}
	
	/**
	 *
	 */
	protected MDModel mdModel = null;
	  
	/**
	 *
	 * @param mdModel MDModel
	 */
	public void setMDModel(MDModel mdModel) {
//		  if(this.userMDModel !=null) return;
		if(this.getViewDataSetID() == null || "".equals(this.getViewDataSetID())) return;
		this.mdModel.setBizDCTID(this.getViewDataSetID());
	    this.mdModel.setRLGL_ID(this.getRlglID());
	    this.mdModel.setFKEY_ID(this.getFkeyID());
	    try {
	    	this.mdModel.load();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    createPopupComponent();
	}
	  
	/**
	 *
	 */
	protected void createPopupComponent() {
		//
//	    if (mdModel != null && getMDModel() != null) {
//	        if (mdModel.getParentDataComponent() == null)
//	        mdModel.setParentDataComponent(this.getMDModel().getDataContainer());
//	        if (mdModel.getCustomFunction() == null)
//	            mdModel.setCustomFunction(this.getMDModel().getCustomFunction());
//	        if (mdModel.getDataFilter() == null)
//	            mdModel.setDataFilter(this.getMDModel().getDataFilter());
//	    }
	    // 如果符合条件就创建
	    if (! (popupType != 0 && getHelpMDModel() != null && this.getViewDataSetID() != null && popupComp == null)) {
	      return;
	    }
	    boolean op = this.isOpaque();
//	    // 创建表格
//	    if (popupType == 1) {
//	      popupComp = createDMTable();
//	    }
	    // 创建树
	    if (popupType == 2) {
	      popupComp = createDMTree();
	    }
//	    // 创建树表
//	    if (popupType == 3) {
//	      popupComp = createDMTreeTable();
//	    }
	    popupPanel = DataSetTablePanel2.getInstance(popupComp,this,dctMetaData);
	    this.setPopupComponent(popupPanel);
	    this.setRenderer(popupPanel.getListCellRenderer());
	    this.setOpaque(op);
	}
	  
	/**
	 *
	 */
	protected DCTMetaData dctMetaData = null;
	
	/**
	 *
	 * @return DMTable
	 */
//	protected DictTable createDMTable() {
//		DictTable dmTable = null;
//	    try {
//	    	dctMetaData = DynTableUtils.getDCTMetaData(getHelpMDModel(), this);
//	    	if (dctMetaData == null) {
//	    		return null;
//	    	}
//	    	ColumnModel columnModel = DynTableUtils.createColumnModel(dctMetaData);
//	    	DictModel tableModel = DynTableUtils.createDCTableModel(dctMetaData, getHelpMDModel(), columnModel, this);
//	    	DynTableUtils.processMDModel(getHelpMDModel(), this);
//	    	dmTable = DynTableUtils.createDMTable(tableModel);
//	    	dmTable.setDCTMetaData(dctMetaData);
//	    }
//	    catch (Exception ex) {
//	      ex.printStackTrace();
//	    }
//	    return dmTable;
//	  }

	  /**
	   *
	   * @return DMTree
	   */
	  protected DMTree createDMTree() {
	    DMTree tree = null;
	    try {
//	      dctMetaData = DynTableUtils.getDCTMetaData(getHelpMDModel(), this);
//	      if (dctMetaData == null) {
//	        return null;
//	      }

	      dctMetaData = (DCTMetaData) DCTMetaDataManager.getDCTMetaDataManager().getMetaData(this.viewDataSetID);
	      DCTreeModel treeModel = DynTreeUtils.createDCTreeModel(getHelpMDModel(), this);
	      DynTableUtils.processMDModel(getHelpMDModel(), this);
	      tree = DynTreeUtils.createDMTree(treeModel);
	      tree.setDCTMetaData(dctMetaData);	      
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return tree;
	  }

//	  /**
//	   *
//	   * @return DMTreeTable
//	   */
//	  protected DMTreeTable createDMTreeTable() {
//	    DMTreeTable dmTreeTable = null;
//	    try {
//	      dctMetaData = DynTableUtils.getDCTMetaData(getHelpMDModel(), this);
//	      if (dctMetaData == null) {
//	    	  return null;
//	      }
//	      ColumnModel columnModel = DynTableUtils.createColumnModel(dctMetaData);
//	      DCTreeTableModel tableModel = DynTreeTableUtils.createDCTreeTableModel(
//	          dctMetaData,
//	          getHelpMDModel(), columnModel, this);
//	      DynTableUtils.processMDModel(getHelpMDModel(), this);
//	      dmTreeTable = DynTreeTableUtils.createDMTreeTable(tableModel);
//	      dmTreeTable.setDCTMetaData(dctMetaData);
//	    }
//	    catch (Exception ex) {
//	      ex.printStackTrace();
//	    }
//	    return dmTreeTable;
//	  }
	  
	  
	/**
	 *
	 * @return MDModel
	 */
	public MDModel getMDModel() {
		return mdModel;
	}
	  
	public MDModel getHelpMDModel() {
		if(mdModel == null){ //如果为NULL，说明设计时设的不对，绝对不能在这里创建，影响设计时选的本身的属性
			  mdModel = createFormModel2MDModel();
		  }
	      return mdModel;
	}
	
	private MDModel createFormModel2MDModel() {
		MDModel mdm = new MDModel();
		if(this.viewDataSetID == null) return null;
		mdm.setServiceKey("DICTService");
		mdm.setBizMDLID("ACFWModel");
		mdm.setBizDCTID(this.viewDataSetID);

		try {
			mdm.load();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return mdm;
	}
	
	/**
	 *
	 * @return RowSetValue
	 */
	public RowSetValue getRowSetValue() {
		return rowSetValue;
	}
	  
	/**
	 *
	 * @param rowSetValue RowSetValue
	 */
	public void setRowSetValue(RowSetValue rowSetValue) {
		this.rowSetValue = rowSetValue;
	    if ( rowSetValue != null )
	    	this.mainRowSet = rowSetValue.getMainRowSet();
	}
	  
	/**
	 *
	 * @param rowSet ESPRowSet
	 */
	public void setMainRowSet(ESPRowSet rowSet) {
	    mainRowSet = rowSet;
	    String value = null;
	    if (rowSet != null) {
	      value = (String) RowSetValueUtils.getObject(rowSet, this);
	    }
	    this.setSelectedItem(value);
	}
	  
	/**
	 *
	 * @param anObject Object
	 */
	public void setDataSetValue(ESPRowSet selectRowSet,Object anObject) {
		super.setSelectedItem(anObject);
	    if(anObject==null)anObject="";
	    ((JTextField) editor.getEditorComponent()).setText(anObject.toString());
	    
	    if(mainRowSet == null) {
	    	this.mainRowSet = selectRowSet;
	    }
	    if ( mainRowSet != null ) {
	        String DCT_ID = this.viewDataSetID;//this.getViewDataSetID();
	        String COL_ID = this.getDataSetColID();
	        String VALUE_COL_ID = getValueDataSetColID();
	        if (VALUE_COL_ID == null || VALUE_COL_ID.trim().length() == 0)
	        	mainRowSet.setID2RowSetFront(DCT_ID,COL_ID,selectRowSet);
	        RowSetValueUtils.putObject(mainRowSet,this,anObject);
	    }
	}
	  
	/**
	 * 扩展帮组编号列 add by ZhangES 2014-05-22
	 */
	protected String helpColumn_ID = null;

	protected boolean ownerHelp = false;

	public boolean isOwnerHelp() {
		return ownerHelp;
	}

	public void setOwnerHelp(boolean ownerHelp) {
		this.ownerHelp = ownerHelp;
	}

	public String getHelpColumn_ID() {
		return helpColumn_ID;
	}

	public void setHelpColumn_ID(String helpColumn_ID) {
		this.helpColumn_ID = helpColumn_ID;
	}

	// private MDModel createFormModel2MDModel() {
	// MDModel mdm = new MDModel();
	// FormModel formModel = (FormModel)this.getDataSetComponent();
	// if(formModel == null || this.viewDataSetID == null) return null;
	// mdm.setDataBase(formModel.getDataBase());
	// mdm.setBizMDLID(formModel.getBizMDLID());
	// mdm.setParamConverter(formModel.getParamConverter());
	// mdm.setServiceKey("DICTService");
	// mdm.setDataFilter(formModel.getDataFilter());
	//
	// mdm.setBizDCTID(this.viewDataSetID);
	// mdm.setRLGL_ID(this.getRlglID());
	// mdm.setFKEY_ID(this.getFkeyID());
	// try {
	// mdm.load();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return mdm;
	// }

	// protected Object prepareHelp(){
	// if(getHelpMDModel().getDataFilter()!=null)
	// getHelpMDModel().getDataFilter().setParamObj(this);
	// return null;
	//
	// }
	// 是不是在table内部的editor
	// 如果不是，要处理focus,为了在fcous离开时，显示名称
	protected boolean inner = false;

	public void setInnerTable(boolean b) {
		inner = b;
	}

	public void helpDICT(Object actionObject, Object nodeArray, Action action, ActionEvent actionevent) {
		EFRowSet                                sel = null;
		String                                value = null;
		DataSetFieldComboBox    datasetFileComboBox = (DataSetFieldComboBox) actionObject;
		HelpInfoContext                         hic = new HelpInfoContext();
		
		
		hic.setDCTID(this.viewDataSetID);
		sel = ESPHelpUtil.ShowHelp(hic);
		value = sel.getString(datasetFileComboBox.getDataSetColID(), "");
		if(mainRowSet == null) {
			mainRowSet = sel;
		}
		if(mainRowSet!=null){
			if(this.isEditable()){//不editable setText不管用，只能selectitem
				//但如果在table里面selectitem了，焦点就不知跑哪去了 快捷键不管用
				String DCT_ID = this.viewDataSetID;//this.getViewDataSetID();
				String COL_ID = this.getDataSetColID();
				String VALUE_COL_ID = getValueDataSetColID();
				if (VALUE_COL_ID == null || VALUE_COL_ID.trim().length() == 0)
					mainRowSet.setID2RowSetFront(DCT_ID, COL_ID, sel);
	           
				RowSetValueUtils.putObject(mainRowSet, this, value);
				if(rowSetValue!=null)rowSetValue.setCellValue(value);
//	           this.setSelectedItem(value);
				((JTextField) editor.getEditorComponent()).setText(value);
	       }else
	    	   setDataSetValue(sel,value);
		}else{
			mainRowSet = sel;
			//直接从问号帮助时需要该行
			super.setSelectedItem(value);
			((JTextField) editor.getEditorComponent()).setText(value);
		}
	}

	public void focusGained(FocusEvent e) {
	    String COL_ID = getDataSetColID();
	    if(userInternalDataSetID)
	      COL_ID=this.internalDataSetID+"."+COL_ID;

	    if(mainRowSet == null) return;
	    String value=mainRowSet.getString(COL_ID,"");
	    ( (JTextField) this.getEditor().getEditorComponent()).setText(value);
	}
	//下面两个事件，只有在table外的独立的组件才有
	public void focusLost(FocusEvent e) {
		String aa=( (JTextField) this.getEditor().getEditorComponent()).getText();
		try{
//			boolean b = checkFkeyValue(aa);
			boolean b = true;
			if (b){
				RowSetValueUtils.putObject(mainRowSet, this, aa);
				String mc=getDisplayString(aa);
				( (JTextField) this.getEditor().getEditorComponent()).setText(mc);
			}else{
				if(!isNotCheckFKey()){
					RowSetValueUtils.putObject(mainRowSet, this, "");
				}else{
					RowSetValueUtils.putObject(mainRowSet, this, aa);
				}
	    	  
			}
	    }catch(Exception ee){
	      ee.printStackTrace();
	    }
	    
	}
	
	protected String getDisplayString(String txt){

	    String COL_ID = getDataSetColID();
	    String DCT_ID = this.viewDataSetID;//getViewDataSetID();
	    String VIEW_COL_ID = getViewDataSetColID();
	    String VALUE_COL_ID = getValueDataSetColID();
	    // 如果设置了值ID，则不处理，是什么值就显示什么值
	    if ( (VALUE_COL_ID != null && VALUE_COL_ID.trim().length() != 0) || VIEW_COL_ID != null || COL_ID != null || 
	         (DCT_ID == null || DCT_ID.trim().length() == 0)|| VIEW_COL_ID.equals(COL_ID) )
	      return txt;

	    ESPRowSet viewRowSet = mainRowSet.getID2RowSet(DCT_ID,COL_ID);
	    if ( viewRowSet != null )
	      return viewRowSet.getString(VIEW_COL_ID,"");
	    return mainRowSet.getID2Name(DCT_ID,COL_ID,"");

	}
	
	/**
	 *
	 */
	protected int popupType = 2;

	/**
	 * 
	 * @return int
	 */
	public int getPopupType() {
		return popupType;
	}

	private Map DCTSuffixMap = new HashMap();

	public void propertyChange(PropertyChangeEvent evt) {

	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public Object getCellEditorValue() {
		return null;
	}

	public boolean isCellEditable(EventObject anEvent) {
		return false;
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		return false;
	}

	public boolean stopCellEditing() {
		return false;
	}

	public void cancelCellEditing() {

	}

	public void addCellEditorListener(CellEditorListener l) {

	}

	public void removeCellEditorListener(CellEditorListener l) {

	}

}
