package com.efounder.dctbuilder.util;

import java.sql.ResultSet;
import java.sql.Statement;

import com.borland.dx.sql.dataset.Database;
import com.efounder.db.DBTools;
import com.efounder.eai.data.JParamObject;
import com.efounder.form.server.resolver.util.SYS_MDL_VAL;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.metadata.bizmodel.SYS_MODEL;
import com.pub.util.StringFunction;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DictBHUtil {

    /**
     *
     */
    public DictBHUtil() {
    }

    public static String[] getNewBH(Database dataBase, JParamObject PO, String objid, String colid, String prefix, int length, int step, int count) {
		 Statement st = null;
		 ResultSet rs = null;
		 String sql = null, where = null, table = DBTools.getDBAObject(PO, "SYS_AUTOBH");
		 String[] bhs = new String[count <= 0 ? 1 : count];
		 boolean autoCommit = dataBase.getAutoCommit();
		 String commit=PO.GetValueByParamName("COMMIT", "1");
		 try {
//			 if("1".equals(commit)){
//				 if (!autoCommit)
//					 dataBase.setAutoCommit(false);
//			 }
			 st = (Statement) dataBase.createStatement();
			 if (prefix == null)
				 prefix = "";
			 // 0.统一条件
			 where = "OBJ_ID = '" + objid + "' and COL_ID = '" + colid + "'";
			 if (prefix.length() == 0)
				 where += " and (F_BHFS is null or F_BHFS = '')";
			 else
				 where += " and F_BHFS = '" + prefix + "'";
			 // 1.没有则先插入
			 sql = " select * from " + table + " where " + where;
			 rs = st.executeQuery(sql);
			 if (!rs.next()) {
				 sql = " insert into " + table + "(OBJ_ID,COL_ID,F_BHFS,F_MXBH)" +
				 " values('" + objid + "','" + colid + "','" + prefix + "',1)";
				 st.executeUpdate(sql);
			 }
			 if("1".equals(commit))
				 dataBase.commit();
			 // 2.加锁
			 sql = " update " + table + " set F_MXBH = F_MXBH where " + where;
			 st.executeUpdate(sql);
			 // 3.取最大号
			 sql = " select F_MXBH from " + table + " where " + where;
			 rs = st.executeQuery(sql);
			 int maxIndex = 1;
			 if (rs.next())
				 maxIndex = rs.getInt("F_MXBH");
			 // 4.生成编号
			 for (int i = 0; i < count; i++) {
				 bhs[i] = StringFunction.FillZeroFromBegin(maxIndex, length); //Modify by bing 2013年12月16日17:43:59
				 maxIndex += step;
			 }
			 // 5.更新回去
			 sql = " update " + table + " set F_MXBH = " + maxIndex + " where " + where;
			 st.executeUpdate(sql);
//			 if("1".equals(commit))
//				 dataBase.commit();
			 return bhs;
		 } catch (Exception ex) {
//			 if("1".equals(commit))
				 dataBase.rollback();
			 ex.printStackTrace();
		 } finally {
//			 if("1".equals(commit))	    	 
//				 dataBase.setAutoCommit(autoCommit);
		 }
		 return null;
	 }
    
	public static String getNewBH(JConnection conn, JParamObject PO, String prefix, String objid, String colid, int length) {
		 JStatement     stmt = null;
		 ResultSet        rs = null;
		 String     strMaxBH = "";
		 String          sql = null, where = null, table = "SYS_AUTOBH";
		 String       strSql = " LOCK TABLES SYS_AUTOBH WRITE "; //将表锁住，防止获取最大编号有问题

		 try {
			 stmt = conn.createStatement();
			 
			 //先将表锁住
			 stmt.execute(strSql);
			 
			 if (prefix == null) prefix = "";
			 // 0.统一条件
			 where = "OBJ_ID = '" + objid + "'";
			 if (prefix.length() == 0) {
				 where += " and (F_BHFS is null or F_BHFS = '')";
			 } else {
				 where += " and F_BHFS = '" + prefix + "'";
			 }
			 // 1.没有则先插入
			 sql = " select * from " + table + " where " + where;
			 rs = stmt.executeQuery(sql);
			 if (!rs.next()) {
				 sql = " insert into " + table + "(OBJ_ID,COL_ID,F_BHFS,F_MXBH)" 
				     + " values('" + objid + "','" + colid + "','" + prefix + "',1)";
				 stmt.executeUpdate(sql);
			 }
			 
			 // 3.取最大号
			 sql = " select F_MXBH from " + table + " where " + where;
			 rs = stmt.executeQuery(sql);
			 int maxIndex = 1;
			 if (rs.next()) {
				 maxIndex = rs.getInt("F_MXBH");
			 }
			 // 4.生成编号
			 strMaxBH = StringFunction.FillZeroFromBegin(maxIndex, length);
			 maxIndex += 1;
			 // 5.更新回去
			 sql = " update " + table + " set F_MXBH = " + maxIndex + " where " + where;
			 stmt.executeUpdate(sql);
			 return strMaxBH;
		 } catch (Exception ex) {
			 conn.rollback();
			 ex.printStackTrace();
		 } finally {
			 try {
				 strSql = " UNLOCK TABLES"; //将表解锁
				stmt.execute(strSql);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
			try {
				closeAllResources(rs, stmt, null);			
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		 }
		 return null;
	 }
	
	public static String getModelNewBH(JConnection conn, JParamObject PO, String prefix, int length) {
		 JStatement     stmt = null;
		 ResultSet        rs = null;
		 String     strMaxBH = "";
		 String          sql = null, where = null, table = "SYS_MDL_BH";
		 String       strSql = " LOCK TABLES SYS_MDL_BH WRITE "; //将表锁住，防止获取最大编号有问题
		 String       MDL_ID = PO.GetValueByParamName(SYS_MODEL.MODEL_ID, "");
		 String       F_DATE = PO.GetValueByParamName("F_DATE", "");
		 String    MDL_BHDCT = PO.GetValueByParamName(SYS_MODEL._MDL_BHDCT_, null);
		 String       F_DJLX = PO.GetValueByParamName(SYS_MODEL._MDL_BHDCT_ + "_VALUE", null);
		 try {
			 stmt = conn.createStatement();
			 
			 //先将表锁住
			 stmt.execute(strSql);
			 
			 if (prefix == null) prefix = "";
			 // 0.统一条件
			 where = "MDL_ID = '" + MDL_ID + "' and F_DATE = '" + F_DATE + "'";
			 
			 if(MDL_BHDCT != null && !MDL_BHDCT.equals("") && F_DJLX != null && !F_DJLX.trim().equals("")) {
				 where += " and F_DJLX = '" + F_DJLX + "'";
			 }
			 
			 // 1.没有则先插入
			 sql = " select * from " + table + " where " + where;
			 rs = stmt.executeQuery(sql);
			 if (!rs.next()) {
				 if(MDL_BHDCT != null && !MDL_BHDCT.equals("") && F_DJLX != null && !F_DJLX.trim().equals("")) {
					 sql = " insert into " + table + "(MDL_ID,F_DATE,F_BHFS,F_MXBH, F_DJLX)" 
			             + " values('" + MDL_ID + "','" + F_DATE + "','',1, '" + F_DJLX + "')";
				 } else {
					 sql = " insert into " + table + "(MDL_ID,F_DATE,F_BHFS,F_MXBH)" 
				         + " values('" + MDL_ID + "','" + F_DATE + "','',1)";
				 }
				 
				 stmt.executeUpdate(sql);
			 }
			 
			 // 3.取最大号
			 sql = " select F_MXBH from " + table + " where " + where;
			 rs = stmt.executeQuery(sql);
			 int maxIndex = 1;
			 if (rs.next()) {
				 maxIndex = rs.getInt("F_MXBH");
			 }
			 // 4.生成编号
			 strMaxBH = StringFunction.FillZeroFromBegin(maxIndex, length);
			 maxIndex += 1;
			 // 5.更新回去
			 sql = " update " + table + " set F_MXBH = " + maxIndex + " where " + where;
			 stmt.executeUpdate(sql);
			 return strMaxBH;
		 } catch (Exception ex) {
			 conn.rollback();
			 ex.printStackTrace();
		 } finally {
			 try {
				 strSql = " UNLOCK TABLES"; //将表解锁
				stmt.execute(strSql);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
			try {
				closeAllResources(rs, stmt, null);			
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		 }
		 return null;
	 }
	
	public static void closeAllResources(ResultSet rs, JStatement stmt, JConnection conn) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		
		try {
			if(stmt != null) stmt.close();
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		
		try {
			if(conn != null) conn.close();
		} catch(Exception ce) {
			ce.printStackTrace();
		}
	}
}

