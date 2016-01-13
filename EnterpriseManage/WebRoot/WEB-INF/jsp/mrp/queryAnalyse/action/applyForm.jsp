<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="accountInfo" style="height:60px;line-height:60px;text-align:center;font-size:34px;font-weight:bold">
	材料申请单
</div>

	<form method="post" action="demo/common/ajaxDone.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div style="background-color:#efefef" class="pageFormContent">
			<p style="width:270px">
				<label style="width:80px">项目编号：</label>
				<input name="F_XMBH" type="text" size="30" style="width:150px" value="${applyForm.F_XMBH}" readonly="readonly"/>
			</p>
			<p style="width:270px">
				<label style="width:80px">项目名称：</label>
				<input name="F_XMMC" type="text" size="30" value="${applyForm.F_XMMC}" style="width:150px" readonly="readonly"/>
			</p>
			<p style="width:270px">
				<label style="width:80px">申请日期：</label>
				<input name="F_SQSJ" type="text" size="30" value="${applyForm.F_SQSJ}" style="width:150px" readonly="readonly"/>
			</p>
			<p style="width:270px">
				<label style="width:80px">申请单位：</label>
				<input name="F_SQDW" type="text" size="30" value="${applyForm.F_SQDW}" style="width:150px" readonly="readonly"/>
			</p>
			<p style="width:270px">
				<label style="width:80px">申请人：</label>
				<input type="text" name="F_SQRMC" value="${applyForm.F_SQRMC}" style="width:150px" readonly="readonly"/>	
			</p>
			<p style="width:270px">
				<label style="width:80px">供应中心：</label>
				<input type="text" name="F_GYZXMC" value="${applyForm.F_GYZXMC}" style="width:150px" readonly="readonly"/>
			</p>
			<p style="width:270px">
				<label style="width:80px">单位领导名称: </label>
				<input type="text" name="F_DWLDMC" value="${applyForm.F_DWLDMC}" style="width:150px" readonly="readonly"/>
			</p>
			<p style="width:270px">
				<label style="width:80px">分管领导名称: </label>
				<input type="text" name="F_FGLDMC" value="${applyForm.F_FGLDMC}" style="width:150px" readonly="readonly"/>
			</p>
			<p style="width:270px">
				<label style="width:80px">主管领导名称: </label>
				<input type="text" name="F_ZGLDMC" value="${applyForm.F_ZGLDMC}" style="width:150px" readonly="readonly"/>
			</p>
			<p style="width:270px">
				<label style="width:80px">材料需求时间: </label>
				<input type="text" name="F_CLXQSJ" value="<fmt:formatDate value="${applyForm.F_CLXQSJ}" pattern="yyyy-MM-dd"/>" style="width:150px" readonly="readonly"/>
				
			</p>							
			<c:choose> 
				<c:when test="${applyForm.F_XMZT=='1'}"> 	
					<p style="width:270px">
						<label style="width:80px">项目状态: </label>
						<input type="text" name="F_ZGLDMC" value="已完工" style="width:150px" readonly="readonly"/>
					</p>	
					<p style="width:270px">
						<label style="width:80px">完工时间: </label>
						<fmt:formatDate value="${applyForm.F_WGSJ}" pattern="yyyy-MM-dd HH:mm:ss"/>	
					</p>
				</c:when>
				<c:otherwise>
					<p style="width:270px">
						<label style="width:80px">项目状态: </label>
						<input type="text" name="F_ZGLDMC" value="未完工" style="width:150px" readonly="readonly"/>
					</p>	
				</c:otherwise>
			</c:choose>					
		</div>		
	</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="icon" target="navTab" rel="xmcpclDetailsLCDeployLiNav" href="${ctx}/queryAnalyse/action/xmcpclDetailsLCDeploy?beginDate=${beginDate}&endDate=${endDate}&F_XMBH=${F_XMBH}&F_CPBH=&{slt_objId}&CX=XMCL" title="材料联查用料调配"><span>材料联查用料调配</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="${ctx}/queryAnalyse/action/export/productDetailsLCSatement?F_XMBH=${F_XMBH}" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>							
		</ul>
	</div>

	<table class="list" width="1400" layoutH="255">
		<thead>
			<tr>
				<th rowspan="2" align="center" width="15"></th>
				<th rowspan="2" align="center">材料编号</th>
				<th rowspan="2" align="center" width="150">材料名称</th>	
				<th rowspan="2" align="center" width="200">型号规格</th>
				<th rowspan="2" align="center">单位</th>			
				<th rowspan="2" align="center">申请数量</th>
				<th rowspan="2" align="center">入库数量</th>
				<th colspan="3" align="center">出库数量</th>
				<th colspan="2" align="center">退货数量</th>
				<th rowspan="2" align="center">调拨数量</th>
				<th rowspan="2" align="center">库存数量</th>
				<th rowspan="2" align="center">领用数量</th>
				<th rowspan="2" align="center">领用总价</th>
				<th rowspan="2" align="center">库存情况</th>
				<th rowspan="2" align="center">备注</th>
			</tr>
			<tr>
				<th align="center">正常领用</th>
				<th align="center">借调</th>	
				<th align="center">被借调</th>
				<th align="center">材料退货</th>
				<th align="center">厂商退货</th>	
			</tr>
		</thead>
		<tbody>
			<dataset:forEach var="item" items="${applyForm}" varStatus="s">
				<tr target="slt_objId" rel="F_CLBH=${item.F_CLBH}">
					<td align="center">${s + 1}</td>
					<td>${item.F_CLBH}</td>
					<td>${item.F_CLMC}</td>			
					<td>${item.F_GGXH}</td>
					<td>${item.F_JLDW}</td>				
					<td align="right">${item.F_SQSL}</td>
					<td align="right">${item.F_RKSL}</td>
					<td align="right">${item.F_ZCSL}</td>
					<td align="right">${item.F_JDSL}</td>
					<td align="right">${item.F_BJDSL}</td>
					<td align="right">${item.F_CLTHSL}</td>
					<td align="right">${item.F_CSTHSL}</td>
					<td align="right">${item.F_DBSL}</td>
					<td align="right">${item.F_KCSL}</td>
					<td align="right">${item.F_LYSL}</td>
					<td align="right">${item.F_CLZJ}</td>
					<td>${item.F_KCQK}</td>
					<td>${item.F_BZ}</td>
				</tr>
			</dataset:forEach>			
		</tbody>
	</table>
</div>