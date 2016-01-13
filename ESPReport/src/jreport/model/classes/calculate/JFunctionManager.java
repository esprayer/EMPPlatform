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
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JFunctionManager {
static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.calculate.Language"); //implements IScanFunction {
  static String FunctionTag="+-*/()><=,;?:~^&$%@#!`.'|";
  public java.util.Vector FunctionList = new java.util.Vector();
  public static JXMLBaseObject FunctionConfigXML = null;
  private static int TmpTableCount = 0;
  public static String Text="";
  //
  private static ArrayList mExtFuncList = null; // 外部函数
  //
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
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
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JFunctionManager() {
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
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
   * 初始化外部函数
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
      // 初始化函数
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
   * 为报告系统所加
   */
  public void initFunctionList(){
      InitFunctionConfigXML("function.xml");
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
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
         /* else { // modify by liukun 找到了就直接返回吧 20100715
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
               * 如果不在用户列表中则需要重新初始化函数
               * 不能直接用系统缓存因为系统缓存只在登录时初始
               * 这样对于像BB类函数，缓冲坐标的行为就可能会出错（中间修改过行列）
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
  //描述: 检查一条公式是不是只有报表函数组成
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public boolean IsReportF1(String F1String) throws Exception {
    JReportF1 EF = new JReportF1();
    String Text = F1String.toUpperCase();String Res;
    Res = ScanFunctionFromF1(Text,null,null,EF,"REPORT");
    return Text.equals(Res);
  }
  //------------------------------------------------------------------------------------------------
  //描述: 检查一条公式可不可以在汇总报表计算
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public boolean IsCountF1(String F1String) throws Exception {
    JReportF1 EF = new JReportF1();
    String Text = F1String.toUpperCase();String Res;
    Res = ScanFunctionFromF1(Text,null,null,EF,"COUNT");
    return Text.equals(Res);
  }

  /**
   * 是否是表内取数公式
   * @param F1String String
   * @return boolean
   * @throws Exception
   */
  public boolean isInnerReportF1(String F1String,String sAddbh, String sBbbh) throws Exception {
      return JCkService.isInnerReportFunction(F1String,sAddbh,sBbbh);
  }
  /**
   * 是否是表内取数公式
   * @param F1String String
   * @return boolean
   * @throws Exception
   */
  public boolean isReportF1(String F1String) throws Exception {
	  return JCkService.isReportFunction(F1String);
  }

  //------------------------------------------------------------------------------------------------
  //描述: 检查一条公式是不是由外部函数组成的
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public boolean isExternalF1(String F1String) throws Exception {
    JExternalF1 EF = new JExternalF1();
    String Text = F1String.toUpperCase();String Res;
    Res = ScanFunctionFromF1(Text,null,null,EF,null);
    return !Text.equals(Res);
  }
  //------------------------------------------------------------------------------------------------
  //描述: 用户模式到存储模式的转换
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String UserModelToStoreModel(String F1String,JParamObject PO,Object ConnObject) throws Exception {
    JScanUserToStore SUS = new JScanUserToStore();
    String Res = ScanFunctionFromF1(F1String,PO,ConnObject,SUS,null);
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //描述: 存储模式到用户模式的转换
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String StoreModelToUserModel(String F1String,JParamObject PO,Object ConnObject) throws Exception {
    JScanStoreToUser SSU = new JScanStoreToUser();
    String Res = ScanFunctionFromF1(F1String,PO,ConnObject,SSU,null);
    return Res;
  }


  //------------------------------------------------------------------------------------------------
  //描述: 存储模式到翻译模式的转换
  //设计: Skyline(2001.12.29)
  //实现: kun
  //修改:
  //------------------------------------------------------------------------------------------------
  public String StoreModelToTrunModel(String F1String,JParamObject PO,Object ConnObject) throws Exception {
      String Res = "";
      return Res;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String ScanFunctionFromF1(String f1string,JParamObject PO,Object ConnObject,IScanFunction Scan,Object UserObject) throws Exception {
    // 此函数的适配器
    return ScanFunctionFromF1(f1string,PO,ConnObject,Scan,UserObject,null);
  }

  /**
   * 分析并计算各报表函数
   * 返回一个计算后的表达式
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
        // 如果等"("压栈
        if ( sTmp1.equals("(") ) {
          SourceStack.push(String.valueOf(i));
        }
        // 括号封闭
        if ( sTmp1.equals(")") ) {
          sTmp2 = (String)SourceStack.pop();b = Integer.valueOf(sTmp2).intValue();f=b-1;
          // 找出函数的左边的开始位置
          while ( f >= 0 ) {
            sTmp2 = F1String.substring(f,f+1);
            if ( FunctionTag.indexOf(sTmp2) != -1 ) {
              break;
            } else f--;
          }
          f++;
          FTag = F1String.substring(f,b);FString = F1String.substring(f,i+1);
          /**
           * 将函数取TRIM，因为在函数两端可能存在空格，这样会导致函数计算不出来
           * 如：KMJE(1001,JFFS) + KMJE(1002,JFFS)
           * modified by hufeng 2005.9.7
           */
          FTag = FTag.trim();
          FS = getFunctionByID(FTag,OwnerObject);
//          if ( FS == null ) {
//            System.out.println("找不到函数的实现类"+FTag);
//            throw new Exception("找不到函数的实现类"+FTag);
//          }
          if ( PO != null && FS != null && Scan instanceof JCalculateZbRequestObject  ) {
              PO.SetValueByParamName("TMP_NUM", String.valueOf(itmp));
			  itmp++;
          }
          FTag = (String)Scan.ScanReplace(FS,FString,PO,ConnObject,UserObject);  // 第一条语句出处
          //add by fsz 2004.5.`14 如果没有错误，返回SQL
          if(FS!= null)
            Text += FS.Function.getErrText();
          //end
          if ( FTag != null ) {
            F1String = F1String.substring(0,f)+FTag+F1String.substring(i+1,F1String.length());
            // i从替换后听位置处开始处理
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
        throw new Exception("公式定义错误,左右括号不匹配!");
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
          throw new Exception("公式定义中不允许包含字符 ';' !");
      }

    return F1String;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
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
   * 取函数分隔符
   * @return String
   */
  public static String getFunctionTag(){
      return FunctionTag;
  }

  public static ArrayList getExtendFunctionList(){
      return mExtFuncList;
  }

}
