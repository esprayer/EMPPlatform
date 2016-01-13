<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include.inc.jsp"%>
<html  xmlns="http://www.w3.org/1999/xhtml">
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
			<title>恒源电气有限公司综合信息平台</title>
			<link href="${ctx}/styles/management/themes/default/style.css" rel="stylesheet" type="text/css" />
			<link href="${ctx}/styles/css/login.css" rel="stylesheet" type="text/css" />
		
			<!-- form验证 -->
			<link rel="stylesheet" href="${ctx}/styles/formValidator.2.2.1/css/validationEngine.jquery.css" type="text/css"/>
			<script src="${ctx}/styles/formValidator.2.2.1/js/jquery-1.6.min.js" type="text/javascript"></script>
			<script src="${ctx}/styles/formValidator.2.2.1/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
			<script src="${ctx}/styles/formValidator.2.2.1/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
			<script>
			    jQuery(document).ready(function(){
			        // binds form submission and fields to the validation engine
			        jQuery("#formID").validationEngine();
			    });
			</script>
	</head>

	<style type="text/css">
		<!--
		.container{width:100%;
	           height:370px;
			   top:20%;
			   position:absolute;
			   background: url(${ctx}/img/login/bg.png) repeat-x center;
			   font-family:Arial, Helvetica, sans-serif;}
		.index-logo{height:80px; width:720px; background:url(${ctx}/img/login/login-logo.png);}
		.index-mid{height:245px; width:720px; background:url(${ctx}/img/login/login-mid.jpg);}	   
		.index-text{width:405px; height:245px; background:url(${ctx}/img/login/login-text.jpg); float:right;}
		.error{color:red;text-align:center;height:10px;margin-bottom:15px}	 
		-->
	</style>

	<body>
		<div class="container">
			<div class="index">
				<div class="index-logo"></div>
				<div class="index-mid">
					<div class="index-text">
						<div class="index-text-title">登  录</div>
						<div class="index-text-con">
							<div id="errortip" class="error">${error}</div>
							<form method="post" action="${ctx}/management/login" id="formID" >
								<div class="index-box">
									<label>用户名：</label>
									<input type="text" name="userId" style="width: 150px;" class="validate[required] login_input" id="userId" value="${username }"/>
								</div>
								<div class="index-box">
									<label>密&nbsp;&nbsp; 码：</label>
									<input type="password" name="password" style="width: 150px;" class="validate[required] login_input" id="password"/>
								</div>
								<div class="bottom">
									<input type="submit" class="submtButtom" value="登 录"></input>
							   		<input type="reset" class="submtButtom" name="Submit2" value="重置" />
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="login-footer">Copyright © 胜利油田恒源电气有限责任公司. All Rights Reserved. 鲁ICP备11013137号-1</div>
			</div>
		</div>
	</body>
</html>