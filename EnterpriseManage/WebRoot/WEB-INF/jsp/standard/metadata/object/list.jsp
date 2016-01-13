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

<form method="post" rel="pagerForm" action="${ctx}/standard/metadata/object/search"  class="pageForm" onsubmit="return navTabSearch(this,'metadataLiNav')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>数据对象基本信息</h1>
			<div>
				对象标识：<input type="text" name="keywords" value="${param.keywords}"/>
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
			<li><a class="add" target="dialog" mask="true" width="420" height="300" rel="objectNav" href="${ctx}/standard/metadata/object/add" title="添加数据对象"><span>添加</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="400" rel="objectNav" href="${ctx}/standard/metadata/object/edit/{slt_objId}" title="编辑数据对象"><span>编辑</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/standard/metadata/object/delete/{slt_objId}?navTabId=metadataLiNav" title="你确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="navTab" rel="metadataColNav" href="${ctx}/standard/metadata/objcols/{slt_objId}" title="对象列属性"><span>对象列属性</span></a></li>						
		</ul>
	</div>
	<table class="table" width="100%" layoutH="161">
		<thead>
			<tr>
				<th width="15"></th>
				<th width="60">对象标识</th>
				<th width="60">对象名称</th>
				<th width="60">对象描述</th>
				<th width="60">对象类型</th>
				<th width="60">应用类型</th>
				<th width="60">系统标识</th>
				<th width="60">对象状态</th>
				<th width="70">创建日期</th>
				<th width="70">修改时间</th>
				<th width="30">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${metaList}" varStatus="s">
			<tr target="slt_objId" rel="${item.OBJ_ID }">
				<td>${s.index + 1}</td>
				<td>${item.OBJ_ID}</td>
				<td>${item.OBJ_MC}</td>
				<td>${item.OBJ_DES}</td>
				<td>
					<c:if test="${item.OBJ_TYPE=='T'}">表</c:if>
					<c:if test="${item.OBJ_TYPE=='V'}">视图</c:if>
					<c:if test="${item.OBJ_TYPE=='P'}">存储过程</c:if>
				
				</td>
				<td>
					<c:if test="${item.OBJ_APPTYPE=='SYS'}">系统表</c:if>
					<c:if test="${item.OBJ_APPTYPE=='DIM'}">维表</c:if>
					<c:if test="${item.OBJ_APPTYPE=='MOD'}">事实表</c:if>
					<c:if test="${item.OBJ_APPTYPE=='RLT'}">关系表</c:if>
					<c:if test="${item.OBJ_APPTYPE=='CFG'}">配置表</c:if>
					
				</td>
				<td>${item.SYS_ID}</td>
				<td>
					<c:if test="${item.f_STAU==0}">未创建</c:if>
					<c:if test="${item.f_STAU==1}">已创建</c:if>
				</td>
				<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:if test="${item.f_STAU==0}">
						<a title="是否创建对象？" target="ajaxTodo" href="${ctx}/standard/metadata/object/createObject/{slt_objId}?navTabId=metadataLiNav" class="btnEdit">创建</a>
					</c:if>
					<c:if test="${item.f_STAU==1}">
						<a title="是否删除删除？" target="ajaxTodo" href="${ctx}/standard/metadata/object/deleteObject/{slt_objId}?navTabId=metadataLiNav" class="btnDel">删除</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<c:import url="../_frag/pager/panelBar.jsp"></c:import>
</div>