<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/standard/metadata/objcols/insert?navTabId=metadataColNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<input type="hidden" name="OBJ_ID" value="${metadataObjectCol.OBJ_ID}"/>
		<p>
			<label>列标识: </label>
			<input type="text" name="COL_ID" value="${metadataObjectCol.COL_ID}" class="required"/>
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
			<select name="COL_TYPE" style="width:135px" class="combox" >
				<option value="C" selected="selected">字符</option>
				<option value="L">大文本串</option>
				<option value="G">大整数</option>
				<option value="N">数值</option>
				<option value="I">整数</option>
				<option value="D">日期</option>
				<option value="T">时间戳</option>
				<option value="B">BLOB</option>
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
			<input name="COL_ISKEY" value="${metadataObjectCol.COL_ISKEY}" type="checkbox">			
		</p>
		<p>
			<label>允许空: </label>
			<input name="COL_ISNULL" value="${metadataObjectCol.COL_ISNULL}" type="checkbox">
		</p>
		<p>
			<label>显示否: </label>
			<input name="COL_VISIBLE" value="${metadataObjectCol.COL_VISIBLE}" type="checkbox">
		</p>
		<p>
			<label>可编辑: </label>
			<input name="COL_EDITABLE" value="${metadataObjectCol.COL_EDITABLE}" type="checkbox">
		</p>
		<p>
			<label>编辑方式: </label>
			<select name="COL_EDIT" style="width:135px" class="combox" >
				<option value="1" selected="selected">字符串</option>
				<option value="2">整型</option>
				<option value="3">实型</option>
				<option value="4">布尔值</option>
				<option value="5">日期</option>
				<option value="6">下拉</option>
				<option value="7">密码</option>
				<option value="8">窗口</option>
				<option value="9">时间</option>
			</select>	
		</p>
		<p>
			<label>编辑掩码: </label>
			<input type="text" name="COL_VIEW" value="${metadataObjectCol.COL_VIEW}"/>
		</p>
		<p>
			<label>默认值: </label>
			<input type="text" name="COL_DEFAULT" value="${metadataObjectCol.COL_DEFAULT}"/>
		</p>
		<p>
			<label>外键列: </label>
			<input name="COL_ISFKEY" value="${metadataObjectCol.COL_ISFKEY}" type="checkbox">
		</p>
		<p>
			<label>引用字典: </label>
			<input type="text" name="COL_FOBJ" value="${metadataObjectCol.COL_FOBJ}"/>
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