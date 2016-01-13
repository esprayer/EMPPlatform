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

<form method="post" rel="pagerForm" action="${ctx}/dailyBusiness/backGoods/form/list"  class="pageForm" onsubmit="return divSearch(this,'clthdListDiv')">
	<div class="pageFormContent">
		<div class="panel" defH="25">
			<h1>材料退货单基本信息</h1>
			<div>
				<input id="F_KJQJ" name="F_KJQJ" value="${F_KJQJ}" type="hidden"/>
				<p style="width:240px;padding: 0px;">
					<label style="width:60px;padding: 0px;">项目名称: </label>
					<input id="F_XMBH" name="F_XMBH" value="${F_XMBH}" type="hidden"/>
					<input readonly="readonly" name="F_XMMC" type="text" value="${F_XMMC}"/>					
				</p>
				<p style="width:240px;padding: 0px;">
					<label style="width:60px;padding: 0px;">仓库名称: </label>
					<input id="F_CKBH" name="F_CKBH" value="${F_CKBH}" type="hidden"/>
					<input readonly="readonly" name="F_CKMC" type="text" value="${F_CKMC}"/>	
					<span class="info"></span>
				</p>
				<p style="width:160px;padding: 0px;">
					<label style="width:60px;padding: 0px;">单据状态：</label>
					<select name="F_DJZT" style="width:135px" class="combox" >
						<option value='-1' <c:if test="${F_DJZT==''|| F_DJZT==null || F_DJZT=='-1'}">selected="selected" </c:if>>全部</option>
						<option value='1' <c:if test="${F_DJZT=='1'}">selected="selected" </c:if>>已提交</option>
						<option value='0' <c:if test="${F_DJZT=='0'}">selected="selected" </c:if>>未提交</option>
					</select>	
				</p>
				<p style="width:230px;padding: 0px;">
					<label style="width:60px;padding: 0px;">退货类型：</label>
					<select class="combox" name="F_THLX" id="F_THLX">
						<option value='-1' <c:if test="${F_THLX==''|| F_THLX==null || F_THLX=='-1'}">selected="selected" </c:if>>全部</option>
						<option value='0' <c:if test="${F_THLX=='0'}">selected="selected" </c:if>>材料退货</option>
						<option value='1' <c:if test="${F_THLX=='1'}">selected="selected" </c:if>>厂商退货</option>
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

<div class="pageContent" id = "ff">
	<div class="panelBar">
		<ul class="toolBar">			
			<li><a class="edit" target="navTab" rel="DV2BGFormLiNav" href="${ctx}/dailyBusiness/backGoods/reloadBackGoodsForm?{slt_objId1}" title="编辑退货单据"><span>编辑退货单据</span></a></li>			
			<li class="line">line</li>								
		</ul>
	</div>
	<table class="table" width="100%" layoutH="195">
		<thead>
			<tr>
				<th width="25">序号</th>
				<th width="60">退货单据编号</th>
				<th width="120">备注</th>
				<th width="60">退货人</th>	
				<th width="60">退货厂商名称</th>		
				<th width="60">制单人</th>	
				<th width="60">单据状态</th>				
				<th width="60">退货日期</th>			
				<th width="70">修改日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${hythdList}" varStatus="s">
			<tr target="slt_objId1" rel="F_KJQJ=${item.f_KJQJ}&F_GUID=${item.f_GUID}&F_DJBH=${item.f_DJBH}">
				<td>${s.index + 1}</td>
				<td>${item.f_DJBH}</td>				
				<td>${item.f_BZ}</td>
				<td>${item.f_THRMC}</td>
				<td>${item.f_CSMC}</td>
				<td>${item.f_BZRMC}</td>
				<td>
					<c:if test="${item.f_DJZT=='0'}">未提交</c:if>
					<c:if test="${item.f_DJZT=='1'}">已提交</c:if>
				</td>					
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