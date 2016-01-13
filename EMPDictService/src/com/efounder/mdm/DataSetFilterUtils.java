package com.efounder.mdm;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.service.script.ScriptManager;

import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DataSetFilterUtils {
 	/**
 	 *
 	 */
	protected DataSetFilterUtils() {
	}
	/**
	 *
	 * @param mainDataSet EFDataSet
	 * @param condition String
	 * @return EFDataSet
	 * @throws Exception
	 */
	public static EFDataSet filterDataSet(EFDataSet mainDataSet,String condition) throws Exception {
		return filterDataSet(mainDataSet,condition,null);
	}
	public static EFDataSet filterDataSet(EFDataSet mainDataSet,String condition,String[]cols) throws Exception {
		if ( mainDataSet == null ) return null;
	    if ( condition == null || condition.trim().length() == 0 ) return mainDataSet;
	    // 从缓冲里取
	    EFDataSet filterDataSet = mainDataSet.getFilterDataSet(condition);
	    if ( filterDataSet != null ) return filterDataSet;
	    filterDataSet = EFDataSet.getInstance(mainDataSet.getTableName());
	    filterDataSet.setPrimeKey(mainDataSet.getPrimeKey());
	    String where = null;
	    ScriptManager scriptManager = ScriptManager.getInstance();
	    where = "returnObject=( "+condition+" )?true:false";
	    java.util.List rowSetList = mainDataSet.getRowSetList();
	    EFRowSet rowSet = null;boolean res = false;
	    for(int i=0;rowSetList!=null&&i<rowSetList.size();i++) {
	      rowSet = (EFRowSet)rowSetList.get(i);
	      res = check(scriptManager,rowSet,where,cols);
	      if ( res ) filterDataSet.addRowSet(rowSet);
	    }
	    // 放到缓冲区里
	    mainDataSet.putFilterDataSet(condition,filterDataSet);
	    return filterDataSet;
	}
	/**
	 *
	 * @param scriptManager ScriptManager
	 * @param rowSet EFRowSet
	 * @param condition String
	 * @return boolean
	 * @throws Exception
	 */

	protected static boolean check(ScriptManager scriptManager,EFRowSet rowSet,String condition,String[]cols) throws Exception {
		java.util.Map dataMap = rowSet.getDataMap();boolean res = false;
		if ( dataMap == null ) return false;
		Object[] keys = dataMap.keySet().toArray();
		java.util.List list=new ArrayList();
		for(int i=0;i<keys.length;i++) {
			scriptManager.setObject(keys[i].toString(),dataMap.get(keys[i]));
			list.add(keys[i].toString()) ;
		}
		for(int i=0;cols!=null&&i<cols.length;i++)
		{
			if(scriptManager.getObject(cols[i],null)==null)
				scriptManager.setObject(cols[i],"");
		}
		for(int i=0;i<list.size();i++){
			String key=(String)list.get(i);
			if(scriptManager.getObject(key,null)==null)
				scriptManager.setObject(key,"");
		}
		Boolean Res = (Boolean)scriptManager.runScriptText(condition);
		if( Res != null ) res = Res.booleanValue();
		for(int i=0;i<keys.length;i++) {
			scriptManager.removeObject(keys[i].toString());
		}
		return res;
	}
}
