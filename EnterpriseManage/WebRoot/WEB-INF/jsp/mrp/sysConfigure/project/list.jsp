<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<script>
	function getXmwgAjaxData() {
		return $("#xmwgForm");
	}
</script>

<form id="pagerForm" method="post" action="/sysConfigure/suppliers">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" id="xmwgForm" action="${ctx}/sysConfigure/project/search"  class="pageForm" onsubmit="return navTabSearch(this,'projectLiNav')">
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
				<p style="width:260px;padding: 0px;">
					<label style="width:60px;padding: 0px;">项目状态：</label>
					<select name="F_XMZT" style="width:135px" class="combox" >
						<option value='-1' <c:if test="${F_XMZT==''|| F_XMZT==null || F_XMZT=='-1'}">selected="selected" </c:if>>全部</option>
						<option value='1' <c:if test="${F_XMZT=='1'}">selected="selected" </c:if>>已完工</option>
						<option value='0' <c:if test="${F_XMZT=='0'}">selected="selected" </c:if>>未完工</option>
					</select>	
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
	<table class="table" width="100%" layoutH="144">
		<thead>
			<tr>
				<th width="15">序号</th>
				<th width="60">项目编号</th>
				<th width="60">项目名称</th>
				<th width="60">申请时间</th>
				<th width="60">项目状态</th>
				<th width="60">完工时间</th>
				<th width="30">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${projectList}" varStatus="s">
			<tr target="slt_objId" rel="${item.f_XMBH}">
				<td>${s.index + 1}</td>
				<td>${item.f_XMBH}</td>
				<td>${item.f_XMMC}</td>				
				<td>${item.f_SQSJ}</td>
				<td>
					<c:if test="${item.f_XMZT==0}">未完工</c:if>
					<c:if test="${item.f_XMZT==1}">已完工</c:if>
				</td>						
				<td><fmt:formatDate value="${item.f_WGSJ}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:if test="${item.f_XMZT==0}">
						<a title="是否确认项目完工？" target="ajaxTodo" getAjaxData="getXmwgAjaxData()" showTitle="1" callback="validateCallSelback" href="${ctx}/sysConfigure/project/xmwg/${item.f_XMBH}?callbackType=forward&forwardUrl=${ctx}/sysConfigure/project&pageBreak=0" class="btnEdit">创建</a>
					</c:if>
					<c:if test="${item.f_XMZT==1}">
						<!-- <a title="是否取消项目完工？" target="ajaxTodo" href="${ctx}/sysConfigure/project/cancelxmwg/${item.f_XMBH}?navTabId=metadataLiNav" class="btnDel">删除</a> -->
						<a class="btnDel"></a>
					</c:if>
				</td>
			</tr>	
		</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
	<div class="pages">
		<span>每页</span>
		<select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
			<c:forEach begin="10" end="40" step="10" varStatus="s">
				<option value="${s.index}" ${numPerPage eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
			</c:forEach>
		</select>
		<span>条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总共: ${numPerPage}页</span>
	</div>
	
	<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${param.pageNum}"></div>
</div>
</div>