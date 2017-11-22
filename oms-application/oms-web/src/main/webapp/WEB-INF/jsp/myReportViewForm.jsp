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

		<form:form commandName="reportForm" id="report-form-id">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Custom Report Configuration</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-10">
							<div class="panel panel-default">
								<div class="panel-heading">Report Details</div>
								<div class="panel-body">
									<form:hidden path="id" id="Report-ID" />
									<div class="row">

										<div class="col-lg-6">
											<div class="form-group">
												<label>Report Name</label>
												<form:input path="name" class="form-control" id="bnm-dd-id"
													disabled="${readOnlyVals}" />
												<form:errors path="name" cssClass="errorMsg" />
											</div>
										</div>

										<div class="col-lg-6">
											<div class="form-group">
												<label>Report Type</label>
												<form:select path="idView" class="form-control"
													id="table-view-id" disabled="${readOnlyVals}">
													<form:option value="0">--Select--</form:option>
													<form:options items="${tableViewsList}" itemValue="id"
														itemLabel="displayName" />
												</form:select>
												<form:errors path="idView" cssClass="errorMsg" />
											</div>
										</div>


									</div>

									<div class="row">
										<div class="col-lg-6">
											<div class="form-group">
												<label>Chart Type</label> <label class="radio-inline">
													<form:radiobutton path="chartType" id="chartType-id"
														value="AreaChart" />Area
												</label> <label class="radio-inline"> <form:radiobutton
														path="chartType" id="chartType-id" value="BarChart" />Bar
												</label> <%-- <label class="radio-inline"> <form:radiobutton
														path="chartType" id="chartType-id" value="DonutChart" />Donut
												</label> --%>

											</div>
										</div>

										<div class="col-lg-6">
											<div class="form-group">
												<label>Duration Type</label> <label class="radio-inline">
													<form:radiobutton path="duration"
														name="optionsRadiosInline" id="optionsRadiosInline1"
														value="WEEK" />Week
												</label> <label class="radio-inline"> <form:radiobutton
														path="duration" id="optionsRadiosInline2" value="MONTH" />Month
												</label> <label class="radio-inline"> <form:radiobutton
														path="duration" name="optionsRadiosInline"
														id="optionsRadiosInline3" value="QUATER" />Quarter
												</label> <label class="radio-inline"> <form:radiobutton
														path="duration" name="optionsRadiosInline"
														id="optionsRadiosInline3" value="HALFYEAR" />Half Year
												</label> <label class="radio-inline"> <form:radiobutton
														path="duration" name="optionsRadiosInline"
														id="optionsRadiosInline3" value="YEAR" />Year
												</label>


											</div>
										</div>
									</div>

									<div class="row">

										<div class="col-lg-6">
											<div class="form-group">
												<label>Date Column </label>
												<form:select path="dateCriteriaColumn.id"
													class="form-control" id="Date-Criteria-id"
													disabled="${readOnlyVals}">
													<form:option value="0">--Select--</form:option>
													<form:options items="${tableViewColumnsList}"
														itemValue="id" itemLabel="displayName" />
												</form:select>
												<form:errors path="dateCriteriaColumn.id"
													cssClass="errorMsg" />
											</div>

										</div>
										<div class="col-lg-6">
											<div class="form-group" id="xdimension-form-id">
												<label>X Dimension </label>
												<form:select path="xDimensionName.id" class="form-control"
													id="xDimensionName-id" disabled="${readOnlyVals}">
													<form:option value="0">--Select--</form:option>
													<form:options items="${tableViewColumnsList}"
														itemValue="id" itemLabel="displayName" />
												</form:select>
												<form:errors path="xDimensionName.id" cssClass="errorMsg" />
											</div>

										</div>

									</div>
								</div>
							</div>
						</div>
						<!-- /.col-lg-12 -->
					</div>

					<div class="row">
						<div class="col-lg-10">
							<div class="panel panel-default">
								<div class="panel-heading">
									Chart Matrices

									<div class="pull-right">
										<div class="btn-group">
											<button type="button"
												class="btn btn-default btn-xs dropdown-toggle"
												data-toggle="dropdown">
												Actions <span class="caret"></span>
											</button>
											<ul class="dropdown-menu pull-right" role="menu">
												<li>
													<button
														class="btn btn-outline btn-primary btn-lg btn-block btn-sm"
														type="submit"
														formaction="${pageContext.request.contextPath}/reports/${reportForm.id}/metrices"
														formmethod="post">Add</button>
												</li>

											</ul>
										</div>
									</div>

								</div>
								<div class="panel-body">
									<div class="dataTable_wrapper">
										<table id="dataTables"
											class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<td>Name</td>
													<td>Operation</td>
													<td>Acton</td>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${reportForm.matrices}" var="matric"
													varStatus="row">
													<c:choose>
													<c:when test="${(reportForm.id == 0 && matric.idReport ==0) || (reportForm.id != 0 && matric.idReport !=0)}">
														<tr>
															<td><form:select
																	path="matrices[${row.index}].idMatric"
																	class="form-control" id="xDimensionName-id"
																	disabled="${readOnlyVals}">
																	<form:option value="0">--Select--</form:option>
																	<form:options items="${tableViewColumnsList}"
																		itemValue="id" itemLabel="displayName" />
																</form:select></td>
															<td><form:hidden path="matrices[${row.index}].id" />
																<form:hidden path="matrices[${row.index}].idReport" />
																<label class="radio-inline"> <form:radiobutton
																		path="matrices[${row.index}].operation" value="COUNT" />Count
															</label> <label class="radio-inline"> <form:radiobutton
																		path="matrices[${row.index}].operation" value="SUM" />Sum
															</label> <label class="radio-inline"> <form:radiobutton
																		path="matrices[${row.index}].operation" value="AVG" />Average
															</label></td>
															<td>
																<button type="submit" class="btn btn-warning btn-circle"
																	formaction="${pageContext.request.contextPath}/reports/${reportForm.id}/metrices/${row.index}"
																	formmethod="post">
																	<i class="fa fa-times"></i>
																</button>

															</td>
														</tr>
													</c:when>
													<c:otherwise>
														<form:hidden path="matrices[${row.index}].id" />
													</c:otherwise>
													</c:choose>
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
							<div class="col-lg-12">
								<button type="submit" class="btn btn-primary"
									formaction="${pageContext.request.contextPath}/reports"
									formmethod="post">Submit</button>
								<button type="submit" class="btn btn-primary"
									formaction="${pageContext.request.contextPath}/reports"
									formmethod="get">Back</button>
							</div>
						</div>
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
	$("#table-view-id").change(
			function() {

				var viewId = $("#table-view-id").val();
				var reportId = $("#Report-ID").val();
				
				$('#report-form-id').attr(
						'action',
						"${pageContext.request.contextPath}/reports/"
								+ reportId + "/refresh").submit();
			})

	$("input[id='chartType-id']").click(function() {
		var radioValue = $("input[id='chartType-id']:checked").val();
		if ("DonutChart" == radioValue) {
			$("#xdimension-form-id").hide();
		} else {
			$("#xdimension-form-id").show();
		}
	});

	$(document).ready(function() {
		var radioValue = $("input[id='chartType-id']:checked").val();
		if ("DonutChart" == radioValue) {
			$("#xdimension-form-id").hide();
		} else {
			$("#xdimension-form-id").show();
		}
	})
</script>
</html>
