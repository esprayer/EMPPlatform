// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name:   ExplorerMenuBar.java

package com.efounder.eai.ide;

import com.efounder.action.ActionMenuBar;

// Referenced classes of package com.efounder.eai.ide:
//            EnterpriseExplorer

public class ExplorerMenuBar extends ActionMenuBar
{

    public ExplorerMenuBar(EnterpriseExplorer EExplorer,String id)
    {
        super(EExplorer,id);
        com.efounder.action.ActionGroup aactiongroup[] = EnterpriseExplorer.getMenuGroups();
        for(int i = 0; i < aactiongroup.length; i++)
            addGroup(aactiongroup[i]);

    }
}
