package jdatareport.dof.classes.report.filter;

import java.util.*;

import org.nfunk.jep.*;
import org.nfunk.jep.function.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class FuncLike
    extends PostfixMathCommand {
    /**
     *
     */
    public FuncLike() {
        this.numberOfParameters = 2;
    }

    /**
     *
     * @param inStack
     * @throws ParseException
     */
    public void run(Stack inStack) throws ParseException {

        // check the stack
        checkStack(inStack);

        // get the parameter from the stack
        Object p0 = inStack.pop();
        Object p1 = inStack.pop();

        // check whether the argument is of the right type
        if (p0 instanceof String && p1 instanceof String) {
            boolean isLike = like( (String) p1, (String) p0);
            if (isLike) {
                inStack.push(new Double(1));
            }
            else {
                inStack.push(new Double(0));
            }
        }
        else {
            throw new ParseException("Invalid parameter type");
        }
    }

    /**
     * like(abc,a%)
     * @param param0
     * @param param1
     * @return
     */
    private boolean like(String p0, String p1) {
        try {
            String strSrc = p0.trim();
            String strTag = p1.trim();
            int firstIndex = strTag.indexOf("%");
            int lastIndex = strTag.lastIndexOf("%");

//            String[] tags = strTag.split("%");
//            for (int i = 0; i < tags.length; i++) {
//                String tag = tags[i];
//                int index = strSrc.indexOf(tag);
//                if (index == -1) {
//                    return false;
//                }
//                strSrc.substring(index);
//            }
            if(strTag.startsWith("%")&&strTag.endsWith("%")){
                String[] tags = strTag.split("%");
                for (int i = 0; i < tags.length; i++) {
                    String tag = tags[i];
                    int index = strSrc.indexOf(tag);
                    if (index == -1) {
                        return false;
                    }
                    strSrc.substring(index);
                }
            }else if(strTag.startsWith("%")&&strTag.length()>1){
                return strSrc.endsWith(strTag.substring(1));
            }else if(strTag.endsWith("%")&&strTag.length()>1){
                return strSrc.startsWith(strTag.substring(0,strTag.length()-1));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}