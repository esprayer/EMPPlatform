<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../../include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="${ctx}/dailyBusiness/excelStatements/project/update/${hyxmObject.f_XMBH}?navTabId=statementsLiNav&callbackType=closeCurrent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>项目编号: </label>
			<input readonly="readonly" type="text" name="F_XMBH" value="${hyxmObject.f_XMBH}" class="required"/>
		</p>
		<p>
			<label>项目名称: </label>
			<input type="text" name="F_XMMC" value="${hyxmObject.f_XMMC}" class="required"/>
		</p>		
		<p>
			<label>项目申请时间: </label>
			<input readonly="readonly" type="text" name="F_SQSJ" value="${hyxmObject.f_SQSJ}" class="required"/>		
		</p>	
		<p>
			<label>申请单位: </label>
			<input type="text" name="F_SQDW" value="${hyxmObject.f_SQDW}" class="required"/>
		</p>
		<div class="divider"></div>
		<p>
			<label>申请人编号: </label>
			<input id="F_SQR" name="F_SQR" type="hidden"/>
			<input readonly="readonly" name="F_SQR" type="text" postField="keyword" suggestFields="F_SQR,F_SQRMC" value="${hyxmObject.f_SQR}" lookupGroup=""/>
			<c:if test="${hyxmObject.f_XMZT==0}">
				<a class="btnLook" href="${ctx}/dictHelp/deportHelp/user?TYPE=F_SQR,F_SQRMC&MUL=1" lookupGroup="">查找带回</a>	
				<span class="info"></span>
			</c:if>				
		</p>		
		<p>
			<label>申请人名称: </label>
			<input type="text" name="F_SQRMC" value="${hyxmObject.f_SQRMC}" class="required"/>		
		</p>	
		<p>
			<label>供应中心: </label>
			<input id="F_GYZX" name="F_GYZX" value="${hyckObject.f_GYZX}" type="hidden"/>
			<input readonly="readonly" name="F_GYZX" type="text" postField="keyword" suggestFields="F_GYZX,F_GYZXMC" lookupGroup="org1" value="${hyxmObject.f_GYZX}"/>
			<c:if test="${hyxmObject.f_XMZT==0}">
				<a class="btnLook" href="${ctx}/dictHelp/deportHelp/user?TYPE=F_GYZX,F_GYZXMC&MUL=0" lookupGroup="">查找带回</a>	
				<span class="info"></span>		
			</c:if>					
		</p>		
		<p>
			<label>供应中心人员名称: </label>
			<input type="text" name="F_GYZXMC" value="${hyxmObject.f_GYZXMC}" class="required"/>		
		</p>	
		<p>
			<label>单位领导编号: </label>			
			<input id="F_DWLD" name="F_DWLD" value="${hyckObject.f_DWLD}" type="hidden"/>
			<input readonly="readonly" name="F_DWLD" type="text" postField="keyword" suggestFields="F_DWLD,F_DWLDMC" lookupGroup="org1" value="${hyxmObject.f_DWLD}"/>
			<c:if test="${hyxmObject.f_XMZT==0}">
				<a class="btnLook" href="${ctx}/dictHelp/deportHelp/user?TYPE=F_DWLD,F_DWLDMC&MUL=0" lookupGroup="">查找带回</a>	
				<span class="info"></span>
			</c:if>				
		</p>		
		<p>
			<label>单位领导名称: </label>
			<input type="text" name="F_DWLDMC" value="${hyxmObject.f_DWLDMC}" class="required"/>		
		</p>	
		<p>
			<label>分管领导编号: </label>
			<input id="F_FGLD" name="F_FGLD" value="${hyckObject.f_DWLD}" type="hidden"/>
			<input readonly="readonly" name="F_FGLD" type="text" postField="keyword" suggestFields="F_FGLD,F_FGLDMC" lookupGroup="org1" value="${hyxmObject.f_FGLD}"/>
			<c:if test="${hyxmObject.f_XMZT==0}">
				<a class="btnLook" href="${ctx}/dictHelp/deportHelp/user?TYPE=F_FGLD,F_FGLDMC&MUL=0" lookupGroup="">查找带回</a>	
				<span class="info"></span>			
			</c:if>				
		</p>		
		<p>
			<label>分管领导名称: </label>
			<input type="text" name="F_FGLDMC" value="${hyxmObject.f_FGLDMC}" class="required"/>		
		</p>	
		<p>
			<label>主管领导编号: </label>
			<input id="F_ZGLD" name="F_ZGLD" value="${hyckObject.f_ZGLD}" type="hidden"/>
			<input readonly="readonly" name="F_ZGLD" type="text" postField="keyword" suggestFields="F_ZGLD,F_ZGLDMC" lookupGroup="org1" value="${hyxmObject.f_ZGLD}"/>
			<c:if test="${hyxmObject.f_XMZT==0}">
				<a class="btnLook" href="${ctx}/dictHelp/deportHelp/user?TYPE=F_ZGLD,F_ZGLDMC&MUL=0" lookupGroup="">查找带回</a>	
				<span class="info"></span>
			</c:if>				
		</p>		
		<p>
			<label>主管领导名称: </label>
			<input type="text" name="F_ZGLDMC" value="${hyxmObject.f_ZGLDMC}" class="required"/>		
		</p>	
		<div class="divider"></div>
		<p>
			<label>材料需求时间: </label>
			<fmt:formatDate value="${hyxmObject.f_CLXQSJ}" pattern="yyyy-MM-dd HH:mm:ss"/>			
		</p>		
		<p>
			<label>项目状态: </label>
			<c:if test="${hyxmObject.f_XMZT==1}">已完工</c:if>
			<c:if test="${hyxmObject.f_XMZT==0}">未完工</c:if>				
		</p>		
		<p>
			<label>完工时间: </label>
			<fmt:formatDate value="${hyxmObject.f_WGSJ}" pattern="yyyy-MM-dd HH:mm:ss"/>					
		</p>
		<p>
			<label>修改时间: </label>
			<fmt:formatDate value="${hyxmObject.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</p>	
	</div>
	<div class="formBar">
		<ul>
			<c:if test="${hyxmObject.f_XMZT==0}">
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			</c:if>			
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>