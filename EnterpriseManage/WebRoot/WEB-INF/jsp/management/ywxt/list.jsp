<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="/standard/metadata/object">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/management/base/ywxt/search"  class="pageForm" onsubmit="return navTabSearch(this,'ywxtLiNav')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>业务系统信息</h1>
			<div>
				业务系统：<input type="text" name="keywords" value="${param.keywords}"/>
				<ul class="rightTools">
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
		<div class="divider"></div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="dialog" mask="true" width="420" height="400" rel="ywxtLiNav" href="${ctx}/management/base/ywxt/add" title="添加数据对象"><span>添加</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="400" rel="ywxtLiNav" href="${ctx}/management/base/ywxt/edit/{slt_objId}" title="编辑数据对象"><span>编辑</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/management/base/ywxt/delete/{slt_objId}?navTabId=ywxtLiNav" title="你确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>								
		</ul>
	</div>
	<table class="table" width="100%" layoutH=161>
		<thead>
			<tr>
				<th width="15">序号</th>
				<th width="30">系统编号</th>
				<th width="50">系统名称</th>
				<th width="60">系统描述</th>
				<th width="50">系统图标</th>
				<th width="20">语言</th>
				<th width="30">使用状态</th>
				<th width="50">创建时间</th>
				<th width="50">修改时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${ywxtList}" varStatus="s">
				<tr target="slt_objId" rel="${item.APP_ID}">
					<td>${s.index + 1}</td>
					<td>${item.APP_ID}</td>
					<td>${item.APP_NAME}</td>				
					<td>${item.APP_DES}</td>
					<td>${item.APP_ICON}</td>		
					<td>${item.LAN_ID}</td>
					<td>
					<c:choose>
						<c:when test="${item.f_SYZT=='1'}">
							启用
						</c:when>
						<c:when test="${item.f_SYZT=='0'}">
							停用
						</c:when>
					</c:choose>
					</td>
					<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>		
					<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>