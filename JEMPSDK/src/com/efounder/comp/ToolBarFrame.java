package com.efounder.comp;

import java.awt.*;
import javax.swing.*;
import com.efounder.action.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ToolBarFrame extends JFrame {
  /**
   *
   */
  BorderLayout borderLayout1 = new BorderLayout();
  /**
   *
   * @return JRootPane
   */
  protected JRootPane createRootPane() {
      JRootPane rootPane = new JRootPane() {
          private boolean packing = false;

          public void validate() {
              super.validate();
              if (!packing) {
                  packing = true;
                  pack();
                  packing = false;
              }
          }
      };
      rootPane.setOpaque(true);
      return rootPane;
    }
    /**
     *
     */
    public ToolBarFrame() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    getContentPane().setLayout(borderLayout1);
  }
  public static ToolBarFrame createToolBarFrame(Object owner,Object source,
                                                  String caption,ActionGroup ag) {
    ToolBarFrame toolBarFrame = null;
//    if ( owner instanceof Dialog ) {
//      toolBarFrame = new ToolBarDialog((Dialog)owner,caption);
//    } else if ( owner instanceof Frame ) {
//      toolBarDialog = new ToolBarDialog((Frame)owner,caption);
//    } else {
//      toolBarDialog = new ToolBarDialog((Frame)null,caption);
//    }
//    if ( toolBarDialog != null ) {
//      toolBarDialog.source = source;
//      toolBarDialog.actionGroup = ag;
//      try {
//        toolBarDialog.initToolBarDialog();
//      }
//      catch (Exception exception) {
//        exception.printStackTrace();
//      }
//    }
    return toolBarFrame;
  }
}
