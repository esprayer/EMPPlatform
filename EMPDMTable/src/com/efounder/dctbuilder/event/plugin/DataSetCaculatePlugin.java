package com.efounder.dctbuilder.event.plugin;

import com.borland.dx.dataset.*;
import com.core.xml.*;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.dctbuilder.data.*;
import com.efounder.dctbuilder.event.*;
import com.efounder.dctbuilder.mdl.*;
import com.efounder.dctbuilder.view.*;

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
public class DataSetCaculatePlugin implements IClientEvent{


    public DataSetCaculatePlugin() {
    }


    public void changed(DictModel dm,StubObject param) {
        // 设置是否显示列脚
        String showFooter = dm.getMetaData().getExtAttribute("", "showFooter");
        if (!"1".equals(showFooter))
            return;
        Column column = (Column) param.getObject("column", null);
        String colid = column.getColumnMeataData().getColid();
        DictView view =(DictView) dm.getView();
//        DictTable table= view.getTable();
//        int columnindex = -1;
//        try{
//        	columnindex = table.getColumnModel().getColumnIndex(colid);
//        }catch(Exception e){
//        	System.out.println("没有获取到列["+ colid +"]的索引号!");
//        	return;
//        }
        ColumnMetaData cmd = (ColumnMetaData) dm.getMetaData().getUsedColInfo(colid);
        if (cmd == null) {
            System.out.println("没有获取到列[" + colid + "]的元数据");
            return;
        }
        int index = dm.getMetaData().getVisibleColList().indexOf(cmd);
        view.hejiONE(cmd, index);
    }

    public boolean canProcess(DictModel dm, DctContext context) throws Exception {
        return true;
    }

}
