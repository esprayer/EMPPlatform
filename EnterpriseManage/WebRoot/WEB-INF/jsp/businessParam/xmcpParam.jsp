<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form method="post" action="${ctx}/dictHelp/materialHelp" navTabId="outBoundLiNav" title="材料出库" callbackType="closeCurrent" class="pageForm required-validate" onsubmit="return validateDialogCallback(this, dialogAjaxNavTab);">
	<input id="F_DWBH" name="F_DWBH" value="${F_DWBH}" type="hidden"/>
	<input id="F_CSBH" name="F_CSBH" value="${F_CSBH}" type="hidden"/>
	<input id="F_CLBH" name="F_CLBH" value="${F_CLBH}" type="hidden"/>
	
	<div class="pageFormContent" layoutH="56">
		<p style="width:240px;padding:15px 0px 0px 0px;">
			<label style="width:60px;padding: 0px;">项目编号: </label>
			<input id="F_XMBH" name="F_XMBH" value="${F_XMBH}" type="hidden"/>
			<input readonly="readonly" class="required" name="F_XMMC" type="text" suggestFields="F_XMBH,F_XMMC" lookupGroup="" value="${F_XMMC}"/>				
			<a class="btnLook" href="${ctx}/dictHelp/projectHelp?F_XMZT=0&F_XMBH=&beginDate=&endDate="  warn="请选择仓库信息" lookupGroup="">查找带回</a>	
			<span class="info"></span>
		</p>	
		<p style="width:240px;padding:15px 0px 0px 0px;">
			<label style="width:60px;padding: 0px;">产品编号: </label>
			<input id="F_CPBH" name="F_CPBH" value="${F_CPBH}" type="hidden"/>
			<input readonly="readonly"  class="required" name="F_CPMC" type="text" postField="keyword" suggestFields="F_CPBH,F_CPMC" lookupGroup="org1" value="${F_CPMC}"/>
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