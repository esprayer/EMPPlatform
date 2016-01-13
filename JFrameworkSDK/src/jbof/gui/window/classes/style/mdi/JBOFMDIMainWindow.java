package jbof.gui.window.classes.style.mdi;

import java.net.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import com.borland.jbcl.layout.*;
import com.core.xml.JBOFClass;
import com.efounder.eai.EAI;
import com.efounder.eai.application.classes.JBOFApplication;
import com.efounder.pub.comp.CompoundIcon;
import com.swing.tablepane.CloseAndMaxTabbedPane;
import com.swing.tablepane.CloseListener;

import jbof.application.classes.*;
import jbof.gui.window.classes.*;
import jbof.gui.window.classes.style.mdi.imp.*;
import jcomponent.custom.swing.*;
import jfoundation.bridge.classes.*;
import jframework.foundation.classes.*;
import jframework.log.JLogManager;
import jframework.resource.classes.*;


/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//锟斤拷锟斤拷:
//锟斤拷锟? Skyline(2001.04.22)
//实锟斤拷: Skyline
//锟睫革拷:
//--------------------------------------------------------------------------------------------------
public class JBOFMDIMainWindow extends JBOFMainWindow implements ActionListener, ChangeListener, WindowListener, MouseListener, Runnable, IObjectStatusChange, CloseListener, com.swing.tablepane.PopupMenuListener {
  static ResourceBundle mPublicStrings = null;

  public Vector                   MenuList = new Vector();

  public Vector                   PopupMenuList = new Vector();
  public Vector                   ToolbarList = new Vector();
  public JWaitThread WaitThread = null;
//  public JBOFToolBar MainToolBar = new JBOFToolBar(1);
  public JBOFToolBar MainToolBar = null;

  public JBOFMDIChildWindow CurrentChildWindow = null;

  public JPanel contentPane = new JPanel();
  public BorderLayout MainborderLayout = new BorderLayout();
  public JMenuBar MainMenu = new JMenuBar();

  public com.jidesoft.swing.JideMenu mnFile   = null;
  public com.jidesoft.swing.JideMenu mnEdit   = null;
  public com.jidesoft.swing.JideMenu mnView   = null;
  public com.jidesoft.swing.JideMenu mnToolbar= null;
  public com.jidesoft.swing.JideMenu mnTools  = null;
  public com.jidesoft.swing.JideMenu mnWindow = null;
  public com.jidesoft.swing.JideMenu mnHelp   = null;
  public JMenuItem mnStatus = null;
  public JMenuItem mnMainToolbar = null;
  ImageIcon CloseImageIcon = null;

  Vector WindowMenuList = new Vector();
  Vector ToolbarMenuList= new Vector();


  public CloseAndMaxTabbedPane MainTabbedPane = new CloseAndMaxTabbedPane(true);
  javax.swing.JPopupMenu.Separator MenuSp   = new javax.swing.JPopupMenu.Separator();
  TitledBorder titledBorder1;
  JPopupMenu TablePopupMenu = new JPopupMenu();
  JPanel pnToolbar = new JPanel();
//  public Font MenuFont        = new java.awt.Font("dialog", 0, 12);
  private JPanel pnBottom = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel pnToolbar2 = new JPanel();
  JPanel pnToolbar1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  FlowLayout flowLayout2 = new FlowLayout();

  JPanel pnToolbar3 = new JPanel();
  FlowLayout flowLayout5 = new FlowLayout();
  JPanel pnStatus = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JProgressBar pbProgressBar = new JProgressBar();
  JPanel pnBottom2 = new JPanel();
  JPanel pnBottom12 = new JPanel();
  JPanel pnBottom11 = new JPanel();
//  javax.swing.JToggleButton bnOffline = new javax.swing.JToggleButton();
  JPanel pnBottom1 = new JPanel();
  JLabel statusLabel1 = new JLabel();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JLabel statusLabel2 = new JLabel();
  BorderLayout borderLayout6 = new BorderLayout();
  JLabel statusLabel3 = new JLabel();
  JPanel jPanel2 = new JPanel();
  JLabel statusLabel4 = new JLabel();
  BorderLayout borderLayout7 = new BorderLayout();
  JCheckBox bnOffline = new JCheckBox();
  BorderLayout borderLayout8 = new BorderLayout();
  JLabel lbSystemSSL = new JLabel();
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 锟斤拷锟届函锟斤�\uFFFD
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public JBOFMDIMainWindow() {
      super();
      //addMouseListener(this);
      try {
          jbInit();

          MainTabbedPane.addCloseListener(this);
          MainTabbedPane.addPopupMenuListener(this);
      }
      catch(Exception e) {
          e.printStackTrace();
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 锟斤拷锟届函锟斤�\uFFFD(锟结供一锟斤拷锟斤拷源装锟斤拷锟斤拷)
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public JBOFMDIMainWindow(JBOFApplication App) {
      super();
      //addMouseListener(this);
      ParentObject = App;
      Application = App;
      if ( Application != null ) {
          if ( Application.getMainWindow() == null ) Application.setMainWindow(this);
      }
      try {
          jbInit();
      }
      catch(Exception e) {
          e.printStackTrace();
      }

      MainTabbedPane.addCloseListener(this);
      MainTabbedPane.addPopupMenuListener(this);

  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 装锟斤拷说锟?
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  private void LoadGUIMenu() {
    String RB=null;
    WindowIcon = JXMLResource.LoadSImageIcon(Application.BOFApplicationStub.name+"/Resource",Application.BOFApplicationStub.img1);
    RB = Application.BOFApplicationStub.name+"/Resource";
    JGUILoader SaxParser = new JGUILoader(this,Application,SystemMenuList,MainMenu,ToolbarList,RB,null);
    String XMLGuiDefineFile;
//      XMLGuiDefineFile  = JActiveDComDM.CodeBase+Application.BOFApplicationStub.name+"/Resource/";
//      XMLGuiDefineFile += JActiveDComDM.International+"/"+Application.BOFApplicationStub.XML_STYLE_MDI;
      URL url = JXMLResource.LoadSXML(Application.BOFApplicationStub.name+"/Resource/",Application.BOFApplicationStub.XML_STYLE_MDI);
      try {
        XMLGuiDefineFile = url.toString();
        SaxParser.LoadGUIMenu(XMLGuiDefineFile, null, "toolbars", "mainmenu");
      } catch ( Exception e ) {
        e.printStackTrace();
      }
      if ( ToolbarList.size() > 0 )
        MainToolBar = (JBOFToolBar)ToolbarList.get(0);
      FindSystemMainMenu();
//      JCheckBoxMenuItem mi = new JCheckBoxMenuItem();
//      mi.setText(MainToolBar.getName());
//      mi.setActionCommand(MainToolBar.getToolBarID());
//      mi.setSelected(true);
//      mnToolbar.add(mi);
      LoadPopopMenu();
  }
  private void LoadPopopMenu() {
    String URI=null,RB=null;
    RB = this.getClass().getName().replace('.','/');;
    RB = "Codebase/"+RB;
//    URI = JActiveDComDM.CodeBase+RB+"/"+JActiveDComDM.International+"/TabPopupMenu.xml";
    URL url = JXMLResource.LoadXML(this,"TabPopupMenu.xml");
    URI = url.toString();
    JGUILoader SaxParser = new JGUILoader(this,Application,PopupMenuList,TablePopupMenu,ToolbarList,RB,this);
    SaxParser.LoadGUIMenu(URI,null,"toolbars","mainmenu");
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 装锟斤拷说锟?
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void FindSystemMainMenu() {
    JMenuItemStub MenuStub;
    for(int i=0;i<SystemMenuList.size();i++) {
      MenuStub = (JMenuItemStub)SystemMenuList.get(i);
      if ( MenuStub.Name.equals("mnFile") == true )
        mnFile   = MenuStub.Menu;
      if ( MenuStub.Name.equals("mnEdit") == true )
        mnEdit   = MenuStub.Menu;
      if ( MenuStub.Name.equals("mnTools")== true )
        mnTools   = MenuStub.Menu;
      if ( MenuStub.Name.equals("mnWindow")== true )
        mnWindow   = MenuStub.Menu;
      if ( MenuStub.Name.equals("mnHelp") == true )
        mnHelp   = MenuStub.Menu;
      if ( MenuStub.Name.equals("mnView") == true )
        mnView   = MenuStub.Menu;
      if ( MenuStub.Name.equals("mnToolbar") == true )
        mnToolbar   = MenuStub.Menu;
      if ( MenuStub.Name.equals("mnStatusbar") == true ) {
        mnStatus   = MenuStub.MenuItem;
        if ( mnStatus != null ) mnStatus.setSelected(true);
      }
      if ( MenuStub.Name.equals("SystemToolbar") == true ) {
        mnMainToolbar   = MenuStub.MenuItem;
        if ( mnMainToolbar != null ) mnMainToolbar.setSelected(true);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 装锟斤拷锟斤拷源
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public JMenuItemStub FindMenuItem(String Name) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<SystemMenuList.size();i++) {
      MenuStub = (JMenuItemStub)SystemMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true )
        return MenuStub;
    }
    JBOFMDIChildWindow FWP;
    FWP = (JBOFMDIChildWindow)MainTabbedPane.getSelectedComponent();
    if ( FWP == null ) return null;
    for(int i=0;i<FWP.XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)FWP.XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true )
        return MenuStub;
    }
    return null;
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 装锟斤拷锟斤拷源
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void setMenuEnabled(String Name,boolean Enable) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<SystemMenuList.size();i++) {
      MenuStub = (JMenuItemStub)SystemMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setEnabled(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setEnabled(Enable);
      }
    }
    JBOFMDIChildWindow FWP;
    FWP = (JBOFMDIChildWindow)MainTabbedPane.getSelectedComponent();
    if ( FWP == null ) return;
    for(int i=0;i<FWP.XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)FWP.XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setEnabled(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setEnabled(Enable);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 装锟斤拷锟斤拷源
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public boolean getMenuEnabled(String Name) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<SystemMenuList.size();i++) {
      MenuStub = (JMenuItemStub)SystemMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isEnabled();
        if ( MenuStub.Button != null ) return MenuStub.Button.isEnabled();
      }
    }
    JBOFMDIChildWindow FWP;
    FWP = (JBOFMDIChildWindow)MainTabbedPane.getSelectedComponent();
    if ( FWP == null ) return false;
    for(int i=0;i<FWP.XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)FWP.XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isEnabled();
        if ( MenuStub.Button != null ) return MenuStub.Button.isEnabled();
      }
    }
    return false;
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 装锟斤拷锟斤拷源
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void setMenuVisible(String Name,boolean Enable) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<SystemMenuList.size();i++) {
      MenuStub = (JMenuItemStub)SystemMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setVisible(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setVisible(Enable);
      }
    }
    JBOFMDIChildWindow FWP;
    FWP = (JBOFMDIChildWindow)MainTabbedPane.getSelectedComponent();
    if ( FWP == null ) return;
    for(int i=0;i<FWP.XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)FWP.XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setVisible(Enable);
        if ( MenuStub.Button != null ) MenuStub.Button.setVisible(Enable);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 装锟斤拷锟斤拷源
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public boolean getMenuVisible(String Name) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<SystemMenuList.size();i++) {
      MenuStub = (JMenuItemStub)SystemMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isVisible();
        if ( MenuStub.Button != null ) MenuStub.Button.isVisible();
      }
    }
    JBOFMDIChildWindow FWP;
    FWP = (JBOFMDIChildWindow)MainTabbedPane.getSelectedComponent();
    if ( FWP == null ) return false;
    for(int i=0;i<FWP.XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)FWP.XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isVisible();
        if ( MenuStub.Button != null ) MenuStub.Button.isVisible();
      }
    }
    return false;
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 装锟斤拷锟斤拷源
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void setMenuSelected(String Name,boolean Selected) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<SystemMenuList.size();i++) {
      MenuStub = (JMenuItemStub)SystemMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setSelected(Selected);
        if ( MenuStub.Button != null ) MenuStub.Button.setSelected(Selected);
      }
    }
    JBOFMDIChildWindow FWP;
    FWP = (JBOFMDIChildWindow)MainTabbedPane.getSelectedComponent();
    if ( FWP == null ) return;
    for(int i=0;i<FWP.XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)FWP.XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) MenuStub.MenuItem.setSelected(Selected);
        if ( MenuStub.Button != null ) MenuStub.Button.setSelected(Selected);
      }
    }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 装锟斤拷锟斤拷源
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public boolean getMenuSelected(String Name) {
    JMenuItemStub MenuStub=null;
    for(int i=0;i<SystemMenuList.size();i++) {
      MenuStub = (JMenuItemStub)SystemMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isSelected();
        if ( MenuStub.Button != null ) return MenuStub.Button.isSelected();
      }
    }
    JBOFMDIChildWindow FWP;
    FWP = (JBOFMDIChildWindow)MainTabbedPane.getSelectedComponent();
    if ( FWP == null ) return false;
    for(int i=0;i<FWP.XMLStubMenuList.size();i++) {
      MenuStub = (JMenuItemStub)FWP.XMLStubMenuList.get(i);
      if ( MenuStub.Name.equals(Name) == true ) {
        if ( MenuStub.MenuItem != null ) return MenuStub.MenuItem.isSelected();
        if ( MenuStub.Button != null ) return MenuStub.Button.isSelected();
      }
    }
    return false;
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 装锟斤拷锟斤拷源
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
//  protected void LoadResource(JDomXmlGUIResource guir) {
//      GUIResource = guir;
//      if ( GUIResource != null ) {
//          GUIResource.SetBOF(Application.DComDM.systembof,this);
//          GUIResource.LoadGUIResource();
//      }
// }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 应锟斤拷锟斤拷源
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  protected void ApplyResource() {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 锟斤拷锟斤拷锟斤拷源装锟斤拷锟斤�\uFFFD
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
//  public void SetResourceLoader(JDomXmlGUIResource guir) {
//      GUIResource = guir;
//  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 锟斤拷锟斤拷应锟斤拷状�\uFFFD�锟斤拷
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void SetApplicationStatus(String StatusString) {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 锟斤拷锟斤拷应锟矫憋拷锟斤拷
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void SetApplicationTitle(String title, Icon icon) {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 锟斤拷一锟斤拷锟斤拷蟠翱�\uFFFD?
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public Component OpenObjectWindow(String title,Icon icon, String tip,Component comp) {
    initCallBackWindowClazz();
    if (this.callBackWindowClazz != null) {
      // 锟斤拷锟矫回碉拷�\uFFFD?
      ( (JBOFChildWindow) comp).MainWindow = this;
       ( (JBOFChildWindow) comp).setTitle(title);
      callBackOpenObjectWindow(title, icon, tip, comp);
      return comp;
    }

      if ( comp != null ) {
        ((JBOFChildWindow)comp).MainWindow = this;
          if ( MainTabbedPane.indexOfComponent(comp) == -1 ) {

            //CompoundIcon compicon = new CompoundIcon(CloseImageIcon,icon,CompoundIcon.HORIZONTAL,0);

            //锟铰匡拷锟斤拷页锟芥都锟斤拷锟斤拷锟斤拷前锟芥�\uFFFD\uFFFD
            //MainTabbedPane.insertTab(title,icon,comp,tip,0);
            MainTabbedPane.addTab(title,icon,comp,tip);
            //MainTabbedPane.addTab(title,compicon,comp,tip);

            ((JBOFChildWindow)comp).WindowOpened();
            JMenuItem mi = new JMenuItem();
            mi.setText(title);mi.setIcon(icon);
            ((JBOFMDIChildWindow)comp).WindowMenuItem = mi;
            mi.setActionCommand("ActiveLive");
            mi.addActionListener((JBOFMDIChildWindow)comp);
            mnWindow.add(mi);
            CreateCloseButton(comp);

          }
          MainTabbedPane.setSelectedComponent(comp);
          MainTabbedPane.repaint();
      }

      return comp;
  }
  void CreateCloseButton(Component comp) {
//    Rectangle  rect;int Index;Icon CompIcon;
//    Index = MainTabbedPane.indexOfComponent(comp);
//    CompIcon = MainTabbedPane.getIconAt(Index);
//    BufferedImage  Image = new BufferedImage(CloseImageIcon.getIconWidth()+CompIcon.getIconWidth(),CompIcon.getIconHeight(),BufferedImage.TYPE_3BYTE_BGR);
//    Graphics gs = Image.getGraphics();
//    CompIcon.
//    gs.drawImage(CloseImageIcon.getImage(),0,0,CloseImageIcon.getIconWidth(),CloseImageIcon.getIconHeight(),CloseImageIcon.getImageObserver());
//    CompIcon.paintIcon(MainTabbedPane,gs,CloseImageIcon.getIconWidth(),0);
//    Image
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void setTitleAt(Component comp,String Title) {
    int Index;
    if ( comp != null ) {
        Index = MainTabbedPane.indexOfComponent(comp);
        if ( Index != -1 ) {
          MainTabbedPane.setTitleAt(Index,Title);
          if ( ((JBOFMDIChildWindow)comp).WindowMenuItem != null )
            ((JBOFMDIChildWindow)comp).WindowMenuItem.setText(Title);
        }
        MainTabbedPane.repaint();
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void setToolTipTextAt(Component comp,String Title) {
    int Index;
    if ( comp != null ) {
        Index = MainTabbedPane.indexOfComponent(comp);
        if ( Index != -1 )
          MainTabbedPane.setToolTipTextAt(Index,Title);
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void setIconAt(Component comp,Icon icon) {
    int Index;
    if ( comp != null ) {
        Index = MainTabbedPane.indexOfComponent(comp);
        if ( Index != -1 )
          MainTabbedPane.setIconAt(Index,icon);
        MainTabbedPane.repaint();
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 锟截憋拷锟斤拷锟叫讹拷锟襟窗匡拷
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public boolean CloseAllWindow() {
    JBOFMDIChildWindow FP;int i = 0,Count;boolean Res = true;
    Vector FPList = new Vector();
      Count = MainTabbedPane.getTabCount();// .getComponentCount();
      for(i=1;i<Count;i++) {
        FP = (JBOFMDIChildWindow)MainTabbedPane.getComponentAt(i);// .getComponent(i);
        FPList.add(FP);
      }
      Count = FPList.size();
      for(i=0;i<Count;i++) {
        FP = (JBOFMDIChildWindow)FPList.get(i);
//            if ( FP.Close() ) {
                Res = CloseObjectWindow(FP);
//            } else Res = false;
      }
      return Res;
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 锟截憋拷锟斤拷锟叫讹拷锟襟窗匡拷
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public JBOFChildWindow GetCurrentWindow() {
    JBOFChildWindow FP;
      FP = (JBOFChildWindow)MainTabbedPane.getSelectedComponent();
      return FP;
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 锟斤拷始锟斤拷锟截硷�\uFFFD
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  private void jbInit() throws Exception {
    this.MainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      LoadGUIMenu();
      contentPane = (JPanel) this.getContentPane();
      titledBorder1 = new TitledBorder("");
      contentPane.setLayout(MainborderLayout);
      this.setJMenuBar(MainMenu);
      this.addWindowListener(this);

      /**
       * 锟斤拷锟斤拷锟斤拷锟斤拷锟矫憋拷锟解�\uFFFD
       * 锟斤拷锟饺硷拷锟斤拷锟皆达拷锟斤拷锟斤拷欠锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷没锟叫ｏ拷锟斤拷取XML锟叫讹拷锟斤拷摹�\uFFFD?
       */
      String pTitle = null;
      try {
        if (mPublicStrings == null) {
          mPublicStrings = ResourceBundle.getBundle(
              "fmis.publicresource.Language");
        }
        pTitle = mPublicStrings.getString("SOFT_DESCRIPTION");
      }
      catch (Exception E) {

      }
      if (pTitle == null || pTitle.equals("")) {
        if (Application != null) {
          pTitle = Application.BOFApplicationStub.description;
        }
      }
      setTitle(pTitle);

      contentPane.setMinimumSize(new Dimension(87, 67));
      contentPane.setPreferredSize(new Dimension(400, 66));
      contentPane.setToolTipText("");
      //MainMenu.setFont(new java.awt.Font("dialog", 0, 12));
//      MenuSp.setFont(MenuFont);
      //MenuSp.setText("|");
      MenuSp.setEnabled(false);
      //MainMenu.setFont(new java.awt.Font("锟斤拷锟斤拷", 1, 12));

      MainTabbedPane.addChangeListener(this);

      //MainTabbedPane.addMouseListener(this);

      pnToolbar.setLayout(verticalFlowLayout1);
      pnBottom.setLayout(borderLayout1);
    verticalFlowLayout1.setHgap(0);
    verticalFlowLayout1.setVgap(0);
    verticalFlowLayout1.setVerticalFill(true);
    pnToolbar1.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    pnToolbar2.setLayout(flowLayout2);
    flowLayout2.setAlignment(FlowLayout.LEFT);
    flowLayout2.setHgap(0);
    flowLayout2.setVgap(0);
    //MainToolBar.setFont(MenuFont);
    pnToolbar3.setLayout(flowLayout5);
    flowLayout5.setAlignment(FlowLayout.LEFT);
    flowLayout5.setHgap(0);
    flowLayout5.setVgap(0);
    pnStatus.setLayout(borderLayout4);
    pbProgressBar.setBorder(titledBorder1);
    pbProgressBar.setPreferredSize(new Dimension(100, 28));
    pnBottom2.setLayout(borderLayout8);
    pnBottom12.setLayout(borderLayout5);
    pnBottom11.setLayout(borderLayout2);
    pnBottom1.setLayout(borderLayout3);
    statusLabel1.setBorder(titledBorder1);
    pnBottom2.setPreferredSize(new Dimension(100, 26));
    pnBottom1.setPreferredSize(new Dimension(83, 24));
    statusLabel2.setBorder(titledBorder1);
    statusLabel2.setToolTipText("");
    statusLabel2.setText(" ");
    jPanel1.setLayout(borderLayout6);
    statusLabel3.setBorder(titledBorder1);
    statusLabel3.setToolTipText("");
    statusLabel3.setText(" ");
    statusLabel4.setToolTipText("");
    statusLabel4.setText(" ");
    statusLabel4.setBorder(titledBorder1);
    jPanel2.setLayout(borderLayout7);
    bnOffline.setBorder(titledBorder1);
    bnOffline.setBorderPainted(true);
    bnOffline.setText("");
    bnOffline.setBorderPaintedFlat(true);
    pnStatus.setPreferredSize(new Dimension(183, 24));
    lbSystemSSL.setBorder(titledBorder1);
    pnBottom.add(pnToolbar3,  BorderLayout.NORTH);
    pnBottom.add(pnStatus, BorderLayout.SOUTH);
    pnStatus.add(pnBottom2,  BorderLayout.EAST);
    pnBottom2.add(pbProgressBar,  BorderLayout.CENTER);
    pnBottom2.add(lbSystemSSL,  BorderLayout.WEST);
    pnStatus.add(pnBottom1,  BorderLayout.CENTER);
    pnBottom1.add(pnBottom11,  BorderLayout.CENTER);
    pnBottom11.add(statusLabel1,  BorderLayout.CENTER);
    pnBottom1.add(pnBottom12,  BorderLayout.EAST);
//    pnBottom12.add(bnOffline,  BorderLayout.SOUTH);
    pnBottom12.add(jPanel1,  BorderLayout.CENTER);
    jPanel1.add(statusLabel2,  BorderLayout.CENTER);
    jPanel1.add(statusLabel3,  BorderLayout.EAST);
    pnBottom12.add(jPanel2,  BorderLayout.EAST);
    jPanel2.add(statusLabel4,  BorderLayout.CENTER);
    jPanel2.add(bnOffline,  BorderLayout.EAST);
    contentPane.add(MainTabbedPane,  BorderLayout.CENTER);
      contentPane.add(pnToolbar, BorderLayout.NORTH);
    pnToolbar.add(pnToolbar1, null);
    pnToolbar.add(pnToolbar2, null);
      //this.setFont(MenuFont);
      //pnToolbar.setFont(MenuFont);
      //MainMenu.add(MenuWindow);
      //MainMenu.add(MenuHelp);
//      TablePopupMenu.add(mniClose);
//      TablePopupMenu.add(mniXClose);
//      TablePopupMenu.add(mniCloseAll);
//      TablePopupMenu.addSeparator();
    contentPane.add(pnBottom, BorderLayout.SOUTH);
    pnToolbar1.add(MainToolBar, null);
      AddSystemMenu();
      MainTabbedPane.setBorder(BorderFactory.createLoweredBevelBorder());
      //MainTabbedPane.setBackground(Color.gray);
      ImageIcon II;
      II = JXMLResource.LoadImageIcon(this,"online.gif");
      bnOffline.setIcon(II);
      II = JXMLResource.LoadImageIcon(this,"offline.gif");
      bnOffline.setSelectedIcon(II);
      bnOffline.setSelected(JActiveDComDM.SystemOffline);
      bnOffline.addActionListener(this);
      if ( JActiveDComDM.SystemSSL )
        II = JXMLResource.LoadImageIcon(this,"lock.gif");
      else
        II = JXMLResource.LoadImageIcon(this,"unlock.gif");
      lbSystemSSL.setIcon(II);
      JActiveDComDM.addEventObject(this);
      CloseImageIcon = JXMLResource.LoadImageIcon(this,"close.gif");
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  private void AddSystemMenu() {
    /*
    int i,Count;JBOFApplication bis;JMenuItem mItem;
      Count = JActiveDComDM.BOFApplicationManager.ApplicationList.size();
      for(i=0;i<Count;i++) {
          bis = (JBOFApplication)JActiveDComDM.BOFApplicationManager.ApplicationList.elementAt(i);
          if ( !bis.BOFApplicationStub.name.equals(Application.BOFApplicationStub.name) ) {
              mItem = new JMenuItem();
              mItem.setFont(MenuFont);
              mItem.setText(bis.BOFApplicationStub.description);
              mItem.setActionCommand(bis.BOFApplicationStub.name);
              mItem.addActionListener(this);
              jOtherSystem.add(mItem);
              SystemMenuList.add(mItem);
          }
      }*/
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void ProcessOffline(ActionEvent e) {
    try {
      JActiveDComDM.setSystemOffline(bnOffline.isSelected());
    } catch ( Exception ee ) {
      bnOffline.setSelected(!bnOffline.isSelected());
    }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public Object ObjectStatusChange(Object ChangeObject) {
    bnOffline.setSelected(JActiveDComDM.SystemOffline);
    return null;
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void actionPerformed(ActionEvent e) {
    Object Sender = e.getSource();
    if ( Sender == this.mnStatus ) {
      pnStatus.setVisible(mnStatus.isSelected());
      return;
    }
    if ( Sender == this.mnMainToolbar && MainToolBar != null ) {
      MainToolBar.setVisible(mnMainToolbar.isSelected());
      return;
    }
    // 锟斤拷锟斤拷Bar
    setToolbarVisible(e.getActionCommand());
    // 锟斤拷锟斤拷锟斤拷锟斤拷/锟斤拷锟竭诧拷锟斤拷
    if ( Sender == bnOffline ) {
      ProcessOffline(e);
      return;
    }
    // 锟斤拷锟斤拷PopMenu
    ProcessPopMenu(Sender,e);
    // 锟斤拷锟斤拷SysMenu
    PorcessSysMenu(Sender,e);
    return;
  }
  void setToolbarVisible(String Name) {
    JBOFMDIChildWindow cw = (JBOFMDIChildWindow)this.GetCurrentWindow();
    if ( cw.ToolbarList == null ) return;
    JBOFToolBar bar;String ID;
    for(int i=0;i<cw.ToolbarList.size();i++) {
      bar = (JBOFToolBar)cw.ToolbarList.get(i);
      if ( bar == null ) continue;
      ID = bar.getToolBarID();
      if ( ID != null && ID.equals(Name) ) {
        if ( bar.CustomObject != null ) {
          bar.setVisible(((JMenuItem)bar.CustomObject).isSelected());
        }
      }
    }

  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void PorcessSysMenu(Object Sender,ActionEvent e) {
    int i,Count;JMenuItemStub MIS;String ActionCommand;
    Count = this.SystemMenuList.size();
    for(i=0;i<Count;i++) {
      MIS = (JMenuItemStub)SystemMenuList.get(i);
        if ( Sender == MIS.MenuItem || Sender == MIS.Button ) {
            Application.CallOperateItem(MIS.OperateItem,MIS,e);
            break;
        }
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void ProcessPopMenu(Object Sender,ActionEvent e) {
    int i,Count;JMenuItemStub MIS;String ActionCommand;
    Count = this.PopupMenuList.size();
    for(i=0;i<Count;i++) {
      MIS = (JMenuItemStub)PopupMenuList.get(i);
        if ( Sender == MIS.MenuItem || Sender == MIS.Button ) {
            ActionCommand = MIS.MenuItem.getName();
            if ( ActionCommand.equals("mnCloseCurrent") ) {
              this.mniClose_actionPerformed(e);
              return;
            }
            if ( ActionCommand.equals("mnCloseOther") ) {
              this.mniXClose_actionPerformed(e);
              return;
            }
            if ( ActionCommand.equals("mnCloseAll") ) {
              this.mniCloseAll_actionPerformed(e);
              return;
            }
            break;
        }
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void SystemMenuItem_actionPerformed(ActionEvent e) {
    String lnfName = e.getActionCommand();
      JActiveDComDM.BOFApplicationManager.Execute(lnfName,null,0);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void miCloseCurrentWindow_actionPerformed(ActionEvent e) {
      CloseObjectWindow(GetCurrentWindow());
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void CloseCurrentWindow() {
    CloseObjectWindow(GetCurrentWindow());
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void miCloseAllWindow_actionPerformed(ActionEvent e) {
      CloseAllWindow();
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void RadioButtonMenuItem_actionPerformed(ActionEvent e) {
    /*
    String lnfName = e.getActionCommand();
      try {
          UIManager.setLookAndFeel(lnfName);
          SwingUtilities.updateComponentTreeUI(this);
          jRadioButtonMenuItem1.setSelected(false);
          jRadioButtonMenuItem2.setSelected(false);
          jRadioButtonMenuItem3.setSelected(false);
          jRadioButtonMenuItem4.setSelected(false);
          JRadioButtonMenuItem button = (JRadioButtonMenuItem)e.getSource();
          button.setSelected(true);
          //this.pack();
      } catch (Exception exc) {
          JRadioButtonMenuItem button = (JRadioButtonMenuItem)e.getSource();
          button.setEnabled(false);
          System.err.println("Could not load LookAndFeel: " + lnfName);
      }*/
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void miAbout_actionPerformed(ActionEvent e) {
      if ( Application != null )
          Application.About();
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void miComWebSite_actionPerformed(ActionEvent e) {
//      if ( Application != null )
//          Application.ComWebSite(null,null,null);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void miTechWebSite_actionPerformed(ActionEvent e) {
//      if ( Application != null )
//          Application.TechWebSite(null,null,null);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void windowActivated(WindowEvent e) {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void windowClosed(WindowEvent e) {
    WaitThread.interrupt();
    WaitThread = null;
    JActiveDComDM.delEventObject(this);
    try {
		EAI.DAL.IOM("LoginfoObject","Logout");
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    /**
     * 锟斤拷录锟剿筹拷锟斤拷志
     * add by hufeng 2006.7.3
     */
    JLogManager.writeLoginOut();
    //
    System.exit(0);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void windowClosing(WindowEvent e) {
      this_windowClosing(e);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void windowDeactivated(WindowEvent e) {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void windowDeiconified(WindowEvent e) {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void windowIconified(WindowEvent e) {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void windowOpened(WindowEvent e) {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void this_windowClosing(WindowEvent e) {
      if ( CloseAllWindow() ) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        windowClosed(e);
//        if ( JActiveDComDM.MainFramework != null &&
//             JActiveDComDM.MainFramework instanceof java.awt.Frame ) {
//          System.exit(0);
//        }
      } else {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:锟斤拷源锟斤拷位锟斤拷母锟绞?ApplicationClassName.FrameworkWindow.锟斤拷源锟斤�\uFFFD
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public String GetResourceByID(String ID) {
//    String Res = null;
//      if ( GUIResource != null )
//          Res = GUIResource.GetResourceByID(ID);
//      return Res;
    return null;
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void miHelptopics_actionPerformed(ActionEvent e) {
//      if ( Application != null )
//          Application.Help(null,null,null);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void miMinWindow_actionPerformed(ActionEvent e) {

  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void stateChanged(ChangeEvent e) {
    if ( e.getSource() == this.MainTabbedPane )
      MainTabbedPane_stateChanged(e);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void MainTabbedPane_stateChanged(ChangeEvent e) {
    JBOFMDIChildWindow FWP;
      DelAllChildWindowGUIResource();
      FWP = (JBOFMDIChildWindow)MainTabbedPane.getSelectedComponent();
      if ( CurrentChildWindow != null ) {
        CurrentChildWindow.WindowDeActive(this);
      }
      if ( FWP != null && FWP.Application != null ) {
          AddChildWindowMainMenu(FWP);
          // 锟斤拷锟铰硷拷要锟斤拷锟捷碉拷锟解部锟斤拷锟斤拷
          try {
			EAI.BOF.IOM(FWP.Application.BOFApplicationStub.name,// 锟斤拷锟斤拷锟斤拷为应锟斤拷锟斤�\uFFFD
					  	  "ChildWindowSwitch",// 锟斤拷锟斤拷锟斤�\uFFFD
					  	  this,// 锟斤拷一锟斤拷锟斤拷锟轿拷锟斤�\uFFFD
					  	  FWP,// 锟节讹拷锟斤拷锟斤拷锟斤拷哟锟斤拷锟?
					  	  null,FWP.Application);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// 锟斤拷锟侥革拷为应锟斤拷锟斤拷锟?
          FWP.WindowActive(this);
          CurrentChildWindow = FWP;
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void DelChildWindowMainMenu(JBOFMDIChildWindow fwp) {
    int i,Count;com.jidesoft.swing.JideMenu Menu;//MenuElement ME;
      if ( fwp != null && fwp.MainMenu != null && fwp.MenuList != null ) {
          try {
              Count = fwp.MenuList.size();
              for(i=0;i<Count;i++) {
                  Menu = (com.jidesoft.swing.JideMenu)fwp.MenuList.get(i);
                  if ( Menu != null ) MainMenu.remove(Menu);
              }
              //MainMenu.remove(MenuSp);
          }catch(Exception e) {
              e.printStackTrace();
          }
      }
      DelChildWindowPopupMenu(fwp);
      DelChildWindowToolbar(fwp);
  }
/*
  Menu = (JMenuItem)fwp.PopupMenuList.get(i);
                  TablePopupMenu.add(Menu,i+3);
*/
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void DelChildWindowPopupMenu(JBOFMDIChildWindow fwp) {
    int i,Count;Component Menu;//MenuElement ME;
      if ( fwp != null && fwp.PopupMenuList != null ) {
          try {
              Count = fwp.PopupMenuList.size();
              for(i=0;i<Count;i++) {
                  Menu = (Component)fwp.PopupMenuList.get(i);
                  TablePopupMenu.remove(Menu);
              }
              TablePopupMenu.remove(MenuSp);
          }catch(Exception e) {
              e.printStackTrace();
          }
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void DelChildWindowToolbar(JBOFMDIChildWindow fwp) {
    int i,Count;JBOFToolBar Toolbar;
      if ( fwp != null && fwp.ToolbarList != null ) {
          try {
              Count = fwp.ToolbarList.size();
              for(i=0;i<Count;i++) {
                  Toolbar = (JBOFToolBar)fwp.ToolbarList.get(i);
                  if ( Toolbar.CustomObject != null ) {
                    mnToolbar.remove((JMenuItem)Toolbar.CustomObject);
                  }
                  if ( Toolbar.ToolbarLayout == 1 ) {
                    pnToolbar1.remove(Toolbar);
                    pnToolbar1.repaint();
                  }
                  if ( Toolbar.ToolbarLayout == 2 ) {
                    pnToolbar2.remove(Toolbar);
                    pnToolbar2.repaint();
                  }
                  if ( Toolbar.ToolbarLayout == 3 ) {
                    pnToolbar3.remove(Toolbar);
                    pnToolbar3.repaint();
                  }
              }
          }catch(Exception e) {
              e.printStackTrace();
          }
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void DelAllChildWindowGUIResource() {
    int i,Count;JBOFMDIChildWindow fwp;Component comp;
      Count = this.MainTabbedPane.getComponentCount();
      for(i=0;i<Count;i++) {
          comp = this.MainTabbedPane.getComponent(i);
          if ( comp instanceof JBOFMDIChildWindow ) {
              fwp = (JBOFMDIChildWindow)comp;
              DelChildWindowMainMenu(fwp);
          }
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void AddChildWindowMainMenu(JBOFMDIChildWindow fwp) {
    int i,Count;com.jidesoft.swing.JideMenu Menu;//MenuElement ME;
    int Index = 0;
      if ( fwp != null && fwp.MainMenu != null && fwp.MenuList != null ) {
          try {
              Count = fwp.MenuList.size();
              for(i=0;i<Count;i++) {
                  Menu = (com.jidesoft.swing.JideMenu)fwp.MenuList.get(i);
                  if ( Menu != null ) {
                    MainMenu.add( (com.jidesoft.swing.JideMenu) Menu, Index + 3);Index++;
                  }
              }
              //MainMenu.add(MenuSp,Count);
          }catch(Exception e) {
              e.printStackTrace();
          }
      }
      AddChildWindowPopupMenu(fwp);
      AddChildWindowToolbar(fwp);
      MainMenu.repaint();
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void AddChildWindowPopupMenu(JBOFMDIChildWindow fwp) {
    int i,Count;Component Menu;
      if ( fwp != null && fwp.PopupMenuList != null ) {
          try {
              Count = fwp.PopupMenuList.size();
              if ( Count > 0 ) TablePopupMenu.add(MenuSp);
              int CN = TablePopupMenu.getComponentCount();
              for(i=0;i<Count;i++) {
                  Menu = (Component)fwp.PopupMenuList.get(i);
                  if ( Menu != null )
                    TablePopupMenu.add(Menu,i+4);
              }
          }catch(Exception e) {
              e.printStackTrace();
          }
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void AddChildWindowToolbar(JBOFMDIChildWindow fwp) {
    int i,Count;JBOFToolBar Toolbar;int Index,Cnt;Component comp;JButton BN;
    Insets insets;
      if ( fwp != null ) {
          try {
              insets=new Insets(2,2,2,2);
              Count = fwp.ToolbarList.size();
              for(i=0;i<Count;i++) {
                  Toolbar = (JBOFToolBar)fwp.ToolbarList.get(i);
                  // 锟斤拷锟斤拷锟剿碉拷锟斤�\uFFFD
                  if ( Toolbar != null ) {
                    JCheckBoxMenuItem TBMenuItem;
                    if ( Toolbar.CustomObject != null ) {
                      TBMenuItem = (JCheckBoxMenuItem)Toolbar.CustomObject;
                      mnToolbar.remove(TBMenuItem);
                    } else
                      TBMenuItem = new JCheckBoxMenuItem();
                    TBMenuItem.addActionListener(this);
                    Toolbar.CustomObject = TBMenuItem;
                    TBMenuItem.setText(Toolbar.getName());
                    TBMenuItem.setActionCommand(Toolbar.getToolBarID());
                    TBMenuItem.setSelected(Toolbar.isVisible());

                    mnToolbar.add(TBMenuItem);
                  }
                    if ( Toolbar.ToolbarLayout == 1 )
                      pnToolbar1.add(Toolbar,-1);
                    if ( Toolbar.ToolbarLayout == 2 )
                      pnToolbar2.add(Toolbar,-1);
                    if ( Toolbar.ToolbarLayout == 3 ) {
                      pnToolbar3.add(Toolbar,-1);
                    }
                    //Toolbar.setVisible(Toolbar.isShow);
                    Toolbar.repaint();
                  Cnt = Toolbar.getComponentCount();
                  for(Index=0;Index<Cnt;Index++) {
                      comp = Toolbar.getComponent(Index);
                      if ( comp instanceof JButton ) {
                          BN = (JButton)comp;
                          BN.setMargin(insets);
                      }
                  }
              }
          }catch(Exception e) {
              e.printStackTrace();
          }
      }
      //pnToolbar.repaint();
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void MouseClickCloseWindow(Point p) {
    int Width = 0;
    int Index = this.MainTabbedPane.indexAtLocation(p.x,p.y);
    if ( Index == -1 ) return;
    Component comp = this.MainTabbedPane.getComponentAt(Index);
    if ( comp == null || !(comp instanceof JBOFChildWindow) ) return;
    Icon icon = MainTabbedPane.getIconAt(Index);
    if ( icon != null ) {Width = icon.getIconWidth();}
    String Text = MainTabbedPane.getTitleAt(Index).trim();
    Width += MainTabbedPane.getGraphics().getFontMetrics().stringWidth(Text);
    int SX,SY,IW,IH;
    IW = CloseImageIcon.getIconWidth();
    IH = CloseImageIcon.getIconHeight();
    Rectangle rect = this.MainTabbedPane.getBoundsAt(Index);
    SX =  rect.x;
    if ( Width < rect.width ) {
      SX = rect.x + (rect.width - Width)/2;
    }
    SY = rect.y + (rect.height - IH)/2;
    if ( p.x >= SX && p.x <= (SX+IW) &&
         p.y >= SY && p.y <= (SY+IH) ) {
      this.CloseObjectWindow(comp);
    }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void mouseClicked(MouseEvent e) {

      if(e.getModifiers() == e.BUTTON3_MASK){
        Rectangle r = MainTabbedPane.getBoundsAt(MainTabbedPane.getSelectedIndex());
//        System.out.print(r);
          PopTabMenu(e.getComponent(),e.getPoint());
          return;
      }
      if ( e.getModifiers() == e.BUTTON1_MASK ) {
        if ( e.getClickCount() == 2 ) {
          this.MainTabbedPane.setSelectedIndex(0);
        }
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void PopTabMenu(Component comp,Point p) {
      TablePopupMenu.show(comp,p.x,p.y);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void mouseEntered(MouseEvent e) {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void mouseExited(MouseEvent e) {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void mousePressed(MouseEvent e) {
    if ( e.getModifiers() == e.BUTTON1_MASK ) {
      Point p = e.getPoint();
      MouseClickCloseWindow(p);
      return;
    }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void mouseReleased(MouseEvent e) {
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void mniClose_actionPerformed(ActionEvent e) {
      miCloseCurrentWindow_actionPerformed(e);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void mniXClose_actionPerformed(ActionEvent e) {
    CloseOtherWindow();
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void CloseOtherWindow() {
    JBOFMDIChildWindow FP;JBOFMDIChildWindow CurFP;int i = 0,Count;
    Vector FPList = new Vector();
      CurFP = (JBOFMDIChildWindow)MainTabbedPane.getSelectedComponent();
      Count = MainTabbedPane.getComponentCount();

      for(i=0;i<Count;i++) {
        Component pChild = MainTabbedPane.getComponent(i);
        if ( pChild instanceof JBOFMDIChildWindow){
          FP = (JBOFMDIChildWindow) pChild;
          FPList.add(FP);
        }
      }

      for(i=0;i<FPList.size();i++) {
        FP = (JBOFMDIChildWindow)FPList.get(i);
        if ( FP != null && CurFP != FP ) {
            if ( FP.Close() ) {
                CloseObjectWindow(FP);
            }
          }
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  void mniCloseAll_actionPerformed(ActionEvent e) {
      this.CloseAllWindow();
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void setActiveWindow(JBOFChildWindow cw){
    if ( cw == null ) return;
    if ( cw == this.GetCurrentWindow() ) return;
    JBOFMDIChildWindow FWP;
      DelAllChildWindowGUIResource();
      MainTabbedPane.setSelectedComponent(cw);
//      FWP = (JBOFMDIChildWindow)MainTabbedPane.getSelectedComponent();
//      if ( FWP != null && FWP.Application != null ) {
//          AddChildWindowMainMenu(FWP);
//          // 锟斤拷锟铰硷拷要锟斤拷锟捷碉拷锟解部锟斤拷锟斤拷
//          JActiveDComDM.BusinessActiveFramework.InvokeObjectMethod(
//              FWP.Application.BOFApplicationStub.name,// 锟斤拷锟斤拷锟斤拷为应锟斤拷锟斤�\uFFFD
//              "ChildWindowSwitch",// 锟斤拷锟斤拷锟斤�\uFFFD
//              this,// 锟斤拷一锟斤拷锟斤拷锟轿拷锟斤�\uFFFD
//              FWP,// 锟节讹拷锟斤拷锟斤拷锟斤拷哟锟斤拷锟?
//              null,FWP.Application);// 锟斤拷锟侥革拷为应锟斤拷锟斤拷锟?
//          FWP.WindowActive(this);
//      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void setStatusText(int Index,String Text) {
    switch ( Index ) {
    case 1:
      this.statusLabel1.setText(Text);return;
    case 2:
      this.statusLabel2.setText(Text);return;
    case 3:
      this.statusLabel3.setText(Text);return;
    case 4:
      this.statusLabel4.setText(Text);return;
    }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷: 锟截憋拷�\uFFFD锟斤拷锟斤拷蟠翱�\uFFFD?
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public boolean CloseObjectWindow(Component comp) {
      if (this.callBackWindowClazz != null) {
          // 锟斤拷锟矫回碉拷�\uFFFD?
          callBackCloseObjectWindow(comp);
          return true;
      }
      JBOFMDIChildWindow fp;
      Component cp = null;
      if (comp != null) {
          fp = (JBOFMDIChildWindow) comp;
          if (fp.Close()) {
              /**
               * 锟饺达拷锟斤拷Closed锟铰硷拷锟斤拷锟斤拷锟斤拷锟斤拷锟狡筹拷
               * 锟斤拷锟斤拷锟节关憋拷锟铰硷拷锟叫ｏ拷锟斤拷锟斤拷锟斤拷取锟斤拷锟斤拷锟节憋拷锟斤拷
               * modified by hufeng 2006.7.5
               */
              fp.WindowClosed();
              mnWindow.remove(fp.WindowMenuItem);
              MainTabbedPane.remove(comp);
              this.DelChildWindowMainMenu(fp);
              fp = (JBOFMDIChildWindow) MainTabbedPane.getSelectedComponent();
              //
              if (fp != CurrentChildWindow) {
                  CurrentChildWindow.WindowDeActive(this);
                  fp.WindowActive(this);
                  CurrentChildWindow = fp;
              }
              if (fp != null) {
                  this.AddChildWindowMainMenu(fp);
              }

              return true;
          } else
              return false;
      }
      return true;
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:synchronized
  //----------------------------------------------------------------------------------------------
  public  void BeginWaitCursor() {
    if ( WaitCount !=  0 ) return;
    WaitCount++;
    this.pbProgressBar.setMaximum(100);
    this.pbProgressBar.setValue(0);
    DefaultCursor = getCursorType();
    setCursor(WAIT_CURSOR);
      try {
        if ( WaitThread == null ) {
          WaitThread = new JWaitThread(pbProgressBar);
          WaitThread.setPriority(Thread.NORM_PRIORITY);
//          WaitThread.setDaemon(true);
          WaitThread.start();
        } else {
          WaitThread.BeginWait();
        }
      } catch( Exception e ) {
        e.printStackTrace();
      }
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:synchronized
  //----------------------------------------------------------------------------------------------
  public  void EndWaitCursor() {
    if ( WaitCount == 0 ) return;
    WaitCount--;
    setCursor(DefaultCursor);
    if ( WaitThread != null ) {
      try {
        WaitThread.EndWait();
        this.pbProgressBar.setValue(100);
      } catch ( Exception e) {
          e.printStackTrace();
      }
    }
      this.pbProgressBar.setMaximum(100);
      this.pbProgressBar.setValue(0);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public void run() {
    int Count = 0 ;
    try {
//      synchronized ( WaitThread ) {
      while ( true ) {
        Count += 1;
        ProcessBar(Count%100);
        //pbProgressBar.repaint();
        WaitThread.yield();
        WaitThread.sleep(100);
//      }
      }
    } catch ( Exception e ) {
      //WaitThread = null;
      e.printStackTrace();
    }
    return;
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  synchronized void ProcessBar(int Value) {
    this.pbProgressBar.setValue(Value);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public int GetChildWindowCount() {
    return this.MainTabbedPane.getTabCount();
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public JBOFChildWindow GetChildWindow(int Index) {
    return (JBOFChildWindow)this.MainTabbedPane.getComponentAt(Index);
  }
  //----------------------------------------------------------------------------------------------
  //锟斤拷锟斤拷:
  //锟斤拷锟? Skyline(2001.12.29)
  //实锟斤拷: Skyline
  //锟睫革拷:
  //----------------------------------------------------------------------------------------------
  public String getTitleAt(JBOFChildWindow wnd) {
    int Index = this.MainTabbedPane.indexOfComponent(wnd);
    return this.MainTabbedPane.getTitleAt(Index);
  }
  public Icon getIcon(JBOFChildWindow wnd) {
    int Index = this.MainTabbedPane.indexOfComponent(wnd);
    Icon icon =
    icon =  this.MainTabbedPane.getIconAt(Index);
    if ( icon instanceof CompoundIcon ) {
      return ((CompoundIcon)icon).getMainIcon();
    }
    return icon;
  }
  public void setOffline(boolean value) {
    this.bnOffline.setSelected(value);
  }

  /**
   * 锟截闭达拷锟节★�\uFFFD
   */
  public void closeOperation(MouseEvent e) {
      Point p = e.getPoint();

      int Index = this.MainTabbedPane.indexAtLocation(p.x, p.y);
      if (Index == -1)return;
      Component comp = this.MainTabbedPane.getComponentAt(Index);
      if (comp == null || ! (comp instanceof JBOFChildWindow))return;

      //锟截闭★�\uFFFD
      this.CloseObjectWindow(comp);
  }

  /**
   * 锟斤拷锟斤拷说锟?
   */
  public void popupOutsideOperation(MouseEvent e){
      PopTabMenu(e.getComponent(),e.getPoint());
  }
  /**
   *
   */
  protected Class callBackWindowClazz = null;
  /**
   *
   */
  protected void initCallBackWindowClazz() {
    String callBackClass = System.getProperty("callBackWindowClazz",null);
    if ( callBackWindowClazz != null || callBackClass == null ) {
    	callBackClass = "com.efounder.eai.EnterpriseDockExplorer";
    }
    try {
      callBackWindowClazz = this.getClass().forName(callBackClass);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  /**
   *
   * @param title String
   * @param icon Icon
   * @param tip String
   * @param comp Component
   */
  protected void callBackOpenObjectWindow(String title,Icon icon, String tip,Component comp) {
    Object[] OArray = {title,icon,tip,comp};
    JBOFClass.CallClassMethod(callBackWindowClazz,"callBackOpenObjectWindow",OArray);
  }
  /**
   *
   * @param comp Component
   */
  protected void callBackCloseObjectWindow(Component comp) {
    Object[] OArray = {comp};
    JBOFClass.CallClassMethod(callBackWindowClazz,"callBackCloseObjectWindow",OArray);
  }
}
