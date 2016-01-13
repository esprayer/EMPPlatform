<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<form id="pagerForm" method="post" action="/management/book/edit/${book.id}">
	<input type="hidden" name="pageNum" value="1" />
</form>

<div class="pageContent">
<form method="post" action="/management/book/update?navTabId=bookLiNav" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
	<input type="hidden" name="id" value="${book.id}"/>
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>ISBN: </label>
			<input type="text" name="sn" value="${book.sn}" class="required" maxlength="20"/>
		</p>
		<p>
			<label>中文书名: </label>
			<input type="text" name="nameCn" value="${book.nameCn}" class="required"/>
		</p>
		<p>
			<label>英文书名: </label>
			<input type="text" name="nameEn" value="${book.nameEn}"/>
		</p>
		<p>
			<label>出版社: </label>
			<input type="text" name="publish" value="${book.publish}" class="required"/>
		</p>
		<p>
			<label>出版日期: </label>
			<input type="text" name="publishDate" class="date required" readonly="readonly" value="<fmt:formatDate value="${book.publishDate}" pattern="yyyy-MM-dd"/>"/>
			<a href="javascript:;" class="inputDateButton">选择</a>
		</p>
		<p>
			<label>创建时间: </label>
			<fmt:formatDate value="${book.insertDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</p>
		<p>
			<label>修改时间: </label>
			<fmt:formatDate value="${book.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</p>
		
		<h3 class="contentTitle">篇章列表</h3>
		<a class="button" target="dialog" mask="true" width="800" height="400" href="${ctx}/management/chapter/add/${book.id}"><span>添加篇章</span></a>
		
		<div id="chapterLiBox" style="clear:both">
			<c:import url="../chapter/list.jsp"></c:import>
		</div>
	
	</div>

	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">退出</button></div></div></li>
		</ul>
	</div>
</form>
</div>