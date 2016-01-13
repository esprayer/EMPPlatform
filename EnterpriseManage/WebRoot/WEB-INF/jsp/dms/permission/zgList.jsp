<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<script>
	$(document).ready(function(){
		$("#userList").change(function() {
			var bmbh =  $('#bmbh').val();
			if(bmbh) {
				$('#bmbh').val("");
				$('#bmmc').val("");
				var $rel = $("#deportFolderPermissionLiBox");
				$rel.loadUrl("${ctx}/dms/folderPermission/bmList");
			}
        });
	});
</script>

<input type="hidden" id="bmbh" name="bmbh" value="${F_BMBH}" />
<input type="hidden" id="bmmc" name="bmmc" value="${F_BMMC}" />
<select name="wfpRoles" id="userList" multiple="multiple" style="width:100%;height:95%">
	<c:forEach var="item" items="${userList}" varStatus="s">
		<option value="${item.USER_ORGID};${item.USER_ID}">${item.USER_NAME}(${item.USER_ID})</option>		
	</c:forEach>	
</select>