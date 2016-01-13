package com.efounder.dataobject.action;

import com.efounder.dataobject.view.*;
import com.borland.dx.dataset.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTObjectAction extends DCTViewAction {
  /**
   *
   * @param ExplorerView DCTView
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   */
  public DCTObjectAction(OBJView ExplorerView,String s, char c, String s1, Icon icon) {
    super(ExplorerView,s, c, s1, icon);
  }
  /**
   *
   * @param parm1 DCTView
   * @param parm2 DataRow[]
   */
  public void actionPerformed(OBJView parm1, DataRow[] parm2) {

  }
}
