package jdatareport.dof.classes.report.event;

import java.util.*;

/**
 * <p>Title: JDREvent</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JDREvent
    extends EventObject {
    /**
     * 事件类型
     */
    public static final int TYPE_BEFORE_INIT = 0; //初始化前
    public static final int TYPE_AFTER_INIT = 1; //初始化后
    public static final int TYPE_BEFORE_LOAD = 2; //加载前
    public static final int TYPE_AFTER_LOAD = 3; //加载后

    private int mEventType = -1;

    /**
     * 构造方法
     * @param source      事件源
     * @param eventType   事件类型
     */
    public JDREvent(Object source, int eventType) {
        super(source);
        mEventType = eventType;
    }
}