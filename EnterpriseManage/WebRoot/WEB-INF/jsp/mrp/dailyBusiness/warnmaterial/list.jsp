<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="/sysConfigure/suppliers">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/dailyBusiness/warnmaterial/search"  class="pageForm" onsubmit="return navTabSearch(this,'warnmaterialLiNav')">
	<div class="pageFormContent">
		<div class="panel" defH="25">
			<h1>材料基本信息</h1>
			<div>
				<p style="width:240px;padding: 0px;">
					<label style="width:60px;padding: 0px;">材料编号: </label>
					<input class="required" name="F_CLBH" type="text" postField="keyword" suggestFields="F_CLBH,F_CLMC" lookupGroup="org1" value="${F_CLBH}"/>
					<a class="btnLook" href="${ctx}/dictHelp/material/help?F_CLBH=&tarRel=/dailyBusiness/warnmaterial/clHelp" lookupGroup="">查找带回</a>	
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

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="dialog" mask="true" width="460" height="340" rel="objectNav" href="${ctx}/dailyBusiness/warnmaterial/add" title="添加材料预警"><span>添加</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="460" height="340" rel="objectNav" href="${ctx}/dailyBusiness/warnmaterial/edit/{slt_objId}" title="编辑材料预警"><span>编辑</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/dailyBusiness/warnmaterial/delete/{slt_objId}?navTabId=warnmaterialLiNav" title="你确定要删除吗?"><span>删除</span></a></li>			
			<li class="line">line</li>								
		</ul>
	</div>
	<table class="table" width="100%" layoutH="144">
		<thead>
			<tr>
				<th width="20">序号</th>
				<th width="60">材料编号</th>
				<th width="60">材料名称</th>
				<th width="60">规格型号</th>
				<th width="60">单位</th>
				<th width="60">供应商名称</th>
				<th width="60">厂商名称</th>
				<th width="60">库存下线</th>
				<th width="70">创建时间</th>
				<th width="70">修改时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${clyjList}" varStatus="s">
			<tr target="slt_objId" rel="${item.f_CLBH}">
				<td>${s.index + 1}</td>
				<td>${item.f_CLBH}</td>
				<td>${item.f_CLMC}</td>				
				<td>${item.f_GGXH}</td>
				<td>${item.f_JLDW}</td>
				<td>${item.f_DWMC}</td>		
				<td>${item.f_CSMC}</td>				
				<td>${item.f_KCXX}</td>				
				<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>				
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