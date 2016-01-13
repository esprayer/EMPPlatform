<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>
<script>
	$(document).ready(function(){
		$("#btnAddSelect").click(function(){
			//获取用户列表所选择的值  
	        $("#userList option:selected").each(function (i){  
		        var addText = this.text;
		        var addValue = this.value;
		        var compareVal = "1;" + this.value;
		        var length;
		        var bAdd = true;
		        
		        length = $("#allPermission option").length;

		        //如果右边选项为0，则右边是空的，直接添加
		        if(length==0) {
		        	//在右边添加左边所选择的用户
					$("#allPermission").append("<option value=1;"+addValue+">[用户]"+addText+"</option>");   
		        } 
				//如果不为0，则右边有选项，需要判断左边选择的选项是否已经添加到右边，如果已经添加，则不允许重复添加
		        else {
					$("#allPermission option").each(function (i){  
						var opVal = this.value;
						if(compareVal==opVal) {
							bAdd = false;
							return false;
						}
					});	

					if(bAdd) {
						//在右边添加左边所选择的用户
						$("#allPermission").append("<option value=1;"+addValue+">[用户]"+addText+"</option>");   
					}
		        }	     
	        }); 

	        //添加部门
	        var bmbh = $('#bmbh').val();
	        var bmmc = $('#bmmc').val();
			var compareVal = "0;" + $('#bmbh').val();
	        var length;
	        var bAdd = true;
	        length = $("#allPermission option").length;
	        //如果右边选项为0，则右边是空的，直接添加
	        if(bmbh) {
	        	if(length==0) {
		        	//在右边添加左边所选择的用户
		        	$("#allPermission").append("<option value=0;"+bmbh+">[部门]"+bmmc+"(" + bmbh+")</option>");   	
		        } 
				//如果不为0，则右边有选项，需要判断左边选择的选项是否已经添加到右边，如果已经添加，则不允许重复添加
		        else {
					$("#allPermission option").each(function (i){  
						var opVal = this.value;
						if(compareVal==opVal) {
							bAdd = false;
							return false;
						}
					});	

					if(bAdd) {
						//在右边添加左边所选择的用户
						$("#allPermission").append("<option value=0;"+bmbh+">[部门]"+bmmc+"(" + bmbh+")</option>");   	
					}
		        }	 
	        }
	            	        
		});
		$("#btnRemove").click(function(){
			//获取要删除的值  
	        $("#allPermission option:selected").each(function (i){          
	            //删除所选值  
	            $(this).remove();            
	        });  
		});
	});

	function getFolderPermissionAjaxData() {
		var content="";
		$("#allPermission option").each(function (i){  
			var opVal = this.value;
			content+=opVal+",";
		});	
		$('#content').val(content);
		return $("#folderPermissionForm");
	}

	function checkPermissions() {
		var arrayObj = new Array([2]);
		
		if($("#G1").attr("checked") == 'checked') {		
			arrayObj[0] = true;
			return arrayObj;
		}
		if($("#G2").attr("checked") == 'checked') {
			arrayObj[0] = true;
			return arrayObj;
		}
		if($("#G3").attr("checked") == 'checked') {
			arrayObj[0] = true;
			return arrayObj;
		}
		if($("#G4").attr("checked") == 'checked') {
			arrayObj[0] = true;
			return arrayObj;
		}
		if($("#G5").attr("checked") == 'checked') {
			arrayObj[0] = true;
			return arrayObj;
		}
		if($("#G6").attr("checked") == 'checked') {
			arrayObj[0] = true;
			return arrayObj;
		}
		if($("#G7").attr("checked") == 'checked') {
			arrayObj[0] = true;
			return arrayObj;
		}
		if($("#G8").attr("checked") == 'checked') {
			arrayObj[0] = true;
			return arrayObj;
		}
		
		
		arrayObj[0] = false;
		arrayObj[1] = "请先选择要设置的权限";
		return arrayObj;
	}
</script>

<form id="pagerForm" method="post" action="/standard/metadata/object">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<div class="panel"  style="padding:0px;border-width:0 0px;background:no-repeat;margin:0;height:600px">
	<div  style="padding:0px;border-width:0 0px;background-color: white;height:100%">				

	<form method="post" id="folderPermissionForm" rel="pagerForm" action="${ctx}/management/qxgl/saveUserRoleGx"  class="pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" id="content" name="content" value="" />
		<div class="panel" defH="25">
			<h1>文档权限</h1>
			<div>
				
				<input type="hidden" name="G1" />
				<input id="G1" name="G1" value="1" type="checkbox">
				管理
				<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>

				<input type="hidden" name="G2" />
				<input id="G2" name="G2" value="1" type="checkbox">
				浏览
				<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>

				<input type="hidden" name="G3" />
				<input id="G3" name="G3" value="1" type="checkbox">
				创建
				<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>

				<input type="hidden" name="G4" />
				<input id="G4" name="G4" value="1" type="checkbox">
				阅读
				<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>

				<input type="hidden" name="G5" />
				<input id="G5" name="G5" value="1" type="checkbox">
				删除
				<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>

				<input type="hidden" name="G6" />
				<input id="G6" name="G6" value="1" type="checkbox">
				打印
				<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>

				<input type="hidden" name="G7" />
				<input id="G7" name="G7" value="1" type="checkbox">	
				下载
				<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>

				<input type="hidden" name="G8" />
				<input id="G8" name="G8" value="1" type="checkbox">	
				编辑
				<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>			
			</div>
		</div>
	
		<div style=" float:left; display:block; margin:10px;vertical-align:middle; overflow:auto; width:253px; height:470px; border:solid 0px #CCC; line-height:21px;">	
			<div id="deportFolderPermissionLiBox" style="float:left; display:block; margin:10px; overflow:auto; width:200px; height:200px; border:solid 1px #CCC; line-height:21px;">
				<c:import url="bmList.jsp"></c:import>
			</div>
		
			<div id="userFolderPermissionLiBox" style="margin:10px; width:205px; height:220px; line-height:21px;">
					<c:import url="zgList.jsp"></c:import>
			</div>
		</div>
		<div style=" float:left; display:block; margin:10px;vertical-align:middle; overflow:auto; width:50px; height:440px; line-height:21px;">
			<div style="margin-top:170px;margin-bottom:20px">
	  			<input type="button" id="btnAddSelect" value="&gt;"  style="width:100%" />
	  			<input type="button" id="btnRemove" value="&lt;" style="width:100%"  />
	  		</div>
		</div>
		<div id="selectFolderPermissionLiBox" style=" float:left; display:block; margin:10px;vertical-align:middle; overflow:auto; width:223px; height:440px; border:solid 0px #CCC; line-height:21px;">	
			<div style="margin:10px;width:185px; height:420px; line-height:21px;">
				<select name="wfpRoles" id="allPermission" multiple="multiple" style="width:100%;height:100%">								
				</select>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><a class="buttonActive" target="ajaxTodo" rel="folderPermissionLiBox" getAjaxData="getFolderPermissionAjaxData()" ajaxData="1" onCheck="checkPermissions()" showTitle="0" callback="divReload" href="${ctx}/dms/folderPermission/addFolderPermission?rel=folderPermissionLiBox&callbackType=closeCurrent&FOLDER_ID=${FOLDER_ID}&forwardUrl=${ctx}/dms/folderPermission/folderPermissionList?pageBreak=0"><span>保存</span></a></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
</div>