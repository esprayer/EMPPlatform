package jservice.jbof.classes.GenerQueryObject;

import java.util.*;

import javax.swing.*;

import com.core.xml.JBOFClass;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.f1j.swing.*;
import com.report.table.jxml.*;
import jbof.application.classes.*;
import jdatareport.dof.classes.*;
import jfoundation.bridge.classes.*;
import jframework.foundation.classes.*;
import jservice.jbof.classes.GenerQueryObject.action.*;
import jservice.jbof.classes.GenerQueryObject.print.*;
import jservice.jbof.classes.GenerQueryObject.print.preview.*;
import jservice.jbof.classes.GenerQueryObject.print.util.*;
import java.awt.*;
import jservice.jbof.classes.GenerQueryObject.action.ui.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JGenerQueryActionImpl
    extends JBookActionDefaultImpl {
    public JGenerQueryActionImpl() {
    }
    /**
     * 打印标题
     * @param action
     */
    public void onPrintTitle(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            Frame owner=JActiveDComDM.MainApplication.MainWindow;
            try {
                JPrintTitleSettingDialog dlg=new JPrintTitleSettingDialog(owner,"打印标题设置",false);
                dlg.setPrintTitleArea(book.getPrintTitles());
                dlg.CenterWindow();
                dlg.show();
                if(dlg.Result==dlg.RESULT_OK){
//                    String printAreaFormula = JBookActionUtils.coor2Formula(book, 0, 0, 0, 0);
                    String printAreaFormula = dlg.getPrintTilteArea();
                    if (printAreaFormula != null && printAreaFormula.length() > 0) {
                        book.setPrintTitles(printAreaFormula);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(owner,"设置打印标题错误："+e.getLocalizedMessage(),"系统提示",JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    /**
     *
     * @param action
     */
    public void onPrint(JBookAction action) {
        /**@todo Override this jservice.jbof.classes.GenerQueryObject.action.JBookActionImpl method*/
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            try {
                //复制一份,设置格式为正常
                JBook printBook = JPrintUtils.copyPrintBook(book);
                Frame owner = JActiveDComDM.MainApplication.MainWindow;
                JReportBookPrintSetDlg Print = new JReportBookPrintSetDlg(printBook);
//                JPrintDlg Print=new JPrintDlg(printBook);
                Print.CenterWindow();
                Print.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onPrintPreview(JBookAction action) {
        /**@todo Override this jservice.jbof.classes.GenerQueryObject.action.JBookActionImpl method*/
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            JGenerQueryWindow queryWindow = (JGenerQueryWindow) action.getParam();
            try {
                JBookPrintPreviewWindow previewWindow = new JBookPrintPreviewWindow(book,false);
                previewWindow.LoadWindowIcon("printview.gif");
                String Text = queryWindow.getTitleAt() + " ( 打印预览 )";
                JActiveDComDM.MainApplication.OpenObjectWindow(Text, previewWindow.WindowIcon, Text, previewWindow);
                previewWindow.InitChildWindow(JActiveDComDM.MainApplication, JActiveDComDM.MainApplication.MainWindow, book, null);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 图形控制
     * @param action
     */
    public void onGraphics(JBookAction action) {
        if (action != null && action.getBook() != null) {
            JBook book = action.getBook();
            JContextObject CO = (JContextObject) action.getParam();
            try {
                if (CO != null) {
                    boolean selected = CO.ChildWindow.getMenuSelected("mnDraw");
                    CO.ChildWindow.setToolbarVisible("DrawToolbar", selected);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 过滤
     * @param action
     */
    public void onFilter(JBookAction action) {
        if (action != null && action.getParam() != null) {
            //构造过滤条件
            JGenerQueryWindow queryWindow = (JGenerQueryWindow) action.getParam();
            if (queryWindow != null) {
                try {
                    JDataReport rpt = queryWindow.vwQueryView.getDataReport();
                    rpt.filter();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 取消过滤
     * @param action
     */
    public void onCancelFilter(JBookAction action) {
        if (action != null && action.getParam() != null) {
            //构造过滤条件
            JGenerQueryWindow queryWindow = (JGenerQueryWindow) action.getParam();
            if (queryWindow != null) {
                try {
                    JDataReport rpt = queryWindow.vwQueryView.getDataReport();
                    rpt.load(rpt.FILTER_ALL);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  排序
     * @param action
     */
    public void onSort(JBookAction action) {
        if (action != null && action.getParam() != null) {
            //构造过滤条件
            JGenerQueryWindow queryWindow = (JGenerQueryWindow) action.getParam();
            if (queryWindow != null) {
                try {
                    //Vector queryData = getQueryData(queryWindow);
                    JDataReport rpt = queryWindow.vwQueryView.getDataReport();
                    rpt.sort();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 查询重设
     * @param action
     */
    public void onRequery(JBookAction action) {
        try {
            if (action != null && action.getParam() != null) {
                JGenerQueryWindow queryWindow = (JGenerQueryWindow) action.getParam();
                //取当前查询是否设置服务名
                String serverName = queryWindow.PO.GetValueByParamName("Server","");
                Object obj = null;
                // 处理前缀阶段
                JFwkActiveObjectStub ativeobjStub = JActiveDComDM.BusinessActiveFramework.findActiveObjectStubByName("GenerQueryObject");
                if (ativeobjStub == null) {
                    return;
                }
                obj = JBOFClass.CallObjectMethod(ativeobjStub.iActiveObject, "processPrefixQuery", queryWindow.QO);
                if (obj == null) {
                    return;
                }
                JParamObject PO = (JParamObject) obj;
                //如果服务名不为空，则再次放入
                if (!serverName.equals(""))
                  PO.SetValueByParamName("Server", serverName);

                JActiveDComDM.MainApplication.BeginWaitCursor();
                // 处理组织阶段
                obj = JBOFClass.CallObjectMethod(ativeobjStub.iActiveObject, "processOrganizeQuery", queryWindow.QO, PO);
                if (obj == null) {
                    return;
                }
                JResponseObject RO = (JResponseObject) obj;
                if (RO.ErrorCode == 0) {
                    // 处理实体阶段
                    queryWindow.QO.RO = RO;
                    queryWindow.ViewQueryData(PO, queryWindow.QO);
                    queryWindow.InitEventListener();
                }
                else {
                    JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, RO.ErrorString, "系统提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, e.getMessage(), "系统提示", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            JActiveDComDM.MainApplication.EndWaitCursor();
        }
    }

    /**
     * 样式
     * @param action
     */
    public void onStyle(JBookAction action) {
        if (action != null && action.getParam() != null) {
            //构造过滤条件
            JGenerQueryWindow queryWindow = (JGenerQueryWindow) action.getParam();
            if (queryWindow != null) {
                try {
                    //Vector queryData = getQueryData(queryWindow);
                    JDataReport rpt = queryWindow.vwQueryView.getDataReport();
                    rpt.style();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    /**
//     * 返回查询结果格式对象TableManager 和 数据全集
//     * @param QueryWindow
//     * @return
//     */
//    public static Vector getQueryData(JGenerQueryWindow QueryWindow) {
//        if (QueryWindow == null) {
//            return null;
//        }
//        String[] QueryObject = QueryWindow.QueryObject;
//        String[] datas = null;
//        if (QueryWindow.QueryObject.length == 5) {
//            datas = new String[] {
//                QueryObject[1], QueryObject[2]};
//        }
//        else if (QueryObject.length == 2) {
//            datas = new String[] {
//                QueryObject[0], QueryObject[1]};
//        }
//        else if (QueryObject.length == 3) {
//            datas = new String[] {
//                QueryObject[1], QueryObject[2]};
//        }
//        if (datas != null && datas.length == 2) {
//            TableManager mFmtMgr = QueryWindow.
//            //TreeTableDataManager mDataMgr = new TreeTableDataManager(datas[1]);
//            Vector queryData = new Vector();
//            queryData.add(mFmtMgr);
//            queryData.add(mDataMgr);
//            return queryData;
//        }
//        return null;
//    }

}
