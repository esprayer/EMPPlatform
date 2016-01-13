<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<form id="pagerForm" action="demo/database/dwzOrgLookup.html">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" id="ckdMaterialForm" name="ckdMaterialForm" method="post" action="${ctx}/dictHelp/material/bussinessHelp?F_CLBH=${F_CLBH}&F_CKBH=${F_CKBH}&tarRel=/mrp/dailyBusiness/outBound/xmcpByclHelp" onsubmit="return dwzSearch(this, 'dialog');">
		<div class="searchBar" >
			<ul class="searchContent">
				<li>
					<label>项目编号:</label>
					<input class="textInput" id="F_XMBH" name="F_XMBH" value="${F_XMBH}" type="text">
				</li>
				<li>
					<label>产品编号:</label>
					<input class="textInput" id="F_CPBH" name="F_CPBH" value="${F_CPBH}" type="text">
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
				<th>项目名称</th>
				<th>产品名称</th>
				<th>供应商名称</th>
				<th>厂商名称</th>
				<th>申请数量</th>
				<th>领用数量</th>
				<th>入库单价</th>
				<th>入库数量</th>
				<th>库存数量</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<dataset:forEach var="item" items="${dataList}" varStatus="status">
				<tr target="slt_objId" rel="${item.F_CLBH}">
					<td>${status + 1}</td>
					<td>${item.F_XMMC}</td>
					<td>${item.F_CPMC}</td>
					<td>${item.F_DWMC}</td>
					<td>${item.F_CSMC}</td>
					<td>${item.F_SQSL}</td>
					<td>${item.F_LYSL}</td>
					<td>${item.F_CLDJ}</td>
					<td>${item.F_RKSL}</td>
					<td>${item.F_SYSL}</td>
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({F_DWBH:'${item.F_DWBH}', F_DWMC:'${item.F_DWMC}', F_CSBH:'${item.F_CSBH}', F_CSMC:'${item.F_CSMC}', F_CLDJ:'${item.F_CLDJ}', F_KCSL:'${item.F_SYSL}',F_SQSL:'${item.F_SQSL}',F_LYSL:'${item.F_LYSL}', F_SSCPBH:'${item.F_CPBH}', F_SSCPMC:'${item.F_CPMC}', F_YYCPBH:'${item.F_CPBH}', F_YYCPMC:'${item.F_CPMC}', F_YYXMBH:'${item.F_XMBH}', F_YYXMMC:'${item.F_XMMC}'})" title="查找带回">选择</a>
						<!-- <a class="btnSelect" href="javascript:$.bringBack({F_CLMC:'${item[3]}', F_CLBH:'${item[2]}', F_DWBH:'${item[4]}', F_DWMC:'${item[5]}',F_SQSL:'${item[11]}',F_LYSL:'${item[12]}'})" title="查找带回">选择</a> -->
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