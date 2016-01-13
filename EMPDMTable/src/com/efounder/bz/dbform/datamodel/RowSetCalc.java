/**
 * 
 */
package com.efounder.bz.dbform.datamodel;

import java.util.ArrayList;

import com.efounder.builder.base.data.EFRowSet;
import com.efounder.service.script.ScriptManager;

/**
 * @author zhtbin
 *
 */
public final class RowSetCalc {

	/**
	 * 
	 * @param rowSet
	 * @param condition
	 * @return
	 */
	public static boolean runCondition(EFRowSet rowSet, String condition){

		boolean res = false;

		String where = null;
		ScriptManager scriptManager = ScriptManager.getInstance();
		where = "returnObject=( "+condition+" )?true:false";

		java.util.Map dataMap = rowSet.getDataMap();
		if ( dataMap == null ) return false;
		Object[] keys = dataMap.keySet().toArray();
		java.util.List list=new ArrayList();
		for(int i=0;i<keys.length;i++) {
			scriptManager.setObject(keys[i].toString(),dataMap.get(keys[i]));
			list.add(keys[i].toString()) ;
		}

		for(int i=0;i<list.size();i++){
			String key=(String)list.get(i);
			if(scriptManager.getObject(key,null)==null)
				scriptManager.setObject(key,"");
		}
		try{
			Boolean Res = (Boolean)scriptManager.runScriptText(where);
			if( Res != null ) res = Res.booleanValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		for(int i=0;i<keys.length;i++) {
			scriptManager.removeObject(keys[i].toString());
		}

		return res;
	}
}
