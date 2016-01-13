<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../../include.inc.jsp"%>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block;}
</style>

<script type="text/javascript">
	function clicked(id){
	    var o, i;
	    o = document.getElementById(id).rows;//表格所有行
	    for(i = 0; i < o.length; i++){
	        o[i].ondblclick = function(){ //设置事件
	            var oo;
	            oo = this.cells[1]; //取得第二列对象
	            alert(oo.innerHTML);
	        }
	    }
	}
	
	window.onload = function(){ //当文件全部下载完之后开始设置对事件，不然可能会报错,  或者把clicked('mytable')放到要设置的表格后面也行
	    clicked('mytable'); //设置第一个表格的事件
	    //clicked('mytable2'); //设置第二个表格的事件
	}

	function GetHtml(row){
		alert($("#mytable tr:eq("+row+")").html());
	}
</script>
<form id="pagerForm" method="post" action="/sysConfigure/product">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<form method="post" rel="pagerForm" action="${ctx}/sysConfigure/product/search"  class="pageForm" onsubmit="return navTabSearch(this,'productLiNav')">
	<div class="pageContent">
		<div class="panel" defH="25">
			<h1>产品基本信息</h1>
			<div>
				产品编号：<input type="text" name="F_CPBH" value="${F_CPBH}"/>
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
			<li><a class="add" target="dialog" mask="true" width="420" height="200" rel="objectNav" href="${ctx}/sysConfigure/product/add" title="添加产品"><span>添加</span></a></li>
			<li><a class="edit" target="dialog"  mask="true" width="420" height="250" rel="objectNav" href="${ctx}/sysConfigure/product/edit/{slt_objId}" title="编辑产品" warn="请选择需要编辑的产品信息！"><span>编辑</span></a></li>
			<li><a class="delete" target="ajaxTodo" href="${ctx}/sysConfigure/product/delete/{slt_objId}?navTabId=productLiNav" title="你确定要删除吗?" warn="请选择需要删除的产品信息！"><span>删除</span></a></li>
			<li class="line">line</li>								
		</ul>
	</div>
	<table id="mytable" name="mytable" class="table" width="100%" layoutH="161">
		<thead>
			<tr>
				<th width="25" align=center>序号</th>
				<th width="60">产品编号</th>
				<th width="60">产品名称</th>				
				<th width="70">使用状态</th>
				<th width="70">创建时间</th>
				<th width="70">修改时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${hycpList}" varStatus="s">
			<tr target="slt_objId" rel="${item.f_CPBH}" ondblclick="GetHtml(this.rowIndex)">
				<td>${s.index + 1}</td>
				<td>${item.f_CPBH}</td>
				<td>${item.f_CPMC}</td>				
				<td>
					<c:if test="${item.f_SYZT==0}">停用</c:if>
					<c:if test="${item.f_SYZT==1}">启用</c:if>
				</td>
				<td><fmt:formatDate value="${item.f_CRDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${item.f_CHDATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<c:import url="../../../jsp/_frag/pager/panelBar.jsp"></c:import>
</div>