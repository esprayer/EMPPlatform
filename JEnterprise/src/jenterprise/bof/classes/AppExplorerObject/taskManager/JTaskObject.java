package jenterprise.bof.classes.AppExplorerObject.taskManager;

import java.io.*;
import java.util.*;
import java.util.Vector;

import org.jdom.Element;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JActionPanel.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JParamPanel.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JSchedulePanel.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JTimePanel.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.subdlg.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.subdlg.JManageCTimeDialog.*;
import jfoundation.dataobject.classes.*;
import com.pansoft.pub.util.Debug;

/**
 * 一个完整的任务对象．
 * 这个类提供了xml和对象之间的转换功能.
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JTaskObject
    implements Serializable {

    public String         taskID;   //任务编号
    public String         taskMC;   //任务名称
    public ActionObject   action;   //任务的动作对象
    public ScheduleObject schedule; //任务的计划对象
    public ParamObject    param;    //任务的参数对象
    public String         xmlString;//任务的xml形式

    private JDataObject   xmlObject;//处理xml的对象

    private String tInfo     = "Info";
    private String tSchedule = "Schedule";
    private String tParam    = "Param";
    private String tAction   = "Action";
    private String tContent  = "Content";
    private String tMode     = "Mode";

    /**
     * 构造一个任务名称为null的对象
     */
    public JTaskObject() {
        xmlObject = new JDataObject();
    }

    /**
     * 构造一个任务名称是tName的对象
     */
    public JTaskObject(String dataString) {
        xmlObject = new JDataObject(dataString);
        xmlString = dataString;
        xmlStringToObject();
    }

    /**
     * 对象数据转化为xml字符
     */
    public void objectToXmlString() {
        //创建信息节点
        Element     infoNode = createInfoNode();
        //创建计划节点
        Element scheduleNode = createScheduleNode();
        //创建动作节点
        Element   actionNode = createActionNode();
        //创建参数节点
        Element    paramNode = createParamNode();

        xmlObject.AddChildElement(null, infoNode);
        xmlObject.AddChildElement(null, scheduleNode);
        xmlObject.AddChildElement(null, actionNode);
        xmlObject.AddChildElement(null, paramNode);

        xmlString = xmlObject.GetRootXMLString();
        Debug.PrintlnMessageToSystem("生成的任务的xml格式：\r\n" + xmlString);
    }

    /**
     * 创建信息节点.
     */
    private Element createInfoNode() {
        Element infoNode = xmlObject.CreateElement(tInfo);
        infoNode.setAttribute("id",taskID);
        infoNode.setAttribute("caption",schedule.taskName);

        return infoNode;
    }

    /**
     * 创建计划节点
     */
    private Element createScheduleNode() {
        Element scheduleNode = xmlObject.CreateElement(tSchedule);
        Element contentNode = xmlObject.AddChildElement(scheduleNode, tContent);
        contentNode.setAttribute("id", String.valueOf(schedule.modeID));
        contentNode.setAttribute("caption", schedule.modeName);
        Element modeNode = xmlObject.AddChildElement(contentNode, tMode);
        switch (schedule.modeID) {
            case JSchedulePanel.ScheduleTypeNone:
                break;
            case JSchedulePanel.ScheduleTypeTime:
                setTimeAttributes(modeNode);
                break;
            case JSchedulePanel.ScheduleTypeCTime:
                setCTimeAttributes(modeNode);
                break;
            case JSchedulePanel.ScheduleTypeEvent:
                break;
        }

        return scheduleNode;
    }

    /**
     * 设置时间对象的属性
     */
    private void setTimeAttributes(Element modeNode) {
        TimeObject tObj = (TimeObject) schedule.modeContent;
        modeNode.setAttribute("id", String.valueOf(tObj.timeModeID));
        modeNode.setAttribute("caption", tObj.timeModeCaption);
        modeNode.setAttribute("date", tObj.date == null ? "" : tObj.date);
        modeNode.setAttribute("time", tObj.time == null ? "" : tObj.time);
        modeNode.setAttribute("hour", tObj.hour == null ? "" : tObj.hour);
        modeNode.setAttribute("minute", tObj.minute == null ? "" : tObj.minute);
        modeNode.setAttribute("second", tObj.second == null ? "" : tObj.second);
    }

    /**
     * 设置复杂时间对象
     */
    private void setCTimeAttributes(Element modeNode) {
        Vector ctimeList = (Vector) schedule.modeContent;
        JCTimeObject ctObj;
        for (Iterator it = ctimeList.iterator(); it.hasNext(); ) {
            ctObj = (JCTimeObject) it.next();
            modeNode.setAttribute("year",  ctObj.year);
            modeNode.setAttribute("month", ctObj.month);
            modeNode.setAttribute("day",   ctObj.day);
            modeNode.setAttribute("time",  ctObj.time);
        }
    }

    /**
     * 创建动作节点
     */
    private org.jdom.Element createActionNode() {
        Element actionNode  = xmlObject.CreateElement(tAction);
        Element contentNode = xmlObject.AddChildElement(actionNode, tContent);
        contentNode.setAttribute("objectID", action.objectID);
        contentNode.setAttribute("actionID", action.actionID);
        contentNode.setAttribute("actionMC", action.actionMC);
        contentNode.setAttribute("paramCls", action.paramClass);

        return actionNode;
    }

    /**
     * 创建参数节点
     */
    private Element createParamNode() {
        Element paramNode = xmlObject.CreateElement(tParam);
        Element contentNode = xmlObject.AddChildElement(paramNode, tContent);
        switch (param.paramType) {
            case 1:
                contentNode.setAttribute("paramType", String.valueOf(param.paramType));
                contentNode.setAttribute("rptType", param.rptReportType);
                contentNode.setAttribute("rptDate", param.rptReportDate);
                contentNode.setAttribute("rptCode", arrayToString(param.rptReportList.toArray()));
                contentNode.setAttribute("addCode", arrayToString(param.rptUnitList.toArray()));
                break;
        }

        return paramNode;
    }

    /**
     * 数组元素转化为字符串
     */
    private String arrayToString(Object[] array) {
        String rtn = "";
        if (array != null) {
            for (int i = 0, n = array.length; i < n; i++) {
                rtn = rtn.concat(array[i] + ",");
            }
            if (rtn.indexOf(",") >= 0) {
                rtn = rtn.substring(0, rtn.length() - 1);
            }
        }
        return rtn;
    }

    /**
     * xml字符转化为对象数据
     */
    public void xmlStringToObject() {
        if (xmlObject != null) {
            //Info节点
            manageInfoNode();
            //Schedule节点
            manageScheduleNode();
            //Action节点
            manageActionNode();
            //Param节点
            manageParamNode();
        }
    }

    /**
     * Info节点
     */
    private void manageInfoNode() {
        Element infoE = xmlObject.GetElementByName(tInfo);
        if (infoE != null) {
            taskID = infoE.getAttributeValue("id", "");
            taskMC = infoE.getAttributeValue("caption", "");
        }
    }

    /**
     * Schedule节点
     */
    private void manageScheduleNode() {
        schedule = new ScheduleObject();
        Element scheduleE = xmlObject.GetElementByName(tSchedule);
        if (scheduleE != null) {
            Element contentE = xmlObject.GetElementByName(scheduleE, tContent);
            String modeID = contentE.getAttributeValue("id","");
            int index = Integer.parseInt(modeID);
            Element modeE = xmlObject.GetElementByName(contentE, tMode);
            Object modeObj = null;
            switch (index) {
                case 1:
                    break;
                case 2://按时间方式运行
                    modeObj = getTimeObject(modeE);
                    break;
                case 3://按事件方式运行
                    break;
                case 4://按复杂时间方式运行
                    modeObj = getCTimeObject(modeE);
                    break;
            }
            schedule.modeID      = Integer.parseInt(modeID);
            schedule.modeName    = contentE.getAttributeValue("caption","");
            schedule.taskName    = taskMC;
            schedule.modeContent = modeObj;
        }
    }

    /**
     * 时间对象
     */
    private Object getTimeObject(Element modeE) {
        TimeObject tObj = new TimeObject();
        tObj.timeModeID      = Integer.parseInt(modeE.getAttributeValue("id",""));
        tObj.timeModeCaption = modeE.getAttributeValue("caption","");
        tObj.date            = modeE.getAttributeValue("date","");
        tObj.time            = modeE.getAttributeValue("time","");
        tObj.hour            = modeE.getAttributeValue("hour","");
        tObj.minute          = modeE.getAttributeValue("minute","");
        tObj.second          = modeE.getAttributeValue("second","");

        return tObj;
    }

    /**
     * 复杂时间对象
     */
    private Object getCTimeObject(Element modeE) {
        JCTimeObject ctObj = new JCTimeObject();
        ctObj.year  = modeE.getAttributeValue("year", "");
        ctObj.month = modeE.getAttributeValue("month", "");
        ctObj.day   = modeE.getAttributeValue("day", "");
        ctObj.time  = modeE.getAttributeValue("time", "");

        return ctObj;
    }

    /**
     * Action节点
     */
    private void manageActionNode() {
        Element actionE = xmlObject.GetElementByName(tAction);
        Element content = xmlObject.GetElementByName(actionE, tContent);
        String actionID = content.getAttributeValue("actionID","");
        String actionMC = content.getAttributeValue("actionMC","");
        String objectID = content.getAttributeValue("objectID","");
        String paramCls = content.getAttributeValue("paramCls","");
        action = new ActionObject(actionID, actionMC, objectID);
        action.setParamClassName(paramCls);
    }

    /**
     * Param节点
     */
    private void manageParamNode() {
        Element paramE  = xmlObject.GetElementByName(tParam);
        Element content = xmlObject.GetElementByName(paramE, tContent);
        String paramID  = content.getAttributeValue("paramType", "");
        param = new ParamObject(Integer.parseInt(paramID));
        param.rptReportDate = content.getAttributeValue("rptDate","");
        param.rptReportType = content.getAttributeValue("rptType","");
        param.rptReportList = getVectorFromString(content.getAttributeValue("rptCode",""));
        param.rptUnitList   = getVectorFromString(content.getAttributeValue("addCode",""));
    }

    /**
     * 将一个字符串装换为Vector
     */
    private java.util.Vector getVectorFromString(String str) {
        java.util.Vector v = new Vector();
        if (str != null && str.length() > 0) {
            if (str.indexOf(",") >= 0) {
                String[] strs = str.split(",");
                for (int i = 0, n = strs.length; i < n; i++) {
                    v.add(strs[i]);
                }
            }
        }

        return v;
    }

    /**
     * 对象String
     */
    public String toString() {
        return xmlString;
    }

}
