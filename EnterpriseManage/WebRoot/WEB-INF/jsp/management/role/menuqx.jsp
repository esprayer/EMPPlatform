<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>
<script>
  var arry = [];
  <% 
  List<Map> listMenu = (List)request.getAttribute("menuList");
  Map map = null;
  for(int i=0;i<listMenu.size();i++){
	  map = listMenu.get(i);
 %>
	 var menu = {
			  menu_bh : '<%=map.get("MENU_BH").toString()%>',
			  menu_mc : '<%=map.get("MENU_MC").toString()%>',
			  f_js    : '<%=map.get("F_JS").toString()%>',
			  f_mx    : '<%=map.get("F_MX").toString()%>',
			  f_parent: '<%=map.get("F_PARENT").toString()%>',
			  f_op    : '<%=map.get("F_OP")%>'
	 };
   arry[<%=i%>] = menu;
  <%
  }
  %> 
  $(document).ready(function(){
	  $("#menuTree").append('<ul class="tree treeFolder"><li id="nodeROOT"><a href="javascript">系统菜单</a><ul></ul></li></ul>');
	  var parent = "#ROOT";
	  var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]");
	  $.each(arry,function(i,obj){
		  parent = obj['f_parent'].replace(pattern,'');
		  if(obj['f_mx']==1){
			  $('#node'+parent+'>ul').append('<li id="node'+obj['menu_bh']+'"><a href="${ctx}/management/qxgl/menu/'+obj['menu_bh']+'?js='+obj['f_js']+'&ywxtId=${ywxtObject.APP_ID}&obj_id=${roleObj.roleId}&type=role" target="ajax" rel="menuListBox">'+obj['menu_mc']+'</a></li>');
		  }else if(obj['f_mx']==0){
			  $('#node'+parent+'>ul').append('<li id="node'+obj['menu_bh']+'"><a href="${ctx}/management/qxgl/menu/'+obj['menu_bh']+'?js='+obj['f_js']+'&ywxtId=${ywxtObject.APP_ID}&obj_id=${roleObj.roleId}&type=role"  target="ajax" rel="menuListBox">'+obj['menu_mc']+'</a>'
								   +'<ul>'
								   +'</ul></li>');
		  }
	  });
  });
  function changeYwxt(){
	  $.pdialog.reload('${ctx}/management/qxgl/change', {data: {type:"role",ywxtId:$("#ywxts").val(),obj_id:$("#ROLE_ID").val()}, navTabId:'rolegnDialog'});
  }
</script>
<div class="pageContent">
     <div class="panel" defH="25">
			<h1>角色功能菜单</h1>
		<div>
			<table class="searchContent">
				<tr>
				   <td>业务系统：</td>
					<td>
					
						  <select id="ywxts" class="combox" name="ywxts"
										onChange="changeYwxt()">
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
						</td>
					<td class="dateRange">
						<input type="hidden" name="ROLE_ID" id="ROLE_ID" value="${roleObj.roleId}" />
			    		当前角色：${roleObj.roleId} ${roleObj.roleName}
					</td>
				</tr>
			</table>
		</div>

		</div>
		<div id="menuTree" layoutH="90" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
			 
	    </div>
				
        <div id="menuListBox" class="unitBox" style="margin-left:246px;">
           <c:import url="menulist.jsp"></c:import>
		</div>
</div>
