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
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.PrintStream;

// Referenced classes of package com.borland.primetime.util:
//            Images

public class AuraImage
{

    protected int alphaThreshold;
    protected int blendMap[];
    protected int auraMap[];
    protected int srcMap[];
    protected int ih;
    protected int iw;
    protected int auraRGB;
    protected boolean auraBlended;
    protected boolean auraCalculated;

    protected void blendAuraMap()
    {
        calcAuraMap();
        if(auraBlended)
            return;
        blendMap = new int[iw * ih];
        for(int i = 0; i < srcMap.length; i++)
            blendMap[i] = srcMap[i] | auraMap[i];

        auraBlended = true;
    }

    protected void calcAuraMap()
    {
        if(auraCalculated)
            return;
        auraMap = new int[iw * ih];
        for(int i = 0; i < ih; i++)
        {
            for(int j = 0; j < iw; j++)
                auraMap[i * iw + j] = calcMapPixel(j, i);

        }

        auraCalculated = true;
    }

    protected int calcMapPixel(int i, int j)
    {
        if(!testOpaque(i, j) && (testOpaque(i - 1, j - 1) || testOpaque(i, j - 1) || testOpaque(i + 1, j - 1) || testOpaque(i - 1, j) || testOpaque(i + 1, j) || testOpaque(i - 1, j + 1) || testOpaque(i, j + 1) || testOpaque(i + 1, j + 1)))
            return auraRGB;
        else
            return 0;
    }

    protected final boolean testOpaque(int i, int j)
    {
        if(i < 0 || i >= iw || j < 0 || j >= ih)
            return false;
        else
            return (srcMap[j * iw + i] & 0xff000000) != 0;
    }

    protected final void fetchPixels(Image image)
    {
        Images.waitForImage(image);
        iw = image.getWidth(null);
        ih = image.getHeight(null);
        srcMap = new int[iw * ih];
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, iw, ih, true);
        try
        {
            pixelgrabber.grabPixels();
        }
        catch(InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
        srcMap = (int[])pixelgrabber.getPixels();
    }

    public void dumpPixelMaps()
    {
        calcAuraMap();
        System.out.println(String.valueOf(String.valueOf((new StringBuffer("AuraImage.dumpMaps() : image size: (")).append(iw).append(",").append(ih).append(")"))));
        System.out.println("key:\tO = opaque pixel\t+ = transparent pixel");
        System.out.println("left: source pixel data\tright: calculated aura pixel data");
        for(int i = 0; i < ih; i++)
        {
            for(int j = 0; j < iw; j++)
            {
                int l = srcMap[i * iw + j] & 0xff000000;
                System.out.print("".concat(String.valueOf(String.valueOf(l == 0 ? "+" : "O"))));
            }

            System.out.print("  ");
            for(int k = 0; k < iw; k++)
            {
                int i1 = auraMap[i * iw + k] & 0xff000000;
                System.out.print("".concat(String.valueOf(String.valueOf(i1 == 0 ? "+" : "O"))));
            }

            System.out.println();
        }

        System.out.println();
    }

    public int getAlphaThreshold()
    {
        return alphaThreshold;
    }

    public void setAlphaThreshold(int i)
    {
        alphaThreshold = i;
        auraCalculated = false;
        auraBlended = false;
    }

    public int getAuraRGB()
    {
        return auraRGB;
    }

    public void setAuraRGB(int i)
    {
        auraRGB = i;
        auraCalculated = false;
        auraBlended = false;
    }

    public Image getBlendedImage()
    {
        calcAuraMap();
        blendAuraMap();
        return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw, ih, blendMap, 0, iw));
    }

    public Image getAuraImage()
    {
        calcAuraMap();
        return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw, ih, auraMap, 0, iw));
    }

    public Image getSourceImage()
    {
        return Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw, ih, srcMap, 0, iw));
    }

    public AuraImage(int ai[], int i, int j, int k)
    {
        auraCalculated = false;
        auraBlended = false;
        auraRGB = SystemColor.textHighlight.getRGB();
        srcMap = ai;
        iw = i;
        ih = j;
        auraRGB = k;
    }

    public AuraImage(Image image)
    {
        this(image, Color.green.getRGB());
    }

    public AuraImage(Image image, int i)
    {
        auraCalculated = false;
        auraBlended = false;
        auraRGB = SystemColor.textHighlight.getRGB();
        if(image == null)
        {
            throw new IllegalArgumentException("Image cannot be null");
        } else
        {
            auraRGB = i;
            fetchPixels(image);
            return;
        }
    }

    public static Image createAuraImage(Image image)
    {
        AuraImage auraimage = new AuraImage(image, Color.green.getRGB());
        return auraimage.getBlendedImage();
    }

    public static Image createAuraImage(Image image, int i)
    {
        AuraImage auraimage = new AuraImage(image, i);
        return auraimage.getBlendedImage();
    }
}
