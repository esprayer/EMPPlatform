package com.efounder.dctbuilder.util;
import java.util.Properties;
import java.util.StringTokenizer;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TStringCompiler {
  public static String CompilerString(String Sql2Compile, int iCompileCode, ICompilerRecall IRecall)
   {
       StringTokenizer tl = new StringTokenizer(Sql2Compile, "@");
       StringBuffer RES = new StringBuffer("");
       while(tl.hasMoreTokens())
       {
           String strEl = tl.nextToken();
           if(strEl != null && strEl.length() >= 0)
           {
               StringBuffer strNotFindWord = new StringBuffer();
               strNotFindWord.append("@");
               strNotFindWord.append(strEl);
               strNotFindWord.append("@");
               String strWord = strNotFindWord.toString();
               if(strEl.charAt(0) == ':' && strEl.length() > 1)
               {
                   strEl = strEl.substring(1);
                   String strProVal = IRecall.onValueRecall(iCompileCode, strEl);
                   if(strProVal == null)
                       RES.append(strWord);
                   else
                       RES.append(strProVal);
               } else
               if(strEl.charAt(0) == '=' && strEl.length() > 1)
               {
                   strEl = strEl.substring(1);
                   String strProVal = IRecall.onFormulaRecall(iCompileCode, strEl);
                   if(strProVal == null)
                       RES.append(strWord);
                   else
                       RES.append(strProVal);
               } else
               if(strEl.charAt(0) == '%' && strEl.length() > 1)
               {
                   strEl = strEl.substring(1);
                   int tmpPOS = strEl.indexOf(':');
                   if(tmpPOS > 0 && tmpPOS != strEl.length())
                   {
                       String strKey = strEl.substring(0, tmpPOS);
                       String strVal = strEl.substring(tmpPOS + 1, strEl.length());
                       String Keys[] = {
                           strKey, strVal
                       };
                       String strProVal = IRecall.onParamRecall(iCompileCode, Keys[0], Keys[1]);
                       if(strProVal == null)
                           RES.append(strWord);
                       else
                           RES.append(strProVal);
                   } else
                   {
                       RES.append(strWord);
                   }
               } else
               if(strEl.charAt(0) == '#' && strEl.length() > 1)
               {
                   strEl = strEl.substring(1);
                   String strProVal = IRecall.onFieldRecall(iCompileCode, strEl);
                   if(strProVal == null)
                       RES.append(strWord);
                   else
                       RES.append(strProVal);
               } else
               {
                   RES.append(strEl);
               }
           }
       }
       return RES.toString();
   }

   public static String CompilerString(String Sql2Compile, Properties pValues)
   {
       Properties Pro = pValues;
       StringTokenizer tl = new StringTokenizer(Sql2Compile, "@");
       StringBuffer RES = new StringBuffer("");
       while(tl.hasMoreTokens())
       {
           String strEl = tl.nextToken();
           if(strEl != null && strEl.length() >= 0)
           {
               StringBuffer strNotFindWord = new StringBuffer();
               strNotFindWord.append("@");
               strNotFindWord.append(strEl);
               strNotFindWord.append("@");
               String strWord = strNotFindWord.toString();
               if(strEl.charAt(0) == ':' && strEl.length() > 1)
               {
                   strEl = strEl.substring(1);
                   Object strProVal = Pro.get(strEl);
                   if(strProVal == null)
                       RES.append(strWord);
                   else
                       RES.append((String)strProVal);
               } else
               {
                   RES.append(strEl);
               }
           }
       }
       return RES.toString();
   }

   public static String CompilerString(String Sql2Compile, String strValueString)
   {
       Properties Pro = new Properties();
       for(StringTokenizer TKVal = new StringTokenizer(strValueString, ";"); TKVal.hasMoreTokens();)
       {
           String strEl = TKVal.nextToken();
           if(strEl != null && strEl.length() >= 0)
           {
               int tmpPOS = strEl.indexOf(':');
               if(tmpPOS > 0 && tmpPOS != strEl.length())
               {
                   String strKey = strEl.substring(0, tmpPOS);
                   String strVal = strEl.substring(tmpPOS + 1, strEl.length());
                   Pro.setProperty(strKey, strVal);
               }
           }
       }

       return CompilerString(Sql2Compile, Pro);
   }

   public static Properties CreateCodeTable(String strValueString)
   {
       Properties Pro = new Properties();
       for(StringTokenizer TKVal = new StringTokenizer(strValueString, ";"); TKVal.hasMoreTokens();)
       {
           String strEl = TKVal.nextToken();
           if(strEl != null && strEl.length() >= 0)
           {
               int tmpPOS = strEl.indexOf(':');
               if(tmpPOS > 0 && tmpPOS != strEl.length())
               {
                   String strKey = strEl.substring(0, tmpPOS);
                   String strVal = strEl.substring(tmpPOS + 1, strEl.length());
                   Pro.setProperty(strKey, strVal);
               }
           }
       }

       return Pro;
   }
}
