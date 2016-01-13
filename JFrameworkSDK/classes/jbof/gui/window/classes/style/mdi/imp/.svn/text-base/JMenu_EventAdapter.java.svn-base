package jbof.gui.window.classes.style.mdi.imp;

import java.awt.event.*;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */

public class JMenu_EventAdapter implements ActionListener {
  JMenuItemStub adaptee;
    //----------------------------------------------------------------------------------------------
    //描述: 构造函数
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public JMenu_EventAdapter(JMenuItemStub adaptee) {
        this.adaptee = adaptee;
    }
    //----------------------------------------------------------------------------------------------
    //描述: 在菜单中执行某项操作
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e) {
        if ( adaptee != null && adaptee.Application != null ) {
            adaptee.Application.CallOperateItem(adaptee.OperateItem,adaptee,e);
        }
    }
}