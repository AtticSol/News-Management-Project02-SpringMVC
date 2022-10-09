<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.menu.href.newslist" var="newsListHref" />
<fmt:message bundle="${loc}" key="local.news.latest" var="latest" />
<fmt:message bundle="${loc}" key="local.message.nonews" var="noNews" />
<fmt:message bundle="${loc}" key="local.registration.button.main" var="backToMain" />


<c:if test="${not (sessionScope.userStatus eq 'registration')}">
	<c:if test="${not (sessionScope.userStatus eq 'logination')}">
		<div class="body-title">
			<a href="">${newsListHref} >> </a> ${latest}
		</div>
		<c:forEach var="news" items="${requestScope.news}">
			<div class="single-news-wrapper">
				<div class="single-news-header-wrapper">
					<div class="news-title">
						<c:out value="${news.title}" />
					</div>
					<div class="news-date">
						<c:out value="${news.newsDate}" />
					</div>
					<div class="news-content">
						<c:out value="${news.briefNews}" />
					</div>
				</div>
			</div>
		</c:forEach>

		<div class="no-news">
			<c:if test="${requestScope.news eq null}">
        		${noNews}
			</c:if>
		</div>
	</c:if>

	<c:if test="${sessionScope.userStatus eq 'logination'}">
		<form:form 	action="${pageContext.request.contextPath}/command/go_to_base_page" method="get">
			<input type="hidden" name="userStatus" value="not_active" />
			<div class="button">
				<input type="submit" value="${backToMain}" />
			</div>
		</form:form>
	</c:if>
	
</c:if>

<c:if test="${sessionScope.userStatus eq 'registration'}">
	<c:import url="/WEB-INF/pages/tiles/registration.jsp" />
</c:if>