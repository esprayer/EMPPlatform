package com.efounder.help;

import java.net.*;
import com.efounder.eai.*;
import com.efounder.node.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class HelpUtil {
  /**
   *
   */
  public HelpUtil() {
  }
  /**
   *
   * @param nodeStub JNodeStub
   */
  public static void setupNode(JNodeStub nodeStub) {
    JNodeSetupDlg dlg = new JNodeSetupDlg(nodeStub);
    dlg.CenterWindow();
    dlg.Show();
  }
  /**
   *
   * @param name String
   * @return URL
   */
  public static URL getHelpUrl(String name) {
    return getUrl("HelpSpace",name);
  }
  /**
   *
   * @param name String
   * @return URL
   */
  public static URL getDocUrl(String name) {
    return getUrl("DocSpace",name);
  }
  /**
   *
   * @param name String
   * @return URL
   */
  public static URL getUrl(String path,String name) {
    String uri = null;URL url = null;
    try {
      uri = "http://" +EAI.Server+":"+EAI.Port+"/EnterpriseServer/"+path+"/"+EAI.Product+"/"+EAI.getLanguage()+"/"+name+".mht";
      url = new URL(uri);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return url;
  }
}
