package jservice.jbof.classes.GenerQueryObject.action.util;

import java.lang.reflect.*;

import com.f1j.swing.*;
import jframework.resource.classes.JMulLangF1Object;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBookDialogProxy
    extends Thread {
    /**
     *
     */
    private JBook mBook = null;
    private Class mClazz = null;
    private boolean mIsModal = true;
    private JBookFormatCellsDlg formatCellsDlg=new JBookFormatCellsDlg();
    /**
     *
     * @param book
     * @param clazz
     */
    public JBookDialogProxy(Class clazz, JBook book, boolean isModal) {
        this.mBook = book;
        this.mClazz = clazz;
        this.mIsModal = isModal;
    }

    /**
     *
     */
    public void run() {
        try {
            Constructor constructor = mClazz.getConstructor(new Class[] {JBook.class});
            Dialog dlg = (Dialog) constructor.newInstance(new Object[] {mBook});
            if (dlg != null) {
                //因为FormatCellsDlg的翻译有冲突，所以特殊处理
                if(dlg instanceof com.f1j.swing.ss.FormatCellsDlg){
                    dlg.addWindowListener(new JMulLangF1Object(formatCellsDlg)); //多语言支持
                }else{
                    dlg.addWindowListener(new JMulLangF1Object(this)); //多语言支持
                }
                dlg.setModal(mIsModal);
                dlg.show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}