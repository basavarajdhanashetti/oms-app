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

		<form:form commandName="purchaseOrder">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<c:choose>
								<c:when test="${taskSummary.name == 'Purchase Order Approval'}">
									<h1 class="page-header">Purchase Order Approval</h1>
								</c:when>
								<c:otherwise>
									<h1 class="page-header">Purchase Order</h1>
								</c:otherwise>
							</c:choose>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">Purchase Order Details</div>
								<div class="panel-body">
									<div class="panel-heading"><b>Requestor Details</b></div>
									<div class="row">
										<div class="col-lg-12">
											<div class="panel panel-default">
												<div class="panel-body">
													<div class="row">
														<div class="col-lg-6">
															<form:hidden path="id" />
															<div class="form-group">
																<label>Department</label>
																${purchaseRequest.departmentDetails.name}
															</div>
														</div>

														<div class="col-lg-6">
															<div class="form-group">
																<label>Request No</label> ${purchaseRequest.requestNo }
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-lg-6">
															<div class="form-group">
																<label>Request Date</label>
																${purchaseRequest.requestDate}
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="panel-heading"><b>Vendor Details</b></div>
									<div class="row">
										<div class="col-lg-12">
											<div class="panel panel-default">
												<div class="panel-body">
													<div class="row">
														<div class="col-lg-6">
															<form:hidden path="id" />
															<div class="form-group">
																<label>Vendor Name</label>
																${purchaseOrder.vendor.name}
															</div>
														</div>
														<div class="col-lg-6">
															<div class="form-group">
																<label>Contact No</label> ${purchaseOrder.vendor.contactNo }
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-lg-6">
															<form:hidden path="id" />
															<div class="form-group">
																<label>Email ID</label>
																${purchaseOrder.vendor.emailId}
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="panel-heading"><b>Purchase Order Details</b></div>
									<div class="row">
										<div class="col-lg-12">
											<div class="panel panel-default">
												<div class="panel-body">
													<div class="row">
														<div class="col-lg-6">
															<form:hidden path="id" />
															<div class="form-group">
																<label>Purchase Order No</label>
																${purchaseOrder.poNumber}
															</div>
														</div>

														<div class="col-lg-6">
															<div class="form-group">
																<label>Order Amount</label>
																${purchaseOrder.poAmount}
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-lg-6">
															<form:hidden path="id" />
															<div class="form-group">
																<label>Tax Amount</label>
																${purchaseOrder.tax}
															</div>
														</div>

														<div class="col-lg-6">
															<div class="form-group">
																<label>Total Amount</label>
																${purchaseOrder.totalAmount}
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="panel-heading"><b>Purchase Items</b></div>
									<div class="row">
										<div class="col-lg-12">
											<div class="panel panel-default">
												<div class="panel-body">
													<div class="dataTable_wrapper">
														<table
															class="table table-striped table-bordered table-hover">
															<thead>
																<tr>
																	<td>Category</td>
																	<td>Sub Category</td>
																	<td>Product</td>
																	<td>Description</td>
																	<td>Quantity</td>
																	<td>Unit Price</td>
																	<td>Discount (%)</td>
																	<td>Sale Price</td>
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${purchaseOrder.items}" var="item" varStatus="row">
																	<tr>
																		<td>${item.product.productSubCategory.productCategory.name}</td>
																		<td>${item.product.productSubCategory.name}</td>
																		<td>${item.product.name}</td>
																		<td>${item.description}</td>
																		<td>${item.quantity}</td>
																		<td>${item.unitPrice}</td>
																		<td>${item.discount}</td>
																		<td>${item.salePrice}</td>
																	</tr>

																</c:forEach>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-lg-12">
											<c:choose>
												<c:when test="${taskSummary.name == 'Purchase Order Approval'}">
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/purchaseorders/complete?taskId=${taskSummary.id}&approved=true"
														formmethod="post">Approve</button>
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/purchaseorders/complete?taskId=${taskSummary.id}&approved=false"
														formmethod="post">Reject</button>
												</c:when>
												<c:otherwise>
													
												</c:otherwise>
											</c:choose>
											<button type="submit" class="btn btn-primary"
												formaction="${pageContext.request.contextPath}/process/tasks"
												formmethod="get">Back</button>

										</div>
									</div>


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

	<script type="text/javascript">
		function checkSelection() {
			var selectedId = $("input[id=QuotSelectionID]:checked").val();
			if (selectedId == undefined || selectedId == 0) {
				alert("Select a Quotation.")
				return false;
			}
		}
	</script>

</body>

</html>
