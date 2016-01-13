package jenterprise.bof.classes.AppExplorerObject.noticeService;

import java.util.*;

import jfoundation.dataobject.classes.*;
import jtoolkit.scheduler.*;
import com.pansoft.service.JServiceManager;
import jfoundation.bridge.classes.JResponseObject;
import jframework.foundation.classes.JActiveDComDM;
import jenterprise.bof.classes.AppExplorerObject.messlist.MessageItem;
import jenterprise.bof.classes.AppExplorerObject.messlist.JIconList;

/**
 * FMIS公告调度任务。
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JNoticeScheduleTask
    extends ScheduleTask {

    static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.noticeService.Language");
  private List iconList;

    /**
     *
     */
    public JNoticeScheduleTask() {
        this(null);
    }

    /**
     *
     * @param obj Object
     */
    public JNoticeScheduleTask(Object obj) {
        super(obj);
    }

    /**
     *
     * @param obj Object
     */
    public JNoticeScheduleTask(Object obj1, Object obj2) {
        this(obj1);
        this.iconList = (List) obj2;
    }

    /**
     * 执行。
     * @todo Implement this java.lang.Runnable method
     */
    public void run() {
        //从数据库获得数据存放到HashMap中,以消息类别作为关键字
        HashMap dataMap = getDataHashMap(increObject);
        //列名称(PSMESS)
        Vector colNames = (Vector) dataMap.get("ColumnName");

        JIconList icon;
        Vector dataVector, finalVector;
        for (Iterator iter = iconList.iterator(); iter.hasNext(); ) {
            finalVector = new Vector();
            icon = (JIconList) iter.next();
            dataVector = (Vector) dataMap.get(icon.getType());
            if (dataVector == null) {
                dataVector = new Vector();
            }
            HashMap itemMap;
            MessageItem item;
            for (int i = 0, n = dataVector.size(); i < n; i++) {
                itemMap = (HashMap) dataVector.get(i);
                item = new MessageItem();
                String iconName = "";
                if (icon.getType().equals(JNoticeParamObject.TYPE_NOTICE_ID)) {
                    iconName = "notice.png";
                } else if (icon.getType().equals(JNoticeParamObject.TYPE_MESSAGE_ID)) {
                    iconName = "message.png";
                } else {
                    iconName = "default.png";
                }
                item = new MessageItem(iconName);
                item.F_ID       = (String) itemMap.get("F_ID");
                item.F_TYPE     = (String) itemMap.get("F_TYPE");
                item.F_XTBH     = (String) itemMap.get("F_XTBH");
                item.F_SEND     = (String) itemMap.get("F_SEND");
                item.F_RECR     = (String) itemMap.get("F_RECR");
                item.F_DATE     = (String) itemMap.get("F_DATE");
                item.F_TIME     = (String) itemMap.get("F_TIME");
                item.F_MESSCAP  = (String) itemMap.get("F_MESSCAP");
                item.F_MESSLINK = (String) itemMap.get("F_MESSLINK");
                item.F_MESSOBJ  = (Object) itemMap.get("F_MESSOBJ");
                item.F_OBJECT   = (String) itemMap.get("F_OBJECT");
                item.F_METHOD   = (String) itemMap.get("F_METHOD");
                item.F_PARAM1   = (String) itemMap.get("F_PARAM1");
                item.F_PARAM2   = (String) itemMap.get("F_PARAM2");
                item.F_PARAM3   = (String) itemMap.get("F_PARAM3");
                finalVector.add(item);
                item = null;
            }
            icon.setListData(finalVector);
        }
    }

    /**
     * 获取数据.
     *
     * @param increObject Object
     * @return            HashMap
     */
    private HashMap getDataHashMap(Object increObject) {
        JParamObject PO = (JParamObject) increObject;
        PO.SetIntByParamName("TypeCount", 2);
        PO.SetValueByParamName("TypeID1", JNoticeParamObject.TYPE_NOTICE_ID);
        PO.SetValueByParamName("TypeID2", JNoticeParamObject.TYPE_MESSAGE_ID);
        PO.SetIntByParamName("RecentCount", 7);
        PO.SetValueByParamName("FromDate","");
        String url  = PO.GetValueByEnvName("STANDARD_SERVICE","");
        // 如果没有指定标准，则默认取自己的消息
        if (url.trim().length() > 0) {
            PO.SetValueByParamName("CommBase", url); // 去标准服务器上取
            PO.SetValueByParamName("Server", JServiceManager.SV_STANDARD);
        } else {
            PO.SetValueByParamName("CommBase", "");
            PO.SetValueByParamName("Server", "");
        }
        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject", "getMessage", PO);
        HashMap dataMap = (HashMap) RO.ResponseObject;
        if (dataMap == null) {
            dataMap = new HashMap();
        }
        return dataMap;
    }

    /**
     * 根据条件去后台获取相关数据.
     */
    private HashMap testMethod() {
        Vector data1 = new Vector();
        Vector data2 = new Vector();
        HashMap data = new HashMap();

        MessageItem item1 = new MessageItem("notice.png");
        item1.F_MESSCAP = res.getString("String_38");
        MessageItem item2 = new MessageItem("document.png");
        item2.F_MESSCAP = "<html>《报表上报操作手册》<font color=#660000></font></html>";
        item2.F_METHOD = "openHyperlink";
        data1.add(item1);
        data1.add(item2);

        MessageItem item;
        for (int i = 0; i < 8; i++) {
            item = new MessageItem("default.png");
            item.F_MESSCAP = generateRandonString();
            item.F_METHOD = "openCard";
            data2.add(item);
            item = null;
        }

        data.put(JNoticeParamObject.TYPE_NOTICE_ID, data1);
        data.put(JNoticeParamObject.TYPE_MESSAGE_ID, data2);

        return data;
    }

    private String generateRandonString() {
        String[] strRandom = {
            "<html>标准编码：<font color=#3333CC>130120023-四川销售分公司十公司</font>被申请<font color=#FF0033>变更</font>；申请单位：<font color=#3333CC>四川销售</font>；审核时间：<font color=#3333CC>2007-07-21 15:10:01</font></html>",
            "<html>标准编码：<font color=#3333CC>130120022-四川销售分公司九公司</font>被申请<font color=#FF0033>变更</font>；申请单位：<font color=#3333CC>四川销售</font>；审核时间：<font color=#3333CC>2007-07-22 15:10:51</font></html>",
            "<html>标准编码：<font color=#3333CC>130120021-四川销售分公司八公司</font>被申请<font color=#FF0033>停用</font>；申请单位：<font color=#3333CC>四川销售</font>；审核时间：<font color=#3333CC>2007-07-22 15:10:56</font></html>",
            "<html>标准编码：<font color=#3333CC>130120037-四川销售分公司七公司</font>被申请<font color=#FF0033>停用</font>；申请单位：<font color=#3333CC>四川销售</font>；审核时间：<font color=#3333CC>2007-07-22 16:10:37</font></html>",
            "<html>标准编码：<font color=#3333CC>130120045-四川销售分公司六公司</font>被申请<font color=#FF0033>停用</font>；申请单位：<font color=#3333CC>四川销售</font>；审核时间：<font color=#3333CC>2007-07-22 17:10:34</font></html>",
            "<html>标准编码：<font color=#3333CC>130120034-四川销售分公司五公司</font>被申请<font color=#FF0033>变更</font>；申请单位：<font color=#3333CC>四川销售</font>；审核时间：<font color=#3333CC>2007-07-23 16:09:48</font></html>",
            "<html>标准编码：<font color=#3333CC>130120004-四川销售分公司四公司</font>被申请<font color=#FF0033>变更</font>；申请单位：<font color=#3333CC>四川销售</font>；审核时间：<font color=#3333CC>2007-07-24 12:09:16</font></html>",
            "<html>标准编码：<font color=#3333CC>130123450-四川销售分公司三公司</font>被申请<font color=#FF0033>变更</font>；申请单位：<font color=#3333CC>四川销售</font>；审核时间：<font color=#3333CC>2007-07-24 13:08:14</font></html>",
            "<html>标准编码：<font color=#3333CC>130123306-四川销售分公司二公司</font>被申请<font color=#FF0033>停用</font>；申请单位：<font color=#3333CC>四川销售</font>；审核时间：<font color=#3333CC>2007-07-25 11:23:45</font></html>",
            "<html>标准编码：<font color=#3333CC>130120000-四川销售分公司一公司</font>被申请<font color=#FF0033>变更</font>；申请单位：<font color=#3333CC>四川销售</font>；审核时间：<font color=#3333CC>2007-07-25 15:10:18</font></html>",
        };
        double randomNum = 9 * Math.random();
        int choice = (int) randomNum;

        return strRandom[choice];
    }
}
