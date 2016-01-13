package com.put.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;

/**
 * @author ES
 * @created 2013-5-18
 * 
 */
public class UploadFileUtil {
	public final static int PIC_BUFF_SIZE = 8 * 1024;

	public final static String SYSTEM_PATH = "pics";

	private long fileSize = 0l;

	public long getFileSize(){
		return this.fileSize;
	}

	
	/**
	 * 文件下载
	 * @param is
	 * @param fileName
	 * @param contentType
	 * @return
	 */
	public static String download(InputStream is, String fileName, String contentType,HttpServletResponse response){
		ServletOutputStream out = null;
		if(ErrorCode.isEmpty(contentType)){
			contentType = "application/x-msdownload";
		}
		if(ErrorCode.isEmpty(fileName)){
			fileName = "filename";
		}
		else{
			if(fileName.indexOf("/")>0){
				fileName = Path.getFileName(fileName);
			}
		}
		try {
			   //response.reset();
			   response.setContentType(contentType);	
			   response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"),"iso8859-1"));
			   out = response.getOutputStream();
	
			   byte[] bytes = new byte[0xffff];
			   int b = 0;
			   
			   while ((b = is.read(bytes, 0, 0xffff)) > 0) {
				   out.write(bytes, 0, b);
			   }
			   is.close();
			   out.flush();
		  } catch (Exception e) {
		   // TODO 自动生成 catch 块
		  		e.printStackTrace();
		  }
		  finally{
			  	if(out!=null){
			  		try{
			  		out.close();
			  		}catch(Exception e){
			  			e.printStackTrace();
			  		}
			  	}	
			  }
		  return null;
	}
	
	/**
	 * 文件下载
	 * @param file
	 * @param fileName
	 * @param contentType
	 * @return
	 */
	public static String download(File file, String fileName, String contentType,HttpServletResponse response){
		InputStream is = null;
		try{
			is = new FileInputStream(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		String value= download(is, fileName, contentType,response);
		try{
			if(is!=null)
				is.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 文件下载
	 * @param file
	 * @param fileName
	 * @param contentType
	 * @return
	 */
	public static String download(String file, String fileName, String contentType,HttpServletResponse response){
		InputStream is = null;
		try{
			is = new FileInputStream(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		String value= download(is, fileName, contentType,response);
		try{		
			if(is!=null)
				is.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}
}
