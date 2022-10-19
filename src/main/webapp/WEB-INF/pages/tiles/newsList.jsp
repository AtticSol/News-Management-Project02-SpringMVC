<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.menu.href.newslist" var="newsListHref" />
<fmt:message bundle="${loc}" key="local.menu.newslist" var="newslist" />
<fmt:message bundle="${loc}" key="local.news.href.view" var="view" />
<fmt:message bundle="${loc}" key="local.news.href.edit" var="edit" />
<fmt:message bundle="${loc}" key="local.news.button.delete" var="delete" />
<fmt:message bundle="${loc}" key="local.message.nonews" var="noNews" />
<fmt:message bundle="${loc}" key="local.news.add.error.delete" var="noNewsToDeleteMessage" /> 
<fmt:message bundle="${loc}" key="local.message.popup.delete" var="deleteMessage" />

<style>
.redWarning {
	font-size: 70%;
	color: red;
}
</style>

<div class="body-title">
	<a href="go_to_news_list">${newsListHref} >> </a> ${newslist}
</div>

<form:form
	action="${pageContext.request.contextPath}/news/do_delete_news"
	modelAttribute="newsList" method="post">
	
	
	&nbsp;&nbsp;

	<c:forEach var="page" items="${page}">
		<a href="${pageContext.request.contextPath}/news/go_to_news_list?pageNumber=${page}">${page}</a>
	</c:forEach>



	<c:forEach var="news" items="${newsList}">
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
				<div class="news-link-to-wrapper">

					<div class="link-position">
						<a href="go_to_view_news?newsId=${news.newsID}&pageNumber=${pageNumber}">${view}</a>&nbsp;

						<c:if test="${not (sessionScope.role eq  'user')}">
							<a href="go_to_edit_news?newsId=${news.newsID}&previousPresentation=${requestScope.presentation}&pageNumber=${pageNumber}">
								${edit}
							</a>
						</c:if>

						&nbsp;
						<c:if test="${sessionScope.role eq 'admin'}">
							<input type="checkbox" name="newsId" value="${news.newsID}" />
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<br />
	</c:forEach>

	<c:if test="${sessionScope.role eq 'admin'}">
		<div class="delete-button-position">
			<c:if test="${not (noNewsToDelete eq null)}">
				<font class="redWarning"> ${noNewsToDeleteMessage} </font>
			</c:if>
			<input type="submit" value="${delete}" onclick="if (!(confirm('${deleteMessage}'))) return false" />
		</div>
	</c:if>



	<div class="no-news">
		<c:if test="${newsList eq null}">
        		${noNews}
		</c:if>
	</div>
</form:form>


