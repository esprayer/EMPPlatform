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

<form method="post" rel="pagerForm" action="${ctx}/dictHelp/deport/search/${F_BMBH}"  class="pageForm" onsubmit="return divSearch(this,'ckLiBox')">
	<div>
		<div class="panel" defH="25">
			<h1>仓库基本信息</h1>
			<div>
				仓库编号：<input type="text" name="keywords" value="${param.keywords}"/>
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
				<th width="20">序号</th>	
				<th width="60">仓库编号</th>
				<th width="60">仓库名称</th>
				<th width="70">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${deportList}" varStatus="s">
				<tr target="slt_objId" rel="${item.f_CKBH}">
					<td>${s.index + 1}</td>
					<td>${item.f_CKBH}</td>
					<td>${item.f_CKMC}</td>									
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({${F_ZDMC}:'${item.f_CKMC}', ${F_ZDBH}:'${item.f_CKBH}'})" title="查找带回">选择</a>
					</td>	
				</tr>	
			</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>