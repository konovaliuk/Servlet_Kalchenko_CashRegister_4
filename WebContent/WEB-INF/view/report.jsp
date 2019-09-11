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

		<c:if test="${not empty sessionScope.user and sessionScope.user.idUserType == '2' and not empty sessionScope.xReport}">
			<p class="reptitle">Х-Звіт</p>	
			<div class="center">						 
			 	<p>Дата друку </p>
			 	<p>${sessionScope.xReport.printtime}</p>
			 	<table style="width: 100%;">
  					<tr>
  						<td>Чеков: </td>
				 		<td class="alignRight">${sessionScope.xReport.countCheck}</td>
				 	</tr>
				 	  <tr>
  						<td>Анульовано: </td>
				 		<td class="alignRight">${sessionScope.xReport.countCancelCheck}</td>
				 	</tr>
			 	</table>
				<table style="margin: auto">
					<tr>
						<th>ПДВ</th><!-- для х-звіта не локализируем -->
						<th>Сума ПДВ</th>
						<th>Сума</th>
					</tr>			
					<c:forEach items="${sessionScope.xReport.detail}" var ="detail" varStatus="counter">
						<tr>
							<td>${detail.nds}</td>
							<td>${detail.ndsTotal}</td>
							<td>${detail.total}</td>
						</tr>
					</c:forEach>
				</table>
				<table style="width: 100%;">	
					<tr>
						<td>Службове внесення: </td>
						<td class="alignRight">0</td>
					</tr>
					<tr>
						<td>Службова видача: </td>
						<td class="alignRight">0</td>
					</tr>
				</table>
				<p>СЛУЖБОВИЙ ЧЕК</p>
				<a class ="print-doc" href="javascript:(print());"> <fmt:message key="report.print"/></a>
			</div>			
		</c:if>
	</body>
</html>