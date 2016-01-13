package jreportfunction.pub;

import java.util.*;

import java.awt.*;
import java.util.ResourceBundle;
import java.math.BigDecimal;
import jframework.foundation.classes.JActiveDComDM;
import jreport.value.JREPORT;

import javax.swing.JOptionPane;

import com.client.fwkview.FMISContentWindow;
import com.efounder.date.DateFunction;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.pub.util.JCommonFunction;

import jbof.gui.window.classes.JBOFChildWindow;

/**
 * <p>����ʽ���ú���</p>
 * <p>����ʽ�õ�һЩС�ļ����õ�\���ú���  </p>
 * <p>Copyright: pansoft (c) 2003.4.23</p>
 * <p>pansoft </p>
 * @author fsz
 * @version 1.0
 */

public class JReportPubFunc {
static ResourceBundle res = ResourceBundle.getBundle("jreportfunction.pub.Language");

    public JReportPubFunc() {
    }

    /**
     * ����ʾ��Ԫ��Χ����������ת��Ϊ������+������+����+��������ʽ
     * @param psCellRange String
     * @param psBbbh String
     * @param psDate String
     * @param model JReportModel
     * @return String
     */
    public static String changeRowColToId(String psCellRange, String psBbbh, String psDate, jreport.swing.classes.JReportModel model) {
        String asTemp, asRow = "", asCol = "";
        long srow, scol, erow, ecol;
        asTemp = psCellRange.substring(0, psCellRange.indexOf('#'));
        srow = Long.valueOf(asTemp).longValue();
        psCellRange = psCellRange.substring(psCellRange.indexOf('#') + 1);
        asTemp = psCellRange.substring(0, psCellRange.indexOf('#'));
        scol = Long.valueOf(asTemp).longValue();
        psCellRange = psCellRange.substring(psCellRange.indexOf('#') + 1);
        asTemp = psCellRange.substring(0, psCellRange.indexOf('#'));
        erow = Long.valueOf(asTemp).longValue();
        psCellRange = psCellRange.substring(psCellRange.indexOf('#') + 1);
        ecol = Long.valueOf(psCellRange).longValue();
        asRow = model.GetHzdOrde(psBbbh, psDate, (int) srow);
        if (null == asRow)
            return "";
        asCol = model.GetLzdOrde(psBbbh, psDate, (int) scol);
        if (null == asCol)
            return "";
        return asRow + '#' + asCol + '#' + String.valueOf(erow - srow) + '#' + String.valueOf(ecol - scol);
    }

    /**
     * ����ʾ��Ԫ��Χ���ڲ��洢��ʽת��Ϊ�����������ʽ
     * @param psCellRange String
     * @param psBbbh String
     * @param psDate String
     * @param model JReportModel
     * @return String
     */
    public static String changeIdToRowCol(String psCellRange, String psBbbh, String psDate, jreport.swing.classes.JReportModel model) {
        String asTemp;
        String srowid, scolid;
        long rows, cols, srow = 0, scol = 0;
        srowid = psCellRange.substring(0, psCellRange.indexOf('#'));
        psCellRange = psCellRange.substring(psCellRange.indexOf('#') + 1);
        scolid = psCellRange.substring(0, psCellRange.indexOf('#'));
        psCellRange = psCellRange.substring(psCellRange.indexOf('#') + 1);
        asTemp = psCellRange.substring(0, psCellRange.indexOf('#'));
        rows = Long.valueOf(asTemp).longValue();
        psCellRange = psCellRange.substring(psCellRange.indexOf('#') + 1);
        cols = Long.valueOf(psCellRange).longValue();
        srow = model.GetHzdZb(psBbbh, psDate, srowid);
        if (srow == 0)
            return "";
        scol = model.GetLzdZb(psBbbh, psDate, scolid);
        if (scol == 0)
            return "";
        return String.valueOf(srow) + '#' + String.valueOf(scol) + '#' + String.valueOf(srow + rows) + '#' + String.valueOf(scol + cols);
    }

    /**
     * ����һ����������ķ�Χ��E3:F4,���ظ÷�Χ�Ŀ�ʼ��+��ʼ��+������+������
     * ֮ǰ��#�ָ�,������""��
     * @param psRange String
     * @return String
     */
    public static String cellRangeToRowCol(String psRange) {
        String asStart, asEnd;
        String asSRow, asERow, asSCol, asECol;
        switch (psRange.indexOf(":")) {
            case -1:
                asStart = asEnd = psRange;
                break;
            case 0:
                asStart = asEnd = psRange.substring(1);
                break;
            default:
                if (psRange.indexOf(":") == psRange.length() - 1)
                    asStart = asEnd = psRange.substring(0, psRange.length() - 1);
                else {
                    asStart = psRange.substring(0, psRange.indexOf(':'));
                    asEnd = psRange.substring(psRange.indexOf(':') + 1);
                }
        }
        asStart = cellToRowCol(asStart);
        if (asStart.trim().length() < 1){
            return "";
        }
        asEnd = cellToRowCol(asEnd);
        if (asEnd.trim().length() < 1){
            return "";
        }
        asSRow = asStart.substring(0, asStart.indexOf('#'));
        asSCol = asStart.substring(asStart.indexOf('#') + 1);
        asERow = asEnd.substring(0, asEnd.indexOf('#'));
        asECol = asEnd.substring(asEnd.indexOf('#') + 1);
        if (Long.valueOf(asSRow).longValue() > Long.valueOf(asERow).longValue())
            return "";
        if (Long.valueOf(asSCol).longValue() > Long.valueOf(asECol).longValue())
            return "";
        return asStart + '#' + asEnd;
    }

    /**
     * ����ת��Ϊ��Ԫ��Χ
     * @param psRowCol String
     * @return String
     */
    public static String rowColToCellRange(String psRowCol) {
        long srow, scol, erow, ecol;
        String asTemp;
        asTemp = psRowCol.substring(0, psRowCol.indexOf('#'));
        srow = Long.valueOf(asTemp).longValue();
        psRowCol = psRowCol.substring(psRowCol.indexOf('#') + 1);
        asTemp = psRowCol.substring(0, psRowCol.indexOf('#'));
        scol = Long.valueOf(asTemp).longValue();
        psRowCol = psRowCol.substring(psRowCol.indexOf('#') + 1);
        asTemp = psRowCol.substring(0, psRowCol.indexOf('#'));
        erow = Long.valueOf(asTemp).longValue();
        asTemp = psRowCol.substring(psRowCol.indexOf('#') + 1);
        ecol = Long.valueOf(asTemp).longValue();

        return rowColToCell(srow, scol) + ":" + rowColToCell(erow, ecol);
    }

    /**
     * ������ת����CELL����
     * @param row long
     * @param col long
     * @return String
     */
    public static String rowColToCell(long row, long col) {
        String asCol;
        int i;
        char ch;
        col--;
        if (col < 26) {
            i = (int) 'A' + (int) col;
            ch = (char) i;
            asCol = String.valueOf(ch);
        } else {
            i = (int) 'A' + ( (int) col / 26 - 1);
            ch = (char) i;
            asCol = String.valueOf(ch);
            i = 'A' + (int) col % 26;
            ch = (char) i;
            asCol += String.valueOf(ch);
        }
        return asCol + String.valueOf(row);
    }

    /**
     * ����һ��EXCEL��ʽ�ĵ�Ԫ��,������+��
     * @param psCell String
     * @return String
     */
    public static String cellToRowCol(String psCell) {
        int i;
        long col = 0;
        String asCol, asRow;
        char ch;
        String asTmp = psCell.trim().toUpperCase();

        for (i = 0; i < asTmp.length(); i++) {
            ch = asTmp.charAt(i);
            if ( (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
                continue;
            break;
        }
        if (i == 0 || i == asTmp.length())
            return "";
        asRow = asTmp.substring(i);
        asCol = asTmp.substring(0, i);

        for (i = asCol.length() - 1; i >= 0; i--)
            col += java.lang.Math.pow(26, i) * (asCol.charAt(asCol.length() - 1 - i) - 'A' + 1);
        return asRow + '#' + String.valueOf(col);
    }

    /**
     * ����һ������,���ϻ��ȥһ������,�õ��µ�����
     * @param psDate String
     * @param piValue int
     * @return String
     */
    public static String getBeforeDate(String psDate, int piValue) {
        int year, month, day;
        String asDate;
        Calendar cl = Calendar.getInstance();
        year = Integer.valueOf(psDate.substring(0, 4)).intValue();
        cl.set(cl.YEAR, year);
        //Calendar���·��Ǵ�0��ʼ��
        month = Integer.valueOf(psDate.substring(4, 6)).intValue() - 1;
        cl.set(cl.MONTH, month);
        day = Integer.valueOf(psDate.substring(6)).intValue();
        cl.set(cl.DAY_OF_MONTH, day);
        cl.add(cl.DATE, piValue);
        year = cl.get(cl.YEAR);
        month = cl.get(cl.MONDAY) + 1;
        day = cl.get(cl.DAY_OF_MONTH);
        asDate = String.valueOf(year);
        if (month < 10)
            asDate += "0" + String.valueOf(month);
        else
            asDate += String.valueOf(month);
        if (day < 10)
            asDate += "0" + String.valueOf(day);
        else
            asDate += String.valueOf(day);
        return asDate;
    }

    
    /**
     * �Ƿ�������
     * @param psYear String
     * @return boolean
     */
    public static boolean isLeapYear(String psYear) {
    	/*       
    	 * ��ݶ�������4���ǲ�������100
    	 * �������������400��
    	 * ������������ 
    	 */
    	int year;
        year = Integer.valueOf(psYear).intValue();
        return (year%4==0&&year%100!=0||year%400==0);
    }

   
    
    /**
     * ȡĳ���µ����һ��
     * @param psYear String
     * @param psMon String
     * @return String
     */
    public static String getLastDay(String psYear, String psMon) {
        int year, month;
        year = Integer.valueOf(psYear).intValue();
        month = Integer.valueOf(psMon).intValue();
        if (month == 12) {
            year += 1;
            month = 0;
        }
        Calendar cl = Calendar.getInstance();
        cl.set(cl.YEAR, year);
        cl.set(cl.MONTH, month);
        cl.set(cl.DAY_OF_MONTH, 1);
        cl.add(cl.DATE, -1);
        return String.valueOf(cl.get(cl.DAY_OF_MONTH));
    }

    /**
     * ���Ƶ����а�
     * @param psMess String
     */
    public static void copytoClipboard(String psMess) {
        if (psMess == null)
            psMess = "";
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        java.awt.datatransfer.StringSelection ss = new java.awt.datatransfer.StringSelection(psMess);
        toolkit.getSystemClipboard().setContents(ss, ss);
    }

    /**
     * ���һ����ʶ���Ƿ�Ϸ�
     * һ���Ϸ��ı�ʶӦ������ĸ,����,���»������
     * ��������ĸ���»��߿�ʼ
     * @param psId String
     * @return boolean
     */
    public static boolean checkStringId(String psId) {
        char ch;
        if (psId.trim().length() == 0)
            return false;
        ch = psId.charAt(0);
        if (!Character.isLetter(ch) && ch != '_')
            return false;
        for (int i = 1; i < psId.length(); i++) {
            if (!Character.isLetterOrDigit(ch) && ch != '_')
                return false;
        }
        return true;
    }

    /**
     * ��һ�����ʽ�и�����ֵ,�ҳ�������źͻ�ֵ
     * �����������+�ָ��+��ֵ����ʽ+�ָ��.
     * ������Ű���""><=>=<=like
     * @param psCause String
     * @param psLeft String
     * @param psNew String
     * @return String
     */
    public static String asRightValue(String psCause, String psLeft, String psNew) {
        String sResult;
        psCause = psCause.toUpperCase();
        sResult = asRightValue(psCause, psLeft, ">=", psNew).trim();
        if (sResult.length() > 0) {
            return ">=" + "$@$@$@" + sResult;
        }
        sResult = asRightValue(psCause, psLeft, "<=", psNew).trim();
        if (sResult.length() > 0) {
            return "<=" + "$@$@$@" + sResult;
        }
        sResult = asRightValue(psCause, psLeft, "<>", psNew).trim();
        if (sResult.length() > 0) {
            return "<>" + "$@$@$@" + sResult;
        }
        sResult = asRightValue(psCause, psLeft, ">", psNew).trim();
        if (sResult.length() > 0) {
            return ">" + "$@$@$@" + sResult;
        }
        sResult = asRightValue(psCause, psLeft, "<", psNew).trim();
        if (sResult.length() > 0) {
            return "<" + "$@$@$@" + sResult;
        }
        sResult = asRightValue(psCause, psLeft, "=", psNew).trim();
        if (sResult.length() > 0) {
            return "=" + "$@$@$@" + sResult;
        }
		// ���� NOT LIKE ���� add by hufeng 2007.12.11
		sResult = asRightValue(psCause, psLeft, "NOT LIKE", psNew).trim();
		if (sResult.length() > 0) {
			return "NOT LIKE" + "$@$@$@" + sResult;
		}
        sResult = asRightValue(psCause, psLeft, "LIKE", psNew).trim();
        if (sResult.length() > 0) {
            return "LIKE" + "$@$@$@" + sResult;
        }
		sResult = asRightValue(psCause, psLeft, "BY", psNew).trim();
		if (sResult.length() > 0) {
			return "BY" + "$@$@$@" + sResult;
		}
        sResult = asRightValue(psCause, psLeft, "IN", psNew).trim();
        if (sResult.length() > 0) {
            return "IN" + "$@$@$@" + sResult;
        }
        return "";
    }

    /**
     * ������ֵ�ҳ���ֵ���滻
     * ������ֵ+$@$@$@+�滻���ԭ��
     * @param psCause String
     * @param psLeft String
     * @param psOper String
     * @param psNew String
     * @return String
     */
    public static String asRightValue(String psCause, String psLeft, String psOper, String psNew) {
        int nLeft, nOper, nRight;
        char ch;
        String sReult;
        if (!checkStringId(psLeft))
            return "";
        nLeft = psCause.indexOf(psLeft);
        if (nLeft == -1)
            return "";
        for (nOper = nLeft + psLeft.length(); nOper < psCause.length(); nOper++)
            if (psCause.charAt(nOper) != ' ')
                break;
        if (nOper >= psCause.length())
            return "";
        if (!psCause.substring(nOper, nOper + psOper.length()).equals(psOper))
            return "";
        for (nRight = nOper + psOper.length(); nRight < psCause.length(); nRight++)
            if (psCause.charAt(nRight) != ' ')
                break;
        if (nRight >= psCause.length())
            return "";
        int i = nRight;
        if (psCause.charAt(i) == '(') {
            i = nRightBracket(psCause, i);
            if (i == 0)
                return "";
        } else {
            for (; i < psCause.length(); i++) {
                ch = psCause.charAt(i);
                if (ch == ' ' || ch == ',' || ch == ')')
                    break;
            }
        }
        sReult = psCause.substring(nRight, i);
        if (null != psNew && psNew.length() > 0) {
            psCause = left(psCause, nLeft) + " " + psNew + " " + psCause.substring(i);
        }
        return sReult + "$@$@$@" + psCause;
    }

    /**
     * �ж�һ���������Ƿ��������
     * @param asValue String
     * @return boolean
     */
    public static boolean isContainCond(String asValue) {
        if (asValue.indexOf("AND") > -1 || asValue.indexOf("OR") > -1 || asValue.indexOf("NOT") > -1
            || asValue.indexOf("=") > -1 || asValue.indexOf(">") > -1 || asValue.indexOf("<") > -1 || asValue.indexOf(" IN ") > -1
            || asValue.indexOf("LIKE") > -1 || asValue.indexOf("KMMX") > -1 || asValue.indexOf("GRKM") > -1
			|| (asValue.indexOf("YQ") > -1 && asValue.indexOf("BCJE") > -1)  //��BCJE������������DQFXJE����ȡ�����󣬲�����ô����һ���ģ���������û�취��
			|| (asValue.indexOf("YNNDQ") > -1 && asValue.indexOf("BCJE") > -1)  //��BCJE������������DQFXJE����ȡ�����󣬲�����ô����һ���ģ���������û�취��
			|| (asValue.indexOf("YNWDQ") > -1 && asValue.indexOf("BCJE") > -1)) //��BCJE������������DQFXJE����ȡ�����󣬲�����ô����һ���ģ���������û�취��
            return true;
        return false;
    }

    /**
     * ȡָ�������ı��볤��
     * @param psJg String
     * @param pnJs int
     * @return String
     */
    public static String getJsLength(String psJg, int pnJs) {
        int nLen = 0;

        for (int i = 0; i < pnJs; i++) {
            if ((int) psJg.charAt(i) >= (int) 'A')
                nLen += 10+(int) psJg.charAt(i)-(int) 'A';
            else
                nLen += Integer.parseInt(String.valueOf(psJg.charAt(i)));
        }
        return String.valueOf(nLen);
    }

    /**
     * �жϱ�ŵļ���
     * @param asJG String
     * @param asBh String
     * @return int
     */
    public static int getBhJs(String asJG, String asBh) {
        int i, nLen;
        for (i = nLen = 0; i < asJG.length(); i++) {
            if ((int) asJG.charAt(i) >= (int) 'A')
                nLen += 10+(int) asJG.charAt(i)-(int) 'A';
            else
                nLen += Integer.parseInt(String.valueOf(asJG.charAt(i)));
            if (nLen >= asBh.length())
                break;
        }
        return i < asJG.length() ? i + 1 : i;
    }
    /**
     * ��ȡ�ϼ����
     * @param asJG String
     * @param asBh String
     * @return String
     */
    public static String getParentBh(String asJG, String asBh) {
        int i, nLen,iJs;
        for (i = nLen = 0; i < asJG.length(); i++) {
            if ((int) asJG.charAt(i) >= (int) 'A')
                nLen += 10+(int) asJG.charAt(i)-(int) 'A';
            else
                nLen += Integer.parseInt(String.valueOf(asJG.charAt(i)));
            if (nLen >= asBh.length())
                break;
        }
        iJs = i < asJG.length() ? i + 1 : i;
        return asBh.substring(0,Integer.valueOf(getJsLength(asJG,iJs-1)));
    }
    /**
     * ��һ�������͵��ִ���1���ٷ����ִ�
     * ���ص��ִ��ĳ���,���㲹0
     * @param as String
     * @param len int
     * @return String
     */
//    public static String incStringNum(String as, int len) {
//        int nValue;
//        String asValue;
//        nValue = Integer.valueOf(as).intValue();
//        nValue += 1;
//        asValue = String.valueOf(nValue);
//        asValue = stringOfChar('0', len - asValue.length()) + asValue;
//        return asValue;
//    }

    /**
     * ���һ���ַ��Ƿ�ȫ�����������
     * @param as String
     * @return boolean
     */
    public static boolean isNumString(String as) {
        char ch;
        for (int i = 0; i < as.length(); i++) {
            ch = as.charAt(i);
            if ( (ch < '0' || ch > '9') && ch != '.')
                return false;
        }
        if (as.indexOf('.') != as.lastIndexOf('.'))
            return false; //���С����
        return true;
    }

    /**
     * n���ַ���ϳ�һ���ִ�
     * @param ch char
     * @param n int
     * @return String
     */
    public static String stringOfChar(char ch, int n) {
        String as = "";
        if (n < 1)
            return "";
        for (int i = 1; i <= n; i++) {
            as += ch;
        }
        return as;
    }

    /**
     * ȡ�ִ���ߵĵ�n���ַ�
     * @param as String
     * @param n int
     * @return String
     */
    public static String left(String as, int n) {
        if (n < 1)
            return "";
        if (n > as.length())
            return "";
        return as.substring(0, n);
    }

    /**
     * �������,����Vector��
     * 1. ����������������б�
     * 2. ���б������������еĲ��ֺ����ڵ����Ż�˫�����еĲ������ַ�'*'�滻����
     * 3. ���ݶ���λ�ð�������Ĳ�����
     * @param asFunc String
     * @param vList Vector
     */
    public static void SplitParams(String asFunc, Vector vList) {
        String asTrueParamList, asTemp;
        int i, nLen;
        vList.clear();
        asTrueParamList = getFuncParamList(asFunc);
        if (asTrueParamList.length() == 0)
            return;
        asTemp = asTrueParamList;
        //���������ַ���*�Ŵ���
        while ( (i = asTemp.indexOf('(')) > -1) {
            nLen = ApartFunc(asTemp, i).length();
            if (nLen < 1)
                return;
            asTemp = left(asTemp, i)
                     + stringOfChar('*', nLen)
                     + asTemp.substring(i + nLen);
        }
        //��'������Ķ�����*����
        while ( (i = asTemp.indexOf("'")) > -1) {
            nLen = asTemp.indexOf("'", i + 1);
            if (nLen == -1)
                break; //δ�ҵ�
            nLen = nLen - i + 1;
            asTemp = left(asTemp, i)
                     + stringOfChar('*', nLen)
                     + asTemp.substring(i + nLen);
        }
        // ˫�����еĲ������ַ�'*'�滻��
        while ( (i = asTemp.indexOf("\"")) > -1) {
            nLen = asTemp.indexOf("\"", i + 1);
            if (nLen == -1)
                break; //δ�ҵ�
            nLen = nLen - i + 1;
            asTemp = left(asTemp, i)
                     + stringOfChar('*', nLen)
                     + asTemp.substring(i + nLen);
        }
        // �������
        while ( (i = asTemp.indexOf(",")) > -1) {
            vList.addElement(left(asTrueParamList, i).trim());
            asTemp = asTemp.substring(i + 1);
            asTrueParamList = asTrueParamList.substring(i + 1);
        }
        vList.addElement(asTrueParamList.trim());
        /*    String[] asparms=asTemp.split(",");
            for(i = 0;i<asparms.length;i++){
              vList.addElement(asparms[i].trim());
            }*/
    }

    /**
     * ��ָ��λ�ÿ�ʼ�ֽ�һ�������ź���
     * @param as String
     * @param nbegin int
     * @return String
     */
    public static String ApartFunc(String as, int nbegin) {
        int i, brkCount;
        String asTemp = as.substring(nbegin, as.length());
        if ( (nbegin = asTemp.indexOf("(")) == -1)
            return ""; // δ�ҵ�������
        for (i = nbegin + 1, brkCount = 1; i < asTemp.length(); i++) {
            switch (asTemp.charAt(i)) {
                case '(':
                    ++brkCount;
                    break;
                case ')':
                    --brkCount;
                    break;
            }
            if (brkCount == 0)
                break;
        }
        return brkCount == 0 ? asTemp.substring(0, i + 1) : "";
    }

    /**
     * ��ú����Ĳ�����,����ȡ���������м�Ĳ���.
     * ���û�������Ż���û����֮ƥ����������򷵻�""
     * @param asFunc String
     * @return String
     */
    public static String getFuncParamList(String asFunc) {
        int nLeft = asFunc.indexOf("(", 0);
        int nRight = nRightBracket(asFunc, nLeft);
        if (nRight > 0 && nRight - nLeft > 1)
            return asFunc.substring(nLeft + 1, nRight);
        else
            return "";
    }

    /**
     * ��ָ��λ���������ŵĶ�Ӧ�����š�
     * ���ָ��λ�ò��������ţ���δ�ҵ�ƥ���������򷵻�0
     * @param as String
     * @param nLeftBr int
     * @return int
     */
    public static int nRightBracket(String as, int nLeftBr) {
        if (as.charAt(nLeftBr) != '(')
            return 0;
        int brkCount = 1;
        for (++nLeftBr; nLeftBr < as.length(); nLeftBr++) {
            switch (as.charAt(nLeftBr)) {
                case '(':
                    ++brkCount;
                    break;
                case ')':
                    --brkCount;
                    break;
            }
            if (brkCount == 0)
                break;
        }
        return brkCount > 0 ? 0 : nLeftBr;
    }

    /**
     * ����һ���������ʽ,�ó��������ͺ����ֵ
     * @param psCause String
     * @param psLeft String
     * @return String
     */
    public static String getOperValueString(String psCause, String psLeft) {
        String asM, asOper, asValue;
        if (psCause.indexOf(psLeft) > -1) {
            asM = JReportPubFunc.asRightValue(psCause, psLeft, "2>1");
            if (asM.length() != 0) {
                asOper = asM.substring(0, asM.indexOf("$@$@$@"));
                asM = asM.substring(asM.indexOf("$@$@$@") + 6);
                asValue = asM.substring(0, asM.indexOf("$@$@$@"));
                asValue = " " + asOper + " " + asValue;
                return asValue;
            }
        }
        return "";
    }

    /**
     * ����ʽת��Ϊ��д�����ǽ���ʽ������ֵ�����д�дת����������ָ��''�ڵ�ֵ
     * �����д�дת����
     *
     * <p>ת������
     * <p>1.����ʽ��<code>'</code>�ָ������
     * <p>2.�ж�����ĸ���:�����������,�����;���򲻽���ת��,����ԭ��ʽ
     * <p>3.�������±�Ϊż���Ĳ���ȫ�����д�дת��,�������ֲ�������
     * <p>4.�������ϸ�������Ϊһ�������Ĺ�ʽ
     *
     * @param  formula   ��ʽԭ��
     * @return String    ת����Ĺ�ʽ
     */
    public static String formulaConditionNotUpper(String formula) {
        if (formula == null || formula.length() == 0) {
            return "";
        }

        Object[] parts = formula.split("'");

	StringBuffer sb = new StringBuffer();
        if (parts != null) {
            //���û��', ���߷���ĸ���Ϊż�������򷵻�ԭ��ʽ
            if (parts.length == 1 || parts.length % 2 == 0)
                return formula;

            for (int i = 0, n = parts.length; i < n; i++) {
                if (i % 2 == 0)
                    sb.append(parts[i].toString().toUpperCase());
                else if (i + 1 < n) {
                    sb.append("'");
                    sb.append(parts[i].toString());
                    sb.append("'");
                }
            }
        }
        return sb.toString();
    }


    /**
     * �������ת������ĸ��ʾ���к�
     * @param id int
     * @return String
     */
    public static String getColumnByID(int id) {
        String sValue = "";
        char c1, c2;

        id--;
        try {
            if (id < 26) {
                c1 = (char) (65 + id);
                sValue = String.valueOf(c1);
            } else {
                c1 = (char) ('A' + id / 26 - 1);
                c2 = (char) ('A' + id % 26);
                sValue = String.valueOf(c1) + String.valueOf(c2);
            }
        } catch (Exception ee) {}

        return sValue;
    }

    /**
     * ����ĸ��ʾ���к�ת���������
     * @param column String
     * @return int
     */
    public static int getIdByColumn(String column) {
        int result = 0;
        int carry = 1;
        for (int i = column.length() - 1; i >= 0; i--) {
            result += ( (column.charAt(i) - 65) + 1) * carry;
            carry *= 26;
        }
        return result;
    }

    /**
     * ���غ�ѡ��
     *
     * @return Object[]
     */
    public static String[] getYearCandidate() {
        JParamObject PO = JParamObject.Create();
        String yearCandidate = PO.GetValueByEnvName("RPT_KJND", "");

        if("".equals(yearCandidate)){
          JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "GetKjnd", PO);
          if(RO.ErrorCode == -1){
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, RO.ErrorString);
          }
          yearCandidate = RO.ResponseObject.toString();
	  }


        String[] dataObj = new String[2];
        for (int i = 0; i < dataObj.length; i++) {
            dataObj[i] = String.valueOf(Integer.parseInt(yearCandidate) + i);
        }
        return dataObj;
    }

    /**
     * ����ϵͳ����ڼ�
     *
     * @return Object[]
     */
    public static String[] getAccountantPeriod() {
        int maxInt = JREPORT.getMaxKjqj();

        String[] dataObj = new String[maxInt];
        String tmpString;
        for (int i = 1; i <= maxInt; i++) {
            tmpString = String.valueOf(i);
            if (tmpString.length() == 1)
                tmpString = "0" + tmpString;
            dataObj[i - 1] = tmpString;
        }
        return dataObj;
    }

    /**
     * �����ݿⷽʽ��ȡ�ַ���
     * ��JAVA�����ı�ʾһ���ַ�
     * ��DB�����ı�ʾ�����ַ�
     * @param Text String
     * @param Len int
     * @param Num int
     * @return String[]
     */
    public static String[] SplitStringDB(String Text,int Len,int Num) {
//        System.out.println("Text:" + Text);
//        System.out.println("TextLen:" + Text.length());
//        System.out.println("TextBytesLen:" + Text.getBytes().length);

        // �����������ַ�����ǰ����
        if(Text.length() == Text.getBytes().length){
            return JCommonFunction.SplitString(Text, Len,Num);
        }
        String[] array = new String[Num];
        ArrayList dataList = SplitChinsesString(Text,Len,Num);
        for(int i=0; i<Num; i++){
            if(i < dataList.size()){
                array[i] = dataList.get(i).toString();
            }else{
                array[i] = "";
            }
        }

        return array;
    }

    /**
     * �����������У�map�ȵ��������㣩
     * @param map1 Obejct
     * @param map2 Object
     * @param oper String
     * @return ArrayList
     */
    public static Map CalculateTwoMapObject(Map mp1,Map mp2,String oper){
//      Map map1 = new LinkedHashMap();
//      Map map2 = new LinkedHashMap();
      Map map1 = (LinkedHashMap) mp1;
      Map map2 = (LinkedHashMap) mp2;
      Map map  = new LinkedHashMap();
      for(int i = 10000 ; i > 0 ; i --){
        Random r  = new Random();
        int x = r.nextInt();
        map1.put(x+"",x);
      }
      for(int j = 5000 ; j < 15000 ; j ++){
        Random r  = new Random();
        int x = r.nextInt();
        map2.put(x+"",x);
      }
      long startTime=System.currentTimeMillis();
      Set set1 = map1.keySet();
      Iterator it = set1.iterator();
      while(it.hasNext()){
        String key1 = (String)it.next();
        int value1 =Integer.parseInt( map1.get(key1).toString());
        int value2 = 0 ;
        if(map2.get(key1)!=null){
          value2 = Integer.parseInt( map2.get(key1).toString());
          if("+".equals(oper))
            map.put(key1,value1 + value2);
          else if("-".equals(oper))
            map.put(key1,value1 - value2);
          else if("*".equals(oper))
            map.put(key1,value1 * value2);
          else if("/".equals(oper))
            map.put(key1,value1 / value2);
          map2.remove(key1);
        }else{
          map.put(key1,value1);
        }
      }
      Set set2 = map2.keySet();
      Iterator it2 = set2.iterator();
      while(it2.hasNext()){
        String key2 = (String)it2.next();
        if(map2.get(key2)!=null){
          int value2 = Integer.parseInt(map2.get(key2).toString()) ;
          map.put(key2, value2);
        }
      }
      long endTime=System.currentTimeMillis();   //��ȡ����ʱ��
      System.out.println("ʱ�䣺 "+(endTime-startTime)+"ms");
      Set sett = (TreeSet)map.keySet();
      long s = System.currentTimeMillis();
      String[]   aKeys   =   (String[])(map.keySet()).toArray(new   String[0]);

      java.util.Arrays.sort(aKeys);
//        while(itt.hasNext()){
//            String key = (String)itt.next();
//            if(map.get(key)!=null){
//                System.out.println(key + " " + map.get(key));
//                count ++ ;
//            }
//            if(count == 100)
//                break ;
//        }
      for(int i = 0 ; i < aKeys.length ; i ++){
        //            count ++ ;
        //            if(count> 9000 && count < 10100)
//                System.out.println(aKeys[i] + " " + map.get(aKeys[i]));
        map.get(aKeys[i]);
      }
      long e = System.currentTimeMillis();
      System.out.println("ʱ�䣺 "+(e-s)+"ms");
      return map;
    }

    /**
 * �����ķֽ������
 * @param Text String
 * @param Len int
 * @param Num int
 * @return ArrayList
 */

    public static ArrayList SplitChinsesString(String Text,int Len,int Num) {
        ArrayList dataList = new ArrayList();
        if(Text.length() <= Len){
            dataList.add(Text);
            return dataList;
        }

        char ch;
        int length = Text.length();
        int count = 0;
        int pos   = 0;
        String value,tmp;
        for(int i=0; i<length; i++){
            tmp = Text.substring(i,i+1);
            // ÿ���ַ����㳤��
            count += tmp.getBytes().length;
            //
            if(count == Len){
                value= Text.substring(pos,i + 1);
                dataList.add(value);
                pos   = i + 1;
                count = 0;
            }else if(count > Len){
                value= Text.substring(pos,i);
                dataList.add(value);
                pos   = i;
                count = 2;
            }
        }
        if(count > 0){
            value= Text.substring(pos,length);
            dataList.add(value);
        }

        return dataList;
    }

    /**
     * ���ص�ǰ����ʱ��
     *
     * @return String
     */
    public static String getLocalCurrentTime() {
        String date = DateFunction.getCurrentDate();
        String time = DateFunction.getCurrentTime();
        return DateFunction.formatDate(date) + " " +
            time.substring(0, 2) + ":" +
            time.substring(2, 4) + ":" +
            time.substring(4, 6);
    }

    /**
     * �ж��ַ��Ƿ�������
     * ��Unix�Ͽ���������
     * @param c char
     * @return boolean
     */
    private static boolean isChinese(char c) {
        return (c > 19967 && c < 40869) ? true : false;
    }

    /**
     * �����������У�map�ȵ��������㣩
     * @param map1 Obejct
     * @param map2 Object
     * @param oper String
     * @param num int
     * @return Map
     * @author mf
     */
    public static Map CalculateTwoMapObject(Map map1,Map map2,String oper,String num,int jd){

      Map map  = new LinkedHashMap();
      BigDecimal n1 = new BigDecimal(num);
      long startTime=System.currentTimeMillis();
      if(n1.compareTo(new BigDecimal(0))==0){
          map = CalculateTwoMap(map1,map2,oper,jd);
      }else{
          map = CalculateMapNum(map1,n1,oper,jd);
      }
      long endTime=System.currentTimeMillis();   //��ȡ����ʱ��
      System.out.println("ʱ�䣺 "+(endTime-startTime)+"ms");

      return map;
    }

    public static Map CalculateTwoMap(Map map1,Map map2,String oper,int jd){
         Map map  = new LinkedHashMap();
         Set set1 = map1.keySet();
         Iterator it = set1.iterator();
         if("+".equals(oper)){
              while(it.hasNext()){
                  String key1 = (String)it.next();
                  BigDecimal value1 = new BigDecimal( map1.get(key1).toString());
                  BigDecimal value2 = new BigDecimal(0) ;
                  String s2 = map2.get(key1).toString();
                  if(s2!=null&&!s2.equals("")){
                      value2 = new BigDecimal(s2);
                      map.put(key1,value1.add(value2).setScale(jd).toString());
                      map2.remove(key1);
                  }else{
                      map.put(key1,value1.toString());
                  }
              }
          }else if("-".equals(oper)){
              while(it.hasNext()){
                  String key1 = (String)it.next();
                  BigDecimal value1 = new BigDecimal( map1.get(key1).toString());
                  BigDecimal value2 = new BigDecimal(0) ;
                  String s2 = map2.get(key1).toString();
                  if(s2!=null&&!s2.equals("")){
                      value2 = new BigDecimal(s2);
                      map.put(key1,value1.subtract(value2).setScale(jd).toString());
                      map2.remove(key1);
                  }else{
                      map.put(key1,value1.toString());
                  }
              }
          }else if("*".equals(oper)){
              while(it.hasNext()){
                  String key1 = (String)it.next();
                  BigDecimal value1 = new BigDecimal( map1.get(key1).toString());
                  BigDecimal value2 = new BigDecimal(0) ;
                  String s2 = map2.get(key1).toString();
                  if(s2!=null&&!s2.equals("")){
                      value2 = new BigDecimal(s2);
                      map.put(key1,value1.multiply(value2).setScale(jd,BigDecimal.ROUND_HALF_UP).toString());
                      map2.remove(key1);
                  }else{
                      map.put(key1,value1.toString());
                  }
              }
          }else if("/".equals(oper)){
              while(it.hasNext()){
                  String key1 = (String)it.next();
                  BigDecimal value1 = new BigDecimal( map1.get(key1).toString());
                  BigDecimal value2 = new BigDecimal(0) ;
                  String s2 = map2.get(key1).toString();
                  if(s2!=null&&!s2.equals("")){
                      value2 = new BigDecimal(s2);
                      map.put(key1,value1.divide(value2,jd,BigDecimal.ROUND_HALF_UP).toString());
                      map2.remove(key1);
                  }else{
                      map.put(key1,value1.toString());
                  }
              }
          }
          Set set2 = map2.keySet();
          Iterator it2 = set2.iterator();
          while(it2.hasNext()){
              String key2 = (String)it2.next();
              String s2 = map2.get(key2).toString();
              if(s2!=null&&!s2.equals("")){
                  BigDecimal value2 = new BigDecimal(s2) ;
                  map.put(key2, value2.toString());
              }
          }
        return map;
    }

    public static Map CalculateMapNum(Map map1,BigDecimal n1,String oper,int jd){
        Map map  = new LinkedHashMap();
        Set set1 = map1.keySet();
        Iterator it = set1.iterator();
        if("+".equals(oper)){
            while(it.hasNext()){
                String key1 = (String)it.next();
                BigDecimal value1 =new BigDecimal( map1.get(key1).toString());
                map.put(key1,value1.add(n1).setScale(jd).toString());
            }
        }else if("-".equals(oper)){
            while(it.hasNext()){
                String key1 = (String)it.next();
                BigDecimal value1 =new BigDecimal( map1.get(key1).toString());
                map.put(key1,value1.subtract(n1).setScale(jd).toString());
            }
        }else if("*".equals(oper)){
            while(it.hasNext()){
                String key1 = (String)it.next();
                BigDecimal value1 =new BigDecimal( map1.get(key1).toString());
                map.put(key1,value1.multiply(n1).setScale(jd,BigDecimal.ROUND_HALF_UP).toString());
            }
        }else if("/".equals(oper)){
            while(it.hasNext()){
                String key1 = (String)it.next();
                BigDecimal value1 =new BigDecimal( map1.get(key1).toString());
                map.put(key1,value1.divide(n1,jd,BigDecimal.ROUND_HALF_UP).toString());
            }
        }
        return map;
    }



    /**
      * �ϲ�map����
      * @param map1 Obejct
      * @param map2 Object
      * @return Map
      * @author mf
      */

     public static Map mergeTwoMapObject(Map map1,Map map2){
         Map map  = new LinkedHashMap();
         long startTime=System.currentTimeMillis();
         Set set1 = map1.keySet();
         Set set2 = map2.keySet();
         int size1 =0;
         int size2 =0;
         Iterator it = set1.iterator();
         Iterator it2 = set2.iterator();
          if(it2.hasNext()){
              String key2 = (String)it2.next();
              Vector v2 = (Vector)map2.get(key2);
              size2 = v2.size();
          }

         while(it.hasNext()){
             String key1 = (String)it.next();
             Vector v1 = (Vector)map1.get(key1);
             Vector v2 = (Vector)map2.get(key1);
             size1 = v1.size();
             if(v2!=null){
                // v2 = (Vector)map2.get(key1);
                 for(int i=0;i<size2;i++){
                     v1.add(v2.get(i));
                 }
      //           v1.addAll(v2);
                 map.put(key1,v1);
                 map2.remove(key1);
             }else{
                 for(int i=0;i<size2;i++){
                     v1.add("");
                 }
                 map.put(key1,v1);
             }

         }
         Iterator it3 = set2.iterator();
         while(it3.hasNext()){
             String key2 = (String)it3.next();
              Vector v2 = (Vector)map2.get(key2);
              Vector v = new Vector();
              for(int i=0;i<size1;i++){
                  v.add("");
              }
             if(v2!=null){
                 for(int i=0;i<size2;i++){
                     v.add(v2.get(i));
                 }
                 map.put(key2, v);
             }else{
                 for(int i=0;i<size2;i++){
                     v.add("");
                 }
                 map.put(key2, v);
             }
         }

         long endTime=System.currentTimeMillis();   //��ȡ����ʱ��
         System.out.println("ʱ�䣺 "+(endTime-startTime)+"ms");
         return map;
    }


    /**
  * ��������󣨣�
  * @param obj Obejct
  * @param num int ���յڼ�������
  * @param order String ˳��
  * @param type String ������ֵnֵ����String(c)����
  * @return Vector
  * @author mf
  */

 public static Vector OrderObject(Object obj,int num,String order,String type){
     Vector v = new Vector();
     Vector  vt = new Vector();
      long startTime=System.currentTimeMillis();
      if(obj instanceof Map){
          Map map  =(Map) obj;
          vt = convertMapToVector(map,"n");
          Vector  vn = (Vector)vt.get(0);
         int k = vn.size();
         if(num>k)//��������кŴ�Լ���� ������
             return vt;
         if("n".equals(type)){
             Float[][] s = convertVectorToFArry(vt);
             Comparator mySort = new MyFSort(num-1,order);
             Arrays.sort(s, mySort);
             v= convertFArryToVector(s);
         }else{
             String[][] s = convertVectorToArry(vt);
             Comparator mySort = new MySSort(num-1,order);
             Arrays.sort(s, mySort);
             v= convertArryToVector(s);
         }

     }else if (obj instanceof Vector){
         vt = (Vector) obj;
          Vector  vn = (Vector)vt.get(0);
         int k = vn.size();
         if(num>k)//��������кŴ������� ������
             return vt;
         if("n".equals(type)){
             Float[][] s = convertVectorToFArry(vt);
             Comparator mySort = new MyFSort(num-1,order);
             Arrays.sort(s, mySort);
             v= convertFArryToVector(s);
         }else{
             String[][] s = convertVectorToArry(vt);
             Comparator mySort = new MySSort(num-1,order);
             Arrays.sort(s, mySort);
             v= convertArryToVector(s);

         }


     }
     long endTime=System.currentTimeMillis();   //��ȡ����ʱ��
     System.out.println("ʱ�䣺 "+(endTime-startTime)+"ms");
     return v;
 }

 public static Vector convertMapToVector(Map map,String type){
     Vector v =new Vector();
     int size1 =0;
     Set set = map.keySet();
     Iterator it = set.iterator();
     while(it.hasNext()){
         String key = (String)it.next();
         Vector v1 = (Vector)map.get(key);
         Vector vf = new Vector();
          vf.add(key);
         size1 = v1.size();
         for(int i=0;i<size1;i++){
             vf.add(v1.get(i).toString());
         }
         v.add(vf);
     }
     return v;
 }

 public static String[][] convertVectorToArry(Vector vt){
     try
         {
             int k=vt.size();                //  ȡ������
             System.out.println("########��ѯ��Ŀk="+k);
             if(k<1)
             {   String[][] nul=null;
                 return nul;
             }
             Vector v[]=new Vector[k];
             for(int i=0;i<k;i++)
                 v[i]=(Vector)vt.get(i);

             if(v[0]==null||v[0].size()==0)
             {   String[][] nul2=null;
                 return nul2;
             }
             String[][] arr=new String[k][v[0].size()];
             for(int p=0;p<k;p++)
                 v[p].copyInto(arr[p]);                  //  �� Vector[p] copy ������ arr[p]��

             //******************** null will be replaced with "" *************//
             for(int i=0;i<arr.length;i++)
             {   for(int j=0;j<arr[0].length;j++)
                     if(arr[i][j]==null)
                         arr[i][j]="";
             }
             return arr;
         }
         catch(Exception e)
         {   e.printStackTrace();
         }
     return null;
 }

 public static Float[][] convertVectorToFArry(Vector vt){
     try
         {
             int k=vt.size();                //  ȡ������
             System.out.println("########��ѯ��Ŀk="+k);
             if(k<1)
             {   Float[][] nul=null;
                 return nul;
             }
             Vector v[]=new Vector[k];
             for(int i=0;i<k;i++)
                 v[i]=(Vector)vt.get(i);

             if(v[0]==null||v[0].size()==0)
             {   Float[][] nul2=null;
                 return nul2;
             }
             Float[][] arr=new Float[k][v[0].size()];
             for(int p=0;p<k;p++){
                 for(int s=0;s<v[0].size();s++){
                     arr[p][s]=Float.valueOf(v[p].get(s).toString());
                 }
             }
               //  �� Vector[p] copy ������ arr[p]
             //******************** null will be replaced with "" *************//
//            for(int i=0;i<arr.length;i++)
//            {   for(int j=0;j<arr[0].length;j++)
//                    if(arr[i][j]==0)
//                        arr[i][j]=0;
//            }
             return arr;
         }
         catch(Exception e)
         {   e.printStackTrace();
         }
     return null;
 }


 public static Vector convertArryToVector(String[][] ary){
     Vector v =new Vector();
     int k = ary.length;//����
     int m = ary[0].length;//����
     Vector v1 = new Vector();
     for (int i=0;i<k;i++){
         for(int j=0;j<m;j++){
            v1.add(ary[i][j]);
         }
         v.add(v1);
         v1 = new Vector();
     }
     return v;
 }

 public static Vector convertFArryToVector(Float[][] ary){
     Vector v =new Vector();
     int k = ary.length;//����
     int m = ary[0].length;//����
     Vector v1 = new Vector();
     for (int i=0;i<k;i++){
         for(int j=0;j<m;j++){
            v1.add(ary[i][j].toString());
         }
         v.add(v1);
         v1 = new Vector();
     }
     return v;
 }


 static class MySort implements Comparator<int[]> {//����INT������
     // ��Ҫ�������������
     private int columnNumber;
     // ����ʽ��desc or asc
     private String order;

     public MySort(int columnNumber, String order) {
         this.columnNumber = columnNumber;
         this.order = order;
     }

     public int compare(int a[], int b[]) {
         if ("desc".equals(order)) {
             return b[columnNumber] - a[columnNumber];
         } else {
             return a[columnNumber] - b[columnNumber];
         }

     }
 }

 static class MySSort implements Comparator<String[]> {//����String������
     // ��Ҫ�������������
     private int columnNumber;
     // ����ʽ��desc or asc
     private String order;

     public MySSort(int columnNumber, String order) {
         this.columnNumber = columnNumber;
         this.order = order;
     }

     public int compare(String a[], String b[]) {
         if ("desc".equals(order)) {
             return b[columnNumber].compareTo(a[columnNumber]);
         } else {
             return a[columnNumber].compareTo(b[columnNumber]);
         }

     }
 }


 static class MyFSort implements Comparator<Float[]> {//����float������
     // ��Ҫ�������������
     private int columnNumber;
     // ����ʽ��desc or asc
     private String order;

     public MyFSort(int columnNumber, String order) {
         this.columnNumber = columnNumber;
         this.order = order;
     }

     public int compare(Float a[], Float b[]) {
         if ("desc".equals(order)) {
             return (int)(b[columnNumber] - a[columnNumber]);
         } else {
             return (int)(a[columnNumber] - b[columnNumber]);
         }

     }
}

    public static void main(String args[]){
//        String area = "A4:B5";
//        String colRow = cellRangeToRowCol(area);
//        System.out.println(colRow);

//        String mess = "12345�й�����123�й���";
//        String[] array = SplitStringDB(mess,4,6);
//        for (int i = 0; i < array.length; i++) {
//            System.out.println(array[i]);
//        }
      Map map1 = new LinkedHashMap();
      Map map2 = new LinkedHashMap();
      for(int i = 10000 ; i > 0 ; i --){
        Random r  = new Random();
        int x = r.nextInt();
        map1.put(x+"",x);
      }
      for(int j = 5000 ; j < 15000 ; j ++){
        Random r  = new Random();
        int x = r.nextInt();
        map2.put(x+"",x);
      }
      Map map = CalculateTwoMapObject(map1,map2,"+");
      Set set2 = map2.keySet();
      int count =0;
      Iterator itt = set2.iterator();
              while(itt.hasNext()){
            String key = (String)itt.next();
            if(map.get(key)!=null){
                System.out.println(key + " " + map.get(key));
                count ++ ;
            }
            if(count == 100)
                break ;
        }
    }
	/**
	 * ��鵱ǰʱ���Ƿ��ڲ���ʱ�����
	 * @param as String
	 * @return boolean
	 */
	public static boolean isCurTimeInRange(String timeRange) {
		boolean bRes = false;
		if ( timeRange.equals("") ) {
			return false;
		}
		String[] RangeList = timeRange.split(";");
		String timeBegin = "";
		String timeEnd   = "";
		String sTemp = "";
		String currentYear="",currentMonth="",currentDay="",currentHour="",currentMin="",currentSec="";
		String rangebeginYear="",rangebeginMonth="",rangebeginDay="",rangebeginHour="",rangebeginMin="",rangebeginSec="";
		String rangeendYear="",rangeendMonth="",rangeendDay="",rangeendHour="",rangeendMin="",rangeendSec="";
		JParamObject PO = JParamObject.Create();
		JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("SecurityObject", "getCurrentDateTime", PO);
		if (RO == null)
			return false;
		JParamObject po = new JParamObject(RO.ResponseObject.toString());
		String currentDate = po.GetValueByParamName("CurrentDate", "");
		String currentTime = po.GetValueByParamName("CurrentTime", "");
		if (currentDate.length() >= 8) {
			currentYear = currentDate.substring(0, 4);
			currentMonth = currentDate.substring(4, 6);
			currentDay = currentDate.substring(6, 8);
			currentDate = currentYear  + currentMonth + currentDay;
		}
		if (currentTime.length() >= 6) {
			currentHour = currentTime.substring(0, 2);
			currentMin  = currentTime.substring(2, 4);
			currentSec  = currentTime.substring(4, 6);
			currentTime = currentHour + currentMin  + currentSec;
		}
		for ( int i=0;i<RangeList.length;i++) {
			sTemp = RangeList[i];
			timeBegin = sTemp.substring(0,sTemp.indexOf("~"));
			timeEnd   = sTemp.substring(sTemp.indexOf("~")+1);
			if ( timeBegin.indexOf("��") >0 ) {
				rangebeginYear = timeBegin.substring(0, timeBegin.indexOf("��"));
				if ( rangebeginYear.length() == 2 ) {
					rangebeginYear = "20"+rangebeginYear;
				}
				timeBegin = timeBegin.substring(timeBegin.indexOf("��")+1);
			}
			if ( timeBegin.indexOf("��") >0 ) {
				rangebeginMonth = timeBegin.substring(0, timeBegin.indexOf("��"));
				if ( rangebeginMonth.length() == 1 ) {
					rangebeginMonth = "0"+rangebeginMonth;
				}
				timeBegin = timeBegin.substring(timeBegin.indexOf("��")+1);
			}
			if ( timeBegin.indexOf("��") >0 ) {
				rangebeginDay = timeBegin.substring(0, timeBegin.indexOf("��"));
				if ( rangebeginDay.length() == 1 ) {
					rangebeginDay = "0"+rangebeginDay;
				}
				timeBegin = timeBegin.substring(timeBegin.indexOf("��")+1);
			}
			if ( timeBegin.indexOf("ʱ") >0 ) {
				rangebeginHour = timeBegin.substring(0, timeBegin.indexOf("ʱ"));
				if ( rangebeginHour.length() == 1 ) {
					rangebeginHour = "0"+rangebeginHour;
				}
				timeBegin = timeBegin.substring(timeBegin.indexOf("ʱ")+1);
			}
			if ( timeBegin.indexOf("��") >0 ) {
				rangebeginMin = timeBegin.substring(0, timeBegin.indexOf("��"));
				if ( rangebeginMin.length() == 1 ) {
					rangebeginMin = "0"+rangebeginMin;
				}
				timeBegin = timeBegin.substring(timeBegin.indexOf("��")+1);
			}
			if ( timeBegin.indexOf("��") >0 ) {
				rangebeginSec = timeBegin.substring(0, timeBegin.indexOf("��"));
				if ( rangebeginSec.length() == 1 ) {
					rangebeginSec = "0"+rangebeginSec;
				}
				timeBegin = timeBegin.substring(timeBegin.indexOf("��")+1);
			}
			if ( timeEnd.indexOf("��") >0 ) {
				rangeendYear = timeEnd.substring(0, timeEnd.indexOf("��"));
				if ( rangeendYear.length() == 2 ) {
					rangeendYear = "20"+rangeendYear;
				}
				timeEnd = timeEnd.substring(timeEnd.indexOf("��")+1);
			}
			if ( timeEnd.indexOf("��") >0 ) {
				rangeendMonth = timeEnd.substring(0, timeEnd.indexOf("��"));
				if ( rangeendMonth.length() == 1 ) {
					rangeendMonth = "0"+rangeendMonth;
				}
				timeEnd = timeEnd.substring(timeEnd.indexOf("��")+1);
			}
			if ( timeEnd.indexOf("��") >0 ) {
				rangeendDay = timeEnd.substring(0, timeEnd.indexOf("��"));
				if ( rangeendDay.length() == 1 ) {
					rangeendDay = "0"+rangeendDay;
				}
				timeEnd = timeEnd.substring(timeEnd.indexOf("��")+1);
			}
			if ( timeEnd.indexOf("ʱ") >0 ) {
				rangeendHour = timeEnd.substring(0, timeEnd.indexOf("ʱ"));
				if ( rangeendHour.length() == 1 ) {
					rangeendHour = "0"+rangeendHour;
				}
				timeEnd = timeEnd.substring(timeEnd.indexOf("ʱ")+1);
			}
			if ( timeEnd.indexOf("��") >0 ) {
				rangeendMin = timeEnd.substring(0, timeEnd.indexOf("��"));
				if ( rangeendMin.length() == 1 ) {
					rangeendMin = "0"+rangeendMin;
				}
				timeEnd = timeEnd.substring(timeEnd.indexOf("��")+1);
			}
			if ( timeEnd.indexOf("��") >0 ) {
				rangeendSec = timeEnd.substring(0, timeEnd.indexOf("��"));
				if ( rangeendSec.length() == 1 ) {
					rangeendSec = "0"+rangeendSec;
				}
				timeEnd = timeEnd.substring(timeEnd.indexOf("��")+1);
			}
			if ( rangebeginYear.equals("") ) {
				rangebeginYear = currentYear;
			}
			if ( rangebeginMonth.equals("") ) {
				rangebeginMonth = currentMonth;
			}
			if ( rangebeginDay.equals("") ) {
				rangebeginDay = currentDay;
			}
			if ( rangebeginHour.equals("") ) {
				rangebeginHour = "00";
			}
			if ( rangebeginMin.equals("") ) {
				rangebeginMin = "00";
			}
			if ( rangebeginSec.equals("") ) {
				rangebeginSec = "00";
			}
			if ( rangeendYear.equals("") ) {
				rangeendYear = currentYear;
			}
			if ( rangeendMonth.equals("") ) {
				rangeendMonth = currentMonth;
			}
			if ( rangeendDay.equals("")  ) {
				rangeendDay = currentDay;
			}
			if ( rangeendHour.equals("") ) {
				rangeendHour = "24";
			}
			if ( rangeendMin.equals("") ) {
				rangeendMin = "00";
			}
			if ( rangeendSec.equals("") ) {
				rangeendSec = "00";
			}
                        long now = Long.parseLong(currentDate+currentTime);
                        long begin =Long.parseLong(rangebeginYear+rangebeginMonth+rangebeginDay+rangebeginHour+rangebeginMin+rangebeginSec);
                        long end = Long.parseLong(rangeendYear+rangeendMonth+rangeendDay+rangeendHour+rangeendMin+rangeendSec);

			if ( now >= begin && now <=end ) {
				bRes = true;
				return bRes;
			}
		}
		return bRes;
	}

        public static String GetRealRptSvr() {
                String sSvrName = "";
                Object childwindow=com.efounder.eai.ide.EnterpriseExplorer.ContentView.getActiveWindow();
                if(childwindow instanceof FMISContentWindow) {
                        childwindow = ( (FMISContentWindow) childwindow).getFMISComp();
                        if ( childwindow instanceof JBOFChildWindow ) {
                                sSvrName = ((JBOFChildWindow)childwindow).getPageBaseUrl();
                        }
                }
                if ( !sSvrName.equals("") && sSvrName != null ) {
                        return sSvrName + ".";
                } else {
                        sSvrName = "";
                }
                return sSvrName;
    }
}
