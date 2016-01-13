package com.efounder.bz.dbform.component;

import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.bz.dbform.datamodel.DMColComponent;

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
public class RowSetValueUtils {
  /**
   *
   */
  protected RowSetValueUtils() {
  }
  /**
   *
   * @param value Object
   * @return String
   */
  public static String getValueCaption(ESPRowSet rowSet,DMColComponent dmColComponent) {
      if ( rowSet != null && dmColComponent != null ) {
        Object value = getObject(rowSet,dmColComponent);
        if ( value == null ) value = "";
        String COL_ID = dmColComponent.getDataSetColID();
        String DCT_ID = dmColComponent.getViewDataSetID();
        String VIEW_COL_ID = dmColComponent.getViewDataSetColID();
        String VALUE_COL_ID = dmColComponent.getValueDataSetColID();
        if ( (VALUE_COL_ID != null && VALUE_COL_ID.trim().length() != 0) ||
             (DCT_ID == null || DCT_ID.trim().length() == 0) ) return value.toString();
        ESPRowSet viewRowSet = rowSet.getID2RowSet(DCT_ID,COL_ID);
        if ( viewRowSet != null )
          return viewRowSet.getString(VIEW_COL_ID,value.toString());
        return rowSet.getID2Name(DCT_ID,COL_ID,value.toString());
      }
      return null;
  }
  /**
   *
   * @param rowSet ESPRowSet
   * @param dmColComponent DMColComponent
   * @param value Object
   */
  public static void putObject(ESPRowSet rowSet,DMColComponent dmColComponent,Object value) {
      if(rowSet==null)return;
    String dataSetID = dmColComponent.getInternalDataSetID();
    String dataSetColID = dmColComponent.getDataSetColID();
    String VALUE_COL_ID = dmColComponent.getValueDataSetColID();
   if (VALUE_COL_ID != null && VALUE_COL_ID.trim().length() != 0)
       dataSetColID=VALUE_COL_ID;

    if ( dmColComponent.isUserInternalDataSetID() ) {
      rowSet.putObject(dataSetID+"."+dataSetColID,value);
    } else {
      rowSet.putObject(dataSetColID,value);
    }
  }
  /**
   *
   * @param rowSet EFRowSet
   * @param dataSetID String
   * @param dataSetColID String
   * @return Object
   */
  public static Object getObject(ESPRowSet rowSet,String dataSetID,String dataSetColID) {
    Object value = rowSet.getObject(dataSetColID,null);
    if ( value == null )
      value = rowSet.getObject(dataSetID+"."+dataSetColID,null);
    return value;
  }
  /**
   *
   * @param rowSet ESPRowSet
   * @param dmColComponent DMColComponent
   * @return Object
   */
  public static Object getObject(ESPRowSet rowSet,DMColComponent dmColComponent) {
    String dataSetID = dmColComponent.getInternalDataSetID();
    String dataSetColID = dmColComponent.getDataSetColID();
    String VALUE_COL_ID = dmColComponent.getValueDataSetColID();
    if (VALUE_COL_ID != null && VALUE_COL_ID.trim().length() != 0)
        dataSetColID=VALUE_COL_ID;
    if ( dmColComponent.isUserInternalDataSetID() ) {
      Object oo= rowSet.getObject(dataSetID+"."+dataSetColID,null);
      if(oo==null)oo=rowSet.getObject(dataSetColID,null);
      return oo;
    } else {
      return rowSet.getObject(dataSetColID,null);
    }

  }
}

