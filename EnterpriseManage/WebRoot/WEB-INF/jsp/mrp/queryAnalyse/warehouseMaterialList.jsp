<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-right:35px}
</style>

<form id="pagerForm" method="post" action="${ctx}/queryAnalyse/warehouseMaterial?F_CLBH=${F_CLBH}&F_CKBH=${F_CKBH}">
	<input type="hidden" name="totalCount" value="${page.totalCount}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" />
	<input type="hidden" name="pageNumShown" value="${page.pageNumShown}" />
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
</form>

<form method="post" action="${ctx}/queryAnalyse/warehouseMaterial?F_CLBH=" onsubmit="return navTabSearch(this,'warehouseMaterialLiNav')">
	<div class="pageFormContent">
		<div class="panel" defH="34">
			<h1>查询条件</h1>
			<div>
				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">仓库名称: </label>
					<input id="F_CKBH" name="F_CKBH" value="${F_CKBH}" type="hidden"/>
					<input readonly="readonly" class="required" name="F_CKMC" type="text" postField="keyword" suggestFields="F_CKBH,F_CKMC" lookupGroup="org1" value="${F_CKMC}"/>
					<a style="float:right; display:block; margin-left:5px" class="btnLook" href="${ctx}/dictHelp/deportHelp"" lookupGroup="">查找带回</a>	
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

<form method="post" action="${ctx}/queryAnalyse/warehouseMaterial?F_CLBH=${F_CLBH}&F_CKBH=${F_CKBH}"  class="pageForm" onsubmit="return navTabSearch(this,'warehouseMaterialLiNav')">
	<div class="pageContent">
		<input type="hidden" name="pageNum" value="${page.pageNum}" />
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="icon" target="navTab" rel="projectCarryoverNav" href="${ctx}/queryAnalyse/action/projectCarryover{slt_objId}&F_XMZT=1&F_XMBH=" title="项目结转"><span>项目结转</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" href="${ctx}/queryAnalyse/export/warehouseMaterial/exportExcel?F_CLBH=&F_CKBH=${F_CKBH}" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>				
			</ul>
		</div>
		<!-- <table class="table" width="100%" layoutH="230"> -->
		<table id="warehouseMaterialTable" class="table" width="100%" layoutH="180">
			<thead>
				<tr>
					<th width="30" align="center">序号</th>
					<th>材料编号</th>
					<th>材料名称</th>
					<th>规格型号</th>
					<th>计量单位</th>
					<th>供应商名称</th>
					<th>厂商名称</th>
					<th align="right">库存数量</th>
					<th align="right">材料单价</th>
					<th align="right">材料总价</th>
				</tr>			
			</thead>
			<tbody>
				<dataset:forEach var="item" items="${queryDataSet}" varStatus="s">
					<tr target="slt_objId" rel="?F_CLBH=${item.F_CLBH}&F_DWBH=${item.F_DWBH}&F_CSBH=${item.F_CSBH}&F_CLDJ=${item.F_CLDJ}&F_CKBH=${F_CKBH}">
						<td width="30">${s + 1}</td>
						<td>${item.F_CLBH}</td>
						<td>${item.F_CLMC}</td>				
						<td>${item.F_GGXH}</td>
						<td>${item.F_JLDW}</td>	
						<td>${item.F_DWMC}</td>
						<td>${item.F_CSMC}</td>
						<td>${item.F_CLSL}</td>
						<td>${item.F_CLDJ}</td>
						<td>${item.F_CLZJ}</td>
					</tr>	
				</dataset:forEach>
			</tbody>
		</table>
		
		<div class="panelBar" >
			<div class="pages">
				<span>显示</span>
				<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
					<option <c:if test="${page.numPerPage==1}">selected="selected" </c:if> value="1">1</option>
					<option <c:if test="${page.numPerPage==2}">selected="selected" </c:if> value="2">2</option>
					<option <c:if test="${page.numPerPage==3}">selected="selected" </c:if> value="3">3</option>
					<option <c:if test="${page.numPerPage==4}">selected="selected" </c:if> value="4">4</option>
				</select>
				<span>条，共${page.totalCount}条</span>
			</div>
			
			<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.numPerPage}" pageNumShown="${page.pageNumShown}" currentPage="${page.pageNum}"></div>		
		</div>

		<!-- <div class="formBar"  style="height:50px">
			<div style="height:10px"></div>		
			<ul>			
			<li><a class="buttonActive" target="navTab" rel="projectCarryoverNav" href="${ctx}/queryAnalyse/action/projectMaterialCarryover{slt_objId}&F_XMZT=1&F_XMBH=" title="项目结转"><span>项目结转</span></a></li>
	
			</ul>
		 -->
	</div>
</form>