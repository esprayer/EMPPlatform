<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/sysConfigure/department/update/${hybmObject.f_BMBH}?navTabId=departmentLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>部门编号: </label>
			<input disabled="true" type="text" name="F_BMBH" value="${hybmObject.f_BMBH}" class="required"/>
		</p>
		<p>
			<label>部门名称: </label>
			<input type="text" name="F_BMMC" value="${hybmObject.f_BMMC}" class="required"/>
		</p>	
		<p>
			<label>使用状态: </label>
			<select name="F_SYZT" style="width:135px" class="combox" >
				<option value=1 <c:if test="${hybmObject.f_SYZT==1}">selected="selected"</c:if>>启用</option>
				<option value=0 <c:if test="${hybmObject.f_SYZT==0}">selected="selected"</c:if>>停用</option>
			</select>			
		</p>		
		<p>
			<label>创建时间: </label>
			<fmt:formatDate value="${hybmObject.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</p>
		<p>
			<label>修改时间: </label>
			<fmt:formatDate value="${hybmObject.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>
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