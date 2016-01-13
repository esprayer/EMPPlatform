package com.efounder.view;

import com.efounder.node.*;
import com.core.xml.*;
import com.efounder.eai.ide.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeObjectRunnable implements Runnable {
  protected JNodeStub nodeStub = null;
  protected String key = null;
  protected Object nodeKey = null;
  protected Context context = null;
  /**
   *
   * @param nodeStub JNodeStub
   * @param key String
   * @param context Context
   */
  public NodeObjectRunnable(JNodeStub nodeStub,Object nodeKey,String key,Context context) {
    this.nodeStub = nodeStub;
    this.key = key;
    this.nodeKey = nodeKey;
    this.context = context;
  }
  public void run() {
//    try {
//      WaitingManager.getDefault().beginWaitingFor(EAI.EA.getMainWindow(),null,"正在打开节点"+nodeStub.toString(),nodeStub.getNodeIcon());
    if ( nodeKey != null ) {
      nodeStub.openObject(nodeKey,key,context);
//      nodeStub.openExpObject(nodeKey,key,context);
    } else {
      nodeStub.openObject(key, context);
      StubObject stub = nodeStub.getNodeStubObject();
      if ( stub != null ) {
        String extendedState = stub.getString("extendedState","0");
        if ( "max".equals(extendedState) ) {
          EnterpriseExplorer.ContentView.getViewDevice().setExtendedState(
                             EnterpriseExplorer.ContentView, Frame.MAXIMIZED_BOTH);
        }
      }
//      nodeStub.openExpObject(key, context);
    }
//    } catch ( Exception e ) {
//      ErrorManager.getDefault().notify(0,e,this,"打开节点出错！");
//    } finally {
//      WaitingManager.getDefault().endWaitngFor(EAI.EA.getMainWindow());
//    }
  }

}
