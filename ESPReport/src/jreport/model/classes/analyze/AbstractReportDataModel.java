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
    public int mTitleRowCount = 0; //��������
    public int mHeadRowCount = 0; //��ͷ����
    public int mBodyRowCount = 0; //��������
    public int mTailRowCount = 0; //��β����
    public int mRowCount = 0; //����
    public int mColCount = 0; //����

    public String mReportNumber = ""; //�������
    public String mReportName = ""; //��������
    public String mReprotDate = ""; //��������
    public String mTitle = ""; //����
    public JXMLObject mXMLObject = null; //����
    public String mCaption = ""; //�Ӵ��ڱ���

    public int OpenReportObject(Object roe, JParamObject PO) {
        /**@todo Override this jreport.foundation.classes.JReportDataModel method*/
        ReportView.setPublicAttrib();
        mXMLObject = new JXMLObject( (String) roe);
        init();
        drawReport();
        return 0;
    }

    //������ʼ��
    public void init() {

    }

    //���Ʊ���
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

    //���Ʊ���
    public void drawReportTitle() {
    }

    //���Ʊ�ͷ
    public void drawReportHead() {
    }

    //���Ʊ���
    public void drawReportBody() {
    }

    //���Ʊ�β
    public void drawReportTail() {
    }

    //���Ʊ��ʽ
    public void setReportFormat() {
    }

    //��ñ����ܵ�����
    public int getRowCount() {
        return mRowCount;
    }

    //��ñ��������
    public int getColCount() {
        return mColCount;
    }

    //��ñ���
    public String getTitle() {
        return mTitle;
    }

    //���ñ���
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
