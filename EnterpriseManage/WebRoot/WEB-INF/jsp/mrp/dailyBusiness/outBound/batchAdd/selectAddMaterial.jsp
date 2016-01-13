<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../include.inc.jsp"%>

<script src="${ctx}/styles/dwz/js/jquery.json-2.2.js" type="text/javascript"></script>

<script language="javascript">

	//全选和取消全选
	function outBoundMaterialCheckAll() {
			var val1 = $("#outBoundMaterialCheckAll").attr("checked");  //全选
			var bSelect = false;
			if(val1 == 'checked') {
				 bSelect = true;
			} else {
				 bSelect = false;
			}
	
			var ttt = $("#outBoundScanningMaterialTablePanel .gridTbody table tbody");
	    	var nTds = $('tr', ttt);
	    	var i = 0;
	    	nTds.each(function () {
				$($("td :checkbox", nTds[i])).attr("checked", bSelect);  //获取真假				
				i++;
			});
	}

	function checkOutBoundBatchAddStep2() {
		var arrayObj = new Array([2]);
		var $form = $("#OutBoundBatchAddStep1");
		
		if (!$form.valid()) {
			
			$("#OutBoundBatchAddStep2 span.error").each(function(){			
				$(this).text("");	
			});	
			$("#OutBoundBatchAddStep2 span").removeClass("error"); 
			arrayObj[0] = false;
			arrayObj[1] = null;
			return arrayObj;
		}
	}

	//检查是否选择了材料信息
	function step2AddOutBoundMaterial() {
		var ttt = $("#outBoundScanningMaterialTablePanel .gridTbody table tbody");
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
							F_CKBH: "${F_CKBH}",
							F_CKMC: "${F_CKMC}",
							F_XMBH: "${F_XMBH}",
							F_XMMC: "${F_XMMC}",
							F_CLBH: $($("td", nTds[i])[2])[0].outerText,
							F_CLMC: $($("td", nTds[i])[3])[0].outerText,
							F_CSBH: $($("td", nTds[i])[4])[0].outerText,
							F_CSMC: $($("td", nTds[i])[5])[0].outerText,
							F_DWBH: $($("td", nTds[i])[6])[0].outerText,
							F_DWMC: $($("td", nTds[i])[7])[0].outerText
						};
				} else if(Sys.chrome) {
					var row = {
							F_CKBH: "${F_CKBH}",
							F_CKMC: "${F_CKMC}",
							F_XMBH: "${F_XMBH}",
							F_XMMC: "${F_XMMC}",
							F_CLBH: $($("td", nTds[i])[2])[0].textContent,
							F_CLMC: $($("td", nTds[i])[3])[0].textContent,
							F_CSBH: $($("td", nTds[i])[4])[0].textContent,
							F_CSMC: $($("td", nTds[i])[5])[0].textContent,
							F_DWBH: $($("td", nTds[i])[6])[0].textContent,
							F_DWMC: $($("td", nTds[i])[7])[0].textContent
						};
				} else {
					var row = {
							F_CKBH: "${F_CKBH}",
							F_CKMC: "${F_CKMC}",
							F_XMBH: "${F_XMBH}",
							F_XMMC: "${F_XMMC}",
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
	function getCkdStep2Event() {
		var $menuLi = $("#addOutBoundMaterialLi");
		$menuLi.append('<a class="add" id="addOutBoundMaterialA"  title="添加出库单明细" href="javaScript:void(0)"><span>添加出库单明细</span></a>');
		return $("#addOutBoundMaterialA");
	}

	function getFields() {
		var $table = $("#outBoundAddFormEntriesTable"); 
		var $tbody = $("#outBoundAddFormEntriesTable tbody");
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
				if(field.name.startsWith("F_SSCPMC")) {
					html = '<input type="hidden" name="F_SSCPBH[#index#]" value="' + row.F_CSBH + '"/>'
				         + '<input type="text" disabled="true" class="required" name="F_SSCPMC[#index#]" style="width:70%" postField="keyword" suggestFields="F_SSCPBH,F_SSCPMC" size="'+field.size+'" lookupGroup="org1" value="" class="'+field.fieldClass+'"/>'
				         + '<a class="btnLook" href="'+field.lookupUrl+'" dynamicTableConvertUrl="1" isCheck="false" width="1024" style="width:20%" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' rel="aa" lookupPk="'+field.lookupPk+'" title="'+field.title+'">'+field.title+'</a>';
				} else if(field.name.startsWith("F_YYCKMC")) {
					html = '<input type="hidden" name="F_YYCKBH[#index#]" value="' + row.F_CKBH + '"/>'
				         + '<input type="text" disabled="true" class="required" name="F_YYCKMC[#index#]" style="width:70%" postField="keyword" suggestFields="F_YYCKBH,F_YYCKMC" size="'+field.size+'" lookupGroup="org1" value="' + row.F_CKMC + '" class="'+field.fieldClass+'"/>'
				     	 + '<a class="btnLook" href="'+field.lookupUrl+'" style="width:20%" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' rel="aa" lookupPk="'+field.lookupPk+'" title="'+field.title+'">'+field.title+'</a>';
				} else if(field.name.startsWith("F_YYXMMC")) {
					html = '<input type="hidden" name="F_YYXMBH[#index#]" value="' + row.F_XMBH + '"/>'
					     + '<input type="text" disabled="true" class="required" name="F_YYXMMC[#index#]" rel="aa" style="width:70%" postField="keyword" suggestFields="F_YYXMBH,F_YYXMMC" size="'+field.size+'" lookupGroup="org1" value="' + row.F_XMMC + '" class="'+field.fieldClass+'"/>'
					     + '<a class="btnLook" href="'+field.lookupUrl+'" dynamicTableConvertUrl="1" isCheck="false" width="1024" style="width:20%" rel="aa" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' lookupPk="'+field.lookupPk+'" title="'+field.title+'">'+field.title+'</a>';
				} else if(field.name.startsWith("F_YYCPMC")) {
					html = '<input type="hidden" name="F_YYCPBH[#index#]"/>'
				     	 + '<input type="text" disabled="true" class="required" name="F_YYCPMC[#index#]" style="width:70%" postField="keyword" suggestFields="F_YYCPBH,F_YYCPMC" size="'+field.size+'" lookupGroup="org1" class="'+field.fieldClass+'"/>'
				         + '<a class="btnLook" width="1024" href="'+field.lookupUrl+'" dynamicTableConvertUrl="1" isCheck="false" warn="请选择项目信息" style="width:20%" lookupGroup="'+field.lookupGroup+'" '+suggestFrag+' rel="aa" lookupPk="'+field.lookupPk+'" title="'+field.title+'">'+field.title+'</a>';
				}	
				break;
			default:
				if(field.name.startsWith("F_CLMC")) {
					html = '<input name="F_CLBH[#index#]" value="'+row.F_CLBH+'" size="'+field.size+'" type="hidden" disabled="true" class="required"/>'
					     + '<input type="text" name="F_CLMC[#index#]" value="'+row.F_CLMC+'" size="'+field.size+'" style="width:'+field.colWidth+'" disabled="true" class="required" class="'+field.fieldClass+'" '+attrFrag+'/>';
				} else if(field.name.startsWith("F_SSCKMC")) {
					html = '<input name="F_SSCKBH[#index#]" value="'+row.F_CKBH+'" size="'+field.size+'" type="hidden" disabled="true" class="required"/>'
				         + '<input type="text" name="F_SSCKMC[#index#]" value="'+row.F_CKMC+'" size="'+field.size+'" style="width:'+field.colWidth+'" disabled="true" class="required" class="'+field.fieldClass+'" '+attrFrag+'/>';
				} else if(field.name.startsWith("F_SSXMMC")) {
					html = '<input name="F_SSXMBH[#index#]" value="'+row.F_XMBH+'" size="'+field.size+'" type="hidden" disabled="true" class="required"/>'
				         + '<input type="text" name="F_SSXMMC[#index#]" value="'+row.F_XMMC+'" size="'+field.size+'" style="width:'+field.colWidth+'" disabled="true" class="required" class="'+field.fieldClass+'" '+attrFrag+'/>';
				} else if(field.name.startsWith("F_DWMC")) {
					html = '<input name="F_DWBH[#index#]" value="'+row.F_DWBH+'" size="'+field.size+'" type="hidden" disabled="true" class="required"/>'
			             + '<input type="text" name="F_DWMC[#index#]" value="'+row.F_DWMC+'" size="'+field.size+'" style="width:'+field.colWidth+'" disabled="true" class="required" class="'+field.fieldClass+'" '+attrFrag+'/>';
				} else if(field.name.startsWith("F_CSMC")) {
					html = '<input name="F_CSBH[#index#]" value="'+row.F_CSBH+'" size="'+field.size+'" type="hidden" disabled="true" class="required"/>'
		         		 + '<input type="text" name="F_CSMC[#index#]" value="'+row.F_CSMC+'" size="'+field.size+'" style="width:'+field.colWidth+'" disabled="true" class="required" class="'+field.fieldClass+'" '+attrFrag+'/>';
				} else if(field.name.startsWith("F_CLDJ")) {
					html = '<input type="text" disabled="true" name="'+field.name+'" style="width:'+field.colWidth+'" value="'+field.defaultVal+'" size="'+field.size+'" class="'+field.fieldClass+'" min="'+field.min+'" '+attrFrag+'/>';
				}  else {
					html = '<input type="text" name="'+field.name+'" style="width:'+field.colWidth+'" value="'+field.defaultVal+'" size="'+field.size+'" class="'+field.fieldClass+'" min="'+field.min+'" '+attrFrag+'/>';
				}		
				break;
		}
		return '<td>'+html+'</td>';
	}

	function checkOutBoundMaterialAjaxData() {
		var ttt = $("#outBoundAddFormEntriesTable tbody");
    	var nTds = $('tr', ttt);
    	var arrayObj = new Array([2]);
    	var i = 0;
    	var error = "";
    	var bCheck = true;

		var $form = $("#addOutBoundScanningMaterialForm");
		
		if (!$form.valid()) {
			arrayObj[0] = false;
			arrayObj[1] = null;
			return arrayObj;
		}
		
    	nTds.each(function () {    	
        	if($($("td :hidden", nTds[i])[2]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行入库产品不能为空，请选择材料所属项目！<br>";
				bCheck = false;
        	}

        	if($($("td :hidden", nTds[i])[4]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行出库仓库不能为空，请选择材料出库仓库！<br>";
				bCheck = false;
        	}

        	if($($("td :hidden", nTds[i])[5]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行出库项目不能为空，请选择材料出库项目！<br>";
				bCheck = false;
        	}

        	if($($("td :hidden", nTds[i])[6]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行出库产品不能为空，请选择材料出库产品！<br>";
				bCheck = false;
        	}

        	if($($("td input[type=text]", nTds[i])[10]).val().length == 0) {
				error += "<br>第" + (i + 1) + "行材料出库数量不能为空，请维护材料出库数量！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[10]).val().length > 0 && !$($("td input[type=text]", nTds[i])[10]).val().isNumber()) {
        		error += "<br>第" + (i + 1) + "行材料出库数量不为数值型，请维护一个数值型出库数量！<br>";
				bCheck = false;
        	} else if($($("td input[type=text]", nTds[i])[10]).val().length > 0 && $($("td input[type=text]", nTds[i])[10]).val().isNumber()) {
            	var sl = $($("td input[type=text]", nTds[i])[10]).val();
            	if(sl <=0 ) {
            		error += "<br>第" + (i + 1) + "行材料出库数量应该大于零！<br>";
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
			arrayObj[1] = "请添加出库单明细信息！";
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
	
	function getOutBoundMaterialAjaxData() {
		var F_CHDATE = $('#selectAddMaterial_CHDATE').val();
		var ttt = $("#outBoundAddFormEntriesTable tbody");
    	var nTds = $('tr', ttt);
    	var i = 0;
    	var addRows=[];
    	var returnData="";
    	nTds.each(function () {
			var row = {
				F_KJQJ:   "${F_KJQJ}",
				F_GUID:   "${F_GUID}",
				F_FLGUID: "${F_FLGUID}",
				F_SSCKBH: $($("td :hidden", nTds[i])[0]).val(),
				F_SSXMBH: $($("td :hidden", nTds[i])[1]).val(),
				F_SSCPBH: $($("td :hidden", nTds[i])[2]).val(),
				F_CLBH:   $($("td :hidden", nTds[i])[3]).val(),
				F_YYCKBH: $($("td :hidden", nTds[i])[4]).val(),
				F_YYXMBH: $($("td :hidden", nTds[i])[5]).val(),
				F_YYCPBH: $($("td :hidden", nTds[i])[6]).val(),
				F_DWBH:   $($("td :hidden", nTds[i])[7]).val(),
				F_CSBH:   $($("td :hidden", nTds[i])[8]).val(),
				F_CLDJ:   $($("td input[type=text]", nTds[i])[9]).val(),
				F_CKSL:   $($("td input[type=text]", nTds[i])[10]).val()
			};
			addRows.push(row);
			i++;
		});
    	var row = new Object();
    	row["com.mrp.persistence.dailyBusiness.outBound.bean.HYCKDMX"] = addRows;
    	row["F_KJQJ"] = "${F_KJQJ}";
    	row["F_GUID"] = "${F_GUID}";
    	row["F_BZRID"] = "${F_LRR}";
    	row["F_CKBH"] = "${F_CKBH}";
    	row["F_XMBH"] = "${F_XMBH}";
    	row["F_GUID"] = "${F_GUID}";
    	row["F_CHDATE"] = F_CHDATE;
    	row["F_FLGUID"] = "${F_FLGUID}";
    	
    	return $.toJSON(row);
	}

	function selectOutBoundAddMaterialCallBackFun(json) {
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
		<h1>扫描出库材料基本信息</h1>
		<div>
			<div class="panel" id="outBoundScanningMaterialTablePanel">
				<table class="table" width="100%" class="required">
					<thead>
						<tr>
							<th width="25">序号</th>		
							<th width="26"><input id="outBoundMaterialCheckAll" name="outBoundMaterialCheckAll" onchange="outBoundMaterialCheckAll()" align=middle type="checkbox"></th>	
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

<form method="post" id="addOutBoundScanningMaterialForm" action="${ctx}/dailyBusiness/outBound/form/update" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabDialogAjaxDone);">	
	<h3 class="contentTitle"></h3>
	<input type="hidden" id="selectAddMaterial_CHDATE" name="selectAddMaterial_CHDATE" value="${F_CHDATE}" />
	<div class="panelBar">
		<ul class="toolBar">
			<li id="addOutBoundMaterialLi"></li>				
			<li class="line">line</li>		
		</ul>
	</div>
	<div class="pageContent">
		<div class="pageFormContent" layoutH="330">
			<div class="tabsContent" style="height: 150px;">
				<div>
					<table id="outBoundAddFormEntriesTable" class="list nowrap itemDetail" getEvent="getCkdStep2Event()" addTrHtml="step2AddOutBoundMaterial" buttonDisable="false" width="100%">
						<thead>
							<tr>
								<th type="text" name="F_SSCKMC[#index#]" size="12" fieldClass="required" colWidth="90%">入库仓库名称</th>
								<th type="text" name="F_SSXMMC[#index#]" fieldClass="required" size="12" colWidth="90%">入库项目名称</th>
								<th type="lookup" name="F_SSCPMC[#index#]" lookupUrl="${ctx}/dictHelp/material/bussinessHelp?F_CPBH=&F_CLBH={F_CLBH[#index#]}&F_XMBH={F_SSXMBH[#index#]}&F_CKBH={F_SSCKBH[#index#]}&tarRel=/mrp/dailyBusiness/outBound/xmcpByclHelp" suggestFields="F_SSCPBH,F_SSCPMC" postField="keywords" size="12" fieldClass="required">入库产品名称</th>
								<th type="text" name="F_CLMC[#index#]" fieldClass="required" size="12" colWidth="90%">材料名称</th>
								<th type="lookup" name="F_YYCKMC[#index#]" lookupUrl="${ctx}/dailyBusiness/outBound/deportHelp" size="12" fieldClass="required" colWidth="90%">出库仓库名称</th>
								<th type="lookup" name="F_YYXMMC[#index#]" lookupUrl="${ctx}/dictHelp/material/bussinessHelp?F_CPBH=&F_CLBH={F_CLBH[#index#]}&F_XMBH=&F_CKBH={F_YYCKBH[#index#]}&tarRel=/mrp/dailyBusiness/outBound/xmcpByclHelp" suggestFields="F_XMBH,F_XMMC" postField="keywords" size="12" fieldClass="required">出库项目名称</th>
								<th type="lookup" name="F_YYCPMC[#index#]" lookupUrl="${ctx}/dictHelp/material/bussinessHelp?F_CPBH=&F_CLBH={F_CLBH[#index#]}&F_XMBH={F_YYXMBH[#index#]}&F_CKBH={F_YYCKBH[#index#]}&tarRel=/mrp/dailyBusiness/outBound/xmcpByclHelp" warn="请选择出库材料所属项目" suggestFields="F_YYCPBH,F_YYCPMC" postField="keywords" size="12" fieldClass="required">出库产品名称</th>
								<th type="text" name="F_DWMC[#index#]" fieldClass="required" size="12" colWidth="90%">供应商名称</th>
								<th type="text" name="F_CSMC[#index#]" fieldClass="required" size="12" colWidth="90%">厂商名称</th>																	
								<th type="text" name="F_CLDJ[#index#]" defaultVal="1" size="12" fieldClass="number" min=0 colWidth="90%">材料单价</th>					
								<th type="text" name="F_CKSL[#index#]" defaultVal="1" size="12" fieldClass="number" min=0 colWidth="90%">出库数量</th>
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
			<li><a class="buttonActive" target="ajaxTodo" onCheck="checkOutBoundMaterialAjaxData()" rel="rkdFormLiBox" ajaxData = "1" getAjaxData="getOutBoundMaterialAjaxData()" callback="selectOutBoundAddMaterialCallBackFun" href="${ctx}/dailyBusiness/form/batchAdd/outBound/saveSelectMaterial?F_NAVTAB=${F_NAVTAB}&formType=2&forwardUrl=${ctx}/dailyBusiness/outBound/form/edit"><span>提交</span></a></li>								
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">退出</button></div></div></li>
		</ul>
	</div>
</form>