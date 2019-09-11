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
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <title><fmt:message key="title"/></title>
	<style>	
	    <%@include file="/css/style.css"%>
	</style>
 </head> 
<body>
<jsp:include page="header.jsp" flush="true" />
<h1>Система <fmt:message key="title"/>. <fmt:message key="goods.title"/></h1>

	<c:if test="${sessionScope.user.idUserType == '4'}">
		<h4>
		   <c:if test="${not empty addedGood}">
		      Товар ${addedGood} <fmt:message key="check.success"/>!
		   </c:if>
		   <c:if test="${not empty code}">
		      <fmt:message key="check.code"/> ${code} <fmt:message key="goods.exists"/>!
		   </c:if>
		</h4>
		<FORM method="POST">
			<table>
				<tr>
					<td style="text-align: left"><fmt:message key="check.goodname"/>:</td>
					<td><input name="name" type="text" size="45" required /></td>
				</tr>
				<tr>
					<td style="text-align: left"><fmt:message key="check.code"/>:</td>
					<td><input name="code" type="number" size="4" autofocus required /></td>
				</tr>
				<tr>
					<td style="text-align: left"><fmt:message key="check.quant"/>:</td>
					<td><input name="quant" type="number" value="0" min="0" step="0.1" required /></td>
				</tr>
				<tr>
					<td style="text-align: left"><fmt:message key="goods.measure"/>:</td>
					<td><input name="measure" type="text" size="45" required /></td>
				</tr>
				<tr>
					<td style="text-align: left"><fmt:message key="goods.comments"/>:</td>
					<td><input name="comments" type="text" size="45" /></td>
				</tr>
				<tr>
					<td><input type="submit" class="button" name="btnSaveGood" value="<fmt:message key="goods.add"/>" />
					</td>
				</tr>
			</table>
		</FORM>
	</c:if>
	</body>
</html>