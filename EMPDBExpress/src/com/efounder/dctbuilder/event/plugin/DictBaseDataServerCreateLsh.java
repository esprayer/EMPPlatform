package com.efounder.dctbuilder.event.plugin;

import java.util.List;

import com.borland.dx.dataset.DataSetView;
import com.borland.dx.sql.dataset.Database;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.core.xml.StubObject;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.dbc.ClientDataSetData;
import com.efounder.dctbuilder.data.DctContext;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.efounder.dctbuilder.server.DataSetServerAdaper;
import com.efounder.dctbuilder.util.DictBHUtil;
import com.efounder.eai.data.JParamObject;
import com.efounder.service.meta.db.DictMetadata;

/**
 * <p>Title: 处理发布数据时的选择列</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 */
public class DictBaseDataServerCreateLsh extends DataSetServerAdaper {

	public boolean canProcess(Database dataBase, JParamObject PO, DctContext context, ClientDataSetData cds) throws Exception {
		DictMetadata     dm = context.getMetaData();
        DictMetaDataEx  dmd = new DictMetaDataEx(dm);
        if(isBuilderFromDB(dmd))
        	return true;
		return false;
	}

	public Object startSave(Database dataBase, JParamObject PO, DctContext context, ClientDataSetData cds, QueryDataSet qds) throws Exception {
		DictMetadata     dm = context.getMetaData();
        DictMetaDataEx  dmd = new DictMetaDataEx(dm);
        
//		DataSetView insertView = new DataSetView();
//		qds.getInsertedRows(insertView);
//        if (insertView.getRowCount() == 0)
//            return null;
        String colid = dmd.getDctBmCol();
//        String serverName = context.getServerName();
        //获取前缀
        String prefix = context.getString("DCT_BM_PREFIX", "");
        //根据前缀获取编码
        int lshlen = dmd.getTotalLength() - prefix.length();
//        String[] bh = DictBHUtil.getBHFromDB(dmd.getDctid(), colid, prefix,
//        		lshlen, dmd.getDctBMStep(), insertView.getRowCount(),
//                                             serverName);
        for (int i = 0; i < cds.getDataSetData().getRowCount(); i++) {
            if(cds.getDataSetData().getRowSet(i).getDataStatus() == EFRowSet._DATA_STATUS_INSERT_) {
            	String[] bh = DictBHUtil.getNewBH(dataBase, PO, dmd.getDctid(),colid, prefix, lshlen,dmd.getDctBMStep(), 1);
            	cds.getDataSetData().getRowSet(i).putString(colid, prefix+bh[0]);
            }
        }
		return null;
	}
	
	/**
     * 判断是否需要从后台获取最大编号
     * @param dmd DictMetaDataEx
     * @return boolean
     */
    private boolean isBuilderFromDB(DictMetaDataEx dmd) {
        if (dmd != null && dmd.getObject("SYS_DCT_CST", null) != null) {
            java.util.List cst = (List) dmd.getObject("SYS_DCT_CST", null);
            for (int i = 0, n = cst.size(); i < n; i++) {
                StubObject so = (StubObject) cst.get(i);
                if (so.getString("DCT_KEY", "").toUpperCase().equals("CODERULEEXACT")) {
                    return "1".equals(so.getString("DCT_VALUE", ""));
                }
            }
        }
        return false;
    }
}
