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

<title>SB Admin 2 - Bootstrap Admin Theme</title>

<!-- Bootstrap Core CSS -->
<link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

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
							<c:if test="${purchaseReqForm.id==0}">
								<h1 class="page-header">New Purchase Request</h1>
							</c:if>
							<c:if test="${purchaseReqForm.id!=0}">
								<h1 class="page-header">Modify Purchase Request</h1>
							</c:if>

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
													id="department-id">
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
													id="bnm-dd-id" />
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
														placeholder="Select Date" />
												</div>

											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-lg-12" align="right">
											<a href="newpritem">Add Item</a>
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
																	<td>Action</td>
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
																		<td><a href="removepritem?index=${row.index}"> Delete </a></td>
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
											<c:if test="${purchaseReqForm.id==0}">
												<button type="submit" class="btn btn-primary"
													formaction="purchases" formmethod="post">Save
													Details</button>
											</c:if>
											<c:if test="${purchaseReqForm.id!=0}">
												<button type="submit" class="btn btn-primary"
													formaction="purchases/complete" formmethod="post">Update
													Details</button>
											</c:if>
											<button type="reset" class="btn btn-primary">Reset</button>
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

	<!-- jQuery -->
	<script src="../vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../dist/js/sb-admin-2.js"></script>

	<!--Project custom JavaScript -->
	<script src="<c:url value="../oms/oms-rest-client.js"/>"></script>

	<!-- Datepicker related JavaScript -->
	<script src="<c:url value="../datepicker/js/bootstrap-datepicker.js"/>"></script>

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

</body>

</html>
