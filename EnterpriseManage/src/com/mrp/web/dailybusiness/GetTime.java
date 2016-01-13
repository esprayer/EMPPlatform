package com.mrp.web.dailybusiness;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class GetTime {

	public static void main(String[] args) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SSS毫秒"); //yyyyMMdd
//			Calendar ca = Calendar.getInstance();
//			  Date d = ca.getTime();
//			  System.out.println(sdf.format(d));
//			  long l = ca.getTimeInMillis();
//			  System.out.println(l);
//			  //ca.setTime(d);
//			  ca.setTimeInMillis(l+2);
//			  d = ca.getTime();
//			  System.out.println(sdf.format(d));
		    
			  Date dd = new Date();
			  long ld = dd.getTime();
			  System.out.println(dd.getTime());
			  System.out.println(sdf.format(dd));
//			  dd.setTime(ld);
			  dd = new Date(ld+2);
			  System.out.println(dd.getTime());
			  System.out.println(sdf.format(dd));
			  setSysClipboardText("sdafsfdsa");
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将当前时间转换为格林时间
	 * 
	 * @return greenWich
	 */
	public static String getGreenWich() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long mTime = System.currentTimeMillis();
		// 得到一个日历使用的默认时区和场所。时区。
		int offset = Calendar.getInstance().getTimeZone().getRawOffset();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(mTime - offset));
		String greenWich = sdf.format(c.getTime());
		return greenWich;
	}

	/**
	 * 将格林时间转换为当前时间
	 * 
	 * 
	 */
	public static void changeDate(String var) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(var);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		GregorianCalendar ca = new GregorianCalendar(
				TimeZone.getTimeZone("GMT 00:00"));
		ca.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		format.setTimeZone(TimeZone.getDefault());
		String localDate = format.format(ca.getTime());

		System.out.println("格林时间：" + var+"    "+ "本地时间：" + localDate);
	}
	
	
	/**
	 * 从剪切板获得文字。
	 */
	public static String getSysClipboardText() {
		String ret = "";
		Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
		// 获取剪切板中的内容
		Transferable clipTf = sysClip.getContents(null);

		if (clipTf != null) {
			// 检查内容是否是文本类型
			if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				try {
					ret = (String) clipTf
							.getTransferData(DataFlavor.stringFlavor);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return ret;
	}

	/**
	 * 将字符串复制到剪切板。
	 */
	public static void setSysClipboardText(String writeMe) {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable tText = new StringSelection(writeMe);
		clip.setContents(tText, null);
	}

	/**
	 * 从剪切板获得图片。
	 */
	public static Image getImageFromClipboard() throws Exception {
		Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable cc = sysc.getContents(null);
		if (cc == null)
			return null;
		else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
			return (Image) cc.getTransferData(DataFlavor.imageFlavor);
		return null;
	}

	/**
	 * 复制图片到剪切板。
	 */
	public static void setClipboardImage(final Image image) {
		Transferable trans = new Transferable() {
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.imageFlavor };
			}

			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return DataFlavor.imageFlavor.equals(flavor);
			}

			public Object getTransferData(DataFlavor flavor)
					throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor))
					return image;
				throw new UnsupportedFlavorException(flavor);
			}

		};
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans,
				null);
	}

}
