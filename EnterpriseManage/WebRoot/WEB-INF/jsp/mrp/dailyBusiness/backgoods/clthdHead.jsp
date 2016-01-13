<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>
<form id="pagerForm" method="post" action="">
	<input type="hidden" name="pageNum" value="1" />
</form>

<div class="panel">
		<div class="panel">
			<b><p style="font-size: 30px;font-family:楷体_GB2312;letter-spacing:12px;text-align:center;text-decoration:underline">材料退货单</p></b>
		</div>
		<div layoutH="0" id="backGoodsLiNav" class="pageFormContent">
			<c:import url="clthdForm.jsp"></c:import>
		</div>
</div>
