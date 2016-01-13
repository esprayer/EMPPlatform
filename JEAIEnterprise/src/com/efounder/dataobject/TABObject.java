package com.efounder.dataobject;

import com.efounder.dbc.*;
import com.borland.dx.dataset.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TABObject implements CalcFieldsListener {
  protected ClientDataSet TAB_DATA  = null;
  protected String        TAB_ID    = null;
  protected INFObject     infObject  = null;
  /**
   * 创建列
   * @throws Exception
   */
  public static void createColumn(TABObject tabObject) {
    // 如果信息对象为空，直接返回
    if ( tabObject.infObject == null ) return;
    Column[] cols = tabObject.getTAB_DATA().getColumns();
    String colCaption = null;
    for(int i=0;i<cols.length;i++) {
      if ( "ROWID".equals(cols[i].getColumnName()) ) {
        cols[i].setVisible(0);
        continue;
      }
//      colCaption = tabObject.getInfObject().getColCaption(cols[i].getColumnName());
//      if ( colCaption != null )
//        cols[i].setCaption(colCaption);
      // 获取显示状态
      DataRow dataRow = tabObject.getInfObject().getColInfo(cols[i].getColumnName());
      if ( dataRow != null ) {
        String COL_VIEW = dataRow.getString("COL_VIEW");
        if ( COL_VIEW != null && "0".equals(COL_VIEW.trim()) ) {
          cols[i].setVisible(0);
        }
        colCaption = dataRow.getString("COL_MC");
        cols[i].setCaption(colCaption);
      }

    }
    //add by fsz
    if(tabObject.getTAB_DATA().hasColumn(tabObject.getTAB_ID()+"_JNodeStub")!=null)
       return;
     //end
    // 创建NodeStub列
    Column nodeColumn = new Column();
    // 设置为对象类型
    nodeColumn.setDataType(com.borland.dx.dataset.Variant.OBJECT);
    // 名称为JNodeStub，固定名称
    nodeColumn.setColumnName(tabObject.getTAB_ID()+"_JNodeStub");
    // 此列不显示
    nodeColumn.setVisible(0);
    // 增加到DataSet中
    tabObject.getTAB_DATA().addColumn(nodeColumn);
  }
  /**
   *
   * @param tabObject TABObject
   * @param dataRow DataRow
   * @param NodeStub Object
   */
  public static void setNodeStub(TABObject tabObject,DataRow dataRow,Object NodeStub) {
    String columnName = tabObject.getTAB_ID()+"_JNodeStub";
    dataRow.setObject(columnName,NodeStub);
  }
  /**
   *
   * @return String
   */
  public String getTAB_ID() {
    return TAB_ID;
  }
  /**
   *
   * @return DBObject
   */
  public INFObject getInfObject() {
    return infObject;
  }
  /**
   *
   * @return ClientDataSet
   */
  public ClientDataSet getTAB_DATA() {
    return TAB_DATA;
  }
  /**
   *
   */
  protected TABObject() {
  }
  /**
   *
   * @param TAB_ID String
   * @param dataSet ClientDataSet
   * @param infoDataSet1 ClientDataSet
   * @param infoDataSet2 ClientDataSet
   * @return TABObject
   */
  public static TABObject getTabObject(String TAB_ID,TABObject tabObject,ClientDataSet dataSet,
                                ClientDataSet infoDataSet1,ClientDataSet infoDataSet2) {
    // 新建TABObject对象
    if ( tabObject == null )
      tabObject = new TABObject();
    // 对象ID
    tabObject.TAB_ID = TAB_ID;
    // 形成对象的信息对象
    INFObject infObject = tabObject.infObject;
    infObject = INFObject.getInfObject(TAB_ID,infObject,infoDataSet1,infoDataSet2);
    // 传递信息对象
    tabObject.infObject = infObject;
    // tabObject对象的数据
    tabObject.TAB_DATA  = dataSet;
    createColumn(tabObject);
    return tabObject;
  }
  /**
   *
   * @param sourceTable TABObject
   * @param targetTable TABObject
   * @param filedName String
   * @return TABObject
   */
  public static TABObject jionTable(TABObject sourceTable,TABObject targetTable,String filedName) {
    // cloneDataSet的结构
    DataSet sourceDataSet = sourceTable.getTAB_DATA();
    // 获取列数组
    Column[] cols = sourceDataSet.getColumns();
    //
    Column col = null;
    for(int i=0;i<cols.length;i++) {
//      if ( "ROWID".equals(cols[i].getColumnName()) ) {
//        System.out.println(cols[i]);
//      }
      // 如果此列是相关列,则不需处理计算列，只改列标题
      if ( filedName.equals(cols[i].getColumnName()) ) {
        // 获取目标表中的相关列
        Column mainCol = targetTable.getTAB_DATA().getColumn(filedName);
        // 重新设置标题
        mainCol.setCaption(cols[i].getCaption());
      } else {// 如果不是相关列，则需处理为计算列
        // clone一个列
        col = cols[i].cloneColumn();
        // 设置为计算列
        col.setCalcType(com.borland.dx.dataset.CalcType.CALC);
        // 将新建的计算列增加到目标的targetTable中
        targetTable.getTAB_DATA().addColumn(col);
      }
//      System.out.println(cols[i]);
    }
    try {
      targetTable.calcTabObject = sourceTable;
      targetTable.calcFieldName = filedName;
//      targetTable.getTAB_DATA().removeCalcFieldsListener(targetTable);
      targetTable.getTAB_DATA().addCalcFieldsListener(targetTable);
    } catch ( Exception e ) {e.printStackTrace();}
    return targetTable;
  }
  /**
   * 计算表
   */
  protected TABObject calcTabObject = null;
  /**
   * 计算表用到的列
   */
  protected String    calcFieldName = null;
  /**
   * calcFields
   * 计算列
   * @param readRow ReadRow
   * @param dataRow DataRow
   * @param boolean2 boolean
   */
  public void calcFields(ReadRow changedRow, DataRow calcRow, boolean isPosted) {
    if ( calcTabObject == null || calcFieldName == null ) return;
    // 获取lookupRow
    DataRow lookupRow = new DataRow(calcTabObject.getTAB_DATA(), calcFieldName);
    // 返回值Row
    DataRow resultRow = new DataRow(calcTabObject.getTAB_DATA());
    Variant v = new Variant();
    changedRow.getVariant(calcFieldName,v);
    // 传递过来的列必须为
    lookupRow.setVariant(calcFieldName, v);
    // 查找
    if ( calcTabObject.getTAB_DATA().lookup(lookupRow, resultRow, Locate.FIRST) ) {
      Column[] cols = this.getTAB_DATA().getColumns();
      for(int i=0;i<cols.length;i++) {
        if ( cols[i].getCalcType() == com.borland.dx.dataset.CalcType.CALC ) {
          String colName = cols[i].getColumnName();
          resultRow.getVariant (colName,v);
          calcRow.setVariant(colName, v);
        }
      }
    }
  }
  public DataSet getDataSetStruct() {
    return getDataSetStruct(true,null);
  }
  /**
   *
   * @return DataSet
   */
  public DataSet getDataSetStruct(boolean visible,String[] Fileds) {
    DataSet dataSet = this.getTAB_DATA().cloneDataSetStructure();
    Column[] scols  = this.getTAB_DATA().getColumns();
    Column[] dcols  = dataSet.getColumns();
    for(int i=0;i<scols.length;i++) {
      dcols[i].setVisible(scols[i].getVisible());
    }
    if ( visible ) {

    } else {
      for (int i = 0; Fileds != null && i < Fileds.length; i++) {
        Column col = dataSet.getColumn(Fileds[i]);
        col.setVisible(0);
      }
    }
    return dataSet;
  }
}
