package com.efounder.dctbuilder.event.plugin;

import java.util.List;

import com.borland.dx.dataset.DataSetView;
import com.borland.dx.sql.dataset.Database;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.core.xml.StubObject;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.dbc.ClientDataSetData;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.data.DctContext;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.efounder.dctbuilder.server.DataSetServerAdaper;
import com.efounder.dctbuilder.util.DictBHUtil;
import com.efounder.eai.data.JParamObject;
import com.efounder.pub.util.MD5Tool;
import com.efounder.service.meta.db.DictMetadata;
import com.efounder.service.meta.db.TableMetadata;

/**
 * <p>Title: 处理发布数据时的选择列</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 */
public class DictEncryptPassDataServer extends DataSetServerAdaper {

	public boolean canProcess(Database dataBase, JParamObject PO, DctContext context, ClientDataSetData cds) throws Exception {
		return true;
	}

	public Object startSave(Database dataBase, JParamObject PO, DctContext context, ClientDataSetData cds, QueryDataSet qds) throws Exception {
		DictMetadata     dm = context.getMetaData();
        DictMetaDataEx  dmd = new DictMetaDataEx(dm);
        
        for (int i = 0; i < cds.getDataSetData().getRowCount(); i++) {
            if(cds.getDataSetData().getRowSet(i).getDataStatus() != EFRowSet._DATA_STATUS_NORMAL_) {
            	cds.getDataSetData().goToRow(i);
            	encryptPassword(cds.getDataSetData().getRowSet(i), dm);
            }
        }
		return null;
	}
	
	private void encryptPassword(EFRowSet rowset, DictMetadata dm) {
		TableMetadata tableMetaData = dm.getTableMetadata();
		List                colList = tableMetaData.getColList();
		ColumnMetaData  colMetaData = null;
		String             colValue = "";
		String          encryptPass = "";
		
		for(int i = 0; i < colList.size(); i++) {
			colMetaData = (ColumnMetaData) colList.get(i);
			if(colMetaData.getColEditType() == 7) {
				colValue = rowset.getString(colMetaData.getColid(), "");
				encryptPass = MD5Tool.getDefault().getMD5ofStr(colValue);
				rowset.putString(colMetaData.getColid(), encryptPass);
			}
		}
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
