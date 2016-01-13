package jenterprise.bof.classes.AppExplorerObject.taskManager.wizard;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JActionPanel.*;

/**
 * 选择动作。
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JSelectActionPanel
    extends JPanel implements IStepable {

    private DefaultComboBoxModel actionModel = new DefaultComboBoxModel();
    private Vector               allAction   = new Vector();
    private String               errString;

    public JSelectActionPanel() {
        try {
            jbInit();
            prepare();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化.
     */
    private void prepare() {
        actionModel = new DefaultComboBoxModel(JTaskUtil.getTaskActionCaption());
        actionComboBox.setModel(actionModel);
    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());
        centerPanel.setLayout(verticalFlowLayout1);
        verticalFlowLayout1.setAlignment(VerticalFlowLayout.MIDDLE);
        jPanel1.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jLabel1.setText("选择动作：");
        this.add(centerPanel, java.awt.BorderLayout.CENTER);
        centerPanel.add(jPanel1);
        jPanel1.add(jLabel1);
        actionComboBox.setPreferredSize(new Dimension(200, 22));
        jPanel1.add(actionComboBox);
        actionComboBox.setEditable(false);
    }

    JPanel centerPanel = new JPanel();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JPanel jPanel1 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JLabel jLabel1 = new JLabel();
    JComboBox actionComboBox = new JComboBox();

    /**
     * 验证步骤的合法性.
     *
     * @return boolean
     * @todo Implement this
     *   jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.IStepable
     *   method
     */
    public boolean isValid() {
        Object item1 = actionComboBox.getSelectedItem();
        if (item1 == null) {
            errString = "选择的动作为空!";
            return false;
        }
        return true;
    }

    /**
     * 获取非法信息.
     *
     * @return String
     * @todo Implement this
     *   jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.IStepable
     *   method
     */
    public String getInvalidMessage() {
        return errString;
    }

    /**
     * 取得选择的数据.
     *
     * @return Object
     * @todo Implement this
     *   jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.IStepable
     *   method
     */
    public Object getSelectedInfo() {
        ActionObject actionObj = null;
        String[] taskIDs = JTaskUtil.getTaskActionID();
        int index = actionComboBox.getSelectedIndex();
        if (index > -1 && index < taskIDs.length) {
            String taskID = taskIDs[index];
            String taskMC = actionComboBox.getSelectedItem().toString();
            String taskObject = JTaskUtil.getValueByKey(taskID + "Object");
            String paramClass = JTaskUtil.getValueByKey(taskID + "Class");
            actionObj = new ActionObject(taskID, taskMC, taskObject);
            actionObj.setParamClassName(paramClass);
        }
        return actionObj;
    }

    /**
     * 设置动作对象。
     * @param obj ActionObject
     */
    public void setActionObject(ActionObject obj) {
        if (obj != null) {
            actionComboBox.setSelectedItem(obj.actionMC);
        }
    }

}
