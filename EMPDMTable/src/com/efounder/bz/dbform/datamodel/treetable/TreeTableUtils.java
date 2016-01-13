package com.efounder.bz.dbform.datamodel.treetable;

import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.base.data.EFDataSet;
import java.util.HashMap;
import com.efounder.builder.base.data.ESPRowSet;
import java.util.List;
import com.efounder.pub.util.StringFunction;
import com.efounder.bz.dbform.datamodel.ColumnModel;
import com.efounder.builder.base.data.*;

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
public class TreeTableUtils {
	/**
	 *
	 */
	protected TreeTableUtils() {
	}
	/**
	 *
	 * @param treeTableModel DataSetTreeTableModel
	 * @param dctMetaData DCTMetaData
	 * @param dataSet EFDataSet
	 * @throws Exception
	 */
	public static void createTreeTable(DataSetTreeTableModel treeTableModel, DCTMetaData dctMetaData,EFDataSet dataSet,ColumnModel colModel) {
		DataSetTreeTableNode rootNode = createTreeTableNode(dctMetaData,dataSet,colModel);
		treeTableModel.setRoot(rootNode);
	}
	public static DataSetTreeTableNode createTreeTable(DataSetTreeTableModel treeTableModel, String[] dsids,EFDataSet dataSet,ColumnModel colModel) {
		DataSetTreeTableNode rootNode = DataSetTreeTableNode.getInstance();
		String             maindsname = dataSet.getTableName();
		rootNode.setColModel(colModel);
		addChildData(maindsname,rootNode,dataSet,dsids,-1,colModel);
		treeTableModel.setRoot(rootNode);
		return rootNode;
	}
	
	protected static void addChildData(String maindsname,DataSetTreeTableNode parent,EFDataSet dataSet,String[] childdsid,int pos,ColumnModel colModel){
  	 if(dataSet==null)return;
  	 int cnt = dataSet.getRowCount();
  	 for(int i=0;i<cnt;i++){
  		 EFRowSet ers=dataSet.getRowSet(i);
  		 ers.putString("JS",String.valueOf(pos+1));
  		 DataSetTreeTableNode rowSetNode = DataSetTreeTableNode.getInstance(ers);
  		 rowSetNode.setColModel(colModel);
  		 rowSetNode.setRowSet(ers);
  		 parent.add(rowSetNode);
  		 if(pos+1<childdsid.length)
  			 addChildData(maindsname,rowSetNode,ers.getDataSet(childdsid[pos+1]),childdsid,pos+1,colModel);
  		 if(ers.getDataSet(maindsname)!=null){
  			 addChildData(maindsname,rowSetNode,ers.getDataSet(maindsname),childdsid,-1,colModel);
  		 }
  	 }
	}
	
	/**
	 *
	 * @param dctMetaData DCTMetaData
	 * @param dataSet EFDataSet
	 * @return DataSetTreeTableNode
	 * @throws Exception
	 */
	public static DataSetTreeTableNode createTreeTableNode(DCTMetaData dctMetaData,EFDataSet dataSet,ColumnModel colModel) {
		DataSetTreeTableNode rootNode = DataSetTreeTableNode.getInstance();
		rootNode.setColModel(colModel);
		List<ESPRowSet> rowSetList = dataSet.getRowSetList();
		if ( rowSetList == null || rowSetList.size() == 0 ) return rootNode;
		java.util.Map context = new HashMap();
		for(int i=0;i<rowSetList.size();i++) {
			createTreeTableNode(dctMetaData,rowSetList.get(i),rootNode,context,colModel);
		}
  	  return rootNode;
	}
	
	/**
	 *
	 * @param dctMetaData DCTMetaData
 	 * @param dataSet EFDataSet
 	 * @param rootNode DataSetTreeTableNode
 	 * @param context Map
 	 * @return DataSetTreeTableNode
 	 * @throws Exception
 	 */
	protected static DataSetTreeTableNode createTreeTableNode(DCTMetaData dctMetaData,ESPRowSet rowSet, DataSetTreeTableNode rootNode, java.util.Map context,ColumnModel colModel) {
		// 生成Node
		DataSetTreeTableNode rowSetNode = DataSetTreeTableNode.getInstance(rowSet);
		rowSetNode.setColModel(colModel);
		// 缓存Node
		putRowSetNode(dctMetaData,rowSet,rowSetNode,context);
		// 得到父Node
		DataSetTreeTableNode parentNode = getParentNode(dctMetaData,rowSet,context,rootNode);
		// 做为子节点增加
		parentNode.add(rowSetNode);
		// 返回
		return rowSetNode;
	}
	
	/**
	 *
	 * @param dctMetaData DCTMetaData
	 * @param rowSet ESPRowSet
	 * @param rowSetNode DataSetTreeTableNode
	 * @param context Map
	 */
	protected static void putRowSetNode(DCTMetaData dctMetaData,ESPRowSet rowSet, DataSetTreeTableNode rowSetNode,java.util.Map context) {
		String nodeCode = getNodeCode(dctMetaData,rowSet);
		context.put(nodeCode,rowSetNode);
	}
	
	/**
	 *
	 * @param dctMetaData DCTMetaData
	 * @param rowSet ESPRowSet
	 * @return String
	 */
	protected static String getNodeCode(DCTMetaData dctMetaData,ESPRowSet rowSet) {
		String DCT_BMCOLID = dctMetaData.getDCT_BMCOLID();
		return rowSet.getString(DCT_BMCOLID,null);
	}
	
	/**
  	 *
  	 * @param parentCode String
  	 * @param context Map
  	 * @param rootNode DataSetTreeTableNode
  	 * @return DataSetTreeTableNode
  	 */
	protected static DataSetTreeTableNode getParentNode(DCTMetaData dctMetaData,ESPRowSet rowSet, java.util.Map context, DataSetTreeTableNode rootNode) {
		String parentCode = getParentCode(dctMetaData,rowSet);
		DataSetTreeTableNode parentNode = (DataSetTreeTableNode)context.get(parentCode);
		if ( parentNode == null ) parentNode = rootNode;
		return parentNode;
	}
	
	/**
	 *
	 * @param dctMetaData DCTMetaData
	 * @param rowSet ESPRowSet
	 * @return String
	 */
	protected static String getParentCode(DCTMetaData dctMetaData,ESPRowSet rowSet) {
		String DCT_BMCOLID = dctMetaData.getDCT_BMCOLID();
		String DCT_JSCOLID = dctMetaData.getDCT_JSCOLID();
		String BMStruct = dctMetaData.getString("DCT_BMSTRU",null);
		String nodeCode = rowSet.getString(DCT_BMCOLID,null);
		int JS = rowSet.getNumber(DCT_JSCOLID,1).intValue();
		if ( JS == 1 || JS == 0 ) return null;
		int Length = StringFunction.GetStructLength(BMStruct, JS - 1);
		String parentCode = nodeCode.substring(0, Length);
		return parentCode;
	}
}
