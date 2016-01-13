package com.efounder.dataobject;

import com.efounder.dbc.*;
import com.efounder.db.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class FCTObject {
  protected TABObject     tabObject = null;
  protected String        FCT_ID    = null;
  protected ClientDataSet SYS_FACTS = null;
  /**
   *
   * @return String
   */
  public String getFCT_ID() {
    return FCT_ID;
  }
  /**
   *
   * @return TABObject
   */
  public TABObject getTabObject() {
    return tabObject;
  }

  /**
   *
   * @return ClientDataSet
   */
  public ClientDataSet getSYS_FACTS() {
    return SYS_FACTS;
  }
  /**
   *
   */
  protected FCTObject() {
  }

}
