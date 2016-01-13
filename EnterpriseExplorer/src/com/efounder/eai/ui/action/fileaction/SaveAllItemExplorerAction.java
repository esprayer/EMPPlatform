package com.efounder.eai.ui.action.fileaction;

import com.efounder.eai.ide.*;
import javax.swing.*;
import com.efounder.pfc.window.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class SaveAllItemExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
  public static SaveAllItemExplorerAction saveAllItemAction = new SaveAllItemExplorerAction(res.getString("KEY8"),'0',res.getString("KEY9"),ExplorerIcons.ICON_SAVEALL);
  /**
   *
   */
  public SaveAllItemExplorerAction() {

  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    boolean enable = false;
    int count = explorer.ContentView.getWindowCount();IWindow win = null;
    for(int i=0;i<count;i++) {
      win = explorer.ContentView.getWindow(i);
      if ( win != null && win instanceof FileActionInterface ) {
        enable = ((FileActionInterface)win).canSave() && ((FileActionInterface)win).isModified();
        if ( enable ) {
          try {((FileActionInterface)win).saveNode();} catch ( Exception e ) {}
//          ((FileActionInterface)win).saveNode();
        }
      }
    }
  }
  /**
   *
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   */
  public SaveAllItemExplorerAction(String s, char c, String s1, Icon icon) {
      super(s, c, s1, icon);
      setWaitInvoke(false);
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer) {
    // ���������ǰ��
    boolean enable = false;
    int count = explorer.ContentView.getWindowCount();IWindow win = null;
    for(int i=0;i<count;i++) {
      win = explorer.ContentView.getWindow(i);
      if ( win != null && win instanceof FileActionInterface ) {
        enable = ((FileActionInterface)win).canSave() && ((FileActionInterface)win).isModified();
        if ( enable ) {
          this.setEnabled(true);return;
        }
      }
    }
    this.setEnabled(false);
  }

}
