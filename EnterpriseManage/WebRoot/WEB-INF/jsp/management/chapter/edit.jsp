<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="/management/chapter/update?navTabId=bookNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="id" value="${chapter.id}"/>
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>书目: </label>
			${book.nameCn}
		</p>
		<%-- <p>
			<label>AuthorId: </label>
			<input type="text" name="authorId" value="${chapter.authorId}"/>
		</p> --%>
		<p>
			<label>译者: </label>
			<input type="text" name="translator" value="${chapter.translator}" class="required"/>
		</p>
		<p>
			<label>篇号: </label>
			<input type="text" name="chapterNo" value="${chapter.chapterNo}" class="required digits" min="1" max="2000"/>
		</p>
		<p>
			<label>中文篇名: </label>
			<input type="text" name="nameCn" value="${chapter.nameCn}" class="required"/>
		</p>
		<p>
			<label>英文篇名: </label>
			<input type="text" name="nameEn" value="${chapter.nameEn}"/>
		</p>
		<p>
			<label>摘要: </label>
			<input type="text" name="summary" value="${chapter.summary}" />
		</p>
		
		<p>
			<label>关键词: </label>
			<input type="text" name="keywords" value="${chapter.keywords}" />
		</p>
		<p>
			<label>起始页: </label>
			<input type="text" name="startPageNo" value="${chapter.startPageNo}" class="required digits"/>
		</p>
		<p>
			<label>总页数: </label>
			<input type="text" name="pageCount" value="${chapter.pageCount}" class="required digits"/>
		</p>
		<p>
			<label>创建时间: </label>
			<fmt:formatDate value="${chapter.insertDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</p>
		<p>
			<label>修改时间: </label>
			<fmt:formatDate value="${chapter.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</p>
		<p>
			<label>Path: </label>
			${chapter.path}
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