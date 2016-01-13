package com.efounder.dctbuilder.event.plugin;

import java.util.List;

import com.borland.dx.dataset.DataSetView;
import com.core.xml.StubObject;
import com.efounder.dctbuilder.data.DctConstants;
import com.efounder.dctbuilder.data.DctContext;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.efounder.dctbuilder.event.DataLoaderCltAdapter;
import com.efounder.dctbuilder.mdl.DictModel;
import com.efounder.dctbuilder.util.DictBHUtil;
import com.efounder.dctbuilder.view.DictView;

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
public class DataSetWriteCoderProcess extends DataLoaderCltAdapter {
//	  List delItem;
//    List insertItem;
    
    /**
     *
     */
    public DataSetWriteCoderProcess() {
    }

    /**
     *
     * @param o1 DictModel
     * @param context DctContext
     * @return boolean
     * @throws Exception
     */
    public boolean canProcess(DictModel o1, DctContext context) throws Exception {
        return!context.isReadOnly()
                && o1.getView() instanceof DictView
                && o1.getNodeStub() != null
                && !(o1.getNodeStub() instanceof com.pansoft.esp.standard.client.apply.node.ESPApplyNode);
    }

    /**
     *
     * @param o1 DictModel
     * @param context DctContext
     * @throws Exception
     */
    public void afterDataLoad(DictModel o1, DctContext context) throws Exception {
        DictView view = (DictView) o1.getView();
        java.util.List list = (List) context.getValue(DctConstants.WRITEABLEDATA, null);
        o1.setWriteAbleCode(list, true);
    }

    /**
     *
     * @param dm DictModel
     * @param context DctContext
     * @return Object
     * @throws Exception
     */
    public Object beforeDataSave(DictModel dm, DctContext context) throws Exception {
    	if(1==1) return null;
    	//数据字典检查
    	if(!check(dm, context))
    		return new Boolean(false);
    	
        // 处理从SYS_AUTOBH获取流水号的列
        processAUTO_BH(dm, context);
        return null;
    }
    
    public void afterDataSave(DictModel dm, DctContext context) throws Exception{
    	DictMetaDataEx dmd = dm.getMetaData();
        // 从数据库中精确获取最大编号
        if (isGetMaxFromDB(dmd)){
        	dm.refrensh();
            return;
        }
    }
    
    protected boolean check(DictModel dictModel, DctContext context) throws Exception {
    	DataSetView insertDataSet = new DataSetView();
    	dictModel.getDataSet().getInsertedRows(insertDataSet);
    	DataSetView updateDataSet = new DataSetView();
    	dictModel.getDataSet().getUpdatedRows(updateDataSet);
    	DataSetView deleteDataSet = new DataSetView();
    	dictModel.getDataSet().getDeletedRows(deleteDataSet);
    	
    	return true;
    }

    /**
     * 自动生成编码
     * @param dictModel DictModel
     */
    protected void processAUTO_BH(DictModel dictModel, DctContext context) throws Exception {
        DictMetaDataEx dmd = dictModel.getMetaData();
        // 从数据库中精确获取最大编号
        if (!isGetMaxFromDB(dmd))
            return;
        DataSetView insertView = new DataSetView();
        dictModel.getDataSet().getInsertedRows(insertView);
        if (insertView.getRowCount() == 0)
            return;
        String colid = dmd.getDctBmCol();
        String serverName = dictModel.getCdsParam().getServerName();

        //获取前缀
        String prefix = dictModel.getCdsParam().getString("DCT_BM_PREFIX", "");
        //根据前缀获取编码
        int lshlen = dmd.getTotalLength() - prefix.length();
        String[] bh = DictBHUtil.getBHFromDB(dmd.getDctid(), colid, prefix,
        		lshlen, dmd.getDctBMStep(), insertView.getRowCount(),
                                             serverName);
        for (int i = 0; i < insertView.getRowCount(); i++) {
            insertView.goToRow(i);
            insertView.setString(colid, bh[i]);
        }
    }

    /**
     *
     * @param dmd DictMetaDataEx
     * @return boolean
     */
    protected boolean isGetMaxFromDB(DictMetaDataEx dmd) {
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
