<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <script type="text/javascript" src="script/validation.js"></script>-->

<link rel="stylesheet" type="text/javascript" href="<c:url value="/resources/script/validation.js"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/newsStyle.css"/>" />


<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.message.welcome" var="welcome"/>
<fmt:message bundle="${loc}" key="local.header.newstitle" var="newstitle"/>

<title>${newstitle}</title>

</head>
<body>
	<div class="page">
	
		<div class="header">
			<c:import url="/WEB-INF/pages/tiles/header.jsp" />
		</div>
		
		<div class="base-layout-wrapper">
			<div class="menu">
				<c:if test="${not (sessionScope.userStatus eq  'active')}">
					<c:out value="${welcome}" />
				</c:if>
				<c:if test="${sessionScope.userStatus eq 'active'}">
					<c:import url="/WEB-INF/pages/tiles/menu.jsp" />
				</c:if>
			</div>
	
			<div class="content">
				<c:if test="${not (sessionScope.userStatus eq 'active')}">
					<c:import url="/WEB-INF/pages/tiles/guestInfo.jsp" />
				</c:if>
				<c:if test="${sessionScope.userStatus eq 'active'}">
					<c:import url="/WEB-INF/pages/tiles/news.jsp" />
				</c:if>
			
			</div>
		
		</div>		
		
		<div class="footer">
			<c:import url="/WEB-INF/pages/tiles/footer.jsp" />
		</div>
		
	</div>
</body>
</html>