package com.efounder.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.xml.PackageStub;


public class RemoteServlet extends HttpServlet {
	
	public void init() throws ServletException{ 
		System.out.println(">>RemoteServlet.init()>>"); 
	} 
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

		System.out.println("doGet：");
	}
	/**
	   *
	   * @param request HttpServletRequest
	   * @param response HttpServletResponse
	   * @throws ServletException
	   * @throws IOException
	   */
	  public void doPost(HttpServletRequest p0, HttpServletResponse p1) throws ServletException, IOException {
	    	System.out.println("doPost：");
	  }

	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,java.io.IOException{
		System.out.print("BaseDataServlet service received a remote request");
		ObjectInputStream in = new ObjectInputStream(req.getInputStream());
		resp.setContentType("application/octest-stream");
		ByteArrayOutputStream byteout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteout);
		Request request;
		try {
			request = (Request)in.readObject();//读取远程调用请求的封装对象
			//System.out.println(":n"+request.toString()); 
			RequestProcessor processor=new RequestProcessor();//请求解析对象 
			out.writeObject(processor.processorLocalhost(request));//执行请求并回写结果
			out.flush();
		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		} 
		byte buf[]= byteout.toByteArray();
		resp.setContentLength(buf.length);
		ServletOutputStream servletout = resp.getOutputStream();
		servletout.write(buf);
		servletout.close(); 
	}
}