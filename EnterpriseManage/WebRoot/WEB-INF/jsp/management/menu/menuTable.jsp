<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="${ctx}/management/base/menu/${USER_ORGID}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="dialog" mask="true" width="420" height="400" rel="objectNav" href="${ctx}/management/base/menu/add/${parent}?APP_ID=${APP_ID}" title="添加菜单" addTip="请选择菜单"><span>添加菜单</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="410" rel="objectNav" href="${ctx}/management/base/menu/edit/{slt_objId}?APP_ID=${APP_ID}" title="编辑菜单"><span>编辑菜单</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/sysConfigure/user/delete/{slt_objId}?rel=userLiBox&USER_ORGID=${APP_ID}" title="你确定要删除该用户吗?"><span>删除菜单</span></a></li>							
		</ul>
	</div>
	<table class="table" width="1600" layoutH="78" rel="menuLiNav">
		<thead>
			<tr>
				<th width="25">序号</th>
				<th width="60">菜单编号</th>
				<th width="120">菜单名称</th>
				<th width="50">模块编号</th>
				<th width="50">应用编号</th>
				<th width="30">级数</th>
				<th width="30">明细</th>
				<th width="50">父节点</th>
				<th width="25">顺序</th>
				<th width="120">URL</th>
				<th width="50">图标</th>
				<th width="30">快捷键</th>
				<th width="70">target</th>
				<th width="70">rel</th>
				<th width="120">其他属性</th>
				<th width="70">创建日期</th>
				<th width="70">修改日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${menuList}" varStatus="s">
				<tr target="slt_objId" rel="${item.MENU_BH}">
					<td>${s.index + 1}</td>
					<td>${item.MENU_BH}</td>
					<td>${item.MENU_MC}</td>				
					<td>${item.MODULE_BH}</td>
					<td>${item.APP_ID}</td>		
					<td>${item.f_JS}</td>
					<td>${item.f_MX}</td>
					<td>${item.f_PARENT}</td>
					<td>${item.f_DISP}</td>				
					<td>${item.URL}</td>
					<td>${item.IMAGE}</td>		
					<td>${item.SHORTCUT}</td>
					<td>${item.f_TARGET}</td>
					<td>${item.f_REL}</td>				
					<td>${item.f_ATTR}</td>
					<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>