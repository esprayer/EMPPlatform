package com.efounder.eai.ui.action.fileaction;

import com.efounder.eai.ide.ExplorerActionGroup;
import javax.swing.*;
import com.efounder.eai.ide.*;
import com.efounder.pfc.window.*;
import com.efounder.action.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class SaveAllActionGroup extends ExplorerActionGroup {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
  /**
   *
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   * @param f boolean
   */
  public SaveAllActionGroup(String s, char c, String s1, Icon icon,boolean f) {
      super(s, c, s1, icon,f);
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(Object obj) {
    EnterpriseExplorer explorer = (EnterpriseExplorer)obj;
    // ���������ǰ��
    this.removeAll();boolean enable = false;SaveItemExplorerAction sie = null;
    int count = explorer.ContentView.getWindowCount();IWindow win = null;
    SaveAllItemExplorerAction saie = null; boolean isFirst = true;ActionGroup ag = null;
    for(int i=0;i<count;i++) {
      win = explorer.ContentView.getWindow(i);
      if ( win != null && win instanceof FileActionInterface ) {
        enable = ((FileActionInterface)win).canSave() && ((FileActionInterface)win).isModified();
        this.setEnabled(enable);
        if ( enable ) {
          if ( isFirst ) {
            // ����ǵ�һ�Σ�����Ҫ����������ȫ�����˵�
            this.add(SaveAllItemExplorerAction.saveAllItemAction);
            this.setDefaultAction(0);
            // ���������Action��
            ag = new ActionGroup();
            this.add(ag);
            isFirst = false;
          }
          // ����ÿһ������
          sie = new SaveItemExplorerAction(res.getString("KEY11")+win.getTitle(),'0',res.getString("KEY12")+win.getTips(),win.getIcon(),win);
          ag.add(sie);
        }
      }
    }
  }

}
