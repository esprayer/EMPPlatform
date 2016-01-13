<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form method="post" action="${ctx}/sysConfigure/project/product/list" navTabId="projectProductLiNav" title="项目产品管理" callbackType="closeCurrent" class="pageForm required-validate" onsubmit="return validateDialogCallback(this, dialogAjaxNavTab);">
	<div class="pageFormContent" layoutH="56">
		<p style="width:260px;padding: 10px;">
			<label style="width:60px;padding: 0px;">起始日期：</label>
				<input type="text" name="beginDate" value="${beginDate}" class="date" readonly="true"/>
				<a class="inputDateButton" href="javascript:;" style="width:20px;padding: 0px;">选择</a>
		</p>
		<p style="width:260px;padding: 10px;">
			<label style="width:60px;padding: 0px;">截止日期：</label>
			<input type="text" name="endDate" value="${endDate}" class="date" readonly="true"/>
			<a class="inputDateButton" href="javascript:;" style="width:30px;padding: 0px;">选择</a>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>