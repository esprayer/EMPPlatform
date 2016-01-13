package com.pub.comp;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ResourceBundle;
/**
 * <p>Title: 非公用类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

class numDoc extends PlainDocument
{
	static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.pub.comp.Language");
  int maxLength=16;
	int decLength=0;
	public numDoc(int decLen,int maxLen)
	{
		maxLength=maxLen;
		decLength=decLen;
	}
	public numDoc(int decLen)
	{
		decLength=decLen;
	}
	public numDoc(){}
	public void insertString(int offset,String s,AttributeSet a)throws BadLocationException
	{
		int len=getLength();
		String str=getText(0,len);
		int decPos=str.indexOf(".");
		//if (
		//s.equals("F")||s.equals("f")||s.equals("D")||s.equals("d")
		//||(str+s).length()>maxLength
		//||(decPos>-1&&offset>decPos&&((str.substring(decPos+1))+s).length()>decLength)
		//||(str.trim().equals("0")&&!s.substring(0,1).equals(".")&&offset!=0)
		//||(s.equals(".")&&decLength==0)
		//||(s.indexOf(".")>-1&&s.substring(s.indexOf(".")+1).length()>decLength)
		//)
		//{
		    //Toolkit.getDefaultToolkit().beep();
		    //return;
		//}
		try{
			if ( offset == 0 )
				Float.parseFloat(s+str);
			else
		    	Float.parseFloat(str+s);
		}catch(Exception e){
		    //Toolkit.getDefaultToolkit().beep();
		    if ( offset == 0 && s.equals("-") ) {
		    } else return;
		}
		super.insertString(offset,s,a);
	}
}
