package jenterprise.bof.classes.AppExplorerObject.messlist;

import java.net.*;

import javax.swing.*;
import java.io.Serializable;

/**
 * 图标消息条目。
 *
 * @version 1.0 初始版本${gengeng}
 */
public class MessageItem
    implements Serializable {

    private Icon        icon; //图标
    private boolean   isRead; //是否已经阅读过
    public String       F_ID; //编号
    public String     F_XTBH; //系统编号
    public String     F_TYPE; //类型：通知/消息
    public String     F_SEND; //发送者
    public String     F_RECR; //接受者
    public String     F_DATE; //日期
    public String     F_TIME; //时间
    public String  F_MESSCAP; //消息标题
    public String F_MESSLINK; //消息附加信息连接（如果存在的话是一个URL）
    public Object  F_MESSOBJ; //消息对象
    public String   F_OBJECT; //对象名称
    public String   F_METHOD; //方法名称
    public String   F_PARAM1; //参数一
    public String   F_PARAM2; //参数二
    public String   F_PARAM3; //参数三

    /**
     *
     * @param icon Icon   图标名称
     * @param text String 消息内容
     */
    public MessageItem(Icon icon) {
        this.icon = icon;
    }

    /**
     *
     * @param text String
     */
    public MessageItem() {
        this("default.png");
    }

    /**
     *
     * @param icon Icon   图标名称
     */
    public MessageItem(String icon) {
        setIcon(getIcon(icon));
    }

    /**
     *
     * @param icon String
     * @return ImageIcon
     */
    private ImageIcon getIcon(String icon) {
        ImageIcon iicon = null;
        String className = this.getClass().getName();
        try {
            className = className.replace('.', '/');
            className = className + "/icons/" + icon;
            URL url = this.getClass().getClassLoader().getResource(className);
            if (url == null)
                return null;
            iicon = new ImageIcon(url);
        } catch (Exception ex) {
        }
        return iicon;
    }

    /**
     *
     * @return Icon
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     *
     * @return String
     */
    public String getText() {
        return F_MESSCAP;
    }

    /**
     * 返回该条目是否读过。
     * @return boolean
     */
    public boolean isReaded() {
        return isRead;
    }

    /**
     *
     * @param isRead boolean
     */
    public void setReaded(boolean isRead) {
        this.isRead = isRead;
    }

    /**
     *
     * @param icon Icon
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    /**
     *
     * @return boolean
     */
    public boolean isHelpable() {
        if (F_MESSOBJ != null) {
            byte data[] = (byte[]) F_MESSOBJ;
            if (data != null && data.length != 0) {
                return true;
            }
        }
        if (F_METHOD != null && F_METHOD.trim().length() != 0) {
            return true;
        }
        return false;
    }
}
