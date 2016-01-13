package jdcom.foundation.classes;

import javax.swing.*;

/**
 * Title:        Java Framework
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Pansoft Ltd.
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述: 该类是本系统中所有可视对象的父类(不包括从JComponent以上层继承的类)
//      此类还实现了IDComInterface接口
//设计: Skyline(2002.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JDComComponent extends com.efounder.object.JDComComponent implements IDComInterface,IDComObjectInformation {
    public JDComComponent() {

    }
}
