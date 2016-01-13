package com.efounder.dctbuilder.mdl;

import java.io.Serializable;
import java.util.*;
import com.core.xml.StubObject;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DictPluginManager implements Comparator, Serializable{
    public static final String DATASETEVENT = "DATASETEVENT";
    public static final String    LOADERCLR = "CLIENTLOAD";
    public static final String    LOADERSVR = "SERVERLOAD";
    
    Map pluginmap=new HashMap();
    Map syspulgin=new HashMap();
    
    protected static DictPluginManager dpm = null;
    
    public DictPluginManager() {

    }
    
    public static DictPluginManager getInstance(){
        if(dpm==null)dpm=new DictPluginManager();
        return dpm;
    }
    public void addPlugins(String userkey,String plugintype){
        java.util.List list=new ArrayList();
        java.util.List  tmlist=(List) syspulgin.get(plugintype);
        if(tmlist==null){
            tmlist = com.efounder.plugin.PluginManager.loadPlugins(
                    plugintype, true);
            syspulgin.put(plugintype,tmlist);
        }
        list.addAll(tmlist);
        tmlist=(List) syspulgin.get(plugintype+"_USER");
        if (tmlist == null) {
            tmlist = com.efounder.plugin.PluginManager.loadPlugins(
                    plugintype + "_USER", true);
            syspulgin.put(plugintype + "_USER", tmlist);
        }
        for(int i=0;tmlist!=null&&i<tmlist.size();i++){
            StubObject so=(StubObject)tmlist.get(i);
            if(so.getString("id","").equals(userkey))
                list.add(so);
        }
        tmlist= com.efounder.plugin.PluginManager.loadPlugins(
                plugintype+"_"+userkey , true);
        list.addAll(tmlist);
         java.util.Collections.sort(list,this);
         pluginmap.put(userkey+"_"+plugintype,list);
    }
    public java.util.List  getPlugins(Class cls,String userkey,String plugintype){
        java.util.List list=(List) pluginmap.get(userkey+"_"+plugintype);
        if(list==null){
          addPlugins(userkey, plugintype);
          list=(List) pluginmap.get(userkey+"_"+plugintype);
        }
        java.util.List newList=new ArrayList();
        for(int i=0;list!=null&&i<list.size();i++){
            StubObject so = (StubObject) list.get(i);
            Object po=so.getObject("pluginObject", null);
            if(cls.isAssignableFrom(po.getClass()))
                newList.add(po);
        }
        return newList;
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
}
