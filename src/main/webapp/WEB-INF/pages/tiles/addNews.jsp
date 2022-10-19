<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.menu.href.newslist" var="newsListHref" />
<fmt:message bundle="${loc}" key="local.news.add" var="add" />
<fmt:message bundle="${loc}" key="local.news.newstitle" var="newsTitle" />
<fmt:message bundle="${loc}" key="local.news.newstitle.placeholder" var="titlePlaceholder" />
<fmt:message bundle="${loc}" key="local.news.newsdate" var="newsDate" />
<fmt:message bundle="${loc}" key="local.news.newsbrief" var="newsBrief" />
<fmt:message bundle="${loc}" key="local.news.newsbrief.placeholder" var="briefPlaceholder" />
<fmt:message bundle="${loc}" key="local.news.newscontent" var="content" />
<fmt:message bundle="${loc}" key="local.news.newscontent.placeholder" var="contentPlaceholder" />
<fmt:message bundle="${loc}" key="local.news.button.save" var="save" />
<fmt:message bundle="${loc}" key="local.news.button.cancel" var="cancel" />

<style>
.redWarning {
	font-size: 70%;
	color: red;
}
</style>

<div class="body-title">
	<a href="">${newsListHref} >> </a> ${add} 
</div>

<br />


<c:if test="${presentation eq 'addNews' }">

	<form:form action="${pageContext.request.contextPath}/news/do_add_news"
			   modelAttribute="newsData"
			   method="post">
			   
			   
		<div class="add-table-margin">
			<table class="news_text_format">
			
				<c:if test="${presentation eq 'addNews' }">
				
					<tr>
						<td class="space_around_title_tex">${newsTitle}</td>
						<td class="space_around_view_text">
							<form:input class="text" path="title" placeholder="${titlePlaceholder}" />
						</td>
					</tr>

					<tr>
						<td class="space_around_title_tex">${newsBrief}</td>
						<td class="space_around_view_text">
							<form:textarea path="briefNews" cols="58" rows="3" placeholder="${briefPlaceholder}" />
						</td>
					</tr>

					<tr>
						<td class="space_around_title_tex">${content}</td>
						<td class="space_around_view_text">
							<form:textarea 	path="content" cols="58" rows="5" placeholder="${contentPlaceholder}" />
						</td>
					</tr>
					
				</c:if>
				
			</table>
			<div class="first-view-button">
				<input type="submit" value="${save}" />
			</div>
		</div>
	</form:form>
</c:if>

<c:if test="${presentation eq 'editNews' }">
	<form:form action="${pageContext.request.contextPath}/news/do_edit_news"
			   modelAttribute="newsData"
			   method="post">

		<div class="add-table-margin">
			<table class="news_text_format">

				<c:if test="${presentation eq 'editNews' }">
				
					<tr>
						<td class="space_around_title_tex">${newsTitle}</td>
						<td class="space_around_view_text">
							<form:input class="text" path="title" value="${title}" />
						</td>
					</tr>

					<tr>
						<td class="space_around_title_tex">${newsDate}</td>
						<td class="space_around_view_text">
							<input type="date" name="date" value="" />
						</td>
					</tr>



					<tr>
						<td class="space_around_title_tex">${newsBrief}</td>
						<td class="space_around_view_text">
							<form:textarea path="briefNews" cols="58" rows="3" value="${briefNews}" />
						</td>
					</tr>

					<tr>
						<td class="space_around_title_tex">${content}</td>
						<td class="space_around_view_text">
					 		<form:textarea path="content" cols="58" rows="5" value="${content}" />
					 	</td>
					</tr>
					
				</c:if>

			</table>

			<div class="first-view-button">
				<form:hidden path="newsID" value="${newsID}" />
				<input type="hidden" name="pageNumber" value="${pageNumber}" />
				<input type="submit" value="${save}" />
			</div>
		</div>
	</form:form>
</c:if>

<div class="second-view-button">
	<form:form action="${pageContext.request.contextPath}/news/do_cancel"
			   modelAttribute="newsData"
			   method="post">

		<form:hidden path="newsID" value="${newsID}" />
		<input type="hidden" name="previousPresentation" value="${previousPresentation}" />
		<input type="hidden" name="pageNumber" value="${pageNumber}" />
		<input type="submit" value="${cancel}" />

	</form:form>

</div>
