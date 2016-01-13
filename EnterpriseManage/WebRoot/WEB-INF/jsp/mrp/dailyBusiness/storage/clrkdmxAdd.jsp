<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<script>
	function checkRkdUrl() {
		var arrayObj = new Array([2]);

		if($("#F_XMBH").val().length == 0) {
			arrayObj[0] = false;
			arrayObj[1] = "项目编号不能为空，请选择入库材料所属项目！";
			return arrayObj;
		} else {
			arrayObj[0] = true;
			return arrayObj;
		}
	}

	function rkdFormAfterLoad() {
		document.rkdMaterialForm.F_CLBH.focus();
		$("#rkdMaterialForm F_CLBH").focus();
	}
</script>
<div class="pageContent">
	<form method="post" action="${ctx}/dailyBusiness/storage/formmx/insert/${F_KJQJ}?F_DJBH=${F_DJBH}&F_GUID=${F_GUID}&F_RKLX=${F_RKLX}&F_CKBH=${F_CKBH}&F_CHDATE=${F_CHDATE}&formType=1&callbackType=closeCurrent&forwardUrl=${ctx}/dailyBusiness/storage/form/edit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input id="F_FLGUID" name="F_FLGUID" value="${F_FLGUID}" type="hidden"/>
			<input id="F_CRDATE" name="F_CRDATE" value="" type="hidden"/>
			<p>
				<label>项目编号: </label>
				<input  readonly="readonly" id="F_XMBH" class="required" name="F_XMBH" type="text" postField="keyword" suggestFields="F_XMBH,F_XMMC" lookupGroup="org1" value="${hyxmObject.f_XMBH}"/>
				<a class="btnLook" href="${ctx}/sysConfigure/project/help?F_XMZT=0" lookupGroup="">查找带回</a>	
				<span class="info"></span>
			</p>
			<p>
				<label>项目名称: </label>
				<input type="text" name="F_XMMC" value="${hyxmmxObject.f_XMMC}" readonly="readonly"/>
			</p>
			<p>
				<label>产品编号: </label>
				<input  readonly="readonly" class="required" id="F_CPBH" name="F_CPBH" type="text" suggestFields="F_CPBH,F_CPMC" lookupGroup=""/>
				<a class="btnLook" href="${ctx}/sysConfigure/project/product/help/{F_XMBH}" warn="请选择一个项目" lookupGroup="">查找带回</a>		
				<span class="info"></span>
			</p>
			<p>
				<label>产品名称: </label>
				<input type="text" name="F_CPMC" value="${hyxmmxObject.f_CPMC}" readonly="readonly"/>
			</p>
			<p>
				<label>材料编号: </label>
				<input  readonly="readonly" class="required" id="F_CLBH" name="F_CLBH" type="text" postField="keyword" suggestFields="F_CLBH,F_CLMC" lookupGroup="org1" value="${hyxmObject.f_CLBH}"/>
				<a class="btnLook" href="${ctx}/dictHelp/materialHelp/{F_XMBH}?F_CLBH=&F_CPBH={F_CPBH}&F_DWBH={F_DWBH}&F_CSBH={F_CSBH}&WHERE=1>2" isCheck="false" onCheck="checkRkdUrl()" warn="请选择一个项目" afterLoad="rkdFormAfterLoad()" lookupGroup="">查找带回</a>	
				<span class="info"></span>
			</p>
			<p>
				<label>材料名称: </label>
				<input type="text" name="F_CLMC" value="${hyxmmxObject.f_CLMC}" readonly="readonly"/>
			</p>		
			<p>
				<label>供应商编号: </label>
				<input  readonly="readonly" class="required" id="F_DWBH" name="F_DWBH" type="text" postField="keyword" suggestFields="F_DWBH,F_DWMC" lookupGroup="org1" value="${hyxmObject.f_DWBH}"/>
				<a class="btnLook" href="${ctx}/sysConfigure/material/suppliers" lookupGroup="">查找带回</a>	
				<span class="info"></span>
			</p>
			<p>
				<label>供应商名称: </label>
				<input type="text" name="F_DWMC" value="${hyxmmxObject.f_DWMC}" readonly="readonly"/>
			</p>
			<p>
				<label>厂商编号: </label>
				<input  readonly="readonly" class="required" id="F_CSBH" name="F_CSBH" type="text" postField="keyword" suggestFields="F_CSBH,F_CSMC" lookupGroup="org1" value="${hyxmObject.f_CSBH}"/>
				<a class="btnLook" href="${ctx}/dictHelp/company/help?F_CSBH=&F_SYZT=1&tarRel=help/dictHelp/csHelp" warn="请选择一个项目" lookupGroup="">查找带回</a>	
				<span class="info"></span>
			</p>
			<p>
				<label>厂商名称: </label>
				<input type="text" name="F_CSMC" value="${hyxmmxObject.f_CSMC}" readonly="readonly"/>
			</p>	
			<p>
				<label>货架（行）: </label>
				<input type="text" name="F_ROW" value="${hyxmmxObject.f_ROW}" class="digits required" min="1" alt="货架（行）"/>
			</p>
			<p>
				<label>货架（列）: </label>
				<input type="text" name="F_COL" value="${hyxmmxObject.f_COL}" class="digits required" min="1" alt="货架（列）"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>材料单价: </label>
				<input type="text" name="F_CLDJ" value="${hyxmmxObject.f_CLDJ}" class="number required" min="0.01" alt="请输入材料单价"/>
				<span class="info">（元）</span>
			</p>
			<p>
				<label>入库数量: </label>
				<input type="text" name="F_RKSL" value="${hyxmmxObject.f_RKSL}" class="number required" min="1" alt="请输入入库数量"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
</div>