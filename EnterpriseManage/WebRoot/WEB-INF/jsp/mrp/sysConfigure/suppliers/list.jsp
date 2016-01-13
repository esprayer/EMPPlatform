<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="/sysConfigure/suppliers">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/sysConfigure/suppliers/search"  class="pageForm" onsubmit="return navTabSearch(this,'suppliersLiNav')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>供应商基本信息</h1>
			<div>
				供应商编号：<input type="text" name="keywords" value="${param.keywords}"/>
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
			<li><a class="add" target="dialog" mask="true" width="420" height="400" rel="objectNav" href="${ctx}/sysConfigure/suppliers/add" title="添加数据对象"><span>添加</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="410" rel="objectNav" href="${ctx}/sysConfigure/suppliers/edit/{slt_objId}"  warn="请选择需要编辑的供应商信息！"><span>编辑</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/sysConfigure/suppliers/delete/{slt_objId}?navTabId=suppliersLiNav" title="你确定要删除吗?"  warn="请选择需要删除的供应商信息！"><span>删除</span></a></li>
			<li class="line">line</li>								
		</ul>
	</div>
	<table class="table" width="100%" layoutH="161">
		<thead>
			<tr>
				<th width="15"></th>
				<th width="60">供应商编号</th>
				<th width="60">供应商名称</th>
				<th width="60">供应商简称</th>
				<th width="60">联系人</th>
				<th width="60">联系电话</th>
				<th width="60">传真</th>
				<th width="60">地址</th>
				<th width="70">使用状态</th>
				<th width="70">创建时间</th>
				<th width="70">修改时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${hydwList}" varStatus="s">
			<tr target="slt_objId" rel="${item.f_DWBH}">
				<td>${s.index + 1}</td>
				<td>${item.f_DWBH}</td>
				<td>${item.f_DWMC}</td>				
				<td>${item.f_DWJC}</td>
				<td>${item.f_LXR}</td>		
				<td>${item.f_LXDH}</td>
				<td>${item.f_CZ}</td>		
				<td>${item.f_DZ}</td>	
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