package jreportwh.jdof.classes.common.util;

import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class BoolUtil
{
  static ResourceBundle res = ResourceBundle.getBundle("jreportwh.jdof.classes.common.util.Language");
    public static int getInt(boolean bool){
        if(bool){
            return 1;
        }else{
            return 0;
        }
    }
    public static char getChar(boolean bool){
        if(bool){
            return '1';
        }else{
            return '0';
        }
    }
    public static String getString(boolean bool){
        if(bool){
            return "1";
        }else{
            return "0";
        }
    }
    public static boolean getBoolean(int i){
        if(i==0){
            return false;
        }else{
            return true;
        }
    }
    public static boolean getBoolean(char ch){
        if(ch=='0'){
            return false;
        }else{
            return true;
        }
    }
    public static boolean getBoolean(String str){
        if (str.equals("0")) {
            return false;
        } else {
            return true;
        }
    }
}
