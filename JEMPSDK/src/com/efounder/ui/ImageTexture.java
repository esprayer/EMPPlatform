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
import java.io.Serializable;

public class ImageTexture
    implements Serializable
{

    public static void texture(Image image, int i, int j, Graphics g, int k, int l, int i1, int j1,
            int k1, int l1)
    {
        int i2 = image.getWidth(null);
        int j2 = image.getHeight(null);
        if(i2 < 1 || j2 < 1)
            return;
        int k2 = i <= 0 ? i2 : i;
        int l2 = j <= 0 ? j2 : j;
        Rectangle rectangle = g.getClipBounds();
        g.clipRect(i1, j1, k1, l1);
        for(; i1 < k; k -= k2);
        for(; j1 < l; l -= l2);
        int i3 = (int)Math.ceil(((i1 - k) + k1) / k2);
        int j3 = (int)Math.ceil(((j1 - l) + l1) / l2);
        int k3 = (i1 - k) / k2;
        int l3 = (j1 - l) / l2;
        for(int i4 = l3; i4 <= j3; i4++)
        {
            for(int j4 = k3; j4 <= i3; j4++)
            {
                int k4 = j4 * k2 + k;
                int l4 = i4 * l2 + l;
                int i5 = (j4 + 1) * k2 + k;
                int j5 = (i4 + 1) * l2 + l;
                g.drawImage(image, k4, l4, i5, j5, 0, 0, i2, j2, null);
            }

        }

        if(rectangle != null)
            g.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public static void texture(Image image, int i, int j, Graphics g, int k, int l, int i1, int j1)
    {
        texture(image, i, j, g, 0, 0, k, l, i1, j1);
    }

    public static void texture(Image image, Graphics g, int i, int j, int k, int l, int i1, int j1)
    {
        texture(image, -1, -1, g, i, j, k, l, i1, j1);
    }

    public static void texture(Image image, Graphics g, int i, int j, int k, int l)
    {
        texture(image, -1, -1, g, 0, 0, i, j, k, l);
    }

    public ImageTexture()
    {
    }
}
