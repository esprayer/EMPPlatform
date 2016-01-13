package com.efounder.view;

import com.efounder.pfc.window.*;
import com.efounder.resource.JResource;

import java.awt.*;
import javax.swing.*;
import com.efounder.util.*;
import com.efounder.eai.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SwitchStatus implements ComponentFactory {
  JCheckBox cbOffline = new JCheckBox();
  JLabel   lbLock    = new JLabel();
  public SwitchStatus() {
  }
  public JComponent[] createComponent(JComponent parent) {
    cbOffline.setOpaque(false);
    lbLock.setOpaque(false);
    Icon icon1 = JResource.getImageIcon(this,"offline.gif",EAI.getLanguage());
    Icon icon2  = JResource.getImageIcon(this,"online.gif",EAI.getLanguage());
    cbOffline.setIcon(icon2);
    if ( EAI.getOffline() )
      cbOffline.setSelectedIcon(icon1);
    else
      cbOffline.setSelectedIcon(icon2);

    icon1 = JResource.getImageIcon(this,"lock.gif",EAI.getLanguage());
    icon2 = JResource.getImageIcon(this,"unlock.gif",EAI.getLanguage());
    if ( EAI.getSecurity() ) {
      lbLock.setIcon(icon1);
    } else
      lbLock.setIcon(icon2);

    Dimension dim = new Dimension(40,16);
    cbOffline.setPreferredSize(dim);
    dim = new Dimension(16,16);
    lbLock.setPreferredSize(dim);
    JComponent[] rs = {lbLock,cbOffline};
    return rs;
  }

}
