// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name:   ExplorerToolBarPane.java

package com.efounder.eai.ide;

import com.efounder.action.ActionGroup;
import com.efounder.action.ActionToolBarPane;
import java.awt.Graphics;
import java.util.ArrayList;

// Referenced classes of package com.efounder.eai.ide:
//            EnterpriseExplorer

public class ExplorerToolBarPane extends ActionToolBarPane
{

    private int Index;

//    public ExplorerToolBarPane(EnterpriseExplorer browser, int index) {
//      this(browser,index,
//    }
    public ExplorerToolBarPane(EnterpriseExplorer browser, int index,String id)
    {
        super(id,browser);
        Index = 0;
        Index = index;
        EnterpriseExplorer _tmp = browser;
        if ( index != -1 ) {
          ActionGroup aactiongroup[] = EnterpriseExplorer.getToolBarGroups(
              Index);
          for (int i = 0; i < aactiongroup.length; i++)
            addGroup(aactiongroup[i]);
          if (groups != null && groups.size() > 0) {
            browser.showToolbar(Index, true);
          }
          else {
            browser.showToolbar(Index, false);
          }
        }
        rebuild();
    }

//    public void addGroup(ActionGroup actiongroup)
//    {
//        if(groups.contains(actiongroup))
//        {
//            return;
//        } else
//        {
//            groups.add(actiongroup);
//            rebuild();
//            return;
//        }
//    }

    public EnterpriseExplorer getExplorer()
    {
        return (EnterpriseExplorer)super.getSource();
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
