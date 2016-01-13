package com.efounder.dataobject.action;

import com.borland.dx.dataset.*;
import java.awt.event.*;
import javax.swing.*;
import com.efounder.action.*;
import com.efounder.dataobject.view.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

abstract public class DCTViewAction extends UpdateAction {
  OBJView ExplorerView = null;

  public abstract void actionPerformed(OBJView ExplorerView,DataRow[] DataRowArray);

  public final void doPerformed(ActionEvent actionevent)
  {
    DataRow[] NodeArray = null;
    NodeArray = ExplorerView.getSelectNodes();
    if(ExplorerView != null && NodeArray != null )
      actionPerformed(ExplorerView,NodeArray);
  }

  public void doUpdate(OBJView ExplorerView)
  {
      super.doUpdate(ExplorerView);
  }

  public final void doUpdate(Object obj)
  {
    if(ExplorerView != null)
      doUpdate(ExplorerView);
  }

  public DCTViewAction(OBJView ExplorerView,String s, char c, String s1, Icon icon, Icon icon1)
  {
      super(s, c, s1, icon, icon1);
      this.ExplorerView = ExplorerView;
  }

  public DCTViewAction(OBJView ExplorerView,String s, char c, String s1, Icon icon, String s2)
  {
    super(s, c, s1, icon, s2);
      this.ExplorerView = ExplorerView;
  }

  public DCTViewAction(OBJView ExplorerView,String s, char c, String s1, Icon icon)
  {
    super(s, c, s1, icon);
      this.ExplorerView = ExplorerView;
  }

  public DCTViewAction(OBJView ExplorerView,String s, char c, String s1)
  {
    super(s, c, s1);
      this.ExplorerView = ExplorerView;
  }

  public DCTViewAction(OBJView ExplorerView,String s, char c)
  {
    super(s, c);
      this.ExplorerView = ExplorerView;
  }

  public DCTViewAction(OBJView ExplorerView,String s)
  {
    super(s);
      this.ExplorerView = ExplorerView;
  }

  public DCTViewAction(OBJView ExplorerView)
  {
      this.ExplorerView = ExplorerView;
  }

}
