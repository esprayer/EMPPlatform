<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>


<form id="pagerForm" action="demo/database/dwzOrgLookup.html">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="${ctx}/dictHelp/excelStatements/material/search?F_CPBH=${F_CPBH}&F_XMBH=${F_XMBH}&F_CKBH=${F_CKBH}&tarRel=/dailyBusiness/outBound/clHelp" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar" >
		<ul class="searchContent">
			<li>
				<label>材料编号:</label>
				<input class="textInput" name="keywords" value="${param.keywords}" type="text">
			</li>	  		
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
		</ul>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="98" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th>序号</th>
				<th>项目编号</th>
				<th>项目名称</th>
				<th>产品编号</th>
				<th>产品名称</th>
				<th>材料名称</th>
				<th>型号规格</th>
				<th>单位</th>
				<th>供应商名称</th>
				<th>入库单价</th>
				<th>库存数量</th>
				<th>查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${dataList}" varStatus="s">
				<tr target="slt_objId">
					<td>${s.index + 1}</td>
					<td>${item[0]}</td>
					<td>${item[1]}</td>		
					<td>${item[2]}</td>
					<td>${item[3]}</td>		
					<td>${item[5]}</td>
					<td>${item[8]}</td>
					<td>${item[9]}</td>
					<td>${item[7]}</td>
					<td>${item[10]}</td>
					<td>${item[11]}</td>				
					<td>
						<!-- <a class="btnSelect" href="javascript:$.bringBack({F_CLMC:'${item[3]}', F_CLBH:'${item[2]}', F_DWBH:'${item[4]}', F_DWMC:'${item[5]}', F_CLDJ:'${item[8]}', F_CKSL:'${item[13]}', F_KCSL:'${item[10]}',F_SQSL:'${item[11]}',F_LYSL:'${item[12]}'})" title="查找带回">选择</a> -->
						<a class="btnSelect" href="javascript:$.bringBack({F_SSXMBH:'${item[0]}', F_SSXMMC:'${item[1]}', F_SSCPBH:'${item[2]}', F_SSCPMC:'${item[3]}', F_DWBH:'${item[6]}', F_DWMC:'${item[7]}',F_CLDJ:'${item[10]}',F_KCSL:'${item[11]}'})" title="查找带回">选择</a>
					</td>
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
</div>