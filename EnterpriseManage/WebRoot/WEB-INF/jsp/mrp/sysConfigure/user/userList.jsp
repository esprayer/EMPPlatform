<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="${ctx}/sysConfigure/user/${F_SXBM}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/sysConfigure/user/search/${F_SXBM}"  class="pageForm" onsubmit="return divSearch(this,'userLiBox')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>用户基本信息</h1>
			<div>
				用户编号：<input type="text" name="keywords" value="${param.keywords}"/>
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
			<li><a class="add" target="dialog" mask="true" width="420" height="400" rel="objectNav" href="${ctx}/sysConfigure/user/add/${F_SXBM}" title="添加用户" warn="请选择一个部门！"><span>添加用户</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="410" rel="objectNav" href="${ctx}/sysConfigure/user/edit/{slt_objId}?F_SXBM=${F_SXBM}" title="编辑用户" warn="请选择需要编辑的用户信息！"><span>编辑用户</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/sysConfigure/user/delete/{slt_objId}?rel=userLiBox&F_SXBM=${F_SXBM}" title="你确定要删除该用户吗?" warn="请选择需要删除的用户信息！"><span>删除用户</span></a></li>
			<li class="line">line</li>								
		</ul>
	</div>
	<table class="table" width="100%" layoutH="161" rel="userLiNav">
		<thead>
			<tr>
				<th width="25">序号</th>
				<th width="60">职工编号</th>
				<th width="60">职工名称</th>
				<th width="60">手机号码</th>
				<th width="60">办公电话</th>
				<th width="60">用户邮箱</th>
				<th width="60">是否管理员</th>
				<th width="70">使用状态</th>
				<th width="70">创建时间</th>
				<th width="70">修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${userList}" varStatus="s">
				<tr target="slt_objId" rel="${item.f_ZGBH}">
					<td>${s.index + 1}</td>
					<td>${item.f_ZGBH}</td>
					<td>${item.f_ZGMC}</td>				
					<td>${item.f_SJHM}</td>
					<td>${item.f_BGDH}</td>		
					<td>${item.f_YHYX}</td>
					<td><input name="F_SFGLY" value="1" <c:if test="${item.f_SFGLY=='1'}">checked=true</c:if> type="checkbox"></td>		
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