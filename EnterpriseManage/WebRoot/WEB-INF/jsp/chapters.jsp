<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include.inc.jsp"%>
<div class="pageTitle">
	<ul class="crumbs">
		<li><a href="/">首页</a></li>
		<li>期刊</li>
	</ul>
	<h1>期刊</h1>
	
	<form action="${ctx}/chapters" method="post">
	<div class="search">
		<span>搜索：</span>
		<input class="q" name="keywords" type="text" value="${param.keywords}" />
		<input type="submit" value="搜索" />
	</div>
	</form>
</div>

<div id="main">
	<table class="plist">
		<thead>
			<tr>
				<th>篇名</th>
				<th width="280">刊名</th>
				<th width="150">作者</th>
				<th width="100">出版日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${chapters}" varStatus="s">
				<tr>
					<td><a href="/chapter/${item.id}">${item.nameCn}</a></td>
					<td>${item.bookNameCn}</td>
					<td>${item.authorId}</td>
					<td><fmt:formatDate value="${item.insertDate}" pattern="yyyy-MM-dd"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>