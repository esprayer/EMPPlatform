package jbof.gui.window.classes.style.mdi.imp;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.jdom.*;

import com.core.xml.JBOFClass;
import com.efounder.eai.application.classes.JBOFApplication;

import jbof.application.classes.*;
import jcomponent.custom.swing.JBOFToolBar;
import jframework.resource.classes.*;
import jtoolkit.xml.classes.*;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */

public class JGUILoader {
  private ActionListener         EventListener=null;
  private JBOFApplication         Application=null;
  Font   MenuFont = new java.awt.Font("dialog", 0, 12);
  Vector SystemMenuList;

  private Document doc = null;
  private String resourcebase;
  private Object resourceobject;
  private JComponent MainMenu;
  private Vector ToolBarList;
  //------------------------------------------------------------------------------------------------
  //描述: 构�\uFFFD�函�\uFFFD
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JGUILoader(ActionListener EL,JBOFApplication APP,Vector MList,
                    JComponent mm,Vector TL,String RB,Object RBO) {
      MainMenu = mm;
      ToolBarList = TL;
      EventListener = EL;
      Application = APP;
      SystemMenuList = MList;
      resourcebase = RB;
      resourceobject = RBO;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void CreateToolbar(JDOMXMLBaseObject DOM,String Toolbars) {
    Element element = DOM.GetElementByName(Toolbars);
    if ( element == null ) return;
    java.util.List nodelist = DOM.BeginEnumerate(element);
    int Index = 0;String NNNN;JBOFToolBar MainToolBar;int Layout=1;boolean Visible=false;
    while ( nodelist != null ) {
      element = DOM.Enumerate(nodelist,Index++);
      if ( element == null ) break;
      NNNN = DOM.GetElementValue(element,"layout");
      if ( NNNN != null && NNNN.trim().length() != 0 ) {
        Layout = Integer.valueOf(NNNN).intValue();
      }
      Visible=false;
      NNNN = DOM.GetElementValue(element,"visible");
      if ( NNNN != null && NNNN.trim().length() != 0 && NNNN.trim().equals("1")) {
        Visible = true;
      }
      NNNN = DOM.GetElementValue(element,"name");
      MainToolBar = new JBOFToolBar(Layout,Visible);
      MainToolBar.setToolBarID(NNNN);
      NNNN = DOM.GetElementValue(element,"des");
      MainToolBar.setName(NNNN);
      ToolBarList.add(MainToolBar);
    }
    DOM.EndEnumerate();
  }
  //----------------------------------------------------------------------------------------------
  //描述: 模块定义文件的解析器解析过程
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:"toolbars","mainmenu"
  //----------------------------------------------------------------------------------------------
  public Object LoadGUIMenu(String uri,Object p,String Toolbars,String Menus) {
    JDOMXMLBaseObject parser;
    if (p == null) {
      parser = new JDOMXMLBaseObject();
    }
    else {
      parser = (JDOMXMLBaseObject) p;
    }
    try {
      parser.InitXMLURI(uri);
      CreateToolbar(parser, Toolbars);
      CreateMainMenu(parser, Menus);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return parser;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void CreateMainMenu(JDOMXMLBaseObject DOM,String Menus) {
    Element element = DOM.GetElementByName(Menus);int Index = 0;
    if ( element == null ) return;
    java.util.List nodelist = DOM.BeginEnumerate(element);
    while ( nodelist != null ) {
      element = DOM.Enumerate(nodelist,Index++);
      if ( element == null ) break;
      CreateMenu(DOM,element,null,null);
    }
    DOM.EndEnumerate();
  }
  //----------------------------------------------------------------------------------------------
  //描述: 模块定义文件的解析器解析过程
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void CreateMenu(JDOMXMLBaseObject DOM,Element MenuNode,Element Parent,com.jidesoft.swing.JideMenu ParentMenu) {
    com.jidesoft.swing.JideMenu Menu=null;JMenuItem MenuItem=null;Element Element=null;
    java.util.List nodelist;int i,Count;String NNNN=null,sTemp;JMenuItemStub MenuStub=null;
      // 创建当前节点菜单,如果caption=="-"，创建分割符
      NNNN = DOM.GetElementValue(MenuNode,"caption");
      if ( NNNN.compareTo("-") == 0 ) {
        if ( ParentMenu != null ) {
          ParentMenu.addSeparator();return;
        } else {
          if ( MainMenu instanceof JPopupMenu )
               ((JPopupMenu)MainMenu).addSeparator();return;
        }
      }
      if ( MenuNode.hasChildren() ) { // 有下级菜�\uFFFD
          Menu = new com.jidesoft.swing.JideMenu();Menu.setFont(MenuFont);Menu.setText(NNNN);
          char K;
          NNNN = DOM.GetElementValue(MenuNode,"selchar");
          if ( NNNN.length() > 0 ) {
            K = NNNN.charAt(0);
            Menu.setMnemonic(K);
          }
          if ( ParentMenu == null ) {
              MainMenu.add(Menu);
          } else {
              ParentMenu.add(Menu);
          }
      } else {
//          if ( ParentMenu == null ) {
//              Menu = new com.jidesoft.swing.JideMenu(); Menu.setFont(MenuFont);Menu.setText(NNNN);MainMenu.add(Menu);
//          } else {
            int Type=0;
            sTemp = DOM.GetElementValue(MenuNode,"type");
            if ( sTemp != null && sTemp.trim().length() != 0 ) {
              Type = Integer.valueOf(sTemp).intValue();
            }
             switch ( Type ) {
               case 0:
                 MenuItem = new JMenuItem();break;
               case 1:
                 MenuItem = new JCheckBoxMenuItem();break;
               case 2:
                 MenuItem = new JRadioButtonMenuItem();break;
               case 3:
//                 MenuItem = new JCheckBoxMenuItem();MenuItem.setVisible(false);break;
                 MenuItem = null;break;
               case 4:
//                 MenuItem = new JRadioButtonMenuItem();MenuItem.setVisible(false);break;
                 MenuItem = null;break;
               case 5:
//                 MenuItem = new JMenuItem();MenuItem.setVisible(false);break;
                 MenuItem = null;break;
               case 6:
                 MenuItem = new JMenuItem();MenuItem.setVisible(false);break;
               default:
//                 MenuItem = new JMenuItem();
//                 MenuItem.setVisible(false);
                 MenuItem = null;
                 break;
             }
             if ( MenuItem != null ) {
               MenuItem.setFont(MenuFont);MenuItem.setText(NNNN);
               if ( ParentMenu != null ) {
                 ParentMenu.add(MenuItem);
               } else {
                 MainMenu.add(MenuItem);
               }
             }
//          }
      }//-----------------------------------------------------------------------------------------
      MenuStub    = new JMenuItemStub(EventListener,Application);
      SystemMenuList.add(MenuStub);
      MenuStub.MenuItem               = MenuItem;
      CreateMenuItem(DOM,MenuStub,MenuNode,EventListener);
      CreateToolBar(DOM,MenuStub,MenuNode,EventListener);
      // �\uFFFD查菜单或按钮的状�\uFFFD
      CheckMenuStatus(MenuStub);
      if ( MenuItem != null ) {

          //JMenu_EventAdapter  MenuEvent   = new JMenu_EventAdapter(MenuStub);
          MenuStub.MenuItem               = MenuItem;
          MenuStub.Menu = null;
          MenuItem.addActionListener(EventListener);
//          CreateMenuItem(MenuStub,attrs,EventListener);
          MenuItem.setName(MenuStub.Name);
          MenuItem.setActionCommand(MenuStub.Name);
      }
      if ( Menu != null ) {
          MenuStub.Menu = Menu;
          MenuStub.MenuItem = Menu;
          Menu.setName(MenuStub.Name);
//          CreateMenuItem(MenuStub,attrs,null);
      }

      // 创建当前节点的子节点
      if ( MenuNode.hasChildren() ) {
          nodelist = DOM.BeginEnumerate(MenuNode);
          Element e;int Index=0;
          while ( nodelist != null ) {
            e = DOM.Enumerate(nodelist,Index++);
            if ( e == null ) break;
            CreateMenu(DOM,e,MenuNode,Menu);
          }
          DOM.EndEnumerate();
      }//-----------------------------------------------------------------------------------------
  }
  void CheckMenuStatus(JMenuItemStub mis) {
    JBOFClass.CallObjectMethod(resourceobject,"CheckChildMenuStatus",mis);
  }
  //----------------------------------------------------------------------------------------------
  //描述: 模块定义文件的解析器解析过程
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  private void CreateMenuItem(JDOMXMLBaseObject DOM,JMenuItemStub MenuStub,Element MenuNode,ActionListener MenuEvent) {
  //<menuitem2 name="new" caption="新建..." operateitem="CreateNewReport" icon="1.gif" toolbar="1"/>
    Element Element;String NNNN;char K;

      MenuStub.Name = DOM.GetElementValue(MenuNode,"name");
      MenuStub.Caption = DOM.GetElementValue(MenuNode,"caption");
      MenuStub.OperateItem = DOM.GetElementValue(MenuNode,"operateitem");
      MenuStub.OperateNo   = DOM.GetElementValue(MenuNode,"operateno");
      MenuStub.IsPublic = DOM.GetElementValue(MenuNode,"public");
      MenuStub.Icon = DOM.GetElementValue(MenuNode,"icon");


      NNNN = DOM.GetElementValue(MenuNode, "type");
      if (NNNN != null && NNNN.trim().length() != 0) {
        MenuStub.Type = Integer.valueOf(NNNN).intValue();
      }

        NNNN = DOM.GetElementValue(MenuNode,"toolbar");
        if ( NNNN != null && NNNN.trim().length() != 0 ) {
          MenuStub.Toolbar = Integer.valueOf(NNNN).intValue();
        }
      if ( MenuStub.Icon != null && MenuStub.Icon.length() != 0 ) {
          try {
              //URL url = new URL(resourcebase+MenuStub.Icon);
              //MenuStub.Image = new ImageIcon(url);
            if ( resourceobject == null )
              MenuStub.Image = JXMLResource.LoadSImageIcon(resourcebase,MenuStub.Icon);
            else
              MenuStub.Image = JXMLResource.LoadImageIcon(resourceobject,MenuStub.Icon);
              if ( MenuStub.MenuItem != null )
                MenuStub.MenuItem.setIcon(MenuStub.Image);
          } catch ( Exception e ) {e.printStackTrace();}
      }
      if ( MenuStub.MenuItem != null ) {
        NNNN = DOM.GetElementValue(MenuNode, "selchar");
        if (NNNN.length() > 0) {
          K = NNNN.charAt(0);
          MenuStub.MenuItem.setMnemonic(K);
        }
          String Key;
          Key = DOM.GetElementValue(MenuNode, "keychar");
          if ( Key.length() == 1 ) {
            K = Key.charAt(0);
              NNNN = DOM.GetElementValue(MenuNode,"keymask");
              if ( NNNN.equalsIgnoreCase("ALT") ) {
                MenuStub.KS = KeyStroke.getKeyStroke(K,Event.ALT_MASK);
                MenuStub.MenuItem.setAccelerator(MenuStub.KS);
              }
              if ( NNNN.equalsIgnoreCase("CTRL") ) {
                MenuStub.KS = KeyStroke.getKeyStroke(K,Event.CTRL_MASK);
                MenuStub.MenuItem.setAccelerator(MenuStub.KS);
              }
              if ( NNNN.equalsIgnoreCase("SHIFT") ) {
                MenuStub.KS = KeyStroke.getKeyStroke(K,Event.SHIFT_MASK);
                MenuStub.MenuItem.setAccelerator(MenuStub.KS);
              }
          }
          if ( Key.length() > 1 ) {
            int KeyCode = Integer.valueOf(Key).intValue();
            NNNN = DOM.GetElementValue(MenuNode,"keymask");
            if ( NNNN != null && NNNN.trim().length() == 0 ) NNNN = null;
            if ( NNNN != null ) {
              if ( NNNN.equalsIgnoreCase("ALT") ) {
                MenuStub.KS = KeyStroke.getKeyStroke(KeyCode,Event.ALT_MASK);
                MenuStub.MenuItem.setAccelerator(MenuStub.KS);
              }
              if ( NNNN.equalsIgnoreCase("CTRL") ) {
                MenuStub.KS = KeyStroke.getKeyStroke(KeyCode,Event.CTRL_MASK);
                MenuStub.MenuItem.setAccelerator(MenuStub.KS);
              }
              if ( NNNN.equalsIgnoreCase("SHIFT") ) {
                MenuStub.KS = KeyStroke.getKeyStroke(KeyCode,Event.SHIFT_MASK);
                MenuStub.MenuItem.setAccelerator(MenuStub.KS);
              }
            } else {
              MenuStub.KS = KeyStroke.getKeyStroke(KeyCode,0);
              MenuStub.MenuItem.setAccelerator(MenuStub.KS);
            }
          }
      }
  }
  //----------------------------------------------------------------------------------------------
  //描述: 模块定义文件的解析器解析过程
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  private void CreateToolBar(JDOMXMLBaseObject DOM,JMenuItemStub MenuStub,Element MenuNode,ActionListener MenuEvent) {
    JBOFToolBar MainToolBar;
      if ( MenuStub.Toolbar != 0 ) {
          MainToolBar = (JBOFToolBar)this.ToolBarList.get(MenuStub.Toolbar-1);
          switch ( MenuStub.Type ) {
            case 1:
            case 3:
              MenuStub.Button = new com.jidesoft.swing.JideToggleButton();break;
            case 2:
            case 4:
              MenuStub.Button = new com.jidesoft.swing.JideToggleButton();break;
            default:
              MenuStub.Button = new com.jidesoft.swing.JideButton();break;
          }
          MenuStub.Button.setSize(30,30);
//          MenuStub.Button.setBorder(null);
          MenuStub.Button.setOpaque(false);
          MenuStub.Button.setName(MenuStub.Name);
          MenuStub.Button.setIcon(MenuStub.Image);
          MenuStub.Button.setFont(MenuFont);
          MenuStub.Button.setToolTipText(MenuStub.Caption);
          MenuStub.Button.setActionCommand(MenuStub.Name);
          MainToolBar.add(MenuStub.Button);
          MenuStub.Button.addActionListener(MenuEvent);
//          MenuStub.Button.setMargin(new Insets(4,4,4,4));
      }
    }
}
