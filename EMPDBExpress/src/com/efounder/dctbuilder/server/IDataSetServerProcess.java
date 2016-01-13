package com.efounder.dctbuilder.server;

import com.borland.dx.sql.dataset.*;
import com.efounder.dbc.*;
import com.efounder.dctbuilder.data.DctContext;
import com.efounder.eai.data.*;

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
public interface IDataSetServerProcess  {
    public boolean canProcess(Database dataBase,JParamObject PO,DctContext context,ClientDataSetData cds)  throws Exception;
    public String getQueryColumn(Database dataBase,JParamObject PO,DctContext context,String columns)  throws Exception;
    public String getQueryWhere(Database dataBase,JParamObject PO,DctContext context,String where)  throws Exception;
    public String getQueryOrder(Database dataBase,JParamObject PO,DctContext context,String order) throws Exception ;

    public Object startLoad(Database dataBase,JParamObject PO,DctContext context,ClientDataSetData cds) throws Exception;

    // 结束Load
    public Object endLoad(Database dataBase,JParamObject PO,DctContext context,ClientDataSetData cds, QueryDataSet qds) throws
        Exception;

    // 准备Save
    public Object startSave(Database dataBase,JParamObject PO,DctContext context,ClientDataSetData cds, QueryDataSet qds) throws
        Exception;

    // 结束Save
    public Object endSave(Database dataBase,JParamObject PO,DctContext context,ClientDataSetData cds, QueryDataSet qds) throws
        Exception;
}
