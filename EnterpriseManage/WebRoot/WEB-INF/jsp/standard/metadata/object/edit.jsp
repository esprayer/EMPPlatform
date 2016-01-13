<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/standard/metadata/object/update/${metadataObject.OBJ_ID}?navTabId=metadataLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>对象标识:  </label>
			<input disabled="true" name="OBJ_MC" type="text" value="${metadataObject.OBJ_ID}" />
			
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
				<option value="T" <c:if test="${metadataObject.OBJ_TYPE=='T'}">selected="selected"</c:if>>表</option>
				<option value="V" <c:if test="${metadataObject.OBJ_TYPE=='V'}">selected="selected"</c:if>>视图</option>
				<option value="P" <c:if test="${metadataObject.OBJ_TYPE=='P'}">selected="selected"</c:if>>存储过程</option>
			</select>			
		</p>
		<p>
			<label>应用类型: </label>
			<select name="OBJ_APPTYPE" style="width:135px" class="combox" >
				<option value="SYS" <c:if test="${metadataObject.OBJ_APPTYPE=='SYS'}">selected="selected"</c:if>>系统表</option>
				<option value="DIM" <c:if test="${metadataObject.OBJ_APPTYPE=='DIM'}">selected="selected"</c:if>>维表</option>
				<option value="MOD" <c:if test="${metadataObject.OBJ_APPTYPE=='MOD'}">selected="selected"</c:if>>事实表</option>
				<option value="RLT" <c:if test="${metadataObject.OBJ_APPTYPE=='RLT'}">selected="selected"</c:if>>关系表</option>
				<option value="CFG" <c:if test="${metadataObject.OBJ_APPTYPE=='CFG'}">selected="selected"</c:if>>配置表</option>
			</select>					
		</p>
		<p>
			<label>系统标识: </label>
			<input type="text" name="SYS_ID" value="${metadataObject.SYS_ID}" class="required"/>
		</p>		
		<p>
			<label>创建时间: </label>
			<fmt:formatDate value="${metadataObject.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</p>
		<p>
			<label>修改时间: </label>
			<fmt:formatDate value="${metadataObject.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>
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