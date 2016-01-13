<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<div class="panel"  style="padding:0px;border-width:0 0px;background:no-repeat;margin:0;">
	<div  style="padding:0px;border-width:0 0px;background-color: white;height:90px">						
		<table id="folderTable" class="list" width="98%" style="cursor:pointer">
			<thead>
				<tr>
					<th></th>
					<th align='left'>操作人</th>
					<th align='left'>日志</th>
					<th align='left'>时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${docLogList}" varStatus="s">
					<tr target="slt_objId" rel="${item.f_OPID}">
						<td>${s.index + 1}</td>	
						<td>
							${item.f_OPERATORNAME}(${item.f_OPERATOR})
						</td>
						<td>
							${item.f_MSG}
						</td>
						<td>
							<fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>	
				</c:forEach>								
			</tbody>
		</table>
		
		<div class="formBar">
			<ul>
				<!-- <li><div class="buttonActive"><div class="buttonContent"><button type="submit">查看所有审计记录</button></div></div></li> -->			
			</ul>
		</div>
	</div>
</div>