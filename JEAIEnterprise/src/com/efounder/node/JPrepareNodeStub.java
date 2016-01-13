package com.efounder.node;

import com.efounder.eai.ide.ViewBuilder;
import javax.swing.Icon;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JPrepareNodeStub extends JNodeStub {
  /**
   *
   */
  public JPrepareNodeStub() {
  }
  public String toString() {
    if ( caption != null ) {
      return caption;
    }
    if ( nodeDataStub != null ) {
      return nodeDataStub.toString();
    } else {
      if ( nodeStubObject != null )
        return nodeStubObject.toString();
      else if ( this.getParentNode() != null )
        return this.getParentNode().toString();
      else
        return this.getClass().getName();
    }
  }
  /**
   *
   * @return int
   */
  public final int getOpenPlace() {
    return ViewBuilder.DIALOG_VIEW;
  }
  public Icon          getNodeIcon() {
    if ( nodeIcon != null ) return nodeIcon;
    if ( nodeStubObject != null ) {
      Icon ii = (Icon)this.nodeStubObject.getObject("icon", null);
      if ( ii != null ) return ii;
    }
    if ( this.getParentNode() != null ) return getParentNode().getNodeIcon();
    return null;
  }
  public boolean allowOpenDocument() {return false;}
  public boolean allowOpenHelp() {return false;}
}
