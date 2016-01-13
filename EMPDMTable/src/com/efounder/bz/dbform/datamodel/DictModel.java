package com.efounder.bz.dbform.datamodel;

import java.util.*;

import javax.swing.event.*;
import javax.swing.table.*;

import com.core.xml.JBOFClass;
import com.core.xml.StubObject;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.component.RowSetValueUtils;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.dbc.ClientDataSet;
import com.efounder.dbc.swing.table.ICellEditable;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.data.DctContext;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.efounder.dctbuilder.event.IDataLoaderClrEvent;
import com.efounder.dctbuilder.loader.Provider;
import com.efounder.dctbuilder.loader.Resolver;
import com.efounder.dctbuilder.mdl.DictPluginManager;
import com.efounder.dctbuilder.view.DictView;
import com.efounder.eai.data.JParamObject;
import com.efounder.service.meta.db.DictMetadata;

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
public class DictModel extends AbstractTableModel implements ListSelectionListener {
	
	protected       ClientDataSet         dataSet = new ClientDataSet();
    protected            Resolver        resolver = null;
    protected            Provider        provider = null;
    protected      DictMetaDataEx             dmd;
    protected              Object            view;
    protected          DctContext         context = new DctContext();
    protected        JParamObject           param = JParamObject.Create();
    protected       java.util.Map       valueList = new java.util.Hashtable();
    protected              String   userPluginKey = "";
    protected   DictPluginManager             dpm = new DictPluginManager();
    protected          StubObject            node = null;
    private               boolean        readOnly;
    private                String          dictID;
    private                String          dictMC;
    private         ICellEditable             ice = null;
    protected         ColumnModel     columnModel = null;
    
    public DictModel(ICellEditable ie) {
        super();
        ice=ie;
    }
    
    public void setNodeStub(StubObject node) {
    	this.node = node;
        setValueNoChange("NODESTUB", node);
    }
    public StubObject getNodeStub() {
        return node;
    }

    public void setValueNoChange(String valueName, Object value) {
        Object oldValue = getValue(valueName, null);
        if (value != null)
            valueList.put(valueName, value);
        else
            valueList.remove(valueName);
    }

    /**
    *
    */
   protected java.util.Map tableColumnMapByModelIndex = new HashMap();
   /**
    *
    * @param modelIndex int
    * @return TableColumn
    */
   	protected TableColumn getTableColumnByModelIndex(int modelIndex) {
   		if (columnModel == null) {
   			return null;
   		}
   		String index = String.valueOf(modelIndex);
   		Column tableColumn = null;
//   		if (DataComponentUtil.isDesigner(this))//add by fsz 不易找
   		tableColumn= (Column) tableColumnMapByModelIndex.get(index);
   		if (tableColumn == null) {
   			tableColumn = columnModel.getColumnByModelIndex(modelIndex);
   			if (tableColumn != null) {
   				tableColumnMapByModelIndex.put(index, tableColumn);
   			}
   		}
   		return tableColumn;
   	}
   
    public Object getValue(String valueName, Object defaultValue) {
        Object value = valueList.get(valueName);
        if (value == null)
            value = defaultValue;
        return value;
    }

    /**
     *
     * @param dctid String
     */
    public void setDCT_ID(String dctid) {
        setValueNoChange("DCT_ID", dctid);
    }

    /**
     *
     * @return String
     */
    public String getDCT_ID(){
        return (String) getValue("DCT_ID", "");
    }
    
    /**
    *
    * @param dm DictMetadata
    */
    public void setMetaData(DictMetadata dm) {
    	if(dm!=null){
    		Object old = dmd;
    		dmd = new DictMetaDataEx(dm);
    		if (getView() != null && getView() instanceof DictView) {
    			((DictView) getView()).processMetaDataChanged(dmd);
    		}
    	}else
    		dmd=new DictMetaDataEx(DictMetadata.getInstance());
    }
    
    public DictMetaDataEx getMetaData() {
    	return dmd;
    }
    
    public ClientDataSet getDataSet() {
    	return dataSet;
    }

   public Provider getProvider() {
       return provider;
   }

   public Resolver getResolver() {
       return resolver;
   }

   public String getUserPluginKey() {
       return userPluginKey;
   }

   public Object getView() {
       return view;
   }

   public DctContext getCdsParam() {
       return context;
   }

   public JParamObject getParam() {
	   return param;
   }

   public void setPO(JParamObject param) {
	   this.param = param;
   }

   public void setDataSet(ClientDataSet dataSet) {
       if(this.dataSet!=dataSet){
           this.dataSet = dataSet;
       }
   }

   public void setProvider(Provider provider) {
       this.provider = provider;
   }

   public void setResolver(Resolver resolver) {
       this.resolver = resolver;
   }
   public void setUserPluginKey(String userPluginKey) {
       this.userPluginKey = userPluginKey;
   }

   public void setView(Object view) {
       this.view = view;
   }

   public void setCdsParam(DctContext cdsParam) {
       this.context = cdsParam;
   }
   
   /**
    *
    * @param cls Class
    * @param syskey String
    * @return List
    */
	public List getUserPlugin(Class cls,String syskey) {
		String key = getUserPluginKey();
		return dpm.getPlugins(cls,key,syskey);
	}
	/**
	 *
	 * @param dctModel DictModel
	 * @param method String
	 * @throws Exception
	 */
	public Object ClientEventProcess(DictModel dctModel,String method) throws Exception {
		Object o = null;
		List list=getUserPlugin(IDataLoaderClrEvent.class,DictPluginManager.LOADERCLR);
		for (int i = 0; i < list.size(); i++) {
			IDataLoaderClrEvent ifp = (IDataLoaderClrEvent) list.get(i);
			if(ifp.canProcess(dctModel,context)) {
				o = JBOFClass.CallObjectMethodException(ifp, method,new Object[]{dctModel,context});
				if (o != null && o instanceof Boolean && !(Boolean) o) {
					return o;
				}
			}
		}
		return o;
	}

	/**
	 *
	 * @throws Exception
	 */
	public void refrensh() throws Exception {
		dataSet = new ClientDataSet();
		context.setFirstLoad(true);
		loadData();
	}

	/**
	 *
	 * @throws Exception
	 */
	public void loadData() throws Exception {
		dataSet = new ClientDataSet();
		this.setColModel(new ColumnModel());
		context.setFirstLoad(true);
		//beforeDataLoad
		if (provider != null) {
			ClientEventProcess(this, "beforeDataLoad");
//			try {
//				Object o1 = getScriptManager().runFunction(this, "beforeLoad");
//				if (o1 != null && o1 instanceof Boolean) {
//					if (! ( (Boolean) o1).booleanValue())
//						return;
//				}
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
			provider.provideData(this, true);

			//afterDataLoad
//			try {
//				Object o2 = getScriptManager().runFunction(this, "afterLoad");
//			} catch (Exception ex1) {
//				ex1.printStackTrace();
//			}
			ClientEventProcess(this, "afterDataLoad");

			//SetColumnPropery
			ClientEventProcess(this, "SetColumnPropery");
		}
//		this.fireStateChanged(provider, DctChangeListener.DATA_LOADED);
	}

	public void loadChildData(String bm, String js) throws Exception {
//    if (provider != null) {
//      js = String.valueOf(Integer.parseInt(js) + 1);
//      String key = "LOADED" + bm + "_" + js;
//      if (this.getValue(key, null) != null)
//        return;
//      context.setCurBM(bm);
//      context.setCurJS(js);
//      context.setFirstLoad(false);
//      ClientEventProcess(this, "beforeDataLoad");
//      try {
//        Object o1 = getScriptManager().runFunction(this, "beforeLoad");
//        if (o1 != null && o1 instanceof Boolean) {
//          if (! ( (Boolean) o1).booleanValue())
//            return;
//        }
//      }
//      catch (Exception ex) {
//        ex.printStackTrace();
//      }
//      provider.provideData(this, true);
//      try {
//        Object o2 = getScriptManager().runFunction(this, "afterLoad");
//      }
//      catch (Exception ex1) {
//        ex1.printStackTrace();
//      }
//      //
//      ClientEventProcess(this, "afterDataLoad");
//      //
//      ClientEventProcess(this, "SetColumnPropery");
//      this.setValueNoChange(key, "1");
//    }
	}
  
	/**
	 *
	 * @throws Exception
	 */
	public Boolean beforeDataSave() throws Exception {
		Object o = ClientEventProcess(this, "beforeDataSave");
		if (o != null && o instanceof Boolean && !(Boolean)o) return new Boolean(false);
//		Object o1 = getScriptManager().runFunction(this, "beforeSave");//脚本
//		if (o1 != null && o1 instanceof Boolean) {
//			if (!((Boolean) o1).booleanValue()) return new Boolean(false);
//		}
		return Boolean.TRUE;
	}

	/**
	 *
	 * @return Object
	 * @throws Exception
	 */
	public Object saveData() throws Exception {
		if (resolver != null) {
			Boolean b = beforeDataSave();
			if (!b.booleanValue()) return new Boolean(false);
//			ClientEventProcess(this, "beforeDataSave");
//			Object o1 = getScriptManager().runFunction(this, "beforeSave");//脚本
//			if (o1 != null && o1 instanceof Boolean) {
//				if (!((Boolean) o1).booleanValue()) return new Boolean(false);
//			}
			Object o = resolver.resolveData(this);
			this.afterDataSave();
//         ClientEventProcess(this, "afterDataSave");
//         Object o2 = getScriptManager().runFunction(this, "afterSave");//脚本
			return o;
		}
		return null;
	}

	/**
	 *
	 * @throws Exception
	 */
	public void afterDataSave() throws Exception {
		ClientEventProcess(this, "afterDataSave");
//		Object o1 = getScriptManager().runFunction(this, "afterSave");//脚本
	}

	public boolean isCellEditable(int row, int col) {
		List list = getUserPlugin(ICellEditable.class, DictPluginManager.DATASETEVENT);
		for (int i = 0; i < list.size(); i++) {
			ICellEditable ifp = (ICellEditable) list.get(i);
			if (!ifp.isCellEditable(this, row, col))
				return false;
		}
		return true;
	}
 
	/**
	 *
	 * @return boolean
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 *
	 * @return String
	 */
	public String getDictID() {
		return dictID;
	}

	/**
	 *
	 * @return String
	 */
	public String getDictMC() {
		return dictMC;
	}

	/**
	 *
	 * @param readOnly boolean
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		this.getCdsParam().setReadOnly(readOnly);
	}

	/**
	 *
	 * @param dictID String
	 */
	public void setDictID(String dictID) {
		this.dictID = dictID;
		this.setDCT_ID(dictID);
	}

	/**
	 *
	 * @param dictMC String
	 */
	public void setDictMC(String dictMC) {
		this.dictMC = dictMC;
	}

	/**
	 *
	 * @return ColumnModel
	 */
	public ColumnModel getColModel() {
		return columnModel;
	}

	/**
	 *
	 * @param colModel ColumnModel
	 */
	public void setColModel(ColumnModel colModel) {
//		if (this.columnModel != null) {
//	      this.columnModel.setDMComponent(null);
//	    }
	    this.columnModel = colModel;
//	    if (this.columnModel != null) {
//	      this.columnModel.setDMComponent(this);
//	    }
	}
	
	public String getColumnName(int columnIndex){
		ColumnModel colModel = this.getColModel();
		if(colModel != null && colModel.getColumn(columnIndex) != null) {
			Column column = (Column) colModel.getColumn(columnIndex);
		}		
		return "你想要设置的对应列的列名";
	}
	  
	/**
	 *
	 * @param columnIndex int
	 * @return Class
	 */
	public Class<?> getColumnClass(int columnIndex) {
	    // 先获取表列
//	    TableColumn tableColumn = this.getTableColumnByColumnIndex(columnIndex);
//	    // 获取列信息
//	    ESPRowSet rowSet = this.getColumnInfo(tableColumn);
//	    // 为空，默认为String.class
//	    if (rowSet == null) {
//	      return String.class;
//	    }
//	    // 取出列信息
//	    String colType = rowSet.getString("COL_TYPE", "C");
	    // 从对照表中取出列类型
	    return Column.getColumnClass("C", String.class);
	  }
	
	public int getRowCount() {
		// 默认为2行
	    if (this.dataSet == null) {
	      return 1;
	    }
	    return dataSet.getRowCount();
	}

	
	public int getColumnCount() {
		// 默认为4列
	    if (this.columnModel == null) {
	      return 4;
	    }
	    return columnModel.getColumnCount();
	}

	
	public Object getValueAt(int rowIndex, int columnIndex) {
		//获取列模型
		ColumnModel            colModel = this.getColModel();
		//获取某一列
		Column                 tableCol = null;
		//获取行
		EFRowSet                 rowset = dataSet.getRowSet(rowIndex);
		//获取列的元数据
		ColumnMetaData   columnMetaData = null;
		//获取列名
		String                    colId = null;
		//获取单元格的值
		String                cellValue = null;
		
		if(rowset != null && columnIndex >= 0 && colModel.getColumnByModelIndex(columnIndex) != null) {			
			tableCol = (Column) colModel.getColumnByModelIndex(columnIndex);
//			cellValue = rowset.getString(tableCol.getColumnMeataData().getColid(), "");
//			columnMetaData = tableCol.getColumnMeataData();
//			colId = columnMetaData.getString("COL_ID", "");
//			cellValue = rowset.getString(colId, "");
//			return cellValue;
			Object obj = RowSetValueUtils.getObject(rowset,(DMColComponent)tableCol);
			return obj;
		} 
		
		return "";
	}

	/**
     *  This empty implementation is provided so users don't have to implement
     *  this method if their data model is not editable.
     *
     *  @param  aValue   value to assign to cell
     *  @param  rowIndex   row of cell
     *  @param  columnIndex  column of cell
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	//获取列模型
		ColumnModel            colModel = this.getColModel();
		//获取某一列
		Column                 tableCol = null;
		//获取行
		EFRowSet                 rowset = dataSet.getRowSet(rowIndex);
		//获取列的元数据
		ColumnMetaData   columnMetaData = null;
		//获取列名
		String                    colId = null;
		//获取单元格的值
		String                cellValue = rowset.getString(colId, "");
		//获取表行数据
		EFRowSet rowSet = dataSet.getRowSet(rowIndex);
		// 先获取表列
	    Column tableColumn = (Column)this.getTableColumnByModelIndex(columnIndex);
	    
		if(columnIndex >= 0 && colModel.getColumn(columnIndex) != null) {
			tableCol = (Column) colModel.getColumn(columnIndex);
			columnMetaData = tableCol.getColumnMeataData();
			colId = columnMetaData.getString("COL_ID", "");
			cellValue = rowset.getString(colId, "");
		} 

	    RowSetValueUtils.putObject(rowSet,(DMColComponent)tableColumn,aValue);
	    if(this.getDataSet().getRowSet(rowIndex).getDataStatus() != EFRowSet._DATA_STATUS_INSERT_) {
	    	this.getDataSet().getRowSet(rowIndex).setDataStatus(EFRowSet._DATA_STATUS_UPDATE_);
	    }	    
    }
    
    /**
     *
     * @param tableColumn Column
     * @return ESPRowSet
     */
    protected ESPRowSet getColumnInfo(TableColumn tableColumn) {
    	if (columnModel == null) {
    		return null;
    	}
    	ESPRowSet rowSet = (ESPRowSet) tableColumnMapByModelIndex.get(tableColumn);
    	if (rowSet == null) {
    		rowSet = columnModel.getColumnInfo(tableColumn);
    		if (rowSet != null) {
    			tableColumnMapByModelIndex.put(tableColumn, rowSet);
    		}
    	}
    	return rowSet;
    }

	public void valueChanged(ListSelectionEvent e) {
		System.out.println("DictModel.valueChanged.......");
	}
	
	public ICellEditable getICellEditable() {
		return this.ice;
	}
}
