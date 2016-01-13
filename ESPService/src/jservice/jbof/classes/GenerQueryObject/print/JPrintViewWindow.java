package jservice.jbof.classes.GenerQueryObject.print;

import java.awt.*;
import jbof.gui.window.classes.style.mdi.JBOFMDIChildWindow;
import javax.swing.*;
import com.print.*;
import jbof.application.classes.*;
import jbof.gui.window.classes.*;

import com.efounder.eai.application.classes.JBOFApplication;
import com.f1j.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JPrintViewWindow extends JBOFMDIChildWindow {
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JCanvasPanel CanvasPanel = null;

  public JPrintViewWindow() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  // 初始化控件
  void initComp() {
    this.setLayout(borderLayout1);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(jScrollPane1, BorderLayout.CENTER);
  }
  // 初始画布
  void initCanvas() {
    CanvasPanel = new JCanvasPanel();
    jScrollPane1.getViewport().add(CanvasPanel, null);
  }
  void jbInit() throws Exception {
    initComp();
    initCanvas();
  }
  /**
   *
   * @param App JBOFApplication
   * @param MainWindow JBOFMainWindow
   * @param AddObject Object
   * @param AddObject1 Object
   * @return Object
   */
  public Object InitChildWindow(JBOFApplication App, JBOFMainWindow MainWindow,
                                  Object AddObject, Object AddObject1) {
      JBook  Book    = (JBook)AddObject;
      Object Context = AddObject1;
      // 初始化画布
      CanvasPanel.initCanvas(Book,Context);
      return null;
  }
}
