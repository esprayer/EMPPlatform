package com.efounder.view.comp;

import java.awt.*;
import javax.swing.*;

import com.core.xml.*;
import com.efounder.eai.ide.*;
import com.efounder.view.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class NodeViewLeftTopView extends JLabel implements ComponentView {
  BorderLayout borderLayout1 = new BorderLayout();
//  JLabel lbImage = new JLabel();

  public NodeViewLeftTopView() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
//    this.setLayout(borderLayout1);
//    this.add(lbImage, java.awt.BorderLayout.CENTER);
    this.setOpaque(false);
  }
  /**
   *
   */
  private void initImage(String imageName) {
    Icon taskIcon = ExplorerIcons.getExplorerIcon(imageName);
    this.setIcon(taskIcon);
  }

  public Component getComponent() {
    return this;
  }

  public Object getValue(Object key, Object def) {
    return null;
  }

  public void setValue(Object key, Object value) {
  }

  public void initComponentView(CompViewFactory cvf,StubObject stubObject) {
    String image = stubObject.getString("image",null);
    if ( image != null )
      initImage(image);
    String lable = stubObject.getString("lable",null);
    if ( lable != null )
      this.setText(lable);
  }

  public void setParentView(Component comp) {
  }
}
