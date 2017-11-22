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

<style>
/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	background-color: #fefefe;
	margin: auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}
</style>
</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<jsp:include page="navigation.jsp"></jsp:include>

		<form:form commandName="stockForm">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Stock Registration</h1>
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
																${inwardRegForm.purchaseOrder.poNumber}
															</div>
														</div>

														<div class="col-lg-6">
															<div class="form-group">
																<label>Order Amount</label>
																${inwardRegForm.purchaseOrder.poAmount}
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

													<c:forEach items="${stockForm.stockItems}" var="item"
														varStatus="row">

														<div class="row">
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Category</label>
																	${item.inItem.product.productSubCategory.productCategory.name}
																</div>
															</div>
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Sub Category</label>
																	${item.inItem.product.productSubCategory.name}
																</div>
															</div>
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Product Name</label> ${item.inItem.product.name}
																</div>
															</div>
														</div>
														<div class="dataTable_wrapper">
															<table
																class="table table-striped table-bordered table-hover">
																<thead>
																	<tr>
																		<td>Asset No</td>
																		<td>Serial No</td>
																		<td>Model</td>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${item.registers}" var="reg"
																		varStatus="loop">
																		<form:hidden
																			path="stockItems[${row.index}].registers[${loop.index}].id" />
																		<form:hidden
																			path="stockItems[${row.index}].registers[${loop.index}].idInwardItem" />
																		<form:hidden
																			path="stockItems[${row.index}].registers[${loop.index}].idProduct" />
																		<tr>
																			<td><form:input
																					path="stockItems[${row.index}].registers[${loop.index}].assetNo" /></td>
																			<td><form:input
																					path="stockItems[${row.index}].registers[${loop.index}].serialNo" /></td>
																			<td><form:input
																					path="stockItems[${row.index}].registers[${loop.index}].model" /></td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>

													</c:forEach>
													<div align="right">
														<button type="submit" class="btn btn-primary"
															formaction="${pageContext.request.contextPath}/stocks/save"
															formmethod="post">Save For Later Submit</button>
													</div>
													<div>
														<input type="checkbox" id="sbtCheck"
															onclick="checkSelection()" value="agree" /> Check to
														Submit
													</div>

												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-lg-12">
												<button type="submit" class="btn btn-primary" id="sbtBtn"
													disabled="true"
													formaction="${pageContext.request.contextPath}/stocks/complete"
													formmethod="post">Submit</button>
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