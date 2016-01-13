package com.efounder.dctbuilder.data;

import com.core.xml.StubObject;
import java.util.Vector;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DataDicDefine  extends StubObject {
  public  static final String ROOT_ID         = "#ROOT";
  public  static final String TYPE_ZBZD       = "TYPE_ZBZD";   //指标字典
  public  static final String TYPE_UNIONZD    = "UNION_ZBZD";  //通用字典
  public  static final String TYPE_DEFINE     = "DEFINE_ZBZD";  //自定义字典

  public  static final String EL_NOVISIBLE    = "0";
  public  static final String EL_STRING       = "1";
  public  static final String EL_INT          = "2";
  public  static final String EL_FLOAT        = "3";
  public  static final String EL_BOOL         = "4";
  public  static final String EL_DATE         = "5";
  public  static final String EL_POPUP        = "6";

  public  static final String[] T_FKEYS = new String[]{"DCT_FKEY1","DCT_FKEY2","DCT_FKEY3","DCT_FKEY4",
      "DCT_FKEY5","DCT_FKEY6","DCT_FKEY7","DCT_FKEY8"};

  public  static final String[] T_FDCTS = new String[]{"DCT_FKEYDCT1","DCT_FKEYDCT2","DCT_FKEYDCT3","DCT_FKEYDCT4",
      "DCT_FKEYDCT5","DCT_FKEYDCT6","DCT_FKEYDCT7","DCT_FKEYDCT8"};
  /**
   * 以下定义的是字段的名字。
   */
  public static final String T_OBJ_ID        = "OBJ_ID";
  public static final String T_DIC_ID        = "DCT_ID";
  public static final String T_DIC_MC        = "DCT_MC";
  public static final String T_DIC_NAME      = "DCT_NAME";
  public static final String T_DIC_DES       = "DCT_DES";
  public static final String T_DIC_TYPE      = "DCT_TYPE";
  public static final String T_DIC_QXOBJ     = "DCT_QXOBJID";
  public static final String T_DIC_FIELD_BH  = "DCT_BMCOLID";
  public static final String T_DIC_FIELD_BZ  = "DCT_BZCOLID";
  public static final String T_DIC_FIELD_MC  = "DCT_MCCOLID";
  public static final String T_DIC_FIELD_JS  = "DCT_JSCOLID";
  public static final String T_DIC_FIELD_MX  = "DCT_MXCOLID";
  public static final String T_DIC_FIELD_PT  = "DCT_PTCOLID";
  public static final String T_DIC_FIELD_KZ  = "DCT_KZCOLID";
  public static final String T_DIC_BH_STRU   = "DCT_BMSTRU";

  public DataDicDefine() {
  }

  /**
   * 是否是分级的。
   * @return boolean
   */
  public boolean isMultiLevle(){
    return getDicBHStruct().length() > 1;
  }
  /**
   * 查询一个信息。
   * @param pConn Connection
   * @param strDicID String
   * @return JDataDicDefine
   */


  public String getDicID() {
    return getString(T_DIC_ID,"");
  }

  public void setDicID(String dicID) {
    setString(T_DIC_ID,dicID);
  }

  public String getObjId() {
    return getString(T_OBJ_ID,"");
  }
  public void setObjId(String objId) {
      setString(T_OBJ_ID,objId);
  }
  public String getDicName() {
  return getString(T_DIC_NAME,"");
  }
  public void setDicName(String dicName) {
    setString(T_DIC_NAME,dicName);
  }
   public String getDicBHStruct() {
     return getString(T_DIC_BH_STRU,"");
   }
   public void setDicBHStruct(String dicBHStruct) {
   setString(T_DIC_BH_STRU,dicBHStruct);
  }

  public String getDicFieldName() {
    return getString(T_DIC_FIELD_MC, "");
  }
     public void setDicFieldName(String dicFieldName) {
        setString(T_DIC_FIELD_MC,dicFieldName);
     }
     public String getDicFieldJS() {
       return getString(T_DIC_FIELD_JS,"");
     }
     public void setDicFieldJS(String dicFieldJS) {
      setString(T_DIC_FIELD_JS,dicFieldJS);
     }
     public String getDicFieldMX() {
       return getString(T_DIC_FIELD_MX,"");
     }
     public void setDicFieldMX(String dicFieldMX) {
       setString(T_DIC_FIELD_MX,dicFieldMX);
     }
     public String getDicFieldParent() {
      return getString(T_DIC_FIELD_PT,"");
     }
     public void setDicFieldParent(String dicFieldParent) {
      setString(T_DIC_FIELD_PT,dicFieldParent);
     }

     /**
      * 返回控制下级列
      * add by gengeng 2009-10-13
      *
      * @return String
      */
     public String getDicFieldControl() {
         return getString(T_DIC_FIELD_KZ, "");
     }

     /**
      * 设置控制下级列
      * add by gengeng 2009-10-13
      *
      * @param dicFieldControl String
      */
     public void setDicFieldControl(String dicFieldControl) {
         setString(T_DIC_FIELD_KZ, dicFieldControl);
     }

   public String getDicFieldBH() {
     return getString(T_DIC_FIELD_BH,"");
   }

   public void setDicFieldBH(String dicFieldBH) {
      setString(T_DIC_FIELD_BH,dicFieldBH);
   }


  public String getDicMC() {
    return getString(T_DIC_MC,"");
  }
  public void setDicMC(String dicMC) {
     setString(T_DIC_MC,dicMC);
  }
  public String getDicNote() {
     return getString(T_DIC_DES,"");
  }
  public void setDicNote(String dicNote) {
   setString(T_DIC_DES,dicNote);
  }
  public String getDicQxObjId() {
    return getString("T_DIC_QXOBJ",null);
  }
  public void setDicQxObjId(String s) {
    setString(T_DIC_QXOBJ,s);
  }
  public String getDicType() {
    return getString(T_DIC_TYPE,"");
  }
  public void setDicType(String s) {
    setString(T_DIC_TYPE,s);
  }

  public String getDicFieldBZBM() {
    return getString(T_DIC_FIELD_BZ,"");
  }
  public void setDicFieldBZBM(String s) {
    setString(T_DIC_FIELD_BZ,s);
  }

  public Vector getDicFKeyColNames() {
    return (Vector)getObject("FKEYCOL",null);
  }
  public void setDicFKeyColNames(Vector v) {
    setObject("FKEYCOL",v);
  }
  public Vector getDicFKeyRefDicNames() {
    return (Vector)getObject("FKEYDCT",null);
  }
  public void setDicFKeyRefDicNames(Vector v) {
    setObject("FKEYDCT",v);
  }


  public void setDicCreateType(String pType){
    setString("MODEL",pType);
  }


  public String getDicCreateType(){
    return getString("MODEL","");
  }

  public String getDicGUID() {
    return getString("GUID","");
  }
}
