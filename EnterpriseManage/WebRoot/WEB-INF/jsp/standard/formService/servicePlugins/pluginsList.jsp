<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="${ctx}/standard/formService/plugins/serviceList/${BBZD_BH}?serviceID=${serviceID}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<div class="pageContent">
	<div class="panel" defH="25">
		<h1>业务服务基本信息</h1>
		<div>
			服务表单编号：<input type="text" name="BBZD_BH" value="${BBZD_BH}"/>
			服务表单名称：<input type="text" name="BBZD_MC" value="${BBZD_MC}"/>
		</div>
	</div>
	<div class="divider"></div>
</div>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="dialog" mask="true" width="420" height="200" rel="objectNav" href="${ctx}/standard/formService/plugins/add/${BBZD_BH}?serviceID=${serviceID}" title="添加业务服务"><span>添加</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="200" rel="objectNav" href="${ctx}/standard/formService/plugins/edit/{slt_objId}&BBZD_BH=${BBZD_BH}&serviceID=${serviceID}" title="编辑业务服务"><span>编辑</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/standard/formService/plugins/servicePlugins/delete/{slt_objId}&rel=servicePluginsLiBox&BBZD_BH=${BBZD_BH}&serviceID=${serviceID}" title="你确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="ajaxTodo" href="${ctx}/standard/formService/plugins/servicePlugins/move/{slt_objId}&rel=servicePluginsLiBox&BBZD_BH=${BBZD_BH}&serviceID=${serviceID}&order=1" title="向上移动?"><span>向上移动</span></a></li>
			<li><a class="icon" target="ajaxTodo" href="${ctx}/standard/formService/plugins/servicePlugins/move/{slt_objId}&rel=servicePluginsLiBox&BBZD_BH=${BBZD_BH}&serviceID=${serviceID}&order=-1" title="向下移动"><span>向下移动</span></a></li>					
		</ul>
	</div>
	<table class="table" width="100%" layoutH="161">
		<thead>
			<tr>
				<th width="60">插件编号</th>
				<th width="60">插件名称</th>
				<th width="100">插件作用范围</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${prepare}" varStatus="s">
			<tr target="slt_objId" rel="${item.compID}?compScope=${item.compScope}">
				<td>${item.compID}</td>
				<td>${item.compName}</td>
				<td>
					<c:if test="${item.compScope==1}">准备阶段</c:if>
					<c:if test="${item.compScope==2}">执行阶段</c:if>
					<c:if test="${item.compScope==3}">结束阶段</c:if>
				</td>
			</tr>
		</c:forEach>
		<c:forEach var="item" items="${execute}" varStatus="s">
			<tr target="slt_objId" rel="${item.compID}?compScope=${item.compScope}">
				<td>${item.compID}</td>
				<td>${item.compName}</td>
				<td>
					<c:if test="${item.compScope==1}">准备阶段</c:if>
					<c:if test="${item.compScope==2}">执行阶段</c:if>
					<c:if test="${item.compScope==3}">结束阶段</c:if>
				</td>
			</tr>
		</c:forEach>
		<c:forEach var="item" items="${finish}" varStatus="s">
			<tr target="slt_objId" rel="${item.compID}?compScope=${item.compScope}">
				<td>${item.compID}</td>
				<td>${item.compName}</td>
				<td>
					<c:if test="${item.compScope==1}">准备阶段</c:if>
					<c:if test="${item.compScope==2}">执行阶段</c:if>
					<c:if test="${item.compScope==3}">结束阶段</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<c:import url="../_frag/pager/panelBar.jsp"></c:import>
</div>