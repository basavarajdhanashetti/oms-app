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
							<c:choose>
								<c:when test="${taskSummary.name == 'Approve Purchase Request'}">
									<h1 class="page-header">Approve Purchase Request</h1>
									<c:set var="readOnlyVals" value="true" />
								</c:when>
								<c:when test="${taskSummary.name == 'Quotation Submission'}">
									<h1 class="page-header">Quotation Submission for
										${purchaseReqForm.requestNo}</h1>
								</c:when>
								<c:otherwise>
									<h1 class="page-header">New Purchase Request</h1>
								</c:otherwise>
							</c:choose>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->

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
																	<td>Vendor Name</td>
																	<td>Contact No</td>
																	<td>Email ID</td>
																	<td>Quotation Amount</td>
																	<td>Tax Amount</td>
																	<td>Total Amount</td>
																	<td>Action</td>
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${prQuotationForm.quotations}" var="quot"
																	varStatus="row">
																	<tr>
																		
																		<td>${quot.vendor.name}</td>
																		<td>${quot.vendor.contactNo}</td>
																		<td>${quot.vendor.emailId}</td>
																		<td>${quot.quoteAmount}</td>
																		<td>${quot.tax}</td>
																		<td>${quot.totalAmount}</td>
																		<td><a href="${pageContext.request.contextPath}/quotations/add/${row.index}">Edit</a></td>
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
												<c:when
													test="${taskSummary.name == 'Approve Purchase Request'}">
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/approval?action=true"
														formmethod="post">Approve</button>
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/approval?action=false"
														formmethod="post">Reject</button>
												</c:when>
												<c:when test="${taskSummary.name == 'Quotation Submission'}">
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/save?taskId=${taskSummary.id}"
														formmethod="post">Save For Later Submit</button>

													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/complete?taskId=${taskSummary.id}"
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
