<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>
<script>
$(document).ready(function(){
	$("#btnAddSelect").click(function(){
		var $options = $('#wfpRoles option:selected');//获取当前选中的项  
        var $remove = $options.remove();//删除下拉列表中选中的项  
        $remove.appendTo('#userRoles');//追加给对方  
	});
	$("#btnAddAll").click(function(){
		 var $options = $('#wfpRoles option');  
         $options.appendTo('#userRoles');  
	});
	$("#btnRemoveAll").click(function(){
		 var $options = $('#userRoles option');  
         $options.appendTo('#wfpRoles');  
	});
	$("#btnRemove").click(function(){
		var $removeOptions = $('#userRoles option:selected');  
        $removeOptions.appendTo('#wfpRoles');;//删除和追加可以用appendTo()直接完成  
	});
	$("#btnSubmit").click(function(){
		$('#userRoles option').each(function(i){
			var roleid = $(this).attr("value");
			var rolename = $(this).text();
			//alert("roleid:"+roleid+"  rolename:"+rolename);
			//把角色添加到form的隐藏元素里面，形成RoleList
			$('<input />').attr('type','hidden')
			.attr('name','roles['+i+']')
	        .attr('value',roleid)
	        .appendTo('#RoleFpForm');
		});
		//提交表单
		$("#RoleFpForm").submit();
	});
	
});
</script>

<form id="pagerForm" method="post" action="/standard/metadata/object">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

	<div class="pageContent">
	<form method="post" id="RoleFpForm" rel="pagerForm" action="${ctx}/management/qxgl/saveUserRoleGx"  class="pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
	
		<div class="panel" defH="25">
			<h1>用户</h1>
			<div>
			<input type="hidden" name ="USER_ID" value="${user.USER_ID}"/>
				当前用户： ${user.USER_ID} ${user.USER_NAME}
			</div>
		</div>
		</form>
		<div style="float:left; display:block; margin:10px; overflow:auto; width:230px; height:300px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
<div style="text-align:center"> 未分配角色</div>
<select name="wfpRoles" id="wfpRoles" multiple="multiple" style="width:100%;height:95%">
	 	<c:forEach var="item" items="${wfpRoles}" varStatus="s">
          <option value="${item.roleId}" >${item.roleName}</option>
        </c:forEach>
	</select>
</div>
<div style=" float:left; display:block; margin:10px;vertical-align:middle;
  overflow:auto; width:50px; height:200px; line-height:21px; background:#FFF;">
  <div style="margin-top:50px;margin-bottom:20px">
  <input type="button" id="btnAddSelect" value="&gt;"  style="width:100%" />
  <input type="button" id="btnAddAll" value="&gt;&gt" style="width:100%"  />
  <input type="button" id="btnRemoveAll" value="&lt;&lt"  style="width:100%" />
  <input type="button" id="btnRemove" value="&lt;" style="width:100%"  />
  </div>
 
</div>

<div style=" float:left; display:block; margin:10px; overflow:auto; width:230px; height:300px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
<div style="text-align:center">已分配角色</div>

<select name="userRoles" id="userRoles" multiple="multiple" style="width:100%;height:95%">
<c:forEach var="item" items="${userRoles}" varStatus="s">
<option value="${item.roleId}" >${item.roleName}</option>
</c:forEach>
	</select>
</div>
</div>
<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btnSubmit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>

</div>