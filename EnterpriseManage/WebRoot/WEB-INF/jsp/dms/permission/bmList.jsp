<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>



<ul id="allDeport" class="tree treeFolder expand">
	<!-- <li id="all"><a href="${ctx}/dms/folderPermission/bmzgList?F_BMBH=all&F_BMMC=所有部门" target="ajax" rel="userFolderPermissionLiBox">所有部门</a> -->
	<li id="all"><a>所有部门</a>	
		<ul>
			<c:forEach var="item" items="${bmList}" varStatus="s">
				<li><a id="${item.f_BMBH}" href="${ctx}/dms/folderPermission/bmzgList?F_BMBH=${item.f_BMBH}" target="ajax" rel="userFolderPermissionLiBox">${item.f_BMMC}</a></li>
			</c:forEach>
		</ul>
	</li>
</ul>