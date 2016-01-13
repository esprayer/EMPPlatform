<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/management/base/role/insert/${roleObject.roleId}?navTabId=roleLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>角色ID: </label>
			<input type="text" name="roleId" value="${roleObject.roleId}" class="required"/>
		</p>
		<p>
			<label>角色名称: </label>
			<input type="text" name="roleName" value="${roleObject.roleName}" class="required"/>
		</p>
		<p>
			<label>级数: </label>
			<input type="text" name="js" value="${roleObject.js}" class="required"/>
		</p>
		<p>
			<label>明细: </label>
			<input type="text" name="mx" value="${roleObject.mx}" class="required"/>
		</p>
		<p>
			<label>父节点: </label>
			<input type="text" name="parent" value="${roleObject.parent}" class="required"/>
		</p>
		<p>
			<label>备注: </label>
			<input type="text" name="note" value="${roleObject.note}" />
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