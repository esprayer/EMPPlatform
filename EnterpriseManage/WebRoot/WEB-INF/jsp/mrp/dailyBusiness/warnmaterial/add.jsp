<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/dailyBusiness/warnmaterial/insert?navTabId=warnmaterialLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>材料编号: </label>
			<input readonly="readonly" class="required" name="F_CLBH" type="text" postField="keyword" suggestFields="F_CLBH,F_CLMC" lookupGroup="org1" value="${hyxmObject.f_CLBH}"/>
			<a class="btnLook" href="${ctx}/dictHelp/material/help?F_CLBH=&tarRel=/mrp/dailyBusiness/warnmaterial/clHelp" lookupGroup="">查找带回</a>	
			<span class="info"></span>
		</p>
		<p>
			<label>材料名称: </label>
			<input type="text" name="F_CLMC" value="${hyxmmxObject.f_CLMC}" readonly="readonly"/>
		</p>
		<p>
			<label>规格型号: </label>
			<input type="text" name="F_GGXH" value="${hyxmmxObject.f_GGXH}" readonly="readonly"/>
		</p>
		<p>
			<label>计量单位: </label>
			<input type="text" name="F_JLDW" value="${hyxmmxObject.f_JLDW}" readonly="readonly"/>
		</p>
		<p>
			<label>供应商名称: </label>
			<input type="text" name="F_DWMC" value="${hyxmmxObject.f_DWMC}" readonly="readonly"/>
		</p>
		<p>
			<label>厂商名称: </label>
			<input type="text" name="F_CSMC" value="${hyxmmxObject.f_CSMC}" readonly="readonly"/>
		</p>
		<p>
			<label>库存下线: </label>
			<input type="text" name="F_KCXX" value="0" class="number required" min="0" alt="请输入库存下线"/>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>