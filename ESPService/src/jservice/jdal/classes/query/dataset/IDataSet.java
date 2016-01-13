package jservice.jdal.classes.query.dataset;

import java.util.Vector;

public interface IDataSet {
  /**
   * 取数据集
   * @return Vector
   */
  public Vector getAllDataSet();

  /**
   * 取第i行数据
   */
  public Object getDataRow(int i);

  /**
   * 追加合计行
   * @return String[]
   */
  public Object appendSumRow(String rowFilterExp);

  /**
   * 列运算
   */
  public void updateCol();

  /**
   * 读(i,j)值
   */
  public Object getValue(int row, int col);

  /**
   * 写i,j)值
   */
  public void setValue(int row, int col, Object value);

  /**
   * 增加行
   */
  public void addDataRow(Object data);
  /**
   * 增加行
   */
  public void addDataRow(int index,Object data);
  /**
   * 列数目
   */
  public int getColumnCount();
  /**
   * 行数目
   */
  public int getRowCount();
  /**
   * 删除所有行
   */
  public void deleteAllRows();
  public void deleteDataRow(int index);
  /**
   * 是否空
   */
  public boolean isEmpty();
}
