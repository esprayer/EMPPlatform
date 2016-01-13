<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<table border=0>
	<tr style="line-height:100px">
		<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;line-height:20px" align='center'><b>文档名称: </b></td>
		<td style="font-size:12px;width:900px" colspan=3>${doc.f_NAME}</td>
	</tr>
	<tr>
		<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;line-height:20px" align='center'><b>文档位置: </b></td>
		<td style="font-size:12px;width:900px" colspan=3>${doc.f_PATH}</td>
	</tr>
	
	<c:if test="${NODETYPE=='0' || NODETYPE=='1'}">
		<c:choose> 
			<c:when test="${createFlag=='0'}"> 	
				<tr>
					<td  align='center' style="font-size:12px;width:100px;font-family:Arial, sans-serif;"><b>锁定状态: </b></td>
					<td style="font-size:12px">
						<c:if test="${doc.f_LOCKED==0}">
							未锁定
						</c:if>
						
						<c:if test="${doc.f_LOCKED==1}">
							<IMG border=0 style="padding: 5px 5px;" align=middle src="${ctx}/img/docico/icon-locked.gif">
							已锁定
						</c:if>
					</td>
					<c:if test="${doc.f_LOCKED==1}">
						<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;"><b>锁定人: </b></td>
						<td style="font-size:12px">
							<c:if test="${doc.f_LOCKED==1}">
								${doc.f_LOCKED_USERNAME}(${doc.f_LOCKED_USERID})
							</c:if>
						</td>
					</c:if>	
				</tr>
				<c:if test="${doc.f_LOCKED==1}">
					<tr>
						<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;" align='center'><b>锁定时间: </b></td>
						<td style="font-size:12px"><fmt:formatDate value="${doc.f_LOCKEDDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>		</td>
						<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;"><a class="button" href="${ctx}/dms/doc/unLocked?NODETYPE=${NODETYPE}&F_DOCID=${F_DOCID}" target="ajax" rel="docInfoLiBox"><span>解锁</span></a></td>
						<td style="font-size:12px"></td>
					</tr>
				</c:if>	
			</c:when> 
			<c:when test="${createFlag=='1'}"> 					
				<tr>
					<td  align='center' style="font-size:12px;width:100px;font-family:Arial, sans-serif;"><b>锁定状态: </b></td>
					<td style="font-size:12px">
						<c:if test="${doc.f_LOCKED==0}">
							未锁定
						</c:if>
						
						<c:if test="${doc.f_LOCKED==1}">
							<IMG border=0 style="padding: 5px 5px;" align=middle src="${ctx}/img/docico/icon-locked.gif">
							已锁定
						</c:if>
					</td>
					<c:if test="${doc.f_LOCKED==0}">
						<td><a class="button" href="${ctx}/dms/doc/locked?NODETYPE=${NODETYPE}&F_DOCID=${F_DOCID}" target="ajax" rel="docInfoLiBox"><span>锁定</span></a></td>
					</c:if>
			
					<c:if test="${doc.f_LOCKED==1}">
						<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;"><b>锁定人: </b></td>
						<td style="font-size:12px">
							<c:if test="${doc.f_LOCKED==1}">
								${doc.f_LOCKED_USERNAME}(${doc.f_LOCKED_USERID})
							</c:if>
						</td>
					</c:if>		
				</tr>
				<c:if test="${doc.f_LOCKED==1}">
					<tr>
						<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;" align='center'><b>锁定时间: </b></td>
						<td style="font-size:12px"><fmt:formatDate value="${doc.f_LOCKEDDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>		</td>
						<td style="font-size:12px;width:100px;font-family:Arial, sans-serif;"><a class="button" href="${ctx}/dms/doc/unLocked?NODETYPE=${NODETYPE}&F_DOCID=${F_DOCID}" target="ajax" rel="docInfoLiBox"><span>解锁</span></a></td>
						<td style="font-size:12px"></td>
					</tr>
				</c:if>					
			</c:when> 
		</c:choose>
	</c:if>
</table>