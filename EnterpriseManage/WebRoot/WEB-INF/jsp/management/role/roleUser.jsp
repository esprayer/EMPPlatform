<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="/standard/metadata/object">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/management/base/role/search"  class="pageForm" onsubmit="return navTabSearch(this,'roleLiNav')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>角色用户</h1>
			<div>
				角色编号：<input type="text" readonly name="keywords" value="${roleObj.roleId}"/>
				角色名称：<input type="text" readonly name="keywords" value="${roleObj.roleName}"/>
			</div>
		</div>
		<div class="divider"></div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="dialog" mask="true" width="700" height="500" rel="roleUserNav" href="${ctx}/management/base/role/userHelp/${roleObj.roleId}" title="添加用户"><span>添加用户</span></a></li>
			<li><a class="delete" rel="userid" target="selectedTodo" postType="string" href="${ctx}/management/qxgl/delRoleUserGx?navTabId=roleUserNav&roleid=${roleObj.roleId}" title="你确定要删除吗?"><span>移除用户</span></a></li>
			<li class="line">line</li>								
		</ul>
	</div>
	<table class="table" width="100%" layoutH="161" rel="roleUserNav">
		<thead>
			<tr>
		        <th width="22"><input type="checkbox" group="userid" class="checkboxCtrl"></th>
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
			<c:forEach var="item" items="${yfpUsers}" varStatus="s">
				<tr target="slt_objId" rel="${item.USER_ID}">
					<td><input name="userid" value="${item.USER_ID}" type="checkbox"></td>
					<td>${item.USER_ID}</td>
					<td>${item.USER_NAME}</td>				
					<td>${item.USER_PHONE1}</td>
					<td>${item.USER_PHONE2}</td>		
					<td>${item.USER_MAIL1}</td>
					<td><input name="F_SFGLY" value="1" <c:if test="${item.USER_ISADMIN=='1'}">checked=true</c:if> type="checkbox"></td>		
					<td>
						<c:if test="${item.USER_DISABLE==1}">停用</c:if>
						<c:if test="${item.USER_DISABLE==0}">启用</c:if>
					</td>
					<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>


	<c:import url="../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>