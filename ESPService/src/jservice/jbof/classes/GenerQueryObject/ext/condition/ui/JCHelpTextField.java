package jservice.jbof.classes.GenerQueryObject.ext.condition.ui;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JCHelpTextField
    extends JTextField {
    public static final short CUSTOM_TYPE_BUTTON = 1;
    public static final short CUSTOM_TYPE_SCROLLBAR = 2;
    private JButton bnCustomButton = null;
    private JScrollBar sbCustomButton = null;
    private Icon iiButton = null;
    short CustomType = 0;
    public static final int WIDTH = 22;
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JCHelpTextField() {
        super();
        setCustomType(CUSTOM_TYPE_BUTTON);
    }

    public JButton getButton() {
        return bnCustomButton;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void setCustomType(short CT) {
        CustomType = CT;
        if (CustomType == CUSTOM_TYPE_BUTTON) {
            InitButton();
        }
        if (CustomType == CUSTOM_TYPE_SCROLLBAR) {
            InitScrollBar();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public short getCustomType() {
        return CustomType;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void setIcon(Icon icon) {
        iiButton = icon;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitButton() {
        if (bnCustomButton != null) {
//      bnCustomButton.removeActionListener(this);
            remove(bnCustomButton);
        }
        if (sbCustomButton != null) {
            remove(sbCustomButton);
//      sbCustomButton.removeAdjustmentListener(this);
        }
        sbCustomButton = null;
        bnCustomButton = new JButton();
        this.add(bnCustomButton);
//    bnCustomButton.addActionListener(this);
        if (iiButton != null) {
            bnCustomButton.setIcon(iiButton);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitScrollBar() {
        if (bnCustomButton != null) {
//      bnCustomButton.removeActionListener(this);
            remove(bnCustomButton);
        }
        if (sbCustomButton != null) {
            remove(sbCustomButton);
//      sbCustomButton.removeAdjustmentListener(this);
        }
        bnCustomButton = null;
        sbCustomButton = new JScrollBar();
        sbCustomButton.setOrientation(sbCustomButton.VERTICAL);
//    sbCustomButton.addAdjustmentListener(this);
        this.add(sbCustomButton);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void paint(Graphics g) {
        super.paint(g);
        if (CustomType == CUSTOM_TYPE_BUTTON) {
            PaintButton();
        }
        if (CustomType == CUSTOM_TYPE_SCROLLBAR) {
            PaintScrollBar();
        }

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void PaintButton() {
        if (bnCustomButton == null) {
            return;
        }
        int x, y, w, h;
        x = this.getWidth() - WIDTH;
        y = 0;
        w = WIDTH;
        h = this.getHeight();
        bnCustomButton.setLocation(x, y);
        bnCustomButton.setSize(w, h);
        if (iiButton != null) {
            bnCustomButton.setIcon(iiButton);
        }
        bnCustomButton.setText("");
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void PaintScrollBar() {
        if (sbCustomButton == null) {
            return;
        }
        int x, y, w, h;
        x = this.getWidth() - WIDTH;
        y = 0;
        w = WIDTH;
        h = this.getHeight();
        sbCustomButton.setLocation(x, y);
        sbCustomButton.setSize(w, h);
    }
}
