<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<jsp:include page="styleAndCss.jsp"></jsp:include>

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<jsp:include page="navigation.jsp"></jsp:include>

		<form:form commandName="purchaseReqForm">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">My Tasks</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">Request Details</div>
								<div class="panel-body">
									<table class="table">
										<thead>
											<tr>
												<td>Task ID</td>
												<td>Task Name</td>
												<td>Task Status</td>
												<td>Description</td>
												<td>CreatedBy</td>
												<td>Created On</td>
												<td>Actions</td>
											</tr>
										</thead>
										<tbody>

											<c:forEach items="${tasks}" var="task">
												<tr>
													<td>${task.id}</td>
													<td>${task.name}</td>
													<td>${task.status}</td>
													<td>${task.description}</td>
													<td>${task.createdBy}</td>
													<jsp:useBean id="dateValue" class="java.util.Date"/>
													<jsp:setProperty name="dateValue" property="time" value="${task.createdOn}"/>
													
													<td><fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd HH:mm"/></td>
													<td><c:choose>

															<c:when test="${task.status eq 'Ready' }">
																<a
																	href="${pageContext.request.contextPath}/process/tasks/${task.id}/claim">
																	Claim </a>
															</c:when>
															<c:when test="${task.status eq 'Reserved' }">
																<a
																	href="${pageContext.request.contextPath}/process/tasks/${task.id}/start">
																	Start </a>
																<br />
																<a
																	href="${pageContext.request.contextPath}/process/tasks/${task.id}/release">
																	Release </a>
															</c:when>
															<c:when test="${task.status eq 'InProgress' }">
																<a
																	href="${pageContext.request.contextPath}/process/tasks/${task.id}/open?taskName=${task.name}">
																	Open </a>
																<br />
																<a
																	href="${pageContext.request.contextPath}/process/tasks/${task.id}/release">
																	Release </a>
															</c:when>
														</c:choose></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

								</div>
							</div>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->

				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /#page-wrapper -->
		</form:form>
	</div>
	<!-- /#wrapper -->

	
</body>

</html>
