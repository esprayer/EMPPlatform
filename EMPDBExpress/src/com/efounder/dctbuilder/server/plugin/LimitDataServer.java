package com.efounder.dctbuilder.server.plugin;

import java.sql.*;
import java.util.*;

import com.borland.dx.dataset.*;
import com.borland.dx.sql.dataset.*;
import com.efounder.db.*;
import com.efounder.dbc.*;
import com.efounder.dctbuilder.data.*;
import com.efounder.dctbuilder.server.*;
import com.efounder.dctbuilder.util.*;
import com.efounder.eai.data.*;
import com.efounder.sql.*;
import com.efounder.service.meta.db.DictMetadata;

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
public class LimitDataServer extends DataSetServerAdaper {

    /**
     *
     */
    public LimitDataServer() {
    }

    /**
     *
     * @param db Database
     * @param PO JParamObject
     * @param context DctContext
     * @param where String
     * @return String
     * @throws Exception
     */
    public String getQueryWhere(Database db, JParamObject PO, DctContext context, String where) throws Exception {
        Statement stmt = db.createStatement();
        String sjid;
        try {
            sjid = getGrantColumn(stmt, context, PO);
        } finally {
            stmt.close();
        }
        String qbzw = context.getQueryQXBZW();
        String bzw = context.getQXBZW();
        if (qbzw == null || qbzw.trim().length() == 0) qbzw = bzw;
        if (qbzw == null || qbzw.trim().length() == 0) qbzw = DctConstants.DCTREADBIT;
        PO.SetValueByParamName("pszgbh", PO.GetValueByEnvName("UserName"));
        PO.SetValueByParamName("psqxbh", context.getQXBH());
        PO.SetValueByParamName("pscol",  context.getDctID() + "." + sjid);
        PO.SetValueByParamName("pibzw",  qbzw);
        String limit = DBOSecurityObject.CheckDataLimit3New(JConnection.getInstance(db.getJdbcConnection()), PO);
        if(limit != null && limit.trim().length() > 0){
	        if (where != null && where.length() > 0) 
	        	where += " AND " + limit;
	        else
	        	where = limit;
        }
        return where;
    }

    /**
     *
     * @param db Database
     * @param PO JParamObject
     * @param context DctContext
     * @param cds ClientDataSetData
     * @param qds QueryDataSet
     * @return Object
     * @throws Exception
     */
    public Object endLoad(Database db, JParamObject PO, DctContext context, ClientDataSetData cds, QueryDataSet qds) throws Exception {
        //如果字典是可写的，得到有写权限的编码
        if(context.isReadOnly())return null;
        DictMetadata dmd=context.getMetaData();
        DictMetaDataEx dmdex=new DictMetaDataEx(dmd);
        Statement stmt = db.createStatement();
        String sjid;
        sjid = getGrantColumn(stmt, context, PO);
        String wbzw = context.getWriteQXBZW();
        String bzw = context.getQXBZW();
        if (wbzw == null || wbzw.trim().length() == 0) wbzw = bzw;
        if (wbzw == null || wbzw.trim().length() == 0) wbzw = DctConstants.DCTWRITEBIT;
        PO.SetValueByParamName("pszgbh", PO.GetValueByEnvName("UserName"));
        PO.SetValueByParamName("psqxbh", context.getQXBH());
        PO.SetValueByParamName("pscol", context.getDctID()+"."+sjid);
        PO.SetValueByParamName("pibzw", wbzw);
        String limit = DBOSecurityObject.CheckDataLimit2(JConnection.getInstance(db.getJdbcConnection()), PO);

        try {
          String where = dmdex.getDctBmCol() + " like '" + context.getCurBM() + "%'";
          if (context.getWhere() != null && context.getWhere().length() > 0 &&
              where.length() > 0)
            where += " and " + context.getWhere();
          if (context.isGradeQuery() && !dmdex.getDctJsCol().equals(""))
            where = where + " and " + dmdex.getDctJsCol() + "=" +
                context.getCurJS();
          where += " and " + limit;
            String sql=DBTools.selectSql(DBTools.getDBAObject(PO,context.getDctID()),sjid,where);
            ResultSet rs=stmt.executeQuery(sql);
            List list=new ArrayList();
            while(rs.next())
                list.add(rs.getString(1));
            context.setValue(DctConstants.WRITEABLEDATA,list);
        }finally{
            stmt.close();
        }
        return null;
    }

    public boolean canProcess(Database dataBase, JParamObject PO,
                              DctContext context, ClientDataSetData cds) throws
            Exception {
        if (context.getQXBH() == null || "".equals(context.getQXBH())) {
          return false;
        }

        return true;
    }
    protected String getGrantColumn(Statement stmt,DctContext context,JParamObject PO) throws Exception {
        String sjid;
        String sql = "select * from " + DBTools.getDBAObject(PO, "BSGRAN");
        sql += " where F_QXBH='" + context.getQXBH() + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            sjid = rs.getString("F_BHZD");
        } else
            return null; //找不到权限表
        return sjid;

    }
    public Object startSave(Database dataBase, JParamObject PO,
                            DctContext context, ClientDataSetData cds,
                            QueryDataSet qds) throws Exception {
        String sjid="";

        DataSetView dsv = new DataSetView();
        qds.getDeletedRows(dsv);
        List delItem = DictUtil.getValueFromDataSet(dsv, sjid);
        dsv = new DataSetView();
        qds.getInsertedRows(dsv);
        List insertItem = DictUtil.getValueFromDataSet(dsv, sjid);
        //用来处理权限
        context.setLosableValue(DctConstants.INSERTITEM, insertItem);
        context.setLosableValue(DctConstants.DELETEITEM, delItem);
        return null;
    }
    public Object endSave(Database dataBase, JParamObject PO,
                          DctContext context, ClientDataSetData cds,
                          QueryDataSet qds) throws Exception {
        List insertItem = (List) context.getLosableValue(DctConstants.INSERTITEM, null);
        List delItem = (List) context.getLosableValue(DctConstants.DELETEITEM, null);
        JConnection conn = JConnection.getInstance(dataBase.getJdbcConnection());
        //
        String qxbh = context.getQXBH();
        if("BSUSER".equals(qxbh)){
          return null; //bsuser需要用插件DictDataServerBSUSER特殊处理
        }
        if (insertItem != null)
            DictUtil.RegDataLimit(conn, qxbh, insertItem, PO, true);
        if (delItem != null)
            DictUtil.RegDataLimit(conn, qxbh, delItem, PO, false);
        return null;
    }
}
