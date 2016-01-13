package com.etsoft.pub.util;

/**
 * 这个类提供了一些根据类的class文件位置来定位的方法。
 */
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class Path {
	private static Logger log4 = Logger.getLogger(Path.class); // 系统日志处理，用于显示异常操作
	 /**
	  * 获取一个类的class文件所在的绝对路径。 这个类可以是JDK自身的类，也可以是用户自定义的类，或者是第三方开发包里的类。
	  * 只要是在本程序中可以被加载的类，都可以定位到它的class文件的绝对路径。
	  * 
	  * @param cls
	  *            一个对象的Class属性
	  * @return 这个类的class文件位置的绝对路径。 如果没有这个类的定义，则返回null。
	  */
	public static String getPathFromClass(Class cls) throws IOException {
		String path = null;
		if (cls == null) {
			throw new NullPointerException();
		}
		URL url = getClassLocationURL(cls);
		if (url != null) {
			path = url.getPath();
			if ("jar".equalsIgnoreCase(url.getProtocol())) {
				try {
					path = new URL(path).getPath();
				} catch (MalformedURLException e) {
				}
				int location = path.indexOf("!/");
				if (location != -1) {
					path = path.substring(0, location);
				}
			}
			File file = new File(path);
			path = file.getCanonicalPath();
		}
		return path;
	}
	/**
	 * 读取utf文件内容,以列表形式显示
	 * @param utf8File
	 * @return
	 * @throws Exception
	 */
	public static List getReaders(String utf8File) throws Exception{
		FileInputStream fis=new FileInputStream(utf8File);
		// 按照 UTF-8 编码方式将字节流转化为字符流
		InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
		// 从字符流中获取文本并进行缓冲
		BufferedReader br=new BufferedReader(isr);
		// 声明并建立 StringBuffer 变量,用于存储全部文本文件内容
		StringBuffer sbContent=new StringBuffer();
		// 声明 String 变量,用于临时存储文本行内容
		String sLine;
		
		List list = new LinkedList();
		// 循环读取文本文件每行内容
		while((sLine=br.readLine())!=null){
		  // 去掉回车和换行符,去掉文本行前后空格,连接全部文本文件内容
//		  sbContent=sbContent.append(sLine.replace("\n","").replace("\r","").trim());
			list.add(sLine.replace("\n","").replace("\r","").trim());
		}
		// 输出文本文件内容
//		out.print(new String(sbContent));
		
		if(fis!=null)
			fis.close();
		if(isr!=null)
			isr.close();
		return list;
	}
	
	  /**  
     *  删除文件  
     *  @param  filePathAndName  String  文件路径及名称  如c:/fqf.txt  
     *  @param  fileContent  String  
     *  @return  boolean  
     */  
   public static void  delFile(String  filePathAndName)  {  
       try  {  
           String  filePath  =  filePathAndName;  
           filePath  =  filePath.toString();  
           java.io.File  myDelFile  =  new  java.io.File(filePath);  
           myDelFile.delete();  
 
       }  
       catch  (Exception  e)  {  
//           System.out.println("删除文件操作出错");
           log4.info("del file error :"+e.getMessage());
           e.printStackTrace();  
 
       }  
 
   }  
	
	/**
	 * 文件复制
	 * @param path1源文件
	 * @param parth2目标文件
	 * @return
	 */
	public static boolean copyFile(String filePath1, String filePath2){
		  try {
	          File f = new File(filePath1);
	          if (f!=null&&f.isFile() == true) {
	                 int c;
	                 FileInputStream in1 = new FileInputStream(f);
	                 File x = new File(filePath2);// 新文件
	                 FileOutputStream out1 = new FileOutputStream(x);
	                 c = (int) f.length();
	                 byte[] b = new byte[c];
	                 /** 以下4行是“全部读入内存进行复制”方式 */
	                 in1.read(b);
	                 in1.close();
	                 out1.write(b);
	                 out1.close();
	                 /*
						 * 
						 * //单字节复制 for (int i = 0; i < f.length(); i++) { b[i] =
						 * (byte)
						 * in1.read(); } for (int i = 0; i < f.length(); i++) {
						 * out1.write(b[i]); }
						 * 
						 */
	                 x = null;
	          } else {
	                 log4.info(filePath1 + " : 文件不存在或不能读取！");
	                 return false;
	          }
	          f = null;
	   } catch (Exception e1) {
	          log4.info(filePath1 + " : 复制过程出现异常！");
	          e1.printStackTrace();
	          return false;
	
	   }
	   return true;
	
	}
	
	/**  
     *  删除文件夹  
     *  @param  filePathAndName  String  文件夹路径及名称  如c:/fqf  
     *  @param  fileContent  String  
     *  @return  boolean  
     */  
   public static void  delFolder(String  folderPath)  {  
       try  {  
           delAllFile(folderPath);  //删除完里面所有内容  
           String  filePath  =  folderPath;  
           filePath  =  filePath.toString();  
           java.io.File  myFilePath  =  new  java.io.File(filePath);  
           myFilePath.delete();  //删除空文件夹  
 
       }  
       catch  (Exception  e)  {  
//           System.out.println("删除文件夹操作出错");  
           log4.error("del fold error :"+e.getMessage());
           e.printStackTrace();  
 
       }  
 
   }  
 
   /**  
     *  删除文件夹里面的所有文件  
     *  @param  path  String  文件夹路径  如  c:/fqf  
     */  
   public static void  delAllFile(String  path)  {  
       File  file  =  new  File(path);  
       if  (!file.exists())  {  
           return;  
       }  
       if  (!file.isDirectory())  {  
           return;  
       }  
       String[]  tempList  =  file.list();  
       File  temp  =  null;  
       for  (int  i  =  0;  i  <  tempList.length;  i++)  {  
           if  (path.endsWith(File.separator))  {  
               temp  =  new  File(path  +  tempList[i]);  
           }  
           else  {  
               temp  =  new  File(path  +  File.separator  +  tempList[i]);  
           }  
           if  (temp.isFile())  {  
               temp.delete();  
           }  
           if  (temp.isDirectory())  {  
               delAllFile(path+"/"+  tempList[i]);//先删除文件夹里面的文件  
               delFolder(path+"/"+  tempList[i]);//再删除空文件夹  
           }  
       }  
   }  
// 
//   /**  
//     *  复制单个文件  
//     *  @param  oldPath  String  原文件路径  如：c:/fqf.txt  
//     *  @param  newPath  String  复制后路径  如：f:/fqf.txt  
//     *  @return  boolean  
//     */  
//   public  void  copyFile(String  oldPath,  String  newPath)  {  
//       try  {  
//           int  bytesum  =  0;  
//           int  byteread  =  0;  
//           File  oldfile  =  new  File(oldPath);  
//           if  (oldfile.exists())  {  //文件存在时  
//               InputStream  inStream  =  new  FileInputStream(oldPath);  //读入原文件  
//               FileOutputStream  fs  =  new  FileOutputStream(newPath);  
//               byte[]  buffer  =  new  byte[1444];  
//               int  length;  
//               while  (  (byteread  =  inStream.read(buffer))  !=  -1)  {  
//                   bytesum  +=  byteread;  //字节数  文件大小  
//                   System.out.println(bytesum);  
//                   fs.write(buffer,  0,  byteread);  
//               }  
//               inStream.close();  
//           }  
//       }  
//       catch  (Exception  e)  {  
//           System.out.println("复制单个文件操作出错");  
//           e.printStackTrace();  
// 
//       }  
// 
//   }  
 
   /**  
     *  复制整个文件夹内容  
     *  @param  oldPath  String  原文件路径  如：c:/fqf  
     *  @param  newPath  String  复制后路径  如：f:/fqf/ff  
     *  @return  boolean  
     */  
   public static void  copyFolder(String  oldPath,  String  newPath)  {  
 
       try  {  
           (new  File(newPath)).mkdirs();  //如果文件夹不存在  则建立新文件夹  
           File  a=new  File(oldPath);  
           String[]  file=a.list();  
           File  temp=null;  
           for  (int  i  =  0;  i  <  file.length;  i++)  {  
               if(oldPath.endsWith(File.separator)){  
                   temp=new  File(oldPath+file[i]);  
               }  
               else{  
                   temp=new  File(oldPath+File.separator+file[i]);  
               }  
 
               if(temp.isFile()){  
                   FileInputStream  input  =  new  FileInputStream(temp);  
                   FileOutputStream  output  =  new  FileOutputStream(newPath  +  "/"  +  
                           (temp.getName()).toString());  
                   byte[]  b  =  new  byte[1024  *  5];  
                   int  len;  
                   while  (  (len  =  input.read(b))  !=  -1)  {  
                       output.write(b,  0,  len);  
                   }  
                   output.flush();  
                   output.close();  
                   input.close();  
               }  
               if(temp.isDirectory()){//如果是子文件夹  
                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);  
               }  
           }  
       }  
       catch  (Exception  e)  {  
//           System.out.println("复制整个文件夹内容操作出错");  
    	   log4.info("copy full fold error "+e.getMessage());
           e.printStackTrace();  
 
       }  
 
   }  
 
   /**  
    *  移动文件到指定目录  
    *  @param  oldPath  String  如：c:/fqf.txt  
    *  @param  newPath  String  如：d:/fqf.txt  
    */  
  public static  void  moveFolder(String  oldPath,  String  newPath)  {  
      copyFolder(oldPath,  newPath);  
      delFolder(oldPath);  

  }  

	   /**  
	     *  移动文件到指定目录  
	     *  @param  oldPath  String  如：c:/fqf.txt  
	     *  @param  newPath  String  如：d:/fqf.txt  
	     */  
	   public static void  moveFile(String  oldPath,  String  newPath)  {  
	       copyFile(oldPath,  newPath);  
	       delFile(oldPath);  
	 
	   } 
	
	/**
	 * 文件复制
	 * @param file 源文件
	 * @param path 目标文件
	 * @return
	 */
	public static boolean copyFile(File file, String filePath){
//		 log4.info("============copy path1="+filePath);
		try {
	         
	          if (file!=null&&file.isFile() == true) {
	                 int c;
	                 FileInputStream in1 = new FileInputStream(file);
	                 File x = new File(filePath);// 新文件
//	                 log4.info("============copy path2="+filePath);
	                 FileOutputStream out1 = new FileOutputStream(x);
	                 c = (int) file.length();
	                 byte[] b = new byte[c];
	                 /** 以下4行是“全部读入内存进行复制”方式 */
	                 in1.read(b);
	                 in1.close();
	                 out1.write(b);
	                 out1.close();
	                 /*
						 * 
						 * //单字节复制 for (int i = 0; i < f.length(); i++) { b[i] =
						 * (byte)
						 * in1.read(); } for (int i = 0; i < f.length(); i++) {
						 * out1.write(b[i]); }
						 * 
						 */
	          } else {
	                 log4.info(file.getAbsolutePath() + " : 文件不存在或不能读取！");
	                 return false;
	          }
	          
	   } catch (Exception e1) {
	          log4.info(file.getAbsolutePath() + " : 复制过程出现异常！");
	          e1.printStackTrace();
	          return false;
	
	   }
		return true;
	}
	
	/**
	 * 如果文件不存在自动创建
	 * @param paths=D:\\test\\test1\\test2\\test3
	 */
	public static void initPath(String paths){
		
		paths = paths.replaceAll("\\\\", "/");
//		paths = paths.replaceAll("//", "/");
//		log4.info("update photo paths="+paths);
//		File f = null;
//		String temp = "";
//		try{		
//			while(paths.indexOf(File.separator)>0){
//
//				temp = temp +File.separator+ paths.substring(0, paths.indexOf(File.separator));
//				paths = paths.substring(paths.indexOf(File.separator)+1);
////				log4.info("temp="+temp.substring(1)+"  path="+paths);
//				f = new File(temp.substring(1));
//				if(!f.exists()){					
//					f.mkdirs();					
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		log4.info("init photo paths="+paths+" separator="+File.separator+" index="+paths.indexOf(File.separator));
		File f = null;
		String temp = "";
		try{		
			while(paths.indexOf("/")>=0){
				temp = temp +"/"+ paths.substring(0, paths.indexOf("/"));
				paths = paths.substring(paths.indexOf("/")+1);
//				System.out.println("temp="+temp.substring(1)+"  path="+paths);
				f = new File(temp.substring(1));
				if(!f.exists()){					
					f.mkdirs();					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
//		f.exists();
//		f.createNewFile();
//		while (paths.)
		
	}
	
	
	/**
	 * web路径处理,如D:/services/tomcat/webapps/ess/
	 * @return绝对路径
	 */
	public static String getWebPath() {
		String paths = null;
		String tempPath = null;
		try {
			tempPath = Path.getPathFromClass(Path.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		paths = tempPath.substring(0, tempPath.indexOf("WEB-INF"));
		return paths;
	}
	/**
	  * 这个方法可以通过与某个类的class文件的相对路径来获取文件或目录的绝对路径。 通常在程序中很难定位某个相对路径，特别是在B/S应用中。
	  * 通过这个方法，我们可以根据我们程序自身的类文件的位置来定位某个相对路径。
	  * 比如：某个txt文件相对于程序的Test类文件的路径是../../resource/test.txt，
	  * 那么使用本方法Path.getFullPathRelateClass("../../resource/test.txt",Test.class)
	  * 得到的结果是txt文件的在系统中的绝对路径。
	  * 
	  * @param relatedPath
	  *            相对路径
	  * @param cls
	  *            用来定位的类
	  * @return 相对路径所对应的绝对路径
	  * @throws IOException
	  *             因为本方法将查询文件系统，所以可能抛出IO异常
	  */
	public static String getFullPathRelateClass(String relatedPath, Class cls)
			throws IOException {
		String path = null;
		if (relatedPath == null) {
			throw new NullPointerException();
		}
		String clsPath = getPathFromClass(cls);
		File clsFile = new File(clsPath);
		String tempPath = clsFile.getParent() + File.separator + relatedPath;
		File file = new File(tempPath);
		path = file.getCanonicalPath();
		return path;
	}
	
	/**
	  * 获取类的class文件位置的URL。这个方法是本类最基础的方法，供其它方法调用。
	  */
	private static URL getClassLocationURL(final Class cls) {
		if (cls == null)
			throw new IllegalArgumentException("null input: cls");
		URL result = null;
		final String clsAsResource = cls.getName().replace('.', '/').concat(
				".class");
		final ProtectionDomain pd = cls.getProtectionDomain();
		// java.lang.Class contract does not specify
		// if 'pd' can ever be null;
		// it is not the case for Sun's implementations,
		// but guard against null
		// just in case:
		if (pd != null) {
			final CodeSource cs = pd.getCodeSource();
			// 'cs' can be null depending on
			// the classloader behavior:
			if (cs != null)
				result = cs.getLocation();

			if (result != null) {
				// Convert a code source location into
				// a full class file location
				// for some common cases:
				if ("file".equals(result.getProtocol())) {
					try {
						if (result.toExternalForm().endsWith(".jar")
								|| result.toExternalForm().endsWith(".zip"))
							result = new URL("jar:".concat(
									result.toExternalForm()).concat("!/")
									.concat(clsAsResource));
						else if (new File(result.getFile()).isDirectory())
							result = new URL(result, clsAsResource);
					} catch (MalformedURLException ignore) {
					}
				}
			}
		}

		if (result == null) {
			// Try to find 'cls' definition as a resource;
			// this is not
			// document��d to be legal, but Sun's
			// implementations seem to //allow this:
			final ClassLoader clsLoader = cls.getClassLoader();
			result = clsLoader != null ? clsLoader.getResource(clsAsResource)
					: ClassLoader.getSystemResource(clsAsResource);
		}
		return result;
	}
	
	public static Date getCreateTime(String path) {
		String datestr = null;
		Date date = null;
		try {
			Process p = Runtime.getRuntime()
					.exec("cmd /C dir " + path + " /tc");
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuffer sb = new StringBuffer();
			String str;
			int i = 0;
			while ((str = br.readLine()) != null) {
				i++;
				if (i == 6) {
					// System.out.println(str.substring(0,17));
					datestr = str.substring(0, 17);
				}
			}
//			System.out.println("==============datestr="+datestr);
			date = CommMethod.convertStrToDate(datestr, "yyyy-MM-dd hh:mm");
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		}
		return date;
	}

	/**
	 * 取路径
	 * @param path
	 * @return
	 */
	public static String getFilePath(String path){
		String value = null;
		if(path!=null){
			value = path.substring(0, path.lastIndexOf("/")+1);
		}
		return value;
	}
	
	/**
	 * 取文件名
	 * @param path
	 * @return
	 */
	public static String getFileName(String path){
		String value = null;
		if(path!=null){
//			String temp = getFilePath(path);
//			System.out.println("temp="+temp);
			value = path.substring(getFilePath(path).length());			
		}
		return value;
	}
	
	/*
	 * 通配符匹配 patten 通配符模式 str 待匹配的字符串 return 匹配成功则返回true,否则返回false
	 */
	public static boolean wildcardMatch(String patten, String str) {
		int pattenLength = patten.length();
		int strLength = str.length();
		int strIndex = 0;
		char ch;
		for (int pattenIndex = 0; pattenIndex < pattenLength; pattenIndex++) {
			ch = patten.charAt(pattenIndex);
			if (ch == '*') {
				// 通配符*可以匹配任意多个字符
				while (strIndex < strLength) {
					if (wildcardMatch(patten.substring(pattenIndex + 1), str
							.substring(strIndex))) {
						return true;
					}
					strIndex++;
				}

			} else if (ch == '?') {
				// //通配符*可以匹配任意一个字符
				strIndex++;
				if (strIndex > strLength) {
					// 表示str中已经没有字符匹配“？“了
					return false;
				}
			} else {
				if (strIndex >= strLength || ch != str.charAt(strIndex)) {
					return false;
				}
				strIndex++;
			}
		}
		return (strIndex == strLength);
	}

	public static void main(String[] args) {
//		try {
//			String tempPath = getPathFromClass(Path.class);
//			log4.info(tempPath.substring(0, tempPath
//					.indexOf("WEB-INF")));
//			log4.info(getPathFromClass(Path.class));
//			log4.info(getFullPathRelateClass("../test/abc/..",
//					Path.class));
//			Path.initPath(null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		String path="/usr/local/test.txt";
		path="E:/services/db_backup/sysFiles/test.txt";
//		System.out.println("filePath="+getFilePath(path));
//		System.out.println("fileName="+getFileName(path));
		try{
			List list = getReaders(path);
			if(list!=null){
				String value = null;
				for(int i=0; i<list.size(); i++){
					value = (String)list.get(i);
					System.out.println("value="+value);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
}
