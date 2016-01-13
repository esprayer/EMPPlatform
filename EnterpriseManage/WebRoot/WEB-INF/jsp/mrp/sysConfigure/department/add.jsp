<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/sysConfigure/department/insert?navTabId=departmentLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>部门编号: </label>
			<input type="text" name="F_BMBH" value="${hybmObject.F_BMBH}" class="required"/>
		</p>
		<p>
			<label>部门名称: </label>
			<input type="text" name="F_BMMC" value="${hybmObject.F_BMMC}" class="required"/>
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