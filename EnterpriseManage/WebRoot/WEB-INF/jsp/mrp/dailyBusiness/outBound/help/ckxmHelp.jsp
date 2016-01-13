<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../include.inc.jsp"%>

<form id="pagerForm" action="demo/database/dwzOrgLookup.html">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="${ctx}/dictHelp/outBound/project/help/searchByRq/${F_CKBH}?F_XMZT=0" onsubmit="return dwzSearch(this, 'dialog');">
		<div class="pageFormContent">
			<p style="width:230px;padding: 0px;">
					<label style="width:60px;padding: 0px;">起始日期：</label>
					<input type="text" name="beginDate" value="${beginDate}" class="date" readonly="true"/>
					<a class="inputDateButton" href="javascript:;" style="width:20px;padding: 0px;">选择</a>
				</p>
				
				<p style="width:230px;padding: 0px;">
					<label style="width:60px;padding: 0px;">截止日期：</label>
					<input type="text" name="endDate" value="${endDate}" class="date" readonly="true"/>
					<a class="inputDateButton" href="javascript:;" style="width:30px;padding: 0px;">选择</a>
				</p>
				
				<p style="width:200px;padding: 0px;">
					<label style="width:60px;padding: 0px;">项目编号：</label>
					<input type="text" name="F_XMBH" value="${F_XMBH}"/>
				</p>
				
				<div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div>
		</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="98" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th orderfield="orgName">序号</th>
				<th orderfield="orgName">项目编号</th>
				<th orderfield="orgNum">项目名称</th>
				<th orderfield="orgNum">申请时间</th>
				<th orderfield="orgNum">申请人名称</th>
				<th orderfield="orgNum">申请单位</th>
				<th orderfield="orgNum">材料需求时间</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${projectList}" varStatus="s">
				<tr target="slt_objId" rel="${item.f_XMBH}">
					<td>${s.index + 1}</td>
					<td>${item.f_XMBH}</td>
					<td>${item.f_XMMC}</td>		
					<td>${item.f_SQSJ}</td>
					<td>${item.f_SQRMC}</td>		
					<td>${item.f_SQDW}</td>
					<td><fmt:formatDate value="${item.f_CLXQSJ}" pattern="yyyy-MM-dd HH:mm:ss"/></td>				
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({F_XMMC:'${item.f_XMMC}', F_XMBH:'${item.f_XMBH}'})" title="查找带回">选择</a>
					</td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>每页</span>

			<select name="numPerPage" onchange="dwzPageBreak({targetType:dialog, numPerPage:'10'})">
				<option value="10" selected="selected">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			<span>条，共2条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="2" numPerPage="10" pageNumShown="1" currentPage="1"></div>
	</div>
</div>