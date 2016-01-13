package com.efounder.dctbuilder.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellRenderer;

import com.core.xml.StubObject;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.util.ESPServerContext;
import com.efounder.builder.meta.MetaDataManager;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.db.DBTools;
import com.efounder.dbc.blob.BlobDataSet;
import com.efounder.dbc.swing.editor.*;
import com.efounder.dbc.swing.help.IHelpCallBack;
import com.efounder.dbc.swing.help.ImageHelpCallback;
import com.efounder.dbc.swing.render.*;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.service.dal.DBUtils;
import com.efounder.service.meta.db.DictMetadata;
import com.efounder.service.meta.db.TableMetadata;
import com.efounder.sql.JConnection;
import com.jidesoft.thirdparty.prefuse.data.column.ColumnMetadata;
import com.pub.util.ESPKeyValueUtil;

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
public class MetaDataUtil {
    public MetaDataUtil() {
    }

    public static java.util.List getUsedColumnStub(DictMetadata dmd) {
        StubObject so;
        String colid;
        List usedColList = new ArrayList();
        if (dmd.getTableMetadata() == null)
            return usedColList;
        List list = dmd.getTableMetadata().getColList();
        for (int col = 0; col < list.size(); col++) {
            so = (StubObject) list.get(col);
            if (so.getInt("F_STAU", 0) == 0)
                continue;
            colid = so.getString("COL_ID", "");
            if ("DWZD_BH".equals(colid))
                so.setString("COL_EDIT", "1");
            usedColList.add(new ColumnMetaData(so));
        }
        java.util.Collections.sort(usedColList, new Comparator() {
            public int compare(Object o1, Object o2) {
                ColumnMetaData cmd1 = (ColumnMetaData) o1;
                ColumnMetaData cmd2 = (ColumnMetaData) o2;
                int disp1 = cmd1.getColDisp();
                int disp2 = cmd2.getColDisp();
                if (disp1 == disp2)
                    return 0;
                if (disp1 < disp2)
                    return -1;
                return 1;
            }
        });
        return usedColList;
    }

    public static String getUsedColString(DictMetadata dmd) {
        List list = getUsedColumnStub(dmd);
        String col = "";
        for (int i = 0; i < list.size(); i++) {
            ColumnMetaData cmd = (ColumnMetaData) list.get(i);
            col += cmd.getColid() + ",";
        }
        if (col.length() > 0)
            col = col.substring(0, col.length() - 1);
        return col;
    }

    /**
     * 多语言的列，不含编号列
     *
     * @param dmd DictMetadata
     * @return String
     */
    public static java.util.List getUsedMLangColStub(DictMetadata dmd) {
        List mlangList = new ArrayList();
        List list = getUsedColumnStub(dmd);
        String bhcol = dmd.getString("DCT_BMCOLID", "");
        String col = "";
        for (int i = 0; i < list.size(); i++) {
            ColumnMetaData cmd = (ColumnMetaData) list.get(i);
            if (cmd.isMLang() && !bhcol.equals(cmd.getColid())) {
                mlangList.add(cmd);
            }
        }
        return mlangList;
    }

    /**
     * 多语言的列，不含编号列
     *
     * @param dmd DictMetadata
     * @return String
     */
    public static String getUsedMLangColString(DictMetadata dmd) {
        List list = getUsedColumnStub(dmd);
        String bhcol = dmd.getString("DCT_BMCOLID", "");
        String col = "";
        for (int i = 0; i < list.size(); i++) {
            ColumnMetaData cmd = (ColumnMetaData) list.get(i);
            if (cmd.isMLang() && !bhcol.equals(cmd.getColid())) {
                col += cmd.getColid() + ",";
            }
        }
        if (col.length() > 0) {
            col = col.substring(0, col.length() - 1);
        }
        return col;
    }
    /**
     * 多语言的列，不含编号列
     *
     * @param dmd DictMetadata
     * @return String
     */
    public static int getUsedMLangColCount(DictMetadata dmd) {
        List list = getUsedColumnStub(dmd);
        String bhcol = dmd.getString("DCT_BMCOLID", "");
        int  count = 0;
        for (int i = 0; i < list.size(); i++) {
            ColumnMetaData cmd = (ColumnMetaData) list.get(i);
            if (cmd.isMLang() && !bhcol.equals(cmd.getColid())) {
                count++;
            }
        }
        return count;
    }

    public static DefaultCellEditor getColumnBlobEditor(BlobDataSet bds,ColumnMetaData colinfo) {
        DefaultCellEditor dce = (DefaultCellEditor) colinfo.getEditor();
       if (dce != null)
           return dce;
        IHelpCallBack callback = null;
        if(colinfo.getHelpClass().trim().length() > 0){
            try {
                callback = (IHelpCallBack) Class.forName(colinfo.getHelpClass().
                        trim()).
                           newInstance();
                callback.setColumnMetaData(colinfo);
                colinfo.setLostValue("IHelpCallback", callback);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else
         callback=new ImageHelpCallback();//默认的
       dce=new BlobCellEditor(callback,bds,colinfo);
       colinfo.setEditor(dce);
       return dce;
    }
    public static DefaultCellEditor getColumnEditor(ColumnMetaData colinfo) {
        DefaultCellEditor dce = (DefaultCellEditor) colinfo.getEditor();
        if (dce != null)
            return dce;
        if (colinfo.getHelpFObj().trim().length() > 0) {
            dce = new FKeyCellEditor(colinfo);
        } else if (colinfo.getHelpClass().trim().length() > 0) {
            IHelpCallBack callback = null;
            try {
                callback = (IHelpCallBack) Class.forName(colinfo.getHelpClass().trim()).newInstance();
                callback.setColumnMetaData(colinfo);
                colinfo.setLostValue("IHelpCallback", callback);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            dce = new SelfHelpCellEditor(callback, colinfo);
        } else if (colinfo.isFKEY()) {
            dce = new FKeyCellEditor(colinfo);
        } else {
            switch (colinfo.getColEditType()) {
                case ColumnMetaData.EL_STRING:
                    dce = new StringCellEditor(colinfo);
                    break;
                case ColumnMetaData.EL_TEXT:
                    dce = new TextCellEditor(colinfo);
                    break;
                case ColumnMetaData.EL_BOOL:
                    dce = new BoolCellEditor();
                    break;
                case ColumnMetaData.EL_DATE:
                    dce = new DateCellEditor();
                    ( (DateCellEditor) dce).setColumnMetaData(colinfo);
                    break;
                case ColumnMetaData.EL_TIME:
                    dce = new TimeCellEditor(colinfo, 0);
                    ( (TimeCellEditor) dce).setColumnMetaData(colinfo);
                    break;
                case ColumnMetaData.EL_FLOAT:
                    dce = new NumberCellEditor();
                    break;
                case ColumnMetaData.EL_POPUP: {
                    Map hash = new Hashtable();
                    String view = colinfo.getString("COL_EDITVIEW", null);
                    if (view == null) view = colinfo.getColView();
                    //如果是变量（脚本对象），则获取真正的掩码
                    if (view != null && view.equals("@POUP@")) {
                        try {
//                            BIZPoupScript poupScript = new BIZPoupScript(
//                                    colinfo.getString("OBJ_ID", null),
//                                    colinfo.getString("COL_ID", null),
//                                    colinfo.getString("ServerName", ""));
//                            Object obj = poupScript.getReturnObject();
//                            if (obj != null && obj instanceof String) {
//                                view = (String) obj;
//                            } else {
//                                view = "";
//                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    String[] item = view.split(";");
                    List keyList = new ArrayList();
                    for (int i = 0; i < item.length; i++) {
                        if (item[i] == null || item[i].indexOf(":") < 0) continue;
                        String s1 = item[i].substring(0, item[i].indexOf(":"));
                        String s2 = item[i].substring(item[i].indexOf(":") +1);
                        hash.put(s1, s2);
                        keyList.add(s1);
                    }
                    String editorEditable = colinfo.getString("EnumCellEditor_EDITABLE","0");
                    if(editorEditable.equals("1")){
                        dce = new EnumCellEditor(hash, keyList, colinfo);
                    }else{
                        dce = new EnumCellEditor(hash, keyList);
                    }

                    break;
                }
                //增加密码列
                case ColumnMetaData.EL_WINDOW: {
                    dce = new PassWordCellEditor();
                    break;
                }
                //增加弹出窗口
                case ColumnMetaData.EL_DIALOG: {
                    dce = new WindowCellEditor(colinfo);
                    break;
                }
                //自列表
				case ColumnMetaData.EL_SELFENUM: {
					Map hash = new Hashtable();
					List keyList = new ArrayList();
					String view = colinfo.getString("COL_EDITVIEW", null);
                    if (view == null) view = colinfo.getColView();
                    String[] item = view.split(";");
                    for (int i = 0; i < item.length; i++) {
                        if (item[i] == null || item[i].indexOf(":") < 0) continue;
                        String s1 = item[i].substring(0, item[i].indexOf(":"));
                        String s2 = item[i].substring(item[i].indexOf(":") +1);
                        hash.put(s1, s2);
                        keyList.add(s1);
                    }
					dce = new SelfEnumCellEditor(hash, keyList, colinfo);

					break;
				}
                default:
                    break;
            }
        }
        colinfo.setEditor(dce);
        return dce;
    }

    public static TableCellRenderer getColumnRender(ColumnMetaData colinfo) {
        TableCellRenderer dcr = (TableCellRenderer) colinfo.getRender();
        if (dcr != null)
            return dcr;
        if(colinfo.getColType().equals("B")){
            dcr=new BlobCellRender();
        }
        else if (colinfo.getHelpFObj().trim().length() > 0) {
            dcr = new FkeyCellRenderer(colinfo.getFOBJ());
            ((FkeyCellRenderer)dcr).setColumnMetaData(colinfo);
        } else if (colinfo.getHelpClass().trim().length() > 0) {
            dcr = new FkeyCellRenderer(getFObj(colinfo));
            ((FkeyCellRenderer)dcr).setColumnMetaData(colinfo);
        } else if (colinfo.isFKEY()) {
            dcr = new FkeyCellRenderer(colinfo.getFOBJ());
            ((FkeyCellRenderer)dcr).setColumnMetaData(colinfo);
        } else {
            switch (colinfo.getColEditType()) {
                case ColumnMetaData.EL_BOOL:
                    dcr = new BoolCellRender("1", "0");
                    break;
                case ColumnMetaData.EL_DATE:
                    String pattern = colinfo.getEditPattern();
                    if (pattern == null || pattern.trim().length() == 0){
                    	pattern = "yyyy-MM-dd HH:mm:ss";
                    	if("C".equals(colinfo.getColType())){
                    		if(colinfo.getColDataLen()==4){
                    			pattern = "yyyy";
                    		}else if(colinfo.getColDataLen()==6){
                    			pattern = "yyyy-MM";
                    		}else if(colinfo.getColDataLen()==8){
                    			pattern = "yyyy-MM-dd";
                    		}
                    		if(colinfo.getColView() != null && colinfo.getColView().trim().length() > 0){
                        		pattern = colinfo.getColView();
                    		}
                    		String displayformat = colinfo.getExtAttriValue("COL_OPT", ESPKeyValueUtil._DEFAULT_CONNECTOR_, ESPKeyValueUtil._DEFAULT_DELIMETER_, "DisplayFormat");
                    		if(displayformat != null && displayformat.trim().length() > 0){
                    			pattern = displayformat;
                    		}
                    	}else if("D".equals(colinfo.getColType())){
                    		String colView = colinfo.getColView();
                    		if(colView.length() > 0)
                    			pattern = colView;
                    	}
                    }
                    dcr = new DateCellRenderer(pattern);
                    break;
                case ColumnMetaData.EL_TIME:
                    String pattern1 = colinfo.getEditPattern();
                    if (pattern1 == null || pattern1.trim().length() == 0)
                        pattern1 = "HH:mm:ss";
                    dcr = new TimeCellRenderer(pattern1);
                    break;
                case ColumnMetaData.EL_FLOAT:
                    dcr = new NumberCellRenderer();
                    ((NumberCellRenderer)dcr).setColumnMetaData(colinfo);
                    break;
                case ColumnMetaData.EL_POPUP:{
                    Hashtable hash = new Hashtable();
                    String view = colinfo.getColView();
                    //如果是变量（脚本对象），则获取真正的掩码
                    if (view != null && view.equals("@POUP@")) {
//                        try {
//                            BIZPoupScript poupScript = new BIZPoupScript(
//                                    colinfo.getString("OBJ_ID", null),
//                                    colinfo.getString("COL_ID", null),
//                                    colinfo.getString("ServerName", ""));
//                            Object obj = poupScript.getScriptManager().runFunction(poupScript,colinfo.getString("COL_ID", null));
//                            if (obj != null && obj instanceof String) {
//                                view = (String) obj;
//                            } else {
//                                view = "";
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
                    }
                    String[] item = view.split(";");
                    for (int i = 0; i < item.length; i++) {
                        String[] v = item[i].split(":");
                        if (v.length == 1)
                            return null;
                        hash.put(v[0], v[1]);
                    }
                    dcr = new EnumCellRenderer(hash);
                    break;
                }
                case ColumnMetaData.EL_WINDOW:
                    dcr = new PassWordCellRender();
                    break;
                case ColumnMetaData.EL_PRO_BAR:
                    dcr = new ProgressCellRender();
                    break;
                case ColumnMetaData.EL_SELFENUM:{
					Hashtable hash = new Hashtable();
					String view = colinfo.getString("COL_EDITVIEW", null);
                    if (view == null) view = colinfo.getColView();
                    String[] item = view.split(";");
                    for (int i = 0; i < item.length; i++) {
                        if (item[i] == null || item[i].indexOf(":") < 0) continue;
                        String s1 = item[i].substring(0, item[i].indexOf(":"));
                        String s2 = item[i].substring(item[i].indexOf(":") +1);
                        hash.put(s1, s2);
                    }
					dcr = new SelfEnumCellRenderer(hash);

					break;
                }
                case ColumnMetaData.EL_STRING:
                    dcr = new StringCellRenderer();
                    break;
                case ColumnMetaData.EL_TEXT:
                    dcr = new TextCellRenderer();
                    break;
                default:
                    break;
            }
        }
        colinfo.setRender(dcr);
        return dcr;
    }

    public static String getFObj(ColumnMetaData colinfo) {
    	String helpclass = colinfo.getHelpClass().trim();
    	if("com.pansoft.esp.standard.client.pub.ESPModelKeySetHelp".equals(helpclass)){
	    	DictModel dictModel = (DictModel)colinfo.getLostValue("DictModelObject", null);
	    	BIZMetaData bizMetaData = (BIZMetaData)dictModel.getCdsParam().getValue("ESPModelStub", null);
	    	return bizMetaData.getKEY_DCT();
    	}
    	return colinfo.getFOBJ();
    }

    /**
     *
     * @param  conn Connection
     * @param    PO JParamObject
     * @param dctid String
     * @return      DictMetadata
     * @throws      Exception
     */
    public static DictMetadata getMetaData(Connection conn, JParamObject PO, String dctid) throws Exception {

        return getMetaDataNew(JConnection.getInstance(conn), PO, dctid);

//        MetaContextObject     mco = MetaContextObject.getInstance(PO, JConnection.getInstance(conn), null, "IndexModel");
//        DictMetadata dictMetadata = DBMetadataManager.getDefault("IndexModel").getDictMetadata(mco, dctid);
//        //增加对数据字典的自定义属性SYS_DCT_CST
//        java.util.List     dctcst = getDictCST(JConnection.getInstance(conn), PO, dctid);
//        dictMetadata.setObject("SYS_DCT_CST", dctcst);
//        return dictMetadata;
    }

    /**
     * 取数据字典的自定义属性列表STUBOBJECT
     */
    private static List getDictCST(JConnection conn, JParamObject PO, String dctid) {
        java.util.List maps = new ArrayList();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            HashMap map = new HashMap();
            map.put("TABLE", "SYS_DCT_CST");
            map.put("WHERE", "DCT_ID = '" + dctid + "'");
            map.put("ORDER", "UNIT_ID,DCT_KEY");
            maps.add(map);
            return DBUtils.SimpleQuery(conn, PO, maps, stmt);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            conn.BackStatement(stmt, null);
        }
        return null;
    }
    public static ColumnMetaData CreateColMetadata(String id, String mc,
                                                int displen, int edittype,
                                                String view) {
    	ColumnMetaData col = ColumnMetaData.getInstance();
        col.setString("COL_ID", id);
        col.setString("COL_MC", mc);
        col.setString("COL_PREC", String.valueOf(displen));
        col.setString("COL_USE", "1");
        String type="C";
        if(edittype==ColumnMetaData.EL_DATE)type="D";
        if(edittype==ColumnMetaData.EL_INT)type="I";
        if(edittype==ColumnMetaData.EL_FLOAT)type="N";
        col.setString("COL_TYPE",type);
        col.setString("COL_EDIT", String.valueOf(edittype));
        if (view != null)
            col.setString("COL_VIEW", view);
        return col;
    }

    /**
     * 创建列
     *
     * @param       id String
     * @param       mc String
     * @param     type String
     * @param      len int
     * @param edittype int
     * @param     view String
     * @return         ColumnMetaData
     */
    public static ColumnMetaData CreateColumnMetadata(String id,
            String mc, String type, int len, int edittype, String view) {
        ColumnMetaData meta = new ColumnMetaData(null);
        meta.setString("COL_ID", id);
        meta.setString("COL_MC", mc);
        meta.setInt("COL_LEN", len);
        meta.setString("COL_TYPE", type);
        meta.setString("COL_EDIT", String.valueOf(edittype));
        if (view != null)
            meta.setString("COL_VIEW", view);
        return meta;
    }





    /**
     * 使用新式的元数据兼容旧的元数据
     */
    protected static DictMetadata getMetaDataNew(JConnection conn, JParamObject PO, String dctid) throws Exception {
        DictMetadata dictMetadata = DictMetadata.getInstance();
        dictMetadata.setID(dctid);
        Statement st = null;
        DCTMetaData DICTMeta = null;
        DOMetaData    DOMeta = null;
        try {
            st = JConnection.getInstance(conn).createStatement();
            //
            ESPServerContext ctx = ESPServerContext.getInstance(PO, JConnection.getInstance(conn));
            ctx.setStatement(st);
            DICTMeta = (DCTMetaData) MetaDataManager.getInstance(MetaDataManager._DCTObject_ + "_DAL").getMetaData(ctx, dctid);
            if (DICTMeta != null) {
                DOMeta = DICTMeta.getDoMetaData();
                dictMetadata.setStubTable(new Hashtable());
                dictMetadata.getStubTable().putAll(DICTMeta.getDataMap());
//                DICTMeta.getSYS_DCT_CST();
            } else {
                DOMeta = (DOMetaData) MetaDataManager.getInstance(MetaDataManager._DataObject_ + "_DAL").getMetaData(ctx, dctid);
            }
            //对象信息
            TableMetadata tableMetadata = TableMetadata.getInstance();
            tableMetadata.setID(dctid);
            tableMetadata.setStubTable(new Hashtable());
            if (tableMetadata.colList == null) tableMetadata.colList = new ArrayList();
            dictMetadata.tableMetadata = tableMetadata;
            //复制对象信息
            if (DOMeta != null)
                tableMetadata.getStubTable().putAll(DOMeta.getDataMap());
            //复制列信息
            getColMetadata(conn, PO, dictMetadata, DOMeta);
            //复制数据字典的扩展属性
            getSYS_DCT_CST(conn, PO, dictMetadata, DICTMeta);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            conn.BackStatement(st, null);
        }
        return dictMetadata;
    }

    protected static void getSYS_DCT_CST(JConnection conn, JParamObject PO, DictMetadata dictMetadata, DCTMetaData DCTMeta) throws Exception {
        if (DCTMeta == null) return;
        TableMetadata tableMetadata = dictMetadata.getTableMetadata();
        String DCT_ID = tableMetadata.getString("OBJ_ID", "");
        java.util.List list = new ArrayList();
        java.util.Map SYS_DCT_CST = DCTMeta.getSYS_DCT_CST();
        if (SYS_DCT_CST == null) return;;
        for (Iterator it = SYS_DCT_CST.keySet().iterator(); it.hasNext(); ) {
            Object key = it.next();
            Object val = SYS_DCT_CST.get(key);
            StubObject so = new StubObject();
            so.setString("UNIT_ID", "");
            so.setString("DCT_ID", DCT_ID);
            so.setString("DCT_KEY", (String)key);
            so.setString("DCT_VALUE", (String)val);
            list.add(so);
        }
        dictMetadata.setObject("SYS_DCT_CST", list);
    }

    /**
     *
     * @param tableMetadata TableMetadata
     * @param        DOMeta DOMetaData
     */
    protected static void getColMetadata(JConnection conn, JParamObject PO, DictMetadata dictMetadata, DOMetaData DOMeta) throws Exception {
        if (DOMeta == null) return;
        TableMetadata tableMetadata = dictMetadata.getTableMetadata();
        if (tableMetadata.colList == null) tableMetadata.colList = new ArrayList();
        EFDataSet efds = DOMeta.getDOColumns();
//        //获取自列表列与掩码Map
//        java.util.Map map = getSelfEnumColViewMap(conn, PO, dictMetadata);
//        //
        for (int i = 0; i < efds.getRowCount(); i++) {
            EFRowSet edrs = efds.getRowSet(i);
            ColumnMetaData colMetadata = ColumnMetaData.getInstance();
            colMetadata.setStubTable(new Hashtable());
            colMetadata.getStubTable().putAll(edrs.getDataMap());
//			String colid = colMetadata.getString("COL_ID", "");
            String coledit = colMetadata.getString("COL_EDIT", "");
//            if(map != null && coledit.equals("15")){
//                String view = (String)map.get(colid);
//                colMetadata.setString("COL_VIEW", view);
//            }
            // 从DOMetaData中获取 gengeng 2011-11-21
            if (coledit.equals(ColumnMetaData.EL_SELFENUM))
                colMetadata.setString("COL_VIEW", edrs.getString("SYS_SELFENUM", ""));

            tableMetadata.colList.add(colMetadata);
//            getColRefDictMetadata(conn, PO, dictMetadata, colMetadata);
        }
    }

    /**
     * 获取自列表的列与掩码的Map
     * @param conn
     * @param PO
     * @param dictMetadata
     * @param DOMeta
     * @throws Exception
     */
    protected static java.util.Map getSelfEnumColViewMap(JConnection conn, JParamObject PO,
            DictMetadata dictMetadata) throws Exception {
        if(dictMetadata == null){
            return null;
        }
        TableMetadata tableMetadata = dictMetadata.getTableMetadata();
        if (tableMetadata == null) {
            return null;
        }
        java.util.List colList = tableMetadata.getColList();
        if (colList == null) return null;

        //获取各列的掩码
        java.util.Map map = new java.util.HashMap();
        String objid = tableMetadata.getString("OBJ_ID", "");
        Statement st = null;
        ResultSet rs = null;
        String sql = " select * from SYS_SELFENUM" +
                     " where OBJ_ID='" + objid + "' order by COL_DISP";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String colid = rs.getString("COL_ID");
                String colval = rs.getString("COL_VAL");
                String colnote = rs.getString("F_NOTE");
                if (colval == null || colval.length() == 0) {
                    colval = " ";
                }
                if (colnote == null || colnote.length() == 0) {
                    colnote = colval;
                }
                String view = (String)map.get(colid);
                if(view == null){
                    map.put(colid, colval+ ":" + colnote);
                }else{
                    if(view.endsWith(";")){
                        view = view.substring(0,view.length()-1);
                    }
                    map.put(colid, view + ";" + colval+ ":" + colnote);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.BackStatement(st, rs);
        }
        return map;
    }

    /**
     *
     * @param conn JConnection
     * @param PO JParamObject
     * @param dictMetadata DictMetadata
     * @param colMetadata ColMetaData
     * @throws Exception
     */
    protected static void getColRefDictMetadata(JConnection conn, JParamObject PO, DictMetadata dictMetadata, ColumnMetaData colMetadata) throws Exception {
        // 如果列没有启用，直接返回
        TableMetadata tableMetadata = dictMetadata.getTableMetadata();
        if ( !"1".equals(colMetadata.getString("COL_USE","1")) ) return;
        String COL_FOBJ = colMetadata.getString("COL_FOBJ",null);
        if ( COL_FOBJ == null || "".equals(COL_FOBJ.trim()) ) return;
        // 如果外键是他自己，可能造成死循环，不再获取元数据
        if (COL_FOBJ.equals(tableMetadata.getString("OBJ_ID", ""))) return;
        DictMetadata fkeyDictMetadata = getMetaDataNew(conn, PO, COL_FOBJ);
        attachColmetadataRefDict(colMetadata, fkeyDictMetadata);
    }

    /**
     *
     * @param colMetadata ColMetadata
     * @param dictMetadata DictMetadata
     */
    public static void attachColmetadataRefDict(ColumnMetaData colMetadata,DictMetadata dictMetadata) {
    	colMetadata.refDictmetadata = dictMetadata;
    }
   
    /**
     *
     * @param text String
     * @param unicodeLength int unicode字符的长度
     * @return int
     */
    public static int getLenth(String text, int unicodeLength) {
        int len = 0;
        for (int i = 0; i < text.length(); i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length == 1)
                len++;
            else
                len+= unicodeLength;
        }
        return len;
    }

}
