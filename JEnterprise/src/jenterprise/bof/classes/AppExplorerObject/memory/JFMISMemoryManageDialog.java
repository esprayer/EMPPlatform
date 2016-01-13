package jenterprise.bof.classes.AppExplorerObject.memory;

import java.awt.*;

import jfoundation.gui.window.classes.*;

/**
 * <p>Title: FMIS内存管理窗口</p>
 * <p>Description: 查看内存的使用情况、运行垃圾收集器等</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class JFMISMemoryManageDialog
    extends JFrameDialog {

    Frame frame;

    public JFMISMemoryManageDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        this.frame = frame;
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        this.getContentPane().add(memoryShow, java.awt.BorderLayout.CENTER);
        this.pack();
        this.setSize(320, 240);
        this.setLocation();
        this.setVisible(true);
    }

    /**
     * 设置监控窗口的位置，在FMIS的左下角
     */
    private void setLocation() {
        if (frame != null) {
            Dimension d1 = frame.getSize();
            double h1 = d1.getHeight();
            double w1 = d1.getWidth();
            Dimension d2 = getSize();
            double h2 = d2.getHeight();
            double w2 = d2.getWidth();
            Point p = frame.getLocationOnScreen();
            int x = p.x + 4;
            int y = (int) (h1 - h2 - 35);
            this.setLocation(x, y);
        }
    }

    BorderLayout borderLayout1 = new BorderLayout();
    MemoryShowPanel memoryShow = new MemoryShowPanel();
}
