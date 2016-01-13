<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="${ctx}/sysConfigure/user/${F_SXBM}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/dailyBusiness/backGoods/help/departmentUser/user/search/${F_SXBM}"  class="pageForm" onsubmit="return divSearch(this,'userLiBox')">
	<div>
		<div class="panel" defH="25">
			<h1>用户基本信息</h1>
			<div>
				用户编号：<input type="text" name="keywords" value="${param.keywords}"/>
				<ul class="rightTools">
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>				
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
				<th width="60">职工编号</th>
				<th width="60">职工名称</th>
				<th width="60">是否管理员</th>
				<th width="70">查找带回</th>	
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${userList}" varStatus="s">
				<tr target="slt_objId" rel="${item.f_ZGBH}">							
					<td>${item.f_ZGBH}</td>
					<td>${item.f_ZGMC}</td>				
					<td>
						<c:if test="${item.f_SFGLY=='0'}">否</c:if>
						<c:if test="${item.f_SFGLY=='1'}">是</c:if>							
					</td>
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({F_THRID:'${item.f_ZGBH}', F_THRMC:'${item.f_ZGMC}', F_CSBH:'', F_CSMC:''})" title="查找带回">选择</a>
					</td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>