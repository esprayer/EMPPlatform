package jenterprise.bof.classes.AppExplorerObject.taskManager.wizard;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.pansoft.swing.waiting.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JActionPanel.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JParamPanel.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JSchedulePanel.*;
import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jfoundation.gui.window.classes.*;
import jframework.foundation.classes.*;

/**
 * 任务添加向导。
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JAddTaskWizardDialog
    extends JFrameDialog implements ActionListener {

    private JSelectActionPanel actionPanel   = new JSelectActionPanel();
    private JSelectParamPanel  paramPanel    = new JSelectParamPanel();
    private JSchedulePanel     schedulePanel = new JSchedulePanel();
    private JMessagePanel      messagePanel  = new JMessagePanel();
    private IStepable[]        steps         = new IStepable[3];
    private String[]           stepName      = {"selectAction", "selectParam", "schedule", "message"};
    private String[]           stepMessage   = {"第一步：选择任务动作。", "第二步：设置任务必需的参数。", "第三步：制定任务执行计划。", "第四步：任务确认信息。"};
    private int                index;

    public final int OPTION_OK     = 1;
    public final int OPTION_CANCEL = 2;
    public       int OPTION;

    public JAddTaskWizardDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            init();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化。
     */
    private void init() {
        steps[0] = actionPanel;
        steps[1] = paramPanel;
        steps[2] = schedulePanel;
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        cancelButton.setPreferredSize(new Dimension(80, 25));
        cancelButton.setMnemonic('C');
        cancelButton.setText("取消(C)");
        cancelButton.setToolTipText("取消");
        cancelButton.addActionListener(this);
        nextButton.setPreferredSize(new Dimension(100, 25));
        nextButton.setMnemonic('N');
        nextButton.setText("下一步(N)");
        nextButton.setToolTipText("下一步");
        nextButton.addActionListener(this);
        previousButton.setPreferredSize(new Dimension(100, 25));
        previousButton.setToolTipText("上一步");
        previousButton.setMnemonic('P');
        previousButton.setText("上一步(P)");
        previousButton.addActionListener(this);
        cmdPanel.setLayout(flowLayout1);
        mainPanel.setLayout(cardLayout);
        infoPanel.setLayout(borderLayout3);
        topPanel.setLayout(flowLayout2);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        centerPanel.setLayout(borderLayout2);
        cmdPanel.setBorder(border1);
        topPanel.setBorder(border2);
        cmdPanel.add(previousButton);
        cmdPanel.add(nextButton);
        cmdPanel.add(cancelButton);
        this.getContentPane().add(topPanel, java.awt.BorderLayout.NORTH);
        topPanel.add(infoLabel);
        this.getContentPane().add(infoPanel, java.awt.BorderLayout.WEST);
        mainPanel.add(actionPanel,stepName[0]);
        mainPanel.add(paramPanel,stepName[1]);
        mainPanel.add(schedulePanel,stepName[2]);
        mainPanel.add(messagePanel,stepName[3]);
        this.getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);
        centerPanel.add(mainPanel, java.awt.BorderLayout.CENTER);
        infoPanel.add(iconLabel, java.awt.BorderLayout.CENTER);
        infoPanel.add(jPanel1, java.awt.BorderLayout.SOUTH);
        infoPanel.add(jPanel2, java.awt.BorderLayout.WEST);
        infoPanel.add(jPanel3, java.awt.BorderLayout.NORTH);
        infoPanel.add(jPanel4, java.awt.BorderLayout.EAST);
        this.getContentPane().add(cmdPanel, java.awt.BorderLayout.SOUTH);
        iconLabel.setPreferredSize(new Dimension(100, 400));
        iconLabel.setIcon(JTaskUtil.getIcon("info.jpg"));
        infoLabel.setText(stepMessage[0]);
        previousButton.setEnabled(false);
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel mainPanel = new JPanel();
    JPanel cmdPanel = new JPanel();
    JButton cancelButton = new JButton();
    JButton nextButton = new JButton();
    JButton previousButton = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    CardLayout cardLayout = new CardLayout();
    JPanel infoPanel = new JPanel();
    JPanel topPanel = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JLabel infoLabel = new JLabel();
    JLabel iconLabel = new JLabel();
    JPanel centerPanel = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    Border border1 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
    Border border2 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
    java.awt.BorderLayout borderLayout3 = new BorderLayout();
    javax.swing.JPanel jPanel1 = new JPanel();
    javax.swing.JPanel jPanel2 = new JPanel();
    javax.swing.JPanel jPanel3 = new JPanel();
    javax.swing.JPanel jPanel4 = new JPanel();

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(final ActionEvent e) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                if (e.getSource() == nextButton) {
                    processNextStep();
                } else if (e.getSource() == previousButton) {
                    processPreviousStep();
                } else if (e.getSource() == cancelButton) {
                    processCancelEvent();
                }
            }
        });
        thread.start();
    }

    /**
     * 上一步
     */
    private void processPreviousStep() {
        index--;
        if (index >= 0 && index < stepName.length)
            cardLayout.show(mainPanel, stepName[index]);
        infoLabel.setText(stepMessage[index]);
        manageButton();
    }

    /**
     * 下一步
     */
    private void processNextStep() {
        if (nextButton.getText().indexOf("完成") >= 0) {
            processOK();
            return;
        }
        //处理前三步
        managePreviousSteps();
        //处理一下按钮的显示
        manageButton();
    }

    /**
     * 处理前三步.
     */
    private void managePreviousSteps() throws HeadlessException {
        IStepable step = null;
        step = steps[index];
        if (step.isValid()) {
            index++;
            if (index >= 0 && index < stepName.length) {
                //按钮不可点
                setButtonEnabled(false);
                //选定了任务的动作以后,需要适配一下该动作需要的参数设置接口
                if (index == 1) {
                    if (!adapteParamPanel()) {
                        index--;
                        return;
                    }
                }
                if (index == 3) {
                    setMessage();
                }
                cardLayout.show(mainPanel, stepName[index]);
                //按钮可点
                setButtonEnabled(true);
            }
            infoLabel.setText(stepMessage[index]);
        } else {
            JOptionPane.showMessageDialog(this, step.getInvalidMessage());
        }
    }

    /**
     * 按钮状态.
     */
    private void setButtonEnabled(boolean enabled) {
        nextButton.setEnabled(enabled);
        previousButton.setEnabled(enabled);
        cancelButton.setEnabled(enabled);
    }

    /**
     * 处理最后一步,确认信息的显示.
     */
    private void setMessage() {
        ActionObject action = (ActionObject) actionPanel.getSelectedInfo();
        ParamObject params = (ParamObject) paramPanel.getSelectedInfo();
        ScheduleObject schedule = (ScheduleObject) schedulePanel.getSelectedInfo();
        messagePanel.setActionObject(action);
        messagePanel.setParamObject(params);
        messagePanel.setScheduleObject(schedule);
    }

    /**
     * 确定,关闭向导.
     */
    private void processOK() {
        OPTION = OPTION_OK;
        dispose();
    }

    /**
     * 返回任务对象
     */
    public Object getTaskObject() {
        String tCode = JTaskUtil.generateTaskID();
        if (tCode == null)
            return null;
        ActionObject   action   = (ActionObject) actionPanel.getSelectedInfo();
        ParamObject    params   = (ParamObject) paramPanel.getSelectedInfo();
        ScheduleObject schedule = (ScheduleObject) schedulePanel.getSelectedInfo();
        JTaskObject tObj = new JTaskObject();
        //设置必须参数
        tObj.taskID   = tCode;
        tObj.taskMC   = schedule.taskName;
        tObj.action   = action;
        tObj.param    = params;
        tObj.schedule = schedule;
        //生成一下JTaskObject的xml形式
        tObj.objectToXmlString();

        return tObj;
    }

    /**
     * 适配参数设置界面.
     */
    private boolean adapteParamPanel() {
        try {
            //返回第一步获得对象信息
            IStepable stepOne = actionPanel;
            ActionObject actionObj = (ActionObject) stepOne.getSelectedInfo();
            if (actionObj != null) {
                if (actionObj.paramClass == null || actionObj.paramClass.equals("")) {
                    throw new Exception("没有找到该动作的参数配置类！");
                }
                JPanel p = paramPanel.getParamPanel();
                if (p == null) {
                    JWaitingManager.beginWait("正在适配参数设置界面，请稍等......");
                    p = (JPanel) Class.forName(actionObj.paramClass).newInstance();
                    paramPanel.addParamPanel(p);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "适配参数设置界面时失败！原因：\r\n" + ex.getMessage());
            return false;
        } finally {
            JWaitingManager.endWait();
        }

        return true;
    }

    /**
     * 设置上一步和下一步按钮.
     */
    private void manageButton() {
        boolean isFirst = index == 0;
        boolean isLast  = index == 3;
        previousButton.setEnabled(!isFirst);
        if (isLast) {
            nextButton.setText("完成(F)");
            nextButton.setToolTipText("完成");
            nextButton.setMnemonic('F');
        } else {
            nextButton.setMnemonic('N');
            nextButton.setText("下一步(N)");
            nextButton.setToolTipText("下一步");
        }
    }

    /**
     * 取消
     */
    private void processCancelEvent() {
        OPTION = OPTION_CANCEL;
        dispose();
    }

}
