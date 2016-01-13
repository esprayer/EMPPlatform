package jreport.swing.classes.ReportBook;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.f1j.swing.JBook;

import jcomponent.custom.swing.*;
import jframework.resource.classes.*;
import jreport.foundation.classes.data.*;
import jreport.swing.classes.*;
import jreportwh.jdof.classes.common.util.*;
import java.util.ResourceBundle;

/**
 * <p>Title: 枚举类别列表</p>
 * <p>Description: 点击变动区域主行时触发</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JBDCellComboBox
        extends JComboBox implements ICellEditor, ActionListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");

    JUnitInfo UI = null;
    static ImageIcon II = null;
    static JIConListCellRenderer ICR = null;

    public JBDCellComboBox() {
        initCombo();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) {
            endEdit();
        }
    }

    private void endEdit() {
        String sValue, bh, mc;
        JBhMc bhMc = null;
        try {
            bhMc = (JBhMc)this.getSelectedItem();
            if (bhMc != null) {
                bh = bhMc.getBh();
                mc = bhMc.getMc();
                /**
                 * 为与CS兼容
                 * 加入对空值的判断
                 * 2006.1.12
                 */
                if (bh == null || bh.trim().equals("")) {
                    sValue = "";
                } else {
                    sValue = "EM_" + bhMc.getBh() + "_" + bhMc.getMc() + ".txt";
                }
                UI.DYZD_COMBO = sValue;
                UI.setModified();
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

    // 获取编缉组件
    public Component getEditorComponent(JReportView ReportView, Object value, boolean isSelected, int Row, int Col) {
//    	JReportModel RM = (JReportModel) ReportView.getModel();
//    	String bbzd_date = RM.BBZD_DATE;
//    	ArrayList CoverCodeList = CoverCode.getCoverCode(bbzd_date).getCoverCodeFileTable();
//        buildCoverCodeList(CoverCodeList);
//        // 确定当前下拉列表的位置
//        findPos(UI); 
//        // 设置事件处理程序
//        this.addActionListener(this);
//      
//        // 说明不是报表格式
//        if (RM.ADD_BH != null && RM.ADD_BH.trim().length() != 0) {
//            this.setEnabled(false); // 不允许
//        }

        return this;
    }

    /**
     * 查找当前所对应的枚举类型
     * @param UI JUnitInfo
     */
    private void findPos(JUnitInfo UI) {
        String sFind = UI.DYZD_COMBO;
        if (sFind == null || sFind.trim().length() == 0) {
            return;
        }
        JBhMc bhMc;
        String bh, mc;
        for (int i = 0; i < this.getItemCount(); i++) {
            bhMc = (JBhMc)this.getItemAt(i);
            bh = bhMc.getBh();
            mc = bhMc.getMc();
            if (!bh.equals("") && sFind.indexOf(bh) >= 0 && sFind.indexOf(mc) >= 0) {
                this.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * 形成下拉列表
     * @param list ArrayList
     */
    private void buildCoverCodeList(ArrayList list) {
        for (int i = 0; i < list.size(); i++) {
            this.addItem(list.get(i));
        }
    }

    // 开始编缉
    public void startEditing(int Row, int Col, JUnitInfo UI, JBDUnitInfo BDUI) {

    }

    // 结束编缉
    public void endEditing(int Row, int Col, JUnitInfo UI, JBDUnitInfo BDUI) {

    }

    // 取消编缉
    public void cancelEditing(int Row, int Col, JUnitInfo UI, JBDUnitInfo BDUI) {

    }

    public void show(boolean v) {
        setVisible(v);
    }

}
