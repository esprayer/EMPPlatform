package com.efounder.dctbuilder.event.plugin;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.bizmodel.*;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dctbuilder.data.*;
import com.efounder.dctbuilder.event.*;
import com.efounder.dctbuilder.mdl.*;
import com.efounder.dctbuilder.util.*;
import com.efounder.eai.data.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DataSetMetaDataProcessKeySet
        extends DataLoaderCltAdapter {

    /**
     *
     */
    public DataSetMetaDataProcessKeySet() {
    }

    /**
     * 只有在菜单中指定了关键字典的功能中可以使用
     */
    public boolean canProcess(DictModel dm, DctContext context) throws Exception {
        if (dm.getNodeStub() == null) return false;
        String canProcess = dm.getNodeStub().getNodeStubObject().getString("keysetdct", "");
        return "1".equals(canProcess);
    }

    /**
     * 根据关键指标设置中对字典做的列的特殊设置确定列的显示名称等
     */
    public void afterDataLoad(DictModel dm, DctContext context) throws Exception {
        if (dm.getNodeStub() == null) return;
        String MDL_ID = dm.getNodeStub().getNodeStubObject().getString("MDL_ID", "");
        if (MDL_ID == null || MDL_ID.trim().length() == 0) return;
        BIZMetaData bizMetaData = (BIZMetaData) BIZMetaDataManager.getBIZMetaDataManager().getMetaData(MDL_ID);
        if (bizMetaData == null) return;
        String UNIT_DCT = "", UNIT_VAL = "";
        JParamObject PO = JParamObject.Create();
        // 关键指标设置类型
        String KEY_SET = bizMetaData.getString("KEY_SET", "");
        if ("1".equals(KEY_SET)) {
            UNIT_DCT = bizMetaData.getUNIT_DCT();
            UNIT_VAL = context.getString(UNIT_DCT, "");
            if (UNIT_VAL == null || UNIT_VAL.trim().length() == 0)
            	UNIT_VAL = PO.GetValueByEnvName(UNIT_DCT, "");
        } else if ("2".equals(KEY_SET)) {
            UNIT_DCT = "SYS_UNITS";
            UNIT_VAL = context.getString("UNIT_ID", "");
            if (UNIT_VAL == null || UNIT_VAL.trim().length() == 0)
                UNIT_VAL = PO.GetValueByEnvName("UNIT_ID", "");
        }
        String CTN_ID = dm.getNodeStub().getNodeStubObject().getString("CTN_ID", null);
        if (CTN_ID == null || CTN_ID.trim().length() == 0)
            CTN_ID = dm.getDCT_ID();
        String serverName = dm.getNodeStub().getNodeStubObject().getString("ServerName", "");
        //是否合并上下级的关键指标
        String mergeDIMSET = context.getString("mergeDIMSET", "0");
        ConfigData[] config = DictUtil.getKeySetDCTConfigData(PO, MDL_ID, UNIT_DCT, UNIT_VAL, CTN_ID, mergeDIMSET, serverName);
        //字典的元数据
        DictMetaDataEx  dmd = dm.getMetaData();
        //根据配置数据和元数据对字典的列进行重新配置
        resetMetaData(dmd, config, dm);
    }

    /**
     * 根据字典元数据和配置数据进行重新设置
     * @param    dmd DictMetaDataEx
     * @param config ConfigData[]
     */
    protected void resetMetaData(DictMetaDataEx dmd, ConfigData[] config, DictModel model) {
        if (dmd == null || config == null || config.length == 0) return;
        EFDataSet cfgDataSet = config[0].getConfigDataSet();
        if (cfgDataSet == null) return;
        String depBM0 = model.getNodeStub().getNodeStubObject().getString("DEP_BM0", "");
        String depBM1 = model.getNodeStub().getNodeStubObject().getString("DEP_BM1", "");
        String depBM2 = model.getNodeStub().getNodeStubObject().getString("DEP_BM2", "");
        //获取那一行编码
        ESPRowSet esp = getKeySet(cfgDataSet, depBM0, depBM1, depBM2);
        if (esp == null) return;
        //获取维度设置信息
        EFDataSet dim = esp.getDataSet("DIMSET");
        //对列进行新的设置
        builderAllColumn(model.getDCT_ID(), dmd, dim);
        // 关键指标定义信息
        model.getCdsParam().setLosableValue("SYS_MDL_KEYSET", esp);
    }

    /**
     *
     * @param ds EFDataSet
     * @param keyBM String
     * @param depBM1 String
     * @param depBM2 String
     * @return EFRowSet
     */
    protected EFRowSet getKeySet(EFDataSet ds, String keyBM, String depBM1, String depBM2) {
        EFRowSet rs = (EFRowSet) ds.getRowSet(new String[] {keyBM, depBM1, depBM2});
        if (rs != null) return rs;
        String keyBMTmp = "", depBM1Tmp = "", depBM2Tmp = "";
        for (int i = keyBM.length(); i >= 0; i--) {
            keyBMTmp = keyBM.substring(0, i);
            for (int m = depBM1.length(); m >= 0; m--) {
                depBM1Tmp = depBM1.substring(0, m);
                for (int n = depBM2.length(); n >= 0; n--) {
                    depBM2Tmp = depBM2.substring(0, n);
                    rs = (EFRowSet) ds.getRowSet(new String[] {keyBMTmp, depBM1Tmp, depBM2Tmp});
                    if (rs != null) return rs;
                }
            }
        }
        return null;
    }

    /**
     * 设置所有的列不可见、不可用
     * @param dmd DictMetaDataEx
     */
    protected void builderAllColumn(String objid, DictMetaDataEx dmd, EFDataSet dim) {
        if (dim == null) return;
        java.util.List colist = dmd.getUsedColList();
        for (int i = 0, n = colist.size(); i < n; i++) {
            ColumnMetaData meta = (ColumnMetaData) colist.get(i);
            EFRowSet rs = getRowSetByColumn(objid, meta.getColid(), dim);
            if (rs == null) continue;
            //替换名称
            meta.setString("COL_MC", rs.getString("CTN_DCT_MC", ""));
            //设置为可见
            meta.setString("COL_VISIBLE", "1");
            //分组编号
            meta.setString("GP_ID", rs.getString("GP_ID", ""));
            //关键指标中的定义
            meta.setString("F_SRCTYPE", rs.getString("F_SRCTYPE", ""));
            meta.setString("F_SRCCONT", rs.getString("F_SRCCONT", ""));
            meta.setString("F_TARTYPE", rs.getString("F_TARTYPE", ""));
            meta.setString("F_TARCONT", rs.getString("F_TARCONT", ""));
            // 优先使用关键指标设置中定义的外键、编辑方式等属性
            autoDIM_SET(meta, rs);
        }
    }

    /**
     *
     * @param meta ColumnMetaData
     * @param rs EFRowSet
     */
    protected void autoDIM_SET(ColumnMetaData meta, EFRowSet rs) {
        if (meta == null || rs == null)
            return;
        String val = rs.getString("COL_EDIT", "");
        if (val != null && val.trim().length() > 0)
            meta.setString("COL_EDIT", val);
        val = rs.getString("COL_VIEW", "");
        if (val != null && val.trim().length() > 0)
            meta.setString("COL_VIEW", val);
        int sca = rs.getInt("COL_PREC", 0);
        if (sca > 0)
            meta.setInt("COL_PREC", sca);
        sca = rs.getInt("COL_SCALE", 0);
        if (sca >= 0)
            meta.setInt("COL_SCALE", sca);
        if (val != null && val.trim().length() > 0)
            meta.setString("COL_VIEW", val);
        val = rs.getString("COL_FOBJ", "");
        if (val != null && val.trim().length() > 0) {
            meta.setString("COL_FOBJ", val);
            meta.setString("COL_ISFKEY", "1");
        }
        val = rs.getString("F_SYZT", null);
        if (val != null)
            meta.setString("COL_VISIBLE", val);
    }

    /**
     * 根据表名和字段名获取行信息
     *
     * @param meta ColumnMetaData
     * @param  dim EFDataSet
     * @return     EFRowSet
     */
    protected EFRowSet getRowSetByColumn(String dctid, String colid, EFDataSet dim) {
        for (int i = 0, n = dim.getRowCount(); i < n; i++) {
            EFRowSet rs = dim.getRowSet(i);
            if (rs.getString("DIM_ID", "").equals(colid)
                && rs.getString("FCT_ID", "").equals(dctid)) 
            	return rs;
        }
        return null;
    }
}
