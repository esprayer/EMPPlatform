<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/management/qxgl/role/update?rel=roleLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>菜单编号: </label>
			<input type="text" name="MENU_BH" value="${menuObject.MENU_BH}" class="required"/>
		</p>
		<p>
			<label>菜单名称: </label>
			<input type="text" name="MENU_MC" value="${menuObject.MENU_MC}" class="required"/>
		</p>
		<p>
			<label>模块编号: </label>
			<input type="text" name="MODULE_BH" value="${menuObject.MODULE_BH}" class="required"/>
		</p>
		<p>
			<label>应用编号: </label>
			<input type="text" name="APP_ID" value="${menuObject.APP_ID}" class="required"/>
		</p>
		<p>
			<label>级数: </label>
			<input type="text" name="F_JS" value="${menuObject.f_JS}" class="required"/>
		</p>
		<p>
			<label>明细: </label>
			<input type="text" name="F_MX" value="${menuObject.f_MX}" class="required"/>
		</p>
		<p>
			<label>父节点: </label>
			<input type="text" name="F_PARENT" value="${menuObject.f_PARENT}" class="required"/>
		</p>
		<p>
			<label>顺序: </label>
			<input type="text" name="F_DISP" class="digits" value="${menuObject.f_DISP}" />
		</p>
		<p>
			<label>URL: </label>
			<input type="text" name="URL" value="${menuObject.URL}" />
		</p>
		<p>
			<label>图标: </label>
			<input type="text" name="IMAGE" value="${menuObject.IMAGE}" />
		</p>
		<p>
			<label>快捷键: </label>
			<input type="text" name="SHORTCUT" value="${menuObject.SHORTCUT}" />
		</p>
		<p>
			<label>target: </label>
			<input type="text" name="F_TARGET" value="${menuObject.f_TARGET}" />
		</p>	
		<p>
			<label>rel: </label>
			<input type="text" name="F_REL" value="${menuObject.f_REL}" />
		</p>	
		<p>
			<label>属性: </label>
			<input type="text" name="F_ATTR" value="${menuObject.f_ATTR}" />
		</p>
		<p>
			<label>创建日期: </label>
			<input type="text" name="F_CRDATE" value="${menuObject.f_CRDATE}" />
		</p>	
		<p>
			<label>修改日期: </label>
			<input type="text" name="F_CHDATE" value="${menuObject.f_CHDATE}" />
		</p>					
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>