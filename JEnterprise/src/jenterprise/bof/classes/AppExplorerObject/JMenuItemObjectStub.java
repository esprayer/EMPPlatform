package jenterprise.bof.classes.AppExplorerObject;

import javax.swing.*;
import javax.swing.tree.*;

import jbof.application.classes.operate.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JMenuItemObjectStub
    implements Serializable {
  static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
  public String       Name=null;
  public String       Caption=null;
  public String       Captions=null;
  public String       OperateItem=null;
  public String       Icon=null;
  public ImageIcon    Image=null;
  public DefaultMutableTreeNode TreeNodeObject = null;
  public JOperateItemStub    OIS;
  public String       Parent=null;
  public String       MenuFileName=null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JMenuItemObjectStub() {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String toString() {
      return Caption;
//      if ( TreeNodeObject == null ) {
//        return Caption;
//      } else {
//        TreePath Path;
//        Path.
//        TreeNodeObject.
//        Path = new TreePath (TreeNodeObject.getPath());
//        return Path.toString();
//      }
//    }
  }

  public ImageIcon getImageIcon(){
    if (Image == null ){
      if ( Icon != null && !Icon.equals("")){
        Image = LoadImageIcon(this, Icon);
      }
    }
    return Image;
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
