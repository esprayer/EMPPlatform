package com.efounder.form.client.component.frame;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.BorderLayout;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JToggleButtonExt extends JToggleButton{
    public JToggleButtonExt() {
        this("",null);
    }
    public JToggleButtonExt(String text, Icon icon) {
        super(text,icon);
        createImage();
    }

    protected void createImage(){
        this.setLayout(new BorderLayout());
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setIcon((ImageIcon)this.getIcon());
    }

    JLabel desLabel = null;
    public JLabel getRlLabel() {
        return desLabel;
    }

    public void setRlLabel(JLabel rlLabel) {
        this.desLabel = rlLabel;
    }

    public void setSelected(boolean b) {
        super.setSelected(b);
        showImageDes(this,desLabel);
    }

    public static void showImageDes(JToggleButton button,JLabel label) {
        JImageDescBean desBean = (JImageDescBean)button.getClientProperty("DES");
        String tip = "        文件名: "+desBean.getImgName() + "        类型: " + desBean.getImgTypeName();
        tip += "        大小: " + desBean.getImgSize() + " KB        尺寸: "+ desBean.getImgWidth() +"×" + desBean.getImgHeight()  ;
        label.setText(tip);
    }

}

  class ImagePanel extends Canvas {
   ImageIcon icon = null;
   public void setIcon(ImageIcon srcIcon) {
     this.icon = srcIcon;
   }

   public void paint(Graphics g) {
     super.paint(g);
     Component parent =  this.getParent();
     ImageIcon shrinkIcon = null;
     if (icon != null) {
       if (icon.getIconWidth() > parent.getWidth()) {
         shrinkIcon = new ImageIcon(icon.getImage().getScaledInstance(parent.getWidth(), -1, Image.SCALE_DEFAULT));
      }
      else {
       shrinkIcon = icon;
      }
      shrinkIcon.paintIcon(this, g, 0, 0);
     }
   }
 }

