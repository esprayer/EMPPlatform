<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
	.grid{width:99.8%;}	
</style>

<script>
	function checkDbdSubmit() {
		var arrayObj = new Array([2]);
		var $form = $("#dbdForm");
		
		if (!$form.valid()) {
			arrayObj[0] = false;
			arrayObj[1] = null;
			return arrayObj;
		}
		var tab = $("#dbdForm").find("#dbdTable");
		//表格行数
		var rows = tab.find("tr").length;
		if(rows == 0) {
			arrayObj[0] = false;
			arrayObj[1] = "材料入库单至少维护一条明细记录！";
			return arrayObj;
		}
		arrayObj[0] = true;
		return arrayObj;
	}

	function getDbdFormAjaxData() {
		return $("#dbdForm");
	}

	function ckdbdFormCallBackFun(response) {
		var json = DWZ.jsonEval(response);
		DWZ.ajaxDone(json);
		
		if (json.statusCode == DWZ.statusCode.ok) {
			navTabDialogAjaxDone(json);
		}else if(json.statusCode == DWZ.statusCode.error) {
			alertMsg.error(json.message);
			return false;
		}
	}
</script>

<form method="post" id="dbdForm" action="${ctx}/dailyBusiness/transfers/form/update?rel=dbdFormLiBox&F_CHDATE=${F_CHDATE}" class="pageForm required-validate" onsubmit="return validateCallback(this,ckdbdFormCallBackFun);">
	<p style="width:210px;">
		<label style="width:60px;">制单日期: </label>
		<input type="text" name="F_DATE" value="${hydbd.f_DATE}" readonly="readonly" maxlength="20"/>
	</p>
	<p style="width:260px;">
		<label style="width:60px;">单据编号: </label>
		<input type="text" name="F_DJBH" value="${hydbd.f_DJBH}" readonly="readonly" style="width:160px;"/>
	</p>
	<p style="width:230px;">
		<label style="width:60px;padding: 0px;">仓库名称: </label>
		<input id="F_CKBH" name="F_CKBH" value="${hydbd.f_CKBH}" type="hidden"/>
		<input readonly="readonly" class="required" name="F_CKMC" type="text" value="${hydbd.f_CKMC}"/>
		<span class="info"></span>
	</p>
	<p style="width:740px;height:40px;">
		<label style="width:60px;">备注: </label>
		<c:if test="${hydbd.f_DJZT=='0'}">
			<textarea name="F_BZ" cols="80" rows="2" style="width:588px;" class="required">${hydbd.f_BZ}</textarea>
		</c:if>	
		<c:if test="${hydbd.f_DJZT=='1'}">
			<textarea name="F_BZ" readonly="true" cols="80" rows="2" style="width:588px;">${hydbd.f_BZ}</textarea>
		</c:if>		
	</p>		
	<h3 class="contentTitle"></h3>
	<div class="panelBar">
		<ul class="toolBar">
			<c:if test="${hydbd.f_DJZT=='0'}">
				<li><a class="add" target="dialog" mask="true" width="850" height="340" rel="objectNav" href="${ctx}/dailyBusiness/transfers/form/addfl?F_DJBH=${hydbd.f_DJBH}&F_GUID=${hydbd.f_GUID}&F_KJQJ=${hydbd.f_KJQJ}&F_CKBH=${hydbd.f_CKBH}&F_CHDATE=${F_CHDATE}" title="添加调拨单明细" warn="请选择仓库信息！"><span>添加调拨单明细</span></a></li>
				<li><a class="add" target="dialog" mask="true" width="350" height="340" rel="objectNav" afterLoad="dbdStep1AfterLoad()" href="${ctx}/dailyBusiness/form/transfers/batchAdd/scanningMaterial?F_DJBH=${hydbd.f_DJBH}&F_GUID=${hydbd.f_GUID}&F_KJQJ=${hydbd.f_KJQJ}&F_CKBH=${hydbd.f_CKBH}&F_CHDATE=${F_CHDATE}" title="批量添加调拨单材料" warn="请选择仓库信息！"><span>批量添加调拨材料</span></a></li>
				<li><a class="edit" target="dialog"  mask="true" width="850" height="340" rel="objectNav" href="${ctx}/dailyBusiness/transfers/form/editfl{slt_objId}&F_DJBH=${hydbd.f_DJBH}&F_KJQJ=${hydbd.f_KJQJ}&F_CKBH={F_CKBH}" title="编辑调拨单明细"><span>编辑调拨单明细</span></a></li>
				<li><a class="delete" target="ajaxTodo" href="${ctx}/dailyBusiness/transfers/form/deletefl{slt_objId}&F_DJBH=${hydbd.f_DJBH}&F_KJQJ=${hydbd.f_KJQJ}&F_CHDATE=${F_CHDATE}&F_CKBH={F_CKBH}&callbackType=forward&callBackUrl=${ctx}/dailyBusiness/transfers/form/editForm" title="你确定要删除该调拨单明细吗?"><span>删除调拨单明细</span></a></li>			
				<li class="line">line</li>	
			</c:if>		
			<c:if test="${hydbd.f_DJZT=='1'}">
				<li><a class="edit" target="dialog"  mask="true" width="850" height="300" rel="objectNav" href="${ctx}/dailyBusiness/transfers/form/editfl{slt_objId}&F_DJBH=${hydbd.f_DJBH}&F_KJQJ=${hydbd.f_KJQJ}&F_CKBH={F_CKBH}" title="查看调拨单明细"><span>查看调拨单明细</span></a></li>
			</c:if>	
		</ul>
	</div>
	<table id="dbdTable" name="dbdTable" class="table" width="100%" layoutH="220">
		<thead>
		<tr>
			<th width="25">序号</th>
			<th width="60">材料名称</th>
			<th width="60">项目名称</th>
			<th width="60">产品名称</th>
			<th width="60">供应商名称</th>
			<th width="60">厂商名称</th>
			<th width="60">材料单价</th>	
			<th width="60">调拨数量</th>		
			<th width="70">调拨时间</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${hydbdmxList}" varStatus="s">
			<tr id="rkdTable" name="rkdTable" target="slt_objId" rel="?F_FLBH=${item.f_FLBH}&F_FLGUID=${item.f_FLGUID}">
				<td>${s.index + 1}</td>
				<td>${item.f_CLMC}</td>		
				<td>${item.f_XMMC}</td>	
				<td>${item.f_CPMC}</td>	
				<td>${item.f_DWMC}</td>	
				<td>${item.f_CSMC}</td>	
				<td>${item.f_CLDJ}</td>
				<td <c:if test="${item.f_CLSL<0}">style="color:red;"</c:if>>${item.f_CLSL}</td>				
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
					<c:if test="${hydbd.f_DJZT=='1'}">已	提交</c:if>
					<c:if test="${hydbd.f_DJZT=='0'}">未提交</c:if>
				</font>
			</li>
				
			<li style="padding:0px 0px 0px 110px;">
				<font style="font-size: 14px;letter-spacing:0px">
					制单人:${hydbd.f_BZRMC}					
				</font>
			</li>
			<c:if test="${hydbd.f_DJZT=='1'}">
				<li style="padding:0px 0px 0px 110px;">
					<font style="font-size: 14px;letter-spacing:0px">
						单据提交人:${hydbd.f_SBRMC}					
					</font>
				</li>
				<li style="padding:0px 0px 0px 110px;">
					<font style="font-size: 14px;letter-spacing:0px">
						单据提交日期:<fmt:formatDate value="${hydbd.f_SBDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>				
					</font>
				</li>
			</c:if>	
		</ul>
		
		<ul>
		<c:if test="${hydbd.f_DJZT=='0'}">			
			<li><a class="buttonActive" target="formSubmit" rel="rkdFormLiBox" onCheck="checkDbdSubmit()" getAjaxData="getDbdFormAjaxData()" href="${ctx}/dailyBusiness/transfers/form/submit?F_GUID=${hydbd.f_GUID}&F_KJQJ=${hydbd.f_KJQJ}&F_CHDATE=${F_CHDATE}&tabid=transfersLiNav&navTabId=transfersNav&forwardUrl=${ctx}/dailyBusiness/transfers/form/editForm" confirmMsg="提交入库单后，入库单不允许修改和删除，你确定要提交该入库单吗?"><span>提交</span></a></li>
			<li><a class="buttonActive" target="formSubmit" rel="rkdFormLiBox" getAjaxData="getDbdFormAjaxData()" href="${ctx}/dailyBusiness/transfers/form/update?F_CHDATE=${F_CHDATE}&F_GUID=${hydbd.f_GUID}&F_KJQJ=${hydbd.f_KJQJ}&tabid=transfersLiNav&navTabId=transfersNav&forwardUrl=${ctx}/dailyBusiness/transfers/form/editForm"><span>保存</span></a></li>			
		</c:if>		
		<!-- <li><div class="button"><div class="buttonContent"><button type="button" class="close">退出</button></div></div></li> -->
		</ul>
	</div>
</form>
