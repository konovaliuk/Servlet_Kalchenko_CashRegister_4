<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:if test="${not empty param.language}">
  <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="contentBundle" />

<nav>
	<ul class="topmenu">
	
		<c:if test="${not empty sessionScope.user}">
			  <li><a class="down">${sessionScope.user.name}</a>
			  <c:if test="${sessionScope.user.idUserType == '4'}">
			  	<li><a href="goods" class="down"><fmt:message key="menu.goods"/></a>
			  </c:if>
			  <c:if test="${sessionScope.user.idUserType == '3' || sessionScope.user.idUserType == '2'}">			  
			  	<li><a href="check" class="down"><fmt:message key="menu.check"/></a>
			  </c:if>
		      <li><a href="logout" class="down"><fmt:message key="menu.logout"/></a>
		</c:if>
	    <c:if test="${empty sessionScope.user}">
		      <li><a href="login" class="down"><fmt:message key="menu.enter"/></a>
		</c:if>		
	    <li><a class="down"><fmt:message key="lang"/></a>
	      <ul class="submenu">
	        <li><a href="?lang=ru" >Русский</a></li>
	        <li><a href="?lang=ua" >Українська</a></li>
	        <li><a href="?lang=en" >English</a></li>
	      </ul>
	    </li>
	</ul>
</nav>