package com.efounder.bz.dbform.component.dc.combobox;

import javax.swing.Icon;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.bz.dbform.datamodel.DMComponent;
import com.efounder.bz.dbform.datamodel.DMColComponent;
import com.efounder.pfc.swing.JIConListCellRenderer;

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
public class RowSetListCellRenderer extends JIConListCellRenderer {
  /**
   *
   */
  protected DMComponent dmComponent = null;
  /**
   *
   */
  protected DMColComponent dmColComponent = null;
  /**
   *
   * @param icon Icon
   */
  public RowSetListCellRenderer(Icon icon,DMComponent dmComponent,DMColComponent dmColComponent) {
    super(icon);
    this.dmComponent = dmComponent;
    this.dmColComponent = dmColComponent;
  }
  /**
   *
   * @param value Object
   * @return String
   */
  protected String getValueCaption(Object value) {
    if ( value instanceof ESPRowSet ) {
      if ( dmColComponent != null ) {
        ESPRowSet rowSet = (ESPRowSet)value;
        String dataSetColID = dmColComponent.getDataSetColID();
        return rowSet.getString(dataSetColID,null);
      } else {
        return null;
      }
    } else {
      ESPRowSet rowSet = dmComponent.getMainRowSet();
      if ( rowSet != null && dmColComponent != null ) {
        String COL_ID = dmColComponent.getDataSetColID();
        String DCT_ID = dmColComponent.getViewDataSetID();
        String VIEW_COL_ID = dmColComponent.getViewDataSetColID();
        String VALUE_COL_ID = dmColComponent.getValueDataSetColID();
        if ( VALUE_COL_ID != null && VALUE_COL_ID.trim().length() != 0){
          return rowSet.getString(VALUE_COL_ID,"");
        }
        if(DCT_ID == null || DCT_ID.trim().length() == 0 ) return value.toString();
        ESPRowSet viewRowSet = rowSet.getID2RowSet(DCT_ID,COL_ID);
        if ( viewRowSet != null )
          return viewRowSet.getString(VIEW_COL_ID,value.toString());
        return rowSet.getID2Name(DCT_ID,COL_ID,value.toString());
      }
      return value.toString();
    }
  }

}
