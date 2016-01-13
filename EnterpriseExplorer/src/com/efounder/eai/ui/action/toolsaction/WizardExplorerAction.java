package com.efounder.eai.ui.action.toolsaction;

import com.efounder.eai.ide.*;
import com.efounder.node.view.*;
import com.efounder.pfc.window.*;
import com.efounder.service.script.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class WizardExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.toolsaction.Res");
  public static WizardExplorerAction wizardAction = new WizardExplorerAction(res.getString("KEY4"),'0',"",ExplorerIcons.getExplorerIcon("oicons/surround_with.png"));
  public WizardExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
    super(s, c, s1, icon);
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof Scriptable ) {
      editScript((Scriptable)win);
    } else if ( win instanceof NodeWindow ) {
      NodeViewer nodeViewer = ((NodeWindow)win).getActiveNodeViewer();
      if ( nodeViewer instanceof Scriptable ) {
        editScript((Scriptable)nodeViewer);
      }
    }
  }
  /**
   *
   * @param script Scriptable
   */
  protected void editScript(Scriptable script) {
    ScriptObject scObject = script.getScriptObject();
//    ScriptEditNode editNode = new ScriptEditNode();
//    Context ctx = new Context(null,editNode);
//    ctx.setObject("SCRIPTOBJECT",scObject);
//    NodeStubUtil.openNodeObject(editNode,null,ctx);
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof Scriptable ) {
      setEnabled ( true );
    } else if ( win instanceof NodeWindow ) {
      NodeViewer nodeViewer = ((NodeWindow)win).getActiveNodeViewer();
      if ( nodeViewer instanceof Scriptable ) {setEnabled ( true ); } else {setEnabled(false);}
    } else {
      setEnabled(false);
    }
  }


}
