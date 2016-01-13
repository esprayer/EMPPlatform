<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="/management/chapter/insert?navTabId=bookNav&callbackType=closeCurrent" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);">
	<input type="hidden" name="bookId" value="${book.id}"/>
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>书目: </label>
			${book.nameCn}
		</p>
		<!-- <p>
			<label>AuthorId: </label>
			<input type="text" name="authorId" />
		</p> -->
		<p>
			<label>译者: </label>
			<input type="text" name="translator" class="required"/>
		</p>
		<p>
			<label>篇号: </label>
			<input type="text" name="chapterNo" class="required digits" min="1" max="2000"/>
		</p>
		<p>
			<label>中文篇名: </label>
			<input type="text" name="nameCn" class="required"/>
		</p>
		<p>
			<label>英文篇名: </label>
			<input type="text" name="nameEn" />
		</p>
		<p>
			<label>摘要: </label>
			<input type="text" name="summary" />
		</p>
		<p>
			<label>关键词: </label>
			<input type="text" name="keywords" />
		</p>
		<p>
			<label>起始页: </label>
			<input type="text" name="startPageNo" class="required digits"/>
		</p>
		<p>
			<label>总页数: </label>
			<input type="text" name="pageCount" class="required digits"/>
		</p>
		<p>
			<label>PDF: </label>
			<input type="file" name="file" class="required"/>
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