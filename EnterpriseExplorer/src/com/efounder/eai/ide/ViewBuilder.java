package com.efounder.eai.ide;

import com.core.xml.*;
import com.efounder.pfc.window.*;
import java.awt.event.*;
import com.efounder.eai.*;
import java.awt.*;
import com.efounder.eai.ui.JDockViewDevice;
import com.efounder.eai.ui.*;
import com.jidesoft.docking.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class ViewBuilder {

  public static final int TOP_VIEW = 0;
  public static final int EXPLORER_VIEW = 1;
  public static final int CONTENT_VIEW  = 2;
  public static final int PROPERTY_VIEW = 3;
  public static final int MESSAGE_VIEW  = 4;
  public static final int STRUCT_VIEW   = 5;
  public static final int DIALOG_VIEW   = 6;
  public static final int FLOAT_VIEW    = 7;

  static public Class PropertyViewClass = null;
  private static IView builderPropertyView(ViewContainer vc) {
    IView view = new JDockView(vc.getDockableHolder(),DockContext.STATE_FRAMEDOCKED,DockContext.DOCK_SIDE_EAST,0);
    // 默认不显示
    view.setVisible(false);
    return view;
//    try {
//      view = (IView) PropertyViewClass.newInstance();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return view;
  }
  static public Class TopViewClass      = null;
  private static IView builderTopView(ViewContainer vc) {
    IView view = new JDockView(vc.getDockableHolder(),DockContext.STATE_FRAMEDOCKED,DockContext.DOCK_SIDE_NORTH,0);
    return view;
//    try {
//      view = (IView) TopViewClass.newInstance();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return view;
  }
  static public Class ExplorerViewClass = null;
  private static IView builderExplorerView(ViewContainer vc) {
    IView view = new JDockView(vc.getDockableHolder(),DockContext.STATE_FRAMEDOCKED,DockContext.DOCK_SIDE_WEST,0);
    return view;
//    try {
//      view = (IView) ExplorerViewClass.newInstance();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return view;
  }
  static public Class ContentViewClass  = null;
  private static IView builderContentView(ViewContainer vc) {
    IView view = new JDockContentView(vc.getDocumentPane());
    return view;
//    try {
//      view = (IView) ContentViewClass.newInstance();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return view;
  }
  static public Class StructViewClass   = null;
  private static IView builderStructView(ViewContainer vc) {
    IView view = new JDockView(vc.getDockableHolder(),DockContext.STATE_FRAMEDOCKED,DockContext.DOCK_SIDE_WEST,1);
    return view;
//    try {
//      view = (IView) StructViewClass.newInstance();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return view;
  }
  static public Class MessageViewClass  = null;
  private static IView builderMessageView(ViewContainer vc) {
    IView view = new JDockView(vc.getDockableHolder(),DockContext.STATE_AUTOHIDE_SHOWING,DockContext.DOCK_SIDE_SOUTH,0);
    return view;
//
//    try {
//      view = (IView) MessageViewClass.newInstance();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return view;
  }
  static public Class DialogViewClass = null;
  private static IView builderDialogView() {
    IView view = null;
    try {
      view = (IView) DialogViewClass.newInstance();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return view;
  }
  static public Class FloatViewClass  = null;
  private static IView builderFloatView() {
    IView view = null;
    try {
      view = (IView) FloatViewClass.newInstance();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return view;
  }

//  static public Class WindowStatusClass = null;
  public static IWindowStatus builderWindowStatus() {
    IWindowStatus view = null;
//    try {
//      view = (IWindowStatus) WindowStatusClass.newInstance();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
    return view;
  }
  static public Class WindowAdapterClass= null;
  public static WindowAdapter builderWindowAdapter() {
    WindowAdapter view = null;
    try {
      view = (WindowAdapter) WindowAdapterClass.newInstance();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return view;
  }
  static public Class  ViewDeviceClass = null;
  private static IViewDevice builderViewDevice(ViewContainer vc) {
    IViewDevice view = new JDockViewDevice(vc.getDockableHolder());
    return view;
//    IViewDevice view = null;
//    try {
//      view = (IViewDevice) ViewDeviceClass.newInstance();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return view;
  }

  public ViewBuilder() {
  }

  public static void initViewClass() {
    try {
      java.util.List viewList = PackageStub.getContentVector("viewclass");
      StubObject SO = null;

//      SO = PackageStub.getStubObject(viewList,"TopViewClass");
//      String CustomClass = SO.getString("class",null);
//      TopViewClass = Class.forName(CustomClass);

//      SO = PackageStub.getStubObject(viewList,"ExplorerViewClass");
//      CustomClass = SO.getString("class",null);
//      ExplorerViewClass = Class.forName(CustomClass);

//      SO = PackageStub.getStubObject(viewList,"ContentViewClass");
//      CustomClass = SO.getString("class",null);
//      ContentViewClass = Class.forName(CustomClass);

//      SO = PackageStub.getStubObject(viewList,"PropertyViewClass");
//      CustomClass = SO.getString("class",null);
//      PropertyViewClass = Class.forName(CustomClass);

//      SO = PackageStub.getStubObject(viewList,"StructViewClass");
//      CustomClass = SO.getString("class",null);
//      StructViewClass = Class.forName(CustomClass);

//      SO = PackageStub.getStubObject(viewList,"MessageViewClass");
//      CustomClass = SO.getString("class",null);
//      MessageViewClass = Class.forName(CustomClass);
      try {
    	  SO = PackageStub.getStubObject(viewList,"DialogViewClass");
    	  String CustomClass = SO.getString("class",null);
    	  DialogViewClass = Class.forName(CustomClass);
      }catch(Exception e ) {};
//      try {
//      SO = PackageStub.getStubObject(viewList,"FloatViewClass");
//      CustomClass = SO.getString("class",null);
//      FloatViewClass = Class.forName(CustomClass);
//      }catch(Exception e ) {};
//      SO = PackageStub.getStubObject(viewList,"ViewDeviceClass");
//      CustomClass = SO.getString("class",null);
//      ViewDeviceClass = Class.forName(CustomClass);

      SO = PackageStub.getStubObject(viewList,"ExplorerAdapter");
      String CustomClass = SO.getString("class",null);
      WindowAdapterClass = Class.forName(CustomClass);

      SO = PackageStub.getStubObject(viewList,"MainWindowBottom");
      CustomClass = SO.getString("class",null);
//      WindowStatusClass = Class.forName(CustomClass);

    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  public static void builderRootView(Container root,ViewContainer vc) {
    try {
      // ViewDevice
      IViewDevice ViewDevice = builderViewDevice(vc);
      vc.setViewDevice(ViewDevice);
//      root.add(ViewDevice.getDeviceComponent(), BorderLayout.CENTER);
      IView view = null;
      // TopView
      view = ViewBuilder.builderTopView(vc);
      vc.setTopView(view);
      view.setID("TopView");
      ViewDevice.openView(view,BorderLayout.NORTH);
      view.setVisible(false);
      // ExplorerView
      IView ExplorerView = ViewBuilder.builderExplorerView(vc);
      vc.setExplorerView(ExplorerView);
      ExplorerView.setID("ExplorerView");
      ViewDevice.openView(ExplorerView,BorderLayout.WEST);
//      ExplorerView.setVisible(false);
      // PropertyView
      IView PropertyView = ViewBuilder.builderPropertyView(vc);
      vc.setPropertyView(PropertyView);
      PropertyView.setID("PropertyView");
      ViewDevice.openView(PropertyView,BorderLayout.EAST);
      PropertyView.setVisible(false);
      // StructView
      IView StructView = ViewBuilder.builderStructView(vc);
      vc.setStructView(StructView);
      StructView.setID("StructView");
      ViewDevice.openView(StructView,IViewDevice.LEFT_BOTTOM);
//      StructView.setVisible(false);
      // ContentView
      IView ContentView = vc.getContentView();//ViewBuilder.builderContentView(vc);
      ContentView.setViewDevice(ViewDevice);
      vc.setContentView(ContentView);
      ContentView.setID("ContentView");
      ViewDevice.openView(ContentView,BorderLayout.CENTER,IView.VIEW_NO_TITLE);
//      ContentView.setVisible(false);
      // MessageView
      IView MessageView = ViewBuilder.builderMessageView(vc);
      vc.setMessageView(MessageView);
      MessageView.setID("MessageView");
      ViewDevice.openView(MessageView,BorderLayout.SOUTH);
//      MessageView.setVisible(false);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  public static IView buildDialogView(Object node) {
    IView dialogView = builderDialogView();
    return dialogView;
  }
  public static void InitOpenEnterpriseExplorer(EnterpriseFounder enterprise,Object args,Object o3,Object o4) {
    initViewClass();
  }

}
