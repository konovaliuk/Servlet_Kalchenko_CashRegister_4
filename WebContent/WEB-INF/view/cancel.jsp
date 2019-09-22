<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
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
<h1><fmt:message key="title"/>. <fmt:message key="cancel.title"/>.</h1>

	<c:if test="${sessionScope.user.idUserType == '2'}">
		<FORM action="cancel" method="POST">
			<table>
				<tr>
					<td style="text-align: left"><fmt:message key="check.number"/>:</td>
					<td><input name="checkid" type="number" min="1" placeholder="<fmt:message key="cancel.search"/>" autofocus required /></td>
				</tr>
				<tr>
					<td><input type="submit" class="button" name="btnSearchCheck" value=<fmt:message key="cancel.find"/> /> </td>
				</tr>
			</table>
		</FORM>
		<c:if test="${sessionScope.check.canceled == '1'}"><p><fmt:message key="check.check"/> № ${sessionScope.check.id} <fmt:message key="cancel.canceled"/>!</p></c:if>
		<c:if test="${not empty sessionScope.checkfind}">
		     <p><fmt:message key="cancel.findfail"/>!</p>
		</c:if>
		<c:if test="${sessionScope.check.canceled == '0' and not empty checkspecs}">
			<p><fmt:message key="check.check"/> № ${sessionScope.check.id} на сумму ${sessionScope.check.total}</p>
			<table class="table-border">
				<tr>
					<th><fmt:message key="check.num"/></th>
					<th><fmt:message key="check.goodname"/></th>
					<th><fmt:message key="check.code"/></th>
					<th><fmt:message key="check.quant"/></th>
					<th><fmt:message key="check.price"/></th>
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
						<td>${checkspecs.ndstotal}</td>
						<td>${checkspecs.total}</td>
						<c:if test="${checkspecs.canceled == '1'}">
							<td><fmt:message key="cancel.item"/></td>
						</c:if>
					</tr>	
				</c:forEach>
			</table>
			<FORM action="cancel" method="POST">
				<table>
					<tr>
						<td><input type="submit" class="button" name="btnCancelCheck" value=<fmt:message key="cancel.check"/> /></td>
					</tr>
					<tr>
						<td style="text-align: left">№ <fmt:message key="cancel.numitem"/>:</td>
						<td><input name="checkspecnum" type="number" min="1"/></td>
					</tr>
					<tr>
						<td><input type="submit" class="button" placeholder="Поиск по № спецификации" name="btnCancelCheckspec"
							value=<fmt:message key="cancel.btnitem"/> />
						</td>
					</tr>
				</table>
			</FORM>
		</c:if>
		<FORM action="cancel" method="POST">
			<table>
				<tr>
					<td style="text-align: left"><fmt:message key="gen"/> <fmt:message key="xreport"/>:</td>
					<td><input type="submit" class="button" name="btnXReport"value=<fmt:message key="xreport"/> />	</td>
				</tr>
				<tr>
					<td style="text-align: left"><fmt:message key="gen"/> <fmt:message key="zreport"/>:</td>
					<td><input type="submit" class="button" name="btnZReport"value=<fmt:message key="zreport"/> />	</td>
				</tr>
			</table>
		</FORM>	
		
	</c:if>
</body>
</html>