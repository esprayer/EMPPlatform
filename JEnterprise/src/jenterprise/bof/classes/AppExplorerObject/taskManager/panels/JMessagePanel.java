package jenterprise.bof.classes.AppExplorerObject.taskManager.panels;

import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import com.borland.jbcl.layout.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JActionPanel.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JParamPanel.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JSchedulePanel.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JTimePanel.*;

/**
 * 确认信息面板.
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JMessagePanel
    extends JPanel {

    private ActionObject actionObj;
    private ParamObject paramObj;
    private ScheduleObject scheduleObj;

    public JMessagePanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 动作对象.
     */
    public void setActionObject(Object action) {
        actionObj = (ActionObject) action;
        if (actionObj != null) {
            String content = "<html><font name = Dialog size=3>动作名称：<font color=#0000ff>";
            String actionID = actionObj.actionID;
            String actionMC = actionObj.actionMC;
            content = content.concat(actionMC + "(" + actionID + ")</font></font></html>");
            actionTextArea.setText(content);
        }
    }

    /**
     * 返回动作对象.
     */
    public Object getActionObject() {
        return actionObj;
    }

    /**
     * 参数对象.
     */
    public void setParamObject(Object param) {
        paramObj = (ParamObject) param;
        if (paramObj != null) {
            String content = "";
            switch (paramObj.paramType) {
                case 1:
                    content = setReportParam(paramObj);
                    break;
            }
            paramTextArea.setText(content);
        }
    }

    /**
     * 设置报表参数．
     */
    private String setReportParam(ParamObject paramObj) {
        String rptTypeName   = paramObj.rptReportTypeName;
        String rptType       = paramObj.rptReportType;
        if ("1".equals(rptType))
            rptType = "汇总报表";
        else if ("2".equals(rptType))
            rptType = "责任中心报表";
        else if ("3".equals(rptType))
            rptType = "部门报表";
        else if ("4".equals(rptType))
            rptType = "成本报表";
        else
            rptType = "未知报表类型";
        String rptDateName   = paramObj.rptReportDateName;
        String rptDate       = paramObj.rptReportDate;
        String rptUnitName   = paramObj.rptUnitListName;
        Vector rptUnitList   = paramObj.rptUnitList;
        String rptReportName = paramObj.rptReportListName;
        Vector rptReportList = paramObj.rptReportList;
        String content = "<html><font name = Dialog size=3>";
        content = content.concat(rptTypeName + "<font color=#0000ff>" + rptType + "</font><br>");
        content = content.concat(rptDateName + "<font color=#0000ff>" + rptDate + "</font><br>");
        content = content.concat(rptUnitName + "<font color=#0000ff>" + getCodeString(rptUnitList) + "</font><br>");
        content = content.concat(rptReportName + "<font color=#0000ff>" + getCodeString(rptReportList) + "</font>");

        return content;
    }

    /**
     * 向量转换成字符串．
     */
    private String getCodeString(java.util.Vector list) {
        String rtnString = "";
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            rtnString = rtnString.concat(it.next().toString() + "；");
        }
        return rtnString;
    }

    /**
     * 返回参数对象.
     */
    public Object getParamObject() {
        return paramObj;
    }

    /**
     * 计划对象.
     */
    public void setScheduleObject(Object schedule) {
        scheduleObj = (ScheduleObject) schedule;
        if (scheduleObj != null) {
            taskNameLabel.setText(scheduleObj.taskName);
            String content = "<html><font name = Dialog size=3>";
            content = content.concat("计划名称：<font color=#0000ff>" + scheduleObj.modeName  + "</font><br>");
            String modeContent = "";
            switch (scheduleObj.modeID) {
                case JSchedulePanel.ScheduleTypeNone:
                    break;
                case JSchedulePanel.ScheduleTypeTime:
                    TimeObject tObj = (TimeObject) scheduleObj.modeContent;
                    modeContent = modeContent.concat("计划内容：<font color=#0000ff>" + tObj.toString() + "</font>");
                    break;
                case JSchedulePanel.ScheduleTypeEvent:
                    break;
                case JSchedulePanel.ScheduleTypeCTime:
                    Vector tList = (Vector)scheduleObj.modeContent;
                    modeContent = modeContent.concat("时间列表：<font color=#0000ff>" + getCodeString(tList) + "</font>");
                    break;
            }
            scheduleTextArea.setText(content + modeContent);
        }
    }

    /**
     * 返回计划对象.
     */
    public Object getScheduleObject() {
        return scheduleObj;
    }

    private void jbInit() throws Exception {
        this.setLayout(verticalFlowLayout1);
        verticalFlowLayout1.setVgap(0);
        schedulePanel.setLayout(borderLayout3);
        schedulePanel.setBorder(border2);
        actionPanel.setBorder(border4);
        actionPanel.setLayout(borderLayout2);
        paramPanel.setBorder(border6);
        paramPanel.setLayout(borderLayout1);
        namePanel.setLayout(flowLayout1);
        jLabel1.setText("任务名称：");
        taskNameLabel.setForeground(Color.blue);
        taskNameLabel.setText("");
        flowLayout1.setAlignment(FlowLayout.LEFT);
        scheduleScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scheduleScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scheduleScrollPane.setBorder(null);
        scheduleTextArea.setBorder(null);
        scheduleTextArea.setPreferredSize(new Dimension(0, 48));
        scheduleTextArea.setEditable(false);
        scheduleTextArea.setContentType("text/html");
        actionTextArea.setPreferredSize(new Dimension(6, 32));
        actionTextArea.setEditable(false);
        actionTextArea.setText("");
        actionTextArea.setContentType("text/html");
        paramTextArea.setPreferredSize(new Dimension(6, 64));
        paramTextArea.setEditable(false);
        paramTextArea.setText("");
        paramTextArea.setContentType("text/html");
        paramScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        paramScrollPane.setBorder(null);
        actionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        actionScrollPane.setBorder(null);
        this.add(namePanel);
        this.add(schedulePanel);
        scheduleScrollPane.getViewport().add(scheduleTextArea);
        this.add(actionPanel);
        actionScrollPane.getViewport().add(actionTextArea);
        this.add(paramPanel);
        namePanel.add(jLabel1);
        namePanel.add(taskNameLabel);
        paramPanel.add(paramScrollPane, java.awt.BorderLayout.CENTER);
        actionPanel.add(actionScrollPane, java.awt.BorderLayout.CENTER);
        schedulePanel.add(scheduleScrollPane, java.awt.BorderLayout.CENTER);
        paramScrollPane.getViewport().add(paramTextArea);
    }

    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JPanel schedulePanel = new JPanel();
    JPanel actionPanel = new JPanel();
    JPanel paramPanel = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
    Border border2 = new TitledBorder(border1, "计划");
    Border border3 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
    Border border4 = new TitledBorder(border3, "动作");
    Border border5 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
    Border border6 = new TitledBorder(border5, "参数");
    JPanel namePanel = new JPanel();
    JLabel taskNameLabel = new JLabel();
    JLabel jLabel1 = new JLabel();
    FlowLayout flowLayout1 = new FlowLayout();
    javax.swing.JScrollPane scheduleScrollPane = new JScrollPane();
    javax.swing.JScrollPane paramScrollPane = new JScrollPane();
    java.awt.BorderLayout borderLayout1 = new BorderLayout();
    javax.swing.border.Border border7 = javax.swing.BorderFactory.createEmptyBorder();
    javax.swing.JScrollPane actionScrollPane = new JScrollPane();
    javax.swing.JEditorPane scheduleTextArea = new JEditorPane();
    javax.swing.JEditorPane actionTextArea = new JEditorPane();
    javax.swing.JEditorPane paramTextArea = new JEditorPane();
    java.awt.BorderLayout borderLayout2 = new BorderLayout();
    java.awt.BorderLayout borderLayout3 = new BorderLayout();
}
