<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/sysConfigure/suppliers/insert?navTabId=suppliersLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>供应商编号: </label>
			<input type="text" name="F_DWBH" value="${hydwObject.F_DWBH}" class="required"/>
		</p>
		<p>
			<label>供应商名称: </label>
			<input type="text" name="F_DWMC" value="${hydwObject.F_DWMC}" class="required"/>
		</p>
		<p>
			<label>供应商简称: </label>
			<input type="text" name="F_DWJC" value="${hydwObject.F_DWJC}" class="required"/>
		</p>
		<p>
			<label>联系人: </label>
			<input type="text" name="F_LXR" value="${hydwObject.F_LXR}" class="required"/>
		</p>
		<p>
			<label>联系电话: </label>
			<input type="text" name="F_LXDH" value="${hydwObject.F_LXDH}" class="required"/>
		</p>
		<p>
			<label>传真: </label>
			<input type="text" name="F_CZ" value="${hydwObject.F_CZ}" class="required"/>
		</p>	
		<p>
			<label>地址: </label>
			<input type="text" name="F_DZ" value="${hydwObject.F_DZ}" class="required"/>
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