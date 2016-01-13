<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<style type="text/css">
		<!--
		.grid{width:1000px;}	
		-->
</style>
<div class="tabs">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li><a href="javascript:;"><span>文档信息</span></a></li>
				<c:if test="${readFlag == '1'}">
					<li><a href="javascript:;"><span>修订版管理</span></a></li>		
					<li><a href="javascript:;"><span>文档评论</span></a></li>
				</c:if>				
				<c:if test="${adminFlag == '1'}">
					<li><a href="javascript:;"><span>文档审计</span></a></li>		
				</c:if>						
			</ul>
		</div>
	</div>
	<div class="tabsContent">
		<div id="docInfoLiBox">			
			<c:import url="docInfo.jsp"></c:import>
		</div>
		
		<c:if test="${readFlag == '1'}">
			<div id="docVersionLiBox" class="pageContent">			
				<c:import url="docVersionManagement.jsp"></c:import>
			</div>
			<div id="docComments" class="pageContent">
				<c:import url="docComments.jsp"></c:import>
			</div>
		</c:if>
		
		<c:if test="${adminFlag == '1'}">
			<div id="docAuditLiBox" class="pageContent">
				<c:import url="docAudit.jsp"></c:import>
			</div>	
		</c:if>		
	</div>
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
</div>