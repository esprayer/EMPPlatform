<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<script>
	function getDocNoteAjaxData() {
		return $("#docNoteForm");
	}
</script>

<div class="pageContent">
	<form id="docNoteForm" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
				<p>
					<label>评论内容: </label>
					<input type="text" name="F_MS" value="${note.f_MS}" class="required"/>
				</p>
			</div>
			<div class="formBar">
				<ul>		
					<li><a class="buttonActive" target="ajaxTodo" rel="docAudit" getAjaxData="getDocNoteAjaxData()" ajaxData="1" showTitle="0" callback="divReload" href="${ctx}/dms/docNote/save?rel=docComments&callbackType=closeCurrent&F_DOCID=${note.f_DOCID}&forwardUrl=${ctx}/dms/docNote/load?F_DOCID=${note.f_DOCID}&F_ID=${note.f_ID}&pageBreak=0"><span>保存</span></a></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
				</ul>
			</div>
	</form>
</div>