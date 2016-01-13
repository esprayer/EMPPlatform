package com.efounder.pub.util;

/**
 * FileFunction.java
 * �����ļ�������һЩ���÷������ࡣ
 * �������½����򿪡����桢�رա�׷�ӡ����롢ɾ������ȡ�����
 * @author  ¬��
 * @version 1.0, 04/02/01
 */
import java.io.*;

public class FileFunction {

  public FileFunction() {

  }
  /**
   * �½�һ�������ļ�
   * @param     File dir,String name      �ļ�Ŀ¼���ļ���
   * @param     String path                �ļ�Ŀ¼
   * @param     String path,String name    �ļ�Ŀ¼���ļ���
   * @return    String fileName            �ļ���
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
   * ɾ��һ�������ļ�
   * @param     File dir,String name      �ļ�Ŀ¼���ļ���
   * @param     String path                �ļ�Ŀ¼
   * @param     String path,String name    �ļ�Ŀ¼���ļ���
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
   * �ж�һ�������ļ��Ƿ����
   * @param      File dir,String name      �ļ�Ŀ¼���ļ���
   * @param      String path                �ļ�Ŀ¼
   * @param      String path,String name    �ļ�Ŀ¼���ļ���
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
   * ���ļ��ж�ȡ
   * @param     String fileName      �ļ���
   * @return    String record        ����ַ���
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
	  //System.out.println("�Բ���IOException����!");
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
	  //System.out.println("�Բ���IOException����!");
	  e.printStackTrace();
	}
  }
  /**
   * ���ļ�д���ַ���
   * @param     String str,String fileName,boolean append
   *            ��д�ַ��ܣ��ļ������Ƿ�׷��
   * @param     String str,String fileName
   *            ��д�ַ��ܣ��ļ���
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
	  //System.out.println("�Բ���IOException����!");
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
