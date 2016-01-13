package jcomponent.custom.swing;

import javax.swing.*;
import java.awt.*;

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
public class JPanelEx extends JPanel {
  private ImageIcon BGImageIcon = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JPanelEx() {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void setBGImageIcon(ImageIcon defaultIcon) {
    BGImageIcon = defaultIcon;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public ImageIcon getBGImageIcon() {
    return BGImageIcon;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  protected void paintComponent(Graphics g) {
    /**@todo Override this javax.swing.JComponent method*/
    super.paintComponent(g);
    if ( BGImageIcon != null ) {
      BGImageIcon.paintIcon(null,g,0,0);
    }
  }
  public void paint(Graphics g) {
    super.paint(g);
  }
}