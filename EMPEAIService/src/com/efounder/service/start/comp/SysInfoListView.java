package com.efounder.service.start.comp;

import javax.swing.*;
import com.efounder.view.ComponentView;
import java.awt.Component;
import com.efounder.view.CompViewFactory;
import com.core.xml.StubObject;
import com.efounder.view.ComponentViewAdapter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import com.borland.jbcl.layout.BoxLayout2;
import java.awt.CardLayout;
import com.borland.jbcl.layout.PaneLayout;
import com.borland.jbcl.layout.*;
import java.awt.Color;
import com.efounder.pfc.swing.*;
import com.efounder.eai.ide.*;

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
public class SysInfoListView extends ComponentViewAdapter {
  /**
   *
   */
  public SysInfoListView() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    jLabel1.setText("系统信息列表");
    jLabel1.setIcon(ExplorerIcons.getExplorerIcon("jrubik/preview.gif"));
    this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    this.add(jPanel1, java.awt.BorderLayout.NORTH);
    jScrollPane1.getViewport().add(listInfoView);
    jPanel1.add(jLabel1);
    initSysInfoList();
  }

  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList listInfoView = new JList();
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel jLabel1 = new JLabel();
  protected void initSysInfoList() {
    DefaultListModel    listContentModel = new DefaultListModel();
    listInfoView.setModel(listContentModel);
    JIConListCellRenderer renderer = new JIConListCellRenderer(ExplorerIcons.ICON_RUN);
    listInfoView.setCellRenderer(renderer);
    StubObject stub = new StubObject("1");
//    stub.setCaption("测试信息一！");
//    stub.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/settings_triggers_32.png"));
//    listContentModel.insertElementAt(stub,0);
//    stub = new StubObject("2");
//    stub.setCaption("测试信息二！");
//    stub.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/help_32.png"));
//    listContentModel.insertElementAt(stub,0);
//    stub = new StubObject("2");
//    stub.setCaption("任务跟踪信息！");
//    stub.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/error_32.png"));
//    listContentModel.insertElementAt(stub,0);
//    stub = new StubObject("2");
//    stub.setCaption("安全警告信息！");
//    stub.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/license_32.png"));
//    listContentModel.insertElementAt(stub,0);
//    stub = new StubObject("2");
//    stub.setCaption("应用服务器故障信息！");
//    stub.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/unfreeze_24.png"));
//    listContentModel.insertElementAt(stub,0);
//    stub = new StubObject("2");
//    stub.setCaption("存储服务器故障信息！");
//    stub.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/trigger_heapthreshold_32.png"));
//    listContentModel.insertElementAt(stub,0);
//    stub = new StubObject("1");
    stub.setCaption("系统正常运行！");
    stub.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/start_24.png"));
    listContentModel.insertElementAt(stub,0);
  }
}
