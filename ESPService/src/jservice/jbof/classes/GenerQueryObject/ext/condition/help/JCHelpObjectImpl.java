package jservice.jbof.classes.GenerQueryObject.ext.condition.help;

import com.efounder.eai.data.JParamObject;

import jframework.foundation.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCHelpObjectImpl {
    /**
     *
     */
    public JCHelpObjectImpl() {
    }

    /**
     *
     * @param PO
     */
    public Object helpKmmc(JParamObject Param) {
        String methodName = "HelpKmInfomation";
        String[] helpData = (String[])this.invokeDofHelp(methodName, Param);
        String data = null;
        if (helpData != null) {
            data = helpData[1];
        }
        return data;

    }
//
//    /**
//     *
//     * @param PO
//     */
//    public Object helpBmsx(JParamObject Param) {
//        JParamObject PO, PO1;
//
//        PO = JParamObject.Create();
//        PO1 = (JParamObject) Param;
//        PO1 = (JParamObject) Param;
//        String offline = "0";
//        if (PO1 != null) {
//            offline = PO1.GetValueByParamName("OFFLINE"); //确定是离线还是在线状态
//            if (offline == null) {
//                offline = "0";
//            }
//        }
//        PO.SetValueByEnvName("OFFLINE", offline);
//
//        PO.SetValueByParamName("helpNo", "");
//        PO.SetValueByParamName("sYear", PO.GetValueByEnvName("LoginFaYear"));
//        PO.SetValueByParamName("sTable", "ACBMSX");
//        PO.SetValueByParamName("sBhzd", "F_SXBH");
//        PO.SetValueByParamName("sMczd", "F_SXMC");
//        PO.SetValueByParamName("sJszd", "F_JS");
//        PO.SetValueByParamName("sMxzd", "F_MX");
////      PO.SetValueByParamName("sSjzd", "");
////      PO.SetValueByParamName("sNmzd", "");
//        PO.SetValueByParamName("BMSTRU", "@ZW_BMSXST");
////      PO.SetValueByParamName("sEveryWhere", sWhere);
////      PO.SetValueByParamName("sParameter", "");
//        PO.SetValueByParamName("sAllorClass", "1");
//        PO.SetValueByParamName("JS", "1");
////      PO.SetValueByParamName("SJBH", "");
////      PO.SetValueByParamName("SJMC", "");
//        String[] helpData = com.pansoft.help.cnttier.JHelp.showHelpDialog(null,
//            "部门属性帮助", PO);
//
//        String data = null;
//        if (helpData != null) {
//            data = helpData[0];
//        }
//        return data;
//
//    }

    /**
     *
     * @param methodName
     * @param PO
     * @return
     */
    private Object invokeDofHelp(String methodName, JParamObject Param) {
        try {
			return JActiveDComDM.DataActiveFramework.InvokeObjectMethod("HelpObject", methodName, Param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}