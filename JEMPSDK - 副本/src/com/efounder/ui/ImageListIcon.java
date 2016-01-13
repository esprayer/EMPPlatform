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
import javax.swing.Icon;

public class ImageListIcon
    implements Icon
{

    private int a;
    private int b;
    private int d;
    private Image c;

    public int getIconHeight()
    {
        return d;
    }

    public int getIconWidth()
    {
        return d;
    }

    public void paintIcon(Component component, Graphics g, int i, int j)
    {
        if(c != null)
            g.drawImage(c, i, j, i + d, j + d, a, 0, a + d, d, component);
    }

    public int getOffset()
    {
        return a;
    }

    public int getIndex()
    {
        return b;
    }

    public Image getImage()
    {
        return c;
    }

    public ImageListIcon(Image image, int i, int j)
    {
        c = image;
        d = i;
        b = j;
        a = i * j;
    }
}
