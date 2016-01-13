<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form method="post" action="${ctx}/dailyBusiness/storage/form" navTabId="storageLiNav" title="材料入库" callbackType="closeCurrent" class="pageForm required-validate" onsubmit="return validateDialogCallback(this, dialogAjaxNavTab);">
	<div class="pageFormContent" layoutH="56" style="height: 1000px;">
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
		<p style="width:230px;padding:25px 0px 0px 0px;">
			<label style="width:60px;padding: 0px;">入库类型：</label>
			<select class="combox" name="F_RKLX" id="F_RKLX">
				<option value="0" selected="selected">采购入库</option>
				<option value="1">更换入库</option>
			</select>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>