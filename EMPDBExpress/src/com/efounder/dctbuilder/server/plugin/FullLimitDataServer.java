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
import com.efounder.eai.service.ParameterManager;
import com.efounder.builder.base.util.ESPServerContext;
import com.efounder.builder.meta.MetaDataManager;
import com.efounder.builder.meta.dctmodel.DCTMetaData;

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
public class FullLimitDataServer extends LimitDataServer {

    /**
     *
     */
    public FullLimitDataServer() {
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
        if (!context.getString("FullLimit", "").equals("1")) 
        	return super.getQueryWhere(db, PO, context, where);
        Statement stmt = db.createStatement();
        String F_BH;
        try {
            F_BH = getGrantColumn(stmt, context, PO);
        } finally {
            stmt.close();
        }
        String bzw = context.getQXBZW();
        if (bzw == null || bzw.trim().length() == 0) bzw = DctConstants.DCTREADBIT;
        String F_ZGBH = PO.GetValueByEnvName("UserName");
        String dctid = context.getDctID();
        JConnection conn = JConnection.getInstance(db.getJdbcConnection());
        ESPServerContext sercontext = ESPServerContext.getInstance(PO, conn);
        sercontext.setStatement(stmt);
        DCTMetaData dctMetaData = (DCTMetaData) MetaDataManager.getDCTMetaDataManager().
                    getMetaData(sercontext, dctid);
        String sjqxb = dctMetaData.getDCT_QXCOLID();
        sjqxb = DBTools.getDBAObject(PO, sjqxb);
        String BSUSERROLE = DBTools.getDBAObject(PO, "BSUSERROLE");

        String where1  = " exists(select 1 from " + sjqxb + " QX where F_G" + bzw + " = '1'";
               where1 += " and ("+ dctid+"."+F_BH+" like QX.F_SJBH ||'%' or QX.F_SJBH like "+ dctid + "." + F_BH + " || '%')";
               where1 += " and QX.F_QXBH = '"+context.getQXBH()+ "' and ";
               where1 += "(QX.F_ZGBH='"+F_ZGBH+"' or exists(select 1 from " + BSUSERROLE+" BSUSERROLE where F_ZGBH='"+F_ZGBH+"' and F_ROLECODE=QX.F_ZGBH";
               if (ParameterManager.getDefault().isUseLimitAudit()) {
                   where1 += " and F_SH='1'";
               }
               where1 += ")))";
        if (where != null && where.length() > 0) where = where + " and " + where1;
        return where;
    }

    protected String getGrantColumn(Statement stmt, DctContext context, JParamObject PO) throws Exception {
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

}
