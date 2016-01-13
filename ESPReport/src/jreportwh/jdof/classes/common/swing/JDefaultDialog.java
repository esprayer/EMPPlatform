package jreportwh.jdof.classes.common.swing;

import java.beans.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jfoundation.gui.window.classes.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JDefaultDialog
    extends JFrameDialog{

    static ResourceBundle res = ResourceBundle.getBundle("jreportwh.jdof.classes.common.swing.Language");
  public static final int OPTION_CANCEL = 0;
    public static final int OPTION_OK = 1;
    public static final Dimension DEFAULT_SIZE = new Dimension(550, 400);

    private Dimension mMinSize = new Dimension(0, 0);
    private int mOption = 0;

    private Object mUserObject = null;

    /**
     *
     */
    public JDefaultDialog() {
        super( (Frame)null, "", false);
        init();
    }

    /***
     *
     */
    public JDefaultDialog(Frame parent, String title, boolean isModal) {
        super(parent, title, isModal);
        init();
    }


    /**
     *
     * @param parent
     * @param title
     * @param isModal
     */
    public JDefaultDialog(Dialog parent, String title, boolean isModal) {
        super(parent, title, isModal);
        init();
    }

    /**
     *
     */
    private void init() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                onWindowClosing();
            }

            public void windowClosed(WindowEvent windowEvent) {
                onWindowClosed();
            }
        });
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(DEFAULT_SIZE);
        this.centerOnScreen();

    }

    /**
     *
     * @return
     */
    public int getOption() {
        return mOption;
    }

    /**
     *
     * @param option
     */
    public void setOption(int option) {
        this.mOption = option;
    }
    /**
     *
     * @param userObject
     */
    public void setUserObject(Object userObject){
      if(userObject != mUserObject){
        this.mUserObject = userObject;
      }
    }
    /**
     *
     * @return
     */
    public Object getUserObject(){
        return mUserObject;
    }
    /**
     *
     * @param width
     * @param height
     */
    public void setMinimumSize(int width, int height) {
        this.setMinimumSize(new Dimension(width, height));
    }

    /**
     *
     * @param minSize
     */
    public void setMinimumSize(Dimension minSize) {
        if (minSize != null) {
            this.mMinSize = minSize;
        }
    }

    /**
     *
     * @return
     */
    public Dimension getMinimumSize() {
        return this.mMinSize;
    }

    /**
     *
     */
    public void centerOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension window = this.getSize();
        if (window.height > screen.height) {
            window.height = screen.height;
        }
        if (window.width > screen.width) {
            window.width = screen.width;
        }
        int xCoord = (screen.width / 2 - window.width / 2);
        int yCoord = (screen.height / 2 - window.height / 2);
        this.setLocation(xCoord, yCoord);
    }

    /**
     *
     */
    public void centerOnParent() {
        Dimension parent = this.getParent().getSize();
        Dimension window = this.getSize();

        if (parent == null) {
            return;
        }

        int xCoord = this.getParent().getLocationOnScreen().x + (parent.width / 2 - window.width / 2);
        int yCoord = this.getParent().getLocationOnScreen().y + (parent.height / 2 - window.height / 2);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xOffScreenExcess = xCoord + window.width - screen.width;
        if (xOffScreenExcess > 0) {
            xCoord = xCoord - xOffScreenExcess;
        }
        if (xCoord < 0) {
            xCoord = 0;
        }
        int yOffScreenExcess = yCoord + window.height - screen.height;
        if (yOffScreenExcess > 0) {
            yCoord = yCoord - yOffScreenExcess;
        }
        if (yCoord < 0) {
            yCoord = 0;
        }
        this.setLocation(xCoord, yCoord);
    }

    /**
     *
     * @return
     */
    protected JRootPane createRootPane() {
        return new MyRootPane( (JDefaultDialog)this);
    }

    /**
     *
     */
    public void onWindowClosing() {

    }

    /**
     *
     */
    public void onWindowClosed() {

    }
    /**
     *
     * @param btn
     * @param actionMethodName 必须为public
     */
    public void addActionListener(AbstractButton btn, String actionMethodName) {
        if(btn != null && actionMethodName != null){
            btn.addActionListener( (ActionListener) EventHandler.create(
                ActionListener.class,
                this,
                actionMethodName));
        }
    }

  /**
     *
     * <p>Title: </p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    class MyRootPane
        extends JRootPane {
        JDialog mParentDialog = null;
        public MyRootPane(JDialog parentDialog) {
            this.mParentDialog = parentDialog;
        }

//        public void reshape(int i, int j, int k, int l) {
//            Dimension dimension = mMinSize;
//            if (dimension != null && (k < dimension.width || l < dimension.height)) {
//                Dimension dimension1 = mParentDialog.getSize();
//                if (k < dimension.width) {
//                    dimension1.width = (dimension1.width - k) + dimension.width;
//                    k = dimension.width;
//                }
//                if (l < dimension.height) {
//                    dimension1.height = (dimension1.height - l) + dimension.height;
//                    l = dimension.height;
//                }
//                mParentDialog.setSize(dimension1);
//            }
//            super.setBounds(i, j, k, l);
//        }
    }
}
