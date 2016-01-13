package jtoolkit.string.classes;

import java.util.StringTokenizer;

/**
 * <p>TString</p>
 * <p>扩展字符串类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TString {
  private static final String T_FLAG = "%s";
  public TString() {
  }

  private static String F(String pStringFormat,Object[] pParams){
    StringBuffer pBuf = new StringBuffer();

    /**
     * 如果以%s开始，则处理一下。以保证正确。
     */
    if ( pStringFormat.startsWith("%s")){
      pStringFormat = " "+pStringFormat;
    }

    /**
     * 下面是正常的处理。
     */
    int iAllValues = pParams.length;

    StringTokenizer st = new StringTokenizer(pStringFormat, T_FLAG, false);

    int iCount = 0;
    while (st.hasMoreElements()) {
      Object pItem = st.nextElement();
      String pText = pItem.toString();

      pBuf.append(pText);

      if ( iCount < iAllValues ){
        Object pThisItem = pParams[iCount];
        String pThisText = pThisItem==null?"null":pThisItem.toString();

        pBuf.append(pThisText);

        iCount ++;
      }
    }

    return pBuf.toString();
  }

  public static String F(String pStringFormat,String P1){
    return F(pStringFormat,new Object[]{P1});
  }

  public static String F(String pStringFormat,String P1,String P2){
    return F(pStringFormat,new Object[]{P1,P2});
  }

  public static String F(String pStringFormat,String P1,String P2,String P3){
    return F(pStringFormat,new Object[]{P1,P2,P3});
  }

  public static String F(String pStringFormat,String P1,String P2,String P3,String P4){
    return F(pStringFormat,new Object[]{P1,P2,P3,P4});
  }

  public static String F(String pStringFormat,String P1,String P2,String P3,String P4,String P5){
    return F(pStringFormat,new Object[]{P1,P2,P3,P4,P5});
  }

  public static String F(String pStringFormat,String P1,String P2,String P3,String P4,String P5,String P6){
    return F(pStringFormat,new Object[]{P1,P2,P3,P4,P5,P6});
  }

  public static void main(String[] pPs){
//    StringTokenizer st = new StringTokenizer("王,%s 我是一个 中国%s他也是%s,所以我不是%s.", "%s", false);
//
//    int iCount = 0;
//    while (st.hasMoreElements()) {
//      Object pItem = st.nextElement();
//      String pText = pItem.toString();
//      System.out.println(pText);
//    }

    String pBUF = "%s您选择的预算项目“%s”是明细级的，不能继续查询！%s";
    String pData = TString.F(pBUF,"0001","0002","003");
    System.out.println(pData);
  }
}
