package com.efounder.dataobject.action;

import javax.swing.*;

import com.efounder.action.*;
import com.efounder.actions.*;
import com.efounder.dataobject.view.*;
import com.efounder.eai.ide.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class GenerDCTDataActionProvider implements ContextActionProvider {
  public GenerDCTDataActionProvider() {
  }
  public Action getContextAction(Object frame, Object[] nodeArray) {
    OBJView dctView = null;
    if ( frame instanceof DCTView ) {
      dctView = (OBJView)frame;
      return getActions(dctView,nodeArray);
    } else {
      return null;
    }
  }
  private Action getActions(OBJView dctView,Object[] dataRows) {
    ActionGroup GROUP_Gener = new ExplorerActionGroup();
    GROUP_Gener.add(getDCTObjectAttrib(dctView,dataRows));
    return GROUP_Gener;
  }
  private Action getDCTObjectAttrib(OBJView dctView,Object[] dataRows) {
    DCTObjectAction dctAction = new DCTObjectAction(dctView,"属性",'0',"数据对象属性",ExplorerIcons.ICON_PRIMITIVE);
    return dctAction;
  }
}
