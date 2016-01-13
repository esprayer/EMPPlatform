<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="../../include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>恒源电器有限公司进销存管理系统</title>
<link href="${ctx}/styles/management/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/management/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/management/themes/css/login.css" rel="stylesheet" type="text/css" />

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
body {
	background:#3c7fb5 url(${ctx}/img/login/bg_login.jpg) repeat-x left top;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
body,table,td,div {
	font-size: 12px;
	line-height: 24px;
}
.error{color:red;text-align:center;height:10px;margin-bottom:15px}
.loginForm { display:block; width:240px; margin-top:0px;margin-left:50px; padding:0px 20px 0 20px;border-style:solid;border:1px;}
.loginForm label { float:left; width:70px; padding:0 0 0 10px; line-height:25px; color:#4c4c4c; font-size:14px;}
.loginForm .login_input {}
.loginForm .code { float:left; margin-right:5px;}
.textfile {background:url(${ctx}/img/login/bg_login_textfile.gif) no-repeat left top; padding: 0px 2px; height: 29px; width: 143px; border: 0; }
-->
</style></head>

<body>
<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="${ctx}/img/login/top_login.jpg" width="596" height="331" /></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="99"><img src="${ctx}/img/login/login_06.jpg" width="99" height="139" /></td>
        <td background="${ctx}/img/login/bg_form.jpg">
        <div id="errortip" class="error">${error}</div>
        	<div class="loginForm">
        	<form method="post" action="${ctx}/management/login" id="formID" >
				<label>用户名:</label>
				<input type="text" name="userId" style="width: 150px;" class="validate[required] login_input" id="userId" value="${username}"/>
				
				<label>密&nbsp;&nbsp;码:</label>
				<input type="password" name="password" style="width: 150px;" class="validate[required] login_input" id="password"/>
				
				
		    <div style="margin-left:80px;margin-top:3px">
		       <input type="submit" name="Submit" value="登录" />
		       <input type="reset" name="Submit2" value="重置" />
		    </div>
            </form>
            </div>
				
        </td>
        <td width="98" align="right"><img src="${ctx}/img/login/login_08.jpg" width="98" height="139" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="${ctx}/img/login/bottom_login.jpg" width="596" height="39" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center">版权所有：恒源电器有限公司</td>
  </tr>
</table>
</body>
</html>