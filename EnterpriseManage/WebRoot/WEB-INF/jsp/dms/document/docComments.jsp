<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<div class="panelBar">
	<ul class="toolBar">
		<li><a class="add" target="dialog" mask="true" width="420" height="150" rel="objectNav" href="${ctx}/dms/docNote/add?F_DOCID=${F_DOCID}" title="添加评论"><span>添加</span></a></li>			
		<li class="line">line</li>								
	</ul>
</div>
	
<div class="panel"  style="padding:0px;border-width:0 0px;background:no-repeat;margin:0;">
	<div  style="padding:0px;border-width:0 0px;background-color: white;height:90px">						
		<table id="folderTable" class="list" width="98%" style="cursor:pointer">
			<thead>
				<tr>
					<th width="15"></th>
					<th width="40">作者</th>
					<th width="230">内容</th>
					<th width="40">时间</th>
					<th width="40">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="item" items="${noteList}" varStatus="s">
				<tr target="slt_objId" rel="${item.f_ID}">	
					<td>${s.index + 1}</td>			
					<td>${item.f_WRITERNAME}</td>
					<td>${item.f_MS}</td>				
					<td>
						<fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<c:if test="${item.f_FLAG==1}">
							<a class="btnEdit" title="编辑" target="dialog" mask="true" width="420" height="150" rel="objectNav" href="${ctx}/dms/docNote/edit?F_ID=${item.f_ID}&F_DOCID=${item.f_DOCID}" class="btnEdit"></a>
							<a class="btnDel" title="是否 删除该评论？" target="ajaxTodo" mask="true" href="${ctx}/dms/docNote/delete?F_ID=${item.f_ID}&reloadDiv=reloadDiv&rel=docComments&forwardUrl=${ctx}/dms/docNote/load?F_DOCID=${item.f_DOCID}"></a>
						</c:if>
					</td>				
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
