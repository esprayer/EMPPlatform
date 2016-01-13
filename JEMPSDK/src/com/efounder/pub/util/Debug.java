package com.efounder.pub.util;

/**
 * Debug.java
 * ���������Ե�һЩ���÷������ࡣ
 * @author  ¬��
 * @version 1.0, 04/02/01
 */
public class Debug {

  /**
   * ������Է�ʽ�򿪱�־(boolean)
   */
  public final static boolean IS_DEBUG = false;

  /**
   * ��������Ϣ�����ϵͳ��(��������:char[])(һ��)
   */
  public static void PrintlnMessageToSystem(char[] p_string) {
	if (IS_DEBUG){
          System.out.println(p_string);
        }
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:char)(һ��)
   */
  public static void PrintlnMessageToSystem(char p_char) {
	if (IS_DEBUG){
          System.out.println(p_char);
        }
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:double)(һ��)
   */
  public static void PrintlnMessageToSystem(double p_double) {
	if (IS_DEBUG){
	  System.out.println(p_double);
	}
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:float)(һ��)
   */
  public static void PrintlnMessageToSystem(float p_float) {
	if (IS_DEBUG){
	  System.out.println(p_float);
	}
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:int)(һ��)
   */
  public static void PrintlnMessageToSystem(int p_int) {
	if (IS_DEBUG){
	  System.out.println(p_int);
	}
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:long)(һ��)
   */
  public static void PrintlnMessageToSystem(long p_long) {
	if (IS_DEBUG){
	  System.out.println(p_long);
	}
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:Object)(һ��)
   */
  public static void PrintlnMessageToSystem(Object p_object) {
	if (IS_DEBUG){
	  System.out.println(p_object);
	}
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:String)(һ��)
   */
  public static void PrintlnMessageToSystem(String p_string) {
	if (IS_DEBUG){
	  System.out.println(p_string);
	}
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:boolean)(һ��)
   */
  public static void PrintlnMessageToSystem(boolean p_boolean) {
	if (IS_DEBUG){
	  System.out.println(p_boolean);
	}
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:char[])
   */
  public static void PrintMessageToSystem(char[] p_string) {
	if (IS_DEBUG){
          System.out.print(p_string);
        }
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:char)
   */
  public static void PrintMessageToSystem(char p_char) {
	if (IS_DEBUG){
          System.out.print(p_char);
        }
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:double)
   */
  public static void PrintMessageToSystem(double p_double) {
	if (IS_DEBUG){
          System.out.print(p_double);
        }
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:float)
   */
  public static void PrintMessageToSystem(float p_float) {
	if (IS_DEBUG){
          System.out.print(p_float);
        }
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:int)
   */
  public static void PrintMessageToSystem(int p_int) {
	if (IS_DEBUG){
          System.out.print(p_int);
        }
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:long)
   */
  public static void PrintMessageToSystem(long p_long) {
	if (IS_DEBUG){
          System.out.print(p_long);
        }
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:Object)
   */
  public static void PrintMessageToSystem(Object p_object) {
	if (IS_DEBUG){
          System.out.print(p_object);
        }
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:String)
   */
  public static void PrintMessageToSystem(String p_string) {
	if (IS_DEBUG){
          System.out.print(p_string);
        }
  }
  /**
   * ��������Ϣ�����ϵͳ��(��������:boolean)
   */
  public static void PrintMessageToSystem(boolean p_boolean) {
	if (IS_DEBUG){
          System.out.print(p_boolean);
        }
  }
  /**
   * ��������Ϣ������ı��ļ���(��������:String)
   * ע��:�ı��ļ�������ڣ������
   * @param     p_string    ������Ϣ
   * @return    fileName    ��־�ļ�
   */
  public static void PrintMessageToTxt(String p_string, String fileName) {
	if (IS_DEBUG)
	  FileFunction.writeIntoFile(p_string, fileName);
  }
}
