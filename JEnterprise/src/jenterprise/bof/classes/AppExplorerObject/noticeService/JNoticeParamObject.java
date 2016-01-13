package jenterprise.bof.classes.AppExplorerObject.noticeService;

import java.util.ResourceBundle;

/**
 *
 * @version 1.0
 */
public class JNoticeParamObject {

    static ResourceBundle res = ResourceBundle.getBundle(
        "jenterprise.bof.classes.AppExplorerObject.noticeService.Language");

    /**通知*/
    public static final String TYPE_NOTICE_ID = "Notice";

    /**通知标题*/
    public static final String TYPE_NOTICE_MC = res.getString("String_2");

    /**消息*/
    public static final String TYPE_MESSAGE_ID = "Message";

    /**消息标题*/
    public static final String TYPE_MESSAGE_MC = res.getString("String_4");

    // ----------流程是独立的Adapter------------------------------------------------------------
    /**流程*/
    public static final String TYPE_WF_NOTICE_ID = "WF_Notice";

    /**流程标题*/
    public static final String TYPE_WF_NOTICE_MC = res.getString("String_5");

    public static final String[] WF_TYPE_ID = {
        TYPE_WF_NOTICE_ID};
    public static final String[] WF_TYPE_MC = {
        TYPE_WF_NOTICE_MC};

    //-----------------------------------------------------------------------
//
  public static final String[] TYPE_ID = {
      TYPE_NOTICE_ID, TYPE_MESSAGE_ID};
  public static final String[] TYPE_MC = {
      TYPE_NOTICE_MC, TYPE_MESSAGE_MC};

}
