package com.efounder.ui;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.net.*;
import java.util.*;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

// Referenced classes of package com.borland.primetime.util:
//            AuraImage, t

public final class Images
{

    static long a = 0L;
    private static HashMap e = new HashMap();
    private static HashMap d = new HashMap();
    private static final HashSet b = new HashSet();
    private static final HashMap c = new HashMap();
    private static final MediaTracker f = new MediaTracker(new t());
    private static BufferedImage h;
    private static final int g;// = 16;

    public static synchronized void waitForImage(Image image)
    {
        if(b.contains(image))
            return;
        f.addImage(image, 0);
        try
        {
            f.waitForID(0);
        }
        catch(InterruptedException interruptedexception) { }
        f.removeImage(image);
        b.add(image);
    }

    public static synchronized Image getImage(URL url, boolean flag)
    {
        Image image = (Image)c.get(url);
        if(image == null)
        {
            image = Toolkit.getDefaultToolkit().createImage(url);
            if(image == null)
                image = a();
            c.put(url, image);
        }
        if(flag)
            waitForImage(image);
        return image;
    }

    public static Image getImage(URL url)
    {
        return getImage(url, true);
    }

    public static Image getImage(Class class1, String s, boolean flag)
    {
        URL url = class1.getResource(s);//a.c(class1, s);
        return url != null ? getImage(url, flag) : a();
    }

    public static Image getImage(Class class1, String s)
    {
        return getImage(class1, s, true);
    }

    public static Image getAuraImage(Image image)
    {
        if(image == null)
            return null;
        Image image1 = (Image)e.get(image);
        if(image1 == null)
        {
            image1 = (new AuraImage(image)).getBlendedImage();
            e.put(image, image1);
        }
        return image1;
    }

    public static synchronized Image getDisabledImage(Image image)
    {
        if(image == null)
            return null;
        Image image1 = (Image)d.get(image);
        if(image1 == null)
        {
            image1 = GrayFilter.createDisabledImage(image);
            waitForImage(image1);
            d.put(image, image1);
        }
        return image1;
    }

    private static Image a()
    {
        if(h == null)
        {
            h = new BufferedImage(16, 16, 1);
            java.awt.Graphics2D graphics2d = h.createGraphics();
            graphics2d.setColor(Color.red);
            graphics2d.fillRect(0, 0, 16, 16);
            b.add(h);
        }
        return h;
    }

    private Images()
    {
    }

    static
    {
        g = 16;
    }

// Unreferenced inner classes:

/* anonymous class */
    static class t extends Component
    {

    }

}
