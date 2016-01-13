package jreport.model.classes.analyze;

import java.util.*;

import org.jdom.*;

import com.efounder.eai.data.JParamObject;
import com.f1j.ss.*;
import com.f1j.util.*;
import jtoolkit.xml.classes.*;
import jreport.swing.classes.*;
import jreport.foundation.classes.*;
import java.util.ResourceBundle;

/**
 * <p>Title: AbstractReportDataModel</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AbstractReportDataModel
    extends JReportDataModel
    implements AnalyzeReportConstants {
static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.analyze.Language");
    public int mTitleRowCount = 0; //标题行数
    public int mHeadRowCount = 0; //表头行数
    public int mBodyRowCount = 0; //表体行数
    public int mTailRowCount = 0; //表尾行数
    public int mRowCount = 0; //行数
    public int mColCount = 0; //列数

    public String mReportNumber = ""; //报表不编号
    public String mReportName = ""; //报表名称
    public String mReprotDate = ""; //报表日期
    public String mTitle = ""; //标题
    public JXMLObject mXMLObject = null; //数据
    public String mCaption = ""; //子窗口标题

    public int OpenReportObject(Object roe, JParamObject PO) {
        /**@todo Override this jreport.foundation.classes.JReportDataModel method*/
        ReportView.setPublicAttrib();
        mXMLObject = new JXMLObject( (String) roe);
        init();
        drawReport();
        return 0;
    }

    //参数初始化
    public void init() {

    }

    //绘制报表
    public void drawReport() {
        try {
            drawReportTitle();
            drawReportHead();
            drawReportBody();
            drawReportTail();
            setReportFormat();
            ReportView.setSelection(0, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //绘制标题
    public void drawReportTitle() {
    }

    //绘制表头
    public void drawReportHead() {
    }

    //绘制表体
    public void drawReportBody() {
    }

    //绘制表尾
    public void drawReportTail() {
    }

    //绘制表格式
    public void setReportFormat() {
    }

    //获得报表总的行数
    public int getRowCount() {
        return mRowCount;
    }

    //获得报表的列数
    public int getColCount() {
        return mColCount;
    }

    //获得标题
    public String getTitle() {
        return mTitle;
    }

    //设置标题
    public void setTitle(String title) {
        mTitle = title;
    }

    public String GetCaption() {
        /**@todo Override this jreport.foundation.classes.JReportDataModel method*/
        return mCaption;
    }

    public void SetCaption(String caption) {
        /**@todo Override this jreport.foundation.classes.JReportDataModel method*/
        mCaption = caption;
    }
}
