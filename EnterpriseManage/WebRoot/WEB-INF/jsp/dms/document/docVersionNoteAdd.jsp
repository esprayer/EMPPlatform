<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<script type="text/javascript" src="http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js"></script>
<script>
	function getVersionNoteAddData() {
		return $("#versionNoteAddData");
	}
</script>
<div class="pageContent">
	<form id="versionNoteAddData" method="post" action="${ctx}/dms/docNote/insert?navTabId=docComments&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56" align="center">
			<dl>
			<dt align="left">评论内容：</dt>
			<dd><textarea name="F_NOTE" cols="30" rows="13"></textarea></dd>
			<input type="hidden" id="realPath" name="realPath" value="${realPath}" />
			<input type="hidden" id="inputPath" name="inputPath" value="${inputPath}" />
		</dl>
		</div>
		<div class="formBar">
			<ul>		
				<li><a class="buttonActive" target="ajaxTodo" rel="docVersionLiBox" getAjaxData="getVersionNoteAddData()" ajaxData="1" showTitle="0" callback="divReload" href="${ctx}/dms/doc/saveVersionNote?callbackType=closeCurrent&F_DOCID=${F_DOCID}&FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&backVersion=${backVersion}&forwardHead=${ctx}/dms"><span>保存</span></a></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
</div>