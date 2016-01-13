<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>


<div class=""  style="width:100%">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a title="确定批量授权？" target="selectedTodo"  posttype="string" targetType="dialog" rel="ids" href="${ctx}/management/qxgl/saveUserGn/${roleObj.roleId}?ywxtId=${ywxtId}&type=role&rel=menuListBox&forwardUrl=${ctx}/management/qxgl/menu/${parentMenu}?js=${js}" callback="divReload" class="add"><span>批量授权</span></a></li>
			<li><a class="delete" href="${ctx}/management/qxgl/delUserGn/${roleObj.roleId}?ywxtId=${ywxtId}&type=role&rel=menuListBox&forwardUrl=${ctx}/management/qxgl/menu/${parentMenu}?js=${js}" rel="ids" target="selectedTodo" callback="divReload" targetType="dialog"  title="确定批量取消授权吗?"><span>批量取消授权</span></a></li>
		</ul>
	</div>
		<table class="table" layoutH="200" style="width:99.8%" >
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="100" orderField="number" class="asc">菜单编号</th>
				<th orderField="name" width="180">菜单名称</th>
				<th width="90">授权</th>
			</tr>
		</thead>
		<tbody>
		 <c:forEach var="item" items="${SubMenu}" varStatus="s">
		   <tr target="menu_obj" rel="${s.index+1}">
				<td><input name="ids" value="${item.MENU_BH}" type="checkbox"></td>
				<td>${item.MENU_BH}</td>
				<td>${item.MENU_MC}</td>
				<td>
				 <c:if test="${item.F_OP==1}">
				     <a title="取消授权" target="ajaxTodo" href="${ctx}/management/qxgl/delUserGn/${roleObj.roleId}?ywxtId=${ywxtId}&type=role&rel=menuListBox&ids=${item.MENU_BH}&forwardUrl=${ctx}/management/qxgl/menu/${parentMenu}?js=${js}" callback="divReload" showTitle="true" class="btnDel">取消授权</a>
				 </c:if>
				 <c:if test="${item.F_OP==0}">
				     <a title="授权" target="ajaxTodo" href="${ctx}/management/qxgl/saveUserGn/${roleObj.roleId}?ywxtId=${ywxtId}&type=role&rel=menuListBox&ids=${item.MENU_BH}&forwardUrl=${ctx}/management/qxgl/menu/${parentMenu}?js=${js}" callback="divReload" showTitle="true" class="btnEdit">授权</a>
				 </c:if>
				  <c:if test="${item.F_OP==null}">
				     <a title="授权" target="ajaxTodo" href="${ctx}/management/qxgl/saveUserGn/${roleObj.roleId}?ywxtId=${ywxtId}&type=role&rel=menuListBox&ids=${item.MENU_BH}&forwardUrl=${ctx}/management/qxgl/menu/${parentMenu}?js=${js}" callback="divReload" showTitle="true" class="btnEdit">授权</a>
				 </c:if>
				
				</td>
			</tr>
		 </c:forEach>
		</tbody>
	</table>
</div>
