package com.efounder.bz.dbform.bizmodel;

import org.openide.*;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.mdm.*;
import com.efounder.service.script.*;

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
public class DataSetFilterAdapter extends DataComponentAdapter implements IMainDataFilter,ComponentManager,Scriptable {
  private Object paramObj;
  public DataSetFilterAdapter() {
  }
  public EFDataSet filterMainData(EFDataSet mainds) {
    EFDataSet ds=null;
    String[] key = mainds.getPrimeKey();
//    try {
//      ds = (EFDataSet) ScriptUtil.runComponentFunction(this, "filterMainData",
//          new String[] {"dataSet","param"},
//          new Object[] {mainds,paramObj});
//    }
//    catch (Exception ex) {
//    	//modify by ZhangES 2014-11-26 不知道为什么，脚本里面的方法获取不到参数，老是弹出窗口报错，鬼知道为什么，屏蔽掉了，待后人解决！！！
//    	ex.printStackTrace();
////      ErrorManager.getDefault().notify(ex);
//    }
    if (ds == null)
      ds = mainds;
    else {
        ds.setPrimeKey(key);
        ds.buildPrimeKeyIndex();
    }
    return ds;
  }

  public void initScript(ScriptManager scriptManager) {
    }
    /**
     *
     * @param scriptManager ScriptManager
     */
    public void finishScript(ScriptManager scriptManager) {
    }
    /**
     *
     * @return ScriptObject
     */
    public ScriptObject getScriptObject() {
//      return ScriptUtil.getFormFunctionObject(this);
        return null;
    }
    /**
     *
     * @return Object
     */
    public Object getScriptKey() {
      return null;
    }
    /**
     *
     * @return Object
     */
    public Object getScriptInstance() {
      return null;
    }
    /**
     *
     * @return ScriptManager
     */
    public ScriptManager getScriptManager() {
//      return ScriptUtil.getScriptManager(this);
        return null;
    }

  public void setParamObj(Object paramObj) {
    this.paramObj = paramObj;
  }

  public Object getParamObj() {
    return paramObj;
  }
}
