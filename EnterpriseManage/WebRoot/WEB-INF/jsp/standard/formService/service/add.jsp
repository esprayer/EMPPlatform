<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/standard/formService/insert?navTabId=formServiceLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<input type="hidden" name="OBJ_ID" value="${metadataObjectCol.OBJ_ID}"/>
		<p>
			<label>服务编号: </label>
			<input type="text" name="BBZD_BH" value="${dbformObject.BBZD_BH}" class="required"/>
		</p>
		<p>
			<label>服务名称: </label>
			<input type="text" name="BBZD_MC" value="${dbformObject.BBZD_MC}" class="required"/>
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