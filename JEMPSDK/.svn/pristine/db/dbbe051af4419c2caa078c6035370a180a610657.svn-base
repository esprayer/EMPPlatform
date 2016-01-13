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
import javax.swing.border.Border;

public class LowBorder
    implements Border
{

    Insets a;

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
        g.setColor(SystemColor.controlShadow);
        g.drawLine(i, j, (i + k) - 2, j);
        g.drawLine(i, j + 1, i, (j + l) - 2);
        g.setColor(SystemColor.controlLtHighlight);
        g.drawLine(i, (j + l) - 1, (i + k) - 1, (j + l) - 1);
        g.drawLine((i + k) - 1, j, (i + k) - 1, (j + l) - 2);
    }

    public LowBorder()
    {
        a = new Insets(1, 3, 1, 3);
    }
}
