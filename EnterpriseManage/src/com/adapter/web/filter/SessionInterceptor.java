package com.adapter.web.filter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import esyt.framework.persistence.qxgl.beans.SYSUser;

public class SessionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		SYSUser user = (SYSUser) request.getSession().getAttribute("contextUser");
		
		if((request.getServletPath().startsWith("/dms") || request.getServletPath().startsWith("/dailyBusiness") || request.getServletPath().startsWith("/sysConfigure"))
		   && user == null && 
		   !request.getServletPath().equals("/management/checkSession") && 
		   !request.getServletPath().equals("/dms/doc/saveDoc") &&
		   !request.getServletPath().equals("/dms/doc/getDocVersion") &&
		   !request.getServletPath().startsWith("/dailyBusiness/form/batchAdd/storage")) {
			
			String jsonStr = "{\"statusCode\":301,\"message\":\"登录已超时，请重新登录！\"}";
			
			if((request.getServletPath().startsWith("/dms") || request.getServletPath().startsWith("/dailyBusiness") || request.getServletPath().startsWith("/sysConfigure")) && user == null) {
				// 未登录				
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
				JsonNode df = mapper.readValue(jsonStr, JsonNode.class);				
				PrintWriter out = response.getWriter();
				response.setContentType("text/plain");
				System.out.println(jsonStr);					
				out.println(df);
				out.close();
				return false;
			}
		}
		return true;  
	}
}
