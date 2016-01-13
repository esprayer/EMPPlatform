<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="/standard/metadata/object">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/dailyBusiness/excelStatements/product/search/${F_XMBH}"  class="pageForm" onsubmit="return navTabSearch(this,'statementsProductLiNav')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>项目申请单产品基本信息</h1>
			<div>
				产品编号：<input type="text" name="F_CPBH" value="${F_CPBH}"/>
				<ul class="rightTools">
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
		<div class="divider"></div>
	</div>
</form>

<div class="pageContent">

	<c:if test="${F_XMZT==0}">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="add" target="dialog" mask="true" width="420" height="300" rel="objectNav" href="${ctx}/dailyBusiness/excelStatements/product/add/${F_XMBH}" title="添加产品"><span>添加产品</span></a></li>				
				<li><a class="delete" target="ajaxTodo" href="${ctx}/sysConfigure/project/product/delete/{slt_objId}?navTabId=statementsProductLiNav&F_XMBH=${F_XMBH}" title="你确定要删除该产品吗?"><span>删除产品</span></a></li>
				<li class="line">line</li>								
			</ul>
		</div>
		<table class="table" width="100%" layoutH="161">
	</c:if>
	
	<c:if test="${F_XMZT==1}">
		<table class="table" width="100%" layoutH="133">
	</c:if>
		<thead>
			<tr>
				<th width="15"></th>
				<th width="60">产品编号</th>
				<th width="60">产品名称</th>				
				<th width="70">创建时间</th>
				<th width="70">修改时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${productList}" varStatus="s">
			<tr target="slt_objId" rel="${item.f_CPBH}">
				<td>${s.index + 1}</td>
				<td>${item.f_CPBH}</td>
				<td>${item.f_CPMC}</td>				
				<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>