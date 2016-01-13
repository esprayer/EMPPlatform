<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/sysConfigure/user/insert/${hyzgObject.f_SXBM}?rel=userLiBox&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>用户编号: </label>
			<input type="text" name="F_ZGBH" value="${hyzgObject.f_ZGBH}" class="required"/>
		</p>
		<p>
			<label>用户名称: </label>
			<input type="text" name="F_ZGMC" value="${hyzgObject.f_ZGMC}" class="required"/>
		</p>
		<p>
			<label>手机号码: </label>
			<input type="text" name="F_SJHM" value="${hyzgObject.f_SJHM}" class="required"/>
		</p>
		<p>
			<label>办公电话: </label>
			<input type="text" name="F_BGDH" value="${hyzgObject.f_BGDH}" class="required"/>
		</p>
		<p>
			<label>用户邮箱: </label>
			<input type="text" name="F_YHYX" value="${hyzgObject.f_YHYX}" class="required"/>
		</p>
		<p>
			<label>是否管理员: </label>
			<input name="F_SFGLY" value="1" type="checkbox">
		</p>			
		<p>
			<label>所属部门: </label>
			<input disabled="true" type="text" name="F_SXBM" value="${hyzgObject.f_SXBM}" class="required"/>
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