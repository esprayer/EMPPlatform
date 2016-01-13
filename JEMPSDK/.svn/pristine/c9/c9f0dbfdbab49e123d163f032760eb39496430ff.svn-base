package com.efounder.resource;

import javax.swing.ImageIcon;
import java.net.URL;
import java.awt.Image;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JResource {
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JResource() {
  }
  public static ImageIcon getImageIcon(Object o, String Path, String r, String Language) {
      ImageIcon ii = null;
      URL url = getResource(o, Path, r, Language);
      if(url != null)
          ii = new ImageIcon(url);
      return ii;
  }

  public static ImageIcon getImageIcon(Object o, String r, String Language) {
      ImageIcon ii = null;
      URL url = getResource(o, r, Language);
      if(url != null)
          ii = new ImageIcon(url);
      return ii;
  }

  public static ImageIcon getImageIcon(Object o, String r) {
      return getImageIcon(o, r, null);
  }

  public static URL getResource(Object o, String Path, String r, String Language) {
      URL url;
      url = null;
      if(Path == null)
          Path = "/";
      if(!Path.endsWith("/"))
          Path = Path + "/";
      String PathName;
      if(Language != null)
          PathName = Path + Language + "/" + r;
      else
          PathName = Path + r;
      url = o.getClass().getResource(PathName);

      return url;
  }

  public static URL getResource(Object o, String r, String Language) {
      URL url = null;
      String ClassName = o.getClass().getName();
      int B = ClassName.lastIndexOf(".") + 1;
      ClassName = ClassName.substring(B, ClassName.length());
      String PathName;
      if(Language != null)
          PathName = ClassName + "/" + Language + "/" + r;
      else
          PathName = ClassName + "/" + r;
      url = o.getClass().getResource(PathName);
      return url;
  }

  public static URL getResource(Object o, String r) {
      return getResource(o, r, null);
  }

}
