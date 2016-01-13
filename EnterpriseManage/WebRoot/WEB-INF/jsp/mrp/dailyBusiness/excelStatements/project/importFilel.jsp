<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../include.inc.jsp"%>

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
		height: 50px;
		overflow: auto;
		border: 1px solid #E5E5E5;
		margin-bottom: 10px;
	}
</style>

<script>
	function onSelectFile(file, data, response){
		$("#FILE_NAME").val(file.name);
	}
	function onCancelFile(file, data, response){
		$("#FILE_NAME").val("");
	}

	function uploadSuccess(file, data, response){
		$.ajax({
			type:'POST', 
			dataType:"json", 
			url:'${ctx}/dailyBusiness/excelStatements/statementsReslove?navTabId=statementsLiNav&callbackType=closeCurrent&FILE_NAME=' + data, 
			cache: false,
			success: function(json){
				alertMsg.correct('文件已经上传成功，文件解析成功！');
				DWZ.ajaxDone(json);
				if (json.statusCode == DWZ.statusCode.ok){
					if (json.navTabId){
						navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
					} else if (json.rel) {
						var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
						var args = $pagerForm.size()>0 ? $pagerForm.serializeArray() : {}
						navTabPageBreak(args, json.rel);
					}
					if ("closeCurrent" == json.callbackType) {
						$.pdialog.closeCurrent();
					}
				}
			},
			error:  function(){
				alertMsg.error('文件已经上传成功，文件解析失败！');
			}
		});
	}
</script>

<div class="pageContent" layoutH="16">
	<h2 class="contentTitle">请选择需要上传的材料申请单Excel文件</h2>
	
	<div class="divider"></div>
	
	<table>
    	<tr>
    		<td style="width:10px;"></td>
    		<td style="width:80px;">申请单文件:</td>
    		<td>
    			<input id="FILE_NAME" name="FILE_NAME" readonly="readonly" style="width:300px;" />
    		</td>
    	</tr>
    	<tr style="height:10px;">
			<td colspan=2></td>			
		</tr>
		<tr>
			<td></td>
			<td></td>
    		<td>
    			<input id="testFileInput2" type="file" name="image2" 
					uploaderOption="{
						swf:'${ctx}/styles/dwz/uploadify/scripts/uploadify.swf',
						uploader:'${ctx}/dailyBusiness/excelStatements/upload?callbackType=closeCurrent',
						queueID:'fileQueue',
						buttonImage:'${ctx}/styles/dwz/uploadify/img/add.jpg',
						buttonClass:'my-uploadify-button',
						width:102,
						auto:false,
						fileTypeDesc:'*.xls;*.xlsx;',
						fileTypeExts:'*.xls;*.xlsx;',
						successTimeout:300000,
						onSelect:onSelectFile,
						onCancel:onCancelFile,
						onUploadSuccess:uploadSuccess,
						uploadLimit:1
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