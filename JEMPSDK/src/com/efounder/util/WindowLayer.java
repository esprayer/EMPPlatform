package com.efounder.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import javax.swing.JComponent;
import java.awt.*;
public  class WindowLayer {

  public WindowLayer() {
  }

  public  void CenterWindow(Window JCOMP){
    Toolkit D = Toolkit.getDefaultToolkit();
    Dimension   S = D.getScreenSize();
    int     w  = (int)S.getWidth();
    int     h  = (int)S.getHeight();

    int    cw = JCOMP.getWidth();
    int    ch = JCOMP.getHeight();

    int    x = ( w - cw ) /2;
    int    y = ( h - ch ) /2;

    JCOMP.setLocation(x,y);
  }
  public void ScreenMaxWindow(Window JCOMP){
    Toolkit D = Toolkit.getDefaultToolkit();
    Dimension   S = D.getScreenSize();
    int     w  = (int)S.getWidth();
    int     h  = (int)S.getHeight();


    JCOMP.setBounds(0,0,w,h);

  }

}
