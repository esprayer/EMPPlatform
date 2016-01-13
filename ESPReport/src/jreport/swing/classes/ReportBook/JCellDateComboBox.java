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
 * <p>Title: 报表枚举字段中的日期选择框</p>
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
    private RegexValidator validator = new RegexValidator();//正则表达式验证器

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
                 * 将2005-12-12改为2005.12.12
                 * 因为CS的F1版本不支持这种格式
                 */
                value = value.replaceAll("-", ".");
                value = value.replaceAll("/", ".");
                BDUI.DYZD_SJ = value;
                ReportView.setPhyText(Row, Col, BDUI.DYZD_SJ);
                ReportView.BookTextField.setText(value, true);
                BDUI.setModified();
                /**
                 * 设置Model为修改状态
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
     * 判断当前单元格数据是否正确
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
             * 股份报告处需求，日期可以写“未约定”
             * add by gengeng 2008-9-28
             */
            if (!"未约定".equals(date) && date.length() < 10) {
                setData(date);
                return;
            }
            if (!"未约定".equals(date) && !validator.accept(validator.patternDate, date)) {
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
     * 取当前编辑控件
     * @return Component
     */
    public Component getComponent() {
        return this;
    }

    /**
     * 获取编辑组件
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
         * 允许在日期框中输入合法的日期和“未约定”
         * add by gengeng 2008-9-28
         */
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            Object o = getEditor().getItem();
            if (o != null) {
                if ("未约定".equals(o.toString())
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
