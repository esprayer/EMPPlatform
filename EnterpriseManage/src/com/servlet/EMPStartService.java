package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import esyt.framework.com.core.xml.PackageStub;

public class EMPStartService extends HttpServlet { 

	private static final long serialVersionUID = 1L; 

	public void init() throws ServletException{ 
		System.out.println(">>可以在这里初始化一些东西>>"); 
		PackageStub.initMETA_INF("Package/client/");
	} 

} 
