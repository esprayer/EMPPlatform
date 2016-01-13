package jdatareport.dof.classes.report.figure;

import java.awt.*;

/**
 *
 * <p>Title: JDRCellFigure</p>
 * <p>Description:
 * 对于没有显式方法设置的属性可以使用setAttribute方法
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft </p>
 * @author Stephen Zhao
 * @version 1.0
 */
public class JDRCellFigure
    extends JDRDefaultFigure {
    /**
     *
     */
    private String mType = TYPE_TEXT;
    private String mCaption = "";
    private Font mFont = new Font("Dialog", Font.PLAIN, 12);
    private Color mForeColor = Color.BLACK;
    private Color mBackColor = Color.BLACK;
    private String mAlignment = "center";
    private String mRowRef = "";
    private String mColRef = "";
    /**
     * 构造方法
     */
    public JDRCellFigure() {
        super();
    }

    /**
     * @return
     */
    public String getType() {
        return mType;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.mType = type;
    }

    /**
     *
     * @return
     */
    public String getCaption() {
        return mCaption;
    }

    /**
     *
     * @param caption
     */
    public void setCaption(String caption) {
        this.mCaption = caption;
    }

    /**
     *
     * @return
     */
    public Font getFont() {
        return this.mFont;
    }

    /**
     *
     * @param font
     */
    public void setFont(Font font) {
        this.mFont = font;
    }

    /**
     *
     * @return
     */
    public String getAlignment() {
        return this.mAlignment;
    }

    /**
     *
     * @param alignment
     */
    public void setAlignment(String alignment) {
        this.mAlignment = alignment;
    }

    /**
     *
     * @return
     */
    public String getRowRef() {
        return this.mRowRef;
    }

    /**
     *
     * @param rowRef
     */
    public void setRowRef(String rowRef) {
        if (rowRef != null) {
            this.mRowRef = rowRef;
        }
    }

    /**
     *
     * @return
     */
    public String getColRef() {
        return this.mColRef;
    }

    /**
     *
     * @param colRef
     */
    public void setColRef(String colRef) {
        if (colRef != null) {
            this.mColRef = colRef;
        }
    }
}