package jframework.foundation.classes;

import java.util.*;

import com.efounder.eai.application.classes.JBOFApplication;
import com.efounder.eai.framework.JActiveFramework;
import com.efounder.eai.registry.JLocalRegistry;

//import jbof.application.classes.*;
import jfoundation.application.classes.*;
import jfoundation.bridge.classes.IObjectStatusChange;
import jtoolkit.registry.classes.*;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */

public class JActiveDComDM {
  static private Vector EventList = new Vector();
  static public boolean SystemOffline = false;
  static public boolean SystemSSL     = false;
  static public String BofXmlFile;
  static public String International;
  static public String CodeBase;
  static public String Company;
  static public String Product;
  static public String Tier;
  static public String LocalUserHome;

  static public JBOFApplication                 MainApplication             = null;
  static public Object                       MainFramework               = null;
  static public JApplicationManager          BOFApplicationManager       = null;       // Ӧ�ó��������\uFFFD
  static public JManagerRequestObject        ManagerRequestObject        = null;       // ������������
  static public JActiveFramework             BusinessActiveFramework     = null;       // ҵ����������
  static public JActiveFramework             DataActiveFramework         = null;       // ��ݶ��������
  static public JActiveFramework             AbstractDataActiveFramework = null;       // ������ݶ�����\uFFFD
  //static public JFrameworkClassLoader        FrameworkClassLoader        = null;
  static public ClassLoader                  FrameworkClassLoader        = null;
  static public JXMLRegistry                 XMLRegistry                 = null;
  static public JLocalRegistry               LocalRegistry               = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JActiveDComDM() {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void setSystemOffline(boolean Value) throws Exception {
    // ����ʲô״̬���п�����Ҫ�ı�����
    // 无论�\uFFFD么状态都有可能需要改变密�\uFFFD
    if ( DataActiveFramework.InvokeObjectMethod("SecurityObject","OnlineLogin") == null ) {
      throw new Exception("登录失败，不允许改变登录状�\uFFFD�！");
    }
    processSystemOffline(Value);
  }
  private static void processSystemOffline(boolean Value) {
    SystemOffline = Value;
    IObjectStatusChange ISOC;
    for(int i=0;i<EventList.size();i++) {
      ISOC = (IObjectStatusChange)EventList.get(i);
      ISOC.ObjectStatusChange(null);
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void addEventObject(IObjectStatusChange ISOC) {
    EventList.add(ISOC);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void delEventObject(IObjectStatusChange ISOC) {
    EventList.remove(ISOC);
  }
}
