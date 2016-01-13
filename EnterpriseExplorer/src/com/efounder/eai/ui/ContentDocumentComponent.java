package com.efounder.eai.ui;

import com.jidesoft.document.DocumentComponent;
import javax.swing.Icon;
import com.efounder.pfc.window.IWindow;

/**
 * <p>Title: Enterprise Explorer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: EFounder</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ContentDocumentComponent extends DocumentComponent {
  /**
   *
   * @param cw IWindow
   * @param title String
   * @param tips String
   * @param icon Icon
   */
  public ContentDocumentComponent(IWindow cw, String title, String tips, Icon icon) {
    super(cw.getWindowComponent(), title, tips, icon);
  }
}
