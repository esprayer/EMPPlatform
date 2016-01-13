<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-right:35px}
</style>

<form id="pagerForm" method="post" action="${ctx}/queryAnalyse/action/projectCarryover?F_CLBH=${F_CLBH}&F_DWBH=${F_DWBH}&F_CSBH=${F_CSBH}&F_CLDJ=${F_CLDJ}&F_CKBH=${F_CKBH}&F_XMZT=1&F_XMBH=${F_XMBH}">
	<input type="hidden" name="totalCount" value="${page.totalCount}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" />
	<input type="hidden" name="pageNumShown" value="${page.pageNumShown}" />
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/queryAnalyse/action/projectCarryover?F_XMZT=1"  class="pageForm" onsubmit="return navTabSearch(this,'projectCarryoverNav')">
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

<form method="post" rel="pagerForm" action="${ctx}/queryAnalyse/action/projectCarryover?F_CLBH=${F_CLBH}&F_DWBH=${F_DWBH}&F_CSBH=${F_CSBH}&F_CLDJ=${F_CLDJ}&F_CKBH=${F_CKBH}&F_XMZT=1&F_XMBH=${F_XMBH}"  class="pageForm" onsubmit="return navTabSearch(this,'projectCarryoverNav')">
	<div class="pageContent">
		<input type="hidden" name="pageNum" value="${page.pageNum}" />
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="icon" target="navTab" rel="projectProductCarryoverNav" href="${ctx}/queryAnalyse/action/projectProductCarryover?F_CLBH=${F_CLBH}&F_DWBH=${F_DWBH}&F_CSBH=${F_CSBH}&F_CLDJ=${F_CLDJ}&F_CKBH=${F_CKBH}&{slt_objId}&F_CPBH=&F_XMZT=1" title="项目产品结转明细"><span>项目产品结转明细</span></a></li>
				<li><a class="icon" target="navTab" rel="carryoverDetailsNav" href="${ctx}/queryAnalyse/action/carryoverDetails?F_CLBH=${F_CLBH}&F_DWBH=${F_DWBH}&F_CSBH=${F_CSBH}&F_CLDJ=${F_CLDJ}&F_CKBH=${F_CKBH}&F_CPBH=&F_XMZT=1&{slt_objId}" title="结转明细"><span>结转明细</span></a></li>				
				<li class="line">line</li>
				<li><a class="icon" href="${ctx}/queryAnalyse/action/export/projectCarryover?F_CLBH=${F_CLBH}&F_DWBH=${F_DWBH}&F_CSBH=${F_CSBH}&F_CLDJ=${F_CLDJ}&F_CKBH=${F_CKBH}&F_XMZT=1&F_XMBH=${F_XMBH}" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
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
					<tr target="slt_objId" rel="F_XMBH=${item.F_XMBH}">
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