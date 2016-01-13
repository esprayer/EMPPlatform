<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<style type="text/css">
		<!--
		.grid{width:1000px;}	
		-->
</style>
<input type="hidden" id="docType" name="docType" value="${docType}" />
<div class="tabs">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<c:if test="${flag=='1'}">
					<li><a href="javascript:;"><span>目录信息</span></a></li>
					<li><a href="javascript:;"><span>目录说明</span></a></li>
					<c:if test="${permissionManagerFlag==1}">
						<c:if test="${NODETYPE=='0' || NODETYPE=='1'}">
							<li><a href="javascript:;"><span>权限管理</span></a></li>
						</c:if>
					</c:if>	
				</c:if>					
			</ul>
		</div>
	</div>
	<div class="tabsContent">
		<c:if test="${flag=='1'}">
			<div id="folderInfoLiBox">
				<c:import url="folderInfo.jsp"></c:import>
			</div>
			<div id="folderDescribeLiBox" class="pageContent" layoutH="232">
				<c:import url="folderDescribe.jsp"></c:import>
			</div>
			<c:if test="${permissionManagerFlag==1}">
				<c:if test="${NODETYPE=='0' || NODETYPE=='1'}">
					<div id="folderPermissionLiBox" class="pageContent" layoutH="2">
						<c:import url="../permission/folderPermissionList.jsp"></c:import>
					</div>
				</c:if>
			</c:if>
		</c:if>
	</div>
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
</div>