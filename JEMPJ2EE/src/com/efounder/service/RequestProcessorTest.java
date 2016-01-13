package com.efounder.service;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestProcessorTest {
	public static void main(String[] args) {
		String urlString = "http://localhost:9080/hyecl/ESPService";
		//String urlString = "http://localhost:8080/wcommons/servlet/BaseDataServlet";
//		urlString="localhost";
		//请求调用远程接口com.westerasoft.common.dnet.RemoteService的方法gbToUtf8(String src)
		Request request = new Request(urlString, "com.efounder.service.RemoteService", "gbToUtf81");
		request.addArgument("中国人民解放军ABC");//添加调用参数即方法gbToUtf81的参数 
		Object answer = (Object) processor(request );
		System.out.println(answer);
		request = new Request(urlString,null,"findUsers");
		//请求调用远程接口userManager(Spring配置的bean name)的findUsers()方法
		request.setServiceBeanName("userManager"); answer = (Object) processor(request );
		System.out.println(answer);
	}
	private static Object processor(Request request) {
		try {
			
			URL url = new URL(request.getServer());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-type", "application/x-java-serialized-object"); 
			con.setRequestProperty("Content-length", "" + -1);
			con.setRequestMethod("POST");  
			ObjectOutputStream dataout = new ObjectOutputStream(con.getOutputStream());
			dataout.writeObject(request); 
//			dataout.write("hello".getBytes());                //随便发送一个，该行也可以不写。
			dataout.flush();
			dataout.close();
			InputStream inStrm = con.getInputStream(); // <===注意，实际发送请求的代码段就在这里
			ObjectInputStream in = new ObjectInputStream(con.getInputStream());
			Object obj = in.readObject();
			in.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
}