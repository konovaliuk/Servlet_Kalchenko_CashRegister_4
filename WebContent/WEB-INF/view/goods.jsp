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
<h1><fmt:message key="title"/>. <fmt:message key="goods.title"/></h1>

	<c:if test="${sessionScope.user.idUserType == '4'}">
		<h4>
		   <c:if test="${not empty addedGood}">
		      <fmt:message key="menu.goods"/> ${addedGood} <fmt:message key="check.success"/>!
		   </c:if>
		   <c:if test="${not empty existsCode}">
		      <fmt:message key="check.code"/> ${existsCode} <fmt:message key="goods.exists"/>!
		   </c:if>
		   <c:if test="${not empty existsName}">
		      <fmt:message key="check.goodname"/> ${existsName} <fmt:message key="goods.exists"/>!
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
					<td><select name="measure" size="1">
						<option value="кг"><fmt:message key="goods.kilo"/></option>
						<option value="шт"><fmt:message key="goods.pc"/></option>
						</select>
					</td>
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
		
		<table class="table-border">
			<tr>
				<th><fmt:message key="check.num"/></th>
				<th><fmt:message key="check.goodname"/></th>
				<th><fmt:message key="check.code"/></th>
				<th><fmt:message key="check.quant"/></th>
				<th><fmt:message key="goods.measure"/></th>
				<th><fmt:message key="goods.comments"/></th>
			</tr>			
			<c:forEach items="${requestScope.viewGoods}" var ="product" varStatus="counter">
				<c:set var="count" value="${(currentPage - 1) * 10 + counter.count}" />
				<tr>
					<td>${count}</td>
					<td>${product.name}</td>
					<td>${product.code}</td>
					<td>${product.quant}</td>
					<td>${product.measure}</td>
					<td>${product.comments}</td>
				</tr>	
			</c:forEach>
		</table>
	    <div class="pagination">
        	<c:if test="${currentPage != 1}">
       			<td><a href="?page=${currentPage - 1}">«</a></td>
   			</c:if>
            <c:forEach begin="1" end="${maxPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage == i}">
                        <td><a>${i}</a></td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="?page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
           	<c:if test="${currentPage < maxPages}">
        		<td><a href="?page=${currentPage + 1}">»</a></td>
   			</c:if>
		</div>
	</c:if>
	</body>
</html>