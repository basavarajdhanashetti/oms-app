<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${sessionScope.Session_Role =='admin'}">
		<jsp:include page="admin_roles.jsp"></jsp:include>
	</c:when>
	<c:when test="${sessionScope.Session_Role =='Employee'}">
		<jsp:include page="employee_roles.jsp"></jsp:include>
	</c:when>
	
</c:choose>
