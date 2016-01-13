<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
	#backGoodsPreForm span.error{width:65px}
</style>

<form method="post" action="${ctx}/dailyBusiness/backGoods/form" navTabId="backGoodsOutBoundFormLiNav" title="材料退货" callbackType="closeCurrent" class="pageForm required-validate" onsubmit="return validateDialogCallback(this, dialogAjaxNavTab);">
	<div id = "backGoodsPreForm" class="pageFormContent" layoutH="56">
		<p style="width:140px;padding:10px 0px 0px 0px;">
			<label style="width:60px;padding: 0px;">会计年度：</label>
			<select class="combox" id="F_YEAR" name="F_YEAR">
				<c:forEach var="item" items="${kjndList}" varStatus="s">
					<option value="${item}" selected="selected">${item}</option>
				</c:forEach>
			</select>
		</p>
		
		<p style="width:130px;padding:10px 0px 0px 0px;">
			<label style="width:60px;padding: 0px;">会计期间：</label>
			<select class="combox" name="F_MONTH" id="F_MONTH">
				<c:forEach var="item" items="${kjqjList}" varStatus="s">
					<option value="${item[0]}" <c:if test="${item[1]=='1'}">selected="selected" </c:if>>${item[0]}</option>
				</c:forEach>
			</select>
		</p>
		<p style="width:240px;padding:15px 0px 0px 0px;">
			<label style="width:60px;padding: 0px;">仓库编号: </label>
			<input id="F_CKBH" name="F_CKBH" value="${F_CKBH}" type="hidden"/>
			<input readonly="readonly"  class="required" name="F_CKMC" type="text" postField="keyword" suggestFields="F_CKBH,F_CKMC" lookupGroup="org1" value="${F_CKMC}"/>
			<a class="btnLook" href="${ctx}/dictHelp/deportHelp"" lookupGroup="">查找带回</a>	
			<span class="info"></span>
		</p>
		<p style="width:240px;padding:15px 0px 0px 0px;">
			<label style="width:60px;padding: 0px;">项目编号: </label>
			<input id="F_XMBH" name="F_XMBH" value="${F_XMBH}" type="hidden"/>
			<input readonly="readonly" class="required" name="F_XMMC" type="text" suggestFields="F_XMBH,F_XMMC" lookupGroup="" value="${F_XMMC}"/>				
			<a class="btnLook" href="${ctx}/dictHelp/project/help/{F_CKBH}?F_CRFX=C&F_XMZT=0&F_YEAR={F_YEAR}&F_MONTH={F_MONTH}&F_XMBH="  warn="请选择仓库信息" lookupGroup="">查找带回</a>	
			<span class="info"></span>
		</p>		
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>