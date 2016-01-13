// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name:   ExplorerAdapter.java

package com.efounder.eai.ide;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import com.efounder.eai.*;
import com.efounder.service.security.ServiceSecurityManager;

public class ExplorerAdapter extends WindowAdapter
{
  protected JFrame frame = null;
  public void setFrame(JFrame frame) {
    this.frame = frame;
  }
    public ExplorerAdapter()
    {

    }

    public void windowClosed(WindowEvent e)
    {
        SystemExitAdapter.execute(EAI.EA);
    }

    public void windowClosing(WindowEvent e)
    {
      boolean isExit = SystemExitAdapter.execute(EAI.EA);
      if(frame==null)frame=(JFrame)e.getComponent();
      if ( frame != null )
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      if ( isExit ) {
        EAI.putEnv("CLOSEALL","1");
        if ( EnterpriseExplorer.closeAllWindow() ) {
          if ( frame != null )
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          try {
            ServiceSecurityManager.getDefault().Logoff(frame, null, null, null);
          }
          catch (Exception ex) {
          }
          System.exit(0);
        }
      }
//      EnterpriseExplorer.getExplorer().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//      if ( EnterpriseExplorer.closeAllWindow() ) {
//        EnterpriseExplorer.getExplorer().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        windowClosed(e);
//        System.exit(0);
//      }
//      else {
//        EnterpriseExplorer.getExplorer().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//      }
    }
}
