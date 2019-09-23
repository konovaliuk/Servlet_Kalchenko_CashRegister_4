<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<c:if test="${not empty param.language}">
  <c:set var="language" value="${param.language}" scope="session"/>
</c:if>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="contentBundle" />

<!DOCTYPE html>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title><fmt:message key="title"/></title>
	<style>	
	    <%@include file="/css/style.css"%>
	</style>
 </head>
<body>

<jsp:include page="header.jsp" flush="true" />
<h1><fmt:message key="title"/>. <fmt:message key="index.welcome"/></h1>
		<h3><font color="red">
		   <c:if test="${not empty userNotExists and userNotExists eq 'true'}">
		      <fmt:message key="index.nouser"/> <!-- учетная запись отсутствует -->
		   </c:if>
		   </font>
		</h3>
		<c:if test="${empty sessionScope.user}">
			<p>a1@gmail.com<p> <!-- для дебага -->
			<p>a3@gmail.com<p> <!-- для дебага -->
			<FORM method="POST">
				<table style="margin: auto">
					<tr>
						<td style="text-align: left">E-mail:</td>
						<td><input name="email" type="email" size="35" placeholder="E-mail" autocomplete="on" autofocus required /></td>
					</tr>
					<tr>
						<td style="text-align: left"><fmt:message key="registration.password"/>:</td>
						<td><input name="password" type="password" placeholder="<fmt:message key="registration.password"/>" size="35" maxlength="35" required /></td>
					</tr>
					<tr>
						<td><input type="submit" class="button" name="btnLogin" value=<fmt:message key="menu.enter"/> /></td>					
					</tr>
				</table>
			</FORM>
			<div align="center"><a href="registration"><fmt:message key="registration"/></a></div>
		</c:if>
	</body>
</html>