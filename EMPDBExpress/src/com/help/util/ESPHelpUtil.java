package com.help.util;

import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.buffer.DictDataBuffer;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.util.ESPClientContext;
import com.efounder.builder.meta.MetaDataManager;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.meta.dctmodel.SYS_DICTS;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.efounder.dbc.swing.context.TreeInfoContext;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.mdm.EFMDMDataModel;
import com.efounder.mdm.MasterDataManager;
import com.efounder.pfc.dialog.JPDialog;
import com.efounder.plugin.PluginManager;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.builder.meta.bizmodel.BIZMetaDataManager;
import com.help.HelpInfoContext;
import com.help.dialog.HelpMainDialog;
import com.help.dialog.HelpTabPanel;
import com.help.plugin.IHelpAdapter;

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
public class ESPHelpUtil {
	public ESPHelpUtil() {
	}

	public static StubObject ShowHelpWithSo(String dctid) {
		EFRowSet ers = ShowHelp(dctid, null);
		if (ers != null) {
			StubObject so = new StubObject();
			so.setString("SB", "");
			so.getStubTable().putAll(ers.getDataMap());
			return so;
		}
		return null;
	}

	public static EFRowSet ShowHelp(String dctid) {
		return ShowHelp(dctid, null);
	}

	public static EFRowSet ShowHelp(String dctid, boolean bmul) {
		return ShowHelp(dctid, null, bmul);
	}

	public static EFRowSet ShowHelpWithFilter(String dctid, String filter) {
		return ShowHelp(dctid, null, filter);
	}

	public static EFRowSet ShowHelpWithFilter(String dctid, String filter, boolean bmul) {
		return ShowHelp(dctid, null, filter, bmul);
	}

	public static EFRowSet ShowHelp(String dctid, String selValue) {
		String mdlid = "";
		return ShowHelp(mdlid, dctid, selValue, null);
	}

	public static EFRowSet ShowHelp(String dctid, String selValue, boolean bmul) {
		String mdlid = "";
		return ShowHelp(mdlid, dctid, selValue, null, bmul);
	}

	public static EFRowSet ShowHelp(String dctid, String selValue, String exp) {
		String mdlid = "";
		return ShowHelp(mdlid, dctid, selValue, exp);
	}

	public static EFRowSet ShowHelp(String dctid, String selValue, String exp, boolean bmul) {
		String mdlid = "";
		return ShowHelp(mdlid, dctid, selValue, exp, bmul);
	}

	public static EFRowSet ShowHelp(String mdlid, String dctid, String selValue, String filterexp) {
		return ShowHelp(mdlid, dctid, selValue, filterexp, false);
	}

	public static EFRowSet ShowHelp(String mdlid, String dctid, String selValue, String filterexp, boolean bmul) {
		return ShowHelp(mdlid, dctid, null, selValue, filterexp, bmul);
	}

	public static EFRowSet ShowHelp(String mdlid, String dctid, String helpcols, String selValue, String filterexp, boolean bmul) {
		HelpInfoContext hic = new HelpInfoContext();
		hic.setMulSel(bmul);
		hic.setDCTID(dctid);
		hic.setSelValue(selValue);
		if (helpcols != null && helpcols.trim().length() > 0) {
			String[] cols = helpcols.trim().split(",");
			hic.setHelpColumn(cols);
		}
		if (filterexp != null)
			hic.setFilterExp(filterexp);
		if (mdlid != null)
			hic.setModelID(mdlid);
		return ShowHelp(hic);
	}

	public static EFRowSet ShowHelp(HelpInfoContext hic) {
		// 是否显示过滤条件对话框
		boolean showFilterDlg = "1".equals(hic.getObject("showFilterDlg", "0"));
		String serverName = hic.getServerName();
		String dctid = hic.getDCTID();
		// 添加过滤条件 add by zhangft 20110728
//		if (showFilterDlg) {
//			HelpFilterDlg helpFilterDlg = new HelpFilterDlg(EAI.EA.getMainWindow(), "过滤条件定义窗口", true, hic);
//			helpFilterDlg.setVisible(true);
//			if (helpFilterDlg.Result == helpFilterDlg.RESULT_OK) {
//				String where = hic.getWhereClause().trim().length() > 0 ? hic.getWhereClause() : "1=1";
//				hic.setWhere(where + helpFilterDlg.getFilterString());
//			} else {
//				return null;
//			}
//		}
		//
		Object parent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
		HelpMainDialog dhw = null;
		if (parent instanceof Frame)
			dhw = new HelpMainDialog((Frame) parent);
		else if (parent instanceof JDialog)
			dhw = new HelpMainDialog((JDialog) parent);
		else
			dhw = new HelpMainDialog(EAI.EA.getMainWindow());
		dhw.initHelpObject(hic);
		dhw.pack();
		dhw.setSize(700, 620);
		dhw.CenterWindow();
		dhw.setVisible(true);
		//
		try {
			// 利用上HIC中的ServerName modified by gengeng 2010-4-19
			JParamObject PO = JParamObject.Create();
			PO.setEAIServer(serverName);
			PO.SetValueByParamName("DCT_SUFFIX", hic.getString("DCT_SUFFIX", ""));// 增加表后缀 for daqing add by
														// wujf at 20120717
			ESPClientContext ctx = ESPClientContext.getInstance(PO);
			DCTMetaData dmd = (DCTMetaData) MetaDataManager.getDCTDataManager().getMetaData(ctx, dctid);
			dmd.putObject(dmd.getObjID() + "_HLPTREE", null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//
		if (dhw.Result == JPDialog.RESULT_OK) {
			Object oo = dhw.getResult();
			if (oo instanceof EFRowSet)
				return (EFRowSet) oo;
			EFRowSet[] erss = (EFRowSet[]) oo;
			if (hic.isMulSel()) {
				EFRowSet ers = EFRowSet.getInstance();
				EFDataSet eds = EFDataSet.getInstance("");
				for (int i = 0; i < erss.length; i++)
					eds.addRowSet(erss[i]);
				ers.setChildDataSet(eds);
				return ers;
			} else {
				return erss[0];
			}
		}
		return null;
	}

	public static void loadHelpTabPlugin(JTabbedPane jtp, DCTMetaData dmd,
			HelpInfoContext hic) { // IVoucherCheck.SAVE_CHECK
		boolean isFjcx = hic.getFJCX();
		List mdlist = PluginManager.loadPlugins("helptab", true);
		for (int i = 0; i < mdlist.size(); i++) {
			StubObject so = (StubObject) mdlist.get(i);
			String id = (String) so.getID();
			// 分级帮助
			// if(isFjcx && "comm".equals(id)) continue;
			if (isFjcx && !"fjcx".equals(id))
				continue;
			if (!isFjcx && "fjcx".equals(id))
				continue;
			IHelpAdapter o = (IHelpAdapter) so.getPluginObject();
			HelpTabPanel htp = (HelpTabPanel) o.createHelpTabPanel(dmd, hic);
			if (htp != null) {
				String icon = so.getString("icon", "");
				Icon ii = ExplorerIcons.getExplorerIcon(icon);
				jtp.addTab(so.getCaption(), ii, htp);
				htp.getExplorePanel().getTypeBox().addActionListener(htp);
				htp.getContentPanel().getContentTable().addMouseListener(o);
			}
		}
	}

	public static EFDataSet getHelpData(JParamObject PO,
			DCTMetaData dctMetaData, boolean b) {
		EFDataSet o = (EFDataSet) DictDataBuffer.getDefault().getDataItem(
				DictDataBuffer.DCTDATASET, dctMetaData.getObjID());
		if (o != null)
			return (EFDataSet) o;
		EFMDMDataModel hdm = loadHelpData(PO, dctMetaData, b);
		if (hdm == null)
			return null;
		return hdm.getDataSet(dctMetaData.getObjID());
	}

	/**
	 * 
	 * @param dctMetaData
	 *            DCTMetaData
	 * @return HelpDataModel
	 */
	public static EFMDMDataModel loadHelpData(JParamObject PO,
			DCTMetaData dctMetaData, boolean b) {
		return loadHelpData(PO, dctMetaData, null, null, b);
	}

	/**
	 * 
	 * @param dctMetaData
	 *            DCTMetaData
	 * @param RLGL_ID
	 *            String
	 * @param F_KEY
	 *            String
	 * @return HelpDataModel
	 */
	public static EFMDMDataModel loadHelpData(JParamObject PO,
			DCTMetaData dctMetaData, String RLGL_ID, String F_KEY, boolean b) {
		return loadHelpData(PO, dctMetaData.getObjID(), RLGL_ID, F_KEY, b);
	}

	/**
	 * @param dmd
	 *            DCTMetaData
	 * @param dctid
	 *            String
	 * @param rlglid
	 *            String
	 * @param fkey
	 *            String
	 * @return HelpDataModel
	 */
	public static EFMDMDataModel loadHelpData(JParamObject PO, String dctid,
			String rlglid, String fkey, boolean brefrensh) {
		if (PO == null)
			PO = JParamObject.Create();
		String mdlid = PO.GetValueByParamName("MDL_ID");
		try {
			EFMDMDataModel efm = MasterDataManager.getDefault()
					.getMDMDataModel(PO, mdlid, dctid, fkey, rlglid, null,
							brefrensh);
			return efm;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	protected static void setRlglHelpData(DOMetaData dmd, EFDataSet eds) {
//		String bm1;
//		if (eds == null)
//			return;
//		EFDataSet rlgleds = EFDataSet.getInstance(eds.getTableName());
//		rlgleds.setPrimeKey(new String[] { SYS_RLGL._MAIN_BM_ });
//		int count = eds.getRowCount();
//		for (int i = 0; i < count; i++) {
//			EFRowSet row = eds.getRowSet(i);
//			bm1 = row.getString(SYS_RLGL._MAIN_BM_, "");
//			EFRowSet ers = (EFRowSet) rlgleds.getRowSet(new String[] { bm1 });
//			if (ers == null) {
//				ers = EFRowSet.getInstance();
//				ers.putString(SYS_RLGL._MAIN_BM_, bm1);
//				rlgleds.addRowSet(ers);
//				EFDataSet mxeds = EFDataSet.getInstance(eds.getTableName());
//				ers.setDataSet(eds.getTableName(), mxeds);
//			}
//			ers.getDataSet(eds.getTableName()).insertRowSet(row);
//		}
//		DictDataBuffer.getDefault().addDataItem(DictDataBuffer.DCTDATASET,
//				dmd.getObjID(), rlgleds);
	}

	protected static void setDataSetToBuffer(DCTMetaData dmd, EFDataSet eds) {
		if (eds == null)
			return;
		String dctid = dmd.getObjID();
		DictDataBuffer.getDefault().addDataItem(DictDataBuffer.DCTDATASET,
				dctid, eds);
	}

	public static TreeInfoContext getTreeInfoFormDO(DCTMetaData dmd) {
		TreeInfoContext hic = new TreeInfoContext();
		String bh = dmd.getString(SYS_DICTS._DCT_BMCOLID_, "");
		String mc = dmd.getString(SYS_DICTS._DCT_MCCOLID_, "");
		String js = dmd.getString(SYS_DICTS._DCT_JSCOLID_, "");
		String mx = dmd.getString(SYS_DICTS._DCT_MXCOLID_, "");
		String stru = dmd.getString(SYS_DICTS._DCT_BMSTRU_, "");
		hic.setCaption(dmd.getString("DCT_MC", ""));
		hic.setBHColumn(bh);
		hic.setMCColumn(mc);
		hic.setDCTID(dmd.getObjID());
		hic.setJSColumn(js);
		hic.setMXColumn(mx);
		hic.setBMStru(stru);
		return hic;
	}

	static public String getSelfHlpFile(String dctid, String type) {
		JParamObject PO = JParamObject.Create();
		String UserName = PO.GetValueByEnvName("UserName");
		String Fileseparator = System.getProperty("file.separator");
		// String filename = System.getProperty("user.home") + Fileseparator +
		// UserName + "_"+dctid+".dat";
		String filename = System.getProperty("user.home") + Fileseparator
				+ UserName + "_" + dctid + "";
		if (type != null) {
			filename += "_" + type;
		}
		filename += ".dat";
		return filename;
	}

	// add by lchong
	static public void save(String dctid, List list) {
		save(dctid, null, list);
	}

	static public void save(String dctid, String type, List list) {
		try {
			String filename = getSelfHlpFile(dctid, type);
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(list);
			fos.close();
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// add by lchong
	public static List load(String dctid) {
		return load(dctid, null);
	}

	// add end

	public static List load(String dctid, String type) {
		try {
			List valueList = null;
			// List valueList = (List) DictDataBuffer.getDefault().getDataItem(
			// DictDataBuffer.DCTDATASET, dctid + "_SELF");
			if (type != null) {
				valueList = (List) DictDataBuffer.getDefault()
						.getDataItem(DictDataBuffer.DCTDATASET,
								dctid + "_" + type + "_SELF");
			} else {
				valueList = (List) DictDataBuffer.getDefault().getDataItem(
						DictDataBuffer.DCTDATASET, dctid + "_SELF");
			}

			if (valueList != null)
				return valueList;

			String filename = getSelfHlpFile(dctid, type);
			File ConfigFile = new File(filename);
			if (ConfigFile.exists()) {
				FileInputStream fis = new FileInputStream(ConfigFile);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object values = ois.readObject();
				valueList = (java.util.List) values;
				fis.close();
				ois.close();
			}
			if (valueList == null)
				valueList = new ArrayList();
			// DictDataBuffer.getDefault().addDataItem(DictDataBuffer.
			// DCTDATASET, dctid + "_SELF", valueList);
			if (type != null) {
				DictDataBuffer.getDefault().addDataItem(
						DictDataBuffer.DCTDATASET,
						dctid + "_" + type + "_SELF", valueList);
			} else {
				DictDataBuffer.getDefault().addDataItem(
						DictDataBuffer.DCTDATASET, dctid + "_SELF", valueList);
			}
			return valueList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param PO
	 *            JParamObject
	 * @param MDL_ID
	 *            String
	 * @return String
	 */
	public static String getZZJG_DCT(JParamObject PO, String MDL_ID) {
		try {
			ESPClientContext t = ESPClientContext.getInstance(PO);
			BIZMetaData meta = (BIZMetaData) BIZMetaDataManager
					.getBIZMetaDataManager().getMetaData(t, MDL_ID);
			return meta.getUNIT_DCT();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param objId
	 * @return
	 */
	public static StubObject getFeatureHelp(String objId) {
		List feature = PackageStub.getContentVector("HELP_FEATURE");
		for (int i = 0; feature != null && i < feature.size(); i++) {
			StubObject so = (StubObject) feature.get(i);
			String id = so.getString("id", "");
			if (objId.equals(id)) {
				return so;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param objId
	 * @return
	 */
	public static String getFeatureRlgl(String objId) {
		String rlgl = "";

		StubObject so = getFeatureHelp(objId);
		if (so != null) {
			String index = so.getString("showIndex", "");
			if ("0".equals(index)) {
				rlgl = so.getString("rlgl", "");
			}
		}

		return rlgl;
	}

}
