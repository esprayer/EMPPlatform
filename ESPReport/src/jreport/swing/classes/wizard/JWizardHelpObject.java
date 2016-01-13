package jreport.swing.classes.wizard;

import java.util.*;

import javax.swing.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.dctmodel.*;
import com.pansoft.help.*;
import com.pansoft.help.util.*;
import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jframework.foundation.classes.*;
import jreport.foundation.classes.*;
import jreport.swing.classes.*;
import jreport.swing.classes.wizard.help.*;
import jreport.swing.classes.wizard.imp.*;
import jreport.value.*;
import jreportfunction.extend.*;
import com.efounder.builder.meta.fctmodel.FCTMetaData;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.dctbuilder.util.DictUtil;
import com.pansoft.esp.fmis.client.fwkview.FMISContentWindow;
import jbof.gui.window.classes.JBOFChildWindow;



/**
 * <p>Title: </p>
 * <p>Description: �����򵼰���</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//���\uFFFD: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JWizardHelpObject {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.Language");

    public JFunctionWizardObject WizardObject;
    //------------------------------------------------------------------------------------------------
    //����:
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public JWizardHelpObject() {
    }

    //------------------------------------------------------------------------------------------------
    //����: ������
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpYear(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        JParamObject PO = null;
        PO = JParamObject.Create();
        int currentReportYear = Integer.parseInt(WizardObject.ReportModel.
                                                 BBZD_DATE.
                                                 substring(0, 4));
        JHelpYearInfoDlg dlg = new JHelpYearInfoDlg(WizardObject.MainFrame,
            res.getString("String_411") +
            currentReportYear, true,
            currentReportYear,
            currentReportYear - 100);
        dlg.CenterWindow();
        dlg.Show();
        if (dlg.getOption() == JHelpYearInfoDlg.OK_OPTION) {
            return new Integer(dlg.getYear());
        }
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //����: ����ڼ�\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpMonth(JLimitObjectStub LOS, JFunctionStub FS,
                            JCustomTextField CTF, Object CO) {
        JParamObject PO = null;
        PO = JParamObject.Create();
        int currentReportMonth = Integer.parseInt(WizardObject.ReportModel.
                                                  BBZD_DATE.substring(4, 6));
        JHelpMonthInfoDlg dlg = new JHelpMonthInfoDlg(WizardObject.MainFrame,
            res.getString("String_412") +
            currentReportMonth, true,
            currentReportMonth);
        dlg.CenterWindow();
        dlg.Show();
        if (dlg.getOption() == JHelpMonthInfoDlg.OK_OPTION) {
            return new Integer(dlg.getMonth());
        }
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //����: ��������
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpZRZX(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  return HelpBH("ACZRZX","");
/*
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("helpNo", "");
        PO.SetValueByParamName("sYear", PO.GetValueByEnvName("LoginFaYear"));
        PO.SetValueByParamName("sTable", "ACZRZX");
        PO.SetValueByParamName("sBhzd", "F_CODE");
        PO.SetValueByParamName("sMczd", "F_STMC");
        PO.SetValueByParamName("sJszd", "F_JS");
        PO.SetValueByParamName("sMxzd", "F_MX");
        PO.SetValueByParamName("sSjzd", "");
        PO.SetValueByParamName("sNmzd", "");
        PO.SetValueByParamName("BMSTRU", "@ZW_ZRSTRU");
        PO.SetValueByParamName("sEveryWhere", "");
        PO.SetValueByParamName("sParameter", "");
        PO.SetValueByParamName("sAllorClass", "1");
        PO.SetValueByParamName("JS", "1");
        PO.SetValueByParamName("SJBH", "");
        PO.SetValueByParamName("SJMC", "");
//        PO.SetValueByParamName("psqxbh", "ZWZRZX");
//        PO.SetValueByParamName("pibzw", "1"); // 1:��ѯ; 2:ҵ��

        String[] helpData = com.pansoft.help.cnttier.JHelp.showHelpDialog(JActiveDComDM.MainApplication.MainWindow,
            res.getString("String_445"), PO);

        String CenterNO = null;
        if (helpData != null) {
            CenterNO = helpData[0];
        }
        return CenterNO;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: �����\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpBKBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  return HelpBH("ACBKZD","");
/*
	  try {
		  DCTMetaData meta = (DCTMetaData) MetaDataManager.getDCTDataManager().
			  getMetaData("ACBKZD");
		  HelpInfoContext hic = new HelpInfoContext();
		  hic.setDCTID("ACBKZD");
		  EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);
		  if (rowset != null) {
			  return (rowset.getString(meta.getDCT_BMCOLID(), ""));
		  }
	  } catch (Exception ex) {
	  }
	  return null;
*/
/*
	  String[] mess = new String[2];
	  try {
		JParamObject PO = JParamObject.Create();
		ESPClientContext ctx = ESPClientContext.getInstance(PO.getEAIParamObject()); //�þ�PO��ȡ��PO
		DCTMetaData meta = (DCTMetaData) DCTMetaDataManager.getDCTMetaDataManager().getMetaData(ctx, "ACBKZD");
		HelpInfoContext hic = new HelpInfoContext();
                if (meta != null) {
		  hic.setDCTID("ACBKZD");
		  hic.setBHColumn(meta.getDCT_BMCOLID());
		  hic.setMCColumn(meta.getDCT_MCCOLID());
		  hic.setJSColumn(meta.getDCT_JSCOLID());
		  hic.setMXColumn(meta.getDCT_MXCOLID());
                  hic.setBMStru(meta.getDCT_BMSTRU());
		}
		hic.setModelID("");
		hic.setBufdata(true);

		EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);
		if (rowset != null) {
		  mess[0] = rowset.getString(hic.getBHColumn(), "");
		  mess[1] = rowset.getString(hic.getMCColumn(), "");
		}
	} catch (Exception ee) {
		  ee.printStackTrace();
	}
    return mess[0];
/*
//        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
//            InvokeObjectMethod("HelpObject", "HelpBkzdInfomation");
          String helpData = this.bkzd_Help().getString("F_BKBH","");
        String CenterNO = null;
        if (helpData != null) {
             CenterNO = helpData;
        }
        return CenterNO;
 */
    }

	//------------------------------------------------------------------------------------------------
	//����: �����\uFFFD
	//���\uFFFD: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//------------------------------------------------------------------------------------------------
	public Object HelpDCOL(JLimitObjectStub LOS, JFunctionStub FS,
						   JCustomTextField CTF, Object CO) {
		String sWhere = "";
		String YEAR = FS.GetParamValueCtrl("YEAR").getText().trim();
		String bclx   = FS.GetParamValueCtrl("BCLX").getText().trim();
		String bcywlx = FS.GetParamValueCtrl("BCYWLX").getText().trim();
		try {
			HelpInfoContext hic = new HelpInfoContext();
			hic.setModelID("ACBCModel");
			hic.setDCTID("SYS_MDL_DIMSET_ACBCModel");
			if ( bcywlx.equals("") || bcywlx == null) { //���ֵ�
				 sWhere = " SYS_MDL_DIMSET_ACBCMODEL.F_YEAR = '" +
					 getAbsoluteYear(YEAR) + "'  and "
					 + " SYS_MDL_DIMSET_ACBCMODEL.FCT_ID = 'BCXMZD' and SYS_MDL_DIMSET_ACBCMODEL.CST_ID in ( "
					 + " select CST_ID from SYS_MDL_KEYSET_ACBCModel where SYS_MDL_KEYSET_ACBCModel.CTN_ID = 'BCXMZD' "
					 + " AND SYS_MDL_KEYSET_ACBCModel.KEY_BM LIKE  '" +bclx + "%')";
				 hic.setWhere(sWhere);
				 JParamObject PO = JParamObject.Create();
				 EFDataSet data = DictUtil.getHelpDataSet(hic,
					 PO.getEAIParamObject());
				 hic.setDataObject(data);
				 hic.setBHColumn("DIM_ID");
				 hic.setMCColumn("CTN_DCT_MC");
				 EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);
				 if (rowset != null) {
					 return ("BCXMZD." + rowset.getString("DIM_ID", ""));
				 }
			} else {                                     //������
				sWhere = " SYS_MDL_DIMSET_ACBCMODEL.F_YEAR = '" +
					getAbsoluteYear(YEAR) + "'  and "
					+ " SYS_MDL_DIMSET_ACBCMODEL.FCT_ID = 'BCITEM' and SYS_MDL_DIMSET_ACBCMODEL.CST_ID in ( "
					+ " select CST_ID from SYS_MDL_KEYSET_ACBCModel where SYS_MDL_KEYSET_ACBCModel.CTN_ID = 'BCITEM' "
					+ " AND SYS_MDL_KEYSET_ACBCModel.KEY_BM LIKE  '" +
					bclx +
					"%' AND SYS_MDL_KEYSET_ACBCModel.DEP_DCT_BM1 like '" +
					bcywlx + "%')";
				hic.setWhere(sWhere);
				JParamObject PO = JParamObject.Create();
				EFDataSet data = DictUtil.getHelpDataSet(hic,
					PO.getEAIParamObject());
				hic.setDataObject(data);
				hic.setBHColumn("DIM_ID");
				hic.setMCColumn("CTN_DCT_MC");
				EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);
				if (rowset != null) {
					return ("BCITEM." + rowset.getString("DIM_ID", ""));
				}
			}
		}
		catch (Exception ex) {
		}
		return null;
    }

	//------------------------------------------------------------------------------------------------
	//����: �����\uFFFD
	//���\uFFFD: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//------------------------------------------------------------------------------------------------
	public Object HelpBBZBDCOL(JLimitObjectStub LOS, JFunctionStub FS,
						   JCustomTextField CTF, Object CO) {
		String sWhere = "";
		String YEAR = FS.GetParamValueCtrl("YEAR").getText().trim();
		String ZBLB   = FS.GetParamValueCtrl("ZBLB").getText().trim();
		try {
			HelpInfoContext hic = new HelpInfoContext();
			hic.setModelID("BBZBModel");
			hic.setDCTID("SYS_MDL_DIMSET_BBZBModel");
			if ( !ZBLB.equals("") &&  ZBLB != null) { //������
				 sWhere = " SYS_MDL_DIMSET_BBZBMODEL.F_YEAR = '" +
					 getAbsoluteYear(YEAR) + "'  and "
					 + " SYS_MDL_DIMSET_BBZBMODEL.FCT_ID = 'ZBMDSJ' and SYS_MDL_DIMSET_BBZBMODEL.CST_ID in ( "
					 + " select CST_ID from SYS_MDL_KEYSET_BBZBModel where SYS_MDL_KEYSET_BBZBModel.CTN_ID = 'ZBITME' "
					 + " AND SYS_MDL_KEYSET_BBZBModel.F_YEAR ='"+getAbsoluteYear(YEAR)+"' AND  SYS_MDL_KEYSET_BBZBModel.KEY_BM LIKE  '" +ZBLB + "%')";
				 hic.setWhere(sWhere);
				 JParamObject PO = JParamObject.Create();
				 EFDataSet data = DictUtil.getHelpDataSet(hic,
					 PO.getEAIParamObject());
				 hic.setDataObject(data);
				 hic.setBHColumn("DIM_ID");
				 hic.setMCColumn("CTN_DCT_MC");
				 EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);
				 if (rowset != null) {
					 return rowset.getString("DIM_ID", "");
				 }
			}
		}
		catch (Exception ex) {
		}
		return null;
	}



	public Object HelpBH(String TabName) {
		return HelpBH(TabName,"","");
	}
	public Object HelpBH(String TabName,String sFilter) {
		return HelpBH(TabName,sFilter,"");
	}

	public Object HelpBH(String TabName,String sFilter,String sWhere) {
		try {
			DCTMetaData meta = (DCTMetaData) MetaDataManager.getDCTDataManager().
				getMetaData(TabName);
			HelpInfoContext hic = new HelpInfoContext();
			if ( TabName.startsWith("RPT") || TabName.startsWith("HZ") ) {
				hic.setModelID("ACPZModel");
			} else {
				hic.setModelID("ACPZModel");
			}
			hic.setDCTID(TabName);
			hic.setFilterExp(sFilter);
			hic.setWhere(sWhere);
			EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);
			if (rowset != null) {
			  return (rowset.getString(meta.getDCT_BMCOLID(), ""));
			}
		}
			catch (Exception ex) {
		}
		return null;
    }

    /**
     * 板块帮助-公共
     * @param e MouseEvent
     */
//    public EFRowSet bkzd_Help(Component component, String year,
//                                     String where) {
      public EFRowSet bkzd_Help() {
      EFRowSet rowset = null;
      try {
        JParamObject PO = (JParamObject) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("EnvironmentObject", "CreateParamObjectDOM");
        HelpInfoContext hic = new HelpInfoContext();
		hic.setModelID("ACPZModel");
        hic.setDCTID("ACBKZD");
        hic.setCaption(res.getString("String_17"));
        hic.setBHColumn("F_BKBH");
        hic.setMCColumn("F_BKMC");
        hic.setBMStru(PO.GetValueByEnvName("ZW_BKSTRU"));
        hic.setJSColumn("F_JS");
        hic.setMXColumn("F_MX");
        hic.setQXBH("ZWBKZD");
        hic.setQXBZW("1");
       // hic.setWhere(where);
        rowset = ESPHelpUtil.ShowHelp(hic);
      } catch (Exception ee) {
        ee.printStackTrace();
      }
      return rowset;
  }
  public Object HelpKMGP(JLimitObjectStub LOS, JFunctionStub FS,
						 JCustomTextField CTF, Object CO) {
	return HelpBH("PSGPKM","");
/*
	try {
		DCTMetaData meta = (DCTMetaData) MetaDataManager.getDCTDataManager().
			getMetaData("PSGPKM");
		HelpInfoContext hic = new HelpInfoContext();
		hic.setDCTID("PSGPKM");
		EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);
		if (rowset != null) {
			return (rowset.getString(meta.getDCT_BMCOLID(), ""));
		}
	} catch (Exception ex) {
	}
	return null;
*/
  }

    //------------------------------------------------------------------------------------------------
    //����: ��Ŀ���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpKMBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  return HelpBH("LSKMZD","");
/*
//        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
//            InvokeObjectMethod("HelpObject", "HelpKmInfomation");
       String helpData = this.kmbh_Help().getString("F_KMBH","");
        String CenterNO = null;
        if (helpData != null) {
            CenterNO = helpData;
        }
        return CenterNO;
 */
    }
    /**
      * ��Ŀ����
      * @param e MouseEvent
      */
//     public static EFRowSet kmbh_Help(Component component, String year,
//                                      String where) {
       public EFRowSet kmbh_Help() {
//       if (!where.trim().equals("") &&
//           !where.toUpperCase().trim().startsWith("AND")) {
//         where = " and " + where;
//       }
       JParamObject PO = (JParamObject) JActiveDComDM.DataActiveFramework.
           InvokeObjectMethod("EnvironmentObject", "CreateParamObjectDOM");
       HelpInfoContext hic = new HelpInfoContext();
	   hic.setModelID("ACPZModel");
       hic.setDCTID("LSKMZD");
       hic.setCaption("��Ŀ����");
       hic.setBHColumn("F_KMBH");
       hic.setMCColumn("F_KMMC");
       hic.setBMStru(PO.GetValueByEnvName("ZW_KMSTRU"));
       hic.setJSColumn("F_JS");
       hic.setMXColumn("F_MX");
       hic.setQXBH("ZWKMQX");
       hic.setQXBZW("1");
 //      hic.setWhere(" F_KJND = '" + year + "' " + where);
       EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);
       return rowset;
  }
    //------------------------------------------------------------------------------------------------
    //����: ��Ŀ���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpZCLB(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCLB();
    }
    //------------------------------------------------------------------------------------------------
    public Object HelpZCBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCBH();
    }
    //------------------------------------------------------------------------------------------------
    public Object HelpZCSYDW(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCSYDW();
    }
    //------------------------------------------------------------------------------------------------
    public Object HelpZCCQGS(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCCQGS();
    }
    //------------------------------------------------------------------------------------------------
    public Object HelpZCJJYT(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCJJYT();
    }

    //------------------------------------------------------------------------------------------------
    public Object HelpZCLY(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCLY();
    }
    //------------------------------------------------------------------------------------------------
    public Object HelpZCSYQK(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCSYQK();
    }
    //------------------------------------------------------------------------------------------------
    public Object HelpZCJSZT(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCJSZT();
    }
    //------------------------------------------------------------------------------------------------
    public Object HelpZCJSYY(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCJSYY();
    }
    //------------------------------------------------------------------------------------------------
    public Object HelpZCJSFS(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCJSFS();
    }
    //------------------------------------------------------------------------------------------------
    public Object HelpZCZJFF(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZCZJFF();
    }
    //------------------------------------------------------------------------------------------------
    //����: ������
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpBBBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String YEAR = FS.GetParamValueCtrl("YEAR").getText().trim();
        String MONTH = FS.GetParamValueCtrl("MONTH").getText().trim();
        String BBZTBH;
        try {
            BBZTBH = FS.GetParamValueCtrl("ZTBH").getText().trim();
        } catch (Exception e) {
            BBZTBH = "";
        }
        String BBZD_DATE = getAbsoluteYear(YEAR).concat(getAbsoluteMonth(MONTH));

        JParamObject PO = null;
        PO = JParamObject.Create();
//		return HelpBH(JREPORT.getRTNbyPO("BBZD",PO)," BBZD_DATE like '"+ BBZD_DATE + "'");
        //return HelpBH(JREPORT.getRTNbyPO("BBZD",PO)," BBZD_DATE.equals(\"" + BBZD_DATE + "\")");
/*
        if (BBZTBH != null && BBZTBH.trim().length() > 0)
            PO.SetValueByParamName("BBZTBH", BBZTBH);
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpBbbhInfomation", PO,
                               BBZD_DATE);
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
*/
        try {
			DCTMetaData meta = (DCTMetaData) MetaDataManager.getDCTDataManager().
				getMetaData(JREPORT.getRTNbyPO("BBZD",PO));
			HelpInfoContext hic = new HelpInfoContext();
			hic.setDCTID(JREPORT.getRTNbyPO("BBZD",PO));
			hic.setWhere(" BBZD_BH='"+BBZD_DATE+"' ");
			EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);
			if (rowset != null) {
				return (rowset.getString(meta.getDCT_BMCOLID(), ""));
			}
		} catch (Exception ex) {
		}
		return null;
    }

    //------------------------------------------------------------------------------------------------
    //����: ������Χ
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpBBREF(JLimitObjectStub LOS, JFunctionStub FS,
                            JCustomTextField CTF, Object CO) {
        JParamObject PO = null;
        JResponseObject RO = null;
        JReportBook reportBook;
        //��ǰ�򿪱������\uFFFD
        String MC = WizardObject.ReportModel.BBZD_MC;
        String YEAR = WizardObject.ReportModel.BBZD_DATE.substring(0, 4);
        String MONTH = WizardObject.ReportModel.BBZD_DATE.substring(4, 6);
        String BH = WizardObject.ReportModel.BBZD_BH;
        //�������򵼱������\uFFFD
        String BBZD_YEAR = this.getAbsoluteYear(FS.GetParamValueCtrl("YEAR").
                                                getText().trim());
        String BBZD_MONTH = this.getAbsoluteMonth(FS.GetParamValueCtrl("MONTH").
                                                  getText().trim());
        String BBZD_BH = FS.GetParamValueCtrl("BBBH").getText().trim();

        String BBZTBH;
        try {
            BBZTBH = FS.GetParamValueCtrl("ZTBH").getText().trim();
        } catch (Exception e) {
            BBZTBH = "";
        }
        if ( (!YEAR.equals(BBZD_YEAR)) || (!MONTH.equals(BBZD_MONTH)) ||
            (!BH.equals(BBZD_BH))) {
            try {
                PO = JParamObject.Create();
                PO.SetValueByParamName("BBZD_DATE", BBZD_YEAR.concat(BBZD_MONTH));
                PO.SetValueByParamName("BBZD_BH", BBZD_BH);
                if (BBZTBH != null && BBZTBH.trim().length() > 0)
                    PO.SetValueByParamName("BBZTBH", BBZTBH);

                reportBook = openReportBook(PO); //open the book
                JHelpReportRefDlg dlg = new JHelpReportRefDlg(
                    WizardObject.MainFrame,
                    res.getString("String_458") +
                    ( (JReportModel) reportBook.ReportView.getModel()).
                    BBZD_MC + "-" +
                    BBZD_YEAR + BBZD_MONTH,
                    true,
                    reportBook);
                dlg.CenterWindow();
                dlg.Show();
                if (dlg.getReportRef().length() > 0) {
                    return dlg.getReportRef();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return LOS.text;
    }

    private JReportBook openReportBook(JParamObject PO) {
        JReportBook reportBook = null;
        JResponseObject RO = null;
        JReportObjectEntity ROE = null;
        PO.SetValueByParamName("ModelClassName",
                               "jreport.swing.classes.JReportModel");
        RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
            InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "OpenReportObject", PO);
        if (RO != null && RO.ErrorCode == 0) {
            ROE = (JReportObjectEntity) RO.ResponseObject;
            reportBook = new JReportBook();
            reportBook.ReportView.OpenReport(ROE, PO);
        }
        return reportBook;
    }
	public String GetRealRptSvr() {
		String sSvrName = "";
		Object childwindow=com.efounder.eai.ide.EnterpriseExplorer.ContentView.getActiveWindow();
		if(childwindow instanceof FMISContentWindow) {
			childwindow = ( (FMISContentWindow) childwindow).getFMISComp();
			if ( childwindow instanceof JBOFChildWindow ) {
				sSvrName = ((JBOFChildWindow)childwindow).getPageBaseUrl();
			}
		}
		if ( !sSvrName.equals("") && sSvrName != null ) {
			return sSvrName + ".";
		} else {
			sSvrName = "";
		}
		return sSvrName;
	}

    //------------------------------------------------------------------------------------------------
    //����: ȡ�����\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpDOBJ(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        //SAP�������ݶ�����Ҫ��̬����ݿ���ȡ��\uFFFD
        //add by gengeng 2007-3-28
        if (FS.id.indexOf("SAPBB") >= 0) {
            String func = FS.GetParamValueCtrl("SAPFUNCFL").getText();
            if (func == null || func.trim().length() == 0)
                return null;
            else
                return JReportHelpObject.HelpSAPDataObj(func);
        } else {
            JHelpDOBJInfoDlg Dlg = new JHelpDOBJInfoDlg(WizardObject.MainFrame,
                res.getString("String_465"), true);
            Dlg.FS = FS;
            Dlg.WizardObject = WizardObject;
            JDataObjectStub DOS;
            Dlg.CenterWindow();
            Dlg.Show();
            if (Dlg.Result == Dlg.RESULT_OK) {
                DOS = Dlg.DOS;
                if (DOS != null) {
                    return HelpSpDOBJ(DOS.id,LOS,FS,CTF,CO);
                }
            }
        }
        return null;
    }
    //���⴦��
    public Object HelpSpDOBJ(String DOBJ,JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        if ( DOBJ.equals("SXMC") ) {

            String gclb = FS.GetParamValueCtrl("ZYGCLB").getText().trim();
            if (gclb.equals("") || gclb.equals("0") || gclb == null) {
                gclb = JReportHelpObject.HelpZYGCLB();
            }
            String sxlb = JReportHelpObject.HelpZYSXLB(gclb);
            return DOBJ+sxlb;
        }
        return DOBJ;
    }


    //------------------------------------------------------------------------------------------------
    //����: �������\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpCOBJ(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JHelpCObject.Help(LOS, FS, CTF, CO);
    }


    public Object HelpSOBJ(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return JHelpCObject.HelpSOBJ(LOS, FS, CTF, CO);
    }

    //------------------------------------------------------------------------------------------------
    //����: ��Ŀ���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpXMBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
		String xmlb = FS.GetParamValueCtrl("XMLB").getText().trim();
		return HelpBH("LSHSZD","F_HSLB.equals(\"" + xmlb + "\"");
//        JParamObject PO1 = null;
//        PO1 = JParamObject.Create();
//        PO1.SetValueByParamName("fieldNames", "F_HSLB");
//        PO1.SetValueByParamName("tableName", "LSZRHS");
//        PO1.SetValueByParamName("everyWhere",
//                                "F_KMBH='" + code + "'");
//        JResponseObject RO;
//        RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
//                InvokeObjectMethod("HelpObject", "getTableSpecifyFieldsValue",
//                                   PO1, "1");
//        String[] helpData1 = (String[]) RO.ResponseObject;
//        if (helpData1 != null) {
//            PO1 = new JParamObject(helpData1[0]);
//            flbh = PO1.GetValueByParamName("F_HSLB");
//        }
//        JParamObject PO = null;
/*
        String xmlb = FS.GetParamValueCtrl("XMLB").getText().trim();
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("EXTVALUE", xmlb);
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpZxhsInfomation", PO);
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: ��λ���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpDWBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  JParamObject PO = null;
	  PO = JParamObject.Create();
	  return HelpBH(JREPORT.getRTNbyPO("DWZD",PO),"");
/*
        String BBZTBH;
        try {
            BBZTBH = FS.GetParamValueCtrl("ZTBH").getText().trim();
        } catch (Exception e) {
            BBZTBH = "";
        }
        JParamObject PO = null;
        PO = JParamObject.Create();
        if (BBZTBH != null && BBZTBH.trim().length() > 0)
            PO.SetValueByParamName("BBZTBH", BBZTBH);

        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpDwbhInfomation", PO);
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: �����\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpLBBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
      JParamObject PO = null;
      PO = JParamObject.Create();
      return HelpBH(JREPORT.getRTNbyPO("LBZD",PO),"");
/*
        String BBZTBH;
        try {
            BBZTBH = FS.GetParamValueCtrl("ZTBH").getText().trim();
        } catch (Exception e) {
            BBZTBH = "";
        }
        JParamObject PO = null;
        PO = JParamObject.Create();
        if (BBZTBH != null && BBZTBH.trim().length() > 0)
            PO.SetValueByParamName("BBZTBH", BBZTBH);
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpLbbhInfomation", PO);
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
 */
    }

    public String getAbsoluteYear(String year) {
        JParamObject PO = null;
        PO = JParamObject.Create();
        int loginYear = Integer.parseInt(PO.GetValueByEnvName("LoginDate").
                                         substring(0, 4));
        String sYear = year;
        if (year != null) {
            if (year.length() == 4) {
                sYear = year;
            } else if (year.length() < 4) {
                if (year.trim().length() == 0) {
                    year = "0";
                }
                sYear = Integer.toString( (Integer.parseInt(year) + loginYear));
            }
        }
        return sYear;
    }

    public String getAbsoluteMonth(String month) {
        JParamObject PO = null;
        PO = JParamObject.Create();
        int loginYear = Integer.parseInt(PO.GetValueByEnvName("LoginDate").
                                         substring(0, 4));
        String sMonth = month;
        if (month != null) {
            if (month.trim().length() == 0) {
                month = "0";
            }
            if (Integer.parseInt(month) <= 0) {
                sMonth = Integer.toString(Integer.parseInt(WizardObject.
                    ReportModel.
                    BBZD_DATE.substring(4, 6)) + Integer.parseInt(month));
            } else {
                sMonth = month;
            }
            if (sMonth.length() < 2) {
                sMonth = "0".concat(sMonth);
            }
        }
        return sMonth;
    }

    //------------------------------------------------------------------------------------------------
    //����: ��Ŀ���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpXMLB(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
		return HelpBH("LSHSFL","");
/*
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpXmlbInfomation");
        String LB = null;
        if (helpData != null) {
            LB = helpData[0];
        }
        return LB;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: ���ű��\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpBMBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
/*
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpBmbhInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
 */
      return HelpBH("ACBMZD","");

    }

    //------------------------------------------------------------------------------------------------
    //����: ���˱��\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpGRBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {

       return HelpBH("LSZGZD","");
    /*
	   String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpGrbhInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
	 */
    }

    //------------------------------------------------------------------------------------------------
    //����: ��ұ��
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpWBBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  return HelpBH("LSWBZD","");
/*
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpWbbhInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: ���ױ��\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpZTBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpZtbhInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: ��Ʒ���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpCPBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  return HelpBH("LSCPZD","");
/*
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpCpbhInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: �ɱ����\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpCBBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  return HelpBH("ACCBZX","");
/*
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpCbbhInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
 */
    }
	public Object HelpCBLX(JLimitObjectStub LOS, JFunctionStub FS,
						   JCustomTextField CTF, Object CO) {
		return HelpBH("LSCBLX", "");
	}
    //------------------------------------------------------------------------------------------------
    //����: �ֽ������\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpXJBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  return HelpBH("ACXJLL","");
/*
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpXjbhInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: ��4��λ���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpWLBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  return HelpBH("LSWLDW","");
/*
        JParamObject PO = JParamObject.Create();
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpWlbhInfomation", PO);
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: ��4���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpWLLB(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  return HelpBH("LSWLFL","","F_SYZT=1");
/*
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpWllbInfomation");
        String LB = null;
        if (helpData != null) {
            LB = helpData[0];
        }
        return LB;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: ̨�����\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpTZLB(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  return HelpBH("LSYSLB","");
/*
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpTzlbInfomation");
        String LB = null;
        if (helpData != null) {
            LB = helpData[0];
        }
        return LB;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: ̨����Ŀ���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpTZBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
	  String tzlb="";

	  tzlb = FS.GetParamValueCtrl("TZLB").getText().trim();
      return HelpBH("LSYSZD"," F_YSLB.equals(\"" + tzlb + "\")");
/*
      String tzlb, xx, code;
        jtoolkit.xml.classes.JXMLObject xml;
        JParamObject PO = null;
        PO = JParamObject.Create();
        if (FS.id.startsWith("XMTZ")) {
            JParamObject PO1 = new JParamObject(PO.GetParamString());
            code = FS.GetParamValueCtrl("KMBH").getText().trim();
            PO1.SetValueByParamName("fieldNames", "F_YSLB");
            PO1.SetValueByParamName("tableName", "LSKMZD");
//            PO1.SetValueByParamName("tableName", "LSKMXX");
            PO1.SetValueByParamName("everyWhere",
                                    "F_KMBH='" +
                                    com.pansoft.pub.util.StringFunction.
                                    FillZeroForBM(code,
                                                  PO.GetValueByEnvName(
                "ZW_KMSTRU")) +
                                    "'");
            JResponseObject RO;
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.
                InvokeObjectMethod("HelpObject",
                                   "getTableSpecifyFieldsValue", PO1,
                                   "1");
            String[] helpData1 = (String[]) RO.ResponseObject;
            if (helpData1 != null) {
                PO1 = new JParamObject(helpData1[0]);
                tzlb = PO1.GetValueByParamName("F_YSLB");
            } else
                return "";
        } else {
            tzlb = FS.GetParamValueCtrl("TZLB").getText().trim();
        }
        if (tzlb.equals("")) {
            Object object = HelpTZLB(LOS, FS, CTF, CO);
            tzlb = object.toString();
        }
        PO.SetValueByParamName("TZLB", tzlb);
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpTzbhInfomation", PO);
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
 */
    }

    //------------------------------------------------------------------------------------------------
    //����: ƾ֤���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpPZBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpPzbhInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: ƾ֤����
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpPZRQ(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        return LOS.text;
    }

    //------------------------------------------------------------------------------------------------
    //����: ƾ֤ժҪ
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpPZJY(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpPzzyInfomation");
        String ZY = null;
        if (helpData != null) {
            ZY = helpData[0];
        }
        return ZY;
    }

    //------------------------------------------------------------------------------------------------
    //����: SQL���\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpDSQL(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        JParamObject PO = null;
        PO = JParamObject.Create();
        JHelpDSQLDlg dlg = new JHelpDSQLDlg(WizardObject.MainFrame, "SQL���\uFFFD", true);
        dlg.CenterWindow();
        dlg.Show();
        if (dlg.getOption() == JHelpDSQLDlg.OK_OPTION) {
            return dlg.getSqlText().trim();
        }
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //����: �����\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpQJBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpQJBHInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: �ͽ����̱��\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpYJGCBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpYJGCBHInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: �ͽ��ɱ����\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpYJCBBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpYJCBBHInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: �ͽ����Ա��\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpYJSXBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpYJSXBHInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: �꾮���̱��\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpZJGCBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpZJGCBHInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: �꾮�ɱ����\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpZJCBBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpZJCBBHInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: �Զ��帨������Ű���\uFFFD
    //���\uFFFD:
    //ʵ��:
    //�޸�: ���к������\uFFFD
    //------------------------------------------------------------------------------------------------
    public Object HelpZFLB(JLimitObjectStub LOS, JFunctionStub FS, JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZFLB();
    }

    /**
     * �Զ��帨�������\uFFFD
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpZFBH(JLimitObjectStub LOS, JFunctionStub FS, JCustomTextField CTF, Object CO) {
        String bh = null;
        String lbbh = FS.GetParamValueCtrl("ZFLB").getText().trim();
        if (lbbh.equals("")) {
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, res.getString("String_535"));
            return "";
        }
        return JReportHelpObject.HelpZFBH(lbbh);
    }

    /**
     * �Զ��帨������Ű���\uFFFD
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpZFLBBH(JLimitObjectStub LOS, JFunctionStub FS, JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZFLBBH();
    }

    //------------------------------------------------------------------------------------------------
    //����: ��ҵ�ɱ��ĺ������\uFFFD
    //���\uFFFD:
    //ʵ��:
    //�޸�: add by fsz 2003.5.14
    //------------------------------------------------------------------------------------------------
    public Object HelpZYGCBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZYGCBH();
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���\uFFFD:
    //ʵ��:
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpZYCBBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZYCBBH();
    }

    /**
     * ת�ʱ�Ű���\uFFFD
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpZYZZBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZYZZBH();
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���\uFFFD:
    //ʵ��:
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpZYXDBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZYXDBH();
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���\uFFFD:
    //ʵ��:
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpZYXZBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZYXZBH();
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���\uFFFD:
    //ʵ��:
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpZYZBBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZYZBBH();
    }

    //------------------------------------------------------------------------------------------------
    //����: ��ҵ����+���\uFFFD
    //���\uFFFD:
    //ʵ��:
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpZYSXBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpZYLBSX();
    }

    //------------------------------------------------------------------------------------------------
    //����: ȡ����
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpDBFFILE(JLimitObjectStub LOS, JFunctionStub FS,
                              JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpDBFFILEInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: ���ݵ�Ԫ��Χ
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpJLBBREF(JLimitObjectStub LOS, JFunctionStub FS,
                              JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpJLBBREFInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: ��Ŀ�б�
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpKMLB(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpKMLBInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: �б���Ŀ
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpLBXM(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        /**
         * ������Ŀ�б�ʱ��Ҫ�Ȱ����̨������ٰ����̨�˱��
         * ��ݵ���ʽ��\uFFFD: 01-010203
         * modified by hufeng 2005.8.11 at scsy
         */
        Object oValue;
        String tzlb = null, BH = "";

        //ȡ̨�����\uFFFD
        oValue = this.HelpTZLB(LOS, FS, CTF, CO);
        if (oValue == null) {
            return oValue;
        }
        tzlb = oValue.toString();
        //ȡ̨�˱��\uFFFD
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("TZLB", tzlb);
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpTzbhInfomation", PO);
        if (helpData != null) {
            BH = helpData[0];
        }
        return tzlb + "-" + BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: Ԥ�����\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpYSLB(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpYSLBInfomation");
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: Ԥ����
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpYSBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String yslb;
        try {
            yslb = FS.GetParamValueCtrl("YSLB").getText().trim();
        } catch (Exception e) {
            yslb = "";
        }
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("YSLB", yslb);
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", "HelpYSBHInfomation", PO);
        String BH = null;
        if (helpData != null) {
            BH = helpData[0];
        }
        return BH;
    }

    //------------------------------------------------------------------------------------------------
    //����: �������\uFFFD
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpFLDX(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String[] id = LOS.des1.split(",");
        String[] text = LOS.des2.split(",");
        Vector v = new Vector();
        JDataObjectStub dos = null;
        for (int i = 0; i < id.length; i++) {
            dos = new JDataObjectStub();
            dos.id = id[i];
            dos.text = res.getString("String_556") + text[i] + res.getString("String_557");
            v.add(dos);
            dos = null;
        }
        JHelpDOBJInfoDlg Dlg = new JHelpDOBJInfoDlg(WizardObject.MainFrame, res.getString("String_558"), true);
        Dlg.WizardObject = WizardObject;
        JDataObjectStub DOS;
        Dlg.InitData(v);
        Dlg.CenterWindow();
        Dlg.Show();
        if (Dlg.Result != Dlg.RESULT_OK)
            return null;
        DOS = Dlg.DOS;
        if (DOS == null)
            return null;
        if (DOS.id.equals("HS")) {
            String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
                InvokeObjectMethod("HelpObject", "HelpXmlbInfomation");
            String LB = null;
            if (helpData != null) {
                LB = helpData[0];
            }
            return LB;

        } else
            return DOS.id;
    }

    //------------------------------------------------------------------------------------------------
    //����: ������
    //���\uFFFD: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�: KM,BM,DW,GR,
    //------------------------------------------------------------------------------------------------
    public Object HelpFLBH(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String fldx, func;
        JParamObject PO = null;
        PO = JParamObject.Create();
        try {
            fldx = FS.GetParamValueCtrl("FLDX").getText().trim();
        } catch (Exception e) {
            fldx = "";
        }
        if (fldx.equals("KM"))
            return HelpBH("LSKMZD","");
        else if (fldx.equals("BM"))
            return HelpBH("ACBMZD","");
        else if (fldx.equals("WL"))
            return HelpBH("LSWLDW","");
        else if (fldx.equals("ZG"))
            return HelpBH("LSZGZD","");
        else if (fldx.equals("XJ"))
            return HelpBH("ACXJLL","");
        else if (fldx.equals("CB"))
            return HelpBH("ACCBZX","");
        else if (fldx.equals("CP"))
            return HelpBH("LSCPZD","");
        else if (fldx.equals("DW"))
            return HelpBH("LSWLDW","");
        else if (fldx.equals("GR"))
            return HelpBH("LSZGZD","");
        else if (fldx.equals("ZF")) {
            String zflb= (String) HelpBH("LSZDFZLB","");
            if (zflb != null ) {
                return zflb+"-"+HelpBH("LSZFXM"+zflb,"");
            }
        }
        else if (fldx.equals("XM"))
			return HelpBH("LSHSZD","");
        else if (fldx.equals("TZ"))
			return HelpBH("LSYSZD","");
        else
            return null;
        return null;
/*
        if (fldx.equals("KM"))
            func = "HelpKmInfomation";
        else if (fldx.equals("BM"))
            func = "HelpBmbhInfomation";
        else if (fldx.equals("WL"))
            func = "HelpWlbhInfomation";
        else if (fldx.equals("ZG"))
            func = "HelpGrbhInfomation";
        else if (fldx.equals("XJ"))
            func = "HelpXjbhInfomation";
        else if (fldx.equals("CB"))
            func = "HelpCbbhInfomation";
        else if (fldx.equals("CP"))
            func = "HelpCpbhInfomation";
        else if (fldx.equals("DW"))
            func = "HelpWlbhInfomation";
//            func = "HelpDwbhInfomation";
        else if (fldx.equals("GR"))
            func = "HelpGrbhInfomation";
        else if (fldx.equals("ZF"))
            return JReportHelpObject.HelpZFLBBH();
        else if (fldx.equals("XM"))
            return JReportHelpObject.HelpXMLBBH();
        else if (fldx.equals("TZ"))
            return JReportHelpObject.HelpTZLBBH();
        else
            return null;
        String[] helpData = (String[]) JActiveDComDM.DataActiveFramework.
            InvokeObjectMethod("HelpObject", func, PO);
        if (helpData != null) {
            return helpData[0];
        }
        return null;
 */
    }

    /**
     * ʱ���ڼ��Ű���
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpSJQJBH(JLimitObjectStub LOS, JFunctionStub FS,
                             JCustomTextField CTF, Object CO) {

        return JReportHelpObject.HelpSJQJBH();
    }

    //------------------------------------------------------------------------------------------------
    //����: ����SAP������࣬����RPTFNZD��
    //���\uFFFD:
    //ʵ��:
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public Object HelpSAPFUNCFL(JLimitObjectStub LOS, JFunctionStub FS,
                                JCustomTextField CTF, Object CO) {
        return JReportHelpObject.HelpSAPFUNCFL();
    }

    /**
     * SAP�����Ű���
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpSAPFLBH(JLimitObjectStub LOS, JFunctionStub FS,
                              JCustomTextField CTF, Object CO) {
        String funcName = FS.GetParamValueCtrl("SAPFUNCFL").getText().trim();
        if (funcName.equals("")) {
            return "";
        }
        return JReportHelpObject.HelpSAPFLBH(funcName);
    }

    /**
     * Ԥ�����������\uFFFD
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpFUNCXH(JLimitObjectStub LOS, JFunctionStub FS,
                              JCustomTextField CTF, Object CO) {
         String date = WizardObject.ReportModel.BBZD_DATE;
         String bh   = WizardObject.ReportModel.BBZD_BH;
        return JReportHelpObject.HelpFUNCXH(bh,date);
    }



    /**
     * JY �ʽ�λ����
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpZJDW(JLimitObjectStub LOS, JFunctionStub FS,
                              JCustomTextField CTF, Object CO) {
        return JTradeHelpObject.HelpZJDW();
    }

    /**
     * JY ���ҵ��������\uFFFD
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpDBLB(JLimitObjectStub LOS, JFunctionStub FS,
                              JCustomTextField CTF, Object CO) {
        return JTradeHelpObject.HelpDBLB();
    }


    /**
     * JY ��ҵ����Ŀ
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpDBXM(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String lbbh = FS.GetParamValueCtrl("DBLB").getText().trim();
        if (lbbh.equals("")) {
            return "";
        }
        return JTradeHelpObject.HelpDBXM(lbbh);
    }

    public Object HelpDBSJ(JLimitObjectStub LOS, JFunctionStub FS,
                           JCustomTextField CTF, Object CO) {
        String lbbh = FS.GetParamValueCtrl("DBLB").getText().trim();
        if (lbbh.equals("")) {
            return "";
        }
        return JTradeHelpObject.HelpDBSJ(lbbh);
    }

    public Object HelpFYYS(JLimitObjectStub LOS, JFunctionStub FS,
                                               JCustomTextField CTF, Object CO) {
        return HelpBH("LSFYZD", "");
    }
	public Object HelpZYGCLB(JLimitObjectStub LOS, JFunctionStub FS,
						   JCustomTextField CTF, Object CO) {
		String gclb = (String) HelpBH("ZYGCLB", "");
		return gclb;
    }
	public Object HelpWLZ(JLimitObjectStub LOS, JFunctionStub FS,
						   JCustomTextField CTF, Object CO) {
		return HelpBH("LSCPLB", "");
	}
    public Object HelpGNFW(JLimitObjectStub LOS, JFunctionStub FS,
						   JCustomTextField CTF, Object CO) {
        return HelpBH("LSGNFW","");
/*
          try {
            DCTMetaData meta = (DCTMetaData) MetaDataManager.getDCTDataManager().
                getMetaData("LSGNFW");
            HelpInfoContext hic = new HelpInfoContext();
            hic.setDCTID("LSGNFW");
			hic.setBHColumn(meta.getDCT_BMCOLID());
			hic.setMCColumn(meta.getDCT_MCCOLID());
			hic.setJSColumn(meta.getDCT_JSCOLID());
			hic.setMXColumn(meta.getDCT_MXCOLID());
            EFRowSet rowset = ESPHelpUtil.ShowHelp(hic);
            if (rowset != null) {
              return (rowset.getString(meta.getDCT_BMCOLID(), ""));
            }
          }
          catch (Exception ex) {
          }
          return null;
 */
    }
	public Object HelpZBLB(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("ZBLBZD", "");
	}
	public Object HelpZBBH(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		String ZBLB   = FS.GetParamValueCtrl("ZBLB").getText().trim();
		String asFilter="";
		if ( !ZBLB.equals("") ) {
		//	asWhere = " F_LBBH = '"+ZBLB+"' ";
            asFilter = "F_LBBH.equals(\""+ZBLB+"\") ";
		}
		return HelpBH("ZBZD_001", asFilter);
	}



	public Object HelpBCLX(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("BCLXZD", "");
	}
	public Object HelpBCYWLX(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("BCYWLX", "");
	}
	public Object HelpBCXM(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("BCXMZD", "");
	}

	public Object HelpDYXMBH(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("DYXMZD", "");
	}
	public Object HelpDYXMLB(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("DYXMLB", "");
	}
	public Object HelpDYXMLX(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("DYXMLX", "");
	}

	public Object HelpSFJT(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("LSJTZD", "");
	}
	public Object HelpSFJN(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("LSJWZD", "");
	}
	public Object HelpSSSYB(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("ACBKZD1", "");
	}
	public Object HelpGJBH(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("LSGJZD", "");
	}
	public Object HelpDQBH(JLimitObjectStub LOS, JFunctionStub FS,
											   JCustomTextField CTF, Object CO) {
		return HelpBH("LSDQSF", "");
	}

	public Object HelpWLKMBH(JLimitObjectStub LOS, JFunctionStub FS,
						   JCustomTextField CTF, Object CO) {
        return HelpBH("LSKMZD","", " exists (select 1 from LSKMSZ SZ where SZ.F_KMBH=LSKMZD.F_KMBH and F_DW= '1' AND F_DZ='1') ");
    }


    /**
     * ���׺���������
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpJYFUNCFL(JLimitObjectStub LOS, JFunctionStub FS,
                                JCustomTextField CTF, Object CO) {
         String[] id = LOS.des1.split(",");
         String[] text = LOS.des2.split(",");
         Vector v = new Vector();
         JDataObjectStub dos = null;
         for (int i = 0; i < id.length; i++) {
             dos = new JDataObjectStub();
             dos.id = id[i];
             dos.text = text[i];
             v.add(dos);
             dos = null;
         }
         JHelpDOBJInfoDlg Dlg = new JHelpDOBJInfoDlg(WizardObject.MainFrame, res.getString("String_558"), true);
         Dlg.WizardObject = WizardObject;
         JDataObjectStub DOS;
         Dlg.InitData(v);
         Dlg.CenterWindow();
         Dlg.Show();
         if (Dlg.Result != Dlg.RESULT_OK)
             return null;
         DOS = Dlg.DOS;
         if (DOS == null){
             return null;
        }
        return DOS.id;
    }

    /**
     * �ⲿ�������������\uFFFD
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public Object HelpExtendParamObject(JLimitObjectStub LOS, JFunctionStub FS,
                                        JCustomTextField CTF, Object CO) {
        String funcID = FS.getId();
        String objID  = LOS.getId();
        //
        ArrayList funcList = WizardObject.getExtFuncList();
        JExtFuncStub funcStub = JExtFuncService.getFuncStubByID(funcList,funcID);
        if(funcStub == null){
            return null;
        }
        JExtObjStub objStub = JExtFuncService.getFuncObjStubByID(funcStub.getPobjList(),objID);
        if(objStub == null){
            return null;
        }
        return JReportHelpObject.HelpExtendObject(objStub);
    }
}
