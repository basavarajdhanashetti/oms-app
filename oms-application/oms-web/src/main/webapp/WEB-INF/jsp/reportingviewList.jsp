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
						<h1 class="page-header">Reporting View Details</h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
								<a href="${pageContext.request.contextPath}/home/">Home</a>
								<div class="pull-right">
									<div class="btn-group">
										<button type="button"
											class="btn btn-default btn-xs dropdown-toggle"
											data-toggle="dropdown">
											Actions <span class="caret"></span>
										</button>
										<ul class="dropdown-menu pull-right" role="menu">
											<li><a
												href="${pageContext.request.contextPath}/reportingviews/0">Add
													New </a></li>

										</ul>
									</div>
								</div>
							</div>
							<div class="panel-body">
								<table id="dataTables-example"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<td>Name</td>
											<td>Display Name</td>
											<td>Actions</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${reportingviewList}" var="reg" varStatus="loop">
											<tr>
												<td>${reg.name}</td>
												<td>${reg.displayName}</td>
												<td><a
													href="${pageContext.request.contextPath}/reportingviews/${reg.id}">Edit</a></td>
												
											</tr>
										</c:forEach>
									</tbody>
								</table>

							</div>
						</div>
					</div>
				</div>
				<!-- /.row -->

			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

</body>


</script>
</html>
