package com.efounder.dctbuilder.util;

import com.jidesoft.comparator.ComparableComparator;

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
public class JOperatorObject {

    public JOperatorObject() {
    }

    //==
    public boolean CALC(String bjf,String colvalue,String bjvalue){
        if(bjf.equals("=")||bjf.equals("EQ"))
            return EQ(colvalue,bjvalue);
         if(bjf.equals("<>")||bjf.equals("!=")||bjf.equals("NE"))
             return NE(colvalue,bjvalue);
         if (bjf.equals("[]") || bjf.equals("LI"))
             return LI(colvalue, bjvalue);
         if (bjf.equals("][") || bjf.equals("NL"))
             return NL(colvalue, bjvalue);
         if (bjf.equals(">") || bjf.equals("GT"))
             return GT(colvalue, bjvalue);
         if (bjf.equals("<") || bjf.equals("GE"))
             return GE(colvalue, bjvalue);
         if (bjf.equals(">=") || bjf.equals("LT"))
             return LT(colvalue, bjvalue);
         if (bjf.equals("<=") || bjf.equals("LE"))
             return LE(colvalue, bjvalue);
         return true;
    }

    //=
    public boolean EQ(Object colvalue, Object bjvalue) {
        return ComparableComparator.getInstance().compare(colvalue, bjvalue) == 0;
//        return colvalue.equals(bjvalue);
    }

    //<>
    public boolean NE(Object colvalue, Object bjvalue) {
        return ComparableComparator.getInstance().compare(colvalue, bjvalue) != 0;
//        return!colvalue.equals(bjvalue);
    }

    //like
    public boolean LI(String colvalue, String bjvalue) {
        return colvalue.indexOf(bjvalue) != -1;
    }

    //no like
    public boolean NL(String colvalue, String bjvalue) {
        return colvalue.indexOf(bjvalue) == -1;
    }

    //>
    public boolean GT(Object colvalue, Object bjvalue) {
        return ComparableComparator.getInstance().compare(colvalue, bjvalue) > 0;
//        return colvalue.compareTo(bjvalue) > 0;
    }

    //<
    public boolean GE(Object colvalue, Object bjvalue) {
        return ComparableComparator.getInstance().compare(colvalue, bjvalue) < 0;
//        return colvalue.compareTo(bjvalue) < 0;
    }

    public boolean LT(Object colvalue, Object bjvalue) {
        return ComparableComparator.getInstance().compare(colvalue, bjvalue) >= 0;
//        return colvalue.compareTo(bjvalue)>0;
    }

    public boolean LE(Object colvalue, Object bjvalue) {
        return ComparableComparator.getInstance().compare(colvalue, bjvalue) <= 0;
//        return colvalue.compareTo(bjvalue)<0;
    }

}
