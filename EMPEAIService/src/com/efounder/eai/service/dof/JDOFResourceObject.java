package com.efounder.eai.service.dof;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import com.core.xml.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;
import com.efounder.eai.framework.*;
import com.efounder.eai.resource.*;
import com.efounder.pfc.swing.*;
import com.efounder.util.*;
import com.incors.plaf.alloy.*;
import com.efounder.eai.ide.*;
import com.sap.plaf.frog.FrogLookAndFeel;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDOFResourceObject extends JActiveObject {
  public static  Hashtable ResourceList = new Hashtable();
  private static final String GUID = "ResourceObject";
  private String LocalFileName;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JDOFResourceObject() {
    setObjectGUID(GUID);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object InitObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JStubObject stub;
    stub           = new JStubObject();
    stub.Name      = "InternationalPanel";
    stub.Caption   = JEnterpriseResource.GetString("JLoginDialog",stub.Name,"区域和语言");
    stub.ClassName = "com.efounder.eai.service.dof.DOFResourceObject.JInternationalPanel";
    try {
      EAI.DOF.InvokeObjectMethod("SecurityObject", "RegistryObject", stub);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    GetOnlineCommBase();
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void GetResource(JDOMXMLBaseObject XMLObject) {
    java.util.List resourceList = PackageStub.getContentVector("Resources");
    StubObject SO = null;
    for(int i=0;i<resourceList.size();i++) {
      SO = (StubObject)resourceList.get(i);
      ResourceList.put(SO.getID(),SO);
    }
//    java.util.List nodelist;Element AppServicesElement=null;Element node;int i=0;String Name="Resources";
//    JResourceStubObject RSO = null;String ID;
//    AppServicesElement = XMLObject.GetElementByName(Name);
//    nodelist = XMLObject.BeginEnumerate(AppServicesElement);
//    int Index = 0;
//    while ( nodelist != null ) {
//      node = XMLObject.Enumerate(nodelist,Index++);
//      if ( node == null ) break;
//      ID = XMLObject.GetElementValue(node,"id");
//      if ( ResourceList.get(ID) != null ) continue;
//      RSO = (JResourceStubObject) StubObject.convertXML2Stub(XMLObject,node,JResourceStubObject.class);
//      RSO.id = ID;
//      RSO.name = XMLObject.GetElementValue(node,"name");
//      RSO.FontName = XMLObject.GetElementValue(node,"fontname");
//      RSO.FontSize = Integer.valueOf(XMLObject.GetElementValue(node,"fontsize")).intValue();
//      RSO.DateFormat = XMLObject.GetElementValue(node,"dateformat");
//      RSO.Money = XMLObject.GetElementValue(node,"money");
    //id="en_US" name="English"  fontname="Dialog" fontsize="12" dateformat="MM/DD/YYYY"    money="$"/>
//      ResourceList.put(RSO.getID(),RSO);
//    }
//    XMLObject.EndEnumerate();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void GetOnlineCommBase() {
//    String FileName = "AppResource.xml";
//    JXMLBaseObject XMLObject = new JXMLBaseObject();
//    ConfigManager.getSpaceFile(XMLObject,ConfigManager.CONF_FILE_DIR,FileName,true);
    GetResource(null);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object LoginInit(Object Param1,Object MethodName,Object Param,Object Data) throws Exception {
    JComboBox ComboBox = (JComboBox)Param1;StubObject ASO = null;
    String ID = EAI.getLanguage();
    String lang = EAI.LocalRegistry.Get("language","");
    String coun = EAI.LocalRegistry.Get("country","");
    boolean Res = false;
    ComboBox.removeAllItems();
    ImageIcon ii = JResource.getImageIcon(this,"icon.gif",null);
    JIConListCellRenderer ICR = new JIConListCellRenderer(ii);
    ComboBox.setRenderer(ICR);

    for(int i=0;i<this.ResourceList.size();i++) {
      ASO = (StubObject)ResourceList.values().toArray()[i];
      String sicon = ASO.getString("icon",null);
      if ( sicon != null && sicon.trim().length() > 0 ) {
        Icon icon = ExplorerIcons.getExplorerIcon(sicon);
        ASO.setIcon(icon);
      }
      ComboBox.addItem(ASO);
//      ComboBox.insertItemAt(ASO,i);
//      if ( ASO.getID().equals(ID) ) {
      if (ASO.getString("language", null).equals(lang) &&
          ASO.getString("country", null).equals(coun)){
        ComboBox.setSelectedIndex(i);
      }
      Res = true;
    }
    if ( ID == null && ComboBox.getItemCount() != 0 ) {
      ComboBox.setSelectedIndex(0);
    }
    return new Boolean(Res);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object LoginSetup(Object Param1,Object Param2,Object Param,Object Data) throws Exception {
    JComboBox ComboBox = (JComboBox)Param1;boolean Res = false;
    StubObject ASO = (StubObject)ComboBox.getSelectedItem();
    JParamObject PO = (JParamObject)Param2;
    if ( ASO != null ) {
      EAI.setLanguage(ASO.getString("language","zh"),ASO.getString("country","CN"));
      EAI.LocalRegistry.Put("language",ASO.getString("language",null));
      EAI.LocalRegistry.Put("country",ASO.getString("country",null));
      PO.SetValueByEnvName("Language",EAI.getLanguage());
      PO.SetValueByEnvName("International",ASO.getString("language","zh"));
     //add by fsz
      PO.SetValueByEnvName("Country",ASO.getString("country","CN"));
      Locale.setDefault(EAI.getEAILocale());
      Res = true;
    }
    return new Boolean(Res);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object LoginActiveEvent(Object Param1,Object Param2,Object Param3,Object Param4) throws Exception {
    if ( Param2 != null ) {
      JBOFClass.CallObjectMethod(Param2,"SetResource");
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object LoginEnd(Object Param1,Object Param2,Object Param3,Object Param4) throws Exception {
    return null;
  }

  public static void setOldLookAndFeel() {
    try {
      com.sun.java.swing.plaf.windows.WindowsLookAndFeel w = new com.sun.java.swing.plaf.windows.WindowsLookAndFeel();
      UIManager.setLookAndFeel(w);
    } catch ( Exception ex ) {
      ex.printStackTrace();
    }
  }
  public static void setNewLookAndFeel() {
    try {
      FrogLookAndFeel df= new FrogLookAndFeel();
      UIManager.setLookAndFeel(df);
      df.getDefaults().put("ClassLoader", JDOFResourceObject.class.getClassLoader());
      UIManager.getDefaults().put("ClassLoader", JDOFResourceObject.class.getClassLoader());
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static final String windows  =
            "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String SAP_STD_LOOKANDFEEL = "com.sap.plaf.frog.FrogLookAndFeel";
  protected static void initSAPLookFeel() throws Exception {
    try {
      FrogLookAndFeel df= new FrogLookAndFeel();
      UIManager.setLookAndFeel(df);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  protected void initAlloyLookFeel() throws Exception {
    try {
      // 设置windows的LookandFeel
      UIManager.setLookAndFeel(windows);
      Object TreeexpandedIcon    = UIManager.getDefaults().get("Tree.expandedIcon");
      Object TreecollapsedIcon = UIManager.getDefaults().get("Tree.collapsedIcon");

      LookAndFeel alloyLnF = getAlloyLookandFeel();
      javax.swing.UIManager.setLookAndFeel(alloyLnF);
      UIManager.getDefaults().put("Tree.expandedIcon",TreeexpandedIcon);
      UIManager.getDefaults().put("Tree.collapsedIcon",TreecollapsedIcon);

    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
    }
  }
  public static LookAndFeel getCurrentLookAndFeel() {
    return javax.swing.UIManager.getLookAndFeel();
  }
  /**
   *
   */
  protected static javax.swing.LookAndFeel alloyLnF = null;
  /**
   *
   * @return LookAndFeel
   */
  public static LookAndFeel getAlloyLookandFeel() {

    LookAndFeel laf = new com.sun.java.swing.plaf.windows.WindowsLookAndFeel();
    return laf;


//    if ( alloyLnF != null ) return alloyLnF;
//    AlloyLookAndFeel.setProperty("alloy.licenseCode","Z#xskyline@163.com#bw06ew#a973q0");
//    AlloyLookAndFeel.setProperty("alloy.theme","Default");//Glass//Default//Bedouin//Acid//alloy.isSandbox=true
//    AlloyLookAndFeel.setProperty("alloy.isPopupShadowEffectEnabled","true");
//    AlloyLookAndFeel.setProperty("alloy.titlePaneTextAlignment","left");
//    alloyLnF = new com.incors.plaf.alloy.AlloyLookAndFeel();
//    alloyLnF.getDefaults().put("ClassLoader", JDOFResourceObject.class.getClassLoader());
//    return alloyLnF;
  }
  public Object initLookFeel(Object Param1,Object Param2,Object Param3,Object Param4) throws Exception {
    Object res = EAI.BOF.IOM(EAI.Product,"initLookFeel",Param1,Param2,Param3,Param4);
    if ( res == null ) {
      initAlloyLookFeel();
    }
    javax.swing.UIManager.getLookAndFeelDefaults().put("ClassLoader", getClass().getClassLoader());
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object initFont(Object Param1,Object Param2,Object Param3,Object Param4) throws Exception {
    Object res = EAI.BOF.IOM(EAI.Product,"initFont",Param1,Param2,Param3,Param4);
    if ( res != null ) return res;
    Font font=new Font("dialog",Font.PLAIN,12);
    String names[]={"Label","CheckBox","PopupMenu","TextPane",
                   "MenuItem","CheckBoxMenuItem","JRadioButtonMenuItem",
                   "ComboBox","Button","Tree","ScrollPane","TabbedPane",
                   "EditorPane","TitledBorder","Menu","TextArea","OptionPane",
                   "MenuBar","ToolBar","ToggleButton","ToolTip","ProgressBar",
                   "TableHeader","Panel","List","ColorChooser","PasswordField",
                   "TextField","Table","Label","Viewport","RadioButtonMenuItem",
                   "RadioButton"};
    for(int i=0;i<names.length;i++)UIManager.put(names[i]+".font",font);
    UIManager.put("Label.foreground",Color.black);
    UIManager.put("Border.foreground",Color.black);
    UIManager.put("TitledBorder.titleColor",Color.black);

    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object initSplash(Object Param1,Object Param2,Object Param3,Object Param4) throws Exception {
    Object res = EAI.BOF.IOM(EAI.Product,"initSplash",Param1,Param2,Param3,Param4);
    if ( res != null ) return res;
    return null;
  }

}
