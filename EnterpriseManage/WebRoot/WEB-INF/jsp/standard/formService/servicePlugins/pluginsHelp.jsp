<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageHeader">
	<div class="searchBar" >
		<ul class="searchContent">
			<li>
				<label>服务编号:</label>
				<input readonly="readonly" class="textInput" name="keywords" value="${serviceID}" type="text">
			</li>	  		
		</ul>
	</div>
</div>
<div class="pageContent">

	<table class="table" layoutH="78" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th>索引</th>
				<th>插件编号</th>
				<th>插件名称</th>				
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pluginsList}" varStatus="s">
				<tr target="slt_objId" rel="${item.compID}">
					<td>${s.index + 1}</td>
					<td>${item.compID}</td>
					<td>${item.compName}</td>					
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({compName:'${item.compName}', compID:'${item.compID}', compClazz:'${item.compClazz}'})" title="查找带回">选择</a>
					</td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>
</div>