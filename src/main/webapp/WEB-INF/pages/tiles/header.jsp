<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<style>
.redWarning {
	font-size: 80%;
	color: red;
}
</style>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.header.newstitle" 	var="newstitle" />
<fmt:message bundle="${loc}" key="local.header.login" var="login" />
<fmt:message bundle="${loc}" key="local.header.password" var="password" />
<fmt:message bundle="${loc}" key="local.header.href.registration" 	var="registration" />
<fmt:message bundle="${loc}" key="local.header.href.logination" 	var="logination" />
<fmt:message bundle="${loc}" key="local.header.locbutton.href.en" 	var="en_button" />
<fmt:message bundle="${loc}" key="local.header.locbutton.href.ru"	var="ru_button" />
<fmt:message bundle="${loc}" key="local.header.button.sign.in" var="in" />
<fmt:message bundle="${loc}" key="local.header.button.sign.out"	var="out" />
<fmt:message bundle="${loc}" key="local.message.error.session"	var="sessionError" />
<fmt:message bundle="${loc}" key="local.message.error.authentication"	var="authenticationErrorMessage" />
<fmt:message bundle="${loc}" key="local.message.error.access"	var="accessError" />


<div class="wrapper">
	<div class="newstitle">${newstitle}</div>

	<div class="local-link">

		<div align="right"><br />
			<a href="${pageContext.request.contextPath}/local/do_change_local?local=en">${en_button}</a>&nbsp;&nbsp;
			<a href="${pageContext.request.contextPath}/local/do_change_local?local=ru">${ru_button}</a><br />
		</div>

		<c:if test="${not (sessionScope.userStatus eq 'active')}">
			<c:if test="${not (sessionScope.userStatus eq 'registration')}">
				<div align="right">

					<%-- <c:if test="${not (requestScope.sessionError eq null)}">
							<font class="redWarning"> ${sessionError} <br />
							</font>
						</c:if>

						<c:if test="${not (requestScope.accessError eq null)}">
							<font class="redWarning"> ${accessError} <br />
							</font>
						</c:if>--%>

					<c:if test="${not (sessionScope.userStatus eq 'logination')}">
						<a href="${pageContext.request.contextPath}/registration/go_to_registration_page">${registration}</a>&nbsp;&nbsp;
						<a href="${pageContext.request.contextPath}/login/logination_form">${logination}</a>

					</c:if>

					<c:if test="${sessionScope.userStatus eq 'logination'}">
					
						<c:if test="${not (authenticationError eq null)}">
							<font class="redWarning"> ${authenticationErrorMessage} <br /></font>
						</c:if>
					
						<form:form action="do_sign_in" method="get" modelAttribute="loginData">
							${login}: <form:input path="login" /><br />
							${password}: <form:password path="password" /><br />
							<input type="submit" value="${in}" /><br />
						</form:form>
					</c:if>
					
				</div>
			</c:if>
		</c:if>

		<c:if test="${sessionScope.userStatus eq 'active'}">
			<div align="right">
				<form:form action="${pageContext.request.contextPath}/login/do_sign_out" method="post">
					<input type="submit" value="${out}" /><br />
				</form:form>
			</div>
		</c:if>


	</div>
</div>