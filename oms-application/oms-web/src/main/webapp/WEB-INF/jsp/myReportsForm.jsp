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

		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">My Reports</h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">Report Details
							<div class="pull-right">
									<div class="btn-group">
										<button type="button"
											class="btn btn-default btn-xs dropdown-toggle"
											data-toggle="dropdown">
											Actions <span class="caret"></span>
										</button>
										<ul class="dropdown-menu pull-right" role="menu">
											<li >
											<form:form>
												<button class="btn btn-outline btn-primary btn-lg btn-block btn-sm"
													type="submit"
													formaction="${pageContext.request.contextPath}/reports/0"
													formmethod="get">New</button>
											</form:form>
											</li>
											
										</ul>
									</div>
								</div>
							</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-12">
										<div class="panel panel-default">
											<div class="panel-body">
												<div class="dataTable_wrapper">
													<table id="dataTables-example"
														class="table table-striped table-bordered table-hover">
														<thead>
															<tr>
																<td>Name</td>
																<td>Report type</td>
																<td>Chart Type</td>
																<td>Duration</td>
																<td>Actopm</td>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${reportsList}" var="report"
																varStatus="row">
																<tr>
																	<td>${report.name}</td>
																	<td>${report.view.name}</td>
																	<td>${report.chartType}</td>
																	<td>${report.duration}</td>
																	<td><a
																		href="${pageContext.request.contextPath}/reports/${report.id}">Edit</a>
																		&nbsp;&nbsp;&nbsp; <a
																		href="${pageContext.request.contextPath}/reports/${report.id}/delete">Delete</a>
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
									<div class="col-lg-12">
										<button type="submit" class="btn btn-primary"
											formaction="${pageContext.request.contextPath}/home"
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
