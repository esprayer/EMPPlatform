<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<div class="panel"  style="padding:0px;border-width:0 0px;background:no-repeat;margin:0;">
	<div  style="padding:0px;border-width:0 0px;background-color: white;height:110px">							
		<table id="folderTable" class="list" width="98%" style="cursor:pointer">
			<thead>
				<tr>
					<th></th>
					<th align='center'>操作</th>
					<th align='center'>版本号</th>
					<th align='center'>修改说明</th>
					<th align='center'>更新者</th>
					<th align='center'>更新日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${versionList}" varStatus="s">
					<tr target="slt_objId" rel="${item.f_ID}">	
						<td>${s.index + 1}</td>					
						<td align=middle>
							<c:if test="${lookFlag == '1'}">
								<A onclick="openWin('${ctx}/management/checkSession', '${ctx}/dms/doc/editDoc?FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&F_DOCID=${F_DOCID}&docVersion=${item.f_VERSION_NUMBER}')" title="新建文件夹">
									<IMG title=阅读 border=0 lowsrc="${ctx}/img/dms/fmnewfolder1.gif" name=fpAnimswapImgFP2
										 src="${ctx}/img/docico/icon-read.gif" dynamicanimation="fpAnimswapImgFP2">
								</A> 
							</c:if>
							
							<c:if test="${createFlag == '1'}">
								<c:if test="${s.index != 0}">
									<a class="add" target="dialog" mask="true" width="450" height="300" rel="objectNav" href="${ctx}/dms/docVersion/backVersion?F_DOCID=${item.f_DOCID}&FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&realPath=@${realPath}@&inputPath=${inputPath}&backVersion=${item.f_VERSION_NUMBER}" title="添加评论">
										<IMG title=回退至该版本 border=0 lowsrc="${ctx}/img/dms/fmnewfolder1.gif" name=fpAnimswapImgFP2
											 src="${ctx}/img/docico/icon-withdraw.gif" dynamicanimation="fpAnimswapImgFP2">
									</a>
								</c:if>
							</c:if>
							
							<c:if test="${downloadFlag == '1'}">
								<A href="${ctx}/dms/doc/download?F_DOCID=${item.f_DOCID}&F_VERSION_NUMBER=${item.f_VERSION_NUMBER}">
									<IMG title=下载 border=0 src="${ctx}/img/docico/icon-download.gif">
								</A>
							</c:if>
						</td>
						<td align=middle>
							${item.f_VERSION_NUMBER}.0
						</td>
						<td align=middle>
							${item.f_COMMENT}
						</td>
						<td align=middle>
							${item.f_CREATORNAME}
						</td>
						<td align=middle>
							<fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>					
					</tr>
				</c:forEach>								
			</tbody>
		</table>
	</div>
</div>