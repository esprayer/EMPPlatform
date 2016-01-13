package com.efounder.dbc.swing.render;

import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.core.xml.StubObject;
import com.efounder.buffer.DictDataBuffer;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.builder.base.util.ESPClientContext;
import com.efounder.builder.meta.MetaDataManager;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dbc.swing.render.plugin.IFKeyCaption;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.util.DictUtil;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.service.MDMDataUtil;
import com.efounder.plugin.PluginManager;
import com.efounder.service.meta.db.DictMetadata;

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
public class FkeyCellRenderer extends DefaultTableCellRenderer{

    /**
     *
     */
    private String fobj;
    private String userObject;

    Map codeNameMap = null;
    DCTMetaData dmd = null;
    private ColumnMetaData  cmd;
    DictModel model;
    private int row;

    /**
     *
     * @param key String
     */
    public FkeyCellRenderer(String key) {
        fobj = key;
        initMetaData();
    }

    /**
     *
     * @param dctkey String
     */
    public void setDctKey(String dctkey) {
        this.fobj = dctkey;
    }

    /**
     *
     * @return String
     */
    public String getDctKey() {
        return this.fobj;
    }

    /**
     *
     * @param tmp String
     */
    public void setUserObject(String tmp){
        userObject = tmp;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    /**
     *
     * @return String
     */
    public String getUserObject(){
        return this.userObject;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public JTable getTable() {
        return table;
    }

    private int column;
    private JTable table;

    /**
     *
     * @param cmd ColumnMetaData
     */
    public void setColumnMetaData(ColumnMetaData cmd){
        this.cmd = cmd;
        initDictModel();
    }

    /**
     *
     * @return ColumnMetaData
     */
    public ColumnMetaData getColumnMetaData() {
        return this.cmd;
    }

    /**
     * 初始化DictModel
     */
    private void initDictModel() {
        if (cmd.getLostValue("DictModelObject", null) != null) {
            model = (DictModel) cmd.getLostValue("DictModelObject", null);
        }
    }

    protected void initMetaData(){
        try {
           if(fobj == null || "".equals(fobj.trim())){
              codeNameMap = new HashMap();
              return ;
           }
           String colServerName = getServerName();
//           System.out.println("colServerName:" + colServerName);
           JParamObject PO = JParamObject.Create();
           PO.setEAIServer(colServerName);
           ESPClientContext cc = ESPClientContext.getInstance();
           cc.setParamObject(PO);
           dmd = (DCTMetaData) MetaDataManager.getDCTDataManager().getMetaData(cc, fobj);
//           EFDataSet efDataSet = (EFDataSet) DictDataBuffer.getDefault().getDataItem(DictDataBuffer.DCTDATASET, fobj);
           EFDataSet efDataSet = (EFDataSet)MDMDataUtil.getMainDctData(PO, fobj,false);
           if(efDataSet == null)efDataSet = DictDataBuffer.getDefault().QueryData(PO,fobj,null,null);
           codeNameMap  = makeMap(efDataSet,dmd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    protected Map makeMap(EFDataSet efDataSet,DCTMetaData dmd){
        if(efDataSet == null) return new HashMap();
        List dataList = efDataSet.getRowSetList();
        if(dataList == null || dataList.size() == 0) return new HashMap();
        Map map = new HashMap();
        for(int i = 0; i < dataList.size(); i++){
            EFRowSet rowSet = (EFRowSet)dataList.get(i);
            map.put(rowSet.getString(dmd.getDCT_BMCOLID(),""),rowSet.getString(dmd.getDCT_MCCOLID(),""));
        }
        return map;
    }


    /**
     *
     * @param value Object
     */
    protected void setValue(Object value) {
        if (value == null) value = "";
        String bh = value.toString().trim();
        if(bh.length() == 0) {
        	setText(""); 
        	return;
        }
        String mc = getCaption(bh);
        if(mc == null) mc = "";
        String text = bh;
        if (cmd != null) {
            String mode = cmd.getFKeyShowMode();
            if (mode.equals(cmd.FKEY_SHOWMODE_BH)) {
                text = bh;
            } else if (mode.equals(cmd.FKEY_SHOWMODE_MC)) {
                text = mc;
            } else if (mode.equals(cmd.FKEY_SHOWMODE_BHMC)) {
                text = bh + " " + mc;
            }
            //如果根据设置的方式无法显示值，则使用编号名称的方式
            if (text.trim().length() == 0) {
                text = bh + " " + mc;
            }
        }
        setText(text);
        setToolTipText(bh + " " + mc);
    }

    /**
     *
     * @param bh String
     * @return String
     */
    protected String getCaption(String bh) {
        //如果外键对象为空,直接返回编号
        if(fobj == null || "".equals(fobj.trim())) return bh;
        String   strCaption = null;
        //1.使用插件方式获取外键描述
        java.util.List list = PluginManager.loadPlugins(IFKeyCaption.class.getName(), true);
        for (int i = 0, n = list.size(); i < n; i++) {
            StubObject so = (StubObject) list.get(i);
            //寻找合适的CAPTION获取插件
            if (!so.getID().equals(fobj)) continue;
            if (so.getPluginObject() != null && so.getPluginObject() instanceof IFKeyCaption) {
                IFKeyCaption caption = (IFKeyCaption) so.getPluginObject();
                strCaption = caption.getCaption(bh, this);
            }
        }
        //如果获取到描述，则返回
        if (strCaption != null && strCaption.trim().length() > 0) {
//        	System.out.println("FkeyCellRenderer-1:" + bh + " - " +strCaption);
        	return strCaption;
        }

        //2.兼容原来的方式，看该列的元数据上是否有外键数据、外键元数据
        DictMetadata meta = cmd.getFKMetaData();
        EFDataSet    efds = cmd.getFKDataMap();
        if (meta != null && efds != null) {
            String DCT_MCCOLID = meta.getString("DCT_MCCOLID", "");
            ESPRowSet efrs = efds.getRowSet(bh);
            if (efrs != null) strCaption = efrs.getString(DCT_MCCOLID, "");
        }
        //如果获取到描述，则返回
        if (strCaption != null && strCaption.trim().length() > 0) {
//        	System.out.println("FkeyCellRenderer-2:" + bh + " - " +strCaption);
        	return strCaption;
        }

        //3.先利用缓存获取描述，获取不到再访问数据库
        EFRowSet rowSet = null;
        if (fobj.length() > 0 && bh.trim().length() > 0) {
        	String colServerName = getServerName();
            rowSet = DictUtil.getRowSetFromBuffer(fobj, bh, colServerName, false);
            if (rowSet == null) {
//            	System.out.println("FkeyCellRenderer-查找数据库");
            	rowSet = DictUtil.getRowSetFromBuffer(fobj, bh, colServerName, true);
            }
        }
        if (rowSet == null) {
//        	System.out.println("FkeyCellRenderer-3:" + bh + " - " );
        	return "";
        }
        if (meta != null){
        	//add by zhangft 20130427 start
        	if (efds == null) {
        		efds = EFDataSet.getInstance(cmd.getFOBJ());
                if (dmd != null){
                    efds.setPrimeKey(new String[]{dmd.getDCT_BMCOLID()});
                }
        	}
        	efds.insertRowSet(rowSet);//将数据添加到外键数据集中
        	efds.buildPrimeKeyIndex(rowSet);// 形成主键索引
        	//add by zhangft 20130427 end
//        	System.out.println("FkeyCellRenderer-4:"  + bh + " - " + rowSet.getString(meta.getString("DCT_MCCOLID",""), ""));
        	return rowSet.getString(meta.getString("DCT_MCCOLID",""), "");
        }else{
            DCTMetaData metaData = getDCTMetaData();
        	if (metaData != null){
	        	//add by zhangft 20130427 start
	        	if (efds == null) {
					efds = EFDataSet.getInstance(cmd.getFOBJ());
					if (dmd != null) {
						efds.setPrimeKey(new String[] { dmd.getDCT_BMCOLID() });
					}
	        	}
	        	efds.insertRowSet(rowSet);//将数据添加到外键数据集中
	        	efds.buildPrimeKeyIndex(rowSet);// 形成主键索引
	        	//add by zhangft 20130427 end
//	        	System.out.println("FkeyCellRenderer-5:" + bh + " - " + rowSet.getString(metaData.getDCT_MCCOLID(), ""));
	        	return rowSet.getString(metaData.getDCT_MCCOLID(), "");
        	}
        }

//        System.out.println("FkeyCellRenderer-6:" + bh + " - " );
        return "";
    }

    /**
     * 初始化字典元数据，主要获取编号、名称列
     */
    protected DCTMetaData getDCTMetaData() {
        try {
            if (fobj == null || fobj.trim().length() == 0) return null;
            return DictUtil.getDCTMetaData(fobj, this.getServerName());
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     *
     * @param table JTable
     * @param value Object
     * @param isSelected boolean
     * @param hasFocus boolean
     * @param row int
     * @param column int
     * @return Component
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setRow(row);
        this.setColumn(column);
        this.setTable(table);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    /**
    *
    * @return String
    */
    private String getServerName() {
//   		String colServerName = cmd.getExtAttriValue("COL_OPT", "=", ";", "ServerName");
//   		if(colServerName != null && colServerName.trim().length() > 0){
//			return colServerName;
//   		}else if(cmd.getString("ServerName","") != null && cmd.getString("ServerName","").trim().length() > 0){
//   			return cmd.getString("ServerName","");
//      	}else if (model != null){
//      		return model.getCdsParam().getServerName();
//      	}
   		return "";
    }
}
