package dwz.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import dwz.business.standard.MetadataObject;
import dwz.web.editor.DateEditor;
import dwz.web.editor.DoubleEditor;
import dwz.web.editor.IntegerEditor;
import dwz.web.editor.LongEditor;

public abstract class BaseController {
	@Autowired
	protected ResourceBundleMessageSource _res;
	
//	@Autowired
//	protected SpringContextHolder _contextHolder;

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	protected ModelAndView ajaxDone(int statusCode, String message) {
		return ajaxDone(statusCode,message,"1");
	}
	
	protected ModelAndView ajaxDone(int statusCode, String message, String type) {
		ModelAndView mav = new ModelAndView("ajaxDone");
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		
		  //开始返回
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        Map attributes = new HashMap();
        attributes.put("success", Boolean.TRUE);
        attributes.put("statusCode", statusCode);
        attributes.put("message", message);
        if(type.equals("1")) attributes.put("callbackType", request.getParameter("callbackType"));
        attributes.put("rel", request.getParameter("rel"));
        attributes.put("forward", request.getParameter("forward"));
        attributes.put("callBackUrl", request.getParameter("callBackUrl"));
        attributes.put("forwardUrl", request.getParameter("forwardUrl"));
        attributes.put("forwardHead", request.getParameter("forwardHead"));        
        attributes.put("pageBreak", request.getParameter("pageBreak"));
        attributes.put("navTabId", request.getParameter("navTabId"));
        attributes.put("tabid", request.getParameter("tabid"));
        attributes.put("reloadDiv", request.getParameter("reloadDiv"));
        attributes.put("callBackFunction", request.getParameter("callBackFunction"));
        
        attributes.put("dialogTitle", request.getParameter("dialogTitle"));
        attributes.put("dialogRel", request.getParameter("dialogRel"));
        attributes.put("dialogWidth", request.getParameter("dialogWidth"));
        attributes.put("dialogHeight", request.getParameter("dialogHeight"));
        attributes.put("dialogMax", request.getParameter("dialogMax"));
        attributes.put("dialogMask", request.getParameter("dialogMask"));
        attributes.put("dialogMaxable", request.getParameter("dialogMaxable"));
        attributes.put("dialogMinable", request.getParameter("dialogMinable"));
        attributes.put("dialogRestoreable", request.getParameter("dialogRestoreable"));
        
        attributes.put("dialogFresh", request.getParameter("dialogFresh"));
        attributes.put("dialogResizable", request.getParameter("dialogResizable"));
        attributes.put("dialogDrawable", request.getParameter("dialogDrawable"));
        attributes.put("dialogClose", request.getParameter("dialogClose"));
        attributes.put("dialogParam", request.getParameter("dialogParam"));
        attributes.put("dialogHref", request.getParameter("dialogHref"));
        attributes.put("dialogWarn", request.getParameter("dialogWarn"));
        view.setAttributesMap(attributes);
        mav.setView(view);
		return mav;
	}
	
	protected ModelAndView ajaxDone1(int statusCode, String message) {
		ModelAndView mav = new ModelAndView("ajaxDone");
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", null);
		
		  //开始返回
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        Map attributes = new HashMap();
        attributes.put("success", Boolean.TRUE);
        attributes.put("callbackType", request.getParameter("callbackType"));
        attributes.put("rel", request.getParameter("rel"));
        attributes.put("forwardUrl", request.getParameter("forwardUrl"));
        attributes.put("title", request.getParameter("title"));
        attributes.put("navTabId", request.getParameter("navTabId"));
        view.setAttributesMap(attributes);
        mav.setView(view);
		return mav;
	}
	
	protected ModelAndView jsonDone(int statusCode, String message) {
		ModelAndView mav = new ModelAndView("jsonView");
		JSONObject  jsonObject = new JSONObject(); 
		jsonObject.element("0", "0");
		JSONArray   jsonArray = new JSONArray();
		jsonArray.add(jsonObject);    
		mav.addObject(jsonArray);
		return mav;
	}

	protected ModelAndView ajaxDoneSuccess(String message) {
		return ajaxDone(200, message);
	}
	
	protected ModelAndView ajaxDoneError(String message) {
		return ajaxDone(300, message);
	}
	
	protected ModelAndView ajaxDoneSuccess1(String message) {
		return ajaxDone1(200, message);
	}

	protected ModelAndView jsonDoneSuccess(String message) {
		return ajaxDone(200, message);
	}

	protected String getMessage(String code) {
		return this.getMessage(code, new Object[] {});
	}

	protected String getMessage(String code, Object arg0) {
		return getMessage(code, new Object[] { arg0 });
	}

	protected String getMessage(String code, Object arg0, Object arg1) {
		return getMessage(code, new Object[] { arg0, arg1 });
	}

	protected String getMessage(String code, Object arg0, Object arg1,
			Object arg2) {
		return getMessage(code, new Object[] { arg0, arg1, arg2 });
	}

	protected String getMessage(String code, Object arg0, Object arg1,
			Object arg2, Object arg3) {
		return getMessage(code, new Object[] { arg0, arg1, arg2, arg3 });
	}

	protected String getMessage(String code, Object[] args) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		Locale locale = localeResolver.resolveLocale(request);

		return _res.getMessage(code, args, locale);
	}
	
	protected String getString(String key, String val) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		if(request.getParameter(key) == null || request.getParameter(key).equals("")) return val;
		else return request.getParameter(key);
	}
}
