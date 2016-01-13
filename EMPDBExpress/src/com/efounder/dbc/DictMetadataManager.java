package com.efounder.dbc;

import com.core.xml.StubObject;
import com.efounder.buffer.*;
import com.efounder.builder.base.data.*;
import com.efounder.service.meta.db.*;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import java.util.Iterator;
import com.efounder.builder.base.util.ESPClientContext;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.meta.dctmodel.DCTMetaDataManager;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.efounder.builder.meta.domodel.DOMetaDataManager;

import java.util.Hashtable;
import java.util.ArrayList;

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
public class DictMetadataManager extends EFRowSet {

    /**
     *
     */
    protected DictMetadataManager() {
    }

    /**
     *
     */
    protected static DictMetadataManager metaDataManager = null;

    /**
     *
     * @return DictMetadataManager
     */
    public static DictMetadataManager getDefault() {
        if (metaDataManager == null)
            metaDataManager = new DictMetadataManager();
        return metaDataManager;
    }

    /**
     *
     * @param dctid String
     * @return DictMetadata
     */
    public DictMetadata getMetaData(String dctid, String serverName) {
        if (dctid == null || dctid.trim().length() == 0)
            return null;
        JParamObject PO = JParamObject.Create();
//        PO.setEAIServer(serverName);
        ESPClientContext tx = ESPClientContext.getInstance(PO);
        DictMetadata dictMetadata = DictMetadata.getInstance();
        try {
            DOMetaData doMetaData = (DOMetaData) DOMetaDataManager.getDODataManager().getMetaData(tx, dctid);
            DCTMetaData dctMeta = doMetaData.getDCTMetaData();
            // 集群环境下，有时候通过DOMetaData取不到
            if (dctMeta == null)
                dctMeta = (DCTMetaData) DCTMetaDataManager.getDCTMetaDataManager().getMetaData(tx, dctid);
            // 字典信息SYS_DICTS
            dictMetadata.setStubTable(new Hashtable());
            if (dctMeta != null)
                dictMetadata.getStubTable().putAll(dctMeta.getDataMap());
            // 对象信息SYS_OBJECTS
            TableMetadata tableMetadata = TableMetadata.getInstance();
            tableMetadata.setID(dctid);
            tableMetadata.setStubTable(new Hashtable());
            tableMetadata.getStubTable().putAll(doMetaData.getDataMap());
            if (tableMetadata.colList == null) tableMetadata.colList = new ArrayList();
            dictMetadata.tableMetadata = tableMetadata;
            // 列信息
            EFDataSet efds = doMetaData.getDOColumns();
            for (int i = 0; efds != null && i < efds.getRowCount(); i++) {
                EFRowSet edrs = efds.getRowSet(i);
                ColumnMetaData colMetadata = ColumnMetaData.getInstance();
                colMetadata.setStubTable(new Hashtable());
                colMetadata.getStubTable().putAll(edrs.getDataMap());
                String coledit = colMetadata.getString("COL_EDIT", "");
                if (coledit.equals(ColumnMetaData.EL_SELFENUM))
                    colMetadata.setString("COL_VIEW", edrs.getString("SYS_SELFENUM", ""));
                tableMetadata.colList.add(colMetadata);
            }
            // 数据对象扩展属性
            // 数据字典扩展属性
            java.util.Map SYS_DCT_CST = null;
            if (dctMeta != null)
                SYS_DCT_CST = dctMeta.getSYS_DCT_CST();
            if (SYS_DCT_CST != null) {
                java.util.List list = new ArrayList();
                for (Iterator it = SYS_DCT_CST.keySet().iterator(); it.hasNext();) {
                    Object key = it.next();
                    Object val = SYS_DCT_CST.get(key);
                    StubObject so = new StubObject();
                    so.setString("UNIT_ID", "");
                    so.setString("DCT_ID", dctid);
                    so.setString("DCT_KEY", (String) key);
                    so.setString("DCT_VALUE", (String) val);
                    list.add(so);
                }
                dictMetadata.setObject("SYS_DCT_CST", list);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(dctid + "'s metadata is null.");
        }
        return dictMetadata;
    }

    /**
     *
     * @param dctid String
     * @return DictMetaDataEx
     */
    public DictMetaDataEx getMetaDataEx(String dctid, String serverName) {
        DictMetadata meta = getMetaData(dctid, serverName);
        if (meta == null) return null;
        return new DictMetaDataEx(meta);
    }


}
