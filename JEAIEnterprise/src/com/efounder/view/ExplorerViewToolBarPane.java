package com.efounder.view;

import com.efounder.action.ActionToolBarPane;
import com.efounder.action.*;
import java.awt.*;
import com.core.xml.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ExplorerViewToolBarPane extends ActionToolBarPane {
  public ExplorerViewToolBarPane(Object ExplorerView) {
    super(ExplorerView);
    ActionGroup aactiongroup[] = (ActionGroup[])JBOFClass.CallObjectMethod(ExplorerView,"getToolBarGroups");
//    ActionGroup aactiongroup[] = ExplorerView.getToolBarGroups();
    if ( aactiongroup != null ) {
      for (int i = 0; i < aactiongroup.length; i++)
        addGroup(aactiongroup[i]);
      if (groups != null && groups.size() > 0) {
        this.setVisible(true);
//        ExplorerView.showToolbar(true);
      }
      else {
        this.setVisible(true);
//        ExplorerView.showToolbar(false);
      }
      rebuild();
    }

  }

//  public void addGroup(ActionGroup actiongroup)
//  {
//      if(groups.contains(actiongroup))
//      {
//          return;
//      } else
//      {
//          groups.add(actiongroup);
//          rebuild();
//          return;
//      }
//  }

  public JComponent getExplorer()
  {
      return (JComponent)super.getSource();
  }

  public void paintComponent(Graphics g)
  {
      super.paintComponent(g);
  }

  protected void rebuildComplete(boolean flag)
  {
      getExplorer().validate();
  }

  public void setToolBarVisible(ActionGroup actiongroup1, boolean flag1)
  {
  }

}
