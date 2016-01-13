<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include.inc.jsp"%>
<div class="pageTitle">
	<ul class="crumbs">
		<li><a href="/">首页</a></li>
		<li>期刊</li>
	</ul>
	<h1>篇名：${chapter.nameCn}</h1>
</div>

<div id="main">
	<a class="back" href="/chapters">返回</a>
	<table class="plist dpage">
		<tbody>
			<tr>
				<th>中午刊名</th>
				<td>${chapter.nameCn}</td>
			</tr>
			<tr>
				<th>英文刊名</th>
				<td>${chapter.nameEn}</td>
			</tr>
			<tr>
				<th>作者</th>
				<td>${chapter.authorId}</td>
			</tr>
			<tr>
				<th>出版日期</th>
				<td><fmt:formatDate value="${chapter.insertDate}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<th>卷期</th>
				<td>57卷4期</td>
			</tr>
			<tr>
				<th>起迄页</th>
				<td>${chapter.startPageNo}-${chapter.startPageNo + chapter.pageCount}</td>
			</tr>
			<tr>
				<th>总页数</th>
				<td>${chapter.pageCount}</td>
			</tr>
			<tr>
				<th>出版社</th>
				<td></td>
			</tr>
			<tr>
				<th colspan="2" class="reader">
					<div class="temreader" id="readBox">
						<select class="pageNo" onchange="showPdf(this.value)">
						</select>
						<select id="ratio" onchange="showPdf(null, this.value)">
							<option value="0.25">25%</option>
							<option value="0.50">50%</option>
							<option value="0.75">75%</option>
							<option value="1" selected="selected">100%</option>
							<option value="1.5">150%</option>
							<option value="2">200%</option>
						</select>
						
						<div class="readContent" style="overflow: auto"></div>
					</div>
				</th>
			</tr>
		</tbody>
	</table>
</div>


<script type="text/javascript">
var jsonInfo = {
	currentIndex: 0,
	ratio: 1,
	items: []
};

function showPdf(pageNo, ratio){
	if (!pageNo) pageNo = jsonInfo.currentIndex + 1;
	else jsonInfo.currentIndex = pageNo -1;
	
	$.getJSON('${chapter.pdf2jsonUri}/p'+pageNo+'.json', function(json){
		var items = jsonInfo.items[pageNo-1];
		var pdf = new PdfContext(items[0],items[1],json,'${chapter.pdf2jsonUri}',pageNo);
		pdf.draw('#readBox .readContent',ratio||1);

		//测试放大效果
		//setTimeout(function(){pdf.draw('#readBox',2);}, 1000)
	});
}

$(function(){
	$.getJSON('${chapter.pdf2jsonUri}/info.json', function(json){
		var optionFrag = "";
		for (var i=1; i<=json.count; i++) {
			optionFrag += '<option value="' + i + '">' + i + '</option>';
		}
		
		$("#readBox .pageNo").html(optionFrag);
		
		jsonInfo.items = json.items;
		showPdf(1);
	});
	
});
</script>
