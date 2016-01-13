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
				<li><a>恒源部门名称</a>
				<ul>
					<c:forEach var="item" items="${hyxmList}" varStatus="s">
						<li><a href="${ctx}/sysConfigure/project/product/${item.f_XMBH}" target="ajax" rel="projectProductLiBox">${item.f_XMMC}</a></li>
					</c:forEach>
				</ul>
				</li>
			</ul>
		</div>
		<div  layoutH="0" id="projectProductLiBox" class="pageContent" style="margin-left:242px;">
			<c:import url="productList.jsp"></c:import>
		</div>
	</div>
</div>