package com.efounder.dbc.blob;

import java.util.*;

import com.borland.dx.sql.dataset.*;
import com.efounder.dbc.*;
import java.sql.*;
import com.borland.dx.dataset.Variant;
import com.efounder.db.*;
import com.efounder.sql.JSQLToolkit;
import com.efounder.sql.*;
import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataSetException;
import com.borland.dx.dataset.*;

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
public class BlobServerDataSet implements ExtendServerDataSet {
    public BlobServerDataSet() {
    }

    protected void loadBlogData(Database db,BlobContext context,ClientDataSetData cdsd,QueryDataSet qds) throws  Exception {
        if(!context.isLoadOpen())return;
        if(qds.getRowCount()==0)return;
        JConnection con = JConnection.getInstance(db.getJdbcConnection());
        String[] blobColumn = context.getBlobColumn();
        String[] keyColumn = context.getKeyColumn();
        String sql = "select ";
        for (int i = 0; i < keyColumn.length; i++) {
            sql += keyColumn[i] + ",";
        }
        for (int i = 0; i < blobColumn.length; i++) {
             sql += blobColumn[i] + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " from " + cdsd.getTableName();
        String where = context.getWhere();
        if (where != null&&!"".equals(where))
            sql += " " + where;
        String key;
        int type;
        Map blobMap = new HashMap();
        Statement stmt = con.createStatement();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                key = "";
                for (int i = 0; i < keyColumn.length; i++) {
                    type = qds.getColumn(keyColumn[i]).getDataType();
                    if (type == Variant.STRING)
                        key += keyColumn[i] + "='" + rs.getString(keyColumn[i]) +
                                "';";
                    if (type == Variant.BIGDECIMAL || type == Variant.INT ||
                        type == Variant.LONG)
                        key += keyColumn[i] + "=" + rs.getInt(keyColumn[i]) +
                                ";";
                }
                for (int i = 0; i < blobColumn.length; i++) {
//                    Object oo = con.getSQLToolkit().getBlogData(rs,
//                            blobColumn[i]);
//                    blobMap.put(key + blobColumn[i], oo);
                }
            }
            rs.close();
            Map map=new HashMap();
            qds.first();
            do{
                long ir=qds.getInternalRow();
                key=context.getKeys(qds);
                for(int i=0;i< blobColumn.length; i++)
                   map.put(ir+" "+blobColumn[i],blobMap.get(key+blobColumn[i]));

            }while(qds.next());

            context.setBlobMap(map);
        } finally {
            con.BackStatement(stmt, null);
        }
    }
    public Object dataSetEndLoad(Database dataBase, RequestDataSetData request,
                                 ClientDataSetData cds, QueryDataSet qds) throws Exception {
        Object oo[]=(Object[])cds.getUserObject();
        BlobContext context=(BlobContext)oo[1];
        loadBlogData(dataBase,context,cds,qds);
        return null;
    }

    public Object dataSetEndSave(Database db, RequestDataSetData request, ClientDataSetData cds, QueryDataSet qds) throws Exception {
          JConnection con = JConnection.getInstance(db.getJdbcConnection());
          Object oo[]=(Object[])cds.getUserObject();
          BlobContext context=(BlobContext)oo[1];
          Map map=context.getBlobMap();
          String key;
          Iterator it=map.keySet().iterator();
          while(it.hasNext()){
        	  key = (String) it.next();
        	  Object o=map.get(key);
        	  String bcol=key.substring(key.lastIndexOf(";")+1);
        	  key=key.substring(0,key.lastIndexOf(";"));
        	  key=key.replaceAll(";"," and ");
        	  con.getSQLToolkit().updateBlobData(con,cds.getTableName(),bcol,key,(byte[])o);
          }
          return null;
    }

    public Object dataSetStartLoad(Database dataBase,
                                   RequestDataSetData request,
                                   ClientDataSetData cds) throws Exception {


        return null;
    }
    public Object dataSetStartSave(Database dataBase,
                                   RequestDataSetData request,
                                   ClientDataSetData cds, QueryDataSet qds) throws Exception {
        return null;
    }
}
