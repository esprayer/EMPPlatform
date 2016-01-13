package jreport.swing.classes.ReportBook;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jcomponent.custom.swing.*;
import jframework.foundation.classes.*;
import jframework.resource.classes.*;
import jreport.foundation.classes.data.*;
import jreport.swing.classes.*;

import java.util.ResourceBundle;
import jtoolkit.string.classes.TString;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JCellComboBox
        extends JComboBox implements ICellEditor, ActionListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
	String bbzd_date ="";
    JUnitInfo UI = null;
    JUnitInfo BDUI = null;
    JReportView ReportView = null;
    int Row;
    int Col;
    static ImageIcon II = null;
    static JIConListCellRenderer ICR = null;

    public JCellComboBox() {
        initCombo();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) {
            endEdit();
        }
    }

    private void endEdit() {
        CodeStub codeStub = null;
        try {
            codeStub = (CodeStub)this.getSelectedItem();
            if (codeStub != null) {
                BDUI.DYZD_DATA = codeStub.getIndex();
                BDUI.DYZD_SJ = codeStub.getValue();
                ReportView.setPhyText(Row, Col, BDUI.DYZD_SJ);
                ReportView.BookTextField.setText(codeStub.toString(), true);
                BDUI.setModified();
                /**
                 * ����ModelΪ�޸�״̬
                 */
                ReportView.getModel().setModified();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCombo() {
        if (II == null || ICR == null) {
            II = JXMLResource.LoadImageIcon(this, "bdicon.gif");
            ICR = new JIConListCellRenderer(II);
        }
        setRenderer(ICR);
    }

    public Component getComponent() {
        return this;
    }

    public void setCustomObject(Object Custom) {
        if (Custom instanceof JUnitInfo) {
            UI = (JUnitInfo) Custom;
        }
    }

    // ��ȡ�༩���
    public Component getEditorComponent(JReportView ReportView, Object value, boolean isSelected, int Row, int Col) {
        this.ReportView = ReportView;
        this.Row = Row;
        this.Col = Col;
        String DYZD_COMBO = UI.DYZD_COMBO.trim().toUpperCase();
        //ȡö�����
        DYZD_COMBO = getMjlb(DYZD_COMBO);
        BDUI = (JUnitInfo) value;
        JReportModel RM = (JReportModel) ReportView.getModel();//add by lqk
    	bbzd_date = RM.BBZD_DATE;//end  
        // �γ������б������
        Vector CoverCodeList = new Vector();//CoverCode.getCoverCode(bbzd_date).getCoverCodeList(DYZD_COMBO);
        buildCoverCodeList(CoverCodeList);
        // ȷ����ǰ�����б��λ��
        findPos(BDUI);
        // �����¼��������
        this.addActionListener(this);
        return this;
    }

    /**
     * ȡö�����
     * EM_BBLX_��������.TXT
     * @param lbbh String
     * @return String
     */
    private String getMjlb(String sValue) {
        int pos;
        pos = sValue.indexOf("_");
        if (pos >= 0) {
            sValue = sValue.substring(pos + 1);
        }
        pos = sValue.indexOf("_");
        if (pos >= 0) {
            sValue = sValue.substring(0, pos);
        }

        return sValue;
    }

    /**
     * ��������ݵĲ���
     * add by hufeng 2008.7.30
     * @param UI JUnitInfo
     */
    private void findPos(JUnitInfo UI) {
        int Index = (int) UI.DYZD_DATA;
        String Content = UI.DYZD_SJ;
        CodeStub codeStub = null;
        for (int i = 0; i < this.getItemCount(); i++) {
            codeStub = (CodeStub)this.getItemAt(i);
            if (codeStub.getIndex() == Index ||
                codeStub.getValue().equals(Content)) {
                this.setSelectedIndex(i);
                ReportView.BookTextField.setText(codeStub.toString(), true);
                break;
            }
        }
    }

    private void buildCoverCodeList(Vector CoverCodeList) {
        CodeStub codeStub = null;
        for (int i = 0; i < CoverCodeList.size(); i++) {
            codeStub = (CodeStub) CoverCodeList.get(i);
            this.insertItemAt(codeStub, i);
        }
    }

    // ��ʼ�༩
    public void startEditing(int Row, int Col, JUnitInfo ui, JBDUnitInfo bdui) {

    }

    /**
     * �����༭
     * �鿴��ǰ�༭�����Ƿ�Ϸ�
     * @param Row int
     * @param Col int
     * @param UI JUnitInfo
     * @param BDUI JBDUnitInfo
     */
    public void endEditing(int Row, int Col, JUnitInfo ui, JBDUnitInfo bdui) {
        int nValue;
        CodeStub codeStub = null;
        String sValue = BDUI.DYZD_SJ;
        if (BDUI.ChangeLog == 0 || sValue == null || sValue.trim().equals("")) {
            return;
        }
        //������û�ж�Ӧ��ö�ٶ���
        try {
            nValue = Integer.parseInt(sValue);
            codeStub = findStub(nValue);
        } catch (Exception e) {
            codeStub = findStub(sValue);
        }
        try {
            if (codeStub == null) {
                JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,
                                              TString.F(res.getString("String_116"), sValue), res.getString("String_118"), JOptionPane.WARNING_MESSAGE);
                BDUI.DYZD_SJ = "";
                Row = BDUI.getPhyHZD_ZB();
                Col = BDUI.getLZD_ZB() - 1;
                ReportView.setPhyText(Row, Col, "");
                BDUI.setModified();
            } else {
                BDUI.DYZD_DATA = codeStub.getIndex();
                BDUI.DYZD_SJ = codeStub.getValue();
                BDUI.setModified();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ����INDEX����ö�ٶ���
     * @param index int
     * @return CodeStub
     */
    private CodeStub findStub(int index) {
        CodeStub codeStub = null;
        for (int i = 0; i < this.getItemCount(); i++) {
            codeStub = (CodeStub)this.getItemAt(i);
            if (codeStub.getIndex() == index) {
                this.setSelectedIndex(i);
                return codeStub;
            }
        }
        return null;
    }

    /**
     * ����INDEX����ö�ٶ���
     * @param index int
     * @return CodeStub
     */
    private CodeStub findStub(String pValue) {
        CodeStub codeStub = null;
        for (int i = 0; i < this.getItemCount(); i++) {
            codeStub = (CodeStub)this.getItemAt(i);
            if (codeStub.getValue().equals(pValue)) {
                this.setSelectedIndex(i);
                return codeStub;
            }
        }
        return null;
    }

    // ȡ���༩
    public void cancelEditing(int Row, int Col, JUnitInfo UI, JBDUnitInfo BDUI) {
    }
}
