package com.core.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils
{
  public static void decodeFile(File zipFile, File dirFile) throws Exception {
    try {
      InputStream fis = new FileInputStream(zipFile);
      ZipInputStream zis = new ZipInputStream(fis);
      ZipEntry zipentry = zis.getNextEntry();
      while (zipentry != null) {
        if (!(zipentry.isDirectory())) {
          String FileName = zipentry.getName();
          int length = (int)zipentry.getSize();
          char[] dataChar = new char[length];
          for (int i = 0; i < dataChar.length; ++i) {
            dataChar[i] = (char)zis.read();
          }
          byte[] data = new byte[length];
          for (int i = 0; i < dataChar.length; ++i) {
            data[i] = (byte)dataChar[i];
          }
          _$8123(dirFile, FileName, data, zipentry);
        }
        zis.closeEntry();
        zipentry = zis.getNextEntry();
      }
      zis.close();
      fis.close();
    } catch (Exception e) {
      e.printStackTrace(); 
    }
  }

  private static void _$8123(File dirFile, String FileName, byte[] data, ZipEntry zipentry) throws Exception {
    File file = new File(dirFile, FileName);
    File parentFile = file.getParentFile();
    if (!(parentFile.exists())) parentFile.mkdirs();
    if (file.createNewFile()) {
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(data);
      fos.close();
      file.setLastModified(zipentry.getTime());
    }
  }

  public static void deleteDirTree(File dirFile) throws Exception {
    if (!(dirFile.exists())) return;
    if (dirFile.isFile()) {
      dirFile.delete(); return;
    }

    File[] listFiles = dirFile.listFiles();
    for (int i = 0; i < listFiles.length; ++i) {
      File file = listFiles[i];
      if (file.isFile()) {
        file.delete();
      }
      if (file.isDirectory())
        deleteDirTree(file);
    }
  }

  public static void copyDir2Dir(String srcDir, String desDir) throws Exception
  {
    File srcFileDir = new File(srcDir);
    copyDir2Dir(srcDir, srcFileDir, desDir); }

  public static void copyDir2Dir(String srcDir, File srcFileDir, String desDir) throws Exception {
    if (!(srcFileDir.exists())) return;

    if (srcFileDir.isFile()) {
      String FilePath = srcFileDir.getAbsolutePath();
      int Index = FilePath.indexOf(srcDir);
      String DesFilePath = desDir + FilePath.substring(srcDir.length());
      File desFile = new File(DesFilePath);
      copyFile(srcFileDir, desFile);
      return;
    }

    File[] listFiles = srcFileDir.listFiles();
    for (int i = 0; i < listFiles.length; ++i) {
      File file = listFiles[i];
      copyDir2Dir(srcDir, file, desDir); }
  }

  public static boolean copyFile(File srcFile, File desFile) throws Exception {
    if (!(srcFile.exists())) return true;
    File dirFile = new File(desFile.getParent());
    if (!(dirFile.exists())) dirFile.mkdirs();
    if (!(desFile.createNewFile())) return false;
    FileInputStream fis = new FileInputStream(srcFile);
    FileOutputStream fos = new FileOutputStream(desFile);
    int length = fis.available(); byte[] datas = new byte[length];
    fis.read(datas);
    fos.write(datas);
    fis.close(); fos.close();
    desFile.setLastModified(srcFile.lastModified());
    return true;
  }
}