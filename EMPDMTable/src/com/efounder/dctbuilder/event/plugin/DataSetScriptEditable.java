package com.efounder.dctbuilder.event.plugin;

import com.borland.dx.dataset.*;
import com.core.xml.*;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dbc.swing.table.*;
import com.efounder.dctbuilder.data.*;
import com.efounder.dctbuilder.event.*;
import com.efounder.dctbuilder.mdl.*;
import com.efounder.dctbuilder.util.*;
import com.efounder.dctbuilder.view.*;
import com.efounder.service.script.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DataSetScriptEditable implements ICellEditable,IClientEvent, Scriptable {

    /**
     *
     */
    public DataSetScriptEditable() {
    }

    /**
     *
     * @param o1 DictModel
     * @param context DctContext
     * @return boolean
     * @throws Exception
     */
    public boolean canProcess(DictModel dm, DctContext context) throws Exception {
        // 默认情况下返回true
        ScriptManager scriptManager = getScriptManager();
        try {
            // 设置参数
            scriptObject = getScriptObject(dm);
            scriptManager.setObject("DCT_ID", getDCT_ID(dm));
            scriptManager.setObject("DictView", dm.getView());
            scriptManager.setObject("DictModel", dm);
            scriptManager.setObject("NodeStub", dm.getNodeStub());
            scriptManager.setObject("DataSet", dm.getDataSet());
            scriptManager.setObject("dctContext", context);
            // 执行脚本
            Object oo = scriptManager.runFunction(this, "canProcess");
            if (oo == null || !(oo instanceof Boolean)) return true;
            return ((Boolean) oo).booleanValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.scriptObject = null;
            scriptManager.setObject("DCT_ID", null);
            scriptManager.setObject("DictView", null);
            scriptManager.setObject("DictModel", null);
            scriptManager.setObject("NodeStub", null);
            scriptManager.setObject("DataSet", null);
            scriptManager.setObject("dctContext", null);
        }
        return true;
    }

    /**
     *
     * @param model DictModel
     * @param context DctContext
     * @throws Exception
     */
    public void added(DictModel dm, DctContext context) throws Exception {
        ScriptManager scriptManager = getScriptManager();
        try {
            // 设置参数
            scriptObject = getScriptObject(dm);
            scriptManager.setObject("DCT_ID", getDCT_ID(dm));
            scriptManager.setObject("DictView", dm.getView());
            scriptManager.setObject("DictModel", dm);
            scriptManager.setObject("NodeStub", dm.getNodeStub());
            scriptManager.setObject("DataSet", dm.getDataSet());
            scriptManager.setObject("dctContext", context);
            // 执行脚本
            Object oo = scriptManager.runFunction(this, "added");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.scriptObject = null;
            scriptManager.setObject("DCT_ID", null);
            scriptManager.setObject("DictView", null);
            scriptManager.setObject("DictModel", null);
            scriptManager.setObject("NodeStub", null);
            scriptManager.setObject("DataSet", null);
            scriptManager.setObject("dctContext", null);
        }
    }

    /**
     *
     * @param dictModel DictModel
     * @param so StubObject
     */
    public void changed(DictModel dm, StubObject so) {
        ScriptManager scriptManager = getScriptManager();
        try {
            Column col = (Column) so.getObject("column", null);
            // 设置参数
            scriptObject = getScriptObject(dm);
            scriptManager.setObject("DCT_ID", getDCT_ID(dm));
            scriptManager.setObject("DictView", dm.getView());
            scriptManager.setObject("DictModel", dm);
            scriptManager.setObject("NodeStub", dm.getNodeStub());
            scriptManager.setObject("DataSet", dm.getDataSet());
            scriptManager.setObject("StubObject", so);
            scriptManager.setObject("column", col);
            scriptManager.setObject("columnName", col.getColumnName());
            // 执行脚本
            Object oo = scriptManager.runFunction(this, "changed");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.scriptObject = null;
            scriptManager.setObject("DCT_ID", null);
            scriptManager.setObject("DictView", null);
            scriptManager.setObject("DictModel", null);
            scriptManager.setObject("NodeStub", null);
            scriptManager.setObject("DataSet", null);
            scriptManager.setObject("StubObject", null);
            scriptManager.setObject("column", null);
            scriptManager.setObject("columnName", null);
        }
    }

    /**
     *
     * @param o Object
     * @param row int
     * @param col int
     * @return boolean
     */
    public boolean isCellEditable(Object o, int row, int col) {
        if (col < 0 || row < 0) return false;
        DictModel dm = (DictModel) o;
        BaseDBTableModel model = (BaseDBTableModel) ( (DictView) dm.getView()).getTable().getModel();
        if (model.getColumn(col) == null) return false;
        String column = model.getColumn(col).getColumnName();
        ScriptManager scriptManager = getScriptManager();
        try {
            // 设置参数
            scriptObject = getScriptObject(dm);
            scriptManager.setObject("DCT_ID", getDCT_ID(dm));
            scriptManager.setObject("DictView", dm.getView());
            scriptManager.setObject("DictModel", dm);
            scriptManager.setObject("NodeStub", dm.getNodeStub());
            scriptManager.setObject("DataSet", dm.getDataSet());
            scriptManager.setObject("editCol", column);
            scriptManager.setObject("editRowIndex", Integer.valueOf(row));
            scriptManager.setObject("editColIndex", Integer.valueOf(col));
            // 执行脚本
            Object oo = scriptManager.runFunction(this, "isCellEditable");
            if (oo == null || !(oo instanceof Boolean)) return true;
            return ((Boolean) oo).booleanValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.scriptObject = null;
            scriptManager.setObject("DCT_ID", null);
            scriptManager.setObject("DictView", null);
            scriptManager.setObject("DictModel", null);
            scriptManager.setObject("NodeStub", null);
            scriptManager.setObject("DataSet", null);
            scriptManager.setObject("editCol", null);
            scriptManager.setObject("editRowIndex", null);
            scriptManager.setObject("editColIndex", null);
        }
        return true;
    }


    /**
     * 脚本
     */
    protected ScriptObject scriptObject;
    /**
     *
     */
    protected ScriptManager scriptManager;

    /**
     *
     * @param scriptManager ScriptManager
     */
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
        return scriptObject;
    }

    /**
     *
     * @param model DictModel
     */
    protected ScriptObject getScriptObject(DictModel model) {
        String server= model.getCdsParam().getServerName();
        return DictUtil.getScriptObject((String) getScriptKey(), getDCT_ID(model), server);
    }

    /**
     *
     * @param model DictModel
     * @return String
     */
    protected String getDCT_ID(DictModel model) {
        String dctid = model.getDCT_ID();
        if (dctid.endsWith("_APPLY")) dctid = dctid.substring(0, dctid.indexOf("_APPLY"));
        return dctid;
    }

    /**
     *
     * @return Object
     */
    public Object getScriptKey() {
        return "BIZDict";
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
        if (scriptManager == null)
            scriptManager = ScriptManager.getInstance();
        return scriptManager;
    }
}
