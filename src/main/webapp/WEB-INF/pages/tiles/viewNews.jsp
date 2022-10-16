<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.menu.href.newslist"
	var="newsListHref" />
<fmt:message bundle="${loc}" key="local.news.viewnews" var="viewNews" />
<fmt:message bundle="${loc}" key="local.news.newstitle" var="newsTitle" />
<fmt:message bundle="${loc}" key="local.news.newsdate" var="newsDate" />
<fmt:message bundle="${loc}" key="local.news.newsbrief" var="newsBrief" />
<fmt:message bundle="${loc}" key="local.news.newscontent" var="content" />
<fmt:message bundle="${loc}" key="local.news.button.edit" var="edit" />
<fmt:message bundle="${loc}" key="local.news.button.delete" var="delete" />
<fmt:message bundle="${loc}" key="local.message.popup.delete.one" var="deleteMessage" />




<div class="body-title">
	<a href="">${newsListHref} >> </a> ${viewNews}
</div>

<div class="add-table-margin">
	<table class="news_text_format">
		<tr>
			<td class="space_around_title_text">${newsTitle}</td>

			<td class="space_around_view_text">
				<div class="word-breaker">
					<c:out value="${newsData.title}" />
				</div>
			</td>
		</tr>
		<tr>
			<td class="space_around_title_text">${newsDate}</td>

			<td class="space_around_view_text"><div class="word-breaker">
					<c:out value="${newsData.newsDate}" />
				</div></td>
		</tr>
		<tr>
			<td class="space_around_title_text">${newsBrief}</td>
			<td class="space_around_view_text"><div class="word-breaker">
					<c:out value="${newsData.briefNews}" />
				</div></td>
		</tr>
		<tr>
			<td class="space_around_title_text">${content}</td>
			<td class="space_around_view_text"><div class="word-breaker">
					<c:out value="${newsData.content}" />
				</div></td>
		</tr>
	</table>
</div>


<c:if test="${not (sessionScope.role eq 'user')}">
	<div class="first-view-button">
		<form:form	action="${pageContext.request.contextPath}/news/go_to_edit_news" method="post">
			<input type="hidden" name="newsId" value="${newsData.newsID}" />
			<input type="hidden" name="pageNumber" value="${pageNumber}" />
			<input type="hidden" name="previousPresentation" value="${presentation}" />
			<input type="submit" value="${edit}" />
		</form:form>
	</div>


	<div class="second-view-button" id="delete">
		<form:form action="${pageContext.request.contextPath}/news/do_delete_news" method="post"
				onclick="if (!(confirm('${deleteMessage}'))) return false">
			<input type="hidden" name="newsId" value="${newsData.newsID}" />
			<input type="submit" value="${delete}" />
		</form:form>
	</div>
 </c:if>