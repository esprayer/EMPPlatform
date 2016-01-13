<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="${ctx}/management/base/user/${USER_ORGID}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/management/base/user/search/${USER_ORGID}"  class="pageForm" onsubmit="return divSearch(this,'userLiBox')">
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
			<li><a class="add" target="dialog" mask="true" width="420" height="400" rel="objectNav" href="${ctx}/management/base/user/add/${USER_ORGID}" title="添加用户"><span>添加用户</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="410" rel="objectNav" href="${ctx}/management/base/user/edit/{slt_objId}?USER_ORGID=${USER_ORGID}" title="编辑用户"><span>编辑用户</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/management/base/user/delete/{slt_objId}?rel=userLiBox&USER_ORGID=${USER_ORGID}" title="你确定要删除该用户吗?"><span>删除用户</span></a></li>
			<li class="line">line</li>								
		    <li><a class="add" target="dialog"  mask="true" width="600" height="468" rel="usgnDialog" href="${ctx}/management/qxgl/assignRole/{slt_objId}?USER_ORGID=${USER_ORGID}" title="分派角色"><span>分派角色</span></a></li>
		    <li><a class="edit" target="dialog"  mask="true" width="800" height="500" rel="usgnDialog" href="${ctx}/management/qxgl/assignGnqx/{slt_objId}?USER_ORGID=${USER_ORGID}" title="分配权限"><span>分配权限</span></a></li>
			<li class="line">line</li>				
		</ul>
	</div>
	<table class="table" width="100%" layoutH="161" rel="userLiNav">
		<thead>
			<tr>
				<th width="25">序号</th>
				<th width="60">用户账号</th>
				<th width="60">用户姓名</th>
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
				<tr target="slt_objId" rel="${item.USER_ID}">
					<td>${s.index + 1}</td>
					<td>${item.USER_ID}</td>
					<td>${item.USER_NAME}</td>				
					<td>${item.USER_PHONE1}</td>
					<td>${item.USER_PHONE2}</td>		
					<td>${item.USER_MAIL1}</td>
					<td><input name="F_SFGLY" value="1" <c:if test="${item.USER_ISADMIN=='1'}">checked=true</c:if> type="checkbox"></td>		
					<td>
						<c:if test="${item.USER_DISABLE==0}">停用</c:if>
						<c:if test="${item.USER_DISABLE==1}">启用</c:if>
					</td>
					<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>