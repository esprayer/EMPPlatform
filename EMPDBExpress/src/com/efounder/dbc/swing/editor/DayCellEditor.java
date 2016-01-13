package com.efounder.dbc.swing.editor;

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
public class DayCellEditor extends MonthCellEditor{
  public DayCellEditor() {
    super();
  }
  protected String[] getData(){
    String[] data = new String[31];
    for(int i = 1; i <= data.length; i++){
      if(i < 10){
        data[i-1] = "0" + i;
      }
      else{
        data[i-1] = "" + i;
      }
    }
    return data;
  }

}
