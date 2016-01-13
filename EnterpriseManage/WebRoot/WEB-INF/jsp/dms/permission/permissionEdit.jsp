<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/dms/folderPermission/save?rel=folderPermissionLiBox&callbackType=closeCurrent&SELECT_ID=${permission.f_ID}&FOLDER_ID=${FOLDER_ID}&forwardUrl=${ctx}/dms/folderPermission/folderPermissionList" class="pageForm required-validate" onsubmit="return validateCallback(this, divReload);">
	<div class="pageFormContent" layoutH="106">
		<input type="hidden" name="G1" />
		<input name="G1" value="1" readonly="readonly"		<c:choose> 
								   		<c:when test="${fn:contains(permission.f_PERMISSION, 'M0')}"> 	
											checked=true disabled 							
										</c:when> 
										<c:when test="${fn:contains(permission.f_PERMISSION, 'M1')}"> 	
											checked=true																			
										</c:when> 
								    </c:choose> type="checkbox">
		 管理&nbsp;&nbsp;&nbsp;&nbsp;
		 
		<input type="hidden" name="G2" />
		<input name="G2" value="1" <c:choose> 
								   		<c:when test="${fn:contains(permission.f_PERMISSION, 'L0')}"> 	
											checked=true disabled 
										</c:when> 
										<c:when test="${fn:contains(permission.f_PERMISSION, 'L1')}"> 	
											checked=true									
										</c:when> 
								    </c:choose> 
								    type="checkbox">
		浏览&nbsp;&nbsp;&nbsp;&nbsp;
								    
		<input type="hidden" name="G3" />
		<input name="G3" value="1" <c:choose> 
								   		<c:when test="${fn:contains(permission.f_PERMISSION, 'C0')}"> 	
											checked=true disabled 						
										</c:when> 
										<c:when test="${fn:contains(permission.f_PERMISSION, 'C1')}"> 	
											checked=true									
										</c:when> 
								    </c:choose> type="checkbox">
		 创建&nbsp;&nbsp;&nbsp;&nbsp;
		 
		<input type="hidden" name="G4" />
		<input name="G4" value="1" <c:choose> 
								   		<c:when test="${fn:contains(permission.f_PERMISSION, 'R0')}"> 	
											checked=true disabled 
										</c:when> 
										<c:when test="${fn:contains(permission.f_PERMISSION, 'R1')}"> 	
											checked=true									
										</c:when> 
							    </c:choose> type="checkbox">
		阅读	&nbsp;&nbsp;&nbsp;&nbsp;
		
		<input type="hidden" name="G5" />
		<input name="G5" value="1" <c:choose> 
								   		<c:when test="${fn:contains(permission.f_PERMISSION, 'D0')}"> 	
											checked=true disabled 
										</c:when> 
										<c:when test="${fn:contains(permission.f_PERMISSION, 'D1')}"> 	
											checked=true									
										</c:when>
								    </c:choose> type="checkbox">
		删除&nbsp;&nbsp;&nbsp;&nbsp;				    

		<input type="hidden" name="G6" />
		<input name="G6" value="1" <c:choose> 
								   		<c:when test="${fn:contains(permission.f_PERMISSION, 'P0')}"> 	
											checked=true disabled 
										</c:when> 
										<c:when test="${fn:contains(permission.f_PERMISSION, 'P1')}"> 	
											checked=true 
										</c:when> 
								    </c:choose> type="checkbox">
		打印	&nbsp;&nbsp;&nbsp;&nbsp;					    

		<input type="hidden" name="G7" />
		<input name="G7" value="1" <c:choose> 
								   		<c:when test="${fn:contains(permission.f_PERMISSION, 'U0')}"> 	
											checked=true  disabled 
										</c:when> 
										<c:when test="${fn:contains(permission.f_PERMISSION, 'U1')}"> 	
											checked=true 
										</c:when> 
								    </c:choose> type="checkbox">
		下载&nbsp;&nbsp;&nbsp;&nbsp;						    

		<input type="hidden" name="G8" />
		<input name="G8" value="1" <c:choose> 
								   		<c:when test="${fn:contains(permission.f_PERMISSION, 'E0')}"> 	
											checked=true disabled 									
										</c:when> 
										<c:when test="${fn:contains(permission.f_PERMISSION, 'E1')}"> 	
											checked=true									
										</c:when> 
								    </c:choose> type="checkbox">	
		编辑	&nbsp;&nbsp;&nbsp;&nbsp;					    		
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>