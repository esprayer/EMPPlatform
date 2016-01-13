package com.efounder.dctbuilder.variant;

import java.io.Serializable;
import java.util.*;

import com.core.xml.*;
import com.efounder.eai.data.JParamObject;
import com.efounder.plugin.PluginManager;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Pansoft</p>
 * @author gengeng
 * @version 1.0
 */
public class VariantPluginManager implements Comparator, Serializable {

    /**
     * 日期时间函数统一由DateParser处理
     */
    private static final String[] DATE = {"DATE", "TIME", "DATETIME",
                                          "YEARFIRSTDAY", "YEARLASTDAY", "MONTHFIRSTDAY", "MONTHLASTDAY",
                                          "NOW", "KJQJ"
                                         };
    private static java.util.List pluginlist = new ArrayList();
    static {
        loadPlugins();
    }

    public VariantPluginManager() {
    }

    private static void loadPlugins() {
        java.util.List tmlist = PluginManager.loadPlugins("Variant", true);
        pluginlist.addAll(tmlist);
    }

    public java.util.List getPlugins() {
        return pluginlist;
    }

    /**
     * 根据变量名称获取变量对象，变量名称去掉头尾@的部分
     *
     * @param  name String
     * @return      Object
     */
    public StubObject getPluginParser(JParamObject PO, String name) {
        if (name == null || name.trim().length() == 0) return null;
        name = name.toUpperCase();
        //日期函数统一处理
        if (Arrays.asList(DATE).contains(name)) name = "DATE";
        //分解
        if (name.indexOf(".") > 0) name = name.substring(0, name.indexOf("."));
        for (int i = 0, n = pluginlist.size(); i < n; i++) {
            StubObject so = (StubObject) pluginlist.get(i);
            if (name.equals(so.getID().toString().toUpperCase())) {
                return so;
            }
        }
        return null;
    }

    public int compare(Object o1, Object o2) {
        StubObject cmd1 = (StubObject) o1;
        StubObject cmd2 = (StubObject) o2;
        int disp1 = cmd1.getInt("order", 0);
        int disp2 = cmd2.getInt("order", 0);
        if (disp1 == disp2)
            return 0;
        if (disp1 < disp2)
            return -1;
        return 1;
    }
    
    /**
     * 根据变量名称获取变量对象，变量名称去掉头尾@的部分
     *
     * @param  name String
     * @return      Object
     */
    public StubObject getPluginParser(String name) {
        if (name == null || name.trim().length() == 0) return null;
        name = name.toUpperCase();
        //日期函数统一处理
        if (Arrays.asList(DATE).contains(name)) name = "DATE";
        //分解
        if (name.indexOf(".") > 0) name = name.substring(0, name.indexOf("."));
        for (int i = 0, n = pluginlist.size(); i < n; i++) {
            StubObject so = (StubObject) pluginlist.get(i);
            if (name.equals(so.getID().toString().toUpperCase())) {
                return so;
            }
        }
        return null;
    }

}
