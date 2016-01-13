package jdcom.foundation.classes;

import java.lang.reflect.*;
/**
 * Title:        Java Framework
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Pansoft Ltd.
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述: 该类是本系统中所有不可视类的父类,凡是本系统中不可视的自定义对象都要从此类继承
//      此类还实现了IDComInterface接口
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JDComObject extends com.efounder.object.JDComObject implements com.efounder.object.IDComInterface {
    public JDComObject() {
        super();
    }
}

