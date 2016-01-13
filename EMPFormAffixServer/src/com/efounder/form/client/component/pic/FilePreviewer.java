package com.efounder.form.client.component.pic;

import javax.swing.JComponent;
import java.awt.Image;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import java.io.File;
import java.beans.PropertyChangeEvent;
import javax.swing.JFileChooser;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import java.awt.Graphics;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class FilePreviewer extends JComponent implements PropertyChangeListener {
      ImageIcon thumbnail = null;

      public FilePreviewer(JFileChooser fc) {
         setPreferredSize(new Dimension(100, 50));
         fc.addPropertyChangeListener(this);
         setBorder(new BevelBorder(BevelBorder.LOWERED));
      }

      public void loadImage(File f) {
          if (f == null) {
              thumbnail = null;
          } else {
             ImageIcon tmpIcon = new ImageIcon(f.getPath());
             if(tmpIcon.getIconWidth() > 90) {
                 thumbnail = new ImageIcon(
                     tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
             } else {
                 thumbnail = tmpIcon;
             }
         }
      }

      public void propertyChange(PropertyChangeEvent e) {
         String prop = e.getPropertyName();
         if(prop == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
             if(isShowing()) {
                  loadImage((File) e.getNewValue());
                 repaint();
             }
         }
      }

      public void paint(Graphics g) {
         super.paint(g);
         if(thumbnail != null) {
             int x = getWidth()/2 - thumbnail.getIconWidth()/2;
             int y = getHeight()/2 - thumbnail.getIconHeight()/2;
             if(y < 0) {
                 y = 0;
             }

             if(x < 5) {
                 x = 5;
             }
             thumbnail.paintIcon(this, g, x, y);
         }
      }
  }

