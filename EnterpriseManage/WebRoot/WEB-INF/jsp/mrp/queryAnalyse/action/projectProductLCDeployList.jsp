<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-right:35px}
</style>

<form id="pagerForm" method="post" action="${ctx}/queryAnalyse/action/xmcpclDetailsLCDeploy?beginDate=${beginDate}&endDate=${endDate}&CX=${CX}">
	<input type="hidden" name="totalCount" value="${page.totalCount}" />
	<input type="hidden" name="numPerPage" value="${page.numPerPage}" />
	<input type="hidden" name="pageNumShown" value="${page.pageNumShown}" />
	<input type="hidden" name="pageNum" value="${page.pageNum}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/queryAnalyse/action/xmcpclDetailsLCDeploy?&CX=${CX}"  class="pageForm" onsubmit="return navTabSearch(this,'xmcpclDetailsLCDeployLiNav')">
	<div class="pageFormContent" id="searchParam">
		<div class="panel" defH="34">
			<h1>查询条件</h1>
			<div>
				<input type="hidden" name="beginDate" value="${beginDate}" />
				<input type="hidden" name="endDate" value="${endDate}" />
				<p style="width:230px;">
					<label style="width:85px;padding: 0px;">项目申请期间：</label>
					<input readonly="readonly" name="F_QJFW" type="text"  value="${formatBeginDate}-${formatEndDate}"/>
				</p>
				
				<!-- 项目 -->
				<c:choose> 
					<c:when test="${XM=='1'}"> 	
						<input type="hidden" id="F_XMBH" name="F_XMBH" value="${F_XMBH}" />
						<p style="width:200px;">
							<label style="width:60px;padding: 0px;">项目名称: </label>
							<input readonly="readonly" class="required" name="F_XMMC" type="text" value="${F_XMMC}"/>
						</p>
					</c:when>
					<c:otherwise>
						<p id="outBoundPreForm" style="width:300px;padding: 10px;">
							<label style="width:100px;padding: 0px;">项目编号：</label>
							<input id="F_XMBH" name="F_XMBH" type="text" postField="keyword" suggestFields="F_XMBH" lookupGroup="org1"/>
							<a class="btnLook" width="1000" href="${ctx}/queryAnalyse/help/project?beginDate=${beginDate}&endDate=${endDate}&F_XMZT=-1&F_XMBH=" lookupGroup="">查找带回</a>	
							<span class="info"></span>
						</p>
					</c:otherwise> 
				</c:choose>
				
				<!-- 产品 -->
				<c:choose> 
					<c:when test="${CP=='1'}"> 	
						<input type="hidden" name="F_CPBH" value="${F_CPBH}" />
						<p style="width:230px;">
							<label style="width:60px;padding: 0px;">产品编号: </label>
							<input readonly="readonly" name="F_CPMC" type="text"  value="${F_CPMC}"/>
						</p>									
					</c:when>
					<c:otherwise>
						<p style="width:230px;">
							<label style="width:60px;padding: 0px;">产品编号：</label>
							<input name="F_CPBH" type="text" postField="keyword" suggestFields="F_CPBH" lookupGroup="org1"/>
							<a class="btnLook" width="1000" href="${ctx}/queryAnalyse/help/product/product?beginDate=${beginDate}&endDate=${endDate}&F_CPBH=" lookupGroup="">查找带回</a>	
							<span class="info"></span>
						</p>	
					</c:otherwise> 
				</c:choose>
				
				<!-- 材料 -->
				<c:choose> 
					<c:when test="${CL=='1'}"> 	
						<input type="hidden" name="F_CLBH" value="${F_CLBH}" />
						<p style="width:230px;">
							<label style="width:60px;padding: 0px;">材料编号: </label>
							<input readonly="readonly" name="F_CLMC" type="text"  value="${F_CLMC}"/>
						</p>
					</c:when>
					<c:otherwise>
						<p style="width:230px;">
							<label style="width:60px;padding: 0px;">材料编号: </label>
							<input name="F_CLBH" type="text" postField="keyword" suggestFields="F_CLBH" lookupGroup="org1" value="${F_CLBH}"/>
							<a style="float:right; display:block; margin-left:5px" class="btnLook" href="${ctx}/queryAnalyse/help/material/helpByProjectDate?beginDate=${beginDate}&endDate=${endDate}&F_CLBH=" lookupGroup="">查找带回</a>	
							<span class="info"></span>
						</p>
					</c:otherwise> 
				</c:choose>
				<ul class="rightTools">
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>					
				</ul>
			</div>
		</div>
		<div class="divider"></div>
	</div>
</form>

<form method="post" rel="pagerForm" action="${ctx}/queryAnalyse/action/xmcpclDetailsLCDeploy?beginDate=${beginDate}&endDate=${endDate}&F_XMBH=${F_XMBH}&F_CPBH=${F_CPBH}&F_CLBH=${item.F_CLBH}&CX=${CX}"  class="pageForm" onsubmit="return navTabSearch(this,'xmcpclDetailsLCDeployLiNav')">
	<div class="pageContent">	
		<input type="hidden" name="pageNum" value="${page.pageNum}" />
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="icon" target="navTab" rel="formNav" href="${ctx}/queryAnalyse/action/formDetails?{slt_objId}" title="联查单据"><span>联查单据</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" href="${ctx}/queryAnalyse/action/export/xmcpclDetailsLCDeploy?beginDate=${beginDate}&endDate=${endDate}&F_XMBH=${F_XMBH}&F_CPBH=${F_CPBH}&F_CLBH=${item.F_CLBH}&CX=${CX}" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			</ul>
		</div>
	
		<table class="list" width="2500" layoutH="180">
			<thead>
				<tr>
					<th rowspan="2" width="30" align="center">序号</th>
					<th rowspan="2" align="center">会计期间</th>
					<th rowspan="2" align="center">单据编号</th>
					<th rowspan="2" align="center">分录编号</th>
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
						<td>${item.F_FLBH}</td>			
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
							<c:if test="${item.F_DJLX=='C0'}">正常出库</c:if>
							<c:if test="${item.F_DJLX=='C1'}">借调出库</c:if>
							<c:if test="${item.F_DJLX=='C2'}">被借调出库</c:if>
							<c:if test="${item.F_DJLX=='D'}">仓库调拨</c:if>
						</td>
						<td align="right">${item.F_CLDJ}</td>
						<td align="right" <c:if test="${item.F_DJLX=='C0' || item.F_CRFX=='D' || item.F_DJLX=='C2'}">style="color:red;"</c:if>>${item.F_CLSL}</td>
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