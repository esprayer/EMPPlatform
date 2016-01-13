package jenterprise.bof.classes.AppExplorerObject.taskManager;

import java.net.*;
import java.util.*;

import javax.swing.*;

import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jframework.foundation.classes.*;

/**
 * 定制任务公用函数类。
 *
 * @version 1.0 初始版本${gengeng}
 */
public final class JTaskUtil {

    /** 配置文件 */
    private final static String ConfigureFileName  = "jenterprise.bof.classes.AppExplorerObject.taskManager.JTaskUtil.taskInit";

    /** 任务ID关键字 */
    private final static String TaskNameIDKey      = "TaskNameID";

    /** 任务描述关键字 */
    private final static String TaskNameCaptionKey = "TaskNameCaption";

    /** 加载配置文件 */
    private static ResourceBundle _rscBundle;
    static {
        try {
            _rscBundle = ResourceBundle.getBundle(ConfigureFileName, Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 私有构造
     */
    private JTaskUtil() {
    }

    /**
     * 返回任务id
     */
    public static String[] getTaskActionID() {
        return getArray(TaskNameIDKey);
    }

    /**
     * 返回任务描述
     */
    public static String[] getTaskActionCaption() {
        return getArray(TaskNameCaptionKey);
    }

    /**
     * 返回配置文件中的数组。
     */
    private static String[] getArray(String key) {
        String content = _rscBundle.getString(key);
        try {
            String str = new String(content.getBytes("iso-8859-1"), "gb2312");
            if (str.indexOf(",") >= 0)
                return str.split(",");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据关键字返回值。
     */
    public static String getValueByKey(String key) {
        if (key != null) {
            return _rscBundle.getString(key);
        }
        return null;
    }

    /**
     * 返回图标资源。
     */
    public static ImageIcon getIcon(String iconName) {
        ImageIcon iicon = null;
        String className = JTaskUtil.class.getName();
        try {
            className = className.replace('.', '/');
            className = className + "/icons/" + iconName;
            URL url = JTaskUtil.class.getClassLoader().getResource(className);
            if (url == null)
                return null;
            iicon = new ImageIcon(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return iicon;
    }

    /**
     * 产生一个任务编号.
     */
    public static String generateTaskID() {
        String tCode = null;
        JParamObject PO = JParamObject.Create();
        JResponseObject RO;
        RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject", "generateTaskCode", PO);
        if (RO != null) {
            if (RO.ErrorCode == -1) {
                JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, "生成任务编号失败！原因：\r\n" + RO.ErrorString);
                return null;
            }
            tCode = (String) RO.ResponseObject;
        } else {
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, "生成任务编号失败！原因：\r\n远程调用失败！");
        }
        return tCode;
    }

    /**
     * 返回任务列表。
     */
    public static Vector getScheduledTaskList() {
        Vector taskList = new Vector();
        Vector taskObjs = getScheduledTaskObjectList();
        if (taskObjs != null) {
            JTaskObject taskObj;
            Object[] taskObject; //依次是编号、名称、任务体、备用一、备用二、备用三
            for (Iterator it = taskObjs.iterator(); it.hasNext(); ) {
                taskObject = (Object[]) it.next();
                byte[] b = (byte[]) taskObject[2];
                taskObj = new JTaskObject(new String(b));
                taskList.add(taskObj);
            }
        }
        return taskList;
    }

    /**
     * 获取任务原型。
     */
    public static Vector getScheduledTaskObjectList() {
        Vector taskList = null;
        JParamObject PO = JParamObject.Create();
        JResponseObject RO;
        RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject", "getScheduledTaskList", PO);
        if (RO != null) {
            if (RO.ErrorCode == -1) {
                JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, "获取任务列表时失败！原因：\r\n" + RO.ErrorString);
                return null;
            }
            taskList = (Vector) RO.ResponseObject;
        } else {
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, "获取任务列表时失败！原因：\r\n远程调用失败！");
        }
        return taskList;
    }

    /**
     * 保存任务
     */
    public static Object saveScheduledTaskList(Vector taskList) {
        JTaskObject tObj;
        if (taskList != null) {
            Vector tv = new Vector();
            Object[] to;
            for (Iterator it = taskList.iterator(); it.hasNext(); ) {
                tObj = (JTaskObject) it.next();
                to = new Object[6];
                to[0] = tObj.taskID;
                to[1] = tObj.taskMC;
                to[2] = tObj.xmlString.getBytes();
                to[3] = "";
                to[4] = "";
                to[5] = "";
                tv.add(to);
                to = null;
            }
            return saveTaskList(tv);
        }
        return null;
    }

    /**
     * 保存任务列表。
     */
    private static Object saveTaskList(Vector tv) {
        Object rtnObj = null;
        JParamObject PO = JParamObject.Create();
        JResponseObject RO;
        RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject", "saveScheduledTaskList", PO, tv, null, null);
        if (RO != null) {
            if (RO.ErrorCode == -1) {
                JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, "保存任务列表时失败！原因：\r\n" + RO.ErrorString);
                return null;
            }
            rtnObj = RO.ResponseObject;
        } else {
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, "保存任务列表时失败！原因：\r\n远程调用失败！");
        }
        return rtnObj;
    }

    /**
     * 取任务日志.看一个任务取其日志,并保存到内存中.
     */
    public static Object getTaskLog() {
        return null;
    }
}
