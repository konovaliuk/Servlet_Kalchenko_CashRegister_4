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
<h1>Система <fmt:message key="title"/>. <fmt:message key="registration.title"/>!</h1>

		<FORM method="POST">
			<table style="margin: auto">
				<tr>
					<td style="text-align: left"><fmt:message key="registration.name"/>:</td>
					<td><input name="name" type="text" size="35" placeholder="Имя пользователя" autofocus required /></td>
				</tr>
				<tr>
					<td style="text-align: left">E-mail:</td>
					<td><input name="email" type="email" size="35" placeholder="E-mail" required /></td>
				</tr>
				<tr>
					<td style="text-align: left">Пароль:</td>
					<td><input id="id_password" name="password" type="password" placeholder="Пароль" size="35" maxlength="35" required oninput='check_pass();'/></td>
				</tr>
				<tr>
					<td style="text-align: left"><fmt:message key="registration.confirm"/>:</td>
					<td><input id="id_confirm" name="password" type="password" placeholder="Подтверждение пароля" size="35" maxlength="35" required oninput='check_pass();'/></td>
				</tr>
				<tr>
					<td><input id="id_btnReg" type="submit" class="button" name="btnReg" value=<fmt:message key="registration.reg"/> /></td>
				</tr>
			</table>
		</FORM>
		<script type="text/javascript">
	    	<%@include file="/js/script.js"%>
		</script>
	</body>
</html>