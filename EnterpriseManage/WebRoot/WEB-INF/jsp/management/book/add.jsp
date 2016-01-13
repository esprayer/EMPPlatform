<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ctx}/management/book/insert?navTabId=bookLiNav" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
	<div class="pageFormContent" layoutH="56">

		<p>
			<label>ISBN: </label>
			<input type="text" name="sn" class="required" maxlength="20"/>
		</p>
		<p>
			<label>中文名: </label>
			<input type="text" name="nameCn" class="required"/>
		</p>
		<p>
			<label>英文名: </label>
			<input type="text" name="nameEn"/>
		</p>
		<p>
			<label>出版社: </label>
			<input type="text" name="publish" class="required"/>
		</p>
		<p>
			<label>出版日期: </label>
			<input type="text" name="publishDate" class="date required" readonly="readonly"/>
			<a href="javascript:;" class="inputDateButton">选择</a>
		</p>

	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">Save</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">Close</button></div></div></li>
		</ul>
	</div>
</form>
</div>
