<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<script language="javascript">
    function deleteDoc(flag, href, reloadHref){
    	var ttt = $("#folderTable tbody");
    	var nTds = $('tr', ttt);
    	var i = 0;
    	var selectVal = "";
    	nTds.each(function () {
            // var val1 = $($("td :text", nTds[i])).val();

            //var val1 = $($("td", nTds[i])[X]).val();  //取第X列的值
			
			var val1 = $($("td :checkbox", nTds[i])).attr("checked");  //获取真假
			if(val1 == 'checked') {
				selectVal += $($("td :hidden", nTds[i])).val() + "@";
			}
			i++;
		})
		
		if(selectVal.length == 0) {
			alert("请选择需要删除的文件！");
			return false;
		} else {	
			var r;

			if(flag == '1') {
				r=confirm("确定要将所选文档移动至回收站吗？\n如果所选目录下包含文档，删除目录的同时将同时删除目录下包含的文档");
			} else if(flag == '2') {
				r=confirm("确定要永久删除所选文档吗？\n如果所选目录下包含文档，删除目录的同时将同时删除目录下包含的文档");
			}
	    	if (r==false) {
				return false;
			}				
		}
		
    	$.ajax({
			type:'POST', 
			url:href, 
			dataType:'json', 
			cache: false,
			data : {'selectData':selectVal}, 
			success:function(response){
				var value = $('#inputPath').val();
		    	reloadHref += "&inputPath=" + encodeURIComponent(value);;
		    	navTab.reload(reloadHref);
			},
			error: function(response){
				var value = $('#inputPath').val();
		    	reloadHref += "&inputPath=" + encodeURIComponent(value);;
		    	navTab.reload(reloadHref);
			}
		});
    	
    }  

    function reduceFolderDoc(href, reloadHref){
    	var ttt = $("#folderTable tbody");
    	var nTds = $('tr', ttt);
    	var i = 0;
    	var selectVal = "";
    	nTds.each(function () {
            // var val1 = $($("td :text", nTds[i])).val();

            //var val1 = $($("td", nTds[i])[X]).val();  //取第X列的值
			
			var val1 = $($("td :checkbox", nTds[i])).attr("checked");  //获取真假
			if(val1 == 'checked') {
				selectVal += $($("td :hidden", nTds[i])).val() + "@";
			}
			i++;
		})
		
		if(selectVal.length == 0) {
			alert("请选择需要还原的文件！");
			return false;
		} else {		
			var r=confirm("确定要还原所选文档吗？");
	    	if (r==false) {
				return false;
			}			
		}
		
    	$.ajax({
			type:'POST', 
			url:href, 
			dataType:'json', 
			cache: false,
			data : {'selectData':selectVal}, 
			success:function(response){
				var value = $('#inputPath').val();
		    	reloadHref += "&inputPath=" + value;
		    	navTab.reload(reloadHref);
			},
			error: function(response){				
				var value = $('#inputPath').val();
		    	reloadHref += "&inputPath=" + value;
		    	navTab.reload(reloadHref);
			}
		});
    	
    }  

    //下载文档
    function downloadDoc(checkSessionHref, href){
    	//检查session是否过期
    	checkSeesion(checkSessionHref, href);
    }  

    //检查session是否过期
    function checkSeesion(checkHref, url) {
    	$.ajax({
			type: 'POST',
			url:checkHref,
			dataType:"json",
			cache: false,
			success: function(response){
				var json = DWZ.jsonEval(response);
				if (json.statusCode == DWZ.statusCode.ok) {
					batchDowload(url);
				} else if (json.statusCode==DWZ.statusCode.timeout){
					if ($.pdialog) $.pdialog.checkTimeout();
					if (navTab) navTab.checkTimeout();

					alertMsg.error(json.message || DWZ.msg("sessionTimout"), {okCall:function(){
						DWZ.loadLogin();
					}});
				}
			}
		});
    }
    
    function batchDowload(href) {
    	var ttt = $("#folderTable tbody");
    	var nTds = $('tr', ttt);
    	var i = 0;
    	var selectVal = "";
    	nTds.each(function () {
            // var val1 = $($("td :text", nTds[i])).val();

            //var val1 = $($("td", nTds[i])[X]).val();  //取第X列的值
			
			var val1 = $($("td :checkbox", nTds[i])).attr("checked");  //获取真假
			var val2 = $($("td :hidden", nTds[i])).val() + "@";
			if(val1 == 'checked' && val2.indexOf("docId:") > -1) {
				selectVal += $($("td :hidden", nTds[i])).val() + "@";
			}
			i++;
		})
		
		if(selectVal.length == 0) {
			alert("请选择需要下载的文件！");
			return false;
		} else {			
			download(href,selectVal);			
		}
    }
                               
    function download(url, data){
    	var form=$("<form>");//定义一个form表单
    	form.attr("style","display:none");
    	form.attr("target","");
    	form.attr("method","post");
    	form.attr("action",url);
    	var input1=$("<input>");
    	input1.attr("type","hidden");
    	input1.attr("name","selectData");
    	input1.attr("value",data);
    	$("body").append(form);//将表单放置在web中
    	form.append(input1);
    	form.submit();//表单提交 
    }
</script>

<div class="panel"  style="padding:0px;border-width:0 0px;background:no-repeat;margin:0;">
	<div  style="padding:0px;border-width:0 0px;background-color: white;">						
		<table id="folderTable" class="list" width="98%" style="border: 0px;cursor:pointer">
			<thead>
				<tr style="border:0px ">
					<th width="26"><input style="width: 20px" id="checkAll" name="checkAll" align=middle type="checkbox"></th>
					<th width="17"></th>
					<th width="123">文件名</th>
					<th width="56">类型</th>
					<th width="65">大小</th>
					<c:if test="${NODETYPE=='0' || NODETYPE=='1'}">
						<th width="62">创建者</th>			
						<th width="62">创建日期</th>			
						<th width="72">修改日期</th>
					</c:if>
					<c:if test="${NODETYPE=='2'}">
						<th width="62">删除者</th>			
						<th width="62">删除日期</th>			
					</c:if>
				</tr>
			</thead>
		
			<tbody>
				<c:forEach var="item" items="${folderList}" varStatus="s">
					<tr target="slt_objId" rel="${item.f_FOLDERID}">
						<td style="border: 0px;cursor:pointer" align=center>	
							<input style="width: 20px" name="ids" value="${item.f_FOLDERID}" type="checkbox">
							<input type="hidden" id="folderId" name="folderId" value="folderId:${item.f_FOLDERID}" />
						</td>
						<td style="border: 0px;cursor:pointer" align=center onclick="showFolderBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=', '${ctx}/dms/folder/${NODETYPE}?FOLDER_ID=${item.f_FOLDERID}&realPath=@${realPath}@', ${NODETYPE})"><IMG align=middle src="${ctx}/img/docico/icon-${item.f_TYPE}.gif"></td>
						<td style="border: 0px;cursor:pointer" onclick="showFolderBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=', '${ctx}/dms/folder/${NODETYPE}?FOLDER_ID=${item.f_FOLDERID}&realPath=@${realPath}@', ${NODETYPE})">${item.f_NAME}</td>
						<td style="border: 0px;cursor:pointer" onclick="showFolderBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=', '${ctx}/dms/folder/${NODETYPE}?FOLDER_ID=${item.f_FOLDERID}&realPath=@${realPath}@', ${NODETYPE})">文件夹</td>
						<td style="border: 0px;cursor:pointer" onclick="showFolderBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=', '${ctx}/dms/folder/${NODETYPE}?FOLDER_ID=${item.f_FOLDERID}&realPath=@${realPath}@', ${NODETYPE})"></td>												
						<c:if test="${NODETYPE=='0' || NODETYPE=='1'}">
							<td style="border: 0px;cursor:pointer" onclick="showFolderBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=', '${ctx}/dms/folder/${NODETYPE}?FOLDER_ID=${item.f_FOLDERID}&realPath=@${realPath}@', ${NODETYPE})">${item.f_ZGMC}</td>
							<td style="border: 0px;cursor:pointer" onclick="showFolderBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=', '${ctx}/dms/folder/${NODETYPE}?FOLDER_ID=${item.f_FOLDERID}&realPath=@${realPath}@', ${NODETYPE})"><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="border: 0px;cursor:pointer" onclick="showFolderBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=', '${ctx}/dms/folder/${NODETYPE}?FOLDER_ID=${item.f_FOLDERID}&realPath=@${realPath}@', ${NODETYPE})"><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</c:if>
						<c:if test="${NODETYPE=='2'}">
							<td style="border: 0px;cursor:pointer" onclick="showFolderBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=')">${item.f_ZGMC}</td>
							<td style="border: 0px;cursor:pointer" onclick="showFolderBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=')"><fmt:formatDate value="${item.f_DELDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</c:if>
					
				</c:forEach>	
				<c:forEach var="item" items="${docList}" varStatus="s">
					<tr target="slt_objId" rel="${item.f_DOCID}">
						<td width="10" style="border: 0px;cursor:pointer" align=middle>	
							<input style="width: 20px" name="ids" value="${item.f_DOCID}" align=middle type="checkbox">
							<input type="hidden" id="docId" name="docId" value="docId:${item.f_DOCID}" />
						</td>
						<td style="border: 0px;cursor:pointer" align=middle onclick="showDocBgc('${ctx}/dms/doc/docInfo?inputPath=@${inputPath}@&realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCID=${item.f_DOCID}&NODETYPE=${NODETYPE}','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=${item.f_TYPE}&NODETYPE=${NODETYPE}&F_DOCID=${item.f_DOCID}')"><IMG border=0 style="margin:0;padding:0px;" align=middle src="${ctx}/img/docico/icon-${item.f_TYPE}.gif"></td>
						<td style="border: 0px;cursor:pointer" onclick="showDocBgc('${ctx}/dms/doc/docInfo?inputPath=@${inputPath}@&realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCID=${item.f_DOCID}&NODETYPE=${NODETYPE}','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=${item.f_TYPE}&NODETYPE=${NODETYPE}&F_DOCID=${item.f_DOCID}')">${item.f_NAME}</td>
						<td style="border: 0px;cursor:pointer" onclick="showDocBgc('${ctx}/dms/doc/docInfo?inputPath=@${inputPath}@&realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCID=${item.f_DOCID}&NODETYPE=${NODETYPE}','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=${item.f_TYPE}&NODETYPE=${NODETYPE}&F_DOCID=${item.f_DOCID}')">${item.f_TYPE}</td>
						<td style="border: 0px;cursor:pointer" onclick="showDocBgc('${ctx}/dms/doc/docInfo?inputPath=@${inputPath}@&realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCID=${item.f_DOCID}&NODETYPE=${NODETYPE}','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=${item.f_TYPE}&NODETYPE=${NODETYPE}&F_DOCID=${item.f_DOCID}')">${item.f_CONSIZE}</td>
						<c:if test="${NODETYPE=='0' || NODETYPE=='1'}">
							<td style="border: 0px;cursor:pointer" onclick="showDocBgc('${ctx}/dms/doc/docInfo?inputPath=@${inputPath}@&realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCID=${item.f_DOCID}&NODETYPE=${NODETYPE}','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=${item.f_TYPE}&NODETYPE=${NODETYPE}&F_DOCID=${item.f_DOCID}')">${item.f_CREATORNAME}</td>
							<td style="border: 0px;cursor:pointer" onclick="showDocBgc('${ctx}/dms/doc/docInfo?inputPath=@${inputPath}@&realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCID=${item.f_DOCID}&NODETYPE=${NODETYPE}','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=${item.f_TYPE}&NODETYPE=${NODETYPE}&F_DOCID=${item.f_DOCID}')"><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="border: 0px;cursor:pointer" onclick="showDocBgc('${ctx}/dms/doc/docInfo?inputPath=@${inputPath}@&realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCID=${item.f_DOCID}&NODETYPE=${NODETYPE}','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=${item.f_TYPE}&NODETYPE=${NODETYPE}&F_DOCID=${item.f_DOCID}')"><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</c:if>		
						<c:if test="${NODETYPE=='2'}">
							<td style="border: 0px;cursor:pointer" onclick="showDocBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=')">${item.f_DEL_USERNAME}</td>
							<td style="border: 0px;cursor:pointer" onclick="showDocBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=')">${item.f_DEL_USERNAME}</td>							
							<td style="border: 0px;cursor:pointer" onclick="showDocBgc('${ctx}/dms/folder/folderInfo?NODETYPE=${NODETYPE}&FOLDER_ID=${FOLDER_ID}&realPath=@${realPath}@','${ctx}/dms/doc/actionControl?realPath=@${realPath}@&FOLDER_ID=${FOLDER_ID}&F_DOCTYPE=folder&NODETYPE=${NODETYPE}&F_DOCID=')"></td>
						</c:if>				
					</tr>	
				</c:forEach>								
			</tbody>
		</table>
	</div>
</div>