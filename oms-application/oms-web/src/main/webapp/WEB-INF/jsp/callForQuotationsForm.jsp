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

		<form:form commandName="prQuotationForm">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Call For Quotations</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">Vendor For Quotation Requests</div>
								<div class="panel-body">

									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label>Select Vendors</label>
												<form:select path="vendorIds" class="form-control"
													disabled="${readOnlyVals}" multiple="true">
													<form:options items="${vendorList}" itemValue="id"
														itemLabel="name" />
												</form:select>
												<form:errors path="vendorIds" cssClass="errorMsg" />
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">Purchase Request Details</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label>Department</label> ${department.name}
											</div>
										</div>

										<div class="col-lg-6">
											<div class="form-group">
												<label>Request No</label> ${purchaseReqForm.requestNo }
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label>Request Date</label> ${purchaseReqForm.requestDate}
											</div>
										</div>
									</div>
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
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${purchaseReqForm.items}" var="item"
																	varStatus="row">
																	<tr>
																		<td>${item.product.productSubCategory.productCategory.name}</td>
																		<td>${item.product.productSubCategory.name}</td>
																		<td>${item.product.name}</td>
																		<td>${item.description}</td>
																		<td>${item.quantity}</td>
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
												<c:when test="${taskSummary.name == 'Call For Quotations'}">
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/call/complete?taskId=${taskSummary.id}"
														formmethod="post">Submit</button>
												</c:when>
												<c:otherwise>


												</c:otherwise>
											</c:choose>
											<button type="reset" class="btn btn-primary"
												formaction="${pageContext.request.contextPath}/process/tasks"
												formmethod="post">Back</button>

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



</body>

</html>
