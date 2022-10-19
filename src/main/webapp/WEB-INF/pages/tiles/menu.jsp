<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.menu.href.newslist"
	var="newsListHref" />
<fmt:message bundle="${loc}" key="local.menu.newslist" var="newsList" />
<fmt:message bundle="${loc}" key="local.menu.addnews" var="addNews" />

<div class="menu-wrapper">
	<div class="menu-title-wrapper">
		<div class="menu-title">${newsListHref}</div>
	</div>
	

	<div class="list-menu-invisible-wrapper">
		<div class="list-menu-wrapper" style="float: right;">
			<c:if test="${sessionScope.userStatus eq 'active'}">
				<ul style="list-style-image: url(${pageContext.request.contextPath}/resources/images/img.jpg); text-align: left;">
					
					<li style="padding-left: 15px;">
						<a href="${pageContext.request.contextPath}/news/go_to_news_list">${newsList}</a><br />
					</li>

					<c:if test="${not (sessionScope.role eq  'user')}">
						<li style="padding-left: 15px;">
							<a href="${pageContext.request.contextPath}/news/go_to_add_news?previousPresentation=newsList&pageNumber=${pageNumber}">
								${addNews}
							</a><br />
						</li>
					</c:if>

				</ul>
			</c:if>
		</div>
		<div class="clear"></div>
	</div>

	<div style="height: 25px;"></div>
</div>
