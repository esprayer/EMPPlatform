<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/standard/metadata/objcols/update/?navTabId=metadataColNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<input type="hidden" name="OBJ_ID" value="${metadataObjectCol.OBJ_ID}"/>
		<input type="hidden" name="COL_ID" value="${metadataObjectCol.COL_ID}"/>
		<p>
			<label>列标识: </label>
			<input type="text" name="COL_ID" disabled="true" value="${metadataObjectCol.COL_ID}" class="required"/>
		</p>
		<p>
			<label>列名称: </label>
			<input type="text" name="COL_MC" value="${metadataObjectCol.COL_MC}" class="required"/>
		</p>
		<p>
			<label>列描述: </label>
			<input type="text" name="COL_DES" value="${metadataObjectCol.COL_DES}" class="required"/>
		</p>
		<p>
			<label>列类型: </label>			
			<select name="COL_TYPE" class="combox" >
				<option value="C" <c:if test="${metadataObjectCol.COL_TYPE=='C'}">selected="selected"</c:if>>字符</option>
				<option value="L" <c:if test="${metadataObjectCol.COL_TYPE=='L'}">selected="selected"</c:if>>大文本串</option>
				<option value="G" <c:if test="${metadataObjectCol.COL_TYPE=='G'}">selected="selected"</c:if>>大整数</option>
				<option value="N" <c:if test="${metadataObjectCol.COL_TYPE=='N'}">selected="selected"</c:if>>数值</option>
				<option value="I" <c:if test="${metadataObjectCol.COL_TYPE=='I'}">selected="selected"</c:if>>整数</option>
				<option value="D" <c:if test="${metadataObjectCol.COL_TYPE=='D'}">selected="selected"</c:if>>日期</option>
				<option value="T" <c:if test="${metadataObjectCol.COL_TYPE=='T'}">selected="selected"</c:if>>时间戳</option>
				<option value="B" <c:if test="${metadataObjectCol.COL_TYPE=='B'}">selected="selected"</c:if>>BLOB</option>
			</select>				
		</p>
		<p>
			<label>列长度: </label>
			<input type="text" name="COL_LEN" value="${metadataObjectCol.COL_LEN}" class="required" alt="请输入数字"/>
		</p>
		<p>
			<label>显示长度: </label>
			<input type="text" name="COL_PREC" value="${metadataObjectCol.COL_PREC}" class="required" alt="请输入数字"/>
		</p>
		<p>
			<label>列精度: </label>
			<input type="text" name="COL_SCALE" value="${metadataObjectCol.COL_SCALE}" class="required" alt="请输入数字"/>			
		</p>
		<p>
			<label>主键列: </label>
			<input name="COL_ISKEY" value="1" <c:if test="${metadataObjectCol.COL_ISKEY=='1'}">checked=true</c:if> type="checkbox">
		</p>
		<p>
			<label>允许空: </label>
			<input name="COL_ISNULL" value="1" <c:if test="${metadataObjectCol.COL_ISNULL==1}">checked=true</c:if> type="checkbox">
		</p>
		<p>
			<label>显示否: </label>
			<input name="COL_VISIBLE" value="1" <c:if test="${metadataObjectCol.COL_VISIBLE==1}">checked=true</c:if> type="checkbox">
		</p>
		<p>
			<label>可编辑: </label>
			<input name="COL_EDITABLE" value="1" <c:if test="${metadataObjectCol.COL_EDITABLE==1}">checked=true</c:if> type="checkbox">
		</p>
		<p>
			<label>编辑方式: </label>
			<select name="COL_EDIT" style="width:135px" class="combox" >
				<option value="1" <c:if test="${metadataObjectCol.COL_EDIT=='1'}">selected="selected"</c:if>>字符串</option>
				<option value="2" <c:if test="${metadataObjectCol.COL_EDIT=='2'}">selected="selected"</c:if>>整型</option>
				<option value="3" <c:if test="${metadataObjectCol.COL_EDIT=='3'}">selected="selected"</c:if>>实型</option>
				<option value="4" <c:if test="${metadataObjectCol.COL_EDIT=='4'}">selected="selected"</c:if>>布尔值</option>
				<option value="5" <c:if test="${metadataObjectCol.COL_EDIT=='5'}">selected="selected"</c:if>>日期</option>
				<option value="6" <c:if test="${metadataObjectCol.COL_EDIT=='6'}">selected="selected"</c:if>>下拉</option>
				<option value="7" <c:if test="${metadataObjectCol.COL_EDIT=='7'}">selected="selected"</c:if>>密码</option>
				<option value="8" <c:if test="${metadataObjectCol.COL_EDIT=='8'}">selected="selected"</c:if>>窗口</option>
				<option value="9" <c:if test="${metadataObjectCol.COL_EDIT=='9'}">selected="selected"</c:if>>时间</option>
			</select>	
		</p>
		<p>
			<label>编辑掩码: </label>
			<input type="text" name="COL_VIEW" value="${metadataObjectCol.COL_VIEW}""/>
		</p>
		<p>
			<label>默认值: </label>
			<input type="text" name="COL_DEFAULT" value="${metadataObjectCol.COL_DEFAULT}"/>
		</p>
		<p>
			<label>外键列: </label>
			<input name="COL_ISFKEY" value="1" <c:if test="${metadataObjectCol.COL_ISFKEY==1}">checked=true</c:if> type="checkbox">
		</p>
		<p>
			<label>引用字典: </label>
			<input type="text" name="COL_FOBJ" value="${metadataObjectCol.COL_FOBJ}"/>
		</p>
		<p>
			<label>创建时间: </label>
			<fmt:formatDate value="${metadataObjectCol.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</p>
		<p>
			<label>修改时间: </label>
			<fmt:formatDate value="${metadataObjectCol.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>
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