<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include.inc.jsp"%>

<div class="pageContent" style="height: 121px;">
	<form method="post" action="${ctx}/management/timeoutLogininCheck?TYPE=${TYPE}" class="pageForm required-validate" onsubmit="return validateCallback(this);">	
		<div class="pageFormContent">
			<p style="width:300px;">
				<label style="text-align:right">用户名: </label>
				<input type="text" name="userId" value="" class="required"/>
			</p>
			<p style="width:300px;">
				<label style="text-align:right">密码: </label>
				<input type="password" name="password" class="validate[required] login_input" id="password"/>
			</p>
		</div>
		<div class="formBar">
			<ul>		
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">登陆</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
</div>