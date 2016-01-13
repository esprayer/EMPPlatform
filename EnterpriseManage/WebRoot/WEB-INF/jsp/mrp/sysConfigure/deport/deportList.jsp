<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="${ctx}/sysConfigure/deport/${F_BMBH}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/sysConfigure/deport/search/${F_BMBH}"  class="pageForm" onsubmit="return divSearch(this,'deportLiBox')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>仓库基本信息</h1>
			<div>
				仓库编号：<input type="text" name="keywords" value="${param.keywords}"/>
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
			<li><a class="add" target="dialog" mask="true" width="420" height="400" rel="objectNav" href="${ctx}/sysConfigure/deport/add/${F_BMBH}" title="添加仓库" warn="请选择一个部门！"><span>添加仓库</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="410" rel="objectNav" href="${ctx}/sysConfigure/deport/edit/{slt_objId}?F_BMBH=${F_BMBH}" title="编辑仓库" warn="请选择需要编辑的仓库信息！"><span>编辑仓库</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/sysConfigure/deport/delete/{slt_objId}?rel=deportLiBox&F_BMBH=${F_BMBH}" title="你确定要删除该仓库吗?" warn="请选择需要删除的仓库信息！"><span>删除仓库</span></a></li>
			<li class="line">line</li>								
		</ul>
	</div>
	<table class="table" width="100%" layoutH="161" rel="deportLiNav">
		<thead>
			<tr>
				<th width="25">序号</th>
				<th width="60">仓库编号</th>
				<th width="60">仓库名称</th>
				<th width="60">管理员编号</th>
				<th width="60">管理员名称</th>
				<th width="60">描述</th>
				<th width="70">使用状态</th>
				<th width="70">创建时间</th>
				<th width="70">修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${deportList}" varStatus="s">
				<tr target="slt_objId" rel="${item.f_CKBH}">
					<td>${s.index + 1}</td>
					<td>${item.f_CKBH}</td>
					<td>${item.f_CKMC}</td>				
					<td>${item.f_ZGBH}</td>
					<td>${item.f_ZGMC}</td>	
					<td>${item.f_MS}</td>							
					<td>
						<c:if test="${item.f_SYZT==0}">停用</c:if>
						<c:if test="${item.f_SYZT==1}">启用</c:if>
					</td>
					<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>