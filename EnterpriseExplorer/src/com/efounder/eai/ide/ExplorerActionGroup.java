// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExplorerActionGroup.java

package com.efounder.eai.ide;

import com.efounder.action.ActionGroup;
import javax.swing.Icon;

public class ExplorerActionGroup extends ActionGroup
{

    public ExplorerActionGroup()
    {
    }

    public ExplorerActionGroup(String s)
    {
        super(s);
    }

    public ExplorerActionGroup(String s, char c)
    {
        super(s, c);
    }

    public ExplorerActionGroup(String s, char c, boolean flag)
    {
        super(s, c);
        super.putValue("Popup", new Boolean(flag));
    }

    public ExplorerActionGroup(String s, char c, String s1)
    {
        super(s, c, s1);
    }

    public ExplorerActionGroup(String s, char c, String s1, Icon icon)
    {
        super(s, c, s1, icon);
    }

    public ExplorerActionGroup(String s, char c, String s1, Icon icon, String s2)
    {
        super(s, c, s1, icon);
        setAltShortText(s2);
    }

    public ExplorerActionGroup(String s, char c, String s1, Icon icon, boolean flag)
    {
        super(s, c, s1, icon);
        super.putValue("Popup", new Boolean(flag));
    }

    public ExplorerActionGroup(String s, char c, String s1, Icon icon, boolean flag, String s2)
    {
        super(s, c, s1, icon);
        super.putValue("Popup", new Boolean(flag));
        setAltShortText(s2);
    }
}
