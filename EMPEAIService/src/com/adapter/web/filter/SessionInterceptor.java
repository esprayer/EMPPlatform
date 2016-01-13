package com.adapter.web.filter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.persistence.qxgl.beans.SYSUser;

public class SessionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		SYSUser                user = (SYSUser) request.getSession().getAttribute("contextUser");
		List<String>    includeList = new ArrayList<String>();
		List<String> notIncludeList = new ArrayList<String>();
		String          noCheckUser = request.getParameter("noCheckUser");
		String          servletPath = request.getServletPath();
		boolean            bInClude = false;
		boolean         bNotInClude = false;
		
		if(noCheckUser == null) noCheckUser = "0";
		
		includeList.add("/standard");
		includeList.add("/management/base/user");
		includeList.add("/cms");
		includeList.add("/sysConfigure");
		includeList.add("/dailyBusiness");
		includeList.add("/dms");
		includeList.add("/query");
		includeList.add("/queryAnalyse");
		includeList.add("/contractManager");
		includeList.add("/noticeMessageManager");
		
		for(int i = 0; i < includeList.size(); i++) {
			bInClude = bInClude || servletPath.startsWith(includeList.get(i));
			if(bInClude) break;
		}
		if(!noCheckUser.equals("1") && bInClude && user == null) {
			String jsonStr = "{\"statusCode\":301,\"message\":\"登录已超时，请重新登录！\"}";
			
//			if((request.getServletPath().startsWith("/dms") || request.getServletPath().startsWith("/dailyBusiness") || request.getServletPath().startsWith("/sysConfigure")) && user == null) {
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
//			}
		}
		return true;  
	}
}
