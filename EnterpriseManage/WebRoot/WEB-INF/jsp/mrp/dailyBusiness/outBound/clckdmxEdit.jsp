<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<script>
	function checkCkdmxAddUrl() {
		var arrayObj = new Array([2]);

		if(typeof($("#F_YYCPBH").val()) === "undefined" || $("#F_YYCPBH").val().length == 0) {
			arrayObj[0] = false;
			arrayObj[1] = "入库材料所属产品编号不能为空，请选择入库材料所属项目！";
			return arrayObj;
		} else {
			arrayObj[0] = true;
			return arrayObj;
		}
	}

	function checkEditOutBoundFormMaterial() {
		var arrayObj = new Array([2]);

		if(typeof($("#EditOutBoundForm").find("#F_CLBH").val()) === "undefined" || $("#EditOutBoundForm").find("#F_CLBH").val().length == 0) {
			arrayObj[0] = false;
			arrayObj[1] = "材料编号不能为空！";
			return arrayObj;
		} else {
			arrayObj[0] = true;
			return arrayObj;
		}
	}
	
	function ckdFormAfterLoad() {
		document.ckdMaterialForm.F_CLBH.focus();
		$("#ckdMaterialForm F_CLBH").focus();
	}
</script>

<div class="pageContent">
	<form id="EditOutBoundForm"  method="post" action="${ctx}/dailyBusiness/outBound/formmx/insert/${hyckdmxList.f_KJQJ}?F_XMBH=${F_XMBH}&F_DJBH=${hyckdmxList.f_DJBH}&F_GUID=${hyckdmxList.f_GUID}&F_CKBH=${hyckd.f_CKBH}&F_CHDATE=${F_CHDATE}&formType=1&callbackType=closeCurrent&forwardUrl=${ctx}/dailyBusiness/outBound/form/edit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input id="F_FLGUID" name="F_FLGUID" value="${hyckdmxList.f_FLGUID}" type="hidden"/>
		<input id="F_FLBH" name="F_FLBH" value="${hyckdmxList.f_FLBH}" type="hidden"/>
		<div class="pageFormContent" layoutH="56">	
			<p>
				<label>入库仓库编号: </label>
				<input readonly="readonly" class="required" id="F_SSCKBH" name="F_SSCKBH" type="text" value="${hyckdmxList.f_SSCKBH}"/>
			</p>
			<p>
				<label>入库仓库名称:</label>
				<input readonly="readonly" class="required" name="F_SSCKMC" type="text" value="${hyckdmxList.f_SSCKMC}"/>			
			</p>
			<p>
				<label>入库项目编号:</label>
				<input readonly="readonly"  class="required" id="F_SSXMBH" name="F_SSXMBH" type="text" value="${hyckdmxList.f_SSXMBH}"/>			
			</p>
			<p>
				<label>入库项目名称:</label>
				<input readonly="readonly"  class="required" name="F_SSXMMC" type="text" value="${hyckdmxList.f_SSXMMC}"/>			
			</p>
			<p>
				<label>入库产品编号: </label>
				<input readonly="readonly" class="required" id="F_SSCPBH" name="F_SSCPBH" type="text" suggestFields="F_SSCPBH,F_SSCPMC" lookupGroup="" value="${hyckdmxList.f_SSCPBH}"/>
				<c:if test="${F_DJZT=='0'}">
					<a class="btnLook" href="${ctx}/dictHelp/project/product/help?F_XMBH=${F_XMBH}&F_CKBH=${F_CKBH}&F_XMZT=0&F_CPBH=&HELPCOL=F_SSCPBH,F_SSCPMC,F_SSCPBH,F_SSCPMC&tarRel=/mrp/dailyBusiness/outBound/xmckcpHelp" warn="请选择出库材料所属项目" lookupGroup="">查找带回</a>		
					<span class="info"></span>
				</c:if>	
			</p>
			<p>
				<label>入库产品名称: </label>
				<input type="text" name="F_SSCPMC" readonly="readonly" value="${hyckdmxList.f_SSCPMC}"/>
			</p>
			<p>
				<label>出库材料编号: </label>		
				<input readonly="readonly" class="required" id="F_CLBH" name="F_CLBH" type="text" postField="keyword" suggestFields="F_CLBH,F_CLMC" lookupGroup="org1" value="${hyckdmxList.f_CLBH}"/>
				<c:if test="${F_DJZT=='0'}">
					<a class="btnLook" width="1024" href="${ctx}/dictHelp/material/bussinessHelp?F_CPBH={F_SSCPBH}&F_CLBH=&F_XMBH={F_SSXMBH}&F_CKBH={F_SSCKBH}&tarRel=/mrp/dailyBusiness/outBound/clHelp" isCheck="false" afterLoad="ckdFormAfterLoad()" lookupGroup="">查找带回</a>	
					<span class="info"></span>
				</c:if>	
			</p>
			<p>
				<label>出库材料名称: </label>	
				<input type="text" name="F_CLMC" readonly="readonly" value="${hyckdmxList.f_CLMC}"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>出库仓库名称: </label>
				<input readonly="readonly"  class="required" id="F_YYCKBH" name="F_YYCKBH" type="text" postField="keyword" suggestFields="F_YYCKBH,F_YYCKMC" lookupGroup="org1" value="${hyckdmxList.f_YYCKBH}"/>
				<c:if test="${F_DJZT=='0'}">
					<a class="btnLook" href="${ctx}/dailyBusiness/outBound/deportHelp" lookupGroup="">查找带回</a>	
					<span class="info"></span>
				</c:if>	
			</p>
			<p>
				<label>出库仓库名称: </label>
				<input type="text" name="F_YYCKMC" readonly="readonly" value="${hyckdmxList.f_YYCKMC}"/>
			</p>
			<p>
				<label>出库项目编号: </label>
				<input readonly="readonly"  class="required" id="F_YYXMBH" name="F_YYXMBH" type="text" postField="keyword" suggestFields="F_YYXMBH,F_YYXMMC" lookupGroup="org1" value="${hyckdmxList.f_YYXMBH}"/>
				<c:if test="${F_DJZT=='0'}">
					<a class="btnLook" width="1024" href="${ctx}/dailyBusiness/outBound/DVProjectProductHelp?F_CLBH={F_CLBH}&beginDate=${F_KJQJ}01&endDate=${F_KJQJ}31&F_XMBH={F_YYXMBH}&F_CPBH=" isCheck="false" onCheck="checkEditOutBoundFormMaterial()" lookupGroup="">查找带回</a>	
					<span class="info"></span>
				</c:if>	
			</p>
			<p>
				<label>出库项目名称: </label>
				<input type="text" name="F_YYXMMC" readonly="readonly" value="${hyckdmxList.f_YYXMMC}"/>
			</p>
			<p>
				<label>出库产品编号: </label>
				<input readonly="readonly" class="required" name="F_YYCPBH" type="text" suggestFields="F_YYCPBH,F_YYCPMC" lookupGroup="" value="${hyckdmxList.f_YYCPBH}"/>
				<c:if test="${F_DJZT=='0'}">
					<a class="btnLook" href="${ctx}/dictHelp/project/product/help?F_XMBH=${F_XMBH}&F_CKBH=${F_CKBH}&F_XMZT=0&F_CPBH=&HELPCOL=F_SSCPBH,F_SSCPMC,F_SSCPBH,F_SSCPMC&tarRel=/mrp/dailyBusiness/outBound/xmckcpHelp" warn="请选择出库材料所属项目" lookupGroup="">查找带回</a>	
					<span class="info"></span>
				</c:if>	
			</p>	
			<p>
				<label>出库产品名称: </label>
				<input type="text" name="F_YYCPMC" readonly="readonly" value="${hyckdmxList.f_YYXMMC}"/>
			</p>	
			<p>
				<label>供应商编号: </label>
				<input id="F_DWBH" name="F_DWBH" value="${hyckdmxList.f_DWBH}" type="text" readonly="readonly" class="required"/>
			</p>
			
			<p>
				<label>供应商名称: </label>
				<input type="text" name="F_DWMC" value="${hyckdmxList.f_DWMC}" readonly="readonly" class="required"/>
			</p>
			
			<p>
				<label>厂商编号: </label>
				<input id="F_CSBH" name="F_CSBH" value="${hyckdmxList.f_CSBH}" type="text" readonly="readonly" class="required"/>
			</p>
			
			<p>
				<label>厂商名称: </label>
				<input type="text" name="F_CSMC" value="${hyckdmxList.f_CSMC}" readonly="readonly" class="required"/>
			</p>
			
			<div class="divider"></div>
			<p>
				<label>材料申请数量: </label>
				<input type="text" name="F_SQSL" readonly="readonly" value="${hyckdmxList.f_SQSL}"/>
			</p>
			<p>
				<label>材料已领用数量: </label>
				<input type="text" name="F_LYSL" readonly="readonly" value="${hyckdmxList.f_LYSL}"/>
			</p>
			<p>
				<label>入库材料单价: </label>
				<input type="text" name="F_CLDJ" readonly="readonly" value="${hyckdmxList.f_CLDJ}"/>
				<span class="info">（元）</span>
			</p>
			<p>
				<label>库存数量: </label>
				<input type="text" name="F_KCSL" readonly="readonly" value="${hyckdmxList.f_KCSL}"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>出库数量: </label>
				<input type="text" <c:if test="${F_DJZT=='1'}"> readonly="readonly" </c:if> name="F_CKSL" value="${hyckdmxList.f_CKSL}" class="number required" min="0.1"/>
			</p>
		</div>
		<c:if test="${F_DJZT=='0'}">
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
				</ul>
			</div>
		</c:if>	
	</form>
</div>