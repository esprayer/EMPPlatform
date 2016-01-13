package jenterprise.bof.classes.AppExplorerObject.taskManager.wizard;

import java.awt.*;
import javax.swing.*;

import jenterprise.bof.classes.AppExplorerObject.taskManager.module.*;

/**
 * 设置参数信息。
 * 由于每个模块所涉及的任务的参数的多样性，用一个统一的窗口去实现参数的设置显得比较困难。
 * 所以将每个模块的参数设置界面细分到一个具体的Panel中，每个模块实现一个参数设置Panel，
 * 这些Panel都实现了一个共同的接口<code>ITaskParamable</code>，每个Panel最终返回
 * 的是统一了数据格式的参数信息。
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JSelectParamPanel
    extends JPanel implements IStepable {

    private ITaskParamable paramPanel;

    public JSelectParamPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 验证步骤的合法性。
     *
     * @return boolean
     * @todo Implement this
     *   jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.IStepCheckable
     *   method
     */
    public boolean isValid() {
        Object paramObject;
        if (paramPanel != null) {
            paramObject = paramPanel.getParamObject();
            return paramObject != null;
        }
        return false;
    }

    /**
     * 返回不合法的信息。
     *
     * @return String
     * @todo Implement this
     *   jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.IStepCheckable
     *   method
     */
    public String getInvalidMessage() {
        if (paramPanel != null) {
            return paramPanel.getErrorMessage();
        }
        return "";
    }

    /**
     * 返回该步骤选择的数据。
     *
     * @return Object
     * @todo Implement this
     *   jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.IStepable
     *   method
     */
    public Object getSelectedInfo() {
        return paramPanel.getParamObject();
    }

    /**
     * 添加参数设置界面.
     */
    public void addParamPanel(JPanel panel) {
        if (panel != null) {
            this.add(panel, java.awt.BorderLayout.CENTER);
            this.paramPanel = (ITaskParamable)panel;
        }
    }

    /**
     * 返回参数设置Panel
     */
    public JPanel getParamPanel() {
        return (JPanel)paramPanel;
    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());
    }

}
