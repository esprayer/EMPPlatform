<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/management/base/user/insert/${userobj.USER_ORGID}?rel=userLiBox&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>用户编号: </label>
			<input type="text" name="USER_ID" value="${userobj.USER_ID}" class="required"/>
		</p>
		<p>
			<label>用户名称: </label>
			<input type="text" name="USER_NAME" value="${userobj.USER_NAME}" class="required"/>
		</p>
		<p>
			<label>用户密码: </label>
			<input name="USER_PASS" value="111111" type="password" minlength="6" maxlength="12" class="required"/>
		</p>
		<p>
			<label>手机号码: </label>
			<input type="text" name="USER_PHONE1" value="${userobj.USER_PHONE1}" class="required"/>
		</p>
		<p>
			<label>办公电话: </label>
			<input type="text" name="USER_PHONE2" value="${userobj.USER_PHONE2}" class="required"/>
		</p>
		<p>
			<label>用户邮箱: </label>
			<input type="text" name="USER_MAIL1" value="${userobj.USER_MAIL1}" class="required"/>
		</p>
		<p>
			<label>是否管理员: </label>
			<input name="USER_ISADMIN" value="1" type="checkbox">
		</p>			
		<p>
			<label>所属部门: </label>
			<input disabled="true" type="text" name="USER_ORGID}" value="${userobj.USER_ORGID}" class="required"/>
		</p>
		<p>
			<label>使用状态: </label>
			<select name="USER_DISABLE" style="width:135px" class="combox" >
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