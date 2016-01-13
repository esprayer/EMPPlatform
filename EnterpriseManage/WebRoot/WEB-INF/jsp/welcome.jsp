<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<script src="js/jquery-ui-1.8.1.custom/js/jquery-1.4.2.min.js" type="text/javascript"></script> 
<script src="js/jquery-ui-1.8.1.custom/js/jquery.tools.min.js" type="text/javascript"></script> 
<title>应用服务列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style type="text/css">
<!--
body {
	font-size:14px;a
	margin-left: 0px;
	margin-top: 120px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(img/login/bg.gif);
}
a.link{
	font-size: 14px;
	color: #0A6CAB;
	text-decoration: none;
	letter-spacing:1px;
	}
a.link:link {
	font-size: 14px;
	color: #0A6CAB;
	letter-spacing:1px;

	}
a.link:visited {
	font-size: 14px;
	color: #0A6CAB;
	letter-spacing:1px;
}
a.link:hover {
	font-size: 14px;
	color: #ff0000;
	letter-spacing:1px;
}
-->
.tooltip {
	display:none;
	background:transparent url(/tools/img/tooltip/black_arrow.png);
	font-size:12px;
	height:70px;
	width:160px;
	padding:25px;
	color:#fff;	
}
a {
 cursor: pointer;
}
-->
</style>
	<script type="text/javascript">
	$(function() {
      $("#cwgl").tooltip();	
	});
	</script>
	
	<script language="javascript">
		function goToApp(url, id) {
			var tmp=window.open(url, id, 'toolbar=no, menubar=no,location=no');
			tmp.moveTo(0,0);
			tmp.resizeTo(screen.width,screen.height);
			tmp.focus();
		} 
	</script>
</head>

<body>
<table width="794"  border="0" align="center" cellpadding="0" cellspacing="0" style="border:1 solid #80B7DD ">
  <!-- 
  <tr>
    <td colspan="2" align="center"><img src="img/login/top1.jpg" width="793" height="155"></td>
  </tr>
   -->
  <tr align="center" bgcolor="0A6CAD">
    <td height="30" colspan="2"><span style="color:#ffffff;font-size:9pt"></span></td>
  </tr>
  
  <tr>
    <td width="210" background="img/login/leftbg.gif"></td>
    <td width="584" bgcolor="ffffff"><table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
      
	    <c:forEach var="item" items="${ywxtList}" varStatus="s">
		  <tr>
	        <td><table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td></td>
	            </tr>
	          <tr>
	            <td>
	            	<a onclick="goToApp('${ctx}/management/welcome?TYPE=${item.APP_ID}', '${item.APP_ID}')">
	            		<img src="${ctx}/${item.APP_ICON}" width="58" height="58" hspace="3" vspace="6" border="0" align="absmiddle">
	            	</a> 
					<a onclick="goToApp('${ctx}/management/welcome?TYPE=${item.APP_ID}', '${item.APP_ID}')" class="link"><strong>${item.APP_NAME}
						</strong>
					</a>
				</td>
	           </tr>
	        </table></td>
	      </tr>			
		</c:forEach>		
      <tr>
        <td>　</td>
        <td>　</td>
      </tr>
      <tr>
        <td>　</td>
        <td>　</td>
      </tr>
    </table></td>
  </tr>
<tr align="center" bgcolor="0A6CAD">
    <td height="30" colspan="2"><span style="color:#ffffff;font-size:9pt"></span></td>
  </tr>
</table>

</body>
</html>
