package jdatareport.dof.classes.report.util;

/**
 *
 * <p>Title: JDRConstants</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */
public interface JDRConstants {
    public static final String TYPE_TEXT = "text"; //静态文本
    public static final String TYPE_FORM = "form"; //数据窗口
    public static final String TYPE_QUERY = "query"; //查询结果

    public final int DEFAULT_SCALE = 18; //16*2;
    public final int DEFAULT_FONT_SCALE = 16;
    public final String DEFAULT_FONT_NAME = "宋体";
    public final int DEFAULT_FONT_SIZE = 12 * DEFAULT_FONT_SCALE;
    public final String DEFAULT_FONT_STYLE = "plain";
    public final String DEFAULT_ALIGNMENT = "center";
    public final int DEFAULT_START_ROW = 0;
    public final int DEFAULT_START_COL = 0;
    public final int DEFAULT_END_ROW = 0;
    public final int DEFAULT_END_COL = 0;
    public final int DEFAULT_ROW_HEIGHT = 23 * DEFAULT_FONT_SCALE;

}
