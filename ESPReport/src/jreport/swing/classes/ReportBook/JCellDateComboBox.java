package jreport.swing.classes.ReportBook;

import java.util.ResourceBundle;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

import com.pub.comp.JDateComboBox;
import jframework.foundation.classes.JActiveDComDM;
import jreport.swing.classes.JReportView;
import jtoolkit.string.classes.TString;

/**
 * <p>Title: ����ö���ֶ��е�����ѡ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: pansoft</p>
 * @author hufeng
 * @version 1.0
 */

public class JCellDateComboBox
    extends JDateComboBox implements ICellEditor, ActionListener, KeyListener {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");

    private JUnitInfo BDUI = null;
    JReportView ReportView = null;
    int Row;
    int Col;
    private RegexValidator validator = new RegexValidator();//������ʽ��֤��

    public JCellDateComboBox() {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) {
            endEdit();
        }
    }

    private void endEdit() {
        try {
            String value = (String)getSelectedItem();
            if (value != null) {
                /**
                 * ��2005-12-12��Ϊ2005.12.12
                 * ��ΪCS��F1�汾��֧�����ָ�ʽ
                 */
                value = value.replaceAll("-", ".");
                value = value.replaceAll("/", ".");
                BDUI.DYZD_SJ = value;
                ReportView.setPhyText(Row, Col, BDUI.DYZD_SJ);
                ReportView.BookTextField.setText(value, true);
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

    public void cancelEditing(int Row, int Col, JUnitInfo UI, JBDUnitInfo BDUI) {
    }

    /**
     * �жϵ�ǰ��Ԫ�������Ƿ���ȷ
     * @param Row int
     * @param Col int
     * @param ui JUnitInfo
     * @param bdui JBDUnitInfo
     */
    public void endEditing(int Row, int Col, JUnitInfo ui, JBDUnitInfo bdui) {
        try {
            String inputDate = BDUI.DYZD_SJ;
            String date = inputDate;
            if (BDUI.ChangeLog == 0 || date == null || date.trim().equals("")) {
                return;
            }
            /**
             * �ɷݱ��洦�������ڿ���д��δԼ����
             * add by gengeng 2008-9-28
             */
            if (!"δԼ��".equals(date) && date.length() < 10) {
                setData(date);
                return;
            }
            if (!"δԼ��".equals(date) && !validator.accept(validator.patternDate, date)) {
                setData(date);
                return;
            } else {
                setSelectedItem(inputDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData(String date) {
        JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, TString.F(res.getString("String_59"), date), res.getString("String_61"), JOptionPane.WARNING_MESSAGE);
        BDUI.DYZD_SJ = "";
        BDUI.DYZD_DATA = 0;
        Row = BDUI.getPhyHZD_ZB();
        Col = BDUI.getLZD_ZB() - 1;
        try {
            ReportView.setPhyText(Row, Col, "");
            ReportView.setPhyNumber(Row, Col, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        BDUI.setModified();
    }

    /**
     * ȡ��ǰ�༭�ؼ�
     * @return Component
     */
    public Component getComponent() {
        return this;
    }

    /**
     * ��ȡ�༭���
     * @param ReportView JReportView
     * @param value Object
     * @param isSelected boolean
     * @param Row int
     * @param Col int
     * @return Component
     */
    public Component getEditorComponent(JReportView ReportView, Object value, boolean isSelected, int Row, int Col) {
        this.ReportView = ReportView;
        this.Row = Row;
        this.Col = Col;
        BDUI = (JUnitInfo) value;
        //
        String date = BDUI.DYZD_SJ;
        this.setSelectedItem(date);
        //
        this.getEditor().getEditorComponent().addKeyListener(this);
        this.addActionListener(this);
        return this;
    }

    public void setCustomObject(Object Custom) {
        if (Custom instanceof JUnitInfo) {
            BDUI = (JUnitInfo) Custom;
        }
    }

    public void startEditing(int Row, int Col, JUnitInfo UI, JBDUnitInfo BDUI) {
    }

    /**
     * Invoked when a key has been typed.
     *
     * @param e KeyEvent
     * @todo Implement this java.awt.event.KeyListener method
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Invoked when a key has been pressed.
     *
     * @param e KeyEvent
     * @todo Implement this java.awt.event.KeyListener method
     */
    public void keyPressed(KeyEvent e) {
        /**
         * ���������ڿ�������Ϸ������ں͡�δԼ����
         * add by gengeng 2008-9-28
         */
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            Object o = getEditor().getItem();
            if (o != null) {
                if ("δԼ��".equals(o.toString())
                    || validator.accept(validator.patternDate, o.toString())) {
                    setSelectedItem(o.toString());
                }
            }
        }
    }

    /**
     * Invoked when a key has been released.
     *
     * @param e KeyEvent
     * @todo Implement this java.awt.event.KeyListener method
     */
    public void keyReleased(KeyEvent e) {
    }

}
