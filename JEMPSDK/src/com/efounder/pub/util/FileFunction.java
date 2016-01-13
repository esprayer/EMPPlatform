package com.efounder.pub.util;

/**
 * FileFunction.java
 * 定义文件操作的一些常用方法的类。
 * 包括：新建、打开、保存、关闭、追加、插入、删除、读取、清空
 * @author  卢波
 * @version 1.0, 04/02/01
 */
import java.io.*;

public class FileFunction {

  public FileFunction() {

  }
  /**
   * 新建一个磁盘文件
   * @param     File dir,String name      文件目录，文件名
   * @param     String path                文件目录
   * @param     String path,String name    文件目录，文件名
   * @return    String fileName            文件名
   */
  public static String createNewFile(File dir, String name) {
	File file = new File(dir, name);
	return file.getName();
  }
  public static String createNewFile(String path) {
	File file = new File(path);
	return file.getName();
  }
  public static String createNewFile(String path, String name) {
	File file = new File(path, name);
	return file.getName();
  }
  /**
   * 删除一个磁盘文件
   * @param     File dir,String name      文件目录，文件名
   * @param     String path                文件目录
   * @param     String path,String name    文件目录，文件名
   * @return    boolean
   */
  public static boolean deleteFile(File dir, String name) {
	File file = new File(dir, name);
	if (file.exists())
	  return file.delete();
	return true;
  }
  public static boolean deleteFile(String path) {
	File file = new File(path);
	if (file.exists())
	  return file.delete();
	return true;
  }
  public static boolean deleteFile(String path, String name) {
	File file = new File(path, name);
	if (file.exists())
	  return file.delete();
	return true;
  }
  /**
   * 判断一个磁盘文件是否存在
   * @param      File dir,String name      文件目录，文件名
   * @param      String path                文件目录
   * @param      String path,String name    文件目录，文件名
   * @return     boolean
   */
  public static boolean isExistFile(File dir, String name) {
	File file = new File(dir, name);
	return file.exists();
  }
  public static boolean isExistFile(String path) {
	File file = new File(path);
	return file.exists();
  }
  public static boolean isExistFile(String path, String name) {
	File file = new File(path, name);
	return file.exists();
  }
  /**
   * 从文件中读取
   * @param     String fileName      文件名
   * @return    String record        结果字符窜
   */
  public static String readFromFile(String fileName) {
	if (!isExistFile(fileName))
	  return null;
	String record = null;
	int recCount = 0;
	try {
	  FileReader fr = new FileReader(fileName);
	  BufferedReader br = new BufferedReader(fr);
	  record = new String();
	  record = br.readLine();
	  br.close();
	  fr.close();
	} catch (IOException e) {
	  //System.out.println("对不起，IOException错误!");
	  e.printStackTrace();
	}
	return record;
  }
  public static String readInput(String fileName, String encoding) throws IOException {

	StringBuffer buffer = new StringBuffer();
	try {
	  FileInputStream fis = new FileInputStream(fileName);
	  InputStreamReader isr = new InputStreamReader(fis, encoding);
	  Reader in = new BufferedReader(isr);
	  int ch;
	  while ((ch = in.read()) > -1) {
		buffer.append((char) ch);
	  }
	  in.close();
	  return buffer.toString();
	} catch (IOException e) {
	  e.printStackTrace();
	  throw e;
	}
  }
  public static void writeIntoFile(String str, String fileName) {

	String str_ = str;
	String fileName_ = fileName;

	try {
	  if (!isExistFile(fileName_))
		fileName_ = createNewFile(fileName_);

	  FileWriter fw = new FileWriter(fileName_);
	  PrintWriter out = new PrintWriter(fw);
	  out.print(str_);
	  out.close();
	  fw.close();

	} catch (IOException e) {
	  //System.out.println("对不起，IOException错误!");
	  e.printStackTrace();
	}
  }
  /**
   * 向文件写入字符窜
   * @param     String str,String fileName,boolean append
   *            待写字符窜，文件名，是否追加
   * @param     String str,String fileName
   *            待写字符窜，文件名
   * @return    void
   */
  public static void writeIntoFile(String str, String fileName, boolean append) {

	String str_ = str;
	boolean append_ = append;
	String fileName_ = fileName;

	try {
	  if (!isExistFile(fileName_))
		fileName_ = createNewFile(fileName_);

	  FileWriter fw = new FileWriter(fileName_, append_);
	  PrintWriter out = new PrintWriter(fw);
	  out.print(str_);
	  out.close();
	  fw.close();

	} catch (IOException e) {
	  //System.out.println("对不起，IOException错误!");
	  e.printStackTrace();
	}
  }
  public static void writeOutput(String fileName, String msg, String encoding) {

	try {
	  FileOutputStream fos = new FileOutputStream(fileName);
	  Writer out = new OutputStreamWriter(fos, encoding);
	  out.write(msg);
	  out.close();
	} catch (IOException e) {
	  e.printStackTrace();
	}
  }
  public static void writeOutput(String fileName, String msg, String encoding, boolean isAppend) throws IOException {

	try {
	  FileOutputStream fos = new FileOutputStream(fileName, isAppend);
	  Writer out = new OutputStreamWriter(fos, encoding);
	  out.write(msg);
	  out.close();
	} catch (IOException e) {
	  e.printStackTrace();
	  throw e;
	}
  }
}
