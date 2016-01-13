package com.efounder.form.client.component.frame;

import java.awt.event.*;

import javax.swing.*;

import com.efounder.form.client.component.*;
import com.efounder.form.client.component.pic.*;
import com.efounder.form.client.component.frame.thread.WindowStateChangeWin;
import com.efounder.form.client.component.infer.IDiglogPanel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JPICFrame extends JPubFrame {

    public JPICFrame(IDiglogPanel generalPanel,String title) {
        super(generalPanel,title);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    FormPicLabel formPicLabelComp = null;
    WindowStateChangeWin waitWin = null;

    public void windowStateChanged(WindowEvent e) {
//        if(e.getOldState() != e.getNewState()){
//            switch (e.getNewState()) {//e.getNewState()
//                case Frame.MAXIMIZED_BOTH:
//                    // 最大化
//                    break;
//                case Frame.ICONIFIED:
//                    // 最小化
//                    break;
//                 case Frame.NORMAL:
//                    // 恢复
//                    break;
//                 default:
//                    break;
//            }
//        }

        ((PreViewImagePanel)centerPanel).autoFitAction();
    }

    public void call(){
    }

    public void exeWindowStatChange(){
        ((PreViewImagePanel)centerPanel).autoFitAction();
    }

    private void jbInit() throws Exception {
//        this.addWindowStateListener(new JPICFrameWindowStateAdapter(this));
        this.addWindowListener(new JPICFrameWindowAdapter(this));
    }

    public void this_windowActivated(WindowEvent e) {
//        ((PreViewImagePanel)centerPanel).autoFitAction();
    }

    public void windowClosing(WindowEvent e) {
         this.centerPanel.destroy();
//         centerPanel = null;

        if(formPicLabelComp != null){//清除Frame窗口句柄
            formPicLabelComp.setAffixWindow(null);
        }
    }




    public void setFormPicLabelComp(FormPicLabel formPicLabelComp) {
        this.formPicLabelComp = formPicLabelComp;
    }

}


class JPICFrameWindowAdapter extends WindowAdapter {
    private JPICFrame adaptee;
    JPICFrameWindowAdapter(JPICFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void windowActivated(WindowEvent e) {
        adaptee.this_windowActivated(e);
    }
    public void windowClosing(WindowEvent e) {
        adaptee.windowClosing(e);
    }

}


class JPICFrameWindowStateAdapter implements WindowStateListener {
    private JPICFrame adaptee;
    JPICFrameWindowStateAdapter(JPICFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void windowStateChanged(WindowEvent e) {
        adaptee.windowStateChanged(e);
    }
}
