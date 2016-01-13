<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="/standard/metadata/object">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/dailyBusiness/excelStatements/project/search"  class="pageForm" onsubmit="return navTabSearch(this,'statementsLiNav')">
	<div class="pageFormContent">
		<div class="panel" defH="25">
			<h1>项目基本信息</h1>
			<div>
				<p style="width:260px;padding: 0px;">
					<label style="width:60px;padding: 0px;">起始日期：</label>
					<input type="text" name="beginDate" value="${beginDate}" class="date" readonly="true"/>
					<a class="inputDateButton" href="javascript:;" style="width:20px;padding: 0px;">选择</a>
				</p>
				
				<p style="width:260px;padding: 0px;">
					<label style="width:60px;padding: 0px;">截止日期：</label>
					<input type="text" name="endDate" value="${endDate}" class="date" readonly="true"/>
					<a class="inputDateButton" href="javascript:;" style="width:30px;padding: 0px;">选择</a>
				</p>
				<p style="width:160px;padding: 0px;">
					<label style="width:60px;padding: 0px;">项目状态：</label>
					<select name="F_XMZT" style="width:135px" class="combox" >
						<option value='-1' <c:if test="${F_XMZT==''|| F_XMZT==null || F_XMZT=='-1'}">selected="selected" </c:if>>全部</option>
						<option value='1' <c:if test="${F_XMZT=='1'}">selected="selected" </c:if>>已完工</option>
						<option value='0' <c:if test="${F_XMZT=='0'}">selected="selected" </c:if>>未完工</option>
					</select>	
				</p>
				<p style="width:260px;padding: 0px;">
					<label style="width:60px;padding: 0px;">项目编号：</label>
					<input type="text" name="keywords" value="${param.keywords}" />
				</p>
				<ul class="rightTools">
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>					
				</ul>
			</div>
		</div>
		<div class="divider"></div>
	</div>	
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="dialog" mask="true" width="430" height="300" rel="objectNav" href="${ctx}/dailyBusiness/excelStatements/project/add" title="导入项目申请单"><span>导入项目申请单</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="800" height="400" rel="objectNav" href="${ctx}/dailyBusiness/excelStatements/project/edit/{slt_objId}" title="编辑项目申请单" warn="请选择需要编辑项目申请单信息！"><span>编辑项目申请单</span></a></li>			
			<li><a class="delete" target="ajaxTodo" href="${ctx}/dailyBusiness/excelStatements/project/delete/{slt_objId}?navTabId=statementsLiNav" title="你确定要删除该项目申请单吗?" warn="请选择需要编辑的项目申请单信息！"><span>删除项目申请单</span></a></li>
			<li class="line">line</li>			
			<li><a class="icon" target="navTab" rel="statementsMateialLiNav" href="${ctx}/dailyBusiness/excelStatements/material/{slt_objId}" title="项目材料明细" warn="请选择需要查看的项目申请单信息！"><span>项目申请单材料明细</span></a></li>					
			<li><a class="icon" target="navTab" rel="statementsProductLiNav" href="${ctx}/dailyBusiness/excelStatements/product/{slt_objId}" title="项目产品明细" warn="请选择需要查看的项目申请单信息！"><span>项目申请单产品明细</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="171">
		<thead>
			<tr>
				<th width="15"></th>
				<th width="50">项目编号</th>
				<th width="90">项目名称</th>		
				<th width="60">申请时间</th>
				<th width="60">申请人</th>
				<th width="60">申请单位</th>
				<th width="60">完工时间</th>				
				<th width="70">项目状态</th>
				<th width="70">创建时间</th>
				<th width="70">修改时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${hyxmList}" varStatus="s">
			<tr target="slt_objId" rel="${item.f_XMBH}">
				<td>${s.index + 1}</td>
				<td>${item.f_XMBH}</td>
				<td>${item.f_XMMC}</td>		
				<td>${item.f_SQSJ}</td>		
				<td>${item.f_SQRMC}</td>	
				<td>${item.f_SQDW}</td>	
				<td><fmt:formatDate value="${item.f_WGSJ}" pattern="yyyy-MM-dd HH:mm:ss"/></td>			
				<td>
					<c:if test="${item.f_XMZT==0}">未完成</c:if>
					<c:if test="${item.f_XMZT==1}">已完成</c:if>
				</td>
				<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../../_frag/pager/panelBar.jsp"></c:import>
</div>