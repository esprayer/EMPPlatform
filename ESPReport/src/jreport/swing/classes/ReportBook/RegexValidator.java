package jreport.swing.classes.ReportBook;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * <p>Title: ������ʽ��֤</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: pansoft</p>
 *
 * @author gengeng
 * @version 1.0
 */
public class RegexValidator {
    /** ����������ʽ */
    public String patternDate = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))";

    /**
     * �Ƿ����������ʽ����
     *
     * @param pattern String ������ʽ
     * @param content String ����֤����
     * @return boolean       �������������ʽ�򷵻���
     * @todo Implement this jstandard.bof.incre.validate.ISCValidate method
     */
    public boolean accept(String pattern, String content) {
        Pattern p;
        Matcher m;

        if (pattern == null || content == null)
            return false;

        try {
            p = Pattern.compile(pattern);
            m = p.matcher(content);
            return m.matches();
        } catch (java.util.regex.PatternSyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

}
