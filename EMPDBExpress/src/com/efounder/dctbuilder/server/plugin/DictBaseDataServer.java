package com.efounder.dctbuilder.server.plugin;

import java.sql.*;
import java.util.*;

import com.borland.dx.sql.dataset.Database;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.db.*;
import com.efounder.dbc.*;
import com.efounder.dctbuilder.data.*;
import com.efounder.dctbuilder.server.*;
import com.efounder.dctbuilder.util.*;
import com.efounder.eai.data.*;
import com.efounder.service.meta.db.*;
import com.efounder.sql.JConnection;
import com.efounder.eai.service.ParameterManager;

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
public class DictBaseDataServer extends DataSetServerAdaper{
    public DictBaseDataServer() {
        super();
    }

    public String getQueryColumn(Database dataBase, JParamObject PO,
                                 DctContext context, String columns) throws Exception {
        DictMetadata dmd=context.getMetaData();
        return MetaDataUtil.getUsedColString(dmd);
    }

    public String getQueryWhere(Database dataBase, JParamObject PO,
                                DctContext context, String where) throws Exception {
         DictMetadata dmd=context.getMetaData();
         DictMetaDataEx dmdex=new DictMetaDataEx(dmd);
         if(dmdex.getDctBmCol().trim().length()!=0){
             if(where.trim().length()>0)where+=" and ";
             where += dmdex.getDctBmCol() + " like '" + context.getCurBM() + "%'";
         }
        if(!dmdex.getDctJsCol().equals("")){
          //添加是否设置级数条件得判断，以使分级字典中能够显示无级数列的多语言视图 zhangft 20091121
//          if (context.isGradeQuery()) {
//            where += " and " + dmdex.getDctJsCol() + "=" +
//                context.getCurJS();
//          }
//          else {
//            where = where + " and " + dmdex.getDctJsCol() + ">0";
//          }
          if (context.getString("NoJsCon", "0").equals("0")) {
              if (context.isGradeQuery()) {
                  where += " and " + dmdex.getDctJsCol() + "=" + context.getCurJS();
              } else {
                  where = where + " and " + dmdex.getDctJsCol() + ">0";
              }
          }
        }
//注意 dictBuilderServer第49行 String where = context.getWhere(); 再+就重复了
//        if (context.getWhere().trim().length() > 0) {
//            where += " and " + context.getWhere();
//        }
        return where;
    }

    public String getQueryOrder(Database dataBase, JParamObject PO,
                                DctContext context, String order) {
        DictMetadata dmd = context.getMetaData();
        DictMetaDataEx dmdex = new DictMetaDataEx(dmd);
        String bmcol = dmdex.getDctBmCol();
        if(bmcol!=null&&bmcol.trim().length()>0){
            if(order!=null&&order.trim().length()>0)order+=",";
            order +=  bmcol;
        }
        if (!dmdex.getDctJsCol().equals("")){
          //添加是否设置级数条件得判断，以使分级字典中能够显示无级数列的多语言视图 zhangft 20091121
          if (context.getString("NoJsCon", "0").equals("0")) {
              if(order!=null&&order.trim().length()>0)order+=",";
            order += dmdex.getDctJsCol();
          }
        }
        //添加其他主键列 gengeng 2009-12-29
        if (order.length() > 0) order += ",";
        java.util.List keycol = dmdex.getKeyColumnName();
        for (int i = 0, n = keycol.size(); i < n; i++) {
            String col = keycol.get(i).toString().trim();
            if (col.length() == 0) {
                continue;
            }
            if (order.indexOf(col) >= 0) {
                continue;
            }
            order += col + ",";
//            if (order.length() == 0) {
//                order += col;
//            } else if (order.indexOf(col) < 0) {
//                order += "," + col;
//            }
        }
        if (order.length() > 0) return order.substring(0, order.length() - 1);
        return order;
    }

    public Object startLoad(Database dataBase, JParamObject PO,
                            DctContext context, ClientDataSetData cds) throws
            Exception {
        return null;
    }

    public Object endLoad(Database db, JParamObject PO,
                          DctContext context, ClientDataSetData cds,
                          QueryDataSet qds) throws Exception {
        try {
//            context.setValue("loadFkeyData", qds.getRowCount() > 0 ? "1" : "0");
            loadFKEYObject(db, PO, context);
        } catch (Exception ex) {
            System.out.println("load fkey fail.");
            ex.printStackTrace();
        }
        List list = context.getOtherFKeyList();
        Map pMap  = context.getFKEYWhereMap();
        Statement stmt = db.createStatement();
        Object whereObj = null;
        try {
            for (int i = 0; list != null && i < list.size(); i++) {
                String dct = (String) list.get(i);
                DictMetadata dm = MetaDataUtil.getMetaData(db.getJdbcConnection(), PO, dct);
                String bmcol = dm.getString("DCT_BMCOLID", "");
                String mccol = dm.getString("DCT_MCCOLID", "");
                String jscol = dm.getString("DCT_JSCOLID", null);
                if (pMap != null) {
                  whereObj = pMap.get(dct);
                }
                String where = "(1 = 1)";
                if (whereObj != null && whereObj.toString().trim().length() > 0) {
                    where += " and " + whereObj.toString();
                }
                if (jscol != null && !"".equals(jscol.trim()))
                    where += " and " + jscol + " > 0";
                String tab = DBTools.getDBAObject(PO, dct);
                String sql = " select " + dct + ".* " +
                             " from " + tab + " " + dct +
                             " where " + where;
                EFDataSet eds=EFDataSet.getInstance(dct);
                eds.setPrimeKey(new String[]{bmcol});
                ResultSet rs = stmt.executeQuery(sql);
                DataSetUtils.resultSet2DataSet(rs,eds);
                rs.close();
                context.setFKEYMetaData(dct, dm);
                context.setFKEYDataSet(dct, eds);
            }
        } finally {
            stmt.close();
        }
        return null;
    }

    protected void loadFKEYObject(Database db, JParamObject PO,
                                 DctContext context) throws Exception {
      if (!"1".equals(context.getString("ID2NAME", "1"))) return;
      DictMetadata mdm=context.getMetaData();
      java.util.List list=MetaDataUtil.getUsedColumnStub(mdm);
      if(list==null)return;
      JConnection conn = JConnection.getInstance(db.getJdbcConnection());
      Statement stmt1 = conn.createStatement();
      Statement stmt2 = conn.createStatement();
      ResultSet rs1 = null, rs2 = null;
      try{
          String sql;
          for (int i = 0; i < list.size(); i++) {
              ColumnMetaData cmd = (ColumnMetaData) list.get(i);
              if (cmd.isFKEY()/** && cmd.isVisible()*/) {
                  DictMetadata dictMetadata = context.getFKEYMetaData(cmd.getFOBJ());
                  if (dictMetadata == null) {
                      dictMetadata = MetaDataUtil.getMetaData(db.getJdbcConnection(), PO, cmd.getFOBJ());
                      if (dictMetadata == null) continue;
//                      context.setFKEYMetaData(cmd.getFOBJ(), dictMetadata);
                  }
                  // 是否同时ID2NAME，默认是
                  String loadFKey = cmd.getExtAttriValue("COL_OPT", "=", ";", "ID2NAME");
                  if (loadFKey == null || loadFKey.trim().length() == 0)
                      loadFKey = "1";
                  if (!"1".equals(loadFKey)) continue;
                  //加载外键数据类型
                  DictMetaDataEx ex = new DictMetaDataEx(dictMetadata);
                  String where = "1=1";
//                  boolean alldata = true;
//                  // 只读模式下，不判断数据个数，只根据主表过滤 modified by gengeng 2012-2-10
//                  if (context.isReadOnly())
//                      alldata = false;
//                  else {
//                      sql = "select count(1) from " + DBTools.getDBAObject(PO, cmd.getFOBJ());
//                      rs1 = stmt1.executeQuery(sql);
//                      int count = 0;
//                      if (rs1.next())
//                          count = rs1.getInt(1);
//                      rs1.close();
//                      // 外键字典没数据，直接返回
//                      if (count == 0) continue;
//                      // 外键字典数据太多，只加载部分数据
//                      if (count > DctConstants.MINFKEYCOUNT)
//                          alldata = false;
//                  }

//                  if (!context.isReadOnly()) {
//                      sql = "select count(1) from " + DBTools.getDBAObject(PO, cmd.getFOBJ());
//                      rs1 = stmt1.executeQuery(sql);
//                      if (!rs1.next()) continue;
//                      int count = rs1.getInt(1);
//                      if (count > DctConstants.MINFKEYCOUNT)
//                          alldata = false;
//                  } else {
//                      alldata = false;
//                  }
                  //获取外键数据
//                  if(alldata){
//                	  dictMetadata.setString(DctConstants.ALLDATA, "1");
//                  } else{
//                      dictMetadata.setString(DctConstants.ALLDATA, "0");
                      where = " exists(select 1 from " + DBTools.getDBAObject(PO, context.getDctID()) +
                      		" where FK." + ex.getDctBmCol() + " = " + cmd.getString("OBJ_ID", "") + "." + cmd.getColid();
	                  // 主字典可能有自己的过滤条件
	                  if (context.getString("FilterWhere", "").trim().length() > 0)
	                      where += " and " + context.getString("FilterWhere", "");
                          if (context.getWhere() != null && context.getWhere().trim().length() > 0)
                              where += " and " + context.getWhere();
	                  where += ")";
//                  }
                  sql = "select FK.* from " + DBTools.getDBAObject(PO, cmd.getFOBJ());
//                  sql = " select FK." + ex.getDctBmCol() + ",FK." + ex.getDctMcCol() + " from " + DBTools.getDBAObject(PO, cmd.getFOBJ());
                  sql += " FK WHERE " + where;
                  EFDataSet eds = context.getFKEYDataSet(cmd.getFOBJ());
                  if(eds==null){
                      eds = EFDataSet.getInstance(cmd.getFOBJ());
                      eds.setPrimeKey(new String[]{ex.getDctBmCol()});
                      context.setFKEYDataSet(cmd.getFOBJ(),eds);
                  }
                try {
                    rs2 = stmt2.executeQuery(sql);
                } catch (SQLException ex1) {
                    System.out.println("error sql:" + sql);
                    throw ex1;
                }
                  String value = ParameterManager.getDefault().getSystemParam("SQL_MONITOR");
                  if (value == null || value.trim().length() == 0)
                      value = "0";
                  int iv = Integer.parseInt(value);
                  if ((iv & 1) != 0)
                      System.out.println(sql);
                  DataSetUtils.resultSet2DataSet(rs2, eds);
                  rs2.close();
              }
          }
      }finally{
    	  conn.BackStatement(stmt1, rs1);
    	  conn.BackStatement(stmt2, rs2);
      }
    }
    /**
     * 特殊处理创建日期、更新日期列，日期以服务器日期为准
     */
    public Object startSave(Database dataBase, JParamObject PO, DctContext context, ClientDataSetData cds, QueryDataSet qds) throws Exception {
        DictMetaDataEx                dmd = new DictMetaDataEx(context.getMetaData());
        EFRowSet                   rowset = null;
        java.util.List                key = dmd.getKeyColumnName();
        Calendar                        c = Calendar.getInstance();
        long                         time = c.getTimeInMillis();
        ColumnMetaData     columnMetaData = null;
        
        // 主键字段为null的统一更新为一个空格
        processDCTKeyColumn(cds, context);
        // 使用标准服务器时间
//        long time = ESPTimeUtil.getSystemTimestamp(PO);
        
        for(int i = 0; i < cds.getDataSetData().getRowCount(); i++) {
        	rowset = cds.getDataSetData().getRowSet(i);
        	if(rowset.getDataStatus() == EFRowSet._DATA_STATUS_INSERT_) {
        		columnMetaData = cds.getDataSetData().hasColumn("F_CRDATE");
        		if(columnMetaData != null && columnMetaData.getColType().equals("T")) {
        			rowset.putDate("F_CRDATE", new Timestamp(time));
        			rowset.putDate("F_CHDATE", new Timestamp(time));
        		}
        	} else if(rowset.getDataStatus() == EFRowSet._DATA_STATUS_UPDATE_) {
        		columnMetaData = cds.getDataSetData().hasColumn("F_CRHATE");
        		if(columnMetaData != null && columnMetaData.getColType().equals("T")) {
        			rowset.putDate("F_CRDATE", new Timestamp(time));
        			rowset.putDate("F_CHDATE", new Timestamp(time));
        		}
        	}
        }
//        if (qds.hasColumn("F_CRDATE") != null && qds.hasColumn("F_CRDATE").getDataType() == Variant.TIMESTAMP) {
//            DataSetView dsvInserted = new DataSetView();
//            qds.getInsertedRows(dsvInserted);
//            dsvInserted.first();
//            while (dsvInserted.inBounds()) {
//                dsvInserted.setTimestamp("F_CRDATE", new Timestamp(time));
//                dsvInserted.setTimestamp("F_CHDATE", new Timestamp(time));
//                dsvInserted.next();
//            }
//        }
//        // 处理F_CHDATE
//        if (qds.hasColumn("F_CHDATE") != null && qds.hasColumn("F_CHDATE").getDataType() == Variant.TIMESTAMP) {
//            DataSetView dsvUpdated = new DataSetView();
//            qds.getUpdatedRows(dsvUpdated);
//            dsvUpdated.first();
//            while (dsvUpdated.inBounds()) {
//                dsvUpdated.setTimestamp("F_CHDATE", new Timestamp(time));
//                dsvUpdated.next();
//            }
//        }
        //


        return null;
    }

    /**
     *
     *
     * @param qds QueryDataSet
     */
    protected void processDCTKeyColumn(ClientDataSetData qds, DctContext context) {
        // 处理新增
//        DataSetView dsvInserted = new DataSetView();
//        qds.getInsertedRows(dsvInserted);
        processDCTKeyColumn1(qds, context.getMetaData());
//        // 处理修改
//        DataSetView dsvUpdated = new DataSetView();
//        qds.getUpdatedRows(dsvUpdated);
////        processDCTKeyColumn1(dsvUpdated, context.getMetaData());
//        // 处理删除
//        DataSetView dsvDeleted = new DataSetView();
//        qds.getDeletedRows(dsvDeleted);
//        processDCTKeyColumn1(dsvDeleted, context.getMetaData());
    }

    /**
     *
     * @param dsView DataSetView
     * @param dictMetadata DictMetadata
     */
    protected void processDCTKeyColumn1(ClientDataSetData qds, DictMetadata dictMetadata) {
//        dsView.first();
//        while (dsView.inBounds()) {
//            processDCTKeyColumn2(dsView, dictMetadata);
//            dsView.next();
//        }
    	for(int i = 0; i < qds.getDataSetData().getRowCount(); i++) {
    		if(qds.getDataSetData().getRowSet(i).getDataStatus() == EFRowSet._DATA_STATUS_INSERT_) {
    			qds.getDataSetData().goToRow(i);
    			processDCTKeyColumn2(qds, dictMetadata);
    		}
    	}
    }

    /**
     * 将所有的主键列为空的改为一个空格
     *
     * @param  dsvInserted DataSetView
     * @param dictMetadata DictMetadata
     */
    protected void processDCTKeyColumn2(ClientDataSetData qds, DictMetadata dictMetadata) {
        if (dictMetadata == null) return;
        DictMetaDataEx dmd = new DictMetaDataEx(dictMetadata);
        if (dmd == null) return;
        java.util.List key = dmd.getKeyColumnName();
        for (int i = 0; key != null && i < key.size(); i++) {
            String col = (String) key.get(i);
            if (col == null || col.trim().length() == 0) continue;
            if (qds.getDataSetData().hasColumn(col) == null) continue;
            String val = DictUtil.getVariantValue(qds.getDataSetData(), col);
            if (val == null || val.length() == 0) {
                val = " ";
                DictUtil.setVariantValue(qds.getDataSetData(), col, val);
            }
        }
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
    public Object endSave(Database db, JParamObject PO,
                          DctContext context, ClientDataSetData cds,
                          QueryDataSet qds) throws Exception {
        DictMetadata dmd = context.getMetaData();
        DictMetaDataEx dmdex = new DictMetaDataEx(dmd);
        String tab=DBTools.getDBAObject(PO,context.getDctID());
        String jscol=dmdex.getDctJsCol();
        String mxcol=dmdex.getDctMxCol();
        String bmcol=dmdex.getDctBmCol();
        String where=context.getString("FilterWhere", "");
        String AutoMx = context.getString("AutoMx", "1");//保存时，根据下级是否有数据判断是明细还是非明细（zhtbin）
        if (where.trim().length() == 0) where = "1=1";
        where = "(" + where + ")";
        if(dmdex.isGradeDict()){
        	String sql="update  "+tab+" A set "+mxcol+"='0'  where exists (select 1 from "+tab+" B";
        	sql+=" where  B."+bmcol+" like A."+bmcol+"||'%'  and B."+jscol+"=A."+jscol+"+1 and " + where + ") and "+mxcol+"='1' and " + where;
        	db.executeStatement(sql);
        	if("1".equals(AutoMx)) {
        		sql=" update  "+tab+" A set "+mxcol+"='1'  where not exists (select 1 from "+tab+" B";
        		sql+=" where  B."+bmcol+" like A."+bmcol+"||'%'  and B."+jscol+"=A."+jscol+"+1 and " + where + ") and "+mxcol+"='0' and " + where;
        		db.executeStatement(sql);
        	}
        }
        return null;
    }

}
