<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include.inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title><fmt:message key="ui.title" /></title>

<link href="${ctx}/styles/dwz/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/dwz/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" />

<!--[if IE]>
<link href="${ctx}/styles/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->

<script src="${ctx}/styles/dwz/js/speedup.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/js/jquery-1.7.1.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>

<script src="${ctx}/styles/dwz/js/dwz.min.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<script src="${ctx}/styles/dwz/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/raphael.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/raphael-min.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/g.raphael.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/g.bar.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/g.line.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/g.pie.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/g.dot.js"></script>

<script type="text/javascript">
$(function(){	
	DWZ.init("${ctx}/styles/dwz/dwz.frag.xml", {
		loginUrl:"${ctx}/management/index/login", loginTitle:"Login",	// 弹出登录对话框
		loginUrl:"${ctx}/management/index/login",	// 跳到登录页面
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${ctx}/styles/dwz/themes"});
		}
	});

});
function logout(){
	var a = confirm("是否退出系统?");
	if(a){
		window.location="${ctx}/management/logout";
	}else{
		
	}
}

</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="javascript:void(0)">Logo</a>
				<ul class="nav">
					<li><a href="${ctx}/management/user!editContext.do" target="dialog" mask="true">用户信息</a></li>
					<li><a href="${ctx}/management/index!editPwd.do" target="dialog" mask="true">修改密码</a></li>
					
					<li><a href="#" onClick="logout();" title="是否退出系统？">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="azure"><div class="selected">天蓝</div></li>
				</ul>
			</div>
		</div>
		
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>菜单</h2><div>collapse</div></div>
			
				<div class="accordion" fillSpace="sideBar">					
					<div class="accordionHeader">
						<h2><span>Folder</span>标准管理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a>元数据管理</a>
								<ul>
									<li><a href="${ctx}/management/base/ywxt" target="navTab" rel="ywxtLiNav">业务系统</a></li>
									<li><a href="${ctx}/management/base/menu" target="navTab" rel="menuLiNav">功能菜单</a></li>
									<li><a href="${ctx}/standard/metadata/object" target="navTab" rel="metadataLiNav">数据对象定义</a></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:void(0)"><span><span class="home_icon">系统首页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:void(0)">系统首页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div>
						<div class="pageFormContent">
						</div>

					</div>
				</div>
			</div>
		</div>

	</div>
	
   <div id="footer"><fmt:message key="ui.copyrights" /></div>
</body>
</html>