<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<title><decorator:title/><s:text name="ui.title" /></title>
<link rel="stylesheet" type="text/css" media="all" href="../styles/themes/css/juiAll.css">
<link rel="stylesheet" type="text/css" media="all" href="../styles/themes/css/common.css">
<style type="text/css">
/* suggest */
#suggest{position:absolute; z-index:2000; left:0; top:0;}
#suggest ul{list-style:none; padding:1px; margin:0; background-color:#fff; border:1px solid #999; width:150px;}
#suggest li{display:block; color:#000; padding:3px; margin:0; border:1px solid #fff; background-color:transparent; text-align:left; cursor:default;}
#suggest li.selected{border:1px solid #0a246a; background-color:#b6bdd2}
#calendar {z-index:1000}

#alertBackground { display:none; width:100%; height:100%; opacity:0.4; filter:alpha(opacity=40); background:#FFF; position:absolute; top:0; left:0; z-index:100;}
</style>
<!--[if lt IE 9]>
<script src="../styles/js/html5.js"></script>
<![endif]-->
<script type="text/javascript" src="../styles/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../styles/js/jquery.validate.js"></script>
<script type="text/javascript" src="../styles/js/jquery.imask.js"></script>
<script type="text/javascript" src="../styles/js/jui.core.js"></script>
<script type="text/javascript" src="../styles/js/jui.alertMsg.js"></script>
<script type="text/javascript" src="../styles/js/jui.drag.js"></script>  
<script type="text/javascript" src="../styles/js/jui.util.date.js"></script>
<script type="text/javascript" src="../styles/js/jui.datepicker.js"></script>
<script type="text/javascript" src="../styles/js/jui.dialog.js"></script>
<script type="text/javascript" src="../styles/js/jui.ajax.js"></script>
<script type="text/javascript" src="../styles/js/jui.ui.js"></script>
<script type="text/javascript" src="../styles/js/jui.regional.zh.js"></script>
<decorator:head/>
</head>

<body class="main">
<c:import url="/WEB-INF/jsp/management/layout/header.jsp" charEncoding="UTF-8"/>
<section id="container" class="container">
	<decorator:body/>
</section>
<c:import url="/WEB-INF/jsp/management/layout/footer.jsp" charEncoding="UTF-8"/>

</body>
</html>
