<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<div class="pageContent">
	<div>
		<div layoutH="1" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
			<ul class="tree treeFolder">
				<li><a>业务服务组件列表</a>
				<ul>
					<c:forEach var="item" items="${comList}" varStatus="s">
						<li><a href="${ctx}/standard/formService/plugins/serviceList/${BBZD_BH}?serviceID=${item.formID}" target="ajax" rel="servicePluginsLiBox">${item.formName}</a></li>
					</c:forEach>
				</ul>
				</li>
			</ul>
		</div>
		<div  layoutH="0" id="servicePluginsLiBox" class="pageContent" style="margin-left:242px;">
			<c:import url="pluginsList.jsp"></c:import>
		</div>
	</div>
</div>