package jservice.jbof.classes;

import java.net.*;
import java.util.*;

import org.jdom.*;

import com.efounder.eai.application.classes.JBOFApplication;

import jbof.application.classes.*;
import jframework.foundation.classes.*;
import jframework.resource.classes.*;
import jservice.jbof.classes.BOFOfflineObject.*;
import jtoolkit.xml.classes.*;
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JBOFOfflineObject extends JActiveObject {
  static ResourceBundle res = ResourceBundle.getBundle("jservice.jbof.classes.Language");
  private static final String GUID = "00000000-1111-0001-0000000000000007";
  public static Vector OfflineDataInfoList = new Vector();
  public static Vector OfflineDataInfoListbak = new Vector();
  public static Vector OfflineDataLbList = new Vector();
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JBOFOfflineObject() {
    setObjectGUID(GUID);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object InitObject(Object Param, Object Data, Object CustomObject,Object AdditiveObject) {
    LoadXMLTable();
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object DatainfoManager(Object Param, Object Data, Object CustomObject,Object AdditiveObject) {
    JBOFApplication Application = (JBOFApplication)AdditiveObject;
    JOfflineDataInfoObject.OpenDataChildWindow(Application,this);
    return null;
  }
  public Object DataSetDemo(Object Param, Object Data, Object CustomObject,Object AdditiveObject) {
    JBOFApplication Application = (JBOFApplication)AdditiveObject;
    JOfflineDataInfoObject.OpenDataSetChildWindow(Application,this);
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object OfflineFunctionManager(Object Param, Object Data, Object CustomObject,Object AdditiveObject) {
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void LoadXMLTable() {
    JXMLResource df ;JOfflineDataInfoStub ODIS,ODISBAK;
    try {
      String uri = JActiveDComDM.CodeBase+"Online/XMLTable.xml";
      JXMLBaseObject XMLObject = new JXMLBaseObject();
      boolean Res = XMLObject.InitXMLURI(uri);
      if ( !Res ) {
        URL url = JXMLResource.LoadXML(this, "XMLTable.xml");
        XMLObject = new JXMLBaseObject(url);
      }
      java.util.List list = XMLObject.BeginEnumerate(XMLObject.Root);
      Element e;int i=0;
      while ( list != null ) {
        e = XMLObject.Enumerate(list,i++);
        if(!e.getName().equals("T"))break;
        if ( e == null ) break;
        ODIS = new JOfflineDataInfoStub();
        ODIS.name = JXMLObject.GetElementValue(e,"name");
        ODIS.text   = JXMLObject.GetElementValue(e,"text");
        ODIS.F_BHZD = JXMLObject.GetElementValue(e,"F_BHZD");
        ODIS.F_CODE = JXMLObject.GetElementValue(e,"F_CODE");
        ODIS.CODE_TYPE = JXMLObject.GetElementValue(e,"CODE_TYPE");
        ODIS.QXBZW = JXMLObject.GetElementValue(e,"QXBZW");
        ODIS.DNDATA = JXMLObject.GetElementValue(e,"DNDATA","1");
        ODIS.WHERE = JXMLObject.GetElementValue(e,"WHERE","");
        ODIS.SQL    = e.getText();
        ODIS.setF_CODE(ODIS.F_CODE);
        ODIS.F_SJQX = JXMLObject.GetElementValue(e,"F_SJQX");
        ODIS.setF_SJQX(ODIS.F_SJQX);
        OfflineDataInfoList.add(ODIS);
        ODISBAK =  new JOfflineDataInfoStub();
        ODISBAK=ODIS;
        OfflineDataInfoListbak.add(ODISBAK);
      }
      list=XMLObject.getElementsByTagName("LB");
      i=0;
      String asData[];
      while(list !=null){
        e = XMLObject.Enumerate(list,i++);
        if ( e == null ) break;
        asData=new String[2];
        asData[0]=JXMLObject.GetElementValue(e,"name");
        asData[1]=JXMLObject.GetElementValue(e,"table");
        OfflineDataLbList.add(asData);
      }
      XMLObject.EndEnumerate();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}
