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

public class FuncSubstring
    extends PostfixMathCommand {
    /**
     *
     */
    public FuncSubstring() {
        this.numberOfParameters = 3;
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
        Object p2 = inStack.pop();
        Object p1 = inStack.pop();
        Object p0 = inStack.pop();

        // check whether the argument is of the right type
        if (p0 instanceof String && p1 instanceof Double && p2 instanceof Double) {
            inStack.push(substring(p0, p1, p2));
        }
        else {
            throw new ParseException("Invalid parameter type");
        }
    }

    /**
     *
     * @param p0
     * @param startIndex
     * @param length
     * @return
     */
    private String substring(Object p0, Object p1, Object p2) {
        try {
            String value = (String) p0;
            int startIndex = ( (Double) p1).intValue()-1;
            int length = ( (Double) p2).intValue();
            int endIndex = startIndex + length;
            return value.substring(startIndex, endIndex);
        }
        catch (Exception e) {
            return (String) p0;
        }
    }
}