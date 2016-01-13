package com.efounder.eai.ui.action;

import com.efounder.eai.ide.*;
import com.core.update.*;
import com.efounder.eai.*;
import javax.swing.*;
import com.efounder.node.*;
import com.efounder.eai.ui.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: EFounder</p>
 *
 * @author Skyline
 * @version 1.0
 */
public class SystemViewAction
    extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.Res");
   public SystemViewAction() {
   }
   public void actionPerformed(EnterpriseExplorer browser) {
     JNodeStub rln = new JNodeStub();
      rln.setCaption(res.getString("KEY"));
     rln.setUserObject("SYSVIEW");
//    JGenerViewPanel jvp= (JGenerViewPanel) browser.ExplorerView.getViewComponent();
//     Context ctx = new Context( ((JExplorerView) jvp.getWindow(0)), rln);
     NodeStubUtil.openNodeObject(rln, null);

   }
   public SystemViewAction(String s, char c, String s1, Icon icon)
   {
       super(s, c, s1, icon);
   }

 }
