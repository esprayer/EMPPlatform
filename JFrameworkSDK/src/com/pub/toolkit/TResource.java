package com.pub.toolkit;

import java.net.URL;
import javax.swing.ImageIcon;

public class TResource
{
  public static ImageIcon LoadImageIcon(Object obj, String ImageName)
  {
    ImageIcon imageicon = null;
    try
    {
      URL url = obj.getClass().getResource(ImageName);
      if (url != null) {
        return new ImageIcon(url);
      }
      return null;
    }
    catch (Exception e)
    {
      imageicon = null;
    }
    return null;
  }
}
