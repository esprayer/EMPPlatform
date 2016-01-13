package com.efounder.dctbuilder.variant;

import com.core.xml.*;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dbc.ClientDataSet;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.efounder.dctbuilder.util.*;
import com.efounder.eai.data.*;
import com.pub.util.ESPKeyValueUtil;

/**
 * <p>Title: 默认值管理工具</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class VariantUtil {

    /**
     * 变量管理器
     */
    public static VariantPluginManager pluginManager = new VariantPluginManager();

    /**
     *
     * @param  colid String
     * @param coldef String
     * @param  model DictModel
     * @return Object
     */
    public static String getDefaultValue(String colid, String coldef) {
    	JParamObject PO = JParamObject.Create();
        return getDefaultValue(PO, colid, coldef);
    }
    
    /**
    *
    * @param  colid String
    * @param coldef String
    * @param  model DictModel
    * @return Object
    */
   public static String getDefaultValue(String colid, String coldef, DictModel model) {
       if (coldef == null) return null;
       //默认值支持多种方式
       String[] items = coldef.split("[+]");
       StringBuffer sb = new StringBuffer();
       for (int i = 0, n = items.length; i < n; i++) {
           Object value = null;
           String var = items[i];
           if (var.startsWith("@") && var.endsWith("@")) {
               //如果从变量里没有获取到值，可能是引用的一个实际列
               value = getVariantValue(colid, items[i], model);
               if (value == null) {
                   value = getColumnValue(colid, items[i], model);
               }
           } else {
               value = var;
           }
           if (value != null) sb.append(value);
       }
       return sb.toString();
   }

    /**
    *
    * @param  colid String
    * @param coldef String
    * @param  model DictModel
    * @return Object
    */
   public static String getDefaultValue(JParamObject PO, String colid, String coldef) {
       if (coldef == null) return null;
       //默认值支持多种方式
       String[] items = coldef.split("[+]");
       StringBuffer sb = new StringBuffer();
       for (int i = 0, n = items.length; i < n; i++) {
           Object value = null;
           String var = items[i];
           if (var.startsWith("@") && var.endsWith("@")) {
               //如果从变量里没有获取到值，可能是引用的一个实际列
               value = getVariantValue(PO, colid, items[i]);
               if (value == null) {
                   value = "";
               }
           } else {
               value = var;
           }
           if (value != null) sb.append(value);
       }
       return sb.toString();
   }

    /**
     * 获取变量的值
     *
     * @param coldef String
     * @param model DictModel
     * @return Object
     */
    private static Object getVariantValue(JParamObject PO, String col, String var) {
        String   name = var.substring(1, var.length() - 1).trim();
        StubObject ss = pluginManager.getPluginParser(PO, name);
        if (ss != null) {
            IVariantAnalytic va = (IVariantAnalytic) ss.getPluginObject();
            //构造一个变量对象
            VariantStub stub = new VariantStub(name);
            stub.setPO(PO);
            stub.setString("ColumnName", col);
            return va.getVariantValue(stub);
        }
        return null;
    }

    /**
     * 当前用户编号
     *
     * @return String
     */
    public static String getUserName() {
        JParamObject PO = JParamObject.Create();
        return PO.GetValueByEnvName("UserName", "");
    }

    /**
     * 当前公司代码
     *
     * @return String
     */
    public static String getUnitID() {
        JParamObject PO = JParamObject.Create();
        return PO.GetValueByEnvName("UNIT_ID", "");
    }
    
    /**
     * 设置默认值
     *
     * @param model DictModel
     */
    public static void setDefaultValue(DictModel model){
        DictMetaDataEx dmd = model.getMetaData();
        java.util.List colList = dmd.getUsedColList();
        StubObject so = null;
        for (int i = 0, n = colList.size(); i < n; i++) {
            so = (StubObject) colList.get(i);
            setDefaultValue(model, so);
        }
    }
    
    /**
     * 设置列的默认值
     *
     * @param model DictModel
     * @param so StubObject
     */
    public static void setDefaultValue(DictModel model, StubObject so) {
        ClientDataSet cds = model.getDataSet();
        String      colid = so.getString("COL_ID", "");
        String    srccont = so.getString("F_SRCCONT", "");
        String     coldef = so.getString("COL_DEFAULT", "");
        java.util.Map map = null;
        if(srccont != null && srccont.trim().length() > 0){
        	srccont = srccont.toUpperCase();
        	map = ESPKeyValueUtil.getKeyValue("=", ";", srccont);
    	}
        if(map != null && map.containsKey("DEFAULTVAL")){
    		coldef = (String)map.get("DEFAULTVAL");
    	}else{
    		coldef = so.getString("COL_DEFAULT", "");
    	}
        if (coldef == null || coldef.trim().length() == 0) {
            return;
        }
        String value = getDefaultValue(colid, coldef, model);
        DictUtil.setVariantValue(cds, colid, value);
    }
    
    /**
     * 获取变量的值
     *
     * @param coldef String
     * @param model DictModel
     * @return Object
     */
    private static Object getVariantValue(String col, String var, DictModel model) {
        String   name = var.substring(1, var.length() - 1).trim();
        JParamObject PO = JParamObject.Create();
        StubObject ss = pluginManager.getPluginParser(PO, name);
        if (ss != null) {
            IVariantAnalytic va = (IVariantAnalytic) ss.getPluginObject();
            //构造一个变量对象
            VariantStub stub = new VariantStub(name, model);
            stub.setString("ColumnName", col);
            return va.getVariantValue(stub);
        }
        return null;
    }

    /**
     * getColumnValue
     *
     * @param colid String
     * @param string String
     * @param model DictModel
     * @return Object
     */
    private static Object getColumnValue(String colid, String var, DictModel model) {
        String name = var.substring(1, var.length() - 1).trim();
        if (name != null && name.trim().length() > 0 && model != null && model.getDataSet() != null && model.getDataSet().hasColumn(name) != null) {
//            return model.getDataSet().format(name);
        }
        return null;
    }
}
