<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<form id="pagerForm" method="post" action="/standard/metadata">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/standard/metadata/search/${OBJ_ID}"  class="pageForm" onsubmit="return navTabSearch(this,'metadataColNav')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>对象列基本信息</h1>
			<div>
				对象标识：<input type="text" disabled="true" name="OBJ_ID" value="${OBJ_ID}"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				列标识：<input type="text" name="keywords" value="${param.keywords}"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				列状态：
				<input type="radio" name="typeKeywords" value="-1" <c:if test="${typeKeywords==''|| typeKeywords==null || typeKeywords=='-1'}">checked="checked" </c:if> />全部
				<input type="radio" name="typeKeywords" value="1" <c:if test="${typeKeywords=='1'}">checked="checked" </c:if>/>启用
				<input type="radio" name="typeKeywords" value="0" <c:if test="${typeKeywords=='0'}">checked="checked" </c:if>/>停用				
				<ul class="rightTools">
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
		<div class="divider"></div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" target="dialog" mask="true" width="800" height="370" rel="objectNav" href="${ctx}/standard/metadata/objcols/add/${OBJ_ID}" title="添加对象列"><span>添加</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="800" height="370" rel="objectNav" href="${ctx}/standard/metadata/objcols/edit/${OBJ_ID}?COL_ID={slt_objId}" title="编辑数据对象"><span>编辑</span></a></li>						
			<li class="line">line</li>			
		</ul>
	</div>
	<table class="table" width="2000" layoutH="161">
		<thead>
			<tr>
				<th width="10"></th>
				<th width="60">列标识</th>
				<th width="60">列名称</th>
				<th width="60">列描述</th>
				<th width="60">列类型</th>
				<th width="60">列长度</th>
				<th width="60">显示长度</th>
				<th width="60">列精度</th>
				<th width="60">主键列</th>
				<th width="60">允许空</th>
				<th width="60">显示否</th>
				<th width="60">可编辑</th>
				<th width="60">编辑方式</th>
				<th width="60">编辑掩码</th>
				<th width="60">默认值</th>
				<th width="60">外键列</th>
				<th width="60">引用字典</th>
				<th width="70">创建日期</th>
				<th width="70">修改时间</th>
				<th width="30">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${metaColList}" varStatus="s">
			<tr target="slt_objId" rel="${item.COL_ID }">
				<td>${s.index + 1}</td>
				<td>${item.COL_ID}</td>
				<td>${item.COL_MC}</td>
				<td>${item.COL_DES}</td>
				<td>
					<c:choose>
						<c:when test="${item.COL_TYPE=='C'}">
							字符
						</c:when>
						<c:when test="${item.COL_TYPE=='L'}">
							大文本串
						</c:when>
						<c:when test="${item.COL_TYPE=='G'}">
							大整数
						</c:when>
						<c:when test="${item.COL_TYPE=='N'}">
							数值
						</c:when>
						<c:when test="${item.COL_TYPE=='I'}">
							整数
						</c:when>
						<c:when test="${item.COL_TYPE=='D'}">
							日期
						</c:when>
						<c:when test="${item.COL_TYPE=='T'}">
							时间戳
						</c:when>
						<c:when test="${item.COL_TYPE=='B'}">
							BLOB
						</c:when>						
					</c:choose>
				</td>
				<td>${item.COL_LEN}</td>
				<td>${item.COL_PREC}</td>
				<td>${item.COL_SCALE}</td>
				<td><input disabled="false" name="COL_ISKEY" value="${item.COL_ISKEY}" <c:if test="${item.COL_ISKEY=='1'}">checked=true</c:if> type="checkbox"></td>
				<td><input disabled="false" name="COL_ISNULL" value="${item.COL_ISNULL}" <c:if test="${item.COL_ISNULL==1}">checked=true</c:if> type="checkbox"></td>
				<td><input disabled="false" name="COL_VISIBLE" value="${item.COL_VISIBLE}" <c:if test="${item.COL_VISIBLE==1}">checked=true</c:if> type="checkbox"></td>
				<td><input disabled="false" name="COL_EDITABLE" value="${item.COL_EDITABLE}" <c:if test="${item.COL_EDITABLE==1}">checked=true</c:if> type="checkbox"></td>
				<td>
					<c:choose>
						<c:when test="${item.COL_EDIT=='1'}">
							字符串
						</c:when>
						<c:when test="${item.COL_EDIT=='2'}">
							整型
						</c:when>
						<c:when test="${item.COL_EDIT=='3'}">
							实型
						</c:when>
						<c:when test="${item.COL_EDIT=='4'}">
							布尔值
						</c:when>
						<c:when test="${item.COL_EDIT=='5'}">
							日期
						</c:when>
						<c:when test="${item.COL_EDIT=='6'}">
							下拉
						</c:when>
						<c:when test="${item.COL_EDIT=='7'}">
							密码
						</c:when>
						<c:when test="${item.COL_EDIT=='8'}">
							窗口
						</c:when>
						<c:when test="${item.COL_EDIT=='9'}">
							时间
						</c:when>
					</c:choose>
				</td>
				<td>${item.COL_VIEW}</td>
				<td>${item.COL_DEFAULT}</td>
				<td><input disabled="false" name="COL_ISFKEY" value="${item.COL_ISFKEY}" <c:if test="${item.COL_ISFKEY==1}">checked=true</c:if> type="checkbox"></td>
				<td>${item.COL_FOBJ}</td>				
				<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:if test="${item.f_STAU==0}">
						<a title="是否启用该列？" target="ajaxTodo" href="${ctx}/standard/metadata/objcols/enableCol/${OBJ_ID}?COL_ID={slt_objId}&navTabId=metadataColNav" class="btnEdit">启用</a>
					</c:if>
					<c:if test="${item.f_STAU==1}">
						<a title="是否 停用该列？" target="ajaxTodo" href="${ctx}/standard/metadata/objcols/disableCol/${OBJ_ID}?COL_ID={slt_objId}&navTabId=metadataColNav" class="btnDel">废弃</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
			
		</tbody>
	</table>

	<c:import url="../_frag/pager/panelBar.jsp"></c:import>
</div>