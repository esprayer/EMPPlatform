<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css" media="screen">

	.my-uploadify-button {
		background:none;
		border: none;
		text-shadow: none;
		border-radius:0;
	}
	
	.uploadify:hover .my-uploadify-button {
		background:none;
		border: none;
	}
	
	.fileQueue {
		width: 300px;
		height: 290px;
		overflow: auto;
		border: 1px solid #E5E5E5;
		margin-bottom: 10px;
	}
</style>

<script>
	function uploadifyQueueComplete(file, data, response){
		var href = "${ctx}/dms/folder/loadFolderById/${NODETYPE}?FOLDER_ID=${FOLDER_ID}&NODETYPE=${NODETYPE}&realPath=@${realPath}@";
		alertMsg.correct('文件上传成功！');
		var $rel = $("#folderDocDiv");
		$rel.loadUrl(href, {}, function(){
			
		});
    	$.pdialog.closeCurrent();
	}
</script>

<div class="pageContent" layoutH="16">
	<h2 class="contentTitle">请选择需要上传的文件</h2>

	<div class="divider"></div>
	
	<table>
    	<tr style="height:10px;">
			<td colspan=2></td>			
		</tr>
		<tr>
			<td></td>
			<td></td>
    		<td>
    			<input id="testFileInput2" type="file" name="image2" 
					uploaderOption="{
						swf:'${ctx}/styles/dwz/uploadify/scripts/uploadify.swf?var='+(new Date()).getTime(),
						uploader:'${ctx}/dms/doc/upload?FOLDER_ID=${FOLDER_ID}&callbackType=closeCurrent',
						queueID:'fileQueue',
						buttonImage:'${ctx}/styles/dwz/uploadify/img/add.jpg',
						buttonClass:'my-uploadify-button',
						width:102,
						auto:false,
						multi:true,
						preserve_relative_urls:true,
						successTimeout:300000,
						onQueueComplete:uploadifyQueueComplete					
					}"
				/>
			</td>
		</tr>  
		
		<tr>
			<td style="width:10px;"></td>
    		<td style="width:80px;">上传进度:</td>
			<td><div id="fileQueue" class="fileQueue"></div></td>
		</tr>
		
		<tr>			
			<td colspan=3 align="right">
				<!-- <a class="button" href="javascript:;" onclick="uploadSuccess()"><span>信息提示</span></a><br /><br /> -->
				<input type="image" src="${ctx}/styles/dwz/uploadify/img/upload.jpg" onclick="$('#testFileInput2').uploadify('upload', '*');"/>
				<input type="image" src="${ctx}/styles/dwz/uploadify/img/cancel.jpg" onclick="$('#testFileInput2').uploadify('cancel', '*');"/>
			</td>
		</tr>
	</table>
</div>