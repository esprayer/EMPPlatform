package com.efounder.ui;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */


import java.awt.*;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class EdgeBorder
    implements Border
{

    private Insets a;
    private int b;
    public static final int EDGE_RIGHT = 61440;
    public static final int EDGE_BOTTOM = 3840;
    public static final int EDGE_LEFT = 240;
    public static final int EDGE_TOP = 15;
    public static final int EDGE_ALL = 65535;

    public boolean isBorderOpaque()
    {
        return true;
    }

    public Insets getBorderInsets(Component component)
    {
        return a;
    }

    public void paintBorder(Component component, Graphics g, int i, int j, int k, int l)
    {
        java.awt.Color color = UIManager.getColor("Separator.shadow");
        java.awt.Color color1 = UIManager.getColor("Separator.highlight");
        java.awt.Color color2 = g.getColor();
        if((b & 0xf) != 0)
        {
            g.setColor(color);
            g.drawLine(i, j, (i + k) - 1, j);
            g.setColor(color1);
            g.drawLine(i, j + 1, (i + k) - 1, j + 1);
        }
        if((b & 0xf0) != 0)
        {
            g.setColor(color);
            g.drawLine(i, j, i, (j + l) - 1);
            g.setColor(color1);
            g.drawLine(i + 1, j, i + 1, (j + l) - 1);
        }
        if((b & 0xf00) != 0)
        {
            g.setColor(color);
            g.drawLine(i, (j + l) - 2, (i + k) - 1, (j + l) - 2);
            g.setColor(color1);
            g.drawLine(i, (j + l) - 1, (i + k) - 1, (j + l) - 1);
        }
        if((b & 0xf000) != 0)
        {
            g.setColor(color);
            g.drawLine((i + k) - 2, j, (i + k) - 2, (j + l) - 1);
            g.setColor(color1);
            g.drawLine((i + k) - 1, j, (i + k) - 1, (j + l) - 1);
        }
        g.setColor(color2);
    }

    public EdgeBorder(int i)
    {
        b = 65535;
        a = new Insets(2, 2, 2, 2);
        b = i;
        recalcInsets();
    }

    public EdgeBorder()
    {
        b = 65535;
        a = new Insets(2, 2, 2, 2);
    }

    protected void recalcInsets()
    {
        a.top = (b & 0xf) == 0 ? 0 : 2;
        a.left = (b & 0xf0) == 0 ? 0 : 2;
        a.bottom = (b & 0xf00) == 0 ? 0 : 2;
        a.right = (b & 0xf000) == 0 ? 0 : 2;
    }


}
