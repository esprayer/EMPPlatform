package com.efounder.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class Icons
{
    private static class v
        implements Icon
    {

        private boolean d;
        private int f;
        private int c;
        private int a;
        private int b;
        private Image e;

        static Icon a(v v1)
        {
            return v1.a();
        }

        public int getIconHeight()
        {
            b();
            return f;
        }

        public int getIconWidth()
        {
            b();
            return c;
        }

        public void paintIcon(Component component, Graphics g, int i, int j)
        {
            b();
            g.drawImage(e, i, j, i + c, j + f, b, a, b + c, a + f, null);
        }

        private Icon a()
        {
            b();
            return new v(Images.getDisabledImage(e), b, a, c, f);
        }

        private void b()
        {
            if(!d)
            {
                Images.waitForImage(e);
                if(c == -1)
                    c = e.getWidth(null);
                if(f == -1)
                    f = e.getHeight(null);
                d = true;
            }
        }

        public v(Image image, int i, int j, int k, int l)
        {
            e = image;
            b = i;
            a = j;
            c = k;
            f = l;
        }
    }

    public static class IconFactory
    {

        private int d;
        private int c;
        private int a;
        private Image b;

        public Icon getIcon(int i, int j)
        {
            return new v(b, i * (a + d), j * (c + d), a, c);
        }

        public Icon getIcon(int i)
        {
            return getIcon(i, 0);
        }

        public IconFactory(Image image, int i, int j, int k)
        {
            b = image;
            a = i;
            c = j;
            d = k;
        }

        public IconFactory(Image image, int i, int j)
        {
            this(image, i, j, 0);
        }

        public IconFactory(Image image, int i)
        {
            this(image, i, i);
        }
    }


    private static HashMap a = new HashMap();

    public static IconFactory getIconFactory(Image image, int i, int j, int k)
    {
        return new IconFactory(image, i, j, k);
    }

    public static IconFactory getIconFactory(Image image, int i, int j)
    {
        return new IconFactory(image, i, j);
    }

    public static IconFactory getIconFactory(Image image, int i)
    {
        return new IconFactory(image, i, i);
    }

    public static Icon getIcon(Image image, int i, int j, int k, int l)
    {
        return new v(image, i, j, k, l);
    }

    public static Icon getIcon(Image image)
    {
        return new v(image, 0, 0, -1, -1);
    }

    public static Icon getIcon(Class class1, String s)
    {
        return getIcon(Images.getImage(class1, s, false));
    }

    public static Icon getIcon(URL url)
    {
        return getIcon(Images.getImage(url));
    }

    public static synchronized Icon getDisabledIcon(Icon icon)
    {
        if(icon == null)
            return null;
        Object obj = (Icon)a.get(icon);
        if(obj == null)
        {
            if(icon instanceof v)
            {
                obj = v.a((v)icon);
            } else
            {
                Object obj1;
                if(icon instanceof ImageIcon)
                {
                    obj1 = ((ImageIcon)icon).getImage();
                } else
                {
                    obj1 = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 2);
                    Graphics g = ((Image) (obj1)).getGraphics();
                    icon.paintIcon(null, g, 0, 0);
                }
                Image image = GrayFilter.createDisabledImage(((Image) (obj1)));
                obj = new v(image, 0, 0, icon.getIconWidth(), icon.getIconHeight());
            }
            a.put(icon, obj);
        }
        return ((Icon) (obj));
    }

    public static Icon getBlankIcon(int i, int j)
    {
        return new w(i, j);
    }

    public Icons()
    {
    }


// Unreferenced inner classes:

/* anonymous class */
    static class w
        implements Icon
    {
      int a,b;
        public void paintIcon(Component component, Graphics g, int i, int j)
        {
        }

        public int getIconHeight()
        {
            return b;
        }

        public int getIconWidth()
        {
            return a;
        }
        public w(int i,int j) {
          a = i;b=j;
        }
    }

}
