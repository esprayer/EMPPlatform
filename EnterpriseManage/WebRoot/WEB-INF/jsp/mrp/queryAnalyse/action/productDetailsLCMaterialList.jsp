<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-right:35px}
</style>
<form id="pagerForm" method="post" action="${ctx}/queryAnalyse/action/productDetailsLCMaterial?F_XMBH=${F_XMBH}&F_CPBH=${F_CPBH}&F_CLBH=${F_CLBH}&beginDate=${beginDate}&endDate=${endDate}">
	<input type="hidden" name="totalCount" value="${page.totalCount}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" />
	<input type="hidden" name="pageNumShown" value="${page.pageNumShown}" />
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/queryAnalyse/action/projectProductDetailsLCMaterial"  class="pageForm" onsubmit="return navTabSearch(this,'productDetailsLCMaterialLiNav')">
	<div class="pageFormContent" id="searchParam">
		<div class="panel" defH="34">
			<h1>查询条件</h1>
			<div>
				<input type="hidden" name="beginDate" value="${beginDate}" />
				<input type="hidden" name="endDate" value="${endDate}" />
				<input type="hidden" name="F_XMBH" value="${F_XMBH}" />
				<input type="hidden" name="F_CPBH" value="${F_CPBH}" />
				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">查询期间：</label>
					<input readonly="readonly" name="F_QJFW" type="text"  value="${formatBeginDate}-${formatEndDate}"/>
				</p>
				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">项目名称: </label>
					<input readonly="readonly" class="required" name="F_XMMC" type="text" value="${F_XMMC}"/>
				</p>
				
				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">产品名称: </label>
					<input readonly="readonly" class="required" name="F_CPMC" type="text" value="${F_CPMC}"/>
				</p>
				
				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">材料名称: </label>
					<input name="F_CLBH" type="text" postField="keyword" suggestFields="F_CLBH" lookupGroup="org1" value="${F_CLBH}"/>
					<a style="float:right; display:block; margin-left:5px" class="btnLook" href="${ctx}/queryAnalyse/help/material/helpByProjectDate?F_CKBH=&beginDate=${beginDate}&endDate=${endDate}&F_CLBH=" lookupGroup="">查找带回</a>	
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

<form method="post" action="${ctx}/queryAnalyse/action/productDetailsLCMaterial?F_XMBH=${F_XMBH}&F_CPBH=${F_CPBH}&F_CLBH=${F_CLBH}&beginDate=${beginDate}&endDate=${endDate}"  class="pageForm" onsubmit="return navTabSearch(this,'productDetailsLCMaterialLiNav')">
	<div class="pageContent">
		<input type="hidden" name="pageNum" value="${page.pageNum}" />
		<div class="panelBar">
			<ul class="toolBar">			
				<li><a class="icon" target="navTab" rel="xmcpclDetailsLCDeployLiNav" href="${ctx}/queryAnalyse/action/xmcpclDetailsLCDeploy?F_XMBH=${F_XMBH}&F_CPBH=${F_CPBH}&{slt_objId}&CX=XMCPCL&beginDate=${beginDate}&endDate=${endDate}" title="材料联查用料调配"><span>材料联查用料调配</span></a></li>				
				<li class="line">line</li>
				<li><a class="icon" href="${ctx}/queryAnalyse/action/export/productDetailsLCMaterial?F_XMBH=${F_XMBH}&F_CPBH=${F_CPBH}&F_CLBH=${F_CLBH}&beginDate=${beginDate}&endDate=${endDate}" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			</ul>
		</div>
	
		<table class="table" width="100%" layoutH="180">
			<thead>
				<tr>
					<th width="30" align="center">序号</th>
					<th>材料编号</th>
					<th>材料名称</th>
					<th>材料库存数量</th>
					<th>材料库存成本</th>
					<th>材料领用数量</th>
					<th>材料领用成本</th>	
					<th>材料总成本</th>												
				</tr>			
			</thead>
			<tbody>
				<dataset:forEach var="item" items="${queryDataSet}" varStatus="s">
					<tr target="slt_objId" rel="F_CLBH=${item.F_CLBH}">
						<td width="30">${s + 1}</td>
						<td>${item.F_CLBH}</td>
						<td>${item.F_CLMC}</td>		
						<td align="right">${item.F_KCSL}</td>		
						<td align="right">${item.F_KCCB}</td>	
						<td align="right">${item.F_XMSL}</td>		
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
					<option <c:if test="${page.numPerPage==1}">selected="selected" </c:if> value="1">1</option>
					<option <c:if test="${page.numPerPage==2}">selected="selected" </c:if> value="2">2</option>
					<option <c:if test="${page.numPerPage==3}">selected="selected" </c:if> value="3">3</option>
					<option <c:if test="${page.numPerPage==4}">selected="selected" </c:if> value="4">4</option>
				</select>
				<span>条，共${page.totalCount}条</span>
			</div>
			
			<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.numPerPage}" pageNumShown="${page.pageNumShown}" currentPage="${page.pageNum}"></div>		
		</div>
	</div>
</form>