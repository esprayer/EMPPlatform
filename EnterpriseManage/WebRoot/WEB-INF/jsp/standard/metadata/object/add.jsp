<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/standard/metadata/object/insert?navTabId=metadataLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>对象标识: </label>
			<input type="text" name="OBJ_ID" value="${metadataObject.OBJ_ID}" class="required"/>
		</p>
		<p>
			<label>对象名称: </label>
			<input type="text" name="OBJ_MC" value="${metadataObject.OBJ_MC}" class="required"/>
		</p>
		<p>
			<label>对象描述: </label>
			<input type="text" name="OBJ_DES" value="${metadataObject.OBJ_DES}" class="required"/>
		</p>
		<p>
			<label>对象类型: </label>
			<select name="OBJ_TYPE" style="width:135px" class="combox" >
				<option value="T" selected="selected">表</option>
				<option value="V">视图</option>
				<option value="P">存储过程</option>
			</select>			
		</p>
		<p>
			<label>应用类型: </label>
			<select name="OBJ_APPTYPE" style="width:135px" class="combox" >
				<option value="SYS" selected="selected">系统表</option>
				<option value="DIM">维表</option>
				<option value="MOD">事实表</option>
				<option value="RLT">关系表</option>
				<option value="CFG">配置表</option>
			</select>					
		</p>
		<p>
			<label>系统标识: </label>
			<input type="text" name="SYS_ID" value="${metadataObject.SYS_ID}" class="required"/>
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