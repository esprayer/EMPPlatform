<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="${ctx}/sysConfigure/user/${F_SXBM}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/dictHelp/project/user/search/${F_SXBM}?TYPE=${TYPE}&MUL=${MUL}"  class="pageForm" onsubmit="return divSearch(this,'userLiBox')">
	<div>
		<div class="panel" defH="25">
			<h1>用户基本信息</h1>
			<div>
				用户编号：<input type="text" name="keywords" value="${param.keywords}"/>
				<ul class="rightTools">
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
					<c:if test="${MUL=='1'}">
						<li><div class="button"><div class="buttonContent"><button type="button" multLookup="orgId" warn="请至少选择一个材料申请人">选择带回</button></div></div></li>
					</c:if>					
				</ul>
			</div>
		</div>
		<div class="divider"></div>
	</div>
</form>

<div>
	<table class="table" width="100%" layoutH="134" rel="userLiNav">
		<thead>
			<tr>
				<c:if test="${MUL=='1'}">
					<th width="25">选择</th>
				</c:if>				
				<th width="60">职工编号</th>
				<th width="60">职工名称</th>
				<th width="60">是否管理员</th>
				<th width="70">使用状态</th>
				<c:if test="${MUL=='0'}">
					<th width="70">查找带回</th>
				</c:if>		
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${userList}" varStatus="s">
				<tr target="slt_objId" rel="${item.f_ZGBH}">
					<c:if test="${MUL=='1'}">
						<td><input type="checkbox" name="orgId" value="{${F_ZDBH}:'${item.f_ZGBH}', ${F_ZDMC}:'${item.f_ZGMC}'}"/></td>
					</c:if>							
					<td>${item.f_ZGBH}</td>
					<td>${item.f_ZGMC}</td>				
					<td>
						<c:if test="${item.f_SFGLY=='0'}">否</c:if>
						<c:if test="${item.f_SFGLY=='1'}">是</c:if>							
					<td>
						<c:if test="${item.f_SYZT==0}">停用</c:if>
						<c:if test="${item.f_SYZT==1}">启用</c:if>
					</td>
					<c:if test="${MUL=='0'}">
						<td>
							<a class="btnSelect" href="javascript:$.bringBack({${F_ZDMC}:'${item.f_ZGMC}', ${F_ZDBH}:'${item.f_ZGBH}'})" title="查找带回">选择</a>
						</td>
					</c:if>		
				</tr>	
			</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>