<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/standard/formService/plugins/servicePlugins/update/${BBZD_BH}?serviceID=${serviceID}&rel=servicePluginsLiBox&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<input type="hidden" name=BBZD_BH value="${BBZD_BH}"/>
		<input type="hidden" name="serviceID" value="${serviceID}"/>
		<p>
			<label>插件编号: </label>
			<input  readonly="readonly" id="compID" class="required" name="compID" type="text" postField="keyword" suggestFields="compID,compName" lookupGroup="org1" value="${stub.compID}"/>
		</p>
		<p>
			<label>插件名称: </label>
			<input readonly="readonly" type="text" name="compName" value="${stub.compName}" class="required"/>
		</p>
		<p>
			<label>作用范围: </label>
			<select name="compScope" style="width:135px" class="combox" >
				<option value=1 <c:if test="${stub.compScope=='1'}">selected="selected"</c:if>>准备阶段</option>
				<option value=2 <c:if test="${stub.compScope=='2'}">selected="selected"</c:if>>执行阶段</option>
				<option value=3 <c:if test="${stub.compScope=='3'}">selected="selected"</c:if>>结束阶段</option>
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