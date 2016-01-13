package jservice.jbof.classes.GenerQueryObject.action;

import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public interface JBookActionConstants {
    //方法前缀
    static ResourceBundle res = ResourceBundle.getBundle("jservice.jbof.classes.GenerQueryObject.action.Language");
  public static final String ACTION_METHOD_PREFIX = "on";

    //空Action
    public static final String ACTION_NULL = "";

    //常规操作
    public static final String ACTION_FIND = "Find";
    public static final String ACTION_REPLACE = "Replace";
    public static final String ACTION_DELETE = "Delete";
    public static final String ACTION_GOTO = "Goto";
    //输入输出
    public static final String ACTION_IMPORT = "Import";
    public static final String ACTION_EXPORT = "Export";

    /**
     * 编辑
     * <p>Title: </p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    public static class EDIT {
        //编辑
        public static final String ACTION_CUT = "Cut";
        public static final String ACTION_COPY = "Copy";
        public static final String ACTION_PASTE = "Paste";
        public static final String ACTION_DELETE = "Delete";
        //插入
        public static final String ACTION_INSERT_ROW = "InsertRow";
        public static final String ACTION_INSERT_COL = "InsertCol";
        public static final String ACTION_INSERT_WORKSHEET = "InsertWorksheet";
        public static final String ACTION_INSERT_PAGE_BREAK = "InsertPageBreak";
        public static final String ACTION_INSERT_HYPER_LINK = "InsertHyperLink";
        public static final String ACTION_INSERT_CHART = "InsertGraphicsChart";
        public static final String ACTION_INSERT_PICTURE = "InsertGraphicsPicture";

        //插入-图形对象
//    public static final String ACTION_INSERT_GRAPHICS_OBJECT  = "InsertGraphicsObject";
        public static final String ACTION_INSERT_GRAPHICS_NORMAL = "InsertGraphicsNormal";
        public static final String ACTION_INSERT_GRAPHICS_CHART = "InsertGraphicsChart";
        public static final String ACTION_INSERT_GRAPHICS_PICTURE = "InsertGraphicsPicture";
        public static final String ACTION_INSERT_GRAPHICS_CURVE = "InsertGraphicsCurve";
        public static final String ACTION_INSERT_GRAPHICS_LINE = "InsertGraphicsLine";
        public static final String ACTION_INSERT_GRAPHICS_ROUND = "InsertGraphicsRound";
        public static final String ACTION_INSERT_GRAPHICS_POLYGON = "InsertGraphicsPolygon";
        public static final String ACTION_INSERT_GRAPHICS_RECTANGLE = "InsertGraphicsRectangle";
        //插入-表单对象
//    public static final String ACTION_INSERT_FORM_OBJECT      = "InsertFormObject";
        public static final String ACTION_INSERT_FORM_BUTTON = "InsertFormButton";
        public static final String ACTION_INSERT_FORM_CHECKBOX = "InsertFormCheckbox";
        public static final String ACTION_INSERT_FORM_COMBOBOX = "InsertFormCombobox";
        public static final String ACTION_INSERT_FORM_LIST = "InsertFormList";
        public static final String ACTION_INSERT_FORM_RADIOBUTTON = "InsertFormRadioButton";
        public static final String ACTION_INSERT_FORM_TEXTAREA = "InsertFormTextArea";

        //删除
        public static final String ACTION_DELETE_ROW = "DeleteRow";
        public static final String ACTION_DELETE_COL = "DeleteCol";
        public static final String ACTION_DELETE_WORKSHEET = "DeleteWorksheet";
        public static final String ACTION_DELETE_PAGE_BREAK = "DeletePageBreak";
        public static final String ACTION_DELETE_HYPER_LINK = "DeleteHyperLink";
    }

    /**
     * 格式
     * <p>Title: </p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    public static class FORMAT {
        //格式
        public static final String ACTION_CELL = "Cell";
        public static final String ACTION_BOLD = "Bold";
        public static final String ACTION_ITALIC = "Italic";
        public static final String ACTION_UNDERLINE = "Underline";
        public static final String ACTION_LEFT = "Left";
        public static final String ACTION_CENTER = "Center";
        public static final String ACTION_RIGHT = "Right";
        public static final String ACTION_LEFT2RIGHT = "Left2Right";
        public static final String ACTION_TOP2BOTTOM = "Top2Bottom";
        public static final String ACTION_MONEY = "Money";
        public static final String ACTION_PERCENT = "Percent";
        public static final String ACTION_DOT = "Dot";
        public static final String ACTION_ADD_ZERO = "AddZero";
        public static final String ACTION_DELETE_ZERO = "DeleteZero";
        public static final String ACTION_SUB_SPACE = "SubSpace";
        public static final String ACTION_ADD_SPACE = "AddSpace";
        public static final String ACTION_BORDER = "Border";
        public static final String ACTION_FONT_COLOR = "FontColor";
        public static final String ACTION_BACK_COLOR = "BackColor";
        public static final String ACTION_TOP = "Top";
        public static final String ACTION_VERTICAL_CENTER = "VerticalCenter";
        public static final String ACTION_BOTTOM = "Bottom";
        public static final String ACTION_MERGE = "Merge";
        public static final String ACTION_GRAPHICS = "Graphics";

        //行
        public static final String ACTION_ROW_HEIGHT = "RowHeight";
        public static final String ACTION_ROW_PREFER_HEIGHT = "RowPreferHeight";
        public static final String ACTION_ROW_HIDE = "RowHide";
        public static final String ACTION_ROW_SHOW = "RowShow";
        //列
        public static final String ACTION_COL_WIDTH = "ColWidth";
        public static final String ACTION_COL_PREFER_WIDTH = "ColPreferWidth";
        public static final String ACTION_COL_HIDE = "ColHide";
        public static final String ACTION_COL_SHOW = "ColShow";
        //工作表
        public static final String ACTION_WORKSHEET_RENAME = "WorksheetRename";
        public static final String ACTION_WORKSHEET_HIDE = "WorksheetHide";
        public static final String ACTION_WORKSHEET_SHOW = "WorksheetShow";
        public static final String ACTION_WORKSHEET_ATTRIBUTE = "WorksheetAttribute";
        //对象
        public static final String ACTION_OBJECT_OPTION = "ObjectOption";
        public static final String ACTION_OBJECT_BRING2FRONT = "ObjectBring2Front";
        public static final String ACTION_OBJECT_SEND2BACK = "ObjectSend2Back";
        public static final String ACTION_OBJECT_GROUP = "ObjectGroup";
        public static final String ACTION_OBJECT_UNGROUP = "ObjectUngroup";

        public static final String ACTION_FREEZE_PANES = "FreezePanes";
        public static final String ACTION_DEFREEZE_PANES = "DefreezePanes";

    }

    /**
     * 打印
     * <p>Title: </p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    public static class PRINT {
        //打印
        public static final String ACTION_PAGESETUP = "PrintPageSetup";
        public static final String ACTION_AREA = "PrintArea";
        public static final String ACTION_CANCEL_AREA = "CancelPrintArea";
        public static final String ACTION_TITLE = "PrintTitle";
        public static final String ACTION_PREWVIEW = "PrintPreview";
        public static final String ACTION_PRINT = "Print";
    }
}
