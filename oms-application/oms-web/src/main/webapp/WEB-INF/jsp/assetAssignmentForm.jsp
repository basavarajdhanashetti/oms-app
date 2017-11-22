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


<jsp:include page="styleAndCss.jsp"></jsp:include>

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<jsp:include page="navigation.jsp"></jsp:include>


		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Asset Request Details</h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->

				<form:form commandName="assetRequest" id="request-form">
					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">Request Details</div>
								<div class="panel-body">
									<form:hidden path="id" />
									<form:hidden path="idProduct" />
									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label>Category</label>
												${assetRequest.product.productSubCategory.productCategory.name }
											</div>
										</div>

										<div class="col-lg-6">
											<div class="form-group">
												<label>Sub Category</label>
												${assetRequest.product.productSubCategory.name }
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label>Product</label> ${assetRequest.product.name }
											</div>
										</div>

										<div class="col-lg-6">
											<div class="form-group">
												<label>Quantity</label> ${assetRequest.requestedCount }
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label>Requestor</label> ${assetRequest.userId }
											</div>
										</div>


									</div>

									<div class="row">
										<div class="col-lg-12">
											<button type="submit" class="btn btn-primary"
												formaction="${pageContext.request.contextPath}/requests/search/products"
												formmethod="get">Search</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- /.col-lg-12 -->
					</div>
				</form:form>

				<c:if test="${formSelection != null }">
					<form:form commandName="formSelection" id="search-form">
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">
									<div class="panel-heading">Request Details</div>
									<div class="panel-body">
										<form:hidden path="id" value="${assetRequest.id}" />
										<table id="dataTables-example"
											class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<td>Product Name</td>
													<td>Asset No</td>
													<td>Serial No</td>
													<td>Model</td>
													<td>Assign</td>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${unAllocatedAssets}" var="reg"
													varStatus="loop">
													<tr>
														<td>${reg.product.name}</td>
														<td>${reg.assetNo}</td>
														<td>${reg.serialNo}</td>
														<td>${reg.model}</td>
														<td><form:checkbox path="selectedIds"
																value="${reg.id}" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>

									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">
									<div class="panel-heading">Comments from Approver</div>
									<div class="panel-body">
										<div class="form-group">
											<form:textarea path="comments" cols="50" />
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12">
								<button type="submit" class="btn btn-primary"
									formaction="${pageContext.request.contextPath}/requests/tasks/complete?approval=true"
									formmethod="post">Approve</button>

								<button type="submit" class="btn btn-primary"
									formaction="${pageContext.request.contextPath}/requests/tasks/complete?approval=false"
									formmethod="post">Reject</button>

								<button type="submit" class="btn btn-primary"
									formaction="${pageContext.request.contextPath}/process/tasks"
									formmethod="get">Back</button>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12"></div>
						</div>
					</form:form>
				</c:if>
				<!-- /.row -->

			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

</body>


<!-- jQuery -->
<script src="../vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="../vendor/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="../dist/js/sb-admin-2.js"></script>

<!--Project custom JavaScript -->
<script src="../oms/oms-rest-client.js"></script>

<!-- Datepicker related JavaScript -->
<script src="../datepicker/js/bootstrap-datepicker.js"></script>

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
</html>
