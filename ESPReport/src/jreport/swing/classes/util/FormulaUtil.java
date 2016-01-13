package jreport.swing.classes.util;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.datatransfer.*;

import jreport.swing.classes.*;
import jreport.swing.classes.ReportBook.*;
import jreportwh.jdof.classes.common.util.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class FormulaUtil {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.util.Language");
  /**
   *
   * @param formula
   */
  public FormulaUtil() {
  }

  /**
   * ����ʽ���Ƶ�������
   */
  public static void copyFormula(JReportBook reportBook) {
    //��֯��ʽ
    FormulaSelection formulaInfo = getFormula(reportBook);
    if (formulaInfo != null) {
      setClipBoardContents(formulaInfo.toString());
    }
  }

  /**
   *
   * @param reportBook
   * @return
   */
  private static FormulaSelection getFormula(JReportBook reportBook) {
    if (reportBook == null) {
      return null;
    }
    try {
      JReportModel model = (JReportModel) reportBook.ReportView.getModel();
      JReportView book = reportBook.ReportView;
      FormulaSelection formulaInfo = new FormulaSelection();
      if (book.getSelectionCount() == 0) {
        return null;
      }
      int startRow = book.getSelStartRow();
      int startCol = book.getSelStartCol();
      int endRow = book.getSelEndRow();
      int endCol = book.getSelEndCol();
      int phySRow = book.getPhySelStartRow();
      int phyERow = book.getPhySelEndRow();
      int phyRow = phySRow;
      int phyCol = endCol;
      int temp = 0;
      if (endRow < startRow) {
        temp = startRow;
        startRow = endRow;
        endRow = temp;
      }
      if (endCol < startCol) {
        temp = startCol;
        startCol = endCol;
        endCol = temp;
      }
      if (endRow > model.BBZD_HS) {
        endRow = model.BBZD_HS;
      }
      if (endCol > model.BBZD_LS) {
        endCol = model.BBZD_LS;
      }

      if (model != null && book != null) {
        int status = model.getDataStatus();
        //����״̬
        if (status == JReportModel.MODEL_STATUS_DATA) {
          return null;
        }

        //���㹫ʽ״̬
        else if (status == JReportModel.MODEL_STATUS_JSGS) {
          phyRow = phySRow;
          for (int i = startRow; i <= endRow; i++) {
            phyCol = endCol;
            for (int j = startCol; j <= endCol; j++) {
              /**
               * ��phyco��Ϊ��ǰ�У���ʹ�䶯�п�����������
               * modified by hufeng 2005.11.15
               */
              JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i + 1, j + 1,
                  phyRow, j, 0, model); // ��ȡUnitInfo
              if (unitInfo != null) {
                String formula = unitInfo.getUIGSX(model);
                Vector formulaList = new Vector();
                if (formula != null) {
                  formulaList.add(formula);
                }
                formulaInfo.addFormula(i, j, unitInfo.DYZD_HOFFSET,
                                       unitInfo.DYZD_LOFFSET, formulaList,
                                       reportBook);
              }
              phyCol++;
            }
            phyRow++;
          }
          return formulaInfo;
        }

        //У�鹫ʽ״̬
        else if (status == JReportModel.MODEL_STATUS_JYGS) {
          phyRow = phySRow;
          for (int i = startRow; i <= endRow; i++) {
            phyCol = endCol;
            for (int j = startCol; j <= endCol; j++) {
              JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i + 1, j + 1,
                  phyRow, phyCol, 0, model); // ��ȡUnitInfo
              if (unitInfo != null && unitInfo.JygsList != null &&
                  unitInfo.JygsList.size() > 0) {
                Vector jygsList = unitInfo.JygsList;
                formulaInfo.addFormula(i, j, unitInfo.DYZD_HOFFSET,
                                       unitInfo.DYZD_LOFFSET, jygsList,
                                       reportBook);
              }
              phyCol++;
            }
            phyRow++;
          }
          return formulaInfo;
        }
        //������ʽ״̬
        else if (status == JReportModel.MODEL_STATUS_TZGS) {
          phyRow = phySRow;
          for (int i = startRow; i <= endRow; i++) {
            phyCol = endCol;
            for (int j = startCol; j <= endCol; j++) {
              JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i + 1, j + 1,
                  phyRow, phyCol, 0, model); // ��ȡUnitInfo
              if (unitInfo != null && unitInfo.JygsList != null &&
                  unitInfo.TzgsList.size() > 0) {
                Vector tzgsList = unitInfo.TzgsList;
                formulaInfo.addFormula(i, j, unitInfo.DYZD_HOFFSET,
                                       unitInfo.DYZD_LOFFSET, tzgsList,
                                       reportBook);
              }
              phyCol++;
            }
            phyRow++;
          }
          return formulaInfo;
        }
        else if (status == JReportModel.MODEL_STATUS_ZSGS) {
            phyRow = phySRow;
            for (int i = startRow; i <= endRow; i++) {
              phyCol = endCol;
              for (int j = startCol; j <= endCol; j++) {
                /**
                 * ��phyco��Ϊ��ǰ�У���ʹ�䶯�п�����������
                 * modified by hufeng 2005.11.15
                 */
                JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i + 1, j + 1,
                    phyRow, j, 0, model); // ��ȡUnitInfo
                if (unitInfo != null) {
                  String formula = unitInfo.getUIZSGSX(model);
                  Vector formulaList = new Vector();
                  if (formula != null) {
                    formulaList.add(formula);
                  }
                  formulaInfo.addFormula(i, j, unitInfo.DYZD_ZSHOFFSET,
                                         unitInfo.DYZD_ZSLOFFSET, formulaList,
                                         reportBook);
                }
                phyCol++;
              }
              phyRow++;
            }
            return formulaInfo;
        }        
        
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * ���������еĹ�ʽճ��
   */
  public static void pasteFormula(JReportBook reportBook) {
    if (reportBook == null) {
      return;
    }

    //��ü���������
    String contents = getClipBoardContents();
    if (contents != null && contents.length() > 0 &&
        contents.startsWith(FormulaSelection.FORMULA_PREFIX)) {
      FormulaSelection formulaInfo = new FormulaSelection(contents);
      pasteFormula(reportBook, formulaInfo);
    }
  }

  /**
   *
   * @param book
   * @param formulaInfo
   */
  private static void pasteFormula(JReportBook reportBook,
                                   FormulaSelection formulaInfo) {
    if (reportBook != null && formulaInfo != null) {
      Vector formulas = formulaInfo.formulas(reportBook);
      if (reportBook == null || formulas.size() == 0) {
        return;
      }
      try {
        JReportModel model = (JReportModel) reportBook.ReportView.getModel();
        JReportView book = reportBook.ReportView;
        if (book.getSelectionCount() == 0) {
          return;
        }
        int phyrow;
        int phySRow = book.getPhySelStartRow();
        int phyERow = book.getPhySelEndRow();
        int startRow = book.getSelStartRow();
        int startCol = book.getSelStartCol();
        int endRow = book.getSelEndRow();
        int endCol = book.getSelEndCol();
        int temp = 0;
        if (endRow < startRow) {
          temp = startRow;
          startRow = endRow;
          endRow = temp;
        }
        if (endCol < startCol) {
          temp = startCol;
          startCol = endCol;
          endCol = temp;
        }

        if (model != null && book != null) {
          int status = model.getDataStatus();

          //����״̬
          if (status == JReportModel.MODEL_STATUS_DATA) {
            return;
          }

          //���㹫ʽ״̬
          else if (status == JReportModel.MODEL_STATUS_JSGS) {

            int rowOffset = 0;
            int colOffset = 0;
            int phyrowOffset = 0;

            if (formulas.size() > 0) {
              FormulaInfo formulaStub = (FormulaInfo) formulas.get(0);
              rowOffset = startRow - formulaStub.mRow + 1;
              colOffset = startCol - formulaStub.mCol + 1;
              phyrowOffset = phySRow - formulaStub.mRow;
            }
            for (Enumeration e = formulas.elements(); e.hasMoreElements(); ) {
              FormulaInfo formulaStub = (FormulaInfo) e.nextElement();
              int row = formulaStub.mRow + rowOffset;
              phyrow = formulaStub.mRow + phyrowOffset;
              int col = formulaStub.mCol + colOffset;
              String formula = null;

              if (formulaStub.mFormulas.size() > 0) {
                formula = (String) formulaStub.mFormulas.get(0);
              }
              if (formula != null && formula.length() > 0) {
                JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(row, col,
                    phyrow, col - 1, 1, model);
                if (unitInfo != null) {
                  unitInfo.setUIGSX(formula, row, col, model);
                  unitInfo.DYZD_HOFFSET = formulaStub.mRowOffset;
                  unitInfo.DYZD_LOFFSET = formulaStub.mColOffset;
                  reportBook.ReportView.setPhyText(unitInfo.getPhyHZD_ZB(),
                      unitInfo.ColInfo.LZD_ZB - 1,
                      res.getString("String_150"));
                  unitInfo.setJSModified();
                  unitInfo.setModified();
                }
              }
            }
          }

          //У�鹫ʽ״̬
          else if (status == JReportModel.MODEL_STATUS_JYGS) {

            int rowOffset = 0;
            int colOffset = 0;
            int phyrowOffset = 0;
            if (formulas.size() > 0) {
              FormulaInfo formulaStub = (FormulaInfo) formulas.get(0);
              rowOffset = startRow - formulaStub.mRow + 1;
              colOffset = startCol - formulaStub.mCol + 1;
              phyrowOffset = phySRow - formulaStub.mRow;
            }
            for (Enumeration e = formulas.elements(); e.hasMoreElements(); ) {
              FormulaInfo formulaStub = (FormulaInfo) e.nextElement();
              int row = formulaStub.mRow + rowOffset;
              phyrow = formulaStub.mRow + phyrowOffset;
              int col = formulaStub.mCol + colOffset;
              JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(row, col, phyrow,
                  col - 1, 1, model);
              if (unitInfo != null) {

                //��Ŵ���
                for (Enumeration em = formulaStub.mFormulas.elements();
                     em.hasMoreElements(); ) {
                  JJygsInfo jygsInfo = (JJygsInfo) em.nextElement();
                  jygsInfo.JYGS_ORDE = (model.JygsMaxOrde + 1) + "";
                  model.JygsMaxOrde++;
                }

                unitInfo.JygsList = formulaStub.mFormulas;
                unitInfo.DYZD_HOFFSET = formulaStub.mRowOffset;
                unitInfo.DYZD_LOFFSET = formulaStub.mColOffset;
                reportBook.ReportView.setPhyText(unitInfo.getPhyHZD_ZB(),
                                                 unitInfo.ColInfo.LZD_ZB - 1,
                                                 res.getString("String_152"));
                unitInfo.setJYModified();
                unitInfo.setModified();
              }

            }

          }
          //������ʽ״̬
          else if (status == JReportModel.MODEL_STATUS_TZGS) {

            int rowOffset = 0;
            int colOffset = 0;
            int phyrowOffset = 0;
            if (formulas.size() > 0) {
              FormulaInfo formulaStub = (FormulaInfo) formulas.get(0);
              rowOffset = startRow - formulaStub.mRow + 1;
              colOffset = startCol - formulaStub.mCol + 1;
              phyrowOffset = phySRow - formulaStub.mRow;
            }
            for (Enumeration e = formulas.elements(); e.hasMoreElements(); ) {
              FormulaInfo formulaStub = (FormulaInfo) e.nextElement();
              int row = formulaStub.mRow + rowOffset;
              phyrow = formulaStub.mRow + phyrowOffset;
              int col = formulaStub.mCol + colOffset;
              JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(row, col, phyrow,
                  col - 1, 1, model);
              if (unitInfo != null) {

//                //��Ŵ���
//                for (Enumeration em = formulaStub.mFormulas.elements();
//                     em.hasMoreElements(); ) {
//                  JTzgsInfo tzgsInfo = (JTzgsInfo) em.nextElement();
////                  tzgsInfo.TZGS_ORDE = (model.TzgsMaxOrde + 1) + "";
////                  model.TzgsMaxOrde++;
//                }

                unitInfo.TzgsList = formulaStub.mFormulas;
                unitInfo.DYZD_HOFFSET = formulaStub.mRowOffset;
                unitInfo.DYZD_LOFFSET = formulaStub.mColOffset;
                reportBook.ReportView.setPhyText(unitInfo.getPhyHZD_ZB(),
                                                 unitInfo.ColInfo.LZD_ZB - 1,
                                                 res.getString("String_153"));
                unitInfo.setJYModified();
                unitInfo.setModified();
              }
            }
          }
          else if (status == JReportModel.MODEL_STATUS_ZSGS) {

              int rowOffset = 0;
              int colOffset = 0;
              int phyrowOffset = 0;

              if (formulas.size() > 0) {
                FormulaInfo formulaStub = (FormulaInfo) formulas.get(0);
                rowOffset = startRow - formulaStub.mRow + 1;
                colOffset = startCol - formulaStub.mCol + 1;
                phyrowOffset = phySRow - formulaStub.mRow;
              }
              for (Enumeration e = formulas.elements(); e.hasMoreElements(); ) {
                FormulaInfo formulaStub = (FormulaInfo) e.nextElement();
                int row = formulaStub.mRow + rowOffset;
                phyrow = formulaStub.mRow + phyrowOffset;
                int col = formulaStub.mCol + colOffset;
                String formula = null;

                if (formulaStub.mFormulas.size() > 0) {
                  formula = (String) formulaStub.mFormulas.get(0);
                }
                if (formula != null && formula.length() > 0) {
                  JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(row, col,
                      phyrow, col - 1, 1, model);
                  if (unitInfo != null) {
                    unitInfo.setUIZSGSX(formula, row, col, model);
                    unitInfo.DYZD_ZSHOFFSET = formulaStub.mRowOffset;
                    unitInfo.DYZD_ZSLOFFSET = formulaStub.mColOffset;
                    reportBook.ReportView.setPhyText(unitInfo.getPhyHZD_ZB(),
                        unitInfo.ColInfo.LZD_ZB - 1,
                        "<����>");
                    unitInfo.setZSModified();
                    unitInfo.setModified();
                  }
                }
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
   * ��ü����������
   * @return
   */
  public static String getClipBoardContents() {
    String text = "";
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable contents = clipboard.getContents(null);
    boolean hasTransferableText = (contents != null) &&
        contents.isDataFlavorSupported(DataFlavor.stringFlavor);
     if (hasTransferableText) {
      try {
        text = (String) contents.getTransferData(DataFlavor.stringFlavor);
        return text;
      }
      catch (UnsupportedFlavorException ex) {
        ex.printStackTrace();
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return null;
  }

  /**
   * ���ü����������
   * @param formula
   */
  public static void setClipBoardContents(String formula) {
    try {
      //���Ƶ�������
      StringSelection stringSelection = new StringSelection(formula);
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(stringSelection, null);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
    * �����������
    * @param reportBook
    */
   public static void saveAsTzgs(JReportBook reportBook) {
     if (reportBook == null) {
       return;
     }
     try {
       JReportModel model = (JReportModel) reportBook.ReportView.getModel();
       JReportView book = reportBook.ReportView;

       if (model != null) {
         int status = model.getDataStatus();
         if (status == JReportModel.MODEL_STATUS_TZGS) {
           //
           int selCount = book.getSelectionCount();
           int startRow = book.getSelStartRow() + 1;
           int phySRow = book.getPhySelStartRow();
           int startCol = book.getSelStartCol() + 1;
           int endRow = book.getSelEndRow() + 1;
           int endCol = book.getSelEndCol() + 1;
           int phyRow;
           if (selCount == 1) {
             phyRow = phySRow;
             for (int i = startRow; i < endRow + 1; i++) {
               for (int j = startCol; j < endCol + 1; j++) {
                 JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j, phyRow,
                     j - 1, 1, model);
                 if (unitInfo != null ) {
                  // if(unitInfo.dyzd)
                   unitInfo.DYZD_TZGS = "ROUND(100,2)";
         //          unitInfo.DYZD_TZGS = "ROUND("+i+","+j+")";
                   unitInfo.setTZModified();
                   unitInfo.setModified();
                   reportBook.ReportView.setPhyText(unitInfo.getPhyHZD_ZB(),
                       unitInfo.ColInfo.LZD_ZB - 1,
                       "<����>");
//                   reportBook.ReportView.setPhyColor(unitInfo.getPhyHZD_ZB(),
//                       unitInfo.ColInfo.LZD_ZB - 1,Color.blue);
                 }
               }
               phyRow++;
             }
           }
         }
       }

     }
     catch (Exception e) {
       e.printStackTrace();
     }
   }
   /**
     *  �����������
     * @param reportBook
     */
    public static void clearAsTzgs(JReportBook reportBook) {
      if (reportBook == null) {
        return;
      }
      try {
        JReportModel model = (JReportModel) reportBook.ReportView.getModel();
        JReportView book = reportBook.ReportView;

        if (model != null) {
          int status = model.getDataStatus();
          if (status == JReportModel.MODEL_STATUS_TZGS) {
            //
            int selCount = book.getSelectionCount();
            int startRow = book.getSelStartRow() + 1;
            int phySRow = book.getPhySelStartRow();
            int startCol = book.getSelStartCol() + 1;
            int endRow = book.getSelEndRow() + 1;
            int endCol = book.getSelEndCol() + 1;
            int phyRow;
            if (selCount == 1) {
              phyRow = phySRow;
              for (int i = startRow; i < endRow + 1; i++) {
                for (int j = startCol; j < endCol + 1; j++) {
                  JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j, phyRow,
                      j - 1, 1, model);
                  if (unitInfo != null ) {
                    unitInfo.DYZD_TZGS = null;
                    unitInfo.setTZModified();
                    unitInfo.setModified();
                    reportBook.ReportView.setPhyText(unitInfo.getPhyHZD_ZB(),
                        unitInfo.ColInfo.LZD_ZB - 1,
                        "");
                  }
                }
                phyRow++;
              }
            }
          }
        }

      }
      catch (Exception e) {
        e.printStackTrace();
      }
   }
   /**
    * ��Ҫ���м���Ĺ�ʽ���м��� �Ƿ����תΪУ�鹫ʽ
    * @param reportBook JReportBook
    */
   public static Boolean checkJsgs(JReportBook reportBook){
     Boolean boo = false ;
        if (reportBook == null) {
          return boo;
        }
          JReportModel model = (JReportModel) reportBook.ReportView.getModel();
          JReportView book = reportBook.ReportView;
	try{
	  if (model != null) {
	    int status = model.getDataStatus();
	    if (status == JReportModel.MODEL_STATUS_DATA) {
	      return boo;
	    }
	    else if (status == JReportModel.MODEL_STATUS_JSGS) {
	      //
	      int selCount = book.getSelectionCount();
	      int startRow = book.getSelStartRow() + 1;
	      int phySRow = book.getPhySelStartRow();
	      int startCol = book.getSelStartCol() + 1;
	      int endRow = book.getSelEndRow() + 1;
	      int endCol = book.getSelEndCol() + 1;
	      int phyRow;
	      if (selCount == 1) {
	        phyRow = phySRow;
	        for (int i = startRow; i < endRow + 1; i++) {
	          for (int j = startCol; j < endCol + 1; j++) {
	            JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j,
	                phyRow,
	                j - 1, 0, model);
	            if (unitInfo != null && unitInfo.DYZD_GSX != null) {
	              String GSX = unitInfo.DYZD_GSX;
	              if (!JReportView.FunctionManager.IsReportF1(GSX)) {
	                boo = true;
	
	              }
	            }
	          }
	          if (boo == true) {
	            break;
	          }
	          else
	            phyRow++;
	        }
	      }
	    }
	  }
	}
	catch(Exception e){e.printStackTrace();}
	    return boo;
  }
   /**
    * ��Ҫ���м���Ĺ�ʽ���м��� �Ƿ����תΪУ�鹫ʽ
    * @param reportBook JReportBook
    */
   public static Boolean checkZsgs(JReportBook reportBook){
     Boolean boo = false ;
        if (reportBook == null) {
          return boo;
        }
          JReportModel model = (JReportModel) reportBook.ReportView.getModel();
          JReportView book = reportBook.ReportView;
	try{
	  if (model != null) {
	    int status = model.getDataStatus();
	    if (status == JReportModel.MODEL_STATUS_DATA) {
	      return boo;
	    }
	    else if (status == JReportModel.MODEL_STATUS_ZSGS) {
	      //
	      int selCount = book.getSelectionCount();
	      int startRow = book.getSelStartRow() + 1;
	      int phySRow = book.getPhySelStartRow();
	      int startCol = book.getSelStartCol() + 1;
	      int endRow = book.getSelEndRow() + 1;
	      int endCol = book.getSelEndCol() + 1;
	      int phyRow;
	      if (selCount == 1) {
	        phyRow = phySRow;
	        for (int i = startRow; i < endRow + 1; i++) {
	          for (int j = startCol; j < endCol + 1; j++) {
	            JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j,
	                phyRow,
	                j - 1, 0, model);
	            if (unitInfo != null && unitInfo.DYZD_ZSGS != null) {
	              String GSX = unitInfo.DYZD_ZSGS;
	              if (!JReportView.FunctionManager.IsReportF1(GSX)) {
	                boo = true;
	
	              }
	            }
	          }
	          if (boo == true) {
	            break;
	          }
	          else
	            phyRow++;
	        }
	      }
	    }
	  }
	}
	catch(Exception e){e.printStackTrace();}
	    return boo;
  }   
   /**
   * ���㹫ʽ���ΪУ�鹫ʽ
   * @param reportBook
   */
  public static void saveAsJygs(JReportBook reportBook) {
    Boolean boo = false ;
    if (reportBook == null) {
      return ;
    }
    try {
      JReportModel model = (JReportModel) reportBook.ReportView.getModel();
      JReportView book = reportBook.ReportView;

      if (model != null) {
        int status = model.getDataStatus();
        if (status == JReportModel.MODEL_STATUS_DATA) {
          return ;
        }
        else if (status == JReportModel.MODEL_STATUS_JSGS) {
          //
          int selCount = book.getSelectionCount();
          int startRow = book.getSelStartRow() + 1;
          int phySRow = book.getPhySelStartRow();
          int startCol = book.getSelStartCol() + 1;
          int endRow = book.getSelEndRow() + 1;
          int endCol = book.getSelEndCol() + 1;
          int phyRow;
          if (selCount == 1) {
          phyRow = phySRow;
          for (int i = startRow; i < endRow + 1; i++) {
            for (int j = startCol; j < endCol + 1; j++) {
              JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j, phyRow,
                  j - 1, 0, model);
              if (unitInfo != null && unitInfo.DYZD_GSX != null) {
                String GSX = unitInfo.DYZD_GSX;
                if (!JReportView.FunctionManager.IsReportF1(GSX)) {
                    boo = true ;
                    break ;
//                    int response = JOptionPane.showConfirmDialog(null, "ѡ�����а������ܴ�ΪУ�鹫ʽ�ĵ�Ԫ��ʽ���Ƿ����?", "Confirm",
//                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                   if (response == JOptionPane.NO_OPTION) {
//                       return ;
//                   } else if (response == JOptionPane.YES_OPTION) {
//                       break ;
//                   } else if (response == JOptionPane.CLOSED_OPTION) {
//                     System.out.println("JOptionPane closed");
//                   }
                }
              }
            }
            if(boo == true){
                break;
            }else
                phyRow++;
          }
        }

          if (selCount == 1) {
            phyRow = phySRow;
            for (int i = startRow; i < endRow + 1; i++) {
              for (int j = startCol; j < endCol + 1; j++) {
                JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j, phyRow,
                    j - 1, 0, model);
                if (unitInfo != null && unitInfo.DYZD_GSX != null) {
                  String GSX = unitInfo.DYZD_GSX;
                  if (StringUtil.isStartWith(GSX, ReportUtil.SYMBOL_NAMES))
                    GSX = ReportUtil.getJYGSWithoutSymbol(GSX);
                  if (!JReportView.FunctionManager.IsReportF1(GSX)) {
                    continue;
                  }

                  //�ɼ��㹫ʽ����У�鹫ʽ
                  JJygsInfo jygsInfo = new JJygsInfo();
                  jygsInfo.JYGS_ORDE = model.JygsMaxOrde + 1 + "";
                  jygsInfo.JYGS_BJ = "=";
                  jygsInfo.JYGS_HOFFSET = unitInfo.DYZD_HOFFSET;
                  jygsInfo.JYGS_LOFFSET = unitInfo.DYZD_LOFFSET;
                  jygsInfo.JYGS_GSX = unitInfo.DYZD_GSX;
                  jygsInfo.JYGS_EXGSX = unitInfo.DYZD_EXGSX;
                  //����У�鹫ʽ����
                  if (model.ADD_TYPE == JReportModel.STUB_TYPE_DWZD) {
                    unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_DWZD;
                  }
                  else if (model.ADD_TYPE == JReportModel.STUB_TYPE_BMZD) {
                    unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_BMZD;
                  }
                  else if (model.ADD_TYPE == JReportModel.STUB_TYPE_CBZD) {
                    unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_CBZD;
                  }
                  else if (model.ADD_TYPE == JReportModel.STUB_TYPE_LBZD) {
                    unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_LBZD;
                  }
                  //end
                  model.JygsMaxOrde++; //�����ż�1
                  if (unitInfo.JygsList == null) {
                    unitInfo.JygsList = new Vector();
                  }
                  unitInfo.JygsList.add(jygsInfo);
                  unitInfo.setJYModified();
                  unitInfo.setModified();
                }

              }
              phyRow++;
            }
          }
          return  ;
        }
        else if (status == JReportModel.MODEL_STATUS_JYGS) {
          //Do nothing
          return ;
        }
        else if (status == JReportModel.MODEL_STATUS_ZSGS) {
            //
            int selCount = book.getSelectionCount();
            int startRow = book.getSelStartRow() + 1;
            int phySRow = book.getPhySelStartRow();
            int startCol = book.getSelStartCol() + 1;
            int endRow = book.getSelEndRow() + 1;
            int endCol = book.getSelEndCol() + 1;
            int phyRow;
            if (selCount == 1) {
            phyRow = phySRow;
            for (int i = startRow; i < endRow + 1; i++) {
              for (int j = startCol; j < endCol + 1; j++) {
                JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j, phyRow,
                    j - 1, 0, model);
                if (unitInfo != null && unitInfo.DYZD_ZSGS != null) {
                  String GSX = unitInfo.DYZD_ZSGS;
                  if (!JReportView.FunctionManager.IsReportF1(GSX)) {
                      boo = true ;
                      break ;
//                      int response = JOptionPane.showConfirmDialog(null, "ѡ�����а������ܴ�ΪУ�鹫ʽ�ĵ�Ԫ��ʽ���Ƿ����?", "Confirm",
//                              JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                     if (response == JOptionPane.NO_OPTION) {
//                         return ;
//                     } else if (response == JOptionPane.YES_OPTION) {
//                         break ;
//                     } else if (response == JOptionPane.CLOSED_OPTION) {
//                       System.out.println("JOptionPane closed");
//                     }
                  }
                }
              }
              if(boo == true){
                  break;
              }else
                  phyRow++;
            }
          }

            if (selCount == 1) {
              phyRow = phySRow;
              for (int i = startRow; i < endRow + 1; i++) {
                for (int j = startCol; j < endCol + 1; j++) {
                  JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j, phyRow,
                      j - 1, 0, model);
                  if (unitInfo != null && unitInfo.DYZD_ZSGS != null) {
                    String GSX = unitInfo.DYZD_ZSGS;
                    if (StringUtil.isStartWith(GSX, ReportUtil.SYMBOL_NAMES))
                      GSX = ReportUtil.getJYGSWithoutSymbol(GSX);
                    if (!JReportView.FunctionManager.IsReportF1(GSX)) {
                      continue;
                    }

                    //�ɼ��㹫ʽ����У�鹫ʽ
                    JJygsInfo jygsInfo = new JJygsInfo();
                    jygsInfo.JYGS_ORDE = model.JygsMaxOrde + 1 + "";
                    jygsInfo.JYGS_BJ = "=";
                    jygsInfo.JYGS_HOFFSET = unitInfo.DYZD_ZSHOFFSET;
                    jygsInfo.JYGS_LOFFSET = unitInfo.DYZD_ZSLOFFSET;
                    jygsInfo.JYGS_GSX = unitInfo.DYZD_ZSGS;
                    jygsInfo.JYGS_EXGSX = unitInfo.DYZD_EXZSGS;
                    //����У�鹫ʽ����
                    if (model.ADD_TYPE == JReportModel.STUB_TYPE_DWZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_DWZD;
                    }
                    else if (model.ADD_TYPE == JReportModel.STUB_TYPE_BMZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_BMZD;
                    }
                    else if (model.ADD_TYPE == JReportModel.STUB_TYPE_CBZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_CBZD;
                    }
                    else if (model.ADD_TYPE == JReportModel.STUB_TYPE_LBZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_LBZD;
                    }
                    //end
                    model.JygsMaxOrde++; //�����ż�1
                    if (unitInfo.JygsList == null) {
                      unitInfo.JygsList = new Vector();
                    }
                    unitInfo.JygsList.add(jygsInfo);
                    unitInfo.setJYModified();
                    unitInfo.setModified();
                  }

                }
                phyRow++;
              }
            }
            return  ;
          }        
        
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * У�鹫ʽ���Ϊ���㹫ʽ
   * @param reportBook
   */
  public static void saveAsJsgs(JReportBook reportBook) {
    if (reportBook == null) {
      return;
    }
    try {
      JReportModel model = (JReportModel) reportBook.ReportView.getModel();
      JReportView book = reportBook.ReportView;
      if (model != null) {
        int status = model.getDataStatus();
        if (status == JReportModel.MODEL_STATUS_DATA) {
          //Do nothing
          return;
        }
        else if (status == JReportModel.MODEL_STATUS_JSGS) {
          //Do nothing
          return;
        }
        else if (status == JReportModel.MODEL_STATUS_JYGS) {
          int selCount = book.getSelectionCount();
          int startRow = book.getSelStartRow() + 1;
          int startCol = book.getSelStartCol() + 1;
          int endRow = book.getSelEndRow() + 1;
          int endCol = book.getSelEndCol() + 1;
          int phySRow = book.getPhySelStartRow();
          int phyRow = phySRow;
          if (selCount == 1) {
            for (int i = startRow; i < endRow + 1; i++) {
              for (int j = startCol; j < endCol + 1; j++) {
                JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j, phyRow,
                    j - 1, 0, model);
                if (unitInfo != null && unitInfo.JygsList.size() > 0) {
                  //�ɵ�һ��У�鹫ʽ�������㹫ʽ
                  JJygsInfo jygsInfo = (JJygsInfo) unitInfo.JygsList.get(0);
                  if (jygsInfo != null) {
                    unitInfo.DYZD_HOFFSET = jygsInfo.JYGS_HOFFSET;
                    unitInfo.DYZD_LOFFSET = jygsInfo.JYGS_LOFFSET;
                    unitInfo.DYZD_GSX = jygsInfo.JYGS_GSX;
                    unitInfo.DYZD_EXGSX = jygsInfo.JYGS_EXGSX;
                    //add by fsz
                    if (model.ADD_TYPE == JReportModel.STUB_TYPE_DWZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_DWZD;
                    }
                    else if (model.ADD_TYPE == JReportModel.STUB_TYPE_BMZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_BMZD;
                    }
                    else if (model.ADD_TYPE == JReportModel.STUB_TYPE_CBZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_CBZD;
                    }
                    else if (model.ADD_TYPE == JReportModel.STUB_TYPE_LBZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_LBZD;
                    }
                    //end

                    unitInfo.setJSModified();
                    unitInfo.setModified();
                  }
                }
              }
              phyRow++;
            }
          }
        }
        else if ( status == JReportModel.MODEL_STATUS_ZSGS ) {
            int selCount = book.getSelectionCount();
            int startRow = book.getSelStartRow() + 1;
            int startCol = book.getSelStartCol() + 1;
            int endRow = book.getSelEndRow() + 1;
            int endCol = book.getSelEndCol() + 1;
            int phySRow = book.getPhySelStartRow();
            int phyRow = phySRow;
            if (selCount == 1) {
            	for (int i = startRow; i < endRow + 1; i++) {
            		for (int j = startCol; j < endCol + 1; j++) {
            			JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j, phyRow,j - 1, 0, model);
            			if (unitInfo != null && unitInfo.DYZD_ZSGS != null && unitInfo.DYZD_ZSGS.length()>0) {
            				unitInfo.DYZD_HOFFSET = unitInfo.DYZD_ZSHOFFSET;
            				unitInfo.DYZD_LOFFSET = unitInfo.DYZD_ZSLOFFSET;
            				unitInfo.DYZD_GSX = unitInfo.DYZD_ZSGS;
            				unitInfo.DYZD_EXGSX = unitInfo.DYZD_EXZSGS;
            				if (model.ADD_TYPE == JReportModel.STUB_TYPE_DWZD) {
            					unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_DWZD;
            				}
            				else if (model.ADD_TYPE == JReportModel.STUB_TYPE_BMZD) {
            					unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_BMZD;
            				}
            				else if (model.ADD_TYPE == JReportModel.STUB_TYPE_CBZD) {
            					unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_CBZD;
            				}
            				else if (model.ADD_TYPE == JReportModel.STUB_TYPE_LBZD) {
            					unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_LBZD;
            				}
            				unitInfo.setJSModified();
            				unitInfo.setModified();
            			}
            		}
            		phyRow++;
            	}
            }        	
      	}
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * У�鹫ʽ���Ϊ���㹫ʽ
   * @param reportBook
   */
  public static void saveAsZsgs(JReportBook reportBook) {
    if (reportBook == null) {
      return;
    }
    try {
      JReportModel model = (JReportModel) reportBook.ReportView.getModel();
      JReportView book = reportBook.ReportView;
      if (model != null) {
        int status = model.getDataStatus();
        if (status == JReportModel.MODEL_STATUS_DATA) {
          //Do nothing
          return;
        }
        else if (status == JReportModel.MODEL_STATUS_JSGS) {
            int selCount = book.getSelectionCount();
            int startRow = book.getSelStartRow() + 1;
            int startCol = book.getSelStartCol() + 1;
            int endRow = book.getSelEndRow() + 1;
            int endCol = book.getSelEndCol() + 1;
            int phySRow = book.getPhySelStartRow();
            int phyRow = phySRow;
            if (selCount == 1) {
            	for (int i = startRow; i < endRow + 1; i++) {
            		for (int j = startCol; j < endCol + 1; j++) {
            			JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j, phyRow,j - 1, 0, model);
            			if (unitInfo != null && unitInfo.DYZD_GSX != null ) {
            				unitInfo.DYZD_ZSHOFFSET = unitInfo.DYZD_HOFFSET;
            				unitInfo.DYZD_ZSLOFFSET = unitInfo.DYZD_LOFFSET;
            				unitInfo.DYZD_ZSGS = unitInfo.DYZD_GSX;
            				unitInfo.DYZD_EXZSGS = unitInfo.DYZD_EXGSX;
            				if (model.ADD_TYPE == JReportModel.STUB_TYPE_DWZD) {
            					unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_DWZD;
            				}
            				else if (model.ADD_TYPE == JReportModel.STUB_TYPE_BMZD) {
            					unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_BMZD;
            				}
            				else if (model.ADD_TYPE == JReportModel.STUB_TYPE_CBZD) {
            					unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_CBZD;
            				}
            				else if (model.ADD_TYPE == JReportModel.STUB_TYPE_LBZD) {
            					unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_LBZD;
            				}
            				unitInfo.setZSModified();
            				unitInfo.setModified();
            			}
            		}
            		phyRow++;
            	}
            }        	        }
        else if (status == JReportModel.MODEL_STATUS_JYGS) {
          int selCount = book.getSelectionCount();
          int startRow = book.getSelStartRow() + 1;
          int startCol = book.getSelStartCol() + 1;
          int endRow = book.getSelEndRow() + 1;
          int endCol = book.getSelEndCol() + 1;
          int phySRow = book.getPhySelStartRow();
          int phyRow = phySRow;
          if (selCount == 1) {
            for (int i = startRow; i < endRow + 1; i++) {
              for (int j = startCol; j < endCol + 1; j++) {
                JUnitInfo unitInfo = JUnitInfo.getGenerUnitInfo(i, j, phyRow,
                    j - 1, 0, model);
                if (unitInfo != null && unitInfo.JygsList.size() > 0) {
                  //�ɵ�һ��У�鹫ʽ�������㹫ʽ
                  JJygsInfo jygsInfo = (JJygsInfo) unitInfo.JygsList.get(0);
                  if (jygsInfo != null) {
                    unitInfo.DYZD_ZSHOFFSET = jygsInfo.JYGS_HOFFSET;
                    unitInfo.DYZD_ZSLOFFSET = jygsInfo.JYGS_LOFFSET;
                    unitInfo.DYZD_ZSGS = jygsInfo.JYGS_GSX;
                    unitInfo.DYZD_EXZSGS = jygsInfo.JYGS_EXGSX;
                    //add by fsz
                    if (model.ADD_TYPE == JReportModel.STUB_TYPE_DWZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_DWZD;
                    }
                    else if (model.ADD_TYPE == JReportModel.STUB_TYPE_BMZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_BMZD;
                    }
                    else if (model.ADD_TYPE == JReportModel.STUB_TYPE_CBZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_CBZD;
                    }
                    else if (model.ADD_TYPE == JReportModel.STUB_TYPE_LBZD) {
                      unitInfo.JYGS_TYPE = unitInfo.JYGS_TYPE_LBZD;
                    }
                    //end

                    unitInfo.setZSModified();
                    unitInfo.setModified();
                  }
                }
              }
              phyRow++;
            }
          }
        }
        else if ( status == JReportModel.MODEL_STATUS_ZSGS ) {
        	// do nothing
        	return;
      	}
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }  
  
}
