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

		<form:form commandName="purchaseReqForm">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<c:choose>
								<c:when test="${taskSummary.name == 'Approve Purchase Request'}">
									<h1 class="page-header">Approve Purchase Request</h1>
									<c:set var = "readOnlyVals" value = "true"/>
								</c:when>
								<c:when test="${taskSummary.name == 'Modify Purchase Request'}">
									<h1 class="page-header">Modify Purchase Request</h1>
								</c:when>
								<c:otherwise>
									<h1 class="page-header">New Purchase Request</h1>
								</c:otherwise>
							</c:choose>
							<form:hidden path="id"/>
							<form:hidden path="requestedBy"/>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">Request Details</div>
								<div class="panel-body">
									<form:hidden path="id" id="Add-Update-Bank-ID" />
									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label>Department</label>
												<form:select path="department" class="form-control"
													id="department-id" disabled="${readOnlyVals}">
													<form:option value="">--select--</form:option>
													<form:options items="${departmentList}" itemValue="id"
														itemLabel="name" />
												</form:select>
												<form:errors path="department" cssClass="errorMsg" />
											</div>
										</div>

										<div class="col-lg-6">
											<div class="form-group">
												<label>Request No</label>
												<form:input path="requestNo" class="form-control"
													id="bnm-dd-id" disabled="${readOnlyVals}"/>
												<form:errors path="requestNo" cssClass="errorMsg" />
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label>Request Date</label>
												<div class="input-group date" id="datetimepicker1">
													<form:input path="requestDate" class="form-control"
														placeholder="Select Date" disabled="${readOnlyVals}"/>
												</div>

											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-lg-12" align="right">
											<c:if
												test="${taskSummary ==  null or taskSummary.name != 'Approve Purchase Request'}">
												<a href="newpritem">Add Item</a>
											</c:if>

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
																	<c:if
																		test="${taskSummary ==  null or taskSummary.name != 'Approve Purchase Request'}">
																		<td>Action</td>
																	</c:if>
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${purchaseReqForm.items}" var="item"
																	varStatus="row">
																	<form:hidden path="items[${row.index}].category" />
																	<form:hidden path="items[${row.index}].subCategory" />
																	<form:hidden path="items[${row.index}].productId" />
																	<form:hidden path="items[${row.index}].quantity" />
																	<form:hidden path="items[${row.index}].description" />
																	<tr>
																		<td>${item.product.productSubCategory.productCategory.name}</td>
																		<td>${item.product.productSubCategory.name}</td>
																		<td>${item.product.name}</td>
																		<td>${item.description}</td>
																		<td>${item.quantity}</td>
																		<c:if
																			test="${taskSummary ==  null or taskSummary.name != 'Approve Purchase Request'}">
																			<td><a href="${pageContext.request.contextPath}/removepritem?index=${row.index}">
																					Delete </a></td>
																		</c:if>
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
														formaction="${pageContext.request.contextPath}/purchases/approval?action=true"
														formmethod="post">Approve</button>
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/purchases/approval?action=false"
														formmethod="post">Reject</button>
												</c:when>
												<c:when
													test="${taskSummary.name == 'Modify Purchase Request'}">
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/purchases/complete" formmethod="post">Submit</button>
													<button type="reset" class="btn btn-primary">Reset</button>
												</c:when>
												<c:otherwise>
													<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/purchases" formmethod="post">Submit</button>
													<button type="reset" class="btn btn-primary">Reset</button>
												</c:otherwise>
											</c:choose>


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
