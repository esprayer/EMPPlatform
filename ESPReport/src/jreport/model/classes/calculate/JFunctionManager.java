package jreport.model.classes.calculate;

import java.util.*;

import jframework.foundation.classes.*;
import jframework.resource.classes.*;
import jreport.foundation.classes.calculate.*;
import jreport.swing.classes.wizard.JFunctionStub;
import jtoolkit.xml.classes.*;
import org.jdom.*;
import javax.swing.JOptionPane;
import java.util.ResourceBundle;
import com.core.xml.StubObject;
import com.core.xml.PackageStub;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.service.MemCachedManager;
import com.danga.MemCached.MemCachedClient;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JFunctionManager {
static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.calculate.Language"); //implements IScanFunction {
  static String FunctionTag="+-*/()><=,;?:~^&$%@#!`.'|";
  public java.util.Vector FunctionList = new java.util.Vector();
  public static JXMLBaseObject FunctionConfigXML = null;
  private static int TmpTableCount = 0;
  public static String Text="";
  //
  private static ArrayList mExtFuncList = null; // �ⲿ����
  //
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static synchronized int getTmpTableCouont() {
	  int iRes = 1;
	  String key = "CalculateTmpTableCount";
	  Object tmpObj;
	  MemCachedClient memCC = MemCachedManager.getDefault().getMemCached();
	  if ( memCC != null ) {
		  tmpObj = memCC.get(key);
		  if (tmpObj == null) {
			 memCC.add(key,new Integer(100));
			  iRes = 100;
		  }
		  else {
			  iRes = (Integer) tmpObj;
			  memCC.incr(key);
			  return iRes;
		  }
	  }	else {
		  return TmpTableCount++;
	  }
	  return iRes;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JFunctionManager() {
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void InitObject(Element PublicElement,Element PrivateElement,String OpFrom) {
/*
    List nodelist;Element RFElement=null;Element node;int i=0;String Name="FunctionManager";
    if ( PublicElement == null ) System.out.println("PublicElement=null");
    if ( PrivateElement == null ) System.out.println("PrivateElement=null");
    RFElement = JActiveDComDM.XMLRegistry.GetElementByName(PublicElement,Name);
    if ( RFElement == null ) return;
    InitFunctionConfigXML(RFElement.getAttribute("functionconfig").getValue());
    JFunctionStub FS;
    nodelist = JActiveDComDM.XMLRegistry.BeginEnumerate(RFElement);
    while ( nodelist != null ) {
      node = JActiveDComDM.XMLRegistry.Enumerate(nodelist,i);
      if ( node == null ) break;
//      if ( node.getNodeType() == node.ELEMENT_NODE ) {
        RFElement     = (Element)node;
        FS            = new JFunctionStub();
        FS.FunctionID = RFElement.getAttribute("id").getValue().toUpperCase();
        FS.ClassName  = RFElement.getAttribute("class").getValue();
        FunctionList.add(FS);
//      }
      i++;
    }
    JActiveDComDM.XMLRegistry.EndEnumerate();
 */
    Element RFElement=null;Element node;int i=0;String Name="FunctionManager";
    if ( PublicElement == null ) System.out.println("PublicElement=null");
    if ( PrivateElement == null ) System.out.println("PrivateElement=null");
    RFElement = JActiveDComDM.XMLRegistry.GetElementByName(PublicElement,Name);
    if ( RFElement == null ) return;
    InitFunctionConfigXML(RFElement.getAttribute("functionconfig").getValue());

     java.util.List nodelist = PackageStub.getContentVector("fmisReportFunction");
     JFunctionStub FS;
     StubObject so=null;
     for (i=0;i<nodelist.size();i++){
       so = (StubObject) nodelist.get(i);
         FS            = new JFunctionStub();
         FS.FunctionID = so.getString("id", "");;
         FS.ClassName  = so.getString(OpFrom, "");;
         FunctionList.add(FS);
     }
  }

  /**
   * ��ʼ���ⲿ����
   * @param funcList ArrayList
   */
  public void InitExtendFunction(ArrayList funcList){
      if(mExtFuncList != null && mExtFuncList.size() != 0){
          return;
      }
      if(funcList == null || funcList.size() == 0){
          return;
      }
      mExtFuncList = funcList;
      // ��ʼ������
      JExtFuncStub extStub = null;
      JFunctionStub funcStub = null;
      for(int i=0; i<funcList.size(); i++){
          extStub = (JExtFuncStub)funcList.get(i);
          funcStub = new JFunctionStub();
          funcStub.setFunctionID(extStub.getFunc());
          funcStub.setClassName(extStub.getFuncClass());
          FunctionList.add(funcStub);
      }
  }


  public void InitFunctionConfigXML(String Name) {
    String URI=null;
    if ( Name == null ) System.out.print("Name=null");
    if ( FunctionConfigXML != null ) return;
//    RB = this.getClass().getName().replace('.','/');;
//    RB = "Codebase/"+RB;
    java.net.URL url = JXMLResource.LoadXML(this,Name,"");
    if ( url == null ) {System.out.println("url=null");return ;}
    URI = url.toString();
//    URI = JActiveDComDM.CodeBase+RB+"/function.xml";
    FunctionConfigXML = new JXMLBaseObject();
    FunctionConfigXML.InitXMLURI(URI);
  }

  /**
   * Ϊ����ϵͳ����
   */
  public void initFunctionList(){
      InitFunctionConfigXML("function.xml");
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JFunctionStub getFunctionByID(String ID,Object OwnerObject) {
    IFunction IF = null;JFunctionStub FS=null;String FT;
    try {
      for(int i=0;i<FunctionList.size();i++) {
        FS = (JFunctionStub)FunctionList.get(i);
//        System.out.println(FS.FunctionID);
        FT = FS.FunctionID+"_";
        if ( FS.FunctionID.equals(ID) || FT.equals(ID) ) {
          if ( FS.Function == null ) {
            FS.FunctionClass = Class.forName(FS.ClassName);
            FS.Function      = (IFunction)FS.FunctionClass.newInstance();
            FS.Function.InitFunction(FS);
          }
         /* else { // modify by liukun �ҵ��˾�ֱ�ӷ��ذ� 20100715
            return FS;
          }
          */
          //
          if ( OwnerObject != null && OwnerObject instanceof Hashtable ) {
            Hashtable OTable = (Hashtable)OwnerObject;
            JFunctionStub fs = (JFunctionStub)OTable.get(ID);
            if ( fs == null ) {
              fs = new JFunctionStub();
              fs.ClassName     = FS.ClassName;
              fs.FunctionClass = FS.FunctionClass;
              fs.FunctionID    = FS.FunctionID;
              OTable.put(ID,fs);
            }
            if ( fs.Function == null ) {
              fs.FunctionClass = Class.forName(FS.ClassName);
              fs.Function      = (IFunction)FS.FunctionClass.newInstance();
              fs.Function.InitFunction(FS);
            }
            FS = fs;
          }
          else{
              /**
               * ��������û��б�������Ҫ���³�ʼ������
               * ����ֱ����ϵͳ������Ϊϵͳ����ֻ�ڵ�¼ʱ��ʼ
               * ����������BB�ຯ���������������Ϊ�Ϳ��ܻ�����м��޸Ĺ����У�
               * modified by hufeng 2007.11.20
               */
              FS.Function  = (IFunction)FS.FunctionClass.newInstance();
              FS.Function.InitFunction(FS);
          }

          return FS;
        }
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����: ���һ����ʽ�ǲ���ֻ�б��������
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public boolean IsReportF1(String F1String) throws Exception {
    JReportF1 EF = new JReportF1();
    String Text = F1String.toUpperCase();String Res;
    Res = ScanFunctionFromF1(Text,null,null,EF,"REPORT");
    return Text.equals(Res);
  }
  //------------------------------------------------------------------------------------------------
  //����: ���һ����ʽ�ɲ������ڻ��ܱ������
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public boolean IsCountF1(String F1String) throws Exception {
    JReportF1 EF = new JReportF1();
    String Text = F1String.toUpperCase();String Res;
    Res = ScanFunctionFromF1(Text,null,null,EF,"COUNT");
    return Text.equals(Res);
  }

  /**
   * �Ƿ��Ǳ���ȡ����ʽ
   * @param F1String String
   * @return boolean
   * @throws Exception
   */
  public boolean isInnerReportF1(String F1String,String sAddbh, String sBbbh) throws Exception {
      return JCkService.isInnerReportFunction(F1String,sAddbh,sBbbh);
  }
  /**
   * �Ƿ��Ǳ���ȡ����ʽ
   * @param F1String String
   * @return boolean
   * @throws Exception
   */
  public boolean isReportF1(String F1String) throws Exception {
	  return JCkService.isReportFunction(F1String);
  }

  //------------------------------------------------------------------------------------------------
  //����: ���һ����ʽ�ǲ������ⲿ������ɵ�
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public boolean isExternalF1(String F1String) throws Exception {
    JExternalF1 EF = new JExternalF1();
    String Text = F1String.toUpperCase();String Res;
    Res = ScanFunctionFromF1(Text,null,null,EF,null);
    return !Text.equals(Res);
  }
  //------------------------------------------------------------------------------------------------
  //����: �û�ģʽ���洢ģʽ��ת��
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String UserModelToStoreModel(String F1String,JParamObject PO,Object ConnObject) throws Exception {
    JScanUserToStore SUS = new JScanUserToStore();
    String Res = ScanFunctionFromF1(F1String,PO,ConnObject,SUS,null);
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //����: �洢ģʽ���û�ģʽ��ת��
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String StoreModelToUserModel(String F1String,JParamObject PO,Object ConnObject) throws Exception {
    JScanStoreToUser SSU = new JScanStoreToUser();
    String Res = ScanFunctionFromF1(F1String,PO,ConnObject,SSU,null);
    return Res;
  }


  //------------------------------------------------------------------------------------------------
  //����: �洢ģʽ������ģʽ��ת��
  //���: Skyline(2001.12.29)
  //ʵ��: kun
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String StoreModelToTrunModel(String F1String,JParamObject PO,Object ConnObject) throws Exception {
      String Res = "";
      return Res;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String ScanFunctionFromF1(String f1string,JParamObject PO,Object ConnObject,IScanFunction Scan,Object UserObject) throws Exception {
    // �˺�����������
    return ScanFunctionFromF1(f1string,PO,ConnObject,Scan,UserObject,null);
  }

  /**
   * �����������������
   * ����һ�������ı��ʽ
   * @param f1string String
   * @param PO JParamObject
   * @param ConnObject Object
   * @param Scan IScanFunction
   * @param UserObject Object
   * @param OwnerObject Object
   * @return String
   * @throws Exception
   */
  public String ScanFunctionFromF1(String f1string,JParamObject PO,Object ConnObject,IScanFunction Scan,Object UserObject,Object OwnerObject) throws Exception {
    Stack SourceStack = new Stack();int i=0,b=0,f=0;String sTmp1,sTmp2,FTag,FString,F1String;
    JFunctionStub FS=null;
    F1String = f1string;
	int itmp = 1;
      while ( i < F1String.length() ) {
        sTmp1 = F1String.substring(i,i+1);
        // �����"("ѹջ
        if ( sTmp1.equals("(") ) {
          SourceStack.push(String.valueOf(i));
        }
        // ���ŷ��
        if ( sTmp1.equals(")") ) {
          sTmp2 = (String)SourceStack.pop();b = Integer.valueOf(sTmp2).intValue();f=b-1;
          // �ҳ���������ߵĿ�ʼλ��
          while ( f >= 0 ) {
            sTmp2 = F1String.substring(f,f+1);
            if ( FunctionTag.indexOf(sTmp2) != -1 ) {
              break;
            } else f--;
          }
          f++;
          FTag = F1String.substring(f,b);FString = F1String.substring(f,i+1);
          /**
           * ������ȡTRIM����Ϊ�ں������˿��ܴ��ڿո������ᵼ�º������㲻����
           * �磺KMJE(1001,JFFS) + KMJE(1002,JFFS)
           * modified by hufeng 2005.9.7
           */
          FTag = FTag.trim();
          FS = getFunctionByID(FTag,OwnerObject);
//          if ( FS == null ) {
//            System.out.println("�Ҳ���������ʵ����"+FTag);
//            throw new Exception("�Ҳ���������ʵ����"+FTag);
//          }
          if ( PO != null && FS != null && Scan instanceof JCalculateZbRequestObject  ) {
              PO.SetValueByParamName("TMP_NUM", String.valueOf(itmp));
			  itmp++;
          }
          FTag = (String)Scan.ScanReplace(FS,FString,PO,ConnObject,UserObject);  // ��һ��������
          //add by fsz 2004.5.`14 ���û�д��󣬷���SQL
          if(FS!= null)
            Text += FS.Function.getErrText();
          //end
          if ( FTag != null ) {
            F1String = F1String.substring(0,f)+FTag+F1String.substring(i+1,F1String.length());
            // i���滻����λ�ô���ʼ����
            i = f+FTag.length();
            continue;
          } else {
            i = f+FString.length();
            continue;
          }
        }
        i++;
      }
      if ( SourceStack.size() != 0 ) {
        throw new Exception("��ʽ�������,�������Ų�ƥ��!");
      }
      Boolean boo = false ;
	  while(b < F1String.length()){
        sTmp2 = F1String.substring(b,b+1);
        if(sTmp2.equals(";")){
            boo = true;
            b = 0 ;
            break ;

        }
        b++ ;
    }
    if(boo == true){
          throw new Exception("��ʽ�����в���������ַ� ';' !");
      }

    return F1String;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JFunctionStub getFunctionStub(String FuncTag) {
    int i,Count;JFunctionStub FS=null;String FT;
    Count = FunctionList.size();
    for(i=0;i<Count;i++) {
      FS = (JFunctionStub)FunctionList.get(i);
      if ( FS.FunctionID.equals(FuncTag) ) return FS;
      FT = FS.FunctionID+"_";
      if ( FT.equals(FuncTag) ) return FS;
    }
    return null;
  }

  /**
   * ȡ�����ָ���
   * @return String
   */
  public static String getFunctionTag(){
      return FunctionTag;
  }

  public static ArrayList getExtendFunctionList(){
      return mExtFuncList;
  }

}
