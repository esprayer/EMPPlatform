package jtoolkit.string.classes;

import java.util.*;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//���: Xyz(2001.12.29)
//ʵ��: Xyz
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JStringFunction {
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JStringFunction() {
  }
  //���ַ�������ת��Ϊ�ַ���������ָ���ķ��ŷָ���Щ������
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static String convertFromStringArrayToStringBySymbol(String[] stringArray,char symbol) {
    StringBuffer sb = new StringBuffer();
    for(int i = 0; i < stringArray.length; i++ ) {
      sb.append(stringArray[i]);
      sb.append(symbol);
    }
    return sb.toString();
  }
 //8��31�ռӵĹ�������
   //����ָ���ķ��ŷָ��������ַ�����ת��Ϊ�ַ�������
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
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
  //����ָ���ķ��ŷָ��������ַ�����ת��Ϊ�ַ�������Vector
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
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
  //���ַ���Vectorת��Ϊ�ַ���������ָ���ķ��ŷָ���Щ������
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
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
   * ��,�ָ�����ʽ�����ֵ
   * @param   value -- ��ֵ
   * @return  String
   */
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static String defaultFormat(double value) {
        //      int i=(int)value*100;
        if (value < 0.01) {
          return "&nbsp;"; //0����ʾ
        } else {
          java.text.NumberFormat nf = new java.text.DecimalFormat("###,###.00");
          return nf.format(value);
        }
  }
  /**
   * �ַ�����亯��
   * Ŀ��       ����һ��������ĳ�ַ������ַ���
   * @param     psStr    Ҫ���д���Ĳ����ַ���
   * @param     psLen    �����ַ����ĳ���
   * @param     psC      �����ַ���
   * @return    string   ������ַ���
   */
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
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
   * �Էָ�����ʽ�����ֵ
   * @param   value -- ��ֵ
   * @param   sep   -- ÿһ�ε�λ��
   * @param   ch    -- �ָ���
   * @param   round -- С��λ
   * @return  String
   */
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static String formatValue(double value, int sep, char ch, int round) {
          if(Math.pow(10.0,round)*value < 1){
                  return "&nbsp;";
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
   *	�����ַ�ת���ķ���
   */
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static String GB2Uni(String original) {
    return original;
//	if (original != null) {
//	  try {
//		  return new String(original.getBytes("GBK"), "ISO8859_1");
//	  } catch (Exception e) {
//  		e.printStackTrace();
//	  	return null;
//	  }
//	} else
//	  return null;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static String Uni2GB(String original) {
    return original;
//	if (original != null) {
//	  try {
//  		return new String(original.getBytes("ISO8859_1"), "GBK");
//	  } catch (Exception e) {
//		  e.printStackTrace();
//	  	return null;
//	  }
//	} else
//	  return null;
  }
  /**
   * ����ֵValue��Ϊ�ַ���
   * @param     pre      ǰ׺
   * @param     value    ��ֵ
   * @param     num      λ������ֵ��λ����
   * @return    ��Ҫ����ֵValue��Ϊ�ַ�����
   */
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
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
   * ���ظ��ݵ�ǰʱ��ȷ����һ��Ψһ�ַ�����
   * @return  String
   */
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static String getTempStr() {
        //String str=String.valueOf(System.currentTimeMillis());
        //return str.substring(str.length()-6);
        return String.valueOf(System.currentTimeMillis());
  }
  /**
   * �ַ����жϺ���
   * Ŀ��       �ж�һ���ַ������Ƿ������һ���ַ���
   * @param     psStr    Ҫ���д���Ĳ����ַ���
   * @param     psS      ���������ַ���
   * @return    boolean  true or false
   */
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static boolean isIncludeString(String psStr, String psS) {
        int viPos = psStr.indexOf(psS);
        return viPos >= 0;
  }
  /**
   * �ַ����滻����
   * @param     psStr  Ҫ���д���Ĳ����ַ���
   * @param     psS    ���滻���ַ���
   * @param     psD    �滻���ַ���
   * @return    psStr  ���滻����ַ�����
   */
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
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
   * �ַ�����ȡ����
   * Ŀ��       ��ָ���ķ��ŷָ� �ַ���
   * @param     psStr      Ҫ���д���Ĳ����ַ���
   * @param     smybol      ָ���ķָ���
   * @return    vsString    �������� :[��ȡ�ַ���ȡ����ַ�]
   */
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
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

  //����ָ���ķ��ŷָ��������ַ�����ת��Ϊ�ַ�������Vector
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
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
   * ���ظ��ݵ�ǰʱ��ȷ���ĺ�Nλ��һ��Ψһ�ַ�����
   * @param   length -- �����ַ����ĳ���
   * @return  String
   */
  public static String getTempStr(int length) {
        String str=String.valueOf(System.currentTimeMillis());
        return str.substring(str.length()-length);
  }

  //����ָ���ķ��ŷָ��������ַ�����ת��Ϊ�ַ�������Vector
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Xyz(2001.12.29)
  //ʵ��: Xyz
  //�޸�:
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
    //����: ��0
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
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
    //����: ��0
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�: ����ֵ: �����Ϊ��,˵�����û��д���Ȩ��,���Ϊ��,˵�����û�û�д���Ȩ��
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
    //����:
    //���: Xyz(2001.12.29)
    //ʵ��: Xyz
    //�޸�:
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
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
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
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //----------------------------------------------------------------------------------------------
    public static String GetSubBMfromBM(String BM,String Struct,int SJS) {
          String Res="";int Len;
                // ��������Ч��
                Len = GetStructLength(Struct,SJS);
                if ( BM.length() >= Len )
                        Res = BM.substring(0,Len);
                return Res;
    }
}
