package jdatareport.dof.classes.report.design;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDRDesignView
    extends JFrame {
    /**
     *
     */
    private JDRDesignViewPresenter mPresenter = new JDRDesignViewPresenter(this);
    /**
     *
     */
    private JPanel jPanel1 = new JPanel();
    private JMenuBar jMenuBar1 = new JMenuBar();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JSplitPane jSplitPane2 = new JSplitPane();
    private JSplitPane jSplitPane3 = new JSplitPane();
    private JPanel mNodePnl = new JPanel();
    private JPanel mPropsPnl = new JPanel();
    private JPanel mViewPnl = new JPanel();
    private JPanel mMsgPnl = new JPanel();
    /**
     *
     * @throws HeadlessException
     */
    public JDRDesignView() throws HeadlessException {
        try {
            jbInit();
            mPresenter.initGUI();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        jPanel1.setLayout(borderLayout1);
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setDividerSize(6);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jSplitPane1, BorderLayout.CENTER);
        jSplitPane1.add(jSplitPane2, JSplitPane.TOP);
        jSplitPane2.add(jSplitPane3, JSplitPane.LEFT);
        jSplitPane3.add(mNodePnl, JSplitPane.TOP);
        jSplitPane3.add(mPropsPnl, JSplitPane.BOTTOM);
        jSplitPane2.add(mViewPnl, JSplitPane.RIGHT);
        jSplitPane1.add(mMsgPnl, JSplitPane.BOTTOM);
        jSplitPane1.setDividerLocation(150);
        jSplitPane2.setDividerLocation(100);
        jSplitPane3.setDividerLocation(50);

    }

}