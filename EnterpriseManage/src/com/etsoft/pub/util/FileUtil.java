package com.etsoft.pub.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dms.persistence.document.bean.DMS_DOC;

public class FileUtil {
	/**
	 * 检查路径是否存在
	 * @param folderPath
	 * @return
	 */
	public static boolean checkExists(String folderPath) {
		File folder = new File(folderPath);    
		return folder.exists();
	}
	
	/**
	 * 检查是否为文件
	 * @param folderPath
	 * @return
	 */
	public static boolean checkFile(String folderPath) {
		File file = new File(folderPath);    
		return file.isFile();
	}
	
	/**
	 * 检查目录是否存在
	 * @param folderPath
	 * @return
	 */
	public static boolean checkDirectory(String folderPath) {
		File folder = new File(folderPath);    
		return folder.isDirectory();
	}
	
	/**
	 * 只能创建一级目录
	 * @param folderPath
	 */
	public static void createDirectory(String folderPath) {
		File folder = new File(folderPath);   
		if(!checkExists(folderPath)) folder.mkdir();		
	}
	
	/**
	 * 在需要时会自动创建上级目录
	 * @param folderPath
	 */
	public static void createDirectorys(String folderPath) {
		File folder = new File(folderPath);   
		if(!checkExists(folderPath)) folder.mkdirs();		
	}
	
	/**
	 * 取得文件大小
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File f) throws Exception{
        long s=0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s= fis.available();
        }
        return s;
    }

	/**
	 * 递归 取得文件夹大小
	 * @param f
	 * @return
	 * @throws Exception
	 */
    public static long getFileSize(File f)throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }
    
    /**
     * 转换文件大小
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) { 
    	DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
   
    /**
     * 递归求取目录文件个数
     * @param f
     * @return
     */
    public static long getlist(File f){
        long size = 0;
        File flist[] = f.listFiles();
        size=flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;
    }
    
    /**
     * 获取文件
     * @param filePath
     * @return
     */
    public static File getFile(String filePath) {
    	return new File(filePath); 
    }

    public static List<File> copyFile(Map<String, DMS_DOC> fileMap, String targetFilePath) throws IOException {
    	Iterator         iterator = null;
    	File srcFile = null;
    	File targetFile = null;
    	List<File> fileList = new ArrayList<File>();
		iterator = fileMap.entrySet().iterator();
		DMS_DOC doc = null;
		while(iterator.hasNext()){
     		Map.Entry m = (Map.Entry) iterator.next();
     		doc = (DMS_DOC) m.getValue();
     		srcFile = FileUtil.getFile(m.getKey().toString());
     		targetFile = new File(targetFilePath + "\\" + doc.getF_NAME());
     		copyFile(srcFile, targetFile);
     		fileList.add(targetFile);
		}
		return fileList;
    }
    
    /**
     * 复制文件
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    
    /**
     * 删除空目录
     * @param dir 将要删除的目录路径
     */
    public static void doDeleteEmptyDir(String dir) {
        (new File(dir)).delete();
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
    public static byte[] getBytesFromFile(String fileName) throws Exception{
    	if(fileName.equals("")) return null;
        File f = new File(fileName);
        if (f == null) {
          return null;
        }
        if(!f.exists()) return null;

        try {
        	FileInputStream stream = new FileInputStream(f);
        	ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
        	byte[] b = new byte[1000];
        	int n;
        	while ( (n = stream.read(b)) != -1) {
        		out.write(b, 0, n);
        	}
        	stream.close();
        	out.close();
        	return out.toByteArray();
        } catch (Exception e) {
          throw e;
        }
	}
}
