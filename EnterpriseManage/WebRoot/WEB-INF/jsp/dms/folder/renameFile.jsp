<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<script>
	function getRenameFileName() {
		return $("#renameFileForm");
	}
</script>
<div class="pageContent">
	<form id="renameFileForm" method="post" action="${ctx}/dms/docNote/insert?navTabId=docComments&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<c:if test="${renameType=='folder'}">
					<label>目录名: </label>
				</c:if>
				<c:if test="${renameType=='doc'}">
					<label>文件名: </label>
				</c:if>
				
				<input type="text" name="fileName" value="${fileName}" class="required"/>
			</p>
			<input type="hidden" id="realPath" name="realPath" value="${realPath}" />
			<input type="hidden" id="inputPath" name="inputPath" value="${inputPath}" />
			<input type="hidden" id="FOLDER_ID" name="FOLDER_ID" value="${FOLDER_ID}" />
			<input type="hidden" id="inputPath" name="NODETYPE" value="${NODETYPE}" />
			<input type="hidden" id="F_DOCID" name="F_DOCID" value="${F_DOCID}" />
			<input type="hidden" id="renameType" name="renameType" value="${renameType}" />
		</div>
		<div class="formBar">
			<ul>					
				<li><a class="buttonActive" target="ajaxTodo" rel="folderDocLiNav" getAjaxData="getRenameFileName()" ajaxData="1" showTitle="0" callback="divReload" href="${ctx}/dms/doc/saveFileName?callbackType=closeCurrent&forwardHead=${ctx}/dms"><span>保存</span></a></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
</div>