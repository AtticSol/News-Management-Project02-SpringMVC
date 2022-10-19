<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.registration.title" var="registrationTitle" />
<fmt:message bundle="${loc}" key="local.registration.name" var="name" />
<fmt:message bundle="${loc}" key="local.registration.name.placeholder" var="namePlaceholder" />
<fmt:message bundle="${loc}" key="local.registration.username" var="username" />
<fmt:message bundle="${loc}" key="local.registration.username.placeholder" var="usernamePlaceholder" />
<fmt:message bundle="${loc}" key="local.registration.password" var="password" />
<fmt:message bundle="${loc}" key="local.registration.password.placeholder" var="passwordPlaceholder" />
<fmt:message bundle="${loc}" key="local.registration.confirmpassword" var="confirmPassword" />
<fmt:message bundle="${loc}" key="local.registration.confirmpassword.placeholder" var="confirmPasswordPlaceholder" />
<fmt:message bundle="${loc}" key="local.registration.email" var="email" />
<fmt:message bundle="${loc}" key="local.registration.email.placeholder" var="emailPlaceholder" />
<fmt:message bundle="${loc}" key="local.registration.sign.up" var="signup" />
<fmt:message bundle="${loc}" key="local.message.error.registration" var="registrationErrorMessage" />
<fmt:message bundle="${loc}" key="local.registration.button.main" var="backToMain" />

<style>
.redWarning {
	font-size: 70%;
	color: red;
}
</style>

<div id="form">
	<h1>${registrationTitle}</h1><br />

	<form:form action="do_registration" modelAttribute="registrationData">

 		<div class="inline_blocks">
			<div class="block1">
				<div class="title">${name}:</div>
			</div>
			<div class="block2">
				<input type="text" name="name" value="" placeholder="${namePlaceholder}"  class="text" />
			</div>
		</div>
		
		<div class="inline_blocks">
			<div class="block1">
				<div class="title">${username}:</div>
			</div>
			<div class="block2">
				<form:input placeholder="${usernamePlaceholder}" path="login" class="text" />
			</div>
		</div>

		<div class="inline_blocks">
			<div class="block1">
				<div class="title">${password}:</div>
			</div>
			<div class="block2">
				<form:password placeholder="${passwordPlaceholder}" path="password" class="text" />
			</div>
		</div>
			
		<div class="inline_blocks">
			<div class="block1">
				<div class="title">${email}:</div>
			</div>
			<div class="block2">
				<input type="text" name="email" value="" placeholder="${emailPlaceholder}" class="text" />
			</div>
		</div>
		
		<div class="button">
			<input type="submit" value="${signup}" />
		</div>
	</form:form>

	<form:form action="${pageContext.request.contextPath}/command/go_to_base_page" method="get">
		<input type="hidden" name="userStatus" value="not_active" />
		<div class="button">
			<input type="submit" value="${backToMain}" />
		</div>
	</form:form>
</div>
