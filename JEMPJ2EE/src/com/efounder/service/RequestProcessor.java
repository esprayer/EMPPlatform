package com.efounder.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;

public class RequestProcessor {
	public Object processorLocalhost(Request request) {
		String serviceInterface = request.getServiceInterface();//服务的接口class名称(被调用者的接口)
		try {
			Object service = null;
//			if(request.getServiceBeanName()!=null){
//				//通过Spring初始化
////				service=com.westerasoft.common.SpringBeanFactory.getBean(request.getServiceBeanName());
//			}
//			else{
//				//初始化
//				service = Class.forName(serviceInterface).newInstance();
//			}
//			Method method = null;
//			BeanInfo beanInfo = Introspector.getBeanInfo(service.getClass());
//			MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
//			for (int i = 0; i < methodDescriptors.length; i++) {
//				MethodDescriptor descriptor = methodDescriptors[i];
//				method = descriptor.getMethod();
//				if (method.getName().equalsIgnoreCase(request.getMethodName()))
//					break;
//				method = null;
//			}
//			if (method != null) {
//				//调用请求的方法
//				return method.invoke(service, request.getArguments().toArray());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "succ";
	}
}
