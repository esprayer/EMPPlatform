package com.efounder.dbc.swing.render;

import javax.swing.table.*;
import java.util.*;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EnumCellRenderer
    extends DefaultTableCellRenderer {
  private Map enumHash;
  public EnumCellRenderer(Map hash) {
    enumHash=hash;
  }
  protected void setValue(Object value) {
    if(value==null)value="";
    Object o=enumHash.get(value.toString());
      setText((o == null||o.equals("")) ? value.toString() : o.toString());
  }

}
