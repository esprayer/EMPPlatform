<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-right:35px}
</style>

<form id="pagerForm" method="post" action="${ctx}/queryAnalyse/productDetails?beginDate=${beginDate}&endDate=${endDate}&F_CPBH=${F_CPBH}">
	<input type="hidden" name="totalCount" value="${page.totalCount}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" />
	<input type="hidden" name="pageNumShown" value="${page.pageNumShown}" />
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
</form>

<form method="post" action="${ctx}/queryAnalyse/productDetails"  class="pageForm" onsubmit="return navTabSearch(this,'productDetailsLiNav')">
	<div class="pageFormContent">
		<input type="hidden" name="beginDate" value="${beginDate}" />
		<input type="hidden" name="endDate" value="${endDate}" />
		<div class="panel" defH="34">
			<h1>查询条件</h1>
			<div>
				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">查询期间：</label>
					<input readonly="readonly" name="F_QJFW" type="text"  value="${formatBeginDate}-${formatEndDate}"/>
				</p>

				<p style="width:300px;">
					<label style="width:100px;padding: 0px;">产品编号：</label>
					<input name="F_CPBH" type="text" postField="keyword" suggestFields="F_CPBH" lookupGroup="org1" value="${F_CPBH}"/>
					<a class="btnLook" width="1000" href="${ctx}/queryAnalyse/help/product/product?beginDate=${beginDate}&endDate=${endDate}&F_CPBH=" lookupGroup="">查找带回</a>	
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

<form method="post" action="${ctx}/queryAnalyse/productDetails?beginDate=${beginDate}&endDate=${endDate}&F_CPBH=${F_CPBH}" onsubmit="return navTabSearch(this,'productDetailsLiNav')">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="icon" target="navTab" rel="productDetailsLCMaterialLiNav" href="${ctx}/queryAnalyse/action/productDetailsLCMaterial?{slt_objId}&F_CLBH=&beginDate=${beginDate}&endDate=${endDate}" title="联查材料"><span>联查材料</span></a></li>			
				<li><a class="icon" target="navTab" rel="xmcpclDetailsLCDeployLiNav" href="${ctx}/queryAnalyse/action/xmcpclDetailsLCDeploy?F_CLBH=&{slt_objId}&CX=CP&beginDate=${beginDate}&endDate=${endDate}" title="项目产品联查用料调配"><span>项目产品联查用料调配</span></a></li>				
				<li class="line">line</li>
				<li><a class="icon" href="${ctx}/queryAnalyse/export/productDetails?beginDate=${beginDate}&endDate=${endDate}&F_CPBH=${F_CPBH}" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			</ul>
		</div>
	
		<table class="table" width="100%" layoutH="180">
			<thead>
				<tr>
					<th width="30" align="center">序号</th>
					<th>项目编号</th>
					<th>项目名称</th>
					<th>产品编号</th>
					<th>产品名称</th>					
					<th>项目状态</th>
					<th>项目库存成本</th>	
					<th>项目领用成本</th>	
					<th>项目预计总成本</th>						
				</tr>			
			</thead>
			<tbody>
				<dataset:forEach var="item" items="${queryDataSet}" varStatus="s">
					<tr target="slt_objId" rel="F_XMBH=${item.F_XMBH}&F_CPBH=${item.F_CPBH}">
						<td width="30">${s + 1}</td>
						<td>${item.F_XMBH}</td>
						<td>${item.F_XMMC}</td>		
						<td>${item.F_CPBH}</td>
						<td>${item.F_CPMC}</td>							
						<td>
							<c:if test="${item.F_XMZT=='0'}">未完工</c:if>
							<c:if test="${item.F_XMZT=='1'}">已完工</c:if>
						</td>
						<td align="right">${item.F_KCCB}</td>		
						<td align="right">${item.F_XMCB}</td>	
						<td align="right">${item.F_ZCB}</td>			
					</tr>	
				</dataset:forEach>
			</tbody>
		</table>
		
		<div class="panelBar" >
			<div class="pages">
				<span>显示</span>
				<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
					<option <c:if test="${page.numPerPage==20}">selected="selected" </c:if> value="20">20</option>
					<option <c:if test="${page.numPerPage==50}">selected="selected" </c:if> value="50">50</option>
					<option <c:if test="${page.numPerPage==100}">selected="selected" </c:if> value="100">100</option>
					<option <c:if test="${page.numPerPage==200}">selected="selected" </c:if> value="200">200</option>
				</select>
				<span>条，共${page.totalCount}条</span>
			</div>
			
			<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.numPerPage}" pageNumShown="${page.pageNumShown}" currentPage="${page.pageNum}"></div>		
		</div>
	</div>
</form>