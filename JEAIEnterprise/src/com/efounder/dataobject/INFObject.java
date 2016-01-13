package com.efounder.dataobject;

import com.efounder.dbc.*;
import com.efounder.db.*;
import com.borland.dx.dataset.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class INFObject {
  protected String OBJ_ID = null;
  protected ClientDataSet SYS_OBJECTS = null;
  protected ClientDataSet SYS_OBJCOLS = null;
  /**
   *
   * @param COL_ID String
   * @return ReadRow
   */
  public DataRow getColInfo(String COL_ID) {
    if ( SYS_OBJCOLS == null ) return null;
    SYS_OBJCOLS.first();
    if ( SYS_OBJCOLS.getRowCount() <= 0 ) return null;
    DataRow dataRow = new DataRow(SYS_OBJCOLS);
    do {
      SYS_OBJCOLS.getDataRow(dataRow);
      if ( COL_ID.equals(dataRow.getString("COL_ID")) ) return dataRow;
    } while ( SYS_OBJCOLS.next() );

    return null;
  }
  /**
   *
   * @param COL_ID String
   * @return String
   */
  public String getColCaption(String COL_ID) {
    DataRow dataRow = getColInfo(COL_ID);
    if ( dataRow == null ) return null;
    return dataRow.getString("COL_MC");
  }
  /**
   *
   * @param SYS_OBJCOLS ClientDataSet
   */
  public void setSYS_OBJCOLS(ClientDataSet SYS_OBJCOLS) {
    this.SYS_OBJCOLS = SYS_OBJCOLS;
  }
  /**
   *
   * @param SYS_OBJECTS ClientDataSet
   */
  public void setSYS_OBJECTS(ClientDataSet SYS_OBJECTS) {
    this.SYS_OBJECTS = SYS_OBJECTS;
  }
  /**
   *
   */
  protected INFObject() {
  }
  /**
   *
   * @return String
   */
  public String getOBJ_ID() {
    return OBJ_ID;
  }
  /**
   *
   * @return ClientDataSet
   */
  public ClientDataSet getSYS_OBJECTS() {
    return SYS_OBJECTS;
  }
  /**
   *
   * @return ClientDataSet
   */
  public ClientDataSet getSYS_OBJCOLS() {
    return SYS_OBJCOLS;
  }
  /**
   *
   * @param OBJ_ID String
   * @param cds1 ClientDataSet
   * @param cds2 ClientDataSet
   * @return DBObject
   */
  static INFObject getInfObject(String OBJ_ID,INFObject infObject,ClientDataSet cds1,ClientDataSet cds2) {
    if ( infObject == null )
      infObject = new INFObject();
    infObject.OBJ_ID = OBJ_ID;
    infObject.SYS_OBJECTS = cds1;
    infObject.SYS_OBJCOLS = cds2;
    return infObject;
  }
  /**
   *
   * @param dataSetdata DataSetData
   * @return ClientDataSet
   */
  public static ClientDataSet createClientDataSet(ClientDataSet cds,DataSetData dataSetdata) {
    if ( cds == null )
      cds = new ClientDataSet();
    else
      cds.empty(); // 清空
    dataSetdata.loadDataSet(cds);
    return cds;
  }
}
