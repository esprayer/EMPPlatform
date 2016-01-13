<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-right:35px}
</style>

<form id="pagerForm" method="post" action="w_list.html">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/queryAnalyse/action/projectMaterialCarryover?F_XMZT=1"  class="pageForm" onsubmit="return navTabSearch(this,'projectMaterialCarryoverNav')">
	<div class="pageFormContent" id="searchParam">
		<div class="panel" defH="34">
			<h1>查询条件</h1>
			<div>
				<input type="hidden" name="F_CLBH" value="${F_CLBH}" />
				<input type="hidden" name="F_DWBH" value="${F_DWBH}" />
				<input type="hidden" name="F_CSBH" value="${F_CSBH}" />
				<input type="hidden" name="F_CKBH" value="${F_CKBH}" />
				<input type="hidden" name="F_CLDJ" value="${F_CLDJ}" />
				
				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">仓库名称: </label>
					<input readonly="readonly" name="F_CKMC" type="text"  value="${F_CKMC}"/>
				</p>
				
				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">材料名称: </label>
					<input readonly="readonly" name="F_CLMC" type="text"  value="${F_CLMC}"/>
				</p>

				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">项目名称: </label>
					<input id="F_XMBH" name="F_XMBH" value="${F_XMBH}" type="hidden"/>
					<input readonly="readonly" class="required" name="F_XMMC" type="text" postField="keyword" suggestFields="F_XMBH,F_XMMC" lookupGroup="org1" value="${F_XMMC}"/>
					<a style="float:right; display:block; margin-left:5px" class="btnLook" href="${ctx}/sysConfigure/project/help?F_XMZT=1"" lookupGroup="">查找带回</a>	
					<span class="info"></span>
				</p>
				<ul class="rightTools">
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>					
				</ul>
			</div>
		</div>
		<div class="divider"></div>
	</div>
</form>

<form method="post" rel="pagerForm" action="${ctx}/queryAnalyse/warehouseMaterial?F_CLBH="  class="pageForm" onsubmit="return navTabSearch(this,'warehouseMaterialLiNav')">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="icon" target="navTab" rel="projectCarryoverDetailsNav" href="${ctx}/queryAnalyse/action/projectMaterialCarryoverDetails?F_CLBH=${item.f_CLBH}&F_DWBH=${item.f_DWBH}&F_CSBH=${item.f_CSBH}&F_CLDJ=${item.f_CLDJ}&F_CKBH=${F_CKBH}" title="项目结转明细"><span>项目结转明细</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			</ul>
		</div>
	
		<table class="table" width="100%" layoutH="180">
			<thead>
				<tr>
					<th width="30" align="center">序号</th>
					<th>项目编号</th>
					<th>项目名称</th>
					<th>入库数量</th>
					<th>出库数量</th>
					<th>材料退货数量</th>
					<th>厂商退货数量</th>
					<th>调拨数量</th>
					<th>结转数量</th>
				</tr>			
			</thead>
			<tbody>
				<dataset:forEach var="item" items="${queryDataSet}" varStatus="s">
					<tr target="slt_objId" rel="&F_XMBH=${item.XMBH}">
						<td width="30">${s + 1}</td>
						<td>${item.F_XMBH}</td>
						<td>${item.F_XMMC}</td>				
						<td align="right">${item.F_RKSL}</td>
						<td align="right" style="color:red;">${item.F_CKSL}</td>	
						<td align="right">${item.F_CLTHSL}</td>
						<td align="right">${item.F_CSTHSL}</td>
						<td align="right" style="color:red;">${item.F_DBSL}</td>	
						<td align="right">${item.F_JZSL}</td>
					</tr>	
				</dataset:forEach>
			</tbody>
		</table>
		
		<div class="panelBar" >
			<div class="pages">
				<span>显示</span>
				<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select>
				<span>条，共23条</span>
			</div>
			
			<div class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="2"></div>		
		</div>
	</div>
</form>