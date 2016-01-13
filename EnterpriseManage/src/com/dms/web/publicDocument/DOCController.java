package com.dms.web.publicDocument;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.etsoft.pub.enums.JDocLogOperateTypeEnum;
import com.etsoft.pub.enums.JPermissionEnum;
import com.etsoft.pub.util.JPermissionFunction;
import com.etsoft.pub.util.StringFunction;
import com.dms.biz.docLogger.DOCLOGServiceMgr;
import com.dms.biz.docNote.DOCNoteServiceMgr;
import com.dms.biz.docVersion.DocVersionServiceMgr;
import com.dms.biz.document.DocServiceMgr;
import com.dms.biz.folder.FolderServiceMgr;
import com.dms.biz.permission.FolderPermissionServiceMgr;
import com.dms.persistence.docLogger.bean.DMS_DOCLOG;
import com.dms.persistence.docNote.bean.DMS_DOCNOTE;
import com.dms.persistence.document.bean.DMS_DOC;
import com.dms.persistence.folder.bean.DMS_FOLDER;
import com.dms.persistence.permission.bean.DMS_FOLDER_PERMISSION;
import com.dms.persistence.version.bean.DMS_DOCVERSION;
import com.etsoft.pub.util.FileUtil;
import com.etsoft.pub.util.StringUtil;

import dwz.web.BaseController;
import esyt.framework.persistence.qxgl.beans.SYSUser;

@Controller
@RequestMapping(value="/dms/doc")
public class DOCController extends BaseController{
	@Autowired
	private DocVersionServiceMgr versionMgr;
	
	@Autowired
	private DocServiceMgr docMgr;
	
	@Autowired
	private DOCLOGServiceMgr docLogMgr;

	@Autowired
	private DOCNoteServiceMgr docNoteMgr;

	@Autowired
	private FolderPermissionServiceMgr permissionMgr;
	
	@Autowired
	private FolderServiceMgr folderMgr;
	
	@Autowired
	public HttpSession session;

	//--------------------------------------------------------------------------------------------------
	//描述:导入文件选择
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/add")
	public String addDoc(@RequestParam String FOLDER_ID, 
						 @RequestParam String NODETYPE, 
						 @RequestParam String realPath, 
						 Model model) {
		model.addAttribute("FOLDER_ID", FOLDER_ID);
		model.addAttribute("NODETYPE", NODETYPE);
		model.addAttribute("realPath", realPath);
		return "/dms/document/importFilel";
	}
	
	
	//--------------------------------------------------------------------------------------------------
	//描述:编辑选择文档
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/editDoc")
	public String editDoc(@RequestParam String FOLDER_ID, 
						  @RequestParam String F_DOCID, 
						  @RequestParam String NODETYPE, 
						  @RequestParam String docVersion, 
						  HttpServletRequest request, 
						  Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		int maxVersionNumber = 1;
		DMS_DOCVERSION version = null;

		if(docVersion.equals("")) {
			DMS_DOCVERSION maxVersion = versionMgr.loadMaxDocVersion(F_DOCID);		    
		    //设置文件名,如果不存在版本号，则默认文件名为1
	        if(maxVersion == null) maxVersionNumber = 1;
	        //否则获取当前版本号做为文件名
	        else maxVersionNumber = maxVersion.getF_VERSION_NUMBER(); 	 
	        model.addAttribute("editType", "edit");
		} else {
			maxVersionNumber = Integer.parseInt(docVersion);
			version = versionMgr.loadDocVersionById(F_DOCID, maxVersionNumber);
	        model.addAttribute("F_COMMENT", version.getF_COMMENT());
	        model.addAttribute("editType", "read");
		}
		
		docVersion = "" + maxVersionNumber;
		DMS_DOC doc = docMgr.loadDoc(F_DOCID);
		doc.setF_TYPE(doc.getF_TYPE().toUpperCase());
		model.addAttribute("FOLDER_ID", FOLDER_ID);
		model.addAttribute("F_DOCID", F_DOCID);
		model.addAttribute("USER_ID", user.getUSER_ID());
		model.addAttribute("USER_NAME", user.getUSER_NAME());
		model.addAttribute("doc", doc);
		model.addAttribute("NODETYPE", NODETYPE);
		model.addAttribute("docVersion", docVersion);
		
		List<DMS_FOLDER_PERMISSION>  permissionList = permissionMgr.folderPermissionList(FOLDER_ID, "", "");
		DMS_FOLDER folder = folderMgr.loadFolder(FOLDER_ID);
		
		//分配权限
//		if (user.getUSER_ISADMIN().equals("1")) {
//			JPermissionFunction.endowPermission(model, "1");
//		} else {
//			JPermissionFunction.permissionAssignment(model, user, folder, permissionList, "", "");
//		}		
		
		
		JPermissionFunction.permissionAssignment(model, user, folder, permissionList, F_DOCID, doc.getF_TYPE());
		//添加文件日志信息
        DMS_DOCLOG docLog = new DMS_DOCLOG();
        docLog.setF_DOCID(F_DOCID);
        docLog.setF_OPERATOR(user.getUSER_ID());
        docLog.setF_OPTYPE(JDocLogOperateTypeEnum.EDIT.getValue());
        docLog.setF_IP(StringFunction.getIpAddr(request));
        docLog.setF_OPID(StringFunction.generateKey());
        docLog.setF_MSG("开始编辑文档：" + doc.getF_NAME());
        docLogMgr.insertDOCLog(docLog);
        
		return "/dms/document/docEdit";
	}
	
	
	//--------------------------------------------------------------------------------------------------
	//描述:weboffice打开offie文档的时候，加载文档
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/loadDoc")  
	public void loadDoc(@RequestParam String F_DOCID,
						@RequestParam String docVersion, 
						HttpServletRequest request, 
						HttpServletResponse response, 
						Model model) throws IOException {  
	    DMS_DOC doc = docMgr.loadDoc(F_DOCID);

	    String servletPath = request.getRealPath("") + "\\upload\\dms";
	    String sourceFileName = servletPath + "\\"  + doc.getF_DOCID() + "\\" + docVersion;
	    String targetFileName = StringFunction.generateKey();
	    String targetFilePath = servletPath + "\\"  + doc.getF_DOCID() + "\\" + targetFileName + "." + doc.getF_TYPE();// 构成新文件名。
	    File sourceFile = new File(sourceFileName);
	    File targetFile = new File(targetFilePath);
	    
	    //将源文件复制一份
	    FileUtil.copyFile(sourceFile, targetFile);
	    com.etsoft.pub.util.UploadFileUtil upFile = new com.etsoft.pub.util.UploadFileUtil();
	    upFile.download(targetFile, doc.getF_NAME(), "", response);
	    targetFile.delete();
	} 
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存修改后的文件
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/saveDoc")  
	public ModelAndView saveDoc(@RequestParam String F_DOCID,	
								@RequestParam String versionNum,
								@RequestParam String USER_ID, 	
								@RequestParam String versionNote,
								HttpServletRequest request, 
								HttpServletResponse response, 
								Model model) throws IOException {  
		String newFileName = null;
		long s = 0;
		int maxVersionNumber = 0;
		boolean bExists = false;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
		String servletPath = request.getRealPath("") + "\\upload\\dms";
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();  
		DMS_DOCVERSION maxVersion = versionMgr.loadMaxDocVersion(F_DOCID);
		
		if(versionNum.equals("-1")) {
			maxVersion = versionMgr.loadMaxDocVersion(F_DOCID);
		} else {
			try {
				maxVersionNumber = Integer.parseInt(versionNum);
			} catch(Exception ce) {
				maxVersionNumber = 1;
			}
		}
		
		//一个文件放到一个目录下，不同的版本放到相同的目录下，文件名为版本号
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
             // 上传文件名     
             MultipartFile mf = entity.getValue();    
             //设置文件名,如果不存在版本号，则默认文件名为1
//             if(maxVersion == null) maxVersionNumber = 1;
//             //否则获取当前版本号加1做为文件名
//             else {
//            	 maxVersionNumber = maxVersion.getF_VERSION_NUMBER();
//            	 maxVersionNumber++; 
//             }
             if(versionNum.equals("-1")) {
            	 maxVersionNumber = maxVersion.getF_VERSION_NUMBER();
            	 maxVersionNumber++; 
             }
             newFileName = servletPath + "\\"  + F_DOCID + "\\" + maxVersionNumber;// 构成新文件名。
             
             //因为是编辑文档保存，所以目录已经存在，只上传文件
             File uploadFile = new File(newFileName);
             
             //判断文件是否存在
             bExists = uploadFile.exists();
             
             try {  
                 FileCopyUtils.copy(mf.getBytes(), uploadFile); 
	         } catch (IOException e) {  
	             e.printStackTrace();  
	         }    
	         
	         //保存编辑后文档大小和最后修改日期
	         DMS_DOC doc = docMgr.loadDoc(F_DOCID);
	         doc.setF_DOCID(F_DOCID);
	         //获取文件大小
	         try {
				s = FileUtil.getFileSizes(uploadFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			BigInteger b = new BigInteger(String.valueOf(s));
	        doc.setF_SIZE(b);
	        doc.setF_CREATOR(USER_ID);
	        docMgr.upateDoc(doc);
  
	        //添加文件日志信息
	        DMS_DOCLOG docLog = new DMS_DOCLOG();
	        docLog.setF_DOCID(F_DOCID);
	        docLog.setF_OPERATOR(USER_ID);
	        docLog.setF_OPTYPE(JDocLogOperateTypeEnum.SAVE.getValue());
	        docLog.setF_IP(StringFunction.getIpAddr(request));
	        docLog.setF_OPID(StringFunction.generateKey());	        
	        docLog.setF_MSG("保存文档：" + doc.getF_NAME());
	        docLogMgr.insertDOCLog(docLog);
	        
	        //添加文件版本
	        if(!bExists) {
		        DMS_DOCVERSION version = new DMS_DOCVERSION();
		        version.setF_DOCID(F_DOCID);
		        version.setF_ID(StringFunction.generateKey());
		        version.setF_VERSION_NUMBER(maxVersionNumber);
		        version.setF_CREATOR(USER_ID);
		        version.setF_COMMENT(versionNote);
		        versionMgr.createVersion(version);
	        }
	        //修改版本信息，因为如果是保存不退出的话，修改说明可能会变化
	        else {
	        	DMS_DOCVERSION version = versionMgr.loadDocVersionById(F_DOCID, maxVersionNumber);
		        version.setF_COMMENT(versionNote);
		        versionMgr.updateVersionNote(version);
	        }
		}   
		ModelAndView mav = new ModelAndView("ajaxDone");
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		Map attributes = new HashMap();
		attributes.put("保存状态：", "保存成功");
		view.setAttributesMap(attributes);
		mav.setView(view);
//		ModelAndView modelView = ajaxDoneSuccess("添加成功");
//		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
//		Map attributes = view.getAttributesMap();
		//String forwardUrl = attributes.get("forwardUrl").toString();
		return mav;
	} 
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存修改后的文件
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/getDocVersion")  
	public ModelAndView getDocVersion(@RequestParam String F_DOCID,								
									  Model model) throws IOException {  
		DMS_DOCVERSION maxVersion = versionMgr.loadMaxDocVersion(F_DOCID);
		
		ModelAndView mav = new ModelAndView("ajaxDone");
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		Map attributes = new HashMap();
		attributes.put("number", maxVersion.getF_VERSION_NUMBER());
		view.setAttributesMap(attributes);
		mav.setView(view);
		
		return  mav;
	} 
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存导入文件
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/upload")
	public void upload(@RequestParam String FOLDER_ID,  
			           HttpServletRequest request, 
			           HttpServletResponse response, 
			           Model model) {
		String fileName;
		String newFileName = null;
		long s = 0;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
		String servletPath = request.getRealPath("") + "\\upload\\dms";
		String strFolder = "";
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();  
		java.util.Date currTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		strFolder = formatter.format(currTime);		
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		
		FileUtil.createDirectorys(servletPath);
		
		//一个文件放到一个目录下，不同的版本放到相同的目录下，文件名为版本号
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
             // 上传文件名     
             MultipartFile mf = entity.getValue();    
             fileName = mf.getOriginalFilename();           
             strFolder = StringUtil.nextId();// 返回一个随机UUID做为文件夹名字。
             String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;

             newFileName = servletPath + "\\"  + strFolder + "\\1";// 构成新文件名。
             
             //判断目录是否存在，每天上传的申请单都放到对应的一个目录下
             FileUtil.createDirectory(servletPath + "\\" + strFolder);
             File uploadFile = new File(newFileName);
             try {  
                 FileCopyUtils.copy(mf.getBytes(), uploadFile); 
	         } catch (IOException e) {  
	             e.printStackTrace();  
	         }    
	         DMS_DOC doc = new DMS_DOC();
	         doc.setF_DOCID(strFolder);
	         doc.setF_FOLDERID(FOLDER_ID);
	         doc.setF_NAME(fileName);
	         doc.setF_TYPE(suffix);
	         doc.setF_DELETE("0");
	         doc.setF_LOCKED("0");
	         //获取文件大小
	         try {
				s = FileUtil.getFileSizes(uploadFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			BigInteger b = new BigInteger(String.valueOf(s));
	        doc.setF_SIZE(b);
	        doc.setF_CREATOR(user.getUSER_ID());
	        docMgr.createDocumennt(doc);
	        
	        //添加文件日志信息
	        DMS_DOCLOG docLog = new DMS_DOCLOG();
	        docLog.setF_DOCID(strFolder);
	        docLog.setF_OPERATOR(user.getUSER_ID());
	        docLog.setF_OPTYPE(JDocLogOperateTypeEnum.UPLOAD.getValue());
	        docLog.setF_IP(StringFunction.getIpAddr(request));
	        docLog.setF_OPID(StringFunction.generateKey());
//	        docLog.setF_MSG("用户  " + user.getUSER_NAME() + "(" + user.getUSER_ID() + ")" + "  上传文件：" + fileName);
	        docLog.setF_MSG("上传文件：" + fileName);
	        docLogMgr.insertDOCLog(docLog);
	        
	        //添加文件版本
	        DMS_DOCVERSION version = new DMS_DOCVERSION();
	        version.setF_DOCID(strFolder);
	        version.setF_ID(StringFunction.generateKey());
	        version.setF_VERSION_NUMBER(1);
	        version.setF_CREATOR(user.getUSER_ID());
	        version.setF_COMMENT("");
	        versionMgr.createVersion(version);
		}   
		try {
			response.getWriter().write(newFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:文件属性
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/docInfo")
	public String docInfo(@RequestParam String inputPath, 
			              @RequestParam String realPath, 
						  @RequestParam String F_DOCID, 
						  @RequestParam String NODETYPE,
						  @RequestParam String FOLDER_ID, 
						  Model model) {
		DMS_DOC doc = docMgr.loadDoc(F_DOCID);
		String docPath = "";
		String F_NAME = "";
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		List<DMS_DOCVERSION> versionList = versionMgr.loadDocVersion(F_DOCID);
		List<DMS_DOCLOG> docLogList = docLogMgr.DOCLogList(F_DOCID, null);
		List<DMS_DOCNOTE> noteList = docNoteMgr.docNoteList(F_DOCID,user.getUSER_ID());
		
		List<DMS_FOLDER_PERMISSION>  permissionList = permissionMgr.folderPermissionList(FOLDER_ID, "", "");
		DMS_FOLDER folder = folderMgr.loadFolder(FOLDER_ID);
		//分配权限
		JPermissionFunction.permissionAssignment(model, user, folder, permissionList, F_DOCID, doc.getF_TYPE().toUpperCase());
		
		Queue<String> folderQueue = getDocPath(doc.getF_FOLDERID());

		if(NODETYPE.equals("0")) {
			docPath = "公共文档：/";
		} else if(NODETYPE.equals("1")) {
			docPath = "我的文档：/";
		} else if(NODETYPE.equals("2")) {
			docPath = "回收站：/";
		} 
		
		//循环队列
        while((F_NAME = folderQueue.poll()) != null){ 	  
        	docPath += F_NAME + "/";   	
        } 
        docPath += doc.getF_NAME();
        doc.setF_PATH(docPath);
        
		model.addAttribute("noteList", noteList);
		model.addAttribute("doc", doc);
		model.addAttribute("F_DOCID", doc.getF_DOCID());
		model.addAttribute("versionList", versionList);
		model.addAttribute("adminFlag", user.getUSER_ISADMIN());
		model.addAttribute("docLogList", docLogList);
		model.addAttribute("inputPath", inputPath);
		model.addAttribute("realPath", realPath);
		model.addAttribute("NODETYPE", NODETYPE);
		model.addAttribute("FOLDER_ID", FOLDER_ID);
		model.addAttribute("renameType", "doc");
		return "/dms/document/documentProperty";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:重命名文件
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/renameDoc")
	public String renameDoc(@RequestParam String inputPath, 
			                @RequestParam String realPath, 
			                @RequestParam String F_DOCID, 
			                @RequestParam String NODETYPE,
			                Model model) {
		DMS_DOC doc = docMgr.loadDoc(F_DOCID);		
		model.addAttribute("F_DOCID", F_DOCID);
		model.addAttribute("inputPath", inputPath);
		model.addAttribute("realPath", realPath);
		model.addAttribute("NODETYPE", NODETYPE);
		model.addAttribute("FOLDER_ID", doc.getF_FOLDERID());
		model.addAttribute("fileName", doc.getF_NAME().substring(0, doc.getF_NAME().lastIndexOf(".")));
		model.addAttribute("renameType", "doc");
		return "/dms/folder/renameFile";
	}
	
	/**
	 * 递归调用该方法，返回从根目录到本级的路径集合
	 */
	private Queue<String> getDocPath(String F_FOLDERID) {
		Queue<String> folderQueue = new LinkedList<String>(); 
		DMS_FOLDER folder = folderMgr.loadFolder(F_FOLDERID, "0");
		
		//如果folder为空，则表示该节点为根目录
		if(folder == null) return folderQueue;
		else {
			folderQueue = getDocPath(folder.getF_PARENTID());
			folderQueue.offer(folder.getF_NAME());
		}
		return folderQueue;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:文件加锁
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/locked")
	public String locked(@RequestParam String NODETYPE, @RequestParam String F_DOCID, HttpServletRequest request, Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		DMS_DOC doc = docMgr.lockedDoc(F_DOCID,user);
		
		String docPath = "";
		String F_NAME = "";
		
		Queue<String> folderQueue = getDocPath(doc.getF_FOLDERID());

		if(NODETYPE.equals("0")) {
			docPath = "公共文档：/";
		} else if(NODETYPE.equals("1")) {
			docPath = "我的文档：/";
		} else if(NODETYPE.equals("2")) {
			docPath = "回收站：/";
		} 
		
		//循环队列
        while((F_NAME = folderQueue.poll()) != null){ 	  
        	docPath += F_NAME + "/";   	
        } 
        docPath += doc.getF_NAME();
        doc.setF_PATH(docPath);
        
		//添加日志信息
		DMS_DOCLOG docLog = new DMS_DOCLOG();
        docLog.setF_DOCID(F_DOCID);
        docLog.setF_OPERATOR(user.getUSER_ID());
        docLog.setF_OPTYPE(JDocLogOperateTypeEnum.LOCK.getValue());
        docLog.setF_IP(StringFunction.getIpAddr(request));
        docLog.setF_OPID(StringFunction.generateKey());
        docLog.setF_MSG(" 将文档：" + doc.getF_NAME() + " 锁定");
        docLogMgr.insertDOCLog(docLog);
        
        List<DMS_FOLDER_PERMISSION>  permissionList = permissionMgr.folderPermissionList(doc.getF_FOLDERID(), "", "");
		DMS_FOLDER folder = folderMgr.loadFolder(doc.getF_FOLDERID());
		//分配权限
		JPermissionFunction.permissionAssignment(model, user, folder, permissionList, F_DOCID, doc.getF_TYPE().toUpperCase());
		
		model.addAttribute("doc", doc);
		model.addAttribute("F_DOCID", doc.getF_DOCID());
		model.addAttribute("NODETYPE", NODETYPE);
		return "/dms/document/docInfo";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:文件解锁
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/unLocked")
	public String unLocked(@RequestParam String NODETYPE, @RequestParam String F_DOCID, HttpServletRequest request, Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		DMS_DOC doc = docMgr.unLockedDoc(F_DOCID,user);
		String docPath = "";
		String F_NAME = "";
		
		Queue<String> folderQueue = getDocPath(doc.getF_FOLDERID());

		if(NODETYPE.equals("0")) {
			docPath = "公共文档：/";
		} else if(NODETYPE.equals("1")) {
			docPath = "我的文档：/";
		} else if(NODETYPE.equals("2")) {
			docPath = "回收站：/";
		} 
		
		//循环队列
        while((F_NAME = folderQueue.poll()) != null){ 	  
        	docPath += F_NAME + "/";   	
        } 
        docPath += doc.getF_NAME();
        doc.setF_PATH(docPath);
        
		List<DMS_DOCVERSION> versionList = versionMgr.loadDocVersion(F_DOCID);
		//添加日志信息
		DMS_DOCLOG docLog = new DMS_DOCLOG();
        docLog.setF_DOCID(F_DOCID);
        docLog.setF_OPERATOR(user.getUSER_ID());
        docLog.setF_OPTYPE(JDocLogOperateTypeEnum.UNLOCK.getValue());
        docLog.setF_IP(StringFunction.getIpAddr(request));
        docLog.setF_OPID(StringFunction.generateKey());
        docLog.setF_MSG(" 将文档：" + doc.getF_NAME() + " 解锁");
        docLogMgr.insertDOCLog(docLog);
        

        List<DMS_FOLDER_PERMISSION>  permissionList = permissionMgr.folderPermissionList(doc.getF_FOLDERID(), "", "");
		DMS_FOLDER folder = folderMgr.loadFolder(doc.getF_FOLDERID());
		//分配权限
		JPermissionFunction.permissionAssignment(model, user, folder, permissionList, F_DOCID, doc.getF_TYPE().toUpperCase());
		
		model.addAttribute("doc", doc);
		model.addAttribute("adminFalg", user.getUSER_ISADMIN());
		model.addAttribute("versionList", versionList);
		model.addAttribute("F_DOCID", doc.getF_DOCID());
		model.addAttribute("USER_ID", user.getUSER_ID());
		model.addAttribute("NODETYPE", NODETYPE);
		return "/dms/document/docInfo";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:下载文件
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
    @RequestMapping("/download")
    public void download(@RequestParam String F_DOCID, @RequestParam String F_VERSION_NUMBER, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
    	DMS_DOC doc = docMgr.loadDoc(F_DOCID);
    	SYSUser user = (SYSUser) session.getAttribute("contextUser");
		try {
			String fileName = request.getRealPath("") + "\\upload\\dms\\" + doc.getF_DOCID() + "\\" + F_VERSION_NUMBER;
			com.etsoft.pub.util.UploadFileUtil upFile = new com.etsoft.pub.util.UploadFileUtil();
			upFile.download(FileUtil.getFile(fileName), doc.getF_NAME(), "", response);
			//添加文件日志信息
	        DMS_DOCLOG docLog = new DMS_DOCLOG();
	        docLog.setF_DOCID(F_DOCID);
	        docLog.setF_OPERATOR(user.getUSER_ID());
	        docLog.setF_OPTYPE(JDocLogOperateTypeEnum.DOWNLOAD.getValue());
	        docLog.setF_IP(StringFunction.getIpAddr(request));
	        docLog.setF_OPID(StringFunction.generateKey());
	        docLog.setF_MSG("下载文档：" + doc.getF_NAME());
	        docLogMgr.insertDOCLog(docLog);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("0");
		} finally {

		}
    }

    //--------------------------------------------------------------------------------------------------
	//描述:批量下载文件
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
    @RequestMapping("/batchDownload")
    public void batchDownload(@RequestParam String selectData, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
    	//最大版本号
    	String F_VERSION_NUMBER = "";
    	//文件上传根目录
    	String rootFileName = request.getRealPath("") + "\\upload\\dms\\";
    	String zipFileName = "";
    	DMS_FOLDER folder = null;
    	SYSUser user = (SYSUser) session.getAttribute("contextUser");
//    	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
//        ServletContext servletContext = webApplicationContext.getServletContext();  

    	DMS_DOC doc;
    	DMS_DOCLOG docLog;
    	
    	String[] data = selectData.split("@");
		String[] array = null;
		String fileName = "";
		Map<String, DMS_DOC> fileMap = new HashMap<String, DMS_DOC>();
		
		try {
			com.etsoft.pub.util.UploadFileUtil upFile = new com.etsoft.pub.util.UploadFileUtil();
			for(int i = 0; i< data.length; i++) {
				array = data[i].split(":");
				doc = docMgr.loadDoc(array[1]);
				if(folder == null) {
					folder = folderMgr.loadFolder(doc.getF_FOLDERID());
				}
				DMS_DOCVERSION maxVersion = versionMgr.loadMaxDocVersion(array[1]);
	    		F_VERSION_NUMBER = "" + maxVersion.getF_VERSION_NUMBER();	    	
	    		fileName = rootFileName + doc.getF_DOCID() + "\\" + F_VERSION_NUMBER;
	    		fileMap.put(fileName, doc);
	    		//添加文件日志信息
		        docLog = new DMS_DOCLOG();
		        docLog.setF_DOCID(array[1]);
		        docLog.setF_OPERATOR(user.getUSER_ID());
		        docLog.setF_OPTYPE(JDocLogOperateTypeEnum.DOWNLOAD.getValue());
		        docLog.setF_IP(StringFunction.getIpAddr(request));
		        docLog.setF_OPID(StringFunction.generateKey());
		        docLog.setF_MSG("下载文档：" + doc.getF_NAME());
		        docLogMgr.insertDOCLog(docLog);
			}
			if(data.length > 0) {
				zipFileName = user.getUSER_NAME() + "_"
				            + folder.getF_NAME() + "_" 
				            + dwz.common.util.DateUtil.date3String("yyyyMMddHHmrps");
				upFile.batchDownload(fileMap, zipFileName, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
    }

    //--------------------------------------------------------------------------------------------------
	//描述:刷新actionDiv
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/actionControl")
	public String action(@RequestParam String FOLDER_ID, 
						 @RequestParam String NODETYPE,  
						 @RequestParam String F_DOCID,  
						 @RequestParam String F_DOCTYPE,  
						 @RequestParam String realPath, 
						 Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		String parentFolderID = "";
		DMS_FOLDER selectFolder = folderMgr.loadFolder(FOLDER_ID);
		if(selectFolder != null) parentFolderID = selectFolder.getF_PARENTID();
		List<DMS_FOLDER_PERMISSION>  selectPermissionList = permissionMgr.folderPermissionList(FOLDER_ID, "", "");
		List<DMS_FOLDER_PERMISSION>  permissionList = permissionMgr.folderPermissionList(parentFolderID, "", "");

		//如果F_DOCID为空，则说明选择的是文件夹
		if(F_DOCID.equals("")) {
			selectPermissionList = permissionMgr.folderPermissionList(FOLDER_ID, "", "");
			selectFolder = folderMgr.loadFolder(FOLDER_ID);
			//因为下级目录继承上级目录，所以判断下级目录权限,根据选择目录分配权限
			JPermissionFunction.permissionAssignment(model, user, selectFolder, selectPermissionList, F_DOCID, F_DOCTYPE);
			JPermissionFunction.menuActionPermissionAssignment(model, user, selectFolder, permissionList);
			model.addAttribute("renameType", "folder");
		} 
		//否则选择的是是文件
		else {
			selectPermissionList = permissionMgr.folderPermissionList(FOLDER_ID, "", "");
			selectFolder = folderMgr.loadFolder(FOLDER_ID);
			//因为下级目录继承上级目录，所以判断下级目录权限,根据选择目录分配权限
			JPermissionFunction.permissionAssignment(model, user, selectFolder, selectPermissionList, F_DOCID, F_DOCTYPE);
			model.addAttribute("renameType", "doc");
		}
		
		//如果上级目录为空，则会跳转到根目录，虽然当前选择目录有下载和上传权限，但是根目录下不能上传文件
		if(selectFolder == null) {
			if(user.getUSER_ISADMIN().equals("1")) {
				model.addAttribute(JPermissionEnum.UPLOAD.getKey(), "0");
			} else {
				
			}
		}

		model.addAttribute("FOLDER_ID", FOLDER_ID);
		model.addAttribute("NODETYPE", NODETYPE);
		model.addAttribute("F_DOCID", F_DOCID);
		model.addAttribute("F_DOCTYPE", F_DOCTYPE.toUpperCase());
		model.addAttribute("realPath", realPath);
		return "/dms/folder/menuAction";
	}

	//--------------------------------------------------------------------------------------------------
	//描述:保存版本修改说明
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/saveVersionNote")
	public ModelAndView saveVersionNote(@RequestParam String FOLDER_ID, 
			 							@RequestParam String NODETYPE,  
			 							@RequestParam String F_DOCID,  
			 							@RequestParam String realPath, 
			 							@RequestParam String inputPath, 
			 							@RequestParam String backVersion, 
			 							@RequestParam String F_NOTE, 
			 							HttpServletRequest request, 
					                    HttpServletResponse response, 
			 							Model model) throws IOException {
		long s = 0;
		String fileName = "";
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		DMS_DOC doc = docMgr.loadDoc(F_DOCID);
	    DMS_DOCVERSION maxVersion = versionMgr.loadMaxDocVersion(F_DOCID);
	    int maxVersionNumber = 1;
	    //设置文件名,如果不存在版本号，则默认文件名为1
        if(maxVersion == null) maxVersionNumber = 1;
        //否则获取当前版本号加1做为文件名
        else {
        	maxVersionNumber = maxVersion.getF_VERSION_NUMBER(); 
        	maxVersionNumber++;
        }
        fileName = doc.getF_NAME();
	    String servletPath = request.getRealPath("") + "\\upload\\dms";
	    String sourceFileName = servletPath + "\\"  + doc.getF_DOCID() + "\\" + backVersion;
	    
	    String targetFilePath = servletPath + "\\"  + doc.getF_DOCID() + "\\" + maxVersionNumber;// 构成新文件名。
	    File sourceFile = new File(sourceFileName);
	    File targetFile = new File(targetFilePath);
	    
	    //将源文件复制一份
	    FileUtil.copyFile(sourceFile, targetFile);
	    
	    doc = docMgr.loadDoc(F_DOCID);
        doc.setF_DOCID(F_DOCID);
        //获取文件大小
        try {
			s = FileUtil.getFileSizes(sourceFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BigInteger b = new BigInteger(String.valueOf(s));
		doc.setF_SIZE(b);
		docMgr.upateDoc(doc);
       
		//添加文件日志信息
		DMS_DOCLOG docLog = new DMS_DOCLOG();
		docLog.setF_DOCID(F_DOCID);
		docLog.setF_OPERATOR(user.getUSER_ID());
		docLog.setF_OPTYPE(JDocLogOperateTypeEnum.BACK.getValue());
		docLog.setF_IP(StringFunction.getIpAddr(request));
		docLog.setF_OPID(StringFunction.generateKey());
//       docLog.setF_MSG("用户  " + user.getUSER_NAME() + "(" + user.getUSER_ID() + ")" + "  上传文件：" + fileName);
		docLog.setF_MSG("修改文件：" + fileName);
		docLogMgr.insertDOCLog(docLog);
       
		//添加文件版本
		DMS_DOCVERSION version = new DMS_DOCVERSION();
		version.setF_DOCID(F_DOCID);
		version.setF_ID(StringFunction.generateKey());
		version.setF_VERSION_NUMBER(maxVersionNumber);
		version.setF_CREATOR(user.getUSER_ID());
		version.setF_COMMENT(F_NOTE);
		versionMgr.createVersion(version);
       
	    ModelAndView modelView = ajaxDoneSuccess("添加成功");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardHead = attributes.get("forwardHead").toString();
		String forwrdValue = "F_DOCID="+F_DOCID+"&FOLDER_ID="+FOLDER_ID+"&NODETYPE="+NODETYPE+"&realPath="+realPath;
		
		//需要重新加载div数量
		attributes.put("forwardUrl_NUMBER", "3");

		forwrdValue = forwardHead + "/docVersion/loadAllVersion?pageBreak=0&" + forwrdValue + "@"
		            + forwardHead + "/doc/docInfo?F_DOCID="+F_DOCID + "&inputPath="+inputPath+"&realPath="+realPath+"&FOLDER_ID="+FOLDER_ID+"&NODETYPE=" + NODETYPE + "@"
		            + forwardHead + "/folder/loadFolderById/" + NODETYPE + "?FOLDER_ID="+FOLDER_ID+"&realPath="+realPath;
		
		attributes.put("forwardDivArray", "docVersionLiBox@infoLiBox@folderDocDiv");
		attributes.put("forwardUrlArray", forwrdValue);
		
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存版本修改说明
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/saveFileName")
	public ModelAndView saveFileName(@RequestParam String FOLDER_ID, 
			 						 @RequestParam String NODETYPE,  
			 						 @RequestParam String F_DOCID,  
			 						 @RequestParam String realPath, 
			 						 @RequestParam String inputPath, 
			 						 @RequestParam String fileName, 
			 						 @RequestParam String renameType, 
			 						 HttpServletRequest request, 
					                 HttpServletResponse response, 
			 						 Model model) throws IOException {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		String oldFileName = "";
		String stuff = "";
		
		if(renameType.equals("doc")) {
			DMS_DOC doc = docMgr.loadDoc(F_DOCID);
			stuff = doc.getF_NAME().substring(doc.getF_NAME().lastIndexOf("."));
			oldFileName = doc.getF_NAME();
			doc.setF_NAME(fileName + stuff);
			doc.setF_CHDATE(new Date());
			docMgr.upateDoc(doc);
			
			//添加文件日志信息
			DMS_DOCLOG docLog = new DMS_DOCLOG();
			docLog.setF_DOCID(F_DOCID);
			docLog.setF_OPERATOR(user.getUSER_ID());
			docLog.setF_OPTYPE(JDocLogOperateTypeEnum.RENAME.getValue());
			docLog.setF_IP(StringFunction.getIpAddr(request));
			docLog.setF_OPID(StringFunction.generateKey());
			docLog.setF_MSG("重命名文档: " + oldFileName + stuff + " 为 " + fileName + stuff);
			docLogMgr.insertDOCLog(docLog);
		} else {
			DMS_FOLDER folder = folderMgr.loadFolder(FOLDER_ID);
			oldFileName = folder.getF_NAME();
			folder.setF_NAME(fileName);
			folder.setF_CHDATE(new Date());
			folderMgr.updateFolder(folder);
		}

	    ModelAndView modelView = ajaxDoneSuccess("文件重命名成功");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardHead = attributes.get("forwardHead").toString();
		
		String forwrdValue = "";
		
		//需要重新加载div数量
		attributes.put("forwardUrl_NUMBER", "2");

		forwrdValue = forwardHead + "/folder/loadFolderById/" + NODETYPE + "?FOLDER_ID="+FOLDER_ID+"&realPath="+realPath + "@";
		if(renameType.equals("doc")) {
			forwrdValue += forwardHead + "/doc/docInfo?F_DOCID=" 
			             + F_DOCID + "&realPath=" + realPath 
			             + "&inputPath=" + inputPath + "&FOLDER_ID=" + FOLDER_ID + "&NODETYPE=" + NODETYPE;
		} else {
			forwrdValue += forwardHead + "/folder/folderInfo?realPath="+realPath+"&FOLDER_ID="+FOLDER_ID+"&NODETYPE=" + NODETYPE;
		}		
		attributes.put("forwardDivArray", "folderDocDiv@infoLiBox");
		attributes.put("forwardUrlArray", forwrdValue);
		
		return modelView;
	}
}
