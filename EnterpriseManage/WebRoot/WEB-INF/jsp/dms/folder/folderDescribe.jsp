<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<form method="post" action="${ctx}/dms/folder/saveFolderDescription?FOLDER_ID=${folder.f_FOLDERID}" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent">
		<p>
			<label>目录描述: </label>
			<input type="text" name="F_DESC" value="${folder.f_DESC}" class="required"/>
		</p>				
	</div>
	<c:if test="${createFlag==1}">
		<c:if test="${NODETYPE=='0' || NODETYPE=='1'}">
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>						
				</ul>
			</div>
		</c:if>						
	</c:if>
</form>