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

		<form:form commandName="inwardRegForm">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<c:choose>
								<c:when
									test="${taskSummary.name == 'Inward Materials Approval'}">
									<h1 class="page-header">Inward Materials Approval</h1>
								</c:when>
								<c:otherwise>
									<h1 class="page-header">Inward Materials</h1>
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
									<div class="panel-heading">
										<b>Purchase Order Details</b>
									</div>
									<div class="row">
										<div class="col-lg-12">
											<div class="panel panel-default">
												<div class="panel-body">
													<div class="row">
														<div class="col-lg-6">
															<div class="form-group">
																<label>Purchase Order No</label>
																${purchaseOrder.poNumber}
															</div>
														</div>

														<div class="col-lg-6">
															<div class="form-group">
																<label>Order Amount</label> ${purchaseOrder.poAmount}
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-lg-6">
															<form:hidden path="id" />
															<div class="form-group">
																<label>Tax Amount</label> ${purchaseOrder.tax}
															</div>
														</div>

														<div class="col-lg-6">
															<div class="form-group">
																<label>Total Amount</label> ${purchaseOrder.totalAmount}
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="panel-heading">
										<b>Vendor Details</b>
									</div>
									<div class="row">
										<div class="col-lg-12">
											<div class="panel panel-default">
												<div class="panel-body">
													<div class="row">
														<div class="col-lg-6">
															<form:hidden path="id" />
															<div class="form-group">
																<label>Vendor Name</label> ${purchaseOrder.vendor.name}
															</div>
														</div>
														<div class="col-lg-6">
															<div class="form-group">
																<label>Contact No</label>
																${purchaseOrder.vendor.contactNo }
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-lg-6">
															<form:hidden path="id" />
															<div class="form-group">
																<label>Email ID</label> ${purchaseOrder.vendor.emailId}
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>


									<div class="panel-heading">
										<b>Purchase Items</b>
									</div>
									<div class="row">
										<div class="col-lg-12">
											<div class="panel panel-default">
												<div class="panel-body">
													<div class="dataTable_wrapper">
														<form:hidden path="id" />
														<form:hidden path="idPurchaseOrder" />

														<table
															class="table table-striped table-bordered table-hover">
															<thead>
																<tr>
																	<td>Category</td>
																	<td>Sub Category</td>
																	<td>Product</td>
																	<td>Ordered Quantity</td>
																	<td>Received Price</td>
																	<td>Comments</td>

																</tr>
															</thead>
															<tbody>
																<c:forEach items="${inwardRegForm.items}" var="item"
																	varStatus="row">
																	<tr>
																		<td>${item.product.productSubCategory.productCategory.name}</td>
																		<td>${item.product.productSubCategory.name}</td>
																		<td>${item.product.name}</td>
																		<td>${item.orderedQty}</td>
																		<c:choose>
																			<c:when
																				test="${taskSummary.name == 'Inward Materials'}">
																				<form:hidden path="items[${row.index}].id" />
																				<form:hidden
																					path="items[${row.index}].idInwardRegister" />
																				<form:hidden path="items[${row.index}].idProduct" />
																				<form:hidden path="items[${row.index}].orderedQty" />
																				<td><form:input
																						path="items[${row.index}].receivedQty" /></td>
																				<td><form:input
																						path="items[${row.index}].comments" /></td>
																			</c:when>
																			<c:otherwise>
																				<c:choose>
																					<c:when
																						test="${item.receivedQty != item.orderedQty }">
																						<c:set var="setForeColor" value="red" />
																					</c:when>
																					<c:otherwise>
																						<c:set var="setForeColor" value="black" />
																					</c:otherwise>
																				</c:choose>
																				<td><label style="color: ${setForeColor}">${item.receivedQty}</label></td>
																				<td><label style="color: ${setForeColor}">${item.comments}</label></td>
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</div>
													<c:if
														test="${inwardRegForm.status == 'REJECTED' || inwardRegForm.status == 'PENDING_APPROVAL' }">
														<div>
															<div class="form-group">
																<label>Comments from Approver:</label>
																<c:choose>
																	<c:when
																		test="${taskSummary.name == 'Inward Materials Approval'}">
																		<form:textarea path="comments" />
																	</c:when>
																	<c:otherwise>
																		<label style="color: red">${inwardRegForm.comments}</label>
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
													</c:if>
													<div align="right">
														<c:if test="${taskSummary.name == 'Inward Materials'}">
															<button type="submit" class="btn btn-primary"
																formaction="${pageContext.request.contextPath}/stores/inwards/save"
																formmethod="post">Save For Later Submit</button>
														</c:if>
													</div>
													<div>
														<c:if test="${taskSummary.name == 'Inward Materials'}">
															<input type="checkbox" id="sbtCheck"
																onclick="checkSelection()" value="agree" /> Check to Submit 
													</c:if>
													</div>

												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-lg-12">
												<c:choose>
													<c:when
														test="${taskSummary.name == 'Inward Materials Approval'}">
														<button type="submit" class="btn btn-primary"
															formaction="${pageContext.request.contextPath}/stores/inwards/complete?approved=true"
															formmethod="post">Approve</button>
														<button type="submit" class="btn btn-primary"
															formaction="${pageContext.request.contextPath}/stores/inwards/complete?approved=false"
															formmethod="post">Reject</button>
													</c:when>
													<c:otherwise>
														<button type="submit" class="btn btn-primary" id="sbtBtn"
															disabled="true"
															formaction="${pageContext.request.contextPath}/stores/inwards/complete"
															formmethod="post">Submit</button>
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
			var agree = $("input[id=sbtCheck]:checked").val();
			if (agree == 'agree') {
				alert("Make sure you have filled all values.")
				$('#sbtBtn').removeAttr('disabled');
			} else {
				$('#sbtBtn').attr('disabled', 'disabled');
			}
		}
	</script>

</body>

</html>