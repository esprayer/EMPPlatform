package jreport.swing.classes.util;

import java.util.*;
import jtoolkit.xml.classes.*;
import org.jdom.*;
import jreport.swing.classes.*;
import jreport.swing.classes.ReportBook.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class FormulaSelection {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.util.Language");
  /**
   * 公式内容前缀,剪贴板中的公式内容需要以此为前缀
   */
  public final static String FORMULA_PREFIX="$Formula$";
  public final static String FORMULA_ROOT="Formulas";

  private JDOMXMLBaseObject mXMLObj = null;
  /**
   *
   */
  public FormulaSelection() {
    mXMLObj = new JDOMXMLBaseObject();
    Element rootElmt = mXMLObj.AddChildElement(null,FORMULA_ROOT);
  }

  public FormulaSelection(String formula){
    /**
     * 需要去除前缀
     */
    if(formula != null && formula.length()>0 &&formula.startsWith(FORMULA_PREFIX)){
          mXMLObj = new JDOMXMLBaseObject(formula.substring(9));
    }
  }
  /**
   *
   * @return
   */
  public Vector formulas(JReportBook reportBook){

    Vector formulas= new Vector();
    if(reportBook == null){
      return formulas;
    }
    JReportModel model = (JReportModel) reportBook.ReportView.getModel();
    int status = model.getDataStatus();
    if(mXMLObj != null){
      Element rootElmt = mXMLObj.GetElementByName(FORMULA_ROOT);
      for(Iterator i=rootElmt.getChildren().iterator();i.hasNext();){
        Element crtSelElmt = (Element)i.next();
        FormulaInfo formulaStub = new FormulaInfo();

        formulaStub.mRow = Integer.parseInt(crtSelElmt.getAttributeValue("Row"));
        formulaStub.mCol = Integer.parseInt(crtSelElmt.getAttributeValue("Col"));
        formulaStub.mRowOffset = Integer.parseInt(crtSelElmt.getAttributeValue("RowOffset"));
        formulaStub.mColOffset = Integer.parseInt(crtSelElmt.getAttributeValue("ColOffset"));

        for(Iterator it=crtSelElmt.getChildren().iterator();it.hasNext();){
            Element crtFormulaElmt = (Element) it.next();
          //计算公式状态
          if (status == JReportModel.MODEL_STATUS_JSGS) {

            String crtFormula = crtFormulaElmt.getAttributeValue("Value");
            formulaStub.mFormulas.add(crtFormula);
          }
          //校验公式状态
          else if (status == JReportModel.MODEL_STATUS_JYGS) {
            JJygsInfo jygsInfo  = new JJygsInfo();
            jygsInfo.JYGS_ORDE  = crtFormulaElmt.getAttributeValue("Orde");
            jygsInfo.JYGS_GSX   = crtFormulaElmt.getAttributeValue("Gsx");
            jygsInfo.JYGS_EXGSX = crtFormulaElmt.getAttributeValue("Exgsx");
            jygsInfo.JYGS_BJ    = crtFormulaElmt.getAttributeValue("Bj");
            jygsInfo.JYGS_HOFFSET = Integer.parseInt(crtFormulaElmt.getAttributeValue("RowOffset"));
            jygsInfo.JYGS_LOFFSET = Integer.parseInt(crtFormulaElmt.getAttributeValue("ColOffset"));
            formulaStub.mFormulas.add(jygsInfo);
          }
          else if (status == JReportModel.MODEL_STATUS_ZSGS) {
              String crtFormula = crtFormulaElmt.getAttributeValue("Value");
              formulaStub.mFormulas.add(crtFormula);
            }
        }
        formulas.add(formulaStub);
      }
    }
    return formulas;
  }
  /**
   * @param row
   * @param col
   * @param rowOffset
   * @param colOffset
   * @param formulas
   * @param reportBook
   */
  public void addFormula(int row,int col,int rowOffset,int colOffset,Vector formulas,JReportBook reportBook){
    if(formulas != null && reportBook != null){
      try {
        Element rootElmt = mXMLObj.GetElementByName(FORMULA_ROOT);
        Element selElmt = mXMLObj.AddChildElement(rootElmt, "Selection");
        selElmt.setAttribute("Row", "" + row);
        selElmt.setAttribute("Col", "" + col);
        selElmt.setAttribute("RowOffset", "" + rowOffset);
        selElmt.setAttribute("ColOffset", "" + colOffset);

        JReportModel model = (JReportModel) reportBook.ReportView.getModel();
        int status = model.getDataStatus();

        for (Enumeration e = formulas.elements(); e.hasMoreElements(); ) {
          Element formulaElmt = mXMLObj.AddChildElement(selElmt, "Formula");

          //计算公式状态
          if (status == JReportModel.MODEL_STATUS_JSGS) {
            String formula = (String) e.nextElement();
            if (formula != null && formula.length() > 0) {
              formulaElmt.setAttribute("Value", formula);
            }
//            else {
//              formulaElmt.setAttribute("Value", "");
//            }
          }

          //校验公式状态
          else if (status == JReportModel.MODEL_STATUS_JYGS) {
            JJygsInfo jygsInfo = (JJygsInfo)e.nextElement();
            if (jygsInfo != null) {
              formulaElmt.setAttribute("Orde", jygsInfo.JYGS_ORDE);
              formulaElmt.setAttribute("Gsx", jygsInfo.JYGS_GSX);
              formulaElmt.setAttribute("Exgsx", jygsInfo.JYGS_EXGSX);
              formulaElmt.setAttribute("Bj", jygsInfo.JYGS_BJ);
              formulaElmt.setAttribute("RowOffset",jygsInfo.JYGS_HOFFSET+"");
              formulaElmt.setAttribute("ColOffset",jygsInfo.JYGS_LOFFSET+"");
            }
          }
          //调整式状态
          else if (status == JReportModel.MODEL_STATUS_TZGS) {
            JTzgsInfo tzgsInfo = (JTzgsInfo)e.nextElement();
            if (tzgsInfo != null) {
             // formulaElmt.setAttribute("Orde", tzgsInfo.TZGS_ORDE);
              formulaElmt.setAttribute("Gsx", tzgsInfo.TZGS_GSX);
              formulaElmt.setAttribute("Exgsx", tzgsInfo.TZGS_EXGSX);
             // formulaElmt.setAttribute("Bj", tzgsInfo.TZGS_BJ);
              formulaElmt.setAttribute("RowOffset",tzgsInfo.TZGS_HZD+"");
              formulaElmt.setAttribute("ColOffset",tzgsInfo.TZGS_LZD+"");
            }
          }
          else if (status == JReportModel.MODEL_STATUS_ZSGS) {
              String formula = (String) e.nextElement();
              if (formula != null && formula.length() > 0) {
                formulaElmt.setAttribute("Value", formula);
              }
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  /**
   *
   * @return
   */
  public String toString(){
    if(mXMLObj!= null){
      return FORMULA_PREFIX+mXMLObj.GetRootXMLString();
    }
    return "";
  }

}
