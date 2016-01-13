package com.efounder.dctbuilder.util;

import java.sql.*;
import java.util.*;

import com.core.xml.*;
import com.efounder.builder.base.data.*;
import com.efounder.eai.data.*;
import com.efounder.plugin.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Pansoft</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ESPPluginManager {
    public static Object loadPlugins(java.lang.String pluginKey, boolean initObject, String idKey){
    	java.util.List plugins = PluginManager.loadPlugins(pluginKey, true);
		for (int i = 0, n = plugins.size(); i < n; i++) {
            StubObject so = (StubObject) plugins.get(i);
            if (so.getPluginObject() == null){
            	continue;
            }
            if(idKey.equals(so.getID().toString())){
            	return so;
            }
		}
		return null;
    }
}
