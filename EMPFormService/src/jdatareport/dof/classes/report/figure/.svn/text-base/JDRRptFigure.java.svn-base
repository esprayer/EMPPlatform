package jdatareport.dof.classes.report.figure;

import java.util.*;

/**
 *
 * <p>Title: JDRRptFigure</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */
public class JDRRptFigure
    extends JDRDefaultFigure {
    /**
     *
     */
    private Vector mCellFigures = new Vector();
    /**
     * 构造方法
     */
    public JDRRptFigure() {
        super();
    }

    /**
     * 获得包含的所有JDRCellFigure
     * @return
     */
    public Vector getCellFigures() {
        return mCellFigures;
    }

    /**
     * 设置包含的所有JDRCellFigure
     * @param figures
     */
    public void setCellFigures(Vector figures) {
        if (figures != null) {
            this.mCellFigures = figures;
        }
    }

    /**
     * 添加包含的JDRCellFigure
     * @param figure
     */
    public void addCellFigure(JDRCellFigure figure) {
        if (figure != null) {
            this.mCellFigures.add(figure);
        }
    }
}