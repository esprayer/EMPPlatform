package jdatareport.dof.classes.report.design;

import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDRDesignViewPresenter {
    /**
     *
     */
    private JDRDesignView mView = null;
    /**
     *
     * @param view
     */
    public JDRDesignViewPresenter(JDRDesignView view) {
        if (view == null) {
            throw new IllegalArgumentException("null JDRDesingView");
        }
        this.mView = view;
    }

    /**
     *
     */
    protected void initGUI() throws Exception {
        //最大化
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mView.setSize(screenSize);
    }
}