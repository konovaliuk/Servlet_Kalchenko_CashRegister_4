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
<h1>Система <fmt:message key="title"/>. <fmt:message key="check.title"/></h1>

	<c:if test="${sessionScope.user.idUserType == '3'}">
		<h4>
		   <c:if test="${not empty addedCheck and addedCheck eq 'true'}">
		      Чек <fmt:message key="check.success"/>!
		   </c:if>
		   <c:if test="${not empty addedCheck and addedCheck eq 'false'}">
		      <fmt:message key="check.error"/>!
		   </c:if>
		  <!-- <c:if test="${not empty addedCheckSpec}">
		      Товар с кодом ${addedCheckSpec} успешно добавлен!
		   </c:if> -->
		   <c:if test="${not empty goodCodeNotFound}"><fmt:message key="check.code"/> ${goodCodeNotFound} <fmt:message key="check.codenotfound"/>!</c:if>
		   <c:if test="${not empty goodNameNotFound}"><fmt:message key="check.goodname"/> ${goodNameNotFound} <fmt:message key="check.namenotfound"/>!</c:if>
		</h4>
		<FORM method="POST" name="addcheck">
			<table>
				<tr>
					<td style="text-align: left"><fmt:message key="check.goodname"/>:</td>
					<td><input id="id_name" name="xname" type="text" size="45" autocomplete="on"/></td>
				</tr>
				<tr>
					<td style="text-align: left"><fmt:message key="check.code"/>:</td>
					<td><input id="id_code" name="xcode" type="number" size="4"/></td>
				</tr>
				<tr>
					<td style="text-align: left"><fmt:message key="check.quant"/>:</td>
					<td><input name="quant" type="number" value="0" min="0" step="0.1" required /></td>
				</tr>
				<tr>
					<td style="text-align: left"><fmt:message key="check.nds"/>:</td>
					<td><input name="nds" type="number" value="20"/></td>
				</tr>
				<tr>
					<td style="text-align: left"><fmt:message key="check.price"/>:</td>
					<td><input name="price" type="number" min="0" step="0.01" required /></td>
				</tr>
				<tr>
					<td><input type="submit" class="button" name="btnAddCheckspec" value=<fmt:message key="check.addgood"/> />	</td>
				</tr>
			</table>
		</FORM>
		<FORM method="POST">
			<table>
				<tr>
					<td><input type="submit" class="button" name="btnCreateCheck" value=<fmt:message key="check.createcheck"/> />	</td>
				</tr>
			</table>
		</FORM>
		<FORM method="POST">
			<table>
				<tr>
					<td><input type="submit" class="button" name="btnCancelCheck" value=<fmt:message key="check.cancelcheck"/> />	</td>
				</tr>
			</table>
		</FORM>
		 <c:if test="${sessionScope.checkspecs.size() > '0'}">
			<table class="table-border">
				<tr>
					<th><fmt:message key="check.num"/></th>
					<th><fmt:message key="check.goodname"/></th>
					<th><fmt:message key="check.code"/></th>
					<th><fmt:message key="check.quant"/></th>
					<th><fmt:message key="check.price"/></th>
					<th><fmt:message key="check.nds"/></th>
					<th><fmt:message key="check.ndstotal"/></th>
					<th><fmt:message key="check.total"/></th>
				</tr>			
				<c:forEach items="${sessionScope.checkspecs}" var ="checkspecs" varStatus="counter">
					<tr>
						<td>${counter.count}</td>
						<td>${checkspecs.xname}</td>
						<td>${checkspecs.xcode}</td>
						<td>${checkspecs.quant}</td>
						<td>${checkspecs.price}</td>
						<td>${checkspecs.nds}</td>
						<td>${checkspecs.ndstotal}</td>
						<td>${checkspecs.total}</td>
					</tr>	
				</c:forEach>
			</table>
		</c:if>
	</c:if>
	<script type="text/javascript">
    	<%@include file="/js/script.js"%>
	</script>
</body>
</html>