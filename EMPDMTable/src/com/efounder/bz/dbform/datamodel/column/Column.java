package com.efounder.bz.dbform.datamodel.column;

import java.math.*;
import java.util.*;

import javax.swing.table.*;

import org.jdesktop.swingx.table.*;

import com.core.xml.StubObject;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.DMColComponent;
import com.efounder.bz.dbform.datamodel.DataSetComponent;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.data.DctConstants;

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

public class Column extends TableColumnExt implements DMColComponent {
	
	StubObject stub = new StubObject();

	//编辑方式
	private int editStyle=1;

	public int getEditStyle() {
		return editStyle;
	}
	public void setEditStyle(int editStyle) {
		this.editStyle = editStyle;
	}
	//工作表ID
	private int sheetID=0;
	public int getSheetID() {
		return sheetID;
	}
	public void setSheetID(int sheetID) {
		this.sheetID = sheetID;
	}
	//单元格ID
	private String cellID;
	public String getCellID() {
		return cellID;
	}
	public void setCellID(String cellID) {
		this.cellID = cellID;
	}
	
	/**
	 * 不透明
	 */
	private boolean opaque = true;
	public boolean isOpaque() {
		return opaque;
	}
	public void setOpaque(boolean opaque) {
		this.opaque = opaque;
	}

	/**
	 *
	 */
	protected ColumnMetaData columnMetaData = null;
	
	/**
	 * 排序类型
	 */
	private int sortOrder;
	/**
	 *
	 */
	public static final int ASCENDING = 1;
	/**
	 *
	 */
	public static final int DESCENDING = 2;
	/**
	 *
	 */
	public static final int UNSORTED = 0;
	
	/**
	 *
	 * @return ESPRowSet
	 */
	public ColumnMetaData getColumnMeataData() {
		return columnMetaData;
	}
	/**
	 *
	 * @param dimSetRowSet ESPRowSet
	 */
	public void setColumnMeataData(ColumnMetaData columnMetaData) {
		this.columnMetaData = columnMetaData;
	}
	
	/**
	 *
	 */
	protected ESPRowSet valSetRowSet = null;
	/**
	 *
	 * @return ESPRowSet
	 */
	public ESPRowSet getValSetRowSet() {
		return valSetRowSet;
	}
	/**
	 *
	 * @param valSetRowSet ESPRowSet
	 */
	public void setValSetRowSet(ESPRowSet valSetRowSet) {
		this.valSetRowSet = valSetRowSet;
	}

	/**
	 *
	 */
	protected static java.util.Map columnClassMap = new java.util.HashMap();
	/**
	 *
	 */
	static {
		columnClassMap.put("C",String.class);
		columnClassMap.put("T",Date.class);
		columnClassMap.put("I",Integer.class);
		columnClassMap.put("N",BigDecimal.class);
	}
	/**
	 *
	 * @param flag String
	 * @param def Class
	 * @return Class
	 */
	public static Class getColumnClass(String flag,Class def) {
		Class val = (Class)columnClassMap.get(flag);
		if ( val == null ) val = def;
		return val;
	}
	/**
	 *
	 * @return Column
	 */
	public static Column getInstance() {
		Column column = new Column();
		return column;
	}
	
	/**
	 *
	 * @return Column
	 */
	public static Column getInstance(ColumnMetaData columnMetaData) {
		Column column = new Column();
		column.setColumnMeataData(columnMetaData);
		return column;
	}

	/**
	 *
	 */
	public Column() {
		super();
		modelIndex = -1;
		this.setMinWidth(0);
	}
	/**
	 *
	 * @param modelIndex int
	 * @param width int
	 * @param cellRenderer TableCellRenderer
	 * @param cellEditor TableCellEditor
	 */
	public Column(int modelIndex, int width, TableCellRenderer cellRenderer, TableCellEditor cellEditor) {
		super(modelIndex, width, cellRenderer, cellEditor);
	}
	
	public String getDataType() {
		return this.getColumnMeataData().getColType();
	}

	/**
	 *
	 * @return String
	 */
	public String getCaption() {
		return this.getColumnMeataData().getColMc();
	}

	/**
	 *
	 * @return String
	 */
	public boolean isNull() {
		return this.getColumnMeataData().getString("COL_ISNULL", "").equals("0");
	}
 
	public String isFKEY() {
		return this.getColumnMeataData().getString("COL_ISFKY", "");
	}
	
	public String getFOBJ() {
		return this.getColumnMeataData().getString("COL_FOBJ", "");
	}
	
	public void setFKMetaData(Object o){
		stub.setObject(DctConstants.METADATA, o);
    }
	
	
	public void getFKMetaData(){
		stub.getObject(DctConstants.METADATA, null);
    }
	
	public void setFKDataSet(Object map){
		stub.setObject("DATAMAP",map);
    }
	
	public EFDataSet getFKDataMap(){
        return (EFDataSet)stub.getObject("DATAMAP",null);
    }
	
	/**
	 *
	 */
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
		this.internalDataSetID = dataSetID;
	}

	/**
	 * 
	 * @return Object
	 */
	public Object getIdentifier() {
		if ((internalDataSetID != null && internalDataSetID.trim().length() != 0) && this.dataSetColID != null) {
			return internalDataSetID + "." + dataSetColID;
		}
		return dataSetColID;
	}

	/**
	 *
	 */
	protected String dataSetColID = null;

	/**
	 * 
	 * @return String
	 */
	public String getDataSetColID() {
		return this.dataSetColID;
	}

	/**
	 * 
	 * @param dataSetColID   String
	 */
	public void setDataSetColID(String dataSetColID) {
		this.dataSetColID = dataSetColID;
	}

	/**
	 *
	 * @return String
	 */
	public String toString() {
		return this.getColumnMeataData().getString("COL_MC", "");
	}
	
	public DataSetComponent getDataSetComponent() {
		return null;
	}
	
	public String getDataSetID() {
		return null;
	}
	
	public boolean getMetaData() {
		return false;
	}
	
	public void setMetaData(boolean v) {
		
	}
	
	public void setViewDataSetID(String viewDataSetID) {
		
	}
	
	public String getViewDataSetID() {
		return null;
	}
	
	public void setViewDataSetColID(String viewDataSetColID) {
		
	}
	
	public String getViewDataSetColID() {
		return null;
	}
	
	public void setValueDataSetColID(String valueDataSetColID) {
		
	}
	
	public String getValueDataSetColID() {
		return null;
	}
	
	public boolean isUserInternalDataSetID() {
		return false;
	}
	
	public void setUserInternalDataSetID(boolean v) {
		
	}
	
	public String getFkeyID() {
		return null;
	}
	
	public void setFkeyID(String fkey) {
		
	}
	
	public String getRlglID() {
		return null;
	}
	
	public void setRlglID(String rlglID) {
		
	}
	
	public int getHorizontalAlignment() {
		return 0;
	}
	
	public void setHorizontalAlignment(int horizontalAlignment) {
		
	}
	
	public String getNumberFormat() {
		return null;
	}
	
	public void setNumberFormat(String numberFormat) {
		
	}
	
	public String getDateFormat() {
		return null;
	}
	
	public void setDateFormat(String dateFormat) {
		
	}
	
	public String getFormulaOne() {
		return null;
	}
	
	public void setFormulaOne(String formulaOne) {
		
	}
	
	public String getEditMask() {
		return null;
	}
	
	public void setEditMask(String editMask) {
		
	}
}
