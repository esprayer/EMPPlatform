<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
	#transfersPreForm span.error{width:65px}
</style>

<form method="post" action="${ctx}/dailyBusiness/transfers/form/list" navTabId="transfersLiNav" title="仓库材料调拨" callbackType="closeCurrent" formValid="1" class="pageForm required-validate" onsubmit="return validateDialogCallback(this, dialogAjaxNavTab);">
	<div id = "transfersPreForm" class="pageFormContent" layoutH="56" style="height: 1000px;">
		<p style="width:140px;padding:15px 0px 0px 0px;">
			<label style="width:60px;padding: 0px;">会计年度：</label>
			<select class="combox" name="F_YEAR">
				<c:forEach var="item" items="${kjndList}" varStatus="s">
					<option value="${item}" selected="selected">${item}</option>
				</c:forEach>
			</select>
		</p>
		
		<p style="width:130px;padding:15px 0px 0px 0px;">
			<label style="width:60px;padding: 0px;">会计期间：</label>
			<select class="combox" name="F_MONTH" id="F_MONTH">
				<c:forEach var="item" items="${kjqjList}" varStatus="s">
					<option value="${item[0]}" <c:if test="${item[1]=='1'}">selected="selected" </c:if>>${item[0]}</option>
				</c:forEach>
			</select>
		</p>
		<p id = "transfersPreForm" style="width:240px;padding:15px 0px 0px 0px;">
			<label style="width:60px;padding: 0px;">仓库编号: </label>
			<input id="F_CKBH" name="F_CKBH" value="${F_CKBH}" type="hidden"/>
			<input readonly="readonly"  class="required" name="F_CKMC" type="text" postField="keyword" suggestFields="F_CKBH,F_CKMC" lookupGroup="org1" value="${F_CKMC}"/>
			<a class="btnLook" href="${ctx}/dictHelp/deportHelp"" lookupGroup="">查找带回</a>	
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