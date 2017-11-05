<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>SB Admin 2 - Bootstrap Admin Theme</title>

<!-- Bootstrap Core CSS -->
<link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

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
							<c:if test="${purchaseReqForm.id==0}">
								<h1 class="page-header">New Purchase Request</h1>
							</c:if>
							<c:if test="${purchaseReqForm.id!=0}">
								<h1 class="page-header">Modify Purchase Request</h1>
							</c:if>

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
												<td>Process</td>
												<td>CreatedBy</td>
												<td>Creation Date</td>
												<td>Actions</td>
											</tr>
										</thead>
										<tbody>

											<c:forEach items="${tasks}" var="task">
												<tr>
													<td>${task.id}</td>
													<td>${task.name}</td>
													<td>${task.status}</td>
													<td>${task.processId}</td>
													<td>${task.createdBy}</td>
													<td>${task.createdOn}</td>
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
																	href="${pageContext.request.contextPath}/purchases/${task.id}">
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

	<!-- jQuery -->
	<script src="../vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../dist/js/sb-admin-2.js"></script>

	<!--Project custom JavaScript -->
	<script src="<c:url value="../oms/oms-rest-client.js"/>"></script>

	<!-- Datepicker related JavaScript -->
	<script src="<c:url value="../datepicker/js/bootstrap-datepicker.js"/>"></script>

	<script type="text/javascript">
		$(function() {
			$('#datetimepicker1 input').datepicker({
				format : "yyyy/mm/dd",
				clearBtn : true,
				autoclose : true,
				todayHighlight : true
			});
		});
	</script>

</body>

</html>
