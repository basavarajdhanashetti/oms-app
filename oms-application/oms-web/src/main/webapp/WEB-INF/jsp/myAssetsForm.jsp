<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
								<div class="panel-heading">
									Request Details
									<div class="pull-right">
										<div class="btn-group">
											<button type="button"
												class="btn btn-default btn-xs dropdown-toggle"
												data-toggle="dropdown">
												Actions <span class="caret"></span>
											</button>
											<ul class="dropdown-menu pull-right" role="menu">
												<li><a
													href="${pageContext.request.contextPath}/requests/new">New
												</a></li>

											</ul>
										</div>
									</div>
								</div>
								<div class="panel-body">
									<table class="table" id="dataTables-example">
										<thead>
											<tr>
												<td>Request No</td>
												<td>Category</td>
												<td>Sub Category</td>
												<td>Product</td>
												<td>Requested On</td>
												<td>Status</td>

											</tr>
										</thead>
										<tbody>

											<c:forEach items="${myRequsets}" var="req">
												<tr>
													<td><a
														href="${pageContext.request.contextPath}/requests/${req.id}/details">${req.requestNo}</a></td>
													<td>${req.product.productSubCategory.productCategory.name}</td>
													<td>${req.product.productSubCategory.name}</td>
													<td>${req.product.name}</td>
													<td><fmt:formatDate value="${req.requestDate}"
															pattern="yyyy/MM/dd HH:mm" /></td>
													<td><c:choose>
															<c:when test="${req.status == 'SAVED'}">Submitted</c:when>
															<c:when test="${req.status == 'PENDING_APPROVAL'}">Pending Approval</c:when>
															<c:when test="${req.status == 'APPROVED'}">Approved</c:when>
															<c:when test="${req.status == 'REJECTED'}">Rejected</c:when>
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
