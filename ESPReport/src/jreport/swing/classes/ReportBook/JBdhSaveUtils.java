package jreport.swing.classes.ReportBook;

import jreport.swing.classes.*;
import jreport.foundation.classes.*;
import org.jdom.*;

import com.efounder.pub.util.NumberFunction;

import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBdhSaveUtils {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
  public JBdhSaveUtils() {
  }
  public static void saveBDSJ(JReportModel RM,JReportDataObject RDO,Element Rows,Element Row,JBdhInfo BdhInfo,JBDHDataInfo BDHDataInfo,int Index,String Ext) {
    // 设置删除的变动数据
   if ( "DEL".equals(Ext) ) {
     saveBDDELSJ(RM,RDO,Rows,Row,BdhInfo,BDHDataInfo,Index);
     return;
   }
   if("TZSJ".equals(Ext)){//报表调整时取数
     // 如果没有修改过，返回
     //   if ( BDHDataInfo.ChangeLog == 0 ) return;
     // BDH_ORDE    HZD_ORDE    BD_OFFSET    BDSJ_HBM
   RDO.SetElementValue(Row,"BDH_ORDE",BdhInfo.BDH_ORDE);
   RDO.SetElementValue(Row,"HZD_ORDE",BdhInfo.RowInfo.HZD_ORDE);
   RDO.SetElementValue(Row,"BD_OFFSET",String.valueOf(BDHDataInfo.BD_OFFSET));
   RDO.SetElementValue(Row,"NEW_BD_OFFSET",String.valueOf(Index+1));// 新的偏移量
   RDO.SetElementValue(Row,"BDSJ_HBM",BDHDataInfo.BDSJ_HBM);
   RDO.SetElementValue(Row,"LS",String.valueOf(RM.BBZD_LS));
   //BDSJ_TEXTxxBDSJ_NUMxx
   JBDUnitInfo UI = null;String Tmp;String TextName,NumName;
   String Tmp1,Tmp2;
   int hzb,lzb;
   JUnitInfo SUI = null;
   String DYZD_SJ;double DYZD_DATA;
   for(int Col=1;Col<=RM.BBZD_LS;Col++) {
     UI = BDHDataInfo.getUnitInfo(Col-1);

     hzb = BDHDataInfo.BdhInfo.RowInfo.HZD_ZB;
     lzb = Col;
     SUI = JUnitInfo.GetUnitInfoByHL(hzb,lzb,1,RM);

     if(SUI.DYZD_TZGS !=null&& SUI.DYZD_TZGS.trim().length()>0){
       if ( Col < 10 ) Tmp = "0"+String.valueOf(Col);else Tmp = String.valueOf(Col);
       TextName = "T"+Tmp;NumName = "N"+Tmp;
       if ( UI == null ) {
         DYZD_SJ = "";DYZD_DATA=0.00;
       } else {
         //DYZD_SJ = UI.DYZD_SJ;
         DYZD_SJ = UI.TZZD_SJ;
         /**
          * 因为BDSJ表中的DYZD_DATA列是numeric(19,6)的，小数点最长为６位
          * 所以在保存前要将小数点四舍五入到第６位
          * modified by hufeng 2006.1.20
          */
         DYZD_DATA=NumberFunction.round(UI.TZZD_DATA,6);
         //DYZD_DATA=NumberFunction.round(UI.DYZD_DATA,6);
       }
       RDO.SetElementValue(Row,NumName,String.valueOf(DYZD_DATA));
       RDO.SetElementValue(Row,TextName,DYZD_SJ==null?"":DYZD_SJ);

     }else{
       if ( Col < 10 ) Tmp = "0"+String.valueOf(Col);else Tmp = String.valueOf(Col);
       TextName = "T"+Tmp;NumName = "N"+Tmp;
       DYZD_SJ = "";DYZD_DATA=0.00;
       RDO.SetElementValue(Row,NumName,String.valueOf(DYZD_DATA));
       RDO.SetElementValue(Row,TextName,DYZD_SJ==null?"":DYZD_SJ);

     }

   }

   }else{
     // 如果没有修改过，返回
//   if ( BDHDataInfo.ChangeLog == 0 ) return;
   // BDH_ORDE    HZD_ORDE    BD_OFFSET    BDSJ_HBM
   RDO.SetElementValue(Row,"BDH_ORDE",BdhInfo.BDH_ORDE);
   RDO.SetElementValue(Row,"HZD_ORDE",BdhInfo.RowInfo.HZD_ORDE);
   RDO.SetElementValue(Row,"BD_OFFSET",String.valueOf(BDHDataInfo.BD_OFFSET));
   RDO.SetElementValue(Row,"NEW_BD_OFFSET",String.valueOf(Index+1));// 新的偏移量
   RDO.SetElementValue(Row,"BDSJ_HBM",BDHDataInfo.BDSJ_HBM);
   RDO.SetElementValue(Row,"LS",String.valueOf(RM.BBZD_LS));
   //BDSJ_TEXTxxBDSJ_NUMxx
   JBDUnitInfo UI = null;String Tmp;String TextName,NumName;
   String DYZD_SJ;double DYZD_DATA;
   for(int Col=1;Col<=RM.BBZD_LS;Col++) {
     UI = BDHDataInfo.getUnitInfo(Col-1);
     if ( Col < 10 ) Tmp = "0"+String.valueOf(Col);else Tmp = String.valueOf(Col);
     TextName = "T"+Tmp;NumName = "N"+Tmp;
     if ( UI == null ) {
       DYZD_SJ = "";DYZD_DATA=0.00;
     } else {
       DYZD_SJ = UI.DYZD_SJ;
       /**
        * 因为BDSJ表中的DYZD_DATA列是numeric(19,6)的，小数点最长为６位
        * 所以在保存前要将小数点四舍五入到第６位
        * modified by hufeng 2006.1.20
        */
       DYZD_DATA=NumberFunction.round(UI.DYZD_DATA,6);
     }
     RDO.SetElementValue(Row,NumName,String.valueOf(DYZD_DATA));
     RDO.SetElementValue(Row,TextName,DYZD_SJ==null?"":DYZD_SJ);
   }

   }


  }
  public static void saveBDDELSJ(JReportModel RM,JReportDataObject RDO,Element Rows,Element Row,JBdhInfo BdhInfo,JBDHDataInfo BDHDataInfo,int Index) {
    // BDH_ORDE    HZD_ORDE    BD_OFFSET    BDSJ_HBM
    RDO.SetElementValue(Row,"BDH_ORDE",BdhInfo.BDH_ORDE);
    RDO.SetElementValue(Row,"HZD_ORDE",BdhInfo.RowInfo.HZD_ORDE);
    RDO.SetElementValue(Row,"BD_OFFSET",String.valueOf(BDHDataInfo.BD_OFFSET));
  }
  public static void saveBDFMT(JReportModel RM,JReportDataObject RDO,Element Rows,Element Row,JBdhInfo BdhInfo,JBDHDataInfo BDHDataInfo,JBDUnitInfo UI,int Index,String Ext) {
// 如果OFFSET没有进行调整,并且计算公式,校验公式没有修改过,说明这一行的格式没有修改过 BD_OFFSET==0 说明是新插入的
    if ( "JS".equals(Ext) ) {
      // 如果是新建的行,并且没有修改计算公式,返回
      if ( BDHDataInfo.BD_OFFSET == 0 && UI.ChangeLogJs == 0 ) return;
      // 如果行坐标没有改变且公式没有修改,返回
      if ( BDHDataInfo.BD_OFFSET == (Index+1) && UI.ChangeLogJs == 0 ) return;
      saveBDJS(RM,RDO,Rows,Row,BdhInfo,BDHDataInfo,UI,Index);
    }
    if ( "JY".equals(Ext) ) {
      if ( BDHDataInfo.BD_OFFSET == 0 && UI.ChangeLogJy == 0 ) return;
      // 如果行坐标没有改变且公式没有修改,返回
      if ( BDHDataInfo.BD_OFFSET == (Index+1) && UI.ChangeLogJy == 0 ) return;
      saveBDJY(RM,RDO,Rows,Row,BdhInfo,BDHDataInfo,UI,Index);
    }
    if ( "TZ".equals(Ext) ) {
//      // 如果是新建的行,并且没有修改计算公式,返回
//      if ( BDHDataInfo.BD_OFFSET == 0 && UI.ChangeLogJs == 0 ) return;
//      // 如果行坐标没有改变且公式没有修改,返回
//      if ( BDHDataInfo.BD_OFFSET == (Index+1) && UI.ChangeLogJs == 0 ) return;
//      saveBDJS(RM,RDO,Rows,Row,BdhInfo,BDHDataInfo,UI,Index);
    }
    if ( "ZS".equals(Ext) ) {
        // 如果是新建的行,并且没有修改计算公式,返回
        if ( BDHDataInfo.BD_OFFSET == 0 && UI.ChangeLogZs == 0 ) return;
        // 如果行坐标没有改变且公式没有修改,返回
        if ( BDHDataInfo.BD_OFFSET == (Index+1) && UI.ChangeLogZs == 0 ) return;
        saveBDZS(RM,RDO,Rows,Row,BdhInfo,BDHDataInfo,UI,Index);
      }

  }
  public static void saveBDJS(JReportModel RM,JReportDataObject RDO,Element Rows,Element Row,JBdhInfo BdhInfo,JBDHDataInfo BDHDataInfo,JBDUnitInfo UI,int Index) {
//    DYZD_HOFFSET DYZD_LOFFSET DYZD_GSX
    if ( UI.ChangeLogJs == 0 && ( UI.DYZD_GSX == null || "".equals(UI.DYZD_GSX.trim()) ) ) return;
    if ( UI.DYZD_GSX == null ) UI.DYZD_GSX = "";
    Row = RDO.AddChildElement(Rows, "R");
    RDO.SetElementValue(Row,"BDH_ORDE",BdhInfo.BDH_ORDE);
    RDO.SetElementValue(Row,"HZD_ORDE",BdhInfo.RowInfo.HZD_ORDE);
    RDO.SetElementValue(Row,"LZD_ORDE",UI.ColInfo.LZD_ORDE);
    RDO.SetElementValue(Row,"BD_OFFSET",String.valueOf(BDHDataInfo.BD_OFFSET));
    RDO.SetElementValue(Row,"NEW_BD_OFFSET",String.valueOf(Index+1));// 新的偏移量

    RDO.SetElementValue(Row,"DYZD_HOFFSET",String.valueOf(UI.DYZD_HOFFSET));
    RDO.SetElementValue(Row,"DYZD_LOFFSET",String.valueOf(UI.DYZD_LOFFSET));
    RDO.SetElementValue(Row,"DYZD_GSX",String.valueOf(UI.DYZD_GSX));
  }
  public static void saveBDZS(JReportModel RM,JReportDataObject RDO,Element Rows,Element Row,JBdhInfo BdhInfo,JBDHDataInfo BDHDataInfo,JBDUnitInfo UI,int Index) {
//    DYZD_HOFFSET DYZD_LOFFSET DYZD_GSX
    if ( UI.ChangeLogZs == 0 && ( UI.DYZD_ZSGS == null || "".equals(UI.DYZD_ZSGS.trim()) ) ) return;
    if ( UI.DYZD_ZSGS == null ) UI.DYZD_ZSGS = "";
    Row = RDO.AddChildElement(Rows, "R");
    RDO.SetElementValue(Row,"BDH_ORDE",BdhInfo.BDH_ORDE);
    RDO.SetElementValue(Row,"HZD_ORDE",BdhInfo.RowInfo.HZD_ORDE);
    RDO.SetElementValue(Row,"LZD_ORDE",UI.ColInfo.LZD_ORDE);
    RDO.SetElementValue(Row,"BD_OFFSET",String.valueOf(BDHDataInfo.BD_OFFSET));
    RDO.SetElementValue(Row,"NEW_BD_OFFSET",String.valueOf(Index+1));// 新的偏移量

    RDO.SetElementValue(Row,"DYZD_ZSHOFFSET",String.valueOf(UI.DYZD_ZSHOFFSET));
    RDO.SetElementValue(Row,"DYZD_ZSLOFFSET",String.valueOf(UI.DYZD_ZSLOFFSET));
    RDO.SetElementValue(Row,"DYZD_ZSGS",String.valueOf(UI.DYZD_ZSGS));
  }  
  public static void saveBDTZ(JReportModel RM,JReportDataObject RDO,Element Rows,Element Row,JBdhInfo BdhInfo,JBDHDataInfo BDHDataInfo,JBDUnitInfo UI,int Index) {
//    DYZD_HOFFSET DYZD_LOFFSET DYZD_GSX
  if ( UI.ChangeLogTz == 0 && ( UI.DYZD_TZGS == null || "".equals(UI.DYZD_TZGS.trim()) ) ) return;
  if ( UI.DYZD_TZGS == null ) UI.DYZD_TZGS = "";
  Row = RDO.AddChildElement(Rows, "R");
  RDO.SetElementValue(Row,"BDH_ORDE",BdhInfo.BDH_ORDE);
  RDO.SetElementValue(Row,"HZD_ORDE",BdhInfo.RowInfo.HZD_ORDE);
  RDO.SetElementValue(Row,"LZD_ORDE",UI.ColInfo.LZD_ORDE);
  RDO.SetElementValue(Row,"TZGS_GSX",String.valueOf(UI.DYZD_TZGS));
}

  public static void saveBDJY(JReportModel RM,JReportDataObject RDO,Element Rows,Element Row,JBdhInfo BdhInfo,JBDHDataInfo BDHDataInfo,JBDUnitInfo UI,int Index) {
//    JYGS_ORDE DYZD_HOFFSET DYZD_LOFFSET JYGS_BJ JYGS_EXP JYGS_SHOW JYGS_TYPE JYGS_STYLE
    if ( UI.ChangeLogJy == 0 ) return;
//    if ( UI.ChangeLogJs == 0 && ( UI.JygsList == null || UI.JygsList.size()==0 ) ) return;
    Row = RDO.AddChildElement(Rows, "R");
    RDO.SetElementValue(Row,"BDH_ORDE",BdhInfo.BDH_ORDE);
    RDO.SetElementValue(Row,"HZD_ORDE",BdhInfo.RowInfo.HZD_ORDE);
    RDO.SetElementValue(Row,"LZD_ORDE",UI.ColInfo.LZD_ORDE);
    RDO.SetElementValue(Row,"BD_OFFSET",String.valueOf(BDHDataInfo.BD_OFFSET));
    RDO.SetElementValue(Row,"NEW_BD_OFFSET",String.valueOf(Index+1));// 新的偏移量

    if ( UI.JygsList == null || UI.JygsList.size() <= 0 ) return;
    int Count = UI.JygsList.size();
    JJygsInfo JygsInfo = null;Element J = null;
    for(int i=0;i<Count;i++) {
      JygsInfo = (JJygsInfo)UI.JygsList.get(i);
      /**
       * 如果当前是单位报表，则不处理报表格式中的校验公式
       * modified by hufeng 2005.11.24
       */
      if(RM.ADD_BH != null && JygsInfo.JYGS_FROM.equals(JJygsInfo.FROM_XTBB)){
        continue;
      }
      if ( JygsInfo.JYGS_GSX == null ) JygsInfo.JYGS_GSX = "";
      if("".equals(JygsInfo.JYGS_GSX.trim())){
        continue;
      }
//      if ( UI.ChangeLogJs == 0 && ( JygsInfo.JYGS_GSX == null || "".equals(JygsInfo.JYGS_GSX.trim()) ) ) return;
      J        = RDO.AddChildElement(Row, "J");
      RDO.SetElementValue(J,"JYGS_ORDE",JygsInfo.JYGS_ORDE);
      RDO.SetElementValue(J,"DYZD_HOFFSET",String.valueOf(JygsInfo.JYGS_HOFFSET));
      RDO.SetElementValue(J,"DYZD_LOFFSET",String.valueOf(JygsInfo.JYGS_HOFFSET));
      RDO.SetElementValue(J,"JYGS_BJ",JygsInfo.JYGS_BJ);
      RDO.SetElementValue(J,"JYGS_EXP",JygsInfo.JYGS_GSX);
      RDO.SetElementValue(J,"JYGS_SHOW",JygsInfo.JYGS_SHOW);
      RDO.SetElementValue(J,"JYGS_TYPE",String.valueOf(JygsInfo.JYGS_TYPE));
      RDO.SetElementValue(J,"JYGS_STYLE",String.valueOf(JygsInfo.JYGS_STYLE));
    }

  }

}
