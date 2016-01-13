package com.efounder.comp;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import com.efounder.action.ActionToolBarPane;
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
public class ToolBarDialog extends JDialog {
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
  JPanel contentPanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  ActionToolBarPane actionToolbar = null;
  protected Object source = null;
  protected ActionGroup actionGroup = null;
  /**
   *
   * @param owner Object
   * @param source Object
   * @param caption String
   * @param ag ActionGroup
   * @return ToolBarDialog
   */
  public static ToolBarDialog createToolBarDialog(Object owner,Object source,
                                                  String caption,ActionGroup ag) {
    ToolBarDialog toolBarDialog = null;
    if ( owner instanceof Dialog ) {
      toolBarDialog = new ToolBarDialog((Dialog)owner,caption);
    } else if ( owner instanceof Frame ) {
      toolBarDialog = new ToolBarDialog((Frame)owner,caption);
    } else {
      toolBarDialog = new ToolBarDialog((Frame)null,caption);
    }
    if ( toolBarDialog != null ) {
      toolBarDialog.source = source;
      toolBarDialog.actionGroup = ag;
      try {
        toolBarDialog.initToolBarDialog();
      }
      catch (Exception exception) {
        exception.printStackTrace();
    }
    }
    return toolBarDialog;
  }
  /**
   *
   * @param owner Dialog
   * @param title String
   */
  protected ToolBarDialog(Dialog owner, String title) {
    super(owner, title, false);
  }
  /**
   *
   * @param owner Frame
   * @param title String
   */
  protected ToolBarDialog(Frame owner, String title) {
    super(owner, title, false);
  }
  /**
   *
   * @throws Exception
   */
  protected void initToolBarDialog() throws Exception {
    setDefaultCloseOperation(HIDE_ON_CLOSE);
    contentPanel.setLayout(borderLayout1);
    getContentPane().add(contentPanel);
    actionToolbar = new ActionToolBarPane(source);
    actionToolbar.addGroup(actionGroup);
    contentPanel.add(actionToolbar, java.awt.BorderLayout.CENTER);
    pack();
  }
}
