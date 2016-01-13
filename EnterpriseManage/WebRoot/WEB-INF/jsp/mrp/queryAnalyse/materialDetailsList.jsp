<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-right:35px}
</style>

<form id="pagerForm" method="post" action="${ctx}/queryAnalyse/materialDetails?beginDate=${beginDate}&endDate=${endDate}&F_CKBH=${F_CKBH}&F_CLBH=${F_CLBH}&F_XMBH=${F_XMBH}">
	<input type="hidden" name="totalCount" value="${page.totalCount}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" />
	<input type="hidden" name="pageNumShown" value="${page.pageNumShown}" />
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
</form>

<form method="post" action="${ctx}/queryAnalyse/materialDetails"  class="pageForm" onsubmit="return navTabSearch(this,'materialDetailsLiNav')">
	<div class="pageFormContent" id="searchParam">
		<div class="panel" defH="34">
			<h1>查询条件</h1>
			<div>
				<input type="hidden" name="beginDate" value="${beginDate}" />
				<input type="hidden" name="endDate" value="${endDate}" />
				<p style="width:200px;">
					<label style="width:60px;padding: 0px;">查询期间：</label>
					<input readonly="readonly" name="F_QJFW" type="text"  value="${formatBeginDate}-${formatEndDate}"/>
				</p>

				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">仓库编号: </label>
					<input id="F_CKBH" name="F_CKBH" type="text" postField="keyword" suggestFields="F_CKBH" lookupGroup="org1" value="${F_CKBH}"/>
					<a style="float:right; display:block; margin-left:5px" class="btnLook" href="${ctx}/dictHelp/deportHelp"" lookupGroup="">查找带回</a>	
					<span class="info"></span>
				</p>
				
				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">项目编号：</label>
					<input name="F_XMBH" type="text" postField="keyword" suggestFields="F_XMBH" lookupGroup="org1" value="${F_XMBH}"/>
					<a style="float:right; display:block; margin-left:5px" class="btnLook" href="${ctx}/queryAnalyse/help/project?beginDate=${beginDate}&endDate=${endDate}&F_XMZT=-1&F_XMBH=" lookupGroup="">查找带回</a>	
					<span class="info"></span>
				</p>
		
				<p style="width:230px;">
					<label style="width:60px;padding: 0px;">材料编号: </label>
					<input name="F_CLBH" type="text" postField="keyword" suggestFields="F_CLBH" lookupGroup="org1" value="${F_CLBH}"/>
					<a style="float:right; display:block; margin-left:5px" class="btnLook" href="${ctx}/queryAnalyse/help/material/helpMaterial?F_CKBH={F_CKBH}&beginDate=${beginDate}&endDate=${endDate}&F_CLBH=" isCheck="false"  lookupGroup="">查找带回</a>	
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

<form method="post" action="${ctx}/queryAnalyse/materialDetails?beginDate=${beginDate}&endDate=${endDate}&F_CKBH=${F_CKBH}&F_CLBH=${F_CLBH}"  class="pageForm" onsubmit="return navTabSearch(this,'materialDetailsLiNav')">
	<div class="pageContent">
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="icon" target="navTab" rel="materialDetailsLCProjectLiNav" href="${ctx}/queryAnalyse/action/materialDetailsLCDetails?beginDate=${beginDate}&endDate=${endDate}&{slt_objId}" title="联查项目材料明细"><span>联查项目材料明细</span></a></li>				
				<li class="line">line</li>
				<li><a class="icon" href="${ctx}/queryAnalyse/export/materialDetails?beginDate=${beginDate}&endDate=${endDate}&F_CKBH=${F_CKBH}&F_CLBH=${F_CLBH}&F_XMBH=${F_XMBH}" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			</ul>
		</div>
	
		<table class="table" border="10" width="200%" layoutH="180">
			<thead>
				<tr>
					<th width="30" align="center">序号</th>
					<th width="100">仓库编号</th>
					<th width="150">仓库名称</th>
					<th width="110">项目编号</th>
					<th width="150">项目名称</th>
					<th width="100">材料编号</th>
					<th width="150">材料名称</th>
					<th width="100">供应商编号</th>
					<th width="150">供应商名称</th>
					<th width="100">厂商编号</th>
					<th width="150">厂商名称</th>
					<th width="250">规格型号</th>
					<th width="100">计量单位</th>
					<th width="100">材料单价</th>
					<th width="100">入库数量</th>
					<th width="100">正常出库数量</th>
					<th width="100">借调出库数量</th>
					<th width="100">材料退货数量</th>
					<th width="100">厂商退货数量</th>
					<th width="100">调拨数量</th>
					<th width="100">库存数量</th>					
				</tr>			
			</thead>
			<tbody>
				<dataset:forEach var="item" items="${queryDataSet}" varStatus="s">
					<tr target="slt_objId" rel="F_XMBH=${item.F_XMBH}&F_CKBH=${item.F_CKBH}&F_CLBH=${item.F_CLBH}&F_DWBH=${item.F_DWBH}&F_CSBH=${item.F_CSBH}&F_CLDJ=${item.F_CLDJ}">
						<td width="30">${s + 1}</td>
						<td>${item.F_CKBH}</td>
						<td>${item.F_CKMC}</td>	
						<td>${item.F_XMBH}</td>
						<td>${item.F_XMMC}</td>			
						<td>${item.F_CLBH}</td>
						<td>${item.F_CLMC}</td>		
						<td>${item.F_DWBH}</td>
						<td>${item.F_DWMC}</td>		
						<td>${item.F_CSBH}</td>
						<td>${item.F_CSMC}</td>		
						<td>${item.F_GGXH}</td>
						<td>${item.F_JLDW}</td>		
						<td align="right">${item.F_CLDJ}</td>		
						<td align="right">${item.F_RKSL}</td>
						<td align="right" style="color:red;">${item.F_ZCCKSL}</td>	
						<td align="right" style="color:red;">${item.F_JDCKSL}</td>	
						<td align="right">${item.F_CLTHSL}</td>
						<td align="right">${item.F_CSTHSL}</td>
						<td align="right" style="color:red;">${item.F_DBSL}</td>	
						<td align="right" >${item.F_RKSL - item.F_ZCCKSL - item.F_JDCKSL + item.F_CLTHSL}</td>							
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