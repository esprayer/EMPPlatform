<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<script>
	function getDocAjaxData() {
		return $("#docForm");
	}
</script>

<div class="pageContent">
<form id="docForm" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>目录名称: </label>
			<input type="text" name="F_WDMC" class="required"/>
		</p>
		<p>
			<label>目录描述: </label>
			<input type="text" name="F_WDMS"/>
		</p>				
	</div>
	<div class="formBar">
		<ul>
			<li><a class="buttonActive" target="ajaxTodo" rel="folderDocDiv" getAjaxData="getDocAjaxData()" ajaxData="1" showTitle="0" callback="divReload" href="${ctx}/dms/folder/save?callbackType=closeCurrent&rel=folderDocDiv&&inputPath=@${inputPath}@&realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&forwardUrl=${ctx}/dms/folder/loadFolderById/${NODETYPE}"><span>保存</span></a></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>