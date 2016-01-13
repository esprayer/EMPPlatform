<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
	<TBODY>
		<TR>
			<TD style="BORDER-BOTTOM: 1px outset; BORDER-LEFT: 1px outset; BORDER-TOP: 1px outset; BORDER-RIGHT: 1px outset"
				bgColor=#d4d0c8 width=70>
				当前路径：
			</TD>
			<TD style="BORDER-BOTTOM: #808080 1px ridge; BORDER-LEFT: #808080 1px ridge; BORDER-TOP: #808080 1px ridge; BORDER-RIGHT: #808080 1px ridge"
				bgColor=#ffffff borderColorLight=#d4d0c8 vAlign=middle borderColorDark=#808080>
				<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD width="2%">
								<IMG border=0 align=middle src="${ctx}/img/dms/deskset.gif">
							</TD>
							<TD vAlign=middle width="7%">
								<c:if test="${NODETYPE == '0'}">
									公共文档:/
								</c:if>
								<c:if test="${NODETYPE == '1'}">
									私人文档:/
								</c:if>
								<c:if test="${NODETYPE == '2'}">
									回收站:/
								</c:if>
							</TD>
							<TD vAlign=middle width="87%">
								<input <c:if test="${NODETYPE=='2'}">readonly="true"</c:if> id="inputPath" type="text" id="inputPath" style="width:100%;height:100%;padding: 0px 0px;" name="inputPath" value="${inputPath}" onkeydown="redirect('${ctx}/dms/folder/goto?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@')"/>
							</TD>
							<c:if test="${NODETYPE=='0' || NODETYPE=='1'}">
								<TD width="4%">
									<A onmouseover="document['fpAnimswapImgFP13'].imgRolln=document['fpAnimswapImgFP13'].src;document['fpAnimswapImgFP13'].src=document['fpAnimswapImgFP13'].lowsrc;"
									   onmouseout="document['fpAnimswapImgFP13'].src=document['fpAnimswapImgFP13'].imgRolln"
									   onclick="goToAction('${ctx}/dms/folder/goto?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@')" title="跳转">
										<IMG id=fpAnimswapImgFP13 title=跳转 border=0 lowsrc="${ctx}/img/dms/fm2.gif" name=fpAnimswapImgFP13
											 src="${ctx}/img/dms/fm1.gif" dynamicanimation="fpAnimswapImgFP13" style="border: 0px;cursor:pointer">
									</A> 							
								</TD>
							</c:if>
							<c:if test="${NODETYPE=='2'}">
								<TD width="4%">
																	
								</TD>
							</c:if>
						</TR>
					</TBODY>
				</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>