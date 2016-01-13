<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../include.inc.jsp"%>

<div class="pageContent">
	<form method="post" action="${ctx}/dailyBusiness/excelStatements/product/insert/${hyxmcpObject.f_XMBH}?navTabId=statementsProductLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>产品编号: </label>
				<input readonly="readonly"  class="required" name="F_CPBH" type="text" postField="keyword" suggestFields="F_CPBH,F_CPMC" lookupGroup="org1" value="${hyxmcpObject.f_CPBH}"/>
				<a class="btnLook" href="${ctx}/dictHelp/product/help?F_CPBH=" lookupGroup="">查找带回</a>	
				<span class="info"></span>
			</p>
			<p>
				<label>产品名称: </label>
				<input disabled="true" type="text" name="F_CPMC" class="required"/>
			</p>
			<p>
				<label>项目编号: </label>
				<input disabled="true" type="text" name="F_XMBH" value="${hyxmcpObject.f_XMBH}" class="required"/>
			</p>
			<p>
				<label>项目名称: </label>
				<input disabled="true" type="text" name="F_XMMC" value="${hyxmcpObject.f_XMMC}" class="required"/>
			</p>
			<p>
				<label>使用状态: </label>
				<select name="F_SYZT" style="width:135px" class="combox" >
					<option value=1 selected="selected">启用</option>
					<option value=0>停用</option>
				</select>			
			</p>	
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
</div>