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

<form method="post" rel="pagerForm" action="${ctx}/dailyBusiness/backGoods/form/outBoundList"  class="pageForm" onsubmit="return divSearch(this,'clckdListDiv')">
	<div class="pageFormContent">
		<div class="panel" defH="25">
			<h1>材料出库单基本信息</h1>
			<input id="F_KJQJ" name="F_KJQJ" value="${F_KJQJ}" type="hidden"/>
			<div class="panel">		
				<p style="width:140px;padding: 0px;">
					<label style="width:60px;padding: 0px;">会计年度：</label>
					<select class="combox" name="F_YEAR">
						<c:forEach var="item" items="${kjndList}" varStatus="s">
							<option value="${item[0]}" <c:if test="${item[1]=='1'}">selected="selected" </c:if>>${item[0]}</option>
						</c:forEach>
					</select>
				</p>
				
				<p style="width:140px;padding: 0px;">
					<label style="width:60px;padding: 0px;">会计期间：</label>
					<select class="combox" name="F_MONTH" id="F_MONTH">
						<c:forEach var="item" items="${kjqjList}" varStatus="s">
							<option value="${item[0]}" <c:if test="${item[1]=='1'}">selected="selected" </c:if>>${item[0]}</option>
						</c:forEach>
					</select>
				</p>		
				<p style="width:240px;padding:0;">
					<label style="width:60px;padding: 0px;">项目名称: </label>
					<input id="F_XMBH" name="F_XMBH" value="${F_XMBH}" type="hidden"/>
					<input readonly="readonly"  class="required" name="F_XMMC" type="text" postField="keyword" suggestFields="F_XMBH,F_XMMC" lookupGroup="org1" value="${F_XMMC}"/>
					<span class="info"></span>
				</p>
				<p style="width:240px;padding: 0px;">
					<label style="width:60px;padding: 0px;">仓库名称: </label>
					<input id="F_CKBH" name="F_CKBH" value="${F_CKBH}" type="hidden"/>
					<input readonly="readonly"  class="required" name="F_CKMC" type="text" postField="keyword" suggestFields="F_CKBH,F_CKMC" lookupGroup="org1" value="${F_CKMC}"/>
					<!-- <a class="btnLook" href="${ctx}/dictHelp/deportHelp"" lookupGroup="">查找带回</a>	
					<span class="info"></span>
					 -->
				</p>
				<ul class="rightTools">
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>					
				</ul>
			</div>
		</div>
		<div class="divider"></div>
	</div>

	<div class="pageContent">
		<h3 class="contentTitle"></h3>
		<table class="table" width="100%" layoutH="210">
			<thead>
				<tr>
					<th width="15">序号</th>
					<th width="30">会计期间</th>
					<th width="30">单据编号</th>
					<th width="150">备注</th>
					<th width="30">领料人</th>	
					<th width="30">单据提交人</th>					
					<th width="40">出库单提交日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${hyckdList}" varStatus="s">
					<tr target="slt_objId" rel="${item.f_DJBH}?EXT_BIZ_KJQJ=${item.f_KJQJ}&F_GUID=${item.f_GUID}&F_KJQJ=${F_KJQJ}">
						<td align="center">${s.index + 1}</td>
						<td>${item.f_KJQJ}</td>
						<td>${item.f_DJBH}</td>					
						<td>${item.f_BZ}</td>
						<td align="center">${item.f_LLRMC}</td>
						<td align="center">${item.f_SBRMC}</td>					
						<td><fmt:formatDate value="${item.f_SBDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>				
					</tr>	
				</c:forEach>
			</tbody>
		</table>
		<div class="formBar">
			<ul>
				<li><a class="buttonActive" target="navTab" rel="DV2BGFormLiNav" href="${ctx}/dailyBusiness/backGoods/backStorageForm/{slt_objId}" title="生成材料退货单据"><span>退货</span></a></li>														
				<li><a class="buttonActive" target="navTab" rel="backGoodsFormsLiNav" href="${ctx}/dailyBusiness/backGoods/searchBackStorageForm/{slt_objId}&F_THLX=0&F_DJZT=0" title="联查退货单据"><span>查看退货单据</span></a></li>						
				<li><a class="buttonActive" target="navTab" rel="backGoodsDVFormLiNav" href="${ctx}/dailyBusiness/backGoods/lcDVForm/{slt_objId}" title="查看出库单据"><span>查看出库单据</span></a></li>
			</ul>
		</div>
	</div>
</form>