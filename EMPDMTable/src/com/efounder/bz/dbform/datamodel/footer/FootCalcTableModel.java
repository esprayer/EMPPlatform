package com.efounder.bz.dbform.datamodel.footer;

import com.efounder.builder.base.data.*;
import java.util.*;
import com.efounder.bz.dbform.datamodel.column.*;
import com.efounder.bz.dbform.datamodel.ColumnModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import java.math.*;

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
//1.知道columnModel 2.知道dataset
public class FootCalcTableModel extends GroupTableModel implements
    DataSetListener,TableColumnModelListener{
  private EFDataSet dataSet;
  private int sumRow=-1;
  private int avgRow=-1;
  private int maxRow=-1;
  private int minRow=-1;
  public FootCalcTableModel() {
  }
  public boolean isCellEditable(int row, int column) {
      return false;
  }
  public void setDataSet(EFDataSet ds) {
    if(dataSet!=ds){
      if(dataSet!=null)
        dataSet.removeDataSetListener(this);
      this.dataSet = ds;
      if(dataSet!=null){
    	  this.calcAllColumn();
    	  dataSet.addDataSetListener(this);
      }
    }
  }

  public void setSumRow(int sumRow) {
    this.sumRow = sumRow;
  }

  public void setAvgRow(int avgRow) {
    this.avgRow = avgRow;
  }

  public void setMaxRow(int maxRow) {
    this.maxRow = maxRow;
  }

  public void setMinRow(int minRow) {
    this.minRow = minRow;
  }

  public EFDataSet getDataSet() {
    return dataSet;
  }

  public int getSumRow() {
    return sumRow;
  }

  public int getAvgRow() {
    return avgRow;
  }

  public int getMaxRow() {
    return maxRow;
  }

  public int getMinRow() {
    return minRow;
  }
  public void calcColumn(){
    int count=columnModel.getColumnCount();
    for(int i=0;i<count;i++){
      Column  tce = columnModel.getColumnByModelIndex(i);
      String  columnid = tce.getDataSetColID();
      if(tce.isUserInternalDataSetID()){
        columnid=tce.getInternalDataSetID()+"."+columnid;
      }
      calcColumn(columnid);
    }
  }
  public void calcAllColumn(){
    int count=columnModel.getColumnCount();
    for(int i=0;i<count;i++){
      Column cn=columnModel.getColumnByModelIndex(i);
      if(cn==null||cn.getDataSetColID() == null)continue;
      calcColumn(cn.getDataSetColID());
    }
  }
  protected void calcColumn(String colid){
    Map map=columnModel.getColumnMap();
    String cc=colid;
    if(cc.indexOf(".")!=-1){
      cc=cc.substring(cc.indexOf(".")+1);
    }
    Column col=(Column) map.get(cc);
    if(col==null)return;
    if(col.isUserInternalDataSetID()&&colid.indexOf(".")==-1){
        colid=col.getInternalDataSetID()+"."+colid;
    }
    sumColumn(col,colid);
    avgColumn(col,colid);
    maxColumn(col,colid);
    minColumn(col,colid);
  }
  protected void sumColumn(Column col,String colid){
    if(col.isCalcSum()&&sumRow!=-1){
       String decn=(String)col.getProperty("DECN","2");
       double sum=footerCalcUtil.sumColumn(dataSet,colid,decn);
       int index=col.getModelIndex();
       this.setValueAt(String.valueOf(sum),sumRow,index);
    }
  }
  protected void avgColumn(Column col,String colid){
    if (col.isCalcAvg() && avgRow != -1) {
      String decn=(String)col.getProperty("DECN","2");
      double sum = footerCalcUtil.avgColumn(dataSet, colid,decn);
      int index = col.getModelIndex();
      this.setValueAt(String.valueOf(sum), avgRow, index);
    }
  }
  protected void maxColumn(Column col,String colid){
    if(col.isCalcMax()&&maxRow!=-1){
      String decn=(String)col.getProperty("DECN","2");
        double sum=footerCalcUtil.maxColumn(dataSet,colid,decn);
        int index=col.getModelIndex();
        this.setValueAt(String.valueOf(sum),maxRow,index);
    }
  }
  public BigDecimal getCalcValue(int row,String colid){
      Map map=columnModel.getColumnMap();
      Column col=(Column) map.get(colid);
      if(col==null)return BigDecimal.ZERO;
      String decn=(String)col.getProperty("DECN","2");
      if(col!=null){
        int index=col.getModelIndex();
        String value=(String) getValueAt(row,index);
        double dd;
        try{
             dd = JFNumber.round(Double.parseDouble(value),
                                       Integer.parseInt(decn));
        }catch(Exception e){
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(dd);
      }
      return BigDecimal.ZERO;
  }
  protected void minColumn(Column col,String colid){
    if(col.isCalcMin()&&minRow!=-1){
      String decn=(String)col.getProperty("DECN","2");
        double sum=footerCalcUtil.minColumn(dataSet,colid,decn);
        int index=col.getModelIndex();
        this.setValueAt(String.valueOf(sum),minRow,index);
    }
  }

  public void dataSetChanged(DataSetEvent e) {
    if (e.getEventType() != DataSetEvent.UPDATE&&
         e.getEventType() != DataSetEvent.DELETE   ) {

      return;
    }
    String col=e.getUpdateFieldName();
    if(col==null)
        calcAllColumn();
    else
        calcColumn(col);
  }
  public void setColumnModel(ColumnModel columnModel) {
    if(this.getColumnModel()!=null)
      getColumnModel().removeColumnModelListener(this);
      super.setColumnModel(columnModel);
      if(this.getColumnModel()!=null)
      getColumnModel().addColumnModelListener(this);
    }

  public void columnAdded(TableColumnModelEvent e) {
    if(this.getColCount()!=getColumnModel().getColumnCount()){
        this.setColCount(getColumnModel().getColumnCount());
        columnModel.getColumnMap().clear();
        calcAllColumn();
    }
  }

  public void columnRemoved(TableColumnModelEvent e) {
  }

  public void columnMoved(TableColumnModelEvent e) {
  }

  public void columnMarginChanged(ChangeEvent e) {
  }

  public void columnSelectionChanged(ListSelectionEvent e) {
  }
}
