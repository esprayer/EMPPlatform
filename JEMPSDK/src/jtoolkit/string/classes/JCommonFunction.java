package jtoolkit.string.classes;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Pansoft Ltd.</p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JCommonFunction {
    private final static int viLen = 30;  //长度
    private final static char[] lsnh = {193,247,35,203,174,196,234,37,187,170};//流#水年%华
    private final static char[] bjlf = {176,209,190,198,193,217,183,231};      //把酒临风
    /**
     * 以下的字串，用于内控加密
     * add by hufeng 2006.8.17
     */
    private final static char[] jbwy = {190,217,177,173,205,251,212,194};      //举杯望月
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public JCommonFunction() {
    }
    //----------------------------------------------------------------------------------------------
    //描述: 按CNPC的加密方式取得加密后的密码
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public static String makeCNPCDarkPass(String psUser,String psPass) throws Exception {
    int vi, vk, viChar1;
    String  vsKey;
    StringBuffer vsRet = new StringBuffer();

    //IF  psUser='9999'  THEN  Return  psPass
    psPass = gfStrAddStr(psPass,psUser);
    psPass = gfXorStr(psPass,String.valueOf(lsnh));

    vsKey = gfFillString(psPass.substring(0,psPass.length()/2),viLen) + gfFillString(psPass.substring(psPass.length()-psPass.length()/2),viLen);
    vsKey = gfStrAddStr(vsKey.substring(0,viLen),gfReverseStrbybit(vsKey.substring(viLen),0));

    /* 取反 */
//    vsKey = gfReverseStrbybit(vsKey,1);
    vsKey = gfStrAddStr(vsKey,gfFillString(gfXorStr(psPass,String.valueOf(bjlf)),viLen));

    for(vi = 0; vi < vsKey.length(); vi = vi + 2)
    {
      viChar1 = 0;
      if(vi+1 < vsKey.length())
        viChar1 = vsKey.charAt(vi+1);
      //
      vk = vsKey.charAt(vi)/16*16 + viChar1/16;
      vk = (vk % 17) + (vk % 31) + (vk % 53) + (vk % 29);
      if(vk < 32) vk = vk + 30;
      if(vk == '"') vk --;
      if(vk == '\'') vk --;
      vsRet.append(gfUCharTo256((char)vk));
    }
    for(vi = 0; vi < vsKey.length(); vi = vi + 2)
    {
      viChar1 = 0;
      if(vi+1 < vsKey.length())
        viChar1 = vsKey.charAt(vi+1);
      //
      vk = (vsKey.charAt(vi) % 16)*16 + (viChar1 % 16);
      vk = (vk % 19) + (vk % 39) + (vk % 59) + (vk % 13);
      if(vk < 32) vk = vk + 30;
      if(vk == '"') vk --;
      if(vk == '\'') vk --;
      vsRet.append(gfUCharTo256((char)vk));
    }
    //
    vsKey = vsRet.toString();
    return  vsKey.substring(vsKey.length()-20);
  }

  /**
   * 形成登录密码
   * 此登录密码只用于内控检查，不用于实际登录
   * add by hufeng 2006.8.17
   */
  public static String makeLoginPass(String psUser, String psPass) throws Exception {
    int vi, vk, viChar1;
    String vsKey;
    StringBuffer vsRet = new StringBuffer();

    //IF  psUser='9999'  THEN  Return  psPass
    psPass = gfStrAddStr(psPass, psUser);
    psPass = gfXorStr(psPass, String.valueOf(jbwy)); //修改关键字

    vsKey = gfFillString(psPass.substring(0, psPass.length() / 2), viLen) +
        gfFillString(psPass.substring(psPass.length() - psPass.length() / 2), viLen);
    vsKey = gfStrAddStr(vsKey.substring(0, viLen), gfReverseStrbybit(vsKey.substring(viLen), 0));

    /* 取反 */
//    vsKey = gfReverseStrbybit(vsKey,1);
    vsKey = gfStrAddStr(vsKey, gfFillString(gfXorStr(psPass, String.valueOf(bjlf)), viLen));

    for (vi = 0; vi < vsKey.length(); vi = vi + 2) {
      viChar1 = 0;
      if (vi + 1 < vsKey.length())
        viChar1 = vsKey.charAt(vi + 1);
        //
      vk = vsKey.charAt(vi) / 16 * 16 + viChar1 / 16;
      vk = (vk % 17) + (vk % 31) + (vk % 53) + (vk % 29);
      if (vk < 32) vk = vk + 30;
      if (vk == '"') vk--;
      if (vk == '\'') vk--;
      vsRet.append(gfUCharTo256( (char) vk));
    }
    for (vi = 0; vi < vsKey.length(); vi = vi + 2) {
      viChar1 = 0;
      if (vi + 1 < vsKey.length())
        viChar1 = vsKey.charAt(vi + 1);
        //
      vk = (vsKey.charAt(vi) % 16) * 16 + (viChar1 % 16);
      vk = (vk % 19) + (vk % 39) + (vk % 59) + (vk % 13);
      if (vk < 32) vk = vk + 30;
      if (vk == '"') vk--;
      if (vk == '\'') vk--;
      vsRet.append(gfUCharTo256( (char) vk));
    }
    //
    vsKey = vsRet.toString();
    return vsKey.substring(vsKey.length() - 20);
  }


    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
  private static String gfStrAddStr(String psStr1,String psStr2){/* 两个字符串相加 */
    String  vsStr1,vsStr2;
    StringBuffer vsRet = new StringBuffer();
    int vi;
    //
    if(psStr1.length() > psStr2.length())
    {
      vsStr1 = psStr2;
      vsStr2 = psStr1;
    }else
    {
      vsStr1 = psStr1;
      vsStr2 = psStr2;
    }
    for(vi = 0; vi < vsStr1.length(); vi ++)
      vsRet.append(gfUCharTo256((char)(vsStr1.charAt(vi)+vsStr2.charAt(vi))));
    vsRet.append(vsStr2.substring(vsStr1.length()));
    //
    return vsRet.toString();
  }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
  private static String gfXorStr(String psStr1,String psStr2){
    StringBuffer vsRet = new StringBuffer();
    int  vi,vj,vk;
    //
    if(psStr1.length() > psStr2.length())
      psStr2 = gfFillString(psStr2, psStr1.length());
    else
      psStr1 = gfFillString(psStr1, psStr2.length());
    for(vi = 0; vi < psStr1.length(); vi ++)
    {
      vk = 0;
      for( vj = 0; vj < 8; vj ++)
        vk = vk * 2 + (((((int)(psStr1.charAt(vi)/Math.pow(2,vj))) % 2)
             + (((int)(psStr2.charAt(vi)/Math.pow(2,vj))) % 2)) % 2);
      vsRet.append(gfUCharTo256((char)vk));
    }
    //
    return vsRet.toString();
  }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
  private static String gfFillString(String psStr,int length){
    if(psStr.length() < length)
    {
      StringBuffer vsRet = new StringBuffer(psStr);
      int vi, vcount = (length - 1 ) / psStr.length();
      //
      for(vi = 0; vi < vcount; vi ++)
      {
        vsRet.append(psStr);
      }
      //
      return vsRet.toString().substring(0, length);
    }
    else if(psStr.length() > length)
      return psStr.substring(0, length);
    else
      return psStr;
  }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    private static String gfReverseStrbybit(String psStr,int piMode){
      StringBuffer vsRet = new StringBuffer();
      int  vi,vj,vk;
        for(vi = 0; vi < psStr.length(); vi ++) {
            vk = 0;
            for(vj = 0; vj < 8; vj ++) {
                if(piMode == 0)
                    vk = vk * 2 + (((int)(psStr.charAt(vi)/Math.pow(2,vj)) % 2));
                else
                    vk = vk * 2 + 1 - (((int)(psStr.charAt(vi)/Math.pow(2,vj)) % 2));
            }
            vsRet.insert(0, gfUCharTo256((char)vk));
        }
        return vsRet.toString();
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    private static String gfUCharTo256(char vcChar) {
      vcChar = (char)(vcChar % 256);
        if(vcChar == 0)
            return "";
        else
            return String.valueOf(vcChar);
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public static String[] SplitString(String Text,int Len,int Num) {
      String[] SArray = new String[Num];int i,SP,EP;
      for(i=0;i<Num;i++) {
        SP = i*Len;
        if ( SP < Text.length() ) {
          EP = (i+1)*Len;
          if ( EP < Text.length() ) {
            SArray[i] = Text.substring(SP,EP);
          } else {
            SArray[i] = Text.substring(SP,Text.length());
          }
        } else {
          SArray[i] = "";
        }
      }
      return SArray;
    }
}
