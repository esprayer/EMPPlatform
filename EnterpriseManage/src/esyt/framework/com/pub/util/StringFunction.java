package esyt.framework.com.pub.util;

import java.util.*;
import java.lang.*;
import sun.io.ByteToCharConverter;
import java.io.UnsupportedEncodingException;
import java.awt.*;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author ES
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: ES(2013.07.29)
//实现: ES
//修改:
//--------------------------------------------------------------------------------------------------
public class StringFunction {
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
  public StringFunction() {
  }
  //将字符串数组转换为字符串，并用指定的符号分隔这些数据项
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String convertFromStringArrayToStringBySymbol(String[] stringArray,char symbol) {
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < stringArray.length; i++ ) {
      sb.append(stringArray[i]);
      sb.append(symbol);
    }
    return sb.toString();
  }
 //8月31日加的公共方法
   //将以指定的符号分隔数据项字符串，转换为字符串数组
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String[] convertFromStringToStringArrayBySymbol(String string,String symbol)
  {
	Vector stringVector = convertFromStringToStringVectorBySymbol(string,symbol);
	String [] stringArray = new String[stringVector.size()];
	for(int i = 0 ; i < stringVector.size() ; i ++ )
	  stringArray[i] = (String)(stringVector.elementAt(i));
	return stringArray;
  }
  public static String[] convertFromStringToStringArrayBySymbolNO(String string,String symbol)
  {
  Vector stringVector = convertFromStringToStringVectorBySymbolNO(string,symbol);
  String [] stringArray = new String[stringVector.size()];
  for(int i = 0 ; i < stringVector.size() ; i ++ )
    stringArray[i] = (String)(stringVector.elementAt(i));
  return stringArray;
  }
  //将以指定的符号分隔数据项字符串，转换为字符串数组Vector
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
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
  public static Vector convertFromStringToStringVectorBySymbolNO(String string,String symbol)
  {
  StringTokenizer st = new StringTokenizer(string,symbol,false);
  Vector stringVector = new Vector();
  while(st.hasMoreElements())
  {
    stringVector.addElement(st.nextElement());
  }
  return stringVector;
  }
  //将字符串Vector转换为字符串，并用指定的符号分隔这些数据项
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
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
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String defaultFormat(double value) {
	//      int i=(int)value*100;
	if (value < 0.01) {
	  return ""; //0不显示
	} else {
	  java.text.NumberFormat nf = new java.text.DecimalFormat("###,###.00");
	  return nf.format(value);
	}
  }
  /**
   * 字符串填充函数
   * 目的       生成一定长度用某字符填充的字符串
   * @param     psStr    要进行处理的参数字符串
   * @param     psLen    返回字符串的长度
   * @param     psC      填充的字符串
   * @return    string   填充后的字符串
   */
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
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
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String formatValue(double value, int sep, char ch, int round) {
	  if(Math.pow(10.0,round)*value < 1){
		  return "";
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
   *	进行字符转换的方法
   */
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String GB2Uni(String original) {
	if (original != null) {
	  try {
		  return new String(original.getBytes("GBK"), "ISO8859_1");
	  } catch (Exception e) {
  		e.printStackTrace();
	  	return null;
	  }
	} else
	  return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String Uni2GB(String original) {
	if (original != null) {
	  try {
  		return new String(original.getBytes("ISO8859_1"), "GBK");
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
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
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
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String getTempStr() {
	//String str=String.valueOf(System.currentTimeMillis());
	//return str.substring(str.length()-6);
	return String.valueOf(System.currentTimeMillis());
  }
  /**
   * 字符串判断函数
   * 目的       判断一个字符串中是否包含另一个字符串
   * @param     psStr    要进行处理的参数字符串
   * @param     psS      被包含的字符串
   * @return    boolean  true or false
   */
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
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
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
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
   * 字符串截取函数
   * 目的       以指定的符号分隔 字符串
   * @param     psStr      要进行处理的参数字符串
   * @param     smybol      指定的分隔符
   * @return    vsString    返回数组 :[所取字符，取后的字符]
   */
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
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

  //将以指定的符号分隔数据项字符串，转换为字符串数组Vector
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
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

  //将以指定的符号分隔数据项字符串，转换为字符串数组Vector
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: ES(2013.07.29)
  //实现: ES
  //修改:
  //------------------------------------------------------------------------------------------------
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

    //----------------------------------------------------------------------------------------------
    //描述: 填0
    //设计: ES(2013.07.29)
    //实现: ES
    //修改:
    //----------------------------------------------------------------------------------------------
    public static String DelZeroForBM(String Bm,int Js,String Struct) {
      int i, Len, Length;
        Len = Bm.length();
        Length = GetStructLength(Struct, Js);
        if ( Len >= Length ) {
	        Bm = Bm.substring(0,Length);
        }
        return Bm;
    }

    //----------------------------------------------------------------------------------------------
    //描述: 填0
    //设计: ES(2013.07.29)
    //实现: ES
    //修改: 返回值: 如果不为空,说明此用户有此项权限,如果为空,说明此用户没有此项权限
    //----------------------------------------------------------------------------------------------
    public static String FillZeroForBM(String Bm, String Struct) {
      int i, Len, Length;
        Len = Bm.length();
        Length = GetStructLength(Struct, 0);
        for (i = 0; i < Length - Len; i++) {
            Bm += "0";
        }
        return Bm;
    }

    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: ES(2013.07.29)
    //实现: ES
    //修改:
    //----------------------------------------------------------------------------------------------
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

    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: ES(2013.07.29)
    //实现: ES
    //修改:
    //----------------------------------------------------------------------------------------------
    public static int GetStructLength(String Struct,int JS) {
      int i,Length=0;String sub;Integer ii;
        if ( JS == 0 ) JS = Struct.trim().length();
        for(i=0;i<JS;i++) {
            sub     = Struct.substring(i,i+1).trim();
            ii		 = Integer.decode("0x"+sub);
            //Length += Integer. .parseInt(sub);
            Length += ii.intValue();
        }
        return Length;
    }

    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: ES(2013.07.29)
    //实现: ES
    //修改:
    //----------------------------------------------------------------------------------------------
    public static String GetSubBMfromBM(String BM,String Struct,int SJS) {
	  String Res="";int Len;
	  	// 级数的有效性
		Len = GetStructLength(Struct,SJS);
		if ( BM.length() >= Len )
			Res = BM.substring(0,Len);
	  	return Res;
    }
    //输入一个数字，得到leng长度的字符。FillZeroFromBegin(8,4)-> "0008"
    //Parm1: num  要进行转换的数字
    //Parm2: leng 返回的字符的长度
    public static String FillZeroFromBegin(int num,int leng){
      return FillTagFromBegin(num,leng,"0");
//    String Res = "",tempStr1="",tempStr2="";
//    tempStr1 = String.valueOf(num);
//    for(int i=0;i<(leng-tempStr1.length());i++){
//      tempStr2 += "0";
//    }
//    Res = tempStr2 + tempStr1;
//    return Res;
    }
    public static String FillTagFromBegin(int num,int leng,String Tag){
      String Res = "",tempStr1="",tempStr2="";
      tempStr1 = String.valueOf(num);
      for(int i=0;i<(leng-tempStr1.length());i++){
        tempStr2 += Tag;
      }
      Res = tempStr2 + tempStr1;
      return Res;
    }
    public static String ClearBMZero(String BM,String BMStruct) {
      return ClearBMTag(BM,BMStruct,"0");
//    int JSLen = BMStruct.length(); // 获取有几级
//    String bm="",ZERO,Tmp;int Len;
//    for(int i=1;i<=JSLen;i++) {
//      Tmp = BMStruct.substring(i-1,i);
//      Len = Integer.parseInt(Tmp);
//      ZERO = RepeatChar("0",Len);
//      if ( !BM.startsWith(ZERO) ) {
//        bm += BM.substring(0,Len);
//        BM = BM.substring(Len);
//      } else break;
//    }
//    return bm;
    }
    public static String ClearBMTag(String BM,String BMStruct,String Tag) {
      int JSLen = BMStruct.length(); // 获取有几级
      String bm="",ZERO,Tmp;int Len;
      for(int i=1;i<=JSLen;i++) {
        Tmp = BMStruct.substring(i-1,i);
        Len = Integer.parseInt(Tmp);
        ZERO = RepeatChar(Tag,Len);
        if ( !BM.startsWith(ZERO) ) {
          bm += BM.substring(0,Len);
          BM = BM.substring(Len);
        } else break;
      }
      return bm;
    }
    public static String RepeatChar(String c,int len) {
      String Res ="" ;
      for(int i=0;i<len;i++) {
        Res += c;
      }
      return Res;
    }
    /**
     * key1=value1;key2=value2;key3=value3
     * @param inputString String
     * @param hashMap Map
     */
    public static void String2HashMap(String inputString,java.util.Map hashMap) {
      String[] keyValues = StringFunction.convertFromStringToStringArrayBySymbolNO(inputString,";");
      String[] keyValue;String key,value;
      for(int i=0;i<keyValues.length;i++) {
        if ( !"".equals(keyValues[i]) ) {
          keyValue = StringFunction.convertFromStringToStringArrayBySymbolNO(keyValues[i],"=");
          if ( keyValue.length < 2 ) continue;
          key   = keyValue[0];
          value = keyValue[1];
          hashMap.put(key,value);
        }
      }
    }
    /**
     *
     * @param hashMap Map
     * @return String
     */
    public static String HashMap2String(java.util.Map hashMap) {
      Object[] keys = hashMap.keySet().toArray();
      String key,value,keyValue="";
      for(int i=0;i<keys.length;i++) {
        key = (String)keys[i];value = (String)hashMap.get(key);
        keyValue += key+"="+value+";";
      }
      if(keyValue.length()>0){
        keyValue = keyValue.substring(0, keyValue.length() - 1);
      }
      return keyValue;
    }
    /**
     *
     * @param value String
     * @return Color
     */
    public static Color getColorFromString(String value) {
      String[] v = value.split(",");
      Color c = new Color(Integer.parseInt(v[0]), Integer.parseInt(v[1]),Integer.parseInt(v[2]));
      return c;
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

    public static void main(String[] args) {
        String value = "a;;;; ";
        String[] rtn = split(value, ";;");
        System.out.println(rtn);
    }



}
