<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="/sysConfigure/material">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/sysConfigure/material/search"  class="pageForm" onsubmit="return navTabSearch(this,'materialLiNav')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>电气材料基本信息</h1>
			<div>
				材料编号：<input type="text" name="keywords" value="${param.keywords}"/>
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
			<li><a class="add" target="dialog" mask="true" width="420" height="450" rel="objectNav" href="${ctx}/sysConfigure/material/add" title="添加材料"><span>添加</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="530" rel="objectNav" href="${ctx}/sysConfigure/material/edit/{slt_objId}" title="编辑材料" warn="请选择需要编辑的电气材料信息！"><span>编辑</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/sysConfigure/material/delete/{slt_objId}?navTabId=materialLiNav" title="你确定要删除该材料吗?"  warn="请选择需要删除的电气材料信息！"><span>删除</span></a></li>
			<li class="line">line</li>								
		</ul>
	</div>
	<table class="table" width="100%" layoutH="161">
		<thead>
			<tr>
				<th width="25">序号</th>
				<th width="60">材料编号</th>
				<th width="60">材料名称</th>
				<th width="60">规格型号</th>
				<th width="60">计量单位</th>
				<th width="60">供应商名称</th>
				<th width="60">厂商名称</th>
				<th width="60">录入人名称</th>
				<th width="70">使用状态</th>
				<th width="70">创建时间</th>
				<th width="70">修改时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${hyclList}" varStatus="s">
			<tr target="slt_objId" rel="${item.f_CLBH}">
				<td>${s.index + 1}</td>
				<td>${item.f_CLBH}</td>
				<td>${item.f_CLMC}</td>				
				<td>${item.f_GGXH}</td>
				<td>${item.f_JLDW}</td>		
				<td>${item.f_DWMC}</td>
				<td>${item.f_CSMC}</td>			
				<td>${item.f_LRRXM}</td>	
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