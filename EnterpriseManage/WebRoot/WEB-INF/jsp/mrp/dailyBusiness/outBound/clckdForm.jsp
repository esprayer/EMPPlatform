<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
	#clckdFormllrP span.error{left:80px;width:65px}
</style>

<script>
	function checkCkdSubmit() {
		var arrayObj = new Array([2]);
		var $form = $("#ckdForm");
		
		if (!$form.valid()) {
			arrayObj[0] = false;
			arrayObj[1] = null;
			return arrayObj;
		}
		var tab = $("#ckdTable");
		//表格行数
		var rows = tab.find("tr").length;
		if(rows == 0) {
			arrayObj[0] = false;
			arrayObj[1] = "材料出库单至少维护一条明细记录！";
			return arrayObj;
		}
		arrayObj[0] = true;
		return arrayObj;
	}

	function getCkdFormAjaxData() {
		return $("#ckdForm");
	}

	function clckdFormCallBackFun(response) {
		var json = DWZ.jsonEval(response);
		DWZ.ajaxDone(json);
		
		if (json.statusCode == DWZ.statusCode.ok) {
			dialogAjaxDone(json);
		}else if(json.statusCode == DWZ.statusCode.error) {
			alertMsg.error(json.message);
			return false;
		}
	}
</script>

<div class="searchBar">
	<form method="post" id="ckdForm" action="${ctx}/dailyBusiness/outBound/form/update?navTabId=outBoundLiNav&rel=ckdFormLiBox" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabDialogAjaxDone);">	
		<input id="F_KJQJ" name="F_KJQJ" value="${hyckd.f_KJQJ}" type="hidden"/>
		<input id="F_GUID" name="F_GUID" value="${hyckd.f_GUID}" type="hidden"/>
		<input id="F_DJZT" name="F_DJZT" value="${hyckd.f_DJZT}" type="hidden"/>
		<input id="F_BZRID" name="F_BZRID" value="${hyckd.f_BZRID}" type="hidden"/>
		
		<p style="width:210px;">
			<label style="width:60px;">制单日期: </label>
			<input type="text" name="F_DATE" value="${hyckd.f_DATE}" readonly="readonly" maxlength="20"/>
		</p>
		<p style="width:260px;">
			<label style="width:60px;">单据编号: </label>
			<input type="text" name="F_DJBH" value="${hyckd.f_DJBH}" readonly="readonly" style="width:160px;"/>
		</p>
		<p style="width:240px;">
			<label style="width:60px;">项目名称:  </label>
			<input id="F_XMBH" name="F_XMBH" value="${hyckd.f_XMBH}" type="hidden"/>
			<input readonly="readonly" class="required" name="F_XMMC" type="text" postField="keyword" suggestFields="F_XMBH,F_XMMC" lookupGroup="org1" value="${hyckd.f_XMMC}"/>
		</p>
		<p style="width:260px;">
			<label style="width:60px;">仓库名称: </label>
			<input id="F_CKBH" name="F_CKBH" value="${hyckd.f_CKBH}" type="hidden"/>
			<input readonly="readonly" class="required" name="F_CKMC" type="text" postField="keyword" suggestFields="F_CKBH,F_CKMC" lookupGroup="org1" value="${hyckd.f_CKMC}"/>
		</p>
		<p style="width:710px;height:40px;">
			<label style="width:60px;">备注: </label>
			<c:if test="${hyckd.f_DJZT=='0'}">
				<textarea name="F_BZ" cols="80" rows="2" style="width:597px;" class="required">${hyckd.f_BZ}</textarea>
			</c:if>	
			<c:if test="${hyckd.f_DJZT=='1'}">
				<textarea name="F_BZ" readonly="true" cols="80" rows="2" style="width:597px;">${hyckd.f_BZ}</textarea>
			</c:if>				
		</p>	
		<c:if test="${hyckd.f_DJZT=='0'}">
			<p id="clckdFormllrP" style="width:240px;height:40px;">
				<label style="width:50px;">领料人: </label>
				<input id="F_LLRID" name="F_LLRID" value="${hyckd.f_LLRID}" type="hidden"/>
				<input readonly="readonly" class="required" name="F_LLRMC" type="text" suggestFields="F_YYCPBH,F_YYCPMC" lookupGroup="" value="${hyckd.f_LLRMC}"/>
				<a class="btnLook" href="${ctx}/dictHelp/deportHelp/user?TYPE=F_LLRID,F_LLRMC&MUL=0" warn="请选择出库材料所属项目" lookupGroup="">查找带回</a>		
				<span class="info"></span>
			</p>
		</c:if>
		<c:if test="${hyckd.f_DJZT=='1'}">
			<p id="clckdFormllrP" style="width:240px;height:40px;">
				<label style="width:50px;">领料人: </label>
				<input id="F_LLRID" name="F_LLRID" value="${hyckd.f_LLRID}" type="hidden"/>
				<input readonly="readonly" class="required" name="F_LLRMC" type="text" suggestFields="F_YYCPBH,F_YYCPMC" lookupGroup="" value="${hyckd.f_LLRMC}"/>
			</p>
		</c:if>
		
		<h3 class="contentTitle"></h3>
		
		<div class="panelBar">
			<ul class="toolBar">
				<c:if test="${hyckd.f_DJZT=='0'}">
					<li><a class="add" target="dialog" mask="true" width="900" height="540" rel="objectNav" href="${ctx}/dailyBusiness/outBound/form/addfl?F_DJBH=${hyckd.f_DJBH}&F_GUID=${hyckd.f_GUID}&F_KJQJ=${hyckd.f_KJQJ}&F_XMBH=${hyckd.f_XMBH}&F_XMMC=${hyckd.f_XMMC}&F_CKBH=${hyckd.f_CKBH}&F_CKMC=${hyckd.f_CKMC}&F_CHDATE=${F_CHDATE}" title="添加出库单明细"><span>添加出库单明细</span></a></li>
					<li><a class="add" target="dialog" mask="true" width="350" height="340" rel="objectNav" afterLoad="ckdStep1AfterLoad()" href="${ctx}/dailyBusiness/form/outBound/batchAdd/scanningMaterial?F_DJBH=${hyckd.f_DJBH}&F_GUID=${hyckd.f_GUID}&F_KJQJ=${hyckd.f_KJQJ}&F_XMBH=${hyckd.f_XMBH}&F_CKBH=${hyckd.f_CKBH}&F_CHDATE=${F_CHDATE}" title="批量添加出库单材料" warn="请选择仓库信息！"><span>批量添加出库材料</span></a></li>
					<li><a class="edit" target="dialog"  mask="true" width="900" height="540" rel="objectNav" href="${ctx}/dailyBusiness/outBound/form/editfl{slt_objId}&F_DJBH=${hyckd.f_DJBH}&F_KJQJ=${hyckd.f_KJQJ}&F_DJZT=${hyckd.f_DJZT}" title="编辑出库单明细"><span>编辑出库单明细</span></a></li>
					<li><a class="delete" target="ajaxTodo" href="${ctx}/dailyBusiness/outBound/form/deletefl{slt_objId}&F_DJBH=${hyckd.f_DJBH}&F_KJQJ=${hyckd.f_KJQJ}&F_CHDATE=${F_CHDATE}&callbackType=forward&callBackUrl=${ctx}/dailyBusiness/outBound/form/edit" title="你确定要删除该出库单明细吗?"><span>删除出库单明细</span></a></li>					
					<li class="line">line</li>	
				</c:if>		
				<c:if test="${hyckd.f_DJZT=='1'}">
					<li><a class="edit" target="dialog"  mask="true" width="900" height="500" rel="objectNav" href="${ctx}/dailyBusiness/outBound/form/editfl{slt_objId}&F_DJBH=${hyckd.f_DJBH}&F_KJQJ=${hyckd.f_KJQJ}&F_DJZT=${hyckd.f_DJZT}" title="查看出库单明细"><span>查看出库单明细</span></a></li>
				</c:if>					
			</ul>
		</div>
		<table id="ckdTable" class="table" width="100%" layoutH="215" rel="deportLiNav" class="required">
			<thead>
				<tr>
					<th width="25">序号</th>
					<th width="60">材料名称</th>
					<th width="60">材料应用产品名称</th>
					<th width="60">材料所属项目名称</th>
					<th width="60">材料所属产品名称</th>
					<th width="60">供应商名称</th>
					<th width="60">材料单价</th>		
					<th width="60">出库数量</th>				
					<th width="70">出库时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${hyckdmxList}" varStatus="s">
					<tr target="slt_objId" rel="?F_FLBH=${item.f_FLBH}&F_FLGUID=${item.f_FLGUID}">
						<td>${s.index + 1}</td>
						<td>${item.f_CLMC}</td>	
						<td>${item.f_YYCPMC}</td>				
						<td>${item.f_SSXMMC}</td>	
						<td>${item.f_SSCPMC}</td>	
						<td>${item.f_DWMC}</td>	
						<td>${item.f_CLDJ}</td>
						<td style="color:red;">${item.f_CKSL}</td>							
						<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>	
				</c:forEach>
			</tbody>
		</table>
		
		<div class="formBar">
			<ul style="float:left; display:block;">
				<li>
					<font style="font-size: 14px;letter-spacing:0px">
						单据状态:
						<c:if test="${hyckd.f_DJZT=='1'}">已	提交</c:if>
						<c:if test="${hyckd.f_DJZT=='0'}">未提交</c:if>
					</font>
				</li>
				
				<li style="padding:0px 0px 0px 110px;">
					<font style="font-size: 14px;letter-spacing:0px">
						制单人:${hyckd.f_BZRMC}						
					</font>
				</li>
			</ul>
			<ul>
				<c:if test="${hyckd.f_DJZT=='0'}">
					<li><a class="buttonActive" target="formSubmit" rel="ckdFormLiBox" onCheck="checkCkdSubmit()" getAjaxData="getCkdFormAjaxData()" href="${ctx}/dailyBusiness/outBound/form/submit?F_CHDATE=${F_CHDATE}&tabid=outBoundLiNav&navTabId=outBoundNav&forwardUrl=${ctx}/dailyBusiness/outBound/form/edit"" confirmMsg="提交出库单后，出库单不允许修改和删除，你确定要提交该出库单吗?"><span>提交</span></a></li>
					<li><a class="buttonActive" target="formSubmit" rel="ckdFormLiBox" getAjaxData="getCkdFormAjaxData()" href="${ctx}/dailyBusiness/outBound/form/update?F_CHDATE=${F_CHDATE}&tabid=outBoundLiNav&navTabId=outBoundNav&forwardUrl=${ctx}/dailyBusiness/outBound/form/edit"><span>保存</span></a></li>
				</c:if>
			</ul>
		</div>
	</form>
</div>