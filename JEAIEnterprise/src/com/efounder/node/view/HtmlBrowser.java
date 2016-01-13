package com.efounder.node.view;

import java.net.*;

import java.awt.*;
import javax.swing.*;

import org.jdesktop.jdic.browser.*;
import org.openide.*;
import com.efounder.comp.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class HtmlBrowser extends JPanel {
  /**
   *
   */
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel actionPanel = new JPanel();
  public static final int NO_HTML_BROWSER     = 0;
  public static final int JAVA_HTML_BROWSER   = 1;
  public static final int NATIVE_HTML_BROWSER = 2;
  protected int browserType = NO_HTML_BROWSER;
  JScrollPane javaBrowserPanel = new JScrollPane();

  protected WebBrowser  nativeWebBrowser = null;
  /**
   *
   * @return WebBrowser
   */
  public WebBrowser getWebBrowser() {
    if ( nativeBrowserPanel != null )
      return null;//(WebBrowser)nativeBrowserPanel.getWebBrower();
    return null;
  }
  protected JEditorPane javaWebBrowser   = null;
  Browser nativeBrowserPanel = null;
  /**
   *
   * @param browserType int
   */
  public void setBrowserType(int browserType) {
    this.browserType = browserType;
  }
  /**
   *
   * @return int
   */
  public int getBrowserType() {
    return browserType;
  }
  /**
   *
   */
  public HtmlBrowser() {
    this(NO_HTML_BROWSER);
  }
  /**
   *
   */
  public HtmlBrowser(int browserType) {
    try {
      this.browserType = browserType;
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   * @throws Exception
   */
  protected void initNativeWebBrowser() {
    if ( nativeBrowserPanel == null ) {
      try {
        nativeBrowserPanel = null;//new com.efounder.comp.Browser();
        this.add(nativeBrowserPanel, BorderLayout.CENTER);
      }catch ( Exception e) {
        e.printStackTrace();
      }
    }
  }
  protected void destroyNativeWebBrowser() {
    if ( nativeBrowserPanel != null ) {
      this.remove(nativeBrowserPanel);
      nativeBrowserPanel = null;
    }
  }
  /**
   *
   * @throws Exception
   */
  protected void initJavaWebBrowser() {
    if ( javaWebBrowser == null ) {
      javaWebBrowser = new JEditorPane();
      javaWebBrowser.setEditable(false);
      javaWebBrowser.setContentType("text/html");
      javaBrowserPanel.getViewport().add(javaWebBrowser, null);
      this.add(javaBrowserPanel, BorderLayout.CENTER);
    }
  }
  protected void destroyJavaWebBrowser() {
    if ( javaWebBrowser != null ) {
      javaBrowserPanel.getViewport().remove(javaWebBrowser);
      this.remove(javaBrowserPanel);
      javaWebBrowser = null;
    }
  }

  /**
   *
   * @throws Exception
   */
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
  }
  public void closeURL() {
    try {
      if (this.browserType == this.NATIVE_HTML_BROWSER) {
        this.destroyNativeWebBrowser();
//        this.nativeWebBrowser.setURL(null);
      }
      if (this.browserType == this.JAVA_HTML_BROWSER) {
        this.destroyJavaWebBrowser();
//        this.javaWebBrowser.setPage((URL)null);
      }
    } catch ( Exception e ) {
      ErrorManager.getDefault().notify(e,this);
    }
  }
  public URL getURL() {
    try {
      if (this.browserType == this.NATIVE_HTML_BROWSER) {
        if ( nativeBrowserPanel != null )
          return this.getWebBrowser().getURL();
      }
      if (this.browserType == this.JAVA_HTML_BROWSER) {
        if ( javaWebBrowser != null )
          return this.javaWebBrowser.getPage();
      }
    } catch ( Exception e ) {
      ErrorManager.getDefault().notify(e,this);
    }
    return null;
  }
  /**
   *
   * @param url URL
   * @throws Exception
   */
  public void setURL(URL url) {
    try {
      if (this.browserType == this.NATIVE_HTML_BROWSER) {
        this.initNativeWebBrowser();
        this.getWebBrowser().setURL(url);
      }
      if (this.browserType == this.JAVA_HTML_BROWSER) {
        this.initJavaWebBrowser();
        this.javaWebBrowser.setPage(url);
      }
    } catch ( Exception e ) {
      ErrorManager.getDefault().notify(e,this);
    }
  }
}
