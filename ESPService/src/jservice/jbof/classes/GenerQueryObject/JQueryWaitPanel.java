package jservice.jbof.classes.GenerQueryObject;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import jframework.resource.classes.*;
import jfoundation.object.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQueryWaitPanel extends JPanel {
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  BoxLayout2 boxLayout21 = new BoxLayout2();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel lbICON = new JLabel();
  JLabel lbTitle1 = new JLabel();
  private JDialog ParentObject = null;
  FlowLayout flowLayout2 = new FlowLayout();
  JLabel lbTitle2 = new JLabel();

  public JQueryWaitPanel() {
    try {
      jbInit();
      initComp();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  public void setParentObject(Object po) {
    ParentObject = (JDialog)po;
  }
  private void initComp() {
    String iconname = "view.gif";
    ImageIcon icon = JXMLResource.LoadImageIcon(this,iconname);
    lbICON.setIcon(icon);
//    java.net.URL url = JXMLResource
  }
  public void setTitle1(String title) {
    lbTitle1.setText(title);
//    ParentObject.pack();
//    JBOFClass.CallObjectMethod(ParentObject,"pack");
  }
  public void setTitle2(String title) {
    lbTitle2.setText(title);
//    ParentObject.pack();
  }
  public void setICON(Icon icon) {
    lbICON.setIcon(icon);
  }
  public String getTitle1() {
    return lbTitle1.getText();
  }
  public String getTitle2() {
    return lbTitle2.getText();
  }
  void jbInit() throws Exception {
    jPanel2.setLayout(flowLayout2);
    jPanel1.setLayout(flowLayout1);
    boxLayout21.setAxis(BoxLayout.Y_AXIS);
    this.setLayout(boxLayout21);
    lbICON.setText("");
    lbTitle1.setForeground(new Color(0, 0, 128));
    lbTitle1.setText("CASL");
//    this.setPreferredSize(new Dimension(73, 26));
    lbTitle2.setForeground(new Color(0, 0, 128));
    this.setBorder(BorderFactory.createRaisedBevelBorder());
    this.add(jPanel1, null);
    jPanel1.add(lbICON, null);
    jPanel1.add(lbTitle1, null);
    this.add(jPanel2, null);
    jPanel2.add(lbTitle2, null);
  }
}
