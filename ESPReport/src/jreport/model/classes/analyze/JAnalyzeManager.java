package jreport.model.classes.analyze;

import java.util.*;

import com.efounder.eai.data.JParamObject;

import jframework.foundation.classes.*;

public class JAnalyzeManager {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.analyze.Language");

    private static ResourceBundle mResourceBundle;

    static {
        mResourceBundle = ResourceBundle.getBundle("jreport.model.classes.analyze.analyze-config");
    }

    /**
     * 必要的参数：
     * PO.SetValueByParamName("ModelClassName",JAnalyzeManager.getString("penetratedata.modelclass"));
     * PO.SetValueByParamName("ANALYZE_OBJECT",JAnalyzeManager.getString("penetratedata.analyzeobject"));
     */
    public static void openReport(JParamObject PO, Object o) throws Exception {
        JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "OpenAnalyzeReport", PO, o);
    }

    /**
     * 新格式采用通用查询窗口展示
     */
    public static void openNewReport(JParamObject PO) throws Exception {
        JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "OpenNewAnalyzeReport", PO);
    }

    public static String getString(String resourceName) {
        return mResourceBundle.getString(resourceName);
    }
}
