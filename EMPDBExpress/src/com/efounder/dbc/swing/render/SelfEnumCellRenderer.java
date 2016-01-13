package com.efounder.dbc.swing.render;

import javax.swing.table.*;
import java.util.*;


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
public class SelfEnumCellRenderer
    extends DefaultTableCellRenderer {
  private Map enumHash = null;
  
  public SelfEnumCellRenderer(Map hash) {
    enumHash=hash;
  }
  
  protected void setValue(Object value) {
		if (value == null)	value = "";
		Object o = enumHash.get(value.toString());
		setText((o == null || o.equals("")) ? value.toString() : o.toString());
  }
  
  public void setHash(Map hash){
	  enumHash=hash;
  }
  
  public Map getHash(){
	  return enumHash;
  }
}
