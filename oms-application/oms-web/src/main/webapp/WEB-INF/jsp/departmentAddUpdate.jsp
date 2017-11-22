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

		<form:form commandName="departmentForm">
			<!-- Page Content -->
			<div id="page-wrapper">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Add/Update Department Details</h1>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<!-- /.row -->

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">Department Details</div>
								<div class="panel-body">
									<form:hidden path="id" />

									<div class="row">

										<div class="col-lg-6">
											<div class="form-group">
												<label>Category Name: </label>
												<form:input path="name" class="form-control" />
												<form:errors path="name" cssClass="errorMsg" />
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-lg-12">
											<button type="submit" class="btn btn-primary"
												formaction="${pageContext.request.contextPath}/departments"
												formmethod="post">Submit</button>
											<button type="submit" class="btn btn-primary"
												formaction="${pageContext.request.contextPath}/departments"
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
