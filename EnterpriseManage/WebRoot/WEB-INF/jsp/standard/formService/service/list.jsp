<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="/standard/metadata/object">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/standard/formService/search"  class="pageForm" onsubmit="return navTabSearch(this,'formServiceLiNav')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>业务服务基本信息</h1>
			<div>
				服务表单编号：<input type="text" name="BBZD_BH" value="${BBZD_BH}"/>
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
			<li><a class="add" target="dialog" mask="true" width="420" height="200" rel="objectNav" href="${ctx}/standard/formService/add" title="添加业务服务"><span>添加</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="200" rel="objectNav" href="${ctx}/standard/formService/edit/{slt_objId}" title="编辑业务服务"><span>编辑</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/standard/formService/delete/{slt_objId}?navTabId=formServiceLiNav" title="你确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="navTab" rel="metadataColNav" href="${ctx}/standard/formService/serviceList/{slt_objId}" title="服务插件"><span>服务插件</span></a></li>						
		</ul>
	</div>
	<table class="table" width="100%" layoutH="161">
		<thead>
			<tr>
				<th width="60">服务编号</th>
				<th width="60">服务名称</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${dbformList}" varStatus="s">
			<tr target="slt_objId" rel="${item.BBZD_BH }">
				<td>${item.BBZD_BH}</td>
				<td>${item.BBZD_MC}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<c:import url="../_frag/pager/panelBar.jsp"></c:import>
</div>