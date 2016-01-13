package com.efounder.bz.dbform.component.dc.table.render;

import java.awt.*;
import javax.swing.*;

import org.jdesktop.swingx.renderer.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.column.*;
import com.efounder.dctbuilder.data.*;

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
public class RowSetValueRenderer extends LabelProvider {
  /**
   *
   */
  public RowSetValueRenderer(TableCellRendererManager tableCellRendererManager) {
    super(new RowSetValueConverter(tableCellRendererManager));
    this.tableCellRendererManager = tableCellRendererManager;
    titleIcon = tableCellRendererManager;
  }
  protected TitleIcon titleIcon = null;
  /**
   *
   */
  protected TableCellRendererManager tableCellRendererManager = null;
  /**
   *
   * @param context CellContext
   */
  protected void configureState(CellContext context) {
    RowSetValue rowSetValue = null;
    if ( context.getValue() instanceof RowSetValue ) {
      rowSetValue = (RowSetValue)context.getValue();
      Column column = rowSetValue.getColumn();
      String editType = RowSetValueConverter.getColEditType(rowSetValue);
      if(!context.isSelected()&&!column.isEditable()||column.getMaxWidth()==0)
        rendererComponent.setBackground(Color.lightGray);
      if (String.valueOf(ColumnMetaData.EL_BOOL).equals(editType)) {//bool type
          rendererComponent.setHorizontalAlignment(SwingConstants.CENTER);return;
      }
      if ( column.isCheckBoxColumn() ) {
        rendererComponent.setHorizontalAlignment(SwingConstants.CENTER);return;
      }
      String colType = RowSetValueConverter.getColType(rowSetValue);
      //add by fsz 有时候用vecid带出来的是数据值的
      if(column.getDimSetRowSet()!=null&&column.getDimSetRowSet().getString("COL_TYPE", "").trim().length()>0)
    	  colType=column.getDimSetRowSet().getString("COL_TYPE", "");
      if(column.getValSetRowSet()!=null&&column.getValSetRowSet().getString("COL_TYPE", "").trim().length()>0)
    	  colType=column.getValSetRowSet().getString("COL_TYPE", "");
      if ( "N".equals(colType) ){
          Object v = rowSetValue.getCellValue();
          if (v != null) {
              if ((v instanceof String && v.toString().startsWith("-")) ||
                  v instanceof Number && ((Number) v).doubleValue() < 0)
                  rendererComponent.setForeground(Color.red);
          }
      }

      int colHA = column.getHorizontalAlignment();
      if ( colHA == -1 ) {
        if ("C".equals(colType))
          rendererComponent.setHorizontalAlignment(SwingConstants.LEFT);
        else if ( "D".equals(colType) || "T".equals(colType) ) {
          rendererComponent.setHorizontalAlignment(SwingConstants.CENTER);
        }
      } else if ( "N".equals(colType) ){
          rendererComponent.setHorizontalAlignment(SwingConstants.RIGHT);
      }else {
        rendererComponent.setHorizontalAlignment(column.getHorizontalAlignment());
      }
    } else
      rendererComponent.setHorizontalAlignment(getHorizontalAlignment());

    if ( titleIcon != null ) {
      titleIcon.confing(rendererComponent,rowSetValue,0);
    }
  }
}
