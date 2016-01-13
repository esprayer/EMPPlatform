package com.efounder.dctbuilder.server;

import java.util.*;

import com.borland.dx.sql.dataset.*;
import com.core.xml.*;
import com.efounder.db.*;
import com.efounder.dbc.*;
import com.efounder.dbc.QueryDescriptor;
import com.efounder.dctbuilder.data.*;
import com.efounder.eai.data.*;
import com.efounder.service.meta.*;
import com.efounder.service.meta.db.*;
import com.efounder.sql.*;
import com.efounder.dctbuilder.util.MetaDataUtil;
import com.efounder.dctbuilder.mdl.DictPluginManager;
import com.efounder.builder.meta.dctmodel.DCTMetaDataManager;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.base.util.ESPServerContext;

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
public class DictBuilderServer implements ExtendServerDataSet {
    public DictBuilderServer() {
    }
    protected void getMetaData(Database db, JParamObject PO, DctContext context,
                               ClientDataSetData cds) throws Exception {
        String dct = context.getDctID();
        DictMetadata dictMetadata = MetaDataUtil.getMetaData(db.getJdbcConnection(),PO,dct);
        context.setMetaData(dictMetadata);
        String tbn = DBTools.getDBAObject(PO, dct);
        cds.setTableName(tbn);

    }

    //生成查询SQL
    protected void prePareQuery(Database db, DctContext context, JParamObject PO, ClientDataSetData cds) throws Exception {
        String where = context.getWhere();
        List    list = getPluginList(context);
        String order=context.getOrder();
        String   col = "", str = "";
        for (int i = 0; i < list.size(); i++) {
            IDataSetServerProcess ifp = (IDataSetServerProcess) list.get(i);
            if (ifp.canProcess(db, PO, context, cds)) {
                col   = ifp.getQueryColumn(db, PO, context, col);
                where = ifp.getQueryWhere(db, PO, context, where);
                order = ifp.getQueryOrder(db, PO, context, order);
            }
        }
        //如果使用多语言视图，则读取多语言视图的数据，否则读取表的数据
        //add by gengeng 2009-10-21
        String tableName = "";
        if (context.isUseMLangeView()) {
            tableName = context.getMLangViewName();
        } else {
            tableName = context.getDctID();
        }
        String tbn = getRealTableName(db, context, PO, tableName) + " " + tableName;
        context.setLosableValue("ALLWHERE",where);
        if (!col.equals("")) {
            //筛选条件
            String filter = context.getString("FilterWhere", "");
            if (filter.trim().length() >0) {
             if(where.trim().length()>0)where+=" and ";
             where+=filter;
            }
            //是否分页读取，如果分页读取个数是0，默认是全部读取
            if (context.isPagingRead() && context.getPagingReadCount() > 0) {
                if(where.trim().length()>0)where+=" and ";
                where += "  ROWNUM <= " + context.getPagingReadCount();
            }
            if(order!=null&&order.trim().length()>0){
                if(where.trim().length()==0)where=" 1=1 ";
                where += " ORDER BY " + order;
            }
            String sql = DBTools.selectSql(tbn, col, where );
            System.out.println("LoadSql：" + sql);
            QueryDescriptor qd = new QueryDescriptor(sql);
            cds.setQuerySet(qd);
        }
    }

    //对于分单位字典查询，应当查询相应视图
    public String getRealTableName(Database db, DctContext context, JParamObject PO, String tableName) {
        String table = "";
        java.sql.Statement st = null;
        try {
            JConnection conn = JConnection.getInstance(db.getJdbcConnection());
            ESPServerContext serverContext = ESPServerContext.getInstance(PO, conn);
            st = conn.createStatement();
            serverContext.setStatement(st);
            DCTMetaData dctMetadata = (DCTMetaData) DCTMetaDataManager.getDCTMetaDataManager().getMetaData(serverContext, tableName);
            boolean queryMunitView = context.getString("queryMunitView", "0").equals("1");
            if (queryMunitView && dctMetadata.isDCT_MUNIT()) {
                table = DBTools.getDBAObject(PO, dctMetadata);
            } else {
                table = DBTools.getDBAObject(PO, tableName);
            }
        } catch (Exception e) {
            table = DBTools.getDBAObject(PO, tableName);
        }
        return table;
    }

    /**
     *
     * @param db Database
     * @param request RequestDataSetData
     * @param cds ClientDataSetData
     * @return Object
     * @throws Exception
     */
    public Object dataSetStartLoad(Database db, RequestDataSetData request, ClientDataSetData cds) throws Exception {
        DctContext context=null;
        Object oo=cds.getUserObject();
        if(oo instanceof DctContext)
            context = (DctContext) cds.getUserObject();
        if(oo instanceof Object[]){
            context = (DctContext)  ((Object[])oo)[0];
        }
        JParamObject PO = (JParamObject) request.getExtentObject();
        getMetaData(db,PO,context,cds);
        if(cds.getQuerySet()==null)
            prePareQuery(db,context,PO,cds);
        List list = getPluginList(context);
        for (int i = 0; i < list.size(); i++) {
            IDataSetServerProcess ifp = (IDataSetServerProcess) list.get(i);
            if (ifp.canProcess(db, PO, context, cds)) {
                long t1 = System.currentTimeMillis();
                ifp.startLoad(db, PO, context, cds);
                long t2 = System.currentTimeMillis();
                System.out.println(context.getDctID() + " startLoad,used:" + (t2 - t1) + ",plugin" + ifp);
            }
        }
        return null;
    }

    public Object dataSetEndLoad(Database db, RequestDataSetData request,
                                 ClientDataSetData cds, QueryDataSet qds) throws
            Exception {
        DctContext context=null;
        Object oo=cds.getUserObject();
        if(oo instanceof DctContext)
            context = (DctContext) cds.getUserObject();
        if(oo instanceof Object[]){
            context = (DctContext)  ((Object[])oo)[0];
        }

        JParamObject PO = (JParamObject) request.getExtentObject();
        List list = getPluginList(context);
        for (int i = 0; i < list.size(); i++) {
            IDataSetServerProcess ifp = (IDataSetServerProcess) list.get(i);
            try {
                if (ifp.canProcess(db, PO, context, cds)) {
                    long t1 = System.currentTimeMillis();
                    ifp.endLoad(db, PO, context, cds, qds);
                    long t2 = System.currentTimeMillis();
//                    System.out.println(context.getDctID() + " endLoad,used:" + (t2 - t1) + ",plugin" + ifp);
                }
            } catch (Exception ex) {
                System.out.println("error...");
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Object dataSetStartSave(Database db,
                                   RequestDataSetData request,
                                   ClientDataSetData cds, QueryDataSet qds) throws
            Exception {
        DctContext context=null;
        Object oo=cds.getUserObject();
        if(oo instanceof DctContext)
            context = (DctContext) cds.getUserObject();
        if(oo instanceof Object[]){
            context = (DctContext)  ((Object[])oo)[0];
        }

        JParamObject PO = (JParamObject) request.getExtentObject();
        //
        getMetaData(db,PO,context,cds);
        List list = getPluginList(context);
        for (int i = 0; i < list.size(); i++) {
            IDataSetServerProcess ifp = (IDataSetServerProcess) list.get(i);
            if (ifp.canProcess(db, PO, context, cds)) {
                long t1 = System.currentTimeMillis();
                ifp.startSave(db, PO, context, cds, qds);
                long t2 = System.currentTimeMillis();
                System.out.println(context.getDctID() + " startSave,used:" + (t2 - t1) + ",plugin" + ifp);
            }
        }
        return null;

    }

    public Object dataSetEndSave(Database db, RequestDataSetData request,
                                 ClientDataSetData cds, QueryDataSet qds) throws
            Exception {
        DctContext context = null;
        Object oo = cds.getUserObject();
        if (oo instanceof DctContext)
            context = (DctContext) cds.getUserObject();
        if (oo instanceof Object[]) {
            context = (DctContext) ((Object[]) oo)[0];
        }

        JParamObject PO = (JParamObject) request.getExtentObject();
        List list = getPluginList(context);
        for (int i = 0; i < list.size(); i++) {
            IDataSetServerProcess ifp = (IDataSetServerProcess) list.get(i);
            if (ifp.canProcess(db, PO, context, cds)) {
                long t1 = System.currentTimeMillis();
                ifp.endSave(db, PO, context, cds, qds);
                long t2 = System.currentTimeMillis();
                System.out.println(context.getDctID() + " endSave,used:" + (t2 - t1) + ",plugin" + ifp);
            }
        }
        return null;

    }
    protected List getPluginList(DctContext context ){
        return DictPluginManager.getInstance().getPlugins(IDataSetServerProcess.class,context.getPlugInKey(),DictPluginManager.LOADERSVR);
    }
}
