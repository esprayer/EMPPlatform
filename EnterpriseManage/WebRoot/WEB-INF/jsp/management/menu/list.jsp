<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,esyt.framework.persistence.qxgl.beans.SYSMenu"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>
<script>
  var arry = [];
  <% 
  List<SYSMenu> listMenu = (List)request.getAttribute("menuTree");
  SYSMenu menu = null;
  if(null!=listMenu){
	  for(int i=0;i<listMenu.size();i++){
		  menu = listMenu.get(i);
 %>
	 var menu = {
			  menu_bh : '<%=menu.getMENU_BH()%>',
			  menu_mc : '<%=menu.getMENU_MC()%>',
			  f_js    : '<%=menu.getF_JS()%>',
			  f_mx    : '<%=menu.getF_MX()%>',
			  f_parent: '<%=menu.getF_PARENT()%>',
	 };
   arry[<%=i%>] = menu;
  <%
   }
  }
  %> 
  $(document).ready(function(){
	  $("#menuTree").append('<ul class="tree treeFolder collapse"><li id="nodeROOT"><a href="${ctx}/management/base/menu/search/ROOT?js=0&APP_ID=${ywxtObject.APP_ID}" target="ajax" rel="menuLiBox">${ywxtObject.APP_NAME}</a></li></ul>');
	  var parent = "#ROOT";
	  var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]");
	  $.each(arry,function(i,obj){
		  parent = obj['f_parent'].replace(pattern,'');
		  if($('#node'+parent+'>ul').length<=0){
			  $('#node'+parent).append('<ul></ul>');
		  }
		 
		  $('#node'+parent+'>ul').append('<li id="node'+obj['menu_bh']+'"><a href="${ctx}/management/base/menu/search/'+obj['menu_bh']+'?js='+obj['f_js']+'&APP_ID=${ywxtObject.APP_ID}"  target="ajax" rel="menuLiBox">'+obj['menu_mc']+'</a>'
				   +'</li>');  
		  
	  });
  });
  function changeYwxt(){
	  navTab.reload('${ctx}/management/base/menu/change', {data: {ywxtId:$("#ywxts").val()}, navTabId:'menuLiNav'});
  }
</script>
<div class="pageContent">
	<div>
	   
		<div id="menuTree" layoutH="1" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
	       <div class="panelBar">
				<select id="ywxts" class="combox" name="ywxts" onChange="changeYwxt()">
					<c:forEach var="item" items="${ywxtList}" varStatus="s">
						<c:choose>
							<c:when test="${item.APP_ID==ywxtObject.APP_ID}">
                               <option value="${item.APP_ID}" selected>&nbsp${item.APP_NAME}&nbsp</option>
      					    </c:when>
							<c:otherwise>
					           <option value="${item.APP_ID}">&nbsp${item.APP_NAME}&nbsp</option>
					        </c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
		<div  layoutH="0" id="menuLiBox" class="unitBox" style="margin-left:242px;">
			<c:import url="menuTable.jsp"></c:import>
		</div>
	</div>
</div>