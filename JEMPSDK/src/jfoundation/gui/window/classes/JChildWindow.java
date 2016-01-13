package jfoundation.gui.window.classes;

import javax.swing.*;

import com.efounder.eai.application.classes.JBOFApplication;

import jbof.gui.window.classes.JBOFMainWindow;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//     (�ڴ��ļ��п���������Դ�ļ�)
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JChildWindow extends JChildPanel {
  public jfoundation.application.classes.JApplication          Application=null;
  public JChildWindow() {
    this.setBorder(null);
  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public void setApplication(jfoundation.application.classes.JApplication App) {
    Application = App;
  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
//  public Object InitChildWindow(jfoundation.application.classes.JApplication App,JMainWindow MainWindow,Object AddObject,Object AddObject1) {
//    return null;
//  }

  public Object InitChildWindow(JBOFApplication App,JBOFMainWindow MainWindow,Object AddObject,Object AddObject1) {
    return null;
  }
  //
  protected String pageBaseUrl = null;
  // 地址或是服务器
  public String getPageBaseUrl() {
    return pageBaseUrl;
  }
  //
  public void setPageBaseUrl(String url) {
    pageBaseUrl = url;
  }
}
