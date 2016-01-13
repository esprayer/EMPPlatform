package com.efounder.pub.util;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64;
import java.awt.Toolkit;
import java.awt.Dimension;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: EFounder Ltd.</p>
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
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public JCommonFunction() {
    }
    public static String makeCNPCDarkPass(String psUser,String psPass) throws Exception {
      return makeCNPCDarkPass(psUser,psPass,"");
    }
    //----------------------------------------------------------------------------------------------
    //描述: 按CNPC的加密方式取得加密后的密码
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public static String makeCNPCDarkPass(String psUser,String psPass,String p) throws Exception {
    int vi, vk, viChar1;
    String  vsKey;
    StringBuffer vsRet = new StringBuffer();
    /**
     * change by xSkyline(x=sex)，密码前加前缀，默认为p
     */
    psPass = p + psPass;
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
    vsKey = vsKey.substring(vsKey.length()-20);
    if ( p != null ) {
      byte[] enbs = Base64.encodeBase64(vsKey.getBytes());
      vsKey = new String(enbs);
      if ( vsKey.length() > 30 ) {
        vsKey = vsKey.substring(0,30);
      }
    }
    return vsKey;
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
    private static char[] charSet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                                      'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                                      'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                                      'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
                                      'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                                      'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                                      'w', 'x', 'y', 'z', '0', '1', '2', '3',
                                      '4', '5', '6', '7', '8', '9', '+', '/' };
    /**
      *  Using Base64 encoding scheme, shift the data to be encoded accordingly
      *  specification. Based on base64 algo for crypto
      *  @param outStram Stream to output data
      *  @param data to be encoded
      *  @offset start to shift from offset bit
      *  @len shifted length
      */

    public static void base64EncodeAlgo( OutputStream outStream, byte[] data, int offset, int len )throws IOException
    {
       byte a, b, c;
       if ( len == 1 )
       {
         a = data[offset];
         b = 0;
         c = 0;
         outStream.write ( charSet[(a >>> 2) & 0x3F] );
         outStream.write ( charSet[((a << 4) & 0x30) + ((b >>> 4) & 0xf)] );
         outStream.write ( '=' );
         outStream.write ( '=' );
       }
       else if ( len == 2 )
       {
         a = data[offset];
         b = data[offset+1];
         c = 0;
         outStream.write ( charSet[(a >>> 2) & 0x3F] );
         outStream.write ( charSet[((a << 4) & 0x30) + ((b >>> 4) & 0xf)] );
         outStream.write ( charSet[((b << 2) & 0x3c) + ((c >>> 6) & 0x3)] );
         outStream.write ( '=' );
       }
       else
       {
         a = data[offset];
         b = data[offset+1];
         c = data[offset+2];
         outStream.write ( charSet[(a >>> 2) & 0x3F] );
         outStream.write ( charSet[((a << 4) & 0x30) + ((b >>> 4) & 0xf)] );
         outStream.write ( charSet[((b << 2) & 0x3c) + ((c >>> 6) & 0x3)] );
         outStream.write ( charSet[c & 0x3F] );
       }
    }

   /**
    *  Using Base64 decoding scheme, converts a MIME string back to a normal String
    *  specification. Based on base64 algo for crypto
    *  @param data data to be decoded
    */

   public static byte[] base64Decode( String inString )
   {
     byte[] Base64DecMap = new byte[128];
     for (int idx=0; idx<charSet.length; idx++)
       Base64DecMap[charSet[idx]] = (byte) idx;

     if ( inString == null )
       return  null;

     byte[] data = inString.getBytes();

     int tail = data.length;
     while (data[tail-1] == '=')
       tail--;

     byte dest[] = new byte[tail - data.length/4];

     // ascii printable to 0-63 conversion
     for (int idx = 0; idx <data.length; idx++)
         data[idx] = Base64DecMap[data[idx]];

     // 4-byte to 3-byte conversion
     int sidx, didx;
     for (sidx = 0, didx=0; didx < dest.length-2; sidx += 4, didx += 3)
     {
         dest[didx]   = (byte) ( ((data[sidx] << 2) & 255) |
                 ((data[sidx+1] >>> 4) & 003) );
         dest[didx+1] = (byte) ( ((data[sidx+1] << 4) & 255) |
                 ((data[sidx+2] >>> 2) & 017) );
         dest[didx+2] = (byte) ( ((data[sidx+2] << 6) & 255) |
                 (data[sidx+3] & 077) );
     }
     if (didx < dest.length)
         dest[didx]   = (byte) ( ((data[sidx] << 2) & 255) |
                 ((data[sidx+1] >>> 4) & 003) );
     if (++didx < dest.length)
         dest[didx]   = (byte) ( ((data[sidx+1] << 4) & 255) |
                 ((data[sidx+2] >>> 2) & 017) );

     return dest;
  }
  public static Dimension getUserScreenSize(double widthper) {
    return getUserScreenSize(widthper,widthper);
  }
  /**
   *
   * @param per double
   * @return Dimension
   */
  public static Dimension getUserScreenSize(double widthper,double heightper) {
    Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension s1 = new Dimension((int)(s.width*widthper),(int)(s.height*heightper));
    return s1;
  }

}
