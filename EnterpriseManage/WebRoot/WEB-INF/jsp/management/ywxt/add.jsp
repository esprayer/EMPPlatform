<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>
<div class="pageContent">
	<form method="post" action="${ctx}/management/base/ywxt/insert?navTabId=ywxtLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>系统编号：</label>
				<input name="APP_ID" class="required" type="text" size="25" value="${ywxtObject.APP_ID}" alt="请输入系统编号"/>
			</p>
			<p>
				<label>系统名称：</label>
				<input name="APP_NAME" class="required" type="text" size="25" value="${ywxtObject.APP_NAME}" alt="请输入系统名称"/>
			</p>
			<p>
				<label>系统描述：</label>
				<input name="APP_DES" type="text" size="25" value="${ywxtObject.APP_DES}" />
			</p>
			<p>
				<label>系统图标：</label>
				<input name="APP_ICON" type="text" size="25" value="${ywxtObject.APP_ICON}" />
			</p>
			<p>
				<label>语言：</label>
				<select name="LAN_ID" class="required combox">
					<option value=zh_cn <c:if test="${ywxtObject.LAN_ID==zh_cn}">selected="selected"</c:if>>中文</option>
				</select>
			</p>
			<p>
				<label>使用状态：</label>
				<select name="F_SYZT" class="required combox">
					<option value=1 <c:if test="${ywxtObject.F_SYZT==1}">selected="selected"</c:if>>启用</option>
				    <option value=0 <c:if test="${ywxtObject.F_SYZT==0}">selected="selected"</c:if>>停用</option>
				</select>
			</p>
			<p>
				<label>创建时间：</label>
				<input name="F_CRDATE" type="text" size="25" readonly value="${ywxtObject.F_CRDATE}" />
			</p>
			<p>
				<label>修改时间：</label>
				<input name="F_CHDATE" type="text" size="25" readonly value="${ywxtObject.F_CHDATE}"/>
			</p>
	    </div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
