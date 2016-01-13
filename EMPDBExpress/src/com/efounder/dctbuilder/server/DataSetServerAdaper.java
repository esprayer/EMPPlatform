package com.efounder.dctbuilder.server;

import com.borland.dx.sql.dataset.*;
import com.efounder.dbc.*;
import com.efounder.dctbuilder.data.*;
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
public class DataSetServerAdaper implements IDataSetServerProcess {
    public DataSetServerAdaper() {
    }
    public boolean canProcess(Database dataBase, JParamObject PO,
                              DctContext context, ClientDataSetData cds) throws Exception {
        return true;
    }
    public Object endLoad(Database dataBase, JParamObject PO,
                          DctContext context, ClientDataSetData cds,
                          QueryDataSet qds) throws Exception {
        return null;
    }

    public String getQueryColumn(Database dataBase, JParamObject PO,
                                 DctContext context, String columns) throws Exception {
        return columns;
    }
    public String getQueryOrder(Database dataBase, JParamObject PO,
                                DctContext context, String order) throws Exception {
        return order;
    }
    public String getQueryWhere(Database dataBase, JParamObject PO,
                                DctContext context, String where) throws Exception {
        return where;
    }
    public Object startLoad(Database dataBase, JParamObject PO,
                            DctContext context, ClientDataSetData cds) throws
            Exception {
        return null;
    }
    public Object startSave(Database dataBase, JParamObject PO,
                            DctContext context, ClientDataSetData cds,
                            QueryDataSet qds) throws Exception {
        return null;
    }
    public Object endSave(Database dataBase, JParamObject PO,
                          DctContext context, ClientDataSetData cds,
                          QueryDataSet qds) throws Exception {
        return null;
    }
}
