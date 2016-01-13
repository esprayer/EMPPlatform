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
	this.f_parent = "";
	this.f_op = "";
}
<% 
List<SYSMenu> listMenu = (List)request.getAttribute("menuList");
SYSMenu menu = null;
if(null!=listMenu){
for(int i=0;i<listMenu.size();i++){
	  menu = listMenu.get(i);
	  int js = menu.getF_JS();
%>
var menuGroup;
var menu = new Menu();
menu.menu_bh = '<%=menu.getMENU_BH()%>';
menu.menu_mc = '<%=menu.getMENU_MC()%>';
menu.f_js    = '<%=js%>';
menu.f_mx    = '<%=menu.getF_MX()%>';
menu.f_parent= '<%=menu.getF_PARENT()%>';
menu.f_op    = '';
<%
   if(js==1){
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
	  var parent = "#ROOT";
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
						         +"<div id='node"+menuGroup.menu.menu_bh+"' class=\"accordionContent\"><ul class=\"tree treeFolder\"></ul></div>");
	 }
	 function createSubMenu(subMeus){
		 $.each(subMeus,function(i,menu){
			  parent = menu['f_parent'].replace(pattern,'');
			  if(menu['f_mx']==1){
				  $('#node'+parent+'>ul').append('<li id="node'+menu['menu_bh']+'"><a href="${ctx}/" target="ajax" rel="menuListBox">'+menu['menu_mc']+'</a></li>');
			  }else if(menu['f_mx']==0){
				  $('#node'+parent+'>ul').append('<li id="node'+menu['menu_bh']+'"><a>'+menu['menu_mc']+'</a>'
									   +'<ul>'
									   +'</ul></li>');
			  }
		  });
	 }
});

$(function(){	
	DWZ.init("${ctx}/styles/dwz/dwz.frag.xml", {
		loginUrl:"${ctx}/management/index/login", loginTitle:"Login",	// 弹出登录对话框
		loginUrl:"${ctx}/management/index/login",	// 跳到登录页面
		debug:true,	// 调试模式 【true|false】
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
						<div class="pageFormContent">
						<!--  -->
						<div class="pageContent">
							<div class="tabs">
							<div class="tabsHeader">
								<div class="tabsHeaderContent">
									<ul>
										<li><a href="javascript:;"><span>代办任务</span></a></li>
										<li><a href="javascript:;"><span>已办任务</span></a></li>
									</ul>
								</div>
							</div>
							
							<div class="tabsContent">
								<div>
									<table class="table" layoutH="50">
										<thead>
											<tr>
												<th width="25">序号</th>
												<th width="60">会计期间</th>
												<th width="60">单据编号</th>
												<th width="60">仓库名称</th>
												<th width="120">备注</th>
												<th width="60">领料人</th>	
												<th width="60">录入人</th>	
												<th width="60">单据状态</th>				
												<th width="60">入库日期</th>			
												<th width="70">单据修改日期</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="item" items="${hyckdList}" varStatus="s">
												<tr target="slt_objId" rel="${item.f_DJBH}?F_KJQJ=${item.f_KJQJ}&F_GUID=${item.f_GUID}">
													<td>${s.index + 1}</td>
													<td>${item.f_KJQJ}</td>
													<td>${item.f_DJBH}</td>		
													<td>${item.f_CKMC}</td>				
													<td>${item.f_BZ}</td>
													<td>${item.f_LLRMC}</td>
													<td>${item.f_ZDRMC}</td>
													<td>
														<c:if test="${item.f_DJZT=='0'}">未提交</c:if>
														<c:if test="${item.f_DJZT=='1'}">已提交</c:if>
													</td>					
													<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
													<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>				
												</tr>	
											</c:forEach>
										</tbody>
									</table>
								</div>
								
								<div>
									<table class="table" layoutH="50">
										<thead>
											<tr>
												<th width="25">序号</th>
												<th width="60">会计期间</th>
												<th width="60">单据编号</th>
												<th width="60">仓库名称</th>
												<th width="120">备注</th>
												<th width="60">领料人</th>	
												<th width="60">录入人</th>	
												<th width="60">单据状态</th>				
												<th width="60">入库日期</th>			
												<th width="70">单据修改日期</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="item" items="${hyckdList}" varStatus="s">
												<tr target="slt_objId" rel="${item.f_DJBH}?F_KJQJ=${item.f_KJQJ}&F_GUID=${item.f_GUID}">
													<td>${s.index + 1}</td>
													<td>${item.f_KJQJ}</td>
													<td>${item.f_DJBH}</td>		
													<td>${item.f_CKMC}</td>				
													<td>${item.f_BZ}</td>
													<td>${item.f_LLRMC}</td>
													<td>${item.f_ZDRMC}</td>
													<td>
														<c:if test="${item.f_DJZT=='0'}">未提交</c:if>
														<c:if test="${item.f_DJZT=='1'}">已提交</c:if>
													</td>					
													<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
													<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>				
												</tr>	
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<div class="tabsFooter">
								<div class="tabsFooterContent"></div>
							</div>
						</div>
					</div>
					<!--  -->
						</div>

					</div>
				</div>
			</div>
		</div>

	</div>
	
   <div id="footer"><fmt:message key="ui.copyrights" /></div>
</body>
</html>