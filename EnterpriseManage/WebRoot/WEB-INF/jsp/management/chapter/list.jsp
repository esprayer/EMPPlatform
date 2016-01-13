<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<table class="list" width="100%">
	<thead>
		<tr>
			<th>篇号</th>
			<th>中文篇名</th>
			<th>英文篇名</th>
			<th>起始页</th>
			<th>总页数</th>
			<th>创建时间</th>
			<th width="80">操作</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="item" items="${chapters}" varStatus="s">
		<tr>
			<td>${item.chapterNo}</td>
			<td>${item.nameCn}</td>
			<td>${item.nameEn}</td>
			<td>${item.startPageNo}</td>
			<td>${item.pageCount}</td>
			<td><fmt:formatDate value="${item.insertDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
				<a target="dialog" mask="true" width="800" height="400" href="${ctx}/management/chapter/edit/${item.id}">编辑</a>
				<a target="ajaxTodo" rel="chapterLiBox" href="${ctx}/management/chapter/delete/${item.id}" title="你确定要删除吗?">删除</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>