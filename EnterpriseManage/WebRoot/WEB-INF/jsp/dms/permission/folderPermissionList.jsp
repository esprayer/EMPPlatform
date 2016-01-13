<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="dialog" mask="true" width="670" height="640" rel="objectNav" href="${ctx}/dms/folderPermission/createFolderPermission?FOLDER_ID=${FOLDER_ID}" title="添加评论"><span>添加权限</span></a></li>			
			<li class="line">line</li>								
		</ul>
</div>
<div class="panel"  style="padding:0px;border-width:0 0px;background:no-repeat;margin:0;">
	<div  style="padding:0px;border-width:0 0px;background-color: white;height:90px">						
		<table id="folderTable" class="list" width="98%" style="cursor:pointer">
			<thead>
				<tr>
					<th align='center'>部门名称</th>
					<th align='center'>用户</th>
					<th align='center'>管理</th>
					<th align='center'>浏览</th>
					<th align='center'>创建</th>
					<th align='center'>阅读</th>
					<th align='center'>删除</th>
					<th align='center'>打印</th>
					<th align='center'>下载</th>			
					<th align='center'>编辑</th>			
					<th align='center'>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${permission}" varStatus="s">
					<tr target="slt_objId" rel="${item.f_FOLDERID}">
						<td align='center'>
							${item.f_BMMC}
						</td>
						<td align='center'>
							${item.f_USERNAME}
						</td>
						<td align='center'>
							<c:choose> 
						    	<c:when test="${fn:contains(item.f_PERMISSION, 'M')}"> 				
						    		<IMG border=0 src="${ctx}/img/docico/icon-yes.gif">				
						    	</c:when> 
					   		</c:choose>
						</td>
						<td align='center'>
							<c:choose> 
						    	<c:when test="${fn:contains(item.f_PERMISSION, 'L')}"> 				
						    		<IMG border=0 src="${ctx}/img/docico/icon-yes.gif">				
						    	</c:when> 
					   		</c:choose>
						</td>
						<td align='center'>
							<c:choose> 
						    	<c:when test="${fn:contains(item.f_PERMISSION, 'C')}"> 				
						    		<IMG border=0 src="${ctx}/img/docico/icon-yes.gif">				
						    	</c:when> 
					   		</c:choose>
						</td>
						<td align='center'>
							<c:choose> 
						    	<c:when test="${fn:contains(item.f_PERMISSION, 'R')}"> 				
						    		<IMG border=0 src="${ctx}/img/docico/icon-yes.gif">				
						    	</c:when> 
					   		</c:choose>
						</td>
						<td align='center'>
							<c:choose> 
						    	<c:when test="${fn:contains(item.f_PERMISSION, 'D')}"> 				
						    		<IMG border=0 src="${ctx}/img/docico/icon-yes.gif">				
						    	</c:when> 
					   		</c:choose>
						</td>
						<td align='center'>
							<c:choose> 
						    	<c:when test="${fn:contains(item.f_PERMISSION, 'P')}"> 				
						    		<IMG border=0 src="${ctx}/img/docico/icon-yes.gif">				
						    	</c:when> 
					   		</c:choose>
						</td>
						<td align='center'>
							<c:choose> 
						    	<c:when test="${fn:contains(item.f_PERMISSION, 'U')}"> 				
						    		<IMG border=0 src="${ctx}/img/docico/icon-yes.gif">				
						    	</c:when> 
					   		</c:choose>
						</td>
						<td align='center'>
							<c:choose> 
						    	<c:when test="${fn:contains(item.f_PERMISSION, 'E')}"> 				
						    		<IMG border=0 src="${ctx}/img/docico/icon-yes.gif">				
						    	</c:when> 
					   		</c:choose>
						</td>
						<td align='center'>
							<a class="btnEdit" title="编辑" target="dialog" mask="true" width="520" height="150" rel="objectNav" href="${ctx}/dms/folderPermission/load?SELECT_ID=${item.f_ID}&FOLDER_ID=${FOLDER_ID}"></a>
							<a class="btnDel" title="是否 删除该权限？" target="ajaxTodo" mask="true" href="${ctx}/dms/folderPermission/delete?SELECT_ID=${item.f_ID}&FOLDER_ID=${FOLDER_ID}&reloadDiv=reloadDiv&rel=folderPermissionLiBox&forwardUrl=${ctx}/dms/folderPermission/folderPermissionList?FOLDER_ID=${FOLDER_ID}"></a>
						</td>
					</tr>	
				</c:forEach>								
			</tbody>
		</table>
	</div>
</div>