package com.security.jdof;

import com.efounder.eai.data.*;
import com.efounder.eai.EAI;
import java.util.*;
/**
 * <p>JDOFSecurityObject</p>
 * <p>安全对象。</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JDOFSecurityObject {
  public JDOFSecurityObject() {
  }

  /**
   * 单项数据权限检查。
   * @param strQxBH String
   * @param strSJBH String
   * @param strBZW String
   * @return boolean
   */
  public static Map getUserDataLimit(JParamObject PO, String qxbh) {
    try {
      JResponseObject RO = (JResponseObject) EAI.DAL.IOM("SecurityObject",
          "getUserDataLimit", PO, qxbh);
      if (RO != null)
        return (Map) RO.getResponseObject();
    }
    catch (Exception E) {
    }
    return null;
  }
  public static boolean CheckDataLimit(JParamObject PO,String strQxBH,String strSJBH,String strBZW){
    String zgbh = PO.GetValueByEnvName("UserName");
    PO.SetValueByParamName("pszgbh", zgbh); //职工姓名
    PO.SetValueByParamName("psqxbh", strQxBH); //权限编号
    PO.SetValueByParamName("pssjbh", strSJBH); //数据编号
    PO.SetValueByParamName("pibzw",  strBZW);  //权限性质：1:查询权限；2:维护权限

    /**
     * 调用检查。
     */
    try{
      JResponseObject RO = (JResponseObject) EAI.DAL.IOM("SecurityService","CheckDataLimit1", PO);
      return RO.getErrorCode() == 0 ;
    }
    catch(Exception E){
      return false;
    }
  }

  /**
   * 单项数据权限检查。
   * @param strQxBH String
   * @param strSJBH String
   * @param strBZW String
   * @return boolean
   */
  public static boolean CheckFunLimit(JParamObject PO,String strGNBH,String strBZW){
    String zgbh = PO.GetValueByEnvName("UserName");
    PO.SetValueByParamName("pszgbh", zgbh); //职工姓名
    PO.SetValueByParamName("FunctionNo", strGNBH); //数据编号
    PO.SetValueByParamName("pibzw",  strBZW);  //权限性质：1:查询权限；2:维护权限

    /**
     * 调用检查。
     */
    try{
      JResponseObject RO = (JResponseObject) EAI.DAL.IOM("SecurityService","CheckFunctionLimit", PO);
      return RO.getErrorCode() == 0 ;
    }
    catch(Exception E){
      return false;
    }
  }

  public static boolean RegDataLimitItem(JParamObject PO,String strQxBH,String strSJBH,String strBZW){
    String zgbh = PO.GetValueByEnvName("UserName");
    PO.SetValueByParamName("pszgbh", zgbh); //职工姓名
    PO.SetValueByParamName("psqxbh", strQxBH); //权限编号
    PO.SetValueByParamName("pssjbh", strSJBH); //数据编号
    PO.SetValueByParamName("pibzw",  strBZW);  //权限性质：1:查询权限；2:维护权限

    /**
     * 调用检查。
     */
    try{
      JResponseObject RO = (JResponseObject) EAI.DAL.IOM("SecurityService","RegDataLimitItem", PO);
      return RO.getErrorCode() == 0 ;
    }
    catch(Exception E){
      return false;
    }
  }

  public static boolean UnRegDataLimitItem(JParamObject PO,String strQxBH,String strSJBH){
    String zgbh = PO.GetValueByEnvName("UserName");
    PO.SetValueByParamName("pszgbh", zgbh); //职工姓名
    PO.SetValueByParamName("psqxbh", strQxBH); //权限编号
    PO.SetValueByParamName("pssjbh", strSJBH); //数据编号
    PO.SetValueByParamName("pibzw",  "1");  //权限性质：1:查询权限；2:维护权限

    /**
     * 调用检查。
     */
    try{
      JResponseObject RO = (JResponseObject) EAI.DAL.IOM("SecurityService","UnRegDataLimitItem", PO);
      return RO.getErrorCode() == 0 ;
    }
    catch(Exception E){
      return false;
    }
  }


  public static boolean RegDataLimitItems(JParamObject PO,String strQxBH, Vector strSJBH,String strBZW){
    String zgbh = PO.GetValueByEnvName("UserName");
    PO.SetValueByParamName("pszgbh", zgbh); //职工姓名
    PO.SetValueByParamName("psqxbh", strQxBH); //权限编号
    PO.setValue("pssjbh", strSJBH); //数据编号
    PO.SetValueByParamName("pibzw",  strBZW);  //权限性质：1:查询权限；2:维护权限

    /**
     * 调用检查。
     */
    try{
      JResponseObject RO = (JResponseObject) EAI.DAL.IOM("SecurityService","RegDataLimitItems", PO);
      return RO.getErrorCode() == 0 ;
    }
    catch(Exception E){
      return false;
    }
  }

  public static boolean UnRegDataLimitItems(JParamObject PO,String strQxBH,Vector strSJBH){
    String zgbh = PO.GetValueByEnvName("UserName");
    PO.SetValueByParamName("pszgbh", zgbh); //职工姓名
    PO.SetValueByParamName("psqxbh", strQxBH); //权限编号
    PO.setValue("pssjbh", strSJBH); //数据编号
    PO.SetValueByParamName("pibzw",  "1");  //权限性质：1:查询权限；2:维护权限

    /**
     * 调用检查。
     */
    try{
      JResponseObject RO = (JResponseObject) EAI.DAL.IOM("SecurityService","UnRegDataLimitItems", PO);
      return RO.getErrorCode() == 0 ;
    }
    catch(Exception E){
      return false;
    }
  }

}
