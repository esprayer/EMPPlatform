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

<form method="post" rel="pagerForm" action="${ctx}/management/base/role/search"  class="pageForm" onsubmit="return navTabSearch(this,'roleLiNav')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>角色信息</h1>
			<div>
				角色：<input type="text" name="keywords" value="${param.keywords}"/>
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
			<li><a class="add" target="dialog" mask="true" width="420" height="300" rel="roleLiNav" href="${ctx}/management/base/role/add" title="添加数据对象"><span>添加</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="400" rel="roleLiNav" href="${ctx}/management/base/role/edit/{slt_objId}" title="编辑数据对象"><span>编辑</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/management/base/role/delete/{slt_objId}?navTabId=roleLiNav" title="你确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>								
		    <li><a class="add" target="navTab" rel="metadataColNav" href="${ctx}/management/base/role/assignUser/{slt_objId}" title="分派角色"><span>分派用户</span></a></li>
		    <li><a class="edit" target="navTab" rel="metadataColNav" href="${ctx}/management/base/role/assignGnqx/{slt_objId}" title="角色权限"><span>角色权限</span></a></li>
			<li class="line">line</li>										
		</ul>
	</div>
	<table class="table" width="100%" layoutH="161">
		<thead>
			<tr>
				<th width="25">序号</th>
				<th width="60">角色编号</th>
				<th width="60">角色名称</th>
				<th width="30">级数</th>
				<th width="30">明细</th>
				<th width="70">备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${roleList}" varStatus="s">
				<tr target="slt_objId" rel="${item.roleId}">
					<td>${s.index + 1}</td>
					<td>${item.roleId}</td>
					<td>${item.roleName}</td>				
					<td>${item.js}</td>
					<td>${item.mx}</td>		
					<td>${item.note}</td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>