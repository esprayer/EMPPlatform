<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../include.inc.jsp"%>

<script src="${ctx}/styles/dwz/js/jquery.json-2.2.js" type="text/javascript"></script>

<script language="javascript">

	//全选和取消全选
	function transfersMaterialCheckAll() {
			var val1 = $("#transfersMaterialCheckAll").attr("checked");  //全选
			var bSelect = false;
			if(val1 == 'checked') {
				 bSelect = true;
			} else {
				 bSelect = false;
			}
	
			var ttt = $("#transfersScanningMaterialTablePanel .gridTbody table tbody");
	    	var nTds = $('tr', ttt);
	    	var i = 0;
	    	nTds.each(function () {
				$($("td :checkbox", nTds[i])).attr("checked", bSelect);  //获取真假				
				i++;
			});
	}

	function checkTransfersBatchAddStep2() {
		var arrayObj = new Array([2]);
		var $form = $("#TransfersBatchAddStep1");
		
		if (!$form.valid()) {
			
			$("#TransfersBatchAddStep2 span.error").each(function(){			
				$(this).text("");	
			});	
			$("#TransfersBatchAddStep2 span").removeClass("error"); 
			arrayObj[0] = false;
			arrayObj[1] = null;
			return arrayObj;
		}
	}

	//检查是否选择了材料信息
	function step2AddTransfersMaterial() {
		var ttt = $("#transfersScanningMaterialTablePanel .gridTbody table tbody");
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
							F_DWMC: $($("td", nTds[i])[7])[0].outerText,
							F_CLDJ: $($("td", nTds[i])[8])[0].outerText,
							F_CLSL: $($("td", nTds[i])[9])[0].outerText
						};
				} else if(Sys.chrome) {
					var row = {
							F_CLBH: $($("td", nTds[i])[2])[0].textContent,
							F_CLMC: $($("td", nTds[i])[3])[0].textContent,
							F_CSBH: $($("td", nTds[i])[4])[0].textContent,
							F_CSMC: $($("td", nTds[i])[5])[0].textContent,
							F_DWBH: $($("td", nTds[i])[6])[0].textContent,
							F_DWMC: $($("td", nTds[i])[7])[0].textContent,
							F_CLDJ: $($("td", nTds[i])[8])[0].textContent,
							F_CLSL: $($("td", nTds[i])[9])[0].textContent
						};
				} else {
					var row = {
							F_CLBH: $($("td", nTds[i])[2])[0].textContent,
							F_CLMC: $($("td", nTds[i])[3])[0].textContent,
							F_CSBH: $($("td", nTds[i])[4])[0].textContent,
							F_CSMC: $($("td", nTds[i])[5])[0].textContent,
							F_DWBH: $($("td", nTds[i])[6])[0].textContent,
							F_DWMC: $($("td", nTds[i])[7])[0].textContent,
							F_CLDJ: $($("td", nTds[i])[8])[0].textContent,
							F_CLSL: $($("td", nTds[i])[9])[0].textContent
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
		$(addRows).each(function(){
			var row = $(this)[0];
			html = '';
			$(fields).each(function(){
				html += addTdHtml(row, this);
			});
			trHtmls += '<tr class="unitBox">'+html+'</tr>';
		});
		
		return trHtmls;
	}
	function getDbdStep2Event() {
		var $menuLi = $("#addMaterialLi");
		$menuLi.append('<a class="add" id="addTransfersMaterialA"  title="添加调拨单明细" href="javaScript:void(0)"><span>添加调拨单明细</span></a>');
		return $("#addTransfersMaterialA");
	}

	function getFields() {
		var $table = $("#transfersAddFormEntriesTable"); 
		var $tbody = $("#transfersAddFormEntriesTable tbody");
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

	function addDbkTrHtmlStep2(trTm) {
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
				     + '<input type="text" disabled="true" class="required" name="F_CSMC[#index#]" style="width:78%" size="'+field.size+'" value="' + row.F_CSMC + '" class="'+field.fieldClass+'"/>';
				} else if(field.name.startsWith("F_DWMC")) {
					html = '<input type="hidden" name="F_DWBH[#index#]" value="' + row.F_DWBH + '"/>'
				     + '<input type="text" disabled="true" class="required" name="F_DWMC[#index#]" style="width:78%" size="'+field.size+'" value="' + row.F_DWMC + '" class="'+field.fieldClass+'"/>';
				} else if(field.name.startsWith("F_XMMC")) {
					html = '<input type="hidden" name="F_XMBH[#index#]"/>'
					     + '<input type="text" disabled="true" class="required" name="F_XMMC[#index#]" rel="aa" style="width:78%" postField="keyword" suggestFields="F_XMBH,F_XMMC" size="'+field.size+'" lookupGroup="org1" class="'+field.fieldClass+'"/>'
					     + '<a class="btnLook" href="'+field.lookupUrl+'" dynamicTableConvertUrl="1" isCheck="false" style="width:15%" rel="aa" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' lookupPk="'+field.lookupPk+'" title="'+field.title+'">'+field.title+'</a>';
				} else if(field.name.startsWith("F_CPMC")) {
					html = '<input type="hidden" name="F_CPBH[#index#]" value=""/>'
				     	 + '<input type="text" disabled="true" class="required" name="F_CPMC[#index#]" style="width:78%" postField="keyword" suggestFields="F_CPBH,F_CPMC" size="'+field.size+'" lookupGroup="org1" class="'+field.fieldClass+'" value=""/>'
				         + '<a class="btnLook" href="'+field.lookupUrl+'" dynamicTableConvertUrl="1" isCheck="true" warn="请选择项目信息" style="width:15%" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' rel="aa" lookupPk="'+field.lookupPk+'" title="'+field.title+'">'+field.title+'</a>';
				}	
				break;
			default:
				if(field.name.startsWith("F_CLMC")) {
					html = '<input name="F_CLBH[#index#]" value="'+row.F_CLBH+'" size="'+field.size+'" type="hidden" disabled="true" class="required"/>'
					     + '<input type="text" name="F_CLMC[#index#]" value="'+row.F_CLMC+'" size="'+field.size+'" style="width:'+field.colWidth+'" disabled="true" class="required" class="'+field.fieldClass+'" '+attrFrag+'/>';
				} else if(field.name.startsWith("F_CLDJ")) {
					html = '<input type="text" disabled="true" name="'+field.name+'" style="width:'+field.colWidth+'" value="'+row.F_CLDJ+'" size="'+field.size+'" class="'+field.fieldClass+'" min="'+field.min+'" '+attrFrag+'/>';
				} else {
					html = '<input type="text" name="'+field.name+'" style="width:'+field.colWidth+'" value="'+row.F_CLSL+'" size="'+field.size+'" class="'+field.fieldClass+'" min="'+field.min+'" '+attrFrag+'/>';
				}	
				break;
		}
		return '<td>'+html+'</td>';
	}

	function checkTransfersMaterialAjaxData() {
		var ttt = $("#transfersAddFormEntriesTable tbody");
    	var nTds = $('tr', ttt);
    	var arrayObj = new Array([2]);
    	var i = 0;
    	var error = "";
    	var bCheck = true;

		var $form = $("#addTransfersScanningMaterialForm");
		
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
				error += "<br>第" + (i + 1) + "行材料单价不能为空，请维护材料入库单价！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[5]).val().length > 0 && !$($("td input[type=text]", nTds[i])[5]).val().isNumber()) {
        		error += "<br>第" + (i + 1) + "行材料单价不为数值型，请维护一个数值型单价！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[5]).val().length > 0 && $($("td input[type=text]", nTds[i])[5]).val().isNumber()) {
            	var dj = $($("td input[type=text]", nTds[i])[5]).val();
            	if(dj <=0 ) {
            		error += "<br>第" + (i + 1) + "行材料单价应该大于等于零！<br>";
    				bCheck = false;
            	}
        	}

        	if($($("td input[type=text]", nTds[i])[6]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行材料调拨数量不能为空，请维护材料入库数量！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[6]).val().length > 0 && !$($("td input[type=text]", nTds[i])[6]).val().isNumber()) {
        		error += "<br>第" + (i + 1) + "行材料调拨数量不为数值型，请维护一个数值型数量！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[6]).val().length > 0 && $($("td input[type=text]", nTds[i])[6]).val().isNumber()) {
            	var sl = $($("td input[type=text]", nTds[i])[6]).val();
            	if(sl <=0 ) {
            		error += "<br>第" + (i + 1) + "行材料调拨数量应该大于等于零！<br>";
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
			arrayObj[1] = "请添加调拨单明细信息！";
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
	
	function getTransfersMaterialAjaxData() {
		var F_CHDATE = $('#selectAddMaterial_CHDATE').val();
		var ttt = $("#transfersAddFormEntriesTable tbody");
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
				F_CLDJ:   $($("td input[type=text]", nTds[i])[5]).val(),
				F_CLSL:   $($("td input[type=text]", nTds[i])[6]).val(),
				F_CKBH:   "${F_CKBH}"
			};
			addRows.push(row);
			i++;
		});
    	var row = new Object();
    	row["com.mrp.persistence.dailyBusiness.transfers.bean.HYDBDMX"] = addRows;
    	row["F_KJQJ"] = "${F_KJQJ}";
    	row["F_GUID"] = "${F_GUID}";
    	row["F_BZRID"] = "${F_LRRID}";
    	row["F_CKBH"] = "${F_CKBH}";
    	row["F_GUID"] = "${F_GUID}";
    	row["F_CHDATE"] = F_CHDATE;
    	row["F_FLGUID"] = "${F_FLGUID}";
    	
    	return $.toJSON(row);
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
			<div class="panel" id="transfersScanningMaterialTablePanel">
				<table class="table" width="100%" class="required">
					<thead>
						<tr>
							<th width="25">序号</th>		
							<th width="26"><input id="transfersMaterialCheckAll" name="transfersMaterialCheckAll" onchange="transfersMaterialCheckAll()" align=middle type="checkbox"></th>	
							<th width="60">材料编号</th>
							<th width="60">材料名称</th>
							<th width="60">厂商编号</th>
							<th width="60">厂商名称</th>
							<th width="60">供应商编号</th>
							<th width="60">供应商名称</th>
							<th width="60">材料单价</th>
							<th width="60">材料数量</th>								
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
							<td>${item.F_CLDJ}</td>	
							<td>${item.F_CLSL}</td>
						</tr>	
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<form method="post" id="addTransfersScanningMaterialForm" action="${ctx}/dailyBusiness/transfers/form/update" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabDialogAjaxDone);">	
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
					<table id="transfersAddFormEntriesTable" class="list nowrap itemDetail" getEvent="getDbdStep2Event()" addTrHtml="step2AddTransfersMaterial" buttonDisable="false" width="100%">
						<thead>
							<tr>
								<th type="text" name="F_CLMC[#index#]" size="12" fieldClass="required" colWidth="90%">材料名称</th>
								<th type="lookup" name="F_XMMC[#index#]" lookupUrl="${ctx}/dictHelp/product/project/help?F_CLBH={F_CLBH[#index#]}&F_XMBH=&F_CPBH={F_CPBH[#index#]}" suggestFields="F_XMBH,F_XMMC" postField="keywords" size="12" fieldClass="required">项目名称</th>
								<th type="lookup" name="F_CPMC[#index#]" lookupUrl="${ctx}/sysConfigure/product/help/{F_XMBH[#index#]}" suggestFields="orgName" postField="keywords" size="12" fieldClass="required">产品名称</th>
								<th type="lookup" name="F_CSMC[#index#]" lookupUrl="${ctx}/dictHelp/company/help?F_CSBH=&F_SYZT=1&tarRel=help/dictHelp/csHelp" suggestFields="orgName" postField="keywords" size="12" fieldClass="required">厂商名称</th>
								<th type="lookup" name="F_DWMC[#index#]" lookupUrl="${ctx}/sysConfigure/material/suppliers" suggestFields="orgName" postField="keywords" size="12" fieldClass="required">供应商名称</th>																	
								<th type="text" name="F_CLDJ[#index#]" defaultVal="1" size="12" fieldClass="number" min=0 colWidth="90%">材料单价</th>					
								<th type="text" name="F_CLSL[#index#]" defaultVal="1" size="12" fieldClass="number" min=0 colWidth="90%">调拨数量</th>
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
			<li><a class="buttonActive" target="ajaxTodo" onCheck="checkTransfersMaterialAjaxData()" rel="rkdFormLiBox" ajaxData = "1" getAjaxData="getTransfersMaterialAjaxData()" callback="selectAddMaterialCallBackFun" href="${ctx}/dailyBusiness/form/batchAdd/transfers/saveSelectMaterial?F_NAVTAB=${F_NAVTAB}&formType=2&forwardUrl=${ctx}/dailyBusiness/transfers/form/editForm"><span>提交</span></a></li>								
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">退出</button></div></div></li>
		</ul>
	</div>
</form>