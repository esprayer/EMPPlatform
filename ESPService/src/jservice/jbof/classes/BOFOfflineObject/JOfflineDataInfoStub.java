package jservice.jbof.classes.BOFOfflineObject;
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JOfflineDataInfoStub {
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JOfflineDataInfoStub() {
  }
  public String name=null;
  public String F_CODE=null;
  public String F_SJQX=null;
  public String F_BHZD=null;
  public String CODE_TYPE=null;
  public String QXBZW = null;
  public String DNDATA = null;
  public String WHERE  = null;
  public String text=null;
  public Boolean isQX = new Boolean(false);
  public Boolean isZX = new Boolean(false);
  public Boolean isDel = new Boolean(true);
  public boolean IsErr = false;
  public String SQL;
  public String ErrorString;
  public String toString() {
    return text;
  }
  public void setF_CODE(String Code) {
    F_CODE = Code;
    isZX = new Boolean( F_CODE != null && F_CODE.trim().equals("1") );
  }
  public void setF_SJQX(String Sjqx) {
    F_SJQX = Sjqx;
    isQX = new Boolean( F_SJQX != null && F_SJQX.trim().length() != 0 );
  }
}