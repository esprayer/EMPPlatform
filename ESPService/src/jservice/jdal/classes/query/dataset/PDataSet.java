package jservice.jdal.classes.query.dataset;

import java.util.Vector;
import com.borland.dx.dataset.DataSet;

public class PDataSet
    implements IDataSet {

  private static int VALVE_ROW=5000;
  private String OBJ_UUID;

  /**
   * 数据集合
   */
  private Vector dataSet;
  /**
   * 数据结构描述
   */
  private PDataSetStru[] dataStruArray;

  public PDataSet() {
    dataSet = new Vector();
  }

  /**
   * 取数据集
   * @return Vector
   */
  public Vector getAllDataSet() {
    return dataSet;
  }

  /**
   * 取第i行数据
   */
  public Object getDataRow(int i) {
    return getAllDataSet().get(i);
  }
  /**
   * 追加合计行
   * @return String[]
   */
  public Object appendSumRow(String rowFilterExp) {
    return null;
  }

  /**
   * 列运算
   */
  public void updateCol() {
  }

  /**
   * 读(i,j)值
   */
  public Object getValue(int row, int col) {
    return ( (Object[]) getAllDataSet().get(row))[col];
  }

  /**
   * 写(i,j)值
   */
  public void setValue(int row, int col, Object value) {
    ( (Object[]) getAllDataSet().get(row))[col] = value;
  }

  /**
   * 增加行
   */
  public void addDataRow(Object data) {
    getDataSet().add(data);
  }

  /**
   * 增加行
   */
  public void addDataRow(int index, Object data) {
    getDataSet().add(index, data);
  }

  /**
   * 列数目
   */
  public int getColumnCount() {
    return getDataStruArray().length;
  }

  /**
   * 行数目
   */
  public int getRowCount() {
    return getDataSet().size();
  }

  /**
   * 删除所有行
   */
  public void deleteAllRows() {
    getDataSet().clear();
  }

  public void deleteDataRow(int index){
    getDataSet().remove(index);
  }
  /**
   * 是否空
   */
  public boolean isEmpty() {
    return getDataSet().isEmpty();
  }

  public void setDataStruArray(PDataSetStru[] dataStruArray) {
    this.dataStruArray = dataStruArray;
  }

  public PDataSetStru[] getDataStruArray() {
    return this.dataStruArray;
  }

  public Vector getDataSet() {
    return this.dataSet;
  }

  public String getOBJ_UUID() {
    return OBJ_UUID;
  }

  public void setDataSet(Vector dataSet) {
    this.dataSet = dataSet;
  }

  public void setOBJ_UUID(String OBJ_UUID) {
    this.OBJ_UUID = OBJ_UUID;
  }
}
