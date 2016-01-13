<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<script>
	function getDocAjaxData() {
		return $("#docForm");
	}
	function setActionUrl(href) {
		 var value = $('#inputPath').val();
	     href += "&inputPath=" + encodeURIComponent(value);
	     value = $('#realPath').val();   
	     href += "&realPath="+encodeURIComponent(value);
	     return href;
	}
</script>

<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#d4d0c8>
	<TBODY>
		<TR>
			<TD style="BORDER-BOTTOM: 1px outset; BORDER-LEFT: 1px outset; BORDER-TOP: 1px outset; BORDER-RIGHT: 1px outset" width="30%">
				<c:if test="${NODETYPE=='0' || NODETYPE=='1'}">
					<c:choose> 
						<c:when test="${realPath==''|| realPath==null}"> 				
							<IMG border=0 src="${ctx}/img/dms/fmup2.gif" height=20> 		
						</c:when> 
						<c:otherwise>
							<A class="add" onmouseover="document['fpAnimswapImgFP1'].imgRolln=document['fpAnimswapImgFP1'].src;document['fpAnimswapImgFP1'].src=document['fpAnimswapImgFP1'].lowsrc;"
							   onmouseout="document['fpAnimswapImgFP1'].src=document['fpAnimswapImgFP1'].imgRolln"
							   href="${ctx}/dms/folder/goBack?FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODEYPE}&realPath=@${realPath}@" target="ajaxToDiv" callBack='1' rel="objectNav" title="返回上级"
							   onCallback="goBack('${ctx}/dms/folder/goBack?FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}')">
								<IMG id=fpAnimswapImgFP1 title=返回上级 border=0 lowsrc="${ctx}/img/dms/fmup1.gif" name=fpAnimswapImgFP1
								     src="${ctx}/img/dms/fmup.gif" dynamicanimation="fpAnimswapImgFP1">
							</A> 
						</c:otherwise>  
					</c:choose>
						
					<!-- 是否显示新建文件夹按钮 -->
					<c:choose> 
						<c:when test="${createFlag=='0'}"> 				
							<IMG border=0 src="${ctx}/img/dms/fmnewfolder2.gif" height=20> 		
						</c:when> 
						<c:when test="${createFlag=='1'}">
							<A class="add" onmouseover="document['fpAnimswapImgFP2'].imgRolln=document['fpAnimswapImgFP2'].src;document['fpAnimswapImgFP2'].src=document['fpAnimswapImgFP2'].lowsrc;"
							   onmouseout="document['fpAnimswapImgFP2'].src=document['fpAnimswapImgFP2'].imgRolln"
							   href="${ctx}/dms/folder/create?inputPath=@${inputPath}@&FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&realPath=@${realPath}@" target="dialog" mask="true" width="420" height="200" rel="objectNav" title="新建文件夹">
								<IMG id=fpAnimswapImgFP2 title=新建文件夹 border=0 lowsrc="${ctx}/img/dms/fmnewfolder1.gif" name=fpAnimswapImgFP2
									 src="${ctx}/img/dms/fmnewfolder.gif" dynamicanimation="fpAnimswapImgFP2">
							</A> 
						</c:when> 
					</c:choose>
					
					<!-- 是否显示重命名按钮 -->
					<c:choose> 
						<c:when test="${renameFlag=='1' && renameType=='folder'}"> 			
							<A class="add" onmouseover="document['fpAnimswapImgFP9'].imgRolln=document['fpAnimswapImgFP9'].src;document['fpAnimswapImgFP9'].src=document['fpAnimswapImgFP9'].lowsrc;"
							   onmouseout="document['fpAnimswapImgFP9'].src=document['fpAnimswapImgFP9'].imgRolln"
							   href="${ctx}/dms/folder/renameFolder?inputPath=@${inputPath}@&NODETYPE=${NODETYPE}&realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}" target="dialog" mask="true" width="420" height="150" rel="objectNav" title="重命名">
								<IMG id=fpAnimswapImgFP9 title=重命名 border=0 lowsrc="${ctx}/img/dms/modify1.gif" name=fpAnimswapImgFP9
									 src="${ctx}/img/dms/modify.gif" dynamicanimation="fpAnimswapImgFP9">
							</A> 								
						</c:when> 
						<c:when test="${renameFlag=='1' && renameType=='doc'}"> 			
							<A class="add" onmouseover="document['fpAnimswapImgFP9'].imgRolln=document['fpAnimswapImgFP9'].src;document['fpAnimswapImgFP9'].src=document['fpAnimswapImgFP9'].lowsrc;"
							   onmouseout="document['fpAnimswapImgFP9'].src=document['fpAnimswapImgFP9'].imgRolln"
							   href="${ctx}/dms/doc/renameDoc?inputPath=@${inputPath}@&F_DOCID=${F_DOCID}&NODETYPE=${NODETYPE}&realPath=@${realPath}@" target="dialog" mask="true" width="420" height="150" rel="objectNav" title="重命名">
								<IMG id=fpAnimswapImgFP9 title=重命名 border=0 lowsrc="${ctx}/img/dms/modify1.gif" name=fpAnimswapImgFP9
									 src="${ctx}/img/dms/modify.gif" dynamicanimation="fpAnimswapImgFP9">
							</A> 								
						</c:when> 
						<c:otherwise>
							<IMG border=0 src="${ctx}/img/dms/modify2.gif" height=20> 		
						</c:otherwise>  
					</c:choose>
					
					<!-- 是否显示编辑按钮 -->
					<c:choose> 
						<c:when test="${readFlag=='0'}"> 				
							<IMG border=0 src="${ctx}/img/dms/edit2.gif" height=20> 		
						</c:when> 
						<c:when test="${readFlag=='1'}"> 			
							<A class="add" onmouseover="document['fpAnimswapImgFP5'].imgRolln=document['fpAnimswapImgFP5'].src;document['fpAnimswapImgFP5'].src=document['fpAnimswapImgFP5'].lowsrc;"
							   onmouseout="document['fpAnimswapImgFP5'].src=document['fpAnimswapImgFP5'].imgRolln"
							   onclick="openWin('${ctx}/management/checkSession', '${ctx}/dms/doc/editDoc?FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&F_DOCID=${F_DOCID}&docVersion=')" title="新建文件夹">
								<IMG id=fpAnimswapImgFP5 title=编辑文档 border=0 lowsrc="${ctx}/img/dms/edit1.gif" name=fpAnimswapImgFP5
									 src="${ctx}/img/dms/edit.gif" dynamicanimation="fpAnimswapImgFP5" style="border: 0px;cursor:pointer">
							</A>
						</c:when> 
					</c:choose>
					
					<!-- 是否显示上传按钮 -->
					<c:choose> 
						<c:when test="${uploadFlag=='0'}"> 							
							  <IMG border=0 src="${ctx}/img/dms/fmnewfile2.gif" height=20> 			
						</c:when> 
						<c:when test="${uploadFlag=='1'}"> 		
							<A onmouseover="document['fpAnimswapImgFP3'].imgRolln=document['fpAnimswapImgFP3'].src;document['fpAnimswapImgFP3'].src=document['fpAnimswapImgFP3'].lowsrc;"
							   onmouseout="document['fpAnimswapImgFP3'].src=document['fpAnimswapImgFP3'].imgRolln"
							   target="dialog" mask="true" width="430" height="500" rel="objectNav" href="${ctx}/dms/doc/add?FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&realPath=@${realPath}@" title="上传文件">
								<IMG id=fpAnimswapImgFP3 title=上传文件 border=0 lowsrc="${ctx}/img/dms/fmnewfile1.gif" name=fpAnimswapImgFP3
									 src="${ctx}/img/dms/fmnewfile.gif" dynamicanimation="fpAnimswapImgFP3">
							</A> 
						</c:when>  
					</c:choose>					
					<!-- 是否显示下载按钮 -->
					<c:choose> 
						<c:when test="${downloadFlag=='0'}"> 	
							<IMG border=0 src="${ctx}/img/dms/download2.gif" height=20>
						</c:when> 
						<c:when test="${downloadFlag=='1'}"> 					
							<A onmouseover="document['fpAnimswapImgFP4'].imgRolln=document['fpAnimswapImgFP4'].src;document['fpAnimswapImgFP4'].src=document['fpAnimswapImgFP4'].lowsrc;"
							   onmouseout="document['fpAnimswapImgFP4'].src=document['fpAnimswapImgFP4'].imgRolln"
							   onclick="downloadDoc('${ctx}/management/checkSession', '${ctx}/dms/doc/batchDownload')">
								<IMG id=fpAnimswapImgFP4 title=下载文档 border=0 lowsrc="${ctx}/img/dms/download1.gif" name=fpAnimswapImgFP4
									 src="${ctx}/img/dms/download.gif" dynamicanimation="fpAnimswapImgFP4" style="border: 0px;cursor:pointer">
							</A> 	
						</c:when> 
					</c:choose>
	
					<!-- 是否显示删除按钮 -->
					<c:choose> 
						<c:when test="${deleteFlag=='0'}"> 	
							<IMG border=0 src="${ctx}/img/dms/delete2.gif" height=20>
						</c:when> 
						<c:when test="${deleteFlag=='1'}"> 						
							<A onmouseover="document['fpAnimswapImgFP6'].imgRolln=document['fpAnimswapImgFP6'].src;document['fpAnimswapImgFP6'].src=document['fpAnimswapImgFP6'].lowsrc;"
							   onmouseout="document['fpAnimswapImgFP6'].src=document['fpAnimswapImgFP6'].imgRolln"
							   onclick="deleteDoc('1', '${ctx}/dms/folder/delete?inputPath=@${inputPath}@&FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&realPath=@${realPath}@','${ctx}/dms/folder/goto?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}')" title="删除">
								<IMG id=fpAnimswapImgFP6 title=删除 border=0 lowsrc="${ctx}/img/dms/delete1.gif" name=fpAnimswapImgFP6
									 src="${ctx}/img/dms/delete.gif" dynamicanimation="fpAnimswapImgFP6" style="border: 0px;cursor:pointer">
							</A> 	
						</c:when> 
					</c:choose>	
				</c:if>
						
				<c:if test="${NODETYPE=='2'}">
					<A onmouseover="document['fpAnimswapImgFP7'].imgRolln=document['fpAnimswapImgFP7'].src;document['fpAnimswapImgFP7'].src=document['fpAnimswapImgFP7'].lowsrc;"
					   onmouseout="document['fpAnimswapImgFP7'].src=document['fpAnimswapImgFP7'].imgRolln"
					   onclick="deleteDoc('2', '${ctx}/dms/folder/completelyDelete?inputPath=@${inputPath}@&FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&realPath=@${realPath}@','${ctx}/dms/folder/rootDocList/${NODETYPE}?FOLDER_ID=2&realPath=')" title="删除">
						<IMG id=fpAnimswapImgFP7 title=删除 border=0 lowsrc="${ctx}/img/dms/delete1.gif" name=fpAnimswapImgFP7
							 src="${ctx}/img/dms/delete.gif" dynamicanimation="fpAnimswapImgFP7" style="border: 0px;cursor:pointer">
					</A> 
						
					<A onmouseover="document['fpAnimswapImgFP8'].imgRolln=document['fpAnimswapImgFP8'].src;document['fpAnimswapImgFP8'].src=document['fpAnimswapImgFP8'].lowsrc;"
					   onmouseout="document['fpAnimswapImgFP8'].src=document['fpAnimswapImgFP8'].imgRolln"
					   onclick="reduceFolderDoc('${ctx}/dms/folder/reduce?inputPath=@${inputPath}@&FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&realPath=@${realPath}@','${ctx}/dms/folder/rootDocList/${NODETYPE}?FOLDER_ID=2&realPath=')" title="删除">
						<IMG id=fpAnimswapImgFP8 title=还原 border=0 lowsrc="${ctx}/img/dms/reduce1.gif" name=fpAnimswapImgFP8
							 src="${ctx}/img/dms/reduce.gif" dynamicanimation="fpAnimswapImgFP8" style="border: 0px;cursor:pointer">
					</A> 
				</c:if>		
			</TD>
		</TR>
	</TBODY>
</TABLE>