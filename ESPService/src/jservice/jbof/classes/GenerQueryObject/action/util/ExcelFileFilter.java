package jservice.jbof.classes.GenerQueryObject.action.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *   因为我们需要创建一个文件的过滤器，以便让文件对话框显示我们指定的文件。这里我们就以Excel2003 2007文件举例。

 *   那么要做到这点我们就需要重写FileFilter 类的accept来设置相关的过滤器。这个继承类你可以写成外部类、内部类甚至是

 *   匿名内部类。我这里写成了一个外部类的形式。

 * @author gj
 * @version 1.0
 */

public class ExcelFileFilter extends FileFilter {

	String ext = "";
	/**
	 * 构造函数
	 * @param ext
	 */
	public ExcelFileFilter(String ext) {

	       this.ext = ext;
	   }
	public ExcelFileFilter() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 这个方法就是重写 FileFilter 类的方法，参数是File对象，一般这个参数是如何传入的我们可以不用关心。
     * 他返回一个布尔型。如果为真表示该文件符合过滤设置，那么就会显示在当前目录下，如果为假就会被过滤掉.
	 */
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		 //首先判断该目录下的某个文件是否是目录，如果是目录则返回true，即可以显示在目录下。

	       if (file.isDirectory())
	       {
	        return true;
	       }
	       
	       String fileName = file.getName();
	       int index = fileName.lastIndexOf('.');

	       if (index > 0 && index < fileName.length() - 1)
	       {
	        String extension = fileName.substring(index + 1).toLowerCase();
	        if (extension.equals(ext))
	         return true;
	       }
		  return false;
	}
   /**
    * 这个方法也是重写FileFilter的方法，作用是在过滤名那里显示出相关的信息。
    * 这个与我们过滤的文件类型想匹配，通过这些信息，可以让用户更清晰的明白需要过滤什么类型的文件。
    */
	public String getDescription() {
		// TODO Auto-generated method stub
		if(this.ext.equals("Excle 97-2003 工作薄(*.xls)")){
			return "Excle 97-2003 工作薄(*.xls)";
		}
		if(this.ext.equals("Excle 2007 工作薄(*.xlsx)")){
			return "Excle 2007 工作薄(*.xlsx)";
		}
		if(this.ext.equals("XML文件(大数据量Excle打开)(*.xml)")){
			return "XML文件(大数据量Excle打开)(*.xml)";
		}
		return "";
	}
}
