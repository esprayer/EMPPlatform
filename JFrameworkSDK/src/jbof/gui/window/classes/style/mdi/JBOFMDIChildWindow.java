package jbof.gui.window.classes.style.mdi;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import com.core.xml.JBOFClass;
import com.efounder.eai.EAI;
import com.efounder.eai.application.classes.JBOFApplication;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;

import jbof.application.classes.JContextObject;
import jbof.gui.window.classes.JBOFChildWindow;
import jbof.gui.window.classes.style.mdi.imp.*;
import jcomponent.custom.swing.*;
import jframework.foundation.classes.JActiveDComDM;
import jframework.foundation.classes.JActiveObject;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JBOFMDIChildWindow extends JBOFChildWindow implements ActionListener {
  public JMenuBar                 MainMenu=null;
  public Vector                   ToolbarList = new Vector();
  public Vector                   XMLToolbarList = null;// = new Vector();
  public Vector                   MenuList    = new Vector();
  public Vector                   XMLStubMenuList = null;// = new Vector();
  public Vector                   PopupMenuList = new Vector();
  public Vector                   PopupStubMenuList = null;
  public JPopupMenu               TablePopupMenu = null;// = new JPopupMenu();
  public JMenuItem                WindowMenuItem = null;
  //----------------------------------------------------------------------------------------------
  //描述: 装入菜单
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Object LoadChildGUIMenu(String DefineFile,JComponent mm,String RB,Object AddObject,String TB,String MM,Object RBO) {
    if ( XMLStubMenuList == null ) XMLStubMenuList = new Vector();
    if ( XMLToolbarList == null ) XMLToolbarList = new Vector();
    JGUILoader SaxParser = new JGUILoader(this,Application,XMLStubMenuList,mm,XMLToolbarList,RB,RBO);
    return SaxParser.LoadGUIMenu(DefineFile,AddObject,TB,MM);
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入菜单
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Object LoadChildGUIMenu(String DefineFile,JComponent mm,String RB,Object AddObject,String TB,String MM) {
    if ( XMLStubMenuList == null ) XMLStubMenuList = new Vector();
    if ( XMLToolbarList == null ) XMLToolbarList = new Vector();
    JGUILoader SaxParser = new JGUILoader(this,Application,XMLStubMenuList,mm,XMLToolbarList,RB,this);
    return SaxParser.LoadGUIMenu(DefineFile,AddObject,TB,MM);
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Object LoadPopupGUIMenu(String DefineFile,JComponent mm,String RB,Object AddObject,String TB,String MM) {
    if ( PopupMenuList == null ) PopupMenuList = new Vector();
    if ( PopupStubMenuList == null ) PopupStubMenuList = new Vector();
    JGUILoader SaxParser = new JGUILoader(this,Application,PopupStubMenuList,mm,XMLToolbarList,RB,this);
    return SaxParser.LoadGUIMenu(DefineFile,AddObject,TB,MM);/*"toolbars","mainmenu"*/
  }
  public Object LoadPopupGUIMenu(String DefineFile,JComponent mm,String RB,Object AddObject,String TB,String MM,Object RBO) {
    if ( PopupMenuList == null ) PopupMenuList = new Vector();
    if ( PopupStubMenuList == null ) PopupStubMenuList = new Vector();
    JGUILoader SaxParser = new JGUILoader(this,Application,PopupStubMenuList,mm,XMLToolbarList,RB,RBO);
    return SaxParser.LoadGUIMenu(DefineFile,AddObject,TB,MM);/*"toolbars","mainmenu"*/
  }
  //----------------------------------------------------------------------------------------------
  //描述: 构�\uFFFD�函�\uFFFD
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JBOFMDIChildWindow() {
      super();
      try {
          jbPInit();
      } catch(Exception ex) {
          ex.printStackTrace();
      }
  }
  //----------------------------------------------------------------------------------------------
  //描述: 构�\uFFFD�函�\uFFFD
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JBOFMDIChildWindow(JBOFApplication App,JActiveObject AO) {
      super(App,AO);
      //LoadResource();
      try {
          jbPInit();
      } catch(Exception ex) {
          ex.printStackTrace();
      }
  }

  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JMenuItemStub FindMenuItem(String Name) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true )
        return MenuStub;
    }
    return null;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void setEnabled(String Name,boolean Enable) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setEnabled(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setEnabled(Enable);
      }
    }
  }
  public void setMenuText(String Name,String text) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        MenuStub.Caption = text;
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setText(text);
        if ( MenuStub.Button != null ) MenuStub.Button.setText(text);
      }
    }
  }

  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void setVisible(String Name,boolean Enable) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setVisible(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setVisible(Enable);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JMenuItemStub FindPopupMenuItem(String Name) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<PopupStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)PopupStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true )
        return MenuStub;
    }
    return null;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void setPopupPopupEnabled(String Name,boolean Enable) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<PopupStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)PopupStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setEnabled(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setEnabled(Enable);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void setPopupPopupVisible(String Name,boolean Enable) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<PopupStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)PopupStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setVisible(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setVisible(Enable);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void RegUserMenuBar(JMenuBar usermainmenu) {
    int i,Count;com.jidesoft.swing.JideMenu Menu;//MenuElement[] MEArray;MenuElement ME;
      if ( usermainmenu != null ) {
          try {
              //MEArray = MainMenu.getSubElements();
              //Count = MEArray.length;
              Count = usermainmenu.getMenuCount();
              for(i=0;i<Count;i++) {
                  //ME = MEArray[i];
                  //MenuList.add(ME);
                  Menu = (com.jidesoft.swing.JideMenu)usermainmenu.getMenu(i);
                  MenuList.add(Menu);
              }
          }catch(Exception e) {
              e.printStackTrace();
          }
          if ( MainMenu == null ) {
            MainMenu = usermainmenu;
          }
      }
  }
  //TablePopupMenu = null;// = new JPopupMenu();
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void RegUserPopopMenu(JPopupMenu usermainmenu) {
    int i,Count;Component Menu;//MenuElement[] MEArray;MenuElement ME;
    TablePopupMenu = usermainmenu;
      if ( TablePopupMenu != null ) {
          try {
              Count = TablePopupMenu.getComponentCount();// .getMenuCount();
              for(i=0;i<Count;i++) {
                  Menu = TablePopupMenu.getComponent(i);// .getMenu(i);
                  PopupMenuList.add(Menu);
              }
          }catch(Exception e) {
              e.printStackTrace();
          }
      }
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void UnregUsermanuBar(JMenuBar usermainmenu) {
      MainMenu = null;
      MenuList.removeAllElements();
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void UnregUserPopopMenu(JPopupMenu usermainmenu) {
      TablePopupMenu = null;
      PopupMenuList.removeAllElements();
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void RegUserToolbar(JBOFToolBar usertoolbar) {
      if ( usertoolbar != null ) {
          ToolbarList.add(usertoolbar);
      }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void setToolbarVisible(String Name,boolean Visible) {
    JBOFToolBar tb;
    tb = getToolbar(Name);
    if ( tb != null ) tb.setVisible(Visible);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public boolean getToolbarVisible(String Name) {
    JBOFToolBar tb;
    tb = getToolbar(Name);
    if ( tb != null ) return tb.isVisible();
    return false;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  JBOFToolBar getToolbar(String Name) {
    JBOFToolBar tb;String ID;
    for(int i=0;i<ToolbarList.size();i++) {
      tb = (JBOFToolBar)ToolbarList.get(i);
      ID = tb.getToolBarID();
      if ( ID != null && ID.equals(Name) ) return tb;
    }
    return null;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void RegXMLToolbarList(Vector List) {
    JBOFToolBar usertoolbar;
    if ( List == null ) return;
    for(int i=0;i<List.size();i++) {
      usertoolbar = (JBOFToolBar)List.get(i);
      if ( usertoolbar != null ) {
          ToolbarList.add(usertoolbar);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void UnregXMLToolbarList(Vector List) {
    JBOFToolBar usertoolbar;
    if ( List == null ) return;
    for(int i=0;i<List.size();i++) {
      usertoolbar = (JBOFToolBar)List.get(i);
      if ( usertoolbar != null ) {
          ToolbarList.remove(usertoolbar);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void UnregUserToolbar(JBOFToolBar usertoolbar) {
      if ( usertoolbar != null ) {
          ToolbarList.remove(usertoolbar);
      }
  }
  //----------------------------------------------------------------------------------------------
  //描述: 构�\uFFFD�函�\uFFFD
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  void jbPInit() throws Exception {
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void CallBackContextObject(JContextObject CO) {
    return;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void actionPerformed(ActionEvent e) {
    String AM = e.getActionCommand();
    if ( AM.equals("ActiveLive") ) {
      this.MainWindow.setActiveWindow(this);
      return;
    }
    if ( ActiveObject == null ) return;
    int i,Count;JMenuItemStub MIS = null;JContextObject CO=new JContextObject();
    // 设置系统参数不清
    CO.Application   = Application;
    CO.ActiveObject  = ActiveObject;
    CO.MainWindow    = (JBOFMDIMainWindow)MainWindow;
    CO.ChildWindow   = this;
    // 回调获取对象参数
    CallBackContextObject(CO);
      Object Sender = e.getSource();String ActionCommand = e.getActionCommand();
      MIS = FindMenuItem(ActionCommand);
      if ( MIS == null ) MIS = FindPopupMenuItem(ActionCommand);
      if ( MIS != null ) {
        CallOperateObjectMethod(CO,MIS,e);
      }
      return;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  void CallOperateObjectMethod(JContextObject CO,JMenuItemStub MIS,ActionEvent AE) {
    String ActionCommand,MethodName;Object CallObject=null;Class CallCalss;
    ActionCommand = AE.getActionCommand();
    MethodName    = "Action"+ActionCommand;
    if ( !CheckFunctionLimit(MIS) ) {
      JOptionPane.showMessageDialog(this, "没有使用此功能的权限�\uFFFD", "系统提示",JOptionPane.ERROR_MESSAGE);
      return ;
    }
      try {
        if ( MIS.OperateItem != null && MIS.OperateItem.trim().length() == 0 ) MIS.OperateItem = null;
        if ( MIS.OperateItem != null ) {
          CallObject = JMenuItemStub.ObjectList.get(MIS.OperateItem);
          if ( CallObject == null ) {
            CallCalss = Class.forName(MIS.OperateItem);
            CallObject = CallCalss.newInstance();
            JMenuItemStub.ObjectList.put(MIS.OperateItem,CallObject);
          }
        }
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    if ( CallObject == null ) {
      // 找不到此功能的实现类
      JOptionPane.showMessageDialog(this,"找不能该功能的实现类,请检查功能定义文�\uFFFD.","系统提示",JOptionPane.ERROR_MESSAGE);
    } else {
      JBOFClass.CallObjectMethod(CallObject,MethodName,CO,MIS,AE,null);
    }
  }
  boolean CheckFunctionLimit(JMenuItemStub MIS) {
    if ( MIS.OperateNo == null || MIS.OperateNo.trim().length() == 0 )  {
      return true;
    }
//    JParamObject PO = (JParamObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("EnvironmentObject", "CreateParamObjectDOM");
    JParamObject PO = JParamObject.Create();
    PO.SetValueByParamName("FunctionNo", MIS.OperateNo); // 此项属于参数
    JResponseObject RO = null;
	try {
		RO = (JResponseObject) EAI.DAL.IOM("SecurityObject", "CheckFunctionLimit", PO);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    if (RO != null && RO.GetErrorCode() == 0 || JActiveDComDM.SystemOffline ) {
      return true;
    }
    return false;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void setMenuEnabled(String Name,boolean Enable) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setEnabled(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setEnabled(Enable);
      }
    }
    for(int i=0;i<PopupStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)PopupStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setEnabled(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setEnabled(Enable);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public boolean getMenuEnabled(String Name) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isEnabled();
        if ( MenuStub.Button != null ) return MenuStub.Button.isEnabled();
      }
    }
    for(int i=0;i<PopupStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)PopupStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isEnabled();
        if ( MenuStub.Button != null ) return MenuStub.Button.isEnabled();
      }
    }
    return false;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void setMenuVisible(String Name,boolean Enable) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setVisible(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setVisible(Enable);
      }
    }
    for(int i=0;i<PopupStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)PopupStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setVisible(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setVisible(Enable);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public boolean getMenuVisible(String Name) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isVisible();
        if ( MenuStub.Button != null ) MenuStub.Button.isVisible();
      }
    }
    for(int i=0;i<PopupStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)PopupStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isVisible();
        if ( MenuStub.Button != null ) MenuStub.Button.isVisible();
      }
    }
    return false;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void setMenuSelected(String Name,boolean Selected) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setSelected(Selected);
        if ( MenuStub.Button != null ) MenuStub.Button.setSelected(Selected);
      }
    }
    for(int i=0;i<PopupStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)PopupStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setSelected(Selected);
        if ( MenuStub.Button != null ) MenuStub.Button.setSelected(Selected);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //描述: 装入资源
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public boolean getMenuSelected(String Name) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isSelected();
        if ( MenuStub.Button != null ) return MenuStub.Button.isSelected();
      }
    }
    for(int i=0;i<PopupStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)PopupStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isSelected();
        if ( MenuStub.Button != null ) return MenuStub.Button.isSelected();
      }
    }
    return false;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public String getTitleAt() {
    if(title==null)
      if ( MainWindow != null ) return this.MainWindow.getTitleAt(this); else return "TEST";
    else
      return title;
  }
  public String getTitle(){
  return getTitleAt();
}
  public String toString() {
    return getTitleAt();
  }
  public Icon getIcon() {
    return MainWindow.getIcon(this);
  }

}
