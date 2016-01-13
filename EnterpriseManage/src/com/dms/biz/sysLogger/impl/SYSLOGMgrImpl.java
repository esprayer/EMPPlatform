package com.dms.biz.sysLogger.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etsoft.pub.util.StringFunction;
import com.dms.biz.sysLogger.SYSLOGServiceMgr;
import com.dms.persistence.sysLogger.bean.DMS_SYSLOG;
import com.dms.persistence.sysLogger.mapper.DMS_SYSLOGMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.SYSUser;

@Transactional(rollbackFor = Exception.class)
@Service("SYSLOGServiceMgr")
public class SYSLOGMgrImpl extends AbstractBusinessObjectServiceMgr implements SYSLOGServiceMgr {

	@Autowired
	private DMS_SYSLOGMapper sysLogMapper ;

	public List<DMS_SYSLOG> SYSLogList(String F_CREATOR) {

		return null;
	}

	public void insertLoginSYSLog(HttpServletRequest request) {
		DMS_SYSLOG sysLog = new DMS_SYSLOG();	
		SYSUser user = (SYSUser)request.getSession().getAttribute("contextUser");
		sysLog.setF_CREATOR(user.getUSER_ID());
		sysLog.setF_IP(getIpAddr(request));
		sysLog.setF_CRDATE(new Date());
		sysLog.setF_MSG("用户  " + user.getUSER_NAME() + "(" + user.getUSER_ID() + ")" + "  登陆系统");
		sysLog.setF_ID(StringFunction.generateKey());
		sysLogMapper.insertSYSLog(sysLog);
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (!checkIP(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		return ip;
	}
	private static boolean checkIP(String ip) {
		if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip)
				|| ip.split(".").length != 4) {
			return false;
		}
		return true;
	}
}
