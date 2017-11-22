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

		<form:form commandName="assetRequest">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Asset Request Details
								</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">Request Details</div>
								<div class="panel-body">
									<form:hidden path="id" id="Request-ID" />
									<c:choose>
										<c:when test="${viewonly}">

											<div class="row">
												<div class="col-lg-6">
													<div class="form-group">
														<label>Category:</label>
														${assetRequest.product.productSubCategory.productCategory.name }
													</div>
												</div>

												<div class="col-lg-6">
													<div class="form-group">
														<label>Sub Category:</label>
														${assetRequest.product.productSubCategory.name }
													</div>
												</div>
											</div>

											<div class="row">
												<div class="col-lg-6">
													<div class="form-group">
														<label>Product:</label> ${assetRequest.product.name }
													</div>
												</div>

												<div class="col-lg-6">
													<div class="form-group">
														<label>Quantity:</label> ${assetRequest.requestedCount }
													</div>
												</div>
											</div>
											
											<div class="row">
												<div class="col-lg-6">
													<div class="form-group">
														<label>Status:</label>
														<c:choose>
															<c:when test="${assetRequest.status == 'SAVED'}">Submitted</c:when>
															<c:when test="${assetRequest.status == 'PENDING_APPROVAL'}">Pending Approval</c:when>
															<c:when test="${assetRequest.status == 'APPROVED'}">Approved</c:when>
															<c:when test="${assetRequest.status == 'REJECTED'}">Rejected</c:when>
														</c:choose>
														
													</div>
												</div>

												<div class="col-lg-6">
													<div class="form-group">
														<label>Comments:</label> ${assetRequest.comments }
													</div>
												</div>
											</div>

										</c:when>
										<c:otherwise>

											<div class="row">
												<div class="col-lg-6">
													<div class="form-group">
														<label>Category</label>
														<form:select
															path="product.productSubCategory.productCategory.id"
															class="form-control" id="Category-id">
															<form:option value="0">--Select--</form:option>
															<form:options items="${categoryList}" itemValue="id"
																itemLabel="name" />
														</form:select>
														<form:errors
															path="product.productSubCategory.productCategory.id"
															cssClass="errorMsg" />
													</div>
												</div>

												<div class="col-lg-6">
													<div class="form-group">
														<label>Sub Category</label>
														<form:select path="product.productSubCategory.id"
															class="form-control" id="SubCategory-id">
															<form:option value="0">--Select--</form:option>
														</form:select>
														<form:errors path="product.productSubCategory.id"
															cssClass="errorMsg" />
													</div>
												</div>
											</div>

											<div class="row">
												<div class="col-lg-6">
													<div class="form-group">
														<label>Product</label>
														<form:select path="idProduct" class="form-control"
															id="Product-id">
															<form:option value="0">--Select--</form:option>
														</form:select>
														<form:errors path="idProduct" cssClass="errorMsg" />
													</div>
												</div>

												<div class="col-lg-6">
													<div class="form-group">
														<label>Quantity</label>
														<form:input path="requestedCount" class="form-control" />
														<form:errors path="requestedCount" cssClass="errorMsg" />
													</div>
												</div>
											</div>

											<div class="row">
												<div class="col-lg-12">
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/requests"
														formmethod="post">Submit</button>
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/requests"
														formmethod="get">Back</button>
												</div>
											</div>


										</c:otherwise>

									</c:choose>



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
