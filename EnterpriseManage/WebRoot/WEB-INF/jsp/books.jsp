<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include.inc.jsp"%>

<h1 class="pagetitle">书目列表</h1>
<table class="list" width="100%">
	<thead>
		<tr>
			<th width="50"></th>
			<th>ISBN</th>
			<th>中文名</th>
			<th>英文名</th>
			<th width="100">出版社</th>
			<th width="100">出版日期</th>
			<th width="130">创建时间</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="item" items="${bookList}" varStatus="s">
		<tr target="slt_objId" rel="${item.id }">
			<td>${s.index + 1}</td>
			<td>${item.sn}</td>
			<td><a href="/book/${item.id}">${item.nameCn}</a></td>
			<td>${item.nameEn}</td>
			<td>${item.publish}</td>
			<td><fmt:formatDate value="${item.publishDate}" pattern="yyyy-MM-dd"/></td>
			<td><fmt:formatDate value="${item.insertDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</c:forEach>
	</tbody>
</table>