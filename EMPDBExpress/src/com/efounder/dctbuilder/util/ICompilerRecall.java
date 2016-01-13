package com.efounder.dctbuilder.util;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface ICompilerRecall {

    public abstract String onFieldRecall(int i, String s);

    public abstract String onFormulaRecall(int i, String s);

    public abstract String onParamRecall(int i, String s, String s1);

    public abstract String onValueRecall(int i, String s);
}
