<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="menustep" value="1" scope="page"/>
<c:set var="count" value="1" scope="page"/>

<c:forEach var="menu" begin="${applicationScope.index}"  items="${menuList}" varStatus="s" step="${pageScope.menustep}">
	<c:if test="${menu.F_PARENT==requestScope.parent}">
	    <c:set var="count" value="${pageScope.count+1}" scope="page"/>    
	
	    <c:set var="index" value="${applicationScope.index+1}" scope="application"/>
	    <c:if test="${menu.F_MX==1}">
		  <li><a href="demo/pagination/list1.html" target="ajax" rel="jbsxBox">${menu.MENU_MC} mxa ${applicationScope.index} treeNode</a></li>
		</c:if>
		<c:if test="${menu.F_MX==0}">
		  <li><a href="demo/pagination/list1.html" target="ajax" rel="jbsxBox">${menu.MENU_MC} fmxa ${applicationScope.index} treeNode</a>
		  <ul>
		  <c:set var="parent" value="${menu.MENU_BH}" scope="request"/>
		  <c:set var="menustep" value="1" scope="page"/>
		  	<c:import url="treeNode.jsp"/>
		  </ul>
		  </li>
		</c:if>
    </c:if>
    <c:if test="${menu.F_PARENT!=requestScope.parent}">
       <c:set var="menustep" value="${pageScope.count}" scope="page"/>
    </c:if>
</c:forEach>