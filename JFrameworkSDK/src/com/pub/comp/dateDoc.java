package com.pub.comp;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
/**
 * <p>Title: 非公用类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

class dateDoc extends PlainDocument
{
	static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.pub.comp.Language");
  private JTextComponent textComponent;
	private int newOffset;

	public dateDoc(JTextComponent tc) {
		textComponent = tc;
		String strCurrentDate=getCurrentDate();
		try{insertString(0, strCurrentDate, null);}catch(Exception ex){}
	}
	public void insertString(int offset, String s,AttributeSet attributeSet) throws BadLocationException
	{
		if (s.length()==1)
		{
			try {Integer.parseInt(s);}catch(Exception ex)
			{
				Toolkit.getDefaultToolkit().beep();
				return;
			}
			newOffset = offset;
			if(offset==4||offset==7){
				newOffset++;
				textComponent.setCaretPosition(newOffset);
			}
			super.remove(newOffset, 1);
			super.insertString(newOffset, s, attributeSet);
		}
		else if(s.length()==10){
			if (!isLegalDate(s))
			{
				Toolkit.getDefaultToolkit().beep();
				return;
			}
			super.remove(0,getLength());
			super.insertString(0, s, attributeSet);
		}
	}
	public void remove(int offset, int length) throws BadLocationException
	{
		if(offset==4||offset==7)
			textComponent.setCaretPosition(offset-1);
		else
			textComponent.setCaretPosition(offset);
	}
	/**
	*耞才strDate琌才砏玥ら戳
	*strDate"YYYY-MM-DD"┪"YYYY/MM/DD"
	*/
	public static boolean isLegalDate(String strDate)
	{
	    int intY,intM,intD;
	    int[]standardDays={31,28,31,30,31,30,31,31,30,31,30,31};
	    int[]leapyearDays={31,29,31,30,31,30,31,31,30,31,30,31};
	    if (strDate==null||strDate.trim().equals("")||strDate.trim().length()!=10)return false;
	    strDate=strDate.trim();
	    try{
	        intY=Integer.parseInt(strDate.substring(0,4));
	        intM=Integer.parseInt(strDate.substring(5,7));
	        intD=Integer.parseInt(strDate.substring(8));
	    }catch(Exception e){return false;}
	    if (intM>12||intM<1||intY<1||intD<1)return false;
	    if ((intY%4==0&&intY%100!=0)||intY%400==0)return (intD<=leapyearDays[intM-1]);
        return (intD<=standardDays[intM-1]);
	}
	/**
	*眔讽玡ら戳"YYYY-MM-DD"才﹃
	*/
	private String getCurrentDate()
	{
	    GregorianCalendar Day=new GregorianCalendar();
	    int yyyy,mm,dd;
	    String MM,DD;
	    yyyy=Day.get(Calendar.YEAR);
	    mm=Day.get(Calendar.MONTH)+1;
	    dd=Day.get(Calendar.DATE);
	    if (mm<10)MM="0"+mm;else MM=Integer.toString(mm);
	    if (dd<10)DD="0"+dd;else DD=Integer.toString(dd);
	    return yyyy+"-"+MM+"-"+DD;
	}
}
