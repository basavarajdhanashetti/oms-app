<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

		<form:form commandName="vendorQuotForm">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
								<h1 class="page-header">Vendor Quotations </h1>
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
												<label>Vendor Name</label> ${vendorQuotForm.vendor.name }
											</div>
										</div>
										<div class="col-lg-6">
											<div class="form-group">
												<label>Contact Details</label> ${vendorQuotForm.vendor.contactNo} - ${vendorQuotForm.vendor.emailId } 
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
																	<td>Unit Price</td>
																	<td>Discount (%)</td>
																	<td>Sale Price</td>
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${vendorQuotForm.items}" var="item"
																	varStatus="row">
																	<form:hidden path="items[${row.index}].category" />
																	<form:hidden path="items[${row.index}].subCategory" />
																	<form:hidden path="items[${row.index}].productId" />
																	<form:hidden path="items[${row.index}].quantity" id="quot_quantity-${row.index}" />
																	<form:hidden path="items[${row.index}].description" />
																	<tr>
																		<td>${item.product.productSubCategory.productCategory.name}</td>
																		<td>${item.product.productSubCategory.name}</td>
																		<td>${item.product.name}</td>
																		<td>${item.description}</td>
																		<td>${item.quantity}</td>
																		<td><form:input path="items[${row.index}].unitPrice" class="form-control"
																				id="quot_unitPrice-${row.index}" readonly="${readOnlyVals}" onchange="calculateAmount(${row.index})"/>
																		</td>
																		<td><form:input path="items[${row.index}].discount" class="form-control"
																				id="quot_discount-${row.index}" readonly="${readOnlyVals}" onchange="calculateAmount(${row.index})"/>
																		</td>
																		<td><form:input path="items[${row.index}].salePrice" class="form-control"
																				id="quot_salePrice-${row.index}" readonly="true" onchange="calculateAmount(${row.index})"/>
																		</td>
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
										<div class="col-lg-6">
											<div class="form-group">
												<label>Quotation Amount</label>
												<form:input path="quoteAmount" class="form-control"
																				id="quoteAmount" readonly="true"/>
																				
											</div>
										</div>
										<div class="col-lg-6">
											<div class="form-group">
												<label>Tax</label>
												<form:input path="tax" class="form-control"
																				id="tax" onchange="calculateTax()"/>
																				
											</div>
										</div>
										<div class="col-lg-6">
											<div class="form-group">
												<label>Total Amount</label>
												<form:input path="totalAmount" class="form-control"
																				id="totalAmount" readonly="true"/>
																				
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-lg-12">
											<button type="submit" class="btn btn-primary"
														formaction="${pageContext.request.contextPath}/quotations/add/${rowInd}"
														formmethod="post">Update</button>
											<button type="submit" class="btn btn-primary"
												formaction="${pageContext.request.contextPath}/quotations/back"
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



</body>

<script type="text/javascript">

function calculateAmount(index){
	var quantity = $("#quot_quantity-"+index).val();
	var unitPrice = $("#quot_unitPrice-"+index).val();
	var discount = $("#quot_discount-"+index).val();
	var salePrice = ((quantity * unitPrice) -  ((quantity * unitPrice) * (discount/100)));
	$("#quot_salePrice-"+index).val(salePrice);
	
	
	var quoteAmount = 0;
	var arrayLen = "${fn:length(vendorQuotForm.items)}";
	
	for(var i=0 ; i< arrayLen; i++){
		quoteAmount = quoteAmount + parseFloat($("#quot_salePrice-"+i).val());
	}
	
	$("#quoteAmount").val(quoteAmount);
	var tax  = $("#tax").val();
	var totalAmount = (quoteAmount + (quoteAmount*tax/100));
	$("#totalAmount").val(totalAmount);
}

function calculateTax(){
	var quoteAmount = 0;
	var arrayLen = "${fn:length(vendorQuotForm.items)}";
	
	for(var i=0 ; i< arrayLen; i++){
		quoteAmount = quoteAmount + parseFloat($("#quot_salePrice-"+i).val());
	}
	
	$("#quoteAmount").val(quoteAmount);
	var tax  = $("#tax").val();
	var totalAmount = (quoteAmount + (quoteAmount*tax/100));
	$("#totalAmount").val(totalAmount);
}
</script>

</html>
