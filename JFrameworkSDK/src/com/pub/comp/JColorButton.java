package com.pub.comp;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;

/**
 * <p>Title: 带色彩的按钮</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JColorButton extends JButton {
  /**
   * declaration of member variables
   */
  private int
  w=ColorConstants.width,
  h=ColorConstants.height;
  /**
   * declaration of GUI components
   */
  private Color color;
  /**
   * 初始化
   * @param color 指定颜色
   */
  public JColorButton(Color color) {
      this.color=color;
  }
  /**
   *this is no need to provide this method to the users
   */
//    public void setColor(Color color){
//        this.color=color;
//        ((JColorIcon)this.getIcon()).setColor(color);
//    }
  public Color getColor(){
      return color;
  }
  public void paint(Graphics g){
    super.paint(g);
    int x=0,y=0;
    x = (this.getWidth()-w)/2;
    y = (this.getHeight()-h)/2;
      if(this.isEnabled()){
          g.setColor(color);
          g.fillRect(x,y,w,h);
      }else{
          g.setColor(color.darker());
          g.fillRect(x,y,w,h);
      }
      g.setColor(Color.BLACK);
      g.drawRect(x,y,w,h);
    }

}