<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<div class="pageFormContent">
	<div class="tabs">
	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>提交出库单据信息</span></a></li>
					<li><a href="javascript:;"><span>退货单据信息</span></a></li>
				</ul>
			</div>
		</div>
		
		<div class="tabsContent">
			<div id = "clckdListDiv">
				<c:import url="clckdFormList.jsp"></c:import>
			</div>
		</div>
		
		<div class="tabsContent">
			<div id = "clthdListDiv">
				<c:import url="clthdFormList.jsp"></c:import>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>