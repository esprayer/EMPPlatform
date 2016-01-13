package jdatareport.dof.classes.report.filter;

import org.nfunk.jep.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TestFunc {
    /**
     *
     */
    public TestFunc() {
    }

    /**
     *
     */
    public void testLike() {
        JEP parser = new JEP(); // Create a new parser
        //like(\"abcdefg\",\"a%ce%g\")
        String expr = "(3 > 2) && (like(\"中国人\",\"中%\"))";
        double value;

        System.out.println("Starting CustFunc...");
        parser.addStandardFunctions();
        parser.addStandardConstants();
        parser.addFunction("like", new FuncLike()); // Add the custom function

        parser.parseExpression(expr); // Parse the expression
        if (parser.hasError()) {
            System.out.println("Error while parsing");
            System.out.println(parser.getErrorInfo());
            return;
        }
        value = parser.getValue(); // Get the value
        if (parser.hasError()) {
            System.out.println("Error during evaluation");
            System.out.println(parser.getErrorInfo());
            return;
        }
        System.out.println(expr + " = " + parser.getValueAsObject()); // Print value
    }

    /**
     *
     */
    public void testSubstring() {
        JEP parser = new JEP(); // Create a new parser
        //like(\"abcdefg\",\"a%ce%g\")
        String expr = "like(substring(\"abcd\",1,2),\"b%\")";
        double value;

        System.out.println("Starting CustFunc...");
        parser.addStandardFunctions();
        parser.addStandardConstants();
        parser.addFunction("like", new FuncLike());
        parser.addFunction("substring", new FuncSubstring()); // Add the custom function

        parser.parseExpression(expr); // Parse the expression
        if (parser.hasError()) {
            System.out.println("Error while parsing");
            System.out.println(parser.getErrorInfo());
            return;
        }
        value = parser.getValue(); // Get the value
        if (parser.hasError()) {
            System.out.println("Error during evaluation");
            System.out.println(parser.getErrorInfo());
            return;
        }
        System.out.println(expr + " = " + parser.getValueAsObject()); // Print value

    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        TestFunc tester = new TestFunc();
        tester.testLike();
        tester.testSubstring();
    }

}