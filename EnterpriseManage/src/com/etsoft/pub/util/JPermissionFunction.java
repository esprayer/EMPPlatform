package com.etsoft.pub.util;

import java.util.List;

import org.springframework.ui.Model;

import com.etsoft.pub.enums.JPermissionEnum;
import com.dms.persistence.folder.bean.DMS_FOLDER;
import com.dms.persistence.permission.bean.DMS_FOLDER_PERMISSION;

import esyt.framework.persistence.qxgl.beans.SYSUser;

public class JPermissionFunction {

	/**
	 * 目录权限分配
	 * @param model
	 * @param user
	 */
	public static void permissionAssignment(Model model, SYSUser user, DMS_FOLDER po, List<DMS_FOLDER_PERMISSION>  permissionList, String DOC_ID, String DOC_TYPE) {
		String permission = mergeFolderPermission(permissionList, user);
		
		//判断手否有创建目录权限
		createPermission(model, user, po, permission);
				
		//判断手否有重命名权限
		renamePermission(model, user, po, permission);
		
		//判断是否有修改权限
		editPermission(model, user, DOC_ID, DOC_TYPE, permission);
				
		//判断是否有上传权限
		uploadPermission(model, user, po, permission);
				
		//判断是否有下载文档权限
		downloadPermission(model, user, permission);

		//判断是否有删除权限
		deletePermission(model, user, po, permission);
		
		//判断是否有权限管理权限
		managerPermission(model, user, po, permission);
		
		//判断是否有阅读权限
		readPermission(model, user, DOC_ID, DOC_TYPE, permission);
		
		//判断是否有阅读文件权限
		lookPermission(model, user, DOC_ID, DOC_TYPE, permission);
		
		//判断是否有打印权限
		printPermission(model, user, po, permission);
	}
	
	/**
	 * 菜单目录权限分配
	 * @param model
	 * @param user
	 */
	public static void menuActionPermissionAssignment(Model model, SYSUser user, DMS_FOLDER po, List<DMS_FOLDER_PERMISSION>  permissionList) {
		String permission = mergeFolderPermission(permissionList, user);
		
		//判断手否有创建目录权限
		createPermission(model, user, po, permission);
			
		//判断是否有上传权限
		uploadPermission(model, user, po, permission);
				
		//判断是否有下载文档权限
		downloadPermission(model, user, permission);
	}
	
	// --------------------------------------------------------------------------------------------------
	// 描述:合并目录权限
	// 设计: ES
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public static String mergeFolderPermission(List<DMS_FOLDER_PERMISSION> permissionList, SYSUser user) {
		String permission = "";
		DMS_FOLDER_PERMISSION po;
		for (int i = 0; i < permissionList.size(); i++) {
			po = permissionList.get(i);
			//继承部门权限
			if (po.getF_USERID().trim().equals("") && po.getF_BMBH().equals(user.getUSER_ORGID())) {
				permission += po.getF_PERMISSION();
			} 
			//合并同一个部门同一个人的多个权限
			else if (!po.getF_USERID().equals("") && po.getF_BMBH().equals(user.getUSER_ORGID()) && po.getF_USERID().equals(user.getUSER_ID())) {
				permission += po.getF_PERMISSION();
			}
		}
		return permission;
	}
	
	/**
	 * 判断是否具有权限位
	 */
	public static boolean judgePermission(String permission, String p) {
		if(permission.indexOf(p) > -1) return true;
		else return false;
	}
	
	/**
	 * 赋予全部权限
	 */
	public static void endowPermission(Model model, String permission) {
		model.addAttribute(JPermissionEnum.FOLDERPERMISSION.getKey(), permission);
		model.addAttribute(JPermissionEnum.FOLDERDESCRIBE.getKey(), permission);	
		model.addAttribute(JPermissionEnum.CREATE.getKey(), permission);
		model.addAttribute(JPermissionEnum.EDIT.getKey(), permission);
		model.addAttribute(JPermissionEnum.UPLOAD.getKey(), permission);
		model.addAttribute(JPermissionEnum.PRINT.getKey(), permission);
		model.addAttribute(JPermissionEnum.DOWNLOAD.getKey(), permission);
		model.addAttribute(JPermissionEnum.DELETE.getKey(), permission);
		model.addAttribute(JPermissionEnum.RENAME.getKey(), permission);
	}
	
	//赋予根目录权限
	public static void endowRootPermission(Model model) {
		endowAdminRootPermission(model);
	}
	
	/**
	 * 赋予管理员根目录权限
	 * @param model
	 */
	public static void endowAdminRootPermission(Model model) {
		model.addAttribute(JPermissionEnum.FOLDERPERMISSION.getKey(), "1");
		model.addAttribute(JPermissionEnum.FOLDERDESCRIBE.getKey(), "1");	
		model.addAttribute(JPermissionEnum.CREATE.getKey(), "1");
		model.addAttribute(JPermissionEnum.EDIT.getKey(), "0");
		model.addAttribute(JPermissionEnum.UPLOAD.getKey(), "0");
		model.addAttribute(JPermissionEnum.PRINT.getKey(), "0");
		model.addAttribute(JPermissionEnum.DOWNLOAD.getKey(), "0");
		model.addAttribute(JPermissionEnum.DELETE.getKey(), "1");
		model.addAttribute(JPermissionEnum.RENAME.getKey(), "0");
	}
	
	/**
	 * 判断是否有创建目录权限
	 */
	public static void createPermission(Model model, SYSUser user, DMS_FOLDER folder, String permission) {
		// 如果是管理员，则有全部权限
		if (user.getUSER_ISADMIN().equals("1")) {
			JPermissionFunction.endowPermission(model, "1");
		}
		// 如果不是管理员
		else {
			// 如果目录不存在，则是根目录，不管是不是有管理权限，都不能在根目录下创建目录
			if (folder == null) {
				model.addAttribute(JPermissionEnum.CREATE.getKey(), "0");
			}
			// 如果目录存在，说明是在根目录的某个下级，则根据是否有创建权限，赋值
			else if (folder != null && JPermissionFunction.judgePermission(permission, "C")) {
				model.addAttribute(JPermissionEnum.CREATE.getKey(), "1");
			} else {
				model.addAttribute(JPermissionEnum.CREATE.getKey(), "0");
			}
		}
	}
	
	/**
	 * 判断是否有重命名文件权限,有创建权限就拥有重命名权限
	 */
	public static void renamePermission(Model model, SYSUser user, DMS_FOLDER folder, String permission) {
		// 如果是管理员，则有全部权限
		if (user.getUSER_ISADMIN().equals("1")) {
			JPermissionFunction.endowPermission(model, "1");
		}
		// 如果不是管理员
		else {
			// 如果目录不存在，则是根目录，不管是不是有管理权限，都不能在根目录下创建目录
			if (folder == null) {
				model.addAttribute(JPermissionEnum.RENAME.getKey(), "0");
			}
			// 如果目录存在，说明是在根目录的某个下级，则根据是否有创建权限，赋值
			else if (folder != null && JPermissionFunction.judgePermission(permission, "C")) {
				model.addAttribute(JPermissionEnum.RENAME.getKey(), "1");
			} else {
				model.addAttribute(JPermissionEnum.RENAME.getKey(), "0");
			}
		}
	}
	
	/**
	 * 判断是否有编辑文件权限
	 */
	public static void editPermission(Model model, SYSUser user, String F_DOCID, String F_DOCTYPE, String permission) {
		//文件名不为空，如果用户为管理员，则具有全部权限，如果不是管理员，如果有编辑权限，都可以修改文件
		if(!F_DOCID.trim().equals("") && (user.getUSER_ISADMIN().equals("1") || judgePermission(permission, "E"))) {
			//如果当前选择文件时office文件，则可以通过weboffice编辑文件
			if(F_DOCTYPE.toUpperCase().equals("DOC") || F_DOCTYPE.toUpperCase().equals("DOCX") ||
			   F_DOCTYPE.toUpperCase().equals("XLS") || F_DOCTYPE.toUpperCase().equals("XLSX")) {
				model.addAttribute(JPermissionEnum.EDIT.getKey(), "1");
			} else {
				model.addAttribute(JPermissionEnum.EDIT.getKey(), "0");
			}
		}
		//为空，说明当前选择的是目录
		else {
			model.addAttribute(JPermissionEnum.EDIT.getKey(), "0");
		}
	}
	
	/**
	 * 判断是否有上传文件权限
	 */
	public static void uploadPermission(Model model, SYSUser user, DMS_FOLDER folder, String permission) {
		//如果目录为空，则为根目录，不管是否为管理员都不可以上传文件
		if(folder == null) {
			model.addAttribute(JPermissionEnum.UPLOAD.getKey(), "0");
		}
		//如果不是根目录，则根据权限控制是否可以上传文件
		else {
			//如果用户为管理员，则具有全部权限，如果不是管理员，如果有上传权限，都可以修改文件
			if(user.getUSER_ISADMIN().equals("1") || judgePermission(permission, "C")) {
				model.addAttribute(JPermissionEnum.UPLOAD.getKey(), "1");
			}
			//否则没有权限
			else {
				model.addAttribute(JPermissionEnum.UPLOAD.getKey(), "0");
			}
		}
	}
	
	/**
	 * 判断是否有上传文件权限
	 */
	public static void uploadPermission(Model model, SYSUser user, DMS_FOLDER folder, boolean bRoot, String permission) {
		//如果目录为空，则为根目录，不管是否为管理员都不可以上传文件
		if(folder == null && !bRoot) {
			model.addAttribute(JPermissionEnum.UPLOAD.getKey(), "0");
		}
		//如果不是根目录，则根据权限控制是否可以上传文件
		else {
			//如果用户为管理员，则具有全部权限，如果不是管理员，如果有上传权限，都可以修改文件
			if(user.getUSER_ISADMIN().equals("1") || judgePermission(permission, "C")) {
				model.addAttribute(JPermissionEnum.UPLOAD.getKey(), "1");
			}
			//否则没有权限
			else {
				model.addAttribute(JPermissionEnum.UPLOAD.getKey(), "0");
			}
		}
	}
	
	/**
	 * 判断是否有下载文件权限
	 */
	public static void downloadPermission(Model model, SYSUser user, String permission) {
		//如果为目录，则不能下载，只能选择文件进行下载
		if(user.getUSER_ISADMIN().equals("1") || judgePermission(permission, "U")) {
			model.addAttribute(JPermissionEnum.DOWNLOAD.getKey(), "1");
			return;
		} 
		//如果选择的为文件，则根据权限控制是否可以允许下载
		else {
			model.addAttribute(JPermissionEnum.DOWNLOAD.getKey(), "0");
		}		
	}
	
	/**
	 * 判断是否有删除目录权限
	 */
	public static void deletePermission(Model model, SYSUser user, DMS_FOLDER folder, String permission) {
		//如果目录为空，则选择的是根目录，则不能进行删除
		if(folder == null) {
			model.addAttribute(JPermissionEnum.DELETE.getKey(), "0");
			return;
		} 
		//如果选择的为目录或者文件，则根据权限控制是否可以允许下载
		else {
			//如果用户为管理员，则具有全部权限，如果不是管理员，如果有下载权限
			if(user.getUSER_ISADMIN().equals("1") || judgePermission(permission, "D")) {
				model.addAttribute(JPermissionEnum.DELETE.getKey(), "1");
			}
			//否则没有权限
			else {
				model.addAttribute(JPermissionEnum.DELETE.getKey(), "0");
			}
		}		
	}
	
	/**
	 * 判断是否具有目录限管理权限
	 */
	public static void managerPermission(Model model, SYSUser user, DMS_FOLDER folder, String permission) {
		//如果目录为空，则选择的是根目录，则不能进行分配权限
		if(folder == null) {
			model.addAttribute(JPermissionEnum.FOLDERPERMISSION.getKey(), "0");
			return;
		} 
		//如果选择的为目录或者文件，则根据权限控制是否可以分配权限
		else {
			//如果用户为管理员，则具有全部权限，如果不是管理员，如果有管理权限
			if(user.getUSER_ISADMIN().equals("1") || judgePermission(permission, "M")) {
				model.addAttribute(JPermissionEnum.FOLDERPERMISSION.getKey(), "1");
			}
			//否则没有权限
			else {
				model.addAttribute(JPermissionEnum.FOLDERPERMISSION.getKey(), "0");
			}
		}		
	}
	
	/**
	 * 判断是否具有阅读权限
	 */
	public static void readPermission(Model model, SYSUser user, String F_DOCID, String F_DOCTYPE, String permission) {
		//文件名不为空，如果用户为管理员，则具有全部权限，如果不是管理员，如果有编辑权限，都可以修改文件
		if(!F_DOCID.trim().equals("") && (user.getUSER_ISADMIN().equals("1") || judgePermission(permission, "R"))) {
			//如果当前选择文件时office文件，则可以通过weboffice编辑文件
			if(F_DOCTYPE.toUpperCase().equals("DOC") || F_DOCTYPE.toUpperCase().equals("DOCX") ||
			   F_DOCTYPE.toUpperCase().equals("XLS") || F_DOCTYPE.toUpperCase().equals("XLSX")) {
				model.addAttribute(JPermissionEnum.READ.getKey(), "1");
			} else {
				model.addAttribute(JPermissionEnum.READ.getKey(), "0");
			}
		}
		//为空，说明当前选择的是目录
		else {
			model.addAttribute(JPermissionEnum.READ.getKey(), "0");
		}	
	}
	
	/**
	 * 判断是否具有查看文件权限
	 */
	public static void lookPermission(Model model, SYSUser user, String F_DOCID, String F_DOCTYPE, String permission) {
		//文件名不为空，如果用户为管理员，则具有全部权限，如果不是管理员，如果有编辑权限，都可以修改文件
		if(!F_DOCID.trim().equals("") && (user.getUSER_ISADMIN().equals("1") || judgePermission(permission, "R"))) {
			//如果当前选择文件时office文件，则可以通过weboffice编辑文件
			if(F_DOCTYPE.toUpperCase().equals("DOC") || F_DOCTYPE.toUpperCase().equals("DOCX") ||
			   F_DOCTYPE.toUpperCase().equals("XLS") || F_DOCTYPE.toUpperCase().equals("XLSX")) {
				model.addAttribute(JPermissionEnum.LOOK.getKey(), "1");
			} else {
				model.addAttribute(JPermissionEnum.LOOK.getKey(), "0");
			}
		}
		//为空，说明当前选择的是目录
		else {
			model.addAttribute(JPermissionEnum.LOOK.getKey(), "0");
		}
	}
	
	/**
	 * 判断是否具有打印文件权限
	 */
	public static void printPermission(Model model, SYSUser user, DMS_FOLDER folder, String permission) {
		//如果目录为空，则选择的是根目录，则不能进行分配权限
		if(folder == null) {
			model.addAttribute(JPermissionEnum.PRINT.getKey(), "0");
			return;
		} 
		//如果选择的为目录或者文件，则根据权限控制是否可以分配权限
		else {
			//如果用户为管理员，则具有全部权限，如果不是管理员，如果有管理权限
			if(user.getUSER_ISADMIN().equals("1") || judgePermission(permission, "P")) {
				model.addAttribute(JPermissionEnum.PRINT.getKey(), "1");
			}
			//否则没有权限
			else {
				model.addAttribute(JPermissionEnum.PRINT.getKey(), "0");
			}
		}		
	}
}
