package jreport.swing.classes.ReportBook;

import com.efounder.eai.data.JParamObject;

import jreport.swing.classes.JReportModel;
import jreportfunction.pub.JReportPubFunc;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: pansoft</p>
 * @author not attributable
 * @version 1.0
 */
public class JCommentInfo {

    public String Comment_Area = "";
    public String Comment_Orde = "";
    public String Comment_User = "";
    public String Comment_Date = "";
    public String Comment_Info = "";
    public int    Comment_HOFFSET = 0;
    public int    Comment_LOFFSET = 0;
    public int    ADD_TYPE;
    public String ADD_BH;
    public String HZD_ORDE;
    public String LZD_ORDE;

    /**
     *
     * @param  RM JReportModel
     * @return    JCommentInfo
     */
    public static JCommentInfo CreateCommentInfo(JReportModel RM) {
        JCommentInfo CommentInfo = new JCommentInfo();
        CommentInfo.Comment_Orde = String.valueOf(++RM.CommentMaxOrde);
        CommentInfo.Comment_User = JParamObject.Create().GetValueByEnvName("UserCaption", "");
        CommentInfo.Comment_Date = JReportPubFunc.getLocalCurrentTime();
        CommentInfo.ADD_BH   = RM.ADD_BH == null ? "" : RM.ADD_BH;
        CommentInfo.ADD_TYPE = RM.ADD_TYPE;

        return CommentInfo;
    }

    /**
     *
     * @return    String
     */
    public String toString() {
        return Comment_User + "(" + Comment_Date + ")£º" + Comment_Info;
    }
}
