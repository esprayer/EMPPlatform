package com.efounder.pub.util;

/**
 * Debug.java
 * 定义程序调试的一些常用方法的类。
 * @author  卢波
 * @version 1.0, 04/02/01
 */
public class Debug {

  /**
   * 程序调试方式打开标志(boolean)
   */
  public final static boolean IS_DEBUG = false;

  /**
   * 将调试信息输出到系统上(参数类型:char[])(一行)
   */
  public static void PrintlnMessageToSystem(char[] p_string) {
	if (IS_DEBUG){
          System.out.println(p_string);
        }
  }
  /**
   * 将调试信息输出到系统上(参数类型:char)(一行)
   */
  public static void PrintlnMessageToSystem(char p_char) {
	if (IS_DEBUG){
          System.out.println(p_char);
        }
  }
  /**
   * 将调试信息输出到系统上(参数类型:double)(一行)
   */
  public static void PrintlnMessageToSystem(double p_double) {
	if (IS_DEBUG){
	  System.out.println(p_double);
	}
  }
  /**
   * 将调试信息输出到系统上(参数类型:float)(一行)
   */
  public static void PrintlnMessageToSystem(float p_float) {
	if (IS_DEBUG){
	  System.out.println(p_float);
	}
  }
  /**
   * 将调试信息输出到系统上(参数类型:int)(一行)
   */
  public static void PrintlnMessageToSystem(int p_int) {
	if (IS_DEBUG){
	  System.out.println(p_int);
	}
  }
  /**
   * 将调试信息输出到系统上(参数类型:long)(一行)
   */
  public static void PrintlnMessageToSystem(long p_long) {
	if (IS_DEBUG){
	  System.out.println(p_long);
	}
  }
  /**
   * 将调试信息输出到系统上(参数类型:Object)(一行)
   */
  public static void PrintlnMessageToSystem(Object p_object) {
	if (IS_DEBUG){
	  System.out.println(p_object);
	}
  }
  /**
   * 将调试信息输出到系统上(参数类型:String)(一行)
   */
  public static void PrintlnMessageToSystem(String p_string) {
	if (IS_DEBUG){
	  System.out.println(p_string);
	}
  }
  /**
   * 将调试信息输出到系统上(参数类型:boolean)(一行)
   */
  public static void PrintlnMessageToSystem(boolean p_boolean) {
	if (IS_DEBUG){
	  System.out.println(p_boolean);
	}
  }
  /**
   * 将调试信息输出到系统上(参数类型:char[])
   */
  public static void PrintMessageToSystem(char[] p_string) {
	if (IS_DEBUG){
          System.out.print(p_string);
        }
  }
  /**
   * 将调试信息输出到系统上(参数类型:char)
   */
  public static void PrintMessageToSystem(char p_char) {
	if (IS_DEBUG){
          System.out.print(p_char);
        }
  }
  /**
   * 将调试信息输出到系统上(参数类型:double)
   */
  public static void PrintMessageToSystem(double p_double) {
	if (IS_DEBUG){
          System.out.print(p_double);
        }
  }
  /**
   * 将调试信息输出到系统上(参数类型:float)
   */
  public static void PrintMessageToSystem(float p_float) {
	if (IS_DEBUG){
          System.out.print(p_float);
        }
  }
  /**
   * 将调试信息输出到系统上(参数类型:int)
   */
  public static void PrintMessageToSystem(int p_int) {
	if (IS_DEBUG){
          System.out.print(p_int);
        }
  }
  /**
   * 将调试信息输出到系统上(参数类型:long)
   */
  public static void PrintMessageToSystem(long p_long) {
	if (IS_DEBUG){
          System.out.print(p_long);
        }
  }
  /**
   * 将调试信息输出到系统上(参数类型:Object)
   */
  public static void PrintMessageToSystem(Object p_object) {
	if (IS_DEBUG){
          System.out.print(p_object);
        }
  }
  /**
   * 将调试信息输出到系统上(参数类型:String)
   */
  public static void PrintMessageToSystem(String p_string) {
	if (IS_DEBUG){
          System.out.print(p_string);
        }
  }
  /**
   * 将调试信息输出到系统上(参数类型:boolean)
   */
  public static void PrintMessageToSystem(boolean p_boolean) {
	if (IS_DEBUG){
          System.out.print(p_boolean);
        }
  }
  /**
   * 将调试信息输出到文本文件上(参数类型:String)
   * 注意:文本文件如果存在，则这接
   * @param     p_string    调试信息
   * @return    fileName    日志文件
   */
  public static void PrintMessageToTxt(String p_string, String fileName) {
	if (IS_DEBUG)
	  FileFunction.writeIntoFile(p_string, fileName);
  }
}
