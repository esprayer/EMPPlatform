<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
ul.rightTools {
	float: right;
	display: block;
}

ul.rightTools li {
	float: left;h
	display: block;
}

.panel .panelFooter, .panel .panelFooterContent{
	background: no-repeat;
}

</style>

<script language="javascript">

	//单击事件
    function showFolderBgc(href,actionHref, hrefUrl, nodeType){
    	if(nodeType=='0' || nodeType=='1') {
			var realPath = hrefUrl.substring(hrefUrl.indexOf("@"));
			realPath = realPath.substring(1, realPath.length -1);
			realPath = encodeURIComponent(realPath);
			var href1 = hrefUrl.substring(0,hrefUrl.indexOf("@")) + realPath;
			var value = $('#realPath').val();   
			navTab.reload(href1);
		}		
    }

  	//单击事件
    function showDocBgc(href,actionHref){
    	var $rel = $("#infoLiBox");
		$rel.loadUrl(href);
		//加载actionDiv
		$rel = $("#actionDiv");
		$rel.loadUrl(actionHref);
		
    }
    
	//双击事件
    function subordinate(href){
        var realPath = href.substring(href.indexOf("@"));
        realPath = realPath.substring(1, realPath.length -1);
        realPath = encodeURIComponent(realPath);
        var href1 = href.substring(0,href.indexOf("@")) + realPath;
    	var value = $('#realPath').val();   
    	navTab.reload(href1);
    }

	//页面跳转
    function goToAction(href){	
        var value = $('#inputPath').val();
        href += "&inputPath=" + encodeURIComponent(value);
    	navTab.reload(href);
    }

	//回车事件
    function redirect(href){
		if (event.keyCode == 13){ 
			goToAction(href);
    	}
    }

    //返回上一级
    function goBack(href) {
    	var value = $('#realPath').val();   
    	href += "&realPath="+encodeURIComponent(value);
    	navTab.reload(href);
    }

  	//检查session是否过期,打开word文档
    function openWin(checkHref, url) {
    	$.ajax({
			type: 'POST',
			url:checkHref,
			dataType:"json",
			cache: false,
			success: function(response){
				var json = DWZ.jsonEval(response);
				if (json.statusCode == DWZ.statusCode.ok) {
					var tmp=window.open(url,"","fullscreen=0");
					tmp.moveTo(0,0);
					tmp.resizeTo(screen.width,screen.height);
					tmp.focus();
				} else if (json.statusCode==DWZ.statusCode.timeout){
					if ($.pdialog) $.pdialog.checkTimeout();
					if (navTab) navTab.checkTimeout();

					alertMsg.error(json.message || DWZ.msg("sessionTimout"), {okCall:function(){
						DWZ.loadLogin();
					}});
				}
			}
		});
    }
    //全选和取消全选
    $(function(){ 
    	$("#checkAll").change(function() { 
    		var val1 = $("#checkAll").attr("checked");  //全选
    		var bSelect = false;
			if(val1 == 'checked') {
				 bSelect = true;
			} else {
				 bSelect = false;
			}

			var ttt = $("#folderTable tbody");
	    	var nTds = $('tr', ttt);
	    	var i = 0;
	    	nTds.each(function () {
				$($("td :checkbox", nTds[i])).attr("checked", bSelect);  //获取真假				
				i++;
			})
    	}); 
	}); 
</script>
<input type="hidden" id="realPath" name="realPath" value="${realPath}" />
<div class="pageContent" style="padding:0px;margin:0;background-color: white;">
	<div class="panel"  style="padding:0px;margin:0;background-color: white;">
		<div  style="padding:0px;margin=0;background-color: white;">
			<TABLE border=0 cellSpacing=0 cellPadding=0 width=100% background="">
				<TBODY>
					<TR>
						<TD background=${ctx}/img/dms/daoh.gif>
							<div id="surplusFlowDiv">
								<c:import url="surplusFlow.jsp"></c:import>
							</div>
							<div id="actionDiv">
								<c:import url="menuAction.jsp"></c:import>
							</div>
							<div id="pathDiv">
								<c:import url="currentPath.jsp"></c:import>
							</div>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
		
			<div id="folderDocDiv" class="pageContent" layoutH="232">
				<c:import url="folderDocList.jsp"></c:import>
			</div>
		</div>
	</div>
	<div class="divider"></div>
	<div id="infoLiBox">
		<c:import url="folderProperty.jsp"></c:import>
	</div>
	
</div>