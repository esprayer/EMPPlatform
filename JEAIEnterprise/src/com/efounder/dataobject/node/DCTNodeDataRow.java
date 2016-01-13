package com.efounder.dataobject.node;

import com.efounder.dataobject.*;
import com.borland.dx.dataset.*;
import javax.swing.*;
import com.efounder.node.*;
import com.efounder.dataobject.view.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface DCTNodeDataRow {
  /**
   *
   * @return DCTObject
   */
  public DCTObject getDCTObjectDataRow(String DCT_TYPE);
  /**
   *
   * @return DataSet
   */
  public DataSet   getDataSet(String DCT_TYPE);
  /**
   *
   * @param dataRow DataRow
   * @return Icon
   */
  public Icon      getIcon(String DCT_TYPE,Object value,DataSet dataSet, int row, int column);
  /**
   *
   * @return String
   */
  public String    getText(String DCT_TYPE);
  public Icon      getIcon(String DCT_TYPE);
  /**
   *
   * @param dataRow DataRow
   * @return int
   */
  public int       getLevel(String DCT_TYPE,Object value,DataSet dataSet, int row, int column);
  /**
   *
   * @param DCT_TYPE String
   * @param dataSet DataSet
   * @param dataRow DataRow
   * @return Context
   */
  public Context getDataRowContext(String DCT_TYPE,DataSet dataSet,DataRow[] dataRows,int[] Rows,DCTView dctView);
}
