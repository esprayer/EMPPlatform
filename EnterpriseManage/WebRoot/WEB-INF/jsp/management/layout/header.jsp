<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include.inc.jsp"%>

<header class="header">
	<article class="masthead">
		<figure class="logo"><a href="${proxy.namespace}/index!index.do"><img src="../styles/themes/images/logo.png" width="245" height="50" alt="敏捷智能总账系统"></a></figure>
		<div class="secondary">
			<ul class="secondarybar">
				
			<c:choose>
				<c:when test="${empty contextUser}">
				<li><a href="index!login.do">登录</a></li>
				</c:when>
				<c:otherwise>
				<li class="accountinfo"><span>您好，<a href="my.html">${contextUser.realname}</a></span></li>
				<li><a href="/passport!logout.do">退出</a></li>
				</c:otherwise>
			</c:choose>
				<li><a href="#">消息</a></li>
				<li><a href="#">建议反馈</a></li>
				<li><a href="#">帮助中心</a></li>
			</ul>
		</div>
		<div class="headerinfo">
			2012年 第2期　您现在使用的是免费版，想要更强的功能请“<a href="#">购买正式版</a>”
		</div>
	</article>
	<nav class="nav">
		<ul class="menu">
			<li ${proxy.actionName eq 'index' ? 'class="selected"' : ''}><a href="${proxy.namespace}/index!index.do">我的总账</a></li>
			<li ${proxy.actionName eq 'jzpz' ? 'class="selected"' : ''}><a href="${proxy.namespace}/jzpz!list.do">会计凭证</a></li>
			<li ${proxy.actionName eq 'frmLedger' ? 'class="selected"' : ''}><a href="${proxy.namespace}/frmLedger!index.do">账簿查询</a></li>
			<li ${proxy.actionName eq 'frmBalance' ? 'class="selected"' : ''}><a href="${proxy.namespace}/frmBalance!index.do">科目余额表</a></li>
			<li ${proxy.actionName eq 'frmClosePeriod' ? 'class="selected"' : ''}><a href="${proxy.namespace}/frmClosePeriod!index.do">期末结账</a></li>
			<li ${proxy.actionName eq 'frmReport' ? 'class="selected"' : ''}><a href="${proxy.namespace}/frmReport!index.do">财务报表</a></li>
			<li ${proxy.actionName eq 'frmHistroy' ? 'class="selected"' : ''}><a href="${proxy.namespace}/frmHistroy!index.do">历史数据</a></li>
		</ul>
		<ul class="more">
			<li class="print"><a href="print.html">打印</a></li>
			<li class="quick "><a href="#quick">快速菜单</a><!-- 鼠标移动或点击的时候 添加selected -->
				<div class="popmenu">
					<div class="menubd clearfix">
						<dl>
							<dt>财务</dt>
							<dd><a href="${proxy.namespace}/jzpz!list.do">会计凭证</a></dd>
							<dd><a href="${proxy.namespace}/frmClosePeriod!index.do">期末结账</a></dd>
							<dd><a href="${proxy.namespace}/frmLedger!index.do">总账</a></dd>
							<dd><a href="${proxy.namespace}/frmLedger!subledger.do">明细账</a></dd>
							<dd><a href="${proxy.namespace}/frmLedger!cashjournal.do">现金日记账</a></dd>
							<dd><a href="${proxy.namespace}/frmLedger!depositjournal.do">银行日记账</a></dd>
							<dd><a href="${proxy.namespace}/frmHistroy!initialization.do">财务余额初始化</a></dd>
							<dd><a href="${proxy.namespace}/jzkm!initBalance.do">财务初始余额</a></dd>
							<dd><a href="${proxy.namespace}/frmHistroy!equivalent.do">试算平衡</a></dd>
							<dd><a href="${proxy.namespace}/frmHistroy!index.do">历史数据</a></dd>
						</dl>
						<dl>
							<dt>报表</dt>
							<dd><a href="${proxy.namespace}/frmReport!index.do">财务报表</a></dd>
							<dd><a href="${proxy.namespace}/frmBalance!index.do">科目余额表</a></dd>
							<dd><a href="${proxy.namespace}/frmBalance!vousummary.do">凭证汇总表</a></dd>
						</dl>
						<dl class="set">
							<dt>设置</dt>
							<dd><a href="${proxy.namespace}/currencyType!list.do">币种汇率</a></dd>
							<dd><a href="${proxy.namespace}/jzkm!list.do">会计科目</a></dd>
							<dd><a href="${proxy.namespace}/jzsummary!list.do">摘要编辑</a></dd>
							<dd><a href="${proxy.namespace}/company!edit.do">公司信息</a></dd>
							<dd><a href="${proxy.namespace}/fzhsContent!list.do">辅助核算</a></dd>
						</dl>
					</div>
				</div>
			</li>
		</ul>
	</nav>
</header>