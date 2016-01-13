package com.efounder.object;

/**
 * Title:        Java Framework
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      efounder Ltd.
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述: 该接口是系统中所有自定义接口的父接口(从Java中继承的接口除外)
//设计: Skyline(2002.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public interface IDComInterface {
    // 查询接口函数
    Object QueryInterface(String GUID);
    // 获取对象或接口名称
    String getObjectName();
    void   setObjectName(String ObjectName);
    // 获取对象或接口的GUID
    String getObjectGUID();
    void   setObjectGUID(String ObjectGUID);
    // 返回接口的类型
    int    getInterfaceType();
}

