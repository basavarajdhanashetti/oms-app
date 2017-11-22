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

		<form:form commandName="reportingViewForm">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Add/Update Reporting View Details</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">View Details</div>
								<div class="panel-body">
									<form:hidden path="id" />

									<div class="row">

										<div class="col-lg-6">
											<div class="form-group">
												<label>View Name: </label>
												<form:input path="name" class="form-control" />
												<form:errors path="name" cssClass="errorMsg" />
											</div>
										</div>

										<div class="col-lg-6">
											<div class="form-group">
												<label>Display Name: </label>
												<form:input path="displayName" class="form-control" />
												<form:errors path="displayName" cssClass="errorMsg" />
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									View Columns
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
														formaction="${pageContext.request.contextPath}/reportingviews/${reportingViewForm.id}/columns/0"
														formmethod="post">New</button>
												</li>
											</ul>
										</div>
									</div>
								</div>
								<div class="panel-body">
									<div class="dataTable_wrapper">
										<table class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<td>Column Name</td>
													<td>Display Name</td>
													<td>Action</td>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${reportingViewForm.viewColumns}"
													var="item" varStatus="row">
													<form:hidden path="viewColumns[${row.index}].id" />
													<form:hidden path="viewColumns[${row.index}].idReportView" />

													<c:if
														test="${(item.id==0 && item.idReportView == 0) || (item.idReportView != 0) }">
														<tr>
															<td><form:input
																	path="viewColumns[${row.index}].columnName" /></td>
															<td><form:input
																	path="viewColumns[${row.index}].displayName" /></td>
															<td>
																<button
																	class="btn btn-warning btn-circle"
																	type="submit"
																	formaction="${pageContext.request.contextPath}/reportingviews/${reportingViewForm.id}/columns/${row.index}/delete"
																	formmethod="post"><i class="fa fa-times"></i></button>
															</td>
														</tr>
													</c:if>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">&nbsp; &nbsp;</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-lg-12">
											<button type="submit" class="btn btn-primary"
												formaction="${pageContext.request.contextPath}/reportingviews"
												formmethod="post">Submit</button>
											<button type="submit" class="btn btn-primary"
												formaction="${pageContext.request.contextPath}/reportingviews"
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

</script>
</html>
