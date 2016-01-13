package com.efounder.eai.resource;

import java.io.*;

import com.efounder.eai.*;
import com.core.xml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JSynObject {
  //
  public boolean SaveXMLObject(JDOMXMLBaseObject DOM,String Path,String FileName,String Language) {
    boolean res = false;
    String Separator = System.getProperty("file.separator");
    String PathName = EAI.LocalUserHome+EAI.Application+Separator+EAI.Product+Separator+Path;
    if ( Language != null )
      PathName += Separator + Language;
    File DirFile = new File(PathName);
    if ( DirFile.mkdirs() || DirFile.exists() ) {
      PathName += Separator + FileName;
      DOM.SaveToFile(PathName);
      res = true;
    }
    return res;
  }
  //
  public boolean SaveXMLObject(JDOMXMLBaseObject DOM,String Path,String FileName) {
    return SaveXMLObject(DOM,Path,FileName,null);
  }
  public boolean SaveEnterpriseXMLObject(JDOMXMLBaseObject DOM,String Path,String FileName,String Language) {
    boolean res = false;
    String Separator = System.getProperty("file.separator");
    String PathName = EAI.LocalUserHome+EAI.Application+Separator+Path;
    if ( Language != null )
      PathName += Separator + Language;
    File DirFile = new File(PathName);
    if ( DirFile.mkdirs() || DirFile.exists() ) {
      PathName += Separator + FileName;
      DOM.SaveToFile(PathName);
      res = true;
    }
    return res;
  }
  public boolean SaveEnterpriseXMLObject(JDOMXMLBaseObject DOM,String Path,String FileName) {
    return SaveEnterpriseXMLObject(DOM,Path,FileName,null);
  }

  public JSynObject() {
  }
  // Path = "JEnterprise/JFinance/Config"
  // Path = "JEnterprise/JFinance/Module"
}
