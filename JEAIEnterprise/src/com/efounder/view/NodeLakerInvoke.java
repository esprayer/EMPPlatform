package com.efounder.view;

import com.efounder.node.view.NodeChildWindow;
import com.efounder.node.view.NodeViewerFactory;
import com.efounder.node.Context;
import com.efounder.node.view.*;
import com.efounder.eai.ide.EnterpriseExplorer;
import com.efounder.eai.ui.JDialogView;
import java.awt.Frame;
import com.efounder.pfc.window.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class NodeLakerInvoke implements Runnable {
  NodeWindow nodeWindow;
  Context context;
  String Key;
  NodeViewerFactory[] nvfs;
  /**
   *
   * @param nodeWindow NodeChildWindow
   * @param context Context
   * @param Key String
   * @param nvfs NodeViewerFactory[]
   */
  public NodeLakerInvoke(NodeWindow nodeWindow,Context context,String Key,NodeViewerFactory[] nvfs) {
    this.nodeWindow = nodeWindow;
    this.context = context;
    this.Key = Key;
    this.nvfs = nvfs;
  }
  public void run() {
    try {
      nodeWindow.initNodeChildWindow2(context, Key, nvfs);
        //
    } catch ( Exception ex ) {
      ex.printStackTrace();
    }
  }
}
