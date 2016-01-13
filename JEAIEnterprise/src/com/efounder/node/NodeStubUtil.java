package com.efounder.node;

import org.openide.*;
import com.efounder.view.*;
import javax.swing.JOptionPane;
import com.efounder.eai.EAI;
import com.core.xml.*;
import com.efounder.eai.ide.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeStubUtil {
	static ResourceBundle res = ResourceBundle.getBundle("com.efounder.node.Res");
  public NodeStubUtil() {
  }
  /**
   *
   * @param nodeStub JNodeStub
   */
  public static void openNodeObject(JNodeStub nodeStub,Object param) {
    JExplorerView ExplorerView = nodeStub.getExplorerView();
    Context context = new Context(ExplorerView,nodeStub);
    context.setUserObject(param);
    NodeStubUtil.openNodeObject(nodeStub,null,context);
  }
  /**
   *
   * @param nodeStub JNodeStub
   * @param Key String
   * @param context Context
   */
  public static void openNodeObject(JNodeStub nodeStub,String Key,Context context) {
    openNodeObject(nodeStub,null,Key,context);
  }
  /**
   *
   * @param nodeStub JNodeStub
   * @param Key String
   * @param context Context
   */
  public static void openNodeObject(JNodeStub nodeStub,Object nodeKey,String Key,Context context) {
    StubObject stub = nodeStub.getNodeStubObject();
    context.setMainNodeStub(nodeStub);
    WaitingManager.getDefault().showWait(EAI.EA.getMainWindow());
    try {
      nodeStub.initData(context);
    }
    catch (Exception ex) {
        ex.printStackTrace();
    } finally {
      WaitingManager.getDefault().endShowWait(EAI.EA.getMainWindow());
    }
    //
    if ( nodeStub.allowOpen() || nodeStub.allowOpen(context) ) { // 如果是打开节点，需要执行如下过程
      if ( !checkOpenNode(nodeStub, nodeKey, Key, context, stub) ) return;
    }
    // 获取该节点的预处理节点对象
    JNodeStub prepareNode = nodeStub.getPrepareNodeStub(nodeKey,Key,context);
    if ( prepareNode != null ) {
      context.setPrepareSucess(false); // 先设置成false
      openNode(prepareNode,nodeKey,Key,context);
    }
    if ( context.isPrepareSucess() ) {// 如果prepare成功，则继续处理
      if ( nodeStub.allowExec() || nodeStub instanceof ExecuteNode ||
           nodeStub.allowExec(context) ) {// 执行节点
        execNode(nodeStub, nodeKey, Key, context);
        return;
      }
      if ( nodeStub.allowOpen() || nodeStub.allowOpen(context) ) {// 打开节点
        openNode(nodeStub, nodeKey, Key, context);
        return;
      }
    }
  }
  /**
   *
   * @param nodeStub JNodeStub
   * @param nodeKey Object
   * @param Key String
   * @param context Context
   * @param stub StubObject
   * @return boolean
   */
  private static boolean checkOpenNode(JNodeStub nodeStub, Object nodeKey,
                                    String Key, Context context,
                                    StubObject stub) {
    if (stub != null) {
      String noOpen = stub.getString("noOpen", "0");
      if ("1".equals(noOpen))
        return false;
    }
    if (!nodeStub.canOpenObject(nodeKey, Key, context)) {
      // 获取最后出错的信息
      String errorMessage = nodeStub.getErrorMessage(nodeKey, Key, context);
      if (errorMessage != null) {
        JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), errorMessage,
                                      "提示信息", JOptionPane.WARNING_MESSAGE, null);
        return false;
      }
    }
    return true;
  }
  /**
   *
   * @param nodeStub JNodeStub
   * @param nodeKey Object
   * @param Key String
   * @param context Context
   */
  protected static void openNode(JNodeStub nodeStub,Object nodeKey,String Key,Context context) {
    NodeObjectRunnable nor = new NodeObjectRunnable(nodeStub,nodeKey,Key,context);
    context.setNode(nodeStub);
    int openPlace = nodeStub.getOpenPlace();
    if ( openPlace == ViewBuilder.DIALOG_VIEW ) {
      try {
        WaitingManager.getDefault().showWait(EAI.EA.getMainWindow());
        nor.run();
      } catch ( Exception e) {
        e.printStackTrace();
      } finally {
        WaitingManager.getDefault().endShowWait(EAI.EA.getMainWindow());
      }
    } else {
//      WaitingManager.getDefault().waitInvoke(nodeStub.toString(),"正在打开节点" + nodeStub.toString(),nodeStub.getNodeIcon(), nor);
    	WaitingManager.getDefault().waitInvoke(nodeStub.toString(),res.getString("OpeningNode") + nodeStub.toString(),nodeStub.getNodeIcon(), nor);
    }
  }
  /**
   *
   * @param nodeStub JNodeStub
   * @param nodeKey Object
   * @param Key String
   * @param context Context
   */
  protected static void execNode(JNodeStub nodeStub,Object nodeKey,String Key,Context context) {
    if ( nodeStub instanceof ExecuteNode ) {
      ((ExecuteNode)nodeStub).executeNode(nodeKey,Key,context);
    } else
      nodeStub.execObject(nodeKey,Key,context);
//    NodeObjectRunnable nor = new NodeObjectRunnable(nodeStub,nodeKey,Key,context);
//    context.setNode(nodeStub);
//    int openPlace = nodeStub.getOpenPlace();
//    if ( openPlace == ViewBuilder.DIALOG_VIEW ) {
//      try {
//        WaitingManager.getDefault().showWait(EAI.EA.getMainWindow());
//        nor.run();
//      } catch ( Exception e) {
//        e.printStackTrace();
//      } finally {
//        WaitingManager.getDefault().endShowWait(EAI.EA.getMainWindow());
//      }
//    } else {
//      WaitingManager.getDefault().waitInvoke(nodeStub.toString(),"正在打开节点" + nodeStub.toString(),nodeStub.getNodeIcon(), nor);
//    }
  }
}
