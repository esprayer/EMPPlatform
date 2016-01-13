package com.efounder.service;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.efounder.eai.data.JParamObject;

public class Request implements java.io.Serializable{
	//目标地址，如果为localhost则为本地方法调用。
	private String server;
	//spring bean的配置名称，如果配置了该值则通过spring配置装载service。
	private String serviceBeanName;
	//服务的接口class名称，必须带包名 
	private String serviceInterface; //要调用的方法名称 
	private String methodName;
	//返回类型，该值不是必须的。
	private String returnType="void";
	//传入的参数列表
	private java.util.List arguments=new java.util.ArrayList();
	/**
	* 构造方法
	* @param server 目标地址
	* @param serviceInterface spring bean的配置名称
	* @param methodName 方法名称
	*/

	public Request(String server, String serviceInterface, String methodName) {
		super();
		this.server = server;
		this.serviceInterface = serviceInterface;
		this.methodName = methodName;
	}
	/**
	* 添加调用参数
	* @param arg 调用参数
	*/
	public void addArgument(Object arg){
		arguments.add(arg);
	}
	public java.util.List getArguments() {
		return arguments;
	}
	public void setArguments(java.util.List arguments) {
		this.arguments = arguments;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getServiceInterface() {
		return serviceInterface;
	}
	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}
	public String getServiceBeanName() {
		return serviceBeanName;
	}
	public void setServiceBeanName(String serviceBeanName) {
		this.serviceBeanName = serviceBeanName;
	}
	/**
	* @see java.lang.Object#toString()
	*/
	public String toString() {
		return new ToStringBuilder(this).append("serviceInterface",
				this.serviceInterface).append("serviceBeanName",
	this.serviceBeanName).append("returnType", this.returnType)
	.append("arguments", this.arguments).append("server",
	this.server).append("methodName", this.methodName)
	.toString();
	} 
	}
	