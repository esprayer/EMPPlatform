package com.efounder.dctbuilder.loader;

import com.borland.dx.sql.dataset.*;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.db.*;
import com.efounder.dbc.*;
import com.efounder.dctbuilder.mdl.*;
import com.efounder.dctbuilder.util.MetaDataUtil;
import com.efounder.eai.data.*;
import com.efounder.service.meta.*;
import com.efounder.service.meta.db.*;
import com.core.xml.*;
import com.efounder.dctbuilder.data.*;
import java.util.*;
import com.efounder.dctbuilder.event.DataSetRowEventClass;
import com.efounder.dctbuilder.event.DataSetColumnEventClass;

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
public class DefaultDctProvider implements Provider {
    final String svrcls = "com.efounder.dctbuilder.server.DictBuilderServer";

    public DefaultDctProvider() {
    }
    //获得元数据
//    protected void getMetaData(DictModel dmModel,JParamObject PO) throws Exception {
//        MetaContextObject mco     = MetaContextObject.getInstance(PO,null,null,"IndexModel");
//        DictMetadata dictMetadata = DBMetadataManager.getDefault("IndexModel").getDictMetadata(mco,dmModel.getDCT_ID());
//        dmModel.setMetaData(dictMetadata);
//    }

    public void provideData(DictModel dmModel, boolean bloadMd) throws Exception {
        initDataSet(dmModel);
        getDataSetData(dmModel);
        addColumnEventLisner(dmModel);
    }
    protected void initDataSet(DictModel dmModel) {
    	ClientDataSet cds = dmModel.getDataSet();
        DctContext context = dmModel.getCdsParam();
//        if (cds.isOpen() && context.isFirstLoad()) {
//            cds.emptyAllRows();
//            cds.close();
//        }
//        if (context.isFirstLoad()) {
            cds.setExtendServerDataSet(svrcls);
            context.setDctID(dmModel.getDCT_ID());
            context.setPlugInKey(dmModel.getUserPluginKey());
            cds.setUserObject(context);
//            cds.setMetaDataUpdate(MetaDataUpdate.NONE);
//            cds.setLoadappened(context.isGradeQuery());
//            DataSetRowEventClass dsre = new DataSetRowEventClass(dmModel);
//            dmModel.getDataSet().addEditListener(dsre);
//            dmModel.getDataSet().addDataChangeListener(dsre);
//        }
    }

    //获得DataSet的数据
    protected void getDataSetData(DictModel dmModel)throws Exception {
        ClientDataSet cds = dmModel.getDataSet();
        DctContext context = dmModel.getCdsParam();
        if (DctConstants.REMOTE)
            cds.setAgentDataBase(AgentDatabase.getDefault(""));
        cds.loadData();
        DctContext newcontext = (DctContext)cds.getUserObject();
        DictMetadata dmd=newcontext.getMetaData();

        // 元数据不从后台同步加载 modified by gengeng 2012-2-16
        if (dmd == null) {
            String dctid = dmModel.getDCT_ID();
            String serverName = context.getServerName();
            // 主表的元数据
            dmd = DictMetadataManager.getDefault().getMetaData(dctid, serverName);
            // 外键的元数据
            if (dmd != null) {
                DictMetadata fkeydmd = null;
                java.util.List list = MetaDataUtil.getUsedColumnStub(dmd);
                for (int i = 0; i < list.size(); i++) {
                    ColumnMetaData cmd = (ColumnMetaData) list.get(i);
                    if (!cmd.isFKEY() || !cmd.isVisible()) continue;
                    String colServerName = cmd.getExtAttriValue("COL_OPT", "=", ";", "ServerName");
                    if(colServerName == null || colServerName.trim().length() == 0){
                    	if(cmd.getString("ServerName", "") != null && cmd.getString("ServerName", "").trim().length() > 0){
                    		colServerName = cmd.getString("ServerName", "");
                    	}else{
                        	colServerName = serverName;
                    	}
                    }
//                    System.out.println("colServerName:" + colServerName);
                    fkeydmd = DictMetadataManager.getDefault().getMetaData(cmd.getFOBJ(), colServerName);
                    context.setFKEYMetaData(cmd.getFOBJ(), fkeydmd);
                }
            }
        }


        if(dmd!=null){
            dmModel.setMetaData(dmd);
            //需要放一下，添加的行的时候会用到
            newcontext.setMetaData(dmd);
        } else {
            dmModel.setMetaData(null);
        }
        //需要放一下，添加的行的时候会用到
//        newcontext.setMetaData(null);
        //从后台取过来的东西，放到前台的losobj里面，这样就不会重复向后传
        context.getLosMap().putAll(newcontext.getAttriMap());        
        cds.setUserObject(context);
    }

    protected void addColumnEventLisner(DictModel dmModel) throws Exception {
          ClientDataSet cds=dmModel.getDataSet();
          DctContext context=dmModel.getCdsParam();
          DataSetColumnEventClass dce = null;
//          int count = cds.getColumnCount();
//          for (int i = 0; i < count; i++) {
//              if (context.isFirstLoad()){
//                if(dce==null)dce=new DataSetColumnEventClass(dmModel);
//                if(cds.getColumn(i).getColumnChangeListener()==null)
//                  cds.getColumn(i).addColumnChangeListener(dce);
//              }
//          }
    }
}
