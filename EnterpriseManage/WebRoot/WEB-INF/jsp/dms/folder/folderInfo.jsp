<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<table border=0>
	<tr style="line-height:100px">
		<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;line-height:25px;padding:0px 12px;"><b>目录名称: </b></td>
		<td style="font-size:12px;width:900px" colspan=3>${folder.f_NAME}</td>
	</tr>
	<tr>
		<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;line-height:25px;padding:0px 12px;"><b>目录位置: </b></td>						
		<td style="font-size:12px;width:900px" colspan=3>${folder.f_FOLDERPATH}</td>
	</tr>
	<tr>
		<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;line-height:25px;padding:0px 12px;"><b>文档数: </b></td>
		<td style="font-size:12px">${folder.f_DOCNumber}</td>
		<c:if test="${flag==0}">
			<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;"></td>
			<td style="font-size:12px"></td>
		</c:if>	
		<c:if test="${flag==1}">
			<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;"><b>创建者: </b></td>
			<td style="font-size:12px">${folder.f_CREATOR}(${folder.f_ZGMC})</td>
		</c:if>	
	</tr>
	
	<c:if test="${flag==1}">
		<tr>
			<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;line-height:25px;padding:0px 12px;"><b>创建时间: </b></td>
			<td style="font-size:12px"><fmt:formatDate value="${folder.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;"><b>修改时间: </b></td>
			<td style="font-size:12px"><fmt:formatDate value="${folder.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</c:if>	
</table>