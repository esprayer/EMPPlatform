<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>
<script src="${ctx}/styles/dwz/js/jquery.json-2.2.js" type="text/javascript"></script>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<script>
	function checkBackGoodsAjaxData() {
		var   arrayObj = new Array([2]);
		var    bInput = true;
		arrayObj[0] = null;
		arrayObj[1] = null;
		var     count = 0;
		var   trIndex = 1;
		
		if($("#backGoodsForm").find("#F_BZ").val().length == 0) {
			arrayObj[0] = false;
			arrayObj[1] = "退货单备注信息不能为空！";
			return arrayObj;
		}

		var thrVal = $("#backGoodsForm").find("#F_THRID").val();
		var thcsVal = $("#backGoodsForm #F_CSBH").val();
		if(thrVal.length == 0 && thcsVal.length == 0) {
			arrayObj[0] = false;
			arrayObj[1] = "退货人和退货厂商不能同时为空！";
			return arrayObj;
		}

		

		$("#backGoodsForm").find('#backGoodsTable tbody tr').each(function(){
			//this.style.display = 'none';
			var bcsl = $(this).find("input[name=F_BCSL]").val().trim();
			var cksl = $(this).find("input[name=F_CKSL]").val().trim();
			var thsl = $(this).find("input[name=F_THSL]").val().trim();
			//必须维护一个大于0的正整数，如果有一条大于0，则
			if(!bcsl.isNumber()) {
				bInput = false;
				arrayObj[0] = false;
				arrayObj[1] = "保存提交失败,原因：<br>第【" + trIndex + "】行本次退货数量不正确，请维护一个合法的数字";
				return false;
			} 

			if(bcsl < 0) {
				bInput = false;
				arrayObj[0] = false;
				arrayObj[1] = "保存提交失败,原因：<br>第【" + trIndex + "】行本次退货数量必须大于零";
				return false;
			}

			if(bcsl != 0) {
				if(cksl-thsl < bcsl) {
					bInput = false;
					arrayObj[0] = false;
					arrayObj[1] = "保存提交失败,原因：<br>第【" + trIndex + "】行本次退货数量大于已出库数量减去已退货数量！<br><li>出库数量为：" + cksl + ";</li><br><li>退货数量：" + thsl + ";</li><br><li>维护本次退货数量：" + bcsl + "<br>请重新维护本次退货数量！";
					return false;
				} else {
					count++;
				}
			}
			trIndex++;
		});
		if(!bInput) {
			return arrayObj;
		}
		if(count==0){
			arrayObj[0] = false;
			arrayObj[1] = "请至少维护一条退货数量大于0的分录信息！";
			return arrayObj;
		}
		
		arrayObj[0] = true;
		return arrayObj;
	}

	function getBackGoodsMaterialAjaxData() {
		var F_CHDATE = $('#selectAddMaterial_CHDATE').val();
    	var i = 0;
    	var addRows=[];
    	var returnData="";
    	$("#backGoodsForm").find('#backGoodsTable tbody tr').each(function(){
			var row = {
				F_KJQJ:         "${hythd.f_KJQJ}",
				F_DJBH:         "${hythd.f_DJBH}",
				F_GUID:         "${hythd.f_GUID}",
				F_FLGUID:       $(this).find("input[name=F_FLGUID]").val().trim(),
				F_FLBH:         $(this).find("input[name=F_FLBH]").val().trim(),
				EXT_BIZ_KJQJ:   $(this).find("input[name=EXT_BIZ_KJQJ]").val().trim(),
				EXT_BIZ_DJBH:   $(this).find("input[name=EXT_BIZ_DJBH]").val().trim(),
				EXT_BIZ_FLBH:   $(this).find("input[name=EXT_BIZ_FLBH]").val().trim(),
				F_THSL:         $(this).find("input[name=F_BCSL]").val().trim()
			};
			addRows.push(row);
			i++;
		});
    	var row = new Object();
    	row["com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHDMX"] = addRows;
    	row["F_KJQJ"] = "${hythd.f_KJQJ}";
    	row["F_DJBH"] = "${hythd.f_DJBH}";
    	row["F_GUID"] = "${hythd.f_GUID}";
    	row["F_BZRID"] = "${hythd.f_BZRID}";
    	row["F_BZ"] = $("#backGoodsForm").find("#F_BZ").val().trim();
    	row["F_THRID"] = $("#backGoodsForm").find("#F_THRID").val();
    	row["F_XMBH"] = "${hythd.f_XMBH}";
    	row["F_CKBH"] = "${hythd.f_CKBH}";
    	row["F_DATE"] = "${hythd.f_DATE}";
    	row["EXT_BIZ_KJQJ"] = "${hythd.EXT_BIZ_KJQJ}";
    	row["EXT_BIZ_DJBH"] = "${hythd.EXT_BIZ_DJBH}";
    	row["F_BZRID"] = "${hythd.f_BZRID}";
    	row["F_CSBH"] = $("#backGoodsForm").find("#F_CSBH").val();
    	row["F_CHDATE"] = "${F_CHDATE}";
    	return $.toJSON(row);
	}
</script>

<form id="pagerForm" method="post" action="${ctx}/dailyBusiness/backGoods/form/thdList?F_KJQJ=${hythd.f_KJQJ}&F_GUID=${hythd.f_GUID}&F_RKLX=0">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<div class="pageContent">
	<form method="post" id="backGoodsForm" action="${ctx}/dailyBusiness/backGoods/createSaveBackGoodsForm?navTabId=storageLiNav&rel=rkdFormLiBox" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabDialogAjaxDone);">	
		<input id="F_KJQJ" name="F_KJQJ" value="${hythd.f_KJQJ}" type="hidden"/>
		<input id="F_GUID" name="F_GUID" value="${F_GUID}" type="hidden"/>
		<input id="F_DJZT" name="F_DJZT" value="${hythd.f_DJZT}" type="hidden"/>
		<input id="EXT_BIZ_KJQJ" name="EXT_BIZ_KJQJ" value="${hythd.EXT_BIZ_KJQJ}" type="hidden"/>
		
		<p style="width:200px;">
		    <label style="width:80px;">退货单据编号: </label>
			<input type="text" name="F_DJBH" value="${hythd.f_DJBH}" readonly="readonly" style="width:80px;"/>
		</p>
		<p style="width:200px;">
			<label style="width:80px;">出库单据编号: </label>
			<input type="text" name="EXT_BIZ_DJBH" value="${hythd.EXT_BIZ_DJBH}" readonly="readonly" style="width:80px;"/>
		</p>
		<p style="width:250px;">
			<label style="width:55px;text-align:left;">项目名称:</label>
			<input id="F_XMBH" name="F_XMBH" value="${hythd.f_XMBH}" type="hidden"/>
			<input readonly="readonly" class="required" name="F_XMMC" type="text" value="${hythd.f_XMMC}"/>
		</p>
		<p style="width:250px;">
			<label style="width:55px;text-align:left;">仓库名称: </label>
			<input id="F_CKBH" name="F_CKBH" value="${hythd.f_CKBH}" type="hidden"/>
			<input readonly="readonly" class="required" name="F_CKMC" type="text" value="${hythd.f_CKMC}"/>
		</p>
		
		<p style="width:280px;">
			<label style="width:80px;">制单日期: </label>
			<input type="text" name="F_DATE" value="${hythd.f_DATE}" readonly="readonly" maxlength="20" style="width:160px;"/>
		</p>
		
		<p style="width:260px;">
			<label style="width:80px;">退货人: </label>
			<input id="F_THRID" name="F_THRID" value="${hythd.f_THRID}" type="hidden"/>
			<input disabled="true"  class="required" name="F_THRMC" type="text" postField="keyword" suggestFields="F_THRID,F_THRMC" lookupGroup="org1" value="${hythd.f_THRMC}"/>
			<c:if test="${hythd.f_DJZT=='0'}">
				<a class="btnLook" href="${ctx}/dailyBusiness/backGoods/help/departmentUser/department" lookupGroup="">查找带回</a>	
				<span class="info"></span>
			</c:if>				
		</p>
			
		<p style="width:260px;">
			<label style="width:80px;">退货厂商: </label>
			<input id="F_CSBH" name="F_CSBH" value="${hythd.f_CSBH}" type="hidden"/>
			<input disabled="true"  class="required" name="F_CSMC" type="text" postField="keyword" suggestFields="F_CSBH,F_CSMC" lookupGroup="org1" value="${hythd.f_CSMC}"/>
			<c:if test="${hythd.f_DJZT=='0'}">
				<a class="btnLook" href="${ctx}/dailyBusiness/backGoods/help/companyHelp?F_CSBH=" lookupGroup="">查找带回</a>	
				<span class="info"></span>
			</c:if>				
		</p>
		
		<p style="width:100%;height:100%">
			<label style="width:80px;">备注: </label>
			<c:if test="${hythd.f_DJZT=='0'}">
				<textarea id="F_BZ" name="F_BZ" cols="80" rows="2" style="width:420px;" class="required" >${hythd.f_BZ}</textarea>
			</c:if>	
			<c:if test="${hythd.f_DJZT=='1'}">
				<textarea id="F_BZ" readonly="true" name="F_BZ" cols="80" rows="2" style="width:420px;" class="required" >${hythd.f_BZ}</textarea>					
			</c:if>		
		</p>	
		
		<h3 class="contentTitle"></h3>
		<div class="panelBar">
			<ul class="toolBar">	
				<li><a class="edit" target="dialog"  mask="true" width="900" height="500" rel="objectNav" href="${ctx}/dailyBusiness/outBound/form/editfl{slt_objId}&F_KJQJ=${hythd.f_KJQJ}&F_DJZT=1" title="查看出库单明细"><span>查看出库单明细</span></a></li>				
			</ul>
		</div>
		<table id="backGoodsTable" class="table" width="100%" layoutH="245" rel="deportLiNav" class="required">
			<thead>
				<tr>
					<th width="3%" align=center>序号</th>
					<th width="11%" align=center>材料名称</th>
					<th width="11%" align=center>项目名称</th>
					<th width="12%" align=center>产品名称</th>
					<th width="12%" align=center>供应商名称</th>
					<th width="11%" align=center>厂商名称</th>
					<th width="10%" align=center>出库单价</th>
					<th width="10%" align=center>出库数量</th>
					<th width="10%" align=center>已退货数量</th>		
					<th width="10%" align=center>本次退货数量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${hythdmxList}" varStatus="status">
					<tr target="slt_objId" rel="?F_FLBH=${item.EXT_BIZ_FLBH}&F_FLGUID=${item.EXT_BIZ_FLGUID}&F_DJBH=${item.EXT_BIZ_DJBH}">
						<td>${status.index + 1}</td>
						<td>${item.f_CLMC}</td>				
						<td>${item.f_SSXMMC}</td>	
						<td>${item.f_SSCPMC}</td>	
						<td>${item.f_DWMC}</td>	
						<td>${item.f_CSMC}</td>	
						<td style="text-align:right">${item.f_CLDJ}</td>
						<td style="text-align:right"><input id="F_CKSL" name="F_CKSL" value="${item.f_CKSL}" type="hidden"/>${item.f_CKSL}</td>
						<td style="text-align:right"><input id="F_THSL" name="F_THSL" value="${item.f_THSL}" type="hidden"/>${item.f_THSL}</td>
						<td style="text-align:right">
							<input id="F_FLBH" name="F_FLBH" value="${item.f_FLBH}" type="hidden"/>
							<input id="F_FLGUID" name="F_FLGUID" value="${item.f_FLGUID}" type="hidden"/>
							<input id="EXT_BIZ_FLBH" name="EXT_BIZ_FLBH" value="${item.EXT_BIZ_FLBH}" type="hidden"/>
							<input id="EXT_BIZ_KJQJ" name="EXT_BIZ_KJQJ" value="${item.EXT_BIZ_KJQJ}" type="hidden"/>
							<input id="EXT_BIZ_DJBH" name="EXT_BIZ_DJBH" value="${item.EXT_BIZ_DJBH}" type="hidden"/>
							<c:if test="${hythd.f_DJZT=='1'}">
								${item.f_BCSL}								
							</c:if>	
							<c:if test="${hythd.f_DJZT=='0'}">
								<input type="text" style="width:90%;text-align:right;" name="F_BCSL" value="${item.f_BCSL}"/>						
							</c:if>	
						</td>					
					</tr>	
				</c:forEach>
			</tbody>
		</table>
		
		<div class="formBar">
			<ul style="float:left; display:block;">
				<li>
					<font style="font-size: 14px;letter-spacing:0px">
						单据状态:
						<c:if test="${hythd.f_DJZT=='1'}">已	提交</c:if>
						<c:if test="${hythd.f_DJZT=='0'}">未提交</c:if>
					</font>
				</li>
				
				<li style="padding:0px 0px 0px 110px;">
					<font style="font-size: 14px;letter-spacing:0px">
						制单人:${hythd.f_BZRMC}						
					</font>
				</li>
				<c:if test="${hythd.f_DJZT=='1'}">
					<li style="padding:0px 0px 0px 110px;">
						<font style="font-size: 14px;letter-spacing:0px">
							单据提交人:${hythd.f_SBRMC}						
						</font>
					</li>
					<li style="padding:0px 0px 0px 110px;">
						<font style="font-size: 14px;letter-spacing:0px">
							单据提交日期:<fmt:formatDate value="${hythd.f_SBDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>					
						</font>
					</li>
				</c:if>	
			</ul>
			<ul>
				<!-- <li><label for="test">过滤退货数量为0记录</label><input type="checkbox" name="test" id="test" value="checkbox"></li> -->
				<c:if test="${hythd.f_DJZT=='0'}">
					<c:if test="${hythd.f_DJBH==''}">						
						<li><a class="buttonActive" target="ajaxTodo" rel="thdFormLiBox" onCheck="checkBackGoodsAjaxData()" ajaxData = "1" getAjaxData="getBackGoodsMaterialAjaxData()" ajaxData="1" callback="validateCallSelback" href="${ctx}/dailyBusiness/backGoods/createSaveBackGoodsForm?formType=2&callbackType=forward&forwardUrl=${ctx}/dailyBusiness/backGoods/reloadBackGoodsForm&pageBreak=0"><span>保存</span></a></li>					
					</c:if>	
					<c:if test="${hythd.f_DJBH!=''}">						
						<li><a class="buttonActive" target="ajaxTodo" rel="thdFormLiBox" onCheck="checkBackGoodsAjaxData()" ajaxData = "1" getAjaxData="getBackGoodsMaterialAjaxData()" showTitle="1" callback="validateCallSelback" href="${ctx}/dailyBusiness/backGoods/createSubmitBackGoodsForm?formType=2&callbackType=forward&forwardUrl=${ctx}/dailyBusiness/backGoods/reloadBackGoodsForm&pageBreak=0" title="提交退库单后，退库单明细不允许修改和删除，你确定要提交该退库单吗?"><span>提交</span></a></li>
						<li><a class="buttonActive" target="ajaxTodo" rel="thdFormLiBox" onCheck="checkBackGoodsAjaxData()" ajaxData = "1" getAjaxData="getBackGoodsMaterialAjaxData()" ajaxData="1" callback="validateCallSelback" href="${ctx}/dailyBusiness/backGoods/createSaveBackGoodsForm?formType=2&callbackType=forward&forwardUrl=${ctx}/dailyBusiness/backGoods/reloadBackGoodsForm&pageBreak=0"><span>保存</span></a></li>					
					</c:if>										
				</c:if>														
			</ul>
		</div>
	</form>
</div>