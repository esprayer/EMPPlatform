package jenterprise.bof.classes.AppExplorerObject;

import javax.swing.ImageIcon;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JResource {
  static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
  public JResource() {
  }

  public static ImageIcon LoadImageIcon(Object obj,String ImageName) {
    ImageIcon imageicon = null;String ClassName,PathName;URL url;
    String    pUrl      = "/Resource/JEnterprise/Resource/Icons/";
      try {
        url =  obj.getClass().getResource(pUrl+ImageName+".gif");
        if ( url != null ){
          imageicon = new ImageIcon(url);
          return imageicon;
        }
        else{
          return null;
        }

      } catch ( Exception e ) {
        imageicon = null;
        return null;
      }
  }

}
	