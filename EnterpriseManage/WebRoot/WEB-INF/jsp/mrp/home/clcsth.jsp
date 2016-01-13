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

<table class="list" width="98%">
	<thead>
		<tr>
			<th width="30" align="center">序号</th>
			<th>材料编号</th>
			<th>材料名称</th>
			<th>厂商编号</th>
			<th>厂商名称</th>
			<th>退货数量</th>
			<th>入库数量</th>
		</tr>			
	</thead>
	<tbody>
		<dataset:forEach var="item" datasetKey="CLCSTH" items="${SYSTEMHOME}" varStatus="s">
			<tr target="slt_objId" rel="F_XMBH=${item.F_XMBH}">
				<td width="30">${s + 1}</td>
				<td>${item.F_CLBH}</td>
				<td>${item.F_CLMC}</td>	
				<td>${item.F_CSBH}</td>
				<td>${item.F_CSMC}</td>	
				<td>${item.F_THSL}</td>
				<td>${item.F_RKSL}</td>				
			</tr>	
		</dataset:forEach>
	</tbody>
</table>	