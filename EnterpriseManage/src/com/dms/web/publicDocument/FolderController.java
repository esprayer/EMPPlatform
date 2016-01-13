package com.dms.web.publicDocument;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.etsoft.pub.enums.JPermissionEnum;
import com.etsoft.pub.util.JPermissionFunction;
import com.etsoft.pub.util.StringFunction;
import com.dms.biz.document.DocServiceMgr;
import com.dms.biz.folder.FolderServiceMgr;
import com.dms.biz.permission.FolderPermissionServiceMgr;
import com.dms.persistence.document.bean.DMS_DOC;
import com.dms.persistence.folder.bean.DMS_FOLDER;
import com.dms.persistence.permission.bean.DMS_FOLDER_PERMISSION;

import dwz.common.util.EncodeUtils;
import dwz.web.BaseController;
import esyt.framework.persistence.qxgl.beans.SYSUser;

@Controller
@RequestMapping(value="/dms/folder")
public class FolderController extends BaseController{
	@Autowired
	private FolderServiceMgr folderMgr;
	
	@Autowired
	private DocServiceMgr docMgr;
	
	@Autowired
	private FolderPermissionServiceMgr permissionMgr;

	@Autowired
	public HttpSession session;

	/**
	 * 点击菜单，单独拿出来处理，因为需要权限过滤一下
	 * @param NOTETYPE               0:公共文档;1:个人文档;2:回收站
	 * @param PARENT_FOLDERID        上级目录
	 * @param SELECT_FOLDERID        当前选择目录
	 * @param curPath		         目录的地址，不是地址栏输入的地址，因为有可能地址栏输入一些其他字符
	 * @param model
	 * @return
	 */
	@RequestMapping("/rootDocList/{NODETYPE}")
	public String rootDOCList(@PathVariable("NODETYPE") String NODETYPE, 
							  @RequestParam String FOLDER_ID,
							  @RequestParam String realPath, 
							  Model model) {		

		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		//加载当前选择目录下的文件和文件夹
		
		List<DMS_FOLDER> folderList = null;
		List<DMS_DOC> docList = null;
		
		if(NODETYPE.equals("2")) {
			folderList = folderMgr.loadDeleteFolder(user.getUSER_ID());
			docList = docMgr.loadDeleteDoc(user.getUSER_ID());
			model.addAttribute("docList", docList);
		} else {
			if(user.getUSER_ISADMIN().equals("1")) {
				folderList = loadFolder(FOLDER_ID);
			} else {
				folderList = loadFolderByPermission(NODETYPE);
			}
		}
		

		//形成根目录节点
		formRootFolder(NODETYPE, model);
	
		if(user.getUSER_ISADMIN().equals("1")) {
			model.addAttribute(JPermissionEnum.FOLDERPERMISSION.getKey(), "1");
			model.addAttribute(JPermissionEnum.FOLDERDESCRIBE.getKey(), "1");	
			model.addAttribute(JPermissionEnum.CREATE.getKey(), "1");
			model.addAttribute(JPermissionEnum.EDIT.getKey(), "0");
			model.addAttribute(JPermissionEnum.UPLOAD.getKey(), "0");
			model.addAttribute(JPermissionEnum.PRINT.getKey(), "0");
			model.addAttribute(JPermissionEnum.DOWNLOAD.getKey(), "0");
			model.addAttribute(JPermissionEnum.DELETE.getKey(), "1");
		} else {
			JPermissionFunction.endowPermission(model, "0");
		}
		model.addAttribute(JPermissionEnum.FLAG.getKey(), "0");
		model.addAttribute("inputPath", "");
		model.addAttribute("realPath", "");			
		model.addAttribute("SYSUser", user);
		model.addAttribute("FOLDER_ID", FOLDER_ID);		
		model.addAttribute("folderList", folderList);
		return "/dms/folder/list";
	}
	
	/**
	 * 双击目录查看下级文件和文件夹
	 * @param NOTETYPE               0:公共文档;1:个人文档;2:回收站
	 * @param PARENT_FOLDERID        上级目录
	 * @param SELECT_FOLDERID        当前选择目录
	 * @param curPath		         目录的地址，不是地址栏输入的地址，因为有可能地址栏输入一些其他字符
	 * @param model
	 * @return
	 */
	@RequestMapping("/{NODETYPE}")
	public String publicDOCList(@PathVariable("NODETYPE") String NODETYPE, 
								@RequestParam String FOLDER_ID,
								@RequestParam String realPath, 
								Model model) {		
		
		DMS_FOLDER folder = new DMS_FOLDER();
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		//加载当前选择目录下的文件和文件夹
		List<DMS_FOLDER> folderList = loadFolder(FOLDER_ID);
		List<DMS_DOC> docList = docMgr.loadFOLDERDOCS(FOLDER_ID);
		//当前节点
		DMS_FOLDER po = folderMgr.loadFolder(FOLDER_ID);
		//完整路径
		String fullPath = "";
		String parentFolderID = "";

		folder = folderMgr.loadFolder(FOLDER_ID);
		
		if(folder != null) parentFolderID = folder.getF_PARENTID();
		
		String folderPath = "";
		String F_NAME = "";
		
		Queue<String> folderQueue = getDocPath(FOLDER_ID);

		if(NODETYPE.equals("0")) {
			folderPath = "公共文档：/";
		} else if(NODETYPE.equals("1")) {
			folderPath = "我的文档：/";
		} else if(NODETYPE.equals("2")) {
			folderPath = "回收站：/";
		} 
		
		//循环队列
        while((F_NAME = folderQueue.poll()) != null){ 	  
        	folderPath += F_NAME + "/";   	
        } 
        folder.setF_FOLDERPATH(folderPath);
        
        if(docList == null) folder.setF_DOCNumber(0);
		else folder.setF_DOCNumber(docList.size());
        
        model.addAttribute("FOLDER_ID", FOLDER_ID);			
		model.addAttribute("folder", folder);	
		model.addAttribute("FLAG", "1");			
			
		if(realPath.equals("")) {
			if(po != null && !FOLDER_ID.equals("0") && !FOLDER_ID.equals("1") && !FOLDER_ID.equals("2")) realPath += folder.getF_NAME();
		} else {
			if(realPath.lastIndexOf("/") == (realPath.length() - 1)){
				realPath += folder.getF_NAME();
			} else {
				realPath += "/" + folder.getF_NAME();
			}
			
		}
		
		if(user.getUSER_ISADMIN().equals("0")) {
			List<String> pathList = new ArrayList<String>();
			
			while(folder != null) {
				pathList.add(folder.getF_NAME());
				folder = folderMgr.loadFolder(folder.getF_PARENTID());
			}
			for(int i = pathList.size() - 1; i >= 0; i--) {
				fullPath += pathList.get(i).toString() + "/";
			}
			fullPath = fullPath.substring(0, fullPath.lastIndexOf("/"));
			model.addAttribute("inputPath", fullPath);
			model.addAttribute("realPath", fullPath);
		} else {
			model.addAttribute("inputPath", realPath);
			model.addAttribute("realPath", realPath);
		}
		
		
		model.addAttribute("NODETYPE", NODETYPE);		
		List<DMS_FOLDER_PERMISSION>  permissionList = permissionMgr.folderPermissionList(FOLDER_ID, "", "");
		
		//分配权限
		JPermissionFunction.permissionAssignment(model, user, po, permissionList, "", "");
		
		model.addAttribute(JPermissionEnum.FLAG.getKey(), "1");
		
		model.addAttribute("permission", permissionList);
		model.addAttribute("SYSUser", user);		
		model.addAttribute("folderList", folderList);
		model.addAttribute("docList", docList);
		
		/////////////////////////////////////////////////////////

		//上级目录
		DMS_FOLDER parentFolder = folderMgr.loadFolder(parentFolderID);
		//当前选择目录
		DMS_FOLDER selectFolder = null;
				
		if(NODETYPE.equals("2")) {
			selectFolder = folderMgr.loadFolder(FOLDER_ID, "1");
		} else {
			selectFolder = folderMgr.loadFolder(FOLDER_ID);
		}

		//上级目录权限
		List<DMS_FOLDER_PERMISSION> parentFolderPermission = permissionMgr.folderPermissionList(parentFolderID, "", "");
		//当前选择目录权限
		List<DMS_FOLDER_PERMISSION> selectFolderPermission = permissionMgr.folderPermissionList(FOLDER_ID, "", "");
		
		//上级目录权限
		String parentPermission = JPermissionFunction.mergeFolderPermission(parentFolderPermission, user);
		//当前选择目录权限
		String seletPermission = JPermissionFunction.mergeFolderPermission(selectFolderPermission, user);

		model.addAttribute("permission", selectFolderPermission);
		
		//判断手否有创建目录权限
		JPermissionFunction.createPermission(model, user, parentFolder, parentPermission);

		//判断是否有修改权限
		JPermissionFunction.editPermission(model, user, "", "", parentPermission);
				
		//判断是否有上传权限
		JPermissionFunction.uploadPermission(model, user, parentFolder, true, parentPermission);
				
		//判断是否有下载文档权限
		JPermissionFunction.downloadPermission(model, user, parentPermission);

		//判断是否有删除权限
		JPermissionFunction.deletePermission(model, user, selectFolder, seletPermission);

		//判断是否有权限管理权限
		JPermissionFunction.managerPermission(model, user, selectFolder, seletPermission);
		
		model.addAttribute("renameType", "folder");
		model.addAttribute(JPermissionEnum.FLAG.getKey(), "1");

		return "/dms/folder/list";
	}
	
	/**
	 * 删除目录
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam String inputPath, 
					   @RequestParam String realPath,
					   @RequestParam String FOLDER_ID,
					   @RequestParam String NODETYPE, 
					   @RequestParam String selectData, 
					   HttpServletRequest request, 
					   Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		String[] data = selectData.split("@");
		String[] array = null;
		List<String> folderList = new ArrayList<String>();
		List<String> docList = new ArrayList<String>();
		
		for(int i = 0; i< data.length; i++) {
			array = data[i].split(":");
			if(array[0].equals("folderId")) {
				folderList.add(array[1]);
			} else if(array[0].equals("docId")) {
				docList.add(array[1]);
			}
		}
		folderMgr.deleteFolder(folderList,user);
		docMgr.deleteDoc(docList,user,request);
	}

	/**
	 * 还原目录
	 * @param model
	 * @return
	 */
	@RequestMapping("/reduce")
	public void reduce(@RequestParam String inputPath, 
					   @RequestParam String realPath,
					   @RequestParam String FOLDER_ID,
					   @RequestParam String NODETYPE, 
					   @RequestParam String selectData, 
					   HttpServletRequest request, 
					   Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		String[] data = selectData.split("@");
		String[] array = null;
		List<String> folderList = new ArrayList<String>();
		List<String> docList = new ArrayList<String>();
		
		for(int i = 0; i< data.length; i++) {
			array = data[i].split(":");
			if(array[0].equals("folderId")) {
				folderList.add(array[1]);
			} else if(array[0].equals("docId")) {
				docList.add(array[1]);
			}
		}
		folderMgr.reduceFolder(folderList,user);
		docMgr.reduceDoc(docList,user,request);
	}
	
	/**
	 * 删除目录
	 * @param model
	 * @return
	 */
	@RequestMapping("/completelyDelete")
	public void completelyDelete(@RequestParam String inputPath, 
					   			 @RequestParam String realPath,
					   			 @RequestParam String FOLDER_ID,
					   			 @RequestParam String NODETYPE, 
					   			 @RequestParam String selectData, 
					   			 HttpServletRequest request, 
					   			 Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		String[] data = selectData.split("@");
		String[] array = null;
		List<String> folderList = new ArrayList<String>();
		List<String> docList = new ArrayList<String>();
		
		for(int i = 0; i< data.length; i++) {
			array = data[i].split(":");
			if(array[0].equals("folderId")) {
				folderList.add(array[1]);
			} else if(array[0].equals("docId")) {
				docList.add(array[1]);
			}
		}
		folderMgr.completelyDelete(folderList,user);
		docMgr.completelyDelete(folderList,docList,user,request);
	}
	
	/**
	 * 
	 * @param TYPE
	 * @param parentPath
	 * @param F_FOLDERID
	 * @param curPathMS
	 * @param model
	 * @return
	 */
	@RequestMapping("/loadFolderById/{NODETYPE}")
	public String loadFolderById(@PathVariable("NODETYPE") String NODETYPE, 
								 @RequestParam String FOLDER_ID,
								 @RequestParam String realPath,
							     Model model) {		
		DMS_FOLDER folder = new DMS_FOLDER();
		realPath = EncodeUtils.urlDecode(realPath);
		List<DMS_FOLDER> folderList = loadFolder(FOLDER_ID);
		List<DMS_DOC> docList = docMgr.loadFOLDERDOCS(FOLDER_ID);
		
		//查询当前选择文件夹信息
		DMS_FOLDER po = folderMgr.loadFolder(FOLDER_ID);
		
		//parentPath为空，则是点击了“公共文档”或者“个人文档”，所以文档信息没有，DMS_FOLDER目录信息自动形成
		if(po == null) {
			//形成根目录节点
			formRootFolder(NODETYPE, model);
			folder.setF_DOCNumber(docList.size());
		} else {
			folder = folderMgr.loadFolder(FOLDER_ID);
			model.addAttribute("folder", folder);	
			model.addAttribute("FLAG", "1");
			model.addAttribute("NODETYPE", NODETYPE);		
		}

		if(po == null) {
			model.addAttribute("inputPath", "");
			model.addAttribute("realPath", "");
		} else {
			model.addAttribute("inputPath", realPath);
			model.addAttribute("realPath", realPath);
		}
		model.addAttribute("FOLDER_ID", FOLDER_ID);	
		model.addAttribute("folderList", folderList);
		model.addAttribute("docList", docList);
		return "/dms/folder/folderDocList";
	}
	
	/**
	 * 新建文件夹
	 * @param model
	 * @return
	 */
	@RequestMapping("/create")
	public String create(@RequestParam String inputPath, 
								  @RequestParam String realPath, 
			                      @RequestParam String FOLDER_ID, 
			                      @RequestParam String NODETYPE, 
			                      Model model) {
		model.addAttribute("inputPath", inputPath);
		model.addAttribute("realPath", realPath);
		model.addAttribute("FOLDER_ID", FOLDER_ID);
		model.addAttribute("NODETYPE", NODETYPE);
		return "/dms/folder/add";
	}
	
	/**
	 * 修改文件描述
	 * @param model
	 * @return
	 */
	@RequestMapping("/saveFolderDescription")
	public ModelAndView saveFolderDescription(@RequestParam String FOLDER_ID, @RequestParam String F_DESC, Model model) {
		DMS_FOLDER folder = folderMgr.loadFolder(FOLDER_ID);
		
		if(folder == null) return ajaxDoneError("文件夹已删除,请重新加载");
		else {
			folder.setF_DESC(F_DESC);
			folderMgr.updateFolder(folder);
			return ajaxDoneSuccess("修改成功");
		}	
	}

	/**
	 * 新建目录
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public ModelAndView newFolderSave(@RequestParam String inputPath, 
			 						  @RequestParam String FOLDER_ID, 
			 						  @RequestParam String F_WDMC, 
			 						  @RequestParam String F_WDMS, 
			 						  @RequestParam String realPath, 
			 						  @RequestParam String NODETYPE, 
			 						  Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		DMS_FOLDER folder = new DMS_FOLDER();

		folder.setF_FOLDERID(StringFunction.generateKey());
		folder.setF_NAME(F_WDMC);
		folder.setF_DESC(F_WDMS);
		folder.setF_CREATOR(user.getUSER_ID());
		folder.setF_TYPE(NODETYPE);
		folder.setF_PARENTID(FOLDER_ID);
		folder.setF_CRDATE(new Date());
		folder.setF_CHDATE(new Date());
		int flag = folderMgr.createDirectory(folder);
		if(flag == 1) {
			List<DMS_FOLDER> folderList = loadFolder(FOLDER_ID);
			model.addAttribute("folderList", folderList);
		} else if (flag == -1) {
			return ajaxDoneError("文件夹已存在！");
		}

		ModelAndView modelView = ajaxDoneSuccess("添加成功");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "?FOLDER_ID=" + FOLDER_ID + "&inputPath=@" + inputPath + "@&realPath=@" + realPath + "@";
		attributes.put("forwardUrl", forwardUrl);
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:加载目录
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public List<DMS_FOLDER> loadFolder(String curPath) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		List<DMS_FOLDER> folderList = folderMgr.folderList(curPath, user);
		return folderList;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据权限加载目录
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public List<DMS_FOLDER> loadFolderByPermission(String NODETYPE) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		List<DMS_FOLDER> folderList = folderMgr.loadPermissionFolderList(NODETYPE, user);
		return folderList;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:文件夹属性
	// FOLDER_ID:         选择文件或者文件夹ID
	// NODETYPE：                             菜单对应类型
	// curPath：                                当前路径
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/folderInfo")
	public String folderProperty(@RequestParam String FOLDER_ID,  
							     @RequestParam String NODETYPE, 
							     @RequestParam String realPath, 
							     Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		String folderPath = "";
		String F_NAME = "";
		List<DMS_DOC> docList = null;
		//上级目录
		DMS_FOLDER parentFolder = null;
		//当前选择目录
		DMS_FOLDER selectFolder = null;
				
		if(NODETYPE.equals("2")) {
			selectFolder = folderMgr.loadFolder(FOLDER_ID, "1");
		} else {
			selectFolder = folderMgr.loadFolder(FOLDER_ID);
		}
		parentFolder = folderMgr.loadFolder(selectFolder.getF_PARENTID());
		
		docList = docMgr.loadFOLDERDOCS(FOLDER_ID);
		if(docList == null) selectFolder.setF_DOCNumber(0);
		else selectFolder.setF_DOCNumber(docList.size());
		
		//上级目录权限
		List<DMS_FOLDER_PERMISSION> parentFolderPermission = permissionMgr.folderPermissionList(selectFolder.getF_PARENTID(), "", "");
		//当前选择目录权限
		List<DMS_FOLDER_PERMISSION> selectFolderPermission = permissionMgr.folderPermissionList(FOLDER_ID, "", "");
		
		//上级目录权限
		String parentPermission = JPermissionFunction.mergeFolderPermission(parentFolderPermission, user);
		//当前选择目录权限
		String seletPermission = JPermissionFunction.mergeFolderPermission(selectFolderPermission, user);
		
		if(realPath.equals("")) realPath += selectFolder.getF_NAME();
		else realPath += "/" + selectFolder.getF_NAME();
		
		Queue<String> folderQueue = getDocPath(FOLDER_ID);

		if(NODETYPE.equals("0")) {
			folderPath = "公共文档：/";
		} else if(NODETYPE.equals("1")) {
			folderPath = "我的文档：/";
		} else if(NODETYPE.equals("2")) {
			folderPath = "回收站：/";
		} 
		
		//循环队列
        while((F_NAME = folderQueue.poll()) != null){ 	  
        	folderPath += F_NAME + "/";   	
        } 
        selectFolder.setF_FOLDERPATH(folderPath);
        
		model.addAttribute("folder", selectFolder);
		model.addAttribute("permission", selectFolderPermission);
		model.addAttribute("NODETYPE", NODETYPE);
		model.addAttribute("FOLDER_ID", FOLDER_ID);

		//判断手否有创建目录权限
		JPermissionFunction.createPermission(model, user, parentFolder, parentPermission);

		//判断是否有修改权限
		JPermissionFunction.editPermission(model, user, "", "", parentPermission);
				
		//判断是否有上传权限
		JPermissionFunction.uploadPermission(model, user, parentFolder, parentPermission);
				
		//判断是否有下载文档权限
		JPermissionFunction.downloadPermission(model, user, parentPermission);

		//判断是否有删除权限
		JPermissionFunction.deletePermission(model, user, selectFolder, seletPermission);

		//判断是否有权限管理权限
		JPermissionFunction.managerPermission(model, user, selectFolder, seletPermission);
		
		model.addAttribute(JPermissionEnum.FLAG.getKey(), "1");
		
		return "/dms/folder/folderProperty";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:重命名文件夹
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/renameFolder")
	public String renameDoc(@RequestParam String inputPath, 
			                @RequestParam String realPath, 
			                @RequestParam String FOLDER_ID, 
			                @RequestParam String NODETYPE,
			                Model model) {
		DMS_FOLDER folder = folderMgr.loadFolder(FOLDER_ID);
		model.addAttribute("renameType", "folder");
		model.addAttribute("inputPath", inputPath);
		model.addAttribute("realPath", realPath);
		model.addAttribute("NODETYPE", NODETYPE);
		model.addAttribute("FOLDER_ID", FOLDER_ID);
		model.addAttribute("fileName", folder.getF_NAME());
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
	/**
	 * 加载目录和文件
	 * @param model
	 * @return
	 */
	@RequestMapping("/goto")
	public String goToPath(@RequestParam String inputPath, 
						   @RequestParam String realPath,
						   @RequestParam String FOLDER_ID,
						   @RequestParam String NODETYPE, 
						   Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		String[] folderId = getFolderID(NODETYPE, inputPath, user);
		DMS_FOLDER folder = new DMS_FOLDER();
		List<DMS_FOLDER> folderList = null;
		List<DMS_DOC> docList = null;
		List<DMS_FOLDER_PERMISSION>  permissionList = new ArrayList<DMS_FOLDER_PERMISSION>();
		String folderPath = "";
		String F_NAME = "";
		
		//如果路径不存在，则重新加载跳转之前的路径
		if(folderId == null) {
			DMS_FOLDER po = folderMgr.loadFolder(FOLDER_ID);
			folderList = loadFolder(FOLDER_ID);
			docList = docMgr.loadFOLDERDOCS(FOLDER_ID);
			
			//parentPath为空，则是点击了“公共文档”或者“个人文档”，所以文档信息没有，DMS_FOLDER目录信息自动形成
			if(po == null) {
				//形成根目录节点
				formRootFolder(NODETYPE, model);
				//加载当前选择目录下的文件和文件夹
				if(user.getUSER_ISADMIN().equals("1")) {
					folderList = loadFolder(NODETYPE);
				} else {
					folderList = loadFolderByPermission(NODETYPE);
				}
				
				//赋权限，在根目录，如果为管理员，默认全部权限，否则没有权限
				if(user.getUSER_ISADMIN().equals("1")) {
					JPermissionFunction.endowPermission(model, "1");
				} else {
					JPermissionFunction.endowPermission(model, "0");
				}				
			} 
			//说明以前的路径是在根目录下的某个子目录中
			else {
				folder = folderMgr.loadFolder(FOLDER_ID);

				Queue<String> folderQueue = getDocPath(FOLDER_ID);

				if(NODETYPE.equals("0")) {
					folderPath = "公共文档：/";
				} else if(NODETYPE.equals("1")) {
					folderPath = "我的文档：/";
				} else if(NODETYPE.equals("2")) {
					folderPath = "回收站：/";
				} 
				
				//循环队列
		        while((F_NAME = folderQueue.poll()) != null){ 	  
		        	folderPath += F_NAME + "/";   	
		        } 
		        folder.setF_FOLDERPATH(folderPath);
		        
				permissionList = permissionMgr.folderPermissionList(FOLDER_ID, "", "");
				
				//分配权限
				JPermissionFunction.permissionAssignment(model, user, po, permissionList, "", "");
				
				model.addAttribute("permission", permissionList);
				model.addAttribute(JPermissionEnum.FLAG.getKey(), "1");	
				model.addAttribute("folder", folder);	
				model.addAttribute("NODETYPE", NODETYPE);
			}
			model.addAttribute("inputPath", realPath);
			model.addAttribute("realPath", realPath);										
			model.addAttribute("FOLDER_ID", FOLDER_ID);		
			model.addAttribute("folderList", folderList);
			model.addAttribute("docList", docList);
		} 
		//如果不为空，则说明输入路径合法，查找路径，并加载权限
		else {
			//curPathMS为空，则是点击了“公共文档”或者“个人文档”，所以文档信息没有，DMS_FOLDER目录信息自动形成
			if(inputPath.equals("")) {
				if(user.getUSER_ISADMIN().equals("1")) {
					folderList = loadFolder(NODETYPE);
				} else {
					folderList = loadFolderByPermission(NODETYPE);
				}
				//形成根目录节点
				formRootFolder(NODETYPE, model);
				//赋权限，在根目录，如果为管理员，默认全部权限，否则没有权限
				if(user.getUSER_ISADMIN().equals("1")) {
					JPermissionFunction.endowRootPermission(model);
					
				} else {
					JPermissionFunction.endowPermission(model, "0");
				}
			} else {
				folder = folderMgr.loadFolder(folderId[0]);
				
				Queue<String> folderQueue = getDocPath(folderId[0]);

				if(NODETYPE.equals("0")) {
					folderPath = "公共文档：/";
				} else if(NODETYPE.equals("1")) {
					folderPath = "我的文档：/";
				} else if(NODETYPE.equals("2")) {
					folderPath = "回收站：/";
				} 
				
				//循环队列
		        while((F_NAME = folderQueue.poll()) != null){ 	  
		        	folderPath += F_NAME + "/";   	
		        } 
		        folder.setF_FOLDERPATH(folderPath);
		        
				folderList = loadFolder(folderId[0]);
				docList = docMgr.loadFOLDERDOCS(folderId[0]);

				permissionList = permissionMgr.folderPermissionList(folderId[0], "", "");
				
				//分配权限
				JPermissionFunction.permissionAssignment(model, user, folder, permissionList, "", "");
				
				model.addAttribute("permission", permissionList);
				model.addAttribute(JPermissionEnum.FLAG.getKey(), "1");	
				model.addAttribute("folder", folder);	
				model.addAttribute("NODETYPE", NODETYPE);
			}

			model.addAttribute("inputPath", inputPath);			
			model.addAttribute("realPath", inputPath);		
			model.addAttribute("FOLDER_ID", folderId[0]);		
			model.addAttribute("folderList", folderList);
			model.addAttribute("docList", docList);
		}
		return "/dms/folder/list";
	}
	
	/**
	 * 返回上一级
	 * @param model
	 * @return
	 */
	@RequestMapping("/goBack")
	public String goBack(@RequestParam String FOLDER_ID, 
			 			 @RequestParam String NODETYPE, 
						 @RequestParam String realPath, 
						 Model model) {		
		DMS_FOLDER folder = folderMgr.loadFolder(FOLDER_ID);
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		List<DMS_FOLDER> folderList = null;
		String folderPath = "";
		String F_NAME = "";
		//如果目录不存在，可能是被某个人删掉了,则自动定位到根目录
		if(folder == null) {		
			realPath = "";
			
			//加载当前选择目录下的文件和文件夹
			if(user.getUSER_ISADMIN().equals("1")) {
				folderList = loadFolder(NODETYPE);
			} else {
				folderList = loadFolderByPermission(NODETYPE);
			}
			
			//形成根目录节点
			formRootFolder(NODETYPE, model);
			
			//赋权限，在根目录，如果为管理员，默认全部权限，否则没有权限
			if(user.getUSER_ISADMIN().equals("1")) {
				JPermissionFunction.endowPermission(model, "1");
			} else {
				JPermissionFunction.endowPermission(model, "0");
			}
			model.addAttribute(JPermissionEnum.FLAG.getKey(), "0");	
			model.addAttribute("inputPath", realPath);
			model.addAttribute("realPath", realPath);				
			model.addAttribute("FOLDER_ID", FOLDER_ID);		
			model.addAttribute("folderList", folderList);		
		} 
		//如果存在，说明目录存在
		else {
			if(realPath.lastIndexOf("/") == -1) realPath = "";
			else realPath = realPath.substring(0, realPath.lastIndexOf("/"));

			List<DMS_DOC> docList = null;
			
			//如果是管理员，则不用根据权限过滤目录
			if(user.getUSER_ISADMIN().equals("1")) {
				folderList = loadFolder(folder.getF_PARENTID());
				docList = docMgr.loadFOLDERDOCS(folder.getF_PARENTID());
				model.addAttribute("docList", docList);
				List<DMS_FOLDER_PERMISSION>  permission = permissionMgr.folderPermissionList(folder.getF_PARENTID(), "", "");
				model.addAttribute("permission", permission);
				folder = folderMgr.loadFolder(folder.getF_PARENTID());
				if(folder == null) {
					model.addAttribute("FOLDER_ID", NODETYPE);	
					model.addAttribute("inputPath", "");
					model.addAttribute("realPath", "");	
					
					//赋权限，在根目录，如果为管理员，默认全部权限，否则没有权限
					if(user.getUSER_ISADMIN().equals("1")) {
						JPermissionFunction.endowPermission(model, "1");
					} else {
						JPermissionFunction.endowPermission(model, "0");
					}
					model.addAttribute(JPermissionEnum.FLAG.getKey(), "0");					
				} else {
					List<DMS_FOLDER_PERMISSION>  permissionList = permissionMgr.folderPermissionList(folder.getF_FOLDERID(), "", "");
					//分配权限
					JPermissionFunction.permissionAssignment(model, user, folder, permissionList, "", "");
					model.addAttribute(JPermissionEnum.FLAG.getKey(), "1");
					model.addAttribute("FOLDER_ID", folder.getF_FOLDERID());
					model.addAttribute("inputPath", realPath);
					model.addAttribute("realPath", realPath);	
					
					Queue<String> folderQueue = getDocPath(folder.getF_FOLDERID());

					if(NODETYPE.equals("0")) {
						folderPath = "公共文档：/";
					} else if(NODETYPE.equals("1")) {
						folderPath = "我的文档：/";
					} else if(NODETYPE.equals("2")) {
						folderPath = "回收站：/";
					} 
					
					//循环队列
			        while((F_NAME = folderQueue.poll()) != null){ 	  
			        	folderPath += F_NAME + "/";   	
			        } 
			        folder.setF_FOLDERPATH(folderPath);
				}
			} else {
				//如果上级目录有权限，则加载父目录信息
				if(judgeParentFolderPermission(NODETYPE, realPath, user)) {
					folderList = loadFolder(folder.getF_PARENTID());
					docList = docMgr.loadFOLDERDOCS(folder.getF_PARENTID());
					model.addAttribute("docList", docList);
					model.addAttribute(JPermissionEnum.FLAG.getKey(), "1");	
					List<DMS_FOLDER_PERMISSION>  permissionList = permissionMgr.folderPermissionList(folder.getF_PARENTID(), "", "");
					model.addAttribute("permission", permissionList);
					folder = folderMgr.loadFolder(folder.getF_PARENTID());
					//说明上级为根目录
					if(folder == null) {
						JPermissionFunction.endowPermission(model, "0");
						model.addAttribute("FOLDER_ID", NODETYPE);	
						model.addAttribute("inputPath", "");
						model.addAttribute("realPath", "");	
					} 
					//上级不是根目录
					else {
						//分配权限
						JPermissionFunction.permissionAssignment(model, user, folder, permissionList, "", "");
						
						model.addAttribute(JPermissionEnum.FLAG.getKey(), "1");
						model.addAttribute("FOLDER_ID", folder.getF_FOLDERID());
						model.addAttribute("inputPath", realPath);
						model.addAttribute("realPath", realPath);	
						
						Queue<String> folderQueue = getDocPath(folder.getF_FOLDERID());

						if(NODETYPE.equals("0")) {
							folderPath = "公共文档：/";
						} else if(NODETYPE.equals("1")) {
							folderPath = "我的文档：/";
						} else if(NODETYPE.equals("2")) {
							folderPath = "回收站：/";
						} 
						
						//循环队列
				        while((F_NAME = folderQueue.poll()) != null){ 	  
				        	folderPath += F_NAME + "/";   	
				        } 
				        folder.setF_FOLDERPATH(folderPath);
					}
				} else {
					folderList = loadFolderByPermission(NODETYPE);
					//形成根目录节点
					formRootFolder(NODETYPE, model);
					model.addAttribute("inputPath", "");
					model.addAttribute("realPath", "");	
					
					if(user.getUSER_ISADMIN().equals("1")) {
						JPermissionFunction.endowPermission(model, "1");
					} else {
						JPermissionFunction.endowPermission(model, "0");
					}
				}
			}

			model.addAttribute("folder", folder);		
			model.addAttribute("NODETYPE", NODETYPE);
			model.addAttribute("folderList", folderList);
			model.addAttribute("docList", docList);
		}
		return "/dms/folder/list";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:形成根节点
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	private void formRootFolder(String NODETYPE, Model model) {
		DMS_FOLDER folder = new DMS_FOLDER();
		if(NODETYPE.equals("0")) {
			folder.setF_NAME("公共文档");
			folder.setF_FOLDERPATH("公共文档");				
		} else if(NODETYPE.equals("1")) {
			folder.setF_NAME("私人文档");
			folder.setF_FOLDERPATH("私人文档");					
		} else if(NODETYPE.equals("2")) {
			folder.setF_NAME("回收站");
			folder.setF_FOLDERPATH("回收站");				
		}

		model.addAttribute(JPermissionEnum.FLAG.getKey(), "0");
		model.addAttribute("NODETYPE", NODETYPE);
		model.addAttribute("folder", 	folder);
	}

	//--------------------------------------------------------------------------------------------------
	//描述:判断上级目录是否有权限
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	private boolean judgeParentFolderPermission(String NODETYPE, String realPath, SYSUser user) {
		Map<String, String> folderMap = new HashMap<String, String>();
		List<DMS_FOLDER> folderList;
		DMS_FOLDER po = null;
		
		//查询根目录到当前节点上级目录的ID索引
		folderMap = getFolderMap(NODETYPE, realPath, user);
		//查询有权限的目录
		folderList = folderMgr.loadPermissionFolderList(NODETYPE, user);
		for(int i = 0; i < folderList.size(); i++) {
			po = folderList.get(i);
			if(folderMap.get(po.getF_FOLDERID()) != null) return true;
		}
		return false;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取根目录到下级目录的路径ID索引
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	private Map<String, String> getFolderMap(String NODETYPE, String curPath, SYSUser user) {
		Map<String, String> folderMap = new HashMap<String, String>();
		DMS_FOLDER folder = null;
		String parentId = NODETYPE;
		String[] path = curPath.split("/");
		//循环每个节点，判断每个路径是否存在
		if(!curPath.trim().equals("")) {
			for(int i = 0; i < path.length; i++) {
				if(folder != null) parentId = folder.getF_FOLDERID(); 
				folder = folderMgr.searchFolder(parentId, path[i]);		
				folderMap.put(folder.getF_FOLDERID(), folder.getF_FOLDERID());
			}
		}
		return folderMap;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据路径获取目录编号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	private String[] getFolderID(String NODETYPE, String curPathMS, SYSUser user) {
		DMS_FOLDER folder = null;
		DMS_FOLDER po = null;
		List<DMS_FOLDER> folderList;
		String parentId = NODETYPE;
		Map<String, String> folderMap = new HashMap<String, String>();
		String[] id = new String[2];//0:父目录编号;1:当前目录编号
		String[] path = curPathMS.split("/");
		if(curPathMS.trim().equals("")) {
			id[0] = "";
			id[1] = NODETYPE;
			return id;
		} else {
			//循环每个节点，判断每个路径是否存在
			for(int i = 0; i < path.length; i++) {				
				folder = folderMgr.searchFolder(parentId, path[i]);		
				if(folder != null) parentId = folder.getF_FOLDERID(); 
				else return null;
				folderMap.put(folder.getF_FOLDERID(), folder.getF_FOLDERID());
			}
			if(folder == null) return null;
			else {
				//如果是管理员，不用判断路径是否符合权限控制
				if(user.getUSER_ISADMIN().equals("1")) {
					id[0] = folder.getF_FOLDERID();
					id[1] = "";
					return id;
				}
				//如果不是管理员，则需要判断是否符合权限控制
				else {
					folderList = folderMgr.loadPermissionFolderList(NODETYPE, user);
					for(int i = 0; i < folderList.size(); i++) {
						po = folderList.get(i);
						if(folderMap.get(po.getF_FOLDERID()) != null) {
							id[0] = folder.getF_FOLDERID();
							id[1] = "";
							return id;
						}
					}
					return null;
				}
			}
		}		
	}
}
