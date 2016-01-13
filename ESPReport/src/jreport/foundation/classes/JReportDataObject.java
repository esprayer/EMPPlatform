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
//����:
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
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

    //��������Ľڵ�
    private Element CtcxElement = null;

    /**
     * �䶯���ݶ���
     * ��Ϊ���ڱ䶯����XML�򿪺���
     * ���Ը�Ϊ�ں�̨�γ��������������
     * ��ǰ̨���γ�XML����ķ�ʽ������
     * modified by hufeng 2008.5.5
     */
    private JBdDataObject mBdDataObject = null;
    private JBdDataObject mBdTzDataObject = null;
    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
//    public JReportDataObject(String s) {
//        super(s);
//    }

    /**
     * ��ROE��������
     * @param ROE JReportObjectEntity
     */
    public JReportDataObject(JReportObjectEntity ROE) {
        super(ROE.DataObject);
        mBdDataObject = ROE.getBdDataObject();
    }

    /**
     * �����µĹ��캯��
     * @param s String
     * @param bdObject JBdDataObject
     */
    public JReportDataObject(String s,JBdDataObject bdObject) {
        super(s);
        mBdDataObject = bdObject;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public JReportDataObject() {
        super();
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getTableElement() {
        if (TableElement == null)
            TableElement = this.GetElementByName("Table");
        return TableElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getRowsElement() {
        if (RowsElement == null)
            RowsElement = GetElementByName("Rows");
        return RowsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getColsElement() {
        if (ColsElement == null)
            ColsElement = GetElementByName("Cols");
        return ColsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getDelRowsElement() {
        if (DelRowsElement == null)
            DelRowsElement = GetElementByName("DelRows");
        return DelRowsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getDelColsElement() {
        if (DelColsElement == null)
            DelColsElement = GetElementByName("DelCols");
        return DelColsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getUnitsElement() {
        if (UnitsElement == null)
            UnitsElement = GetElementByName("Units");
        return UnitsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
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
             * ��һ�ζ�ȡ��ʱ��
             * �Ӷ������γ�XML����
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
             * ��һ�ζ�ȡ��ʱ��
             * �Ӷ������γ�XML����
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
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getBdhsElement() {
        if (BdhsElement == null) {
            BdhsElement = GetElementByName("Bdhs");
            /**
             * ��һ�ζ�ȡ��ʱ��
             * �Ӷ������γ�XML����
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
         * ��һ�ζ�ȡ��ʱ��
         * �Ӷ������γ�XML����
         */
        if (BdhsElement != null && mBdTzDataObject != null) {
            mBdTzDataObject.getSysBdData(this, BdhsElement);
            mBdTzDataObject.getAddBdData(this, BdhsElement);
        }
    }
    return BdhsElement;
}


    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getBdlsElement() {
        if (BdlsElement == null)
            BdlsElement = GetElementByName("Bdls");
        return BdlsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getDelBdhsElement() {
        if (DelBdhsElement == null)
            DelBdhsElement = GetElementByName("DelBdhs");
        return DelBdhsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getDelBdlsElement() {
        if (DelBdlsElement == null)
            DelBdlsElement = GetElementByName("DelBdls");
        return DelBdlsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getJygsesElement() {
        if (JygsesElement == null)
            JygsesElement = GetElementByName("Jygses");
        return JygsesElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: mengfei(2009.11.18)
    //ʵ��: mengfei
    //�޸�:
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
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getCommentsElement() {
        if (CommentsElement == null)
            CommentsElement = GetElementByName("Comments");
        return CommentsElement;
    }

    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getAddjsgsesElement() {
        if (AddjsgsesElement == null)
            AddjsgsesElement = GetElementByName("Addjsgses");
        return AddjsgsesElement;
    }
    //-------------------------------------------------------------------------------------------------
    //����:
    //���: mengfei(2009.11.18)
    //ʵ��: mengfei
    //�޸�:
    //-------------------------------------------------------------------------------------------------
    public Element getAddtzgsesElement() {
        if (AddtzgsesElement == null)
            AddtzgsesElement = GetElementByName("Addtzgses");
        return AddtzgsesElement;
    }
    //-------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.04.22)
    //ʵ��: Skyline
    //�޸�:
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
     * ���浥λ�䶯����
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
     * ���浥λ�䶯��������
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
     * ����ϵͳ�䶯����
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
     * ȡ�䶯����
     * @return JBdDataObject
     */
    public JBdDataObject getBdDataObject(){
        return mBdDataObject;
    }

    /**
     * ȡ�䶯��������
     * @return JBdDataObject
     */
    public JBdDataObject getBdTzDataObject(){
        return mBdTzDataObject;
    }

}
