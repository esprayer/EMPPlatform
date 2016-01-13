<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,esyt.framework.persistence.qxgl.beans.SYSMenu"%>
<%@ include file="../../../include.inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title><fmt:message key="ui.title" /></title>

<link href="${ctx}/styles/dwz/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/dwz/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/dwz/themes/css/ContextMenu.css" rel="stylesheet" type="text/css" />

<!--[if IE]>
<link href="${ctx}/styles/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->

<script src="${ctx}/styles/dwz/js/jquery-1.7.1.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>

<script src="${ctx}/styles/dwz/js/dwz.min.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<script src="${ctx}/styles/dwz/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>
<script src="${ctx}/styles/dwz/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>

<script src="${ctx}/styles/dwz/js/jquery.json-2.2.js" type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/raphael.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/raphael-min.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/g.raphael.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/g.bar.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/g.line.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/g.pie.js"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/jsp/analyze/chart/g.dot.js"></script>

<style type="text/css">
	.welcome{ background:url(${ctx}/img/login/welcome.jpg) right bottom no-repeat; min-height:250px; padding-top:40px; padding-right:300px;}
	.welcome h2{font-size:28px; letter-spacing:5px; color:#039; text-align: center; line-height:150px; display:block;}
}

</style>

<script type="text/javascript">
var arryMenuGroup = [];
function MenuGroup(){
	this.menu = new Menu();
	this.subMenus = [];
}
function Menu(){
	this.menu_bh = "";
	this.menu_mc = "";
	this.f_js = "";
	this.f_mx = "";
	this.f_url = "";
	this.f_attr = "";
	this.f_target = "";
	this.f_rel = "";
	this.f_parent = "";
}
var menuGroup = new MenuGroup();
<% 
List<SYSMenu> listMenu = (List)request.getAttribute("menuList");
SYSMenu menuObj = null;
if(null!=listMenu){
for(int i=0;i<listMenu.size();i++){
	 menuObj = listMenu.get(i);
	 String js = String.valueOf(menuObj.getF_JS());
%>
var menu = new Menu();
menu.menu_bh = '<%=menuObj.getMENU_BH()%>';
menu.menu_mc = '<%=menuObj.getMENU_MC()%>';
menu.f_js    = '<%=js%>';
menu.f_mx    = '<%=menuObj.getF_MX()%>';
menu.f_url  = '<%=menuObj.getURL()%>';
menu.f_target  = '<%=menuObj.getF_TARGET()%>';
menu.f_rel  = '<%=menuObj.getF_REL()%>';
menu.f_attr = '<%=menuObj.getF_ATTR()%>';
menu.f_parent= '<%=menuObj.getF_PARENT()%>';
<%
   if(js.equals("1")){
%>
menuGroup = new MenuGroup();
menuGroup.menu = menu;
arryMenuGroup.push(menuGroup);
<%}else{%>
menuGroup.subMenus.push(menu);
  <%}
}
}
%> 
$(document).ready(function(){
	
	  var parent;
	  var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]");
	  var createMenu = function (){
		  $.each(arryMenuGroup,function(i,menuGroup){
			  creatMenuGroup(menuGroup);
			  createSubMenu(menuGroup.subMenus);
		  });
	   }();
	 function creatMenuGroup(menuGroup){
		 $('#menuContent').append("<div class='accordionHeader'>"
						         +"    <h2><span>Folder</span>"+menuGroup.menu.menu_mc+"</h2>"
						         +"</div>"
						         +"<div id='MRPNODE"+menuGroup.menu.menu_bh+"' class=\"accordionContent\"><ul class=\"tree treeFolder\"></ul></div>");
	 }
	 function createSubMenu(subMeus){
		 $.each(subMeus,function(i,menu){
			  parent = menu['f_parent'].replace(pattern,'');
			  if(menu['f_mx']==1){
				  $('#MRPNODE'+parent+'>ul').append('<li id="MRPNODE'+menu['menu_bh']+'"><a href="${ctx}'+menu['f_url']+'" target="'+menu['f_target']+'" '+menu['f_attr']+' rel="'+menu['f_rel']+'">'+menu['menu_mc']+'</a></li>');
			  }else if(menu['f_mx']==0){
				  $('#MRPNODE'+parent+'>ul').append('<li id="MRPNODE'+menu['menu_bh']+'"><a>'+menu['menu_mc']+'</a>'
									   +'<ul>'
									   +'</ul></li>');
			  }
		  });
	 }
});
$(function(){	
	DWZ.init("${ctx}/styles/dwz/dwz.frag.xml", {
		loginUrl:"${ctx}/management/timeoutLoginin?TYPE=MRP", loginTitle:"Login",	// 弹出登录对话框
		loginUrl:"${ctx}/management/timeoutLoginin?TYPE=MRP",	// 跳到登录页面
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
					<a class="logo" href="javascript:void(0)" style="width:450px;background:none;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${ctx}/styles/dwz/themes/default/images/jxclogo.png', sizingMethod='crop');">Logo</a>				<ul class="nav">
					<li><a href="${ctx}/management/user!editContext.do" target="dialog" mask="true">用户信息</a></li>
					<li><a href="${ctx}/management/index!editPwd.do" target="dialog" mask="true">修改密码</a></li>
					
					<li><a href="#" onClick="logout();" title="是否退出系统？">退出</a></li>
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
			
				<div id="menuContent" class="accordion" fillSpace="sideBar">
					
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
						<div class="accountInfo">
							<div class="right">
								<p><fmt:formatDate value="${model.now}" pattern="dd MMMM yyyy, EEEE"/></p>
							</div>
							
							<p></p>
							<p>欢迎您，${contextUser.USER_NAME} </p>
						</div>
						<div class="pageContent sortDrag" selector="h1" layoutH="42">

							<div class="panel collapse" defH="150">
								<h1>材料超出预警设置</h1>
								<ul id="CLYJ" class="contextMenu">
									<li id="refresh" class="refresh" targetTag="CLYJ">
										<a target="reloadDiv" rel="HYCLYJ" href="${ctx}/queryAnalyse/homePage/clyj">刷新</a>
									</li>									
								</ul>
								<div contentMenu="CLYJ" id="HYCLYJ">										
									<c:import url="home/clyj.jsp"></c:import>
								</div>
							</div>
							<div class="panel close collapse" minH="100" defH="150">
								<h1>材料入库单保存未提交</h1>
								<ul id="HYRKD" class="contextMenu">
									<li id="refresh" class="refresh">
										<a target="reloadDiv" rel="HYRKD" href="${ctx}/queryAnalyse/homePage/hyrkd?USER_ID=${contextUser.USER_ID}">刷新</a>
									</li>									
								</ul>
								<div contentMenu="HYRKD" id="HYRKD">	
									<c:import url="home/hyrkd.jsp"></c:import>
								</div>
							</div>
							<div class="panel close collapse" minH="100" defH="150">
								<h1>材料出库单保存未提交</h1>
								<ul id="HYCKD" class="contextMenu">
									<li id="refresh" class="refresh">
										<a target="reloadDiv" rel="HYCKD" href="${ctx}/queryAnalyse/homePage/hyckd?USER_ID=${contextUser.USER_ID}">刷新</a>
									</li>									
								</ul>
								<div contentMenu="HYCKD" id="HYCKD">		
									<c:import url="home/hyckd.jsp"></c:import>
								</div>
							</div>
							<div class="panel close collapse" minH="100" defH="150">
								<h1>材料退货单保存未提交</h1>
								<ul id="HYTHD" class="contextMenu">
									<li id="refresh" class="refresh">
										<a target="reloadDiv" rel="HYTHD" href="${ctx}/queryAnalyse/homePage/hythd?USER_ID=${contextUser.USER_ID}">刷新</a>
									</li>									
								</ul>
								<div contentMenu="HYTHD" id="HYTHD">
									<c:import url="home/hythd.jsp"></c:import>
								</div>
							</div>
							<div class="panel close collapse" minH="100" defH="150">
								<h1>材料调拨单保存未提交</h1>
								<ul id="HYDBD" class="contextMenu">
									<li id="refresh" class="refresh">
										<a target="reloadDiv" rel="HYDBD" href="${ctx}/queryAnalyse/homePage/hydbd?USER_ID=${contextUser.USER_ID}">刷新</a>
									</li>									
								</ul>
								<div contentMenu="HYDBD" id="HYDBD">
									<c:import url="home/hydbd.jsp"></c:import>
								</div>
							</div>
							<div class="panel close collapse" minH="100" defH="150">
								<h1>材料厂商退货</h1>
								<ul id="CLCSTH" class="contextMenu">
									<li id="refresh" class="refresh">
										<a target="reloadDiv" rel="CLCSTH" href="${ctx}/queryAnalyse/homePage/clcsth">刷新</a>
									</li>									
								</ul>
								<div contentMenu="CLCSTH" id="CLCSTH">
									<c:import url="home/clcsth.jsp"></c:import>
								</div>
							</div>
						</div>
						<!--  <div class="pageFormContent" layoutH="80">
							<div class="welcome">
								<h2>欢迎您使用物料管理系统！</h2>
								
							</div>
						</div>
-->
					</div>
				</div>
			</div>
		</div>
	</div>
	
   <div id="footer"><fmt:message key="ui.copyrights" /></div>
</body>
</html>