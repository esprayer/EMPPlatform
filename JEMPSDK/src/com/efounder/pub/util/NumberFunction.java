package com.efounder.pub.util;

import java.math.BigDecimal;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class NumberFunction {
  public static final int NUMBER_DDY  = 0;
  public static final int NUMBER_BDY  = 1;
  public static final int NUMBER_DY   = 2;
  public static final int NUMBER_DYDY = 3;
  public static final int NUMBER_XY   = 4;
  public static final int NUMBER_XYDY = 5;
  public NumberFunction() {
  }
  public static int CmpDouble(double d1,double d2,int dec) {
    long l1,l2,l3=1;
    if ( dec == 0 ) {
      l3 = 1;
    } else {
      for(int i=1;i<=dec;i++) {
        l3 = l3*10;
      }
    }
    l1 = (long)(d1*l3);
    l2 = (long)(d2*l3);
    if ( l1 == l2 ) return NUMBER_DDY;
    if ( l1 != l2 ) return NUMBER_BDY;
    if ( l1 > l2 ) return NUMBER_DY;
    if ( l1 >= l2 ) return NUMBER_DYDY;
    if ( l1 < l2 ) return NUMBER_XY;
    if ( l1 <= l2 ) return NUMBER_XYDY;
    return -1;
  }

  //返回指定位数的 数值型数据 ,取精度
  public static double round(double anumber,int place){
    return (new BigDecimal(anumber)).setScale(place, BigDecimal.ROUND_HALF_UP).doubleValue();
  }
  /**
   *
   * @param num int
   * @return byte[]
   */
  static public byte[] int2bytes(int num) {
    byte[] b=new byte[4];
    int mask=0xff;
    for(int i=0;i<4;i++){
      b[i]=(byte)(num>>>(24-i*8));
    }
    return b;
  }
  /**
   *
   * @param b byte[]
   * @return int
   */
  static public int bytes2int(byte[] b) {
    int mask=0xff;
    int temp=0;
    int res=0;
    for(int i=0;i<4;i++){
      res<<=8;
      temp=b[i]&mask;
      res|=temp;
    }
    return res;
  }

}
