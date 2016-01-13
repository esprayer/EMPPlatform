<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-right:35px}
</style>

<form id="pagerForm" method="post" action="${ctx}/queryAnalyse/action/carryoverDetails?F_CLBH=${F_CLBH}&F_DWBH=${F_DWBH}&F_CSBH=${F_CSBH}&F_CLDJ=${F_CLDJ}&F_CKBH=${F_CKBH}&F_CPBH=&F_XMZT=1&F_XMBH=${F_XMBH}">
	<input type="hidden" name="totalCount" value="${page.totalCount}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" />
	<input type="hidden" name="pageNumShown" value="${page.pageNumShown}" />
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
</form>

<form method="post" action="${ctx}/queryAnalyse/action/carryoverDetails?F_XMZT=1"  class="pageForm" onsubmit="return navTabSearch(this,'carryoverDetailsNav')">
	<div class="pageFormContent" id="searchParam">
		<div class="panel" defH="34">
			<h1>查询条件</h1>
			<div>
				<input type="hidden" name="F_CLBH" value="${F_CLBH}" />
				<input type="hidden" name="F_DWBH" value="${F_DWBH}" />
				<input type="hidden" name="F_CSBH" value="${F_CSBH}" />
				<input type="hidden" name="F_CKBH" value="${F_CKBH}" />
				<input type="hidden" name="F_CLDJ" value="${F_CLDJ}" />
				<input type="hidden" name="F_XMBH" value="${F_XMBH}" />
				
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
					<input readonly="readonly" class="required" name="F_XMMC" type="text" value="${F_XMMC}"/>
				</p>
				
				<!-- <p style="width:230px;">
					<label style="width:60px;padding: 0px;">产品名称: </label>
					<input id="F_CPBH" name="F_CPBH" value="${F_CPBH}" type="hidden"/>
					<input readonly="readonly" class="required" name="F_CPMC" type="text" postField="keyword" suggestFields="F_CPBH,F_CPMC" lookupGroup="org1" value="${F_CPMC}"/>
					<a style="float:right; display:block; margin-left:5px" class="btnLook" href="${ctx}/sysConfigure/product/help/${F_XMBH}" lookupGroup="">查找带回</a>	
					<span class="info"></span>
				</p>
				<ul class="rightTools">
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>					
				</ul>
				 -->
			</div>
		</div>
		<div class="divider"></div>
	</div>
</form>

<form method="post" action="${ctx}/queryAnalyse/action/carryoverDetails?F_CLBH=${F_CLBH}&F_DWBH=${F_DWBH}&F_CSBH=${F_CSBH}&F_CLDJ=${F_CLDJ}&F_CKBH=${F_CKBH}&F_CPBH=&F_XMZT=1&F_XMBH=${F_XMBH}"  class="pageForm" onsubmit="return navTabSearch(this,'carryoverDetailsNav')">
	<div class="pageContent">
		<input type="hidden" name="pageNum" value="${page.pageNum}" />
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="icon" target="navTab" rel="formNav" href="${ctx}/queryAnalyse/action/formDetails?{slt_objId}" title="联查单据"><span>联查单据</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" href="${ctx}/queryAnalyse/action/export/materialDetailsLCDetails?beginDate=${beginDate}&endDate=${endDate}&F_CLBH=${F_CLBH}&F_DWBH=${F_DWBH}&F_CSBH=${F_CSBH}&F_CLDJ=${F_CLDJ}&F_CKBH=${F_CKBH}&F_XMBH=${F_XMBH}" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			</ul>
		</div>
	
		<table class="list" width="2400" layoutH="180">
			<thead>
				<tr>
					<th rowspan="2" width="30" align="center">序号</th>
					<th rowspan="2" align="center">会计期间</th>
					<th rowspan="2" align="center">单据编号</th>
					<th colspan="6" align="center">材料入库信息</th>
					<th colspan="6" align="center">材料出库信息</th>
					<th rowspan="2" align="center">材料编号</th>
					<th rowspan="2" align="center">材料名称</th>
					<th rowspan="2" align="center">规格型号</th>
					<th rowspan="2" align="center">计量单位</th>
					<th rowspan="2" align="center">供应商编号</th>
					<th rowspan="2" align="center">供应商名称</th>
					<th rowspan="2" align="center">厂商编号</th>
					<th rowspan="2" align="center">厂商名称</th>
					<th rowspan="2" align="center">出入方向</th>
					<th rowspan="2" align="center">单据类型</th>
					<th rowspan="2" align="center">材料单价</th>
					<th rowspan="2" align="center">材料数量</th>
					<th rowspan="2" align="center">材料总价</th>
				</tr>
				<tr>
					<th align="center">仓库编号</th>
					<th align="center">仓库名称</th>
					<th align="center">项目编号</th>
					<th align="center">项目名称</th>
					<th align="center">产品编号</th>
					<th align="center">产品名称</th>
					<th align="center">仓库编号</th>
					<th align="center">仓库名称</th>
					<th align="center">项目编号</th>
					<th align="center">项目名称</th>
					<th align="center">产品编号</th>
					<th align="center">产品名称</th>					
				</tr>				
			</thead>
			<tbody>
				<dataset:forEach var="item" items="${queryDataSet}" varStatus="s">
					<tr target="slt_objId" rel="F_KJQJ=${item.F_KJQJ}&F_DJBH=${item.F_DJBH}&F_CRFX=${item.F_CRFX}">
						<td width="30">${s + 1}</td>
						<td>${item.F_KJQJ}</td>
						<td>${item.F_DJBH}</td>				
						<td>${item.F_CKBH}</td>
						<td>${item.F_CKMC}</td>
						<td>${item.F_XMBH}</td>
						<td>${item.F_XMMC}</td>
						<td>${item.F_CPBH}</td>
						<td>${item.F_CPMC}</td>
						<td>${item.F_YYCKBH}</td>
						<td>${item.F_YYCKMC}</td>
						<td>${item.F_YYXMBH}</td>
						<td>${item.F_YYXMMC}</td>
						<td>${item.F_YYCPBH}</td>
						<td>${item.F_YYCPMC}</td>
						<td>${item.F_CLBH}</td>
						<td>${item.F_CLMC}</td>
						<td>${item.F_GGXH}</td>
						<td>${item.F_JLDW}</td>
						<td>${item.F_DWBH}</td>
						<td>${item.F_DWMC}</td>
						<td>${item.F_CSBH}</td>
						<td>${item.F_CSMC}</td>
						<td>
							<c:if test="${item.F_CRFX=='R'}">入库单</c:if>
							<c:if test="${item.F_CRFX=='C'}">出库单</c:if>
							<c:if test="${item.F_CRFX=='T'}">退货单</c:if>
							<c:if test="${item.F_CRFX=='D'}">调拨单</c:if>
						</td>
						<td>
							<c:if test="${item.F_DJLX=='R0'}">采购入库</c:if>
							<c:if test="${item.F_DJLX=='R1'}">更换入库</c:if>
							<c:if test="${item.F_DJLX=='T0'}">材料退货</c:if>
							<c:if test="${item.F_DJLX=='T1'}">厂商退货</c:if>
						</td>
						<td align="right">${item.F_CLDJ}</td>
						<td align="right">${item.F_CLSL}</td>
						<td align="right">${item.F_CLZJ}</td>
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