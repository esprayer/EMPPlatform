<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<script>
	function sumbitSeleced(formObj){
		var ids = _getIds("userid","userDiv");
		$("#selectedIds").val(ids);
		return validateCallback(formObj, dialogAjaxDone);
	};
	
	function _getIds(selectedIds, containerID){
		var ids = "";
		$("#"+containerID).find("input:checked").filter("[name='"+selectedIds+"']").each(function(i){
			var val = $(this).val();
			ids += i==0 ? val : ","+val;
		});
		return ids;
	};

</script>   
<form id="pagerForm" action="demo/database/dwzOrgLookup.html">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" method="post" action="demo/database/dwzOrgLookup.html" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>部门名称:</label>
				<input class="textInput" name="orgName" value="" type="text">
			</li>	  
			<li>
				<label>部门编号:</label>
				<input class="textInput" name="orgNum" value="" type="text">
			</li>
			<li>
				<label>部门经理:</label>
				<input class="textInput" name="fullName" value="" type="text">
			</li>
				<li>
				<label>上级部门:</label>
				<input class="textInput" name="parentOrg.orgName" value="" type="text">
			</li> 
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<form method="post" action="${ctx}/management/qxgl/saveRoleUserGx?navTabId=roleUserNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return sumbitSeleced(this);">
    <input id="selectedIds" name="selectedIds"  value="" type="hidden">
    <input id="selectedIds" name="roleid"  value="${roleid}" type="hidden">
    
	<div class="pageContent" id="userDiv">
	
		<table class="table" width="100%" layoutH="161" rel="userLiNav">
			<thead>
				<tr>
					<th width="22"><input type="checkbox" group="userid" class="checkboxCtrl"></th>
					<th width="60">用户账号</th>
					<th width="60">用户姓名</th>
					<th width="60">手机号码</th>
					<th width="60">办公电话</th>
					<th width="60">用户邮箱</th>
					<th width="60">是否管理员</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${wfpUsers}" varStatus="s">
					<tr target="slt_objId" rel="${item.USER_ID}">
						<td><input name="userid" value="${item.USER_ID}" type="checkbox"></td>
						<td>${item.USER_ID}</td>
						<td>${item.USER_NAME}</td>				
						<td>${item.USER_PHONE1}</td>
						<td>${item.USER_PHONE2}</td>		
						<td>${item.USER_MAIL1}</td>
						<td><input name="F_SFGLY" value="1" <c:if test="${item.USER_ISADMIN=='1'}">checked=true</c:if> type="checkbox"></td>		
					</tr>	
				</c:forEach>
			</tbody>
		</table>
	
		<div class="panelBar">
			<div class="pages">
				<span>每页</span>
	
				<select name="numPerPage" onchange="dwzPageBreak({targetType:dialog, numPerPage:'10'})">
					<option value="10" selected="selected">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
				</select>
				<span>条，共2条</span>
			</div>
			<div class="pagination" targetType="dialog" totalCount="2" numPerPage="10" pageNumShown="1" currentPage="1"></div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</div>
</form>

