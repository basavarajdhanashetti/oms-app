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

		<form:form commandName="quotationSelectionForm">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<c:choose>
								<c:when test="${taskSummary.name == 'Quotation Approval'}">
									<h1 class="page-header">Quotation Approval</h1>
									<c:set var="readOnlyVals" value="true" />
									<form:hidden path="id" />
								</c:when>
								<c:when test="${taskSummary.name == 'Quotation Selection'}">
									<h1 class="page-header">Quotation Evaluation & Selection</h1>
								</c:when>
								<c:otherwise>
									<h1 class="page-header">Amend Quotation</h1>
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
												<label>Department</label>
												${purchaseReqForm.departmentDetails.name}
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
																	<c:if
																		test="${taskSummary.name == 'Quotation Selection'}">
																		<td>Select</td>
																	</c:if>
																	<td>Vendor Name</td>
																	<td>Contact No</td>
																	<td>Email ID</td>
																	<td>Quotation Amount</td>
																	<td>Tax Amount</td>
																	<td>Total Amount</td>
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${quotations}" var="quot"
																	varStatus="row">
																	<c:choose>
																		<c:when test="${quot.id == quotationSelectionForm.id}">
																			<tr bgcolor="yellow" >
																				<td><a
																					href="${pageContext.request.contextPath}/quotations/view/${quot.id}">${quot.vendor.name}</a></td>
																				<td>${quot.vendor.contactNo}</td>
																				<td>${quot.vendor.emailId}</td>
																				<td>${quot.quoteAmount}</td>
																				<td>${quot.tax}</td>
																				<td>${quot.totalAmount}</td>
																			</tr>

																		</c:when>
																		<c:otherwise>
																			<tr>
																				<c:if
																					test="${taskSummary.name == 'Quotation Selection'}">
																					<td><form:radiobutton path="id"
																							value="${quot.id}" id="QuotSelectionID" /></td>
																				</c:if>
																				<td><a
																					href="${pageContext.request.contextPath}/quotations/view/${quot.id}">${quot.vendor.name}</a></td>
																				<td>${quot.vendor.contactNo}</td>
																				<td>${quot.vendor.emailId}</td>
																				<td>${quot.quoteAmount}</td>
																				<td>${quot.tax}</td>
																				<td>${quot.totalAmount}</td>
																			</tr>
																		</c:otherwise>
																	</c:choose>

																</c:forEach>
															</tbody>
														</table>
														<c:if test="${taskSummary.name == 'Quotation Approval'}">
														Note: Selected Quotation is marked in green background.
														</c:if>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-lg-12">
											<c:choose>
												<c:when test="${taskSummary.name == 'Quotation Approval'}">
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/complete?taskId=${taskSummary.id}&approved=true"
														formmethod="post">Approve</button>
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/complete?taskId=${taskSummary.id}&approved=false"
														formmethod="post">Reject</button>
												</c:when>
												<c:when test="${taskSummary.name == 'Quotation Selection'}">
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/complete?taskId=${taskSummary.id}&amendreq=true"
														formmethod="post">Ask for Amend</button>

													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/complete?taskId=${taskSummary.id}&amendreq=false"
														formmethod="post" onclick="return checkSelection()">Select
														& Submit</button>
												</c:when>
												<c:otherwise>
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/complete?taskId=${taskSummary.id}"
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
			var selectedId = $("input[id=QuotSelectionID]:checked").val();
			if (selectedId == undefined || selectedId == 0) {
				alert("Select a Quotation.")
				return false;
			}
		}
	</script>

</body>

</html>
