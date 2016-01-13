package com.efounder.dbc.swing.help;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.core.xml.StubObject;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.dbc.ClientDataSet;
import com.efounder.dbc.DictMetadataManager;
import com.efounder.dbc.swing.render.FkeyCellRenderer;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.efounder.dctbuilder.util.DictUtil;
import com.efounder.dctbuilder.variant.IVariantAnalytic;
import com.efounder.dctbuilder.variant.Variant;
import com.efounder.dctbuilder.variant.VariantPluginManager;
import com.efounder.dctbuilder.variant.VariantStub;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.eai.service.MDMDataUtil;
import com.efounder.eai.service.ParameterManager;
import com.efounder.service.meta.db.DictMetadata;
import com.efounder.service.script.ScriptManager;
import com.efounder.service.script.ScriptObject;
import com.efounder.service.script.Scriptable;
import com.help.HelpInfoContext;
import com.help.util.ESPHelpUtil;
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
public class DictHelpTextField extends JPanel implements ActionListener,  MouseListener, FocusListener, Scriptable {

    BorderLayout bl = new BorderLayout();
    JTextField text = null;
    JButton bthelp = new JButton();
    Icon icon = null;
    ColumnMetaData cmd;
    private DictModel model;
    private String fwhere;   //SQL语法的过滤条件
    private String filterExp;//JAVA语法的过滤条件

    /**
     *
     * @param jtf JTextField
     * @param cmd ColumnMetaData
     */
    public DictHelpTextField(JTextField jtf,ColumnMetaData cmd) {
        this.cmd = cmd;
        setLayout(bl);
        text = jtf;
        text.addMouseListener(this);
        text.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        icon = ExplorerIcons.getExplorerIcon("help.gif");
        initDictModel();
        bthelp.setIcon(icon);
        bthelp.setPreferredSize(new Dimension(icon.getIconWidth(),
                                              icon.getIconHeight()));
        add(text, BorderLayout.CENTER);
        add(bthelp, BorderLayout.EAST);
        text.addFocusListener(this);
        bthelp.addActionListener(this);
    }

    /**
     * 初始化DictModel
     */
    private void initDictModel() {
        if (cmd.getLostValue("DictModelObject", null) != null) {
            model = (DictModel) cmd.getLostValue("DictModelObject", null);
        }
    }

    /**
     * 初始化外键字典
     */
    private void initFObj() {
        String fobjid = cmd.getHelpFObj();
        //如果是引用变量，则从DataSet中获取外键字典名称
        fobjid = fobjid.trim();
        if (fobjid.startsWith("@") && fobjid.endsWith("@")) {
            fobjid = fobjid.substring(1, fobjid.length() - 1);
            if (model.getDataSet().hasColumn(fobjid) != null) {
                fobjid = model.getDataSet().getRowSet().getString(fobjid, "");
            }
        } else {
            fobjid = cmd.getFOBJ();
        }
        if (fobjid.trim().length() > 0) {
            cmd.setString("COL_ISFKEY", "1");
            cmd.setString("COL_FOBJ", fobjid);
            DictMetadata dictMetadata = DictMetadataManager.getDefault().getMetaData(fobjid, getServerName());
            cmd.setFKMetaData(dictMetadata);
        }
    }

    /**
     * SQL语法过滤条件
     */
    private void initFWhere() {
        fwhere = cmd.getHelpFWhere();
        //--20130606 add by zhangft--
        String srccont = cmd.getString("F_SRCCONT", "");
        if(srccont != null && srccont.length()>0){
        	fwhere = ESPKeyValueUtil.getValueByKey("HELPFWHERE", fwhere, srccont);
        }
        //---------------------------
        //--20111202 add by zhangft--
        String NodeFHelpWhere = cmd.getString("NodeFHelpWhere","");
        if(NodeFHelpWhere != null && NodeFHelpWhere.trim().length() > 0){
        	if(fwhere != null && fwhere.trim().length() > 0){
        		fwhere += " and ";
        	}
        	fwhere += NodeFHelpWhere;
        }
        //--------------------------
        fwhere = initVariant(fwhere);
    }

    /**
     * JAVA语法的过滤
     */
    private void initFilterExp() {
        filterExp = cmd.getHelpFliterExp();
        //--20130606 add by zhangft--
        String srccont = cmd.getString("F_SRCCONT", "");
        if(srccont != null && srccont.length()>0){
        	filterExp = ESPKeyValueUtil.getValueByKey("HELPFILTEREXP", filterExp, srccont);
        }
        //---------------------------
        filterExp = initVariant(filterExp);
    }

    private VariantPluginManager pluginManager = new VariantPluginManager();

    /**
     *
     * @param variant String
     */
    private String initVariant(String expString) {
		if (expString == null || expString.trim().length() == 0
				|| expString.indexOf("@") < 0){
			return expString;
		}
		//用“@”分割后索引为大于1的奇数项就是变量
        String[] items = expString.split("@");
        for (int i = 1; i < items.length; i = i + 2) {
            //如果存在这样的列
            if (model.getDataSet().hasColumn(items[i]) != null) {
//                Variant variant = new Variant();
//                model.getDataSet().getRowSet().getVariant(items[i], variant);
//                items[i] = variant.toString();
            }
            //否则可能为固定变量
            else{
            	Object value = getVariantValue(cmd.getColid(), items[i]);
            	if (value != null) items[i] = value.toString();
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < items.length; i++) {
            sb.append(items[i]);
        }
        return sb.toString();
    }

    /**
     * 获固定变量的值
     *
     * @param coldef String
     * @param model DictModel
     * @return Object
     */
    private Object getVariantValue(String colid, String var) {
        StubObject ss = pluginManager.getPluginParser(var);
        if (ss != null) {
            IVariantAnalytic va = (IVariantAnalytic) ss.getPluginObject();
            //构造一个变量对象
            VariantStub stub = new VariantStub(var, null);
            stub.setString("ColumnName", colid);
            return va.getVariantValue(stub);
        }
        return null;
    }

    /**
     *
     * @return JButton
     */
    public JButton getHelpButton() {
        return bthelp;
    }

    /**
     *
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        //初始化外键字典
        initFObj();
        //初始化后台过滤条件
        initFWhere();
        //初始化前台过滤条件
        initFilterExp();
        DictMetadata fkdmd = cmd.getFKMetaData();
        DictMetaDataEx fkdme = null;
        if (fkdmd != null)
            fkdme = new DictMetaDataEx(fkdmd);
        HelpInfoContext hic = new HelpInfoContext();
        hic.setServerName(getServerName());
        if (fkdme != null && fkdme.getDctid() != null && fkdme.getDctid().trim().length() > 0) {
        	String[] helpfcolumn =  getHelpFColumn(fkdme);
            hic.setDCTID(fkdme.getDctid());
            hic.setBHColumn(fkdme.getDctBmCol());
            hic.setMCColumn(fkdme.getDctMcCol());
            hic.setJSColumn(fkdme.getDctJsCol());
            hic.setMXColumn(fkdme.getDctMxCol());
            hic.setPTColumn(fkdme.getDctParentCol());
            hic.setBMStru(fkdme.getDctBMStru());
            hic.setHelpColumn(helpfcolumn);
        } else {
            hic.setDCTID(cmd.getFOBJ());
            if (hic.getDCTID().trim().length() == 0) return;
        }
        hic.setWhere(this.fwhere);
        hic.setFilterExp(this.filterExp);
        //设置模型。默认设置，当DctEditUseSjqx为0时不设置
        // 默认应该使用数据权限
        String DctEditUseSjqx = cmd.getExtAttriValue("F_SRCCONT", "=", ";", "DctEditUseSjqx");
        if(DctEditUseSjqx == null || DctEditUseSjqx.trim().length() == 0){
        	DctEditUseSjqx = cmd.getExtAttriValue("COL_OPT", "=", ";", "DctEditUseSjqx");
        }
        if (DctEditUseSjqx == null)
        	DctEditUseSjqx = "1";
        if(DctEditUseSjqx != null && !"0".equals(DctEditUseSjqx)){
            String MDL_ID = ParameterManager.getDefault().getSystemParam(EAI.Product + "_MODEL");
            if(MDL_ID.equals("")) MDL_ID="ACPZModel";
            hic.setModelID(MDL_ID);
        }
        String showFilterDlg = cmd.getExtAttriValue("F_SRCCONT", "=", ";", "showFilterDlg");
		if(showFilterDlg == null || showFilterDlg.trim().length() == 0){
			showFilterDlg = cmd.getExtAttriValue("COL_OPT", "=", ";", "showFilterDlg");
		}
        if (showFilterDlg == null) showFilterDlg = "0";
        hic.setObject("showFilterDlg", showFilterDlg);
        //对于外键可变的字段，给其renderer设置其外键key，方便显示名称
        setFObjKey(hic.getDCTID());
        DCTMetaData   meta = null;
        String fkbmcol = hic.getBHColumn();
        if (fkbmcol == null || fkbmcol.trim().length() == 0) {
            try {
                meta = DictUtil.getDCTMetaData(hic.getDCTID(), hic.getServerName());
                if (meta != null) fkbmcol = meta.getDCT_BMCOLID();
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        }
        /**
         * 如果指定了后台过滤条件，则使用自定义的获取数据的方法
         */
//        if (hic.getWhereClause().trim().length() > 0) {
//            EFDataSet data = null;
//            JParamObject PO = JParamObject.Create();
//            PO.setEAIServer(hic.getServerName());
//            if (meta != null) data = ESPHelpUtil.loadHelpData(PO, meta, false).getDCTDataSet();
//            data = DictUtil.getHelpDataSet(hic, JParamObject.Create());
//            hic.setDataObject(data);
//        }

        try {
        	//beforeHelp脚本
            getScriptManager().setObject("HIC", hic);
            getScriptManager().setObject("FKey", hic.getDCTID());
            getScriptManager().setObject("ColumnMetaData", cmd);
            getScriptManager().setObject("ActionEvent", e);
            getScriptManager().setObject("DictModel", model);
            try {
                Object o = getScriptManager().runFunction(this, "beforeHelp");
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                getScriptManager().setObject("HIC", null);
                getScriptManager().setObject("FKey", null);
                getScriptManager().setObject("ColumnMetaData", null);
                getScriptManager().setObject("ActionEvent", null);
                getScriptManager().setObject("DictModel", null);
            }

            //显示帮助窗口
            EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);

            if (rowset != null) {

                // 把帮助出来的数据加到缓存中
                DictMetadata dictMeta = cmd.getFKMetaData();
                EFDataSet dictData = cmd.getFKDataMap();
                if (dictData == null) {
                    dictData = EFDataSet.getInstance("");
                    //modified by lx   DCT_BHCOLID ---> DCT_BMCOLID
                    dictData.setPrimeKey(new String[] {dictMeta.getString("DCT_BMCOLID", "")});
                    cmd.setFKDataSet(dictData);
                }
                String bhval = rowset.getString(dictMeta.getString("DCT_BMCOLID", ""), "");
                if (dictData.getRowSet(new String[] {bhval}) == null) {
                    dictData.addRowSet(rowset);
                }



            	//获取帮助中的取值字段
            	//如果指定了,则获取指定列的值
            	String value = null;
            	String HELPVALUECOL = cmd.getExtAttriValue("F_SRCCONT", "=", ";", "HELPVALUECOL");
                if(HELPVALUECOL == null || HELPVALUECOL.trim().length() == 0){
                	HELPVALUECOL = cmd.getExtAttriValue("COL_OPT", "=", ";", "HELPVALUECOL");
                }
                if(HELPVALUECOL != null && HELPVALUECOL.trim().length() > 0){
        			value = rowset.getString(HELPVALUECOL.trim(), "");
                }
                //否则,获取默认的编码列的值
                else{
                	value = rowset.getString(fkbmcol, "");
                }
                text.setText(value);

                //附属帮助列(HELPAFFIXCOL=A&B,F&C,G&D)
                String HELPAFFIXCOL = cmd.getExtAttriValue("F_SRCCONT", "=", ";", "HELPAFFIXCOL");
                if(HELPAFFIXCOL == null || HELPAFFIXCOL.trim().length() == 0){
                	HELPAFFIXCOL = cmd.getExtAttriValue("COL_OPT", "=", ";", "HELPAFFIXCOL");
                }
                if(HELPAFFIXCOL != null && HELPAFFIXCOL.trim().length() > 0){
                	HELPAFFIXCOL = HELPAFFIXCOL.trim();
                	java.util.Map map = ESPKeyValueUtil.getKeyValue("&", ",", HELPAFFIXCOL);
                	if(map != null && !map.isEmpty()){
                		ClientDataSet cds = model.getDataSet();
                        String colid = "";
                        Object colval = null;
                        String linkcol = "";
                        Iterator keys = map.keySet().iterator();
                        while(keys.hasNext()){
                        	colid = (String)keys.next();
                        	if(colid == null || colid.trim().length() == 0){
                        		continue;
                        	}
                        	colid = colid.trim();
                        	linkcol = (String)map.get(colid);
                        	colval = rowset.getObject(linkcol, null);
                        	DictUtil.setVariantValue(cds, colid, colval);
                        }
                	}
                }

                //afterHelp脚本
                try {
                    getScriptManager().setObject("HIC", hic);
                    getScriptManager().setObject("FKey", hic.getDCTID());
                    getScriptManager().setObject("ColumnMetaData", cmd);
                    getScriptManager().setObject("ActionEvent", e);
                    getScriptManager().setObject("DictModel", model);
                    getScriptManager().setObject("EFRowSet", rowset);
                    Object o = getScriptManager().runFunction(this, "afterHelp");
                } catch (Exception E) {
                    E.printStackTrace();
                } finally {
                    getScriptManager().setObject("HIC", null);
                    getScriptManager().setObject("FKey", null);
                    getScriptManager().setObject("ColumnMetaData", null);
                    getScriptManager().setObject("ActionEvent", null);
                    getScriptManager().setObject("DictModel", null);
                    getScriptManager().setObject("EFRowSet", null);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private String[] getHelpFColumn(DictMetaDataEx fkdme){
    	String[] cols = null;
    	String helpcolumn = cmd.getHelpFColumn().trim();
    	if(helpcolumn.length() == 0){
    		cols = new String[2];
    		cols[0] = fkdme.getDctBmCol();
    		cols[1] = fkdme.getDctMcCol();
    	}else{
    		cols = helpcolumn.split(",");
    	}
    	return cols;
    }
    
    /**
     *
     * @param dctid String
     */
    private void setFObjKey(String dctid) {
        if (model == null || cmd == null) return;
        Column pcolumn = model.getDataSet().getColumn(cmd.getColid());
//        Object painter = pcolumn.getItemPainter();
//        if (painter != null && painter instanceof FkeyCellRenderer) {
//            ((FkeyCellRenderer) painter).setDctKey(dctid);
//        }
    }

    /**
     * 脚本
     */
    protected ScriptObject scriptObject;
    protected ScriptManager scriptManager;

    /**
     *
     * @param scriptManager ScriptManager
     */
    public void initScript(ScriptManager scriptManager) {
    }

    /**
     *
     * @param scriptManager ScriptManager
     */
    public void finishScript(ScriptManager scriptManager) {
        getScriptManager().setObject("HIC", null);
        getScriptManager().setObject("ColumnMetaData", null);
        getScriptManager().setObject("ActionEvent", null);
        getScriptManager().setObject("DictModel", null);
        getScriptManager().setObject("FKey", null);
    }

    /**
     *
     * @return ScriptObject
     * @todo Implement this com.efounder.service.script.Scriptable method
     */
    public ScriptObject getScriptObject() {
//        return DictUtil.getScriptObject( (String) getScriptKey(),
//                                        getDCT_ID(),
//                                        getServerName());
    	return null;
    }

    /**
     *
     * @return String
     */
    private String getServerName() {
    	String colServerName = cmd.getExtAttriValue("COL_OPT", "=", ";", "ServerName");
        if(colServerName != null && colServerName.trim().length() > 0){
			return colServerName;
        }else if(cmd.getString("ServerName","") != null && cmd.getString("ServerName","").trim().length() > 0){
			return cmd.getString("ServerName","");
        }else if (model != null){
        	return model.getCdsParam().getServerName();
        }
        return "";
    }

    /**
     *
     * @return String
     */
    private String getDCT_ID() {
        String DCT_ID = "";
        if (model != null) DCT_ID = model.getDCT_ID();
        if (DCT_ID.endsWith("_APPLY")) return DCT_ID.substring(0, DCT_ID.indexOf("_APPLY"));
        return DCT_ID;
    }

    /**
     *
     * @return Object
     */
    public Object getScriptKey() {
        return "BIZDict";
    }

    /**
     *
     * @return Object
     */
    public Object getScriptInstance() {
        return null;
    }

    /**
     *
     * @return ScriptManager
     */
    public ScriptManager getScriptManager() {
        if (scriptManager == null)
            scriptManager = ScriptManager.getInstance();
        return scriptManager;
    }

    public static void main (String[] args) {
        String s = "@F_LBBH@";
        String[] t = s.split("@");
        System.out.println(t);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     *
     * @param e MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.text && e.getClickCount() == 1 &&
            e.getButton() == e.BUTTON3)
            actionPerformed(null);
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e MouseEvent
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     *
     * @param e MouseEvent
     */
    public void mouseExited(MouseEvent e) {
    }



    /**
     * 获得焦点
     */
	public void focusGained(FocusEvent e) {

	}

	/**
	 * 失去焦点
	 */
	public void focusLost(FocusEvent e) {
		if (e.getSource() == this.text){
    		String value = text.getText();
    		if(value == null || value.trim().length() == 0){
    			return;
    		}
			//附属帮助列(HELPAFFIXCOL=A&B,F&C,G&D)
    		String HELPAFFIXCOL = cmd.getExtAttriValue("F_SRCCONT", "=", ";", "HELPAFFIXCOL");
            if(HELPAFFIXCOL == null || HELPAFFIXCOL.trim().length() == 0){
            	HELPAFFIXCOL = cmd.getExtAttriValue("COL_OPT", "=", ";", "HELPAFFIXCOL");
            }
            if(HELPAFFIXCOL != null && HELPAFFIXCOL.trim().length() > 0){
            	HELPAFFIXCOL = HELPAFFIXCOL.trim();
            	java.util.Map map = ESPKeyValueUtil.getKeyValue("&", ",", HELPAFFIXCOL);
            	if(map != null && !map.isEmpty()){
            		EFRowSet rowset = getEFRowSetByValue(getServerName(), value);
            		ClientDataSet cds = model.getDataSet();
                    String colid = "";
                    Object colval = null;
                    String linkcol = "";
                    Iterator keys = map.keySet().iterator();
                    while(keys.hasNext()){
                    	colid = (String)keys.next();
                    	if(colid == null || colid.trim().length() == 0){
                    		continue;
                    	}
                    	colid = colid.trim();
                    	linkcol = (String)map.get(colid);
                    	colval = rowset.getObject(linkcol, null);
                    	DictUtil.setVariantValue(cds, colid, colval);
                    }
            	}
            }
		}
	}

	public EFRowSet getEFRowSetByValue(String serverName, String value) {
		if (value == null || value.trim().length() == 0)
			return null;
		//获取帮助中的取值字段
    	//如果指定了,则获取指定列的值
		DictMetadata    fkdmd = cmd.getFKMetaData();
		String  fkdctid = "", fkbmcol = "";
		if (fkdmd != null) {
            DictMetaDataEx fkdme = new DictMetaDataEx(fkdmd);
            fkdctid = fkdme.getDctid();
            fkbmcol = fkdme.getDctBmCol();
		}
        String HELPVALUECOL = cmd.getExtAttriValue("F_SRCCONT", "=", ";", "HELPVALUECOL");
        if(HELPVALUECOL == null || HELPVALUECOL.trim().length() == 0){
        	HELPVALUECOL = cmd.getExtAttriValue("COL_OPT", "=", ";", "HELPVALUECOL");
        }
        String where = "";
        if(HELPVALUECOL != null && HELPVALUECOL.trim().length() > 0){
        	where = HELPVALUECOL +"='"+ value +"'";
        }
        else{
        	where = fkbmcol+"='"+ value +"'";
        }
		JParamObject PO = JParamObject.Create();
		PO.setEAIServer(serverName);
		EFDataSet dataset = MDMDataUtil.getTableData(PO, fkdctid, where, true);
		if (dataset == null || dataset.getRowCount() ==0 ) {
			return null;
		}
		return dataset.getRowSet(0);
	}

    /**
     * Requests that this Component get the input focus, and that this Component's top-level ancestor become the
     * focused Window.
     *
     * @todo Implement this java.awt.Component method
     */
    public void requestFocus() {
        this.text.requestFocus();
    }
}
