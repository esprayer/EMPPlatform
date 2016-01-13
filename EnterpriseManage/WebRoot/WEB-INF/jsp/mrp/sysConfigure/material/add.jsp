<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/sysConfigure/material/insert?navTabId=materialLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>材料编号: </label>
			<input type="text" name="F_CLBH" value="${hyclObject.f_CLBH}" class="required"/>
		</p>
		<p>
			<label>材料名称: </label>
			<input type="text" name="F_CLMC" value="${hyclObject.f_CLMC}" class="required"/>
		</p>
		<div class="divider"></div>
		<p>
			<label>规格型号: </label>
			<input type="text" name="F_GGXH" value="${hyclObject.f_GGXH}" class="required"/>
		</p>
		<p>
			<label>单位: </label>
			<input type="text" name="F_JLDW" value="${hyclObject.f_JLDW}" class="required"/>
		</p>
		<p>
			<label>供应商编号: </label>			
			<input class="readonly" class="required" name="F_DWBH" type="text" postField="keyword" suggestFields="F_DWBH,F_DWMC" lookupGroup="org1"/>
			<a class="btnLook" href="${ctx}/sysConfigure/material/suppliers" lookupGroup="">查找带回</a>	
			<span class="info"></span>
		</p>
		<p>
			<label>供应商名称: </label>
			<input class="readonly" name="F_DWMC" readonly="readonly" type="text"/>			
		</p>		
		<p>
			<label>生产厂商编号: </label>			
			<input class="readonly" class="required" name="F_CSBH" type="text" postField="keyword" suggestFields="F_CSBH,F_CSMC" lookupGroup="org1"/>
			<a class="btnLook" href="${ctx}/dictHelp/companyHelp?F_CSBH=&F_SYZT=1" lookupGroup="">查找带回</a>	
			<span class="info"></span>
		</p>
		<p>
			<label>生产厂商名称: </label>
			<input class="readonly" name="F_CSMC" readonly="readonly" type="text"/>			
		</p>
		<p>
			<label>录入人编号: </label>
			<input class="readonly" type="text" name="F_LRR" value="${hyclObject.f_LRR}" readonly="readonly"/>
		</p>
		<p>
			<label>录入人名称: </label>
			<input class="readonly" type="text" name="F_LRRXM" value="${hyclObject.f_LRRXM}" readonly="readonly"/>
		</p>	
		<p>
			<label>使用状态: </label>
			<select name="F_SYZT" style="width:135px" class="combox" >
				<option value=1 selected="selected">启用</option>
				<option value=0>停用</option>
			</select>			
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