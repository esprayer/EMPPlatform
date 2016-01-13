package com.efounder.dctbuilder.event.plugin;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.borland.dx.dataset.Column;
import com.core.xml.StubObject;
import com.efounder.dbc.swing.editor.SelfEnumCellEditor;
import com.efounder.dbc.swing.render.SelfEnumCellRenderer;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.data.DctContext;
import com.efounder.dctbuilder.event.IClientEvent;
import com.efounder.dctbuilder.mdl.DictModel;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class UpdateSelfEnumCell
    implements IClientEvent {

    public UpdateSelfEnumCell() {
    }
    
	public boolean canProcess(DictModel o1, DctContext context)
			throws Exception {
		return true;
	}


    public void changed(DictModel dm, StubObject param) {
        Column col = (Column) param.getObject("column", null);
        String colid = col.getColumnName();
        ColumnMetaData cmd = (ColumnMetaData) dm.getMetaData().getUsedColInfo(colid);
        if (cmd == null) return;
        int edittype = cmd.getColEditType();
        if(edittype != 15){
        	return;
        }
        String colval = col.getDataSet().getString(colid);
        String view = cmd.getColView();
        Hashtable hash = new Hashtable();
        List keyList = new ArrayList();
        String[] item = view.split(";");
        for (int i = 0; i < item.length; i++) {
            if (item[i] == null || item[i].indexOf(":") < 0) continue;
            String s1 = item[i].substring(0, item[i].indexOf(":"));
            String s2 = item[i].substring(item[i].indexOf(":") +1);
            hash.put(s1, s2);
            keyList.add(s1);
        }
        if(!hash.containsKey(colval)){
        	hash.put(colval, colval);
        	keyList.add(colval);
        	cmd.setString("COL_VIEW", view + ";" + colval + ":" + colval);
            Object editor = cmd.getEditor();
            SelfEnumCellEditor selfEnumCellEditor = (SelfEnumCellEditor)editor;
            selfEnumCellEditor.setColumnMetaData(cmd);
            cmd.setEditor(selfEnumCellEditor);
            Object render = cmd.getRender();
            SelfEnumCellRenderer selfEnumCellRenderer = (SelfEnumCellRenderer)render;
            selfEnumCellRenderer.setHash(hash);
            cmd.setRender(selfEnumCellRenderer);
        }
    }
}
