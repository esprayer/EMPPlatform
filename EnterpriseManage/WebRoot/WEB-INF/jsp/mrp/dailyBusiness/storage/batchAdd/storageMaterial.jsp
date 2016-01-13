<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../include.inc.jsp"%>

<script>
	function checkStorageBatchAddStep1() {
		var arrayObj = new Array([2]);
		var $form = $("#StorageBatchAddStep1");
		
		if (!$form.valid()) {
			arrayObj[0] = false;
			arrayObj[1] = null;
			return arrayObj;
		} else {
			arrayObj[0] = true;
			arrayObj[1] = null;
			return arrayObj;
		}
	}
	function getScanningMaterial() {
		return $("#StorageBatchAddStep1");
	}
	function rkdStep1AfterLoad() {
		document.StorageBatchAddStep1.F_CLBH.focus();
	}
</script>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
	#StorageBatchAddStep1 span.error{left:100px}
</style>

<form method="post" id="StorageBatchAddStep1" name="StorageBatchAddStep1" action="${ctx}/dailyBusiness/storage/form" navTabId="storageLiNav" title="材料入库" callbackType="closeCurrent" class="pageForm required-validate" onsubmit="return validateDialogCallback(this, dialogAjaxNavTab);">
	<div class="pageFormContent" layoutH="56" style="border:0">
		<p style="width:100%;">
			<label style="width:60px;">材料编号：</label>
			<textarea name="F_CLBH" id="F_CLBH" style="width:220px;" cols="30" rows="16" class="required"></textarea>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<a class="buttonActive" target="dialogToDialog" rel="rkdFormLiBox" onCheck="checkStorageBatchAddStep1()" 
				   ajaxData="1" getAjaxData="getScanningMaterial()" callback="openDialog"
				   href="${ctx}/dailyBusiness/form/batchAdd/storage/checkInputMaterial?F_DJBH=${F_DJBH}&F_GUID=${F_GUID}&F_KJQJ=${F_KJQJ}&F_RKLX=${F_RKLX}&F_CKBH=${F_CKBH}&F_CHDATE=${F_CHDATE}&F_NAVTAB=${F_NAVTAB}&dialogMax=true&dialogMaxable=true&dialogTitle=@批量添加入库材料@&callbackType=closeCurrent&dialogMask=true&dialogRestoreable=false&dialogHref=${ctx}/dailyBusiness/form/batchAdd/storage/selectMaterial?">
				   <span>下一步</span>
				</a>
			</li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>