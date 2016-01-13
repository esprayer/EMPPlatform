package jreport.swing.classes.ReportBook;

import java.util.*;

import com.efounder.eai.data.JParamObject;

import jreport.swing.classes.*;
import jreport.swing.classes.util.*;
import jreportfunction.pub.*;
import jreportwh.jdof.classes.common.util.*;
//import com.pansoft.doc.bof.pub.DocUtil;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JUnitInfo {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
//  public JRowInfo     HeadRowInfo = null;
//  public JColInfo     HeadColInfo = null;
//  public int MaxOrde=0;
  private int JYGSIndex = -1;
  public static final int JYGS_TYPE_BBZD   = 0;
  public static final int JYGS_TYPE_LBZD   = 1;
  public static final int JYGS_TYPE_DWZD   = 2;
  public static final int JYGS_TYPE_BMZD   = 3;
  public static final int JYGS_TYPE_CBZD   = 4;

  public static final int JSGS_TYPE_BBZD   = 0;
  public static final int JSGS_TYPE_LBZD   = 1;
  public static final int JSGS_TYPE_DWZD   = 2;
  public static final int JSGS_TYPE_BMZD   = 3;
  public static final int JSGS_TYPE_CBZD   = 4;

  //������ʽ��״̬����
  public static final int TZGS_TYPE_BBZD   = 0;
  public static final int TZGS_TYPE_LBZD   = 1;
  public static final int TZGS_TYPE_DWZD   = 2;
  public static final int TZGS_TYPE_BMZD   = 3;
  public static final int TZGS_TYPE_CBZD   = 4;

  //���㹫ʽ��״̬����
  public static final int ZSGS_TYPE_BBZD   = 0;
  public static final int ZSGS_TYPE_LBZD   = 1;
  public static final int ZSGS_TYPE_DWZD   = 2;
  public static final int ZSGS_TYPE_BMZD   = 3;
  public static final int ZSGS_TYPE_CBZD   = 4;
  

  public int JYGS_TYPE = JYGS_TYPE_BBZD;//
  public int JSGS_TYPE = JSGS_TYPE_BBZD;//
  public int TZGS_TYPE = TZGS_TYPE_BBZD;//
  public int ZSGS_TYPE = ZSGS_TYPE_BBZD;//
  JReportModel ReportModel = null;
  public JBDHDataInfo BDHDataInfo = null;

//  public String DYZD_ORDE="";
  public int DYZD_LX=0; // 0:��ֵ��1:�ַ���
  public int DYZD_DEC=2;
  public String DYZD_SJ=null;
  public double DYZD_DATA=0.00;
  public String TZZD_SJ=null;//�����ֵ�����
  public double TZZD_DATA=0.00;//�����ֵ���ֵ
  public float TZXS =0;
  public int TZJD = 2;
  public String TZGS = "";
  //public String TZGS_GSX = "";

  public boolean DYZD_SFTZ=true;
  public boolean DYZD_SFBH=false;
  public int DYZD_HOFFSET=0;
  public int DYZD_LOFFSET=0;
  public String DYZD_GSX=null;
  public String DYZD_EXGSX=null;
  public String DYZD_GSBZ="";
  public String DYZD_COMBO = null; // �����б���ļ���
  public String DYZD_TZGS=null;
  public String DYZD_ZSGS=null;
  public String DYZD_EXZSGS=null;
  public String DYZD_FONT=null; //����
  public int DYZD_ZSHOFFSET=0;
  public int DYZD_ZSLOFFSET=0;
  public String DYZD_ZBXX=null;


  public int DYZD_GSJB=0;
  public int ChangeLog=0;     // �޸ı�־(0:û���޸Ĺ���1:�½���2:�޸Ĺ�)
  public int ChangeLogJs=0;   // ���㹫ʽ�޸ı�־(ͬ�ϣ�
  public int ChangeLogJy=0;   // У�鹫ʽ�޸ı�־(ͬ�ϣ�
  public int ChangeLogTz=0;   // ������ʽ�޸ı�־(ͬ�ϣ�
  public int ChangeLogPh=0;   // ƽ�⹫ʽ�޸ı�־
  public int ChangeLogPz=0;   // ������ע�޸ı�־
  public int ChangeLogZs=0;   // ���㹫ʽ�޸ı�־(ͬ�ϣ�
  public int lCa=0;           // �õ㹫ʽ�ļ���λ��
  public boolean DYZD_MJZD=false;

  public JUnitInfo PrioUnit=null;
  public JUnitInfo NextUnit=null;
  public JUnitInfo RowPrioUnit=null;  // ͬһ�е���һ����Ԫ
  public JUnitInfo RowNextUnit=null;  // ͬһ�е���һ����Ԫ
  public JUnitInfo ColPrioUnit=null;  // ͬһ�е���һ����Ԫ
  public JUnitInfo ColNextUnit=null;  // ͬһ�е���һ����Ԫ
  public JRowInfo RowInfo=null;      // �е�ָ��
  public JColInfo ColInfo=null;      // �е�ָ��
  public Vector JygsList=null;       // У�鹫ʽ
  public Vector TzgsList=null;       //������ʽ
  public Vector CommentList=null;       // ����ע�б�
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JUnitInfo() {
  }
  public Vector createJygsList() {
    if ( JygsList == null ) {
      JygsList = new Vector();
    }
    return JygsList;
  }

  public Vector createTzgsList() {
    if ( TzgsList == null ) {
      TzgsList = new Vector();
    }
    return TzgsList;
  }

  public Vector createCommentList() {
      if (CommentList == null) {
          CommentList = new Vector();
      }
      return CommentList;
  }

  //���ù�ʽ��λ��
  public void setOffset(int hOffset,int lOffset){
      if ( this.DYZD_GSX == null || DYZD_GSX.trim().length() == 0 ) return;
      this.DYZD_HOFFSET=hOffset;
      this.DYZD_LOFFSET=lOffset;
      setModified();
  }
  //����У�鹫ʽ��ĳ��У�鹫ʽ��λ��
  public void setOffset(int hOffset,int lOffset,int index){
      if(JygsList == null || JygsList.size()==0) return ;
      JJygsInfo jygsInfo=(JJygsInfo)JygsList.get(index);
      jygsInfo.JYGS_HOFFSET=hOffset;
      jygsInfo.JYGS_LOFFSET=lOffset;
      setModified();
  }
  //���ù�ʽ��λ��
  public void setZSOffset(int hOffset,int lOffset){
      if ( this.DYZD_ZSGS == null || DYZD_ZSGS.trim().length() == 0 ) return;
      this.DYZD_ZSHOFFSET=hOffset;
      this.DYZD_ZSLOFFSET=lOffset;
      setModified();
  }  
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void setModified() {
    if ( ChangeLog != 1 ) ChangeLog = 2;
    if ( BDHDataInfo != null ) {
      BDHDataInfo.setModified();
    }
  }
  public void setJSModified() {
    ChangeLogJs = 2;
  }
  public void setJYModified() {
    ChangeLogJy = 2;
  }

  public void setTZModified() {
    ChangeLogTz = 2;
  }
  public void setZSModified() {
	    ChangeLogZs = 2;
  }

  public void setCommentModified(){
      ChangeLogPz = 2;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void setJSGS_TYPE(JReportModel RM) {
    if ( RM.ADD_TYPE == RM.STUB_TYPE_LBZD ) JSGS_TYPE = JSGS_TYPE_LBZD;
    if ( RM.ADD_TYPE == RM.STUB_TYPE_DWZD ) JSGS_TYPE = JSGS_TYPE_DWZD;
    if ( RM.ADD_TYPE == RM.STUB_TYPE_BMZD ) JSGS_TYPE = JSGS_TYPE_BMZD;
    if ( RM.ADD_TYPE == RM.STUB_TYPE_CBZD ) JSGS_TYPE = JSGS_TYPE_CBZD;
    if ( RM.ADD_TYPE == RM.STUB_TYPE_REPORT ) JSGS_TYPE = JSGS_TYPE_BBZD;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void setJYGS_TYPE(JReportModel RM) {
    if ( RM.ADD_TYPE == RM.STUB_TYPE_LBZD ) JYGS_TYPE = JYGS_TYPE_LBZD;
    if ( RM.ADD_TYPE == RM.STUB_TYPE_DWZD ) JYGS_TYPE = JYGS_TYPE_DWZD;
    if ( RM.ADD_TYPE == RM.STUB_TYPE_BMZD ) JYGS_TYPE = JYGS_TYPE_BMZD;
    if ( RM.ADD_TYPE == RM.STUB_TYPE_CBZD ) JYGS_TYPE = JYGS_TYPE_CBZD;
    if ( RM.ADD_TYPE == RM.STUB_TYPE_REPORT ) JYGS_TYPE = JYGS_TYPE_BBZD;
  }


  //------------------------------------------------------------------------------------------------
//����:
//���: Skyline(2001.12.29)
//ʵ��: Skyline
//�޸�:
//------------------------------------------------------------------------------------------------
public void setTZGS_TYPE(JReportModel RM) {
  if ( RM.ADD_TYPE == RM.STUB_TYPE_LBZD ) TZGS_TYPE = TZGS_TYPE_LBZD;
  if ( RM.ADD_TYPE == RM.STUB_TYPE_DWZD ) TZGS_TYPE = TZGS_TYPE_DWZD;
  if ( RM.ADD_TYPE == RM.STUB_TYPE_BMZD ) TZGS_TYPE = TZGS_TYPE_BMZD;
  if ( RM.ADD_TYPE == RM.STUB_TYPE_CBZD ) TZGS_TYPE = TZGS_TYPE_CBZD;
  if ( RM.ADD_TYPE == RM.STUB_TYPE_REPORT ) TZGS_TYPE = TZGS_TYPE_BBZD;
}
//------------------------------------------------------------------------------------------------
//����:
//���: Skyline(2001.12.29)
//ʵ��: Skyline
//�޸�:
//------------------------------------------------------------------------------------------------
public void setZSGS_TYPE(JReportModel RM) {
if ( RM.ADD_TYPE == RM.STUB_TYPE_LBZD ) ZSGS_TYPE = ZSGS_TYPE_LBZD;
if ( RM.ADD_TYPE == RM.STUB_TYPE_DWZD ) ZSGS_TYPE = ZSGS_TYPE_DWZD;
if ( RM.ADD_TYPE == RM.STUB_TYPE_BMZD ) ZSGS_TYPE = ZSGS_TYPE_BMZD;
if ( RM.ADD_TYPE == RM.STUB_TYPE_CBZD ) ZSGS_TYPE = ZSGS_TYPE_CBZD;
if ( RM.ADD_TYPE == RM.STUB_TYPE_REPORT ) ZSGS_TYPE = ZSGS_TYPE_BBZD;
}

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void deleteGS(JReportModel RM) {
    int rowCount = 0,colCount = 0;
    if ( RM.getDataStatus() == RM.MODEL_STATUS_DATA ||
         RM.getDataStatus() == RM.MODEL_STATUS_JSGS ) {
      rowCount = DYZD_HOFFSET;
      colCount = DYZD_LOFFSET;
      DYZD_GSX="";
      DYZD_EXGSX="";
      setJSModified();
      setJYGS_TYPE(RM);
      
    }
    if ( RM.getDataStatus() == RM.MODEL_STATUS_ZSGS ) {
         rowCount = DYZD_ZSHOFFSET;
         colCount = DYZD_ZSLOFFSET;
         DYZD_ZSGS="";
         DYZD_EXZSGS="";
         setZSModified();
       }    
    if ( RM.getDataStatus() == RM.MODEL_STATUS_JYGS ) {
        setJYGS_TYPE(RM);
    	
      //JygsList = null;
//      JJygsInfo j;
//      for(int i=0;i<JygsList.size();i++){
//        j=(JJygsInfo)JygsList.get(i);
//        j.JYGS_GSX="";
//        j.JYGS_EXGSX="";
//        setJYModified();
//        //��鹫ʽ�ķ�Χ����DYZD_HOFFSET
//        rowCount = j.JYGS_HOFFSET;
//        colCount = j.JYGS_LOFFSET;
//      }
      /**
       * ����ǵ�λ����ֻɾ����λ�Զ����У�鹫ʽ
       * modified by hufeng 2005.11.21
       */
      if(JygsList == null){
        return;
      }
      JJygsInfo j;
      JygsList.clear();
//      for (int i = 0; i < JygsList.size(); i++) {
//        j = (JJygsInfo) JygsList.get(i);
//        if (j.JYGS_FROM == JJygsInfo.FROM_XTBB && RM.ADD_BH != null) {
//          continue;
//        }
//        j.JYGS_GSX = "";
//        j.JYGS_EXGSX = "";
//      }
      setJYModified();
    }
    //add by fsz 2004.5.27
    if ( this.BDHDataInfo == null ) {
      try {
        /**
         * ��������Χ�ÿգ���Ϊ�ڷ�Χ�ڿ�����ͬ��ͬ�Ϲ�ʽ��ʶ
         * modified by hufeng 2005.11.15
         */
        for(int i=0; i<=rowCount; i++){
          for(int j=0; j<=colCount; j++){
            if(i==0 && j==0){
              RM.ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, DYZD_SJ);
            }else{
              RM.ReportView.setText(RowInfo.HZD_ZB - 1 + i, ColInfo.LZD_ZB - 1 + j, "");
            }
          }
        }
//        RM.ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, DYZD_SJ);
//        RM.ReportView.setNumber(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, DYZD_DATA);

      }
      catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      try {
        /**
         * ��������Χ�ÿգ���Ϊ�ڷ�Χ�ڿ�����ͬ��ͬ�Ϲ�ʽ��ʶ
         * modified by hufeng 2005.11.15
         */
        for(int i=0; i<=rowCount; i++){
          for(int j=0; j<=colCount; j++){
            if(i==0 && j==0){
              RM.ReportView.setPhyText(BDHDataInfo.getPhyPrioRow()+BDHDataInfo.getTrueOffset(), ColInfo.LZD_ZB - 1, DYZD_SJ);
            }else{
              RM.ReportView.setPhyText(BDHDataInfo.getPhyPrioRow()+BDHDataInfo.getTrueOffset() + i, ColInfo.LZD_ZB - 1 + j, "");
            }
          }
        }
//        RM.ReportView.setPhyText(BDHDataInfo.getPhyPrioRow()+BDHDataInfo.getTrueOffset(), ColInfo.LZD_ZB - 1, DYZD_SJ);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    //end
    setModified();

    RM.setModified();
  }

  //------------------------------------------------------------------------------------------------
   //����:ʵ����յ�������
   //���: Skyline(2001.12.29)
   //ʵ��: Skyline
   //�޸�:
   //------------------------------------------------------------------------------------------------
   public void deleteTz(JReportModel RM) {
     int rowCount = 0,colCount = 0;
     if (  RM.getDataStatus() == RM.MODEL_STATUS_TZGS ) {
       DYZD_TZGS="";
       setModified();
     }
     setTZModified();
     setTZGS_TYPE(RM);
     setModified();

     RM.setModified();
   }




  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void deleteComment(JReportModel RM) {
      int rowCount = 0, colCount = 0;
      if (RM.getDataStatus() == RM.MODEL_STATUS_DATA || RM.getDataStatus() == RM.MODEL_STATUS_BBPZ) {
          rowCount = DYZD_HOFFSET;
          colCount = DYZD_LOFFSET;
          setCommentModified();
      }
      if (RM.getDataStatus() == RM.MODEL_STATUS_BBPZ) {
          /**
           * ����ǵ�λ����ֻɾ����λ�Զ������ע
           */
          if (CommentList == null) {
              return;
          }
          CommentList.clear();
//          JCommentInfo j;
//          for (int i = 0; i < CommentList.size(); i++) {
//              j = (JCommentInfo) CommentList.get(i);
//              j.Comment_Date = "";
//              j.Comment_User = "";
//              j.Comment_Info = "";
//          }
          setCommentModified();
      }
      if (BDHDataInfo == null) {
          try {
              /**
               * ��������Χ�ÿգ���Ϊ�ڷ�Χ�ڿ�����ͬ��ͬ�Ϲ�ʽ��ʶ
               */
              for (int i = 0; i <= rowCount; i++) {
                  for (int j = 0; j <= colCount; j++) {
                      if (i == 0 && j == 0) {
                          RM.ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, DYZD_SJ);
                      } else {
                          RM.ReportView.setText(RowInfo.HZD_ZB - 1 + i, ColInfo.LZD_ZB - 1 + j, "");
                      }
                  }
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
      } else {
          try {
              /**
               * ��������Χ�ÿգ���Ϊ�ڷ�Χ�ڿ�����ͬ��ͬ�Ϲ�ʽ��ʶ
               */
              for (int i = 0; i <= rowCount; i++) {
                  for (int j = 0; j <= colCount; j++) {
                      if (i == 0 && j == 0) {
                          RM.ReportView.setPhyText(BDHDataInfo.getPhyPrioRow() + BDHDataInfo.getTrueOffset(), ColInfo.LZD_ZB - 1, DYZD_SJ);
                      } else {
                          RM.ReportView.setPhyText(BDHDataInfo.getPhyPrioRow() + BDHDataInfo.getTrueOffset() + i, ColInfo.LZD_ZB - 1 + j, "");
                      }
                  }
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      setModified();
      RM.setModified();
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public int setUIGSX(String GSX,int HOffset,int LOffset,JReportModel RM) throws Exception {
      //���㹫ʽ,��GSXΪ"="�ұߵ�ֵ
      //У�鹫ʽ,��GSXΪ�������ŵ�ֵ
    String Text;
    //GSX = GSX.toUpperCase();
//    if ( GSX.startsWith("=") ) GSX = GSX.substring(1,GSX.length());
    if ( RM.getDataStatus() == RM.MODEL_STATUS_DATA ||
         RM.getDataStatus() == RM.MODEL_STATUS_JSGS ) { // ��������ݻ���㹫ʽ״̬
        if ( GSX.startsWith("=") ) GSX = GSX.substring(1,GSX.length());
      Text = JReportView.FunctionManager.UserModelToStoreModel(GSX,null,RM);
      DYZD_GSX = Text;
      DYZD_EXGSX = GSX;
      DYZD_HOFFSET = HOffset;
      DYZD_LOFFSET = LOffset;
      setJSModified();
      if ( this.BDHDataInfo == null ) { // ������Ǳ䶯���е���Ҫ����
        for (int i = RowInfo.HZD_ZB + 1; i < RowInfo.HZD_ZB + HOffset; i++) {
          for (int j = ColInfo.LZD_ZB + 1; j < ColInfo.LZD_ZB + LOffset; j++) {
            // ���ɵ�Ԫ
            GetUnitInfoByHL(i, j, 1, RM);
          }
        }
      }
      setJSGS_TYPE(RM);
    }
    if (RM.getDataStatus() == RM.MODEL_STATUS_JYGS) { // �����У�鹫ʽ
        setUIJYGS(GSX, HOffset, LOffset, RM);
      } else if (RM.getDataStatus() == RM.MODEL_STATUS_TZGS) {
          /**
           * ����ǵ�����ʽ
           * add by mengfei 2009-11-16
           */
          setUITZGS(GSX, HOffset, LOffset, RM);
      }else if (RM.getDataStatus() == RM.MODEL_STATUS_BBPZ) {
        /**
         * ����Ǳ�����ע
         * add by gengeng 2008-8-28
         */
        setUIComment(GSX, HOffset, LOffset, RM);
    }
    setModified();
    RM.setModified();
    return 0;
  }

  public int setUIJYGS(String GSX, int HOffset, int LOffset, JReportModel RM) throws Exception {
      //У�鹫ʽ��־
      int Index = ( (Integer) RM.getModelInfo("JYGS_INDEX", new Integer(0))).intValue();
      if (Index == -1) Index = 0;
//    String BJ=RM.getModelInfo("JYGS_FH","=").toString();
      String BJ = ReportUtil.getSymbolFromJYGS(GSX);
      boolean STAT = ( (Boolean) RM.getModelInfo("JYGS_STAT", new Boolean(false))).booleanValue();

      if (STAT) { //׷��
          JJygsInfo JygsInfo = null;
          GSX = GSX.toUpperCase();
//        if (GSX.startsWith(BJ))
//            GSX = GSX.substring(1, GSX.length());
          if (StringUtil.isStartWith(GSX, ReportUtil.SYMBOL_NAMES))
//            GSX = GSX.substring(1, GSX.length());
              GSX = ReportUtil.getJYGSWithoutSymbol(GSX);

          if (!JReportView.FunctionManager.IsReportF1(GSX)) {
              throw new Exception("У�鹫ʽ��ֻ�ܰ��������ຯ��!");
          }
          if (JygsList == null)
              JygsList = new Vector();
//        if (Index < JygsList.size())
//            JygsInfo = (JJygsInfo) JygsList.get(Index);
          if (JygsInfo == null)
              JygsInfo = JJygsInfo.CreateJygsInfo(RM);
          JygsInfo.JYGS_GSX = JReportView.FunctionManager.UserModelToStoreModel(GSX, null, RM);
          JygsInfo.JYGS_BJ = BJ;
          JygsInfo.JYGS_HOFFSET = HOffset;
          JygsInfo.JYGS_LOFFSET = LOffset;
          JygsInfo.JYGS_EXGSX = GSX;
          if (this.BDHDataInfo == null) { // ��������ڱ䶯��
              for (int i = RowInfo.HZD_ZB + 1; i < RowInfo.HZD_ZB + HOffset; i++) {
                  for (int j = ColInfo.LZD_ZB + 1; j < ColInfo.LZD_ZB + LOffset;
                       j++) {
                      // ���ɵ�Ԫ
                      GetUnitInfoByHL(i, j, 1, RM);
                  }
              }
          }
//        if (Index < JygsList.size())
//            JygsList.set(Index, JygsInfo);
//        else
          JygsList.add(JygsInfo);
          setJYGS_TYPE(RM);
          setModified();
          setJYModified();
          RM.setModified();
      } else { //�޸�
          JJygsInfo JygsInfo = null;
          GSX = GSX.toUpperCase();
//        if (GSX.startsWith(BJ))
          if (StringUtil.isStartWith(GSX, ReportUtil.SYMBOL_NAMES))
//            GSX = GSX.substring(1, GSX.length());
              GSX = ReportUtil.getJYGSWithoutSymbol(GSX);
          if (!JReportView.FunctionManager.IsReportF1(GSX)) {
              throw new Exception("У�鹫ʽ��ֻ�ܰ��������ຯ��!");
          }
          if (JygsList == null)
              JygsList = new Vector();
          if (Index < JygsList.size() && Index != -1)
              JygsInfo = (JJygsInfo) JygsList.get(Index);
          if (JygsInfo == null)
              JygsInfo = JJygsInfo.CreateJygsInfo(RM);
          JygsInfo.JYGS_GSX = JReportView.FunctionManager.
              UserModelToStoreModel(GSX, null, RM);
          JygsInfo.JYGS_BJ = BJ;
          JygsInfo.JYGS_HOFFSET = HOffset;
          JygsInfo.JYGS_LOFFSET = LOffset;
          JygsInfo.JYGS_EXGSX = GSX;
          if (this.BDHDataInfo == null) { // ��������ڱ䶯��
              for (int i = RowInfo.HZD_ZB + 1; i < RowInfo.HZD_ZB + HOffset; i++) {
                  for (int j = ColInfo.LZD_ZB + 1; j < ColInfo.LZD_ZB + LOffset;
                       j++) {
                      // ���ɵ�Ԫ
                      GetUnitInfoByHL(i, j, 1, RM);
                  }
              }
          }
          if (Index < JygsList.size())
              JygsList.set(Index, JygsInfo);
          else
              JygsList.add(JygsInfo);
          setJYModified();
          setJYGS_TYPE(RM);
          setModified();
          RM.setModified();
      }
      return 0;
  }

  public int setUITZGS(String GSX, int HOffset, int LOffset, JReportModel RM) throws Exception {
     //������ʽ��־
/*
     int Index = ( (Integer) RM.getModelInfo("TZGS_INDEX", new Integer(0))).intValue();
     if (Index == -1) Index = 0;
     String BJ = ReportUtil.getSymbolFromJYGS(GSX);
     boolean STAT = ( (Boolean) RM.getModelInfo("TZGS_STAT", new Boolean(false))).booleanValue();

     if (STAT) { //׷��
         JTzgsInfo TzgsInfo = null;
         GSX = GSX.toUpperCase();
         if (StringUtil.isStartWith(GSX, ReportUtil.SYMBOL_NAMES))
             GSX = ReportUtil.getTZGSWithoutSymbol(GSX);
         if (!JReportView.FunctionManager.IsReportF1(GSX)) {
             throw new Exception("������ʽ��ֻ�ܰ��������ຯ��!");
         }
         if (TzgsList == null)
             TzgsList = new Vector();
         if (TzgsInfo == null)
             TzgsInfo = JTzgsInfo.CreateTzgsInfo(RM);
         TzgsInfo.TZGS_GSX = JReportView.FunctionManager.UserModelToStoreModel(GSX, null, RM);
         TzgsInfo.TZGS_HZD = HOffset;
         TzgsInfo.TZGS_LZD = LOffset;
         TzgsInfo.TZGS_EXGSX = GSX;
         if (this.BDHDataInfo == null) { // ��������ڱ䶯��
             for (int i = RowInfo.HZD_ZB + 1; i < RowInfo.HZD_ZB + HOffset; i++) {
                 for (int j = ColInfo.LZD_ZB + 1; j < ColInfo.LZD_ZB + LOffset;
                      j++) {
                     // ���ɵ�Ԫ
                     GetUnitInfoByHL(i, j, 1, RM);
                 }
             }
         }
         TzgsList.add(TzgsInfo);
         setTZGS_TYPE(RM);
         setModified();
         setTZModified();
         RM.setModified();
     } else { //�޸�
         JTzgsInfo TzgsInfo = null;
         GSX = GSX.toUpperCase();
         if (StringUtil.isStartWith(GSX, ReportUtil.SYMBOL_NAMES))
             GSX = ReportUtil.getJYGSWithoutSymbol(GSX);
         if (!JReportView.FunctionManager.IsReportF1(GSX)) {
             throw new Exception("������ʽ��ֻ�ܰ��������ຯ��!");
         }
         if (TzgsList == null)
             TzgsList = new Vector();
         if (Index < TzgsList.size() && Index != -1)
             TzgsInfo = (JTzgsInfo) TzgsList.get(Index);
         if (TzgsInfo == null)
             TzgsInfo = JTzgsInfo.CreateTzgsInfo(RM);
         TzgsInfo.TZGS_GSX = JReportView.FunctionManager.
             UserModelToStoreModel(GSX, null, RM);
         TzgsInfo.TZGS_HZD = HOffset;
         TzgsInfo.TZGS_LZD = LOffset;
         TzgsInfo.TZGS_EXGSX = GSX;
         if (this.BDHDataInfo == null) { // ��������ڱ䶯��
             for (int i = RowInfo.HZD_ZB + 1; i < RowInfo.HZD_ZB + HOffset; i++) {
                 for (int j = ColInfo.LZD_ZB + 1; j < ColInfo.LZD_ZB + LOffset;
                      j++) {
                     // ���ɵ�Ԫ
                     GetUnitInfoByHL(i, j, 1, RM);
                 }
             }
         }
         if (Index < JygsList.size())
             TzgsList.set(Index, TzgsInfo);
         else
             TzgsList.add(TzgsInfo);
         setTZModified();
         setTZGS_TYPE(RM);
         setModified();
         RM.setModified();
     }
 */
     return 0;
 }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public int setUIZSGSX(String GSX,int HOffset,int LOffset,JReportModel RM) throws Exception {
      //���㹫ʽ,��GSXΪ"="�ұߵ�ֵ
      //У�鹫ʽ,��GSXΪ�������ŵ�ֵ
    String Text;
    //GSX = GSX.toUpperCase();
//    if ( GSX.startsWith("=") ) GSX = GSX.substring(1,GSX.length());
    if ( RM.getDataStatus() == RM.MODEL_STATUS_ZSGS ) { // ���㹫ʽ״̬
        if ( GSX.startsWith("=") ) GSX = GSX.substring(1,GSX.length());
      Text = JReportView.FunctionManager.UserModelToStoreModel(GSX,null,RM);
//      DYZD_GSX = Text;
      DYZD_ZSGS = Text;
      DYZD_EXZSGS = GSX;
      DYZD_ZSHOFFSET = HOffset;
      DYZD_ZSLOFFSET = LOffset;
      setZSModified();
      if ( this.BDHDataInfo == null ) { // ������Ǳ䶯���е���Ҫ����
        for (int i = RowInfo.HZD_ZB + 1; i < RowInfo.HZD_ZB + HOffset; i++) {
          for (int j = ColInfo.LZD_ZB + 1; j < ColInfo.LZD_ZB + LOffset; j++) {
            // ���ɵ�Ԫ
            GetUnitInfoByHL(i, j, 1, RM);
          }
        }
      }
      setZSGS_TYPE(RM);
    }

    setModified();
    RM.setModified();
    return 0;
  }

  /**
   * ���ñ���ע��Ϣ
   * @param GSX String
   * @param HOffset int
   * @param LOffset int
   * @param RM JReportModel
   * @return int
   * @throws Exception
   */
  public int setUIComment(String GSX, int HOffset, int LOffset, JReportModel RM) throws Exception {
      int Index = ( (Integer) RM.getModelInfo("BBFZ_INDEX", new Integer(0))).intValue();
      if (Index == -1) Index = 0;
      boolean STAT = ( (Boolean) RM.getModelInfo("BBFZ_STAT", new Boolean(false))).booleanValue();

      JCommentInfo commentInfo = null;
      if (CommentList == null)
          CommentList = new Vector();

      if (GSX != null && (GSX.trim().equals("") || GSX.trim().equals("<ͬ��>") || GSX.trim().equals("<ͬ��>"))) {
          return 0;
      }

      if (GSX != null && GSX.indexOf("��") >= 0) {
          GSX = GSX.substring(GSX.indexOf("��") + 1);
      }
      if (STAT) { //׷��
          if (commentInfo == null)
              commentInfo = JCommentInfo.CreateCommentInfo(RM);
          commentInfo.Comment_User = JParamObject.Create().GetValueByEnvName("UserCaption","");
          commentInfo.Comment_Date = JReportPubFunc.getLocalCurrentTime();
          commentInfo.Comment_Info = GSX;
          commentInfo.Comment_HOFFSET = HOffset;
          commentInfo.Comment_LOFFSET = LOffset;
          commentInfo.ADD_TYPE = RM.ADD_TYPE;
          commentInfo.ADD_BH = RM.ADD_BH;
          commentInfo.HZD_ORDE = RowInfo.HZD_ORDE;
          commentInfo.LZD_ORDE = ColInfo.LZD_ORDE;
          if (BDHDataInfo == null) { // ��������ڱ䶯��
              for (int i = RowInfo.HZD_ZB + 1; i < RowInfo.HZD_ZB + HOffset; i++) {
                  for (int j = ColInfo.LZD_ZB + 1; j < ColInfo.LZD_ZB + LOffset;
                       j++) {
                      // ���ɵ�Ԫ
                      GetUnitInfoByHL(i, j, 1, RM);
                  }
              }
          }
          CommentList.add(commentInfo);
          setModified();
          setCommentModified();
          RM.setModified();
      } else { //�޸�
          if (Index < CommentList.size() && Index != -1)
              commentInfo = (JCommentInfo) CommentList.get(Index);
          if (commentInfo == null)
              commentInfo = JCommentInfo.CreateCommentInfo(RM);
          commentInfo.Comment_User = JParamObject.Create().GetValueByEnvName("UserCaption","");
          commentInfo.Comment_Date = JReportPubFunc.getLocalCurrentTime();
          commentInfo.Comment_Info = GSX;
          commentInfo.Comment_HOFFSET = HOffset;
          commentInfo.Comment_LOFFSET = LOffset;
          commentInfo.ADD_TYPE = RM.ADD_TYPE;
          commentInfo.ADD_BH = RM.ADD_BH;
          commentInfo.HZD_ORDE = RowInfo.HZD_ORDE;
          commentInfo.LZD_ORDE = ColInfo.LZD_ORDE;
          if (BDHDataInfo == null) { // ��������ڱ䶯��
              for (int i = RowInfo.HZD_ZB + 1; i < RowInfo.HZD_ZB + HOffset; i++) {
                  for (int j = ColInfo.LZD_ZB + 1; j < ColInfo.LZD_ZB + LOffset;
                       j++) {
                      // ���ɵ�Ԫ
                      GetUnitInfoByHL(i, j, 1, RM);
                  }
              }
          }
          if (Index < CommentList.size())
              CommentList.set(Index, commentInfo);
          else
              CommentList.add(commentInfo);
          setCommentModified();
          setModified();
          RM.setModified();
      }
      return 0;
  }

//  public String getF1Area() {
//    String Text;
//    Text = "��ʽ��Χ:"+
//  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:���������Ϊԭ
  //------------------------------------------------------------------------------------------------
  public String getUIGSX(JReportModel RM) throws Exception {
    if ( RM.getDataStatus() == RM.MODEL_STATUS_DATA ||
         RM.getDataStatus() == RM.MODEL_STATUS_JSGS ) {
      if ( DYZD_GSX != null && DYZD_GSX.trim().length() == 0 ) DYZD_GSX = null;
      if ( DYZD_GSX == null ) return null;
      if ( DYZD_EXGSX == null ) {
        try {
          DYZD_EXGSX = JReportView.FunctionManager.StoreModelToUserModel(
              DYZD_GSX, null, RM);
        } catch ( Exception e ) {
//          DYZD_EXGSX= "#Error!";
            DYZD_EXGSX = DYZD_GSX;
          e.printStackTrace();
        }
      }
      return DYZD_EXGSX;
    }
    if ( RM.getDataStatus() == RM.MODEL_STATUS_JYGS ) {
      return getUIJYGS(RM,0);
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String getUIJYGS(JReportModel RM,int Index) throws Exception {
    JJygsInfo JygsInfo = null;
    if ( JygsList == null ) return null;
    if ( JygsList.size() == 0 ) return null;
    if ( Index < JygsList.size() )
      JygsInfo = (JJygsInfo)JygsList.get(Index);
    if ( JygsInfo != null ) {
      // ���Ϊ�������NULL
      if ( JygsInfo.JYGS_GSX != null && JygsInfo.JYGS_GSX.trim().length() == 0 ){
          JygsInfo.JYGS_GSX = null;
      }
      if ( JygsInfo.JYGS_GSX == null || "".equals(JygsInfo.JYGS_GSX) ) return null;
      if ( JygsInfo.JYGS_EXGSX == null || "".equals(JygsInfo.JYGS_EXGSX))
        JygsInfo.JYGS_EXGSX = JReportView.FunctionManager.StoreModelToUserModel(JygsInfo.JYGS_GSX,null,RM);
      return JygsInfo.JYGS_EXGSX;
    }
    return null;
  }
  public JJygsInfo getUIJygsInfo(JReportModel RM,int Index) {
    JJygsInfo JygsInfo = null;
    if ( JygsList != null && Index < JygsList.size() ) {
      JygsInfo = (JJygsInfo)JygsList.get(Index);
    }
    return JygsInfo;
  }

  public String getUIComment(JReportModel RM, int Index) throws Exception {
      JCommentInfo commentInfo = null;
      if (CommentList == null)return null;
      if (CommentList.size() == 0)return null;
      if (Index < CommentList.size())
          commentInfo = (JCommentInfo) CommentList.get(Index);
      if (commentInfo != null) {
          // ���Ϊ�������NULL
          if (commentInfo.Comment_Info == null || commentInfo.Comment_Info.trim().length() == 0) {
              return "";
          }
          return commentInfo.Comment_Info;
      }
      return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:���������Ϊԭ
  //------------------------------------------------------------------------------------------------
  public String getUIZSGSX(JReportModel RM) throws Exception {
    if ( RM.getDataStatus() == RM.MODEL_STATUS_ZSGS) {
      if ( DYZD_ZSGS != null && DYZD_ZSGS.trim().length() == 0 ) DYZD_ZSGS = null;
      if ( DYZD_ZSGS == null ) return null;
      if ( DYZD_EXZSGS == null ) {
        try {
          DYZD_EXZSGS = JReportView.FunctionManager.StoreModelToUserModel(
        		  DYZD_ZSGS, null, RM);
        } catch ( Exception e ) {
//          DYZD_EXGSX= "#Error!";
            DYZD_EXZSGS = DYZD_ZSGS;
          e.printStackTrace();
        }
      }
      return DYZD_EXZSGS;
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public int getJYGSIndex() {
    return JYGSIndex;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void setJYGSIndex(int i) {
    JYGSIndex = i;
  }
  public static JUnitInfo getGenerUnitInfo(int logRow,int logCol,int phyRow,int phyCol,int Op,JReportModel RM) {
    JUnitInfo UI = null;
    // 0:������ 1:�ɰ�䶯�� 2:�°�䶯���� 3:�°�䶯��
    int RowStatus = JBdhUtils.checkPhyRowStatus(RM,phyRow);
    if ( RowStatus == 3 ) {
      if ( Op == 0 )
        UI = JBdhInfo.getUnitInfo(RM, logRow - 1, logCol - 1, phyRow, phyCol);
      if ( Op == 1 )
        UI = JBdhInfo.getBDUnitInfo(RM, logRow - 1, logCol - 1, phyRow, phyCol);
    } else {
      UI = GetUnitInfoByHL(logRow,logCol,Op,RM);
    }
    return UI;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JUnitInfo GetUnitInfoByHL(int HZB,int LZB,JReportModel RM) {
    return GetUnitInfoByHL(HZB,LZB,0,RM);
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JUnitInfo GetUnitInfoByHL(int HZB,int LZB,int Op,JReportModel RM) {
    JUnitInfo UnitInfo=null;JRowInfo RowInfo;JColInfo ColInfo;
    RowInfo = JRowInfo.GetRowInfoByZB(HZB,Op,RM);
    if ( RowInfo == null ) return null;
    ColInfo = JColInfo.GetColInfoByZB(LZB,Op,RM);
    if ( ColInfo == null ) return null;
    UnitInfo = RowInfo.UnitRowHead;
    while ( UnitInfo != null ) {
      if ( UnitInfo.ColInfo.LZD_ZB == LZB ) return UnitInfo;
      UnitInfo = UnitInfo.RowNextUnit;
    }
    /**
     * ֻ����û�д���ʱ�Ŵ���
     */
    if ( Op == 1) {
      UnitInfo = CreateUI(RowInfo,ColInfo,RM);
      RM.setModified();
    }

    return UnitInfo;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static JUnitInfo CreateUI(JRowInfo RowInfo,JColInfo ColInfo,JReportModel RM) {
    JUnitInfo UnitInfo = new JUnitInfo();
    UnitInfo.ReportModel = RM;
    UnitInfo.ChangeLog = 1; // �½�
    UnitInfo.RowInfo = RowInfo;
    UnitInfo.ColInfo = ColInfo;
    // �������б�
    UnitInfo.RowNextUnit = RowInfo.UnitRowHead;
    if ( RowInfo.UnitRowHead != null ) RowInfo.UnitRowHead.PrioUnit = UnitInfo;
    RowInfo.UnitRowHead = UnitInfo;
    // �������б�
    UnitInfo.ColNextUnit = ColInfo.UnitColHead;
    if ( ColInfo.UnitColHead != null ) ColInfo.UnitColHead.PrioUnit = UnitInfo;
      ColInfo.UnitColHead = UnitInfo;
    return UnitInfo;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static JUnitInfo GetUnitInfoByOrde(String HOrde,String LOrde,int Op,JReportModel RM) {
    JUnitInfo UnitInfo=null;JRowInfo RowInfo;JColInfo ColInfo;
    RowInfo = JRowInfo.GetRowInfoByOrde(HOrde,RM);
    if ( RowInfo == null ) return null;
    ColInfo = JColInfo.GetColInfoByOrde(LOrde,RM);
    if ( ColInfo == null ) return null;
    UnitInfo = RowInfo.UnitRowHead;
    while (UnitInfo != null) {
      if (UnitInfo.ColInfo.LZD_ORDE.equals(LOrde))return UnitInfo;
      UnitInfo = UnitInfo.RowNextUnit;
    }
    /**
     * ֻ����û�д���ʱ�Ŵ���
     * ��ֹ�ظ�����
     */
    if ( Op == 1) {
      UnitInfo = CreateUI(RowInfo,ColInfo,RM);
    }

    return UnitInfo;
  }
  public int getPhyHZD_ZB() {
    return JBdhUtils.getPhyRow(RowInfo.HZD_ZB-1,ReportModel);
  }
  public int getHZD_ZB() {
    return RowInfo.HZD_ZB;
  }
  public int getLZD_ZB() {
    return ColInfo.LZD_ZB;
  }
}
