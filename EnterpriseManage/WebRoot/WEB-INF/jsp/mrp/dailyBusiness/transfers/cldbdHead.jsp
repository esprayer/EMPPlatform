<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	.grid{width:99.9%;}	
</style>

<form id="pagerForm" method="post" action="/management/book/edit/${book.id}">
	<input type="hidden" name="pageNum" value="1" />
</form>

<div class="panel">
		<div class="panel">
			<b><p style="font-size: 30px;font-family:楷体_GB2312;letter-spacing:12px;text-align:center;text-decoration:underline">仓库调拨单</p></b>
		</div>
		<div id="rkdFormLiBox" class="pageFormContent">
				<c:import url="cldbdForm.jsp"></c:import>
		</div>
</div>
