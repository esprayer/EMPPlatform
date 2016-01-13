package jreport.foundation.classes;

import org.jdom.*;

import com.efounder.eai.data.JDataObject;

import jtoolkit.xml.classes.JDOMXMLBaseObject;
import jreport.foundation.classes.data.JBdDataObject;
import java.sql.ResultSet;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0

 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JReportDataObject
    extends JDataObject {
    private Element TableElement = null;
    private Element RowsElement = null;
    private Element ColsElement = null;
    private Element DelRowsElement = null;
    private Element DelColsElement = null;
    private Element BdhsElement = null;
    private Element BdlsElement = null;
    private Element DelBdhsElement = null;
    private Element DelBdlsElement = null;
    private Element UnitsElement = null;
    private Element AddUnitsElement = null;
    private Element AddAdjustUnitsElement = null;
    private Element JygsesElement = null;
    private Element TzgsesElement = null;
    private Element CommentsElement = null;
    private Element ZsgsesElement = null;
    private Element AddjsgsesElement = null;
    private Element AddjygsesElement = null;
    private Element AddtzgsesElement = null;
    private Element AddzsgsesElement = null;
    private Element AddBdqUnitsElement = null;
    private Element AddBdqTzUnitsElement = null;

    //各分析类的节点
    private Element CtcxElement = null;

    /**
     * 变动数据对象
     * 因为现在变动行用XML打开很慢
     * 所以改为在后台形成数组对象存放数据
     * 在前台再形成XML对象的方式来处理
     * modified by hufeng 2008.5.5
     */
    private JBdDataObject mBdDataObject = null;
    private JBdDataObject mBdTzDataObject = null;
    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
//    public JReportDataObject(String s) {
//        super(s);
//    }

    /**
     * 用ROE创建对象
     * @param ROE JReportObjectEntity
     */
    public JReportDataObject(JReportObjectEntity ROE) {
        super(ROE.DataObject);
        mBdDataObject = ROE.getBdDataObject();
    }

    /**
     * 创建新的构造函数
     * @param s String
     * @param bdObject JBdDataObject
     */
    public JReportDataObject(String s,JBdDataObject bdObject) {
        super(s);
        mBdDataObject = bdObject;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public JReportDataObject() {
        super();
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getTableElement() {
        if (TableElement == null)
            TableElement = this.GetElementByName("Table");
        return TableElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getRowsElement() {
        if (RowsElement == null)
            RowsElement = GetElementByName("Rows");
        return RowsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getColsElement() {
        if (ColsElement == null)
            ColsElement = GetElementByName("Cols");
        return ColsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getDelRowsElement() {
        if (DelRowsElement == null)
            DelRowsElement = GetElementByName("DelRows");
        return DelRowsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getDelColsElement() {
        if (DelColsElement == null)
            DelColsElement = GetElementByName("DelCols");
        return DelColsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getUnitsElement() {
        if (UnitsElement == null)
            UnitsElement = GetElementByName("Units");
        return UnitsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getAddUnitsElement() {
        if (AddUnitsElement == null)
            AddUnitsElement = GetElementByName("AddUnits");
        return AddUnitsElement;
    }

    public Element getAddAdjustUnitsElement() {
      if (AddAdjustUnitsElement == null)
        AddAdjustUnitsElement = GetElementByName("AddAdjustUnits");
      return AddAdjustUnitsElement;
    }


    public Element getAddBdqUnitsElement() {
        if (AddBdqUnitsElement == null) {
            AddBdqUnitsElement = GetElementByName("AddBdqUnits");

            /**
             * 第一次读取的时候
             * 从对象中形成XML内容
             * add by hufeng 2008.5.5
             */
            if (BdhsElement != null && mBdDataObject != null) {
                mBdDataObject.getAddBdData(this, AddBdqUnitsElement);
            }
        }
        return AddBdqUnitsElement;
    }

    public Element getAddBdqTzUnitsElement() {
        if (AddBdqTzUnitsElement == null) {
            AddBdqTzUnitsElement = GetElementByName("AddBdqTzUnits");

            /**
             * 第一次读取的时候
             * 从对象中形成XML内容
             * add by hufeng 2008.5.5
             */
//            if (BdhsElement != null && mBdTzDataObject != null) {
//                mBdTzDataObject.getAddBdData(this, AddBdqTzUnitsElement);
//            }
            if (BdhsElement != null && mBdTzDataObject != null) {
              mBdTzDataObject.getAddBdTzData(this, AddBdqTzUnitsElement);
}

        }
        return AddBdqTzUnitsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getBdhsElement() {
        if (BdhsElement == null) {
            BdhsElement = GetElementByName("Bdhs");
            /**
             * 第一次读取的时候
             * 从对象中形成XML内容
             */
            if (BdhsElement != null && mBdDataObject != null) {
                mBdDataObject.getSysBdData(this, BdhsElement);
                mBdDataObject.getAddBdData(this, BdhsElement);
                mBdDataObject.getAddBdTzData(this, BdhsElement);
            }
        }
        return BdhsElement;
    }

    public Element getBdhsTzElement() {
    if (BdhsElement == null) {
        BdhsElement = GetElementByName("Bdhs");
        /**
         * 第一次读取的时候
         * 从对象中形成XML内容
         */
        if (BdhsElement != null && mBdTzDataObject != null) {
            mBdTzDataObject.getSysBdData(this, BdhsElement);
            mBdTzDataObject.getAddBdData(this, BdhsElement);
        }
    }
    return BdhsElement;
}


    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getBdlsElement() {
        if (BdlsElement == null)
            BdlsElement = GetElementByName("Bdls");
        return BdlsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getDelBdhsElement() {
        if (DelBdhsElement == null)
            DelBdhsElement = GetElementByName("DelBdhs");
        return DelBdhsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getDelBdlsElement() {
        if (DelBdlsElement == null)
            DelBdlsElement = GetElementByName("DelBdls");
        return DelBdlsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getJygsesElement() {
        if (JygsesElement == null)
            JygsesElement = GetElementByName("Jygses");
        return JygsesElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: mengfei(2009.11.18)
    //实现: mengfei
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getTzgsesElement() {
        if (TzgsesElement == null)
            TzgsesElement = GetElementByName("Tzgses");
        return TzgsesElement;
    }

    public Element getZsgsesElement() {
        if (ZsgsesElement == null)
            ZsgsesElement = GetElementByName("Zsgses");
        return ZsgsesElement;
    }
    public Element getAddzsgsesElement() {
        if (AddzsgsesElement == null)
            AddzsgsesElement = GetElementByName("Addzsgses");
        return AddzsgsesElement;
    }
    
//-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getCommentsElement() {
        if (CommentsElement == null)
            CommentsElement = GetElementByName("Comments");
        return CommentsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getAddjsgsesElement() {
        if (AddjsgsesElement == null)
            AddjsgsesElement = GetElementByName("Addjsgses");
        return AddjsgsesElement;
    }
    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: mengfei(2009.11.18)
    //实现: mengfei
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getAddtzgsesElement() {
        if (AddtzgsesElement == null)
            AddtzgsesElement = GetElementByName("Addtzgses");
        return AddtzgsesElement;
    }
    //-------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.04.22)
    //实现: Skyline
    //修改:
    //-------------------------------------------------------------------------------------------------
    public Element getAddjygsesElement() {
        if (AddjygsesElement == null)
            AddjygsesElement = GetElementByName("Addjygses");
        return AddjygsesElement;
    }

    public Element getCtcxElement() {
        if (CtcxElement == null)
            CtcxElement = GetElementByName("Ctcxs");
        return CtcxElement;
    }

    public Element getShgsElement() {
        return GetElementByName("Shgs");
    }

    public Element getFmshgsElement() {
        return GetElementByName("Fmshgs");
//    if(CtcxElement==null)
//      CtcxElement=GetElementByName("Fmshgs");
    }

    /**
     * 保存单位变动数据
     * @param rs ResultSet
     * @param bdhOrde String
     * @return int
     * @throws Exception
     */
    public int setAddBdData(ResultSet rs,String bdhOrde) throws Exception{
        if(mBdDataObject == null){
            mBdDataObject = new JBdDataObject();
        }
        return mBdDataObject.setAddBdData(rs,bdhOrde);
    }


    /**
     * 保存单位变动调整数据
     * @param rs ResultSet
     * @param bdhOrde String
     * @return int
     * @throws Exception
     */
    public int setAddBdTzData(ResultSet rs,String bdhOrde) throws Exception{
//        if(mBdTzDataObject == null){
//            mBdTzDataObject = new JBdDataObject();
//        }
//        return mBdTzDataObject.setAddBdData(rs,bdhOrde);
        if(mBdDataObject == null){
            mBdDataObject = new JBdDataObject();
        }
        return mBdDataObject.setAddBdTzData(rs,bdhOrde);

    }
    /**
     * 保存系统变动数据
     * @param rs ResultSet
     * @param bdhOrde String
     * @return int
     * @throws Exception
     */
    public int setSysBdData(ResultSet rs,String bdhOrde) throws Exception{
        if(mBdDataObject == null){
            mBdDataObject = new JBdDataObject();
        }
        return mBdDataObject.setSysBdData(rs,bdhOrde);
    }

    /**
     * 取变动数据
     * @return JBdDataObject
     */
    public JBdDataObject getBdDataObject(){
        return mBdDataObject;
    }

    /**
     * 取变动调整数据
     * @return JBdDataObject
     */
    public JBdDataObject getBdTzDataObject(){
        return mBdTzDataObject;
    }

}
