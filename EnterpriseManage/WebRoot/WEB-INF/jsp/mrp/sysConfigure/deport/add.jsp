<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/sysConfigure/deport/insert/${hyckObject.f_BMBH}?rel=deportLiBox&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>仓库编号: </label>
			<input type="text" name="F_CKBH" value="${hyckObject.f_CKBH}" class="required"/>
		</p>
		<p>
			<label>仓库名称: </label>
			<input type="text" name="F_CKMC" value="${hyckObject.f_CKMC}" class="required"/>
		</p>
		<p>
			<label>部门编号: </label>
			<input disabled="true" type="text" name="F_BMBH" value="${hyckObject.f_BMBH}" class="required"/>
		</p>
		<p>
			<label>部门名称: </label>
			<input disabled="true" type="text" name="F_BMMC" value="${hyckObject.f_BMMC}" class="required"/>
		</p>
		<p>
			<label>管理员编号: </label>
			<input class="required" id="F_ZGBH" name="F_ZGBH" value="" type="hidden"/>
			<input disabled="true"  class="required" name="F_ZGBH" type="text" postField="keyword" suggestFields="F_ZGBH,F_ZGMC" lookupGroup="org1"/>
			<a class="btnLook" href="${ctx}/sysConfigure/deport/department/${hyckObject.f_BMBH}" lookupGroup="">查找带回</a>	
			<span class="info"></span>
		</p>
		<p>
			<label>管理员名称: </label>
			<input class="readonly" name="F_ZGMC" readonly="readonly" type="text"/>			
		</p>			
		<p>
			<label>描述: </label>
			<input type="text" name="F_MS" value="${hyckObject.f_MS}" class="required"/>
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