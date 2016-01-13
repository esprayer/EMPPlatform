<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<form id="pagerForm" action="demo/database/dwzOrgLookup.html">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="${ctx}/queryAnalyse/help/product/product?beginDate=${beginDate}&endDate=${endDate}" onsubmit="return dwzSearch(this, 'dialog');">
		<div class="pageFormContent">			
			<p style="width:200px;padding: 0px;">
				<label style="width:60px;padding: 0px;">产品编号：</label>
				<input type="text" name="F_CPBH" value="${F_CPBH}"/>
			</p>
			<div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div>
		</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="98" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th orderfield="orgName">序号</th>
				<th orderfield="orgName">产品编号</th>
				<th orderfield="orgNum">产品名称</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<dataset:forEach var="item" items="${helpDataSet}" varStatus="s">
				<tr target="slt_objId" rel="${item.f_CPBH}">
					<td>${s + 1}</td>
					<td>${item.F_CPBH}</td>
					<td>${item.F_CPMC}</td>						
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({F_CPMC:'${item.F_CPMC}', F_CPBH:'${item.F_CPBH}'})" title="查找带回">选择</a>
					</td>
				</tr>	
			</dataset:forEach>
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
</div>