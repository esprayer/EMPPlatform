<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<div class="pageContent">
	<div>
		<div layoutH="1" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
			<ul class="tree treeFolder">
				<li><a href="javascript">恒源部门名称</a>
					<ul>
						<c:forEach var="item" items="${hybmList}" varStatus="s">
							<li><a href="${ctx}/dictHelp/project/user/${item.f_BMBH}?TYPE=${TYPE}&MUL=${MUL}" target="ajax" rel="userLiBox">${item.f_BMMC}</a></li>
						</c:forEach>
					</ul>
				</li>
			</ul>
		</div>
		<div  layoutH="0" id="userLiBox" style="margin-left:242px;">
			<c:import url="userList.jsp"></c:import>
		</div>
	</div>
</div>