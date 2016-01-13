<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../include.inc.jsp"%>

<script src="${ctx}/styles/dwz/js/jquery.json-2.2.js" type="text/javascript"></script>

<script language="javascript">

	//全选和取消全选
	function storageMaterialCheckAll() {
			var val1 = $("#storageMaterialCheckAll").attr("checked");  //全选
			var bSelect = false;
			if(val1 == 'checked') {
				 bSelect = true;
			} else {
				 bSelect = false;
			}
	
			var ttt = $("#storageScanningMaterialTablePanel .gridTbody table tbody");
	    	var nTds = $('tr', ttt);
	    	var i = 0;
	    	nTds.each(function () {
				$($("td :checkbox", nTds[i])).attr("checked", bSelect);  //获取真假				
				i++;
			});
	}

	function checkStorageBatchAddStep2() {
		var arrayObj = new Array([2]);
		var $form = $("#StorageBatchAddStep1");
		
		if (!$form.valid()) {
			
			$("#StorageBatchAddStep2 span.error").each(function(){			
			$(this).text("");	
			});	
			$("#StorageBatchAddStep2 span").removeClass("error"); 
			arrayObj[0] = false;
			arrayObj[1] = null;
			return arrayObj;
		}
	}

	//检查是否选择了材料信息
	function step2AddStorageMaterial() {
		var ttt = $("#storageScanningMaterialTablePanel .gridTbody table tbody");
    	var nTds = $('tr', ttt);
    	var i = 0;
    	var addRows=[];
    	var returnData=[];
    	var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
        if (window.ActiveXObject)
            Sys.ie = ua.match(/msie ([\d.]+)/)[1];
        else if (document.getBoxObjectFor)
            Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1];
        else if (window.MessageEvent && !document.getBoxObjectFor)
            Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1];
        else if (window.opera)
            Sys.opera = ua.match(/opera.([\d.]+)/)[1];
        else if (window.openDatabase)
            Sys.safari = ua.match(/version\/([\d.]+)/)[1];
    	nTds.each(function () {
			var val1 = $($("td :checkbox", nTds[i])).attr("checked");  //获取真假
			if(val1 == 'checked') {
				if(Sys.ie) {
					var row = {
							F_CLBH: $($("td", nTds[i])[2])[0].outerText,
							F_CLMC: $($("td", nTds[i])[3])[0].outerText,
							F_CSBH: $($("td", nTds[i])[4])[0].outerText,
							F_CSMC: $($("td", nTds[i])[5])[0].outerText,
							F_DWBH: $($("td", nTds[i])[6])[0].outerText,
							F_DWMC: $($("td", nTds[i])[7])[0].outerText
						};
				} else if(Sys.chrome) {
					var row = {
							F_CLBH: $($("td", nTds[i])[2])[0].textContent,
							F_CLMC: $($("td", nTds[i])[3])[0].textContent,
							F_CSBH: $($("td", nTds[i])[4])[0].textContent,
							F_CSMC: $($("td", nTds[i])[5])[0].textContent,
							F_DWBH: $($("td", nTds[i])[6])[0].textContent,
							F_DWMC: $($("td", nTds[i])[7])[0].textContent
						};
				} else {
					var row = {
							F_CLBH: $($("td", nTds[i])[2])[0].textContent,
							F_CLMC: $($("td", nTds[i])[3])[0].textContent,
							F_CSBH: $($("td", nTds[i])[4])[0].textContent,
							F_CSMC: $($("td", nTds[i])[5])[0].textContent,
							F_DWBH: $($("td", nTds[i])[6])[0].textContent,
							F_DWMC: $($("td", nTds[i])[7])[0].textContent
						};
				}
				addRows.push(row);
			}
			i++;
		});
		
		if(addRows.length == 0) {
			alert("请选择需要添加的材料信息！");
			return false;
		}

		var fields=getFields();
		trHtml = addTrHtml(addRows, fields);
		returnData.data=trHtml;
		returnData.rowNum=1;
		return returnData;
	}

	function addTrHtml(addRows, fields){
		var html = '';
		var trHtmls = '';
		for(var i = 0; i < addRows.length; i++) {
			var row = addRows[i];
			
			html = '';
			$(fields).each(function(){
				html += addTdHtml(row, this);
			});
			trHtmls += '<tr class="unitBox">'+html+'</tr>';
		}
		return trHtmls;
	}
	function getRkdStep2Event() {
		var $menuLi = $("#addMaterialLi");
		$menuLi.append('<a class="add" id="addStorageMaterialA"  title="添加入库单明细" href="javaScript:void(0)"><span>添加入库单明细</span></a>');
		return $("#addStorageMaterialA");
	}

	function getFields() {
		var $table = $("#storageAddFormEntriesTable"); 
		var $tbody = $("#storageAddFormEntriesTable tbody");
		var fields=[];
		$table.find("tr:first th[type]").each(function(i){
			var $th = $(this);
			var field = {
				type: $th.attr("type") || "text",
				title: $th.attr("title") || "查找带回",
				patternDate: $th.attr("dateFmt") || "yyyy-MM-dd",
				name: $th.attr("name") || "",
				defaultVal: $th.attr("defaultVal") || "",
				size: $th.attr("size") || "12",
				enumUrl: $th.attr("enumUrl") || "",
				lookupGroup: $th.attr("lookupGroup") || "",
				lookupUrl: $th.attr("lookupUrl") || "",
				lookupPk: $th.attr("lookupPk") || "id",
				suggestUrl: $th.attr("suggestUrl"),
				suggestFields: $th.attr("suggestFields"),
				postField: $th.attr("postField") || "",
				fieldClass: $th.attr("fieldClass") || "",
				min: $th.attr("min") || "",
				fieldAttrs: $th.attr("fieldAttrs") || "",
				colWidth:$th.attr("colWidth") || "0"
			};
			fields.push(field);
		});
		return fields;
	}

	function addRdkTrHtmlStep2(trTm) {
		var html = '';
		var fields = getFields();
		var row;
		$(fields).each(function(){
			html += tdHtml(this);
		});
		return '<tr class="unitBox">'+html+'</tr>';
	}

	function addTdHtml(row, field){
		var html = '', suffix = '';
		
		if (field.name.endsWith("[#index#]")) suffix = "[#index#]";
		else if (field.name.endsWith("[]")) suffix = "[]";
		
		var suffixFrag = suffix ? ' suffix="' + suffix + '" ' : '';
		
		var attrFrag = '';
		if (field.fieldAttrs){
			var attrs = DWZ.jsonEval(field.fieldAttrs);
			for (var key in attrs) {
				attrFrag += key+'="'+attrs[key]+'"';
			}
		}
		switch(field.type){
			case 'del':
				html = '<a href="javascript:void(0)" class="btnDel '+ field.fieldClass + '">删除</a>';
				break;
			case 'lookup':
				var suggestFrag = '';
				if (field.suggestFields) {
					suggestFrag = 'autocomplete="off" lookupGroup="'+field.lookupGroup+'"'+suffixFrag+' suggestUrl="'+field.suggestUrl+'" suggestFields="'+field.suggestFields+'"' + ' postField="'+field.postField+'"';
				}
				if(field.name.startsWith("F_CSMC")) {
					html = '<input type="hidden" name="F_CSBH[#index#]" value="' + row.F_CSBH + '"/>'
				     + '<input type="text" disabled="true" class="required" name="F_CSMC[#index#]" style="width:78%" postField="keyword" suggestFields="F_CSBH,F_CSMC" size="'+field.size+'" lookupGroup="org1" value="' + row.F_CSMC + '" class="'+field.fieldClass+'"/>'
				     + '<a class="btnLook" href="'+field.lookupUrl+'" style="width:15%" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' rel="aa" lookupPk="'+field.lookupPk+'" title="'+field.title+'">'+field.title+'</a>';
				} else if(field.name.startsWith("F_DWMC")) {
					html = '<input type="hidden" name="F_DWBH[#index#]" value="' + row.F_DWBH + '"/>'
				     + '<input type="text" disabled="true" class="required" name="F_DWMC[#index#]" style="width:78%" postField="keyword" suggestFields="F_DWBH,F_DWMC" size="'+field.size+'" lookupGroup="org1" value="' + row.F_DWMC + '" class="'+field.fieldClass+'"/>'
				     + '<a class="btnLook" href="'+field.lookupUrl+'" style="width:15%" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' rel="aa" lookupPk="'+field.lookupPk+'" title="'+field.title+'">'+field.title+'</a>';
				} else if(field.name.startsWith("F_XMMC")) {
					html = '<input type="hidden" name="F_XMBH[#index#]"/>'
					     + '<input type="text" disabled="true" class="required" name="F_XMMC[#index#]" rel="aa" style="width:78%" postField="keyword" suggestFields="F_XMBH,F_XMMC" size="'+field.size+'" lookupGroup="org1" class="'+field.fieldClass+'"/>'
					     + '<a class="btnLook" href="'+field.lookupUrl+'" style="width:15%" dynamicTableConvertUrl="1" rel="aa" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' lookupPk="'+field.lookupPk+'" title="'+field.title+'">'+field.title+'</a>';
				} else if(field.name.startsWith("F_CPMC")) {
					html = '<input type="hidden" name="F_CPBH[#index#]"/>'
				     	 + '<input type="text" disabled="true" class="required" name="F_CPMC[#index#]" style="width:78%" postField="keyword" suggestFields="F_CPBH,F_CPMC" size="'+field.size+'" lookupGroup="org1" class="'+field.fieldClass+'"/>'
				         + '<a class="btnLook" href="'+field.lookupUrl+'" dynamicTableConvertUrl="1" warn="请选择项目信息" style="width:15%" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' rel="aa" lookupPk="'+field.lookupPk+'" title="'+field.title+'">'+field.title+'</a>';
				}	
				break;
			default:
				if(field.name.startsWith("F_CLMC")) {
					html = '<input name="F_CLBH[#index#]" value="'+row.F_CLBH+'" size="'+field.size+'" type="hidden" disabled="true" class="required"/>'
					     + '<input type="text" name="F_CLMC[#index#]" value="'+row.F_CLMC+'" size="'+field.size+'" style="width:'+field.colWidth+'" disabled="true" class="required" class="'+field.fieldClass+'" '+attrFrag+'/>';
				} else {
					html = '<input type="text" name="'+field.name+'" style="width:'+field.colWidth+'" value="'+field.defaultVal+'" size="'+field.size+'" class="'+field.fieldClass+'" min="'+field.min+'" '+attrFrag+'/>';
				}		
				break;
		}
		return '<td>'+html+'</td>';
	}

	function checkStorageMaterialAjaxData() {
		var ttt = $("#storageAddFormEntriesTable tbody");
    	var nTds = $('tr', ttt);
    	var arrayObj = new Array([2]);
    	var i = 0;
    	var error = "";
    	var bCheck = true;

		var $form = $("#addStorageScanningMaterialForm");
		
		if (!$form.valid()) {
			arrayObj[0] = false;
			arrayObj[1] = null;
			return arrayObj;
		}
		
    	nTds.each(function () {    	
        	if($($("td :hidden", nTds[i])[1]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行项目不能为空，请选择材料所属项目！<br>";
				bCheck = false;
        	}

        	if($($("td :hidden", nTds[i])[2]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行产品不能为空，请选择材料所属产品！<br>";
				bCheck = false;
        	}

        	if($($("td :hidden", nTds[i])[3]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行厂商不能为空，请选择材料所属厂商！<br>";
				bCheck = false;
        	}

        	if($($("td :hidden", nTds[i])[4]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行供应商不能为空，请选择材料所属供应商！<br>";
				bCheck = false;
        	}

        	if($($("td input[type=text]", nTds[i])[5]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行货架（行）不能为空，请输入材料所在货架（行）！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[5]).val().length > 0 && !$($("td input[type=text]", nTds[i])[5]).val().isInteger()) {
        		error += "<br>第" + (i + 1) + "行货架（行）请维护一个整数！<br>";
				bCheck = false;
        	}

        	if($($("td input[type=text]", nTds[i])[6]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行货架（列）不能为空，请输入材料所在货架（列）！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[6]).val().length > 0 && !$($("td input[type=text]", nTds[i])[6]).val().isInteger()) {
        		error += "<br>第" + (i + 1) + "行货架（列）请维护一个整数！<br>";
				bCheck = false;
        	}

        	if($($("td input[type=text]", nTds[i])[7]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行材料单价不能为空，请维护材料入库单价！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[7]).val().length > 0 && !$($("td input[type=text]", nTds[i])[7]).val().isNumber()) {
        		error += "<br>第" + (i + 1) + "行材料单价不为数值型，请维护一个数值型单价！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[7]).val().length > 0 && $($("td input[type=text]", nTds[i])[7]).val().isNumber()) {
            	var dj = $($("td input[type=text]", nTds[i])[7]).val();
            	if(dj <=0 ) {
            		error += "<br>第" + (i + 1) + "行材料单价应该大于等于零！<br>";
    				bCheck = false;
            	}
        	}

        	if($($("td input[type=text]", nTds[i])[8]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行材料入库数量不能为空，请维护材料入库数量！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[8]).val().length > 0 && !$($("td input[type=text]", nTds[i])[8]).val().isNumber()) {
        		error += "<br>第" + (i + 1) + "行材料单价不为数值型，请维护一个数值型单价！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[8]).val().length > 0 && $($("td input[type=text]", nTds[i])[8]).val().isNumber()) {
            	var sl = $($("td input[type=text]", nTds[i])[8]).val();
            	if(sl <=0 ) {
            		error += "<br>第" + (i + 1) + "行材料单价应该大于等于零！<br>";
    				bCheck = false;
            	}
        	}
			i++;
			if(!bCheck) {
				return false;
			}	
		});

		if(i == 0) {
			arrayObj[0] = false;
			arrayObj[1] = "请添加入库单明细信息！";
			return arrayObj;
		}
		if(!bCheck) {
			arrayObj[0] = false;
			arrayObj[1] = error;
			return arrayObj;
		} else {
			arrayObj[0] = true;
			arrayObj[1] = null;
			return arrayObj;
		}
	}
	
	function getStorageMaterialAjaxData() {
		var F_CHDATE = $('#selectAddMaterial_CHDATE').val();
		var ttt = $("#storageAddFormEntriesTable tbody");
    	var nTds = $('tr', ttt);
    	var i = 0;
    	var addRows=[];
    	var returnData="";
    	nTds.each(function () {
			var row = {
				F_KJQJ:   "${F_KJQJ}",
				F_GUID:   "${F_GUID}",
				F_FLGUID: "${F_FLGUID}",
				F_CLBH:   $($("td :hidden", nTds[i])[0]).val(),
				F_XMBH:   $($("td :hidden", nTds[i])[1]).val(),
				F_CPBH:   $($("td :hidden", nTds[i])[2]).val(),
				F_CSBH:   $($("td :hidden", nTds[i])[3]).val(),
				F_DWBH:   $($("td :hidden", nTds[i])[4]).val(),
				F_ROW:    $($("td input[type=text]", nTds[i])[5]).val(),
				F_COL:    $($("td input[type=text]", nTds[i])[6]).val(),
				F_CLDJ:   $($("td input[type=text]", nTds[i])[7]).val(),
				F_RKSL:   $($("td input[type=text]", nTds[i])[8]).val()
			};
			addRows.push(row);
			i++;
		});
    	var row = new Object();
    	row["com.mrp.persistence.dailyBusiness.storage.bean.HYRKDMX"] = addRows;
    	row["F_KJQJ"] = "${F_KJQJ}";
    	row["F_GUID"] = "${F_GUID}";
    	row["F_BZRID"] = "${F_LRR}";
    	row["F_RKLX"] = "${F_RKLX}";
    	row["F_CKBH"] = "${F_CKBH}";
    	row["F_GUID"] = "${F_GUID}";
    	row["F_CHDATE"] = F_CHDATE;
    	row["F_FLGUID"] = "${F_FLGUID}";
    	return $.toJSON(row);
		//return DWZ.obj2json(row);
	}

	function selectAddMaterialCallBackFun(json) {
		DWZ.ajaxDone(json);
		
		if (json.statusCode == DWZ.statusCode.ok) {
			if (json.navTabId) {			
				$('#selectAddMaterial_CHDATE').val(json.F_CHDATE);
				navTab.openTab(json.navTabId, json.forwardUrl, {
					title: json.title,
					fresh: true,
					data:json,
					external: false
				});
			}
		}
	}
</script>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
	.span.error{left:98px}
</style>

<div class="pageFormContent" style="border:0">
	<div class="panel" defH="180">
		<h1>扫描入库材料基本信息</h1>
		<div>
			<div class="panel" id="storageScanningMaterialTablePanel">
				<table class="table" width="100%" class="required">
					<thead>
						<tr>
							<th width="25">序号</th>		
							<th width="26"><input id="storageMaterialCheckAll" name="storageMaterialCheckAll" onchange="storageMaterialCheckAll()" align=middle type="checkbox"></th>	
							<th width="60">材料编号</th>
							<th width="60">材料名称</th>
							<th width="60">厂商编号</th>
							<th width="60">厂商名称</th>
							<th width="60">供应商编号</th>
							<th width="60">供应商名称</th>
							<th width="60">规格型号</th>
							<th width="60">计量单位</th>								
						</tr>
					</thead>
					<tbody>
					<c:forEach var="item" items="${clList}" varStatus="s">
						<tr target="slt_objId" rel="${item.F_CLBH}">
							<td>${s.index + 1}</td>
							<td style="cursor:pointer" align=center>	
								<input style="width: 20px" name="ids" value="${item.f_FOLDERID}" type="checkbox">
								<input type="hidden" id="folderId" name="folderId" value="F_CLBH:" />
							</td>
							<td>${item.F_CLBH}</td>	
							<td>${item.F_CLMC}</td>	
							<td>${item.F_CSBH}</td>	
							<td>${item.F_CSMC}</td>	
							<td>${item.F_DWBH}</td>	
							<td>${item.F_DWMC}</td>	
							<td>${item.F_GGXH}</td>	
							<td>${item.F_JLDW}</td>
						</tr>	
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<form method="post" id="addStorageScanningMaterialForm" action="${ctx}/dailyBusiness/storage/form/update" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabDialogAjaxDone);">	
	<h3 class="contentTitle"></h3>
	<input type="hidden" id="selectAddMaterial_CHDATE" name="selectAddMaterial_CHDATE" value="${F_CHDATE}" />
	<div class="panelBar">
		<ul class="toolBar">
			<li id="addMaterialLi"></li>				
			<li class="line">line</li>		
		</ul>
	</div>
	<div class="pageContent">
		<div class="pageFormContent" layoutH="330">
			<div class="tabsContent" style="height: 150px;">
				<div>
					<table id="storageAddFormEntriesTable" class="list nowrap itemDetail" getEvent="getRkdStep2Event()" addTrHtml="step2AddStorageMaterial" buttonDisable="false" width="100%">
						<thead>
							<tr>
								<th type="text" name="F_CLMC[#index#]" size="12" fieldClass="required" colWidth="90%">材料名称</th>
								<th type="lookup" name="F_XMMC[#index#]" lookupUrl="${ctx}/storageDailyBusiness/projectHelp?F_CLBH={F_CLBH[#index#]}&F_XMBH=" suggestFields="F_XMBH,F_XMMC" postField="keywords" size="12" fieldClass="required">项目名称</th>
								<th type="lookup" name="F_CPMC[#index#]" lookupUrl="${ctx}/sysConfigure/project/product/help?F_XMBH={F_XMBH[#index#]}" suggestFields="orgName" postField="keywords" size="12" fieldClass="required">产品名称</th>
								<th type="lookup" name="F_CSMC[#index#]" lookupUrl="${ctx}/dictHelp/company/help?F_CSBH=&F_SYZT=1&tarRel=help/dictHelp/csHelp" suggestFields="orgName" postField="keywords" size="12" fieldClass="required">厂商名称</th>
								<th type="lookup" name="F_DWMC[#index#]" lookupUrl="${ctx}/sysConfigure/material/suppliers" suggestFields="orgName" postField="keywords" size="12" fieldClass="required">供应商名称</th>									
								<th type="text" name="F_ROW[#index#]" defaultVal="#index#" size="12" fieldClass="number" min=0 colWidth="90%">货架（行）</th>
								<th type="text" name="F_COL[#index#]" defaultVal="#index#" size="12" fieldClass="number" min=0 colWidth="90%">货架（列）</th>
								<th type="text" name="F_CLDJ[#index#]" defaultVal="1" size="12" fieldClass="number" min=0 colWidth="90%">材料单价</th>					
								<th type="text" name="F_RKSL[#index#]" defaultVal="1" size="12" fieldClass="number" min=0 colWidth="90%">入库数量</th>
								<th type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<div class="formBar">
		<ul>					
			<li><a class="buttonActive" target="ajaxTodo" onCheck="checkStorageMaterialAjaxData()" rel="rkdFormLiBox" ajaxData = "1" getAjaxData="getStorageMaterialAjaxData()" callback="selectAddMaterialCallBackFun" href="${ctx}/dailyBusiness/form/batchAdd/storage/saveSelectMaterial?F_NAVTAB=${F_NAVTAB}&formType=2&forwardUrl=${ctx}/dailyBusiness/storage/form/edit"><span>提交</span></a></li>								
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">退出</button></div></div></li>
		</ul>
	</div>
</form>