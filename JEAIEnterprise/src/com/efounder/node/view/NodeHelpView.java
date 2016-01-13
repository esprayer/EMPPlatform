package com.efounder.node.view;

import java.net.*;

import javax.swing.*;

import com.efounder.eai.ide.*;
import com.efounder.help.*;
import com.efounder.node.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeHelpView extends NodeDocumentView {
  /**
   *
   */
  public NodeHelpView() {
    super();
  }
  protected int getBrowserType() {
    return HtmlBrowser.NATIVE_HTML_BROWSER;
  }
  /**
   *
   * @return URL
   */
  protected URL getUrlPath() {
    String name = nodeStub.getDocName();
    URL url = HelpUtil.getHelpUrl(name);
    if ( url == null ) {
      url   = HelpUtil.getDocUrl(JNodeStub.HelpNotFound);
    }
    return url;

  }
  /**
   *
   * @return Icon
   */
  public Icon getViewerIcon() {
    return ExplorerIcons.ICON_WARNING;//ICON_HELPBORLANDONLINE;
  }
  /**
   *
   * @return String
   */
  public String getViewerDescription() {
    return "帮助视图";
  }
  /**
   *
   * @return String
   */
  public String getViewerTitle() {
    return "帮助";
  }
}
