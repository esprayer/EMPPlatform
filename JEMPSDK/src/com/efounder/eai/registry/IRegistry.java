package com.efounder.eai.registry;

import org.jdom.*;
import com.core.xml.*;
import java.io.File;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface IRegistry extends IDOMXMLBaseObject {
  static final public int  HKEY_CLASSES_ROOT    = 0;
  static final public int  HEKY_CURRENT_USER    = 1;
  static final public int  HKEY_LOCAL_MACHINE   = 2;
  static final public int  HKEY_USERS           = 3;
  static final public int  HKEY_CURRENT_CONFIG  = 4;

  public Element RegOpenKey(int hKey,String SubKeys);
  public Element RegOpenKey(Element eKey,String SubKeys);
  public int RegCloseKey(Element SubKey);
  public Element IsExistKey(Element ParentKey,String SubKey);
  public boolean InitRegistry();
}
