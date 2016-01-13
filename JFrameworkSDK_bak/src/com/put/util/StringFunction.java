package com.put.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: 字符串处理类</p>
 * <p>Description: 实现对字符串的各种处理</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StringFunction {
  static int TempKey = 0;
  static final int MaxKey = 999999;
  public StringFunction() {
  }
  /**
   * 将字符串数组转换为字符串，并用指定的符号分隔这些数据项
   * @param stringArray 字符串数组
   * @param symbol 分隔符
   * @return
   */
  public static String convertFromStringArrayToStringBySymbol(String[] stringArray,char symbol)
  {
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < stringArray.length; i++ )
    {
      sb.append(stringArray[i]);
      sb.append(symbol);
    }
    return sb.toString();
  }
  /**
   * 将以指定的符号分隔数据项字符串，转换为字符串数组
   * @param string 字符串
   * @param symbol 分隔符
   * @return
   */
  public static String[] convertFromStringToStringArrayBySymbol(String string,String symbol)
  {
    Vector stringVector = convertFromStringToStringVectorBySymbol(string,symbol);
    String [] stringArray = new String[stringVector.size()];
    for(int i = 0 ; i < stringVector.size() ; i ++ )
      stringArray[i] = (String)(stringVector.elementAt(i));
    return stringArray;
  }
  //
  /**
   * 将以指定的符号分隔数据项字符串，转换为Vector
   * @param string 字符串
   * @param symbol 分隔符
   * @return
   */
  public static Vector convertFromStringToStringVectorBySymbol(String string,String symbol)
  {
    StringTokenizer st = new StringTokenizer(string,symbol,true);
    Vector stringVector = new Vector();
    while(st.hasMoreElements())
    {
      stringVector.addElement(st.nextElement());
    }
    return stringVector;
  }
  /**
   * 将字符串Vector转换为字符串,并用指定的符号分隔这些数据项
   * @param stringVector 字符串Vector
   * @param symbol 分隔符
   * @return
   */
  public static String convertFromStringVectorToStringBySymbol(Vector stringVector,char symbol)
  {
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < stringVector.size(); i++ )
    {
      sb.append(stringVector.elementAt(i));
      sb.append(symbol);
    }
    return sb.toString();
  }
  /**
   * 以,分隔的形式输出数值
   * @param   value -- 数值
   * @return  String
   */
  public static String defaultFormat(double value) {
    //      int i=(int)value*100;
    if (value < 0.01) {
      return "&nbsp;"; //0不显示
    } else {
      java.text.NumberFormat nf = new java.text.DecimalFormat("###,###.00");
      return nf.format(value);
    }
  }
  /**
   * 字符串填充函数.目的:生成一定长度用某字符填充的字符串
   * @param     psStr    要进行处理的参数字符串
   * @param     psLen    返回字符串的长度
   * @param     psC      填充的字符串
   * @return    string   填充后的字符串
   */
  public static String fillString(String psStr, char psC, int psLen) {
    if (psStr.length() > psLen) {
      return psStr.substring(0, psLen);
    } else {
      char[] vcTemp = new char[psLen];
      for (int i = 0; i < psLen; i++) {
        vcTemp[i] = psC;
      }
      String vsTemp = new String(vcTemp);
      String vsResult = psStr.concat(vsTemp);
      return vsResult.substring(0, psLen);
    }
  }
  /**
   * 以分隔的形式输出数值
   * @param   value -- 数值
   * @param   sep   -- 每一段的位数
   * @param   ch    -- 分隔符
   * @param   round -- 小数位
   * @return  String
   */
  public static String formatValue(double value, int sep, char ch, int round) {
    if(Math.abs(Math.pow(10.0,round)*value) < 1){
//      return "&nbsp;"; likebing 2007.3.26 bj modi.
      return "0.00";
    }
    char[] chs = new char[sep + round + 2];
    chs[0] = ch;
    for (int i = 0; i < sep; i++)
      chs[i + 1] = '#';
    if (round > 0)
      chs[sep + 1] = '.';
    for (int i = 1; i <= round; i++)
      chs[sep + 1 + i] = '0';
    String str = new String(chs);
    java.text.NumberFormat nf = new java.text.DecimalFormat(str); //",###.00");
    return nf.format(value);
  }
  /**
   * 进行字符转换的方法.
   * 目前软件已无需要再使用本方法
   * @param original
   * @return
   */
  public static String GB2Uni(String original) {
    return original;
//    if (original != null) {
//      try {
//        return new String(original.getBytes("GBK"), "ISO8859_1");
//      } catch (Exception e) {
//        e.printStackTrace();
//        return null;
//      }
//    } else
//      return null;
  }
  /**
   * 进行字符转换的方法.
   * 目前软件已无需要再使用本方法
   * @param original
   * @return
   */
  public static String Uni2GB(String original) {
    return original;
//    if (original != null) {
//      try {
//        return new String(original.getBytes("ISO8859_1"), "GBK");
//      } catch (Exception e) {
//        e.printStackTrace();
//        return null;
//      }
//    } else
//      return null;
  }
  
  /**
   * 进行字符转换的方法.
   * 目前软件已无需要再使用本方法
   * @param original
   * @return
   */
  public static String ISO2UTF(String original) {
    if (original != null) {
      try {
        return new String(original.getBytes("ISO8859_1"), "UTF-8");
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    } else
      return null;
  }
  
  /**
   * 将数值Value变为字符串
   * @param     pre      前缀
   * @param     value    数值
   * @param     num      位数（数值的位数）
   * @return    想要的数值Value变为字符串。
   */
  public static String generateID(String pre, int value, int num) {
    char chs[] = new char[num];
    for (int i = 0; i < num; i++) {
      chs[num - i - 1] = (char) (48 + value % 10);
      value = value / 10;
    }
    return pre.trim() + new String(chs);
  }
  /**
   * 返回根据当前时间确定的一个唯一字符串，
   * @return  String
   */
  public static String getTempStr() {
    //String str=String.valueOf(System.currentTimeMillis());
    //return str.substring(str.length()-6);
    return String.valueOf(System.currentTimeMillis());
  }
  /**
   * 字符串判断函数.
   * 目的:判断一个字符串中是否包含另一个字符串
   * @param     psStr    要进行处理的参数字符串
   * @param     psS      被包含的字符串
   * @return    boolean  true or false
   */
  public static boolean isIncludeString(String psStr, String psS) {
    int viPos = psStr.indexOf(psS);
    return viPos >= 0;
  }
  /**
   * 字符串替换函数
   * @param     psStr  要进行处理的参数字符串
   * @param     psS    被替换的字符串
   * @param     psD    替换的字符串
   * @return    psStr  被替换后的字符串。
   */
  public static String replaceString(String psStr, String psS, String psD) {
    int viPos = psStr.indexOf(psS);
    if (viPos < 0)
      return psStr;
    int viLength = psS.length();
    StringBuffer vsValue = new StringBuffer();
    while (viPos >= 0) {
      vsValue.append(psStr.substring(0, viPos));
      vsValue.append(psD);
      psStr = psStr.substring(viPos + viLength);
      viPos = psStr.indexOf(psS);
    }
    vsValue.append(psStr);
    return vsValue.toString();
  }
  /**
   * 字符串截取函数.
   * 目的:以指定的符号分隔 字符串
   * @param     psStr      要进行处理的参数字符串
   * @param     smybol      指定的分隔符
   * @return    vsString    返回数组 :[所取字符，取后的字符]
   */
  public static String[] splitStringByToken(String vsStr, String symbol) {
    String vsString[] = { "", "" };
    int viPos1;
    viPos1 = vsStr.indexOf(symbol);
    if (viPos1 < 0) {
      vsString[0] = vsStr;
      vsString[1] = "";
      return vsString;
    }
    vsString[0] = vsStr.substring(0, viPos1);
    vsString[1] = vsStr.substring(viPos1 + symbol.length(), vsStr.length());
    return vsString;
  }
  /**
   * 将以指定的符号分隔数据项字符串，转换为字符串数组Vector
   * @param string 字符串
   * @param symbol 分隔符
   * @return Vector
   */
  public static Vector convertFromStringToStringVectorByString(String string,String symbol)
  {
    Vector stringVector = new Vector();
    String vsStr = string.trim();
    String vsTemp = null;
    String[] st = null;
    while((!vsStr.equals("")) && isIncludeString(vsStr,symbol))
    {
      st = splitStringByToken(vsStr,symbol);
      vsTemp = st[0].trim();
      if(!vsTemp.equals(""))
        stringVector.addElement(vsTemp);
      vsStr = st[1].trim();
    }
    if(!vsStr.equals(""))
      stringVector.addElement(vsStr);
    return stringVector;
  }

  /**
   * 返回根据当前时间确定的后N位的一个唯一字符串，
   * @param   length -- 返回字符串的长度
   * @return  String
   */
  public static String getTempStr(int length) {
    String str=String.valueOf(System.currentTimeMillis());
    return str.substring(str.length()-length);
  }
  public static synchronized String getTempKey(int length) {
    if ( TempKey > MaxKey ) {
      TempKey = 0;
    }
    return String.valueOf(TempKey++);
  }
  /**
   * 将以指定的符号分隔数据项字符串，转换为字符串数组Vector
   * @param string 字符串
   * @param symbol 分隔符
   * @return
   */
  public static Vector convertFromStringToStringVectorByStringWithNull(String string,String symbol)
  {
    Vector stringVector = new Vector();
    String vsStr = string.trim();
    String vsTemp = null;
    String[] st = null;
    while((!vsStr.equals("")) && isIncludeString(vsStr,symbol))
    {
      st = splitStringByToken(vsStr,symbol);
      vsTemp = st[0].trim();
      stringVector.addElement(vsTemp);
      vsStr = st[1].trim();
    }
    if(!vsStr.equals(""))
      stringVector.addElement(vsStr);
    return stringVector;
  }

  /**
   * 删除编码后面的零
   * @param Bm 编码
   * @param Js 级数
   * @param Struct 编码结构
   * @return 去零后的编码
   */
  public static String DelZeroForBM(String Bm,int Js,String Struct) {
    int i, Len, Length;
    Len = Bm.length();
    Length = GetStructLength(Struct, Js);
    if ( Len >= Length ) {
      Bm = Bm.substring(0,Length);
    }
    return Bm;
  }
  /**
   * 根据指定级数,截断编码
   * @param Bm 编码
   * @param Js 级数
   * @param Struct 编码结构
   * @return 截断后的编码
   */
  public static String DelTagForBM(String Bm,int Js,String Struct) {
    int i, Len, Length;
    Len = Bm.length();
    Length = GetStructLength(Struct, Js);
    if ( Len >= Length ) {
      Bm = Bm.substring(0,Length);
    }
    return Bm;
  }
  /**
   * 对编码进行补零(后面补零)
   * @param Bm 编码
   * @param Struct 编码结构
   * @return 补零后的编码
   */
  public static String FillZeroForBM(String Bm, String Struct) {
    return FillTagForBM(Bm,Struct,"0");
//    int i, Len, Length;
//    Len = Bm.length();
//    Length = GetStructLength(Struct, 0);
//    for (i = 0; i < Length - Len; i++) {
//      Bm += "0";
//    }
//    return Bm;
  }
  /**
   * 按编码结构,在编码后面补充指定字符
   * @param Bm 编码
   * @param Struct 编码结构
   * @param tag 要补充的字符
   * @return 补充字符后的字符串
   */
  public static String FillTagForBM(String Bm, String Struct,String tag) {
    int i, Len, Length;
    Len = Bm.length();
    Length = GetStructLength(Struct, 0);
    for (i = 0; i < Length - Len; i++) {
      Bm += tag;
    }
    return Bm;
  }
  /**
   * 取得指定编码的级数
   * @param code 编码
   * @param struct 编码结构
   * @return 指定编码的级数
   */
  public static int getJsByCodingStruct(String code, String struct) {
    int codeLength = code.length();
    int structLength = struct.length();
    int tempLength = 0;
    for(int js = 1 ; js <= structLength ; js ++ ) {
      tempLength = GetStructLength(struct, js);
      if(codeLength == tempLength)
        return js;
    }
    return 0;
  }
  /**
   * 获得从第一级到指定级数的编码总长度
   * @param Struct 编码结构
   * @param JS 级数
   * @return 长度
   */
  public static int GetStructLength(String Struct,int JS) {
    int i,Length=0;String sub;int ii;
    if ( JS == 0 ) JS = Struct.trim().length();
    for(i=0;i<JS;i++) {
      sub     = Struct.substring(i,i+1).trim();
      //ii		 = Integer.decode("0x"+sub);
      ii = Integer.parseInt(sub,36);
      //Length += Integer. .parseInt(sub);
      Length += ii;
      //Length += ii.intValue();
    }
    return Length;
  }
  /**
   * 获得指定级数的编码
   * @param BM 编码
   * @param Struct 编码结构
   * @param SJS 指定级数
   * @return 指定级数的编码或空
   */
  public static String GetSubBMfromBM(String BM,String Struct,int SJS) {
    String Res="";int Len;
    // 级数的有效性
    Len = GetStructLength(Struct,SJS);
    if ( BM.length() >= Len )
      Res = BM.substring(0,Len);
    return Res;
  }
  /**
   * 将EXECL表中的列转换为数字的列号.例如:传入值"A",返回值"1";传入值"AA",返回值"27";
   * @param aCol EXCEL中的列编号
   * @return String 十进制的列编号
   */
  public static String Chars2Number(String aCol){
    String strnum = "";
    long num=0 ;
    int i = 0;
    for(i=aCol.length()-1; i>=0; i--)
      num += Math.pow(26, i) * (aCol.charAt(aCol.length()-1-i)-'A'+1);
    strnum = String.valueOf(num);
    if(strnum==null)  strnum= "";
    return strnum;
  }
  /**
   * 输入一个数字，在该数字前面进行补零操作,得到leng长度的字符。FillZeroFromBegin(8,4)-> "0008"
   * @param num 要进行转换的数字
   * @param leng 返回的字符的长度
   * @return 指定长度的字符串
   */
  public static String FillZeroFromBegin(int num,int leng){
    return FillTagFromBegin(num,leng,"0");
  }
  /**
   * 输入一个数字，在该数字前面补充指定字符,得到leng长度的字符。FillZeroFromBegin(8,4,"A")-> "AAA8"
   * @param num 要进行转换的数字
   * @param leng 返回的字符的长度
   * @param Tag 补充的字符
   * @return 补充指定字符后的字符串
   */
  public static String FillTagFromBegin(int num,int leng,String Tag){
    String Res = "",tempStr1="",tempStr2="";
    tempStr1 = String.valueOf(num);
    for(int i=0;i<(leng-tempStr1.length());i++){
      tempStr2 += Tag;
    }
    Res = tempStr2 + tempStr1;
    return Res;
  }
  /**
   * 对编码进行去零操作
   * @param BM 编码
   * @param BMStruct 编码结构
   * @return 去掉零后的字符串
   */
  public static String ClearBMZero(String BM,String BMStruct) {
    return ClearBMTag(BM,BMStruct,"0");
  }
  /**
   * 去除编码中某级全是指定字符
   * @param BM 编码
   * @param BMStruct 编码结构
   * @param Tag 指定字符
   * @return 去掉指定字符的串后的编码
   */
  public static String ClearBMTag(String BM,String BMStruct,String Tag) {
    int JSLen = BMStruct.length(); // 获取有几级
    String bm="",ZERO,Tmp;int Len;
    for(int i=1;i<=JSLen;i++) {
      Tmp = BMStruct.substring(i-1,i);
      Len = Integer.parseInt(Tmp);
      ZERO = RepeatChar(Tag,Len);
      if ( !BM.startsWith(ZERO) && BM.length()>=Len ) {
        bm += BM.substring(0,Len);
        BM = BM.substring(Len);
      } else break;
    }
    return bm;
  }
  /**
   * 对指定字符串进行多次+连接操作
   * @param c 指定的字符
   * @param len 重复次数
   * @return 重复后的字符串
   */
  public static String RepeatChar(String c,int len) {
    StringBuffer Res = new StringBuffer();
    for(int i=0;i<len;i++) {
      Res.append(c);
    }
    return Res.toString();
  }

  /**
   * 指定一个数组，把数组里面的字符串替换成目标字符串
   *
   * @param  srcString String
   * @param    replace String[]
   * @param     target String
   * @return           String
   */
  public static String replaceStringAll(String srcString, String[] replace, String target) {
      if (replace != null) {
          for (int i = 0, n = replace.length; i < n; i++) {
              srcString = replaceString(srcString, replace[i], target);
          }
      }
      return srcString;
  }

  /**
   * 产生唯一的随机字符串
   * 
   * @return
   */
  public static String generateKey() {
	  return  UUID.randomUUID().toString();
  }
  
  /**
   * 查询IP
   * @param request
   * @return
   */
  public static String getIpAddr(HttpServletRequest request) {
	String ip = request.getHeader("x-forwarded-for");
	if (!checkIP(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
	}
	if (!checkIP(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
	}
	if (!checkIP(ip)) {
		ip = request.getRemoteAddr();
	}
	if(ip.equals("0:0:0:0:0:0:0:1")) {
		ip = "127.0.0.1";
	}
	return ip;
  }
  
  /**
   * 判断IP是否合法
   * @param ip
   * @return
   */
  private static boolean checkIP(String ip) {
	if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) || ip.split(".").length != 4) {
		return false;
	}
	return true;
  }
  
  /**
	* 将字符串复制到剪切板。
	*/
  public static void setSysClipboardText(String writeMe) {
	Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
	Transferable tText = new StringSelection(writeMe);
	clip.setContents(tText, null);
  }
  
  /**
	 * 将A-Z转换成10-35
	 * @param source String
	 * @return int
	 */
	public static int getStrutLength(String pStruct){
		String target = "";
	    Pattern pattern = Pattern.compile("[0-9]");
	    Matcher matcher = pattern.matcher(pStruct);
	    if (matcher.matches()) {
	      target = pStruct;
	    } else {
	      char[] temp = pStruct.toCharArray();
	      char sou = temp[0];
	      target = String.valueOf(((int) sou) - 55);
	    }
	    return Integer.parseInt(target);
	}
	
	/**
	 *
	 * @param value String
	 * @param  mark String
	 * @return      String[]
	 */
	public static String[] split(String value, String mark) {
		int index = value.indexOf(mark);
		java.util.List list = new ArrayList();
		while (index >= 0) {
			list.add(value.substring(0, index));
			value = value.substring(index + mark.length());
			index = value.indexOf(mark);
		}
		list.add(value);
		String[] rtn = new String[list.size()];
		System.arraycopy(list.toArray(), 0, rtn, 0, rtn.length);
		return rtn;
	}
}