<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include.inc.jsp"%>
<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" media="screen" href="../resources/js/jqgrid/themes/redmond/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../resources/js/jqgrid/themes/ui.jqgrid.css" /> 

<script src="../resources/js/jqgrid/jquery-1.8.2.min.js" ></script>
<script src="../resources/js/jqgrid/i18n/grid.locale-en.js" type="text/javascript"></script>  

<script src="../resources/js/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>  
<style type="text/css">
.tdRightAlign{
text-align:right;
width:7%;
}
.tdLeftAlign{
text-align:left;
text-decoration:underline;
width:10%;
}
.column1 {
text-align:right;
	width:70px;
	overflow:hidden;
}
.column2 {
	text-align:left;
	width:180px;
	overflow:hidden;
	text-decoration:underline;
}
.column3 {
text-align:right;
	width:70px;
	overflow:hidden;
}
.column4 {
	text-align:left;
	width:80px;
	overflow:hidden;
	text-decoration:underline;
}
.column5 {
	text-align:right;
	width:90px;
	overflow:hidden;
}
.column6 {
	text-align:left;
	width:120px;
	overflow:hidden;
	text-decoration:underline;
}
</style>

<script>
jQuery(document).ready(function(){
	alert("fafds");
	initGrid();

	function initGrid(){
		var mydata = [{"F_JFGSBMBH":"12222","F_JFGSBMMC":"12222","F_ZCXMBH":"3333","F_ZCXMMC":"dkdkdkkdkd"},{"F_JFGSBMBH":"12222","F_JFGSBMMC":"12222","F_ZCXMBH":"3333","F_ZCXMMC":"dkdkdkkdkd"}];
		alert("ccccc");
		jQuery("#listMx").jqGrid({
			alert("bbbb");
		});
		
	}
});
</script>	
 
					<table id="listMx"></table>
